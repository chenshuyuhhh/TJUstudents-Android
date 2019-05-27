package com.chenshuyusc.tjustudents.Manage.Selection.Search

import com.chenshuyusc.tjustudents.Entity.sidSelection
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory
import com.chenshuyusc.tjustudents.Util.RefreshState
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch

object SelectSearchModel {
    fun sidcid(
        sid: String, cid: String, callback: suspend (RefreshState<Unit>, selection: sidSelection?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.sidcid(sid, cid).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it)
            }
        }
    }

    fun sidcname(
        sid: String, cname: String, callback: suspend (RefreshState<Unit>, selection: sidSelection?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.sidcname(sid, cname).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it)
            }
        }
    }

    fun snamecid(
        sname: String, cid: String, callback: suspend (RefreshState<Unit>, selection: sidSelection?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.snamecid(sname, cid).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it)
            }
        }
    }

    fun snamecname(
        sname: String, cname: String, callback: suspend (RefreshState<Unit>, selection: sidSelection?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.snamecname(sname, cname).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it)
            }
        }
    }
}