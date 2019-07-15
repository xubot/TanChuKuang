package com.example.xykf.tanchukuang;

import android.app.Dialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xykf.tanchukuang.listener.onClickListener;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private String[] provinceList=new String[]{"省一","省二","省三","省四","省五"};
    private String[] cityList=new String[]{"全部市","市一","市二","市三","市四","市五"};
    private String[] areaList=new String[]{"全部县","县一","县二","县三","县四","县五"};
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        address = findViewById(R.id.tv_address);
        address.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_address:
                // 以特定的风格创建一个dialog
                final Dialog dialog = new Dialog(MainActivity.this,R.style.MyDialog);
                // 加载dialog布局view
                View addressView = LayoutInflater.from(MainActivity.this).inflate(R.layout.address_dialog, null);
                // 设置外部点击 取消dialog
                dialog.setCancelable(true);
                // 获得窗体对象
                Window window = dialog.getWindow();
                // 设置窗体的对齐方式
                window.setGravity(Gravity.BOTTOM);
                // 设置窗体动画
                window.setWindowAnimations(R.style.AnimBottom);
                // 设置窗体的padding
                window.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = 1200;
                window.setAttributes(lp);
                dialog.setContentView(addressView);
                dialog.show();

                final TextView title=addressView.findViewById(R.id.title);
                TextView sureT=addressView.findViewById(R.id.sure_tv);
                final RelativeLayout provinceR=addressView.findViewById(R.id.province_R);
                final TextView provinceT=addressView.findViewById(R.id.province_tv);
                final View provinceV=addressView.findViewById(R.id.province_v);
                final RelativeLayout cityR=addressView.findViewById(R.id.city_R);
                final TextView cityT=addressView.findViewById(R.id.city_tv);
                final View cityV=addressView.findViewById(R.id.city_v);
                final RelativeLayout areaR=addressView.findViewById(R.id.area_R);
                final TextView areaT=addressView.findViewById(R.id.area_tv);
                final View areaV=addressView.findViewById(R.id.area_v);
                final RecyclerView addressRecycleView=addressView.findViewById(R.id.address_RV);

                AddressAdapter addressAdapter = getAddressAdapter(addressRecycleView,provinceList);
                //条目的点击事件
                addressAdapter.setOnClickListener(new onClickListener<String>() {//省的点击事件
                    @Override
                    public void onClick(View view, int position, String item) {
                        provinceT.setText(provinceList[position]);//给省赋值
                        cityR.setVisibility(View.VISIBLE);
                        provinceV.setVisibility(View.GONE);
                        provinceT.setTextColor(Color.BLUE);
                        cityT.setTextColor(Color.BLACK);
                        areaT.setTextColor(Color.BLACK);
                        AddressAdapter addressAdapter = getAddressAdapter(addressRecycleView,cityList);
                        addressAdapter.setOnClickListener(new onClickListener<String>() {//市的点击事件
                            @Override
                            public void onClick(View view, int position, String item) {
                                cityT.setText(cityList[position]);//给市赋值
                                areaR.setVisibility(View.VISIBLE);
                                cityV.setVisibility(View.GONE);
                                provinceT.setTextColor(Color.BLACK);
                                cityT.setTextColor(Color.BLUE);
                                areaT.setTextColor(Color.BLACK);
                                if(!cityT.getText().toString().equals("全部市")){
                                    AddressAdapter addressAdapter = getAddressAdapter(addressRecycleView,areaList);
                                    addressAdapter.setOnClickListener(new onClickListener<String>() {//县的点击事件
                                        @Override
                                        public void onClick(View view, int position, String item) {
                                            areaT.setText(areaList[position]);//给县赋值
                                            provinceT.setTextColor(Color.BLACK);
                                            cityT.setTextColor(Color.BLACK);
                                            areaT.setTextColor(Color.BLUE);
                                        }
                                    });
                                }else {
                                    areaR.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                });

                sureT.setOnClickListener(new View.OnClickListener() {//确认点击事件
                    @Override
                    public void onClick(View view) {
                        if(provinceT.getText().toString().equals("请选择")&&cityT.getText().toString().equals("请选择")
                           &&areaT.getText().toString().equals("请选择")){
                            Toast.makeText(MainActivity.this, "请选择区域", Toast.LENGTH_SHORT).show();
                        }else {
                            if(!provinceT.getText().toString().equals("请选择")&&cityT.getText().toString().equals("请选择")){
                                address.setText(provinceT.getText().toString());
                            }else if(!cityT.getText().toString().equals("请选择")&&areaT.getText().toString().equals("请选择")
                               &&cityT.getText().toString().equals("全部市")){
                                address.setText(provinceT.getText().toString());
                            }else if(!cityT.getText().toString().equals("全部市")&&!areaT.getText().toString().equals("请选择")
                                    &&areaT.getText().toString().equals("全部县")){
                                //address.setText(provinceT.getText().toString()+"=="+cityT.getText().toString());
                                if(!cityT.getText().toString().equals("请选择")&&!areaT.getText().toString().equals("请选择")
                                   ||areaT.getText().toString().equals("全部县")){
                                    address.setText(cityT.getText().toString());
                                }else {
                                    address.setText(provinceT.getText().toString());
                                }
                            }else {
                                if(!areaT.getText().toString().equals("请选择")&&!areaT.getText().toString().equals("全部县")){
                                    address.setText(areaT.getText().toString());
                                }else {
                                    address.setText(cityT.getText().toString());
                                }
                                //address.setText(provinceT.getText().toString()+"=="+cityT.getText().toString()+"=="+areaT.getText().toString());
                            }
                        }
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    @NonNull
    private AddressAdapter getAddressAdapter(RecyclerView addressRecycleView,String[] address) {
        LinearLayoutManager managerDynamic = new LinearLayoutManager(this);
        managerDynamic.setOrientation(RecyclerView.VERTICAL);
        addressRecycleView.setLayoutManager(managerDynamic);
        AddressAdapter addressAdapter = new AddressAdapter(this, address);
        addressRecycleView.setAdapter(addressAdapter);
        return addressAdapter;
    }

}
