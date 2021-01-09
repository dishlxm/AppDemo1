package com.example.appdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;

public class MyAppWiget extends AppWidgetProvider {
    public static final String WIDGET_BTN_ACTION = "widget_btn_action";
//    Calendar calendar = Calendar.getInstance();
//    int year = calendar.get(Calendar.YEAR);
//    int month =calendar.get(Calendar.MONTH) + 1;
//    int day = calendar.get(Calendar.DAY_OF_MONTH);
//    String date = year + "-" + month + "-" + day;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent != null && TextUtils.equals(intent.getAction(), WIDGET_BTN_ACTION)) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.table_show);
            remoteViews.setTextViewText(R.id.tsTitle, "be clicked");
//            getATitle();

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);// 单例模式
            ComponentName componentName = new ComponentName(context, MyAppWiget.class);
            appWidgetManager.updateAppWidget(componentName, remoteViews);//setText之后，记得更新一下
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.table_show);

        Intent intent = new Intent();
        intent.setClass(context, MyAppWiget.class);
        intent.setAction(WIDGET_BTN_ACTION);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        rv.setOnClickPendingIntent(R.id.widget_btn, pendingIntent);//控件btn_widget的点击事件：点击按钮时，会发一个带action的广播。

        appWidgetManager.updateAppWidget(appWidgetIds, rv);

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

//    public String getATitle(){
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month =calendar.get(Calendar.MONTH) + 1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        String date = year + "-" + month + "-" + day;
//        String rtTitle = "";
//
//        DbHelper dbhelper = new DbHelper(AddMemoActivity.mContext, "db_bwl", null, 1);
//
//        SQLiteDatabase db = dbhelper.getWritableDatabase();
//        Cursor cursor = db.query("tb_bwl",null,null,null,null,null,null);
//
//        if(cursor.moveToFirst()){
//            do{
//                String title = cursor.getString(cursor.getColumnIndex("title"));
//                String noticeDate = cursor.getString(cursor.getColumnIndex("noticeDate"));
//                Log.d("title",title);
//                if(date.equals(noticeDate)){
//                    rtTitle = title;
//                    break;
//                }
//            }while(cursor.moveToNext());
//        }
//
//        cursor.close();
//
//        return rtTitle;
//    }

}
