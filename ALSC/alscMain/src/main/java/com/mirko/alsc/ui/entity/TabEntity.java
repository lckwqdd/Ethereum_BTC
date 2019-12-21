package com.mirko.alsc.ui.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {

    public String titleSlected;
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String titleSlected,String title, int selectedIcon, int unSelectedIcon) {

        this.titleSlected = titleSlected;
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    public TabEntity(String title) {
        this.title = title;
    }

//    @Override
//    public String getTabSlectedTitle() {
//        return titleSlected;
//    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
