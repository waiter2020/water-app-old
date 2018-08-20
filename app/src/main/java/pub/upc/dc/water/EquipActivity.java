package pub.upc.dc.water;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import pub.upc.dc.water.bean.EquipmentInfo;
import pub.upc.dc.water.bean.Family;
import pub.upc.dc.water.data.AppData;
import pub.upc.dc.water.utils.Action;

public class EquipActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    private EquipmentInfo equipmentInfo;
    private boolean isChange=false;
    private Toolbar toolbar;
    private GridLayout mGridLayout;
    private int columnCount; //列数
    private int screenWidth; //屏幕宽度

    private TextView waterUsage;
    private TextView thresholdType;
    private TextView thresholdValue;
    private TextView model;
    private TextView equipState;
    private TextView electricity;
    private TextView equipName;
    private TextView equipId;

    private Switch aSwitch;

    private Button getData;
    private Button restart;
    private Button threshold;
    private Button changeModel;
    private Button waterCondition;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip);
        initData();
        initView();
        showData();
    }

    private void initView(){

        waterUsage = (TextView) findViewById(R.id.waterUsage);
        thresholdType= (TextView) findViewById(R.id.thresholdType);
        thresholdValue = (TextView) findViewById(R.id.thresholdValue);
        model= (TextView) findViewById(R.id.model);
        equipState = (TextView) findViewById(R.id.equipState);
        electricity = (TextView) findViewById(R.id.electricity);
        aSwitch= (Switch) findViewById(R.id.switch_equip);
        getData = (Button) findViewById(R.id.get_data);
        restart = (Button) findViewById(R.id.restart);
        threshold = (Button) findViewById(R.id.threshold);
        waterCondition = (Button) findViewById(R.id.water_condition);

        changeModel = (Button) findViewById(R.id.change_model);
        equipName= (TextView) findViewById(R.id.equip_name_);
        equipId = (TextView) findViewById(R.id.equip_id_);

        aSwitch.setOnClickListener(this);
        getData.setOnClickListener(this);
        restart.setOnClickListener(this);
        threshold.setOnClickListener(this);
        changeModel.setOnClickListener(this);
        waterCondition.setOnClickListener(this);


        toolbar = (Toolbar) findViewById(R.id.equip_head);
        toolbar.setTitle("设备信息");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(tooBarListener);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }


        mGridLayout = (GridLayout) findViewById(R.id.grid_layout);
        columnCount = mGridLayout.getColumnCount();
        screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        for (int i = 0; i < mGridLayout.getChildCount(); i++) {
            Button button = (Button) mGridLayout.getChildAt(i);
            button.setWidth(screenWidth / columnCount);
        }

    }

    private void initData(){
        context=this;
        if(equipmentInfo==null) {
            Intent intent = getIntent();
            equipmentInfo = (EquipmentInfo) intent.getSerializableExtra("item");
        }
        equipmentInfo = Action.getEquipInfoById(equipmentInfo.getEquipId(),context);
    }

    private void showData(){
        equipId.setText(String.valueOf(equipmentInfo.getEquipId()));
        equipName.setText(String.valueOf(equipmentInfo.getName()));
        waterUsage.setText(String.valueOf(equipmentInfo.getWaterUsage()));
        if (equipmentInfo.getThresholdType()==0){
            thresholdType.setText("时间");
        }else {
            thresholdType.setText("流量");
        }

        thresholdValue.setText(String.valueOf(equipmentInfo.getThresholdValue()));

        switch (equipmentInfo.getModel()){
            case 0:
                model.setText("大漏失、小漏失都检测");
                break;
            case 1:
                model.setText("只检测小漏");
                break;
            case 2:
                model.setText("只检测大漏失");
                break;
            case 3:
                model.setText("大小漏失都不检测");
                break;
        }

        if (equipmentInfo.isOnline()){
            if(!equipmentInfo.isOpen()&&equipmentInfo.getEquipState()!=1&&equipmentInfo.getEquipState()!=2){
                equipState.setText("关总阀");
            }else {
                switch (equipmentInfo.getEquipState()){
                    case 0:
                        equipState.setText("正常");
                        break;
                    case 1:
                        equipState.setText("小漏失");
                        break;
                    case 2:
                        equipState.setText("大漏失");
                        break;
                    case 5:
                        equipState.setText("正常");
                        break;
                }
            }
        }else {
            equipState.setText("离线");
        }

        if (equipmentInfo.isLowQuantityOfElectricity()){
            electricity.setText("低电量");
        }else {
            electricity.setText("正常");
        }

        aSwitch.setChecked(equipmentInfo.isOpen());



        if (equipmentInfo.isOnline()){
            restart.setClickable(true);
            getData.setClickable(true);
            aSwitch.setClickable(true);
        }else {
            restart.setClickable(false);
            getData.setClickable(false);
            aSwitch.setClickable(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.equip_menu, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 动态设置ToolBar状态
        if(AppData.getUser().getFamily().getAdmin().equals(AppData.getUser().getUsername())){
            menu.findItem(R.id.delete_equip).setVisible(true);
        }else {
            menu.findItem(R.id.delete_equip).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }
    Toolbar.OnMenuItemClickListener tooBarListener =new Toolbar.OnMenuItemClickListener(){

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete_equip:

                    new AlertDialog.Builder(context)
                            .setTitle("提示")
                            .setMessage("此操作将会从您的设备列表中移除此设备，您确定要这样做？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String s = Action.deleteEquip(equipmentInfo.getEquipId(), context);
                                    if (s!=null){
                                        new AlertDialog.Builder(context)
                                                .setTitle("提示")
                                                .setMessage(s)
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Activity activity = (Activity) context;
                                                        Intent intent = activity.getIntent();
                                                        intent.putExtra("isChange",true);
                                                        setResult(1,intent);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            Intent intent = getIntent();
            intent.putExtra("isChange",isChange);
            setResult(1,intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        isChange=true;
        String s=null;
        switch (v.getId()){
            case R.id.switch_equip:
                 s = Action.openOrCloseEquip(equipmentInfo.getEquipId(), context);
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                initData();
                showData();
                break;
            case R.id.get_data:
                 s = Action.getEquipData(equipmentInfo.getEquipId(), context);
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                initData();
                showData();
                break;
            case R.id.restart:
                s = Action.restartEquip(equipmentInfo.getEquipId(), context);
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                initData();
                showData();
                break;
            case R.id.change_model:
                AlertDialog.Builder builder = new AlertDialog.Builder(context,android.R.style.Theme_Holo_Light_Dialog);
                //builder.setIcon(R.drawable.ic_launcher);
                builder.setTitle("选择一个模式");
                //    指定下拉列表的显示数据

                final String[] model = {"大漏失、小漏失都检测", "只检测小漏失", "只检测大漏失", "大小漏失都不检测", };
                //    设置一个下拉的列表选择项
                builder.setItems(model, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(context, "选择的模式为：" + model[which], Toast.LENGTH_SHORT).show();
                        String s = Action.changeModelEquip(equipmentInfo.getEquipId(),which, context);
                        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                        initData();
                        showData();

                    }
                });
                builder.show();
                break;

            case R.id.threshold:
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("请选择阈值类型");
                final String[] type = { "时间","流量"};
                final int[] t = new int[1];
                t[0]=0;
                //    设置一个单项选择下拉框
                /**
                 * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                 * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
                 * 第三个参数给每一个单选项绑定一个监听器
                 */
                builder1.setSingleChoiceItems(type, 0, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(context, "类型为：" + type[which], Toast.LENGTH_SHORT).show();
                        t[0]=which;
                    }
                });


                LayoutInflater from = LayoutInflater.from(this);
                final View inflate = from.inflate(R.layout.input, null);

                final TextView viewById = (TextView) inflate.findViewById(R.id.tip);
                viewById.setText("请输入阈值");

                builder1.setView(inflate);
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        EditText viewById1 = (EditText) inflate.findViewById(R.id.input);
                        String text = viewById1.getText().toString();
                        if (text==null||text.length()<3){
                            Toast.makeText(getApplicationContext(),"请输入有效数据",Toast.LENGTH_LONG).show();
                            return;
                        }else {
                            try{
                                Integer.parseInt(text);
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(),"请输入有效数据",Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        Map<String,Object> map = new HashMap<>();
                        map.put("equipId",equipmentInfo.getEquipId());
                        map.put("thresholdType",t[0]);
                        map.put("thresholdValue",Integer.parseInt(text));
                        String s1 = Action.changeThresholdEquip(map, context);
                        Toast.makeText(context,s1,Toast.LENGTH_LONG).show();
                        initData();
                        showData();
                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                builder1.show();
                break;

            case R.id.water_condition:
                Intent intent = new Intent(context, WaterConditionActivity.class);
                intent.putExtra("equip",equipmentInfo);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = getIntent();
        intent.putExtra("isChange",isChange);
        setResult(1,intent);
        super.onBackPressed();
    }

}
