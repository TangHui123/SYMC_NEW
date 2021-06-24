package com.sany.pumpcarinfo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

/**
 * @author TangHui
 * @date 2021/6/21
 */

public class ArmView extends View {
    private static final String TAG = "ArmView";
    private Point mPoint0, mPoint1, mPoint2, mPoint3, mPoint4, mPoint5, mPoint6, mPoint7, mPoint8;
    private Matrix mMatrix0, mMatrix1, mMatrix2, mMatrix3, mMatrix4, mMatrix5, mMatrix6, mMatrix7;
    private Paint mPaint;
    private int mWidth, mHeight;
    private float mRotate, mAngle1, mAngle2, mAngle3, mAngle4, mAngle5, mAngle6, mAngle7;
    private int mArmLen1, mArmLen2,mArmLen3,mArmLen4,mArmLen5,mArmLen6,mArmLen7;
    private int mStrokeWidth = 8;
    public ArmView(Context context) {
        super(context);
        initData();
    }

    public ArmView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public ArmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        //test
        mRotate = 0;
        mAngle1 = 30;
        mAngle2 = 150;
        mAngle3 = 180;
        mAngle4 = 180;
        mAngle5 = 180;
        mAngle6 = 180;
        mAngle7 = 180;
        //end
        mArmLen1 = 100;
        mArmLen2 = 80;
        mArmLen3 = 80;
        mArmLen4 = 80;
        mArmLen5 = 50;
        mArmLen6 = 50;
//        mArmLen7 = 50;
        mPoint0 = new Point();
        mPoint1 = new Point();
        mPoint2 = new Point();
        mPoint3 = new Point();
        mPoint4 = new Point();
        mPoint5 = new Point();
        mPoint6 = new Point();
        mPoint7 = new Point();
        mMatrix0 = new Matrix();
        mMatrix1 = new Matrix();
        mMatrix2 = new Matrix();
        mMatrix3 = new Matrix();
        mMatrix4 = new Matrix();
        mMatrix5 = new Matrix();
        mMatrix6 = new Matrix();
        mMatrix7 = new Matrix();
        mWidth = 0;
        mHeight = 0;
        mPaint = new Paint();
        mStrokeWidth = dip2px(getContext(), 4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(Color.GRAY);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = getWidth();
                mHeight = getHeight();
                mPoint0.x = dip2px(getContext(), mHeight/20);
                mPoint0.y = mHeight - dip2px(getContext(), mHeight/20);
                Log.i(TAG, "size mWidth=" + mWidth +",mHeight=" + mHeight);
                int drawLen = mHeight - dip2px(getContext(), mHeight/20)- dip2px(getContext(), 5);
                int sum = 44; //49
                mArmLen1 = drawLen * 10 / sum;
                mArmLen2 = drawLen * 8 / sum;
                mArmLen3 = drawLen * 8 / sum;
                mArmLen4 = drawLen * 8 / sum;
                mArmLen5 = drawLen * 6 / sum;
                mArmLen6 = drawLen * 6 / sum;
//                mArmLen7 = drawLen * 6 / sum;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mWidth == 0 || mHeight == 0) {
            mWidth = getWidth();
            mHeight = getHeight();
            mPoint0.x = dip2px(getContext(), mHeight/20);
            mPoint0.y = mHeight - dip2px(getContext(), mHeight/20);
            Log.i(TAG, "onDraw size mWidth=" + mWidth +",mHeight=" + mHeight);
            int drawLen = mHeight - dip2px(getContext(), mHeight/20) - dip2px(getContext(), 5);
            int sum = 44; //49
            mArmLen1 = drawLen * 10 / sum;
            mArmLen2 = drawLen * 8 / sum;
            mArmLen3 = drawLen * 8 / sum;
            mArmLen4 = drawLen * 8 / sum;
            mArmLen5 = drawLen * 6 / sum;
            mArmLen6 = drawLen * 6 / sum;
//            mArmLen7 = drawLen * 6 / sum;
        }
        int bottomLen = dip2px(getContext(), mHeight/20);
        Path pathBottom = new Path();
        pathBottom.moveTo(0, mHeight);
        pathBottom.lineTo(bottomLen*2, mHeight);
        pathBottom.lineTo(bottomLen, mHeight - bottomLen);
        pathBottom.close();
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(pathBottom, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);

        mMatrix0.postTranslate(mPoint0.x, mPoint0.y);

