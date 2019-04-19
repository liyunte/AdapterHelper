package com.lyt.adapterhelper.library.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;


public class RViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private SparseArray<Long> mSpans;
    private View mContentView;
    private static final long QUICK_EVENT_TIME_SPAN = 1000;

    private RViewHolder(@NonNull View itemView) {
        super(itemView);
        mContentView = itemView;
        mViews = new SparseArray<>();
        mSpans = new SparseArray<>();
    }

    public static RViewHolder createViewHolder(Context context, ViewGroup viewParent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, viewParent, false);
        return new RViewHolder(itemView);
    }
    public static RViewHolder createViewHolder(@NonNull View itemView) {
        return new RViewHolder(itemView);
    }

    public View getContentView() {
        return mContentView;
    }

    public RViewHolder setText(int viewId, CharSequence value) {
        TextView view = (TextView) this.getView(viewId);
        view.setText(value);
        return this;
    }

    public RViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = (ImageView) this.getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public RViewHolder setBackgroundColor(int viewId, int color) {
        View view = this.getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public RViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = this.getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public RViewHolder setTextColor(int viewId, int textColor) {
        TextView view = (TextView) this.getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public RViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = (ImageView) this.getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public RViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = (ImageView) this.getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public RViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= 11) {
            this.getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0L);
            alpha.setFillAfter(true);
            this.getView(viewId).startAnimation(alpha);
        }

        return this;
    }

    public RViewHolder setVisible(int viewId, boolean visible) {
        View view = this.getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public RViewHolder linkify(int viewId) {
        TextView view = (TextView) this.getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public RViewHolder setTypeface(int viewId, Typeface typeface) {
        TextView view = (TextView) this.getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | 128);
        return this;
    }

    public RViewHolder setTypeface(Typeface typeface, int... viewIds) {
        int[] var3 = viewIds;
        int var4 = viewIds.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            int viewId = var3[var5];
            TextView view = (TextView) this.getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | 128);
        }

        return this;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
    }

    public void setOnLongClickListener(int viewId, View.OnLongClickListener longClickListener) {
        getView(viewId).setOnLongClickListener(longClickListener);
    }

    /**
     * 防止点击事件误操作
     *
     * @param viewId
     * @param listener
     */
    public void setOnPreventClickListener(int viewId, View.OnClickListener listener) {
        long currentTime = System.currentTimeMillis();
        long timeSpan = currentTime - mSpans.get(viewId);
        if (timeSpan < QUICK_EVENT_TIME_SPAN) {
            return;
        }
        mSpans.put(viewId, currentTime);
        getView(viewId).setOnClickListener(listener);
    }


}
