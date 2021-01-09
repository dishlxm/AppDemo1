package com.example.appdemo;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class AddMemoActivity extends Activity {
    private EditText etDate = null,etTime=null,etTitle=null,etContent=null;
    private Button btnSave = null;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;

    private DbHelper dbhelper;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        dbhelper = new DbHelper(this, "db_bwl", null, 1);

        etTitle = (EditText)findViewById(R.id.etTitle);
        etContent = (EditText)findViewById(R.id.etContent);

        etDate = (EditText)findViewById(R.id.etDate);
        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);

            }
        });

        etTime = (EditText)findViewById(R.id.etTime);
        etTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showDialog(TIME_DIALOG_ID);

            }
        });


        btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ContentValues value = new ContentValues();

                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();
                String noticeDate = etDate.getText().toString();
                String noticeTime = etTime.getText().toString();

                value.put("title", title);
                value.put("content", content);
                value.put("noticeDate", noticeDate);
                value.put("noticeTime", noticeTime);


                SQLiteDatabase db = dbhelper.getWritableDatabase();

                long id = 0;

                long status = 0;
                if(bundle!=null){
                    id = bundle.getLong("id");
                    status = db.update("tb_bwl", value, "id=?", new String[]{bundle.getLong("id")+""});
                }else{
                    status = db.insert("tb_bwl", null, value);
                    id = status;
                }

                if(status!=-1){
                    setAlarm(id);
                    Toast.makeText(AddMemoActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AddMemoActivity.this, "保存失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        bundle = this.getIntent().getExtras();
        if(bundle!=null){
            etDate.setText(bundle.getString("noticeDate"));
            etTime.setText(bundle.getString("noticeTime"));
            etTitle.setText(bundle.getString("title"));
            etContent.setText(bundle.getString("content"));
        }
    }

    private OnDateSetListener dateSetListener = new OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            StringBuilder dateStr = new StringBuilder();
            dateStr.append(year).append("-")
                    .append(month+1).append("-")
                    .append(day);

            etDate.setText(dateStr.toString());
        }
    };


    private OnTimeSetListener timeSetListener = new OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {


            StringBuilder timeStr = new StringBuilder();
            timeStr.append(hour).append(":")
                    .append(minute);

            etTime.setText(timeStr.toString());
        }
    };

    protected Dialog onCreateDialog(int id){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        switch(id){
            case DATE_DIALOG_ID:
                DatePickerDialog dpd = new DatePickerDialog(this,dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dpd.setCancelable(true);
                dpd.setTitle("选择日期");
                dpd.show();
                break;
            case TIME_DIALOG_ID:
                TimePickerDialog tpd = new TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
                tpd.setCancelable(true);
                tpd.setTitle("选择时间");
                tpd.show();
                break;
            default:
                break;
        }
        return null;
    }

    private AlarmManager alarmManager=null;


    public void setAlarm(long id){


        String noticeDate = etDate.getText().toString();
        String noticeTime = etTime.getText().toString();

        Calendar calendar = Calendar.getInstance();

        calendar.set(Integer.parseInt(noticeDate.split("-")[0]),
                Integer.parseInt(noticeDate.split("-")[1])-1,
                Integer.parseInt(noticeDate.split("-")[2]),
                Integer.parseInt(noticeTime.split(":")[0]),
                Integer.parseInt(noticeTime.split(":")[1]));

        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);


        Intent intent = new Intent(AddMemoActivity.this, AlarmReceiver.class); //创建Intent对象

        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putString("title", etTitle.getText().toString());
        bundle.putString("content", etContent.getText().toString());
        bundle.putString("noticeDate", etDate.getText().toString());
        bundle.putString("noticeTime", etTime.getText().toString());

        intent.putExtras(bundle);

        intent.setAction("ALARM_ACTION"+calendar.getTimeInMillis());

        PendingIntent pi = PendingIntent.getBroadcast(AddMemoActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+5000, pi);

    }

}
