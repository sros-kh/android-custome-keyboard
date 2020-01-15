package org.klsoft.keyboard;

import java.util.HashMap;
import java.util.Map;

public class Keys {

    public static Map<String , String> krKeys(){
        Map<String, String> keys = new HashMap<>();


        return keys;
    }

    public static Map<String , String> enKeys(){
        Map<String, String> keys = new HashMap<>();
        keys.put("q", "113");
        keys.put("w", "119");
        keys.put("e", "101");
        keys.put("r", "114");
        keys.put("t", "116");
        keys.put("y", "121");
        keys.put("u", "117");
        keys.put("i", "105");
        keys.put("o", "111");
        keys.put("p", "112");
        keys.put("a", "97");
        keys.put("s", "115");
        keys.put("d", "100");
        keys.put("f", "102");
        keys.put("g", "103");
        keys.put("h", "104");
        keys.put("j", "106");
        keys.put("k", "107");
        keys.put("l", "108");
        keys.put("z", "122");
        keys.put("x", "120");
        keys.put("c", "99");
        keys.put("v", "118");
        keys.put("b", "98");
        keys.put("n", "110");
        keys.put("m", "109");
        keys.put(".", "46");

        return keys;
    }


    /*<Row>
        <Key android:codes="113" android:keyLabel="q" android:keyEdgeFlags="left"/>
        <Key android:codes="119" android:keyLabel="w"/>
        <Key android:codes="101" android:keyLabel="e"/>
        <Key android:codes="114" android:keyLabel="r"/>
        <Key android:codes="116" android:keyLabel="t"/>
        <Key android:codes="121" android:keyLabel="y"/>
        <Key android:codes="117" android:keyLabel="u"/>
        <Key android:codes="105" android:keyLabel="i"/>
        <Key android:codes="111" android:keyLabel="o"/>
        <Key android:codes="112" android:keyLabel="p" android:keyEdgeFlags="right"/>
    </Row>
    <Row>
        <Key android:codes="97"  android:keyLabel="a" android:keyEdgeFlags="left" android:horizontalGap="5%"/>
        <Key android:codes="115" android:keyLabel="s"/>
        <Key android:codes="100" android:keyLabel="d"/>
        <Key android:codes="102" android:keyLabel="f"/>
        <Key android:codes="103" android:keyLabel="g"/>
        <Key android:codes="104" android:keyLabel="h"/>
        <Key android:codes="106" android:keyLabel="j"/>
        <Key android:codes="107" android:keyLabel="k"/>
        <Key android:codes="108" android:keyLabel="l"/>
    </Row>
    <Row>
        <Key android:codes="-1"  android:keyEdgeFlags="left" android:keyWidth="14.199996%" android:keyIcon="@drawable/shift"/>
        <Key android:codes="122" android:keyLabel="z"/>
        <Key android:codes="120" android:keyLabel="x"/>
        <Key android:codes="99"  android:keyLabel="c"/>
        <Key android:codes="118" android:keyLabel="v"/>
        <Key android:codes="98"  android:keyLabel="b"/>
        <Key android:codes="110" android:keyLabel="n"/>
        <Key android:codes="109" android:keyLabel="m"/>
        <Key android:keyLabel="." android:codes="46"/>
        <Key android:codes="-5"  android:keyEdgeFlags="right" android:keyWidth="17%" android:keyIcon="@drawable/delete" />
    </Row>*/
}
