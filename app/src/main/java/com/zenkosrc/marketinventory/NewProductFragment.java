package com.zenkosrc.marketinventory;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;
import com.zenkosrc.marketinventory.database.Product;
import com.zenkosrc.marketinventory.database.ProductProperties;
import com.zenkosrc.marketinventory.managers.DataBaseManager;
import com.zenkosrc.marketinventory.util.TextUtils;

import java.util.List;

import static com.zenkosrc.marketinventory.EditProductPropertiesActivity.EXTRA_PROPERTIES_LABEL;

public class NewProductFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = NewProductFragment.class.getSimpleName();

    private View            view;
    private ImageView       editGroupImageView;
    private ImageView       editQuantityImageView;
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
        barCodeEditText             = (EditText)     view.findViewById(R.id.barCodeEditText);
        productNameEditText         = (EditText)     view.findViewById(R.id.productNameEditText);
        productGroupEditText        = (EditText)     view.findViewById(R.id.productGroupEditText);
        productQuantityEditText     = (EditText)     view.findViewById(R.id.productQuantityEditText);
        productDescriptionEditText  = (EditText)     view.findViewById(R.id.productDescriptionEditText);
        editGroupImageView          = (ImageView)    view.findViewById(R.id.editGroupImageView);
        editQuantityImageView       = (ImageView)    view.findViewById(R.id.editQuantityImageView);
        closeImageView              = (ImageView)    view.findViewById(R.id.closeImageView);

        editGroupImageView.setOnClickListener(this);
        editQuantityImageView.setOnClickListener(this);
        closeImageView.setOnClickListener(this);
        addButtonLinearLayout.setOnClickListener(this);
        barCodeEditText.setOnTouchListener(new EditTextIconClickListener());
        productGroupEditText.setOnTouchListener(new EditTextIconClickListener());
        productQuantityEditText.setOnTouchListener(new EditTextIconClickListener());
    }

    private void closeFragmentAnimation() {
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down).remove(this).commit();

        Animation animFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        rootLinearLayout.startAnimation(animFadeOut);

        ((MainActivity) getActivity()).refreshProductCount();
    }

    private void startFragmentAnimation() {

        Animation animFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        rootLinearLayout.startAnimation(animFadeIn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.closeImageView:

                closeFragmentAnimation();
                break;

            case R.id.addButtonLinearLayout:

                if (!isFieldsEmpty()){
                    addNewProduct(getCurentProduct());
                    closeFragmentAnimation();
                }
                break;

            case R.id.editGroupImageView:

                startEditProperties(ProductProperties.GROUP);
                break;

            case R.id.editQuantityImageView:

                startEditProperties(ProductProperties.QUANTITY);
                break;
        }
    }

    private void addNewProduct(Product product){
        DataBaseManager.getInstance(getContext()).saveProductInDataBase(product);
    }

    private Product getCurentProduct(){

        Product curentProduct = new Product();
        curentProduct.setBarcode(barCodeEditText.getText().toString());
        curentProduct.setName(productNameEditText.getText().toString());
        curentProduct.setGroup(productGroupEditText.getText().toString());
        curentProduct.setQuantity(productQuantityEditText.getText().toString());
        curentProduct.setDescription(productDescriptionEditText.getText().toString());
        curentProduct.setTime(System.currentTimeMillis());
        return curentProduct;
    }

    private boolean isFieldsEmpty(){

        if(TextUtils.isEmpty(barCodeEditText)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.toast_is_empty_product_barcode), Toast.LENGTH_SHORT).show();
            return true;
        }

        if(TextUtils.isEmpty(productNameEditText)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.toast_is_empty_product_name), Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private void startScan() {
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(getActivity())
                .withEnableAutoFocus(true)
                .withBleepEnabled(false)
                .withBackfacingCamera()
                .withText("Scanning...")
                .withCenterTracker ()
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {

                        Log.i(TAG, "Scanned product " + barcode.rawValue);

                        barCodeEditText.setText(barcode.rawValue);
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }

    private class EditTextIconClickListener implements View.OnTouchListener {

        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        @Override
        public boolean onTouch(View v, MotionEvent event) {


            if(event.getAction() == MotionEvent.ACTION_UP) {

                switch (v.getId()){
                    case R.id.barCodeEditText:

                        Log.i(TAG, "barCodeEditText");
                        if(event.getRawX() >= (barCodeEditText.getRight() - barCodeEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                            startScan();
                            return true;
                        }
                        break;

                    case R.id.productGroupEditText:

                        Log.i(TAG, "productGroupEditText");
                        if(event.getRawX() >= (productGroupEditText.getRight() - productGroupEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                            showDialogPropertiseList(DataBaseManager.getInstance(getContext()).getProductPropertiesGroupList(), getString(R.string.title_group_name));
                            return true;
                        }
                        break;

                    case R.id.productQuantityEditText:

                        Log.i(TAG, "productGroupEditText");
                        if(event.getRawX() >= (productQuantityEditText.getRight() - productQuantityEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                            showDialogPropertiseList(DataBaseManager.getInstance(getContext()).getProductPropertiesQuantityList(), getString(R.string.title_quantity_name));
                            return true;
                        }
                        break;
                }
            }
            return false;
        }
    }

    private void showDialogPropertiseList (final List<ProductProperties> productPropertiesList, String title){

        String[] namesArray = new String[productPropertiesList.size()];

        for (int i = 0; i < productPropertiesList.size(); i++){
            namesArray[i] = productPropertiesList.get(i).getPropertiesName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setItems(namesArray, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                setProperties(productPropertiesList.get(item));

                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void setProperties(ProductProperties selectedProductProperties){

        switch (selectedProductProperties.getLabel()){
            case ProductProperties.GROUP:
                productGroupEditText.setText(selectedProductProperties.getPropertiesName());
                break;

            case ProductProperties.QUANTITY:
                productQuantityEditText.setText(selectedProductProperties.getPropertiesName());
                break;
        }
    }

    private void startEditProperties(int propertiesLabel){
        Intent intent = new Intent(getContext(), EditProductPropertiesActivity.class);

        if (propertiesLabel == ProductProperties.GROUP){
            intent.putExtra(EXTRA_PROPERTIES_LABEL, ProductProperties.GROUP);
        }else {
            intent.putExtra(EXTRA_PROPERTIES_LABEL, ProductProperties.QUANTITY);
        }
        startActivity(intent);
    }
}
