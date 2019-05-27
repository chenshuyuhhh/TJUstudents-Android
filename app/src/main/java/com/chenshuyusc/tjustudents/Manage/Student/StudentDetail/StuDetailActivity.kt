package com.chenshuyusc.tjustudents.Manage.Student.StudentDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.chenshuyusc.tjustudents.Manage.Student.studentSearch.StuSearchModel
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Util.ConstValue.ADD
import com.chenshuyusc.tjustudents.Util.ConstValue.DELETE
import com.chenshuyusc.tjustudents.Util.ConstValue.KEY_STATUS
import com.chenshuyusc.tjustudents.Util.ConstValue.SID
import com.chenshuyusc.tjustudents.Util.ConstValue.UPDATE
import com.chenshuyusc.tjustudents.Util.RefreshState
import es.dmoral.toasty.Toasty
import com.chenshuyusc.tjustudents.Entity.Student
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitService
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch

class StuDetailActivity : AppCompatActivity() {

    private lateinit var sid: EditText
    private lateinit var name: EditText
    private lateinit var gender: EditText
    private lateinit var age: EditText
    private lateinit var year: EditText
    private lateinit var classname: EditText

    private lateinit var sub: ImageView

    private lateinit var status: String
    private lateinit var bundle:Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)
        bindID()
        bundle = this.intent.extras
        status = bundle?.getString(KEY_STATUS).toString()
        if (status == UPDATE) {
            initView()
        }
        sub.setOnClickListener {
            when (status) {
                UPDATE -> {
                    update()
                }
                ADD -> {
                    add()
                }
            }
        }
    }

    private fun bindID() {
        sid = findViewById(R.id.student_et_cid)
        name = findViewById(R.id.student_et_cname)
        gender = findViewById(R.id.student_et_gender)
        age = findViewById(R.id.student_et_age)
        year = findViewById(R.id.student_et_year)
        classname = findViewById(R.id.student_et_classname)
        sub = findViewById(R.id.student_sub)
    }

    /**
     * 为更新状态时，需要初始化
     */
    private fun initView() {
        val id = bundle?.getString(SID).toString()
        StuSearchModel.getStudentById(id) { refresh, student ->
            when (refresh) {
                is RefreshState.Failure -> {
                    Toasty.error(this, "${refresh.throwable}", Toast.LENGTH_LONG, true).show()
                }
                is RefreshState.Success -> {
                    if (student == null) {
                        Toasty.info(this, "抱歉，没有搜到内容 T_T ", Toast.LENGTH_LONG, true).show()
                    } else {
                        sid.setText(student.sid)
                        name.setText(student.sname)
                        gender.setText(student.gender)
                        age.setText(student.age.toString())
                        year.setText(student.year.toString())
                        classname.setText(student.classname)
                    }
                }
            }
        }
    }

    private fun update() {
        GlobalScope.launch(Dispatchers.Main) {
            val gson = Gson()
            RetrofitFactory.api.updateStudent(gson.toJson(getstudent())).awaitAndHandle {
                Toasty.error(this@StuDetailActivity, it.message.toString(), Toast.LENGTH_LONG, true).show()
            }?.let {
                Toasty.info(this@StuDetailActivity, "更新成功", Toast.LENGTH_LONG, true).show()
            }
        }
    }

    private fun add() {
        GlobalScope.launch(Dispatchers.Main) {
            val gson = Gson()
            RetrofitFactory.api.addStudent(gson.toJson(getstudent())).awaitAndHandle {
                Toasty.error(this@StuDetailActivity, it.message.toString(), Toast.LENGTH_LONG, true).show()
            }?.let {
                Toasty.info(this@StuDetailActivity, "添加成功", Toast.LENGTH_LONG, true).show()
            }
        }
    }

    private fun getstudent(): Student {
        return Student(
            age.text.toString().toInt(),
            classname.text.toString(),
            gender.text.toString(),
            sid.text.toString(),
            name.text.toString(),
            year.text.toString().toInt()
        )
    }
}