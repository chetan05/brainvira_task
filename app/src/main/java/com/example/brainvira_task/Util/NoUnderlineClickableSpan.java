package com.example.brainvira_task.Util;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * Created by pbrower on 8/20/15.
 */
public abstract class NoUnderlineClickableSpan extends ClickableSpan {
    public static final String TAG = NoUnderlineClickableSpan.class.getCanonicalName();

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }
}
