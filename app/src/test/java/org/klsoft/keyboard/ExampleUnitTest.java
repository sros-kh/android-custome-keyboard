package org.klsoft.keyboard;

import android.util.Xml;

import org.junit.Test;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void writeKeyboardRandom() {
        try {
            FileOutputStream fileos = new FileOutputStream("keyboard.xml");

            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "userData");
            xmlSerializer.startTag(null, "userName");
            xmlSerializer.text("sros");
            xmlSerializer.endTag(null, "userName");
            xmlSerializer.startTag(null, "password");
            xmlSerializer.text("123");
            xmlSerializer.endTag(null, "password");
            xmlSerializer.endTag(null, "userData");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(4, 2 + 2);
    }
}