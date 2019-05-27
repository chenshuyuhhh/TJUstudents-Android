package com.chenshuyusc.tjustudents.Manage.Selection.Home

import com.chenshuyusc.tjustudents.Util.RefreshState

class SelectionPresenterImp(val selectionView: SelectionContract.SelectionView) :
    SelectionContract.SelectionPresenter {
    override fun selectionList(page: Int, size: Int) {
        SelectionModel.getSelecionList(
            page,
            size
        ) { refreshstate, selectionpage ->
            when (refreshstate) {
                is RefreshState.Success -> {
                    selectionpage?.data?.let {
                        if (page == 1) {
                            selectionView.showSelectionList(it.list)
                        } else if (it.list.isEmpty()) {
                            selectionView.onNullSelectionList()
                        } else {
                            if (page <= it.lastPage) selectionView.showMoreSelctionList(it.list)
                            else selectionView.onBottom()
                        }
                    }
                }
                is RefreshState.Failure -> {
                    selectionView.onError("${refreshstate.throwable}")
                }
            }
        }
    }

    fun deleteSelection(selection: String) {
        SelectionModel.deleteSelection(selection) { refreshstate, studentpage ->
            when (refreshstate) {
                is RefreshState.Success -> {
                    selectionList(1,4)
                }
                is RefreshState.Failure -> {
                    selectionView.onError("${refreshstate.throwable}")
                }
            }
        }
    }

//

}