package cn.com.sany.symc.zg.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.sany.symc.zg.R;
import cn.com.sany.symc.zg.entity.MultipleStateInfo;
import cn.com.sany.symc.zg.entity.SystemBrightManager;
import cn.com.sany.symc.zg.help.IConstant;
import cn.com.sany.symc.zg.serialport.SerialHelper;
import cn.com.sany.symc.zg.ui.fragment.IFunCallback;
import cn.com.sany.symc.zg.ui.fragment.MessageShowFragment;
import cn.com.sany.symc.zg.ui.fragment.PasswordInputFragment;
import cn.com.sany.symc.zg.util.CacheData;
import cn.com.sany.symc.zg.util.LogUtil;
import cn.com.sany.symc.zg.util.NumberBytes;

/**
 * 首页应用
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity.class";

    private byte temp;
    private byte[] temp_1 = new byte[1];
    private byte[] temp_3 = new byte[3];
    private byte[] temp_2 = new byte[2];

    private int tmp_0;
    private int tmp_1;
    private int tmp_2;
    private int tmp_3;
    private int tmp_4;
    private int tmp_5;
    private int tmp_6;
    private int tmp_7;

    private TextView signalStrength;
    private TextView BatteryElectricity;
    private FrameLayout signalStrengthLayout;
    private FrameLayout signalStrengthLayout1;
    private LinearLayout waterLLayout;
    // private LinearLayout startLLayout;
    private TextView WirelessLinkStatus;
    private TextView RemoteCtrlStatus;



    private LinearLayout noSignal;
    private LinearLayout haveSignal;

    private LinearLayout testB98LLayout;    //测试界面
    private LinearLayout mainLLayout;    //内容显示主界面

    private static int change_page_old = 0;  //保存之前一次上升沿  0x01从0到1切换

    private static int change_bright_old = 0;  //保存之前一次亮度上升沿   0x04从0到1切换

    private LinearLayout lowBattery;
    private LinearLayout haveBattery;
    private TextView Battery1;
    private TextView Battery2;
    private TextView Battery3;
    private TextView Battery4;

    private TextView tvLeftXz;

    private TextView tvRightXz;

    private boolean syncFlag = true;  //报第一次经纬度执行一次
    private CameraSurfaceView cameraView; //预览
    //串口帮助类
    private SerialHelper serialhelp;
    Handler mHandler = new MyHandler(this);
    private int cameraIndex = 7;
    private static int cameraFlag7 = 0;
    private static int cameraFlag5 = 0;
    private MessageShowFragment messageShowFragment = new MessageShowFragment();

    int count = 0;
    private long[] mHints = new long[3];
    private PasswordInputFragment passwordInputFragment;

    //更新定位地图信息
    private Timer timer_loc = new Timer();
    private TimerTask timerTask_loc;

    private static int cameraTime_flag = 0;

    //测试界面控件  start
    private ImageView b80_1_iv;
    private ImageView b80_0_iv;


    private ImageView b81_iv;
    private ImageView b82_iv;

    private ImageView b83_1_iv;
    private ImageView b83_0_iv;

    private ImageView b84_1_iv;
    private ImageView b84_0_iv;

    private ImageView b85_1_iv;
    private ImageView b85_0_iv;

    private ImageView b86_iv;
    private ImageView b87_iv;

    private ImageView  b90_1_iv;
    private ImageView  b90_0_iv;
    private ImageView  b91_iv;
    private ImageView  b925_iv;
    private ImageView  b967_1001_iv;

    private ImageView  b102_1_iv;
    private ImageView  b102_0_iv;
    private ImageView  b103_iv;
    private ImageView  b104_iv;
    private ImageView  b105_iv;
    private ImageView  b106_iv;
    private ImageView  b107_iv;


    private ImageView ivLeftFour1111;
    private ImageView ivLeftFour1122;
    private ImageView ivLeftFour1133;
    private ImageView ivLeftFour1144;

    private TextView tvLeftFour1111;
    private TextView tvLeftFour1122;
    private TextView tvLeftFour1133;
    private TextView tvLeftFour1144;

    private ImageView ivRightFour1111;
    private ImageView ivRightFour1122;
    private ImageView ivRightFour1133;
    private ImageView ivRightFour1144;

    private TextView tvRightFour1111;
    private TextView tvRightFour1122;
    private TextView tvRightFour1133;
    private TextView tvRightFour1144;

    private ImageView b1920_down_iv;
    private ImageView b1920_up_iv;
    private TextView  b1920_tv;

    private ImageView b2122_down_iv;
    private ImageView b2122_up_iv;
    private TextView  b2122_tv;

    private ImageView b2324_down_iv;
    private ImageView b2324_up_iv;
    private TextView  b2324_tv;

    private ImageView b25_01_iv;
    private ImageView b25_00_iv;
    private ImageView b25_11_iv;
    private ImageView b25_10_iv;

    //电池
    private LinearLayout lowBatteryTest;
    private LinearLayout haveBatteryTest;
    private TextView tvBatteryTest1;
    private TextView tvBatteryTest2;
    private TextView tvBatteryTest3;
    private TextView tvBatteryTest4;
    private TextView tvStartStaTest;      // 测试界面  未启动
    private LinearLayout noSignalTest;
    private LinearLayout haveSignalTest;
    private TextView signalStrengthTest;
    private FrameLayout signalStrengthLayoutTest;
    private FrameLayout signalStrengthLayout1Test;

    private LinearLayout fileManagerLL;

    //tanghui add for SYMC new
    private ImageView b32_iv;
    private ImageView b32_page_iv;
    private TextView b33_tv;
    private DashBoardView b34_tv;
    private TextView b35_tv;
    private TextView b36_tv;
    
    private TextView b3738_tv;
    private TextView b39_tv;
    private TextView b40_tv;
    private DashBoardView b4142_tv;
    private TextView b4344_tv;
    
    private TextView b4546_tv;
    private TextView b4748_tv;
    private TextView b4950_tv;
    private TextView b51_tv;
    
    private TextView b52_tv;
    private TextView b53_tv;
    private TextView b54_tv;
    private TextView b55_tv;
    
    private TextView b56_tv;
    private TextView b57_tv;
    private TextView b5859_tv;
    private TextView b6061_tv;
    
    private TextView b6263_tv;
    private TextView b6465_tv;
    private TextView b6667_tv;
    private TextView b6869_tv;
    
    private TextView b700_tv;


    
    private TextView b71_tv;

    private String question_old = "";


    //tanghui add en

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  设置屏幕始终在前面，不然点击鼠标，重新出现虚拟按键
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        // bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.STATUS_BAR_HIDDEN);

        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //初始化布局
        initView(savedInstanceState);

        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    //打开串口
                    LogUtil.d(TAG, "===========initSerial=========start=============");
                    initSerial();   //tanghui delete for crash
                } catch (Exception e) {
                    LogUtil.e(TAG, "========initSerial===========打开串口数据异常==================>" + e.getMessage());
                }
            }
        }, 1200);  //先连接控制器，获取控制器版本号 4000 to 1000


    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public int getCameraIndex() {
        return cameraIndex;
    }

    public void setCameraIndex(int index) {
        this.cameraIndex = index;
    }

    /**
     * 控件初始化
     */
    private void initView(Bundle savedInstanceState) {

        //初始化控件基本信息
        initMainView();
        initBaseInfo();

        //初始化摄像头信息
        initCameraInfo();

        // OpenCameraInfo();

//        SystemBrightManager.stopAutoBrightness(this);
        SharedPreferences share = getSharedPreferences("Acitivity", Context.MODE_PRIVATE);
        int val = share.getInt("value", 200);
        SystemBrightManager.setBrightness(this, val);

        SharedPreferences.Editor editor = share.edit();  //获取编辑器
        editor.putInt("value", val);
        editor.commit();//提交修改

    }
    
    private void initMainView() {

        fileManagerLL =  (LinearLayout) findViewById(R.id.fileManagerLL);
        fileManagerLL.setOnClickListener(this);

        b4142_tv = (DashBoardView) findViewById(R.id.b4142_tv);
        String[] pointer_4142 = new String[] {"0", "2.5","5","7.5","10", "12.5","15","17.5","20", "22.5","25"};
        b4142_tv.setCompleteDegree(0f);
        b4142_tv.setPointerMain(pointer_4142);

        String[] pointer_33 = new String[] {"0","1","2","3", "4","5","6","7", "8","9","10"};
        b34_tv = (DashBoardView) findViewById(R.id.b34_tv);

        b34_tv.setCompleteDegree(0f);
        b34_tv.setPointerMain(pointer_33);

        b32_iv =  (ImageView) findViewById(R.id.b32_iv);
        b32_page_iv =  (ImageView) findViewById(R.id.b32_page_iv);

        b33_tv = (TextView) findViewById(R.id.b33_tv);
        b35_tv = (TextView) findViewById(R.id.b35_tv);
        b36_tv = (TextView) findViewById(R.id.b36_tv);

        b3738_tv = (TextView) findViewById(R.id.b3738_tv);
        b39_tv = (TextView) findViewById(R.id.b39_tv);
        b40_tv = (TextView) findViewById(R.id.b40_tv);
        b4142_tv = (DashBoardView) findViewById(R.id.b4142_tv);

        b4344_tv = (TextView) findViewById(R.id.b4344_tv);
        b4546_tv = (TextView) findViewById(R.id.b4546_tv);
        b4748_tv = (TextView) findViewById(R.id.b4748_tv);
        b4950_tv = (TextView) findViewById(R.id.b4950_tv);

        b51_tv = (TextView) findViewById(R.id.b51_tv);
        b52_tv = (TextView) findViewById(R.id.b52_tv);
        b53_tv = (TextView) findViewById(R.id.b53_tv);
        b54_tv = (TextView) findViewById(R.id.b54_tv);

        b55_tv = (TextView) findViewById(R.id.b55_tv);
        b56_tv = (TextView) findViewById(R.id.b56_tv);
        b57_tv = (TextView) findViewById(R.id.b57_tv);
        b5859_tv = (TextView) findViewById(R.id.b5859_tv);
        b6061_tv = (TextView) findViewById(R.id.b6061_tv);

        b6263_tv = (TextView) findViewById(R.id.b6263_tv);
        b6465_tv = (TextView) findViewById(R.id.b6465_tv);
        b6667_tv = (TextView) findViewById(R.id.b6667_tv);
        b6869_tv = (TextView) findViewById(R.id.b6869_tv);

        b700_tv = (TextView) findViewById(R.id.b700_tv);

    }

    private String stringSub(String pa_str, String son_str) {
        StringBuffer sb = new StringBuffer(pa_str);
        int index = pa_str.indexOf(son_str);
        sb.delete(index, index + son_str.length());
        return sb.toString();
    }

    /**
     * 将byte[]转为各种进制的字符串
     *
     * @param bytes byte[]
     * @return 转换后的字符串
     */
    private String byteToString(byte[] bytes) throws Exception {
        String str = new String(bytes, "GBK");
        return TextUtils.isEmpty(str) ? str : str.trim();
    }

    /**
     * 初始化缓存数据
     */
    private void initBaseInfo() {

        testB98LLayout = (LinearLayout) findViewById(R.id.testB98LLayout);
        mainLLayout = (LinearLayout) findViewById(R.id.mainLLayout);

        b80_1_iv = (ImageView) findViewById(R.id.b80_1_iv);
        b80_0_iv = (ImageView) findViewById(R.id.b80_0_iv);

        b81_iv = (ImageView) findViewById(R.id.b81_iv);
        b82_iv = (ImageView) findViewById(R.id.b82_iv);

        b83_1_iv = (ImageView) findViewById(R.id.b83_1_iv);
        b83_0_iv = (ImageView) findViewById(R.id.b83_0_iv);

        b84_1_iv = (ImageView) findViewById(R.id.b84_1_iv);
        b84_0_iv = (ImageView) findViewById(R.id.b84_0_iv);

        b85_1_iv = (ImageView) findViewById(R.id.b85_1_iv);
        b85_0_iv = (ImageView) findViewById(R.id.b85_0_iv);

        b86_iv = (ImageView) findViewById(R.id.b86_iv);
        b87_iv = (ImageView) findViewById(R.id.b87_iv);


        b90_1_iv = (ImageView) findViewById(R.id.b90_1_iv);
        b90_0_iv = (ImageView) findViewById(R.id.b90_0_iv);
        b91_iv = (ImageView) findViewById(R.id.b91_iv);
        b925_iv = (ImageView) findViewById(R.id.b925_iv);
        b967_1001_iv = (ImageView) findViewById(R.id.b967_1001_iv);


        b102_1_iv = (ImageView) findViewById(R.id.b102_1_iv);
        b102_0_iv = (ImageView) findViewById(R.id.b102_0_iv);

        b103_iv = (ImageView) findViewById(R.id.b103_iv);
        b104_iv = (ImageView) findViewById(R.id.b104_iv);
        b105_iv = (ImageView) findViewById(R.id.b105_iv);
        b106_iv = (ImageView) findViewById(R.id.b106_iv);
        b107_iv = (ImageView) findViewById(R.id.b107_iv);

        b85_1_iv = (ImageView) findViewById(R.id.b85_1_iv);
        b85_0_iv = (ImageView) findViewById(R.id.b85_0_iv);

        b86_iv = (ImageView) findViewById(R.id.b86_iv);
        b87_iv = (ImageView) findViewById(R.id.b87_iv);


        ivLeftFour1111 = (ImageView) findViewById(R.id.ivLeftFour1111); //动臂下降
        ivLeftFour1122 = (ImageView) findViewById(R.id.ivLeftFour1122); //动臂上升
        ivLeftFour1133 = (ImageView) findViewById(R.id.ivLeftFour1133); //铲斗挖掘
        ivLeftFour1144 = (ImageView) findViewById(R.id.ivLeftFour1144); //铲斗卸载

        tvLeftFour1111 = (TextView) findViewById(R.id.tvLeftFour1111);  //斗杆卸载
        tvLeftFour1122 = (TextView) findViewById(R.id.tvLeftFour1122);  //斗杆挖掘
        tvLeftFour1133 = (TextView) findViewById(R.id.tvLeftFour1133);  //左回旋
        tvLeftFour1144 = (TextView) findViewById(R.id.tvLeftFour1144);  //右回旋

        ivRightFour1111 = (ImageView) findViewById(R.id.ivRightFour1111);
        ivRightFour1122 = (ImageView) findViewById(R.id.ivRightFour1122);
        ivRightFour1133 = (ImageView) findViewById(R.id.ivRightFour1133);
        ivRightFour1144 = (ImageView) findViewById(R.id.ivRightFour1144);

        tvRightFour1111 = (TextView) findViewById(R.id.tvRightFour1111);
        tvRightFour1122 = (TextView) findViewById(R.id.tvRightFour1122);
        tvRightFour1133 = (TextView) findViewById(R.id.tvRightFour1133);
        tvRightFour1144 = (TextView) findViewById(R.id.tvRightFour1144);

        b1920_down_iv = (ImageView) findViewById(R.id.b1920_down_iv);
        b1920_down_iv.setOnClickListener(this);
        b1920_up_iv = (ImageView) findViewById(R.id.b1920_up_iv);
        b1920_tv = (TextView) findViewById(R.id.b1920_tv);

        b2122_down_iv = (ImageView) findViewById(R.id.b2122_down_iv);
        b2122_down_iv.setOnClickListener(this);
        b2122_up_iv = (ImageView) findViewById(R.id.b2122_up_iv);
        b2122_tv = (TextView) findViewById(R.id.b2122_tv);

        b2324_down_iv = (ImageView) findViewById(R.id.b2324_down_iv);
        b2324_up_iv = (ImageView) findViewById(R.id.b2324_up_iv);
        b2324_tv = (TextView) findViewById(R.id.b2324_tv);

        b25_01_iv = (ImageView) findViewById(R.id.b25_01_iv);
        b25_00_iv = (ImageView) findViewById(R.id.b25_00_iv);
        b25_11_iv = (ImageView) findViewById(R.id.b25_11_iv);
        b25_10_iv = (ImageView) findViewById(R.id.b25_10_iv);

        //臂架旋转
        tvLeftXz = (TextView) findViewById(R.id.tvLeftXz);

        //备用旋转
        tvRightXz = (TextView) findViewById(R.id.tvRightXz);


        noSignalTest = (LinearLayout) findViewById(R.id.noSignalTest);
        haveSignalTest = (LinearLayout) findViewById(R.id.haveSignalTest);

        lowBatteryTest = (LinearLayout) findViewById(R.id.lowBatteryLayoutTest);
        haveBatteryTest = (LinearLayout) findViewById(R.id.haveBatteryLayoutTest);

        tvBatteryTest1 = (TextView) findViewById(R.id.tvBatteryTest1);
        tvBatteryTest2 = (TextView) findViewById(R.id.tvBatteryTest2);
        tvBatteryTest3 = (TextView) findViewById(R.id.tvBatteryTest3);
        tvBatteryTest4 = (TextView) findViewById(R.id.tvBatteryTest4);

        tvStartStaTest = (TextView) findViewById(R.id.tvStartStaTest);
        signalStrengthTest = (TextView) findViewById(R.id.signalStrengthTest);

        signalStrengthLayoutTest = (FrameLayout) findViewById(R.id.signalStrengthLayoutTest);
        signalStrengthLayout1Test = (FrameLayout) findViewById(R.id.signalStrengthLayout1Test);

        signalStrengthLayout = (FrameLayout) findViewById(R.id.signalStrengthLayout);
        signalStrengthLayout1 = (FrameLayout) findViewById(R.id.signalStrengthLayout1);
        
        signalStrength = (TextView) findViewById(R.id.signalStrength);
        RemoteCtrlStatus = (TextView) findViewById(R.id.RemoteCtrlStatus);
        RemoteCtrlStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLLayout.setVisibility(View.GONE);
                testB98LLayout.setVisibility(View.VISIBLE);
            }
        });

        signalStrengthLayout.setOnClickListener(this);
        signalStrengthLayout1.setOnClickListener(this);
