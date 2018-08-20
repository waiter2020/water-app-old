package pub.upc.dc.water;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import pub.upc.dc.water.bean.EquipmentInfo;
import pub.upc.dc.water.bean.Family;
import pub.upc.dc.water.bean.User;
import pub.upc.dc.water.data.AppData;
import pub.upc.dc.water.data.EquipmentInfoContent;
import pub.upc.dc.water.data.UserContent;
import pub.upc.dc.water.fragment.ItemFragment;
import pub.upc.dc.water.fragment.MyItemRecyclerViewAdapter;
import pub.upc.dc.water.fragment.MyThreeRecyclerViewAdapter;
import pub.upc.dc.water.fragment.MyTowRecyclerViewAdapter;
import pub.upc.dc.water.fragment.ThreeFragment;
import pub.upc.dc.water.fragment.TowFragment;
import pub.upc.dc.water.utils.Action;
import pub.upc.dc.water.widget.BadgeRadioButton;

import static pub.upc.dc.water.R.id.frame_layout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;
    private MyThreeRecyclerViewAdapter myThreeRecyclerViewAdapter;
    private MyTowRecyclerViewAdapter myTowRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private BadgeRadioButton one;
    private BadgeRadioButton two;
    private BadgeRadioButton three;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2;

    private RadioGroup radioGroup;
    private Toolbar toolbar;
    private Button createFamily;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);

            context = this;

            if (AppData.getToken() == null || "".equals(AppData.getToken())) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                initView();
                initRecyclerView();
                initData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(frame_layout);
        one = (BadgeRadioButton) findViewById(R.id.one);
        two = (BadgeRadioButton) findViewById(R.id.two);
        three = (BadgeRadioButton) findViewById(R.id.three);
        linearLayout = (LinearLayout) findViewById(R.id.top);
        linearLayout2 = (LinearLayout) findViewById(R.id.top2);

        radioGroup = (RadioGroup) findViewById(R.id.rg_home_select_tab);
        createFamily = (Button) findViewById(R.id.create_family);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        one.clearNum();
        two.clearNum();
        three.clearNum();

        toolbar = (Toolbar) findViewById(R.id.tl_head);
        toolbar.setTitle("设备列表");

        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(tooBarListener);

    }

    private void initRecyclerView() {
        toolbar.setTitle("设备列表");

        Action.getEquipInfo(this);
        if (EquipmentInfoContent.getCOUNT()>0) {
            linearLayout.setVisibility(View.VISIBLE);
        }else {
            linearLayout.setVisibility(View.GONE);
        }
        linearLayout2.setVisibility(View.GONE);

        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(EquipmentInfoContent.getITEMS(), new ItemFragment.OnListFragmentInteractionListener() {

            @Override
            public void onListFragmentInteraction(EquipmentInfo item) {
                Toast.makeText(getApplicationContext(), item.getEquipId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, EquipActivity.class);
                intent.putExtra("item",item);
                startActivityForResult(intent,1);
            }
        });


        recyclerView.setAdapter(myItemRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myItemRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void initTwoView() {
        if(AppData.getUser().getFamily()!=null){
            toolbar.setTitle(String.valueOf(AppData.getUser().getFamily().getFamilyName()));
            linearLayout2.setVisibility(View.VISIBLE);
        }else {
            toolbar.setTitle("家庭组列表");
            linearLayout2.setVisibility(View.GONE);
        }
        Action.getFamily(this);
        linearLayout.setVisibility(View.GONE);
        myTowRecyclerViewAdapter = new MyTowRecyclerViewAdapter(UserContent.getITEMS(), new TowFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(User item) {
                Toast.makeText(getApplicationContext(), item.getUsername(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,FamilyActivity.class);
                intent.putExtra("user",item);
                startActivityForResult(intent,2);
            }
        });
        recyclerView.setAdapter(myTowRecyclerViewAdapter);
        myTowRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void initThreeView() {
        toolbar.setTitle("个人信息");
        createFamily.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);
        myThreeRecyclerViewAdapter = new MyThreeRecyclerViewAdapter(new ThreeFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction() {

            }
        });

        try {
            recyclerView.setAdapter(myThreeRecyclerViewAdapter);
        } catch (Exception e) {
            Toast.makeText(this, e.getCause().toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void initData() {
        Action.getUserInfo(this);
    }

    @Override
    public void onClick(View view) {
        invalidateOptionsMenu();
        switch (view.getId()) {
            case R.id.one:
                initRecyclerView();
                break;
            case R.id.two:
                initTwoView();
                break;
            case R.id.three:
                initThreeView();

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 动态设置ToolBar状态
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.one:
                if (AppData.getUser().getFamily() != null&&AppData.getUser().getFamily().getAdmin().equals(AppData.getUser().getUsername())) {
                    menu.findItem(R.id.add_equip).setVisible(true);
                    createFamily.setVisibility(View.GONE);
                } else if(AppData.getUser().getFamily() == null){
                    menu.findItem(R.id.add_equip).setVisible(false);
                    linearLayout.setVisibility(View.GONE);
                    createFamily.setVisibility(View.VISIBLE);
                }else {
                    menu.findItem(R.id.add_equip).setVisible(false);
                    createFamily.setVisibility(View.GONE);
                }
                menu.findItem(R.id.add_user).setVisible(false);
                menu.findItem(R.id.action_settings).setVisible(false);
                menu.findItem(R.id.remove_family).setVisible(false);
                menu.findItem(R.id.exit_family).setVisible(false);
                break;
            case R.id.two:
                menu.findItem(R.id.add_equip).setVisible(false);
                if (AppData.getUser().getFamily() == null||!AppData.getUser().getFamily().getAdmin().equals(AppData.getUser().getUsername())) {
                    menu.findItem(R.id.add_user).setVisible(false);
                    menu.findItem(R.id.remove_family).setVisible(false);
                    if(AppData.getUser().getFamily() != null) {
                        menu.findItem(R.id.exit_family).setVisible(true);
                    }else {
                        menu.findItem(R.id.exit_family).setVisible(false);
                        createFamily.setVisibility(View.VISIBLE);
                    }

                } else {
                    menu.findItem(R.id.remove_family).setVisible(true);
                    menu.findItem(R.id.add_user).setVisible(true);
                    menu.findItem(R.id.exit_family).setVisible(false);
                    createFamily.setVisibility(View.GONE);
                }
                menu.findItem(R.id.action_settings).setVisible(false);
                break;
            case R.id.three:
                menu.findItem(R.id.add_equip).setVisible(false);
                menu.findItem(R.id.add_user).setVisible(false);
                menu.findItem(R.id.remove_family).setVisible(false);
                menu.findItem(R.id.exit_family).setVisible(false);
                menu.findItem(R.id.action_settings).setVisible(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressLint("InflateParams")
    public void createFamily(View view) {
        LayoutInflater from = LayoutInflater.from(this);
        final View inflate = from.inflate(R.layout.input, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(inflate);
        builder.setTitle("请输入家庭组名称");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText input = (EditText)inflate.findViewById(R.id.input);
                Editable text = input.getText();
                if ("".equals(text.toString())){
                    Toast.makeText(getApplicationContext(),"请输入有效数据",Toast.LENGTH_LONG).show();
                    return;
                }
                Family family = new Family();
                family.setAdmin(AppData.getUser().getUsername());
                family.setFamilyName(text.toString());

                boolean family1 = Action.createFamily(family, getApplicationContext());
                if (family1){
                    Toast.makeText(getApplicationContext(),"创建成功",Toast.LENGTH_LONG).show();
                    createFamily.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getApplicationContext(),"创建失败",Toast.LENGTH_LONG).show();
                }
                initRecyclerView();
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
    }

    Toolbar.OnMenuItemClickListener tooBarListener =new Toolbar.OnMenuItemClickListener(){

     @Override
     public boolean onMenuItemClick(MenuItem item) {
         LayoutInflater from = LayoutInflater.from(context);
         final View inflate = from.inflate(R.layout.input, null);


         AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(inflate);
         switch (item.getItemId()){
             case R.id.add_equip:

                 builder.setTitle("请输入设备编号");
                 builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         EditText input = (EditText)inflate.findViewById(R.id.input);
                         Editable text = input.getText();
                         if ("".equals(text.toString())){
                             Toast.makeText(context,"请输入有效数据",Toast.LENGTH_LONG).show();
                             return;
                         }


                         String s = Action.addEquip(text.toString(), context);
                            if (s==null||"".equals(s)) {
                                Toast.makeText(context, "添加成功", Toast.LENGTH_LONG).show();
                                initRecyclerView();
                                myItemRecyclerViewAdapter.notifyDataSetChanged();
                            }else {
                                new AlertDialog.Builder(context)
                                        .setTitle("错误")
                                        .setMessage(s)
                                        .setPositiveButton("确定",null)
                                        .show();
                            }


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
             case R.id.add_user:
                 builder.setTitle("请输入用户名");
                 builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         EditText input = (EditText)inflate.findViewById(R.id.input);
                         Editable text = input.getText();
                         if ("".equals(text.toString())){
                             Toast.makeText(context,"请输入有效数据",Toast.LENGTH_LONG).show();
                             return;
                         }
                         String s = Action.addUser(text.toString().trim(), context);
                         if (s==null||"".equals(s)){
                             Toast.makeText(context,"添加成功",Toast.LENGTH_LONG).show();
                             initTwoView();
                             myTowRecyclerViewAdapter.notifyDataSetChanged();
                         }else {
                             Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                         }
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
             case R.id.remove_family:

                 new AlertDialog.Builder(context)
                         .setTitle("提示")
                         .setMessage("此操作不可逆，可能会造成不可挽回的后果，您确定要这样做？")
                         .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 String s = Action.removeFamily(context);
                                 if (s!=null){
                                     new AlertDialog.Builder(context)
                                             .setTitle("提示")
                                             .setMessage(s)
                                             .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                 @Override
                                                 public void onClick(DialogInterface dialog, int which) {
                                                     Action.getUserInfo(context);
                                                     initTwoView();
                                                 }
                                             })
                                             .show();
                                 }
                             }
                         })
                         .setNegativeButton("取消",null)
                         .show();
                 break;
             case R.id.exit_family:
                 new AlertDialog.Builder(context)
                         .setTitle("提示")
                         .setMessage("您即将退出家庭组，退出后将无法查看设备信息，您确定要这样做？")
                         .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 String s = Action.exitFamily(context);
                                 if (s!=null){
                                     new AlertDialog.Builder(context)
                                             .setTitle("提示")
                                             .setMessage(s)
                                             .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                 @Override
                                                 public void onClick(DialogInterface dialog, int which) {
                                                     Action.getUserInfo(context);
                                                     initTwoView();
                                                 }
                                             })
                                             .show();
                                 }
                             }
                         })
                         .setNegativeButton("取消",null)
                         .show();
                 break;
             case R.id.action_settings:
                 startActivity(new Intent(context,SettingsActivity.class));
                 break;
         }
         return true;
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode){
            case 1:
                if (resultCode==1&&intent.getBooleanExtra("isChange",false)){
                    initRecyclerView();
                    myItemRecyclerViewAdapter.notifyDataSetChanged();
                }

                break;
            case 2:
                initTwoView();
                break;
            default:
                break;
        }

    }

}
