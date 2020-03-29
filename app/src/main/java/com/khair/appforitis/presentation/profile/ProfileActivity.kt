package com.khair.appforitis.presentation.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.card.MaterialCardView
import com.khair.appforitis.R
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.presentation.login.LoginActivity

class ProfileActivity : AppCompatActivity(), ProfileContract.View {

    companion object {
        const val ID = "ID"
        fun start(context: Context, id: Long){
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra(ID, id)
            context.startActivity(intent)
        }
    }

    private lateinit var tvName: TextView
    private lateinit var tvCompanyName: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvVk: TextView
    private lateinit var tvTelegram: TextView
    private lateinit var tvFacebook: TextView
    private lateinit var tvAdditionalDescription: TextView
    private lateinit var pbLoading: ProgressBar
    private lateinit var mcContainer: MaterialCardView

    private lateinit var presenter: ProfileContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initToolbar()
        initViews()
        initViewListeners()
        initPresenter()
        getProfileFromPresenter()
    }

    override fun showProfile(profile: Profile) {
        profile.run {
            tvName.text = name
            tvCompanyName.text = company.name
            tvPhone.text = phone
            tvVk.text = vk
            tvTelegram.text = telegram
            tvFacebook.text = facebook
            tvAdditionalDescription.text = additionalInformation
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
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun initViews() {
        tvName = findViewById(R.id.tv_student_name)
        tvCompanyName = findViewById(R.id.tv_company_name)
        tvPhone = findViewById(R.id.tv_phone)
        tvVk = findViewById(R.id.tv_vk)
        tvTelegram = findViewById(R.id.tv_telegram)
        tvFacebook = findViewById(R.id.tv_facebook)
        tvAdditionalDescription = findViewById(R.id.tv_addition_description)
        pbLoading = findViewById(R.id.pb_loading)
        mcContainer = findViewById(R.id.cv_profile_container)
    }

    private fun initViewListeners() {
        //TODO Listeners
    }

    private fun initPresenter() {
        presenter = ProfilePresenter(this)
    }

    private fun getProfileFromPresenter() {
        val profileId = intent.getLongExtra(ID, 0)
        presenter.getProfile(profileId)
    }
}
