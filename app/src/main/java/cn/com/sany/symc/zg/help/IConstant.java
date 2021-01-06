package cn.com.sany.symc.zg.help;

/**
 *
 *公共静态常量类
 * @auther bird
 * Created  2017/12/27 09:30
 */

public class IConstant {

//    public static final int REGISTER_NO = 0;        //从未注册
//    public static final int REGISTER_SUCESS = 1;   //注册成功
//    public static final int REGISTER_FAIL = 2;   //注册失败
//    public static final long I_PASS_NUM = 11111111111L;   //终端号码
//    public static final String I_DELETE_ZTC_CONF = "delete_zic_conf";   //删除该文件夹和下面的文件



    public static final int RECEIVE_DATA_MIN_LENGTH = 9;      //接收数据最少长度

    public static final byte RECEIVE_DATA_PROTOCOL_HEAD_1 = (byte)0xAA;      //起始头第一个字节,0xAA

    public static final byte RECEIVE_DATA_PROTOCOL_HEAD_2 = (byte)0x75;      //起始头第二个字节

    public static final byte PROTOCOL_VERSIOON_1 = (byte)0x04;      //接收数据最少长度

    public static final byte PROTOCOL_COMMAND_MIN = (byte)0x00;      //命令字取值范围最少

    public static final byte PROTOCOL_COMMAND_MAX = (byte)0xFF;      //命令字取值范围最大

    public static final int PROTOCOL_COMMAND_DELETE_ALL = 255;      //删除全部

    public static final byte PROTOCOL_COMMAND_DELETE_NO = (byte)0x00;      //没有删除

    //    public static final byte COMMAND_ANSWER = (byte)0x00;      //显示屏和linux的通用应答
    public static final byte COMMAND_ANSWER = (byte)0xA1;      //显示屏和linux的通用应答

    //    public static final byte COMMAND_MULTIPLE_POSTION_INFO = (byte)0xA1;      //综合位置信息上传（1S周期）
    public static final byte COMMAND_MULTIPLE_POSTION_INFO = (byte)0x00;      //综合位置信息上传（1S周期）

    public static final byte COMMAND_UPLOAD_RAIL = (byte)0xA3;          //上传围栏数据（上电时或有变化时）

    public static final byte COMMAND_UPLOAD_ROAD = (byte)0xA4;      //上传路线数据 （上电时或有变化时）

    public static final byte COMMAND_UPLOAD_CARD = (byte)0xA5;      //上传核准证数据（上电时或有变化时）

    public static final byte COMMAND_UPLOAD_TXT = (byte)0xA7;      //上传通知文本（平台下发时）数据第一位为标志位，标记是紧急、通知、报警

    public static final byte COMMAND_UPLOAD_UPGRADE_FILE = (byte)0xA9;      //上传显示屏升级文件（多包）

    public static final byte COMMAND_UPLOAD_UPGRADE_FILE_ANSWER = (byte)0xAA;      //升级文件接收包应答

    public static final int  VIDEO_PALY_TIME_UPDATE = 2001;      //上传显示屏升级文件（多包）

    public static final byte COMMAND_QUERY_UPLOAD_FILE_DATA = (byte)0xA2;      //linux查询内屏已接收包数

    public static final byte COMMAND_QUERY_NEW_VERSION = (byte)0xAC;      //linux查询内屏已接收包数

    // public static final byte COMMAND_UPLOAD_UPGRADE_ANSWER = (byte) 0xAA;      //显示屏应答接收升级文件

    public static final byte COMMAND_UPLOAD_DELETE_DATA = (byte)0xA8;      //上传删除围栏、路线、核准证

    public static final byte COMMAND_DOWN_VIDEO = (byte)0xC1;      //显示屏下发视频录制配置信息

    public static final byte COMMAND_DELETE_RECORD_DATA = (byte)0xC6;      //显示屏下发删除核准证、路线、电子围栏

    public static final byte COMMAND_QUERY_UPDATE_NUM = (byte)0xC5;      //查询内屏已接收包总数指令应答

    public static final byte COMMAND_DOWN_CAMERA = (byte)0xC2;      //显示屏下发摄像头指定显示指令

    public static final byte COMMAND_DOWN_HISTORY_VIDEO_LOOK = (byte)0xC3;      //显示屏下发视频回放指令（选择时间段和通道）（数据第一位为标志位，标记访问、暂停、继续播放、关闭）

