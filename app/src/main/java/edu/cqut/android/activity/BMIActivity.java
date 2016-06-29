package edu.cqut.android.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import edu.cqut.android.common.weight.DialChart02View;

/**
 * 计算BMI指数
 * 代码中的图表显示控件采用的是第三方，详情请参考README.txt
 */
public class BMIActivity extends Activity implements View.OnClickListener{
    //图表控件
    private DialChart02View chart5 = null;
    //右上角关于BMI图标
    private ImageView aboutBmi = null;
    //体重输入栏
    private EditText weight = null;
    //身高输入栏
    private EditText height = null;
    //计算按钮
    private Button calculate = null;
    //BMI指数
    private float BMINum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        //通过id得到控件
        chart5 = (DialChart02View) findViewById(R.id.circle);
        aboutBmi = (ImageView) findViewById(R.id.about_bmi);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        calculate = (Button) findViewById(R.id.calculate);

        //设置控件的点击事件
        aboutBmi.setOnClickListener(this);
        calculate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_bmi:showMessage();break;
            case R.id.calculate:calculate();break;
        }
    }

    /**
     * 计算BMI指数
     */
    private void calculate() {
        if ( !isEmpty()){
            Toast.makeText(this, "请输入体重或者身高!", Toast.LENGTH_LONG).show();
        }else{
            try{
                float weightNum = Float.parseFloat((weight.getText().toString()));
                float heightNum = Float.parseFloat(height.getText().toString());
                BMINum = ((int)(weightNum / (heightNum * heightNum) * 100)) / 100.00f;
                if (BMINum >= 50){
                    Toast.makeText(this, "爆表了！该减肥了！", Toast.LENGTH_LONG).show();
                }
                chart5.setCurrentStatus(BMINum);
                chart5.invalidate();
            }catch (NumberFormatException e){
                Toast.makeText(this,"请输入正确的数字!", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 判断信息是否录入完整
     * @return
     */
    private boolean isEmpty(){
        if (TextUtils.isEmpty(weight.getText().toString()) || TextUtils.isEmpty(height.getText().toString())){
            return false;
        }
        return true;
    }


    /**
     * 弹出介绍BMI窗口
     */
    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
        builder.setTitle("BMI简介");
        builder.setMessage(getResources().getString(R.string.about_bmi));
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}
