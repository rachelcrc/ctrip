package com.android.ctrip;

import android.util.Log;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.Until;

import org.junit.Test;

import java.io.IOException;

import static android.os.SystemClock.sleep;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.android.ctrip.utils.assertUtils.JudgeRoundWayFightNum;
import static com.android.ctrip.utils.assertUtils.judgeArrivalCity;
import static com.android.ctrip.utils.assertUtils.judgeDepartCity;
import static com.android.ctrip.utils.utils.chooseWay;
import static com.android.ctrip.utils.utils.setArrivalCity;
import static com.android.ctrip.utils.utils.setDepartCity;
import static com.android.ctrip.utils.utils.setRoundTripCabinType;
import static com.android.ctrip.utils.utils.sumit;

public class roundTripTicketTest extends baseTest {
    private UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
    private static String TAG = "roundTripTicketTest";

    //查询往深圳到北京的成人的公务舱机票，不勾选酒店
    @Test
    public void queryBussinessAirTicketest() throws UiObjectNotFoundException {
        Log.i(TAG, "queryBussinessAirTicketWithchildTest");
        Log.i(TAG, "选择出发城市");
        setDepartCity(mDevice, "深圳", "国内");
        Log.i(TAG, "选择目的城市");
        setArrivalCity(mDevice, "北京", "国内");
        Log.i(TAG, "选择公务舱、头等舱");
        setRoundTripCabinType(mDevice, "公务/头等舱");
        sumit(mDevice);

        mDevice.wait(Until.findObject(By.text("下一步")), TIME_OUT);
        Log.i(TAG, "判断结果");
        judgeDepartCity(mDevice, "深圳");
        judgeArrivalCity(mDevice, "北京");
        JudgeRoundWayFightNum(mDevice, true);
    }

    @Override
    public void beforeTest() throws UiObjectNotFoundException, InterruptedException, IOException {
        super.beforeTest();
        chooseWay(mDevice,"往返");
    }
}
