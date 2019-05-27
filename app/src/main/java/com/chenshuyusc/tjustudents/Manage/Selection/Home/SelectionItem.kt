package com.chenshuyusc.tjustudents.Manage.Selection.Home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.chenshuyusc.tjustudents.R
import com.chenshuyusc.tjustudents.Entity.*
import com.chenshuyusc.tjustudents.Util.ConstValue

class SelectionItem(val selection: Selection, val jump: (String, Selection) -> Unit) : Item {

    companion object Controller : ItemController {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_selection, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {

            holder as ViewHolder
            item as SelectionItem

            holder.apply {
                cid.text = item.selection.cid
                sid.text = item.selection.sid
                year.text = item.selection.year.toString()
                score.text = item.selection.score.toString()
            }
            holder.delete.setOnClickListener {
                item.jump(ConstValue.DELETE, item.selection)
            }

            holder.modify.setOnClickListener {
                item.jump(ConstValue.UPDATE, item.selection)
            }
        }

        private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cid = itemView.findViewById<TextView>(R.id.select_tv_cid)
            val sid = itemView.findViewById<TextView>(R.id.select_tv_sid)
            val year = itemView.findViewById<TextView>(R.id.select_tv_year_content)
            val score = itemView.findViewById<TextView>(R.id.select_tv_score_content)

            val delete = itemView.findViewById<ImageView>(R.id.select_iv_delete)
            val modify = itemView.findViewById<ImageView>(R.id.select_iv_edit)
        }
    }

    override val controller: ItemController
        get() = SelectionItem
}

fun MutableList<Item>.addSelection(selection: Selection, jump: (String, Selection) -> Unit) = add(
    SelectionItem(
        selection, jump
    )
)