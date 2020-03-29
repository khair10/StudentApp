package com.khair.appforitis.presentation.profileediting

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.khair.appforitis.R
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.profileediting.dto.CompanyDto
import com.khair.appforitis.presentation.profileediting.dto.ProfileDto

class ProfileEditingActivity : AppCompatActivity(), ProfileEditingContract.View {

    companion object {
        fun start(context: Context){
            val intent = Intent(context, ProfileEditingActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var etName: TextInputEditText
    private lateinit var spinCompany: AutoCompleteTextView
    private lateinit var etPhone: EditText
    private lateinit var etVk: EditText
    private lateinit var etTelegram: EditText
    private lateinit var etFacebook: EditText
    private lateinit var etAdditionalDescription: EditText
    private lateinit var pbLoading: ProgressBar
    private lateinit var mcContainer: MaterialCardView
    private lateinit var btnEdit: MaterialButton
    private lateinit var spinAdapter: ArrayAdapter<CompanyDto>

    private lateinit var presenter: ProfileEditingContract.Presenter

    private var company: CompanyDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_editing)
        initToolbar()
        initViews()
        initViewListeners()
        initPresenter()
        getProfileFromPresenter()
    }

    override fun fillSpinnerWithCompanies(companies: List<CompanyDto>) {
        spinAdapter.clear()
        spinAdapter.addAll(companies)
    }

    override fun showProfile(profile: Profile) {
        profile.run {
            val pos = spinAdapter.getPosition(CompanyDto(this.company.id, this.company.name))
            spinCompany.setSelection(pos)
            etName.setText(name)
            etPhone.setText(phone)
            etVk.setText(vk)
            etTelegram.setText(telegram)
            etFacebook.setText(facebook)
            etAdditionalDescription.setText(additionalInformation)
        }
    }

    override fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        mcContainer.visibility = View.GONE
    }

    override fun hideLoading() {
        pbLoading.visibility = View.GONE
        mcContainer.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun finishActivity() {
        finish()
    }

    override fun openLoginPage() {
        LoginActivity.start(this)
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    private fun initViews() {
        etName = findViewById(R.id.et_name)
        spinCompany = findViewById(R.id.spin_company_name)
        etPhone = findViewById(R.id.et_phone)
        etVk = findViewById(R.id.et_vk)
        etTelegram = findViewById(R.id.et_telegram)
        etFacebook = findViewById(R.id.et_facebook)
        etAdditionalDescription = findViewById(R.id.et_addition_description)
        btnEdit = findViewById(R.id.btn_edit_profile)
        spinAdapter = ArrayAdapter<CompanyDto>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            ArrayList<CompanyDto>()
        ).also { adapter ->
            //            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinCompany.setAdapter(adapter)
        }
        pbLoading = findViewById(R.id.pb_loading)
        mcContainer = findViewById(R.id.cv_profile_container)
    }

    private fun initViewListeners() {
        btnEdit.setOnClickListener {
            hideKeyboard()
            presenter.setProfile(
                ProfileDto(
                    etName.text.toString(),
                    CompanyDto(company?.id ?: -1L, company?.name ?: ""),
                    etPhone.text.toString(),
                    etVk.text.toString(),
                    etTelegram.toString(),
                    etFacebook.toString(),
                    etAdditionalDescription.text.toString()
                )
            )
        }
        spinCompany.setOnItemClickListener { adapterView, _, position, _ ->
            run {
                val item: Any = adapterView.getItemAtPosition(position)
                if (item is CompanyDto) {
                    // TODO presenter.rememberSelection()
                    company = item
                }
            }
        }
    }

    private fun initPresenter() {
        presenter = ProfileEditingPresenter(this)
    }

    private fun getProfileFromPresenter() {
        presenter.getProfile()
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
