package com.android.ctrip.utils;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import static com.android.ctrip.constant.controlClassName.fightListClassName;
import static com.android.ctrip.constant.controlResId.dateResId;
import static com.android.ctrip.constant.controlResId.listPageArrivalResId;
import static com.android.ctrip.constant.controlResId.listPageDepartCityResId;
import static com.android.ctrip.constant.controlText.backFightListFirstText;
import static com.android.ctrip.constant.controlText.fightListFirstText;
import static com.android.ctrip.constant.controlText.goFightListFirstText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class assertUtils {
    //判断出发地是否正确
    public static void judgeDepartCity(UiDevice mDevice,String expectCity) throws UiObjectNotFoundException {
        String result = mDevice.findObject(new UiSelector().descriptionContains(listPageDepartCityResId)).getText();
        assertThat("出发地检测",result,is(expectCity));
    }

    //判断目的地是否正确
    public static void judgeArrivalCity(UiDevice mDevice,String expectCity) throws UiObjectNotFoundException {
        String result = mDevice.findObject(new UiSelector().descriptionContains(listPageArrivalResId)).getText();
        assertThat("目的地检测",result,is(expectCity));
    }

    //判断所选日期是否正确
    public static void judgeTime(UiDevice mDevice,String date) throws UiObjectNotFoundException {
        String result = mDevice.findObject(new UiSelector().resourceId(dateResId)).getText();
        assertThat("日期检测",result,is(equalTo(date)));
    }

    //判断是否存在一条航班
    public static void JudgeOneWayFightNum(UiDevice mDevice,boolean isExist) {
       boolean result = mDevice.findObject(new UiSelector().className(fightListClassName).resourceId(fightListFirstText)).exists();
       assertThat("航班查询结果检测",result,is(isExist));
    }


    //判断往返航班
    public static void JudgeRoundWayFightNum(UiDevice mDevice,boolean isExist) {
        boolean result = mDevice.findObject(new UiSelector().descriptionContains(goFightListFirstText)).exists();
        System.out.println("result=" + result);
        assertThat("去程航班查询结果检测",result,is(isExist));
        assertThat("返程航班查询结果检测",mDevice.findObject(new UiSelector().descriptionContains(backFightListFirstText)).exists(),is(isExist));
    }
}
