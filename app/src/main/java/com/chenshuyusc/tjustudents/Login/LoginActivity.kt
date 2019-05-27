package com.chenshuyusc.tjustudents.Login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.chenshuyusc.tjustudents.Manage.Home.HomeManageActivity
import com.chenshuyusc.tjustudents.R
//import com.chenshuyusc.tjustudents.Student.Home.HomeStudentActivity
import com.chenshuyusc.tjustudents.Util.ConstValue.manageStatus
import com.chenshuyusc.tjustudents.Util.ConstValue.studentStatus

class LoginActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var key: EditText
    private lateinit var manage: TextView
    private lateinit var student: TextView
    private lateinit var choosemg: ImageView
    private lateinit var choosestu: ImageView
    private lateinit var rememberline: ImageView
    private lateinit var remembersure: ImageView
    private lateinit var rememberText: TextView
    private lateinit var login: ImageView

    private lateinit var sp: SharedPreferences

    private var status: String = manageStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //去除标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)
        //window.statusBarColor = Color.parseColor("@colors/colorLoginBack")
        bindID()

        // 默认的是 manage 身份
        choosemg.visibility = View.VISIBLE
        choosestu.visibility = View.GONE

        // sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE)

        // 身份选择
        manage.setOnClickListener {
            status = manageStatus
            choosemg.visibility = View.VISIBLE
            choosestu.visibility = View.GONE
        }
        student.setOnClickListener {
            status = studentStatus
            choosestu.visibility = View.VISIBLE
            choosemg.visibility = View.GONE
        }

        // 根据身份 status 判断跳转到对应的首页
        val intent = Intent()
        login.setOnClickListener {
            if (status == manageStatus) {
                intent.setClass(this@LoginActivity,HomeManageActivity::class.java)
            }else{
               // intent.setClass(this@LoginActivity,HomeStudentActivity::class.java)
            }
            startActivity(intent)
        }
    }

    private fun bindID() {
        manage = findViewById(R.id.login_manage)
        student = findViewById(R.id.login_student)
        key = findViewById(R.id.login_key)
        username = findViewById(R.id.login_name)
        choosemg = findViewById(R.id.login_manage_icon)
        choosestu = findViewById(R.id.login_student_icon)
        rememberline = findViewById(R.id.login_remember_line)
        remembersure = findViewById(R.id.login_remember_sure)
        rememberText = findViewById(R.id.login_remember_text)
        login = findViewById(R.id.login_sure_icon)
    }
}