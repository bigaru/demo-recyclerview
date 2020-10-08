package `in`.abaddon.demorv

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(
    val hasMoreItems: (currentSize: Int)->Boolean,
    val loadMoreItems: (currentSize: Int)->Unit
): RecyclerView.OnScrollListener(){
    var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val hasScrolledDown = dy > 0

        if(!hasScrolledDown) {
            return
        }

        val currentSize = layoutManager.itemCount

        if(!isLoading && hasMoreItems(currentSize)){
            isLoading = true
            loadMoreItems(currentSize)
        }
    }
}