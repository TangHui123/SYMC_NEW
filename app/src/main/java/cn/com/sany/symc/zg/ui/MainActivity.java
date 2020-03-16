package cn.com.sany.symc.zg.ui;

import android.Manifest;
import android.app.Activity;
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
 *
 * 首页应用
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,CameraInterface.CamOpenOverCallback  {

    public static final String TAG = "MainActivity.class";

    private byte temp;
    private byte [] temp_1 = new byte[1];
    private byte [] temp_3 = new byte[3];
    private byte [] temp_2 = new byte[2];

    private int tmp_0;
    private int tmp_1;
    private int tmp_2;
    private int tmp_3;
    private int tmp_4;
    private int tmp_5;
    private int tmp_6;
    private int tmp_7;
    private String str_temp;
    private int res_id;
    private TextView TurretAngle;
    //    private TextView EngineSpeedLow;
//    private TextView EngineSpeedHigh;
    private TextView EngineSpeed;
    private TextView HydraulicPress;
    private TextView PumpVentPress;
    private TextView ErrCode;

    //    private TextView ArmAngle1L;
//    private TextView ArmAngle1H;
    private TextView ArmAngle1;
    //    private TextView ArmAngle2L;
//    private TextView ArmAngle2H;
    private TextView ArmAngle2;
    //    private TextView ArmAngle3L;
//    private TextView ArmAngle3H;
    private TextView ArmAngle3;
    //    private TextView ArmAngle4L;
//    private TextView ArmAngle4H;
    private TextView ArmAngle4;
    //    private TextView ArmAngle5L;
//    private TextView ArmAngle5H;
    private TextView ArmAngle5;
    //    private TextView ArmAngle6L;
//    private TextView ArmAngle6H;
    private TextView ArmAngle6;
    //    private TextView WaterFlowLow;
//    private TextView WaterFlowHigh;
    private TextView WaterFlow;
    //    private TextView WindSpeedLow;
//    private TextView WindSpeedHigh;
    private TextView WindSpeed;

    private TextView signalStrength;
    private TextView BatteryElectricity;
    private FrameLayout signalStrengthLayout;
    private FrameLayout signalStrengthLayout1;
    private LinearLayout waterLLayout;
    // private LinearLayout startLLayout;
    private TextView WirelessLinkStatus;
    private TextView RemoteCtrlStatus;

    private LinearLayout turretHitok;
    private LinearLayout turretHitfail;
    private LinearLayout urgentStopok;
    private LinearLayout urgentStopfail;
    private LinearLayout waterPumpok;
    private LinearLayout waterPumpfail;

    private LinearLayout noSignal;
    private LinearLayout haveSignal;

    private LinearLayout testB98LLayout;    //测试界面
    private LinearLayout mainLLayout;    //内容显示主界面

    private static int  change_page_old = 0;  //保存之前一次上升沿  0x01从0到1切换

    private static int  change_bright_old = 0;  //保存之前一次亮度上升沿   0x04从0到1切换

    private LinearLayout lowBattery;
    private LinearLayout haveBattery;
    private TextView Battery1;
    private TextView Battery2;
    private TextView Battery3;
    private TextView Battery4;

    private TextView tvLeftXz;
    private TextView tvMidXz;
    private TextView tvRightXz;

    private TextView formLevel1;
    private TextView formLevel2;
    private TextView formLevel3;
    private TextView formLevel4;
    private TextView formLevel5;
    private TextView formLevel6;
    private TextView formLevel7;
    private TextView formLevel8;

    private TextView waterLevel1;
    private TextView waterLevel2;
    private TextView waterLevel3;
    private TextView waterLevel4;
    private TextView waterLevel5;
    private TextView waterLevel6;
    private TextView waterLevel7;
    private TextView waterLevel8;



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
    private long[] mHints=new long[3];
    private PasswordInputFragment passwordInputFragment;

    //更新定位地图信息
    private Timer timer_loc = new Timer();
    private TimerTask timerTask_loc;

    private static int cameraTime_flag = 0;

    //测试界面控件  start
    private ImageView ivLeft1;
    private ImageView ivLeft2;
    private ImageView ivLeft3;
    private ImageView ivLeft4;

    private ImageView ivRight1;
    private ImageView ivRight2;
    private ImageView ivRight3;
    private ImageView ivRight4;

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

    private ImageView ivMidFour1111;
    private ImageView ivMidFour1122;
    private ImageView ivMidFour1133;
    private ImageView ivMidFour1144;

    private TextView tvMidFour1111;
    private TextView tvMidFour1122;
    private TextView tvMidFour1133;
    private TextView tvMidFour1144;

    private ImageView  ivStop1111;
    private TextView tvStop1111;

    private TextView tvRightTwo1211;
    private TextView tvRightTwo1212;

    private ImageView ivMid1111;
    private ImageView ivMid1112;

    private ImageView ivMid1211;
    private ImageView ivMid1212;
    private ImageView ivMid1213;

    private ImageView ivMid1311;
    private ImageView ivMid1312;
    private ImageView ivMid1313;

    private ImageView ivMid1411;
    private ImageView ivMid1412;
    private ImageView ivMid1413;

    private ImageView ivMid1511;
    private ImageView ivMid1512;
    private ImageView ivMid1513;

    private ImageView ivMid1611;
    private ImageView ivMid1612;

    private ImageView ivMid2111;
    private ImageView ivMid2112;

    private ImageView ivMid2211;
    private ImageView ivMid2212;

    private ImageView ivMid3211;
    private ImageView ivMid3212;

    private ImageView ivMid2311;
    private ImageView ivMid2312;
    private ImageView ivMid2313;

    private ImageView ivMid3311;
    private ImageView ivMid3312;
    private ImageView ivMid3313;
    private TextView signalStrength2;  //有线和无线

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
                    initSerial();
                } catch (Exception e) {
                    LogUtil.e(TAG,"========initSerial===========打开串口数据异常==================>"+e.getMessage());
                }
            }
        }, 1200);  //先连接控制器，获取控制器版本号 4000 to 1000

        //开启子线程，从文件加载数据到缓存,完成后打开串口、串口连接成功后，再进行同步
//        new Handler() {
//        }.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    initData();
//                } catch (Exception e) {
//                    LogUtil.e(TAG,"========初始化数据异常===============>"+e.getMessage());
//                }
//            }
//        }, 4000);

        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    CacheData.setMsg_info("==========cameraIndex7=======cameraIndex=======收到数据======>" + cameraIndex ,0);
                    cameraIndex = 7;
                    CameraInterface.getInstance().doStopCamera();
                    OpenCameraInfo();
                } catch (Exception e) {
                    LogUtil.e(TAG,"========初始化数据异常===============>"+e.getMessage());
                }
            }
        }, 1000);
