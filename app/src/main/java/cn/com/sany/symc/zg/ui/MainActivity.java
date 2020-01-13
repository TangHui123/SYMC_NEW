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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
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

/**
 *
 * 首页应用
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,CameraInterface.CamOpenOverCallback  {

    public static final String TAG = "MainActivity.class";

    private TextView ArmSpreadStatus;
    private TextView FoamLevel;
    private TextView WaterPumpStatus;
    private TextView waterLevel;
    //    private TextView TurretAngleLow;
//    private TextView TurretAngleHigh;
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

    private LinearLayout lowBattery;
    private LinearLayout haveBattery;
    private TextView Battery1;
    private TextView Battery2;
    private TextView Battery3;
    private TextView Battery4;

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

    private static int  change_bright_old = 0;  //保存之前一次亮度上升沿   0x04从0到1切换

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
    private void initData(){
//        if (timerTask_loc != null){
//            timerTask_loc.cancel();  //将原任务从队列中移除
//        }
//
//        timerTask_loc = getTimerTask_loc();
//        timer_loc = new Timer();
//        timer_loc.schedule(timerTask_loc,500,1000);

    }







    /**
     * 初始化控件基本信息
     * @param
     */
    private void initBaseInfo(){
//        ArmSpreadStatus = (TextView) findViewById(R.id.ArmSpreadStatus);
//        WaterPumpStatus = (TextView) findViewById(R.id.WaterPumpStatus);
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
                        //根据上传的状态显示对应的信息
                        displayMultipleState(multipleStateInfo);
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

