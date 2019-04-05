package com.example.onboarding;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ContactDialog extends AppCompatDialogFragment {

    ImageView contactImage;
    TextView contactName,contactNumber;
    Button callButton,messageButton;

    //Contact info
    static Contact contactToBePopulated;

    public static int CALL_BUTTON_ID = 0;
    public static  int MESSAGE_BUTTON_ID =1;


    static OnCustomDialogClickListener mCallback;

    interface OnCustomDialogClickListener{
        void onCustomDialogClickListener(int buttonId);
    }

    public static ContactDialog newInstance(OnCustomDialogClickListener callback,Contact contact){
        contactToBePopulated = contact;
        mCallback = callback;
        return new ContactDialog();
    }

    public ContactDialog(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        View view = inflater.inflate(R.layout.contacts_selection_dialog,container,false);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view  = getActivity().getLayoutInflater().inflate(R.layout.contacts_selection_dialog,null);
        builder.setView(view);
        contactImage = view.findViewById(R.id.dialog_image);
        contactName = view.findViewById(R.id.dialog_contact_name);
        contactNumber = view.findViewById(R.id.dialog_phone_number);
        callButton = view.findViewById(R.id.dialog_call_button);
        messageButton = view.findViewById(R.id.dialog_message_button);
        contactName.setText(contactToBePopulated.getName());
        contactNumber.setText(contactToBePopulated.getPhone());
        Glide.with(getContext())
                .load(contactToBePopulated.getPhoto())
                .apply(RequestOptions.circleCropTransform())
                .into(contactImage);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onCustomDialogClickListener(MESSAGE_BUTTON_ID);
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onCustomDialogClickListener(CALL_BUTTON_ID);
            }
        });
        return  builder.create();
    }
}
