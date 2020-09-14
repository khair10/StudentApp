package com.khair.appforitis.presentation.recallsforcompany

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.khair.appforitis.R
import com.khair.appforitis.itemBottomMargin
import com.khair.appforitis.itemTopMargin
import com.khair.appforitis.presentation.base.Navigation
import com.khair.appforitis.presentation.base.VerticalItemDecoration
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.main.recalls.dto.RecallPreviewDto
import com.khair.appforitis.presentation.main.recalls.util.RecallListAdapter
import com.khair.appforitis.presentation.recall.RecallActivity
import com.khair.appforitis.presentation.recallcreation.RecallCreationActivity
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class CompanyRecallsActivity : MvpAppCompatActivity(), Navigation, CompanyRecallListContract.View {

    companion object {
        const val ID = "ID"
        fun start(context: Context, company: String){
            val intent = Intent(context, CompanyRecallsActivity::class.java)
            intent.putExtra(ID, company)
            context.startActivity(intent)
        }
    }

    private lateinit var rvRecalls: RecyclerView
    private lateinit var fabAddRecall: FloatingActionButton
    private lateinit var tvEmpty: TextView
    private lateinit var recallListAdapter: RecallListAdapter
    private lateinit var pbLoading: ProgressBar

    @InjectPresenter
    lateinit var recallPresenter: CompanyRecallListPresenter

    @ProvidePresenter
    fun provideCompanyRecallListPresenter(): CompanyRecallListPresenter{
        return CompanyRecallListPresenter(intent.getStringExtra(ID))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_recalls)
        initToolbar()
        initViews()
        initViewListeners()
    }

    override fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        rvRecalls.visibility = View.GONE
    }

    override fun hideLoading() {
        pbLoading.visibility = View.GONE
        rvRecalls.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initViewListeners() {
        fabAddRecall.setOnClickListener{
            RecallCreationActivity.start(this)
        }
    }

    private fun initViews() {
        tvEmpty = findViewById(R.id.tv_empty)
        rvRecalls = findViewById(R.id.rv_recalls)
        rvRecalls.addItemDecoration(VerticalItemDecoration(this, itemTopMargin, itemBottomMargin))
        fabAddRecall = findViewById(R.id.fab_add_recall)
        recallListAdapter = RecallListAdapter(this)
        rvRecalls.apply {
            layoutManager = LinearLayoutManager(this@CompanyRecallsActivity)
            adapter = recallListAdapter
        }
        pbLoading = findViewById(R.id.pb_loading)
    }

    override fun navigate(id: Long) {
        RecallActivity.start(this, id)
    }

    override fun showRecalls(recalls: List<RecallPreviewDto>) {
        tvEmpty.visibility = View.GONE
        rvRecalls.visibility = View.VISIBLE
        recallListAdapter.recallPreviewDtos = recalls
    }

    override fun showEmpty() {
        rvRecalls.visibility = View.GONE
        tvEmpty.visibility = View.VISIBLE
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    override fun openLoginPage() {
        LoginActivity.start(this)
    }
}
