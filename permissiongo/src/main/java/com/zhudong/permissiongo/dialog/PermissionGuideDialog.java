package com.zhudong.permissiongo.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhudong.permissiongo.R;
import com.zhudong.permissiongo.Utils.ToolUtil;
import com.zhudong.permissiongo.callbacks.PermissionsDialogCallback;
import com.zhudong.permissiongo.model.PermissionGuideModel;

public class PermissionGuideDialog extends SafeDialogFragment {
    private PermissionGuideModel model;
    private PermissionsDialogCallback dismissListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.permission_guide, container);
        initView(view);
        return view;
    }

    public static void showDialog(FragmentManager fragmentManager, PermissionGuideModel model, PermissionsDialogCallback listener) {
        PermissionGuideDialog dialog = new PermissionGuideDialog();
        dialog.dismissListener = listener;
        dialog.model = model;
        dialog.setStyle(STYLE_NO_TITLE, R.style.full_screen_dialog);
        dialog.show(fragmentManager, null);
    }

    private void initView(View view) {
        TextView title = view.findViewById(R.id.permission_title);
        TextView desc = view.findViewById(R.id.permission_desc);
        TextView btn = view.findViewById(R.id.permission_btn);
        View close = view.findViewById(R.id.permission_close);
        RecyclerView recycleView = view.findViewById(R.id.permission_recyclerview);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        title.setText(model.getTitle());
        btn.setText(model.getBottomDesc());
        if (model.getDesc() == null || model.getDesc().length() == 0) {
            desc.setVisibility(View.GONE);
        } else {
            desc.setText(model.getDesc());
        }
        if (model.isShowClose()) {
            close.setVisibility(View.VISIBLE);
        }
        if (model.getList() != null && model.getList().size() != 0) {
            LinearLayout.LayoutParams layoutParams;
            switch (model.getList().size()){
                case 1:
                    layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ToolUtil.dip2px(70f));
                    break;
                case 2:
                    layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ToolUtil.dip2px(167f));
                    break;
                default:
                    layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ToolUtil.dip2px(263f));
                    break;
            }
            recycleView.setLayoutParams(layoutParams);
            PermissionAdapter adapter = new PermissionAdapter();
            adapter.setItemList(model.getList());
            recycleView.setAdapter(adapter);
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dismissListener!=null){
                    dismissListener.onDismiss(false);
                }
                dismiss();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dismissListener!=null){
                    dismissListener.onDismiss(true);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        dismissListener = null;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if(dismissListener!=null){
            dismissListener.onDismiss(false);
        }
    }
}
