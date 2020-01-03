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
import com.mirko.alsc.databinding.ActivityPatternSettingBinding;
import com.mirko.alsc.utils.pattern.PatternHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;
import org.greenrobot.greendao.annotation.NotNull;
import java.util.List;


/**
 * Created by Mirko on 2019/12/24.
 * 邮箱验证
 */
public class PatternSettingActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "PatternSettingActivity";
    ActivityPatternSettingBinding binding;
    private PatternHelper patternHelper = new PatternHelper();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pattern_setting);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.setClickListener(this);
        patternSetting();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(PatternSettingActivity.this, getResources().getColor(R.color.color_slide_bg));
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

                }
                updateView();
            }

            @Override
            public void onClear(@NotNull PatternLockerView patternLockerView) {
                if (patternHelper.isFinish()) {
                    startActivity(new Intent(PatternSettingActivity.this, PatternCheckingActivity.class));
                    PatternSettingActivity.this.finish();
                }
            }
        });
    }

    private boolean isPatternOk(List<Integer> hitIndexList){
        patternHelper.validateForSetting(hitIndexList);
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
