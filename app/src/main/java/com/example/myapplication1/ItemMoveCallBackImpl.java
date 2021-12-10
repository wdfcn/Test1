package com.example.myapplication1;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemMoveCallBackImpl extends ItemTouchHelper.Callback {

    private WishlistActivity.ItemMoveHelperApi mHelperApi;

    public ItemMoveCallBackImpl(WishlistActivity.ItemMoveHelperApi helperApi) {
        this.mHelperApi = helperApi;
    }
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (mHelperApi != null) {
            mHelperApi.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }
}