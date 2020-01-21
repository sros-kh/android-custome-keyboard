package org.klsoft.keyboard;

import android.content.Context;
import android.util.Xml;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    }

    @Test
    public void writeKeyboardRandom() {

        try {

            XmlSerializer x = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            x.setOutput(writer);

            x.startDocument("UTF-8", true);
            x.startTag(null, "Keyboard");
            x.attribute(null, "xmlns:android", "http://schemas.android.com/apk/res/android");
            x.attribute(null, "android:keyWidth", "10%p");
            x.attribute(null, "android:horizontalGap", "0px");
            x.attribute(null, "android:verticalGap", "0px");
            x.attribute(null, "android:keyHeight", "60dp");

            for (int i = 0; i < 3; i++) {

                x.startTag(null, "Row");

                for (int j = 0; j < 10; j++) {

                    if (i == 1 && j == 9) break;

                    char ch = '\u0000';
                    if (!(i == 2 && j == 9 || i == 2 && j == 0)) ch = getRandomKey();

                    x.startTag(null, "Key");


                    if (i == 1 && j == 0) x.attribute(null, "android:horizontalGap", "5%");

                    if (i == 2 && j == 0) {
                        x.attribute(null, "android:keyWidth", "14.199996%");
                        x.attribute(null, "android:keyIcon", "@drawable/icon_shift_key");
                        x.attribute(null, "android:codes", -1 + "");
                    } else if (i == 2 && j == 9) {
                        x.attribute(null, "android:keyWidth", "17%");
                        x.attribute(null, "android:keyIcon", "@drawable/icon_delete_key");
                        x.attribute(null, "android:codes", -5 + "");
                    } else {
                        x.attribute(null, "android:codes", (int) ch + "");
                        x.attribute(null, "android:keyLabel", ch + "");
                    }

                    if (j == 0) x.attribute(null, "android:keyEdgeFlags", "left");
                    if (j == 9) x.attribute(null, "android:keyEdgeFlags", "right");
                    x.endTag(null, "Key");
                }
                x.endTag(null, "Row");

            }

            x.startTag(null, "Row");
            x.attribute(null, "android:rowEdgeFlags", "bottom");

            x.startTag(null, "Key");
            x.attribute(null, "android:codes", "-52");
            x.attribute(null, "android:keyLabel", "...");
            x.attribute(null, "android:keyWidth", "15%p");
            x.attribute(null, "android:keyEdgeFlags", "left");
            x.endTag(null, "Key");

            x.startTag(null, "Key");
            x.attribute(null, "android:codes", "-51");
            x.attribute(null, "android:keyIcon", "@drawable/icon_language_key");
            x.attribute(null, "android:keyWidth", "10%p");
            x.endTag(null, "Key");

            x.startTag(null, "Key");
            x.attribute(null, "android:codes", "44");
            x.attribute(null, "android:keyLabel", ",");
            x.attribute(null, "android:keyWidth", "10%p");
            x.endTag(null, "Key");

            x.startTag(null, "Key");
            x.attribute(null, "android:codes", "32");
            x.attribute(null, "android:keyLabel", "___");
            x.attribute(null, "android:keyWidth", "30%p");
            x.endTag(null, "Key");

            x.startTag(null, "Key");
            x.attribute(null, "android:codes", "64");
            x.attribute(null, "android:keyLabel", "\\@");
            x.attribute(null, "android:keyWidth", "10%p");
            x.endTag(null, "Key");

            x.startTag(null, "Key");
            x.attribute(null, "android:codes", "47");
            x.attribute(null, "android:keyLabel", "/");
            x.attribute(null, "android:keyWidth", "10%p");
            x.endTag(null, "Key");

            x.startTag(null, "Key");
            x.attribute(null, "android:codes", "-4");
            x.attribute(null, "android:keyWidth", "15%p");
            x.attribute(null, "android:keyIcon", "@drawable/icon_enter_key");
            x.attribute(null, "android:keyEdgeFlags", "right");
            x.endTag(null, "Key");

            x.endTag(null, "Row");

            x.endDocument();
            x.flush();
            System.out.println(writer.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(4, 2 + 2);
    }

    private static char[] keySequence = "abcdefghijklmnopqrstuvwxyz.".toCharArray();
//    private static char[] keySequence = "ㄱㄴㄷㄹㅁㅂㅅㅈㅊㅋㅌㅍㅎㅇㅏㅑㅓㅕㅗㅛㅜㅠㅡㅣㅐㅔ".toCharArray();

    public static char getRandomKey() {
        int index = new Random().nextInt(keySequence.length);
        char value = keySequence[index];
        keySequence = removeTheElement(keySequence, index);
        return value;
    }

    public static char[] removeTheElement(char[] arr, int index) {

        if (arr == null || index < 0 || index >= arr.length)
            return arr;

        char[] anotherArray = new char[arr.length - 1];

        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index)
                continue;
            anotherArray[k++] = arr[i];
        }
        return anotherArray;
    }
}
