package org.klsoft.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KLSoftKeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private static final Pattern ENG_PATTERN = Pattern.compile("[a-zA-Z]");

    private KeyboardView keyboardView;
    private Keyboard keyboardEn;
    private Keyboard keyboardKr;
    private Keyboard keyboardKrShift;
    private Keyboard keyboardNo;
    private Keyboard keyboardNoShift;

    private boolean isEn = true;
    private boolean isNo;
    private boolean isShift;
    private boolean isNoShift;
    private boolean isCaps;

    public static Keyboard getRandomKeyboard(Keyboard[] keyboards) {
        int index = new Random().nextInt(keyboards.length);
        return keyboards[index];
    }

    @Override
    public View onCreateInputView() {
        keyboardKrShift = new Keyboard(this, R.xml.keyboard_kr_shift);
        keyboardNo = new Keyboard(this, R.xml.keyboard_no);
        keyboardNoShift = new Keyboard(this, R.xml.keyboard_no_shift);

        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboardView.setOnKeyboardActionListener(this);

        return keyboardView;
    }

    @Override
    public void onWindowShown() {
        keyboardEn = getRandomKeyboard(enKeyboard());
        keyboardKr = getRandomKeyboard(krKeyboard());
        keyboardView.setKeyboard(isEn ? keyboardEn : keyboardKr);
    }


    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);

        switch (primaryCode) {

            case Keyboard.KEYCODE_DELETE:

                if (isEn) {
                    ic.deleteSurroundingText(1, 0);
                }
                else {

                    CharSequence sequence = ic.getTextBeforeCursor(1, 0);

                    if (!"".contentEquals(sequence)){
                        sequence = EngKorTypingConvertor.convertKor2Eng(sequence.toString());
                        sequence = sequence.subSequence(0, sequence.length() - 1);
                        sequence = EngKorTypingConvertor.convertEng2Kor(sequence.toString());

                        ic.deleteSurroundingText(1, 0);
                        ic.commitText(sequence, 1);
                    }

                }
                break;

            case Keyboard.KEYCODE_SHIFT:

                if (isEn) {
                    if (isNo) {
                        keyboardView.setKeyboard(isNoShift ? keyboardNo : keyboardNoShift);
                        isNoShift = !isNoShift;
                    } else {
                        isCaps = !isCaps;
                        keyboardEn.setShifted(isCaps);
                    }
                } else {
                    keyboardView.setKeyboard(isNo ?
                            isNoShift ? keyboardNo : keyboardNoShift :
                            !isShift ? keyboardKrShift : keyboardKr);
                    isShift = !isShift;
                    isNoShift = !isNoShift;
                }

                keyboardView.invalidateAllKeys();
                break;

            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;

            case -51: //switch language
                keyboardEn = getRandomKeyboard(enKeyboard());
                keyboardKr = getRandomKeyboard(krKeyboard());
                keyboardView.setKeyboard(isEn ? keyboardKr : keyboardEn);
                isEn = !isEn;
                isNo = false;
                isShift = false;
                isCaps = false;
                isNoShift = false;
                break;

            case -52: //switch keyboard
                keyboardView.setKeyboard(!isNo ? keyboardNo : isEn ? keyboardEn : keyboardKr);
                isNo = !isNo;
                break;

            default:
                char code = (char) primaryCode;
                String c = "";

                if (Character.isLetter(code)) {
                    if(isEn){
                        if (isCaps) code = Character.toUpperCase(code);
                        c = String.valueOf(code);
                    }else {
                        CharSequence sequence = ic.getTextBeforeCursor(1, 0);
                        Matcher mc = ENG_PATTERN.matcher(sequence);

                        if(mc.find()){
                            c = String.valueOf(code);

                        }else {
                            sequence = EngKorTypingConvertor.convertKor2Eng(sequence.toString());
                            c = EngKorTypingConvertor.convertKor2Eng(String.valueOf(code));
                            c = EngKorTypingConvertor.convertEng2Kor(sequence + c);
                            ic.deleteSurroundingText(1, 0);
                        }
                    }

                }else {
                    c = String.valueOf(code);
                }

                ic.commitText(c, 1);
        }
    }

    public Keyboard[] enKeyboard() {
        Keyboard[] keyboards = new Keyboard[5];
        keyboards[0] = new Keyboard(this, R.xml.keyboard_en);
        keyboards[1] = new Keyboard(this, R.xml.keyboard_en_random);
        keyboards[2] = new Keyboard(this, R.xml.keyboard_en_random_2);
        keyboards[3] = new Keyboard(this, R.xml.keyboard_en_random_3);
        keyboards[4] = new Keyboard(this, R.xml.keyboard_en_random_4);

        return keyboards;
    }

    public Keyboard[] krKeyboard() {
        Keyboard[] keyboards = new Keyboard[5];
        keyboards[0] = new Keyboard(this, R.xml.keyboard_kr);
        keyboards[1] = new Keyboard(this, R.xml.keyboard_kr_random);
        keyboards[2] = new Keyboard(this, R.xml.keyboard_kr_random_2);
        keyboards[3] = new Keyboard(this, R.xml.keyboard_kr_random_3);
        keyboards[4] = new Keyboard(this, R.xml.keyboard_kr_random_4);

        return keyboards;
    }


    private void playClick(int i) {

        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (i) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

}