    public static final byte COMMAND_DOWN_DATA_SYNC_ANSWER = (byte)0xAB;   //显示屏下发数据同步指令

    public static final byte COMMAND_DOWN_DATA_SYNC = (byte)0xC4;   //显示屏下发数据同步指令

    public static final byte COMMAND_QUERY_BASIC_DATA = (byte)0xC7;   //内屏上报本地版本等信息到记录仪

    public static final int COMMAND_MESSAGE_START_POSTION = 8;      //消息体开始位置

    public static final byte COMMAND_ANSWER_SUCESS = (byte)0x00;      //上传或下发成功

    public static final byte COMMAND_ANSWER_FAIL = (byte)0x01;      //上传或下发失败

    public static final byte COMMAND_ANSWER_VERSION_EQUAL = (byte)0x2;      //版本一样，不需升级

    public static final int UPDATE_SHOW_INFO = 0x66;      //更新显示消息信息

    //    public static final byte PROTOCOL_VERSION_1 = (byte)0x04;      //版本号
    public static final byte PROTOCOL_VERSION_1 = (byte)0x00;      //版本号

    public static final byte [] COMMAND_COMMON_ANSWER_LENGTH = {0x00,0x03};  //通用应答三位长度

    public static final byte [] COMMAND_QUERY_UPDATE_DATA_LENGTH = {0x00,0x28};  //通用应答三位长度


    public static final String RAIL_TYPE_ROUND = "1";       //圆形

    public static final String RAIL_TYPE_SQUARE = "2";      //矩形

    public static final String RAIL_TYPE_MULTILAT = "3";    //多边型

    public static final int TAKE_PHOTO_REQUEST_CODE = 100;  //相机请求权限参数

    public static final String PERMISSION_CAMERA = "android.permission.CAMERA";

    public static final String MULTIPLE_FILE_NAME_HEAD = "multiple_state.txt";    //综合信息文件名前缀

    public static final String RAIL_FILE_NAME_HEAD = "rail_";    //围栏文件名前缀

    public static final String ROAD_FILE_NAME_HEAD = "road_";    //路线文件名前缀

    public static final String CARD_FILE_NAME_HEAD = "card_";    //核准证文件名前缀

    public static final String NOTICE_FILE_NAME_HEAD = "notice_";    //通知文件名前缀

    public static final String UPGRADE_FILE_NAME_HEAD = "/file_";    //升级文件名前缀  update_file/file_

    public static final  String  UPGRADE_FILE_NAME_HEAD_1 = "update_file/";              // 升级文件名前缀  update_file/file_

    public static final String CONFIG_FILE_NAME_CONFIG = "configInfoEntity.txt";   //配置文件名

    public static final String UPDATE_CONFIG_50_FILE_NAME = "updateConfigEntity_50.txt";   //配置文件名

    public static final String UPDATE_CONFIG_100_FILE_NAME = "updateConfigEntity_100.txt";   //配置文件名

    public static final String SETTING_VIDEO_CONFIG = "setting_video.txt";   //录像和监控备份文件

    public static final String VIDEO_LOOK_CONFIG = "video_look.txt";   //视频回看备份文件

    public static final  String  U_UPGRADE_PATH = "/symt1.01.16.apk";      //U盘升级安装目录  /mnt/sdcard/symt1.01.12.apk symt1.01.16

    public static final int SOURCE_SETTING = 0;     //跳转到设置

    public static final int SOURCE_ES_FILEMANAGER = 1;     //跳转到ES文件浏览器

    public static final int SOURCE_QUERY_LOG = 2;     //查看日志

    public static final int SOURCE_U_UPGRADE = 3;     //默认安装U盘根目录升级包

    public static final int SOURCE_CLEAR_RECORD_DEVICE_DATA = 4;     //删除记录仪所有数据

    public static final int SOURCE_UPGRADE_PASSWORD = 0;     //升级和查看日志密码

    public static final int SOURCE_OTHER_PASSWORD = 1001;     //删除记录仪所有数据

    public static final int SOURCE_MANAGER_PASSWORD = 6677;     //升级和查看日志密码

    public static final byte VIDEO_LOOK_VISIT = (byte)0x01;       //访问

    public static final byte VIDEO_LOOK_PAUSE = (byte)0x02;      //暂停

    public static final byte VIDEO_LOOK_CONTINUE = (byte)0x03;       //继续播放

