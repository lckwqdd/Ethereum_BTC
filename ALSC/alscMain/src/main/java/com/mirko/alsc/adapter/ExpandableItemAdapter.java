package com.mirko.alsc.adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.alsc.wallet.utils.LogUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mirko.alsc.R;
import com.mirko.alsc.entity.Level0Item;
import com.mirko.alsc.entity.Level1Item;
import java.util.List;

/**
 * Created by WuQuan on 2019/12/18.
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;


    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.list_item_total_assets);
        addItemType(TYPE_LEVEL_1, R.layout.list_item_alsc_assets);
    }


    @Override
    protected void convert(@NonNull final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final Level0Item lv0 = (Level0Item) item;
                LogUtils.d("分屏扩展功能:"+ GsonUtils.toJson(lv0));
                holder.setImageResource(R.id.iv_currenty, lv0.getSymbolPicture())
                        .setText(R.id.symbol, lv0.getSymbolNmae())
                        .setText(R.id.balance, lv0.getSymbolBalcance())
                        .setText(R.id.tv_property_cny, lv0.getSymbolValue());
//                      .setImageResource(R.id.iv_expand,lv0.isExpanded()?R.mipmap.arrow_b:R.mipmap.arrow_r);
//                      .setImageResource(R.id.iv_expand,R.mipmap.arrow_r);
                holder.itemView.setOnClickListener((v -> {
                    int pos = holder.getAdapterPosition();
                    Log.d(TAG, "Level 0 item pos: " + pos);
                    if (lv0.isExpanded()) {
                        collapse(pos);
                    } else {
                        expand(pos);
                    }
                }));
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                holder.setText(R.id.tv_has_alsc_address, lv1.getAlscName())
                        .setText(R.id.tv_make_alsc_address, lv1.getAlscAddress())
                        .setText(R.id.balance, lv1.getAlscBalance())
                        .setText(R.id.tv_property_cny, lv1.getAlscValue());
                break;
            default:
                break;
        }
    }
}
