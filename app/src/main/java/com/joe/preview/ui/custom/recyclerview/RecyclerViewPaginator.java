package com.joe.preview.ui.custom.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public abstract class RecyclerViewPaginator extends RecyclerView.OnScrollListener {

    private Long currentPage = 1L;
    private boolean endWithAuto = false;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    protected RecyclerViewPaginator(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        init();
    }

    private void init() {
        recyclerView.addOnScrollListener(this);
        layoutManager = recyclerView.getLayoutManager();
        loadFirstData(currentPage);
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == SCROLL_STATE_IDLE) {
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();

            int firstVisibleItemPosition = 0;

            if (layoutManager instanceof LinearLayoutManager)
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

            if (isLastPage())
                return;

            int threshold = 2;
            if ((visibleItemCount + firstVisibleItemPosition + threshold) >= totalItemCount) {
                if (!endWithAuto) {
                    endWithAuto = true;
                    loadMore(++currentPage);
                }
            } else
                endWithAuto = false;
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    public abstract boolean isLastPage();

    public abstract void loadMore(Long page);

    public abstract void loadFirstData(Long page);
}
