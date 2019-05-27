package com.chenshuyusc.tjustudents.Entity

data class CoursePage<T>(
    val data: T,
    val code: Int,
    val message: String
)

data class DataC(
    val endRow: Int,
    val firstPage: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val isFirstPage: Boolean,
    val isLastPage: Boolean,
    val lastPage: Int,
    val list: List<Course>,
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

data class Course(
    val cancelyear: Int,
    val cid: String,
    val cname: String,
    val credit: Double,
    val suitgrade: Int,
    val teacher: String
)

data class CourseInfo(
    val avg: Double,
    val b6and7: Int,
    val b7and8: Int,
    val b8and9: Int,
    val b9and10: Int,
    val cancelyear: Int,
    val cid: String,
    val cname: String,
    val credit: Double,
    val full: Int,
    val suitgrade: Int,
    val teacher: String,
    val under60: Int,
    val num:Int
)