package com.zhangyan.animation.PropertyAnimation;

import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhangyan.animation.R;
import com.zhangyan.animation.databinding.ActivitySafariBinding;

public class SafariActivity extends AppCompatActivity {

    private ActivitySafariBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_safari);
        binding.setPresenter(new Presenter());
    }

    class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.start_btn:

                    break;
                default:
                    break;
            }
        }
    }
    private void startFristAnimation(){
        /**
         * 1.旋转动画
         * 2.透明度动画
         * 3.缩放动画
         */
        ObjectAnimator mFristRotation = ObjectAnimator.ofFloat(binding.firstImageView, "rotationX", 0f, 25f);
        mFristRotation.setDuration(200);
        mFristRotation.start();

    }
}
