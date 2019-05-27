package com.chenshuyusc.tjustudents.Manage.Home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.chenshuyusc.tjustudents.Manage.ClassName.ClassesActivity
import com.chenshuyusc.tjustudents.Manage.Course.Home.CourseActivity
import com.chenshuyusc.tjustudents.Manage.Selection.Home.SelectionActivity
import com.chenshuyusc.tjustudents.Manage.Student.studentHome.StudentActivity
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Util.ConstValue.CLASSNAME
import com.chenshuyusc.tjustudents.Util.ConstValue.YEAR

class HomeManageActivity : AppCompatActivity() {

    private lateinit var course: ImageView
    private lateinit var selection: ImageView
    private lateinit var student: ImageView
    private lateinit var recyclerView: RecyclerView

    private lateinit var logout: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_mg)
        bindID()
        jumpActivity()
        recyclerView.layoutManager = LinearLayoutManager(this)
        val intent = Intent()
        val bundle = Bundle()
        recyclerView.withItems {
            Classes("2018 级 1 班") {
                bundle.putString(CLASSNAME,"1")
                bundle.putString(YEAR,"2018")
                intent.putExtras(bundle)
                intent.setClass(this@HomeManageActivity,ClassesActivity::class.java)
                startActivity(intent)
            }
            Classes("2018 级 2 班") {
                bundle.putString(CLASSNAME,"2")
                bundle.putString(YEAR,"2018")
                intent.putExtras(bundle)
                intent.setClass(this@HomeManageActivity,ClassesActivity::class.java)
                startActivity(intent)
            }
            Classes("2017 级 1 班") {
                bundle.putString(CLASSNAME,"1")
                bundle.putString(YEAR,"2017")
                intent.putExtras(bundle)
                intent.setClass(this@HomeManageActivity,ClassesActivity::class.java)
                startActivity(intent)
            }
            Classes("2017级 2 班") {
                bundle.putString(CLASSNAME,"2")
                bundle.putString(YEAR,"2017")
                intent.putExtras(bundle)
                intent.setClass(this@HomeManageActivity,ClassesActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun bindID() {
        course = findViewById(R.id.home_mg_iv_course)
        selection = findViewById(R.id.home_mg_iv_select)
        student = findViewById(R.id.home_mg_iv_student)
        recyclerView = findViewById(R.id.home_mg_rv)
    }

    /**
     * 点击控件，跳转 activity
     */
    fun jumpActivity() {
        val intent = Intent()
        course.setOnClickListener {
            intent.setClass(this@HomeManageActivity, CourseActivity::class.java)
            startActivity(intent)
        }
        selection.setOnClickListener {
            intent.setClass(this@HomeManageActivity, SelectionActivity::class.java)
            startActivity(intent)
        }
        student.setOnClickListener {
            intent.setClass(this@HomeManageActivity, StudentActivity::class.java)
            startActivity(intent)
        }
    }


}