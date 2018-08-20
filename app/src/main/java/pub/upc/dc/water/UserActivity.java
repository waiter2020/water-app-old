package pub.upc.dc.water;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import pub.upc.dc.water.bean.User;
import pub.upc.dc.water.data.AppData;
import pub.upc.dc.water.utils.Action;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private Context context;
    private EditText userName;
    private EditText phone;
    private EditText email;
    private EditText birth;
    private Button changePwd;
    private boolean a,b;
    private Calendar calendar=Calendar.getInstance(Locale.CHINA);;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        context=this;
        initView();
        showData();
    }


    private void initView(){
        toolbar= (Toolbar) findViewById(R.id.tl_head);
        toolbar.setTitle("个人信息");
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(tooBarListener);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        userName = (EditText) findViewById(R.id.username);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        birth = (EditText) findViewById(R.id.birth);

        changePwd = (Button) findViewById(R.id.change_pwd);
        changePwd.setOnClickListener(this);

        userName.setEnabled(false);
        phone.setEnabled(false);
        email.setEnabled(false);
        birth.setEnabled(false);

        birth.setFocusable(false);
        birth.setClickable(false);
        birth.setOnClickListener(this);


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = email.getText().toString();
                boolean matches = text.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
                if (!matches) {
                    email.setTextColor(Color.rgb(255, 0, 0));
                    a = false;
                } else {
                    email.setTextColor(Color.rgb(0, 0, 0));
                    a = true;
                }
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = phone.getText().toString();
                boolean matches = text.matches("^(1[0-9])\\d{9}$");
                if (!matches) {
                    b = false;
                    phone.setTextColor(Color.rgb(255, 0, 0));
                } else {
                    phone.setTextColor(Color.rgb(0, 0, 0));
                    b = true;
                }
            }
        });
    }

    private void showData(){
        User user = AppData.getUser();
        userName.setText(String.valueOf(user.getUsername()));
        email.setText(String.valueOf(user.getEmail()));
        phone.setText(String.valueOf(user.getPhoneNumber()));
        birth.setText(String.valueOf(simpleDateFormat.format(user.getBirth())));
        calendar.setTime(user.getBirth());
    }


    Toolbar.OnMenuItemClickListener tooBarListener =new Toolbar.OnMenuItemClickListener(){

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Menu menu;
            switch (item.getItemId()){
                case R.id.change_user:
                    email.setEnabled(true);
                    phone.setEnabled(true);
                    birth.setEnabled(true);
                    birth.setClickable(true);
                    changePwd.setVisibility(View.GONE);
                     menu = toolbar.getMenu();
                    menu.findItem(R.id.save).setVisible(true);
                    item.setVisible(false);
                    break;
                case R.id.save:
                    if(a&&b){
                        Map<String,Object> map = new HashMap<>();
                        map.put("phoneNumber",phone.getText().toString());
                        map.put("email",email.getText().toString());
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm",Locale.CHINA);
                        map.put("birth",simpleDateFormat1.format(calendar.getTime()));
                        String s = Action.changeUser(map, context);
                        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                        Action.getUserInfo(context);
                        initView();
                        showData();
                        item.setVisible(false);
                        menu = toolbar.getMenu();
                        menu.findItem(R.id.change_user).setVisible(true);
                    }else {
                        new AlertDialog.Builder(context)
                                .setTitle("错误")
                                .setMessage("请输入正确数据")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        }
                                }).show();
                    }
                    break;
            }
            return true;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 动态设置ToolBar状态
        menu.findItem(R.id.save).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.birth:
                showDatePicker(calendar);
                break;
            case R.id.change_pwd:
                startActivity(new Intent(context,PassWdActivity.class));
                break;
        }
    }

    private void showDatePicker(final Calendar calendar){
        //生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                //修改日历控件的年，月，日

                //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致

                calendar.set(Calendar.YEAR,year);

                calendar.set(Calendar.MONTH,month);

                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                updateTimeShow();

            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();



        //将页面TextView的显示更新为最新时间
    }

    private void updateTimeShow(){

        //将页面TextView的显示更新为最新时间

        birth.setText(simpleDateFormat.format(calendar.getTime()));
    }
}
