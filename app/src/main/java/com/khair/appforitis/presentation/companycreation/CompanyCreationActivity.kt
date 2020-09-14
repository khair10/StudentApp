package com.khair.appforitis.presentation.companycreation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.khair.appforitis.R
import com.khair.appforitis.presentation.base.ConnectionManager
import com.khair.appforitis.presentation.companycreation.dto.CompanyCreationDto
import com.khair.appforitis.presentation.login.LoginActivity
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter


class CompanyCreationActivity : MvpAppCompatActivity(), CompanyCreationContract.View {

    companion object {
        fun start(context: Context){
            val intent = Intent(context, CompanyCreationActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var etName: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var etWebSite: TextInputEditText
    private lateinit var etContactNumber: TextInputEditText
    private lateinit var etInfo: TextInputEditText
    private lateinit var pbLoading: ProgressBar
    private lateinit var mcContainer: MaterialCardView
    private lateinit var btnCreate: MaterialButton

    @InjectPresenter
    lateinit var presenter: CompanyCreationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_creation)
        initToolbar()
        initViews()
        initViewListeners()
    }

    override fun finishActivity() {
        finish()
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

    override fun openLoginPage() {
        LoginActivity.start(this)
    }

    private fun initToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViews() {
        etName = findViewById(R.id.et_company_name)
        etAddress = findViewById(R.id.et_company_address)
        etWebSite = findViewById(R.id.et_company_site)
        etContactNumber = findViewById(R.id.et_company_phone)
        etInfo = findViewById(R.id.et_company_description)
        btnCreate = findViewById(R.id.btn_create_company)
        pbLoading = findViewById(R.id.pb_loading)
        mcContainer = findViewById(R.id.cv_company_container)
    }

    private fun initViewListeners() {
        btnCreate.setOnClickListener {
            hideKeyboard()
            if(ConnectionManager.hasConnection(this)){
                presenter.addCompany(
                    CompanyCreationDto(
                        etName.text.toString(),
                        etAddress.text.toString(),
                        etWebSite.text.toString(),
                        etContactNumber.text.toString(),
                        etInfo.text.toString()
                    )
                )
            } else {
                Toast.makeText(this, "Проверьте соединение с сетью", Toast.LENGTH_SHORT)
                    .show()
            }
        }
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
