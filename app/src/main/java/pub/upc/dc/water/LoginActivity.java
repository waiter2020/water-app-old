package pub.upc.dc.water;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Map;
import java.util.TreeMap;
import pub.upc.dc.water.data.AppData;
import pub.upc.dc.water.utils.Action;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText passwd;
    private CheckBox remember;
    private CheckBox autoLogin;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (AppData.getToken()!=null&&!"".equals(AppData.getToken())){
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        initView();
    }

    private void initView(){

        toolbar = (Toolbar) findViewById(R.id.tl_head);
        toolbar.setTitle("登录");

        setSupportActionBar(toolbar);

        username = (EditText) findViewById(R.id.edit_username);
        passwd = (EditText) findViewById(R.id.edit_passwd);
        remember = (CheckBox) findViewById(R.id.remember);
        autoLogin = (CheckBox) findViewById(R.id.auto_login);

        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("remember",false)){
            username.setText(sharedPreferences.getString("username",""));
            username.setSelection(sharedPreferences.getString("username","").length());
            passwd.setText(sharedPreferences.getString("passwd",""));
            remember.setChecked(true);
            if (sharedPreferences.getBoolean("autoLogin",false)){
                autoLogin.setChecked(true);
            }

            if (sharedPreferences.getBoolean("autoLogin",false)&&getIntent().getBooleanExtra("auto_login",true)){
                onBtn_loginClick(null);
            }


        }

    }


    public void onBtn_loginClick(View view) {

        String s1 = username.getText().toString();
        String s = passwd.getText().toString();

        Map<String,String> map = new TreeMap<>();
        map.put("username",s1);
        map.put("passwd",s);
        ProgressDialog progressDialog;

        progressDialog = ProgressDialog.show(this, "", "正在登录...");
        progressDialog.onStart();
        boolean login = Action.login(map,this);

        progressDialog.dismiss();
        if(login){
            saveUser();
            Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            remember.setChecked(false);
            autoLogin.setChecked(false);
            new AlertDialog.Builder(this)
                    .setTitle("错误")
                    .setMessage("用户名或密码错误")
                    .setPositiveButton("确定",null)
                    .show();
            username.setText("");
            passwd.setText("");
        }
    }

    private void saveUser(){
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("remember",remember.isChecked());
            edit.putString("username",username.getText().toString());
            edit.putString("passwd",passwd.getText().toString());
            edit.putBoolean("autoLogin",autoLogin.isChecked());
            edit.apply();

    }


    public void onBtn_regClick(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode){
            case 0:
                if (1 == resultCode){
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
                default:
                    break;
        }

    }


}
