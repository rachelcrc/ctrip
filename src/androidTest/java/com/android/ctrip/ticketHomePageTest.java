package com.android.ctrip;

import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.android.ctrip.constant.controlClassName.internalTabClassName;
import static com.android.ctrip.constant.controlClassName.internationalTabClassName;
import static com.android.ctrip.constant.controlResId.arrivalCityResId;
import static com.android.ctrip.constant.controlResId.chooseClassResId;
import static com.android.ctrip.constant.controlResId.chooseHotileResId;
import static com.android.ctrip.constant.controlResId.cityListCloseResId;
import static com.android.ctrip.constant.controlResId.citySearchInputResId;
import static com.android.ctrip.constant.controlResId.departCityResId;
import static com.android.ctrip.constant.controlResId.exchangeCityResId;
import static com.android.ctrip.constant.controlText.citySearchInputText;
import static com.android.ctrip.constant.controlText.internalTabText;
import static com.android.ctrip.constant.controlText.internationalTabText;
import static com.android.ctrip.utils.utils.isChooseHotile;
import static com.android.ctrip.utils.utils.setArrivalCity;
import static com.android.ctrip.utils.utils.setCabinType;
import static com.android.ctrip.utils.utils.setDepartCity;
import static com.android.ctrip.utils.utils.setRoundTripCabinType;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ticketHomePageTest extends baseTest{
    private UiDevice mDevice= UiDevice.getInstance(getInstrumentation());
    private static String TAG = "OneWayTicketTest";

    //出发地选择页
    @Test
    public void chooseDepartCityPageTest() throws UiObjectNotFoundException, InterruptedException {
        Log.i(TAG,"chooseDepartCityPageTest");
        mDevice.findObject(new UiSelector().resourceId(departCityResId)).click();
        UiObject closeObject = mDevice.findObject(new UiSelector().resourceId(cityListCloseResId));
        System.out.println("today=" + closeObject.exists());
        assertThat("关闭按钮检测",closeObject.exists(),is(true));
        UiObject internalObject = mDevice.findObject(new UiSelector().className(internalTabClassName).text(internalTabText));
        assertThat("国内tab检测",internalObject.exists(),is(true));
        UiObject internationalObject = mDevice.findObject(new UiSelector().className(internationalTabClassName).text(internationalTabText));
        assertThat("国际tab检测",internationalObject.exists(),is(true));
    }

    //出发地搜索
    @Test
    public void searchTest() throws UiObjectNotFoundException {
        Log.i(TAG,"searchTest");
        mDevice.findObject(new UiSelector().resourceId(departCityResId)).click();
        UiObject searchObject = mDevice.findObject(new UiSelector().resourceId(citySearchInputResId));
        assertThat("搜索框默认文本检测",searchObject.getText(),is(citySearchInputText));
    }


    //目的地选择页
    @Test
    public void chooseArrivalCityPageTest() throws UiObjectNotFoundException {
        Log.i(TAG,"chooseArrivalCityPageTest");
        mDevice.findObject(new UiSelector().resourceId(arrivalCityResId)).click();
        assertThat("关闭按钮检测",mDevice.findObject(new UiSelector().resourceId(cityListCloseResId)).exists(),is(true));
        assertThat("国内tab检测",mDevice.findObject(new UiSelector().className(internalTabClassName).text(internalTabText)).exists(),is(true));
        assertThat("国际tab检测",mDevice.findObject(new UiSelector().className(internationalTabClassName).text(internationalTabText)).exists(),is(true));
    }

    //交换出发地和目的地
    @Test
    public void changeCityTest() throws UiObjectNotFoundException {
        Log.i(TAG,"changeCityTest");
        setDepartCity(mDevice,"深圳","国内");
        setArrivalCity(mDevice,"北京","国内");
        mDevice.findObject(new UiSelector().resourceId(exchangeCityResId)).click();
        assertThat("出发地为北京",mDevice.findObject(new UiSelector().resourceId(departCityResId)).getText(),is("北京"));
        assertThat("目的地为深圳",mDevice.findObject(new UiSelector().resourceId(arrivalCityResId)).getText(),is("深圳"));
    }

    //头等舱经济舱选择
    @Test
    public void chooseCabinTypeTest() throws UiObjectNotFoundException {
        Log.i(TAG,"chooseCabinTypeTest");
        String cabinType = "经济舱";
        setRoundTripCabinType(mDevice,cabinType);
        assertThat("判断是否显示经济舱",mDevice.findObject(new UiSelector().text("经济舱")).isSelected(),is(true));
    }

    //勾选酒店
    @Test
    public void chooseHotileTest() throws UiObjectNotFoundException {
        Log.i(TAG,"chooseHotileTest");
        setDepartCity(mDevice,"深圳","国内");
        setArrivalCity(mDevice,"北京","国内");
        isChooseHotile(mDevice);
        assertThat("酒店选项勾选检测",mDevice.findObject(new UiSelector().resourceId(chooseHotileResId)).isSelected(),is(true));
    }

}

