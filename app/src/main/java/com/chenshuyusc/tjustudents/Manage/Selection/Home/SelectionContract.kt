package com.chenshuyusc.tjustudents.Manage.Selection.Home

import com.chenshuyusc.tjustudents.Entity.*

class SelectionContract {
    interface SelectionView {
        fun showSelectionList(selections: List<Selection>)
        fun showMoreSelctionList(selections: List<Selection>)
        fun onNullSelectionList()
        fun onError(msg: String)
        fun onBottom()
    }

    interface SelectionPresenter {
        fun selectionList(page: Int, size: Int)
    }
}