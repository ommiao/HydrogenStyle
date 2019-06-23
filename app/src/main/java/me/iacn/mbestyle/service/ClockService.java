package me.iacn.mbestyle.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import java.lang.ref.WeakReference;

import me.iacn.mbestyle.ui.widget.AppClockWidget;

public class ClockService extends Service {

    private static final String TAG = ClockService.class.getSimpleName();
    private Handler mHandler = new MyHandler(this);

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler{

        private WeakReference<ClockService> clockServiceWeakReference;

        private MyHandler(ClockService clockService){
            clockServiceWeakReference = new WeakReference<>(clockService);
        }

        @Override
        public void handleMessage(Message msg) {
            if(clockServiceWeakReference.get() == null){
                return;
            }
            ComponentName thisWidget = new ComponentName(ClockService.this, AppClockWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(ClockService.this);
            int[] ids = manager.getAppWidgetIds(thisWidget);
            if (ids == null || ids.length == 0) {
                stopService(new Intent(getApplicationContext(), ClockService.class));
                return;
            }

            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            intent.setPackage(getPackageName());
            sendBroadcast(intent);

            mHandler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler.sendEmptyMessage(0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
