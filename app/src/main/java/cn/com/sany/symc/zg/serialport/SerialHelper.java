package cn.com.sany.symc.zg.serialport;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.serialport.SerialPort;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import cn.com.sany.symc.zg.entity.DownDataEntity;
import cn.com.sany.symc.zg.entity.MultipleStateInfo;
import cn.com.sany.symc.zg.help.IConstant;
import cn.com.sany.symc.zg.ui.MainActivity;
import cn.com.sany.symc.zg.util.CacheData;
import cn.com.sany.symc.zg.util.LogUtil;
import cn.com.sany.symc.zg.util.NumberBytes;



/**
 *
 * @author benjaminwan
 * 串口工具类
 *
 */
public class SerialHelper {

	//    private boolean debug = true;
	private boolean debug = false;

	public static final String TAG = "SerialHelper.class";
	private SerialPort mSerialPort;
	private OutputStream mOutputStream;
	private InputStream mInputStream;



	private String sPort="/dev/ttyS1";   //外放:mcu2ttyS1  深圳:ttyS1
	//	private int iBaudRate = 115200;
	private int iBaudRate = 115200;
	private boolean _isOpen=false;



	// 每次收到实际长度
	int available = 0;
	Handler mHandler;
	private ReadSerialDataThread mReadThread;
	Timer timer2;
	TimerTask task2;

	private int cameraCnt = 0;////
	private MainActivity mainActivity = new MainActivity();////

	private Context context;
	private SharedPreferences sp_rail;
	private byte temp;
	private byte [] temp_1 = new byte[1];
	private byte [] temp_3 = new byte[3];
	private byte [] temp_2 = new byte[2];
	private byte [] temp_4 = new byte[4];
	private byte [] temp_6 = new byte[6];
	private byte [] temp_12 = new byte[12];
	private byte [] temp_16 = new byte[16];
	private byte [] temp_20 = new byte[20];
	private byte [] temp_32 = new byte[32];
	private byte [] temp_64 = new byte[64];

	private byte[] send_common = new byte[12];


	private String rail_id = "";

	//接收本地缓存区间
	private byte[] buffer = new byte[2048];  //2048
	private int nowBufferLength = 0; //buffer中实际存放数据的长度
	private byte[] temp_buffer = new byte[2048];  //2048  2500  5000

	private byte[] sendBuffer=new byte[2048];
	private int sendLenth=0;
	private boolean issend=false;
	private static int sendtag = 0;

	//内屏升级 start
	private String update_pkg_id = "";
	private int countPkg = 0;
	private int currentPkg = 0;
	private String md5 = "";
	private String version_code = "";
	//内屏升级 end

	//综合状态信息
	private MultipleStateInfo multipleStateInfo = new MultipleStateInfo();


