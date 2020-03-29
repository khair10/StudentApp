package com.khair.appforitis.presentation.recallcreation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.khair.appforitis.R
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.recallcreation.dto.CompanyItemDto
import com.khair.appforitis.presentation.recallcreation.dto.RecallCreationDto
import com.khair.appforitis.presentation.recallcreation.dto.StudentItemDto
import com.khair.appforitis.studentId
import com.khair.appforitis.studentName

class RecallCreationActivity : AppCompatActivity(), RecallCreationContract.View{

    companion object {
        fun start(context: Context){
            val intent = Intent(context, RecallCreationActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var etDescription: EditText
    private lateinit var spinCompanies: AutoCompleteTextView
    private lateinit var spinAdapter: ArrayAdapter<CompanyItemDto>
    private lateinit var rbRationg: RatingBar
    private lateinit var pbLoading: ProgressBar
    private lateinit var mcContainer: MaterialCardView
    private lateinit var btnCreate: MaterialButton

    private lateinit var presenter: RecallCreationContract.Presenter

    // TODO move to presenter
    private var company: CompanyItemDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recall_creation)
        initToolbar()
        initViews()
        initViewListeners()
        initPresenter()
        presenter.getCompanies()
    }

    override fun finishActivity() {
        finish()
    }

    override fun openLoginPage() {
        LoginActivity.start(this)
    }

    override fun fillSpinnerWithCompanies(companies: List<CompanyItemDto>) {
        spinAdapter.clear()
        spinAdapter.addAll(companies)
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
        MaterialAlertDialogBuilder(this)
            .setTitle("Ошибка")
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton("Закрыть", null)
            .show()
    }

    private fun initToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViews() {
        etDescription = findViewById(R.id.et_recall)
        spinCompanies = findViewById(R.id.spin_company_name)
        rbRationg = findViewById(R.id.rb_company_rating)
        btnCreate = findViewById(R.id.btn_create_recall)
        spinAdapter = ArrayAdapter<CompanyItemDto>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            ArrayList<CompanyItemDto>()
        ).also { adapter ->
            //            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinCompanies.setAdapter(adapter)
        }
        pbLoading = findViewById(R.id.pb_loading)
        mcContainer = findViewById(R.id.cv_recall_container)
    }

    private fun initViewListeners() {
        btnCreate.setOnClickListener {
            hideKeyboard()
            presenter.addRecall(
                RecallCreationDto(
                    StudentItemDto(studentId, studentName),
                    CompanyItemDto(company?.id ?: -1, company?.name ?: ""),
                    etDescription.text.toString(),
                    rbRationg.rating
                )
            )
        }
        spinCompanies.setOnItemClickListener { adapterView, _, position, _ ->
            run {
                val item: Any = adapterView.getItemAtPosition(position)
                if (item is CompanyItemDto) {
                    // TODO presenter.rememberSelection()
                    company = item
                }
            }
        }
    }

    private fun initPresenter() {
        presenter = RecallCreationPresenter(this)
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
