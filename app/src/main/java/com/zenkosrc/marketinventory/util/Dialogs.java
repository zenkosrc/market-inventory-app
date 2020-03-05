package com.zenkosrc.marketinventory.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.zenkosrc.marketinventory.R;

public class Dialogs {

    public static void showInfoDialog(Context context, String title,
                                      String message,
                                      DialogInterface.OnClickListener listener) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton(android.R.string.ok, listener);
        dialog.show();
    }

    public static void showDialog(Context context, String message,
                                  DialogInterface.OnClickListener positivListener,
                                  DialogInterface.OnClickListener negativListener) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(message);
        dialog.setPositiveButton(R.string.dialog_product_delete_yes, positivListener);
        dialog.setNegativeButton(R.string.dialog_product_delete_no, negativListener);
        dialog.show();
    }


    public static void showNewPropertiesDialog(Context context,
                                               String title,
                                               final OnNewPropertiesListener listener) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final EditText input = new EditText(context);
        input.setSingleLine();
        FrameLayout container = new FrameLayout(context);
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(40, 0, 40, 0);
        input.setLayoutParams(params);
        container.addView(input);
        alert.setTitle(title);
        alert.setView(container);
        alert.setPositiveButton(R.string.dialog_product_properties_new,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        if (input.getText().length() != 0) listener.newPropertiesCreate(input.getText().toString());
                    }
                });
        alert.show();
    }
}
