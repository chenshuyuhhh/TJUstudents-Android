package com.chenshuyusc.tjustudents.Manage.Student.studentSearch

import com.chenshuyusc.tjustudents.Entity.Student
import com.chenshuyusc.tjustudents.Entity.Selection
import com.chenshuyusc.tjustudents.Manage.Selection.Home.SelectionModel
import com.chenshuyusc.tjustudents.Manage.Student.studentHome.StudentModel
import com.chenshuyusc.tjustudents.Util.ConstValue.BLANK
import com.chenshuyusc.tjustudents.Util.ConstValue.NOBLANK
import com.chenshuyusc.tjustudents.Util.RefreshState

class StuSearchPresenter(val stuSearchView: StuSearchView) {
    fun getSearch(keyword: String) {
        if (keyword.isDigit()) {
            StuSearchModel.getStudentById(keyword) { refresh, students ->
                when (refresh) {
                    is RefreshState.Success -> {
                        if (students == null) {
                            stuSearchView.onNull(BLANK)
                        } else {
                            val list = arrayListOf<Student>()
                            list.add(students)
                            StuSearchModel.getSelectionBySid(students.sid) { refresh2, selections ->
                                when (refresh2) {
                                    is RefreshState.Success -> {
                                        if (selections.orEmpty().isEmpty()){
                                            stuSearchView.onNull(NOBLANK)
                                        }
                                        val map = HashMap<String, List<Selection>>()
                                        map[students.sid] = selections.orEmpty()
                                        stuSearchView.onSuccess(list, map)
                                    }
                                    is RefreshState.Failure -> {
                                        stuSearchView.onError("${refresh2.throwable}")
                                    }
                                }
                            }
                        }
                    }
                    is RefreshState.Failure -> {
                        stuSearchView.onError("${refresh.throwable}")
                    }
                }
            }
        } else {
            StuSearchModel.getStudentByName(keyword) { refresh, students ->
                when (refresh) {
                    is RefreshState.Success -> {
                        if (students == null || students.isEmpty()) {
                            stuSearchView.onNull(BLANK)
                        } else {
                            repeat(students.size) { i ->
                                StuSearchModel.getSelectionBySid(students[i].sid) { refresh2, selections ->
                                    when (refresh2) {
                                        is RefreshState.Success -> {
                                            if (selections.orEmpty().isEmpty()){
                                                stuSearchView.onNull(NOBLANK)
                                            }
                                            val map = HashMap<String, List<Selection>>()
                                            map[students[i].sid] = selections.orEmpty()
                                            if (i == students.size - 1) {
                                                stuSearchView.onSuccess(students, map)
                                            }
                                        }
                                        is RefreshState.Failure -> {
                                            stuSearchView.onError("${refresh2.throwable}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is RefreshState.Failure -> {
                        stuSearchView.onError("${refresh.throwable}")
                    }
                }
            }
        }
    }

    fun deleteStu(id: String, keyword: String) {
        StudentModel.deleteStu(id) { refreshstate, studentpage ->
            when (refreshstate) {
                is RefreshState.Success -> {
                    getSearch(keyword)
                }
                is RefreshState.Failure -> {
                    stuSearchView.onError("${refreshstate.throwable}")
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
                    stuSearchView.onError("${refreshstate.throwable}")
                }
            }
        }
    }

    //fun getSelections

    private fun String.isDigit(): Boolean {
        repeat(this.length) { i ->
            if (!Character.isDigit(this[i])) {
                return false
            }
        }
        return true
    }
}