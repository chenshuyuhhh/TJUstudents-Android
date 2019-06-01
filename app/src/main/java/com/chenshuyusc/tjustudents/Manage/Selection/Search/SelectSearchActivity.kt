package com.chenshuyusc.tjustudents.Manage.Selection.Search

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.chenshuyusc.tjustudents.Entity.Selection
import com.chenshuyusc.tjustudents.Manage.Selection.Detail.SelectionDetailActivity
import com.chenshuyusc.tjustudents.Manage.Selection.Home.addSelection
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Util.ConstValue
import com.google.gson.Gson
import es.dmoral.toasty.Toasty

class SelectSearchActivity : AppCompatActivity(), SelecSearchView {

    private lateinit var stu: EditText
    private lateinit var cour: EditText

    private lateinit var sid: TextView
    private lateinit var sname: TextView
    private lateinit var cid: TextView
    private lateinit var cname: TextView

    private lateinit var search: ImageView
    private lateinit var back: ImageView

    private lateinit var rv: RecyclerView

    private lateinit var p: String
    private lateinit var keywords: String
    private lateinit var keywordc: String

    private val presenterImp = SelectSearchPresenterImp(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_search)
        bindID()
        rv.layoutManager = LinearLayoutManager(this)
        back.setOnClickListener { onBackPressed() }
        changeP()
        search.setOnClickListener {
            getKey()
            presenterImp.searchSelection(p, keywords, keywordc)
        }
    }

    private fun bindID() {
        stu = findViewById(R.id.et_sid)
        cour = findViewById(R.id.et_cname)

        sid = findViewById(R.id.tv_sid)
        sname = findViewById(R.id.tv_sname)
        cid = findViewById(R.id.tv_cid)
        cname = findViewById(R.id.tv_cname)

        rv = findViewById(R.id.student_search_rv)
        back = findViewById(R.id.student_search_iv_back)
        search = findViewById(R.id.student_search_iv_icon)
    }

    override fun onSuccess(selections: List<Selection>) {
        rv.withItems {
            repeat(selections.size) { i ->
                addSelection(selections[i]) { judge, selection ->
                    jump(judge, selection)
                }
            }
        }
    }

    override fun onNull() {
        rv.withItems { }
        Toasty.info(this, "哭了，小的没有搜到，嘤嘤嘤～", Toast.LENGTH_LONG, true).show()
    }

    override fun onError(msg: String) {
        rv.withItems { }
        Toasty.error(this, msg, Toast.LENGTH_LONG, true).show()
    }

    /**
     * 获得搜索信息
     */
    private fun getKey() {
        keywordc = cour.text.toString()
        keywords = stu.text.toString()
        // #646F72 被选中的颜色

        p = if (sid.currentTextColor == Color.parseColor("#646F72")) {
            if (cid.currentTextColor == sid.currentTextColor) {
                ConstValue.SIDCID
            } else {
                ConstValue.SIDCNAME
            }
        } else {
            if (cid.currentTextColor == sid.currentTextColor) {
                ConstValue.SNAMECNAME
            } else {
                ConstValue.SNAMECID
            }
        }
    }

    private fun jump(judge: String, selection: Selection) {
        val intent = Intent()
        val bundle = Bundle()
        if (judge == ConstValue.UPDATE) {
            bundle.putString(ConstValue.KEY_STATUS, ConstValue.UPDATE)
            bundle.putString(ConstValue.SELECTION, Gson().toJson(selection))
            intent.putExtras(bundle)
            intent.setClass(this@SelectSearchActivity, SelectionDetailActivity::class.java)
            startActivity(intent)
        } else if (judge == ConstValue.DELETE) {
            presenterImp.deleteSelection(Gson().toJson(selection), p, keywords, keywordc)
        }
    }

    private fun changeP() {
        sid.setOnClickListener {
            sid.setTextColor(Color.parseColor("#646F72"))
            sname.setTextColor(Color.parseColor("#BBC7CC"))
        }
        sname.setOnClickListener {
            sid.setTextColor(Color.parseColor("#BBC7CC"))
            sname.setTextColor(Color.parseColor("#646F72"))
        }
        cid.setOnClickListener {
            cid.setTextColor(Color.parseColor("#646F72"))
            cname.setTextColor(Color.parseColor("#BBC7CC"))
        }
        cname.setOnClickListener {
            cid.setTextColor(Color.parseColor("#BBC7CC"))
            cname.setTextColor(Color.parseColor("#646F72"))
        }
    }

}