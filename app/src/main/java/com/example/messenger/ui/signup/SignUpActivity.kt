package com.example.messenger.ui.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.messenger.R
import com.example.messenger.data.AppPreferences
import com.example.messenger.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(),SignUpView{

    private lateinit var presentor: SignUpPresentor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        presentor = SignUpPresentorImpl(this)
        presentor.appPreferences = AppPreferences.create(this)
        btn_sign_up.setOnClickListener{
            presentor.executeSignUp(
                et_username.text.toString(),
                et_password.text.toString(),
                et_phone.text.toString())
        }
        bindViews()
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun showSignUpError() {
        Toast.makeText(this,"An sign up error. Please try again later.",Toast.LENGTH_SHORT).show()
    }

    override fun setUsernameError() {
        et_username.error = "Username Error"
    }

    override fun setPasswordError() {
        et_password.error = "Password Error"
    }

    override fun setPhoneNumberError() {
        et_phone.error = "Phone Number Error"
    }

    override fun hideProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun navigateToHome() {
        finish()
        startActivity(Intent(this,MainActivity::class.java))
    }

    override fun bindViews() {

    }

    override fun getContext(): Context {
        return this
    }

    override fun showAuthError() {
        Toast.makeText(this,"An authorization error. Please try again later.",Toast.LENGTH_SHORT).show()
    }
}