package com.example.onboarding;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    // We are going to use this from main activity, so we need a context,
    // and we will pass mainActivity's context
    Context mContext;
    LayoutInflater mLayoutInflater;

    public SliderAdapter(Context context) {
        mContext = context;
    }

    //Arrays
    public int[] slide_images = {
            R.drawable.image_food,
            R.drawable.image_owl,
            R.drawable.image_code
    };

    public String[] slide_headings = {"EAT","SLEEP","CODE"};

    public String[] slide_description = {
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
            "t is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English.",
            "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (ConstraintLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView imageView = (ImageView) view.findViewById(R.id.image_placeholder);
        TextView tv_header = (TextView) view.findViewById(R.id.tv_header);
        TextView tv_description = (TextView) view.findViewById(R.id.tv_description);

        imageView.setImageResource(slide_images[position]);
        tv_header.setText(slide_headings[position]);
        tv_description.setText(slide_description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
