package com.susankya.swadesibidhesi.ItemDecorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ichha on 3/12/2018.
 */

public class HomeGridSpaceItemDecoration extends RecyclerView.ItemDecoration {

//    private final int verticalSpaceHeight;

    public HomeGridSpaceItemDecoration() {
//        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int spanCount = 2;
        int spacing = 14;//spacing between views in grid

        if (position >= 0) {
            int column = position % spanCount; // item column

            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = 0;
        }
    }
}
