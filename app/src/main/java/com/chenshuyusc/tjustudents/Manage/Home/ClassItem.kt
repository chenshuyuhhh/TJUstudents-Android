package com.chenshuyusc.tjustudents.Manage.Home

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.chenshuyusc.tjustudents.R

class ClassItem(val name: String, val jump: () -> Unit) : Item {
    /**
     * implements these functions to delegate the core method of RecyclerView's Item
     */
    companion object Controller : ItemController {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_class, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            /**
             * with the help of Kotlin Smart Cast, we can cast the ViewHolder and item first.
             * the RecyclerView DSL framework could guarantee the holder and item are correct, just cast it !
             *
             * 因为Kotlin的智能Cast 所以后面我们就不需要自己强转了
             * DSL 框架可以保证holder和item的对应性
             */
            holder as ViewHolder
            item as ClassItem
            /**
             * what you do in OnBindViewHolder in RecyclerView, just do it here
             */
            holder.apply {
                textView.text = item.name
//                number.text = item.number.toString()

                imageView.setOnClickListener {
                    item.jump()
                }
            }
        }

        /**
         * define your ViewHolder here to pass view from OnCreateViewViewHolder to OnBindViewHolder
         * this ViewHolder class should be private and only use in this scope
         *
         * 在这里声明此Item所对应的ViewHolder，用来从OnCreateViewHolder传View到OnBindViewHolder中。
         * 这个ViewHolder类应该是私有的，只在这里用
         */
        private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView = itemView.findViewById<TextView>(R.id.class_text)
            val imageView = itemView.findViewById<ImageView>(R.id.class_jump_iv)
        }
    }

    /**
     * ItemController is necessary , it is often placed in the Item's companion Object
     * DON'T new an ItemController , because item viewType is corresponding to ItemController::class.java
     * or you will get many different viewType (for one type really) , which could break the RecyclerView's Cache
     *
     * 一般来讲，我们把ItemController放在Item的伴生对象里面，不要在这里new ItemController，因为在自动生成ViewType的时候，
     * 我们是根据ItemController::class.java 来建立一一对应关系，如果是new的话，会导致无法相等以至于生成许多ItemType，这样子会严重破坏Recyclerview的缓存机制
     */
    override val controller: ItemController
        get() = Controller
}

/**
 * wrap the add SingleTextItem function with DSL style
 *
 * 用DSL来风格来简单保证add SingleTextItem的操作
 */
fun MutableList<Item>.Classes(name: String, jump: () -> Unit) = add(ClassItem(name, jump))