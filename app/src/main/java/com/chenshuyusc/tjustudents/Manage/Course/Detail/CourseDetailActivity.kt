package com.chenshuyusc.tjustudents.Manage.Course.Detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.chenshuyusc.tjustudents.Entity.Student
import com.chenshuyusc.tjustudents.Manage.Course.Search.CourseSearchModel
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory
import com.chenshuyusc.tjustudents.Entity.Course
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Util.ConstValue
import com.chenshuyusc.tjustudents.Util.ConstValue.CID
import com.chenshuyusc.tjustudents.Util.RefreshState
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch

class CourseDetailActivity : AppCompatActivity() {
    private lateinit var cid: EditText
    private lateinit var name: EditText
    private lateinit var credit: EditText
    private lateinit var suit: EditText
    private lateinit var cancle: EditText
    private lateinit var teacher: EditText

    private lateinit var sub: ImageView

    private lateinit var status: String
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_course_add)
        bindID()
        bundle = this.intent.extras
        status = bundle?.getString(ConstValue.KEY_STATUS).toString()
        if (status == ConstValue.UPDATE) {
            initView()
        }
        sub.setOnClickListener {
            when (status) {
                ConstValue.UPDATE -> {
                    update()
                }
                ConstValue.ADD -> {
                    add()
                }
            }
        }
    }

    private fun bindID() {
        cid = findViewById(R.id.select_course_et_cid)
        name = findViewById(R.id.select_course_et_cname)
        cancle = findViewById(R.id.select_course_et_cancle)
        suit = findViewById(R.id.select_course_et_grade)
        credit = findViewById(R.id.select_course_et_score)
        teacher = findViewById(R.id.select_course_et_teacher)

        sub = findViewById(R.id.course_sub)
    }

    /**
     * 为更新状态时，需要初始化
     */
    private fun initView() {
        val id = bundle?.getString(CID).toString()
        CourseSearchModel.searchCourseID(id) { refresh, course ->
            when (refresh) {
                is RefreshState.Failure -> {
                    Toasty.error(this, "${refresh.throwable}", Toast.LENGTH_LONG, true).show()
                }
                is RefreshState.Success -> {
                    if (course == null) {
                        Toasty.info(this, "抱歉，没有内容 T_T ", Toast.LENGTH_LONG, true).show()
                    } else {
                        cid.setText(course.cid)
                        name.setText(course.cname)
                        cancle.setText(course.cancelyear.toString())
                        credit.setText(course.credit.toString())
                        teacher.setText(course.teacher)
                        suit.setText(course.suitgrade.toString())
                    }
                }
            }
        }
    }

    private fun update() {
        GlobalScope.launch(Dispatchers.Main) {
            val gson = Gson()
            RetrofitFactory.api.updateCourse(gson.toJson(getcourse())).awaitAndHandle {
                Toasty.error(this@CourseDetailActivity, it.message.toString(), Toast.LENGTH_LONG, true).show()
            }?.let {
                Toasty.info(this@CourseDetailActivity, "更新成功", Toast.LENGTH_LONG, true).show()
            }
        }
    }

    private fun add() {
        GlobalScope.launch(Dispatchers.Main) {
            val gson = Gson()
            RetrofitFactory.api.addCourse(gson.toJson(getcourse())).awaitAndHandle {
                Toasty.error(this@CourseDetailActivity, it.message.toString(), Toast.LENGTH_LONG, true).show()
            }?.let {
                Toasty.info(this@CourseDetailActivity, "添加成功", Toast.LENGTH_LONG, true).show()
            }
        }
    }

    private fun getcourse(): Course {
        return Course(
            cancle.text.toString().toInt(),
            cid.text.toString(),
            name.text.toString(),
            credit.text.toString().toDouble(),
            suit.text.toString().toInt(),
            teacher.text.toString()
        )
    }
}