package org.crazyit.auction.client;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.crazyit.BaseActivity;
import org.crazyit.BaseFragment;
import org.crazyit.auction.client.bean.KindBean;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.crazyit.auction.client.util.LogUtils;

import android.Manifest;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class AddKindFragment extends BaseFragment implements OnClickListener{
    // 定义界面中两个文本框
    EditText kindName, kindDesc;
    // 定义界面中两个按钮
    Button bnAdd, bnCancel;

    ImageView ivAddKind;

    KindBean bean = new KindBean();

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.add_kind
                , container, false);
        // 获取界面中两个编辑框
        kindName = (EditText) rootView.findViewById(R.id.kindName);
        kindDesc = (EditText) rootView.findViewById(R.id.kindDesc);
        // 获取界面中的两个按钮
        bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
        bnCancel = (Button) rootView.findViewById(R.id.bnCancel);
        ivAddKind= (ImageView) rootView.findViewById(R.id.iv_add_kind);
        ivAddKind.setOnClickListener(this);
        // 为取消按钮的单击事件绑定事件监听器
        bnCancel.setOnClickListener(new HomeListener(getActivity()));
        bnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 输入校验
                if (validate()) {
                    // 获取用户输入的种类名、种类描述
                    String name = kindName.getText().toString();
                    String desc = kindDesc.getText().toString();
                    if(bean.getKindIcon()==null){
                        activity.toast(R.string.choose_image);
                        return;
                    }
                    // 添加物品种类
                    addKind(name, desc);
                }
            }
        });
        return rootView;
    }

    // 对用户输入的种类名称进行校验
    private boolean validate() {
        String name = kindName.getText().toString().trim();
        if (name.equals("")) {
            DialogUtil.showDialog(getActivity(), "种类名称是必填项！", false);
            return false;
        }
        return true;
    }

    private void addKind(String name, String desc) {
        bean.setKindName(name);
        bean.setKindDesc(desc);
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    LogUtils.logd("商品种类添加成功：" + s);
                    activity.toast("商品种类添加成功：" + s);
                    activity.finish();
                } else {
                    LogUtils.logd("商品种类添加失败：" + e.getMessage());
                    activity.toast("商品种类添加失败：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_add_kind:
                AddKindFragmentPermissionsDispatcher.openGalleryWithCheck(this);
                break;
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void openGallery() {
        /***启动GalleryFinal多选***/
        GalleryFinal.openGallerySingle(1001, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (resultList != null) {
                    final File file=new File(resultList.get(0).getPhotoPath());
                    final BmobFile bmobFile=new BmobFile(file);
                    DialogUtil.showProgress(getActivity(),true);//显示加载dialog
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            DialogUtil.showProgress(getActivity(),false);
                            if(e==null){
                                //bmobFile.getFileUrl()--返回的上传文件的完整地址
//								toast("上传文件成功:" + bmobFile.getFileUrl());
                                LogUtils.logd("上传文件成功:" + bmobFile.getFileUrl());
                                bean.setKindIcon(bmobFile.getFileUrl());
                                Glide.with(activity).load(file).placeholder(R.drawable.icon_photo_add_2).centerCrop().into(ivAddKind);
                            }else{
                                ((BaseActivity)getActivity()).toast("上传文件失败:"+e.getMessage() );
                            }

                        }
                    });
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ———————————————打开权限模块：照相———————————————

    @OnShowRationale(Manifest.permission.CAMERA)
    void show(PermissionRequest request) {
        DialogUtil.showDialog(getActivity(), getString(R.string.permission_camera_grant),false);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void deny() {
        activity.toast(R.string.permission_camera_no);
//		BaseUtils.ToastLong(R.string.permission_camera_no);
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void never() {
        DialogUtil.showDialog(activity,activity.getString(R.string.permission_camera_go_to_setting),false);
//		DialogUtil.buildNoPermissionAndGoSetting(getActivity(), getString(R.string.permission_camera_go_to_setting));
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AddKindFragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

}