package com.zenkosrc.marketinventory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class NewProductFragment extends Fragment {

    private View view;

    private ImageView       closeImageView;
    private EditText        barCodeEditText;
    private EditText        productNameEditText;
    private EditText        productGroupEditText;
    private EditText        productQuantityEditText;
    private EditText        productDescriptionEditText;
    private LinearLayout    addButtonLinearLayout;
    private LinearLayout    rootLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_product, container, false);

        initResources();
        startFragmentAnimation();

        return view;
    }

    private void initResources() {

        rootLinearLayout            = (LinearLayout) view.findViewById(R.id.rootLinearLayout);
        addButtonLinearLayout       = (LinearLayout) view.findViewById(R.id.addButtonLinearLayout);
        barCodeEditText             = (EditText) view.findViewById(R.id.barCodeEditText);
        productNameEditText         = (EditText) view.findViewById(R.id.productNameEditText);
        productGroupEditText        = (EditText) view.findViewById(R.id.productGroupEditText);
        productQuantityEditText     = (EditText) view.findViewById(R.id.productQuantityEditText);
        productDescriptionEditText  = (EditText) view.findViewById(R.id.productDescriptionEditText);

        closeImageView = (ImageView) view.findViewById(R.id.closeImageView);
        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeFragmentAnimation();
            }
        });
    }

    private void closeFragmentAnimation() {
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down).remove(this).commit();

        Animation animFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        rootLinearLayout.startAnimation(animFadeOut);
    }

    private void startFragmentAnimation() {

        Animation animFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        rootLinearLayout.startAnimation(animFadeIn);
    }
}
