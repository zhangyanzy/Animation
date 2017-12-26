package com.zhangyan.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
                    XMLTest();
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

    //组合动画
    private void combinationAnimation() {
        ObjectAnimator mTranslationX = ObjectAnimator.ofFloat(binding.testImage, "translationX", -500f, 0f);
        ObjectAnimator mRotate = ObjectAnimator.ofFloat(binding.testImage, "rotation", 0f, 360f);
        ObjectAnimator mAlpha = ObjectAnimator.ofFloat(binding.testImage, "alpha", 1f, 0f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(mRotate).with(mAlpha).after(mTranslationX);
        set.setDuration(5000);
        set.start();

    }

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
         * 可以随意选择需要箭筒的内容
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


}

