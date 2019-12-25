package com.mirko.alsc.ui.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabWalletEntity implements CustomTabEntity {

        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabWalletEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        public TabWalletEntity(String title) {
            this.title = title;
        }

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
