package com.chenshuyusc.tjustudents.Entity

data class StudentPage<T>(
    val data: T,
    val code: Int,
    val message: String
)

data class DataS(
    val endRow: Int,
    val firstPage: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val isFirstPage: Boolean,
    val isLastPage: Boolean,
    val lastPage: Int,
    val list: List<Student>,
    val navigateFirstPage: Int,
    val navigateLastPage: Int,
    val navigatePages: Int,
    val navigatepageNums: List<Int>,
    val nextPage: Int,
    val orderBy: Any,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val prePage: Int,
    val size: Int,
    val startRow: Int,
    val total: Int
)

data class Student(
    val age: Int,
    val classname: String,
    val gender: String,
    val sid: String,
    val sname: String,
    val year: Int
)

data class StudentSearchID(
    val data: Student,
    val code: Int,
    val message: String
)

data class StudentSearchName(
    val data: StudentName,
    val code: Int,
    val message: String
)

data class StudentName(
    val endRow: Int,
    val firstPage: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val isFirstPage: Boolean,
    val isLastPage: Boolean,
    val lastPage: Int,
    val list: List<Student>,
    val navigateFirstPage: Int,
    val navigateLastPage: Int,
    val navigatePages: Int,
    val navigatepageNums: List<Int>,
    val nextPage: Int,
    val orderBy: Any,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val prePage: Int,
    val size: Int,
    val startRow: Int,
    val total: Int
)


data class StudentInfo(
    val age: Int,
    val avgScore: Double,
    val classname: String,
    val gender: String,
    val sid: String,
    val sname: String,
    val year: Int
)