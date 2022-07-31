package kr.enjoyworks.room.common

import androidx.recyclerview.widget.DiffUtil

class GenericDiffUtil<T>(
    private val areItemsTheSameCallback: (old: T, new: T) -> Boolean,
    private val areContentsTheSameCallback: (old: T, new: T) -> Boolean,
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return areItemsTheSameCallback.invoke(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areContentsTheSameCallback.invoke(oldItem, newItem)
    }
}
