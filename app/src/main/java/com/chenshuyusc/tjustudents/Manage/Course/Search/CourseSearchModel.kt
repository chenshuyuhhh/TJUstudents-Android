package com.chenshuyusc.tjustudents.Manage.Course.Search

import com.chenshuyusc.tjustudents.Entity.Selection
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory
import com.chenshuyusc.tjustudents.Util.RefreshState
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import com.chenshuyusc.tjustudents.Entity.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch

object CourseSearchModel {
    fun searchCourseID(
        id: String, callback: suspend (RefreshState<Unit>, course: Course?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.getCourseByID(id).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it.data)
            }
        }
    }

    fun searchCourseName(
        name: String, callback: suspend (RefreshState<Unit>, course: Course?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.getCourseByName(name).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it.data)
            }
        }
    }

    fun selectionsCid(
        cid: String, callback: suspend (RefreshState<Unit>, selection: List<Selection>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.getSelectionByCid(cid).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it.data)
            }
        }
    }

    fun selectionsCname(
        cname: String, callback: suspend (RefreshState<Unit>, selection: List<Selection>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.getSelectionByCname(cname).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it.data)
            }
        }
    }
}