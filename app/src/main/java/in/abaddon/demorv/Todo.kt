package `in`.abaddon.demorv

import java.util.*

data class Todo(val id: UUID, val description: String, val isDone: Boolean, val dueDate: Date) {
    companion object{
        var counter = 0

        fun createMock(): Todo {
            val d = Date()
            val c = Calendar.getInstance()
            c.setTime(d)
            c.add(Calendar.DAY_OF_MONTH, 5)

            return Todo(
                UUID.randomUUID(),
                "Task ${counter++}",
                false,
                c.time
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

    override fun getMany(beginAt: Int, count: Int): List<Todo> = items.slice(beginAt until count)

    override fun insert(item: Todo): Boolean = items.add(item)

    override fun totalSize(): Int = items.size

    override fun remove(id: UUID): Boolean {
        return items.removeIf { it.id == id }
    }
}
