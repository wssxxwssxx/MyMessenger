package com.example.messenger.ui.login

import android.content.Context
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.messenger.R
import com.example.messenger.data.AppPreferences
import com.example.messenger.ui.main.MainActivity
import com.example.messenger.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var presenter: LoginPresentor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bindViews()
    }

    override fun bindViews() {
        presenter = LoginPresentorImpl(this)

        btn_login.setOnClickListener {
            presenter.executeLogin(et_username_login.text.toString(), et_password_login.text.toString())
        }
        btn_sign_up_login.setOnClickListener {
            navigateToSignUp()
        }
    }

    override fun showProgress() {
        progress_bar_login.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar_login.visibility = View.GONE
    }

    override fun setUsernameError() {
        et_username_login.error = "Username field cannot be empty"
    }

    override fun setPasswordError() {
        et_password_login.error = "Password field cannot be empty"
    }

    override fun navigateToSignUp() {
        finish()
        startActivity(Intent(this,SignUpActivity::class.java))
    }

    override fun navigateToHome() {
        startActivity(Intent(this,MainActivity::class.java))
    }


    override fun getContext(): Context {
        return this
    }

    override fun showAuthError() {
        Toast.makeText(this,"Invalid username and password combination",
        Toast.LENGTH_LONG).show()
    }

//    override fun onClick(v: View) {
//        if(v.id == R.id.btn_login){
//            presenter.executeLogin(et_username_login.text.toString(), et_password_login.text.toString())
//        }else if(v.id == R.id.btn_sign_up){
//            navigateToSignUp()
//        }
//    }
}