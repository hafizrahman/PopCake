package com.example.android.popcake;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.util.Log;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WidgetPopCakeConfigureActivity WidgetPopCakeConfigureActivity}
 */
public class WidgetPopCake extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        int selectedRecipeId = WidgetPopCakeConfigureActivity.loadSelectedRecipeIdPref(context, appWidgetId);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pop_cake_homescreen_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent = new Intent(context, WidgetPopCakeService.class);

        // Intent data sending is a bit different for RemoteViewService
        // Refer to https://stackoverflow.com/a/46414673 for this bit of code
        intent.setData(Uri.fromParts("content", String.valueOf(selectedRecipeId), null));


        views.setTextViewText(R.id.tv_widget_recipe_name, "Recipe id is " + selectedRecipeId);
        views.setRemoteAdapter(R.id.lv_widget_recipe_ingredients, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Log.d(Const.APP_TAG, "Hi, currently updating " + appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            WidgetPopCakeConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