//
//        new Handler() {
//        }.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    CacheData.setMsg_info("==========cameraIndex5=======cameraIndex=======收到数据======>" + cameraIndex ,0);
//                    cameraIndex = 5;
//                    CameraInterface.getInstance().doStopCamera();
//                    OpenCameraInfo();
//                } catch (Exception e) {
//                    LogUtil.e(TAG,"========初始化数据异常===============>"+e.getMessage());
//                }
//            }
//        }, 10000);

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public int getCameraIndex()
    {
        return cameraIndex;
    }

    public void setCameraIndex(int index)
    {
        this.cameraIndex = index;
    }

    /**
     *
     * 控件初始化
     *
     */
    private void initView(Bundle savedInstanceState){

        //初始化控件基本信息
        initBaseInfo();

        //初始化摄像头信息
        initCameraInfo();

        // OpenCameraInfo();
        SystemBrightManager.stopAutoBrightness(this);
        SystemBrightManager.setBrightness(this,100);

    }

    private String stringSub(String pa_str,String son_str){
        StringBuffer sb = new StringBuffer(pa_str);
        int index = pa_str.indexOf(son_str);
        sb.delete(index, index + son_str.length());
        return sb.toString();
    }

    /**
     * 将byte[]转为各种进制的字符串
     * @param bytes byte[]
     * @return 转换后的字符串
     */
    private String byteToString(byte[] bytes) throws Exception{
        String str = new String(bytes, "GBK");
        return TextUtils.isEmpty(str)?str:str.trim();
    }

    /**
     *
     * 初始化缓存数据
     *
     */
    private void initBaseInfo(){

        testB98LLayout = (LinearLayout)findViewById(R.id.testB98LLayout);
        mainLLayout = (LinearLayout)findViewById(R.id.mainLLayout);

        ivLeft1 = (ImageView)findViewById(R.id.ivLeft1);
        ivLeft2 = (ImageView)findViewById(R.id.ivLeft2);
        ivLeft3 = (ImageView)findViewById(R.id.ivLeft3);
        ivLeft4  = (ImageView)findViewById(R.id.ivLeft3);

        ivRight1 = (ImageView)findViewById(R.id.ivRight1);
        ivRight2 = (ImageView)findViewById(R.id.ivRight2);
        ivRight3 = (ImageView)findViewById(R.id.ivRight3);
        ivRight4 = (ImageView)findViewById(R.id.ivRight4);

        ivLeftFour1111 = (ImageView)findViewById(R.id.ivLeftFour1111);
        ivLeftFour1122 = (ImageView)findViewById(R.id.ivLeftFour1122);
        ivLeftFour1133 = (ImageView)findViewById(R.id.ivLeftFour1133);
        ivLeftFour1144 = (ImageView)findViewById(R.id.ivLeftFour1144);

        tvLeftFour1111 = (TextView)findViewById(R.id.tvLeftFour1111);
        tvLeftFour1122 = (TextView)findViewById(R.id.tvLeftFour1122);
        tvLeftFour1133 = (TextView)findViewById(R.id.tvLeftFour1133);
        tvLeftFour1144 = (TextView)findViewById(R.id.tvLeftFour1144);

        ivRightFour1111 = (ImageView)findViewById(R.id.ivRightFour1111);
        ivRightFour1122 = (ImageView)findViewById(R.id.ivRightFour1122);
        ivRightFour1133 = (ImageView)findViewById(R.id.ivRightFour1133);
        ivRightFour1144 = (ImageView)findViewById(R.id.ivRightFour1144);

        tvRightFour1111 = (TextView)findViewById(R.id.tvRightFour1111);
        tvRightFour1122 = (TextView)findViewById(R.id.tvRightFour1122);
        tvRightFour1133 = (TextView)findViewById(R.id.tvRightFour1133);
        tvRightFour1144 = (TextView)findViewById(R.id.tvRightFour1144);

        ivMidFour1111 = (ImageView)findViewById(R.id.ivMidFour1111);
        ivMidFour1122 = (ImageView)findViewById(R.id.ivMidFour1122);
        ivMidFour1133 = (ImageView)findViewById(R.id.ivMidFour1133);
        ivMidFour1144 = (ImageView)findViewById(R.id.ivMidFour1144);

        tvMidFour1111 = (TextView)findViewById(R.id.tvMidFour1111);
        tvMidFour1122 = (TextView)findViewById(R.id.tvMidFour1122);
        tvMidFour1133 = (TextView)findViewById(R.id.tvMidFour1133);
        tvMidFour1144 = (TextView)findViewById(R.id.tvMidFour1144);

        tvMidFour1133 = (TextView)findViewById(R.id.tvMidFour1133);
        tvMidFour1144 = (TextView)findViewById(R.id.tvMidFour1144);

        ivStop1111 = (ImageView)findViewById(R.id.ivStop1111);
        tvStop1111 = (TextView)findViewById(R.id.tvStop1111);

        tvRightTwo1211 = (TextView)findViewById(R.id.tvRightTwo1211);
        tvRightTwo1212 = (TextView)findViewById(R.id.tvRightTwo1212);

        ivMid1111 = (ImageView)findViewById(R.id.ivMid1111);
        ivMid1112 = (ImageView)findViewById(R.id.ivMid1112);

        ivMid1211 = (ImageView)findViewById(R.id.ivMid1211);
        ivMid1212 = (ImageView)findViewById(R.id.ivMid1212);
        ivMid1213 = (ImageView)findViewById(R.id.ivMid1213);

        ivMid1311 = (ImageView)findViewById(R.id.ivMid1311);
        ivMid1312 = (ImageView)findViewById(R.id.ivMid1312);
        ivMid1313 = (ImageView)findViewById(R.id.ivMid1313);

        ivMid1411 = (ImageView)findViewById(R.id.ivMid1411);
        ivMid1412 = (ImageView)findViewById(R.id.ivMid1412);
        ivMid1413 = (ImageView)findViewById(R.id.ivMid1413);

        ivMid1511 = (ImageView)findViewById(R.id.ivMid1511);
        ivMid1512 = (ImageView)findViewById(R.id.ivMid1512);
        ivMid1513 = (ImageView)findViewById(R.id.ivMid1513);

        ivMid1611 = (ImageView)findViewById(R.id.ivMid1611);
        ivMid1612 = (ImageView)findViewById(R.id.ivMid1612);

        ivMid2111 = (ImageView)findViewById(R.id.ivMid2111);
        ivMid2112 = (ImageView)findViewById(R.id.ivMid2112);

        ivMid2211 = (ImageView)findViewById(R.id.ivMid2211);
        ivMid2212 = (ImageView)findViewById(R.id.ivMid2212);

        ivMid3211 = (ImageView)findViewById(R.id.ivMid3211);
        ivMid3212 = (ImageView)findViewById(R.id.ivMid3212);

        ivMid2311 = (ImageView)findViewById(R.id.ivMid2311);
        ivMid2312 = (ImageView)findViewById(R.id.ivMid2312);
        ivMid2313 = (ImageView)findViewById(R.id.ivMid2313);

        ivMid3311 = (ImageView)findViewById(R.id.ivMid3311);
        ivMid3312 = (ImageView)findViewById(R.id.ivMid3312);
        ivMid3313 = (ImageView)findViewById(R.id.ivMid3313);

        //臂架旋转
        tvLeftXz = (TextView)findViewById(R.id.tvLeftXz);
        //属具旋转
        tvMidXz = (TextView)findViewById(R.id.tvMidXz);
        //备用旋转
        tvRightXz = (TextView)findViewById(R.id.tvRightXz);

        noSignalTest = (LinearLayout)findViewById(R.id.noSignalTest);
        haveSignalTest = (LinearLayout)findViewById(R.id.haveSignalTest);

        lowBatteryTest = (LinearLayout)findViewById(R.id.lowBatteryLayoutTest);
        haveBatteryTest = (LinearLayout)findViewById(R.id.haveBatteryLayoutTest);

        tvBatteryTest1 = (TextView)findViewById(R.id.tvBatteryTest1);
        tvBatteryTest2 = (TextView)findViewById(R.id.tvBatteryTest2);
        tvBatteryTest3 = (TextView)findViewById(R.id.tvBatteryTest3);
        tvBatteryTest4 = (TextView)findViewById(R.id.tvBatteryTest4);

        tvStartStaTest = (TextView)findViewById(R.id.tvStartStaTest);
        signalStrengthTest = (TextView)findViewById(R.id.signalStrengthTest);

        signalStrengthLayoutTest = (FrameLayout)findViewById(R.id.signalStrengthLayoutTest);
        signalStrengthLayout1Test = (FrameLayout)findViewById(R.id.signalStrengthLayout1Test);

        signalStrengthLayout = (FrameLayout) findViewById(R.id.signalStrengthLayout);
        signalStrengthLayout1 = (FrameLayout) findViewById(R.id.signalStrengthLayout1);
//        TurretAngleLow = (TextView) findViewById(R.id.TurretAngleLow);
//        TurretAngleHigh = (TextView) findViewById(R.id.TurretAngleHigh);
        TurretAngle = (TextView) findViewById(R.id.TurretAngle);
//        EngineSpeedLow = (TextView) findViewById(R.id.EngineSpeedLow);
//        EngineSpeedHigh = (TextView) findViewById(R.id.EngineSpeedHigh);
        EngineSpeed = (TextView) findViewById(R.id.EngineSpeed);
        HydraulicPress = (TextView) findViewById(R.id.HydraulicPress);
        PumpVentPress = (TextView) findViewById(R.id.PumpVentPress);
        ErrCode = (TextView) findViewById(R.id.ErrCode);
//        ArmAngle1L = (TextView) findViewById(R.id.ArmAngle1L);
//        ArmAngle1H = (TextView) findViewById(R.id.ArmAngle1H);
        ArmAngle1 = (TextView) findViewById(R.id.ArmAngle1);
//        ArmAngle2L = (TextView) findViewById(R.id.ArmAngle2L);
//        ArmAngle2H = (TextView) findViewById(R.id.ArmAngle2H);
        ArmAngle2 = (TextView) findViewById(R.id.ArmAngle2);
//        ArmAngle3L = (TextView) findViewById(R.id.ArmAngle3L);
//        ArmAngle3H = (TextView) findViewById(R.id.ArmAngle3H);
        ArmAngle3 = (TextView) findViewById(R.id.ArmAngle3);
//        ArmAngle4L = (TextView) findViewById(R.id.ArmAngle4L);
//        ArmAngle4H = (TextView) findViewById(R.id.ArmAngle4H);
        ArmAngle4 = (TextView) findViewById(R.id.ArmAngle4);
//        ArmAngle5L = (TextView) findViewById(R.id.ArmAngle5L);
//        ArmAngle5H = (TextView) findViewById(R.id.ArmAngle5H);
        ArmAngle5 = (TextView) findViewById(R.id.ArmAngle5);
//        ArmAngle6L = (TextView) findViewById(R.id.ArmAngle6L);
//        ArmAngle6H = (TextView) findViewById(R.id.ArmAngle6H);
//        ArmAngle6 = (TextView) findViewById(R.id.ArmAngle6);

//        WaterFlowLow = (TextView) findViewById(R.id.WaterFlowLow);
//        WaterFlowHigh = (TextView) findViewById(R.id.WaterFlowHigh);
        WaterFlow = (TextView) findViewById(R.id.WaterFlow);
//        WindSpeedLow = (TextView) findViewById(R.id.WindSpeedLow);
//        WindSpeedHigh = (TextView) findViewById(R.id.WindSpeedHigh);
//        WindSpeed = (TextView) findViewById(R.id.WindSpeed);

        signalStrength = (TextView) findViewById(R.id.signalStrength);
        RemoteCtrlStatus = (TextView) findViewById(R.id.RemoteCtrlStatus);

        signalStrengthLayout.setOnClickListener(this);
        signalStrengthLayout1.setOnClickListener(this);
        waterLLayout = (LinearLayout) findViewById(R.id.waterLLayout);
        waterLLayout.setOnClickListener(this);
//        startLLayout = (LinearLayout) findViewById(R.id.startLLayout);
//        startLLayout.setOnClickListener(this);


        turretHitok = (LinearLayout) findViewById(R.id.TurretHitok);
        turretHitfail = (LinearLayout) findViewById(R.id.TurretHitfail);

        urgentStopok = (LinearLayout) findViewById(R.id.UrgentStopok);
        urgentStopfail = (LinearLayout) findViewById(R.id.UrgentStopfail);

        waterPumpok = (LinearLayout) findViewById(R.id.WaterPumpok);
        waterPumpfail = (LinearLayout) findViewById(R.id.WaterPumpfail);

        noSignal = (LinearLayout) findViewById(R.id.tvSignal2);
        haveSignal = (LinearLayout) findViewById(R.id.tvSignal1);

        lowBattery = (LinearLayout) findViewById(R.id.lowBatteryLayout);
        haveBattery = (LinearLayout) findViewById(R.id.haveBatteryLayout);
        Battery1 = (TextView) findViewById(R.id.tvBattery1);
        Battery2 = (TextView) findViewById(R.id.tvBattery2);
        Battery3 = (TextView) findViewById(R.id.tvBattery3);
        Battery4 = (TextView) findViewById(R.id.tvBattery4);

//        formLevel1 = (TextView) findViewById(R.id.tvFormlevel1);
//        formLevel2 = (TextView) findViewById(R.id.tvFormlevel2);
//        formLevel3 = (TextView) findViewById(R.id.tvFormlevel3);
//        formLevel4 = (TextView) findViewById(R.id.tvFormlevel4);
//        formLevel5 = (TextView) findViewById(R.id.tvFormlevel5);
//        formLevel6 = (TextView) findViewById(R.id.tvFormlevel6);
//        formLevel7 = (TextView) findViewById(R.id.tvFormlevel7);
//        formLevel8 = (TextView) findViewById(R.id.tvFormlevel8);

//        waterLevel1 = (TextView) findViewById(R.id.tvWaterlevel1);
//        waterLevel2 = (TextView) findViewById(R.id.tvWaterlevel2);
//        waterLevel3 = (TextView) findViewById(R.id.tvWaterlevel3);
//        waterLevel4 = (TextView) findViewById(R.id.tvWaterlevel4);
//        waterLevel5 = (TextView) findViewById(R.id.tvWaterlevel5);
//        waterLevel6 = (TextView) findViewById(R.id.tvWaterlevel6);
//        waterLevel7 = (TextView) findViewById(R.id.tvWaterlevel7);
//        waterLevel8 = (TextView) findViewById(R.id.tvWaterlevel8);

    }

    /**
     * 初始化地图信息
     * @param
     */
    private void initCameraInfo(){
        //摄像头控件
        cameraView = (CameraSurfaceView)findViewById(R.id.cameraView);
        cameraView.setVisibility(View.VISIBLE);

    }

    /**
     * 初始化串口
     * @param
     */
    private void initSerial(){

        try {
            serialhelp = new SerialHelper(mHandler, getApplicationContext());
            serialhelp.open();
        } catch (Exception e) {
            LogUtil.d("initSerial","============打开串口异常==============：" + e.getMessage());

        }

    }


    private void changePage(int flag){
        int val;
        // CacheData.setMsg_info("=======changePage====flag====flag========="+flag,1);
        if((flag&0x1) == 0x1) {
            if(change_page_old == 0){
//                SharedPreferences share = getSharedPreferences("Acitivity", Context.MODE_WORLD_READABLE);
//                int page = share.getInt("page", 0);
                if (mainLLayout.getVisibility() == View.VISIBLE){  //0表示内容显示界面， 1：表示测试诊断界面
                    // page = 1;
                    mainLLayout.setVisibility(View.GONE);
                    testB98LLayout.setVisibility(View.VISIBLE);
                }else {
                    // page = 0;
                    mainLLayout.setVisibility(View.VISIBLE);
                    testB98LLayout.setVisibility(View.GONE);
                }
//                SharedPreferences.Editor editor = share.edit();//获取编辑器
//                editor.putInt("page", page);
//                editor.commit();
            }
            change_page_old = 1;
        }else{
            change_page_old = 0;
        }

    }


    /**
     * 布局中所有点击事件处理
     * @param view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        try {
            switch (id) {

                //打开摄像头
//                case R.id.waterLevel: {
//                    //往linux发送视频摄像头打开指令
//                    if (serialhelp != null) {
//                        //打开预览
//                        OpenCameraInfo();
//                        //serialhelp.sendDisplayVideo(NumberBytes.intToByte(CacheData.getSerial_id()), CacheData.content_display,true);
//                    }
//                    break;
//                }



                case R.id.signalStrengthLayout:
                case R.id.signalStrengthLayout1:{

                    //弹出调试信息显示框
                    if(messageShowFragment == null){
                        messageShowFragment = new MessageShowFragment();
                    }

                    if(!messageShowFragment.isAdded() && !messageShowFragment.isVisible()&& !messageShowFragment.isRemoving()){
                        messageShowFragment.show(getSupportFragmentManager(), "messageLogId");
                    }
                    //进入文件系统目录
//                    dealPasswordValidate(IConstant.SOURCE_SETTING);

                    break;

                }

                case R.id.waterLLayout:{
                    //进入文件系统目录
                    dealPasswordValidate(IConstant.SOURCE_SETTING);
                    break;
                }



            }

        }catch (Exception e) {

        }
    }

    /**
     *
     * 跳转到密码输入框，验证通过后，做相应的处理
     *
     */
    private void dealPasswordValidate(final int source){
        System.arraycopy(mHints,1,mHints,0,mHints.length-1);
        mHints[mHints.length-1] = SystemClock.uptimeMillis();
        if(SystemClock.uptimeMillis()-mHints[0]<=500){

            if(passwordInputFragment == null){
                passwordInputFragment = new PasswordInputFragment();
            }

            passwordInputFragment.setSourece(source);
            passwordInputFragment.setFunCallback(new IFunCallback() {
                @Override
                public void onSuccess(Object obj) {
                    final int source = (int)obj;
                    String content = "";
                    if(source==IConstant.SOURCE_SETTING){
                        content = "当前安装包的版本号：" + CacheData.versioCode_current + "当前安装包的version_code：" + CacheData.versionName_current +"，您确定要进设置吗？";
                    }else if(source==IConstant.SOURCE_ES_FILEMANAGER){
                        content = "当前安装包的版本号：" + CacheData.versioCode_current + "当前安装包的version_code：" + CacheData.versionName_current +"，您确定要进ES文件管理器吗？";
                    }else if(source==IConstant.SOURCE_QUERY_LOG){
                        if(messageShowFragment == null){
                            messageShowFragment = new MessageShowFragment();
                        }

                        if(!messageShowFragment.isAdded() && !messageShowFragment.isVisible()&& !messageShowFragment.isRemoving()){
                            messageShowFragment.show(getSupportFragmentManager(), "messageLogId");
                        }
                        return;
                    }else if(source==IConstant.SOURCE_U_UPGRADE){
                        // if(FileUtil.getAllExternalSdcardPath()){
                        //发送安装广播
                        Intent intent = new Intent();
                        intent.setAction("com.unisound.unicar.install.action");
                        intent.putExtra("apk","/storage/usb0/app-debug.apk");    //  /storage/usb0/app-debug.apk
                        sendBroadcast(intent);
//                        }else{
//                            Toast.makeText(getApplicationContext(), "没有检测到连接的U盘，连接U盘到检测到大概需要15秒！", Toast.LENGTH_SHORT).show();
//                        }


                    }else if(source==IConstant.SOURCE_CLEAR_RECORD_DEVICE_DATA){

                        //根据上传的状态显示对应的信息
//                        serialhelp.deleteRecordDate();
//                        Toast.makeText(getApplicationContext(), "删除记录仪数据信息",
//                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage(content);
                    dialog.setPositiveButton("确认",new DialogInterface.OnClickListener(){
                        public  void onClick(DialogInterface dialog,int which)
                        {

                            //MainActivity.this.finish();
                            //System.exit(0);

                            try{
                                Intent intent = new Intent();
                                if(source==IConstant.SOURCE_SETTING){
                                    intent.setClassName("com.android.settings", "com.android.settings.Settings");
                                    startActivity(intent);
//                                     MainActivity.this.finish();
//                                     System.exit(0);
                                }else if(source==IConstant.SOURCE_ES_FILEMANAGER){
                                    intent.setClassName("com.estrongs.android.pop",
                                            "com.estrongs.android.pop.view.FileExplorerActivity");
                                    startActivity(intent);

                                }else if(source==IConstant.SOURCE_U_UPGRADE){
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

                            }catch (Exception e){
                                LogUtil.e(TAG,"=================dealPasswordValidate=====================>"+e.getMessage());

                            }

                        }
                    });
                    dialog.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                        public  void onClick(DialogInterface dialog,int which)
                        {

                        }
                    });
                    dialog.show();
                }
            });

            if(!passwordInputFragment.isAdded() && !passwordInputFragment.isVisible()
                    && !passwordInputFragment.isRemoving()){
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

            try{

                switch (msg.what) {

                    //综合信息上传
                    case IConstant.COMMAND_MULTIPLE_POSTION_INFO: {
                        MultipleStateInfo multipleStateInfo = (MultipleStateInfo) msg.obj;
                        byte [] content = msg.getData().getByteArray("content");
                        //根据上传的状态显示对应的信息
                        displayMultipleState(multipleStateInfo);
                        dealOtherData(content);    //处理诊断测试数据
                    }
                    break;

                    //显示屏下发摄像头指定显示指令linux成功应答
                    case IConstant.COMMAND_DOWN_CAMERA: {
                        //=============

                    }
                    break;

                }
            }catch (Exception e){
                LogUtil.e(TAG,"===================handleMessage===========================>"+e.getMessage());
                e.printStackTrace();
            }

        }

    }


    /**
     *
     * 处理其它数据
     * @param content
     *
     */
    private void dealOtherData(byte [] content) {

        CacheData.setMsg_info("============ReadSerialDataThread=========content===================>" + NumberBytes.byteArrayToHexStr(content), IConstant.MESSAGE_INFO_ALL);
        //B40对应181_b0 遥控启动
        //181数据解析
        if (content.length > 40) {
            temp = content[40];
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
                ivLeft1.setImageResource(R.drawable.ic_round_red);
            } else {
                ivLeft1.setImageResource(R.drawable.ic_round_white);
            }

            //遥控直流  遥控喷雾
            if (IConstant.STATE_10.equals("" + tmp_1 + tmp_2)){   //
                ivMid1311.setImageResource(R.drawable.ic_3_up_red);
                ivMid1312.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid1313.setImageResource(R.drawable.ic_3_do_wh);
            } else if (IConstant.STATE_00.equals("" + tmp_1 + tmp_2)) {
                ivMid1311.setImageResource(R.drawable.ic_3_up_wh);
                ivMid1312.setImageResource(R.drawable.ic_3_mi_red);
                ivMid1313.setImageResource(R.drawable.ic_3_do_wh);
            } else if (IConstant.STATE_01.equals("" + tmp_1 + tmp_2)){
                ivMid1311.setImageResource(R.drawable.ic_3_up_wh);
                ivMid1312.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid1313.setImageResource(R.drawable.ic_3_do_red);
            }

            //遥控水炮上  遥控水炮下
            if (IConstant.STATE_10.equals("" + tmp_3 + tmp_4)) {
                ivMid1511.setImageResource(R.drawable.ic_3_up_red);
                ivMid1512.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid1513.setImageResource(R.drawable.ic_3_do_wh);
            } else if (IConstant.STATE_00.equals("" + tmp_3 + tmp_4)) {
                ivMid1511.setImageResource(R.drawable.ic_3_up_wh);
                ivMid1512.setImageResource(R.drawable.ic_3_mi_red);
                ivMid1513.setImageResource(R.drawable.ic_3_do_wh);
            } else if (IConstant.STATE_01.equals("" + tmp_3 + tmp_4)){
                ivMid1511.setImageResource(R.drawable.ic_3_up_wh);
                ivMid1512.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid1513.setImageResource(R.drawable.ic_3_do_red);
            }

            //遥控水炮左  遥控水炮右
            if (IConstant.STATE_10.equals("" + tmp_5 + tmp_6)) {
                ivMid1411.setImageResource(R.drawable.ic_3_up_red);
                ivMid1412.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid1413.setImageResource(R.drawable.ic_3_do_wh);
            } else if (IConstant.STATE_00.equals("" + tmp_5 + tmp_6)) {
                ivMid1411.setImageResource(R.drawable.ic_3_up_wh);
                ivMid1412.setImageResource(R.drawable.ic_3_mi_red);
                ivMid1413.setImageResource(R.drawable.ic_3_do_wh);
            } else if (IConstant.STATE_01.equals("" + tmp_5 + tmp_6)) {
                ivMid1411.setImageResource(R.drawable.ic_3_up_wh);
                ivMid1412.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid1413.setImageResource(R.drawable.ic_3_do_red);
            }

            //讯响/喇叭
            if (tmp_7 == 1) {
                ivRight4.setImageResource(R.drawable.ic_round_red);
            } else {
                ivRight4.setImageResource(R.drawable.ic_round_white);
            }
        }

        if (content.length > 41) {
            temp = content[41];
            tmp_0 = (temp >> 0) & 0x1;
            tmp_1 = (temp >> 1) & 0x1;
            tmp_2 = (temp >> 2) & 0x1;
            tmp_3 = (temp >> 3) & 0x1;
            tmp_4 = (temp >> 4) & 0x1;
            tmp_5 = (temp >> 5) & 0x1;
            tmp_6 = (temp >> 6) & 0x1;
            tmp_7 = (temp >> 7) & 0x1;

            //选择手动
            if (tmp_0 == 1) {
                ivMid2211.setImageResource(R.drawable.ic_22_up_red);
                ivMid2212.setImageResource(R.drawable.ic_22_do_wh);
                //提附具  放附具
//                if (tmp_2 == 1) {
//                    ivMid3311.setImageResource(R.drawable.ic_3_up_red);
//                    ivMid3312.setImageResource(R.drawable.ic_3_mi_wh);
//                    ivMid3313.setImageResource(R.drawable.ic_3_do_wh);
//                } else if (tmp_3 == 1) {
//                    ivMid3311.setImageResource(R.drawable.ic_3_up_wh);
//                    ivMid3312.setImageResource(R.drawable.ic_3_mi_red);
//                    ivMid1313.setImageResource(R.drawable.ic_3_do_wh);
//                } else {
//                    ivMid3311.setImageResource(R.drawable.ic_3_up_wh);
//                    ivMid3312.setImageResource(R.drawable.ic_3_mi_wh);
//                    ivMid1313.setImageResource(R.drawable.ic_3_do_red);
//                }
            } else {
                ivMid2211.setImageResource(R.drawable.ic_22_up_wh);
                ivMid2212.setImageResource(R.drawable.ic_22_do_red);
            }

            //臂架使能
            if (tmp_1 == 1) {
                ivMid2111.setImageResource(R.drawable.ic_22_up_red);
                ivMid2112.setImageResource(R.drawable.ic_22_do_wh);
            } else {
                ivMid2111.setImageResource(R.drawable.ic_22_up_wh);
                ivMid2112.setImageResource(R.drawable.ic_22_do_red);
            }

            //遥控器照明
            if (tmp_2 == 1) {
                ivRight3.setImageResource(R.drawable.ic_round_red);
            } else {
                ivRight3.setImageResource(R.drawable.ic_round_white);
            }

            //急停
            if(tmp_3 == 1) {
                // ivRight3.setImageResource(R.drawable.ic_round_red);
            } else {
                // ivRight3.setImageResource(R.drawable.ic_round_white);
            }

            //水炮自摆  水炮复位
            if (IConstant.STATE_10.equals("" + tmp_4 + tmp_5)) {
                ivMid1211.setImageResource(R.drawable.ic_3_up_red);
                ivMid1212.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid1213.setImageResource(R.drawable.ic_3_do_wh);
            } else if (IConstant.STATE_00.equals("" + tmp_4 + tmp_5)) {
                ivMid1211.setImageResource(R.drawable.ic_3_up_wh);
                ivMid1212.setImageResource(R.drawable.ic_3_mi_red);
                ivMid1213.setImageResource(R.drawable.ic_3_do_wh);
            } else  if (IConstant.STATE_01.equals("" + tmp_4 + tmp_5)) {
                ivMid1211.setImageResource(R.drawable.ic_3_up_wh);
                ivMid1212.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid1213.setImageResource(R.drawable.ic_3_do_red);
            }

            //回转对中
            if (tmp_0 == 1) {
                ivLeft2.setImageResource(R.drawable.ic_round_red);
            } else {
                ivLeft2.setImageResource(R.drawable.ic_round_white);
            }

            //快档 1
            if (tmp_1 == 1) {
                ivMid3211.setImageResource(R.drawable.ic_22_up_red);
                ivMid3212.setImageResource(R.drawable.ic_22_do_wh);
            } else {
                ivMid3211.setImageResource(R.drawable.ic_22_up_wh);
                ivMid3212.setImageResource(R.drawable.ic_22_do_red);
            }
        }

        if (content.length > 42) {
            temp = content[42];
            tmp_0 = (temp >> 0) & 0x1;
            tmp_1 = (temp >> 1) & 0x1;
            tmp_2 = (temp >> 2) & 0x1;
            tmp_3 = (temp >> 3) & 0x1;
            tmp_4 = (temp >> 4) & 0x1;
            tmp_5 = (temp >> 5) & 0x1;
            tmp_6 = (temp >> 6) & 0x1;
            tmp_7 = (temp >> 7) & 0x1;

            //锤
            if (IConstant.STATE_100.equals("" + tmp_0 + tmp_1 + tmp_2)) {
                ivMid2311.setImageResource(R.drawable.ic_3_up_red);
                ivMid2312.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid2313.setImageResource(R.drawable.ic_3_do_wh);
            } else if (IConstant.STATE_010.equals("" + tmp_0 + tmp_1 + tmp_2)) {  //选择剪
                ivMid2311.setImageResource(R.drawable.ic_3_up_wh);
                ivMid2312.setImageResource(R.drawable.ic_3_mi_red);
                ivMid2313.setImageResource(R.drawable.ic_3_do_wh);
            } else  if (IConstant.STATE_001.equals("" + tmp_0 + tmp_1 + tmp_2)) {  //选择铲
                ivMid2311.setImageResource(R.drawable.ic_3_up_wh);
                ivMid2312.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid2313.setImageResource(R.drawable.ic_3_do_red);
            }

            //一键定位
            if (tmp_3 == 1) {
                ivRight1.setImageResource(R.drawable.ic_round_red);
            } else {
                ivRight1.setImageResource(R.drawable.ic_round_white);
            }

            //属具锁定
            if (tmp_4 == 1) {
                ivLeft3.setImageResource(R.drawable.ic_round_red);
            } else {
                ivLeft3.setImageResource(R.drawable.ic_round_white);
            }

            //附具解锁
            if (tmp_5 == 1) {
                ivLeft4.setImageResource(R.drawable.ic_round_red);
            } else {
                ivLeft4.setImageResource(R.drawable.ic_round_white);
            }

            //炮臂伸  炮臂收
            if (IConstant.STATE_10.equals("" + tmp_6 + tmp_7)) {
                ivMid3311.setImageResource(R.drawable.ic_3_up_red);
                ivMid3312.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid3313.setImageResource(R.drawable.ic_3_do_wh);
            } else if (IConstant.STATE_00.equals("" + tmp_6 + tmp_7)) {
                ivMid3311.setImageResource(R.drawable.ic_3_up_wh);
                ivMid3312.setImageResource(R.drawable.ic_3_mi_red);
                ivMid3313.setImageResource(R.drawable.ic_3_do_wh);
            } else if (IConstant.STATE_01.equals("" + tmp_6 + tmp_7)) {
                ivMid3311.setImageResource(R.drawable.ic_3_up_wh);
                ivMid3312.setImageResource(R.drawable.ic_3_mi_wh);
                ivMid3313.setImageResource(R.drawable.ic_3_do_red);
            }

        }

        //发动机熄火
        if (content.length > 43) {
            temp = content[43];
            tmp_0 = (temp >> 0) & 0x1;
            tmp_1 = (temp >> 1) & 0x1;
            tmp_2 = (temp >> 2) & 0x1;
            tmp_3 = (temp >> 3) & 0x1;
            tmp_4 = (temp >> 4) & 0x1;
            tmp_5 = (temp >> 5) & 0x1;
            tmp_6 = (temp >> 6) & 0x1;
            tmp_7 = (temp >> 7) & 0x1;
            //发动机启动
//            if (tmp_0 == 1) {
//
//            } else {
//
//            }

            //任意手柄动作
            if (tmp_1 == 1) {
                //ivMid3311.setImageResource(R.drawable.ic_22_up_red);
               // ivMid3312.setImageResource(R.drawable.ic_22_do_wh);
            } else {
                //ivMid3311.setImageResource(R.drawable.ic_22_up_wh);
                //ivMid3312.setImageResource(R.drawable.ic_22_do_red);
            }

            //水炮照明
            if (tmp_4 == 1) {
                ivRight2.setImageResource(R.drawable.ic_round_red);
            } else {
                ivRight2.setImageResource(R.drawable.ic_round_white);
            }

            // 水泵启动
            if (tmp_6 == 1) {
                ivMid1611.setImageResource(R.drawable.ic_22_up_red);
                ivMid1612.setImageResource(R.drawable.ic_22_do_wh);
            } else {
                ivMid1611.setImageResource(R.drawable.ic_22_up_wh);
                ivMid1612.setImageResource(R.drawable.ic_22_do_red);
            }

            //臂架喷淋
            if (tmp_7 == 1) {
                ivMid1111.setImageResource(R.drawable.ic_22_up_red);
                ivMid1112.setImageResource(R.drawable.ic_22_do_wh);
            } else {
                ivMid1111.setImageResource(R.drawable.ic_22_up_wh);
                ivMid1112.setImageResource(R.drawable.ic_22_do_red);
            }



            //炮出水开
//            if (tmp_4 == 1) {
//                ivMid1111.setImageResource(R.drawable.ic_22_up_red);
//                ivMid1112.setImageResource(R.drawable.ic_22_do_wh);
//            } else {
//                ivMid1111.setImageResource(R.drawable.ic_22_up_wh);
//                ivMid1112.setImageResource(R.drawable.ic_22_do_red);
//            }

            //钮子备用
//            if (tmp_5 == 1) {
//                ivMid2311.setImageResource(R.drawable.ic_22_up_red);
//                ivMid2312.setImageResource(R.drawable.ic_22_do_wh);
//            } else {
//                ivMid2311.setImageResource(R.drawable.ic_22_up_wh);
//                ivMid2312.setImageResource(R.drawable.ic_22_do_red);
//            }

        }


        // 回转手柄信号  ==  臂架旋转
        System.arraycopy(content,45,temp_1,0,1);
        int hzsbxh = bytes2int(temp_1);
        if(hzsbxh  <= 127){
            tvLeftXz.setText("" + hzsbxh);
        }else if(hzsbxh > 127){
            tvLeftXz.setText("-" + (256-hzsbxh));
        }
        if(hzsbxh==0){
            tvLeftXz.setText("");
        }


        //一臂手柄信号
        System.arraycopy(content,46,temp_1,0,1);
        int ybsb = bytes2int(temp_1);
        if(ybsb  <= 127){   //  1为上，0为下  图片的设置
            ivMidFour1111.setImageResource(R.drawable.ic_up_default);
            tvMidFour1111.setText("");
            ivMidFour1122.setImageResource(R.drawable.ic_down_select);
            tvMidFour1122.setText("" + ybsb);

        }else if(ybsb > 127){
            ivMidFour1111.setImageResource(R.drawable.ic_up_select);
            tvMidFour1111.setText("-" + (256-ybsb));
            ivMidFour1122.setImageResource(R.drawable.ic_down_default);
            tvMidFour1122.setText("");
        }
        if(ybsb==0){
            ivMidFour1122.setImageResource(R.drawable.ic_down_default);
            tvMidFour1122.setText("");
        }

        //二臂手柄信号
        System.arraycopy(content,47,temp_1,0,1);
        int ebsb =bytes2int(temp_1);
        if(ebsb  <= 127){   //  1为上，0为下  图片的设置
            ivLeftFour1111.setImageResource(R.drawable.ic_up_default);
            tvLeftFour1111.setText("");
            ivLeftFour1122.setImageResource(R.drawable.ic_down_select);
            tvLeftFour1122.setText("" + ebsb);

        }else if(ebsb > 127){
            ivLeftFour1111.setImageResource(R.drawable.ic_up_select);
            ivLeftFour1122.setImageResource(R.drawable.ic_down_default);
            tvLeftFour1111.setText("-" + (256-ebsb));
            tvLeftFour1122.setText("");
        }

        if(ebsb==0){
            ivLeftFour1122.setImageResource(R.drawable.ic_down_default);
            tvLeftFour1122.setText("");
        }


        //三臂手柄信号
        System.arraycopy(content,48,temp_1,0,1);
        int sanbsb = bytes2int(temp_1);
        if(sanbsb <= 127){     // 1为上，0为下
            ivLeftFour1133.setImageResource(R.drawable.ic_left_default);
            tvLeftFour1133.setText("");
            ivLeftFour1144.setImageResource(R.drawable.ic_right_select);
            tvLeftFour1144.setText("" + sanbsb);

        }else if(sanbsb > 127){
            ivLeftFour1133.setImageResource(R.drawable.ic_left_select);
            tvLeftFour1133.setText("-" + (256-sanbsb));
            ivLeftFour1144.setImageResource(R.drawable.ic_right_default);
            tvLeftFour1144.setText("");
        }

        if(sanbsb==0){
            ivLeftFour1144.setImageResource(R.drawable.ic_right_default);
            tvLeftFour1144.setText("");
        }

        //附具俯仰信号
        System.arraycopy(content,49,temp_1,0,1);
        int fjfysb = bytes2int(temp_1);

        if(fjfysb  <= 127){   //  1为上，0为下
            ivRightFour1133.setImageResource(R.drawable.ic_left_default);
            tvRightFour1133.setText("");
            if(fjfysb==0){
                ivRightFour1144.setImageResource(R.drawable.ic_right_default);
                tvRightFour1144.setText("" );
            }else{
                ivRightFour1144.setImageResource(R.drawable.ic_right_select);
                tvRightFour1144.setText("" + fjfysb);
            }
        }else if(fjfysb > 127){
            ivRightFour1133.setImageResource(R.drawable.ic_left_select);
            ivRightFour1144.setImageResource(R.drawable.ic_right_default);
            tvRightFour1133.setText("-" + (256-fjfysb));
            tvRightFour1144.setText("");
        }

        //剪顺逆旋信号
        System.arraycopy(content,50,temp_1,0,1);
        int jxxzxh = bytes2int(temp_1);
        if(jxxzxh  <= 127){
            tvMidXz.setText("" + jxxzxh);
        }else if(jxxzxh > 127){
            tvMidXz.setText("-" + (256-jxxzxh));
        }
        if(jxxzxh==0){
            tvMidXz.setText("");
        }

        //水泵调速信号
        System.arraycopy(content,51,temp_1,0,1);
        int sdtjxh = bytes2int(temp_1);
        tvRightTwo1211.setText("" + sdtjxh);



        //四臂调速信号
        System.arraycopy(content,52,temp_1,0,1);
        int sibsb = bytes2int(temp_1);
        if(sibsb <= 127){     //  1 为上，0为下
            ivRightFour1111.setImageResource(R.drawable.ic_up_default);
            tvRightFour1111.setText("");
            if(sibsb==0){
                ivRightFour1122.setImageResource(R.drawable.ic_down_default);
                tvRightFour1122.setText("" );
            }else{
                ivRightFour1122.setImageResource(R.drawable.ic_down_select);
                tvRightFour1122.setText("" + sibsb);
            }

        }else if(sibsb > 127){
            ivRightFour1111.setImageResource(R.drawable.ic_up_select);
            ivRightFour1122.setImageResource(R.drawable.ic_down_default);
            tvRightFour1111.setText("-" + (256-sibsb));
            tvRightFour1122.setText("");
        }

        //剪开合信号信号
        System.arraycopy(content,53,temp_1,0,1);
        int jkhxh = bytes2int(temp_1);
        if(jkhxh  <= 127){   //  1为上，0为下  图片的设置
            ivMidFour1133.setImageResource(R.drawable.ic_up_default);
            tvMidFour1133.setText("");
            ivMidFour1144.setImageResource(R.drawable.ic_down_select);
            tvMidFour1144.setText("" + jkhxh);

        }else if(jkhxh > 127){
            ivMidFour1133.setImageResource(R.drawable.ic_up_select);
            tvMidFour1133.setText("-" + (256-jkhxh));
            ivMidFour1144.setImageResource(R.drawable.ic_down_default);
            tvMidFour1144.setText("");
        }
        if(jkhxh==0){
            ivMidFour1144.setImageResource(R.drawable.ic_down_default);
            tvMidFour1144.setText("");
        }

        //备用信号
        System.arraycopy(content,39,temp_1,0,1);
        int byxh = bytes2int(temp_1);
        if(byxh  <= 127){
            tvLeftXz.setText("" + byxh);
        }else if(byxh > 127){
            tvLeftXz.setText("-" + (256-byxh));
        }
        if(byxh==0){
            tvLeftXz.setText("");
        }

   }


    /**
     * 将byte[]转为int形
     * @param bytes byte[]
     * @return 转换后的字符串
     *
     */
    private int bytes2int(byte[] bytes){
        return new BigInteger(1, bytes).intValue();// 这里的1代表正数
    }



    /**
     *
     * 显示上报的综合信息
     * @param multipleStateInfo
     *
     */
    private void displayMultipleState(MultipleStateInfo multipleStateInfo) throws Exception {

//        CacheData.setMsg_info("==========displayMultipleState=======count=======11111======>" + count ,0);

        String turrent_angel_negtive = multipleStateInfo.getTurret_angle_negtive();

        String arm_spread_status = multipleStateInfo.getArm_spread_status();

        String arm_shrink_status = multipleStateInfo.getArm_shrink_status();

        String foam_level = multipleStateInfo.getFoam_level();

        String water_pump_status = multipleStateInfo.getWater_pump_status();

        String turret_hit_status = multipleStateInfo.getTurret_hit_status();

        String urgent_stop = multipleStateInfo.getUrgent_stop();

        String water_level = multipleStateInfo.getWater_level();

//        String turret_angle_low = multipleStateInfo.getTurret_angle_low();
//        String turret_angle_high = multipleStateInfo.getTurret_angle_high();
        float turret_angle = multipleStateInfo.getTurret_angle();

//        String engine_speed_low = multipleStateInfo.getEngine_speed_low();
//        String engine_speed_high = multipleStateInfo.getEngine_speed_high();
        int engine_speed = multipleStateInfo.getEngine_speed();

        String hydraulic_press = multipleStateInfo.getHydraulic_press();

//        String pump_vent_press = multipleStateInfo.getPump_vent_press();
        float pump_vent_press = multipleStateInfo.getPump_vent_press();

        String err_code = multipleStateInfo.getErr_code();

//        String arm_angle1_low = multipleStateInfo.getArm_angle1_low();
//        String arm_angle1_high = multipleStateInfo.getArm_angle1_high();
        int arm_angle1 = multipleStateInfo.getArm_angle1();

//        String arm_angle2_low = multipleStateInfo.getArm_angle2_low();
//        String arm_angle2_high = multipleStateInfo.getArm_angle2_high();
        int arm_angle2 = multipleStateInfo.getArm_angle2();

//        String arm_angle3_low = multipleStateInfo.getArm_angle3_low();
//        String arm_angle3_high = multipleStateInfo.getArm_angle3_high();
        int arm_angle3 = multipleStateInfo.getArm_angle3();

//        String arm_angle4_low = multipleStateInfo.getArm_angle4_low();
//        String arm_angle4_high = multipleStateInfo.getArm_angle4_high();
        int arm_angle4 = multipleStateInfo.getArm_angle4();

//        String arm_angle5_low = multipleStateInfo.getArm_angle5_low();
//        String arm_angle5_high = multipleStateInfo.getArm_angle5_high();
        int arm_angle5 = multipleStateInfo.getArm_angle5();

//        String arm_angle6_low = multipleStateInfo.getArm_angle6_low();
//        String arm_angle6_high = multipleStateInfo.getArm_angle6_high();
        int arm_angle6 = multipleStateInfo.getArm_angle6();

//        String water_flow_low = multipleStateInfo.getWater_flow_low();
//        String water_flow_high = multipleStateInfo.getWater_flow_high();
        int water_flow = multipleStateInfo.getWater_flow();
//
//        String wind_speed_low = multipleStateInfo.getWind_speed_low();
//        String wind_speed_high = multipleStateInfo.getWind_speed_high();
        float wind_speed = multipleStateInfo.getWind_speed();

        String battery_electricity = multipleStateInfo.getBattery_electricity();

        int signal_strength = multipleStateInfo.getSignal_strength();

        String wireless_link_status = multipleStateInfo.getWireless_link_status();

        int remote_ctrl_status = multipleStateInfo.getRemote_ctrl_status();

        int brightness = multipleStateInfo.getBrightness();
//        CacheData.setMsg_info("==========displayMultipleState=======1=======收到数据======>" ,0);

        if(IConstant.TURRET_ANGEL_NEGTIVE.equals(turrent_angel_negtive) )
        {
            TurretAngle.setText("-" + turret_angle);
        }else{
            TurretAngle.setText("" + turret_angle);
        }
//        TurretAngleLow.setText(turret_angle_low);
//        TurretAngleHigh.setText(turret_angle_high);
//        TurretAngle.setText("" + turret_angle);

//        EngineSpeedLow.setText(engine_speed_low);
//        EngineSpeedHigh.setText(engine_speed_high);
        EngineSpeed.setText("" + engine_speed);

        HydraulicPress.setText(hydraulic_press);
        PumpVentPress.setText("" + pump_vent_press);

//        ArmAngle1L.setText(arm_angle1_low);
//        ArmAngle1H.setText(arm_angle1_high);
        ArmAngle1.setText("" + arm_angle1);
//        ArmAngle2L.setText(arm_angle2_low);
//        ArmAngle2H.setText(arm_angle2_high);
        ArmAngle2.setText("" + arm_angle2);
//        ArmAngle3L.setText(arm_angle3_low);
//        ArmAngle3H.setText(arm_angle3_high);
        ArmAngle3.setText("" + arm_angle3);
//        ArmAngle4L.setText(arm_angle4_low);
//        ArmAngle4H.setText(arm_angle4_high);
        ArmAngle4.setText("" + arm_angle4);
//        ArmAngle5L.setText(arm_angle5_low);
//        ArmAngle5H.setText(arm_angle5_high);
        ArmAngle5.setText("" + arm_angle5);
//        ArmAngle6L.setText(arm_angle6_low);
//        ArmAngle6H.setText(arm_angle6_high);
//        ArmAngle6.setText("" + arm_angle6);

//        WaterFlowLow.setText(water_flow_low);
//        WaterFlowHigh.setText(water_flow_high);
        WaterFlow.setText("" + water_flow);
//
//        WindSpeedLow.setText(wind_speed_low);
//        WindSpeedHigh.setText(wind_speed_high);
//        WindSpeed.setText("" + wind_speed);
//        CacheData.setMsg_info("==========displayMultipleState=======2=======收到数据======>" ,0);

//        cameraTime_flag++;
//
//        if ((brightness&0x8) == 0x0 && cameraFlag7 == 0 && cameraTime_flag>6){
//            CacheData.setMsg_info("===========cameraIndex = 7;=============",1);
//            cameraFlag7 = 1;
//            cameraFlag5 = 0;
//            CameraInterface.getInstance().doStopCamera();
//            cameraView.setVisibility(View.VISIBLE);
//            cameraIndex = 7;
//            OpenCameraInfo();
//        }else if ((brightness&0x8) == 0x8 && cameraFlag5 == 0 && cameraTime_flag>6){
//            CacheData.setMsg_info("===========cameraIndex = 5;=============",1);
//            cameraFlag7 = 0;
//            cameraFlag5 = 1;
//            CameraInterface.getInstance().doStopCamera();
//            cameraView.setVisibility(View.VISIBLE);
//            cameraIndex = 5;
//            OpenCameraInfo();
//        }
//
//        if(cameraTime_flag > 10000){
//            cameraTime_flag = 100;
//        }
//        CacheData.setMsg_info("==========displayMultipleState=======3=======收到数据======>" ,0);

        signalStrength.setText(wireless_link_status + "-" + signal_strength);

        if (IConstant.WIRELESS_LINK_0.equals(wireless_link_status)){
            signalStrengthLayout.setVisibility(FrameLayout.VISIBLE);
            signalStrengthLayout1.setVisibility(FrameLayout.GONE);
            noSignal.setVisibility(LinearLayout.VISIBLE);
            haveSignal.setVisibility(LinearLayout.GONE);
            signalStrength.setVisibility(View.GONE);
        }else if (IConstant.WIRELESS_LINK_1.equals(wireless_link_status)){
            signalStrengthLayout.setVisibility(FrameLayout.VISIBLE);
            signalStrengthLayout1.setVisibility(FrameLayout.GONE);
            noSignal.setVisibility(LinearLayout.GONE);
            haveSignal.setVisibility(LinearLayout.VISIBLE);
            signalStrength.setVisibility(View.VISIBLE);
        } else if (IConstant.WIRELESS_LINK_2.equals(wireless_link_status)){
            signalStrengthLayout.setVisibility(FrameLayout.GONE);
            signalStrengthLayout1.setVisibility(FrameLayout.VISIBLE);
            noSignal.setVisibility(LinearLayout.GONE);
            haveSignal.setVisibility(LinearLayout.GONE);
            signalStrength.setVisibility(View.GONE);
        }



//        if (IConstant.ARM_SPREAD_1.equals(arm_spread_status) && IConstant.ARM_SHRINK_0.equals(arm_shrink_status)){
//            ArmSpreadStatus.setText(getString(R.string.arm_spread));
//        }else if (IConstant.ARM_SPREAD_0.equals(arm_spread_status) && IConstant.ARM_SHRINK_1.equals(arm_shrink_status)){
//            ArmSpreadStatus.setText(getString(R.string.arm_shrink));
//        }else {
//            ArmSpreadStatus.setText(getString(R.string.arm_none));
//        }
//        CacheData.setMsg_info("==========displayMultipleState=======4=======收到数据======>" ,0);
        if (IConstant.Turret_Hit_ok.equals(turret_hit_status)){
            turretHitok.setVisibility(View.VISIBLE);
            turretHitfail.setVisibility(View.GONE);
        }else if (IConstant.Turret_Hit_fail.equals(turret_hit_status)){
            turretHitok.setVisibility(View.GONE);
            turretHitfail.setVisibility(View.VISIBLE);
        }

        if (IConstant.Urgent_Stop_ok.equals(urgent_stop)){
            urgentStopok.setVisibility(View.VISIBLE);
            urgentStopfail.setVisibility(View.GONE);
        }else if (IConstant.Urgent_Stop_fail.equals(urgent_stop)){
            urgentStopok.setVisibility(View.GONE);
            urgentStopfail.setVisibility(View.VISIBLE);
        }

        if (IConstant.Water_Pump_ok.equals(water_pump_status)){
            waterPumpok.setVisibility(View.VISIBLE);
            waterPumpfail.setVisibility(View.GONE);
        }else if (IConstant.Water_Pump_fail.equals(water_pump_status)){
            waterPumpok.setVisibility(View.GONE);
            waterPumpfail.setVisibility(View.VISIBLE);
        }

//        CacheData.setMsg_info("==========displayMultipleState=======5=======收到数据======>" ,0);

//        if (IConstant.FOAM_LEVEL_0000.equals(foam_level)){
//            formLevel1.setVisibility(View.INVISIBLE);
//            formLevel2.setVisibility(View.INVISIBLE);
//            formLevel3.setVisibility(View.INVISIBLE);
//            formLevel4.setVisibility(View.INVISIBLE);
//            formLevel5.setVisibility(View.INVISIBLE);
//            formLevel6.setVisibility(View.INVISIBLE);
//            formLevel7.setVisibility(View.INVISIBLE);
//            formLevel8.setVisibility(View.INVISIBLE);
//        }else if(IConstant.FOAM_LEVEL_0001.equals(foam_level)){
//            formLevel1.setVisibility(View.INVISIBLE);
//            formLevel2.setVisibility(View.INVISIBLE);
//            formLevel3.setVisibility(View.INVISIBLE);
//            formLevel4.setVisibility(View.INVISIBLE);
//            formLevel5.setVisibility(View.INVISIBLE);
//            formLevel6.setVisibility(View.INVISIBLE);
//            formLevel7.setVisibility(View.INVISIBLE);
//            formLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.FOAM_LEVEL_0010.equals(foam_level)){
//            formLevel1.setVisibility(View.INVISIBLE);
//            formLevel2.setVisibility(View.INVISIBLE);
//            formLevel3.setVisibility(View.INVISIBLE);
//            formLevel4.setVisibility(View.INVISIBLE);
//            formLevel5.setVisibility(View.INVISIBLE);
//            formLevel6.setVisibility(View.INVISIBLE);
//            formLevel7.setVisibility(View.VISIBLE);
//            formLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.FOAM_LEVEL_0011.equals(foam_level)){
//            formLevel1.setVisibility(View.INVISIBLE);
//            formLevel2.setVisibility(View.INVISIBLE);
//            formLevel3.setVisibility(View.INVISIBLE);
//            formLevel4.setVisibility(View.INVISIBLE);
//            formLevel5.setVisibility(View.INVISIBLE);
//            formLevel6.setVisibility(View.VISIBLE);
//            formLevel7.setVisibility(View.VISIBLE);
//            formLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.FOAM_LEVEL_0100.equals(foam_level)){
//            formLevel1.setVisibility(View.INVISIBLE);
//            formLevel2.setVisibility(View.INVISIBLE);
//            formLevel3.setVisibility(View.INVISIBLE);
//            formLevel4.setVisibility(View.INVISIBLE);
//            formLevel5.setVisibility(View.VISIBLE);
//            formLevel6.setVisibility(View.VISIBLE);
//            formLevel7.setVisibility(View.VISIBLE);
//            formLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.FOAM_LEVEL_0101.equals(foam_level)){
//            formLevel1.setVisibility(View.INVISIBLE);
//            formLevel2.setVisibility(View.INVISIBLE);
//            formLevel3.setVisibility(View.INVISIBLE);
//            formLevel4.setVisibility(View.VISIBLE);
//            formLevel5.setVisibility(View.VISIBLE);
//            formLevel6.setVisibility(View.VISIBLE);
//            formLevel7.setVisibility(View.VISIBLE);
//            formLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.FOAM_LEVEL_0110.equals(foam_level)){
//            formLevel1.setVisibility(View.INVISIBLE);
//            formLevel2.setVisibility(View.INVISIBLE);
//            formLevel3.setVisibility(View.VISIBLE);
//            formLevel4.setVisibility(View.VISIBLE);
//            formLevel5.setVisibility(View.VISIBLE);
//            formLevel6.setVisibility(View.VISIBLE);
//            formLevel7.setVisibility(View.VISIBLE);
//            formLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.FOAM_LEVEL_0111.equals(foam_level)){
//            formLevel1.setVisibility(View.INVISIBLE);
//            formLevel2.setVisibility(View.VISIBLE);
//            formLevel3.setVisibility(View.VISIBLE);
//            formLevel4.setVisibility(View.VISIBLE);
//            formLevel5.setVisibility(View.VISIBLE);
//            formLevel6.setVisibility(View.VISIBLE);
//            formLevel7.setVisibility(View.VISIBLE);
//            formLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.FOAM_LEVEL_1000.equals(foam_level)){
//            formLevel1.setVisibility(View.VISIBLE);
//            formLevel2.setVisibility(View.VISIBLE);
//            formLevel3.setVisibility(View.VISIBLE);
//            formLevel4.setVisibility(View.VISIBLE);
//            formLevel5.setVisibility(View.VISIBLE);
//            formLevel6.setVisibility(View.VISIBLE);
//            formLevel7.setVisibility(View.VISIBLE);
//            formLevel8.setVisibility(View.VISIBLE);
//        }

//        if (IConstant.WATER_LEVEL_0000.equals(water_level)){
//            waterLevel1.setVisibility(View.INVISIBLE);
//            waterLevel2.setVisibility(View.INVISIBLE);
//            waterLevel3.setVisibility(View.INVISIBLE);
//            waterLevel4.setVisibility(View.INVISIBLE);
//            waterLevel5.setVisibility(View.INVISIBLE);
//            waterLevel6.setVisibility(View.INVISIBLE);
//            waterLevel7.setVisibility(View.INVISIBLE);
//            waterLevel8.setVisibility(View.INVISIBLE);
//        }else if(IConstant.WATER_LEVEL_0001.equals(water_level)){
//            waterLevel1.setVisibility(View.INVISIBLE);
//            waterLevel2.setVisibility(View.INVISIBLE);
//            waterLevel3.setVisibility(View.INVISIBLE);
//            waterLevel4.setVisibility(View.INVISIBLE);
//            waterLevel5.setVisibility(View.INVISIBLE);
//            waterLevel6.setVisibility(View.INVISIBLE);
//            waterLevel7.setVisibility(View.INVISIBLE);
//            waterLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.WATER_LEVEL_0010.equals(water_level)){
//            waterLevel1.setVisibility(View.INVISIBLE);
//            waterLevel2.setVisibility(View.INVISIBLE);
//            waterLevel3.setVisibility(View.INVISIBLE);
//            waterLevel4.setVisibility(View.INVISIBLE);
//            waterLevel5.setVisibility(View.INVISIBLE);
//            waterLevel6.setVisibility(View.INVISIBLE);
//            waterLevel7.setVisibility(View.VISIBLE);
//            waterLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.WATER_LEVEL_0011.equals(water_level)){
//            waterLevel1.setVisibility(View.INVISIBLE);
//            waterLevel2.setVisibility(View.INVISIBLE);
//            waterLevel3.setVisibility(View.INVISIBLE);
//            waterLevel4.setVisibility(View.INVISIBLE);
//            waterLevel5.setVisibility(View.INVISIBLE);
//            waterLevel6.setVisibility(View.VISIBLE);
//            waterLevel7.setVisibility(View.VISIBLE);
//            waterLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.WATER_LEVEL_0100.equals(water_level)){
//            waterLevel1.setVisibility(View.INVISIBLE);
//            waterLevel2.setVisibility(View.INVISIBLE);
//            waterLevel3.setVisibility(View.INVISIBLE);
//            waterLevel4.setVisibility(View.INVISIBLE);
//            waterLevel5.setVisibility(View.VISIBLE);
//            waterLevel6.setVisibility(View.VISIBLE);
//            waterLevel7.setVisibility(View.VISIBLE);
//            waterLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.WATER_LEVEL_0101.equals(water_level)){
//            waterLevel1.setVisibility(View.INVISIBLE);
//            waterLevel2.setVisibility(View.INVISIBLE);
//            waterLevel3.setVisibility(View.INVISIBLE);
//            waterLevel4.setVisibility(View.VISIBLE);
//            waterLevel5.setVisibility(View.VISIBLE);
//            waterLevel6.setVisibility(View.VISIBLE);
//            waterLevel7.setVisibility(View.VISIBLE);
//            waterLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.WATER_LEVEL_0110.equals(water_level)){
//            waterLevel1.setVisibility(View.INVISIBLE);
//            waterLevel2.setVisibility(View.INVISIBLE);
//            waterLevel3.setVisibility(View.VISIBLE);
//            waterLevel4.setVisibility(View.VISIBLE);
//            waterLevel5.setVisibility(View.VISIBLE);
//            waterLevel6.setVisibility(View.VISIBLE);
//            waterLevel7.setVisibility(View.VISIBLE);
//            waterLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.WATER_LEVEL_0111.equals(water_level)){
//            waterLevel1.setVisibility(View.INVISIBLE);
//            waterLevel2.setVisibility(View.VISIBLE);
//            waterLevel3.setVisibility(View.VISIBLE);
//            waterLevel4.setVisibility(View.VISIBLE);
//            waterLevel5.setVisibility(View.VISIBLE);
//            waterLevel6.setVisibility(View.VISIBLE);
//            waterLevel7.setVisibility(View.VISIBLE);
//            waterLevel8.setVisibility(View.VISIBLE);
//        }else if(IConstant.WATER_LEVEL_1000.equals(water_level)){
//            waterLevel1.setVisibility(View.VISIBLE);
//            waterLevel2.setVisibility(View.VISIBLE);
//            waterLevel3.setVisibility(View.VISIBLE);
//            waterLevel4.setVisibility(View.VISIBLE);
//            waterLevel5.setVisibility(View.VISIBLE);
//            waterLevel6.setVisibility(View.VISIBLE);
//            waterLevel7.setVisibility(View.VISIBLE);
//            waterLevel8.setVisibility(View.VISIBLE);
//        }

//        if (IConstant.REMOTE_CTRL_STATUS_2.equals(remote_ctrl_status)) {
//            RemoteCtrlStatus.setText(getString(R.string.scram));
//        } else if (IConstant.REMOTE_CTRL_STATUS_1.equals(remote_ctrl_status)) {
//            RemoteCtrlStatus.setText(getString(R.string.started));
//        } else if (IConstant.REMOTE_CTRL_STATUS_0.equals(remote_ctrl_status)) {
//            RemoteCtrlStatus.setText(getString(R.string.not_started));
//        }
        if ((remote_ctrl_status & 0x2) == 0x2) {
            RemoteCtrlStatus.setText(getString(R.string.scram));
        } else if((remote_ctrl_status & 0x1) == 0x1) {
            RemoteCtrlStatus.setText(getString(R.string.started));
        } else if ((remote_ctrl_status & 0x1) == 0x0) {
            RemoteCtrlStatus.setText(getString(R.string.not_started));
        }

//        CacheData.setMsg_info("==========displayMultipleState=======6=======收到数据======>" ,0);
        if (IConstant.BATTARY_STATUS_0.equals(battery_electricity)) {
            lowBattery.setVisibility(LinearLayout.VISIBLE);
            haveBattery.setVisibility(LinearLayout.GONE);
        } else if (IConstant.BATTARY_STATUS_1.equals(battery_electricity)) {
            haveBattery.setVisibility(LinearLayout.VISIBLE);
            lowBattery.setVisibility(LinearLayout.GONE);
            Battery1.setVisibility(View.VISIBLE);
            Battery2.setVisibility(View.INVISIBLE);
            Battery3.setVisibility(View.INVISIBLE);
            Battery4.setVisibility(View.INVISIBLE);
        } else if (IConstant.BATTARY_STATUS_2.equals(battery_electricity)) {
            haveBattery.setVisibility(LinearLayout.VISIBLE);
            lowBattery.setVisibility(LinearLayout.GONE);
            Battery1.setVisibility(View.VISIBLE);
            Battery2.setVisibility(View.VISIBLE);
            Battery3.setVisibility(View.INVISIBLE);
            Battery4.setVisibility(View.INVISIBLE);
        } else if (IConstant.BATTARY_STATUS_3.equals(battery_electricity)) {
            haveBattery.setVisibility(LinearLayout.VISIBLE);
            lowBattery.setVisibility(LinearLayout.GONE);
            Battery1.setVisibility(View.VISIBLE);
            Battery2.setVisibility(View.VISIBLE);
            Battery3.setVisibility(View.VISIBLE);
            Battery4.setVisibility(View.INVISIBLE);
        } else if (IConstant.BATTARY_STATUS_4.equals(battery_electricity)) {
            haveBattery.setVisibility(LinearLayout.VISIBLE);
            lowBattery.setVisibility(LinearLayout.GONE);
            Battery1.setVisibility(View.VISIBLE);
            Battery2.setVisibility(View.VISIBLE);
            Battery3.setVisibility(View.VISIBLE);
            Battery4.setVisibility(View.VISIBLE);
        }

//        if (IConstant.WATER_PUMP_START.equals(water_pump_status)){
//            WaterPumpStatus.setText(getString(R.string.started));
//        } else if (IConstant.WATER_PUMP_STOP.equals(water_pump_status)) {
//            WaterPumpStatus.setText(getString(R.string.stop));
//        }

//        CacheData.setMsg_info("==========displayMultipleState=======7=======收到数据======>" ,0);

        if (IConstant.ERR_CODE_0.equals(err_code)){
            ErrCode.setText(getString(R.string.err_code_0));
        } else if (IConstant.ERR_CODE_1.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_1));
        } else if (IConstant.ERR_CODE_2.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_2));
        } else if (IConstant.ERR_CODE_3.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_3));
        } else if (IConstant.ERR_CODE_4.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_4));
        } else if (IConstant.ERR_CODE_5.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_5));
        } else if (IConstant.ERR_CODE_6.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_6));
        } else if (IConstant.ERR_CODE_7.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_7));
        } else if (IConstant.ERR_CODE_8.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_8));
        } else if (IConstant.ERR_CODE_9.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_9));
        } else if (IConstant.ERR_CODE_10.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_10));
        } else if (IConstant.ERR_CODE_11.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_11));
        } else if (IConstant.ERR_CODE_12.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_12));
        } else if (IConstant.ERR_CODE_13.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_13));
        } else if (IConstant.ERR_CODE_14.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_14));
        } else if (IConstant.ERR_CODE_15.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_15));
        } else if (IConstant.ERR_CODE_16.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_16));
        } else if (IConstant.ERR_CODE_17.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_17));
        } else if (IConstant.ERR_CODE_18.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_18));
        } else if (IConstant.ERR_CODE_19.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_19));
        } else if (IConstant.ERR_CODE_20.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_20));
        } else if (IConstant.ERR_CODE_21.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_21));
        } else if (IConstant.ERR_CODE_22.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_22));
        } else if (IConstant.ERR_CODE_23.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_23));
        } else if (IConstant.ERR_CODE_24.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_24));
        } else if (IConstant.ERR_CODE_25.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_25));
        } else if (IConstant.ERR_CODE_26.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_26));
        } else if (IConstant.ERR_CODE_27.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_27));
        } else if (IConstant.ERR_CODE_28.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_28));
        } else if (IConstant.ERR_CODE_29.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_29));
        } else if (IConstant.ERR_CODE_30.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_30));
        } else if (IConstant.ERR_CODE_31.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_31));
        } else if (IConstant.ERR_CODE_32.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_32));
        } else if (IConstant.ERR_CODE_33.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_33));
        } else if (IConstant.ERR_CODE_34.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_34));
        } else if (IConstant.ERR_CODE_35.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_35));
        } else if (IConstant.ERR_CODE_36.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_36));
        } else if (IConstant.ERR_CODE_37.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_37));
        } else if (IConstant.ERR_CODE_38.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_38));
        } else if (IConstant.ERR_CODE_39.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_39));
        } else if (IConstant.ERR_CODE_40.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_40));
        } else if (IConstant.ERR_CODE_41.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_41));
        } else if (IConstant.ERR_CODE_42.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_42));
        } else if (IConstant.ERR_CODE_43.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_43));
        } else if (IConstant.ERR_CODE_44.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_44));
        } else if (IConstant.ERR_CODE_45.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_45));
        } else if (IConstant.ERR_CODE_46.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_46));
        } else if (IConstant.ERR_CODE_47.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_47));
        } else if (IConstant.ERR_CODE_48.equals(err_code)) {
            ErrCode.setText(getString(R.string.err_code_48));
        }

        if(signal_strength > 127){
            signal_strength = 256 - signal_strength;
//            CacheData.setMsg_info("==========signal_strength=====+++====== > 127======>"+signal_strength ,0);
            signalStrength.setText(("-" + signal_strength));
        }else{
//            CacheData.setMsg_info("==========signal_strength=====+++====== <= 127======>"+signal_strength ,0);
            signalStrength.setText("" + signal_strength);
        }

        brightNess(brightness);
        changePage(brightness);


