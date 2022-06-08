package com.example.Testproj.Utils;
import com.example.Testproj.R;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
public class DialogContainer{
    public static Dialog dialog;
    //弹出图片选择对话框，选择拍照、还是本地相册读取
    //4个参数，第一个参数是上下文，第二个参数是Dialog的布局xml文件，
    //第三个参数 是拍照广播的action名，第四个参数是本地相册广播的action名
    public static void showPictureDialog(final Context context, int c_dialogid,
                                         final String zhaoxiang_action, final String dcim_action){
        dialog = new Dialog(context, R.style.JumpDialog);//指明Dialog容器弹出的动画风格
        //根据layout文件绘制出加载动画的视图
        LinearLayout linear = (LinearLayout) LayoutInflater.from(context).inflate(c_dialogid,null);
        TextView kongjian1 = (TextView)linear.findViewById(R.id.text_camera);
        TextView kongjian2 = (TextView)linear.findViewById(R.id.text_dcim);
        TextView kongjian3 = (TextView)linear.findViewById(R.id.text_cancel);
        //点击拍照，发送 拍照的广播，这个拍照广播是在 MainActivity中注册的
        kongjian1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送打开相机通知
                Intent intent = new Intent(zhaoxiang_action);
                context.sendBroadcast(intent);
                deleteDialog();
            }
        });
        //点击相册，发送 相册的广播，这个相册广播是在 MainActivity中注册的
        kongjian2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(dcim_action);
                context.sendBroadcast(intent1);
                deleteDialog();
            }
        });
        //取消Dialog
        kongjian3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog();
            }
        });
        dialog.setContentView(linear);//将视图加入容器
        Window dialogWindow = dialog.getWindow();//获得窗口
        dialogWindow.setGravity(Gravity.BOTTOM);//放置在底部
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int)context.getResources().getDisplayMetrics().widthPixels; // 宽度
        linear.measure(0, 0);
        lp.height = linear.getMeasuredHeight();
        lp.alpha = 1; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(false);
        dialog.show();
    }


    public static void deleteDialog(){
        if (dialog!=null){
            dialog.cancel();
        }
    }
}

