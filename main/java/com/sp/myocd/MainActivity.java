package com.sp.myocd;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class MainActivity extends Activity implements SensorEventListener {
    private Button connectBtn,forwardBtn,reverseBtn,hornBtn;//Buttons to connect and send the instruction
    private Socket tcpSocket;//socket to connect to esp-01
    private EditText ip, port;//text fields to key in port and ip
    private OutputStream os;//output stream to send data to esp using the socket
    private InputStream is;//to get input from ESP-01
    private String temp;//temp string for inputstream
    private SensorManager sensorManager;
    private Sensor phonePosition;
    private TextView rotateVal,testdata,espIp;
    private int temp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        forwardBtn = (Button) findViewById(R.id.forwardBtn);
        reverseBtn = (Button) findViewById(R.id.reverseBtn);
        hornBtn = (Button) findViewById(R.id.hornBtn);
        hornBtn.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mHandler != null) return true;
                    mHandler = new Handler();
                    mHandler.postDelayed(mAction, 0);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mHandler == null) return true;
                    mHandler.removeCallbacks(mAction);
                    mHandler = null;
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (os != null) {
                                try {
                                    os.write("4".getBytes());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    if (os != null) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    os.write("3".getBytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                    mHandler.postDelayed(this, 2000);
                }
            };
        });
        forwardBtn.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mHandler != null) return true;
                    mHandler = new Handler();
                    mHandler.postDelayed(mAction, 0);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mHandler == null) return true;
                    mHandler.removeCallbacks(mAction);
                    mHandler = null;
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (os != null) {
                                try {
                                    os.write("0".getBytes());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    if (os != null) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    os.write("1".getBytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                    mHandler.postDelayed(this, 2000);
                }
            };
        });
        reverseBtn.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mHandler != null) return true;
                    mHandler = new Handler();
                    mHandler.postDelayed(mAction, 0);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mHandler == null) return true;
                    mHandler.removeCallbacks(mAction);
                    mHandler = null;
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (os != null) {
                                try {
                                    os.write("0".getBytes());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    if (os != null) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    os.write("2".getBytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                    mHandler.postDelayed(this, 2000);
                }
            };
        });
        rotateVal = (TextView) findViewById(R.id.rotateval);
        testdata = (TextView) findViewById(R.id.testdata);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        phonePosition = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener((SensorEventListener) this, phonePosition, SensorManager.SENSOR_DELAY_NORMAL);
        connectBtn = (Button) findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(new View.OnClickListener() {//set click listeners
            @Override
            public void onClick(View v) {
                //establish connection in the background
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (tcpSocket != null) tcpSocket.close();
                            tcpSocket = new Socket(ip.getText().toString(), Integer.valueOf(port.getText().toString()));//open socket with data keyed in
                            os = tcpSocket.getOutputStream();//assign the output stream to the OutputStream object
                            is = tcpSocket.getInputStream();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
        //assign the views to the variables
        ip = (EditText) findViewById(R.id.ipText);
        port = (EditText) findViewById(R.id.portText);
    }
    @Override
    public void onSensorChanged(final SensorEvent event){
        if (os != null) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (tcpSocket != null) {
                            double temp = event.values[0];
                            //temp1 = (int) (((event.values[0]-250) * 103.0 /65) + 76);
                            temp1 = (int) ((event.values[1]*10)+127);
                            if(temp1>=180)temp1=179;
                            if(temp1<=75)temp1=76;
                            os.write((byte)(temp1));//write data to esp using the outputstream
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    testdata.setText(String.valueOf(temp1));
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        rotateVal.setText(String.valueOf(event.values[0])+ " "+String.valueOf(event.values[1])+ " "+String.valueOf(event.values[2])+ " ");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
