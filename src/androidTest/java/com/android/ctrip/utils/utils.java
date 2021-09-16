package com.android.ctrip.utils;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.UiWatcher;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.test.espresso.action.ViewActions.click;
import static com.android.ctrip.constant.controlClassName.setRegionClassName;
import static com.android.ctrip.constant.controlClassName.wayTypeClassName;
import static com.android.ctrip.constant.controlResId.arrivalCityResId;
import static com.android.ctrip.constant.controlResId.chooseClassResId;
import static com.android.ctrip.constant.controlResId.chooseHotileResId;
import static com.android.ctrip.constant.controlResId.classItemResId;
import static com.android.ctrip.constant.controlResId.departCityResId;
import static com.android.ctrip.constant.controlResId.inquireResId;
import static com.android.ctrip.constant.controlResId.passengerAddResId;
import static com.android.ctrip.constant.controlResId.passengerCountResId;
import static com.android.ctrip.constant.controlResId.passengerCountSubmitResId;
import static com.android.ctrip.constant.controlResId.setCityResId;
import static com.android.ctrip.constant.controlResId.ticketEntryResId;
import static com.android.ctrip.constant.controlText.internalTabText;
import static com.android.ctrip.constant.controlText.internationalTabText;
import static com.android.ctrip.constant.controlText.passengerCountSubmitText;
import static com.android.ctrip.constant.controlText.ticketEntryText;
import static java.lang.Thread.sleep;

public class utils {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static String TAG="utils";
    static UiWatcher knowWatcher = new UiWatcher() {
        UiObject knowButton = new UiObject(new UiSelector().text("知道了"));
        @Override
        public boolean checkForCondition() {
            if(knowButton.exists()){
                try {
                    knowButton.click();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    };

    public static void startApp(UiDevice mDevice,String packageNameOfApp)  {
        //Initialize UiDevice instance
        Log.i(TAG,"startApp");
        mDevice.pressHome();
        UiObject ctripAppButton = mDevice.findObject(new UiSelector().description(packageNameOfApp));

        // open ctripApp
        try {
            ctripAppButton.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        Log.i(TAG,"registerWatcher");
        mDevice.registerWatcher("知道了",utils.knowWatcher);
        mDevice.runWatchers();
    }

    //始发地选择
    public static void setDepartCity(UiDevice mDevice,String departCity,String region) throws UiObjectNotFoundException {
        Log.i(TAG,"setDepartCity");
        mDevice.findObject(new UiSelector().resourceId(departCityResId)).click();
        setCity(mDevice,region,departCity);
    }

    //目的地选择
    public static void setArrivalCity(UiDevice mDevice,String arrivalCity,String region) throws UiObjectNotFoundException {
        Log.i(TAG,"setArrivalCity");
        mDevice.findObject(new UiSelector().resourceId(arrivalCityResId)).click();
        setCity(mDevice,region,arrivalCity);
    }

    //设置城市
    public static void setCity(UiDevice mDevice,String region,String city) throws UiObjectNotFoundException {
        Log.i(TAG,"setCity");
        if(region.equals(internationalTabText)){
            mDevice.findObject(new UiSelector().className(setRegionClassName).descriptionContains(internationalTabText)).click();
            if(city.equals("中国香港")){
                mDevice.findObject(new UiSelector().className("android.widget.TextView").text("中国港澳台")).click();
            }
            else if(city.equals("纽约")){
                mDevice.findObject(new UiSelector().className("android.widget.TextView").text("美洲")).click();
            }
            mDevice.findObject(new UiSelector().text(city).clickable(true)).click();
        }
        else if(region.equals(internalTabText)){
            mDevice.findObject(new UiSelector().className(setRegionClassName).descriptionContains(internalTabText)).click();
            UiScrollable uiScrollable = new UiScrollable(new UiSelector().className("android.widget.LinearLayout"));
            UiObject departCityObject = uiScrollable.getChildByText(new UiSelector().className("android.widget.TextView"),city,true);
            departCityObject.click();
        }
        mDevice.findObject(new UiSelector().resourceId(setCityResId)).click();
    }

    //是否携带儿童
    public static void isCarrryChild(UiDevice mDevice,boolean isCarrying) throws UiObjectNotFoundException {
        Log.i(TAG,"isCarrryChild");
//        mDevice.findObject(new UiSelector().resourceId("ctrip.android.view:id/tv_title").text("带儿童")).click();
        mDevice.findObject(new UiSelector().resourceId(passengerCountResId)).click();
        mDevice.findObject(new UiSelector().resourceId(passengerAddResId).instance(1)).click();
        mDevice.findObject(new UiSelector().resourceId(passengerCountSubmitResId).text(passengerCountSubmitText)).click();
    }

    //是否携带婴儿
    public static void isCarrryBaby(UiDevice mDevice,boolean isCarrying) throws UiObjectNotFoundException {
        Log.i(TAG,"isCarrryBaby");
//        mDevice.findObject(new UiSelector().resourceId("ctrip.android.view:id/tv_title").text("带婴儿")).click();
        mDevice.findObject(new UiSelector().resourceId(passengerCountResId)).click();
        mDevice.findObject(new UiSelector().resourceId(passengerAddResId).instance(2)).click();
        mDevice.findObject(new UiSelector().resourceId(passengerCountSubmitResId).text(passengerCountSubmitText)).click();
    }


    //单程选择舱位
    public static void setCabinType(UiDevice mDevice,String cabinType) throws UiObjectNotFoundException {
        Log.i(TAG,"setCabinType");
//        mDevice.findObject(new UiSelector().className("android.widget.TextView").text(cabinType)).click();
        mDevice.findObject(new UiSelector().resourceId(chooseClassResId)).click();
        mDevice.findObject(new UiSelector().resourceId(classItemResId).text(cabinType)).click();
    }

    //往返选择舱位
    public static void setRoundTripCabinType(UiDevice mDevice,String cabinType) throws UiObjectNotFoundException {
        Log.i(TAG,"setCabinType");
        mDevice.findObject(new UiSelector().className("android.widget.TextView").text(cabinType)).click();
    }


    //是否勾选酒店
    public static void isChooseHotile(UiDevice mDevice) throws UiObjectNotFoundException {
        Log.i(TAG,"isChooseHotile");
        mDevice.findObject(new UiSelector().resourceId(chooseHotileResId)).click();
    }

    // 点击查询
    public static void sumit(UiDevice mDevice) throws UiObjectNotFoundException {
        Log.i(TAG,"sumit");
        mDevice.findObject(new UiSelector().resourceId(inquireResId)).click();
    }

    //选择单程or往返
    public static void chooseWay(UiDevice mDevice,String way) throws UiObjectNotFoundException {
        Log.i(TAG,"chooseWay");
        mDevice.findObject(new UiSelector().className(wayTypeClassName).text(way)).click();
    }

    // 获取当天日期
    public static String getCurrentDate(){
        Log.i(TAG,"getCurrentDate");
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }


    //
}
