package cn.com.sany.symc.zg.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
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

import android.support.v4.content.ContextCompat;

/**
 * 首页应用
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity.class";

    private byte temp;
    private byte[] temp_1 = new byte[1];
    private byte[] temp_3 = new byte[3];
    private byte[] temp_2 = new byte[2];
    private byte[] temp_4 = new byte[4];

    private int tmp_0;
    private int tmp_1;
    private int tmp_2;
    private int tmp_3;
    private int tmp_4;
    private int tmp_5;
    private int tmp_6;
    private int tmp_7;
    private int bj_down;

    private TextView signalStrength;
    private TextView BatteryElectricity;
    private FrameLayout signalStrengthLayout;
    private FrameLayout signalStrengthLayout1;
    private LinearLayout waterLLayout;
    private LinearLayout dwLLayout;
    private TextView WirelessLinkStatus;
    private TextView RemoteCtrlStatus;
    private TextView b45_tv;



    private LinearLayout noSignal;
    private LinearLayout haveSignal;

    private LinearLayout main_test_cab;    //测试界面
    private LinearLayout mainLLayout;    //内容显示主界面

    private LinearLayout driverBoLayout;    //行驶界面
    private LinearLayout driverRLayout;    //火场监控界面
    private LinearLayout workBoLayout;    //行驶界面
    private LinearLayout workRLayout;    //火场监控界面 （工作）
    private LinearLayout statusLayout22;
    private static int change_page_old = 0;  //保存之前一次上升沿  0x01从0到1切换

    private static int change_bright_old = 0;  //保存之前一次亮度上升沿   0x04从0到1切换

    private LinearLayout lowBattery;
    private LinearLayout haveBattery;
    private TextView Battery1;
    private TextView Battery2;
    private TextView Battery3;
    private TextView Battery4;

    private int page = 2;  //0表示驾驶界面， 1：表示测试诊断界面  2：表示作业显示界面

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
    private LinearLayout b80_1_lay;  // 行驶模式
    private LinearLayout b81_1_lay;  // 取力模式
    private LinearLayout b82_1_lay;  // 驻车模式

   // private LinearLayout b86_1_lay;  //转台角度符合
    private LinearLayout b87_1_lay;  //发动机启
    private String turret_angle_negtive = "";


    private ImageView b90_up_iv;
    private ImageView b90_down_iv;
    private ImageView b9012_up_iv;
    private ImageView b9012_down_iv;

    private ImageView b32_iv;

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

    private ImageView ivStop1111;
    private TextView  tvStop1111;

    private TextView TurretAngle;
    private TextView EngineSpeed;
    private TextView HydraulicPress;
    private TextView PumpVentPress;
    private TextView ErrCode;
    private TextView ErrCode1;

    private TextView WaterPumpStatus;
    private LinearLayout turretHitok;

    private LinearLayout zttp_lay;  //
    private LinearLayout ztsh_lay;  //

    private TextView ArmAngle1;
    private TextView ArmAngle2;
    private TextView ArmAngle3;

    private TextView WaterFlow;
    private TextView oilPostion;
    private TextView oilPos;

    private TextView carSpeed;
    private TextView oil_tv;
    private TextView brake_tv;
    private TextView directionArml;

    private ImageView b370_iv;

    private ImageView b37_12_up;
    private ImageView b37_12_mid;
    private ImageView b37_12_down;

    private ImageView b37_34_up;
    private ImageView b37_34_mid;
    private ImageView b37_34_down;

    private ImageView b37_56_up;
    private ImageView b37_56_mid;
    private ImageView b37_56_down;

    private ImageView b377_iv;

    private ImageView b38_14_up;
    private ImageView b38_14_mid;
    private ImageView b38_14_down;

    private ImageView b385_iv;
    private ImageView b387_up_iv;
    private ImageView b387_down_iv;

    private ImageView b39_01_up;
    private ImageView b39_01_mid;
    private ImageView b39_01_down;

    private ImageView b39_45_up;
    private ImageView b39_45_mid;
    private ImageView b39_45_down;

    private ImageView b393_iv;

    private ImageView b40_0_up;
    private ImageView b40_0_mid;
    private ImageView b40_0_down;

    private ImageView b40_12_up;
    private ImageView b40_12_mid;
    private ImageView b40_12_down;

    private ImageView b40_34_up;
   // private ImageView b40_34_mid;
    private ImageView b40_34_down;

    private ImageView b40_567_up;
    private ImageView b40_567_mid;
    private ImageView b40_567_down;

    private LinearLayout b40_N_up;
    private LinearLayout b40_D_up;
    private LinearLayout b40_R_up;

    private ImageView b41_01_up;
    private ImageView b41_01_mid;
    private ImageView b41_01_down;
    private ImageView b41_2_up;
    private ImageView b41_2_mid;
    private ImageView b41_2_down;
    private ImageView b41_3_up;
    private ImageView b41_3_down;
    private ImageView b41_45_up;
    private ImageView b41_45_mid;
    private ImageView b41_45_down;
    private ImageView b416_iv;
    private ImageView b417_iv;

    private ImageView ivDown11Up;
    private ImageView ivDown11Down;
    private TextView tvDown11;

    private ImageView ivDown22Up;
    private ImageView ivDown22Down;
    private TextView tvDown22;

    private ImageView ivDown33Up;
    private ImageView ivDown33Down;
    private TextView tvDown33;

    private TextView b925_iv;


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
    private String question_old = "";
    //tanghui add en

    // 自动模式
    private LinearLayout autoModeLL;
    // 臂架状态  自动展  自动收
    private TextView tv_boom;

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

        setContentView(R.layout.activity_main_bak);
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

        b80_1_lay =  (LinearLayout) findViewById(R.id.b80_1_lay);  // 行驶模式
        b81_1_lay =  (LinearLayout) findViewById(R.id.b81_1_lay);  // 取力模式
        b82_1_lay =  (LinearLayout) findViewById(R.id.b82_1_lay);  // 驻车模式

      //  b86_1_lay =  (LinearLayout) findViewById(R.id.b86_1_lay);  //转台角度符合
        b87_1_lay =  (LinearLayout) findViewById(R.id.b87_1_lay);  //发动机启

        b90_up_iv =  (ImageView) findViewById(R.id.b90_up_iv);
        b90_down_iv =  (ImageView) findViewById(R.id.b90_down_iv);
        b9012_up_iv =  (ImageView) findViewById(R.id.b9012_up_iv);
        b9012_up_iv.setOnClickListener(this);
        b9012_down_iv =  (ImageView) findViewById(R.id.b9012_down_iv);

        formLevel1 = (TextView) findViewById(R.id.tvFormlevel1);
        formLevel2 = (TextView) findViewById(R.id.tvFormlevel2);
        formLevel3 = (TextView) findViewById(R.id.tvFormlevel3);
        formLevel4 = (TextView) findViewById(R.id.tvFormlevel4);
        formLevel5 = (TextView) findViewById(R.id.tvFormlevel5);
        formLevel6 = (TextView) findViewById(R.id.tvFormlevel6);
        formLevel7 = (TextView) findViewById(R.id.tvFormlevel7);
        formLevel8 = (TextView) findViewById(R.id.tvFormlevel8);

        waterLevel1 = (TextView) findViewById(R.id.tvWaterlevel1);
        waterLevel2 = (TextView) findViewById(R.id.tvWaterlevel2);
        waterLevel3 = (TextView) findViewById(R.id.tvWaterlevel3);
        waterLevel4 = (TextView) findViewById(R.id.tvWaterlevel4);
        waterLevel5 = (TextView) findViewById(R.id.tvWaterlevel5);
        waterLevel6 = (TextView) findViewById(R.id.tvWaterlevel6);
        waterLevel7 = (TextView) findViewById(R.id.tvWaterlevel7);
        waterLevel8 = (TextView) findViewById(R.id.tvWaterlevel8);

        ivStop1111 = (ImageView)findViewById(R.id.ivStop1111);
        tvStop1111 = (TextView) findViewById(R.id.tvStop1111);
        ivStop1111.setOnClickListener(this);

        TurretAngle = (TextView) findViewById(R.id.TurretAngle);
        EngineSpeed = (TextView) findViewById(R.id.EngineSpeed);
        HydraulicPress = (TextView) findViewById(R.id.HydraulicPress);
        PumpVentPress = (TextView) findViewById(R.id.PumpVentPress);
        ErrCode = (TextView) findViewById(R.id.ErrCode);
        ErrCode.setOnClickListener(this);
        ErrCode1 = (TextView) findViewById(R.id.ErrCode1);
        ErrCode1.setOnClickListener(this);

        ArmAngle1 = (TextView) findViewById(R.id.ArmAngle1);
        ArmAngle2 = (TextView) findViewById(R.id.ArmAngle2);
        ArmAngle3 = (TextView) findViewById(R.id.ArmAngle3);
        WaterFlow = (TextView) findViewById(R.id.WaterFlow);
        oilPostion = (TextView) findViewById(R.id.oilPostion);
        oilPos = (TextView) findViewById(R.id.oilPos);
        WaterPumpStatus = (TextView) findViewById(R.id.WaterPumpStatus);

        turretHitok = (LinearLayout) findViewById(R.id.turretHitok);  // 转塔对中
        zttp_lay = (LinearLayout) findViewById(R.id.zttp_lay);  // 支腿调平
        ztsh_lay = (LinearLayout) findViewById(R.id.ztsh_lay);  // 支腿收到位

        carSpeed = (TextView) findViewById(R.id.carSpeed);  //车速
        oil_tv = (TextView) findViewById(R.id.oil_tv);      //油门
        brake_tv = (TextView) findViewById(R.id.brake_tv);  //刹车
        directionArml = (TextView) findViewById(R.id.directionArml);  //方向盘转角

        waterLLayout = (LinearLayout) findViewById(R.id.waterLLayout);
        waterLLayout.setOnClickListener(this);

        dwLLayout = (LinearLayout) findViewById(R.id.dwLLayout);
        dwLLayout.setOnClickListener(this);

        autoModeLL = (LinearLayout) findViewById(R.id.autoModeLL);   //刹车
        tv_boom = (TextView) findViewById(R.id.tv_boom);             //方向盘转角

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

        main_test_cab = (LinearLayout) findViewById(R.id.main_test_cab);
        mainLLayout = (LinearLayout) findViewById(R.id.mainLLayout);

        driverBoLayout = (LinearLayout) findViewById(R.id.driverBoLayout);

        driverRLayout = (LinearLayout) findViewById(R.id.driverRLayout);
        workBoLayout = (LinearLayout) findViewById(R.id.workBoLayout);
        workRLayout = (LinearLayout) findViewById(R.id.workRLayout);

        statusLayout22 = (LinearLayout) findViewById(R.id.statusLayout22);

        b370_iv = (ImageView) findViewById(R.id.b370_iv);

        b32_iv = (ImageView) findViewById(R.id.b32_iv);


        b37_12_up = (ImageView) findViewById(R.id.b37_12_up);
        b37_12_mid = (ImageView) findViewById(R.id.b37_12_mid);
        b37_12_down = (ImageView) findViewById(R.id.b37_12_down);

        b37_34_up = (ImageView) findViewById(R.id.b37_34_up);
        b37_34_mid = (ImageView) findViewById(R.id.b37_34_mid);
        b37_34_down = (ImageView) findViewById(R.id.b37_34_down);

        b37_56_up = (ImageView) findViewById(R.id.b37_56_up);
        b37_56_mid = (ImageView) findViewById(R.id.b37_56_mid);
        b37_56_down = (ImageView) findViewById(R.id.b37_56_down);
        b377_iv = (ImageView) findViewById(R.id.b377_iv);

        b38_14_up = (ImageView) findViewById(R.id.b38_14_up);
        b38_14_mid = (ImageView) findViewById(R.id.b38_14_mid);
        b38_14_down = (ImageView) findViewById(R.id.b38_14_down);

        b385_iv = (ImageView) findViewById(R.id.b385_iv);

        b387_up_iv = (ImageView) findViewById(R.id.b387_up_iv);
        b387_down_iv = (ImageView) findViewById(R.id.b387_down_iv);

        b39_01_up = (ImageView) findViewById(R.id.b39_01_up);
        b39_01_mid = (ImageView) findViewById(R.id.b39_01_mid);
        b39_01_down = (ImageView) findViewById(R.id.b39_01_down);

        b39_45_up = (ImageView) findViewById(R.id.b39_45_up);
        b39_45_mid = (ImageView) findViewById(R.id.b39_45_mid);
        b39_45_down = (ImageView) findViewById(R.id.b39_45_down);
        b393_iv = (ImageView) findViewById(R.id.b393_iv);

        b40_0_up = (ImageView) findViewById(R.id.b40_0_up);
        b40_0_mid = (ImageView) findViewById(R.id.b40_0_mid);
        b40_0_down = (ImageView) findViewById(R.id.b40_0_down);

        b40_12_up = (ImageView) findViewById(R.id.b40_12_up);
        b40_12_mid = (ImageView) findViewById(R.id.b40_12_mid);
        b40_12_down = (ImageView) findViewById(R.id.b40_12_down);

        b40_567_up = (ImageView) findViewById(R.id.b40_567_up);
        b40_567_mid = (ImageView) findViewById(R.id.b40_567_mid);
        b40_567_down = (ImageView) findViewById(R.id.b40_567_down);

        b40_34_up = (ImageView) findViewById(R.id.b40_34_up);
        //b40_34_mid = (ImageView) findViewById(R.id.b40_34_mid);
        b40_34_down = (ImageView) findViewById(R.id.b40_34_down);
        b40_N_up = (LinearLayout) findViewById(R.id.b40_N_up);
        b40_D_up = (LinearLayout) findViewById(R.id.b40_D_up);
        b40_R_up = (LinearLayout) findViewById(R.id.b40_R_up);

        b41_01_up = (ImageView) findViewById(R.id.b41_01_up);
        b41_01_mid = (ImageView) findViewById(R.id.b41_01_mid);
        b41_01_down = (ImageView) findViewById(R.id.b41_01_down);

        b41_2_up = (ImageView) findViewById(R.id.b41_2_up);
        b41_2_mid = (ImageView) findViewById(R.id.b41_2_mid);
        b41_2_down = (ImageView) findViewById(R.id.b41_2_down);

        b41_3_up = (ImageView) findViewById(R.id.b41_3_up);
        b41_3_down = (ImageView) findViewById(R.id.b41_3_down);
        b41_45_up = (ImageView) findViewById(R.id.b41_45_up);

        b41_45_mid = (ImageView) findViewById(R.id.b41_45_mid);
        b41_45_down = (ImageView) findViewById(R.id.b41_45_down);
        b416_iv = (ImageView) findViewById(R.id.b416_iv);
        b417_iv = (ImageView) findViewById(R.id.b417_iv);

        ivDown11Up = (ImageView) findViewById(R.id.ivDown11Up);
        ivDown11Down = (ImageView) findViewById(R.id.ivDown11Down);
        tvDown11 = (TextView) findViewById(R.id.tvDown11);

        ivDown22Up = (ImageView) findViewById(R.id.ivDown22Up);
        ivDown22Down = (ImageView) findViewById(R.id.ivDown22Down);
        tvDown22 = (TextView) findViewById(R.id.tvDown22);

        ivDown33Up = (ImageView) findViewById(R.id.ivDown33Up);
        ivDown33Down = (ImageView) findViewById(R.id.ivDown33Down);
        tvDown33 = (TextView) findViewById(R.id.tvDown33);

        b925_iv = (TextView) findViewById(R.id.b925_iv);
        b925_iv.setOnClickListener(this);

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
                main_test_cab.setVisibility(View.VISIBLE);
            }
        });

        signalStrengthLayout.setOnClickListener(this);
        signalStrengthLayout1.setOnClickListener(this);

        b45_tv = (TextView) findViewById(R.id.b45_tv);

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


    /**
     *
     * 按菜单键切页界面
     * @param flag
     *
     */
    private void changePage(int flag){
        int val;
        if((flag&0x1) == 0x01) {
            if(change_page_old == 0){
              //  CacheData.setMsg_info("===========flag============="+flag,1);
//
                if (page == 0) {  //0表示驾驶界面， 1：表示测试诊断界面  2：表示作业显示界面
                    page = 1;

                    mainLLayout.setVisibility(View.GONE);
                    main_test_cab.setVisibility(View.VISIBLE);

                    driverBoLayout.setVisibility(View.GONE);
                    driverRLayout.setVisibility(View.GONE);

                    workBoLayout.setVisibility(View.GONE);
                    workRLayout.setVisibility(View.GONE);

                }else if(page == 1) {
                    page = 2;

                    mainLLayout.setVisibility(View.VISIBLE);
                    main_test_cab.setVisibility(View.GONE);

                    driverBoLayout.setVisibility(View.VISIBLE);
                    driverRLayout.setVisibility(View.VISIBLE);

                    workBoLayout.setVisibility(View.GONE);
                    workRLayout.setVisibility(View.GONE);


                }else {
                    page = 0;
                    mainLLayout.setVisibility(View.VISIBLE);
                    main_test_cab.setVisibility(View.GONE);


                    driverBoLayout.setVisibility(View.GONE);
                    driverRLayout.setVisibility(View.GONE);

                    workBoLayout.setVisibility(View.VISIBLE);
                    workRLayout.setVisibility(View.VISIBLE);


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
     *
     * 行驶 和 取力
     * @param flag 为 true:显示 行驶界面 ，false 显示 作业界面
     *
     */
    private void changePageMain(boolean flag){ // 为 true:显示 行驶界面 ，false 显示 作业界面
        //0表示驾驶界面， 1：表示测试诊断界面  2：表示作业显示界面
        if(page!=1){  // 不再测试界面的时候，切换
            if(flag) { // 行驶行驶界面
                if(page == 2) {
                    driverBoLayout.setVisibility(View.GONE);
                    driverRLayout.setVisibility(View.GONE);

                    workBoLayout.setVisibility(View.VISIBLE);
                    workRLayout.setVisibility(View.VISIBLE);
                    page = 0;
                }
            }else { // 行驶作业界面
                if(page == 0 ) {  // 行驶界面
                    driverBoLayout.setVisibility(View.VISIBLE);
                    driverRLayout.setVisibility(View.VISIBLE);

                    workBoLayout.setVisibility(View.GONE);
                    workRLayout.setVisibility(View.GONE);
                    page = 2;
                }

            }
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

                //调速器
                case R.id.b925_iv: {
                    //弹出调试信息显示框
                    if (messageShowFragment == null) {
                        messageShowFragment = new MessageShowFragment();
                    }

                    if (!messageShowFragment.isAdded() && !messageShowFragment.isVisible() && !messageShowFragment.isRemoving()) {
                        messageShowFragment.show(getSupportFragmentManager(), "messageLogId");
                    }
                    break;
                }

                case R.id.ivStop1111: {
                    dealPasswordValidate(IConstant.SOURCE_SETTING);
                    break;
                }

                // 换页
                case R.id.b9012_up_iv: {
                    changePage(1);
                    break;
                }

                // 换页
                case R.id.ErrCode: {
                    changePage(1);
                    break;
                }

                // 换页
                case R.id.ErrCode1: {
                    changePage(1);
                    break;
                }

                case R.id.dwLLayout:{
                    dealPasswordValidate(IConstant.SOURCE_SETTING);
                    break;
                }
                case R.id.waterLLayout:{
                    dealPasswordValidate(IConstant.SOURCE_SETTING);
                    break;
                }
//                case R.id.signalStrengthLayout1: {
//
//                    //弹出调试信息显示框
//                    if (messageShowFragment == null) {
//                        messageShowFragment = new MessageShowFragment();
//                    }
//
//                    if (!messageShowFragment.isAdded() && !messageShowFragment.isVisible() && !messageShowFragment.isRemoving()) {
//                        messageShowFragment.show(getSupportFragmentManager(), "messageLogId");
//                    }
//                    //进入文件系统目录
////                    dealPasswordValidate(IConstant.SOURCE_SETTING);
//
//                    break;
//
//                }


            }

        } catch (Exception e) {

        }
    }




    private void  brightNess(int flag){
        int val;

       // CacheData.setMsg_info("=================brightNess==========true=======flag======="+"" + ((flag & 0x4) == 0x4),1);
        if((flag&0x4) == 0x4) {
          //  CacheData.setMsg_info("=================brightNess==========change_bright_old == 0==========="+"" + (change_bright_old == 0),1);
            if(change_bright_old == 0){
              //  CacheData.setMsg_info("=================brightNess========1111====="+"" + change_bright_old,1);
                SharedPreferences share = getSharedPreferences("aaa", Context.MODE_PRIVATE);
                val = share.getInt("value", 200);
              //  CacheData.setMsg_info("=================brightNess========2222====="+"" + (change_bright_old == 0),1);
                if (val >= 250) {
                    val = 2;
                }else if(val >= 200){
                    val = 250;
                }else{
                    val = val + 50;
                }
              //  CacheData.setMsg_info("=================brightNess==========3333=============="+"" + val + "%",1);

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
        if(content.length>49){
            temp = content[0];
            tmp_0 = (temp >> 0) & 0x1;
            tmp_1 = (temp >> 1) & 0x1;
            tmp_2 = (temp >> 2) & 0x1;
            tmp_3 = (temp >> 3) & 0x1;
            tmp_4 = (temp >> 4) & 0x1;
            tmp_5 = (temp >> 5) & 0x1;
            tmp_6 = (temp >> 6) & 0x1;
            tmp_7 = (temp >> 7) & 0x1;
            //行驶模式
            if (tmp_0 == 1) {
                b80_1_lay.setBackgroundResource(R.drawable.shape_ring_connect_press);
            } else {
                b80_1_lay.setBackgroundResource(R.drawable.shape_ring_connect_nor);
            }

            //取力模式
            if (tmp_1 == 1) {
                b81_1_lay.setBackgroundResource(R.drawable.shape_ring_connect_press);
            } else {
                b81_1_lay.setBackgroundResource(R.drawable.shape_ring_connect_nor);
            }

            if(tmp_0 == 1){
                changePageMain(true); // 为 true:显示 行驶界面 ，false 显示 作业界面
            }

            // 取力模式不显示作业节目，支腿调平才显示
//            if(tmp_1 == 1){
//                changePageMain(false); // 为 true:显示 行驶界面 ，false 显示 作业界面
//            }

            // 驻车
            if (tmp_2 == 1) {
                b82_1_lay.setBackgroundResource(R.drawable.shape_ring_connect_press);
            } else {
                b82_1_lay.setBackgroundResource(R.drawable.shape_ring_connect_nor);
            }

            //D
            if (tmp_3 == 1) {
                b40_D_up.setBackgroundResource(R.drawable.shape_ring_connect_press);
            } else {
                b40_D_up.setBackgroundResource(R.drawable.shape_ring_connect_nor);
            }

            //N
            if (tmp_4 == 1) {
                b40_N_up.setBackgroundResource(R.drawable.shape_ring_connect_press);
            } else {
                b40_N_up.setBackgroundResource(R.drawable.shape_ring_connect_nor);
            }

            //R
            if (tmp_5 == 1) {
                b40_R_up.setBackgroundResource(R.drawable.shape_ring_connect_press);
            } else {
                b40_R_up.setBackgroundResource(R.drawable.shape_ring_connect_nor);
            }

            //转台角度符号
            turret_angle_negtive = "" + ((temp>>6) & 0x1);

            // 发动机启
            if (tmp_7 == 1) {
                b87_1_lay.setBackgroundResource(R.drawable.shape_ring_connect_press);
            } else {
                b87_1_lay.setBackgroundResource(R.drawable.shape_ring_connect_nor);
            }

            temp = content[1];
            tmp_0 = (temp >> 0) & 0x1;
            tmp_1 = (temp >> 1) & 0x1;
            tmp_2 = (temp >> 2) & 0x1;
            tmp_3 = (temp >> 3) & 0x1;
            tmp_4 = (temp >> 4) & 0x1;
            tmp_5 = (temp >> 5) & 0x1;
            tmp_6 = (temp >> 6) & 0x1;
            tmp_7 = (temp >> 7) & 0x1;

            //自动
            if (tmp_0 == 1) {
                b90_up_iv.setImageResource(R.drawable.ic_22_up_wh);
                b90_down_iv.setImageResource(R.drawable.ic_22_do_red);
                autoModeLL.setBackgroundResource(R.drawable.shape_ring_connect_press);
            } else {
                b90_up_iv.setImageResource(R.drawable.ic_22_up_red);
                b90_down_iv.setImageResource(R.drawable.ic_22_do_wh);
                autoModeLL.setBackgroundResource(R.drawable.shape_ring_connect_nor);
            }

            // 自动展臂    内容界面显示
            if (tmp_1 == 1) {
                tv_boom.setText(getString(R.string.auto_open));
            } else if(tmp_2 == 1){ // 自动收臂
                tv_boom.setText(getString(R.string.auto_close));
            }

            //泡沫罐液位
            String foam_level = ""+((temp >> 6) & 0x1) + ""+((temp >> 5) & 0x1) + ""+((temp >> 4) & 0x1)  + ""+((temp >> 3) & 0x1);
            if (IConstant.FOAM_LEVEL_0000.equals(foam_level)){
                formLevel1.setVisibility(View.INVISIBLE);
                formLevel2.setVisibility(View.INVISIBLE);
                formLevel3.setVisibility(View.INVISIBLE);
                formLevel4.setVisibility(View.INVISIBLE);
                formLevel5.setVisibility(View.INVISIBLE);
                formLevel6.setVisibility(View.INVISIBLE);
                formLevel7.setVisibility(View.INVISIBLE);
                formLevel8.setVisibility(View.INVISIBLE);
            }else if(IConstant.FOAM_LEVEL_0001.equals(foam_level)){
                formLevel1.setVisibility(View.INVISIBLE);
                formLevel2.setVisibility(View.INVISIBLE);
                formLevel3.setVisibility(View.INVISIBLE);
                formLevel4.setVisibility(View.INVISIBLE);
                formLevel5.setVisibility(View.INVISIBLE);
                formLevel6.setVisibility(View.INVISIBLE);
                formLevel7.setVisibility(View.INVISIBLE);
                formLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.FOAM_LEVEL_0010.equals(foam_level)){
                formLevel1.setVisibility(View.INVISIBLE);
                formLevel2.setVisibility(View.INVISIBLE);
                formLevel3.setVisibility(View.INVISIBLE);
                formLevel4.setVisibility(View.INVISIBLE);
                formLevel5.setVisibility(View.INVISIBLE);
                formLevel6.setVisibility(View.INVISIBLE);
                formLevel7.setVisibility(View.VISIBLE);
                formLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.FOAM_LEVEL_0011.equals(foam_level)){
                formLevel1.setVisibility(View.INVISIBLE);
                formLevel2.setVisibility(View.INVISIBLE);
                formLevel3.setVisibility(View.INVISIBLE);
                formLevel4.setVisibility(View.INVISIBLE);
                formLevel5.setVisibility(View.INVISIBLE);
                formLevel6.setVisibility(View.VISIBLE);
                formLevel7.setVisibility(View.VISIBLE);
                formLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.FOAM_LEVEL_0100.equals(foam_level)){
                formLevel1.setVisibility(View.INVISIBLE);
                formLevel2.setVisibility(View.INVISIBLE);
                formLevel3.setVisibility(View.INVISIBLE);
                formLevel4.setVisibility(View.INVISIBLE);
                formLevel5.setVisibility(View.VISIBLE);
                formLevel6.setVisibility(View.VISIBLE);
                formLevel7.setVisibility(View.VISIBLE);
                formLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.FOAM_LEVEL_0101.equals(foam_level)){
                formLevel1.setVisibility(View.INVISIBLE);
                formLevel2.setVisibility(View.INVISIBLE);
                formLevel3.setVisibility(View.INVISIBLE);
                formLevel4.setVisibility(View.VISIBLE);
                formLevel5.setVisibility(View.VISIBLE);
                formLevel6.setVisibility(View.VISIBLE);
                formLevel7.setVisibility(View.VISIBLE);
                formLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.FOAM_LEVEL_0110.equals(foam_level)){
                formLevel1.setVisibility(View.INVISIBLE);
                formLevel2.setVisibility(View.INVISIBLE);
                formLevel3.setVisibility(View.VISIBLE);
                formLevel4.setVisibility(View.VISIBLE);
                formLevel5.setVisibility(View.VISIBLE);
                formLevel6.setVisibility(View.VISIBLE);
                formLevel7.setVisibility(View.VISIBLE);
                formLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.FOAM_LEVEL_0111.equals(foam_level)){
                formLevel1.setVisibility(View.INVISIBLE);
                formLevel2.setVisibility(View.VISIBLE);
                formLevel3.setVisibility(View.VISIBLE);
                formLevel4.setVisibility(View.VISIBLE);
                formLevel5.setVisibility(View.VISIBLE);
                formLevel6.setVisibility(View.VISIBLE);
                formLevel7.setVisibility(View.VISIBLE);
                formLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.FOAM_LEVEL_1000.equals(foam_level)){
                formLevel1.setVisibility(View.VISIBLE);
                formLevel2.setVisibility(View.VISIBLE);
                formLevel3.setVisibility(View.VISIBLE);
                formLevel4.setVisibility(View.VISIBLE);
                formLevel5.setVisibility(View.VISIBLE);
                formLevel6.setVisibility(View.VISIBLE);
                formLevel7.setVisibility(View.VISIBLE);
                formLevel8.setVisibility(View.VISIBLE);
            }

            //紧急停止
            if (tmp_7 == 1 ) {
                tvStop1111.setText(getString(R.string.scram));
                ivStop1111.setImageResource(R.drawable.ic_jt_use);
            } else {
                tvStop1111.setText(getString(R.string.shut_stop));
                ivStop1111.setImageResource(R.drawable.ic_jt_stop);
            }


            System.arraycopy(content,2,temp_1,0,1);
            float turretAngleL = Float.valueOf(binary(temp_1,10)).floatValue();//转塔角度L
            System.arraycopy(content,3,temp_1,0,1);
            float turretAngleH = Float.valueOf(binary(temp_1,10)).floatValue();//转塔角度H
            float turretAngle = (turretAngleH * 256 + turretAngleL) / 10;
            if(IConstant.TURRET_ANGEL_NEGTIVE.equals(turret_angle_negtive) )
            {
                TurretAngle.setText("-" + turretAngle);
            }else{
                TurretAngle.setText("" + turretAngle);
            }

            //发动机转速
            System.arraycopy(content,4,temp_1,0,1);
            int engineSpeedL = Integer.valueOf(binary(temp_1,10)).intValue();
            System.arraycopy(content,5,temp_1,0,1);
            int engineSpeedH = Integer.valueOf(binary(temp_1,10)).intValue();
            int engine_speed = engineSpeedH * 256 + engineSpeedL;
            EngineSpeed.setText("" + engine_speed);

            //液压系统压力
            System.arraycopy(content,6,temp_1,0,1);
            HydraulicPress.setText(binary(temp_1,10));


            //水泵出口压力
            System.arraycopy(content,7,temp_1,0,1);
            float pumpVentPress = Float.valueOf(binary(temp_1,10)).floatValue() / 10;
            PumpVentPress.setText("" + pumpVentPress);

            //故障代码
            System.arraycopy(content,8,temp_1,0,1);
            String err_code = binary(temp_1,10);
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
            }else if (IConstant.ERR_CODE_49.equals(err_code)) {
                ErrCode.setText(getString(R.string.err_code_49));
            }else if (IConstant.ERR_CODE_49.equals(err_code)) {
                ErrCode.setText(getString(R.string.err_code_49));
            }else if (IConstant.err_code_50.equals(err_code)) {
                ErrCode.setText(getString(R.string.err_code_50));
            }else if (IConstant.err_code_51.equals(err_code)) {
                ErrCode.setText(getString(R.string.err_code_51));
            }else if (IConstant.err_code_52.equals(err_code)) {
                ErrCode.setText(getString(R.string.err_code_52));
            }else if (IConstant.err_code_53.equals(err_code)) {
                ErrCode.setText(getString(R.string.err_code_53));
            }else if (IConstant.err_code_54.equals(err_code)) {
                ErrCode.setText(getString(R.string.err_code_54));
            }else {
                ErrCode.setText("");
            }

            temp = content[9];
            tmp_0 = (temp >> 0) & 0x1;
            tmp_1 = (temp >> 1) & 0x1;
            tmp_6 = (temp >> 6) & 0x1;
            tmp_7 = (temp >> 7) & 0x1;

            //1:水泵运行状态
            String water_pump_status = "" + tmp_0;
            if (IConstant.WATER_PUMP_START.equals(water_pump_status)){
                WaterPumpStatus.setText(getString(R.string.started));
            } else if (IConstant.WATER_PUMP_STOP.equals(water_pump_status)) {
                WaterPumpStatus.setText(getString(R.string.stop));
            }

           // 1:塔台对中状态
            if (tmp_1 == 1) {
                turretHitok.setBackgroundResource(R.drawable.shape_ring_connect_press);
            } else {
                turretHitok.setBackgroundResource(R.drawable.shape_ring_connect_nor);
            }

            //水罐液位
            String water_level = "" + ((temp >> 5) & 0x1)  + "" + ((temp >> 4) & 0x1) + "" + ((temp >> 3) & 0x1) + "" + ((temp >> 2) & 0x1);
            if (IConstant.WATER_LEVEL_0000.equals(water_level)){
                waterLevel1.setVisibility(View.INVISIBLE);
                waterLevel2.setVisibility(View.INVISIBLE);
                waterLevel3.setVisibility(View.INVISIBLE);
                waterLevel4.setVisibility(View.INVISIBLE);
                waterLevel5.setVisibility(View.INVISIBLE);
                waterLevel6.setVisibility(View.INVISIBLE);
                waterLevel7.setVisibility(View.INVISIBLE);
                waterLevel8.setVisibility(View.INVISIBLE);
            }else if(IConstant.WATER_LEVEL_0001.equals(water_level)){
                waterLevel1.setVisibility(View.INVISIBLE);
                waterLevel2.setVisibility(View.INVISIBLE);
                waterLevel3.setVisibility(View.INVISIBLE);
                waterLevel4.setVisibility(View.INVISIBLE);
                waterLevel5.setVisibility(View.INVISIBLE);
                waterLevel6.setVisibility(View.INVISIBLE);
                waterLevel7.setVisibility(View.INVISIBLE);
                waterLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.WATER_LEVEL_0010.equals(water_level)){
                waterLevel1.setVisibility(View.INVISIBLE);
                waterLevel2.setVisibility(View.INVISIBLE);
                waterLevel3.setVisibility(View.INVISIBLE);
                waterLevel4.setVisibility(View.INVISIBLE);
                waterLevel5.setVisibility(View.INVISIBLE);
                waterLevel6.setVisibility(View.INVISIBLE);
                waterLevel7.setVisibility(View.VISIBLE);
                waterLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.WATER_LEVEL_0011.equals(water_level)){
                waterLevel1.setVisibility(View.INVISIBLE);
                waterLevel2.setVisibility(View.INVISIBLE);
                waterLevel3.setVisibility(View.INVISIBLE);
                waterLevel4.setVisibility(View.INVISIBLE);
                waterLevel5.setVisibility(View.INVISIBLE);
                waterLevel6.setVisibility(View.VISIBLE);
                waterLevel7.setVisibility(View.VISIBLE);
                waterLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.WATER_LEVEL_0100.equals(water_level)){
                waterLevel1.setVisibility(View.INVISIBLE);
                waterLevel2.setVisibility(View.INVISIBLE);
                waterLevel3.setVisibility(View.INVISIBLE);
                waterLevel4.setVisibility(View.INVISIBLE);
                waterLevel5.setVisibility(View.VISIBLE);
                waterLevel6.setVisibility(View.VISIBLE);
                waterLevel7.setVisibility(View.VISIBLE);
                waterLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.WATER_LEVEL_0101.equals(water_level)){
                waterLevel1.setVisibility(View.INVISIBLE);
                waterLevel2.setVisibility(View.INVISIBLE);
                waterLevel3.setVisibility(View.INVISIBLE);
                waterLevel4.setVisibility(View.VISIBLE);
                waterLevel5.setVisibility(View.VISIBLE);
                waterLevel6.setVisibility(View.VISIBLE);
                waterLevel7.setVisibility(View.VISIBLE);
                waterLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.WATER_LEVEL_0110.equals(water_level)){
                waterLevel1.setVisibility(View.INVISIBLE);
                waterLevel2.setVisibility(View.INVISIBLE);
                waterLevel3.setVisibility(View.VISIBLE);
                waterLevel4.setVisibility(View.VISIBLE);
                waterLevel5.setVisibility(View.VISIBLE);
                waterLevel6.setVisibility(View.VISIBLE);
                waterLevel7.setVisibility(View.VISIBLE);
                waterLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.WATER_LEVEL_0111.equals(water_level)){
                waterLevel1.setVisibility(View.INVISIBLE);
                waterLevel2.setVisibility(View.VISIBLE);
                waterLevel3.setVisibility(View.VISIBLE);
                waterLevel4.setVisibility(View.VISIBLE);
                waterLevel5.setVisibility(View.VISIBLE);
                waterLevel6.setVisibility(View.VISIBLE);
                waterLevel7.setVisibility(View.VISIBLE);
                waterLevel8.setVisibility(View.VISIBLE);
            }else if(IConstant.WATER_LEVEL_1000.equals(water_level)){
                waterLevel1.setVisibility(View.VISIBLE);
                waterLevel2.setVisibility(View.VISIBLE);
                waterLevel3.setVisibility(View.VISIBLE);
                waterLevel4.setVisibility(View.VISIBLE);
                waterLevel5.setVisibility(View.VISIBLE);
                waterLevel6.setVisibility(View.VISIBLE);
                waterLevel7.setVisibility(View.VISIBLE);
                waterLevel8.setVisibility(View.VISIBLE);
            }

            // 1: 支腿调平
            if (tmp_6 == 1) {
                zttp_lay.setBackgroundResource(R.drawable.shape_ring_connect_press);
                //如果再测试诊断界面不切换，不再显示遥控作业界面
                changePageMain(false); // 为 true:显示 行驶界面 ，false 显示 作业界面

            } else {
                zttp_lay.setBackgroundResource(R.drawable.shape_ring_connect_nor);
            }


            // 1:支腿收到位
            if (tmp_7 == 1) {
                ztsh_lay.setBackgroundResource(R.drawable.shape_ring_connect_press);
            } else {
                ztsh_lay.setBackgroundResource(R.drawable.shape_ring_connect_nor);
            }

            //1 臂架角度
            System.arraycopy(content,10,temp_1,0,1);
            int arm_Angle1L = Integer.valueOf(binary(temp_1,10)).intValue();
            System.arraycopy(content,11,temp_1,0,1);
            int arm_Angle1H = Integer.valueOf(binary(temp_1,10)).intValue();
            int arm_Angle1 = arm_Angle1H * 256 + arm_Angle1L;
            ArmAngle1.setText("" + arm_Angle1);

            // 2#臂架角度
            System.arraycopy(content,12,temp_1,0,1);
            //2#臂架角度L
            int arm_Angle2L = Integer.valueOf(binary(temp_1,10)).intValue();
            System.arraycopy(content,13,temp_1,0,1);
           //2#臂架角度H
            int arm_Angle2H = Integer.valueOf(binary(temp_1,10)).intValue();
            int arm_Angle2 = arm_Angle2H * 256 + arm_Angle2L;
            ArmAngle2.setText("" + arm_Angle2);

            System.arraycopy(content,14,temp_1,0,1);
            //3#臂架角度L
            int arm_Angle3L = Integer.valueOf(binary(temp_1,10)).intValue();
            System.arraycopy(content,15,temp_1,0,1);
            //3#臂架角度H
            int arm_Angle3H = Integer.valueOf(binary(temp_1,10)).intValue();
            int arm_Angle3 = arm_Angle3H * 256 + arm_Angle3L;
            ArmAngle3.setText("" + arm_Angle3);

            // 方向盘符号位
            temp = content[16];
            tmp_0 = (temp >> 0) & 0x1;  // 声光报警
            tmp_1 = (temp >> 1) & 0x1;  // 方向盘符号位
            tmp_6 = (temp >> 6) & 0x1;
            tmp_7 = (temp >> 7) & 0x1;
            int dire_fit = tmp_1;
            // ===

            System.arraycopy(content,22,temp_1,0,1);
            //水流量L
            int water_FlowL = Integer.valueOf(binary(temp_1,10)).intValue();
            System.arraycopy(content,23,temp_1,0,1);
            //水流量H
            int water_FlowH = Integer.valueOf(binary(temp_1,10)).intValue();
            int water_Flow = water_FlowH * 256 + water_FlowL;
            WaterFlow.setText("" + water_Flow);

            //车速
            System.arraycopy(content,24,temp_1,0,1);
            int speed = Integer.valueOf(binary(temp_1,10)).intValue();
            carSpeed.setText("" + speed + "km/h");

            //油门
            System.arraycopy(content,25,temp_1,0,1);
            int oil = Integer.valueOf(binary(temp_1,10)).intValue();
            oil_tv.setText("" + oil + "%");

            //刹车
            System.arraycopy(content,26,temp_1,0,1);
            int breakData = Integer.valueOf(binary(temp_1,10)).intValue();
            brake_tv.setText("" + breakData + "%");

            // 方向盘转角
            System.arraycopy(content,27,temp_1,0,1);
            long directionArmlLL = Integer.valueOf(binary(temp_1,10)).longValue();
            System.arraycopy(content,28,temp_1,0,1);
            long directionArmlL = Integer.valueOf(binary(temp_1,10)).longValue();

            System.arraycopy(content,29,temp_1,0,1);
            long directionArmlH = Integer.valueOf(binary(temp_1,10)).longValue();
            System.arraycopy(content,30,temp_1,0,1);
            long directionArmlHH = Integer.valueOf(binary(temp_1,10)).longValue();

            long direction = directionArmlHH * 256 * 256 * 256 +  + directionArmlH * 256 * 256 + directionArmlL * 256 + directionArmlLL;
            direction = direction>1000?1000:direction;
            // 范围 -1000 到 1000
            String dire_armgle = "" + direction;

            dire_armgle = (dire_fit==1?("-"+dire_armgle):dire_armgle);
            directionArml.setText("" + dire_armgle + "°");

        }

        System.arraycopy(content, 32, temp_1, 0, 1);
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


        System.arraycopy(content, 33, temp_1, 0, 1);
        int signal_strength = bytes2int(temp_1);
        System.arraycopy(content, 34, temp_1, 0, 1);
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

        System.arraycopy(content, 36, temp_1, 0, 1);
        int remote_ctrl_status = Integer.valueOf(binary(temp_1, 10)).intValue();

        if ((remote_ctrl_status & 0x2) == 0x2) {
            RemoteCtrlStatus.setText(getString(R.string.scram));
            tvStartStaTest.setText(getString(R.string.scram));
            tvStop1111.setText(getString(R.string.scram));
            ivStop1111.setImageResource(R.drawable.ic_jt_use);
        } else if ((remote_ctrl_status & 0x1) == 0x1) {
            RemoteCtrlStatus.setText(getString(R.string.started));
            tvStartStaTest.setText(getString(R.string.started));
             tvStop1111.setText(getString(R.string.shut_stop));
            ivStop1111.setImageResource(R.drawable.ic_jt_stop);
        } else if ((remote_ctrl_status & 0x1) == 0x0) {
            RemoteCtrlStatus.setText(getString(R.string.not_started));
            tvStartStaTest.setText(getString(R.string.not_started));
            tvStop1111.setText(getString(R.string.shut_stop));
             ivStop1111.setImageResource(R.drawable.ic_jt_stop);
        }


        try {
            System.arraycopy(content,37,temp_1,0,1);
            int brightness = Integer.valueOf(binary(temp_1,10)).intValue();
          //  CacheData.setMsg_info("============dealOtherData=========content==========37====2222=temp======>" + temp, IConstant.MESSAGE_INFO_ALL);

            brightNess(brightness);
            changePage(brightness);
        }catch (Exception e) {
            e.printStackTrace();
        }

        temp = content[40];
        tmp_0 = (temp >> 0) & 0x1;
        tmp_7 = (temp >> 7) & 0x1;

        String b37_12 = "" + ((temp>>1) & 0x1) + ((temp>>2) & 0x1);    //1 直流  2 喷雾
        if(IConstant.STATE_10.equals(b37_12)){   //直流
            b37_12_up.setImageResource(R.drawable.ic_3_up_red);
            b37_12_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b37_12_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_00.equals(b37_12)){
            b37_12_up.setImageResource(R.drawable.ic_3_up_wh);
            b37_12_mid.setImageResource(R.drawable.ic_3_mi_red);
            b37_12_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_01.equals(b37_12)){   //喷雾
            b37_12_up.setImageResource(R.drawable.ic_3_up_wh);
            b37_12_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b37_12_down.setImageResource(R.drawable.ic_3_do_red);
        }

        String b37_34 = "" + ((temp>>3) & 0x1) + ((temp>>4) & 0x1);    //3 水炮上  4 水炮下
        if(IConstant.STATE_10.equals(b37_34)){   //水炮上
            b37_34_up.setImageResource(R.drawable.ic_3_up_red);
            b37_34_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b37_34_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_00.equals(b37_34)){
            b37_34_up.setImageResource(R.drawable.ic_3_up_wh);
            b37_34_mid.setImageResource(R.drawable.ic_3_mi_red);
            b37_34_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_01.equals(b37_34)){   //水炮下
            b37_34_up.setImageResource(R.drawable.ic_3_up_wh);
            b37_34_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b37_34_down.setImageResource(R.drawable.ic_3_do_red);
        }

        String b37_56 = "" + ((temp>>5) & 0x1) + ((temp>>6) & 0x1);    //1 左  2 右
        if(IConstant.STATE_10.equals(b37_56)){   // 左
            b37_56_up.setImageResource(R.drawable.ic_3_up_red);
            b37_56_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b37_56_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_00.equals(b37_56)){
            b37_56_up.setImageResource(R.drawable.ic_3_up_wh);
            b37_56_mid.setImageResource(R.drawable.ic_3_mi_red);
            b37_56_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_01.equals(b37_56)){   //右
            b37_56_up.setImageResource(R.drawable.ic_3_up_wh);
            b37_56_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b37_56_down.setImageResource(R.drawable.ic_3_do_red);
        }

        // 喇叭
        if (tmp_7 == 1) {
            b377_iv.setImageResource(R.drawable.ic_round_red);
        } else {
            b377_iv.setImageResource(R.drawable.ic_round_white);
        }

        temp = content[41];
        tmp_0 = (temp >> 0) & 0x1;
        tmp_1 = (temp >> 1) & 0x1;
        tmp_2 = (temp >> 2) & 0x1;  //自动展臂
        tmp_3 = (temp >> 3) & 0x1;  //自动收臂
        tmp_4 = (temp >> 4) & 0x1;
        tmp_5 = (temp >> 5) & 0x1;
        tmp_6 = (temp >> 6) & 0x1;
        tmp_7 = (temp >> 7) & 0x1;

        // 遥控手动
        if (tmp_0 == 1) {
            b90_up_iv.setImageResource(R.drawable.ic_22_up_red);
            b90_down_iv.setImageResource(R.drawable.ic_22_do_wh);
           // CacheData.setMsg_info("============dealOtherData=======content=====temp====41==========>" + temp, IConstant.MESSAGE_INFO_ALL);
        } else {
            b90_up_iv.setImageResource(R.drawable.ic_22_up_wh);
            b90_down_iv.setImageResource(R.drawable.ic_22_do_red);
        }

        String b37_14 = "" + ((temp>>1) & 0x1);   //1 臂架使能

        String b41_45 = "" + ((temp>>4) & 0x1) + ((temp>>5) & 0x1);    //4 支腿收回  5 支腿调平
        if(IConstant.STATE_10.equals(b41_45)){   //支腿收回
            b39_45_up.setImageResource(R.drawable.ic_3_up_red);
            b39_45_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b39_45_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_00.equals(b41_45)){
            b39_45_up.setImageResource(R.drawable.ic_3_up_wh);
            b39_45_mid.setImageResource(R.drawable.ic_3_mi_red);
            b39_45_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_01.equals(b41_45)){   //支腿调平
            b39_45_up.setImageResource(R.drawable.ic_3_up_wh);
            b39_45_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b39_45_down.setImageResource(R.drawable.ic_3_do_red);
        }




        // 末端供电（备用）不知到
//        if (tmp_6 == 1) {
//            b385_iv.setImageResource(R.drawable.ic_round_red);
//        } else {
//            b385_iv.setImageResource(R.drawable.ic_round_white);
//        }

        //  快 慢档
        if (tmp_7 == 1) {
            b387_up_iv.setImageResource(R.drawable.ic_22_up_red);
            b387_down_iv.setImageResource(R.drawable.ic_22_do_wh);
        } else {
            b387_up_iv.setImageResource(R.drawable.ic_22_up_wh);
            b387_down_iv.setImageResource(R.drawable.ic_22_do_red);
        }


        temp = content[42];
        tmp_0 = (temp >> 0) & 0x1;
        tmp_1 = (temp >> 1) & 0x1;
        tmp_2 = (temp >> 2) & 0x1;
        tmp_3 = (temp >> 3) & 0x1;
        tmp_4 = (temp >> 4) & 0x1;  // 三臂伸缩使能
        tmp_5 = (temp >> 5) & 0x1;
        tmp_6 = (temp >> 6) & 0x1;
       // bj_down = (temp >> 6) & 0x1; //视频变焦 往下
        tmp_7 = (temp >> 7) & 0x1;
        String b39_01 = "" + ((temp>>0) & 0x1) + ((temp>>1) & 0x1);    //0 水炮自摆  1 水炮复位
        if(IConstant.STATE_10.equals(b39_01)){   //水炮自摆
            b39_01_up.setImageResource(R.drawable.ic_3_up_red);
            b39_01_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b39_01_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_00.equals(b39_01)){
            b39_01_up.setImageResource(R.drawable.ic_3_up_wh);
            b39_01_mid.setImageResource(R.drawable.ic_3_mi_red);
            b39_01_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_01.equals(b39_01)){   //水炮复位
            b39_01_up.setImageResource(R.drawable.ic_3_up_wh);
            b39_01_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b39_01_down.setImageResource(R.drawable.ic_3_do_red);
        }

        b37_14 = "" + b37_14 + ((temp>>2) & 0x1);  //臂架使能   支腿使能
        if(IConstant.STATE_10.equals(b37_14)){   //臂架使能
            b38_14_up.setImageResource(R.drawable.ic_3_up_red);
            b38_14_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b38_14_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_00.equals(b37_14)){
            b38_14_up.setImageResource(R.drawable.ic_3_up_wh);
            b38_14_mid.setImageResource(R.drawable.ic_3_mi_red);
            b38_14_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_01.equals(b37_14)){   //支腿使能
            b38_14_up.setImageResource(R.drawable.ic_3_up_wh);
            b38_14_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b38_14_down.setImageResource(R.drawable.ic_3_do_red);
        }

        // 回转对中
        if (tmp_3 == 1) {
            b393_iv.setImageResource(R.drawable.ic_round_red);
        } else {
            b393_iv.setImageResource(R.drawable.ic_round_white);
        }

        if(tmp_4 == 1){    // 三臂伸缩使能
            b41_45_mid.setImageResource(R.drawable.ic_22_up_red);
            b41_45_down.setImageResource(R.drawable.ic_22_do_wh);
        }else {      //
            b41_45_mid.setImageResource(R.drawable.ic_22_up_wh);
            b41_45_down.setImageResource(R.drawable.ic_22_do_red);
        }

        // 遥控器照明
        if (tmp_5 == 1) {
            b417_iv.setImageResource(R.drawable.ic_round_red);
        } else {
            b417_iv.setImageResource(R.drawable.ic_round_white);
        }

        // 启动按钮
        if (tmp_6 == 1) {
            b370_iv.setImageResource(R.drawable.ic_round_red);
        } else {
            b370_iv.setImageResource(R.drawable.ic_round_white);
        }

        temp = content[43];
        tmp_0 = (temp >> 0) & 0x1;
        tmp_1 = (temp >> 1) & 0x1;
        tmp_2 = (temp >> 2) & 0x1;
        tmp_3 = (temp >> 3) & 0x1;
        tmp_4 = (temp >> 4) & 0x1;
        tmp_5 = (temp >> 5) & 0x1;
        tmp_6 = (temp >> 6) & 0x1;
        tmp_7 = (temp >> 7) & 0x1;

        // 切换使能
        if (tmp_0 == 1) {
            b40_0_up.setImageResource(R.drawable.ic_3_up_red);
            b40_0_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b40_0_down.setImageResource(R.drawable.ic_3_do_wh);
        } else {
            b40_0_up.setImageResource(R.drawable.ic_3_up_wh);
            b40_0_mid.setImageResource(R.drawable.ic_3_mi_red);
            b40_0_down.setImageResource(R.drawable.ic_3_do_wh);
        }

        String b40_12 = "" + ((temp>>1) & 0x1) + ((temp>>2) & 0x1);    //0 行驶  1 取力
        if(IConstant.STATE_10.equals(b40_12)){    // 水炮自摆
            b40_12_up.setImageResource(R.drawable.ic_3_up_red);
            b40_12_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b40_12_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_00.equals(b40_12)){
            b40_12_up.setImageResource(R.drawable.ic_3_up_wh);
            b40_12_mid.setImageResource(R.drawable.ic_3_mi_red);
            b40_12_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if(IConstant.STATE_01.equals(b40_12)){   //水炮复位
            b40_12_up.setImageResource(R.drawable.ic_3_up_wh);
            b40_12_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b40_12_down.setImageResource(R.drawable.ic_3_do_red);
        }

        String b40_34 = "" + ((temp>>3) & 0x1) + ((temp>>4) & 0x1);    //0 驻车制动  1 解除
        if (IConstant.STATE_10.equals(b40_34)) {
            b40_34_up.setImageResource(R.drawable.ic_3_up_red);
            b40_34_down.setImageResource(R.drawable.ic_3_do_wh);
        } else {
            b40_34_up.setImageResource(R.drawable.ic_3_up_wh);
            b40_34_down.setImageResource(R.drawable.ic_3_do_red);
        }

        // D
        if (tmp_5 == 1 && tmp_6 == 0  && tmp_7 == 0) {
            b40_567_up.setImageResource(R.drawable.ic_3_up_red);
            b40_567_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b40_567_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if (tmp_5 == 0 && tmp_6 == 1  && tmp_7 == 0) {  // N
            b40_567_up.setImageResource(R.drawable.ic_3_up_wh);
            b40_567_mid.setImageResource(R.drawable.ic_3_mi_red);
            b40_567_down.setImageResource(R.drawable.ic_3_do_wh);
        }else if (tmp_5 == 0 && tmp_6 == 0  && tmp_7 == 1) {  // R
            b40_567_up.setImageResource(R.drawable.ic_3_up_wh);
            b40_567_mid.setImageResource(R.drawable.ic_3_mi_wh);
            b40_567_down.setImageResource(R.drawable.ic_3_do_red);
        }


            temp = content[44];
            tmp_0 = (temp >> 0) & 0x1;
            tmp_1 = (temp >> 1) & 0x1;
            tmp_2 = (temp >> 2) & 0x1;
            tmp_3 = (temp >> 3) & 0x1;
            tmp_4 = (temp >> 4) & 0x1;
            tmp_5 = (temp >> 5) & 0x1;
            tmp_6 = (temp >> 6) & 0x1;
            tmp_7 = (temp >> 7) & 0x1;

            String b41_01 = "" + ((temp>>0) & 0x1) + ((temp>>1) & 0x1);    //0 发动机启  1 发动机停
            if(IConstant.STATE_10.equals(b41_01)){    // 发动机启
                b41_01_up.setImageResource(R.drawable.ic_3_up_red);
                b41_01_mid.setImageResource(R.drawable.ic_3_mi_wh);
                b41_01_down.setImageResource(R.drawable.ic_3_do_wh);
            }else if(IConstant.STATE_00.equals(b41_01)){
                b41_01_up.setImageResource(R.drawable.ic_3_up_wh);
                b41_01_mid.setImageResource(R.drawable.ic_3_mi_red);
                b41_01_down.setImageResource(R.drawable.ic_3_do_wh);
            }else if(IConstant.STATE_01.equals(b41_01)){   //发动机停
                b41_01_up.setImageResource(R.drawable.ic_3_up_wh);
                b41_01_mid.setImageResource(R.drawable.ic_3_mi_wh);
                b41_01_down.setImageResource(R.drawable.ic_3_do_red);
            }

            // 视频变焦上   下
             String b44_23 = "" + tmp_3 + tmp_2;
            if (IConstant.STATE_01.equals(b44_23)) {
                b41_2_up.setImageResource(R.drawable.ic_3_up_red);
                b41_2_mid.setImageResource(R.drawable.ic_3_mi_wh);
                b41_2_down.setImageResource(R.drawable.ic_3_do_wh);
            } else if(IConstant.STATE_00.equals(b44_23)){
                b41_2_up.setImageResource(R.drawable.ic_3_up_wh);
                b41_2_mid.setImageResource(R.drawable.ic_3_mi_red);
                b41_2_down.setImageResource(R.drawable.ic_3_do_wh);
            }else if(IConstant.STATE_10.equals(b44_23)){
                b41_2_up.setImageResource(R.drawable.ic_3_up_wh);
                b41_2_mid.setImageResource(R.drawable.ic_3_mi_wh);
                b41_2_down.setImageResource(R.drawable.ic_3_do_red);
            }

            String b41_34 = "" + tmp_5 + tmp_4;
            if (IConstant.STATE_01.equals(b41_34)) {     // 水泵启动
                b41_3_up.setImageResource(R.drawable.ic_3_up_red);
                b41_3_down.setImageResource(R.drawable.ic_3_mi_wh);
                b41_45_up.setImageResource(R.drawable.ic_3_do_wh);
            } else if(IConstant.STATE_00.equals(b41_34)){   // 水泵停止
                b41_3_up.setImageResource(R.drawable.ic_3_up_wh);
                b41_3_down.setImageResource(R.drawable.ic_3_mi_red);
                b41_45_up.setImageResource(R.drawable.ic_3_do_wh);
            }else if(IConstant.STATE_10.equals(b41_34)){   // 一键炮出水
                b41_3_up.setImageResource(R.drawable.ic_3_up_wh);
                b41_3_down.setImageResource(R.drawable.ic_3_mi_wh);
                b41_45_up.setImageResource(R.drawable.ic_3_do_red);
            }


        // 强制动作
        if (tmp_6 == 1) {
            b385_iv.setImageResource(R.drawable.ic_round_red);
        } else {
            b385_iv.setImageResource(R.drawable.ic_round_white);
        }


        // 水炮照明
        if (tmp_7 == 1) {
            b416_iv.setImageResource(R.drawable.ic_round_red);
        } else {
            b416_iv.setImageResource(R.drawable.ic_round_white);
        }



        System.arraycopy(content, 45, temp_1, 0, 1);
        int temp_45 = bytes2int(temp_1);
        if (temp_45==0) {
            b9012_up_iv.setImageResource(R.drawable.ic_2_left_wh);
            b9012_down_iv.setImageResource(R.drawable.ic_2_right_wh);
            b45_tv.setText("" + temp_45);
        } else if(temp_45<=127 && temp_45>0){
            b9012_up_iv.setImageResource(R.drawable.ic_2_left_wh);
            b9012_down_iv.setImageResource(R.drawable.ic_2_right_red);
            b45_tv.setText("" + temp_45);
        }else{
            b9012_up_iv.setImageResource(R.drawable.ic_2_left_red);
            b9012_down_iv.setImageResource(R.drawable.ic_2_right_wh);
            b45_tv.setText(("-" + temp_45));

        }

        if (temp_45 > 127) {
            temp_45 = 256 - temp_45;
            b45_tv.setText(("-" + temp_45));
        } else {
            b45_tv.setText("" + temp_45);
        }


        //一臂手柄信号
        System.arraycopy(content,46,temp_1,0,1);
        int ybsb = bytes2int(temp_1);
        if(ybsb==0){
            ivDown11Up.setImageResource(R.drawable.ic_2_up_wh);
            ivDown11Down.setImageResource(R.drawable.ic_2_do_wh);
            tvDown11.setText("" + ybsb);
        }else if(ybsb<=127 && ybsb>0){
            ivDown11Up.setImageResource(R.drawable.ic_2_up_wh);
            ivDown11Down.setImageResource(R.drawable.ic_2_do_red);
            tvDown11.setText("" + ybsb);
        }else{
            ivDown11Up.setImageResource(R.drawable.ic_2_up_red);
            ivDown11Down.setImageResource(R.drawable.ic_2_do_wh);
            tvDown11.setText("-" + (256-ybsb));
        }

        //二臂手柄信号
        System.arraycopy(content,47,temp_1,0,1);
        int ebsb =bytes2int(temp_1);
        if(ebsb==0){
            ivDown22Up.setImageResource(R.drawable.ic_2_up_wh);
            ivDown22Down.setImageResource(R.drawable.ic_2_do_wh);
            tvDown22.setText("" + ebsb);
        }else if(ebsb<=127 && ebsb>0){
            ivDown22Up.setImageResource(R.drawable.ic_2_up_wh);
            ivDown22Down.setImageResource(R.drawable.ic_2_do_red);
            tvDown22.setText("" + ebsb);
        }else{
            ivDown22Up.setImageResource(R.drawable.ic_2_up_red);
            ivDown22Down.setImageResource(R.drawable.ic_2_do_wh);
            tvDown22.setText("-" + (256-ebsb));
        }


        //三臂手柄信号
        System.arraycopy(content,48,temp_1,0,1);
        int sanbsb = bytes2int(temp_1);
        if(sanbsb==0){
            ivDown33Up.setImageResource(R.drawable.ic_2_up_wh);
            ivDown33Down.setImageResource(R.drawable.ic_2_do_wh);
            tvDown33.setText("" + sanbsb);
        }else if(sanbsb<=127 && sanbsb>0){
            ivDown33Up.setImageResource(R.drawable.ic_2_up_wh);
            ivDown33Down.setImageResource(R.drawable.ic_2_do_red);
            tvDown33.setText("" + sanbsb);
        }else{
            ivDown33Up.setImageResource(R.drawable.ic_2_up_red);
            ivDown33Down.setImageResource(R.drawable.ic_2_do_wh);
            tvDown33.setText("-" + (256-sanbsb));
        }

       // temp = content[49];

        //  CacheData.setMsg_info("============dealOtherData========content[2]==================>" + content[2], IConstant.MESSAGE_INFO_ALL);
        System.arraycopy(content,49,temp_1,0,1);
        int sbts = bytes2int(temp_1);
        b925_iv.setText("" + sbts);
          //  CacheData.setMsg_info("============dealOtherData========content[2]=====tmp_2=============>" + tmp_2, IConstant.MESSAGE_INFO_ALL);


        //油门
        System.arraycopy(content,50,temp_1,0,1);
        int oil = Integer.valueOf(binary(temp_1,10)).intValue();
        oilPostion.setText("" + oil + "%");
        oilPos.setText("" + oil + "%");



    }


    /**
     * 保留两位小数  四舍五入
     * @param num
     * @return
     */
    private String dataTranInfo(float num){
        BigDecimal b = new BigDecimal(num);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        String for_num = String.format("%.2f",f1);
        return for_num;
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


