package com.avermedia.training.cardstyle;

import android.widget.AbsListView;

/**
 * An {@link android.widget.AbsListView.OnScrollListener} 
 * that is used in conjunction with {@link SwipeDismissAdapter}. 
 * Override this class to provide a custom implementation of the OnScrollListener. 
 * Do not forget to call super on the overridden methods!
 */
public class SwipeOnScrollListener implements AbsListView.OnScrollListener {

    private SwipeDismissListViewTouchListener mTouchListener;

    public void setTouchListener(final SwipeDismissListViewTouchListener touchListener) {
        mTouchListener = touchListener;
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        if (scrollState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            mTouchListener.disallowSwipe();
        }
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
    }
}
