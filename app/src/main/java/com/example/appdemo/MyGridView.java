package com.example.appdemo;

import android.util.AttributeSet;
import android.widget.GridView;
import android.content.Context;
import android.view.MotionEvent;

public class MyGridView extends GridView {
//    private OnTouchInvalidPositionListener onTouchInvalidPositionListener;

    public MyGridView(Context context) {
        super(context);
    }
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int expandSpec= MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec,expandSpec);
    }

}
