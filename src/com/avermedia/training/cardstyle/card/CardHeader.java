package com.avermedia.training.cardstyle.card;

import android.widget.PopupMenu.OnMenuItemClickListener;

/**
 * Data structure of Card header
 */
public class CardHeader {
	protected String mTitle;
	protected boolean mIsShowOtherButton = false;
	protected boolean mIsShowFlowButton = false;
	
	protected int mPopupMenuResId = -1;
	protected OnMenuItemClickListener mOnMenuItemClickListener;
	

	public void setTitle(String title) {
		mTitle = title;
	}
	
	public String getTitle() { return mTitle; }
	
	public void setShowButton(boolean showFlowButton, boolean showOtherButton) {
		mIsShowOtherButton = showFlowButton;
		mIsShowFlowButton = showOtherButton;
	}
	
	public boolean isShowOtherButton() { return mIsShowOtherButton; }
	public boolean isShowFlowButton() { return mIsShowFlowButton; }
	
	public void setPopupMenu(int popupMenuResId, OnMenuItemClickListener clickListener) {
		mPopupMenuResId = popupMenuResId;
		mOnMenuItemClickListener = clickListener;
	}
	
	public int getPopupMenu() { return mPopupMenuResId; }
	public OnMenuItemClickListener getPopupMenuItemClickListener() { return mOnMenuItemClickListener; }
}
