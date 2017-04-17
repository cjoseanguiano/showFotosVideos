package com.devix.www.fragmentfotosvideosonClick;
/*
 *Created by Carlos Anguiano on 14/04/17.
 *For more info contact : c.joseanguiano@gmail.com
 */

import android.view.View;
import android.widget.AdapterView;

public interface RecyclerViewClickListener {
    void recyclerViewListClicked(AdapterView<?> parent,View v, int position);
}