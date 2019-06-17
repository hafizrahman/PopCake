package com.example.android.popcake;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetPopCakeService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        // Refer to https://stackoverflow.com/a/46414673 for this bit of code
        int recipeId = Integer.valueOf(intent.getData().getSchemeSpecificPart());

        return new WidgetPopCakeDataProvider(
                this,
                intent,
                recipeId);

    }
}
