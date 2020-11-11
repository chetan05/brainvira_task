package com.example.brainvira_task

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.*
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.brainvira_task.Util.UIUtils
import com.example.brainvira_task.extensions.INVISIBLE
import com.example.brainvira_task.extensions.VISIBLE


class KGErrorEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    lateinit var editText: EditText
        private set
    private lateinit var fieldName: TextView
    private lateinit var errorTextView: TextView

    private val focusChangeListener = OnFocusChangeListener { _, hasFocus ->
        if (onFocusChangeListener != null)
            onFocusChangeListener.onFocusChange(this, hasFocus)
    }

    private val textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //empty method
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //empty method
        }

        override fun afterTextChanged(s: Editable) {
            // Show the label when typing if text is visible.
            if (s.isEmpty()) fieldName.INVISIBLE() else fieldName.VISIBLE()
        }
    }

    private val selectionActionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            //empty method
        }
    }

    init {
        init()
        attrs?.let {
            checkAttributeSet(it)
        }
    }

    private fun init() {
        addView(
            LayoutInflater.from(context).inflate(R.layout.error_edittext, null),
            LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        editText = findViewById(R.id.errorEditTextField)
        editText.apply {
            onFocusChangeListener = focusChangeListener
            addTextChangedListener(textChangeListener)
            if (inputType == InputType.TYPE_CLASS_TEXT || inputType == InputType.TYPE_CLASS_NUMBER) {
                customSelectionActionModeCallback = selectionActionModeCallback
                isLongClickable = false
            }
        }

        fieldName = findViewById(R.id.errorEditTextFieldName)
        errorTextView = findViewById(R.id.errorEditTextError)
    }

    fun showError(error: String) {
        errorTextView.apply {
            text = error
            visibility = View.VISIBLE
            showError()
        }
    }

    /**
     * Used when we just want to turn the edit text bar red.
     */
    fun showError() {
        editText.apply {
            setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(context, R.drawable.error),
                null
            )
            isSelected = true
        }
        fieldName.apply {
            setTextColor(ContextCompat.getColor(context, R.color.actionbar_red))
            VISIBLE()
        }
        UIUtils.shakeEmailView(context, this)
    }

    fun dismissError() {
        editText.apply {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            isSelected = false
        }
        errorTextView.INVISIBLE()
        // Always show the label UNLESS there is no text.
        if (editText.text.isEmpty()) {
            fieldName.INVISIBLE()
        }
        fieldName.setTextColor(ContextCompat.getColor(context, R.color.text_blue))
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        editText.addTextChangedListener(watcher)
    }

    @SuppressLint("ResourceType")
    private fun checkAttributeSet(attrs: AttributeSet) {
        val attrsArray = intArrayOf(
            android.R.attr.hint, // 0
            android.R.attr.inputType, // 1
            R.attr.fieldName, // 2
            R.attr.letterSpacing, // 3
            R.attr.textSize, // 4
            R.attr.maxLength // 5
        )

        val ta = context.obtainStyledAttributes(attrs, attrsArray)

        // Set max length of EditText
        val fArray = arrayOfNulls<InputFilter>(1)
        fArray[0] = InputFilter.LengthFilter(ta.getInt(5, DEFAULT_MAX_LENGTH))

        fieldName.text = ta.getString(2)

        editText.apply {
            hint = ta.getString(0)
            filters = fArray
            inputType = ta.getInt(1, InputType.TYPE_CLASS_TEXT)
            letterSpacing = ta.getFloat(3, 0.0f) // Default is no spacing
            textSize = ta.getFloat(4, DEFAULT_FONT_SIZE)
        }
        ta.recycle()
    }

    fun setFilters(filters: Array<InputFilter>) {
        editText.filters = filters
    }

    fun setMaxLength(maxLength: Int) {
        // Set max length of EditText
        val fArray = arrayOfNulls<InputFilter>(1)
        fArray[0] = InputFilter.LengthFilter(maxLength)
        editText.filters = fArray
    }

    override fun setEnabled(isEnabled: Boolean) {
        editText.isEnabled = isEnabled
    }

    companion object {
        private const val DEFAULT_FONT_SIZE = 18.0f
        private const val DEFAULT_MAX_LENGTH = 50
    }
}