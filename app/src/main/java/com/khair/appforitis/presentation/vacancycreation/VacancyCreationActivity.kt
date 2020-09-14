package com.khair.appforitis.presentation.vacancycreation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.khair.appforitis.R
import com.khair.appforitis.presentation.base.ConnectionManager
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.recallcreation.dto.CompanyItemDto
import com.khair.appforitis.presentation.vacancy.VacancyActivity
import com.khair.appforitis.presentation.vacancycreation.dto.CompanyDto
import com.khair.appforitis.presentation.vacancycreation.dto.StudentDto
import com.khair.appforitis.presentation.vacancycreation.dto.VacancyCreationDto
import com.khair.appforitis.studentId
import com.khair.appforitis.studentName
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter


class VacancyCreationActivity : MvpAppCompatActivity(), VacancyCreationContract.View {

    companion object {
        fun start(context: Context){
            val intent = Intent(context, VacancyCreationActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var etName: EditText
    private lateinit var etSalary: EditText
    private lateinit var spinCompanies: AutoCompleteTextView
    private lateinit var etDescription: EditText
    private lateinit var btnCreate: MaterialButton
    private lateinit var spinAdapter: ArrayAdapter<CompanyDto>
    private lateinit var pbLoading: ProgressBar
    private lateinit var mcContainer: MaterialCardView

    @InjectPresenter
    lateinit var presenter: VacancyCreationPresenter

    // TODO move to presenter
    private var company: CompanyDto = CompanyDto(-1, "", 0F, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacancy_creation)
        initToolbar()
//        supportActionBar?.setDisplayShowTitleEnabled(false)
        initViews()
        initViewListeners()
    }

    override fun fillSpinnerWithCompanies(companies: List<CompanyDto>) {
        spinAdapter.clear()
        spinAdapter.addAll(companies)
    }

    override fun finishActivity() {
        finish()
    }

    override fun openLoginPage() {
        LoginActivity.start(this)
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
        etName = findViewById(R.id.et_vacancy_name)
        etSalary = findViewById(R.id.et_vacancy_salary)
        spinCompanies = findViewById(R.id.spin_company_name)
        etDescription = findViewById(R.id.et_vacancy)
        btnCreate = findViewById(R.id.btn_create_vacancy)
        spinAdapter = ArrayAdapter<CompanyDto>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            ArrayList<CompanyDto>()
        ).also { adapter ->
            //            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinCompanies.setAdapter(adapter)
        }
        pbLoading = findViewById(R.id.pb_loading)
        mcContainer = findViewById(R.id.cv_vacancy_container)
    }

    private fun initViewListeners() {
        btnCreate.setOnClickListener {
            hideKeyboard()
            if(ConnectionManager.hasConnection(this)){
                presenter.addVacancy(
                    VacancyCreationDto(
                        etName.text.toString(),
                        etSalary.text.toString(),
                        company,
                        etDescription.text.toString(),
                        StudentDto(studentId, studentName)
                    )
                )
            } else {
                Toast.makeText(this, "Проверьте соединение с сетью", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        spinCompanies.setOnItemClickListener { adapterView, _, position, _ ->
            run {
                val item: Any = adapterView.getItemAtPosition(position)
                if (item is CompanyDto) {
                    // TODO presenter.rememberSelection()
                    company = CompanyDto(item.id, item.name, item.rating, item.recallsCount)
                }
            }
        }
        spinCompanies.setOnKeyListener(null)
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
