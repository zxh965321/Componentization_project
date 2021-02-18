package com.karson.lib_commen.ui.wight.viewpager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @Author karson
 * @Date 2020/12/31-18:13
 * @desc 支持十几个以上碎片不会出现页面加载出错
 */
public class SupportViewPager extends ViewPager {

    private SupportViewPagerHelper helper ;
    public SupportViewPager(@NonNull Context context) {
        super(context);
    }

    public SupportViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        helper = new SupportViewPagerHelper(this);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item,true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        MScroller scroller=helper.getScroller();
        if(Math.abs(getCurrentItem()-item)>1){
            scroller.setNoDuration(true);
            super.setCurrentItem(item, smoothScroll);
            scroller.setNoDuration(false);
        }else{
            scroller.setNoDuration(false);
            super.setCurrentItem(item, smoothScroll);
        }
    }
}
