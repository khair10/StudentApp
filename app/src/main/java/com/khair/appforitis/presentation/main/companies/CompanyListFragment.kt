package com.khair.appforitis.presentation.main.companies

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SearchView
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.khair.appforitis.R
import com.khair.appforitis.itemBottomMargin
import com.khair.appforitis.itemTopMargin
import com.khair.appforitis.presentation.base.Navigation
import com.khair.appforitis.presentation.base.VerticalItemDecoration
import com.khair.appforitis.presentation.company.CompanyActivity
import com.khair.appforitis.presentation.companycreation.CompanyCreationActivity
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.main.companies.dto.CompanyPreviewDto
import com.khair.appforitis.presentation.main.companies.util.CompanyListAdapter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class CompanyListFragment : MvpAppCompatFragment(), Navigation, CompanyListContract.View {

    private lateinit var rvCompanies: RecyclerView
    private lateinit var fabAddCompany: FloatingActionButton
    private lateinit var searchView: SearchView
    private lateinit var tvEmpty: TextView
    private lateinit var companyListAdapter: CompanyListAdapter
    private lateinit var pbLoading: ProgressBar

    @InjectPresenter
    lateinit var companyListPresenter: CompanyListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_company_list, container, false)
        initViews(root)
        initViewListeners()
        return root
    }

    override fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        rvCompanies.visibility = View.GONE
    }

    override fun hideLoading() {
        pbLoading.visibility = View.GONE
        rvCompanies.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        activity?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViewListeners() {
        fabAddCompany.setOnClickListener{
            activity?.let {
                CompanyCreationActivity.start(it)
            }
        }
    }

    private fun initViews(root: View) {
        tvEmpty = root.findViewById(R.id.tv_empty)
        rvCompanies = root.findViewById(R.id.rv_companies)
        activity?.let {
            rvCompanies.addItemDecoration(VerticalItemDecoration(it, itemTopMargin, itemBottomMargin))
        }
        fabAddCompany = root.findViewById(R.id.fab_add_company)
        companyListAdapter = CompanyListAdapter(this@CompanyListFragment)
        rvCompanies.apply {
            layoutManager = LinearLayoutManager(this@CompanyListFragment.context)
            adapter = companyListAdapter
        }
        pbLoading = root.findViewById(R.id.pb_loading)
    }

    override fun navigate(id: Long) {
        activity?.let {
            CompanyActivity.start(it, id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val mSearchMenuItem: MenuItem = menu.findItem(R.id.search)
        mSearchMenuItem.setOnActionExpandListener(
            object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    companyListPresenter.getCompanies()
                    return true
                }
            }
        )
        searchView = mSearchMenuItem.actionView as SearchView
        searchView.clearFocus()
        searchView.queryHint = "Type company name"
        val editText = searchView.findViewById<EditText>(R.id.search_src_text)
        val typedValue = TypedValue()
        val theme = context!!.theme
        theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true)
        @ColorInt val color = typedValue.data
        ColorUtils.setAlphaComponent(color, 99)
        editText.setTextColor(color)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_refresh -> companyListPresenter.getCompanies()
            R.id.search -> item.expandActionView()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchManager: SearchManager? = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                companyListPresenter.getCompaniesWithQuery(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
    }

    override fun showCompanies(companies: List<CompanyPreviewDto>) {
        tvEmpty.visibility = View.GONE
        rvCompanies.visibility = View.VISIBLE
        companyListAdapter.companyPreviewDtos = companies
    }

    override fun showEmpty() {
        rvCompanies.visibility = View.GONE
        tvEmpty.visibility = View.VISIBLE
    }

    override fun openLoginPage() {
        activity?.let {
            LoginActivity.start(it)
        }
    }
}