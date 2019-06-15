package com.example.android.popcake;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.example.android.popcake.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class PopCakeHomescreenWidgetDataProvider extends RemoteViewsFactory {
    List<Ingredient> mIngredient = new ArrayList<>();

    Context mContext = null;

    public PopCakeHomescreenWidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
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
}