//        CacheData.setMsg_info("==========displayMultipleState=======8=======收到数据======>" ,0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }



    /**
     *
     * 打开摄像头进行视频监控或回看视频
     * @param
     *
     */
    //   private void OpenCameraInfo(){
    public void OpenCameraInfo(){
        try{
            if (!CameraInterface.getInstance().CameraState()) {
                // check Android 6 permission
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    Thread openThread = new Thread() {
                        @Override
                        public void run() {
                            //CameraInterface.getInstance().doStopCamera();
                            CameraInterface.getInstance().doOpenCamera(MainActivity.this,cameraIndex);
                        }
                    };
                    openThread.start();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA}, IConstant.TAKE_PHOTO_REQUEST_CODE); //1 can be another integer
                }

            }

        }catch (Exception e){
            LogUtil.d("cameraHasOpened","没有连接摄像头。");
            LogUtil.e(TAG,"===================OpenCameraInfo===========================>"+e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try{
            if (requestCode == IConstant.TAKE_PHOTO_REQUEST_CODE) {
                for (int index = 0; index < permissions.length; index++) {
                    switch (permissions[index]) {
                        case IConstant.PERMISSION_CAMERA:
                            if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                                Thread openThread = new Thread() {
                                    @Override
                                    public void run() {
                                        CameraInterface.getInstance().doStopCamera();
                                        CameraInterface.getInstance().doOpenCamera(MainActivity.this,cameraIndex);
                                    }
                                };
                                openThread.start();
                            }
                            break;
                    }
                }
            }
        }catch (Exception e){
            LogUtil.e(TAG,"===================onRequestPermissionsResult===========================>"+e.getMessage());
            e.printStackTrace();
        }


    }


    @Override
    public void cameraHasOpened() {
        try{

            float previewRate =0.1f;
            try {
                Thread.sleep(200);
            }catch (InterruptedException e){

            }

            SurfaceHolder holder = cameraView.getSurfaceHolder();
            CameraInterface.getInstance().doStartPreview(holder, previewRate);
        }catch (Exception e){
            LogUtil.e(TAG,"===================cameraHasOpened===========================>"+e.getMessage());
            e.printStackTrace();
        }

    }

    private void  brightNess(int flag){
        int val;
//        if((flag&0x4) == 0x4) {
////            CacheData.setMsg_info("===========flag============="+flag,1);
//            SharedPreferences share = getSharedPreferences("Acitivity", Context.MODE_WORLD_READABLE);
//            val = share.getInt("value", 0);
//            if (val >= 250) {
//                val = 2;
//            } else {
//                val = val + 50;
//            }
//
//            SharedPreferences.Editor editor = share.edit();//获取编辑器
//            editor.putInt("value", val);
//            editor.commit();//提交修改
//            SystemBrightManager.setBrightness(this, val);
//        }


        if((flag&0x4) == 0x4) {
            if(change_bright_old == 0){
                SharedPreferences share = getSharedPreferences("Acitivity", Context.MODE_WORLD_READABLE);
                val = share.getInt("value", 100);
                if (val >= 250) {
                    val = 2;
                }else if(val >= 200){
                    val = 250;
                }else{
                    val = val + 50;
                }
                // CacheData.setMsg_info("=================brightNess==========tvBright=============="+"" + (val*100)/250 + "%",1);
               // tvBright.setText("" + (val*100)/250 + "%");
                SharedPreferences.Editor editor = share.edit();//获取编辑器
                editor.putInt("value", val);
                editor.commit();//提交修改
                SystemBrightManager.setBrightness(this, val);
            }
            change_bright_old = 1;
        }else{
            change_bright_old = 0;
        }


    }

    private TimerTask getTimerTask_loc(){

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try{
                    if(CacheData.getThread_flag()==12){
                        serialhelp.close();
                        initSerial();
                        CacheData.setMsg_info("============================getTimerTask_loc=====================initSerial==",0);
                    }else{
                        CacheData.setThread_flag(CacheData.getThread_flag() + 1);
                    }

                }catch (Exception e){
                    LogUtil.e(TAG,"===================TimerTask===========================>"+e.getMessage());
                    e.printStackTrace();
                }

            }
        };

        return task;

    }


}

