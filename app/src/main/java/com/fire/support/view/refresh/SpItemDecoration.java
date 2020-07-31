package com.fire.support.view.refresh;


import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class SpItemDecoration extends RecyclerView.ItemDecoration {

    private int height;


    private boolean isHeader = true;


    public SpItemDecoration setHeight(int height) {
        this.height = height;

        return this;
    }


    public SpItemDecoration setIsHeader(boolean isHeader) {

        this.isHeader = isHeader;
        return this;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);


        try {
            setOutRect(outRect, view, parent);
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    private void setOutRect(Rect outRect, View view, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            int position = parent.getChildAdapterPosition(view);

            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup lookup = gridLayoutManager.getSpanSizeLookup();

            int spanCount = gridLayoutManager.getSpanCount();
            int spanGroup = lookup.getSpanGroupIndex(position, spanCount);

            int count = gridLayoutManager.getItemCount();
            int lastGroup = lookup.getSpanGroupIndex(count - 1, spanCount);

            if (isHeader && spanGroup == 0) {
                outRect.set(0, height, 0, 0);
            }

            if (!isHeader && spanGroup == lastGroup) {

                outRect.set(0, 0, 0, height);

            }

        } else {

            int rowSpan = 1;

            if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager gridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                rowSpan = gridLayoutManager.getSpanCount();
            }


            boolean relatedPosition = false;


            if (isHeader) {

                relatedPosition = parent.getChildLayoutPosition(view) < rowSpan;

            } else {

                int lastSum = 1;


                int itemCount = layoutManager.getItemCount();

                if (itemCount > 0 && rowSpan > 1) {

                    lastSum = itemCount % rowSpan;

                    if (lastSum == 0) {
                        lastSum = rowSpan;
                    }
                }

                int count = itemCount - lastSum;


                int lastPosition = parent.getChildLayoutPosition(view);


                relatedPosition = lastPosition >= count;

            }


            if (relatedPosition) {
                if (isHeader) {

                    outRect.set(0, height, 0, 0);

                } else {

                    outRect.set(0, 0, 0, height);

                }
            }


        }
    }


}