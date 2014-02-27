package com.avermedia.training.cardstyle;

import android.view.View;

public interface SwipeOnTouchListener extends View.OnTouchListener {
    /**
     * @return true if the user is currently swiping a list-item horizontally.
     */
    public boolean isSwiping();
}
