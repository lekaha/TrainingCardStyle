package utils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AdapterViewUtil {

    /**
     * Get the position within the adapter's dataset for the view, where view is an adapter item or a descendant of an adapter item.
     * Unlike {@link AdapterView#getPositionForView(android.view.View)}, returned position will reflect the position of the item given view is representing,
     * by subtracting the header views count.
     * @param adapterView the AdapterView containing the view.
     * @param view an adapter item or a descendant of an adapter item. This must be visible in given AdapterView at the time of the call.
     * @return the position of the item in the AdapterView represented by given view, or {@link AdapterView#INVALID_POSITION} if the view does not
     * correspond to a list item (or it is not visible).
     */
    public static int getPositionForView(final AdapterView<?> adapterView, final View view) {
        int position = adapterView.getPositionForView(view);

        if (adapterView instanceof ListView) {
            position -= ((ListView) adapterView).getHeaderViewsCount();
        }

        return position;
    }
}
