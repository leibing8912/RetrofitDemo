package com.ym.retrofitdemo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @className : GZIP.java
 * @classDescription : GZIP压缩解压类
 * @author : AIDAN SU
 * @createTime : 2013-10-10
 *
 */
public class GZIP {

    private static String encode = "utf-8";//"ISO-8859-1"

    public String getEncode() {
        return encode;
    }

    /**
     * 设置 编码，默认编码：UTF-8
     *
     * @param encode 字符编码
     */
    public void setEncode(String encode) {
        if(encode !=null && !"".equals(encode.trim())){
            GZIP.encode = encode;
        }
    }

    /**
     *  字符串压缩为字节数组
     *
     * @param target 被压缩字符串
     * @return byte[]
     */
    public static byte[] compressToByte(String target){
        return  compressToByte(target, encode);

    }

    /**
     * 字符串压缩为字节数组
     *
     * @param target 被压缩字符串
     * @param encoding 字符编码
     * @return byte[]
     */
    public static byte[] compressToByte(String target, String encoding){

        if (target == null || target.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(target.getBytes(encoding));

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
      	  try {
				if(gzip != null)gzip.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
      }
        return out.toByteArray();
    }

    /**
     * 字节数组解压缩后返回字符串
     *
     * @param b 被解压字节数组
     * @return String
     */
    public static String unCompressToString(byte[] b) {
        return unCompressToString(b, encode);
    }

    /**
     * 字节数组解压缩后返回字符串
     *
     * @param b 被解压字节数组
     * @param encoding 字符编码
     * @return String
     */
    public static String unCompressToString(byte[] b, String encoding) {
        if (b == null || b.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        try {
            GZIPInputStream gUnZip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gUnZip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}