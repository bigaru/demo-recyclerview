package `in`.abaddon.demorv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

typealias OnItemClick = (Todo, Int) -> Unit
const val LOAD_TYPE = -1

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

data class LoadVH(val root: View): RecyclerView.ViewHolder(root)

class TodoAdapter(val data: MutableList<Item>, val onItemClick: OnItemClick): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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

        return TodoVH(rootView, onItemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]

        when(item){
            is Todo -> (holder as TodoVH).bindData(item)
        }
    }

    override fun getItemCount(): Int = data.size

    fun showLoading(){
        data.add(LoadItem)
        notifyItemInserted(data.size -1)
    }
    fun hideLoading(){
        data.removeIf{i -> i == LoadItem}
        notifyItemRemoved(data.size -1)
    }

    fun insertData(newData: List<Todo>){
        val begin = data.size
        val end = begin + newData.size - 1

        data.addAll(newData)
        notifyItemRangeInserted(begin, end)
    }
}