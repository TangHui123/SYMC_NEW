package cn.com.sany.symc.zg.util;


import java.nio.ByteBuffer;
import java.util.ArrayList;

public class NumberBytes {
//2bytes change to char
    public static char bytesToChar(byte[] b) {
        char c = (char) ((b[0] << 8) & 0xFF00L);
        c |= (char) (b[1] & 0xFFL);
        return c;
    }


    public static double bytesToDouble(byte[] b) {
        return Double.longBitsToDouble(bytesToLong(b));
    }


    public static float bytesToFloat(byte[] b) {
        return Float.intBitsToFloat(bytesToInt(b));
    }


    public static int bytesToInt(byte[] b) {
        int i = (b[0] << 24) & 0xFF000000;
        i |= (b[1] << 16) & 0xFF0000;
        i |= (b[2] << 8) & 0xFF00;
        i |= b[3] & 0xFF;
        return i;
    }
    public static int bytesToIntLow(byte[] b) {
        int i = (b[3] << 24) & 0xFF000000;
        i |= (b[2] << 16) & 0xFF0000;
        i |= (b[1] << 8) & 0xFF00;
        i |= b[0] & 0xFF;
        return i;
    }
    public static int TwobytesToInt(byte[] b) {//���ģʽ
        int i = (b[0] << 8) & 0xFF00;
        i |= b[1] & 0xFF;
        return i;
    }

    public static long bytesToLong(byte[] b) {
        long l = ((long) b[0] << 56) & 0xFF00000000000000L;

        l |= ((long) b[1] << 48) & 0xFF000000000000L;
        l |= ((long) b[2] << 40) & 0xFF0000000000L;
        l |= ((long) b[3] << 32) & 0xFF00000000L;
        l |= ((long) b[4] << 24) & 0xFF000000L;
        l |= ((long) b[5] << 16) & 0xFF0000L;
        l |= ((long) b[6] << 8) & 0xFF00L;
        l |= (long) b[7] & 0xFFL;
        return l;
    }


    public static byte[] charToBytes(char c) {
        byte[] b = new byte[8];
        b[0] = (byte) (c >>> 8);
        b[1] = (byte) c;
        return b;
    }


    public static byte[] doubleToBytes(double d) {
        return longToBytes(Double.doubleToLongBits(d));
    }


    public static byte[] floatToBytes(float f) {
        return intToBytes(Float.floatToIntBits(f));
    }


    public static byte[] intToBytes(int i) {
        byte[] b = new byte[4];
        b[0] = (byte) (i >>> 24);
        b[1] = (byte) (i >>> 16);
        b[2] = (byte) (i >>> 8);
        b[3] = (byte) i;
        return b;
    }
    public static byte[] intTo2Bytes(int i) {
        byte[] b = new byte[2];
        b[0] = (byte) (i >>> 8);
        b[1] = (byte) i;
        return b;
    }

    public  static byte[] arrayListToBytes(ArrayList al)
    {
        byte[] b = new byte[al.size()];
        for(int i=0;i<al.size();i++)
        {
            b[i]=(byte)al.get(i);
        }
        return b;
    }
    public static byte[] longToBytes(long l) {
        byte[] b = new byte[8];
        b[0] = (byte) (l >>> 56);
        b[1] = (byte) (l >>> 48);
        b[2] = (byte) (l >>> 40);
        b[3] = (byte) (l >>> 32);
        b[4] = (byte) (l >>> 24);
        b[5] = (byte) (l >>> 16);
        b[6] = (byte) (l >>> 8);
        b[7] = (byte) (l);
        return b;
    }


    private static ByteBuffer buffer = ByteBuffer.allocate(8);

    public static void main(String[] args) {

        //测试 int 转 byte
        int int0 = 234;
        byte byte0 = intToByte(int0);
        System.out.println("byte0=" + byte0);//byte0=-22
        //测试 byte 转 int
        int int1 = byteToInt(byte0);
        System.out.println("int1=" + int1);//int1=234



        //测试 int 转 byte 数组
        int int2 = 1417;
        byte[] bytesInt = intToByteArray(int2);
        System.out.println("bytesInt=" + bytesInt);//bytesInt=[B@de6ced
        //测试 byte 数组转 int
        int int3 = byteArrayToInt(bytesInt);
        System.out.println("int3=" + int3);//int3=1417


        //测试 long 转 byte 数组
        long long1 = 2223;
        byte[] bytesLong = longToBytes(long1);
        System.out.println("bytes=" + bytesLong);//bytes=[B@c17164
        //测试 byte 数组 转 long
        long long2 = bytesToLong(bytesLong);
        System.out.println("long2=" + long2);//long2=2223
    }


