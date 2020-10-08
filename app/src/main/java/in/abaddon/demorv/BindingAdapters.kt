package `in`.abaddon.demorv

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["data", "loadingVisibility","loadMoreItems"])
fun modifyAdapter(
    recyclerView: RecyclerView,
    data: LiveData<List<Item>>,
    isLoadingVisible: LiveData<Boolean>,
    loadMoreItems: LoadMoreListener
){
    if (recyclerView.adapter == null){
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = TodoAdapter(data.value ?: emptyList())

        val scrollListener = InfiniteScrollListener(loadMoreItems)
        recyclerView.addOnScrollListener(scrollListener)

    }else{
        val todoAdapter = recyclerView.adapter as TodoAdapter
        val items = data.value ?: emptyList()

        todoAdapter.updateData(items)
        todoAdapter.setLoading(isLoadingVisible.value ?: false)
    }
}
