package `in`.abaddon.demorv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

const val LOAD_TYPE = -1

data class TodoVH(
    val root: View,
): RecyclerView.ViewHolder(root) {

    val description: TextView = root.findViewById(R.id.row_description)
    val id: TextView = root.findViewById(R.id.row_id)

    fun bindData(data: Todo){
        description.text = data.description
        id.text = data.id.toString().take(8)
    }
}

data class LoadVH(val root: View): RecyclerView.ViewHolder(root)

class TodoAdapter(var data: List<Item>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        return when(item){
            is Todo -> item.type.value
            is LoadItem -> LOAD_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = when(viewType) {
            TodoType.Work.value -> R.layout.row_work
            TodoType.Home.value -> R.layout.row_home
            TodoType.Hobby.value -> R.layout.row_hobby
            else -> R.layout.row_load
        }

        val rootView = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        if (viewType == LOAD_TYPE){
            return LoadVH(rootView)
        }

        return TodoVH(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]

        when(item){
            is Todo -> (holder as TodoVH).bindData(item)
        }
    }

    override fun getItemCount(): Int = data.size

    fun setLoading(isVisible: Boolean){
        if(isVisible) {
            data = data + listOf(LoadItem)
            notifyItemInserted(data.size - 1)
        } else {
            data = data.filter { i -> i != LoadItem }
            notifyItemRemoved(data.size - 1)
        }
    }

    fun updateData(newData: List<Item>){
        val diffCallback = TodoDiffCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        data = newData
        diffResult.dispatchUpdatesTo(this)
    }
}