package com.mirko.alsc.ui.wallet.online;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternLockerView;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityPatternCheckingBinding;
import com.mirko.alsc.databinding.ActivityPatternSettingBinding;
import com.mirko.alsc.ui.devote.DevoteHomeActivity;
import com.mirko.alsc.utils.pattern.PatternHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * Created by Mirko on 2020/01/01.
 * 邮箱验证
 */
public class PatternCheckingActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "PatternCheckingActivity";
    ActivityPatternCheckingBinding binding;
    private PatternHelper patternHelper = new PatternHelper();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pattern_checking);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.setClickListener(this);
        patternSetting();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(PatternCheckingActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {

    }


    /**
     * 下一步
     */
    private void next() {

    }

    private void patternSetting(){
        binding.patternLockView.setOnPatternChangedListener(new OnPatternChangeListener() {
            @Override
            public void onStart(@NotNull PatternLockerView patternLockerView) {

            }

            @Override
            public void onChange(@NotNull PatternLockerView patternLockerView, @NotNull List<Integer> list) {

            }

            @Override
            public void onComplete(@NotNull PatternLockerView patternLockerView, @NotNull List<Integer> list) {
                boolean isOk = isPatternOk(list);
                if (isOk){
                    startActivity(new Intent(PatternCheckingActivity.this, DevoteHomeActivity.class));
                    PatternCheckingActivity.this.finish();
                }
                updateView();
            }

            @Override
            public void onClear(@NotNull PatternLockerView patternLockerView) {
                if (patternHelper.isFinish()) {

                }
            }
        });
    }

    private boolean isPatternOk(List<Integer> hitIndexList){
        patternHelper.validateForChecking(hitIndexList);
        return patternHelper.isOk();
    }

    private void updateView(){
        binding.tvStatus.setText(patternHelper.getMessage());
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                next();
                break;
        }
    }
}
