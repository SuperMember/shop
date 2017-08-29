package com.example.lenovo.taoshop.widget.animation;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by lenovo on 2017  五月  14  0014.
 */
//赛贝尔曲线
public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF p1;

    public BezierEvaluator(PointF p1) {
        this.p1 = p1;
    }

    @Override
    public PointF evaluate(float t, PointF p0, PointF p2) {
        PointF point = new PointF();
        float temp = 1 - t;
        point.x = temp * temp * p0.x + 2 * t * temp * p1.x + t * t * p2.x;
        point.y = temp * temp * p0.y + 2 * t * temp * p1.y + t * t * p2.y;
        return point;
    }
}
