package com.khair.appforitis.presentation.main.profile


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

import com.khair.appforitis.R
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.profileediting.ProfileEditingActivity
import com.khair.appforitis.studentId
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class ProfileFragment : MvpAppCompatFragment(), ProfileContract.View {

    private lateinit var tvName: TextView
    private lateinit var tvCompanyName: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvVk: TextView
    private lateinit var tvTelegram: TextView
    private lateinit var tvFacebook: TextView
    private lateinit var tvAdditionalDescription: TextView
    private lateinit var pbLoading: ProgressBar
    private lateinit var mcContainer: MaterialCardView
    private lateinit var btnEdit: MaterialButton

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(root)
        initViewListeners()
        return root
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
        activity?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun openLoginPage() {
        activity?.let {
            LoginActivity.start(it)
        }
    }

    private fun initViewListeners() {
        btnEdit.setOnClickListener {
            activity?.let {
                ProfileEditingActivity.start(it)
            }
        }
    }

    private fun initViews(root: View) {
        tvName = root.findViewById(R.id.tv_student_name)
        tvCompanyName = root.findViewById(R.id.tv_company_name)
        tvPhone = root.findViewById(R.id.tv_phone)
        tvVk = root.findViewById(R.id.tv_vk)
        tvTelegram = root.findViewById(R.id.tv_telegram)
        tvFacebook = root.findViewById(R.id.tv_facebook)
        tvAdditionalDescription = root.findViewById(R.id.tv_addition_description)
        btnEdit = root.findViewById(R.id.btn_edit_profile)
        pbLoading = root.findViewById(R.id.pb_loading)
        mcContainer = root.findViewById(R.id.cv_profile_container)
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
}
