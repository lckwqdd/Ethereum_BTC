package com.mirko.alsc.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mirko.alsc.adapter.ExpandableItemAdapter;

/**
 * Created by luoxw on 2016/8/10.
 */

public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity {

    private int symbolPicture;
    private String symbolNmae;
    private String symbolBalcance;
    private String symbolValue;

    public Level0Item(int symbolPicture, String symbolNmae, String symbolBalcance, String symbolValue) {
        this.symbolPicture = symbolPicture;
        this.symbolNmae = symbolNmae;
        this.symbolBalcance = symbolBalcance;
        this.symbolValue = symbolValue;
    }

    public int getSymbolPicture() {
        return symbolPicture;
    }

    public void setSymbolPicture(int symbolPicture) {
        this.symbolPicture = symbolPicture;
    }

    public String getSymbolNmae() {
        return symbolNmae;
    }

    public void setSymbolNmae(String symbolNmae) {
        this.symbolNmae = symbolNmae;
    }

    public String getSymbolBalcance() {
        return symbolBalcance;
    }

    public void setSymbolBalcance(String symbolBalcance) {
        this.symbolBalcance = symbolBalcance;
    }

    public String getSymbolValue() {
        return symbolValue;
    }

    public void setSymbolValue(String symbolValue) {
        this.symbolValue = symbolValue;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }
}