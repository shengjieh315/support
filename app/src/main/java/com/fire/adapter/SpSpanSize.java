package com.fire.adapter;

public interface SpSpanSize {

    boolean isHeaderPosition(int position);

    boolean isFooterPosition(int position);

    boolean isGroupPosition(int position);

    int getSpanIndex(int position, int spanCount);

    int getSpanSize(int position);
}
