package `in`.abaddon.demorv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var scrollListener: InfiniteScrollListener
    private lateinit var todoAdapter: TodoAdapter

    private val todoRepo = DefaultTodoRepo()
    private val PAGE_SIZE = 5

    fun loadMoreItems(currentSize: Int){
        todoAdapter.showLoading()

        // artificial delay
        Handler().postDelayed({
            val newData = todoRepo.getMany(currentSize, PAGE_SIZE)
            todoAdapter.insertData(newData)

            scrollListener.isLoading = false
            todoAdapter.hideLoading()
        }, 4000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val initialData = todoRepo.getMany(0, 10).map { it as Item }.toMutableList()
        todoAdapter = TodoAdapter(initialData){_, _ ->  }

        scrollListener = InfiniteScrollListener(
            {currentSize ->  todoRepo.totalSize() > currentSize},
            this::loadMoreItems
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.apply {
            adapter = todoAdapter
            setHasFixedSize(true)
            addOnScrollListener(scrollListener)
        }
    }
}
