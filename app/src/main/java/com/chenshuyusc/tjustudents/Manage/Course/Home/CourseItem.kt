package com.chenshuyusc.tjustudents.Manage.Course.Home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Entity.*
import com.chenshuyusc.tjustudents.Util.ConstValue.DELETE
import com.chenshuyusc.tjustudents.Util.ConstValue.SCORE
import com.chenshuyusc.tjustudents.Util.ConstValue.UPDATE

class CourseItem(val course: Course, val jump: (String, String) -> Unit) : Item {

    companion object Controller : ItemController {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {

            holder as ViewHolder
            item as CourseItem

            holder.apply {
                cid.text = item.course.cid
                name.text = item.course.cname
                teacher.text = "教师：${item.course.teacher}"
                credit.text = "学分：${item.course.credit}"
                suite.text = "适合的年级：${item.course.suitgrade}"
                cancel.text = "取消年份：${item.course.cancelyear}"
            }

            holder.edit.setOnClickListener {
                item.jump(UPDATE, item.course.cid)
            }
            holder.delete.setOnClickListener {
                item.jump(DELETE, item.course.cid)
            }
            holder.score.setOnClickListener {
                item.jump(SCORE, item.course.cid)
            }
        }

        private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cid = itemView.findViewById<TextView>(R.id.course_tv_cid)
            val name = itemView.findViewById<TextView>(R.id.course_tv_name)
            val teacher = itemView.findViewById<TextView>(R.id.course_tv_teacher)
            val credit = itemView.findViewById<TextView>(R.id.course_tv_credit)
            val suite = itemView.findViewById<TextView>(R.id.course_tv_suitgrade)
            val cancel = itemView.findViewById<TextView>(R.id.course_tv_cancelyear)

            val delete = itemView.findViewById<ImageView>(R.id.course_iv_delete)
            val edit = itemView.findViewById<ImageView>(R.id.course_iv_edit)
            val score = itemView.findViewById<ImageView>(R.id.course_iv_score)
        }
    }

    override val controller: ItemController
        get() = CourseItem
}

fun MutableList<Item>.addCourse(course: Course, jump: (String, String) -> Unit) = add(
    CourseItem(course, jump)
)