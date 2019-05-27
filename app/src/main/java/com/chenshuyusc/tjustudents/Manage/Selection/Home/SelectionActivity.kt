package com.chenshuyusc.tjustudents.Manage.Selection.Home

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.Toast
import cn.edu.twt.retrox.recyclerviewdsl.ItemAdapter
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.chenshuyusc.tjustudents.Entity.Selection
import com.chenshuyusc.tjustudents.Manage.Selection.Detail.SelectionDetailActivity
import com.chenshuyusc.tjustudents.Manage.Selection.Search.SelectSearchActivity
import com.chenshuyusc.tjustudents.Manage.Student.studentSearch.StudentSearchActivity
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Util.BottomItem
import com.chenshuyusc.tjustudents.Util.ConstValue
import com.google.gson.Gson
import es.dmoral.toasty.Toasty

class SelectionActivity : AppCompatActivity(),
    SelectionContract.SelectionView {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemManager: ItemManager
    private val presenter = SelectionPresenterImp(this)
    private val size: Int = 4
    private var page: Int = 1
    private var isLoad: Boolean = false

    private lateinit var search: ImageView
    private lateinit var add: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        bindID()
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        presenter.selectionList(page, size)
//        initView()
        refreshData()
        loadMoreData()


        val intent = Intent()
        search.setOnClickListener {
            intent.setClass(this@SelectionActivity, SelectSearchActivity::class.java)
            startActivity(intent)
        }
        add.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(ConstValue.KEY_STATUS, ConstValue.ADD)
            intent.putExtras(bundle)
            intent.setClass(this@SelectionActivity, SelectionDetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        page = 1
        presenter.selectionList(page, size)
    }

    private fun bindID() {
        swipeRefreshLayout = findViewById(R.id.selection_swiperefresh)
        recyclerView = findViewById(R.id.select_rv)

        search = findViewById(R.id.select_search)
        add = findViewById(R.id.select_add)
    }

    //    private fun initView() {
//        presenter.selectionList(page, size)
//    }
//
    private fun refreshData() {
        swipeRefreshLayout.setOnRefreshListener {
            page = 1
            presenter.selectionList(page, size)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun loadMoreData() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var lastVisibleItem: Int = 0
            var totalCount: Int = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                totalCount = linearLayoutManager.itemCount
                if (lastVisibleItem + 1 == totalCount && !isLoad) {
                    isLoad = true
                    ++page
                    presenter.selectionList(page, size)
                }
            }
        })
    }

    override fun showSelectionList(selections: List<Selection>) {
        recyclerView.withItems {
            repeat(selections.size) { i ->
                addSelection(selections[i]) { judge, selection ->
                    jump(judge, selection)
                }
            }
        }
        isLoad = false
        itemManager = (recyclerView.adapter as ItemAdapter).itemManager as ItemManager
    }

    override fun showMoreSelctionList(selections: List<Selection>) {
        repeat(selections.size) { i ->
            itemManager.add(SelectionItem(selections[i]) { judge, selection ->
                jump(judge, selection)
            })
        }
        isLoad = false
    }

    override fun onNullSelectionList() {
        itemManager.add(BottomItem())
        isLoad = false
    }

    override fun onError(msg: String) {
        Toasty.error(this, msg, Toast.LENGTH_LONG, true).show()
        isLoad = false
    }

    override fun onBottom() {
        itemManager.add(BottomItem())
    }

    private fun jump(judge: String, selection: Selection) {
        val intent = Intent()
        val bundle = Bundle()
        if (judge == ConstValue.UPDATE) {
            bundle.putString(ConstValue.KEY_STATUS, ConstValue.UPDATE)
            bundle.putString(ConstValue.SELECTION, Gson().toJson(selection))
            intent.putExtras(bundle)
            intent.setClass(this@SelectionActivity, SelectionDetailActivity::class.java)
            startActivity(intent)
        } else if (judge == ConstValue.DELETE) {
            presenter.deleteSelection(Gson().toJson(selection))
        }
    }
}