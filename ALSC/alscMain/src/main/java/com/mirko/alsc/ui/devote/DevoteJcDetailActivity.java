package com.mirko.alsc.ui.devote;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alsc.net.api.DevoteCreatApi;
import com.alsc.net.api.DevoteJackpotInfoApi;
import com.alsc.net.api.DevoteSharedInfoApi;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.entity.JackPotResultEntity;
import com.alsc.net.bean.entity.SharedResultEntity;
import com.alsc.net.bean.request.ListPageRequest;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.DevoteDetailListAdapter;
import com.mirko.alsc.bean.DevoteData;
import com.mirko.alsc.databinding.ActivityDevoteHomeBinding;
import com.mirko.alsc.databinding.ActivityDevoteJcDetailBinding;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;


/**
 * Created by Mirko on 2019/12/28.
 * 奖池明细
 */
public class DevoteJcDetailActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "DevoteJcDetailActivity";
    ActivityDevoteJcDetailBinding binding;

    private List<DevoteData> devoteDatas = new ArrayList<>();
    private int requestPagerNumber = 8; //每次请求 每页请求的条数
    private int requestPager = 1; //每次请求的页数，下来加载的时候页数增加
    private boolean loadItemVisibled = false;//确保上拉加载只执行一次
    private DevoteDetailListAdapter adapter;
    private View footView;
    private TextView tvFooterMsg;//底部显示的信息

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_devote_jc_detail);
        footView = LayoutInflater.from(this).inflate(R.layout.list_footer_view, binding.lvDevoteDetail, false);
        footView.setVisibility(View.GONE);
        tvFooterMsg = (TextView) footView.findViewById(R.id.tv_footer_msg);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.setClickListener(this);
        binding.rgJd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rb_cjjd){
                    binding.llCjjd.setVisibility(View.VISIBLE);
                    binding.llCjjdTitle.setVisibility(View.VISIBLE);
                    binding.llZjd.setVisibility(View.GONE);
                    binding.llZjdTitle.setVisibility(View.GONE);
                    devoteDatas.clear();
                    getJackPotDetailDatas(0,1);
                }else{
                    binding.llCjjd.setVisibility(View.GONE);
                    binding.llCjjdTitle.setVisibility(View.GONE);
                    binding.llZjd.setVisibility(View.VISIBLE);
                    binding.llZjdTitle.setVisibility(View.VISIBLE);
                    devoteDatas.clear();
                    getJackPotDetailDatas(0,2);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(DevoteJcDetailActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
        initListView();
    }

    @Override
    public void loadData() {
        getJackPotDetailDatas(0,1);
        loadMoreData();
    }


    /**
     * 初始化ListView
     */
    private void initListView() {

        adapter = new DevoteDetailListAdapter(this, devoteDatas);
        binding.lvDevoteDetail.setAdapter(adapter);
        binding.lvDevoteDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                LogUtils.d(TAG, "item点击：" + position);
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
                            getJackPotDetailDatas(requestPager,1);
                        }
                    }
                }, 100);
            }
        });
    }


    private void getJackPotDetailDatas(int pageNo,int type) {

        ListPageRequest request = new ListPageRequest();
        request.setToken(ComUtils.getTokenCache());
        request.setPage_size(requestPagerNumber);
        request.setPage_index(pageNo);
        request.setType(type);

        HttpManager.getInstance().doHttpDeal(new DevoteJackpotInfoApi((new HttpOnNextListener<JackPotResultEntity>() {

            @Override
            public void onNext(JackPotResultEntity result) {
                LogUtils.d(TAG, "获取列表成功：");
                binding.tvFhTotal.setText(result.getTotal_income()+"");
                binding.tvJcTotal.setText(result.getTotal_alsc()+"");
                binding.tvSyTotal.setText(result.getTotal_ach()+"");
                if (result != null && result.getList().size() > 0) {
                    footView.setVisibility(View.GONE);
                    if (result.getList().size() <= requestPagerNumber) { //数据不够时，说明已经到底了
                        loadItemVisibled = true;
                        tvFooterMsg.setText(getResources().getString(R.string.list_footer_no_data));
                    } else {
                        tvFooterMsg.setText(getResources().getString(R.string.list_footer_loading));
                        loadItemVisibled = false;
                    }
                    devoteDatas.clear();
                    for (int i = 0; i < result.getList().size(); i++) {
                        DevoteData data = new DevoteData();
                        data.setNumber_one(result.getList().get(i).getAdd_time());
                        data.setNumber_two(result.getList().get(i).getFree());
                        data.setNumber_three(result.getList().get(i).getIncome());
                        devoteDatas.add(data);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    loadItemVisibled = true;
                    adapter.notifyDataSetChanged();
                }
                if (devoteDatas.size() > 0) {
                    binding.loadMore.setVisibility(View.VISIBLE);
                    binding.llNoData.setVisibility(View.GONE);
                } else {
                    binding.loadMore.setVisibility(View.GONE);
                    binding.llNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                LogUtils.d(TAG, "获取失败：" + e.toString());
            }

            @Override
            public void onCacheNext(String string) {
            }

        }), DevoteJcDetailActivity.this, request));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG, "返回点击");
                onBackPressed();

            case R.id.btn_devote_creat:

            case R.id.btn_exchange:
            case R.id.btn_super:

        }
    }
}
