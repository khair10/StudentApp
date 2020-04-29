package com.khair.appforitis.presentation.main.recalls

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
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
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

class RecallListFragment : MvpAppCompatFragment(), Navigation, RecallListContract.View {

    private lateinit var rvRecalls: RecyclerView
    private lateinit var fabAddRecall: FloatingActionButton
    private lateinit var searchView: SearchView
    private lateinit var tvEmpty: TextView
    private lateinit var recallListAdapter: RecallListAdapter
    private lateinit var pbLoading: ProgressBar

    @InjectPresenter
    lateinit var recallPresenter: RecallListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_recall_list, container, false)
        initViews(root)
        initViewListeners()
        return root
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
        activity?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViewListeners() {
        fabAddRecall.setOnClickListener{
            activity?.let {
                RecallCreationActivity.start(it)
            }
        }
    }

    private fun initViews(root: View) {
        tvEmpty = root.findViewById(R.id.tv_empty)
        rvRecalls = root.findViewById(R.id.rv_recalls)
        activity?.let {
            rvRecalls.addItemDecoration(VerticalItemDecoration(it, itemTopMargin, itemBottomMargin))
        }
        fabAddRecall = root.findViewById(R.id.fab_add_recall)
        recallListAdapter = RecallListAdapter(this@RecallListFragment)
        rvRecalls.apply {
            layoutManager = LinearLayoutManager(this@RecallListFragment.context)
            adapter = recallListAdapter
        }
        pbLoading = root.findViewById(R.id.pb_loading)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val mSearchMenuItem: MenuItem = menu.findItem(R.id.search)
        menu.findItem(R.id.filter).isVisible = true
        mSearchMenuItem.setOnActionExpandListener(
            object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    recallPresenter.getRecalls()
                    return true
                }
            }
        )
        searchView = mSearchMenuItem.actionView as SearchView
        searchView.clearFocus()
        searchView.queryHint = "Type recall name"
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
            R.id.menu_refresh -> recallPresenter.getRecalls()
            R.id.search -> item.expandActionView()
            R.id.filter_alpha -> recallPresenter.getRecallsSorted(SortOption.ALPHABET)
            R.id.filter_date -> recallPresenter.getRecallsSorted(SortOption.DATE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchManager: SearchManager? = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                recallPresenter.getRecalls(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
    }

    override fun navigate(id: Long) {
        activity?.let {
            RecallActivity.start(it, id)
        }
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

    override fun openLoginPage() {
        activity?.let {
            LoginActivity.start(it)
        }
    }
}