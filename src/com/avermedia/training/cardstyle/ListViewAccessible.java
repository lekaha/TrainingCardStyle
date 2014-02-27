package com.avermedia.training.cardstyle;

import android.widget.AbsListView;

/**
 * An interface to specify whether certain list view can or cannot be accessed.
 *
 */
public interface ListViewAccessible {
	public void setAbsListView(final AbsListView listView);
	public AbsListView getAbsListView();
}