    public static final byte VIDEO_LOOK_CLOSE = (byte)0x04;      //关闭

    public static final byte VIDEO_LOOK_QUICK = (byte)0x05;      //快进

    public static final byte [] CAMERA_CLOSE = {(byte)0x00};      //关闭打开的摄像头


    public static final int GPS_NO_SIGNAL_YES = 1;      //GPS无信号
    public static final int ECU_CHEAT_YES = 1;           //1：ECU作弊
    public static final int CAR_UP_CHEAT_YES = 1;   //1：举升作弊
    public static final int CAR_OPEN_CHEAT_YES = 1;    //1：开关箱作弊

    public static final int CAR_EMPTY_CHEAT_YES = 1;   //1：空重车作弊
    public static final int CAR_SMUGGLE_YES = 1;   //1：偷运
    public static final int ROAD_ERROR_YES = 1;   //1：路线偏离报警

    public static final String MANAGER_000 = "000";    //自由模式
    public static final String MANAGER_100 = "100";    //管控模式1
    public static final String MANAGER_010 = "010";    //管控模式2
    public static final String MANAGER_110 = "110";    //管控模式3
    public static final String MANAGER_111 = "111";    //表示模式7


    public static final int  PAULIN_STATE_0 = 0;    //篷布状态0:打开
    public static final int  PAULIN_STATE_1 = 1;    //篷布状态 1：关闭

    public static final int  LIFT_STATE_1 = 1;    //举升状态1：举升
    public static final int  LIFT_STATE_0 = 0;    //举升状态 0：未举升
    // 00：空车,01:半载,10:保留,11:满载
    public static final String  LOAD_STATE_00 = "00";
    public static final String  LOAD_STATE_01 = "01";
    public static final String  LOAD_STATE_11 = "11";

    //00=未锁车， 01=平台锁车 10=本地锁车   11=平台锁车且本地锁车
    public static final String  PLAT_LOCK_STATE_00 = "00";
    public static final String  PLAT_LOCK_STATE_01 = "01";
    public static final String  PLAT_LOCK_STATE_10 = "10";
    public static final String  PLAT_LOCK_STATE_11 = "11";

    //00=未限速  01=平台限速  10=本地限速  11=本地限速且平台限速
    public static final String  LIMIT_SPEED_STATE_00 = "00";
    public static final String  LIMIT_SPEED_STATE_01 = "01";
    public static final String  LIMIT_SPEED_STATE_10 = "10";
    public static final String  LIMIT_SPEED_STATE_11 = "11";

    //00=未限举  01=平台限举 10=本地限举  11=平台限举且本地限举
    public static final String  CAR_UP_STATE_00 = "00";
    public static final String  CAR_UP_STATE_01 = "01";
    public static final String  CAR_UP_STATE_10 = "10";
    public static final String  CAR_UP_STATE_11 = "11";

    public static final int  FINGER_STATE_YES = 1;    //平台开启指纹管控 1：有 0：无

    public static final int  FINGER_STATE_NO = 0;    //平台开启指纹管控 1：有 0：无


    public static final int  D0_0 = 0 ;    //0=ACC关
    public static final int  D0_1 = 1 ;    //1=ACC开

    public static final int  D1_0 = 0;    // 0= 左转弯灯未开
    public static final int  D1_1 = 1;    // 1=左转弯灯开

    public static final int  D2_0 = 0;    //0 =右转弯灯关
    public static final int  D2_1 = 1;    //1=右转弯灯开

    public static final int  D3_0 = 0;    //0=正常行驶
    public static final int  D3_1 = 1;    //1=倒车

    public static final int  D4_0 = 0;    //D4
    public static final int  D4_1 = 1;    //D4
    public static final int  D5_0 = 0;    //D4;    //D5
    public static final int  D5_1 = 1;    //D4
    public static final int  D6_0 = 0;    //D4;    //D6
    public static final int  D6_1 = 1;    //D4

    public static final int  D7_0 = 0;    //0=未刹车
    public static final int  D7_1 = 1;    //1=刹车

    public static final String  PLAT_CONNECT_0 = "0";   //0：网络不通；
    public static final String  PLAT_CONNECT_1 = "1";   //1:平台连接成功 ；
    public static final String  PLAT_CONNECT_2 = "2";   //2：注册失败；
    public static final String  PLAT_CONNECT_3 = "3";   //3：鉴权失败
    public static final int  FINGER_RESULT_YES = 1;    //0：指纹验证通过 1：指纹验证失败
    public static final int  FINGER_RESULT_NO = 0;    //0：指纹验证通过 1：指纹验证失败


