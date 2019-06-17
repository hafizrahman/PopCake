package com.example.android.popcake;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetPopCakeService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetPopCakeDataProvider(
                this,
                intent,
                intent.getIntExtra("WIDGET_SELECTED_RECIPE_ID", 1));
    }
}
