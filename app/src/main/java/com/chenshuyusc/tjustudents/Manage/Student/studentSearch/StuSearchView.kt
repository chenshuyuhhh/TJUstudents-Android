package com.chenshuyusc.tjustudents.Manage.Student.studentSearch

import com.chenshuyusc.tjustudents.Entity.Student
import com.chenshuyusc.tjustudents.Entity.Selection

interface StuSearchView {
    fun onSuccess(students: List<Student>, selections: HashMap<String, List<Selection>>)
    fun onNull(status: String)
    fun onError(msg: String)
}