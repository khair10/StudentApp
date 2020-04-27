package com.khair.appforitis.presentation.registration

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
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.login.LoginContract
import com.khair.appforitis.presentation.login.LoginPresenter
import com.khair.appforitis.presentation.login.dto.LoginDto
import com.khair.appforitis.presentation.registration.dto.ProfileDto
import com.khair.appforitis.presentation.registration.dto.RegistrationDto

class RegistrationActivity : AppCompatActivity(), RegistrationContract.View {

    companion object {
        fun start(context: Context){
            val intent = Intent(context, RegistrationActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var etName: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etLogin: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etPasswordConfirm: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnRegistrate: MaterialButton
    private lateinit var pbLoading: ProgressBar
    private lateinit var mcContainer: MaterialCardView

    private lateinit var presenter: RegistrationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
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

    override fun openLogin() {
        LoginActivity.start(this)
        finish()
    }

    private fun initToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViews() {
        etName = findViewById(R.id.et_name)
        etPhone = findViewById(R.id.et_phone)
        etLogin = findViewById(R.id.et_login)
        etPassword = findViewById(R.id.et_password)
        etPasswordConfirm = findViewById(R.id.et_password_confirm)
        btnRegistrate = findViewById(R.id.btn_registration)
        btnLogin = findViewById(R.id.btn_login)
        pbLoading = findViewById(R.id.pb_loading)
        mcContainer = findViewById(R.id.cv_registration_container)
    }

    private fun initViewListeners() {
        btnRegistrate.setOnClickListener {
            hideKeyboard()
            presenter.registration(
                RegistrationDto(
                    etLogin.text.toString(),
                    etPassword.text.toString(),
                    etPasswordConfirm.text.toString(),
                    ProfileDto(
                        etName.text.toString(),
                        etPhone.text.toString()
                    )
                )
            )
        }
        btnLogin.setOnClickListener {
            LoginActivity.start(this)
        }
    }

    private fun initPresenter() {
        presenter = RegistrationPresenter(this)
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
