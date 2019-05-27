package com.chenshuyusc.tjustudents.Manage.Course.Home

import com.chenshuyusc.tjustudents.Manage.Selection.Home.SelectionModel
import com.chenshuyusc.tjustudents.Util.RefreshState

class CoursePresenterImp(val courseView: CourseContract.CourseView) :
    CourseContract.CoursePresenter {
    override fun courseList(page: Int, size: Int) {
        CourseModel.getCourseList(
            page,
            size
        ) { refreshstate, selectionpage ->
            when (refreshstate) {
                is RefreshState.Success -> {
                    selectionpage?.data?.let {
                        if (page == 1) {
                            courseView.showList(it.list)
                        } else if (it.list.isEmpty()) {
                            courseView.onNull()
                        } else {
                            if (page <= it.lastPage) courseView.showMoreList(it.list)
                            else courseView.onBottom()
                        }
                    }
                }
                is RefreshState.Failure -> {
                    courseView.onError("${refreshstate.throwable}")
                }
            }
        }
    }

    fun deleteSelection(selection: String) {
        SelectionModel.deleteSelection(selection) { refreshstate, studentpage ->
            when (refreshstate) {
                is RefreshState.Success -> {
                    courseList(1, 6)
                }
                is RefreshState.Failure -> {
                    courseView.onError("${refreshstate.throwable}")
                }
            }
        }
    }
}