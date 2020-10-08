package `in`.abaddon.demorv

import java.util.Date
import java.util.Calendar
import java.util.UUID
import kotlin.random.Random

sealed class Item
object LoadItem: Item()

enum class TodoType(val value: Int) { Work(0), Home(1), Hobby(2) }
data class Todo(val id: UUID, val description: String, val isDone: Boolean, val dueDate: Date, val type: TodoType ): Item() {
    companion object{
        var counter = 0

        fun createMock(): Todo {
            val d = Date()
            val c = Calendar.getInstance()
            c.setTime(d)
            c.add(Calendar.DAY_OF_MONTH, 5)

            val type = when(Random.nextInt(1,4)){
                1 -> TodoType.Work
                2 -> TodoType.Home
                3 -> TodoType.Hobby
                else -> TodoType.Home
            }

            return Todo(
                UUID.randomUUID(),
                "Task ${counter++}",
                false,
                c.time,
                type
            )
        }
    }
}

interface TodoRepository {
    fun getOne(id: UUID): Todo?
    fun getMany(beginAt: Int, count: Int): List<Todo>
    fun insert(item: Todo): Boolean
    fun remove(id: UUID): Boolean
    fun totalSize(): Int
}

class DefaultTodoRepo: TodoRepository {
    private val items: MutableList<Todo> = (0..30).map { Todo.createMock() }.toMutableList()

    override fun getOne(id: UUID): Todo? = items.find { it.id == id }

    override fun getMany(beginAt: Int, count: Int): List<Todo> = items.drop(beginAt).take(count)

    override fun insert(item: Todo): Boolean = items.add(item)

    override fun totalSize(): Int = items.size

    override fun remove(id: UUID): Boolean {
        return items.removeIf { it.id == id }
    }
}
