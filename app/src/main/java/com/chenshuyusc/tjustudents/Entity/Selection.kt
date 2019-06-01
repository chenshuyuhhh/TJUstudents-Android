package com.chenshuyusc.tjustudents.Entity

data class SelectionPage(
    val data: Data,
    val code: Int,
    val message: String
)


data class Data(
    val endRow: Int,
    val firstPage: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val isFirstPage: Boolean,
    val isLastPage: Boolean,
    val lastPage: Int,
    val list: List<Selection>,
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

/**
 * 选课信息
 */
data class Selection(
    val cid: String,
    val score: Int?,
    val sid: String,
    val year: Int
)

data class sidSelection(
    val data: List<Selection>,
    val code: Int,
    val message: String
)

data class selectionsCid(
    val data: List<Selection>,
    val code: Int,
    val message: String
)

