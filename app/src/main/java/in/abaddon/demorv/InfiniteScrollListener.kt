package `in`.abaddon.demorv

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

interface LoadMoreListener{
    fun loadMore(currentSize: Int)
}

class InfiniteScrollListener(
    val loadMoreListener: LoadMoreListener
): RecyclerView.OnScrollListener(){
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val hasScrolledDown = dy > 0

        if(!hasScrolledDown) {
            return
        }

        val currentSize = layoutManager.itemCount
        loadMoreListener.loadMore(currentSize)
    }
}