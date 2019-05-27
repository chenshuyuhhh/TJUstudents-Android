package com.chenshuyusc.tjustudents.Manage.Course.Home

import com.chenshuyusc.tjustudents.Entity.Selection
import com.chenshuyusc.tjustudents.Entity.*

class CourseContract {
    interface CourseView {
        fun showList(courses: List<Course>)
        fun showMoreList(courses: List<Course>)
        fun onNull()
        fun onError(msg: String)
        fun onBottom()
    }

    interface CoursePresenter {
        fun courseList(page: Int, size: Int)
    }
}