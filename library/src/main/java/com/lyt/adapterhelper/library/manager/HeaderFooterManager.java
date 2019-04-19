package com.lyt.adapterhelper.library.manager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 头部尾部管理器
 */
public class HeaderFooterManager {
    private LinearLayout mHeaderLayout;
    private LinearLayout mCopyHeaderLayout;
    private LinearLayout mFooterLayout;
    private LinearLayout mCopyFooterLayout;
    private LinearLayout mEmptyLayout;
    private LinearLayout mCopyEmptyLayout;


    public LinearLayout getHeaderLayout() {
        return mHeaderLayout;
    }

    public LinearLayout getFooterLayout() {
        return mFooterLayout;
    }

    public LinearLayout getEmptyLayout() {
        return mEmptyLayout;
    }

    public void addHeaderView(View header, int index) {
        if (this.mHeaderLayout == null) {
            if (this.mCopyHeaderLayout == null) {
                this.mHeaderLayout = new LinearLayout(header.getContext());
                this.mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                this.mHeaderLayout.setLayoutParams(new android.support.v7.widget.RecyclerView.LayoutParams(-1, -2));
                this.mCopyHeaderLayout = this.mHeaderLayout;
            } else {
                this.mHeaderLayout = this.mCopyHeaderLayout;
            }
        }
        index = index >= this.mHeaderLayout.getChildCount() ? -1 : index;
        this.mHeaderLayout.addView(header, index);
    }

    public void addFooterView(View footer, int index) {
        if (this.mFooterLayout == null) {
            if (this.mCopyFooterLayout == null) {
                this.mFooterLayout = new LinearLayout(footer.getContext());
                this.mFooterLayout.setOrientation(LinearLayout.VERTICAL);
                this.mFooterLayout.setLayoutParams(new android.support.v7.widget.RecyclerView.LayoutParams(-1, -2));
                this.mCopyFooterLayout = this.mFooterLayout;
            } else {
                this.mFooterLayout = this.mCopyFooterLayout;
            }
        }
        index = index >= this.mFooterLayout.getChildCount() ? -1 : index;
        this.mFooterLayout.addView(footer, index);
    }

    public void setEmptyView(View emptyView,int index) {
        if (this.mEmptyLayout == null) {
            if (this.mCopyEmptyLayout == null) {
                this.mEmptyLayout = new LinearLayout(emptyView.getContext());
                this.mEmptyLayout.setOrientation(LinearLayout.VERTICAL);
                this.mEmptyLayout.setLayoutParams(new android.support.v7.widget.RecyclerView.LayoutParams(-1, -2));
                this.mCopyEmptyLayout = this.mEmptyLayout;
            } else {
                this.mEmptyLayout = this.mCopyEmptyLayout;
            }
        }
        index = index >= this.mEmptyLayout.getChildCount() ? -1 : index;
        this.mEmptyLayout.addView(emptyView, index);
    }


    public void removeHeaderView(View header) {
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.removeView(header);
            if (this.mHeaderLayout.getChildCount() == 0) {
                this.mHeaderLayout = null;
            }
        }
    }

    public void removeFooterView(View footer) {
        if (this.mFooterLayout != null) {
            this.mFooterLayout.removeView(footer);
            if (this.mFooterLayout.getChildCount() == 0) {
                this.mFooterLayout = null;
            }
        }
    }

    public void removeEmptyView(View emptyView) {
        if (mEmptyLayout != null) {
            mEmptyLayout.removeView(emptyView);
            if (this.mEmptyLayout.getChildCount() == 0) {
                this.mEmptyLayout = null;
            }
        }
    }

    public void removeAllHeaderView() {
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.removeAllViews();
            this.mHeaderLayout = null;
        }
    }

    public void removeAllFooterView() {
        if (this.mFooterLayout != null) {
            this.mFooterLayout.removeAllViews();
            this.mFooterLayout = null;
        }
    }

    public void removeAllEmptyView() {
        if (this.mEmptyLayout != null) {
            this.mEmptyLayout.removeAllViews();
            this.mEmptyLayout = null;
        }
    }

    /**
     * 头部数量
     *
     * @return
     */
    public int getHeaderCount() {
        return mHeaderLayout == null ? 0 : 1;
    }

    /**
     * 底部数量
     *
     * @return
     */
    public int getFooterCount() {
        return mFooterLayout == null ? 0 : 1;
    }

    /**
     * 空布局数量
     *
     * @return
     */
    public int getEmptyViewCount() {
        return mEmptyLayout == null ? 0 : 1;
    }
}
