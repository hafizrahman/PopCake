package com.example.android.popcake;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class PopCakeHomescreenWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new PopCakeHomescreenWidgetDataProvider(this, intent);
    }
}
