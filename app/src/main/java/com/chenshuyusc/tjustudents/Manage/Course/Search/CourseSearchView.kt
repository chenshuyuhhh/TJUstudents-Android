package com.chenshuyusc.tjustudents.Manage.Course.Search

import com.chenshuyusc.tjustudents.Entity.Course
import com.chenshuyusc.tjustudents.Entity.Selection

interface CourseSearchView {
    fun onNull(status: String)
    fun onError(msg: String)
    fun onSuccess(course: Course, selections: List<Selection>)
}