package com.khair.appforitis.presentation.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.khair.appforitis.R
import com.khair.appforitis.presentation.login.dto.LoginDto
import com.khair.appforitis.presentation.main.MainActivity
import com.khair.appforitis.presentation.registration.RegistrationActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {

    companion object {
        fun start(context: Context){
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var etLogin: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var pbLoading: ProgressBar
    private lateinit var mcContainer: MaterialCardView
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnRegistration: MaterialButton

    private lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initToolbar()
        initViews()
        initViewListeners()
        initPresenter()
    }

    override fun showError(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Ошибка")
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton("Закрыть", null)
            .show()
    }

    override fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        mcContainer.visibility = View.GONE
    }

    override fun hideLoading() {
        pbLoading.visibility = View.GONE
        mcContainer.visibility = View.VISIBLE
    }

    override fun openHome() {
        MainActivity.start(this)
    }

    private fun initToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViews() {
        etLogin = findViewById(R.id.et_login)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegistration = findViewById(R.id.btn_registration)
        pbLoading = findViewById(R.id.pb_loading)
        mcContainer = findViewById(R.id.cv_login_container)
    }

    private fun initViewListeners() {
        btnLogin.setOnClickListener {
            hideKeyboard()
            presenter.login(
                LoginDto(
                    etLogin.text.toString(),
                    etPassword.text.toString()
                )
            )
        }
        btnRegistration.setOnClickListener {
            RegistrationActivity.start(this)
            finish()
        }
    }

    private fun initPresenter() {
        presenter = LoginPresenter(this)
    }

    private fun hideKeyboard() {
        val view: View? = currentFocus
        view?.let {
            val imm: InputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
    }
}
