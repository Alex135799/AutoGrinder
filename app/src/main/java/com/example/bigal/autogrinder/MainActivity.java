package com.example.bigal.autogrinder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecordActions recordActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView textView = (TextView) findViewById(R.id.text_view);
        final TextView textView2 = (TextView) findViewById(R.id.text_view2);
        recordActions = new RecordActions(textView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.only_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {recordActions.toggleRecording();
            }
        });

        FloatingActionButton xButton = (FloatingActionButton) findViewById(R.id.x_button);
        xButton.setOnClickListener(new MyOnClickListener(textView2));
    }

    private class MyOnClickListener implements View.OnClickListener {

        private int count = 1;
        private TextView textView;

        MyOnClickListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View v) {
            textView.setText(""+count);
            count++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        recordActions.handleAction(event);
        return super.dispatchTouchEvent(event);
    }
}