    //byte 与 int 的相互转换
    public static byte intToByte(int x) {
        return (byte) x;
    }

    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    //byte 数组与 int 的相互转换
    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    /**
     * 二进制字符串转byte
     */
    public static byte decodeBinaryString(String byteStr) {
        int re, len;
        if (null == byteStr) {
            return 0;
        }
        len = byteStr.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理
            if (byteStr.charAt(0) == '0') {// 正数
                re = Integer.parseInt(byteStr, 2);
            } else {// 负数
                re = Integer.parseInt(byteStr, 2) - 256;
            }
        } else {// 4 bit处理
            re = Integer.parseInt(byteStr, 2);
        }
        return (byte) re;
    }

    /**
     *
     * 日期格式字符串转成BCD[6]的byte[]
     *
     */
    public static byte[] timeToBCD(String time){
        byte[] bcd = ASCII_To_BCD(time.getBytes(), time.length());
        return bcd;
    }

    private static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd= new byte[asc_len / 2];
        if(asc_len%2==1)
            bcd = new byte[(asc_len+1) / 2];
        int j = 0;
        for (int i = 0; i < bcd.length; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    private static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

    
    public static byte getXor(byte[] datas){

        byte temp=datas[0];

        for (int i = 1; i <datas.length -1; i++) {
            temp ^=datas[i];
        }

        return temp;
    }

    /**
     * 不包括校验位的datas里面的实际长度
     * @param datas   里面存储的需要校验的实际数据
     * @param length:datas 里面存储的需要校验的实际数据长度
     * @return
     *
     */
    public static byte getXorByte(byte[] datas,int length){

        byte temp=datas[0];
        if(length<datas.length){
            for (int i = 1; i < length; i++) {
                temp ^=datas[i];
            }
        }

        return temp;
    }



    /**
     *
     * 字节数组节转换成二进制字符串
     * @param byteArray
     * @return
     *
     */
    public static String byteArrayToBinaryString(byte[] byteArray) {
        String str = "";
        for (int i = 0; i < byteArray.length; i++)
        {
            str = str + Integer.toBinaryString(byteArray[i] & 0xFF).toUpperCase() + ",";

        }
        return str.toString();
    }


    /**
     *
     * 字节数组转换成十六进制字符串
     * @param byteArray
     * @return
     *
     */
    public static String byteArrayToHexStr(byte[] byteArray){
        String h = "";
        for(int i = 0; i < byteArray.length; i++){
            String temp = Integer.toHexString(byteArray[i] & 0xFF).toUpperCase();
            if(temp.length() == 1){
                temp = "(byte)0x0" + temp;
            }else{
                temp = "(byte)0x" + temp;
            }
            h = h + ","+ temp;
        }

        return h;
    }

    /**
     *
     * 字节数组转换成十六进制字符串
     * @param byteArray
     * @return
     *
     */
    public static String byteArrayToHexstr(byte[] byteArray,int length){
        String h = "";
        for(int i = 0; i < length; i++){
            String temp = Integer.toHexString(byteArray[i] & 0xFF).toUpperCase();
            if(temp.length() == 1){
                temp = "0x0" + temp;
            }else{
                temp = "0x" + temp;
            }
            h = h + "="+ temp;
        }

        return h.toUpperCase();
    }


    static byte[] crc8_tab = { (byte) 0, (byte) 94, (byte) 188, (byte) 226, (byte) 97, (byte) 63, (byte) 221, (byte) 131, (byte) 194, (byte) 156, (byte) 126, (byte) 32, (byte) 163, (byte) 253, (byte) 31, (byte) 65, (byte) 157, (byte) 195, (byte) 33, (byte) 127, (byte) 252, (byte) 162, (byte) 64, (byte) 30, (byte) 95, (byte) 1, (byte) 227, (byte) 189, (byte) 62, (byte) 96, (byte) 130, (byte) 220, (byte) 35, (byte) 125, (byte) 159, (byte) 193, (byte) 66, (byte) 28, (byte) 254, (byte) 160, (byte) 225, (byte) 191, (byte) 93, (byte) 3, (byte) 128, (byte) 222, (byte) 60, (byte) 98, (byte) 190, (byte) 224, (byte) 2, (byte) 92, (byte) 223, (byte) 129, (byte) 99, (byte) 61, (byte) 124, (byte) 34, (byte) 192, (byte) 158, (byte) 29, (byte) 67, (byte) 161, (byte) 255, (byte) 70, (byte) 24,
            (byte) 250, (byte) 164, (byte) 39, (byte) 121, (byte) 155, (byte) 197, (byte) 132, (byte) 218, (byte) 56, (byte) 102, (byte) 229, (byte) 187, (byte) 89, (byte) 7, (byte) 219, (byte) 133, (byte) 103, (byte) 57, (byte) 186, (byte) 228, (byte) 6, (byte) 88, (byte) 25, (byte) 71, (byte) 165, (byte) 251, (byte) 120, (byte) 38, (byte) 196, (byte) 154, (byte) 101, (byte) 59, (byte) 217, (byte) 135, (byte) 4, (byte) 90, (byte) 184, (byte) 230, (byte) 167, (byte) 249, (byte) 27, (byte) 69, (byte) 198, (byte) 152, (byte) 122, (byte) 36, (byte) 248, (byte) 166, (byte) 68, (byte) 26, (byte) 153, (byte) 199, (byte) 37, (byte) 123, (byte) 58, (byte) 100, (byte) 134, (byte) 216, (byte) 91, (byte) 5, (byte) 231, (byte) 185, (byte) 140, (byte) 210, (byte) 48, (byte) 110, (byte) 237,
            (byte) 179, (byte) 81, (byte) 15, (byte) 78, (byte) 16, (byte) 242, (byte) 172, (byte) 47, (byte) 113, (byte) 147, (byte) 205, (byte) 17, (byte) 79, (byte) 173, (byte) 243, (byte) 112, (byte) 46, (byte) 204, (byte) 146, (byte) 211, (byte) 141, (byte) 111, (byte) 49, (byte) 178, (byte) 236, (byte) 14, (byte) 80, (byte) 175, (byte) 241, (byte) 19, (byte) 77, (byte) 206, (byte) 144, (byte) 114, (byte) 44, (byte) 109, (byte) 51, (byte) 209, (byte) 143, (byte) 12, (byte) 82, (byte) 176, (byte) 238, (byte) 50, (byte) 108, (byte) 142, (byte) 208, (byte) 83, (byte) 13, (byte) 239, (byte) 177, (byte) 240, (byte) 174, (byte) 76, (byte) 18, (byte) 145, (byte) 207, (byte) 45, (byte) 115, (byte) 202, (byte) 148, (byte) 118, (byte) 40, (byte) 171, (byte) 245, (byte) 23, (byte) 73, (byte) 8,
            (byte) 86, (byte) 180, (byte) 234, (byte) 105, (byte) 55, (byte) 213, (byte) 139, (byte) 87, (byte) 9, (byte) 235, (byte) 181, (byte) 54, (byte) 104, (byte) 138, (byte) 212, (byte) 149, (byte) 203, (byte) 41, (byte) 119, (byte) 244, (byte) 170, (byte) 72, (byte) 22, (byte) 233, (byte) 183, (byte) 85, (byte) 11, (byte) 136, (byte) 214, (byte) 52, (byte) 106, (byte) 43, (byte) 117, (byte) 151, (byte) 201, (byte) 74, (byte) 20, (byte) 246, (byte) 168, (byte) 116, (byte) 42, (byte) 200, (byte) 150, (byte) 21, (byte) 75, (byte) 169, (byte) 247, (byte) 182, (byte) 232, (byte) 10, (byte) 84, (byte) 215, (byte) 137, (byte) 107, 53 };

    /**
     * 计算数组的CRC8校验值
     *
     * @param data
     *            需要计算的数组
     * @return CRC8校验值
     */
    public static byte calcCrc8(byte[] data) {
        return calcCrc8(data, 0, data.length, (byte) 0);
    }

    /**
     * 计算CRC8校验值
     *
     * @param data
     *            数据
     * @param offset
     *            起始位置
     * @param len
     *            长度
     * @return 校验值
     */
    public static byte calcCrc8(byte[] data, int offset, int len) {
        return calcCrc8(data, offset, len, (byte) 0);
    }

    /**
     * 计算CRC8校验值
     *
     * @param data
     *            数据
     * @param offset
     *            起始位置
     * @param len
     *            长度
     * @param preval
     *            之前的校验值
     * @return 校验值
     */
    public static byte calcCrc8(byte[] data, int offset, int len, byte preval) {
        byte ret = preval;
        for (int i = offset; i < (offset + len); ++i) {
            ret = crc8_tab[(0x00ff & (ret ^ data[i]))];
        }
        return ret;
    }



}