        mPoint1.x = mPoint0.x;
        mPoint1.y = mPoint0.y;
        mPoint2 = getRotatePoint((int) -mAngle1, mPoint1, new Point(mPoint1.x + mArmLen1, mPoint1.y));
        Point tmp = getRotatePoint((int)(180 - mAngle1), mPoint2, new Point(mPoint2.x + mArmLen2, mPoint2.y));
        mPoint3 = getRotatePoint((int) -mAngle2, mPoint2, tmp);
        mPoint4 = getRotatePoint((int) -mAngle3, mPoint3, mPoint2);

        mPoint5 = getRotatePoint((int) (360-mAngle4), mPoint4, mPoint3);

        int xAngle = getXAngle(mPoint4, mPoint5);
        Point tmp2 = new Point();
        double radian = xAngle * Math.PI / 180;
        tmp2.x = (int) (mPoint5.x - mArmLen5*Math.cos(radian));
        tmp2.y = (int) (mPoint5.y - mArmLen5*Math.sin(radian));
        mPoint6 = getRotatePoint((int) -mAngle5, mPoint5, tmp2);
        mPoint7 = getRotatePoint((int) -mAngle6, mPoint6, mPoint5);
        mPoint8 = getRotatePoint((int) -mAngle7, mPoint7, mPoint6);

        Path path = new Path();
        path.moveTo(mPoint1.x, mPoint1.y);
        path.lineTo(mPoint2.x, mPoint2.y);
        path.lineTo(mPoint3.x, mPoint3.y);
        path.lineTo(mPoint4.x, mPoint4.y);
        path.lineTo(mPoint5.x, mPoint5.y);
        path.lineTo(mPoint6.x, mPoint6.y);
        path.lineTo(mPoint7.x, mPoint7.y);
//        path.lineTo(mPoint8.x, mPoint8.y);
        canvas.drawPath(path, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(mPoint0.x, mPoint0.y, mStrokeWidth, mPaint);
        canvas.drawCircle(mPoint1.x, mPoint1.y, mStrokeWidth, mPaint);
        canvas.drawCircle(mPoint2.x, mPoint2.y, mStrokeWidth, mPaint);
        canvas.drawCircle(mPoint3.x, mPoint3.y, mStrokeWidth, mPaint);
        canvas.drawCircle(mPoint4.x, mPoint4.y, mStrokeWidth, mPaint);
        canvas.drawCircle(mPoint5.x, mPoint5.y, mStrokeWidth, mPaint);
        canvas.drawCircle(mPoint6.x, mPoint6.y, mStrokeWidth, mPaint);
//        canvas.drawCircle(mPoint7.x, mPoint7.y, mStrokeWidth, mPaint);

    }

    private Point getRotatePoint(int angle, Point point0, Point point) {
        double x = point.x;
        double y = point.y;
        double dx = point0.x;
        double dy = point0.y;
        //一个点(x,y)绕任意点(dx,dy)顺时针旋转a度后的坐标
        double xx = (x - dx)*Math.cos(angle* Math.PI / 180) - (y - dy)*Math.sin(angle* Math.PI / 180) + dx;
        double yy = (x - dx)*Math.sin(angle* Math.PI / 180) + (y - dy)*Math.cos(angle* Math.PI / 180) + dy;

        Point pot = new Point();
        pot.x = (int) xx;
        pot.y = (int) yy;
        Log.i(TAG,"getRotatePoint pot=" + pot);
        return pot;
    }

    private int getXAngle(Point p1, Point p2) {
        double angle = 0;
//        p1 = new Point(40,30);
//        p2 = new Point(80,60);
        angle= Math.atan2((p2.y-p1.y), (p2.x-p1.x)); //弧度  0.6435011087932844
        double theta = angle*(180/Math.PI); //角度  36.86989764584402
        Log.i(TAG,"getXAngle angle=" + angle +",theta=" + theta);
        return (int) theta;
    }

    public void setArmAngle(double rotate, double arm1, double arm2, double arm3, double arm4, double arm5, double arm6, double arm7) {
        mRotate = (float) rotate;
        mAngle1 = (float) arm1;
        mAngle2 = (float) arm2;
        mAngle3 = (float) arm3;
        mAngle4 = (float) arm4;
        mAngle5 = (float) arm5;
        mAngle6 = (float) arm6;
        mAngle7 = (float) arm7;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(final Context context, final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

