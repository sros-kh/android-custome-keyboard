package org.klsoft.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class KLSoftKeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private KLSoftKeyboard keyboardEn;
    private KLSoftKeyboard keyboardKr;
    private KLSoftKeyboard keyboardKrShift;
    private KLSoftKeyboard keyboardNo;
    private KLSoftKeyboard keyboardKrNo;

    private boolean isEn = true;
    private boolean isNo;
    private boolean isCaps;
    private boolean isShift;

    public void initializeKeyboards() {
        keyboardEn = new KLSoftKeyboard(this, R.xml.keyboard_en);
        keyboardKr = new KLSoftKeyboard(this, R.xml.keyboard_kr);
        keyboardKrShift = new KLSoftKeyboard(this, R.xml.keyboard_kr_shift);
        keyboardNo = new KLSoftKeyboard(this, R.xml.keyboard_no_en);
        keyboardKrNo = new KLSoftKeyboard(this, R.xml.keyboard_no_kr);
    }


    @Override
    public View onCreateInputView() {
        initializeKeyboards();
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

            case KLSoftKeyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;

            case KLSoftKeyboard.KEYCODE_SHIFT:
                isCaps = !isCaps;
                if (isEn)
                    keyboardEn.setShifted(isCaps);
                else {
                    keyboardView.setKeyboard(isNo ? keyboardKrNo : !isShift ? keyboardKrShift : keyboardKr);
                    isShift = !isShift;
                }
                keyboardView.invalidateAllKeys();
                break;

            case KLSoftKeyboard.KEYCODE_DONE:
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
            case KLSoftKeyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case KLSoftKeyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }
}
