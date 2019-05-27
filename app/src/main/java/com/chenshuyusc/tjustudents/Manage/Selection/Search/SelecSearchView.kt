package com.chenshuyusc.tjustudents.Manage.Selection.Search

import com.chenshuyusc.tjustudents.Entity.Selection

interface SelecSearchView {
    fun onSuccess(selections:List<Selection>)
    fun onNull()
    fun onError(msg: String)
}