package com.android.ctrip;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.android.ctrip.utils.assertUtils.JudgeOneWayFightNum;
import static com.android.ctrip.utils.assertUtils.judgeArrivalCity;
import static com.android.ctrip.utils.assertUtils.judgeDepartCity;
import static com.android.ctrip.utils.utils.chooseWay;
import static com.android.ctrip.utils.utils.getCurrentDate;
import static com.android.ctrip.utils.utils.isCarrryChild;
import static com.android.ctrip.utils.utils.setArrivalCity;
import static com.android.ctrip.utils.utils.setCabinType;
import static com.android.ctrip.utils.utils.setDepartCity;
import static com.android.ctrip.utils.utils.startApp;
import static com.android.ctrip.utils.utils.sumit;
import static java.lang.Thread.sleep;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class OneWayTicketTest extends baseTest{
    private UiDevice mDevice= UiDevice.getInstance(getInstrumentation());
    private static String TAG = "OneWayTicketTest";

    //查询今天从中国香港到纽约的成人并携带儿童的公务舱机票，不勾选酒店
    @Test
    public void queryBussinessAirTicketWithchildTest() throws UiObjectNotFoundException {
        Log.i(TAG,"queryBussinessAirTicketWithchildTest");
        Log.i(TAG,"选择出发城市");
        setDepartCity(mDevice,"中国香港","国际/中国港澳台");
        Log.i(TAG,"选择目的城市");
        setArrivalCity(mDevice,"纽约","国际/中国港澳台");
        Log.i(TAG,"选择携带儿童");
        isCarrryChild(mDevice,true);
        Log.i(TAG,"选择公务舱、头等舱");
        setCabinType(mDevice,"公务舱、头等舱");
        sumit(mDevice);

        mDevice.wait(Until.findObject(By.text("筛选")), TIME_OUT);
        Log.i(TAG,"判断结果");
        judgeDepartCity(mDevice,"中国香港");
        judgeArrivalCity(mDevice,"纽约");
        JudgeOneWayFightNum(mDevice,true);
    }

    @Override
    public void beforeTest() throws UiObjectNotFoundException, InterruptedException, IOException {
        super.beforeTest();
        chooseWay(mDevice,"单程");
    }
}