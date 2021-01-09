package com.example.appdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdemo.R;
import com.example.appdemo.adapter.DateAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
    private MyGridView record_gridView;//定义gridView
    private DateAdapter dateAdapter;//定义adapter
    private ImageView record_left;//左箭头
    private ImageView record_right;//右箭头
    private TextView record_title;//标题
    private int year;
    private int month;
    private String title;
    private int[][] days = new int[6][7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        year = DateUtils.getYear();
        month = DateUtils.getMonth();

        record_gridView = (MyGridView) this.findViewById(R.id.record_gridView);
        days = DateUtils.getDayOfMonthFormat(year, month);

        dateAdapter = new DateAdapter(this, days, year, month);

        record_gridView.setAdapter(dateAdapter);
        record_gridView.setVerticalSpacing(60);
        record_gridView.setEnabled(false);

        record_left = (ImageView) findViewById(R.id.record_left);
        record_right = (ImageView) findViewById(R.id.record_right);
        record_title = (TextView) findViewById(R.id.record_title);

        record_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = prevMonth();
                dateAdapter = new DateAdapter(MainActivity.this, days, year, month);
                record_gridView.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTile();
            }
        });
        record_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = nextMonth();
                dateAdapter = new DateAdapter(MainActivity.this, days, year, month);
                record_gridView.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTile();
            }
        });
        record_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MemoActivity.class);
                startActivity(intent);
            }
        });
    }

    private int[][] nextMonth() {
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        days = DateUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    private int[][] prevMonth() {
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        days = DateUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    private void setTile() {
        title = year + "年" + month + "月";
        record_title.setText(title);
    }

}
