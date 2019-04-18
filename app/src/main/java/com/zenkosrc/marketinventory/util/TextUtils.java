package com.zenkosrc.marketinventory.util;

import android.support.annotation.Nullable;
import android.widget.EditText;

public class TextUtils {

    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(EditText value) {
        return value == null || value.getText() == null || isEmpty(value.getText().toString());
    }

}
