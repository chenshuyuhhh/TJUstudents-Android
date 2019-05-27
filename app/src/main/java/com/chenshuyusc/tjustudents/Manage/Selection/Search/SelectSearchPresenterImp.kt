package com.chenshuyusc.tjustudents.Manage.Selection.Search

import com.chenshuyusc.tjustudents.Manage.Selection.Home.SelectionModel
import com.chenshuyusc.tjustudents.Util.ConstValue
import com.chenshuyusc.tjustudents.Util.RefreshState

class SelectSearchPresenterImp(private val searchView: SelecSearchView) {
//    fun selectionSD(sid: String, cid: String) {
//        SelectSearchModel.selectionSD(sid, cid) { refreshstate, selection ->
//            when (refreshstate) {
//                is RefreshState.Success -> {
//                    if (selection == null || selection.data.isEmpty()) {
//                        searchView.onNull()
//                    } else {
//                        searchView.onSuccess(selection.data)
//                    }
//                }
//                is RefreshState.Failure -> {
//                    searchView.onError("${refreshstate.throwable}")
//                }
//            }
//        }
//    }

    fun searchSelection(p: String, keywords: String, keywordc: String) {
        when (p) {
            ConstValue.SIDCID -> {
                SelectSearchModel.sidcid(keywords, keywordc) { refreshstate, selection ->
                    when (refreshstate) {
                        is RefreshState.Success -> {
                            if (selection == null || selection.data.isEmpty()) {
                                searchView.onNull()
                            } else {
                                searchView.onSuccess(selection.data)
                            }
                        }
                        is RefreshState.Failure -> {
                            searchView.onError("${refreshstate.throwable}")
                        }
                    }
                }
            }
            ConstValue.SIDCNAME -> {
                SelectSearchModel.sidcname(keywords, keywordc) { refreshstate, selection ->
                    when (refreshstate) {
                        is RefreshState.Success -> {
                            if (selection == null || selection.data.isEmpty()) {
                                searchView.onNull()
                            } else {
                                searchView.onSuccess(selection.data)
                            }
                        }
                        is RefreshState.Failure -> {
                            searchView.onError("${refreshstate.throwable}")
                        }
                    }
                }
            }
            ConstValue.SNAMECID -> {
                SelectSearchModel.snamecid(keywords, keywordc) { refreshstate, selection ->
                    when (refreshstate) {
                        is RefreshState.Success -> {
                            if (selection == null || selection.data.isEmpty()) {
                                searchView.onNull()
                            } else {
                                searchView.onSuccess(selection.data)
                            }
                        }
                        is RefreshState.Failure -> {
                            searchView.onError("${refreshstate.throwable}")
                        }
                    }
                }
            }
            ConstValue.SNAMECNAME -> {
                SelectSearchModel.snamecname(keywords, keywordc) { refreshstate, selection ->
                    when (refreshstate) {
                        is RefreshState.Success -> {
                            if (selection == null || selection.data.isEmpty()) {
                                searchView.onNull()
                            } else {
                                searchView.onSuccess(selection.data)
                            }
                        }
                        is RefreshState.Failure -> {
                            searchView.onError("${refreshstate.throwable}")
                        }
                    }
                }
            }
        }
    }

    fun deleteSelection(selection: String, p: String, keywords: String, keywordc: String) {
        SelectionModel.deleteSelection(selection) { refreshstate, studentpage ->
            when (refreshstate) {
                is RefreshState.Success -> {
                    searchSelection(p, keywords, keywordc)
                }
                is RefreshState.Failure -> {
                    searchView.onError("${refreshstate.throwable}")
                }
            }
        }
    }
}