package com.robsterthelobster.ucitransit.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by robin on 10/29/2017.
 *
 * https://stackoverflow.com/questions/27414173/
 * equivalent-of-listview-setemptyview-in-recyclerview/27801394#27801394
 *
 * Changed to include SwipeRefreshLayout
 */

public class EmptyRecyclerView extends RecyclerView {

    private View emptyView;
    private View swipeRefreshLayout;
    private View emptySwipeRefreshLayout;

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkIfEmpty();
        }
    };

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty() {
        if (emptyView != null && swipeRefreshLayout != null
                && emptySwipeRefreshLayout != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            emptySwipeRefreshLayout.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
            swipeRefreshLayout.setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }

    public void setSwipeRefreshLayout(View swipeRefreshLayout){
        this.swipeRefreshLayout = swipeRefreshLayout;
        checkIfEmpty();
    }

    public void setEmptySwipeRefreshLayout(View swipeRefreshLayout){
        this.emptySwipeRefreshLayout = swipeRefreshLayout;
        checkIfEmpty();
    }
}