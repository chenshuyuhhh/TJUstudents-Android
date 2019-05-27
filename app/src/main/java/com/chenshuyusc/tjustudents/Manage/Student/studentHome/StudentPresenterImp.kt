package com.chenshuyusc.tjustudents.Manage.Student.studentHome

import com.chenshuyusc.tjustudents.Util.RefreshState

class StudentPresenterImp(val studentView: StudentContract.StudentView): StudentContract.StudentPresenter {

    override fun studentList(page: Int, size: Int) {
        StudentModel.getStudentList(
            page,
            size
        ) { refreshstate, studentpage ->
            when (refreshstate) {
                is RefreshState.Success -> {
                    studentpage?.data?.let {
                        if (page == 1) {
                            studentView.showList(it.list)
                        } else if (it.list.isEmpty()) {
                            studentView.onNull()
                        } else {
                            if (page <= it.lastPage) studentView.showMoreList(it.list)
                            else studentView.onBottom()
                        }
                    }
                }
                is RefreshState.Failure -> {
                    studentView.onError("${refreshstate.throwable}")
                }
            }
        }
    }

    override fun deleteStu(id: String) {
        StudentModel.deleteStu(id) { refreshstate, studentpage ->
            when (refreshstate) {
                is RefreshState.Success -> {
                    studentList(1,6)
                }
                is RefreshState.Failure -> {
                    studentView.onError("${refreshstate.throwable}")
                }
            }
        }
    }
}