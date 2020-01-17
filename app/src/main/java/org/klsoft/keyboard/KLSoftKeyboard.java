package org.klsoft.keyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.inputmethod.EditorInfo;

import java.util.StringTokenizer;

public class KLSoftKeyboard extends Keyboard {

    KLSoftKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        return new KLSoftKey(res, parent, x, y, parser);
    }

    static class KLSoftKey extends Keyboard.Key {

        KLSoftKey(Resources res, Keyboard.Row parent, int x, int y, XmlResourceParser parser) {

            super(parent);

            /*this.x = x;
            this.y = y;

            TypedArray a = res.obtainAttributes(Xml.asAttributeSet(parser),
                    R.styleable.Keyboard);

            width = getDimensionOrFraction(a,
                    R.styleable.Keyboard_keyWidth, parent.defaultWidth, parent.defaultWidth);
            height = getDimensionOrFraction(a,
                    R.styleable.Keyboard_keyHeight, parent.defaultHeight * 4, parent.defaultHeight);
            gap = getDimensionOrFraction(a,
                    R.styleable.Keyboard_horizontalGap, 0, parent.defaultHorizontalGap);
            a.recycle();
            a = res.obtainAttributes(Xml.asAttributeSet(parser),
                    R.styleable.Keyboard_Key);
            this.x += gap;
            TypedValue codesValue = new TypedValue();
            a.getValue(R.styleable.Keyboard_Key_codes,
                    codesValue);
            if (codesValue.type == TypedValue.TYPE_INT_DEC
                    || codesValue.type == TypedValue.TYPE_INT_HEX) {
                codes = new int[] { codesValue.data };
            } else if (codesValue.type == TypedValue.TYPE_STRING) {
                codes = parseCSV(codesValue.string.toString());
            }

            iconPreview = a.getDrawable(R.styleable.Keyboard_Key_iconPreview);
            if (iconPreview != null) {
                iconPreview.setBounds(0, 0, iconPreview.getIntrinsicWidth(),
                        iconPreview.getIntrinsicHeight());
            }
            popupCharacters = a.getText(
                    R.styleable.Keyboard_Key_popupCharacters);
            popupResId = a.getResourceId(
                    R.styleable.Keyboard_Key_popupKeyboard, 0);
            repeatable = a.getBoolean(
                    R.styleable.Keyboard_Key_isRepeatable, false);
            modifier = a.getBoolean(
                    R.styleable.Keyboard_Key_isModifier, false);
            sticky = a.getBoolean(
                    R.styleable.Keyboard_Key_isSticky, false);
            edgeFlags = a.getInt(R.styleable.Keyboard_Key_keyEdgeFlags, 0);
            edgeFlags |= parent.rowEdgeFlags;

            icon = a.getDrawable(
                    R.styleable.Keyboard_Key_keyIcon);
            if (icon != null) {
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
            }
            label = a.getText(R.styleable.Keyboard_Key_keyLabel);
            text = a.getText(R.styleable.Keyboard_Key_keyOutputText);

            if (codes == null && !TextUtils.isEmpty(label)) {
                codes = new int[] { label.charAt(0) };
            }
            a.recycle();*/

        }

        @Override
        public boolean isInside(int x, int y) {
            return super.isInside(x, codes[0] == KEYCODE_CANCEL ? y - 10 : y);
        }

        static int getDimensionOrFraction(TypedArray a, int index, int base, int defValue) {
            TypedValue value = a.peekValue(index);
            if (value == null) return defValue;
            if (value.type == TypedValue.TYPE_DIMENSION) {
                return a.getDimensionPixelOffset(index, defValue);
            } else if (value.type == TypedValue.TYPE_FRACTION) {
                // Round it to avoid values like 47.9999 from getting truncated
                return Math.round(a.getFraction(index, base, base, defValue));
            }
            return defValue;
        }

        int[] parseCSV(String value) {
            int count = 0;
            int lastIndex = 0;
            if (value.length() > 0) {
                count++;
                while ((lastIndex = value.indexOf(",", lastIndex + 1)) > 0) {
                    count++;
                }
            }
            int[] values = new int[count];
            count = 0;
            StringTokenizer st = new StringTokenizer(value, ",");
            while (st.hasMoreTokens()) {
                try {
                    values[count++] = Integer.parseInt(st.nextToken());
                } catch (NumberFormatException nfe) {
                    Log.e(this.getClass().getName(), "Error parsing keycodes " + value);
                }
            }
            return values;
        }
    }
}
