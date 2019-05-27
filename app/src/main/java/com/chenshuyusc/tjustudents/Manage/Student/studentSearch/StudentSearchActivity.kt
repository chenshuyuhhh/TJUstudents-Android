package com.chenshuyusc.tjustudents.Manage.Student.studentSearch

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import cn.edu.twt.retrox.recyclerviewdsl.ItemAdapter
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.chenshuyusc.tjustudents.Entity.Student
import com.chenshuyusc.tjustudents.Entity.Selection
import com.chenshuyusc.tjustudents.Manage.Selection.Detail.SelectionDetailActivity
import com.chenshuyusc.tjustudents.Manage.Selection.Home.addSelection
import com.chenshuyusc.tjustudents.Manage.Student.StudentDetail.StuDetailActivity
import com.chenshuyusc.tjustudents.Manage.Student.studentHome.addStudent
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Util.ConstValue
import com.chenshuyusc.tjustudents.Util.ConstValue.BLANK
import es.dmoral.toasty.Toasty
import com.chenshuyusc.tjustudents.Util.ConstValue.DELETE
import com.chenshuyusc.tjustudents.Util.ConstValue.KEY_STATUS
import com.chenshuyusc.tjustudents.Util.ConstValue.SID
import com.chenshuyusc.tjustudents.Util.ConstValue.UPDATE
import com.google.gson.Gson


class StudentSearchActivity : AppCompatActivity(), StuSearchView {

    private lateinit var back: ImageView
    private lateinit var input: EditText
    private lateinit var search: ImageView
    private lateinit var keyword: String

    private lateinit var itemManager: ItemManager

    private val presenter: StuSearchPresenter = StuSearchPresenter(this)

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_search)
        bindID()
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        search.setOnClickListener {
            getKey()
            presenter.getSearch(keyword)
        }
        back.setOnClickListener {
            onBackPressed()
        }
    }

    fun bindID() {
        back = findViewById(R.id.student_search_iv_back)
        input = findViewById(R.id.student_search_et_input)
        search = findViewById(R.id.student_search_iv_icon)
        recyclerView = findViewById(R.id.student_search_rv)
    }

    // 获得搜索关键字
    fun getKey() {
        keyword = input.text.toString()
    }

    override fun onSuccess(students: List<Student>, map: HashMap<String, List<Selection>>) {
        recyclerView.withItems {
            repeat(students.size) { i ->
                addStudent(students[i]) { judge, sid ->
                    jump(judge, sid)
                }
                val templist = map[students[i].sid].orEmpty()
                repeat(templist.size) { j ->
                    addSelection(templist[j]) { judge, selection ->
                        jump(judge, selection)
                    }
                }
            }
        }
        itemManager = (recyclerView.adapter as ItemAdapter).itemManager as ItemManager
    }

    override fun onNull(status: String) {
        if (status==BLANK){
            recyclerView.withItems { }
            Toasty.info(this, "哭了，小的没有搜到，嘤嘤嘤～ ", Toast.LENGTH_LONG, true).show()

        }else{
            Toasty.info(this, "哭了，这个学生没有选课，呜呜呜～ ", Toast.LENGTH_LONG, true).show()
        }
    }

    override fun onError(msg: String) {
        Toasty.error(this, msg, Toast.LENGTH_LONG, true).show()
    }

    private fun jump(judge: String, sid: String) {
        val intent = Intent()
        val bundle = Bundle()
        if (judge == UPDATE) {
            bundle.putString(KEY_STATUS, UPDATE)
            bundle.putString(SID, sid)
            intent.putExtras(bundle)
            intent.setClass(this@StudentSearchActivity, StuDetailActivity::class.java)
            startActivity(intent)
        } else if (judge == DELETE) {
            presenter.deleteStu(sid, keyword)
        }
    }

    private fun jump(judge: String, selection: Selection) {
        val intent = Intent()
        val bundle = Bundle()
        if (judge == UPDATE) {
            bundle.putString(KEY_STATUS, UPDATE)
            bundle.putString(ConstValue.SELECTION, Gson().toJson(selection))
            intent.putExtras(bundle)
            intent.setClass(this@StudentSearchActivity, SelectionDetailActivity::class.java)
            startActivity(intent)
        } else if (judge == DELETE) {
            presenter.deleteSelection(Gson().toJson(selection), keyword)
        }
    }
}