package com.example.android.popcake;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.popcake.database.IngredientDAO;
import com.example.android.popcake.database.PopCakeRoomDatabase;
import com.example.android.popcake.database.RecipeDAO;
import com.example.android.popcake.model.Ingredient;
import com.example.android.popcake.model.Recipe;

import java.lang.reflect.Array;
import java.util.List;

/**
 * The configuration screen for the {@link PopCakeHomescreenWidget PopCakeHomescreenWidget} AppWidget.
 */
public class PopCakeHomescreenWidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "com.example.android.popcake.PopCakeHomescreenWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText;
    Spinner mSpinner;
    List<Recipe> mRecipes;
    PopCakeRoomDatabase mPopCakeRoomDB;

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = PopCakeHomescreenWidgetConfigureActivity.this;

            // Get currently selected value from the spinner, then save to preferences
            mSpinner = findViewById(R.id.sp_widget_config_recipe_list);
            // By default spinner position starts from 0, while our recipe id starts from 1,
            // hence why we add by 1.
            int selectedRecipeId = mSpinner.getSelectedItemPosition() + 1;
            Log.d(Const.APP_TAG, "saya pilih " + selectedRecipeId);


            SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
            prefs.putInt(PREF_PREFIX_KEY + mAppWidgetId, selectedRecipeId);
            prefs.apply();


            // When the button is clicked, store the string locally
            //String widgetText = mAppWidgetText.getText().toString();
            //saveTitlePref(context, mAppWidgetId, widgetText);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            PopCakeHomescreenWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public PopCakeHomescreenWidgetConfigureActivity() {
        super();
    }


    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static int loadSelectedRecipeIdPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int selectedRecipeId = prefs.getInt(PREF_PREFIX_KEY + appWidgetId, 1);
        return selectedRecipeId;
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        // Init DB
        //mPopCakeRoomDB = PopCakeRoomDatabase.getDatabase(this);


        setContentView(R.layout.pop_cake_homescreen_widget_configure);
        //mAppWidgetText = (EditText) findViewById(R.id.appwidget_text);

        // Fill spinner from DB
        new getRecipeAsyncTask(this, this).execute();


        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        //mAppWidgetText.setText(loadTitlePref(PopCakeHomescreenWidgetConfigureActivity.this, mAppWidgetId));
    }


    // Insert method for Ingredients
    static class getRecipeAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {
        PopCakeRoomDatabase mPopCakeRoomDB;
        private RecipeDAO asyncTaskRecipeDAO;
        Context mContext; // See https://stackoverflow.com/questions/37531862/how-to-pass-context-to-asynctask/37532046
        Activity mActivity; // see https://stackoverflow.com/a/31167792

        public getRecipeAsyncTask(Context context, Activity activity) {
            mContext = context;
            mActivity = activity;
            mPopCakeRoomDB = PopCakeRoomDatabase.getDatabase(mContext);
            asyncTaskRecipeDAO = mPopCakeRoomDB.recipeDAO();
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            return asyncTaskRecipeDAO.getRecipesForWidgets();
        }

        // This runs in UI when background thread finishes
        // In this case, we fill the spinner with recipe names
        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            Spinner asyncTaskSpinner;
            super.onPostExecute(recipes);
            String recipeNames[] = new String[recipes.size()];

            for(int i = 0; i<recipes.size(); i++) {
                recipeNames[i] = recipes.get(i).getName();
            }

            // Learned about setting up spinner and adapter from
            // https://stackoverflow.com/a/34808771
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    mContext,
                    android.R.layout.simple_spinner_dropdown_item,
                    recipeNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            asyncTaskSpinner = mActivity.findViewById(R.id.sp_widget_config_recipe_list);
            asyncTaskSpinner.setAdapter(adapter);
        }
    }
}

