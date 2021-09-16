package com.android.ctrip;

import android.util.Log;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.android.ctrip.constant.appInfo.appName;
import static com.android.ctrip.constant.controlResId.ticketEntryResId;
import static com.android.ctrip.constant.controlText.ticketEntryText;
import static com.android.ctrip.utils.utils.startApp;
import static java.lang.Thread.sleep;

public class baseTest {
    private UiDevice mDevice= UiDevice.getInstance(getInstrumentation());
    private static final String TAG = "baseTest";
    public static final int TIME_OUT = 5000;
    @Before
    public void beforeTest() throws UiObjectNotFoundException, InterruptedException, IOException {
        Log.i(TAG,"beforeTest");
        mDevice.executeShellCommand("am force-stop ctrip.android.view");
        startApp(mDevice, appName);
        mDevice.wait(Until.findObject(By.text("首页")), TIME_OUT);
        Log.i(TAG,"打开机票首页");
        mDevice.findObject(new UiSelector().resourceId(ticketEntryResId).text(ticketEntryText)).click();
    }

    @After
    public void afterTest() throws IOException, InterruptedException {
        Log.i(TAG,"afterTest");
        mDevice.executeShellCommand("am force-stop ctrip.android.view");
    }

}
