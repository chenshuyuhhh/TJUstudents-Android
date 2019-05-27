package com.chenshuyusc.tjustudents.Manage.Selection.Detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Util.ConstValue.UPDATE
import com.chenshuyusc.tjustudents.Entity.*
import com.chenshuyusc.tjustudents.Manage.Selection.Home.SelectionModel
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory.api
import com.chenshuyusc.tjustudents.Manage.Student.studentSearch.StuSearchModel
import com.chenshuyusc.tjustudents.Util.ConstValue
import com.chenshuyusc.tjustudents.Util.RefreshState
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch

class SelectionDetailActivity : AppCompatActivity() {

    // 标志这个 activity 是提交新的，还是修改原有的
    private lateinit var status: String
    private lateinit var bundle: Bundle

    private lateinit var sub: ImageView

    private lateinit var sid: EditText
    private lateinit var cid: EditText
    private lateinit var year: EditText
    private lateinit var score: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_course)
        bindID()
        bundle = this.intent.extras
        status = bundle?.getString(ConstValue.KEY_STATUS).toString()
        if (status == UPDATE) {
            initView()
        }
        sub.setOnClickListener {
            when (status) {
                UPDATE -> {
                    update()
                }
                ConstValue.ADD -> {
                    add()
                }
            }
        }
    }

    private fun bindID() {
        sub = findViewById(R.id.select_course_sub)
        sid = findViewById(R.id.select_course_et_sid)
        cid = findViewById(R.id.select_course_et_cid)
        year = findViewById(R.id.select_course_et_year)
        score = findViewById(R.id.select_course_et_score)
    }

    private fun update() {
        GlobalScope.launch(Dispatchers.Main) {
            val gson = Gson()
            RetrofitFactory.api.updateStudent(gson.toJson(getSelection())).awaitAndHandle {
                Toasty.error(this@SelectionDetailActivity, it.message.toString(), Toast.LENGTH_LONG, true).show()
            }?.let {
                Toasty.info(this@SelectionDetailActivity, "更新成功", Toast.LENGTH_LONG, true).show()
            }
        }
    }


    private fun add() {
        GlobalScope.launch(Dispatchers.Main) {
            val gson = Gson()
            RetrofitFactory.api.addSelection(gson.toJson(getSelection())).awaitAndHandle {
                Toasty.error(this@SelectionDetailActivity, it.message.toString(), Toast.LENGTH_LONG, true).show()
            }?.let {
                Toasty.info(this@SelectionDetailActivity, "添加成功", Toast.LENGTH_LONG, true).show()
            }
        }
    }

    private fun getSelection(): Selection {
        return Selection(
            cid.text.toString(),
            score.text.toString().toInt(),
            sid.text.toString(),
            year.text.toString().toInt()
        )
    }

    private fun initView() {
        val temp = bundle?.getString(ConstValue.SELECTION).toString()
        val selection = Gson().fromJson(temp,Selection::class.java)
        cid.setText(selection.cid)
        sid.setText(selection.sid)
        score.setText(selection.score.toString())
        year.setText(selection.year.toString())
    }
}