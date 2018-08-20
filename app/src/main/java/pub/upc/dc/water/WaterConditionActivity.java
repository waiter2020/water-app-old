package pub.upc.dc.water;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pub.upc.dc.water.bean.EquipmentInfo;
import pub.upc.dc.water.bean.WaterCondition;
import pub.upc.dc.water.data.PageBean;
import pub.upc.dc.water.fragment.MyWaterConditionRecyclerViewAdapter;
import pub.upc.dc.water.fragment.WaterConditionFragment;
import pub.upc.dc.water.utils.Action;

public class WaterConditionActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;

    private Context context;
    private RecyclerView recyclerView;
    private TextView tip;
    private EditText startTime;
    private EditText endTime;
    private PageBean<WaterCondition> pageBean;
    private EquipmentInfo equipmentInfo;

    private Button select;
    private Button top;
    private Button previous;
    private Button next;
    private Button end;
    private Button goTo;

    private MyWaterConditionRecyclerViewAdapter myWaterConditionRecyclerViewAdapter;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm",Locale.CHINA);

    //获取日期格式器对象

    private Calendar startCalendar = Calendar.getInstance(Locale.CHINA);
    private Calendar endCalendar = Calendar.getInstance(Locale.CHINA);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_condition);
        context=this;
        initView();
        initData();
        showData();
        updateTimeShow();
    }

    private void initData() {
        Intent intent = getIntent();
        equipmentInfo = (EquipmentInfo) intent.getSerializableExtra("equip");
    }

    private void initView(){
        tip= (TextView) findViewById(R.id.tip);
        select = (Button) findViewById(R.id.select);
        top = (Button) findViewById(R.id.top);
        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        end = (Button) findViewById(R.id.end);
        goTo = (Button) findViewById(R.id.go_to);

        select.setOnClickListener(this);
        top.setOnClickListener(this);
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        end.setOnClickListener(this);
        goTo.setOnClickListener(this);
        tip.setText("");

        recyclerView= (RecyclerView) findViewById(R.id.frame_layout);

        toolbar = (Toolbar) findViewById(R.id.head);
        toolbar.setTitle("用水信息");

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        startTime = (EditText) findViewById(R.id.start_time);
        startTime.setFocusable(false);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDatePicker(startCalendar);
            }
        });


        endTime = (EditText) findViewById(R.id.end_time);

        endTime.setFocusable(false);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(endCalendar);
            }
        });

    }


    private void showData(){
        List<WaterCondition> list=null;
        if(pageBean==null||pageBean.getPageData().size()==0){
            list=new ArrayList<>();
            tip.setText("");
            top.setVisibility(View.GONE);
            previous.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            end.setVisibility(View.GONE);
            goTo.setVisibility(View.GONE);
        }else {
            list=pageBean.getPageData();
            top.setVisibility(View.VISIBLE);
            previous.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            end.setVisibility(View.VISIBLE);
            goTo.setVisibility(View.VISIBLE);
            tip.setText("当前第"+(pageBean.getCurrentPage()+1)+"页/共"+pageBean.getTotalPage()+"页");
        }

        myWaterConditionRecyclerViewAdapter = new MyWaterConditionRecyclerViewAdapter(list, new WaterConditionFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(WaterCondition item) {

            }
        });

        recyclerView.setAdapter(myWaterConditionRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myWaterConditionRecyclerViewAdapter.notifyDataSetChanged();
    }


    private void getWaterConditions(int page){
        if (page<0){
            page=0;
        }else if (pageBean!=null&&page>=pageBean.getTotalPage()){
            page=pageBean.getTotalPage()-1;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("page", page);
        map.put("equipId",equipmentInfo.getEquipId());
        map.put("startTime",startTime.getText().toString());
        map.put("endTime",endTime.getText().toString());
        pageBean = Action.getWaterCondition(map, context);
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                    @Override

                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //同DatePickerDialog控件

                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);

                        calendar.set(Calendar.MINUTE,minute);
                        updateTimeShow();
                    }

                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);

                timePickerDialog.show();


            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

        updateTimeShow();

        //将页面TextView的显示更新为最新时间
    }

    private void updateTimeShow(){

        //将页面TextView的显示更新为最新时间

        startTime.setText(dateFormat.format(startCalendar.getTime()));
        endTime.setText(dateFormat.format(endCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        int page;
        switch (v.getId()){
            case R.id.select:
            case R.id.top:
                getWaterConditions(0);
                showData();
                break;
            case R.id.previous:
                if (pageBean==null){
                    page=0;
                }else {
                    page=pageBean.getCurrentPage()-1;
                }
                getWaterConditions(page);
                showData();
                break;
            case R.id.next:
                if (pageBean==null){
                    page=0;
                }else {
                    page=pageBean.getCurrentPage()+1;
                }
                getWaterConditions(page);
                showData();
                break;
            case R.id.end:
                if (pageBean==null){
                    page=0;
                }else {
                    page=pageBean.getTotalPage()-1;
                }
                getWaterConditions(page);
                showData();
                break;
            case R.id.go_to:
                LayoutInflater from = LayoutInflater.from(this);
                final View inflate = from.inflate(R.layout.input, null);


                AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(inflate);
                builder.setTitle("请输入跳转的页数");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText input = (EditText)inflate.findViewById(R.id.input);
                        Editable text = input.getText();
                        if ("".equals(text.toString())){
                            try {

                                Integer.parseInt(text.toString());
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(),"请输入有效数据",Toast.LENGTH_LONG).show();
                                return;
                            }
                            Toast.makeText(getApplicationContext(),"请输入有效数据",Toast.LENGTH_LONG).show();
                            return;
                        }
                        getWaterConditions(Integer.parseInt(text.toString())-1);
                        showData();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create();
                builder.show();
                break;
        }
    }
}
