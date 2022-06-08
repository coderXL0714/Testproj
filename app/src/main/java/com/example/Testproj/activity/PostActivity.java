package com.example.Testproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Testproj.R;
import com.example.Testproj.Utils.CaptureClass;
import com.example.Testproj.Utils.DcimUriget;
import com.example.Testproj.Utils.DialogContainer;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostActivity extends AppCompatActivity {
    private ImageView picturehere;
    TextView setdate;
    Calendar cal;
    Calendar ca = Calendar.getInstance();
    int  mYear = ca.get(Calendar.YEAR);
    int  mMonth = ca.get(Calendar.MONTH);
    int  mDay = ca.get(Calendar.DAY_OF_MONTH);
    private XiangceReceiver xiangceReceiver;//启动相册的通知
    private CameraReceiver cameraReceiver;//启动相机的通知
    private Uri mphotoUri=null;
    private static File fileer;//全局文件，用于发送图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setdate = findViewById(R.id.set_deadline);
        picturehere = (ImageView)findViewById(R.id.imagehere);
        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PostActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mYear = year;
                                mMonth = month;
                                mDay = dayOfMonth;
                                final String data =  year + "-" +(month+1) + "-" + dayOfMonth;
                                setdate.setText(data);
                            }
                        },
                        mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        ImageView camera = findViewById(R.id.picture);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogContainer.showPictureDialog(PostActivity.this,R.layout.dialog_touxiang,
                        "com.ilikexy.picturetest.paizhao","com.ilikexy.picturetest.xiangce");
            }
        });

    }
    public void broadcastPicture(){
        IntentFilter intentFilter1 = new IntentFilter("com.ilikexy.picturetest.paizhao");
        IntentFilter intentFilter2 = new IntentFilter("com.ilikexy.picturetest.xiangce");
        xiangceReceiver = new XiangceReceiver();
        cameraReceiver = new CameraReceiver();
        registerReceiver(cameraReceiver,intentFilter1);
        registerReceiver(xiangceReceiver,intentFilter2);
    }
    class XiangceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            new CaptureClass(PostActivity.this).openDcim();//打开相册
        }
    }
    //打开相机通知
    class CameraReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            new CaptureClass(PostActivity.this).openCamera();//打开相机
        }
    }
    //对于captureclass读取本地图片动态权限申请处理结果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 11:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new CaptureClass(PostActivity.this).openDcimright();//请求成功
                } else {
                    Toast.makeText(this, "拒绝授权，程序销毁！", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
    //对于图片获取的处理结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1://打开相机
                if (resultCode == RESULT_OK) {
                    Toast.makeText(PostActivity.this,"相机代码出了点问题，不好意思哈",Toast.LENGTH_SHORT).show();
                }else{Toast.makeText(PostActivity.this,"拍照失败",Toast.LENGTH_SHORT).show();}
                break;
            case 2:
                if (resultCode==RESULT_OK){//打开相册成功
                    Uri uri = data.getData();//通过data获得图片uri
                    String filePath = DcimUriget.getFilePathByUri(PostActivity.this, uri);//调用根据uri获得图片路径的方法
                    if (!TextUtils.isEmpty(filePath)) {//如果路径不为空
                        //需要Glide加载图片
                        Bitmap bitmaperer = BitmapFactory.decodeFile(filePath);
                        fileer = new File(filePath);//将图片转为file形式。
                        Log.d("xfwa",""+filePath+(bitmaperer==null));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                picturehere.setImageBitmap(bitmaperer);
                            }
                        });
                    }
                }else{Toast.makeText(PostActivity.this,"打开相册失败",Toast.LENGTH_SHORT).show();}
                break;
            default:
                break;
        }//switchcasef方法
    }
    //网络请求，将图片数据发给服务器
    public void sendToServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                //所有图片类型
                MediaType mediaType=MediaType.Companion.parse("image/*; charset=utf-8");
                //第一层，说明数据为文件，以及文件类型
                RequestBody fileBody=RequestBody.Companion.create(fileer,mediaType);

                //第二层，指明服务表单的键名，文件名，文件体
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("myfile",fileer.getName(),fileBody)
                        .build();
                Request request = new Request.Builder()
                        .url("http://8.142.134.155:8234/uploadFile")
                        .post(requestBody)
                        .build();
                //发送请求
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        //网络故障
                        Looper.prepare();
                        Toast.makeText(PostActivity.this,"网络故障！",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.body()!=null){
                            Log.d("xfwa",response.body().string());
                        }
                    }
                });


            }
        }).start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(xiangceReceiver);
    }
}