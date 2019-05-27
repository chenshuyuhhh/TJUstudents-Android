package com.chenshuyusc.tjustudents.Manage.Course.Home

import com.chenshuyusc.tjustudents.Entity.CoursePage
import com.chenshuyusc.tjustudents.Entity.Course
import com.chenshuyusc.tjustudents.Entity.DataC
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory
import com.chenshuyusc.tjustudents.Util.RefreshState
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch

object CourseModel {
    fun getCourseList(
        page: Int,
        size: Int,
        callback: suspend (RefreshState<Unit>, coursePage: CoursePage<DataC>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.getCoursePage(page, size).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it)
            }
        }
    }

    fun deleteCourse(
        id: String, callback: suspend (RefreshState<Unit>, course: CoursePage<Course>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.deleteCourse(id).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it)
            }
        }
    }
}