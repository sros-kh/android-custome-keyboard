package org.klsoft.keyboard;

import android.util.Xml;

import androidx.core.content.ContextCompat;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Map;

public class Utils {

    public static String writeXml(Map<String, String> keys) {

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "Keyboard");
            serializer.attribute("", "xmlns:android", "http://schemas.android.com/apk/res/android");
            serializer.attribute("", "android:keyWidth", "10%p");
            serializer.attribute("", "android:horizontalGap", "0%p");
            serializer.attribute("", "android:verticalGap", "0%p");
            serializer.attribute("", "android:keyHeight", "60%p");

            int flag = 0;
            for (Map.Entry<String, String> key : keys.entrySet()) {

                if (flag == 0 || flag == 10 || flag == 19) serializer.startTag("", "Row");

                serializer.startTag("", "Key");
                serializer.attribute("", "android:keyLabel", key.getKey());
                serializer.attribute("", "android:codes", key.getValue());

                if (flag == 0 || flag == 10)
                    serializer.attribute("", "android:keyEdgeFlags", "left");

                if (flag == 9 || flag == 18)
                    serializer.attribute("", "android:keyEdgeFlags", "right");

                serializer.endTag("", "Key");

                if (flag == 9 || flag == 18 || flag == keys.size())
                    serializer.endTag("", "Row");

                flag++;
            }

            serializer.endDocument();

            return writer.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        /*<Row>
        <Key android:codes="-1"      android:keyEdgeFlags="left" android:keyWidth="14.199996%" android:keyIcon="@drawable/shift"/>
        <Key android:codes="0x314b"  android:keyLabel="ㅋ" />
        <Key android:codes="0x314c"  android:keyLabel="ㅌ" />
        <Key android:codes="0x314a"  android:keyLabel="ㅊ" />
        <Key android:codes="0x314d"  android:keyLabel="ㅍ" />
        <Key android:codes="0x3160"  android:keyLabel="ㅠ" />
        <Key android:codes="0x315c"  android:keyLabel="ㅜ" />
        <Key android:codes="0x3161"  android:keyLabel="ㅡ" />
        <Key android:codes="-5"      android:keyEdgeFlags="right"   android:keyWidth="17%" android:keyIcon="@drawable/delete"/>
    </Row>
    <Row android:rowEdgeFlags="bottom">
        <Key android:codes="-52" android:keyLabel="123"    android:keyWidth="10%p"  android:keyEdgeFlags="left"/>
        <Key android:codes="-51" android:keyIcon="@drawable/language"    android:keyWidth="10%p"/>
        <Key android:codes="44"  android:keyLabel=","   android:keyWidth="10%p" />
        <Key android:codes="47"  android:keyLabel="/"   android:keyWidth="10%p" />
        <Key android:codes="32"  android:keyLabel="간격" android:keyWidth="40%p" />
        <Key android:codes="-4"  android:keyLabel="이미" android:keyWidth="20%p" android:keyEdgeFlags="right"/>
    </Row>*/
    }
}
