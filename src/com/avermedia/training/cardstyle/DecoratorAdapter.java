package com.avermedia.training.cardstyle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

/**
 * The listview's adapter is decorated with this abstract adapter.
 */
public abstract class DecoratorAdapter extends BaseAdapter implements ListViewAccessible{
	protected final BaseAdapter mDecoratedBaseAdapter;
	protected AbsListView mListView;
	
	public DecoratorAdapter(BaseAdapter adapter) {
		mDecoratedBaseAdapter = adapter;
	}

	@Override
	public int getCount() {
		return mDecoratedBaseAdapter.getCount();
	}

	@Override
	public Object getItem(int position) {
		return mDecoratedBaseAdapter.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return mDecoratedBaseAdapter.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return mDecoratedBaseAdapter.getView(position, convertView, parent);
	}

	@Override
	public void setAbsListView(AbsListView listView) {
		mListView = listView;
		
		if(mDecoratedBaseAdapter instanceof ListViewAccessible) {
			((ListViewAccessible)mDecoratedBaseAdapter).setAbsListView(listView);
		}
		
	}

	@Override
	public AbsListView getAbsListView() {
		return mListView;
	}

	
	
}


