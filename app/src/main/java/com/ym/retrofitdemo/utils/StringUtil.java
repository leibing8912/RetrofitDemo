package com.ym.retrofitdemo.utils;

import android.content.Context;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作
 */
public class StringUtil {


	//单例
	private static StringUtil instance;

	//上下文
	private static Context mContext;

	/**
	 * 构造函数
	 */
	private StringUtil(Context mContext){
		this.mContext=mContext;
	}


	/**
	 * 单例
	 * @param mContext
	 * @return
	 */
	public static synchronized StringUtil getInstance(Context mContext)
	{
		if(instance==null)
		{
			instance=new StringUtil(mContext);
		}

		return instance;
	}



    /**
     * 判断是否为null或空字符串
     * @param str
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }
    
    /**
     * 判断是否不为null或不是空字符串
     * @param str
     * @return boolean
     */
    public static boolean isNotEmpty(String str){
		if (str == null || str.trim().equals(""))
			return false;
		return true;
    }
    
    /**
     * 判断整型
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
    	   return str.matches("[\\d]+");
    }

    /**
     * //判断小数，与判断整型的区别在与d后面的小数点（红色）
     * @param str
     * @return
     */
	public static boolean isFloatNumber(String str) {
	   return str.matches("[\\d.]+");
	}

    

	/**	
	 * @Description: 验证手机号码是否合法
	 * @param phoneNumber 手机号码
	 * @return 是否合法
	 */
    public static boolean isPhoneNumber(String phoneNumber) {
        String reg = "1[3,4,5,7,8]{1}\\d{9}";
        return phoneNumber.matches(reg);
    }


	/**
	 * @Description: 合法密码，6位以上的数字和字母
	 * @param password 密码
	 * @return 是否合法
	 */
	public static boolean isPassword( String password ){
		Pattern p = Pattern.compile("^([0-9]|[a-zA-Z]){6,}$");
	    Matcher m = p.matcher(password);
	    return m.matches();
	}
	
	/**	
	 * @Description: 验证激活码是否正确
	 * @param activation 激活码
	 * @return 是否合法
	 */
	public static boolean isActivation( String activation ){
		Pattern p = Pattern.compile("^[0-9]{7}$");
	    Matcher m = p.matcher(activation);
	    return m.matches();
	}
	
	/**	
	 * @Description: 验证里程数是否正确
	 * @param mileage 里程数
	 * @return 是否合法
	 */
	public static boolean isMileage( String mileage ){
		Pattern p = Pattern.compile("^[0-9]{1,9}");
	    Matcher m = p.matcher(mileage);
	    return m.matches();
	}
	
	/**	
	 * @Description: 验证短信验证码是否正确
	 * @param vcode 短信验证码
	 * @return 是否合法
	 */
	public static boolean isSMSVcode( String vcode ){
		Pattern p = Pattern.compile("^[0-9]{4}$");
	    Matcher m = p.matcher(vcode);
	    return m.matches();
	}

	
	/**
     * 判断车牌号码是否正确
     */
	 public static boolean isVehicleNo(String vehicleNo){
	    	boolean flag = false ;
			String regEx = "^[\u4e00-\u9fa5][a-zA-Z0-9]{6}$";
			Pattern pattern = Pattern.compile(regEx);
			Matcher matcher = pattern.matcher(vehicleNo);
			if(!matcher.matches()){
//				ExceptionRemind.msg ="输入的车牌号不符合规范";
			}else{
				flag = true;
			}
			return flag;
	}

    /**
     * 判断是否为教练车车牌
     */
    public static boolean isCoachCarsPlate (String plate){
        String regEx = "^[\u4e00-\u9fa5][a-zA-Z0-9]{5}[\u4e00-\u9fa5]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(plate);
        return matcher.matches();
    }

	/**
	 * 校验车牌号是否正确
	 * 校验规：	1.车牌号满5位不能输入
	 * 		 	2.如果少于5位则保存时提示“请输入完整的车牌号
	 * @author swallow
	 * @createTime 2015/7/2
	 * @lastModify 2015/7/2
	 * @param plateNum 车牌号
	 * @return true = 正确; false = 不正确
	 */
	public static boolean isPlateNumRight(String plateNum){
		return !(plateNum.length()<7);
	}

	 
 /**
     * <判断是否为车架号>
     * @param rackNo
     * @return
     */
    public static boolean isRackNo(String rackNo){
    	String reg = "^[0-9a-zA-Z]{17}$";
    	return rackNo.matches(reg);
    }
    /**
     * <判断是否为发动机号>
     * @param enginNo
     * @return
     */
    public static boolean isEnginNo(String enginNo){
    	String reg = "^[0-9a-zA-Z]{6,}$";
    	return enginNo.matches(reg);
    }
    
	/**
	 * 去掉空格换行符
	 */
	public static String replaceBlank(String str) {
	    String dest = "";
	    if (str!=null) {
	        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	        Matcher m = p.matcher(str);
	        dest = m.replaceAll("");
	    }
	    return dest;
	}
	
	/**
	 * 截取一定区间的字符串
	 * @param str 要截取的字符串
	 * @param start 截取开始位置
	 * @param end 截取结束的位置
	 * @return
	 */
	public static String substring(String str, int start, int end){
        if (isEmpty(str)) {
            return "";
        }
        int len = str.length();
        if (start > end) {
            return "";
        }
        if (start > len) {
            return "";
        }
        if (end > len) {
            return str.substring(start, len);
        }
        return str.substring(start,end);
    }
	
	public static int strToInt(String str){
		int i = 0;
		if(StringUtil.isNotEmpty(str)){
			try{
				i = Integer.parseInt(str);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return i;
	}

    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }
    
}