//        waterLLayout.setOnClickListener(this);

        tvLeftXz.setOnClickListener(this);
        tvRightXz.setOnClickListener(this);

        noSignal = (LinearLayout) findViewById(R.id.tvSignal2);
        haveSignal = (LinearLayout) findViewById(R.id.tvSignal1);

        lowBattery = (LinearLayout) findViewById(R.id.lowBatteryLayout);
        haveBattery = (LinearLayout) findViewById(R.id.haveBatteryLayout);
        Battery1 = (TextView) findViewById(R.id.tvBattery1);
        Battery2 = (TextView) findViewById(R.id.tvBattery2);
        Battery3 = (TextView) findViewById(R.id.tvBattery3);
        Battery4 = (TextView) findViewById(R.id.tvBattery4);

    }

    /**
     * 初始化地图信息
     *
     * @param
     */
    private void initCameraInfo() {
        //摄像头控件
    }

    /**
     * 初始化串口
     *
     * @param
     */
    private void initSerial() {

        try {
            serialhelp = new SerialHelper(mHandler, getApplicationContext());
            serialhelp.open();
        } catch (Exception e) {
            LogUtil.d("initSerial", "============打开串口异常==============：" + e.getMessage());

        }

    }


    private void changePage(int flag) {
        int val;
        Log.i(TAG,"click changePage flag=" + flag);
        // CacheData.setMsg_info("=======changePage====flag====flag========="+flag,1);
        if ((flag & 0x1) == 0x1) {
            if (change_page_old == 0) {
//                SharedPreferences share = getSharedPreferences("Acitivity", Context.MODE_WORLD_READABLE);
//                int page = share.getInt("page", 0);
                if (mainLLayout.getVisibility() == View.VISIBLE) {  //0表示内容显示界面， 1：表示测试诊断界面
                    // page = 1;
                    mainLLayout.setVisibility(View.GONE);
                    testB98LLayout.setVisibility(View.VISIBLE);
                } else {
                    // page = 0;
                    mainLLayout.setVisibility(View.VISIBLE);
                    testB98LLayout.setVisibility(View.GONE);
                }

                b32_page_iv.setImageResource(R.drawable.ic_round_red);
//                SharedPreferences.Editor editor = share.edit();//获取编辑器
//                editor.putInt("page", page);
//                editor.commit();
            }
            change_page_old = 1;
            b32_page_iv.setImageResource(R.drawable.ic_round_white);
        } else {
            change_page_old = 0;
            b32_page_iv.setImageResource(R.drawable.ic_round_white);
        }

    }


    /**
     * 布局中所有点击事件处理
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        try {
            switch (id) {

                //左边圆圈
                case R.id.b2122_down_iv: {
                    //弹出调试信息显示框
                    if (messageShowFragment == null) {
                        messageShowFragment = new MessageShowFragment();
                    }

                    if (!messageShowFragment.isAdded() && !messageShowFragment.isVisible() && !messageShowFragment.isRemoving()) {
                        messageShowFragment.show(getSupportFragmentManager(), "messageLogId");
                    }
                    break;
                }
                //右边圆圈
                case R.id.b1920_down_iv: {
                    //往linux发送视频摄像头打开指令
                    dealPasswordValidate(IConstant.SOURCE_SETTING);
                    break;
                }


                case R.id.fileManagerLL:{
                    dealPasswordValidate(IConstant.SOURCE_SETTING);
                }
                case R.id.signalStrengthLayout1: {

                    //弹出调试信息显示框
                    if (messageShowFragment == null) {
                        messageShowFragment = new MessageShowFragment();
                    }

                    if (!messageShowFragment.isAdded() && !messageShowFragment.isVisible() && !messageShowFragment.isRemoving()) {
                        messageShowFragment.show(getSupportFragmentManager(), "messageLogId");
                    }
                    //进入文件系统目录
//                    dealPasswordValidate(IConstant.SOURCE_SETTING);

                    break;

                }


            }

        } catch (Exception e) {

        }
    }



    private void  brightNess(int flag){
        int val;
       // CacheData.setMsg_info("=================brightNess==========tvBright=======flag======="+"" + flag,1);
       // CacheData.setMsg_info("=================brightNess==========true=======flag======="+"" + ((flag & 0x4) == 0x1),1);
        if(flag == 1) {
          //  CacheData.setMsg_info("=================brightNess==========change_bright_old == 0==========="+"" + (change_bright_old == 0),1);
            if(change_bright_old == 0){
               // CacheData.setMsg_info("=================brightNess========1111====="+"" + change_bright_old,1);
                SharedPreferences share = getSharedPreferences("aaa", Context.MODE_PRIVATE);
                val = share.getInt("value", 200);
               // CacheData.setMsg_info("=================brightNess========2222====="+"" + (change_bright_old == 0),1);
                if (val >= 250) {
                    val = 2;
                }else if(val >= 200){
                    val = 250;
                }else{
                    val = val + 50;
                }
               // CacheData.setMsg_info("=================brightNess==========3333=============="+"" + val + "%",1);

                SharedPreferences.Editor editor = share.edit();//获取编辑器
                editor.putInt("value", val);
                editor.commit();//提交修改
                SystemBrightManager.setBrightness(this, val);
                b32_iv.setImageResource(R.drawable.ic_round_red);
            }
            change_bright_old = 1;
            b32_iv.setImageResource(R.drawable.ic_round_white);
        }else{
            change_bright_old = 0;
            b32_iv.setImageResource(R.drawable.ic_round_white);
        }



    }


    /**
     * 跳转到密码输入框，验证通过后，做相应的处理
     */
    private void dealPasswordValidate(final int source) {
        System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
        mHints[mHints.length - 1] = SystemClock.uptimeMillis();
        if (SystemClock.uptimeMillis() - mHints[0] <= 500) {

            if (passwordInputFragment == null) {
                passwordInputFragment = new PasswordInputFragment();
            }

            passwordInputFragment.setSourece(source);
            passwordInputFragment.setFunCallback(new IFunCallback() {
                @Override
                public void onSuccess(Object obj) {
                    final int source = (int) obj;
                    String content = "";
                    if (source == IConstant.SOURCE_SETTING) {
                        content = "当前安装包的版本号：" + CacheData.versioCode_current + "当前安装包的version_code：" + CacheData.versionName_current + "，您确定要进设置吗？";
                    } else if (source == IConstant.SOURCE_ES_FILEMANAGER) {
                        content = "当前安装包的版本号：" + CacheData.versioCode_current + "当前安装包的version_code：" + CacheData.versionName_current + "，您确定要进ES文件管理器吗？";
                    } else if (source == IConstant.SOURCE_QUERY_LOG) {
                        if (messageShowFragment == null) {
                            messageShowFragment = new MessageShowFragment();
                        }

                        if (!messageShowFragment.isAdded() && !messageShowFragment.isVisible() && !messageShowFragment.isRemoving()) {
                            messageShowFragment.show(getSupportFragmentManager(), "messageLogId");
                        }
                        return;
                    } else if (source == IConstant.SOURCE_U_UPGRADE) {
                        // if(FileUtil.getAllExternalSdcardPath()){
                        //发送安装广播
                        Intent intent = new Intent();
                        intent.setAction("com.unisound.unicar.install.action");
                        intent.putExtra("apk", "/storage/usb0/app-debug.apk");    //  /storage/usb0/app-debug.apk
                        sendBroadcast(intent);
//                        }else{
//                            Toast.makeText(getApplicationContext(), "没有检测到连接的U盘，连接U盘到检测到大概需要15秒！", Toast.LENGTH_SHORT).show();
//                        }


                    } else if (source == IConstant.SOURCE_CLEAR_RECORD_DEVICE_DATA) {

                        //根据上传的状态显示对应的信息
//                        serialhelp.deleteRecordDate();
//                        Toast.makeText(getApplicationContext(), "删除记录仪数据信息",
//                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage(content);
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //MainActivity.this.finish();
                            //System.exit(0);

                            try {
                                Intent intent = new Intent();
                                if (source == IConstant.SOURCE_SETTING) {
                                    intent.setClassName("com.android.settings", "com.android.settings.Settings");
                                    startActivity(intent);
//                                     MainActivity.this.finish();
//                                     System.exit(0);
                                } else if (source == IConstant.SOURCE_ES_FILEMANAGER) {
                                    intent.setClassName("com.estrongs.android.pop",
                                            "com.estrongs.android.pop.view.FileExplorerActivity");
                                    startActivity(intent);

                                } else if (source == IConstant.SOURCE_U_UPGRADE) {
                                    //  String path = FileUtil.getExtSDCard();
//                                    String path = "/storage/usb";   //  /mnt/sdcard  /mnt/usb0
//                                    if(TextUtils.isEmpty(path)){
//                                        Toast.makeText(getApplicationContext(), "没有识别到U盘，请稍等一会再试！",
//                                                Toast.LENGTH_LONG).show();
//                                    }else{
//                                        //发送安装广播
//                                        Toast.makeText(getApplicationContext(), "U盘路径为" + path + IConstant.U_UPGRADE_PATH,
//                                                Toast.LENGTH_LONG).show();
//                                        intent.setAction("com.unisound.unicar.install.action");
//                                        intent.putExtra("apk",path + IConstant.U_UPGRADE_PATH);
//                                        sendBroadcast(intent);
//                                    }

                                    intent.setClassName("com.estrongs.android.pop",
                                            "com.estrongs.android.pop.view.FileExplorerActivity");
                                    startActivity(intent);
                                }

                            } catch (Exception e) {
                                LogUtil.e(TAG, "=================dealPasswordValidate=====================>" + e.getMessage());

                            }

                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            });

            if (!passwordInputFragment.isAdded() && !passwordInputFragment.isVisible()
                    && !passwordInputFragment.isRemoving()) {
                passwordInputFragment.show(getSupportFragmentManager(), "");
            }

        }
    }


    class MyHandler extends Handler {
        private WeakReference<MainActivity> ref;

        public MyHandler(MainActivity ac) {
            ref = new WeakReference<>(ac);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (ref.get() == null || ref.get().isFinishing()) {
                return;
            }

            try {

                switch (msg.what) {

                    //综合信息上传
                    case IConstant.COMMAND_MULTIPLE_POSTION_INFO: {
                        MultipleStateInfo multipleStateInfo = (MultipleStateInfo) msg.obj;
                        byte[] content = msg.getData().getByteArray("content");
                        //根据上传的状态显示对应的信息
                       // displayMultipleState(content);
                        dealOtherData(content);    //处理诊断测试数据
                    }
                    break;

                    //显示屏下发摄像头指定显示指令linux成功应答
                    case IConstant.COMMAND_DOWN_CAMERA: {
                        //=============

                    }
                    break;

                }
            } catch (Exception e) {
                // LogUtil.e(TAG,"===================handleMessage===========================>"+e.getMessage());
                e.printStackTrace();
            }

        }

    }


    /**
     * 将byte[]转为各种进制的字符串
     * @param bytes byte[]
     * @param radix 基数可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    private String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }


    /**
     * 将byte[]转为int形
     *
     * @param bytes byte[]
     * @return 转换后的字符串
     */
    private int bytes2int(byte[] bytes) {
        int val = 0;
        val =  (new BigInteger(1, bytes)).intValue();
        return val;// 这里的1代表正数
    }


    /**
     * 处理其它数据
     *
     * @param content
     */
    private void dealOtherData(byte[] content) {

        // CacheData.setMsg_info("============dealOtherData=========content===================>" + NumberBytes.byteArrayToHexStr(content), IConstant.MESSAGE_INFO_ALL);
        //B40对应181_b0 遥控启动
        //181数据解析
        if (content.length > 0) {
            temp = content[0];
            tmp_0 = (temp >> 0) & 0x1;
            tmp_1 = (temp >> 1) & 0x1;
            tmp_2 = (temp >> 2) & 0x1;
            tmp_3 = (temp >> 3) & 0x1;
            tmp_4 = (temp >> 4) & 0x1;
            tmp_5 = (temp >> 5) & 0x1;
            tmp_6 = (temp >> 6) & 0x1;
            tmp_7 = (temp >> 7) & 0x1;

            //遥控启动
            if (tmp_0 == 1) {
                b80_1_iv.setImageResource(R.drawable.ic_22_up_red);
                b80_0_iv.setImageResource(R.drawable.ic_22_do_wh);

            } else {
                b80_1_iv.setImageResource(R.drawable.ic_22_up_wh);
                b80_0_iv.setImageResource(R.drawable.ic_22_do_red);

            }

            if (tmp_1 == 1) {
                b81_iv.setImageResource(R.drawable.shape_ring_bg_press);
            } else {
                b81_iv.setImageResource(R.drawable.shape_ring_bg_normal);
            }

            if (tmp_2 == 1) {
                b82_iv.setImageResource(R.drawable.shape_ring_bg_press);
            } else {
                b82_iv.setImageResource(R.drawable.shape_ring_bg_normal);
            }

            if (tmp_3 == 1) {
                b83_1_iv.setImageResource(R.drawable.ic_22_up_red);
                b83_0_iv.setImageResource(R.drawable.ic_22_do_wh);

            } else {
                b83_1_iv.setImageResource(R.drawable.ic_22_up_wh);
                b83_0_iv.setImageResource(R.drawable.ic_22_do_red);

            }

            if (tmp_4 == 1) {
                b84_1_iv.setImageResource(R.drawable.ic_22_up_red);
                b84_0_iv.setImageResource(R.drawable.ic_22_do_wh);

            } else {
                b84_1_iv.setImageResource(R.drawable.ic_22_up_wh);
                b84_0_iv.setImageResource(R.drawable.ic_22_do_red);
            }

            if (tmp_5 == 1) {
                b85_1_iv.setImageResource(R.drawable.ic_22_up_red);
                b85_0_iv.setImageResource(R.drawable.ic_22_do_wh);
            } else {
                b85_1_iv.setImageResource(R.drawable.ic_22_up_wh);
                b85_0_iv.setImageResource(R.drawable.ic_22_do_red);
            }

            if (tmp_6 == 1) {
                b86_iv.setImageResource(R.drawable.shape_ring_bg_press);
            } else {
                b86_iv.setImageResource(R.drawable.shape_ring_bg_normal);
            }

            if (tmp_7 == 1) {
                b87_iv.setImageResource(R.drawable.shape_ring_bg_press);
            } else {
                b87_iv.setImageResource(R.drawable.shape_ring_bg_normal);
            }

        }

        if (content.length > 1) {
            temp = content[1];
            tmp_0 = (temp >> 0) & 0x1;
            tmp_1 = (temp >> 1) & 0x1;
            tmp_2 = (temp >> 2) & 0x1;
            tmp_3 = (temp >> 3) & 0x1;
            tmp_4 = (temp >> 4) & 0x1;
            tmp_5 = (temp >> 5) & 0x1;
            tmp_6 = (temp >> 6) & 0x1;
            tmp_7 = (temp >> 7) & 0x1;

            //B9
            if (tmp_0 == 1) {
                b90_1_iv.setImageResource(R.drawable.ic_22_up_red);
                b90_0_iv.setImageResource(R.drawable.ic_22_do_wh);
            } else {
                b90_1_iv.setImageResource(R.drawable.ic_22_up_wh);
                b90_0_iv.setImageResource(R.drawable.ic_22_do_red);

            }

            if (tmp_1 == 1) {
                b91_iv.setImageResource(R.drawable.ic_round_red);
            } else {
                b91_iv.setImageResource(R.drawable.ic_round_white);
            }

          //  CacheData.setMsg_info("============dealOtherData========content[2]==================>" + content[2], IConstant.MESSAGE_INFO_ALL);
            tmp_2 = (int) (((temp >> 5) & 0x1) * 8) + (int) (((temp >> 4) & 0x1) * 4) + (int) (((temp >> 3) & 0x1) * 2) + (int) ((temp >> 2) & 0x1);
            tmp_2 = tmp_2 + 1;
          //  CacheData.setMsg_info("============dealOtherData========content[2]=====tmp_2=============>" + tmp_2, IConstant.MESSAGE_INFO_ALL);
            switch (tmp_2) {
                case 1: {
                    b925_iv.setImageResource(R.drawable.ic_925_1);
                    break;
                }
                case 2: {
                    b925_iv.setImageResource(R.drawable.ic_925_2);
                    break;
                }

                case 3: {
                    b925_iv.setImageResource(R.drawable.ic_925_3);
                    break;
                }

                case 4: {
                    b925_iv.setImageResource(R.drawable.ic_925_4);
                    break;
                }

                case 5: {
                    b925_iv.setImageResource(R.drawable.ic_925_5);
                    break;
                }

                case 6: {
                    b925_iv.setImageResource(R.drawable.ic_925_6);
                    break;
                }

                case 7: {
                    b925_iv.setImageResource(R.drawable.ic_925_7);
                    break;
                }

                case 8: {
                    b925_iv.setImageResource(R.drawable.ic_925_8);
                    break;
                }

                case 9: {
                    b925_iv.setImageResource(R.drawable.ic_925_9);
                    break;
                }

                case 10: {
                    b925_iv.setImageResource(R.drawable.ic_925_10);
                    break;
                }

                case 11: {
                    b925_iv.setImageResource(R.drawable.ic_925_11);
                    break;
                }

                default: {
                    b925_iv.setImageResource(R.drawable.ic_925_1);
                    break;
                }

            }

         //   CacheData.setMsg_info("============dealOtherData========content[2]=====tmp_6=============>" + tmp_6, IConstant.MESSAGE_INFO_ALL);
            // 熄火
            if (tmp_6 == 1) {
                b967_1001_iv.setImageResource(R.drawable.ic_967_xh);
            }

          //  CacheData.setMsg_info("============dealOtherData========content[2]=====tmp_7=============>" + tmp_7, IConstant.MESSAGE_INFO_ALL);
            // 上电
            if (tmp_7 == 1) {
                b967_1001_iv.setImageResource(R.drawable.ic_967_sd);
            }

            temp = content[2];
            tmp_0 = (temp >> 0) & 0x1;
            tmp_1 = (temp >> 1) & 0x1;

            // 启动
            if (tmp_0 == 1) {
                b967_1001_iv.setImageResource(R.drawable.ic_967_qd);
            }

            // 急停
//            if (tmp_1 == 1) {
//                b967_1001_iv.setImageResource(R.drawable.ic_967_fdj);
//            }

            if(tmp_0 == 0 && tmp_1 == 0 && tmp_6 == 0 && tmp_7 == 0){
                b967_1001_iv.setImageResource(R.drawable.ic_967_xh);
            }

            tmp_2 = (temp >> 2) & 0x1;
            tmp_3 = (temp >> 3) & 0x1;
            tmp_4 = (temp >> 4) & 0x1;
            tmp_5 = (temp >> 5) & 0x1;
            tmp_6 = (temp >> 6) & 0x1;
            tmp_7 = (temp >> 7) & 0x1;

            //自动控制
            if (tmp_2 == 1) {
                b102_1_iv.setImageResource(R.drawable.ic_22_up_red);
                b102_0_iv.setImageResource(R.drawable.ic_22_do_wh);
            } else {
                b102_1_iv.setImageResource(R.drawable.ic_22_up_wh);
                b102_0_iv.setImageResource(R.drawable.ic_22_do_red);
            }

            // 灯光
            if (tmp_3 == 1) {
                b103_iv.setImageResource(R.drawable.ic_round_red);
            } else {
                b103_iv.setImageResource(R.drawable.ic_round_white);
            }

            // 备用一
            if (tmp_4 == 1) {
                b104_iv.setImageResource(R.drawable.ic_round_red);
            } else {
                b104_iv.setImageResource(R.drawable.ic_round_white);
            }

            // 备用二
            if (tmp_5 == 1) {
                b105_iv.setImageResource(R.drawable.ic_round_red);
            } else {
                b105_iv.setImageResource(R.drawable.ic_round_white);
            }

            // 备用三
            if (tmp_6 == 1) {
                b106_iv.setImageResource(R.drawable.ic_round_red);
            } else {
                b106_iv.setImageResource(R.drawable.ic_round_white);
            }

            // 遥控启动
            if (tmp_7 == 1) {
                b107_iv.setImageResource(R.drawable.ic_round_red);
            } else {
                b107_iv.setImageResource(R.drawable.ic_round_white);
            }

            //动臂上升下降
            System.arraycopy(content, 3, temp_2, 0, 2);
            int temp_1112 = bytes2int(temp_2);
            if (temp_1112 < 8190 && temp_1112 > 0) {   //  1为上，0为下  图片的设置
                ivRightFour1111.setImageResource(R.drawable.ic_up_select);
                tvRightFour1111.setText("" + temp_1112);
                ivRightFour1122.setImageResource(R.drawable.ic_down_default);
                tvRightFour1122.setText("");

            } else if (temp_1112 > 8190) {
                ivRightFour1111.setImageResource(R.drawable.ic_up_default);
                tvRightFour1111.setText("");
                ivRightFour1122.setImageResource(R.drawable.ic_down_select);
                tvRightFour1122.setText("" + temp_1112);
            } else {
                ivRightFour1111.setImageResource(R.drawable.ic_up_default);
                tvRightFour1111.setText("" );
                ivRightFour1122.setImageResource(R.drawable.ic_down_default);
                tvRightFour1122.setText("");
            }

            //斗杆卸载
            System.arraycopy(content, 5, temp_2, 0, 2);
            int temp_1314 = bytes2int(temp_2);
            if (temp_1314 < 8190 && temp_1314 > 0) {   //  1为上，0为下  图片的设置
                ivLeftFour1111.setImageResource(R.drawable.ic_up_select);
                tvLeftFour1111.setText("" + temp_1314);
                ivLeftFour1122.setImageResource(R.drawable.ic_down_default);
                tvLeftFour1122.setText("");

            } else if (temp_1314 > 8190) {
                ivLeftFour1111.setImageResource(R.drawable.ic_up_default);
                tvLeftFour1111.setText("");
                ivLeftFour1122.setImageResource(R.drawable.ic_down_select);
                tvLeftFour1122.setText("" + temp_1314);
            } else {
                ivLeftFour1111.setImageResource(R.drawable.ic_up_default);
                tvLeftFour1111.setText("" );
                ivLeftFour1122.setImageResource(R.drawable.ic_down_default);
                tvLeftFour1122.setText("");
            }


            //铲斗挖掘
            System.arraycopy(content, 7, temp_2, 0, 2);
            int temp_1516 = bytes2int(temp_2);
            if (temp_1516 < 8190 && temp_1314 > 0) {   //  1为上，0为下  图片的设置
                ivRightFour1133.setImageResource(R.drawable.ic_left_select);
                tvRightFour1133.setText("" + temp_1516);
                ivRightFour1144.setImageResource(R.drawable.ic_right_default);
                tvRightFour1144.setText("");

            } else if (temp_1516 > 8190) {
                ivRightFour1133.setImageResource(R.drawable.ic_left_default);
                tvRightFour1133.setText("");
                ivRightFour1144.setImageResource(R.drawable.ic_right_select);
                tvRightFour1144.setText("" + temp_1516);
            } else {
                ivRightFour1133.setImageResource(R.drawable.ic_left_default);
                tvRightFour1133.setText("" );
                ivRightFour1144.setImageResource(R.drawable.ic_right_default);
                tvRightFour1144.setText("");
            }

            //左右回旋转
            System.arraycopy(content, 9, temp_2, 0, 2);
            int temp_1718 = bytes2int(temp_2);
            if (temp_1718 < 8190 && temp_1718 > 0) {   //  1为上，0为下  图片的设置
                ivLeftFour1133.setImageResource(R.drawable.ic_left_select);
                tvLeftFour1133.setText("" + temp_1718);
                ivLeftFour1144.setImageResource(R.drawable.ic_right_default);
                tvLeftFour1144.setText("");

            } else if (temp_1718 > 8190) {
                ivLeftFour1133.setImageResource(R.drawable.ic_left_default);
                tvLeftFour1133.setText("");
                ivLeftFour1144.setImageResource(R.drawable.ic_right_select);
                tvLeftFour1144.setText("" + temp_1718);
            } else {
                ivLeftFour1133.setImageResource(R.drawable.ic_left_default);
                tvLeftFour1111.setText("");
                ivLeftFour1144.setImageResource(R.drawable.ic_right_default);
                tvLeftFour1144.setText("");
            }


            //左行前进、后退
            System.arraycopy(content, 11, temp_2, 0, 2);
            int temp_1920 = bytes2int(temp_2);
            if (temp_1920 < 8190 && temp_1920 > 0) {   //  1为上，0为下  图片的设置
                b1920_down_iv.setImageResource(R.drawable.ic_2_up_red);
                b1920_up_iv.setImageResource(R.drawable.ic_2_do_wh);

            } else if (temp_1920 > 8190) {
                b1920_down_iv.setImageResource(R.drawable.ic_2_up_wh);
                b1920_up_iv.setImageResource(R.drawable.ic_2_do_red);
            } else {
                b1920_down_iv.setImageResource(R.drawable.ic_2_up_wh);
                b1920_up_iv.setImageResource(R.drawable.ic_2_do_wh);
            }
            b1920_tv.setText((temp_1920 == 0 || temp_1920 == 8190 )? "" : "" + temp_1920);

            //右行前进、后退
            System.arraycopy(content, 13, temp_2, 0, 2);
            int temp_2122 = bytes2int(temp_2);
            if (temp_2122 < 8190 && temp_2122 > 0) {   //  1为上，0为下  图片的设置
                b2122_down_iv.setImageResource(R.drawable.ic_2_up_red);
                b2122_up_iv.setImageResource(R.drawable.ic_2_do_wh);
            } else if (temp_2122 > 8190) {
                b2122_down_iv.setImageResource(R.drawable.ic_2_up_wh);
                b2122_up_iv.setImageResource(R.drawable.ic_2_do_red);
            } else {
                b2122_down_iv.setImageResource(R.drawable.ic_2_up_wh);
                b2122_up_iv.setImageResource(R.drawable.ic_2_do_wh);
            }
            b2122_tv.setText((temp_2122 == 0 || temp_2122 == 8190  ) ? "" : "" + temp_2122);

            //液压剪/破碎锤开合
            System.arraycopy(content, 15, temp_2, 0, 2);
            int temp_2324 = bytes2int(temp_2);
            if (temp_2324 < 8190 && temp_2324 > 0) {   //  1为上，0为下  图片的设置
                b2324_down_iv.setImageResource(R.drawable.ic_2_left_red);
                b2324_up_iv.setImageResource(R.drawable.ic_2_right_wh);
            } else if (temp_2324 > 8190) {
                b2324_down_iv.setImageResource(R.drawable.ic_2_left_wh);
                b2324_up_iv.setImageResource(R.drawable.ic_2_right_red);
            } else {
                b2324_down_iv.setImageResource(R.drawable.ic_2_left_wh);
                b2324_up_iv.setImageResource(R.drawable.ic_2_right_wh);
            }
            b2324_tv.setText((temp_2324 == 0 || temp_2324 == 8190) ? "" : "" + temp_2324);


            if (content.length > 24) {

                temp = content[17];
                tmp_0 = (temp >> 0) & 0x1;
                tmp_1 = (temp >> 1) & 0x1;

                // 平地
                if (tmp_0 == 1) {
                    b25_01_iv.setImageResource(R.drawable.ic_22_up_red);
                    b25_00_iv.setImageResource(R.drawable.ic_22_do_wh);
                } else {
                    b25_01_iv.setImageResource(R.drawable.ic_22_up_wh);
                    b25_00_iv.setImageResource(R.drawable.ic_22_do_red);
                }

                // 修坡
                if (tmp_1 == 1) {
                    b25_11_iv.setImageResource(R.drawable.ic_22_up_red);
                    b25_10_iv.setImageResource(R.drawable.ic_22_do_wh);
                } else {
                    b25_11_iv.setImageResource(R.drawable.ic_22_up_wh);
                    b25_10_iv.setImageResource(R.drawable.ic_22_do_red);
                }


                System.arraycopy(content, 19, temp_1, 0, 1);
                String battery_electricity = binary(temp_1, 10);

                if (IConstant.BATTARY_STATUS_0.equals(battery_electricity)) {
                    lowBattery.setVisibility(LinearLayout.VISIBLE);
                    haveBattery.setVisibility(LinearLayout.GONE);

                    lowBatteryTest.setVisibility(LinearLayout.VISIBLE);
                    haveBatteryTest.setVisibility(LinearLayout.GONE);
                } else if (IConstant.BATTARY_STATUS_1.equals(battery_electricity)) {
                    haveBattery.setVisibility(LinearLayout.VISIBLE);
                    lowBattery.setVisibility(LinearLayout.GONE);
                    Battery1.setVisibility(View.VISIBLE);
                    Battery2.setVisibility(View.INVISIBLE);
                    Battery3.setVisibility(View.INVISIBLE);
                    Battery4.setVisibility(View.INVISIBLE);

                    haveBatteryTest.setVisibility(LinearLayout.VISIBLE);
                    lowBatteryTest.setVisibility(LinearLayout.GONE);
                    tvBatteryTest1.setVisibility(View.VISIBLE);
                    tvBatteryTest2.setVisibility(View.INVISIBLE);
                    tvBatteryTest3.setVisibility(View.INVISIBLE);
                    tvBatteryTest4.setVisibility(View.INVISIBLE);
                } else if (IConstant.BATTARY_STATUS_2.equals(battery_electricity)) {
                    haveBattery.setVisibility(LinearLayout.VISIBLE);
                    lowBattery.setVisibility(LinearLayout.GONE);
                    Battery1.setVisibility(View.VISIBLE);
                    Battery2.setVisibility(View.VISIBLE);
                    Battery3.setVisibility(View.INVISIBLE);
                    Battery4.setVisibility(View.INVISIBLE);

                    haveBatteryTest.setVisibility(LinearLayout.VISIBLE);
                    lowBatteryTest.setVisibility(LinearLayout.GONE);
                    tvBatteryTest1.setVisibility(View.VISIBLE);
                    tvBatteryTest2.setVisibility(View.VISIBLE);
                    tvBatteryTest3.setVisibility(View.INVISIBLE);
                    tvBatteryTest4.setVisibility(View.INVISIBLE);
                } else if (IConstant.BATTARY_STATUS_3.equals(battery_electricity)) {
                    haveBattery.setVisibility(LinearLayout.VISIBLE);
                    lowBattery.setVisibility(LinearLayout.GONE);
                    Battery1.setVisibility(View.VISIBLE);
                    Battery2.setVisibility(View.VISIBLE);
                    Battery3.setVisibility(View.VISIBLE);
                    Battery4.setVisibility(View.INVISIBLE);

                    haveBatteryTest.setVisibility(LinearLayout.VISIBLE);
                    lowBatteryTest.setVisibility(LinearLayout.GONE);
                    tvBatteryTest1.setVisibility(View.VISIBLE);
                    tvBatteryTest2.setVisibility(View.VISIBLE);
                    tvBatteryTest3.setVisibility(View.VISIBLE);
                    tvBatteryTest4.setVisibility(View.INVISIBLE);
                } else if (IConstant.BATTARY_STATUS_4.equals(battery_electricity)) {
                    haveBattery.setVisibility(LinearLayout.VISIBLE);
                    lowBattery.setVisibility(LinearLayout.GONE);
                    Battery1.setVisibility(View.VISIBLE);
                    Battery2.setVisibility(View.VISIBLE);
                    Battery3.setVisibility(View.VISIBLE);
                    Battery4.setVisibility(View.VISIBLE);

                    haveBatteryTest.setVisibility(LinearLayout.VISIBLE);
                    lowBatteryTest.setVisibility(LinearLayout.GONE);
                    tvBatteryTest1.setVisibility(View.VISIBLE);
                    tvBatteryTest2.setVisibility(View.VISIBLE);
                    tvBatteryTest3.setVisibility(View.VISIBLE);
                    tvBatteryTest4.setVisibility(View.VISIBLE);
                }


                System.arraycopy(content, 20, temp_1, 0, 1);
                int signal_strength = bytes2int(temp_1);
                System.arraycopy(content, 21, temp_1, 0, 1);
                String wireless_link_status = binary(temp_1, 10); //无线连接状态

                if (signal_strength > 127) {
                    signal_strength = 256 - signal_strength;
                    signalStrength.setText(("-" + signal_strength));
                    signalStrengthTest.setText(("-" + signal_strength));
                } else {
                    signalStrength.setText("" + signal_strength);
                    signalStrengthTest.setText(("" + signal_strength));
                }

                //signalStrength.setText(wireless_link_status + "-" + signal_strength);
                if (IConstant.WIRELESS_LINK_0.equals(wireless_link_status)) {
                    signalStrengthLayout.setVisibility(FrameLayout.VISIBLE);
                    signalStrengthLayout1.setVisibility(FrameLayout.GONE);
                    noSignal.setVisibility(LinearLayout.VISIBLE);
                    haveSignal.setVisibility(LinearLayout.GONE);
                    signalStrength.setVisibility(View.GONE);

                    signalStrengthLayoutTest.setVisibility(FrameLayout.VISIBLE);
                    signalStrengthLayout1Test.setVisibility(FrameLayout.GONE);
                    noSignalTest.setVisibility(LinearLayout.VISIBLE);
                    haveSignalTest.setVisibility(LinearLayout.GONE);
                    signalStrengthTest.setVisibility(View.GONE);

                } else if (IConstant.WIRELESS_LINK_1.equals(wireless_link_status)) {
                    signalStrengthLayout.setVisibility(FrameLayout.VISIBLE);
                    signalStrengthLayout1.setVisibility(FrameLayout.GONE);
                    noSignal.setVisibility(LinearLayout.GONE);
                    haveSignal.setVisibility(LinearLayout.VISIBLE);
                    signalStrength.setVisibility(View.VISIBLE);

                    signalStrengthLayoutTest.setVisibility(FrameLayout.VISIBLE);
                    signalStrengthLayout1Test.setVisibility(FrameLayout.GONE);
                    noSignalTest.setVisibility(LinearLayout.GONE);
                    haveSignalTest.setVisibility(LinearLayout.VISIBLE);
                    signalStrengthTest.setVisibility(View.VISIBLE);
                } else if (IConstant.WIRELESS_LINK_2.equals(wireless_link_status)) {
                    signalStrengthLayout.setVisibility(FrameLayout.GONE);
                    signalStrengthLayout1.setVisibility(FrameLayout.VISIBLE);
                    noSignal.setVisibility(LinearLayout.GONE);
                    haveSignal.setVisibility(LinearLayout.GONE);
                    signalStrength.setVisibility(View.GONE);

                    signalStrengthLayoutTest.setVisibility(FrameLayout.GONE);
                    signalStrengthLayout1Test.setVisibility(FrameLayout.VISIBLE);
                    noSignalTest.setVisibility(LinearLayout.GONE);
                    haveSignalTest.setVisibility(LinearLayout.GONE);
                }

                System.arraycopy(content, 23, temp_1, 0, 1);
                int remote_ctrl_status = Integer.valueOf(binary(temp_1, 10)).intValue();

                if ((remote_ctrl_status & 0x2) == 0x2) {
                    RemoteCtrlStatus.setText(getString(R.string.scram));
                    tvStartStaTest.setText(getString(R.string.scram));
                    //tvStop1111.setText(getString(R.string.scram));
                    //ivStop1111.setImageResource(R.drawable.ic_jt_use);
                } else if ((remote_ctrl_status & 0x1) == 0x1) {
                    RemoteCtrlStatus.setText(getString(R.string.started));
                    tvStartStaTest.setText(getString(R.string.started));
                   // tvStop1111.setText(getString(R.string.shut_stop));
                    //ivStop1111.setImageResource(R.drawable.ic_jt_stop);
                } else if ((remote_ctrl_status & 0x1) == 0x0) {
                    RemoteCtrlStatus.setText(getString(R.string.not_started));
                    tvStartStaTest.setText(getString(R.string.not_started));
                   // tvStop1111.setText(getString(R.string.shut_stop));
                   // ivStop1111.setImageResource(R.drawable.ic_jt_stop);
                }

                System.arraycopy(content, 24, temp_1, 0, 1);
                try {
                    int brightness = Integer.valueOf(binary(temp_1, 10)).intValue();
                    temp = content[24];
                    tmp_0 = (temp >> 0) & 0x1;
                    tmp_1 = (temp >> 1) & 0x1;
                    tmp_2 = (temp >> 2) & 0x1;
                    tmp_3 = (temp >> 3) & 0x1;
                    tmp_4 = (temp >> 4) & 0x1;
                    tmp_5 = (temp >> 5) & 0x1;
                    tmp_6 = (temp >> 6) & 0x1;
                    tmp_7 = (temp >> 7) & 0x1;

                    brightNess(tmp_2);
                    changePage(brightness);
                }catch (Exception e) {
                    e.printStackTrace();
                }


            }

            if (content.length > 62) {
                // 油门挡位
                System.arraycopy(content, 25, temp_1, 0, 1);
                int b33 = bytes2int(temp_1);
                b33_tv.setText("" + (b33>11?11:b33));

                // 燃油油位
                System.arraycopy(content, 26, temp_1, 0, 1);
                int b34 = bytes2int(temp_1);
                b34_tv.setCompleteDegree(b34>100?100:b34);  //8888

                //水温
                System.arraycopy(content, 27, temp_1, 0, 1);
                int b35 = bytes2int(temp_1);
                if(b35 >40 && b35 <215){
                    b35 = 40;
                }else if(b35>215 && b35 < 256){
                    b35 = b35 - 256;
                }else{
                    b35 = 0;
                }
                b35_tv.setText("" +  b35 + "\u00B0");

                //液压油温
                System.arraycopy(content, 28, temp_1, 0, 1);
                int b36 = bytes2int(temp_1);
                b36_tv.setText("" + (b36>150?150:b36) + "\u00B0");

                // 机油压力
                System.arraycopy(content, 29, temp_2, 0, 2);
                int b3738 = bytes2int(temp_2);
                b3738_tv.setText("" + (b3738>1000?1000:b3738) + "KPa");

                // 功率模式
                System.arraycopy(content, 31, temp_1, 0, 1);
                int b39 = bytes2int(temp_1);
                if(b39==0) {
                    b39_tv.setText("NG");
                }else if(b39==1) {
                    b39_tv.setText("F");
                }else if(b39==2) {
                    b39_tv.setText("B");
                }else if(b39==3) {
                    b39_tv.setText("G");
                }

                // 作业模式
                System.arraycopy(content, 32, temp_1, 0, 1);
                int b40 = bytes2int(temp_1);
                if(b40==0) {
                    b40_tv.setText("动臂模式");
                }else if(b39==1) {
                    b40_tv.setText("回转优先");
                }else if(b39==2) {
                    b40_tv.setText("微操作模式");
                }else if(b39==3) {
                    b40_tv.setText("平地模式");
                }else if(b39==4) {
                    b40_tv.setText("修坡模式");
                }


                // 发动机转速
                System.arraycopy(content, 33, temp_2, 0, 2);
                int b4142 = bytes2int(temp_2);
                b4142 = b4142>2500?2500:b4142;
                b4142_tv.setCompleteDegree((b4142/2500f)*100); //

                // 泵1压力
                System.arraycopy(content, 35, temp_2, 0, 2);
                int b4344 = bytes2int(temp_2);
                b4344_tv.setText("" + (b4344>500?500:b4344) + "bar");

                // 泵2压力
                System.arraycopy(content, 37, temp_2, 0, 2);
                int b4546 = bytes2int(temp_2);
                b4546_tv.setText("" + (b4546>500?500:b4546) + "bar");

                // 泵1电流
                System.arraycopy(content, 39, temp_2, 0, 2);
                int b4748 = bytes2int(temp_2);
                b4748_tv.setText("" + (b4748>1000?1000:b4748) + "mA");

                // 泵2电流
                System.arraycopy(content, 41, temp_2, 0, 2);
                int b4950 = bytes2int(temp_2);
                b4950_tv.setText("" + (b4950>1000?1000:b4950)  + "mA");

                // 先导压力
                System.arraycopy(content, 43, temp_1, 0, 1);
                int b51 = bytes2int(temp_1);
                b51_tv.setText("" + (b51>50?50:b51) + "bar");

                //动臂操作
                System.arraycopy(content, 44, temp_1, 0, 1);
                int b52 = bytes2int(temp_1);
                b52 = dataTranInfo(b52);
                b52_tv.setText("" + b52 + "\u00B0");

                // 斗杆操作
                System.arraycopy(content, 45, temp_1, 0, 1);
                int b53 = bytes2int(temp_1);
                b53 = dataTranInfo(b53);
                b53_tv.setText("" + b53 + "\u00B0");

                // 铲斗操作
                System.arraycopy(content, 46, temp_1, 0, 1);
                int b54 = bytes2int(temp_1);
                b54 = dataTranInfo(b54);
                b54_tv.setText("" + b54 + "\u00B0");

                // 回转操作
                System.arraycopy(content, 47, temp_1, 0, 1);
                int b55 = bytes2int(temp_1);
                b55 = dataTranInfo(b55);
                b55_tv.setText("" + b55 + "\u00B0");

                // 左行走
                System.arraycopy(content, 48, temp_1, 0, 1);
                int b56 = bytes2int(temp_1);
                b56 = dataTranInfo(b56);
                b56_tv.setText("" + b56 + "\u00B0");

                // 右行走
                System.arraycopy(content, 49, temp_1, 0, 1);
                int b57 = bytes2int(temp_1);
                b57 = dataTranInfo(b57);
                b57_tv.setText("" + b57 + "\u00B0");

                // 动臂角度
                System.arraycopy(content, 50, temp_2, 0, 2);
                int b5859 = bytes2int(temp_2);
                b5859_tv.setText("" + (b5859>360?360:b5859) + "\u00B0");

                // 斗杆角度
                System.arraycopy(content, 52, temp_2, 0, 2);
                int b6061 = bytes2int(temp_2);
               // CacheData.setMsg_info("=================b6061==========b6061=============="+"" + b6061,1);
                b6061_tv.setText("" + (b6061>360?360:b6061) + "\u00B0");

                // 铲斗角度
                System.arraycopy(content, 54, temp_2, 0, 2);
                int b6263 = bytes2int(temp_2);
                b6263_tv.setText("" + (b6263>360?360:b6263) + "\u00B0");

                // 回转角度
                System.arraycopy(content, 56, temp_2, 0, 2);
                int b6465 = bytes2int(temp_2);
                b6465_tv.setText("" + (b6465>360?360:b6465) + "\u00B0");

                // 前后倾角
                System.arraycopy(content, 58, temp_2, 0, 2);
                int b6667 = bytes2int(temp_2);
                b6667_tv.setText("" + (b6667>360?360:b6667) + "\u00B0");

                // 左右倾角
                System.arraycopy(content, 60, temp_2, 0, 2);
                int b6869 = bytes2int(temp_2);
                b6869_tv.setText("" + (b6869>360?360:b6869) + "\u00B0");

                temp = content[62];
                tmp_0 = (temp >> 0) & 0x1;
                tmp_1 = (temp >> 1) & 0x1;
                tmp_2 = (temp >> 2) & 0x1;
                tmp_3 = (temp >> 3) & 0x1;
                tmp_4 = (temp >> 4) & 0x1;
                tmp_5 = (temp >> 5) & 0x1;
                tmp_6 = (temp >> 6) & 0x1;
                tmp_7 = (temp >> 7) & 0x1;

                String question = "故障信息：";
                question = question + (tmp_0 == 1? "\u3000\u3000蓄电池供电":"");
                question = question + (tmp_1 == 1? "\u3000\u3000机油压力低":"");
                question = question + (tmp_2 == 1? "\u3000\u3000水温高":"");
                question = question + (tmp_3 == 1? "\u3000\u3000空滤阻塞":"");
                question = question + (tmp_4 == 1? "\u3000\u3000油水分离器水位高":"");
//                question = question + (tmp_5 == 1? "\u3000\u3000":"");
//                question = question + (tmp_6 == 1? "\u3000\u3000":"");
//                question = question + (tmp_7 == 1? "\u3000\u3000":"");

                question_old = (question_old == null) ? "":question_old;
                if(!question_old.equals(question)){
                    b700_tv.setText(question);
                    b700_tv.setSelected(true);
                }else if(question.equals("故障信息：")){
                    question = question + "无";
                }

                question_old = question;


                // 启动
//                if (tmp_0 == 1) {
//                    b967_1001_iv.setImageResource(R.drawable.ic_round_red);
//                } else {
//                    b967_1001_iv.setImageResource(R.drawable.ic_round_white);
//                }

            }

        }

    }


    private int dataTranInfo(int num){
        if(num >= 20 && num <235){
            num = 20;
        }else if(num>235 && num < 256){
            num = num - 256;
        }else{
            num = 0;
        }
        return num;
    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }






    private TimerTask getTimerTask_loc() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (CacheData.getThread_flag() == 12) {
                        serialhelp.close();
                        initSerial();
                       // CacheData.setMsg_info("============================getTimerTask_loc=====================initSerial==", 0);
                    } else {
                        CacheData.setThread_flag(CacheData.getThread_flag() + 1);
                    }

                } catch (Exception e) {
                    LogUtil.e(TAG, "===================TimerTask===========================>" + e.getMessage());
                    e.printStackTrace();
                }

            }
        };

        return task;

    }


 }


