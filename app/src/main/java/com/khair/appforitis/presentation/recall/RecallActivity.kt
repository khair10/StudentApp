package com.khair.appforitis.presentation.recall

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.khair.appforitis.R
import com.khair.appforitis.domain.entity.Recall
import com.khair.appforitis.presentation.base.ConnectionManager
import com.khair.appforitis.presentation.company.CompanyActivity
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.profile.ProfileActivity
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class RecallActivity : MvpAppCompatActivity(), RecallContract.View {

    companion object {
        const val ID = "ID"
        fun start(context: Context, id: Long){
            val intent = Intent(context, RecallActivity::class.java)
            intent.putExtra(ID, id)
            context.startActivity(intent)
        }
    }

    var studentId: Long = -1
    var companyId: Long = -1

    private lateinit var toolbar: MaterialToolbar
    private lateinit var tvStudentName: TextView
    private lateinit var tvCompanyName: TextView
    private lateinit var tvCompanyRating: TextView
    private lateinit var rbCompanyRating: RatingBar
    private lateinit var tvRecallDescription: TextView
    private lateinit var ivVkontakte: ImageView
    private lateinit var ivTelegram: ImageView
    private lateinit var ivLinkedIn: ImageView
    private lateinit var ivFacebook: ImageView
    private lateinit var pbLoading: ProgressBar
    private lateinit var mcContainer: MaterialCardView

    @InjectPresenter
    lateinit var recallPresenter: RecallPresenter

    @ProvidePresenter
    fun provideRecallPresenter(): RecallPresenter {
        return RecallPresenter(intent.getLongExtra(ID, 0))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recall)
        initToolbar()
        initViews()
        initViewListeners()
    }

    override fun showRecall(recall: Recall) {
        recall.run {
            studentId = student.id
            companyId = company.id
            tvStudentName.text = student.name
            tvCompanyName.text = company.name
            tvCompanyRating.text = rating.toString()
            rbCompanyRating.rating = rating
            tvRecallDescription.text = information
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
        tvStudentName = findViewById(R.id.tv_student_name)
        tvCompanyName = findViewById(R.id.tv_company_name)
        tvCompanyRating = findViewById(R.id.tv_company_rating)
        rbCompanyRating = findViewById(R.id.rb_company_rating)
        tvRecallDescription = findViewById(R.id.tv_recall)
//        ivVkontakte = findViewById(R.id.iv_vk)
//        ivTelegram = findViewById(R.id.iv_telegram)
//        ivLinkedIn = findViewById(R.id.iv_linked_in)
//        ivFacebook = findViewById(R.id.iv_facebook)
        pbLoading = findViewById(R.id.pb_loading)
        mcContainer = findViewById(R.id.cv_recall_container)
    }

    private fun initViewListeners() {
        tvStudentName.setOnClickListener {
            if(ConnectionManager.hasConnection(this) && studentId != -1L){
                ProfileActivity.start(this, studentId)
            } else {
                Toast.makeText(this, "Проверьте соединение с сетью", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        tvCompanyName.setOnClickListener {
            if(companyId != -1L)
                CompanyActivity.start(this, companyId)
            // TODO что если нет Айди
        }
    }
}
