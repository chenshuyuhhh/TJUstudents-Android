package com.chenshuyusc.tjustudents.Manage.Student.studentHome

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
import com.chenshuyusc.tjustudents.Entity.Student
import com.chenshuyusc.tjustudents.Manage.Student.StudentDetail.StuDetailActivity
import com.chenshuyusc.tjustudents.Manage.Student.studentSearch.StudentSearchActivity
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Util.BottomItem
import com.chenshuyusc.tjustudents.Util.ConstValue.ADD
import com.chenshuyusc.tjustudents.Util.ConstValue.UPDATE
import com.chenshuyusc.tjustudents.Util.ConstValue.DELETE
import com.chenshuyusc.tjustudents.Util.ConstValue.SID
import com.chenshuyusc.tjustudents.Util.ConstValue.KEY_STATUS
import es.dmoral.toasty.Toasty

class StudentActivity : AppCompatActivity(),
    StudentContract.StudentView {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemManager: ItemManager
    private val presenter = StudentPresenterImp(this)
    private val size: Int = 6
    private var page: Int = 1
    private var isLoad: Boolean = false

    private lateinit var search: ImageView
    private lateinit var add: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        bindID()
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        presenter.studentList(page, size)
//        initView()
        refreshData()
        loadMoreData()

        val intent = Intent()
        search.setOnClickListener {
            intent.setClass(this@StudentActivity, StudentSearchActivity::class.java)
            startActivity(intent)
        }

        add.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(KEY_STATUS,ADD)
            intent.putExtras(bundle)
            intent.setClass(this@StudentActivity, StuDetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        page = 1
        presenter.studentList(page, size)
    }

    private fun bindID() {
        swipeRefreshLayout = findViewById(R.id.student_swiperefresh)
        recyclerView = findViewById(R.id.student_rv)

        search = findViewById(R.id.student_search)
        add = findViewById(R.id.student_add)
    }

    private fun refreshData() {
        swipeRefreshLayout.setOnRefreshListener {
            page = 1
            presenter.studentList(page, size)
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
                    presenter.studentList(page, size)
                }
            }
        })
    }

    override fun showList(students: List<Student>) {
        recyclerView.withItems {
            repeat(students.size) { i ->
                addStudent(students[i]) { judge, sid ->
                    jump(judge, sid)
                }
            }
        }
        isLoad = false
        itemManager = (recyclerView.adapter as ItemAdapter).itemManager as ItemManager
    }

    override fun showMoreList(students: List<Student>) {
        repeat(students.size) { i ->
            itemManager.add(StudentItem(students[i]) { judge, sid ->
                jump(judge, sid)
            })
        }
        isLoad = false
    }

    override fun onNull() {
        Toasty.info(this, "抱歉，没有搜到内容 T_T ", Toast.LENGTH_LONG, true).show()
        //itemManager.add(BottomItem())
        isLoad = false
    }

    override fun onError(msg: String) {
        Toasty.error(this, msg, Toast.LENGTH_LONG, true).show()
        isLoad = false
    }

    override fun onBottom() {
        itemManager.add(BottomItem())
    }

    private fun jump(judge: String, sid: String) {
        val intent = Intent()
        val bundle = Bundle()
        if (judge == UPDATE) {
            bundle.putString(KEY_STATUS, UPDATE)
            bundle.putString(SID, sid)
            intent.putExtras(bundle)
            intent.setClass(this@StudentActivity, StuDetailActivity::class.java)
            startActivity(intent)
        } else if (judge == DELETE) {
            presenter.deleteStu(sid)
        }
    }
}