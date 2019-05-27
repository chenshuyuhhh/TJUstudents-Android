package com.chenshuyusc.tjustudents.Manage.Course.Home

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
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Util.BottomItem
import es.dmoral.toasty.Toasty
import com.chenshuyusc.tjustudents.Entity.*
import com.chenshuyusc.tjustudents.Manage.Course.Detail.CourseDetailActivity
import com.chenshuyusc.tjustudents.Manage.Course.Search.CourseSearchActivity
import com.chenshuyusc.tjustudents.Manage.Course.score.ScoreActivity
import com.chenshuyusc.tjustudents.Manage.Student.StudentDetail.StuDetailActivity
import com.chenshuyusc.tjustudents.Manage.Student.studentSearch.StudentSearchActivity
import com.chenshuyusc.tjustudents.Util.ConstValue
import com.chenshuyusc.tjustudents.Util.ConstValue.CID
import com.chenshuyusc.tjustudents.Util.ConstValue.DELETE
import com.chenshuyusc.tjustudents.Util.ConstValue.KEY_STATUS
import com.chenshuyusc.tjustudents.Util.ConstValue.SCORE
import com.chenshuyusc.tjustudents.Util.ConstValue.UPDATE
import com.google.gson.Gson

class CourseActivity : AppCompatActivity(), CourseContract.CourseView {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemManager: ItemManager
    private val presenter = CoursePresenterImp(this)
    private val size: Int = 6
    private var page: Int = 1
    private var isLoad: Boolean = false

    private lateinit var search: ImageView
    private lateinit var add: ImageView
    private lateinit var score: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        bindID()
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        presenter.courseList(page, size)
//        initView()
        refreshData()
        loadMoreData()

        val intent = Intent()
        search.setOnClickListener {
            intent.setClass(this@CourseActivity, CourseSearchActivity::class.java)
            startActivity(intent)
        }

        add.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(KEY_STATUS, ConstValue.ADD)
            intent.putExtras(bundle)
            intent.setClass(this@CourseActivity, CourseDetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        page = 1
        presenter.courseList(page, size)
    }

    private fun bindID() {
        swipeRefreshLayout = findViewById(R.id.course_swiperefresh)
        recyclerView = findViewById(R.id.course_rv)

        search = findViewById(R.id.course_search)
        add = findViewById(R.id.course_add)
    }

    private fun refreshData() {
        swipeRefreshLayout.setOnRefreshListener {
            page = 1
            presenter.courseList(page, size)
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
                    presenter.courseList(page, size)
                }
            }
        })
    }

    override fun showList(courses: List<Course>) {
        recyclerView.withItems {
            repeat(courses.size) { i ->
                addCourse(courses[i]) { judge, cid ->
                    jump(judge, cid)
                }
            }
        }
        isLoad = false
        itemManager = (recyclerView.adapter as ItemAdapter).itemManager as ItemManager
    }

    override fun showMoreList(courses: List<Course>) {
        repeat(courses.size) { i ->
            itemManager.add(CourseItem(courses[i]) { judge, cid ->
                jump(judge, cid)
            })
        }
        isLoad = false
    }

    override fun onNull() {
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

    private fun jump(judge: String, cid: String) {
        val intent = Intent()
        val bundle = Bundle()
        when (judge) {
            UPDATE -> {
                bundle.putString(KEY_STATUS, UPDATE)
                bundle.putString(CID, cid)
                intent.putExtras(bundle)
                intent.setClass(this@CourseActivity, CourseDetailActivity::class.java)
                startActivity(intent)
            }
            DELETE -> presenter.deleteSelection(cid)
            SCORE -> {
                bundle.putString(CID, cid)
                intent.putExtras(bundle)
                intent.setClass(this@CourseActivity, ScoreActivity::class.java)
                startActivity(intent)
            }
        }
    }
}