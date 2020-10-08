package `in`.abaddon.demorv

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(), LoadMoreListener{
    private val PAGE_SIZE = 5
    private val todoRepo: TodoRepository = DefaultTodoRepo()

    private var _data: MutableLiveData<List<Item>> = MutableLiveData(emptyList())
    val data: LiveData<List<Item>> = _data

    private var _isLoadingVisible: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadingVisible: LiveData<Boolean> = _isLoadingVisible

    init {
        val initialData = todoRepo.getMany(0,10)
        _data.value = initialData
    }

    override fun loadMore(currentSize: Int) {
        val isLoading = _isLoadingVisible.value!!

        if (!isLoading && todoRepo.totalSize() > currentSize) {
            _isLoadingVisible.postValue(true)

            // artificial delay
            Handler().postDelayed({
                val newData = todoRepo.getMany(currentSize, PAGE_SIZE)
                val oldData = _data.value ?: emptyList()
                _data.postValue(oldData + newData)

                _isLoadingVisible.postValue(false)
            }, 4000)
        }
    }
}