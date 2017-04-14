package com.devix.www.fragmentfotosvideosonClick;
/*
 *Created by Carlos Anguiano on 11/04/17.
 *For more info contact : c.joseanguiano@gmail.com
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devix.www.fragmentfotosvideos.R;

public class MediaListRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected ImageView thumbnail;
    protected TextView title;


    public MediaListRowHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        this.title = (TextView) view.findViewById(R.id.title);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();

    }


    public void dysplay(String text, boolean isSelected) {
//        textView.setText(text);
        thumbnail.setImageResource(R.drawable.logo_slogan);
        dysplay(isSelected);
    }

    public void dysplay(boolean isSelected) {
        thumbnail.setBackgroundResource(isSelected ? R.drawable.logo_slogan : R.drawable.logo_slogan);
    }
}
