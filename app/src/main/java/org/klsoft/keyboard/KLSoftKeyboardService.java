package org.klsoft.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class KLSoftKeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboardEn;
    private Keyboard keyboardKr;
    private Keyboard keyboardKrShift;
    private Keyboard keyboardNo;
    private Keyboard keyboardKrNo;

    private boolean isEn = true;
    private boolean isNo;
    private boolean isCaps;
    private boolean isShift;


    @Override
    public View onCreateInputView() {

        keyboardEn = new Keyboard(this, R.xml.keyboard_en);
        keyboardKr = new Keyboard(this, R.xml.keyboard_kr);
        keyboardKrShift = new Keyboard(this, R.xml.keyboard_kr_shift);
        keyboardNo = new Keyboard(this, R.xml.keyboard_no_en);
        keyboardKrNo = new Keyboard(this, R.xml.keyboard_no_kr);

        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboardView.setKeyboard(keyboardEn);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);


        switch (primaryCode) {

            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;

            case Keyboard.KEYCODE_SHIFT:
                isCaps = !isCaps;
                if (isEn)
                    keyboardEn.setShifted(isCaps);
                else {
                    keyboardView.setKeyboard(isNo ? keyboardKrNo : !isShift ? keyboardKrShift : keyboardKr);
                    isShift = !isShift;
                }
                keyboardView.invalidateAllKeys();
                break;

            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;

            case -51:
                keyboardView.setKeyboard(isEn ? keyboardKr : keyboardEn);
                isEn = !isEn;
                isNo = false;
                break;

            case -52:
                keyboardView.setKeyboard(!isNo ? isEn ? keyboardNo : keyboardKrNo : isEn ? keyboardEn : keyboardKr);
                isNo = !isNo;
                break;

            default:
                char code = (char) primaryCode;
                if (Character.isLetter(code) && isCaps)
                    code = Character.toUpperCase(code);
                ic.commitText(String.valueOf(code), 1);
        }
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
