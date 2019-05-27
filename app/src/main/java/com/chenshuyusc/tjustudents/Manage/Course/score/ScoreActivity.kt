package com.chenshuyusc.tjustudents.Manage.Course.score

import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Util.ConstValue
import com.chenshuyusc.tjustudents.Entity.CourseInfo
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import com.idtk.smallchart.chart.CombineChart
import com.idtk.smallchart.data.BarData
import com.idtk.smallchart.data.CurveData
import com.idtk.smallchart.data.LineData
import com.idtk.smallchart.interfaces.iData.IBarLineCurveData
import com.lixs.charts.BarChart.LBarChartView
import com.lixs.charts.LineChartView
import com.lixs.charts.RadarChartView
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch
import java.text.DecimalFormat


class ScoreActivity : AppCompatActivity() {

    private lateinit var char: CombineChart
    private val mPointArrayList = ArrayList<PointF>()
    private val mCurveData = CurveData() // 曲线图
    private val mLineData = LineData() // 折线图
    private val mBarData = BarData() // 柱状图
    val mDataList = ArrayList<IBarLineCurveData>()

    private lateinit var chartView: RadarChartView
    private lateinit var lBarChartView: LBarChartView
    private lateinit var lineChartView: LineChartView

    private lateinit var id: String

    private lateinit var cid: TextView
    private lateinit var name: TextView
    private lateinit var teacher: TextView
    private lateinit var credit: TextView
    private lateinit var suite: TextView
    private lateinit var cancel: TextView
    private lateinit var avg: TextView
    private lateinit var cardView: CardView

    private lateinit var b6: TextView
    private lateinit var b6to7: TextView
    private lateinit var b7to8: TextView
    private lateinit var b8to9: TextView
    private lateinit var b9to10: TextView
    private lateinit var b10: TextView
    private lateinit var scorecardView: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_score)

        bindID()

        val bundle = this.intent.extras
        id = bundle?.getString(ConstValue.CID).toString()
        //char.isAnimated = true

        initView()
    }

    fun bindID() {
        cardView = findViewById(R.id.course_score_cv)
        cid = findViewById(R.id.course_tv_cid)
        name = findViewById(R.id.course_tv_name)
        teacher = findViewById(R.id.course_tv_teacher)
        credit = findViewById(R.id.course_tv_credit)
        suite = findViewById(R.id.course_tv_suitgrade)
        cancel = findViewById(R.id.course_tv_cancelyear)
        avg = findViewById(R.id.course_tv_avg)
        // char = findViewById(R.id.score_combineChart)

        chartView = findViewById(R.id.radarView)
        lBarChartView = findViewById(R.id.frameNewBase)
        lineChartView = findViewById(R.id.lineView)

        b6 = findViewById(R.id.belowsix)
        b7to8 = findViewById(R.id.seveneight)
        b6to7 = findViewById(R.id.sixseven)
        b8to9 = findViewById(R.id.eightnine)
        b9to10 = findViewById(R.id.nineten)
        b10 = findViewById(R.id.ten)
        scorecardView = findViewById(R.id.score_cv)
    }

    fun initView() {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.getscore(id).awaitAndHandle {
                //  char.visibility = View.GONE
                cardView.visibility = View.GONE
                scorecardView.visibility = View.GONE
                lBarChartView.visibility = View.GONE
                lineChartView.visibility = View.GONE
                Log.d("credit", it.message.toString())
                Toasty.error(this@ScoreActivity, it.message.toString(), Toast.LENGTH_LONG, true).show()
            }?.let { it ->
                val courseinfo = it.data
                if (it.data == null) {
                    // char.visibility = View.GONE
                    cardView.visibility = View.GONE
                    scorecardView.visibility = View.GONE
                    lBarChartView.visibility = View.GONE
                    lineChartView.visibility = View.GONE
                    Toasty.info(this@ScoreActivity, "莫得数据，哭了。。。", Toast.LENGTH_LONG, true).show()
                } else if (Math.abs(it.data.avg - 0) < 0.000001) {
                    val df = DecimalFormat("#.000")

                    cid.text = courseinfo.cid
                    name.text = courseinfo.cname
                    teacher.text = "教师：${courseinfo.teacher}"
                    credit.text = "学分：${courseinfo.credit.toString()}"
                    suite.text = "适合的年级：${courseinfo.suitgrade}"
                    cancel.text = "取消年份：${courseinfo.cancelyear}"
                    avg.text = "（ 课程平均分：${df.format(courseinfo.avg)} ）"

                    Toasty.info(this@ScoreActivity, "这个课程还没有考试", Toast.LENGTH_LONG, true).show()
                    scorecardView.visibility = View.GONE
                    lBarChartView.visibility = View.GONE
                    lineChartView.visibility = View.GONE
                    // char.visibility = View.GONE
                } else {
//                    mPointArrayList.apply {
//                        add(PointF(50f, courseinfo.under60 * 1.0f))
//                        add(PointF(60f, courseinfo.b6and7 * 1.0f))
//                        add(PointF(70f, courseinfo.b7and8 * 1.0f))
//                        add(PointF(80f, courseinfo.b8and9 * 1.0f))
//                        add(PointF(90f, courseinfo.b9and10 * 1.0f))
//                        add(PointF(100f, courseinfo.full * 1.0f))
//                    }
                    //initData()

                    val df = DecimalFormat("#.000")

                    cid.text = courseinfo.cid
                    name.text = courseinfo.cname
                    teacher.text = "教师：${courseinfo.teacher}"
                    credit.text = "学分：${courseinfo.credit.toString()}"
                    suite.text = "适合的年级：${courseinfo.suitgrade}"
                    cancel.text = "取消年份：${courseinfo.cancelyear}"
                    avg.text = "（ 课程平均分：${df.format(courseinfo.avg)} ）"

                    b6.text = "< 60 :${courseinfo.under60}"
                    b6to7.text = "[ 60, 70 ) : ${courseinfo.b6and7}"
                    b7to8.text = "[ 70, 80 ) : ${courseinfo.b7and8}"
                    b8to9.text = "[ 80, 90 ) : ${courseinfo.b8and9}"
                    b9to10.text = "[ 90, 100 ) : ${courseinfo.b9and10}"
                    b10.text = "100 : ${courseinfo.full}"

                    initRadarDatas(courseinfo)
                }
            }
        }
    }
