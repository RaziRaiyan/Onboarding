package com.example.onboarding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FragmentContact extends Fragment implements
        ContactDialog.OnCustomDialogClickListener, RecyclerItemClickListener.OnRecyclerClickListner{
    
    private static final String TAG = "FragmentContact";

    public static boolean isInActionMode = false;

    View v;
    private List<Contact> mContactList;
    private ContactDialog mContactDialog;

    static int count_item_selected = 0;
    static List<Integer> item_selected = new ArrayList<>();

    RecyclerViewAdapter recyclerViewAdapter;
    LinearLayoutManager mLinearLayoutManager;

    CheckBox checkBox;

    public FragmentContact() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.contact_fragment,container,false);

        RecyclerView myRecyclerView = v.findViewById(R.id.contacts_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), mContactList);
        myRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), myRecyclerView,this));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactList = new ArrayList<>();
        mContactList.add(new Contact("Aaron Jones","(111) 251236211",R.drawable.one));
        mContactList.add(new Contact("Raiyan Razi","7292897571",R.drawable.two));
        mContactList.add(new Contact("Raj Kumar","9122587896",R.drawable.three));
        mContactList.add(new Contact("Akhil Unnikrishnan","7289567410",R.drawable.four));
        mContactList.add(new Contact("Anas Razi","9963524178",R.drawable.five));
        mContactList.add(new Contact("Alpha Stalk","1123507896",R.drawable.six));
        mContactList.add(new Contact("Suleman bin Osama","9956854121",R.drawable.seven));
        mContactList.add(new Contact("George Washington","8856965414",R.drawable.eight));
        mContactList.add(new Contact("Waseem Jaafar","8685789632",R.drawable.nine));
        mContactList.add(new Contact("Armaan Jaafar","9632564785",R.drawable.ten));
        mContactList.add(new Contact("Virat Kohli","9696897454",R.drawable.eleven));
        mContactList.add(new Contact("Rohit Sharma","9885741445",R.drawable.twelve));
        mContactList.add(new Contact("Mahendra Singh Dhoni","8986857412",R.drawable.thirteen));
    }

    @Override
    public void onCustomDialogClickListener(int buttonId) {
        if(buttonId == ContactDialog.CALL_BUTTON_ID){
            Toast.makeText(getContext(),"Call button pressed",Toast.LENGTH_SHORT).show();
        }else if(buttonId == ContactDialog.MESSAGE_BUTTON_ID){
            Toast.makeText(getContext(),"Message button pressed",Toast.LENGTH_SHORT).show();
        }
        mContactDialog.dismiss();
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts");
        Log.d(TAG, "onItemClick: at position = "+position);
        if(isInActionMode){
            checkBox.setVisibility(View.VISIBLE);
            checkBox.toggle();
            if(!item_selected.contains(position)){
                item_selected.add(position);
                count_item_selected++;
            }else {
                item_selected.remove((Integer)position);
                count_item_selected--;
                if(count_item_selected==0){
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }
            ((InsideActivity)getActivity()).changeItemCount(count_item_selected);
            for(Integer i:item_selected){
                Log.d(TAG, "itemes = "+i);
            }
            recyclerViewAdapter.notifyDataSetChanged();
        }else {
            mContactDialog = ContactDialog.newInstance(this,mContactList.get(position));
            mContactDialog.show(getActivity().getSupportFragmentManager(),"contactsDialog");
        }
        Log.d(TAG, "onItemClick: view id = "+view.getId());
        Log.d(TAG, "onItemClick: ends");
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: starts");
        if(!isInActionMode){
            isInActionMode = true;
            item_selected.add(position);
            count_item_selected++;
            checkBox = view.findViewById(R.id.item_checkbox);
            checkBox.setVisibility(View.VISIBLE);
            checkBox.toggle();
            ((InsideActivity)getActivity()).changeMenuState(1,isInActionMode,count_item_selected);
            recyclerViewAdapter.notifyDataSetChanged();
            ((InsideActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Log.d(TAG, "onItemLongClick: ends");
    }


}
