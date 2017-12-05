package app.ljw.com.myapplication;

import android.app.Application;

import com.vondear.rxtools.RxTool;

import app.ljw.com.myapplication.dummy.CatchHandler;

/**
 * @author LIJUWEN
 * @email yiyayiyayaoljw@gmail.com
 * @date 2017/11/15 13:39
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        CatchHandler handler = CatchHandler.getInstance();
        handler.setMyApplication(this);
        handler.init(getApplicationContext());
        RxTool.init(this);
    }


}
