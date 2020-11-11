package com.example.brainvira_task.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.brainvira_task.R
import com.example.brainvira_task.Util.NetworkUtils.isNetworkAvailable
import com.example.brainvira_task.Util.UIUtils
import com.mobiquity.mobsterapp.dev.GeneralDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() , View.OnClickListener {
    companion object {
        const val API_ERROR_DIALOG_TAG = "API_ERROR_DIALOG_TAG"
        const val REQUEST_DETAILS_RESULT = 1500
        const val username: String = "test@android.com"
        const val pwd: String = "123456"

    }
    private lateinit var ft: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        UIUtils.hideKeyBoardOnEditTextLostFocus(findViewById(R.id.login_screen), this)
        sign_in.setOnClickListener(this)
        email.onFocusChangeListener = onFocusChangeListener
        password.onFocusChangeListener = onFocusChangeListener
        ft = supportFragmentManager
        password.editText.setImeOptions(EditorInfo.IME_ACTION_DONE)
    }


    var onFocusChangeListener =
        OnFocusChangeListener { v, hasFocus ->
            when (v.id) {
                R.id.email -> if (!hasFocus) {
                    UIUtils.clientSideEmailValidation(email)
                } else {
                    email.dismissError()
                }
                R.id.password -> if (!hasFocus) {
                    TextUtils.isEmpty(password.editText.getText().toString())
                } else if (UIUtils.clientSideEmailValidation(email)) {
                    email.dismissError()
                }
                else -> {
                }
            }
            password.dismissError()
        }

    override fun onClick(view: View) {
        when (view.id) {

            R.id.sign_in -> {
              //  progressBar.visibility = View.VISIBLE
               // signIn.setVisibility(View.GONE)
                login(
                    email.editText.getText().toString(),
                    password.editText.getText().toString()
                )
            }
            else -> {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.GONE
        sign_in.setVisibility(View.VISIBLE)
    }

    fun login(emailId: String?, password: String?) {

      if (emailId != null && password!=null){
          if (emailId.equals(username) && password.equals(pwd)){
              if (isNetworkAvailable(this)) {
                  startActivity(Intent(this, RatesActivity::class.java))
                  finish()
                  overridePendingTransition(R.anim.left_to_right, R.anim.no_change)
              }
          } else {
              GeneralDialog.newInstance(
                  this.getString(R.string.error_happened),
                  this.getString(R.string.login_failed_error_message), this.getString(R.string.ok), ""
              ).show(
                  ft,
                  SearchActivity.API_ERROR_DIALOG_TAG
              )
          }

      } else {
          GeneralDialog.newInstance(
              this.getString(R.string.error_happened),
              this.getString(R.string.login_failed_error_message), this.getString(R.string.ok), ""
          ).show(
              ft,
              SearchActivity.API_ERROR_DIALOG_TAG
          )
      }

    }
}