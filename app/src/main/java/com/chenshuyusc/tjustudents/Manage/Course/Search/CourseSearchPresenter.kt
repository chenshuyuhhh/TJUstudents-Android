package com.chenshuyusc.tjustudents.Manage.Course.Search

import com.chenshuyusc.tjustudents.Entity.Selection
import com.chenshuyusc.tjustudents.Entity.Student
import com.chenshuyusc.tjustudents.Manage.Selection.Home.SelectionModel
import com.chenshuyusc.tjustudents.Manage.Student.studentHome.StudentModel
import com.chenshuyusc.tjustudents.Manage.Student.studentSearch.StuSearchModel
import com.chenshuyusc.tjustudents.Util.ConstValue
import com.chenshuyusc.tjustudents.Util.ConstValue.BLANK
import com.chenshuyusc.tjustudents.Util.RefreshState

class CourseSearchPresenter(private val searchView: CourseSearchView) {

    fun getSearch(keyword: String) {
        if (keyword.isDigit()) {
            CourseSearchModel.searchCourseID(keyword) { refresh, course ->
                when (refresh) {
                    is RefreshState.Success -> {
                        if (course == null) {
                            searchView.onNull(BLANK)
                        } else {
//                            val list = arrayListOf<Student>()
//                            list.add(students)
                            CourseSearchModel.selectionsCid(keyword) { refresh2, selections ->
                                when (refresh2) {
                                    is RefreshState.Success -> {
                                        if (selections.orEmpty().isEmpty()) {
                                            searchView.onNull(ConstValue.NOBLANK)
                                        }
                                        searchView.onSuccess(course, selections.orEmpty())
                                    }
                                    is RefreshState.Failure -> {
                                        searchView.onError("${refresh2.throwable}")
                                    }
                                }
                            }
                        }
                    }
                    is RefreshState.Failure -> {
                        searchView.onError("${refresh.throwable}")
                    }
                }
            }
        } else {
            CourseSearchModel.searchCourseName(keyword) { refresh, course ->
                when (refresh) {
                    is RefreshState.Success -> {
                        if (course == null) {
                            searchView.onNull(BLANK)
                        } else {
//                            val list = arrayListOf<Student>()
//                            list.add(students)
                            CourseSearchModel.selectionsCid(keyword) { refresh2, selections ->
                                when (refresh2) {
                                    is RefreshState.Success -> {
                                        if (selections.orEmpty().isEmpty()) {
                                            searchView.onNull(ConstValue.NOBLANK)
                                        }
                                        searchView.onSuccess(course, selections.orEmpty())
                                    }
                                    is RefreshState.Failure -> {
                                        searchView.onError("${refresh2.throwable}")
                                    }
                                }
                            }
                        }
                    }
                    is RefreshState.Failure -> {
                        searchView.onError("${refresh.throwable}")
                    }
                }
            }
        }
    }


    fun deleteSelection(id: String, keyword: String) {
        SelectionModel.deleteSelection(id) { refreshstate, studentpage ->
            when (refreshstate) {
                is RefreshState.Success -> {
                    getSearch(keyword)
                }
                is RefreshState.Failure -> {
                    searchView.onError("${refreshstate.throwable}")
                }
            }
        }
    }

    private fun String.isDigit(): Boolean {
        repeat(this.length) { i ->
            if (!Character.isDigit(this[i])) {
                return false
            }
        }
        return true
    }
}