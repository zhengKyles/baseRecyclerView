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

    private int spanCount = 2;//当Grid时，列数
    private Drawable mDivider = null;//分割线颜色、图片等

    private PagerItemDecoration itemDecoration;

    public Builder builder = new Builder(this);

    public LRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LRecyclerView);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.LRecyclerView_divider_width_horizontal) {
                dividerHorizontal = ((int) a.getDimension(attr, 0));
            } else if (attr == R.styleable.LRecyclerView_divider_height_vertical) {
                dividerVertical = ((int) a.getDimension(attr, 0));
            } else if (attr == R.styleable.LRecyclerView_span_count) {
                spanCount = (a.getInt(attr, 2));
            } else if (attr == R.styleable.LRecyclerView_recycler_divider) {
                mDivider = (a.getDrawable(attr));
            } else if (attr == R.styleable.LRecyclerView_direction) {
                mOrientation = (a.getInt(attr, VERTICAL));
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


    public class Builder {
        private LRecyclerView recyclerView;

        public Builder(LRecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }


        /***
         * 设置分割线样式
         * @param mDivider
         */
        public Builder setDivider(Drawable mDivider) {
            recyclerView.mDivider = mDivider;
            return this;
        }

        /***
         * 设置水平分割线
         * @param dividerHorizontal
         */
        public Builder setDividerHorizontal(int dividerHorizontal) {
            recyclerView.dividerHorizontal = dividerHorizontal;
            return this;
        }

        /***
         * 设置竖直分割线
         * @param dividerVertical
         */
        public Builder setDividerVertical(int dividerVertical) {
            recyclerView.dividerVertical = dividerVertical;
            return this;
        }

        /***
         * 设置方向
         * @param orientation
         */
        public Builder setOrientation(@OrientationMode int orientation) {
            recyclerView.mOrientation = orientation;
            return this;
        }

        /***
         * 设置grid时，列数
         * @param spanCount
         */
        public Builder setSpanCount(int spanCount) {
            if(spanCount>=2) {
                recyclerView.spanCount = spanCount;
            }
            return this;
        }

        public void build() {
            recyclerView.initView();
            recyclerView.requestView();
        }
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
            for (int i = 0; i < childCount; i++) {

                final View child = parent.getChildAt(i);

                final LayoutParams params =
                        (LayoutParams) child.getLayoutParams();

                left = child.getRight() + params.rightMargin;
                right = left + dividerHorizontal;
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

        /***
         * 最终是为了设置item的padding
         * @param outRect
         * @param view
         * @param parent
         * @param state
         */
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
            int bottom = dividerVertical;
            int right = dividerHorizontal;
            //是否是最后一行
            boolean isLastRow=isLastRow(view, (LRecyclerView) parent);;
            //是否是最后一列
            boolean isLastColumn = isLastColumn(view, (LRecyclerView) parent);
            if (mOrientation == GRID) {
                //如果是最后一行，则bottomPadding设为0
                if (isLastRow) {
                    bottom = 0;
                }
                int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

                int left=0;
                //将横向的所有间隔总和分成spanCount份，比如spanCount为3，dividerHorizontal为30dp,那么总和为30*(3-2）=60,分成3份60/3=20
                int eachPadding = (spanCount - 1) * dividerHorizontal / spanCount;
                //一行三个item分别设置padding
                // 规则是:每个item的padding左右加起来一致为:20,且item之间的间隔固定为30
                // 第一个设置right为20，所以第二个设置left为1*(30-20)=10，right为20-10=10，第三个设置left为2*(30-20)=20,right设置为0
                //以起到所有item宽度相等，且分割区域宽度相等
                left=itemPosition % spanCount * (getDividerHorizontal() - eachPadding);
                right = eachPadding - left;
                outRect.set(left, 0, right, bottom);
            } else if (mOrientation == HORIZONTAL) {
                if (!isLastColumn) {
                    outRect.set(0, 0, right, 0);
                }
            } else {
                if (!isLastRow) {
                    outRect.set(0, 0, 0, bottom);
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
