package `in`.abaddon.demorv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class TodoVH(val root: View): RecyclerView.ViewHolder(root){
    val description: TextView = root.findViewById(R.id.row_description)
    val id: TextView = root.findViewById(R.id.row_id)
    val checkBox: CheckBox = root.findViewById(R.id.row_checkbox)

    fun bindData(item: Todo){
       description.text = item.description
       id.text = item.id.toString().take(8)
    }
}

class TodoAdapter(val data: List<Todo>): RecyclerView.Adapter<TodoVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoVH {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.row_default, parent, false)
        return TodoVH(rootView)
    }

    override fun onBindViewHolder(holder: TodoVH, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int = data.size
}