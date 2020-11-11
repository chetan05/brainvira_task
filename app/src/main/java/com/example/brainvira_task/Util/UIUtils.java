package com.example.brainvira_task.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.brainvira_task.KGErrorEditText;
import com.example.brainvira_task.R;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public final class UIUtils {

    private UIUtils() {
    }

    private static AlertDialog alertDialog;
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String PICKUP_FORMAT_HISTORY_DATE = "EEEE, MMM. d";

    public static void shakeEmailView(Context context, View view) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation(shake);
    }

    public static void hideKeyBoard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void hideKeyBoardOnView(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void hideKeyBoardOnEditTextLostFocus(View view, final Activity activity) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener((v, event) -> {
                UIUtils.hideKeyBoard(activity);
                return false;
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyBoardOnEditTextLostFocus(innerView, activity);
            }
        }
    }

    /**
     * Force the keyboard to display.
     *
     * @param view
     * @param context
     */
    public static void showKeyboard(final View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * setup clickable blue link in text
     *
     * @param context         needed for grabbing resources
     * @param textView        textView to be modified
     * @param content         text of the TextView
     * @param startIndex      beginning of linkable text
     * @param endIndex        end of linkable text
     * @param onClickListener what do when clicked
     */
    public static void setSpannableLink(Context context, TextView textView, String content, int startIndex, int endIndex, final View.OnClickListener onClickListener) {
        SpannableString ss = new SpannableString(content);
        ClickableSpan clickableSpan = new NoUnderlineClickableSpan() {
            @Override
            public void onClick(View textView) {
                onClickListener.onClick(textView);
            }
        };
        ss.setSpan(clickableSpan,
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_blue)),
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * setup clickable blue link in text
     *
     * @param context         needed for grabbing resources
     * @param textView        textView to be modified
     * @param startIndex      beginning of linkable text
     * @param endIndex        end of linkable text
     * @param onClickListener what do when clicked
     */
    public static void setSpannableLink(Context context, TextView textView, int startIndex, int endIndex, final View.OnClickListener onClickListener) {
        setSpannableLink(context, textView, textView.getText().toString(), startIndex, endIndex, onClickListener);
    }

    /**
     * performs entered email validation at the client end
     *
     * @param editText contains the entered email
     * @return true or false depending on whether email is valid or not
     */
    public static boolean clientSideEmailValidation(KGErrorEditText editText) {
        String email = editText.getEditText().getText().toString();

        // if the email EditText is empty
        if (TextUtils.isEmpty(email)) {
            editText.dismissError();
            return false;
        } else {
            // validate entered email in proper format
            if (Validator.validateEmail(email)) {
                editText.dismissError();
                return true;
            } else {
                editText.showError(editText.getContext().getString(R.string.email_input_error_text));
                return false;
            }
        }
    }

    /**
     * Listner which allows for easier creation of our custom dialog without having to make
     * a new class for it.
     */
    public interface CustomDialogInterface {
        /**
         * @return A basic String array of the positive and negative button text. Index 0 should be the positive button, index 1 should be the negative button, and index 2 should be the neutral button.
         */
        String[] getButtonText();

        /**
         * See {@link DialogInterface.OnClickListener} for specifics, as
         * this is just mimicing that.
         *
         * @param dialog
         * @param which
         */
        void onPositiveClick(DialogInterface dialog, int which);

        /**
         * See {@link DialogInterface.OnClickListener} for specifics, as
         * this is just mimicing that.
         *
         * @param dialog
         * @param which
         */
        void onNegativeClick(DialogInterface dialog, int which);

        void onNeutralClick(DialogInterface dialog, int which);
    }

    public static AlertDialog getCustomDialog(final Context context, String title, String message, final CustomDialogInterface listener, boolean cancelable) {
        if (alertDialog != null && alertDialog.isShowing()) {
            return alertDialog;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton(listener.getButtonText()[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onPositiveClick(dialog, which);
                    }
                });

        if (listener.getButtonText().length >= 2) {
            builder.setNegativeButton(listener.getButtonText()[1], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onNegativeClick(dialog, which);
                }
            });
        }

        if (listener.getButtonText().length >= 3) {
            builder.setNeutralButton(listener.getButtonText()[2], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onNeutralClick(dialog, which);
                }
            });
        }

        builder.setCancelable(cancelable);

        alertDialog = builder.create();
        if (!TextUtils.isEmpty(title)) {
            alertDialog.setTitle(title);
        }
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                TextView textView = alertDialog.findViewById(android.R.id.message);
                textView.setTypeface(getFont(context));

                alertDialog.findViewById(android.R.id.custom).setVisibility(View.GONE);

                Button buttonPoz = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
                buttonPoz.setTypeface(getFont(context));
                Button buttonNeg = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);
                buttonNeg.setTypeface(getFont(context));
                Button buttonNeutral = alertDialog.getButton(Dialog.BUTTON_NEUTRAL);
                buttonNeutral.setTypeface(getFont(context));
            }
        });


        return alertDialog;
    }

    private static Typeface getFont(Context c) {
        return ResourcesCompat.getFont(c, R.font.helvetica_neue);
    }

    public static void navigateToSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }
    public static void capsAllTextEditText(EditText et)
    {
        InputFilter[] editFilters = et.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        et.setFilters(newFilters);
    }

    /**
     * get file contents as String
     *
     * @param relFilePath path to file relative to KumNGo/app directory, eg "mock_responses/facebook_response_without_name.json"
     * @return contents of file as String
     * @throws Exception
     */
    public static String getTestResourceString(Context context, String relFilePath) {
        try {
            Reader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(relFilePath)));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } catch (Exception e) {
        }
        return null;
    }

    public static String getDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT, Locale.US);
        try {
            Date parsingDate = sdf.parse(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
            return simpleDateFormat.format(parsingDate.getTime());
        } catch (ParseException e) {
            //exception
        }
        return "";
    }
}
