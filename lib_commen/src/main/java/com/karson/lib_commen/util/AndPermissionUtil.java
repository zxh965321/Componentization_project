package com.karson.lib_commen.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.karson.lib_commen.R;
import com.karson.lib_commen.ui.wight.dialog.CustomDailog;
import com.yanzhenjie.permission.AndPermission;


/**
 * @Author karson
 * @Date 2020/8/22-11:11
 * @desc 权限请求工具
 */
public class AndPermissionUtil {

    public static final int REQUEST_CODE_SETTING = 1;
    private static CustomDailog dialog;

    public static boolean checkPermission(Context context, String permission) {
        return PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(permission, context.getPackageName());
    }

    @SuppressLint("WrongConstant")
    public static void requestPermissions(final Activity acl, String[] permissions, final OnRequestsListener listener, String msg) {
        AndPermission.with(acl)
                .runtime()
                .permission(permissions)
                .rationale(((context, data, executor) -> executor.execute()))
                .onGranted(p -> {
                    listener.onRequestSuccessFull();
                })
                .onDenied(p -> {
                    if (AndPermission.hasAlwaysDeniedPermission(acl, p)) {
                        View view =
                                LayoutInflater.from(acl).inflate(R.layout.item_dialog_addresstip,
                                        null, false);
                        TextView tv = view.findViewById(R.id.tipTv);
                        tv.setText(msg);
                        dialog = CustomDailog.getBuilder(acl).setIsBackGrund(true)
                                .setContentView(view).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        AndPermission.with(acl).runtime().setting().start(REQUEST_CODE_SETTING);
                                        dialog.dismiss();
                                    }
                                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                })
                                .build();
                        dialog.show();
//                        AndPermission.with(acl).runtime().setting().start(REQUEST_CODE_SETTING);
                        return;
                    }
                    listener.onRequestFailed();
                }).start();

    }

    public interface OnRequestsListener {
        /**
         * 申请成功
         */
        void onRequestSuccessFull();

        /**
         * 申请失败
         */
        void onRequestFailed();
    }
}