	//配置信息
	//private ConfigInfoEntity configInfoEntity = new ConfigInfoEntity();

	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	private static final int CORE_POOL_SIZE = CPU_COUNT+1;  // CPU_COUNT + 1
	private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 +1;  //CPU_COUNT * 2 + 1;
	private static final int KEEP_ALIVE = 1;
	private static final BlockingQueue<Runnable> sPoolWorkQueue =
			new LinkedBlockingQueue<Runnable>(128);  //128

	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);

		public Thread newThread(Runnable r) {
			return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
		}
	};

	public static final Executor threadPoolExecutor
			= new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
			TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);


	/**
	 * 构造函数
	 * @param sPort
	 * @param iBaudRate
	 *
	 */
	public SerialHelper(String sPort, int iBaudRate){
		this.sPort = sPort;
		this.iBaudRate=iBaudRate;
	}

	/**
	 * 构造函数
	 * @param mHandler
	 *
	 */
	public SerialHelper(Handler mHandler, Context context){

//		this("/dev/ttyS1",115200);   //ttyS1  mcu2ttyS1
		this("/dev/ttyS1",115200);   //ttyS1  mcu2ttyS1
		this.mHandler= mHandler;
		this.context = context;
	}


	/**
	 *
	 * 打开串口、并进行初始化、启动写数据、读数据双工线程* @param uHandler
	 *
	 */
	public void open() throws SecurityException, IOException,InvalidParameterException {


		if (!debug){
			File f = new File(sPort);
			mSerialPort = new SerialPort(f, iBaudRate, 0);
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();
			//如果传的是第一包、则update_file文件夹下的所有升级文件
			sp_rail = context.getSharedPreferences("symt_shy_rail",context.MODE_PRIVATE);
		}


		mReadThread = new ReadSerialDataThread(mHandler);
		mReadThread.setPriority(Thread.MAX_PRIORITY);
		threadPoolExecutor.execute(mReadThread);


		CacheData.setMsg_info("串口连接成功"+sPort+":"+ String.valueOf(iBaudRate),0);

		_isOpen=true;

	}



	/**
	 *
	 * 保存到缓存
	 * @param
	 *
	 */
	private void saveDownDataEntity(byte command,byte serial_id){
		DownDataEntity downDataEntity = new DownDataEntity();
		downDataEntity.setCommand(command);
		downDataEntity.setSerial_id(serial_id);
		downDataEntity.setTime(new Date());
		//LogUtil.d(TAG,"===============saveDownDataEntity===saveDownDataEntity==========getCommand=====getSerial_id======================>" +"" + NumberBytes.byteToInt(command) + NumberBytes.byteToInt(serial_id));
//		CacheData.operateDownDataEntityMap("" + NumberBytes.byteToInt(command) + NumberBytes.byteToInt(serial_id),downDataEntity,true);
	}


	/**
	 *
	 * 往串口发送数据封装封装
	 * @param
	 *
	 */
	public synchronized boolean sendDataInfo(byte command,byte version,byte serial_id,byte[] content_length,byte[] content,boolean flag){
		try{

			//flag为true：表示通用应答,否则表示下发指令消息
			if(flag){
				send_common[0] = 0x55;
				send_common[1] = 0x7A;
				send_common[2] = version;
				send_common[3] = 0x00; // command  to
				send_common[4] = 0x00;
				send_common[5] = serial_id;
				send_common[6] = content_length[0];
				send_common[7] = content_length[1];
				System.arraycopy(content, 0, send_common,8,content.length);
				//增加校验
				send_common[8 + content.length ] =  NumberBytes.getXor(send_common);

//				LogUtil.d(TAG,"====往linnux发送的数据=======sendDataInfo====00====byteArrayToHexStr========>" + NumberBytes.byteArrayToHexStr(send_common));
//				CacheData.setMsg_info("发送的数据为====linux=====成功===byteArrayToBinaryString==="+ NumberBytes.byteArrayToHexStr(send_common),0);

				sendData(send_common);
			}else{
				byte [] send_other = new byte[9 + content.length];
				send_other[0] = 0x55;
				send_other[1] = 0x7A;
				send_other[2] = version;
				send_other[3] = command;
				send_other[4] = 0x00;
				send_other[5] = serial_id;
				send_other[6] = content_length[0];
				send_other[7] = content_length[1];
				System.arraycopy(content, 0, send_other,8,content.length);
				//增加校验
				send_other[8 + content.length ] =  NumberBytes.getXorByte(send_other,8 + content.length);

				//LogUtil.d(TAG,"====往linnux发送的数据=======send_other===11=====byteArrayToHexStr========>" + NumberBytes.byteArrayToHexStr(send_other));
				//CacheData.setMsg_info("发送的数据为====linux=====成功===byteArrayToBinaryString==="+ NumberBytes.byteArrayToHexStr(send_other),0);

				sendData(send_other);
			}
			return true;

		} catch (Exception e){
			LogUtil.e(TAG,"===================sendDataInfo===========================>"+e.getMessage());
			return false;
		}
	}



	/**
	 *
	 * 往串口发送数据
	 *
	 */
	public void send(byte[] bOutArray){
		try
		{
			mOutputStream.write(bOutArray);

			//LogUtil.d(TAG,"====往linnux发送的数据=======send=========发送成功了=====发送成功了=====>" + NumberBytes.byteArrayToBinaryString(bOutArray));

		} catch (Exception e){
			LogUtil.e(TAG,"===============往串口发送数据===========send===============>"+e.getMessage());
		}
	}

	/**
	 *
	 * 获取要发送的数据
	 * @return
	 *
	 */
	private byte[] getbLoopData()
	{
		issend=true;
		byte[] data=new byte[sendLenth];
		System.arraycopy(sendBuffer, 0, data, 0, sendLenth);
		sendLenth=0;
		issend=false;
		return data;
	}


	/**
	 *
	 * 发送数据
	 * @param data
	 *
	 */
	private void sendData(byte[] data){
		while(true)
		{
			try {
				if (issend||sendtag>0)
					Thread.sleep(10);
				else {
					sendtag++;
					System.arraycopy(data, 0, sendBuffer, sendLenth, data.length);
					sendLenth+=data.length;
					sendtag--;
					break;
				}
			}catch (Exception e)
			{
				LogUtil.e(TAG,"===================sendData===========================>"+e.getMessage());
				CacheData.setMsg_info("=====sendData：========发送数据================>" + e.getMessage(),IConstant.MESSAGE_INFO_ALL);
			}

		}
	}


	/**
	 *
	 * 从串口读取数据线程
	 *
	 */
	private class ReadSerialDataThread extends Thread {

		Handler uHandler;
		public ReadSerialDataThread(Handler uiHandler)
		{
			uHandler=uiHandler;
		}
		@Override
		public void run() {
			super.run();

			while(!isInterrupted()) {
				try{
					try {

						// CacheData.setMsg_info("=====00==========ReadSerialDataThread======读取数据前，先往linux发送数据",IConstant.MESSAGE_INFO_ALL);
						//LogUtil.d(TAG,"接收的数据为====ReadSerialDataThread====异常测试=============11111======================>");
						if(!debug){
							if(sendLenth!=0) {
								byte[] data1 = getbLoopData();
								send(data1);
								// CacheData.setMsg_info("=====11==========ReadSerialDataThread======读取数据前，先往linux发送数据",IConstant.MESSAGE_INFO_ALL);
								// LogUtil.d(TAG,"====ReadSerialDataThread====往linux发送数据完成==========22222======================>" + NumberBytes.byteArrayToHexStr(data1));
							}

							if(mInputStream == null) {
								return;
							}


							//CacheData.setMsg_info("=====22==========ReadSerialDataThread======读取数据前，先往linux发送数据",IConstant.MESSAGE_INFO_ALL);
							// LogUtil.d(TAG,"接收的数据为====ReadSerialDataThread====异常测试=============33333======================>");
							//每次收到的数据实际长度
							available = mInputStream.available();

							//CacheData.setMsg_info("====33=========ReadSerialDataThread===nowBufferLength====available===读取数据前，先往linux发送数据" + nowBufferLength + available,IConstant.MESSAGE_INFO_ALL);
							// LogUtil.d(TAG,"接收的数据为====ReadSerialDataThread====异常测试=============4444======================>");
							//已经大于缓冲区长度、直接返回
							if(nowBufferLength + available > buffer.length){
//							CacheData.setMsg_info("====33=========ReadSerialDataThread==========reset==data==0============",IConstant.MESSAGE_INFO_ALL);
								System.arraycopy(temp_buffer,0,buffer,0,buffer.length);
								nowBufferLength = 0;
								continue;
								//return;
							}
						}else{
							available =100;
						}

						if (available > 0) {



							byte[] readData = {(byte)0xAA,(byte)0x75,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x37,
									(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00, (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00, (byte)0x00,
									(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00, (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00, (byte)0x00,
									(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00, (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00, (byte)0x00,
									(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00, (byte)0x00,(byte)0x00,(byte)0x01,(byte)0x81, (byte)0x00,
									(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x00, (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00, (byte)0x00,
									(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00, (byte)0x00,(byte)0x00,(byte)0x01,(byte)0x81, (byte)0x00,
									(byte)0x00,(byte)0x00,(byte)0x69
							};


							if(debug){
								available = readData.length;
							}else{
								readData = new byte[available];
								mInputStream.read(readData,0,available);
							}

							//CacheData.setMsg_info("=====ReadSerialDataThread==test====22222====开始接收的数据==readData=：==>" +NumberBytes.byteArrayToHexStr(readData),IConstant.MESSAGE_INFO_ALL);
//						   CacheData.setMsg_info("=====ReadSerialDataThread====开始接收的数据=========================readData=：==>",IConstant.MESSAGE_INFO_ALL);
//						   LogUtil.d(TAG,"接收的数据为====ReadSerialDataThread====开始接收的数据=============555555======================>" + NumberBytes.byteArrayToHexStr(readData));

						 //  CacheData.setMsg_info("=====ReadSerialDataThread==test===66666====开始接收的数据==readData=：==>" +NumberBytes.byteArrayToHexStr(readData),IConstant.MESSAGE_INFO_ALL);

							//把数据拷入缓冲区
							System.arraycopy(readData,0,buffer,nowBufferLength,available);
							nowBufferLength = nowBufferLength + available;

							//解析缓冲区数据中符合要求的数据
							if(nowBufferLength >= 8){//8
							//	CacheData.setMsg_info("===========0===================",1);
								for(int i=0;i<nowBufferLength-8;){        //  -8
									//判断前面三位是否符合协议的前三位、不符合移动下一位比较
									boolean flag1 = (buffer[i] == IConstant.RECEIVE_DATA_PROTOCOL_HEAD_1);  //0xAA
									boolean flag2 = (buffer[i+1] == IConstant.RECEIVE_DATA_PROTOCOL_HEAD_2);
									boolean flag3 = (buffer[i+2] == IConstant.PROTOCOL_VERSION_1);
								//	CacheData.setMsg_info("===========i=========="+i,1);
								//	CacheData.setMsg_info("===========buffer[i]=========="+buffer[i],1);
								//	CacheData.setMsg_info("===========buffer[i+1]=========="+buffer[i+1],1);
								//	CacheData.setMsg_info("===========buffer[i+2]=========="+buffer[i+2],1);
									if(flag1 && flag2 && flag3){   //andy_zhushi _2018_04_27
								//		CacheData.setMsg_info("===========1===================",1);
										//从串口读取到的数组
										System.arraycopy(buffer,i+6,temp_1,0,1);//2
										final int low_length = bytes2int(temp_1);
										System.arraycopy(buffer,i+7,temp_1,0,1);//2
										final int high_length = bytes2int(temp_1);
										final int content_length = (high_length * 256) + low_length;

//									   System.arraycopy(buffer,i+6,temp_2,0,2);//2
//									   final int content_length = bytes2int(temp_2);  //数据体长度,temp_2
									//	CacheData.setMsg_info("发送的数据为====ReadSerialDataThread===数据体长度content_length===========>：" + content_length,0);
									//	CacheData.setMsg_info("发送的数据为====ReadSerialDataThread===nowBufferLength-i=========>："+ (nowBufferLength-i),0);
//									   CacheData.setMsg_info("发送的数据为====ReadSerialDataThread===i=========>："+ i,0);
//									   CacheData.setMsg_info("发送的数据为====ReadSerialDataThread===nowBufferLength=========>：" + nowBufferLength,0);

										//长度不够，等从linux读取下一包、数据完整了再解析
										if(nowBufferLength-i< content_length + 9){//9
										//		CacheData.setMsg_info("===========2===================",1);
												// nowBufferLength = 0;//////////// limiao add
												break; //退出当前循环
											}else{
											final byte [] packageData = new byte[content_length + 9]; //整个协议包的长度、包括校验位,9
											System.arraycopy(buffer,i,packageData,0,content_length + 9);//9
										//	CacheData.setMsg_info("===========3===================",1);
											// LogUtil.d(TAG,"发送的数据为====ReadSerialDataThread=====44==readData===byteArrayToBinaryString========>："+ NumberBytes.byteArrayToHexStr(packageData));
											//CacheData.setMsg_info("=====ReadSerialDataThread=====runnable===calcCrc8========calcCrc8=：==>"+ NumberBytes.calcCrc8(packageData,0,packageData.length-1),IConstant.MESSAGE_INFO_ALL);

											//CacheData.setMsg_info("=====ReadSerialDataThread=====runnable===calcCrc8========packageData[packageData.length -1]=：==>"+ packageData[packageData.length -1],IConstant.MESSAGE_INFO_ALL);

											//进行异或和验证、验证通过执行
//											boolean flag = true;
//											if(flag){
											if(NumberBytes.calcCrc8(packageData,0,packageData.length-1) == packageData[packageData.length -1]){
											  // CacheData.setMsg_info("================xor success===============",1);
//										   boolean aa = true;
//										   if(aa){
//												CacheData.setMsg_info("================xor success===============",1);
												//  LogUtil.d(TAG,"发送的数据为====ReadSerialDataThread====getXor===readData===dealCommandDetail========>：  ==/n");
												//  CacheData.setMsg_info("=====ReadSerialDataThread=====getXor===dealCommandDetail====11====开始接收的数据========readData=：==>",IConstant.MESSAGE_INFO_ALL);


												dealCommandDetail(packageData, content_length);
//
//													   }catch (Exception e){
//														   e.printStackTrace();
//														   LogUtil.d(TAG,"发送的数据为====ReadSerialDataThread====Exception==00=====>" + e.toString());
//													   }
//												   }
//											   };
//											   threadPoolExecutor.execute(runnable);

												nowBufferLength = nowBufferLength - (i+content_length + 9);//9
//											   CacheData.setMsg_info("====after deal===nowBufferLength===========>：" + nowBufferLength,0);
												//处理完后、buffer往前移
												System.arraycopy(buffer,i+content_length + 9,buffer,0,nowBufferLength);//9
												//System.arraycopy(temp_buffer,0,buffer,nowBufferLength,buffer.length-nowBufferLength);

												i=0;
											}else{
												// i++;  //andy_zhushi _2018_04_27
											//	CacheData.setMsg_info("===========4===================",1);
												//优化匹配速度 start
//											   String buffer_now_str = NumberBytes.byteArrayToHexstr(buffer,nowBufferLength);
//											  // int length = buffer_now_str.indexOf(IConstant.RECEIVE_DATA_PROTOCOL_HEAD_3);
//											   int length = buffer_now_str.indexOf(IConstant.RECEIVE_DATA_PROTOCOL_HEAD_3,i+3);
//											   if(length<0) {
//												   break;
//											   }else{
//												   i = (length+i+3)/5;
//											   }
												//优化匹配速度 end
												//校验不过,丢掉,往前移动
												nowBufferLength = nowBufferLength - (i+content_length + 9);
												System.arraycopy(buffer,i+content_length + 9,buffer,0,nowBufferLength);//9
												i=0;

											}

										}

									}else{
									//	CacheData.setMsg_info("===========5===================",1);
										//nowBufferLength = 0;//////////// limiao add
										//优化匹配速度 start
										String buffer_now_str = NumberBytes.byteArrayToHexstr(buffer,nowBufferLength);
										// CacheData.setMsg_info("===========5============buffer_now_str======="+buffer_now_str,1);
										int length = buffer_now_str.indexOf(IConstant.RECEIVE_DATA_PROTOCOL_HEAD_3);
									//	CacheData.setMsg_info("===========5===========length========"+length,1);

										if(length<0) {
											break;
										}else{
											i = length/5;
										}
										//优化匹配速度 end

										//i++;  //andy_zhushi _2018_04_27

									}

								}

							}


						}

					}catch (Exception e) {
						e.printStackTrace();
					//	LogUtil.d(TAG,"发送的数据为====ReadSerialDataThread====Exception===11====>" + e.getMessage());
					//	CacheData.setMsg_info("=====ReadSerialDataThread====开始接收的数据==readData==available：==>" + e.toString(),IConstant.MESSAGE_INFO_ALL);
					}

					try{
						Thread.sleep(50); //延时
					}catch (InterruptedException e){
						e.printStackTrace();
					}
				} catch (Exception e){
				//	LogUtil.d(TAG,"发送的数据为====ReadSerialDataThread====Exception===22====>" + e.getMessage());
				//	CacheData.setMsg_info("=====ReadSerialDataThread====开始接收的数据==readData==available：==>" + e.toString(),IConstant.MESSAGE_INFO_ALL);
					e.printStackTrace();
					return;
				}

			}
		}
	}


	/**
	 *
	 * 处理命令对应的详细数据解析
	 *@param readData:整个协议包、包括从0xAA、0x75到 最后的校验位  content_size:数据体长度
	 *
	 */
	private void dealCommandDetail(byte[] readData,int content_size) throws Exception{

		//版本号
		//byte version = readData[2];
		//命令字
		int command = readData[3];
		//流水号
		int serial_id = readData[5];

		//数据体,处理后content 不包含校验位
		byte[] content = new byte[content_size];
		System.arraycopy(readData,IConstant.COMMAND_MESSAGE_START_POSTION,content,0,content_size);


		//根据命令字处理对应的数据内容
		switch (command){

			//下发的指令通用应答
			case IConstant.COMMAND_ANSWER:{

				//消息下发成功
				if(content.length>2 && IConstant.COMMAND_ANSWER_SUCESS == content[2]){

					//下发成功、通知到UI界面
					if(IConstant.COMMAND_DOWN_CAMERA == content[1] || IConstant.COMMAND_DOWN_HISTORY_VIDEO_LOOK == content[1] ){
						setMessage(content[1],null,null);
					}
				}else {
					//消息下发失败，重新下发
//                   if(content.length>1){
//					   //公共应答处理方法、待写
//					   //通用应答失败
//					   sendCommanAnswer(readData[3],readData[2],readData[5],IConstant.COMMAND_ANSWER_FAIL);
//				   }

				}

				break;
			}

			//综合位置信息上传
			case IConstant.COMMAND_MULTIPLE_POSTION_INFO:{
				dealMultiplePosInfo(readData[3],readData[2],readData[5],content,content_size);
				break;
			}

			//同步应答
			case IConstant.COMMAND_DOWN_DATA_SYNC_ANSWER:{
			//	LogUtil.d(TAG,"------下发消息成功--同步应答----流水号：");

//				deleteDataInfo(readData[3],readData[2],readData[5],content,content_size);
				break;
			}

		}

	}


	/***
	 *
	 * 处理综合位置信息上传
	 *@param command:命令字  serial_id:流水号  content 消息内容
	 *@return true 校验成功   false 校验失败
	 *
	 */
	private boolean dealMultiplePosInfo(byte command,byte version,byte serial_id,byte[] content,int content_size){

		CacheData.setThread_flag(0);	//判断线程一直在运行

		//发送消息,更新UI
		setMessage(IConstant.COMMAND_MULTIPLE_POSTION_INFO,multipleStateInfo,content);

		//LogUtil.d(TAG,"===========initSerial=============start=================dealMultiplePosInfo===end===================");
		//CacheData.setMsg_info("===========initSerial==================start====dealMultiplePosInfo===end=================",1);

		//通用应答成功
		sendCommanAnswer(command,version,serial_id,IConstant.COMMAND_ANSWER_SUCESS);

		return true;
	}


	/**
	 *
	 * 删除字符串中的子字符串
	 * @param  pa_str,son_str
	 *
	 */
	private String stringSub(String pa_str,String son_str){
		StringBuffer sb = new StringBuffer(pa_str);
		int index = pa_str.indexOf(son_str);
		if(index>-1){
			sb.delete(index, index + son_str.length());
		}
		return sb.toString();
	}


	/**
	 *
	 * 发送消息，更新UI
	 * @param what：命令字    object：更新的数据对象
	 *
	 */
	private void setMessage(int what,Object object,byte[] content){
		Message message = new Message();
		message.what = what;
		message.obj = object;
		Bundle bundle = new Bundle();
		bundle.putByteArray("content",content);
		message.setData(bundle);
		mHandler.sendMessage(message);
	}


	/**
	 *
	 * 通用应答
	 * @param command
	 * @param version
	 * @param serial_id
	 * @param message
	 * @return
	 */
	private boolean sendCommanAnswer(byte command,byte version,byte serial_id,byte message){
		temp_3[0] = serial_id;
		temp_3[1] = command;
		temp_3[2] = message;
		return sendDataInfo(command,version,serial_id,IConstant.COMMAND_COMMON_ANSWER_LENGTH,temp_3,true);
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
	 * @param bytes byte[]
	 * @return 转换后的字符串
	 *
	 */
	private int bytes2int(byte[] bytes){
		return new BigInteger(1, bytes).intValue();// 这里的1代表正数
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
	 * 转成浮点型
	 * @param num
	 * @return
	 */
	private String  dateTran(String num){
		return "" + Long.valueOf(num).intValue()*0.000001f;
	}


//    /***
//	 *
//	 * 向串口发送数据方法,主要是下发的视频、同步指令、发送不成功、再发一次
//	 *
//	 */
//	private void startRequestDataTimer(){
//
//	    //发送数据定时器
//		timer2 = new Timer();
//		task2 = new TimerTask() {
//			@Override
//			public void run() {
//
//				try{
//					DownDataEntity downDataEntity = null;
//					for(String key:CacheData.getDownDataEntityMap().keySet()) {
//						downDataEntity = CacheData.getDownDataEntityMap().get(key);
//						long times = (new Date().getTime() - downDataEntity.getTime().getTime()) / 1000;
//						//超过两秒没收到回复，重新下发
//						if (times > 2) {
//							LogUtil.d(TAG,"======Timer===Timer==========getCommand=====getSerial_id==========>"+NumberBytes.byteToInt(downDataEntity.getCommand())
//									+ "" + NumberBytes.byteToInt(downDataEntity.getSerial_id()));
//							CacheData.setMsg_info("======Timer===Timer==========getCommand=====getSerial_id=======>"+NumberBytes.byteToInt(downDataEntity.getCommand())
//									+ "" + NumberBytes.byteToInt(downDataEntity.getSerial_id()) +  rail_id,7);
//							CacheData.operateDownDataEntityMap(""+"" + NumberBytes.byteToInt(downDataEntity.getCommand())
//									+ "" + NumberBytes.byteToInt(downDataEntity.getSerial_id()),null,false);
//							switch(downDataEntity.getCommand()) {
//								//显示屏下发视频录制配置信息
//								case IConstant.COMMAND_DOWN_VIDEO: {
//									sendRecordVideo(NumberBytes.intToByte(CacheData.getSerial_id()),false);
//									break;
//								}
//
//							}
//						}
//
//					}
//
//				}catch (Exception e){
//					LogUtil.e(TAG,"==============startRequestDataTimer====================>"+e.getMessage());
//					e.printStackTrace();
//				}
//			}
//		};
//		timer2.schedule(task2,3000, IConstant.GetDeviceDataCycle);   //10005000
//	}


	public void close(){
		if (mReadThread != null)
			mReadThread.stop();
		if (mSerialPort != null) {
			mSerialPort.close();
			mSerialPort = null;
		}
		_isOpen=false;
	}

}