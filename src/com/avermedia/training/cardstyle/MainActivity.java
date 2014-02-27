package com.avermedia.training.cardstyle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.avermedia.training.cardstyle.card.CardHeadView;
import com.avermedia.training.cardstyle.card.CardHeader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final int ITEM_COUNT = 20;
	
	private CardsAdapter<Integer> mCardsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView listView = (ListView) findViewById(R.id.cards_listview);
		mCardsAdapter = new CardsAdapter<Integer>(this);
		SwipeDismissAdapter swipeDismissAdapter = new SwipeDismissAdapter(mCardsAdapter, new OnDismissCallback() {
			
			@Override
			public void onDismiss(final AbsListView listView, final int[] reverseSortedPositions) {
				for (int position : reverseSortedPositions) {
					mCardsAdapter.remove(position);
				}
			}
		});
		
		swipeDismissAdapter.setAbsListView(listView);
		listView.setAdapter(swipeDismissAdapter);
		
		mCardsAdapter.addAll(getItems());
	}
	
	private ArrayList<Integer> getItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for(int i = 0; i < ITEM_COUNT; i++) {
			items.add(i);
		}
		
		return items;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private static class CardsAdapter<T> extends BaseAdapter {
		private final Context mContext;
		private final List<T> mItems;
		private final LruCache<Integer, Bitmap> mMemoryCache;

		public CardsAdapter(final Context context) {
			mContext = context;
			mItems = new ArrayList<T>();
			
			final int cacheSize = (int) (Runtime.getRuntime().maxMemory() / 1024);
			mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(final Integer key, final Bitmap bitmap) {
					// The cache size will be measured in kilobytes rather than
					// number of items.
					return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
				}
			};
		}
		
		public boolean addAll(final Collection<? extends T> collection) {
			boolean result = mItems.addAll(collection);
	        notifyDataSetChanged();
	        return result;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
	    public T remove(final int location) {
	        T result = mItems.remove(location);
	        notifyDataSetChanged();
	        return result;
	    }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			View view = convertView;
			if (view == null) {
				view = LayoutInflater.from(mContext).inflate(R.layout.activity_googlecards_card, parent, false);

				viewHolder = new ViewHolder();
				viewHolder.cardHeadView = (CardHeadView) view.findViewById(R.id.card_header_layout);
				viewHolder.title = (TextView) view.findViewById(R.id.activity_googlecards_card_title);
				viewHolder.subtitle = (TextView) view.findViewById(R.id.activity_googlecards_card_subtitle);
				viewHolder.imageView = (ImageView) view.findViewById(R.id.activity_googlecards_card_imageview);
				
				view.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			int index = 0;
			if(getItem(position) instanceof Integer) {
				index = (Integer) getItem(position);
			}
			else {
				index = (int) getItemId(position);
			}
			setImageView(viewHolder, position);
			
			if(null == viewHolder.cardHeadView.getCardHeader()) {
				CardHeader ch = new CardHeader();
				ch.setTitle("This is card " + (index + 1));
				ch.setShowButton(true, false);
				ch.setPopupMenu(R.menu.popupmain, new OnMenuItemClickListener() {
					
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						// TODO Auto-generated method stub
						return false;
					}
				});
				viewHolder.cardHeadView.setCardHeader(ch);
			}

			return view;
		}
		
		private void setImageView(final ViewHolder viewHolder, final int position) {
			int imageResId;
			int index = 0;
			if(getItem(position) instanceof Integer) {
				index = (Integer) getItem(position);
			}
			else {
				index = (int) getItemId(position);
			}
			
			
			switch (index % 5) {
			case 0:
				imageResId = R.drawable.google_icon;
				viewHolder.title.setText(R.string.google_search_title);
				viewHolder.subtitle.setText(R.string.google_search_subtitle);
				break;
			case 1:
				imageResId = R.drawable.google_chrome_icon;
				viewHolder.title.setText(R.string.google_chrome_title);
				viewHolder.subtitle.setText(R.string.google_chrome_subtitle);
				break;
			case 2:
				imageResId = R.drawable.google_plus_icon;
				viewHolder.title.setText(R.string.google_plus_title);
				viewHolder.subtitle.setText(R.string.google_plus_subtitle);
				break;
			case 3:
				imageResId = R.drawable.google_drive_icon;
				viewHolder.title.setText(R.string.google_drive_title);
				viewHolder.subtitle.setText(R.string.google_drive_subtitle);
				break;
			default:
				imageResId = R.drawable.google_play_music_icon;
				viewHolder.title.setText(R.string.google_music_title);
				viewHolder.subtitle.setText(R.string.google_music_subtitle);
			}

			Bitmap bitmap = getBitmapFromMemCache(imageResId);
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(mContext.getResources(), imageResId);
				addBitmapToMemoryCache(imageResId, bitmap);
			}
			viewHolder.imageView.setImageBitmap(bitmap);
		}
		
		private void addBitmapToMemoryCache(final int key, final Bitmap bitmap) {
			if (getBitmapFromMemCache(key) == null) {
				mMemoryCache.put(key, bitmap);
			}
		}

		private Bitmap getBitmapFromMemCache(final int key) {
			return mMemoryCache.get(key);
		}

		private static class ViewHolder {
			CardHeadView cardHeadView;
			ImageView imageView;
			TextView title;
			TextView subtitle;
		}
	}

}
