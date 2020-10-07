package `in`.abaddon.demorv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface OnItemClickListener {
    fun onItemClick(item: Todo, position: Int)
}

data class TodoVH(
    val root: View,
    val clickListener: OnItemClickListener
): RecyclerView.ViewHolder(root), View.OnClickListener {

    val description: TextView = root.findViewById(R.id.row_description)
    val id: TextView = root.findViewById(R.id.row_id)
    val checkBox: CheckBox = root.findViewById(R.id.row_checkbox)
    lateinit var todo: Todo

    init {
        root.setOnClickListener(this)
    }

    fun bindData(data: Todo){
        description.text = data.description
        id.text = data.id.toString().take(8)
        todo = data
    }

    override fun onClick(v: View?) {
        clickListener.onItemClick(todo, adapterPosition)
    }
}

class TodoAdapter(val data: List<Todo>, val clickListener: OnItemClickListener): RecyclerView.Adapter<TodoVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoVH {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.row_default, parent, false)
        return TodoVH(rootView, clickListener)
    }

    override fun onBindViewHolder(holder: TodoVH, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int = data.size
}