//
//    fun initData() {
//
//        mBarData.apply {
//            value = mPointArrayList
//            color = Color.parseColor("#5FB4DB")
//            paintWidth = px2dp(this@ScoreActivity, 12f)
//            textSize = px2dp(this@ScoreActivity, 10f)
//        }
//
//        mLineData.apply {
//            value = mPointArrayList
//            color = Color.parseColor("#4F575F")
//            paintWidth = px2dp(this@ScoreActivity, 3f)
//            textSize = px2dp(this@ScoreActivity, 10f)
//        }
//
//        mCurveData.apply {
//            value = mPointArrayList
//            color = Color.parseColor("#12b7b7")
//            paintWidth = 3f
//            textSize = px2dp(this@ScoreActivity, 10f)
//        }
//
//        mDataList.add(mBarData)
//        mDataList.add(mLineData)
//        mDataList.add(mCurveData)
//
//        char.setDataList(mDataList)
//    }

    private fun initRadarDatas(courserInfo: CourseInfo) {
        val datas = ArrayList<Float>()
        val datab = ArrayList<Double>()
        val description = ArrayList<String>()

        var num: Int = 0
        courserInfo.apply {
            if (under60 > num) num = under60
            if (b6and7 > num) num = b6and7
            if (b7and8 > num) num = b7and8
            if (b8and9> num) num = b8and9
            if (b9and10 > num) num = b9and10
            if (full > num) num = full
        }
        datas.add(courserInfo.under60.toFloat() / num)
        datas.add(courserInfo.b6and7.toFloat() / num)
        datas.add(courserInfo.b7and8.toFloat() / num)
        datas.add(courserInfo.b8and9.toFloat() / num)
        datas.add(courserInfo.b9and10.toFloat() / num)
        datas.add(courserInfo.full.toFloat() / num)

        datab.apply {
            add(courserInfo.under60.toDouble())
            add(courserInfo.b6and7.toDouble())
            add(courserInfo.b7and8.toDouble())
            add(courserInfo.b8and9.toDouble())
            add(courserInfo.b9and10.toDouble())
            add(courserInfo.full.toDouble())
        }

        description.add("<60")
        description.add("[60,70)")
        description.add("[70,80)")
        description.add("[80,90)")
        description.add("[90,100)")
        description.add("100")

        //点击动画开启
        chartView.setCanClickAnimation(true)
        chartView.setDatas(datas)
        chartView.setPolygonNumbers(6)
        chartView.setDescriptions(description)

        lBarChartView.setDatas(datab,description,true)

        lineChartView.setDatas(datab,description)
    }
}