package com.zhudong.permissiongo.dialog;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhudong.permissiongo.R;
import com.zhudong.permissiongo.Utils.ToolUtil;
import com.zhudong.permissiongo.model.PermissionItemModel;

import java.util.List;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.ItemHolder> {

    private List<PermissionItemModel> itemList;

    public void setItemList(List<PermissionItemModel> itemList){
        this.itemList = itemList;
    }

    @Override
    public PermissionAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.permission_item_layout, parent, false));
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        PermissionItemModel item = itemList.get(position);
        holder.permissionItemImg.setImageResource(item.getImg());
        holder.permissionItemName.setText(item.getTitle());
        holder.permissionItemDesc.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return ToolUtil.size(itemList);
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        ImageView permissionItemImg;
        TextView permissionItemName;
        TextView permissionItemDesc;

        ItemHolder(View itemView) {
            super(itemView);
            permissionItemImg = itemView.findViewById(R.id.permission_item_img);
            permissionItemName = itemView.findViewById(R.id.permission_item_name);
            permissionItemDesc = itemView.findViewById(R.id.permission_item_desc);
        }
    }
}
