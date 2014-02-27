package com.avermedia.training.cardstyle;

import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class SwipeDismissAdapter extends DecoratorAdapter {
	protected OnDismissCallback mOnDismissCallback;
    protected SwipeDismissListViewTouchListener mSwipeDismissListViewTouchListener;
    protected SwipeOnScrollListener mSwipeOnScrollListener;
    
    /**
     * Create a new SwipeDismissAdapter.
     *
     * @param baseAdapter       the {@link android.widget.BaseAdapter to use}
     * @param onDismissCallback the {@link OnDismissCallback} to be notified of dismissed items.
     */
    public SwipeDismissAdapter(final BaseAdapter baseAdapter, final OnDismissCallback onDismissCallback) {
        this(baseAdapter, onDismissCallback, new SwipeOnScrollListener());
    }

    /**
     * Create a new SwipeDismissAdapter.
     *
     * @param baseAdapter           the {@link android.widget.BaseAdapter to use}
     * @param onDismissCallback     the {@link OnDismissCallback} to be notified of dismissed items.
     * @param swipeOnScrollListener the {@link SwipeOnScrollListener} to use.
     */
    public SwipeDismissAdapter(final BaseAdapter baseAdapter, 
    		final OnDismissCallback onDismissCallback, final SwipeOnScrollListener swipeOnScrollListener) {
        super(baseAdapter);
        mOnDismissCallback = onDismissCallback;
        mSwipeOnScrollListener = swipeOnScrollListener;
    }
    
    protected SwipeDismissListViewTouchListener createListViewTouchListener(final AbsListView listView) {
        return new SwipeDismissListViewTouchListener(listView, mOnDismissCallback, mSwipeOnScrollListener);
    }

    @Override
    public void setAbsListView(final AbsListView listView) {
        super.setAbsListView(listView);
        if (mDecoratedBaseAdapter instanceof ArrayAdapter<?>) {
            ((ArrayAdapter<?>) mDecoratedBaseAdapter).notifyDataSetChanged();
        }
        mSwipeDismissListViewTouchListener = createListViewTouchListener(listView);
        listView.setOnTouchListener(mSwipeDismissListViewTouchListener);
    }

}
