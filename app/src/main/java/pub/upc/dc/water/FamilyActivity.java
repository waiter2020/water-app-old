package pub.upc.dc.water;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

import pub.upc.dc.water.bean.User;
import pub.upc.dc.water.data.AppData;
import pub.upc.dc.water.utils.Action;


public class FamilyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Context context;
    private TextView userName;
    private TextView phone;
    private TextView email;
    private TextView birth;

    private User user;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        context=this;
        initView();
        initData();
        showData();
    }


    private void initView(){
        toolbar= (Toolbar) findViewById(R.id.tl_head);
        toolbar.setTitle("成员信息");
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(tooBarListener);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        userName = (TextView) findViewById(R.id.username);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        birth = (TextView) findViewById(R.id.birth);
    }

    private void initData(){
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }

    private void showData(){
        phone.setText(String.valueOf(user.getPhoneNumber()));
        userName.setText(String.valueOf(user.getUsername()));
        email.setText(String.valueOf(user.getEmail()));
        birth.setText(simpleDateFormat.format(user.getBirth()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.family_menu, menu);
        return true;
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 动态设置ToolBar状态
        if(AppData.getUser().getFamily().getAdmin().equals(AppData.getUser().getUsername())&&!AppData.getUser().getUsername().equals(user.getUsername())){
            menu.findItem(R.id.delete_user).setVisible(true);
        }else {
            menu.findItem(R.id.delete_user).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }


    Toolbar.OnMenuItemClickListener tooBarListener =new Toolbar.OnMenuItemClickListener(){

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete_user:

                    new AlertDialog.Builder(context)
                            .setTitle("提示")
                            .setMessage("此操作将会从您的家庭组中移除此人，您确定要这样做？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String s = Action.deleteUser(user.getId(),context);
                                    if (s!=null){
                                        new AlertDialog.Builder(context)
                                                .setTitle("提示")
                                                .setMessage(s)
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Activity activity = (Activity) context;
                                                        activity.finish();
                                                    }
                                                })
                                                .show();
                                    }
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();


                    break;
            }
            return true;
        }
    };
}
