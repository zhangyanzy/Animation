package com.zhangyan.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;


import com.zhangyan.animation.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPresenter(new Presenter());

    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.test_translate:
                    interpolator();
                    break;
                default:
                    break;
            }
        }
    }

    //透明属性
    private void alphaDemo() {
        Toast.makeText(getApplicationContext(), "透明", Toast.LENGTH_SHORT).show();
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.testImage, "alpha", 1f, 0f);
        animator.setDuration(5000);
        animator.start();
    }

    //旋转
    private void rotation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.testImage, "rotation", 0f, 360f, 0f);
        animator.setDuration(3000);
        animator.start();
    }

    //移除屏幕后在移动回来（按照X轴平移）
    private void translation() {
        //获取图片在X轴的位置
        float curTranslationX = binding.testImage.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.testImage, "translationX", curTranslationX, -500f, curTranslationX);
        animator.setDuration(5000);
        animator.start();
    }

    //缩放--放大
    private void scaleY() {
        //scaleX或者scaleY
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.testImage, "scaleX", 1f, 3f, 1f);
        animator.setDuration(3000);
        animator.start();
    }


    /**
     * set.play()//执行单个动画
     * set.playTogether(animation,animation,animation);//同时执行
     * set.playSequentially(animation,animation,animation);//依次执行
     * set.setDuration();
     * set.start();
     */

    //组合动画(集合)
    private void combinationAnimation() {
        ObjectAnimator mTranslationX = ObjectAnimator.ofFloat(binding.testImage, "translationX", -500f, 0f);
        ObjectAnimator mRotate = ObjectAnimator.ofFloat(binding.testImage, "rotation", 0f, 360f);
        ObjectAnimator mAlpha = ObjectAnimator.ofFloat(binding.testImage, "alpha", 1f, 0f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(mRotate).with(mAlpha).after(mTranslationX);
        set.setDuration(5000);
        set.start();
    }


    //同事开启多种动画

    //Animator监听器
    private void AnimatorListener() {
        float mTranslationX = binding.testImage.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.testImage, "translationX", mTranslationX, -500f, mTranslationX);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //动画开始时监听
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //动画结束时监听
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                //动画取消时监听
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                //动画重复时调用
            }
        });

        /**
         * 可以随意选择需要监听的内容
         */
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        //
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

            }
        });

    }

    //XML
    private void XMLTest() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_file);
        animator.setTarget(binding.testImage);
//        animator.setDuration(3000);
        animator.start();
    }

    /**
     * 抛物线效果
     * X轴：匀速
     * Y轴：加速度. Y = 1/2 *g *t*t(g(加速度的值  ))
     * 估值器实现
     */
    private void parabola() {
        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(8000);
        animator.setObjectValues(new PointF(0, 0));
        //估值器
        animator.setEvaluator(new TypeEvaluator<PointF>() {
            /**
             * @param fraction:百分比
             * @param startValue
             * @param endValue
             * @return
             */
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                //拿到每一个时间点的坐标
                PointF pointF = new PointF();
                pointF.x = 100f * (fraction * 4);//初始速度*执行的百分比
                pointF.y = 0.5f * 9.8f * (fraction * 4) * (fraction * 4);
                return pointF;
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                //得到时间点的坐标
                binding.testImage.setX(pointF.x);
                binding.testImage.setY(pointF.y);
            }
        });


        animator.start();
    }

    /**
     * 设置加速器
     */
    private void interpolator() {
//        float mTranslationX = binding.testImage.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.testImage, "translationY", 0f, 300);
        animator.setDuration(500 );
        //设置加速
//        animator.setInterpolator(new AccelerateInterpolator(5));
        //设置先加速后减速
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        // 回荡秋千的方式
//        animator.setInterpolator(new AnticipateInterpolator(8 ));
        //回弹 缓冲的效果
//        animator.setInterpolator(new OvershootInterpolator());
        //
        animator.start();
    }


}


















