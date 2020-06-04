package com.tgr.springbootmybatis.util.card;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//import zipkin2.internal.DateUtil;

import java.util.Date;


@Component
public class GenerateID {


    // ==============================Fields===========================================
    /** 开始时间截 (2019-01-01) */
    private final long twepoch = 1567685260470L;
//  private final long twepoch = 1420041600000L;

    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;
//    private final long workerIdBits = 1L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;
//    private final long datacenterIdBits = 1L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    @Value("${workerId}")
    private long workerId;

    /** 数据中心ID(0~31) */
    @Value("${datacenterId}")
    private long datacenterId;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    //==============================Constructors=====================================
    /**
     * 构造函数
     */
    public GenerateID() {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
//        this.workerId = workerId;
//        this.datacenterId = datacenterId;
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized String nextId() {
        long timestamp = System.currentTimeMillis();//1578915770536

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return String.valueOf(((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence);
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    //==============================Test=============================================
    /** 测试 */
    public static void main(String[] args)  throws  Exception{
        //GenerateID idWorker = new GenerateID(0, 2);
        for (int i = 0; i < 10; i++) {
            //long id = idWorker.nextId();
            //System.out.println(LaserUtils.binaryStringToHexString(Long.toBinaryString(id)));


            //System.out.println(new Date().getTime());
            //System.out.println(DateUtil.format(DateUtil.format("20190101","yyyyMMdd")));


        }
    }

    public void parseInfo(String id) {
        id = Long.toBinaryString(Long.parseLong(id));
        int        len           = id.length();
        JSONObject jsonObject    = new JSONObject();
        long       sequenceStart = len < workerIdShift ? 0 : len - workerIdShift;
        long       workerStart   = len < datacenterIdShift ? 0 : len - datacenterIdShift;
        long       timeStart     = len < timestampLeftShift ? 0 : len - timestampLeftShift;
        String     sequence      = id.substring((int)sequenceStart, len);
        String     workerId      = sequenceStart == 0 ? "0" : id.substring( (int)workerStart, (int)sequenceStart);
        String     dataCenterId  = workerStart == 0 ? "0" : id.substring((int)timeStart,  (int)workerStart);
        String time = timeStart == 0 ? "0" : id.substring(0, (int)timeStart);
        int sequenceInt = Integer.valueOf(sequence, 2);
        //System.out.println(sequence);
        //System.out.println("sequence:"+sequenceInt);
        int workerIdInt = Integer.valueOf(workerId, 2);
        //System.out.println(workerId);
        //System.out.println("workerId:"+ workerIdInt);
        int dataCenterIdInt = Integer.valueOf(dataCenterId, 2);
        //System.out.println(dataCenterId);
        //System.out.println("dataCenter:"+dataCenterIdInt);
        long diffTime = Long.parseLong(time, 2);
        //System.out.println(diffTime);
        System.out.println(time+" "+dataCenterId +" "+workerId+" "+sequence);
        long timeLong = diffTime + twepoch;
        //String date = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss SSS",new Date(timeLong));
//        jsonObject.put("date", date);
        //System.out.print("生成时间："+date);
        System.out.print("  数据中心："+dataCenterId);
        System.out.print("  工作ID："+workerId);
        System.out.println("  序列号："+sequence);
    }
}

