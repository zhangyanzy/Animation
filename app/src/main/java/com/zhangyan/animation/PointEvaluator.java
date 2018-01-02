package com.zhangyan.animation;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;

/**
 * Created by Administrator on 2017/12/26.
 */

public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point mStartPoint = (Point) startValue;
        Point mEndPoint = (Point) endValue;
        float x = mStartPoint.getX() + fraction * (mEndPoint.getX() - mStartPoint.getX());
        float y = mStartPoint.getX() + fraction * (mEndPoint.getY() - mStartPoint.getX());
        Point mPoint = new Point(x, y);
        return mPoint;
    }

    Point mPoint1 = new Point(0, 0);
    Point mPoint2 = new Point(300, 300);
    ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), mPoint1, mPoint2);

}
