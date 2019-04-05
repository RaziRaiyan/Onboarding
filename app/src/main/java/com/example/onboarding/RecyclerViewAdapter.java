package com.example.onboarding;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static com.example.onboarding.FragmentContact.item_selected;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    Context mContext;
    List<Contact> mContacts;

    static SparseBooleanArray itemStateArray= new SparseBooleanArray();

    MyViewHolder mMyViewHolder;

    public RecyclerViewAdapter(Context context, List<Contact> contacts) {
        mContext = context;
        mContacts = contacts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_contact,viewGroup,false);
        mMyViewHolder = new MyViewHolder(v);
        return mMyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_contact_name.setText(mContacts.get(i).getName());
        myViewHolder.tv_contact_number.setText(mContacts.get(i).getPhone());
        Glide.with(mContext)
                .load(mContacts.get(i).getPhoto())
                .apply(RequestOptions.circleCropTransform())
                .into(myViewHolder.contact_image);
//        if(isInActionMode){
            Log.d(TAG, "onBindViewHolder: starting bind");
            myViewHolder.bind(i);
            Log.d(TAG, "onBindViewHolder: returned from bind");
//        }
    }

    @Override
    public int getItemCount() {
        return (mContacts!=null||mContacts.size()!=0)?mContacts.size():0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_contact_name;
        private TextView tv_contact_number;
        private ImageView contact_image;
        private ConstraintLayout contact_item;
        private CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contact_item = itemView.findViewById(R.id.contact_item);
            tv_contact_name = itemView.findViewById(R.id.item_contact_name);
            tv_contact_number = itemView.findViewById(R.id.item_contact_number);
            contact_image = itemView.findViewById(R.id.item_contact_photo);
            mCheckBox = itemView.findViewById(R.id.item_checkbox);
        }

        void bind(int position) {
            // use the sparse boolean array to check
            Log.d(TAG, "bind: called");
            if(!item_selected.contains(position)){
                mCheckBox.setChecked(false);
                mCheckBox.setVisibility(View.GONE);
            }else {
                mCheckBox.setVisibility(View.VISIBLE);
                mCheckBox.setChecked(true);
            }
//
//            if (!itemStateArray.get(position, false)) {
//                mCheckBox.setChecked(false);}
//            else {
//                mCheckBox.setChecked(true);
//            }
        }
    }


}
