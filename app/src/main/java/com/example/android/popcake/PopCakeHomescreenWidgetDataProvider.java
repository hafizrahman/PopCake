package com.example.android.popcake;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.popcake.database.IngredientDAO;
import com.example.android.popcake.database.PopCakeRoomDatabase;
import com.example.android.popcake.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;


/**
 * PopCakeHomescreenWidgetDataProvider acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */
public class PopCakeHomescreenWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    List<Ingredient> mIngredient = new ArrayList<>();
    Context mContext = null;
    int mRecipeId;

    public PopCakeHomescreenWidgetDataProvider(Context context, Intent intent, int recipeId) {
        mContext = context;
        mRecipeId = recipeId;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        PopCakeRoomDatabase mPopCakeRoomDB;
        IngredientDAO mIngredientDAO;

        // Init DB
        mPopCakeRoomDB = PopCakeRoomDatabase.getDatabase(mContext);
        mIngredientDAO = mPopCakeRoomDB.ingredientDAO();
        Log.d( Const.APP_TAG, "sampai di provider, saya pilih " + mRecipeId);
        mIngredient = mIngredientDAO.getIngredientsForRecipeForWidget(mRecipeId);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        if (mIngredient == null) {
            return 0;
        }
        else {
            return mIngredient.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient currentItem = mIngredient.get(position);
        RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.pop_cake_homescreen_widget_list_item);
        String ingredientText = currentItem.getQuantity() + " " + currentItem.getMeasure() + " " + currentItem.getIngredient();
        view.setTextViewText(R.id.tv_widget_recipe_ingredient_item, ingredientText);
        return view;
    }
}
