package com.khair.appforitis.presentation.vacancy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.khair.appforitis.R
import com.khair.appforitis.domain.entity.Vacancy
import com.khair.appforitis.presentation.company.CompanyActivity
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.profile.ProfileActivity

class VacancyActivity : AppCompatActivity(), VacancyContract.View {

    companion object {
        const val ID = "ID"
        fun start(context: Context, id: Long){
            val intent = Intent(context, VacancyActivity::class.java)
            intent.putExtra(ID, id)
            context.startActivity(intent)
        }
    }

    private lateinit var toolbar: MaterialToolbar
    private lateinit var tvVacancyName: TextView
    private lateinit var tvVacancySalary: TextView
    private lateinit var tvCompanyName: TextView
    private lateinit var rbCompanyRating: RatingBar
    private lateinit var tvRecallsCount: TextView
    private lateinit var btnShowDetails: MaterialButton
    private lateinit var tvVacancy: TextView
    private lateinit var pbLoading: ProgressBar
    private lateinit var mcContainer: MaterialCardView

    private lateinit var vacancyPresenter: VacancyContract.Presenter

    var studentId: Long = -1
    var companyId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacancy)
        initToolbar()
        initViews()
        initViewListeners()
        initPresenter()
        getVacancyFromPresenter()
    }

    override fun showVacancy(vacancy: Vacancy) {
        vacancy.run {
            studentId = student.id
            companyId = company.id
            tvVacancyName.text = name
            tvVacancySalary.text = salary.toString()
            tvCompanyName.text = company.name
            rbCompanyRating.rating = rating
            tvRecallsCount.text = recallsCount.toString()
            tvVacancy.text = information
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

    override fun showException(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun openLoginPage() {
        LoginActivity.start(this)
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViews() {
        tvVacancyName = findViewById(R.id.tv_vacancy_name)
        tvVacancySalary = findViewById(R.id.tv_vacancy_salary)
        tvCompanyName = findViewById(R.id.tv_company_name)
        rbCompanyRating = findViewById(R.id.rb_company_rating)
        tvRecallsCount = findViewById(R.id.tv_recalls_count)
        btnShowDetails = findViewById(R.id.btn_show_details_and_contacts)
        tvVacancy = findViewById(R.id.tv_vacancy)
        pbLoading = findViewById(R.id.pb_loading)
        mcContainer = findViewById(R.id.cv_vacancy_container)
    }

    private fun initViewListeners() {
        btnShowDetails.setOnClickListener {
            if(studentId != -1L)
                ProfileActivity.start(this, studentId)
            // TODO
        }
        tvCompanyName.setOnClickListener {
            if(companyId != -1L)
                CompanyActivity.start(this, studentId)
        }
    }

    private fun initPresenter() {
        vacancyPresenter = VacancyPresenter(this)
    }

    private fun getVacancyFromPresenter(){
        val vacancyId = intent.getLongExtra(ID, 0)
        vacancyPresenter.getVacancy(vacancyId)
    }
}
