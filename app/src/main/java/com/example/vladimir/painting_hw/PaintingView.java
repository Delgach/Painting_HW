package com.example.vladimir.painting_hw;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by vladimir on 24.04.17.
 */

public class PaintingView extends View {

    private Paint editModePaint = new Paint();
    private Bitmap mBitmap;
    private Canvas mCanvasBitmap;
    private SparseArray<PointF> mLastPoints = new SparseArray<>(10);


    private float x;
    private float y;



    private PointF lastPointRect;

    private boolean flagOfPen;
    private boolean flagOfRect;

    public PaintingView(Context context) {
        super(context);
        init();

    }

    public PaintingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public PaintingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaintingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    private void init() {
        if (getRootView().isInEditMode()) {
            editModePaint.setColor(Color.MAGENTA);
        }
        flagOfPen = true;
        flagOfRect = false;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.line_color));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.painting_line_width));
        editModePaint = paint;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w > 0 && h > 0){
           Bitmap bitmap =  Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
           Canvas canvas = new Canvas(bitmap);
            if(canvas != null){
                canvas.drawBitmap(bitmap, 0, 0, null);
               // bitmap.recycle();
            }
            mBitmap = bitmap;
            mCanvasBitmap = canvas;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                lastPointRect = new PointF(event.getX(event.getActionIndex()), event.getY(event.getActionIndex()));
                int pointerId = event.getPointerId(event.getActionIndex());
                mLastPoints.put(pointerId,new PointF(event.getX(event.getActionIndex()), event.getY(event.getActionIndex())));
                return true;
            case MotionEvent.ACTION_MOVE:
                for(int i = 0 ; i < event.getPointerCount(); i++){
                    PointF pointF = mLastPoints.get(event.getPointerId(i));
                     Paint paint = editModePaint;

                    if(mLastPoints != null ){
                         x = event.getX(i);
                         y = event.getY(i);
                        if(flagOfPen) {
                            mCanvasBitmap.drawLine(pointF.x, pointF.y, x, y, paint);
                        }
                        pointF.x = x;
                        pointF.y = y;
                    }
                }

                invalidate();

            case MotionEvent.ACTION_POINTER_UP:
                if(flagOfRect) {
                   //Drawable drawable = getResources().getDrawable(R.drawable.shape);
                    //drawable.setBounds((int)lastPointRect.x, (int)lastPointRect.y, (int)x, (int)y);
                    //drawable.draw(mCanvasBitmap);
                    mCanvasBitmap.drawRect(lastPointRect.x, lastPointRect.y, x, y, editModePaint);
                    invalidate();
                }
                return true;
            case MotionEvent.ACTION_UP:
                mLastPoints.clear();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isInEditMode()){
            canvas.drawRect(0.1f * getWidth(), 0.1f * getHeight(), 0.9f * getWidth(), 0.9f * getHeight(), editModePaint );
        }
        canvas.drawBitmap(mBitmap,0,0,null);
    }

    public void clear(){

        mCanvasBitmap.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
        invalidate();
    }
    public void beRect(){
        flagOfRect = true;
        flagOfPen = false;
    }
    public void bePen(){
        flagOfPen = true;
        flagOfRect = false;
    }
}
