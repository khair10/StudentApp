package com.khair.appforitis.presentation.company

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
import com.google.android.material.textview.MaterialTextView
import com.khair.appforitis.R
import com.khair.appforitis.data.network.AuthenticationProvider
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.recallsforcompany.CompanyRecallsActivity
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class CompanyActivity : MvpAppCompatActivity(), CompanyContract.View {

    companion object {
        const val ID = "ID"
        fun start(context: Context, id: Long){
            val intent = Intent(context, CompanyActivity::class.java)
            intent.putExtra(ID, id)
            context.startActivity(intent)
        }
    }

    private lateinit var toolbar: MaterialToolbar
    private lateinit var tvCompanyName: TextView
    private lateinit var tvCompanyAddress: MaterialTextView
    private lateinit var tvCompanySite: MaterialTextView
    private lateinit var tvCompanyPhone: MaterialTextView
    private lateinit var tvCompanyRating: MaterialTextView
    private lateinit var rbCompanyRating: RatingBar
    private lateinit var tvRecallsCount: TextView
    private lateinit var tvCompanyDescription: TextView
    private lateinit var pbLoading: ProgressBar
    private lateinit var mcContainer: MaterialCardView
    private lateinit var btnShowRecalls: MaterialButton

    @InjectPresenter
    lateinit var presenter: CompanyPresenter

    @ProvidePresenter
    fun provideCompanyPresenter(): CompanyPresenter{
        return CompanyPresenter(intent.getLongExtra(ID, 0))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
        initToolbar()
        initViews()
        initViewListeners()
    }

    override fun showCompany(company: Company) {
        company.run {
            tvCompanyName.text = name
            tvCompanyAddress.text = address
            tvCompanySite.text = website
            tvCompanyPhone.text = phone
            tvCompanyRating.text = rating.toString()
            rbCompanyRating.rating = rating
            tvRecallsCount.text = "($recallsCount чел.)"
            tvCompanyDescription.text = information
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

    override fun openLoginPage() {
        LoginActivity.start(this)
    }

    override fun showException(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViews() {
        tvCompanyName = findViewById(R.id.tv_company_name)
        tvCompanyAddress = findViewById(R.id.tv_company_address)
        tvCompanySite = findViewById(R.id.tv_company_website)
        tvCompanyPhone = findViewById(R.id.tv_company_phone)
        tvCompanyRating = findViewById(R.id.tv_company_rating)
        rbCompanyRating = findViewById(R.id.rb_company_rating)
        tvRecallsCount = findViewById(R.id.tv_recalls_count)
        tvCompanyDescription = findViewById(R.id.tv_company_description)
        btnShowRecalls = findViewById(R.id.btn_show_recalls)
        pbLoading = findViewById(R.id.pb_loading)
        mcContainer = findViewById(R.id.cv_company_container)
    }

    private fun initViewListeners() {
        btnShowRecalls.setOnClickListener {
            CompanyRecallsActivity.start(this, tvCompanyName.text.toString())
        }
    }
}
