package me.iacn.mbestyle.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.Date;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.service.ClockService;

/**
 * Implementation of App Widget functionality.
 */
public class AppClockWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock);
        updateViews(views);
        views.setOnClickPendingIntent(R.id.clock, PendingIntent.getService(context, 0, new Intent(context, ClockService.class), 0));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void updateViews(RemoteViews views) {
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        String hour = h >= 10 ? String.valueOf(h) : "0" + h;
        String hour0 = hour.substring(0, 1);
        String hour1 = hour.substring(1, 2);
        int m = calendar.get(Calendar.MINUTE);
        String minute = m >= 10 ? String.valueOf(m) : "0" + m;
        String minute0 = minute.substring(0, 1);
        String minute1 = minute.substring(1, 2);
        int y = calendar.get(Calendar.YEAR);
        String year = String.valueOf(y);
        int M = calendar.get(Calendar.MONTH) + 1;
        String month = getDisplayMonth(M);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        String day = d >= 10 ? String.valueOf(d) : "0" + d;
        String date = month + "." + day + "." + year;
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String week = getDisplayWeek(w);
        views.setTextViewText(R.id.hour0, hour0);
        views.setTextViewText(R.id.hour1, hour1);
        views.setTextViewText(R.id.minute0, minute0);
        views.setTextViewText(R.id.minute1, minute1);
        views.setTextViewText(R.id.date, date);
        views.setTextViewText(R.id.week, week);
    }

    private static String getDisplayWeek(int w) {
        switch (w){
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 0:
                return "Sunday";
        }
        return "Null...";
    }

    private static String getDisplayMonth(int m) {
        switch (m){
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sept";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }
        return "Null";
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        context.startService(new Intent(context, ClockService.class));
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        context.stopService(new Intent(context, ClockService.class));
    }
}