    public static final String  RAIL_AREA_ROUND = "1";       //1：圆形；
    public static final String  RAIL_AREA_SQUARE = "2";      //2：矩形；
    public static final String  RAIL_AREA_MULTILAT = "3";    //3：多边形；

    public static final String  RAIL_TYPE_1 = "1";   //1：停车场
    public static final String  RAIL_TYPE_2 = "2";   //2：工地
    public static final String  RAIL_TYPE_3 = "3";   //3：消纳场
    public static final String  RAIL_TYPE_4 = "4";   //4：禁区
    public static final String  RAIL_TYPE_5 = "5";   //5：限速圈；

    public static final String  SERIAL_CONNECT_SUCESS = "串口连接成功";

    public static int GetDeviceDataCycle = 1200;//请求设备数据周期

    public static final String SETTING_CHANNEL_SELECTE = "1";   //被选中

    public static final String RECEIVE_DATA_PROTOCOL_HEAD_3 = "0XAA=0X75=0X00";  //字符串匹配

    public static final int MESSAGE_INFO_ALL = 0;      //查看所有日志

    public static final int UPDATE_LOCATION_INFO = 500;      //查看所有日志

    public static final String WATER_PUMP_START = "1"; //水泵启动
    public static final String WATER_PUMP_STOP = "0"; //水泵停止

    public static final String TURRET_ANGEL_NEGTIVE = "1"; //转塔角度为负

    public static final String ARM_SPREAD_1 = "1";//臂架展开
    public static final String ARM_SPREAD_0 = "0";//臂架未展开
    public static final String ARM_SHRINK_1 = "1";//臂架收缩
    public static final String ARM_SHRINK_0 = "0";//臂架未收缩

    public static final String Turret_Hit_ok = "1"; //塔台对中
    public static final String Turret_Hit_fail = "0"; //塔台未对中

    public static final String Urgent_Stop_ok = "1"; //紧急停止
    public static final String Urgent_Stop_fail = "0"; //未紧急停止

    public static final String Water_Pump_ok = "1"; //水泵运行OK
    public static final String Water_Pump_fail = "0"; //水泵运行

    public static final String FOAM_LEVEL_0000 = "0000";    //空罐
    public static final String FOAM_LEVEL_0001 = "0001";    //1/8罐
    public static final String FOAM_LEVEL_0010 = "0010";    //2/8罐
    public static final String FOAM_LEVEL_0011 = "0011";    //3/8罐
    public static final String FOAM_LEVEL_0100 = "0100";    //4/8罐
    public static final String FOAM_LEVEL_0101 = "0101";    //5/8罐
    public static final String FOAM_LEVEL_0110 = "0110";    //6/8罐
    public static final String FOAM_LEVEL_0111 = "0111";    //7/8罐
    public static final String FOAM_LEVEL_1000 = "1000";    //满罐

    public static final String WATER_LEVEL_0000 = "0000";    //空罐
    public static final String WATER_LEVEL_0001 = "0001";    //1/8罐
    public static final String WATER_LEVEL_0010 = "0010";    //2/8罐
    public static final String WATER_LEVEL_0011 = "0011";    //3/8罐
    public static final String WATER_LEVEL_0100 = "0100";    //4/8罐
    public static final String WATER_LEVEL_0101 = "0101";    //5/8罐
    public static final String WATER_LEVEL_0110 = "0110";    //6/8罐
    public static final String WATER_LEVEL_0111 = "0111";    //7/8罐
    public static final String WATER_LEVEL_1000 = "1000";    //满罐

    public static final String REMOTE_CTRL_STATUS_0 = "0";    //未启动
    public static final String REMOTE_CTRL_STATUS_1 = "1";    //启动
    public static final String REMOTE_CTRL_STATUS_2 = "2";    //急停

    public static final String BATTARY_STATUS_0 = "0";    //电池状态0
    public static final String BATTARY_STATUS_1 = "1";    //电池状态1
    public static final String BATTARY_STATUS_2 = "2";    //电池状态2
    public static final String BATTARY_STATUS_3 = "3";    //电池状态3
    public static final String BATTARY_STATUS_4 = "4";    //电池状态4

