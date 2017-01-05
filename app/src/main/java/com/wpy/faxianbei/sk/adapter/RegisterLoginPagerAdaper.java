package com.wpy.faxianbei.sk.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RegisterLoginPagerAdaper extends PagerAdapter {

        private List<View> mView;

        public RegisterLoginPagerAdaper(List<View> mView) {
            this.mView = mView;
        }

        @Override
        public int getCount() {
            return mView.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(mView.get(position), ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return mView.get(position);
        }
    }