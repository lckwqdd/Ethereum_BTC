package com.mirko.alsc.ui.wallet.address;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alsc.net.db.bean.ContactBean;
import com.alsc.net.db.helper.ContactBeanHelper;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.entity.AddAddressBean;
import com.alsc.wallet.entity.AddWalletBean;
import com.alsc.wallet.utils.ToastUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.AddAddressAdapter;
import com.mirko.alsc.adapter.AddWalletAdapter;
import com.mirko.alsc.databinding.ActivityAddAddressBinding;
import com.mirko.alsc.ui.wallet.ETHTransferActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加联系人
 */
public class AddAddressActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityAddAddressBinding binding;
    private AddAddressAdapter adapter;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_address);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.addreess));
        binding.btnNext.setOnClickListener(this);
        binding.commonHeader.tvHeaderRight.setOnClickListener(this);
    }

    @Override
    public void initAttrs() {
        if (ContactBeanHelper.getInstance().getCount(ContactBean.class) == 0) {
            binding.llFristContact.setVisibility(View.VISIBLE);
            binding.btnNext.setVisibility(View.VISIBLE);
            binding.commonHeader.tvHeaderRight.setVisibility(View.GONE);
            binding.addressRv.setVisibility(View.GONE);
        } else {
            binding.llFristContact.setVisibility(View.GONE);
            binding.btnNext.setVisibility(View.GONE);
            binding.commonHeader.tvHeaderRight.setVisibility(View.VISIBLE);
            binding.commonHeader.tvHeaderRight.setText(getString(R.string.addreess_add));
            binding.addressRv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.addressRv.setLayoutManager(layoutManager);
        adapter = new AddAddressAdapter(R.layout.list_item_add_address, genrateData());
        adapter.setOnItemClickListener(((adapter1, view, position) -> {
            ToastUtils.showToast("position:" + position);
            switch (position) {
                case 0:
                    goTo(ETHTransferActivity.class);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;

            }
        }));
        binding.addressRv.setAdapter(adapter);
    }

    private List<AddAddressBean> genrateData() {
        List<AddAddressBean> addAddressBeanList = new ArrayList<>();

        addAddressBeanList.add(new AddAddressBean("刘某某", "fdfdfdf", "刘某某的alsc收款地址"));
        addAddressBeanList.add(new AddAddressBean("刘某某", "fdfdfdf", "刘某某的alsc收款地址"));
        return addAddressBeanList;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                goTo(AddContactsActivity.class);
                finish();
                break;
            case R.id.tv_header_right:
                goTo(AddContactsActivity.class);
                finish();
                break;
        }
    }
}