    public static final String WIRELESS_LINK_0 = "0"; //断开
    public static final String WIRELESS_LINK_1 = "1"; //无线连接
    public static final String WIRELESS_LINK_2 = "2"; //有线连接



    public static final String ERR_CODE_0 = "0";    //无
    public static final String ERR_CODE_1 = "1";    //禁止臂架动作
    public static final String ERR_CODE_2 = "2";    //禁止支腿动作
    public static final String ERR_CODE_3 = "3";    //发动机测速故障
    public static final String ERR_CODE_4 = "4";    //机械式编码器故障
    public static final String ERR_CODE_5 = "5";    //旋转左限位
    public static final String ERR_CODE_6 = "6";    //旋转右限位
    public static final String ERR_CODE_7 = "7";    //驾驶室超高干涉
    public static final String ERR_CODE_8 = "8";    //横向角度过大，不能调平
    public static final String ERR_CODE_9 = "9";    //油温过高
    public static final String ERR_CODE_10 = "10";  //液压油位过低
    public static final String ERR_CODE_11 = "11";  //纵向角度过大，不能调平
    public static final String ERR_CODE_12 = "12";  //电子水平仪故障
    public static final String ERR_CODE_13 = "13";  //到达臂架安全边界
    public static final String ERR_CODE_14 = "14";  //没有零点标定
    public static final String ERR_CODE_15 = "15";  //线路故障
    public static final String ERR_CODE_16 = "16";  //臂架倾角传感器故障
    public static final String ERR_CODE_17 = "17";  //2臂臂架干涉
    public static final String ERR_CODE_18 = "18";  //3臂臂架干涉
    public static final String ERR_CODE_19 = "19";  //4臂臂架干涉
    public static final String ERR_CODE_20 = "20";  //5臂臂架干涉
    public static final String ERR_CODE_21 = "21";  //分动箱测速故障
    public static final String ERR_CODE_22 = "22";  //紧急停止
    public static final String ERR_CODE_23 = "23";  //超速故障
    public static final String ERR_CODE_24 = "24";  //臂架达到最远距离
    public static final String ERR_CODE_25 = "25";  //臂架未完全展开
    public static final String ERR_CODE_26 = "26";  //控制器1通信故障
    public static final String ERR_CODE_27 = "27";  //档位挂错
    public static final String ERR_CODE_28 = "28";  //升速故障
    public static final String ERR_CODE_29 = "29";  //压力传感器故障
    public static final String ERR_CODE_30 = "30";  //油位传感器故障
    public static final String ERR_CODE_31 = "31";  //分动箱润滑油压力低
    public static final String ERR_CODE_32 = "32";  //温度传感器故障
    public static final String ERR_CODE_33 = "33";  //控制器2通信故障
    public static final String ERR_CODE_34 = "34";  //控制器3通信故障
    public static final String ERR_CODE_35 = "35";  //显示屏故障
    public static final String ERR_CODE_36 = "36";  //支腿离地故障
    public static final String ERR_CODE_37 = "37";  //稳定受限，谨防倾翻
    public static final String ERR_CODE_38 = "38";  //1臂展开角度偏小，限制旋转角度
    public static final String ERR_CODE_39 = "39";  //偏离零位角度过大，禁止1臂下压
    public static final String ERR_CODE_40 = "40";  //水罐液位传感器故障
    public static final String ERR_CODE_41 = "41";  //请先展开6臂
    public static final String ERR_CODE_42 = "42";  //6臂臂架干涉
    public static final String ERR_CODE_43 = "43";  //自动收臂，请将1臂抬至70度
    public static final String ERR_CODE_44 = "44";  //6臂过收报警
    public static final String ERR_CODE_45 = "45";  //风力过大，请收回臂架，停止作业
    public static final String ERR_CODE_46 = "46";  //风速传感器故障
    public static final String ERR_CODE_47 = "47";  //泡沫罐液位传感器故障
    public static final String ERR_CODE_48 = "48";  //臂架靠近高压线警告

    public static final String STATE_00 = "00";   //中间位置

    public static final String STATE_10 = "10";   //上面位置

    public static final String STATE_01 = "01";   //下面位置

    public static final String STATE_100 = "100";   //中间位置

    public static final String STATE_010 = "010";   //上面位置

    public static final String STATE_001 = "001";   //下面位置

}
