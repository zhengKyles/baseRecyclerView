package com.kyle.baserecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;


/**
 * Created by Kyle on 2018/8/15.
 * 主RecyclerView 可设置分割线等
 */

public class LRecyclerView extends RecyclerView {

    public LRecyclerView(Context context) {
        this(context, null);
    }

    @IntDef({HORIZONTAL, VERTICAL, GRID})
    @Retention(RetentionPolicy.SOURCE)
    @interface OrientationMode {
    }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int GRID = 2;

    private int mOrientation = VERTICAL;
    private Context context;

    private int dividerHorizontal;
    private int dividerVertical;

    private int spanCount = 1;//当Grid时，列数
    private Drawable mDivider = null;//分割线颜色、图片等

    private boolean lastEnable = false;//最后一行一列是否画线
    private PagerItemDecoration itemDecoration;

    public LRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LRecyclerView);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.LRecyclerView_divider_width_horizontal) {
                dividerHorizontal = (int) a.getDimension(attr, 0);
            } else if (attr == R.styleable.LRecyclerView_divider_height_vertical) {
                dividerVertical = (int) a.getDimension(attr, 0);
            } else if (attr == R.styleable.LRecyclerView_span_count) {
                this.spanCount = a.getInt(attr, 1);
            } else if (attr == R.styleable.LRecyclerView_recycler_divider) {
                mDivider = a.getDrawable(attr);
            } else if (attr == R.styleable.LRecyclerView_direction) {
                mOrientation = a.getInt(attr, VERTICAL);
            } else if (attr == R.styleable.LRecyclerView_lastEnable) {
                lastEnable = a.getBoolean(attr, false);
            }
        }
        a.recycle();
        setOverScrollMode(OVER_SCROLL_NEVER);
        initView();
    }

    private void initView() {
        LayoutManager manager = null;
        switch (mOrientation) {
            case HORIZONTAL:
                manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                break;
            case VERTICAL:
                manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                break;
            case GRID:
                manager = new GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false);
                break;
        }
        setLayoutManager(manager);
        if (itemDecoration == null) {
            itemDecoration = new PagerItemDecoration();
            addItemDecoration(itemDecoration);
        }
    }

    public boolean isLastEnable() {
        return lastEnable;
    }


    public int getDividerHorizontal() {
        return dividerHorizontal;
    }

    public int getDividerVertical() {
        return dividerVertical;
    }

    public int getSpanCount() {
        return spanCount;
    }

    public int getOrientation() {
        return mOrientation;
    }

    //setter

    /***
     * 最后一行一列是否显示
     * @param lastEnable
     */
    public void setLastEnable(boolean lastEnable) {
        this.lastEnable = lastEnable;
        requestView();
    }

    /***
     * 设置分割线样式
     * @param mDivider
     */
    public void setDivider(Drawable mDivider) {
        this.mDivider = mDivider;
        requestView();
    }

    /***
     * 设置水平分割线
     * @param dividerHorizontal
     */
    public void setDividerHorizontal(int dividerHorizontal) {
        this.dividerHorizontal = dividerHorizontal;
        requestView();
    }

    /***
     * 设置竖直分割线
     * @param dividerVertical
     */
    public void setDividerVertical(int dividerVertical) {
        this.dividerVertical = dividerVertical;
        requestView();
    }

    /***
     * 设置方向
     * @param orientation
     */
    public void setOrientation(@OrientationMode int orientation) {
        this.mOrientation = orientation;
        initView();//重设了方向，重新初始化
    }

    /***
     * 设置grid时，列数
     * @param spanCount
     */
    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
        initView();
    }


    public void requestView() {
        invalidate();
    }

    public class PagerItemDecoration extends ItemDecoration {

        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull State state) {
            if (mDivider != null) {
                if (mOrientation == GRID) {
                    drawHorizontal(c, parent);
                    drawVertical(c, parent);
                } else if (mOrientation == HORIZONTAL) {
                    drawHorizontal(c, parent);
                } else {
                    drawVertical(c, parent);
                }
            }
        }

        void drawHorizontal(Canvas c, RecyclerView parent) {
            int left;
            int right;
            int top;
            int bottom;
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {

                final View child = parent.getChildAt(i);

                final LayoutParams params =
                        (LayoutParams) child.getLayoutParams();

                left = child.getRight() + params.rightMargin;
                right = left + dividerHorizontal;

                if (((LRecyclerView) parent).getOrientation() == GRID) {
                    if (child.getMeasuredWidth() == 0) {
                        int parentWidth = getMeasuredWidth();
                        int itemWidth;
                        if (lastEnable) {
                            itemWidth = (parentWidth - (spanCount * dividerHorizontal)) / spanCount;
                        } else {
                            itemWidth = (parentWidth - ((spanCount - 1) * dividerHorizontal)) / spanCount;
                        }
                        int x = (i + 1) % spanCount == 0 ? spanCount : (i + 1) % spanCount;

                        left = x * itemWidth + (x - 1) * dividerHorizontal;
                        right = left + dividerHorizontal;
                    }else{
                        return;
                    }
                }
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        void drawVertical(Canvas c, RecyclerView parent) {
            int left;
            int right;
            int top;
            int bottom;
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final LayoutParams params =
                        (LayoutParams) child.getLayoutParams();
                top = child.getBottom() + params.bottomMargin;
                bottom = top + dividerVertical;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
            int curVertical = dividerVertical;
            int curHorizontal = dividerHorizontal;
            //是否是最后一行
            boolean isLastRow = false;
            //是否是最后一列
            boolean isLastColumn = false;

            if (!lastEnable) {
                isLastRow = isLastRow(view, (LRecyclerView) parent);
                isLastColumn = isLastColumn(view, (LRecyclerView) parent);
            }
            if (mOrientation == GRID) {
                if (isLastRow) {
                    curVertical = 0;
                }
                if (isLastColumn) {
                    curHorizontal = 0;
                }
                outRect.set(0, 0, curHorizontal, curVertical);
            } else if (mOrientation == HORIZONTAL) {
                if (!isLastColumn) {
                    outRect.set(0, 0, dividerHorizontal, 0);
                }
            } else {
                if (!isLastRow) {
                    outRect.set(0, 0, 0, dividerVertical);
                }
            }
        }
    }

    /***
     * 是否最后一行
     * @return
     */
    private boolean isLastRow(View item, LRecyclerView recyclerView) {
        int position = recyclerView.getChildLayoutPosition(item);
        int orientation = recyclerView.getOrientation();
        int totalCount = Objects.requireNonNull(recyclerView.getAdapter()).getItemCount();

        if (orientation == HORIZONTAL || orientation == VERTICAL) {
            return position == totalCount - 1;
        } else {
            int cut = totalCount % spanCount;
            if (cut == 0) {
                return position >= totalCount - spanCount;
            } else {
                return position >= totalCount - cut;
            }
        }
    }

    /***
     * 是否最后一列
     * @return
     */
    private boolean isLastColumn(View item, LRecyclerView recyclerView) {
        int position = recyclerView.getChildAdapterPosition(item);
        int orientation = recyclerView.getOrientation();
        int totalCount = Objects.requireNonNull(recyclerView.getAdapter()).getItemCount();
        if (orientation == HORIZONTAL || orientation == VERTICAL) {
            return position == totalCount - 1;
        } else {
            return (position + 1) % spanCount == 0;
        }
    }
}
