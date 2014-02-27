package com.avermedia.training.cardstyle.card;

import com.avermedia.training.cardstyle.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class CardHeadView extends FrameLayout{
	
	protected int card_header_layout_resourceID = R.layout.card_head_view_layout;
	
	/**
     * Compound View for this Component
     */
    protected View mInternalOuterView;
    protected ImageView mImageButtonOverflow;
    protected ImageView mImageButtonOther;
    protected TextView mTitleTextView;
    protected FrameLayout mFrameButton;
    protected FrameLayout mFrameInner;
    
    protected CardHeader mCardHeader;
	
	public CardHeadView(Context context) {
		super(context);
		
		init(null, 0);
	}
	
	public CardHeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init(attrs, 0);
	}

	public CardHeadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		init(attrs, defStyle);
	}
	
	/**
     * Initializes component
     *
     * @param attrs
     * @param defStyle
     */
    protected void init(AttributeSet attrs, int defStyle) {
        //Init attrs
        initAttrs(attrs, defStyle);

        //Init View
        if (!isInEditMode())
            initView();
    }

    /**
     * Initial custom attributes.
     *
     * @param attrs
     * @param defStyle
     */
    protected void initAttrs(AttributeSet attrs, int defStyle) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);

        try {
            card_header_layout_resourceID = a.getResourceId(R.styleable.card_options_card_header_layout_resourceID, card_header_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    /**
     * Inits view
     */
    protected void initView() {

        //Inflate the root view (outerView)
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInternalOuterView = inflater.inflate(card_header_layout_resourceID, this, true);

        //Get buttons from layout
        mImageButtonOverflow = (ImageButton) findViewById(R.id.card_header_button_overflow);
        mImageButtonOverflow.setVisibility(GONE);
        mImageButtonOther = (ImageButton) findViewById(R.id.card_header_button_other);
        mImageButtonOther.setVisibility(GONE);
        mTitleTextView = (TextView) findViewById(R.id.card_header_inner_title);
        mTitleTextView.setVisibility(GONE);
        
        //Get frames
        mFrameInner = (FrameLayout) findViewById(R.id.card_header_inner_frame);
        mFrameButton = (FrameLayout) findViewById(R.id.card_header_button_frame);

    }
    
    
    public void setCardHeader(CardHeader cardHeader) {
    	mCardHeader = cardHeader;
    	
    	if(null != mCardHeader) {
    		if(mCardHeader.isShowOtherButton()) {
    			mImageButtonOverflow.setVisibility(GONE);
    			mImageButtonOther.setVisibility(VISIBLE);
    		}
    		
    		if(mCardHeader.isShowFlowButton()) {
    			mImageButtonOverflow.setVisibility(VISIBLE);
    			mImageButtonOther.setVisibility(GONE);
    		}
    		
    		if(null != mCardHeader.getTitle()) {
    			mTitleTextView.setVisibility(VISIBLE);
    			mTitleTextView.setText(cardHeader.getTitle());
    		} 
    		
    		setupButton();
    	}
    }
    
    public CardHeader getCardHeader() { return mCardHeader; }
    
    public void setupButton() {
    	if(-1 != mCardHeader.getPopupMenu() && null != mImageButtonOther) {
    		mImageButtonOther.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					PopupMenu popup = new PopupMenu(getContext(), mImageButtonOther);
					MenuInflater inflater = popup.getMenuInflater();
					inflater.inflate(mCardHeader.getPopupMenu(), popup.getMenu());
					popup.setOnMenuItemClickListener(mCardHeader.getPopupMenuItemClickListener());
					popup.show();
				}
			});
    	}
    }

}
