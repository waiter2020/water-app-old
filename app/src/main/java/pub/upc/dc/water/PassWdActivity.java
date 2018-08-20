package pub.upc.dc.water;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import pub.upc.dc.water.utils.Action;

public class PassWdActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private EditText oldPw;
    private EditText newPw;
    private EditText newPw2;
    private String pwd;
    private Button button;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_wd);
        context=this;
        initView();
        initData();
    }

    private void initView(){
        toolbar= (Toolbar) findViewById(R.id.tl_head);
        toolbar.setTitle("修改密码");
        setSupportActionBar(toolbar);


        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        oldPw= (EditText) findViewById(R.id.old_passwd);
        newPw= (EditText) findViewById(R.id.passwd);
        newPw2= (EditText) findViewById(R.id.passwd2);
        button= (Button) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldPw.getText().toString().equals(pwd)){
                    if(newPw.getText().toString().equals(newPw2.getText().toString())){
                        Map<String,Object> map = new HashMap<>();
                        map.put("pwd",newPw.getText().toString());
                        String s = Action.changeUserPwd(map, context);
                        new AlertDialog.Builder(context)
                                .setTitle("提示")
                                .setMessage(s+"\n"+"请重新登录")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    Action.logout(context);
                                    }
                                }).show();
                    }else {
                        new AlertDialog.Builder(context)
                                .setTitle("错误")
                                .setMessage("两次输入密码不相同")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                }else {
                    new AlertDialog.Builder(context)
                            .setTitle("错误")
                            .setMessage("原密码不正确")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            }
        });
    }

    private void initData(){
        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        pwd=sharedPreferences.getString("passwd","");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
