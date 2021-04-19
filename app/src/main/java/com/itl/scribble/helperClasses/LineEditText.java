package com.itl.scribble.helperClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class LineEditText extends androidx.appcompat.widget.AppCompatEditText {
    private Rect mRect;
    private Paint mPaint;

    public LineEditText(Context context, AttributeSet attrs) {
        super(context);
        mRect=new Rect();
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0x800000FF);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count=getLineCount();
        Rect r=mRect;
        Paint paint=mPaint;

        for (int i=0;i<count;i++){
            int baseline=getLineBounds(i,r);
            canvas.drawLine(r.left,baseline+15,r.right,baseline+15,paint);
        }
        super.onDraw(canvas);
    }
}
