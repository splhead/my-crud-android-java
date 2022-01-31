package com.splhead.mycrud.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MaskEditUtils {
    public static final String FORMAT_CPF = "###.###.###-##";
    public static final String FORMAT_PHONE = "(##) #####-####";
    public static final String FORMAT_CEP = "#####-###";

    public static TextWatcher mask(final EditText editText, final String mask) {

        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int start, final int before, final int count) {
                final String str = MaskEditUtils.unmask(charSequence.toString());
                String masked = "";

                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }

                int i = 0;

                for (final char maskChar : mask.toCharArray()) {
                    if (maskChar != '#' && str.length() > old.length()) {
                        masked += maskChar;
                        continue;
                    }

                    try {
                        masked += str.charAt(i);
                    } catch (final Exception e) {
                        break;
                    }

                    i++;
                }
                isUpdating = true;
                editText.setText(masked);
                editText.setSelection(masked.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    private static String unmask(String str) {
        return str.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]","").replaceAll("[(]","")
                .replaceAll("[ ]", "").replaceAll("[:]","")
                .replaceAll("[)]","");
    }
}
