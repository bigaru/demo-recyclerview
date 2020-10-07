package `in`.abaddon.demorv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoRepo = DefaultTodoRepo()
        val initialData = todoRepo.getMany(0, 10)
        val todoAdapter = TodoAdapter(initialData){item: Todo, position: Int ->
            Toast.makeText(this, item.description, Toast.LENGTH_SHORT).show()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.apply {
            adapter = todoAdapter
            setHasFixedSize(true)
        }
    }
}
