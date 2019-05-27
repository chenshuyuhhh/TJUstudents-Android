package com.chenshuyusc.tjustudents.Manage.Student.studentHome

import com.chenshuyusc.tjustudents.Entity.StudentPage
import com.chenshuyusc.tjustudents.Entity.DataS
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory
import com.chenshuyusc.tjustudents.Util.RefreshState
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch

object StudentModel {
    fun getStudentList(
        page: Int,
        size: Int,
        callback: suspend (RefreshState<Unit>, studentPage: StudentPage<DataS>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.getStudentPage(page, size).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it)
            }
        }
    }

    fun deleteStu(
        id: String, callback: suspend (RefreshState<Unit>, studentPage: StudentPage<DataS>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.deleteStu(id).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it)
            }
        }
    }
}