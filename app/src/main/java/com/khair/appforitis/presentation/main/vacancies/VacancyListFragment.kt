package com.khair.appforitis.presentation.main.vacancies

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
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.khair.appforitis.R
import com.khair.appforitis.itemBottomMargin
import com.khair.appforitis.itemTopMargin
import com.khair.appforitis.presentation.base.Navigation
import com.khair.appforitis.presentation.base.VerticalItemDecoration
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.main.vacancies.dto.VacancyPreviewDto
import com.khair.appforitis.presentation.main.vacancies.util.VacancyListAdapter
import com.khair.appforitis.presentation.vacancy.VacancyActivity
import com.khair.appforitis.presentation.vacancycreation.VacancyCreationActivity


class VacancyListFragment : Fragment(), Navigation, VacancyListContract.View {

    private lateinit var rvVacancies: RecyclerView
    private lateinit var fabAddVacancy: FloatingActionButton
    private lateinit var searchView: SearchView
    private lateinit var tvEmpty: TextView
    private lateinit var pbLoading: ProgressBar
    private lateinit var vacancyListAdapter: VacancyListAdapter
    private lateinit var vacancyPresenter: VacancyListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_vacancy_list, container, false)
        initViews(root)
        initViewListeners()
        initPresenter()
        getVacancyListFromRepository()
        return root
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
                    vacancyPresenter.getVacancies()
                    return true
                }
            }
        )
        searchView = mSearchMenuItem.actionView as SearchView
        searchView.clearFocus()
        searchView.queryHint = "Type vacancy name"
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
            R.id.search -> item.expandActionView()
            R.id.menu_refresh -> vacancyPresenter.getVacancies()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchManager: SearchManager? = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                vacancyPresenter.getVacancies(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
    }

    override fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        rvVacancies.visibility = View.GONE
    }

    override fun hideLoading() {
        pbLoading.visibility = View.GONE
        rvVacancies.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        activity?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViewListeners() {
        fabAddVacancy.setOnClickListener{
            activity?.let {
                VacancyCreationActivity.start(it)
            }
        }
    }

    private fun initPresenter() {
        vacancyPresenter = VacancyListPresenter(this)
    }

    private fun initViews(root: View) {
        tvEmpty = root.findViewById(R.id.tv_empty)
        fabAddVacancy = root.findViewById(R.id.fab_add_vacancy)
        rvVacancies = root.findViewById(R.id.rv_vacancies)
        activity?.let {
            rvVacancies.addItemDecoration(VerticalItemDecoration(it, itemTopMargin, itemBottomMargin))
        }
        vacancyListAdapter = VacancyListAdapter(this@VacancyListFragment)
        rvVacancies.apply {
            layoutManager = LinearLayoutManager(this@VacancyListFragment.context)
            adapter = vacancyListAdapter
        }
        pbLoading = root.findViewById(R.id.pb_loading)
    }

    override fun navigate(id: Long) {
        activity?.let {
            VacancyActivity.start(it, id)
        }
    }

    private fun getVacancyListFromRepository() {
        vacancyPresenter.getVacancies()
    }

    override fun showVacancies(vacancies: List<VacancyPreviewDto>) {
        tvEmpty.visibility = View.GONE
        rvVacancies.visibility = View.VISIBLE
        vacancyListAdapter.vacancyPreviewDtos = vacancies
    }

    override fun showEmpty() {
        rvVacancies.visibility = View.GONE
        tvEmpty.visibility = View.VISIBLE
    }

    override fun openLoginPage() {
        activity?.let {
            LoginActivity.start(it)
        }
    }
}