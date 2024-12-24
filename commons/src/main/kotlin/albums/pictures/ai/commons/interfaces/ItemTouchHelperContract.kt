package albums.pictures.ai.commons.interfaces

import albums.pictures.ai.commons.adapters.MyRecyclerViewAdapter

interface ItemTouchHelperContract {
    fun onRowMoved(fromPosition: Int, toPosition: Int)

    fun onRowSelected(myViewHolder: MyRecyclerViewAdapter.ViewHolder?)

    fun onRowClear(myViewHolder: MyRecyclerViewAdapter.ViewHolder?)
}
