package com.mirko.alsc.ui.slide;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alsc.net.api.NoticeApi;
import com.alsc.net.bean.NoticeResult;
import com.alsc.net.bean.entity.NoticeResultEntity;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.NoticeListAdapter;
import com.mirko.alsc.databinding.ActivitySystemNoticeBinding;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;


/**
 * Created by Mirko on 2019/12/22.
 * 系统通知
 */
public class SystemNoticeActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "SystemNoticeActivity";
    ActivitySystemNoticeBinding binding;
    private int requestPagerNumber = 8; //每次请求 每页请求的条数
    private int requestPager = 1; //每次请求的页数，下来加载的时候页数增加
    private boolean loadItemVisibled = false;//确保上拉加载只执行一次
    private NoticeListAdapter adapter;
    private List<NoticeResult> datas = new ArrayList<>();
    private View footView;
    private TextView tvFooterMsg;//底部显示的信息


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_system_notice);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);
        footView = LayoutInflater.from(SystemNoticeActivity.this).inflate(R.layout.list_footer_view, binding.lvNotice, false);
        footView.setVisibility(View.GONE);
        tvFooterMsg = (TextView) footView.findViewById(R.id.tv_footer_msg);
        initListView();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(SystemNoticeActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {
        getNoticeDatas(Constant.NOTICE_TYPE_SYSTEM,0);
        loadMoreData();
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        adapter = new NoticeListAdapter(SystemNoticeActivity.this, datas);
        binding.lvNotice.setAdapter(adapter);
        binding.lvNotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                LogUtils.d(TAG,"item点击："+ position);
            }
        });
    }

    /**
     * 上拉加载数据
     */
    private void loadMoreData() {

        binding.loadMore.setLoadMoreView(footView);
        //添加加载更多的事件监听
        binding.loadMore.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                footView.setVisibility(View.VISIBLE);
                binding.loadMore.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.loadMore.loadMoreFinish(false, true);
                        if (!loadItemVisibled) {
                            loadItemVisibled = true;
                            requestPager++;
                            getNoticeDatas(Constant.NOTICE_TYPE_SYSTEM,requestPager);
                        }
                    }
                }, 100);
            }
        });
    }

    /**
     * 获取系统通知列表
     */

    private void getNoticeDatas(int type ,int pageNo){

        HttpManager.getInstance().doHttpDeal(new NoticeApi((new HttpOnNextListener<NoticeResultEntity>() {

            @Override
            public void onNext(NoticeResultEntity result) {
                LogUtils.d(TAG,"获取公告列表成功：");
                if (result != null && result.getList().size() > 0){
                    LogUtils.d(TAG,"获取公告数："+result.getList().size());
                    footView.setVisibility(View.GONE);
                    if(result.getList().size() < requestPagerNumber){ //数据不够时，说明已经到底了
                        loadItemVisibled =  true;
                        tvFooterMsg.setText(getResources().getString(R.string.list_footer_no_data));
                    }else{
                        tvFooterMsg.setText(getResources().getString(R.string.list_footer_loading));
                        loadItemVisibled =  false;
                    }
                    datas.addAll(result.getList());
                    adapter.notifyDataSetChanged();
                }else{
                    loadItemVisibled = true;
                    adapter.notifyDataSetChanged();
                }
                if (datas.size()>0){
                    binding.loadMore.setVisibility(View.VISIBLE);
                    binding.llNoData.setVisibility(View.GONE);
                }else{
                    binding.loadMore.setVisibility(View.GONE);
                    binding.llNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                LogUtils.d(TAG,"获取当前群公告列表失败："+ e.toString());
            }

            @Override
            public void onCacheNext(String string) {
                LogUtils.d(TAG,"获取当前群公告列表缓存：");
            }

        }), SystemNoticeActivity.this ,type, requestPagerNumber,pageNo));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG,"返回点击");
                onBackPressed();
                break;
        }
    }
}
