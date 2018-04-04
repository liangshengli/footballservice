package com.football.util;

import com.football.domain.PushIO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
@Controller
@RequestMapping(value = "/pushio")
public class Test {

    public static void main(String[] args) {
//        System.out.println(getSafeUrl(Date2TimeStamp("2018-03-30 23:59:59", "yyyy-MM-dd HH:mm:ss")));
    }

    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * @Description:获取推流地址
     * @param:[key(推流防盗链Key), streamId(直播码), txTime(unix格式的时间戳)]
     * @return java.lang.String
     * @Date:  2018-3-30 14:01下午
     * @Author:王陈
     *
     */
    @RequestMapping("/getpushiourl")
    @ResponseBody
    public   String getSafeUrl(String times) {
        Long txTime = Date2TimeStamp(times, "yyyy-MM-dd HH:mm:ss");
        String bizid = "22060";
        String ss = getrandomdate();
        System.out.println("时间戳："+ss);
        String streamId = bizid+"_"+ss;
        String key = "f92faa2d5ac3aa01a4604ece0b6745a3";
        String input = new StringBuilder().
                append(key).
                append(streamId).
                append(Long.toHexString(txTime).toUpperCase()).toString();

        String txSecret = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            txSecret  = byteArrayToHexString(
                    messageDigest.digest(input.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

       String  pushurl = txSecret == null ? "" :
               new StringBuilder().
                       append("rtmp://").append(bizid).append(".livepush.myqcloud.com/live/").append(streamId).append("?bizid=").
                       append(bizid).append("&").
                       append("txSecret=").
                       append(txSecret).
                       append("&").
                       append("txTime=").
                       append(Long.toHexString(txTime).toUpperCase()).
                       toString();
        String playurl = txSecret == null ? "" :
                new StringBuilder().
                        append("rtmp://").append(bizid).append(".livepush.myqcloud.com/live/").append(streamId).append("?bizid=").
                        append(bizid).
                        toString();

        return "{\"pushurl\":\""+pushurl+"\",\"playurl\":\""+playurl+"\"}";
    }

    private static String byteArrayToHexString(byte[] data) {
        char[] out = new char[data.length << 1];

        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }


    /**
     * @Description:根据所传的日期和日期格式 获取unix时间戳
     * @param:[dateStr, format]
     * @return java.lang.Long
     * @Date:  2018-3-30 14:02下午
     * @Author:王陈
     *
     */
    public static Long Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private static String  getrandomdate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        return df.format(new Date());
    }
}


