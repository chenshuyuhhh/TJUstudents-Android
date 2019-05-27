package com.chenshuyusc.tjustudents.Manage.Student.studentSearch

import com.chenshuyusc.tjustudents.Entity.Student
import com.chenshuyusc.tjustudents.Entity.Selection
import com.chenshuyusc.tjustudents.Entity.StudentName
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory
import com.chenshuyusc.tjustudents.Util.RefreshState
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch

object StuSearchModel {

    fun getStudentById(
        id: String,
        callback: suspend (RefreshState<Unit>, student: Student?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.getStudentById(id).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it.data)
            }
        }
    }


    fun getStudentByName(
        name: String,
        callback: suspend (RefreshState<Unit>,  student: List<Student>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.getStudentByName(name).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it.data.list)
            }
        }
    }

    fun getSelectionBySid(
        sid: String,
        callback: suspend (RefreshState<Unit>, selections: List<Selection>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.getSelectionBySid(sid).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it.data)
            }
        }
    }
}