package `in`.abaddon.demorv

import androidx.recyclerview.widget.DiffUtil

class TodoDiffCallback(val old: List<Item>, val new: List<Item>): DiffUtil.Callback(){
    override fun getOldListSize(): Int = old.size
    override fun getNewListSize(): Int = new.size

    // Compare the identifiers
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = old[oldItemPosition]
        val newItem = new[newItemPosition]

        return when {
            oldItem is Todo && newItem is Todo -> oldItem.id == newItem.id
            oldItem is LoadItem && newItem is LoadItem -> true
            else -> false
        }
    }

    // Compare the contents
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = old[oldItemPosition]
        val newItem = new[newItemPosition]

        return when {
            oldItem is Todo && newItem is Todo -> oldItem == newItem
            oldItem is LoadItem && newItem is LoadItem -> true
            else -> false
        }
    }
}