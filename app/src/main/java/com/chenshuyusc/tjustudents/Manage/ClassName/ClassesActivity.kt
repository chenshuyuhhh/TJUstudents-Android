package com.chenshuyusc.tjustudents.Manage.ClassName

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory
import com.chenshuyusc.tjustudents.Manage.Student.StudentDetail.StuDetailActivity
import com.chenshuyusc.tjustudents.Manage.Student.studentHome.StudentModel
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Util.ConstValue
import com.chenshuyusc.tjustudents.Util.ConstValue.CLASSNAME
import com.chenshuyusc.tjustudents.Util.ConstValue.YEAR
import com.chenshuyusc.tjustudents.Util.RefreshState
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class ClassesActivity : AppCompatActivity() {

    private lateinit var agvscore: TextView
    private lateinit var recyclerView: RecyclerView
    private var classavg: Double = 0.0
    private lateinit var classname: String
    private var year: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classname)
        agvscore = findViewById(R.id.class_avg)
        recyclerView = findViewById(R.id.class_rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val bundle = this.intent.extras
        year = bundle.getString(YEAR).toString().toInt()
        classname = bundle.getString(CLASSNAME).toString()
        getData()
    }

    fun getData() {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.classAvg(classname, year).awaitAndHandle {
                Toasty.error(this@ClassesActivity, it.message.toString(), Toast.LENGTH_LONG, true).show()
            }?.let {
                val list = it.data
                if (list.isEmpty()) {
                    Toasty.info(this@ClassesActivity, "这个班没有学生，太惨了", Toast.LENGTH_LONG, true).show()
                } else {
                    recyclerView.withItems {
                        repeat(list.size) { i ->
                            classavg += list[i].avgScore
                            addStudentInfo(list[i]) { judge, id ->
                                jump(judge, id)
                            }
                        }
                        classavg /= list.size
                        val df = DecimalFormat("#.000")
                        agvscore.text = "🤓 ${year}级${classname}班的平均加权成绩为：${df.format(classavg)}"
                    }
                }
            }
        }
    }

    private fun jump(judge: String, sid: String) {
        val intent = Intent()
        val bundle = Bundle()
        if (judge == ConstValue.UPDATE) {
            bundle.putString(ConstValue.KEY_STATUS, ConstValue.UPDATE)
            bundle.putString(ConstValue.SID, sid)
            intent.putExtras(bundle)
            intent.setClass(this@ClassesActivity, StuDetailActivity::class.java)
            startActivity(intent)
        } else if (judge == ConstValue.DELETE) {
            StudentModel.deleteStu(sid) { refreshstate, studentpage ->
                when (refreshstate) {
                    is RefreshState.Success -> {
                        getData()
                    }
                    is RefreshState.Failure -> {
                        Toasty.error(this@ClassesActivity, "${refreshstate.throwable}", Toast.LENGTH_LONG, true).show()
                    }
                }
            }
        }
    }
}