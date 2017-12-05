package app.ljw.com.myapplication.dummy;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import app.ljw.com.myapplication.HomeActivity;
import app.ljw.com.myapplication.MyApplication;

/**
 * @author LIJUWEN
 * @email yiyayiyayaoljw@gmail.com
 * @date 2017/11/15 11:57
 */
public class CatchHandler implements Thread.UncaughtExceptionHandler {

    MyApplication myApplication;
    private static CatchHandler instance;  //单例引用，这里我们做成单例的，因为我们一个应用程序里面只需要一个UncaughtExceptionHandler实例

    private CatchHandler(){}

    public synchronized static CatchHandler getInstance(){  //同步方法，以免单例多线程环境下出现异常
        if (instance == null){
            instance = new CatchHandler();
        }
        return instance;
    }

    public void init(Context ctx){  //初始化，把当前对象设置成UncaughtExceptionHandler处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {  //当有未处理的异常发生时，就会来到这里。。
        Log.e("Sandy", "uncaughtException, thread: " + thread
                + " name: " + thread.getName() + " id: " + thread.getId() + "exception: "
                + ex);
        String threadName = thread.getName();
        if ("sub1".equals(threadName)){
            Log.e("Sandy", "xxx");
        }else{
            //这里我们可以根据thread name来进行区别对待，同时，我们还可以把异常信息写入文件，以供后来分析。
            //使用Toast来显示异常信息
            new Thread(){
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(myApplication.getApplicationContext(), "很抱歉,程序出现异常,即将退出.",
                            Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }.start();

            Intent intent = new Intent(myApplication, HomeActivity.class);
            @SuppressLint("WrongConstant") PendingIntent restartIntent = PendingIntent.getActivity(
                    myApplication.getApplicationContext(), 0, intent,
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            //退出程序
            AlarmManager mgr = (AlarmManager)myApplication.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                    restartIntent); // 1秒钟后重启应用
            android.os.Process.killProcess(android.os.Process.myPid());



        }
    }

    public  void setMyApplication(MyApplication myApplication){
        this.myApplication=myApplication;
    }

}
