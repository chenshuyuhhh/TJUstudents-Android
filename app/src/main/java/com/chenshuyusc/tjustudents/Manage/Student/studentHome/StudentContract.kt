package com.chenshuyusc.tjustudents.Manage.Student.studentHome

import com.chenshuyusc.tjustudents.Entity.Course
import com.chenshuyusc.tjustudents.Entity.Student

class StudentContract {
    interface StudentView {
        fun showList(students: List<Student>)
        fun showMoreList(students: List<Student>)
        fun onNull()
        fun onError(msg: String)
        fun onBottom()
    }

    interface StudentPresenter {
        fun studentList(page: Int, size: Int)
        fun deleteStu(id: String)
    }
}