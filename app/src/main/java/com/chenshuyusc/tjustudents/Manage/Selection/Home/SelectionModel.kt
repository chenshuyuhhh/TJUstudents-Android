package com.chenshuyusc.tjustudents.Manage.Selection.Home

import com.chenshuyusc.tjustudents.Entity.*
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory
import com.chenshuyusc.tjustudents.Manage.Service.RetrofitFactory.api
import com.chenshuyusc.tjustudents.Util.RefreshState
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch

object SelectionModel {

    fun getSelecionList(
        page: Int,
        size: Int,
        callback: suspend (RefreshState<Unit>, selectionPage: SelectionPage?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            api.getSelectionPage(page, size).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it)
            }
        }
    }

    fun deleteSelection(
        selction: String, callback: suspend (RefreshState<Unit>, selection: sidSelection?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            RetrofitFactory.api.deleteSelection(selction).awaitAndHandle {
                callback(RefreshState.Failure(it), null)
            }?.let {
                callback(RefreshState.Success(Unit), it)
            }
        }
    }

//    fun selectionSD(
//        sid: String, cid: String, callback: suspend (RefreshState<Unit>, selection: sidSelection?) -> Unit
//    ) {
//        GlobalScope.launch(Dispatchers.Main) {
//            RetrofitFactory.api.getSelectionSD(sid,cid).awaitAndHandle {
//                callback(RefreshState.Failure(it), null)
//            }?.let {
//                callback(RefreshState.Success(Unit), it)
//            }
//        }
//    }
}