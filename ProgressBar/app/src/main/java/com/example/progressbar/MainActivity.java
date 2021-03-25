package com.example.progressbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private ProgressBar _horizontalProgressBar;
    private TextView _progressText;
    private int _progressStatus;
    private String _ptext;
    private Handler _handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _showProgressbar();
        // Enables Always-on
        setAmbientEnabled();
    }

    private void _showProgressbar()
    {
        _horizontalProgressBar = (ProgressBar) findViewById(R.id.horizontalProgressBar);
        _progressText = (TextView) findViewById(R.id.progressText);

        _handler = new Handler();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                _progressStatus = _horizontalProgressBar.getMin();

                while (_progressStatus < _horizontalProgressBar.getMax())
                {
                    _progressStatus += 1;
                    _handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            _horizontalProgressBar.setProgress(_progressStatus);
                            _ptext = getString(R.string.progress_status, _progressStatus, _horizontalProgressBar.getMax());
                            _progressText.setText(_ptext);
                        }
                    });
                    try
                    {
                        Thread.sleep(200);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
