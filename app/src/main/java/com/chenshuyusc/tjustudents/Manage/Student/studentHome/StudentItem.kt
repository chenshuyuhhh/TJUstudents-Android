package com.chenshuyusc.tjustudents.Manage.Student.studentHome

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Entity.Student
import com.chenshuyusc.tjustudents.Util.ConstValue.DELETE
import com.chenshuyusc.tjustudents.Util.ConstValue.UPDATE

class StudentItem(val student: Student,val jump: (String,String) -> Unit) : Item {
    companion object Controller : ItemController {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {

            holder as ViewHolder
            item as StudentItem

            holder.apply {
                if (item.student.gender == "女") {
                    woman.visibility = View.VISIBLE
                    man.visibility = View.INVISIBLE
                } else {
                    woman.visibility = View.INVISIBLE
                    man.visibility = View.VISIBLE
                }
                sid.text = "( ID: ${item.student.sid} )"
                name.text = item.student.sname
                classname.text = "[ Class: ${item.student.classname} ]"
                age.text = "入学年龄：${item.student.age.toString()}岁"
                year.text = "入学年份：${item.student.year.toString()} 年"


            }
            holder.delete.setOnClickListener {
                item.jump(DELETE,item.student.sid)
            }

            holder.modify.setOnClickListener {
                item.jump(UPDATE,item.student.sid)
            }
        }

        private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val woman = itemView.findViewById<ImageView>(R.id.student_iv_woman)
            val man = itemView.findViewById<ImageView>(R.id.student_iv_man)
            val sid = itemView.findViewById<TextView>(R.id.student_tv_id)
            val name = itemView.findViewById<TextView>(R.id.student_name)
            val classname = itemView.findViewById<TextView>(R.id.student_tv_classname)
            val age = itemView.findViewById<TextView>(R.id.student_tv_age)
            val year = itemView.findViewById<TextView>(R.id.student_tv_year)
            val delete = itemView.findViewById<ImageView>(R.id.student_iv_delete)
            val modify = itemView.findViewById<ImageView>(R.id.student_iv_edit)
        }
    }

    override val controller: ItemController
        get() = StudentItem
}

fun MutableList<Item>.addStudent(student: Student,jump: (String,String) -> Unit) = add(
    StudentItem(student,jump)
)