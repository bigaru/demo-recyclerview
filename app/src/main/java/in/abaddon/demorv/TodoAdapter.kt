package `in`.abaddon.demorv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

typealias OnItemClick = (Todo, Int) -> Unit

data class TodoVH(
    val root: View,
    val onItemClick: OnItemClick
): RecyclerView.ViewHolder(root) {

    val description: TextView = root.findViewById(R.id.row_description)
    val id: TextView = root.findViewById(R.id.row_id)
    val checkBox: CheckBox = root.findViewById(R.id.row_checkbox)
    lateinit var todo: Todo

    init {
        root.setOnClickListener{ onItemClick(todo, adapterPosition) }
    }

    fun bindData(data: Todo){
        description.text = data.description
        id.text = data.id.toString().take(8)
        todo = data
    }
}

class TodoAdapter(val data: List<Todo>, val onItemClick: OnItemClick): RecyclerView.Adapter<TodoVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoVH {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.row_default, parent, false)
        return TodoVH(rootView, onItemClick)
    }

    override fun onBindViewHolder(holder: TodoVH, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int = data.size
}