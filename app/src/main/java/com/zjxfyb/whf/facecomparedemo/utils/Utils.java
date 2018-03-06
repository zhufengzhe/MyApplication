package com.zjxfyb.whf.facecomparedemo.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Utils {

	/**
	 * String转MD5
	 * 
	 * @author wangxiaofeng
	 * @date 2017-5-10 21:15:22
	 * @param string
	 *            md5str 需要转MD5的字符串
	 * @return
	 * 
	 *         *注 公用方法，尽量不要修改，若需要修改请联系作者
	 * 
	 */
	public static String getMd5(String md5str) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(md5str.getBytes());
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 根据要求string转MD5
	 * 
	 * @author wangxiaofeng
	 * @date 2017-5-11 10:43:34
	 * @param md5str
	 *            需要转MD5的字符串
	 * @param direction
	 *            方向 固定参数 左=left，右=right
	 * @param n
	 *            从左/右截取几位
	 * @return str
	 * 
	 *         *注 公用方法，尽量不要修改，若需要修改请联系作者
	 * 
	 */
	public static String getMd5(String md5str, String direction, int n) {
		String str = "";
		if (notNull(md5str)) {
			md5str = getMd5(md5str);
			if (md5str.length() > n && n > 0) {// 截取数不能超过MD5长度,n不能小于0
				if ("left".equals(direction)) {
					str = md5str.substring(0, n);
				} else if ("right".equals(direction)) {
					str = md5str
							.substring(md5str.length() - n, md5str.length());
				}
			}

		}

		return str;
	}

	/**
	 * 将str串转MD5,并从左边数截取第几位（返回单个字符串）
	 * 
	 * @author wangxiaofeng
	 * @date 2017-5-11 10:48:41
	 * @param md5str
	 *            需要转MD5的字符串
	 * @param n
	 *            从左边数 截取第几位,第一位下标为0
	 * @return str
	 * 
	 *         *注 公用方法，尽量不要修改，若需要修改请联系作者
	 * 
	 */
	public static String getMd5(String md5str, int n) {
		String str = "";
		if (notNull(md5str)) {

			if (md5str.length() > n && n > 0) {// 截取数不能超过MD5长度,n不能小于0
				str = md5str.substring(n - 1, n);
			}

		}

		return str;
	}
	/**
	 * 生成签名
	 * @param param1
	 * @param param2
	 * @return
	 */
	public static String getSign(String param1, String param2){
	    String str = "";
	    if (notNull(param1) && notNull(param2)) {
    	    Calendar calendar = Calendar.getInstance(); 
    	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    	    
            String left = getMd5(param1, "left", 5);
    	    String date = format.format(calendar.getTime());
    	    String center = getMd5(date, "right", 7);
    	    String right = getMd5(param2, "right", 5);
    	    str = left + center + right;
	    }
	    return str;
	}
    
	/**
	 * 生成签名
	 * @param param
	 * @return
	 */
    public static String getSign(String param){
        String str = "";
        if (notNull(param)) {
            Calendar calendar = Calendar.getInstance(); 
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            
            String left = getMd5(param, "left", 10);
            String date = format.format(calendar.getTime());
            String center = getMd5(date, "right", 7);
            str = left + center;
        }
        return str;
    }

	/**
	 * 
	 * 字符串比对 返回boolean结果
	 * 
	 * @author wangxiaofeng
	 * @date 2017-5-11 10:55:21
	 * @param str1
	 * @param str2
	 * @return boolean str=str return true
	 * 
	 *         *注 公用方法，尽量不要修改，若需要修改请联系作者
	 * 
	 */
	public static boolean strmatch(String str1, String str2) {

		boolean fal = false;

		if (notNull(str1) && notNull(str2)) {
			fal = str1.equals(str2);
		}

		return fal;
	}

	/**
	 * 对象是否为空判断
	 * 
	 * @author wangxiaofeng
	 * @date 2017-5-11 10:57:26
	 * @param String
	 * @return
	 * 
	 *         *注 公用方法，尽量不要修改，若需要修改请联系作者
	 * 
	 */
	public static boolean notNull(String str) {

		return str != null && !"".equals(str);

		// 后续会丰富此方法内容

	}

	public static boolean notNull(Object obj) {

		return obj != null;

		// 后续会丰富此方法内容

	}

	/**
	 * 
	 * 生成指定位数全数字验证码
	 * 
	 * @author wangxiaofeng
	 * @date 2017-5-10 16:49:35
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @param n
	 *            几位验证码
	 * @return String code
	 * 
	 *         *注：若有修改请在下面注明
	 * 
	 */
	public static String randomCodeByNum(int min, int max, int n) {
		int[] result = new int[n];
		int count = 0;
		StringBuffer code = new StringBuffer();
		while (count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;

			// for (int j = 0; j < n; j++){打开注释可去重
			// if(num == result[j]){
			// flag = false;
			// break;
			// }
			// }

			if (flag) {
				result[count] = num;
				code.append(num);
				count++;
			}

		}
		return code.toString();
	}

	/**
	 * 日志公用方法
	 * 
	 * @return
	 */
//	public static Logger log() {
//		Logger log = Logger.getLogger("vmcard");
//		return log;
//	}

	/**
	 * 判断字符串是否为数字
	 */
	public static boolean isNumber(String str) {
		if (!notNull(str)) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 将字符串转换为数字(long)
	 * @param str
	 * @return long
	 */
	public static long toLong(String str) {
	    long l = 0l;
	    if (notNull(str)) {
	        l = Long.parseLong(str);
	    }
	    return l;
	}
	
	private static String trim(String str, String stripChars, int mode) {
		if (str == null) {
			return null;
		}
		int length = str.length();
		int start = 0;
		int end = length;
		// 扫描字符串头部
		if (mode <= 0) {
			if (stripChars == null) {
				while ((start < end)
						&& (Character.isWhitespace(str.charAt(start)))) {
					start++;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end)
						&& (stripChars.indexOf(str.charAt(start)) != -1)) {
					start++;
				}
			}
		}
		// 扫描字符串尾部
		if (mode >= 0) {
			if (stripChars == null) {
				while ((start < end)
						&& (Character.isWhitespace(str.charAt(end - 1)))) {
					end--;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end)
						&& (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
					end--;
				}
			}
		}
		if ((start > 0) || (end < length)) {
			return str.substring(start, end);
		}
		return str;
	}

	public static String trimEnd(String str, String stripChars) {
		return trim(str, stripChars, 1);
	}
	
	/* 取得根目录下的指定目录 */
	public static String getFilePath(String virtualPath) {
		String filePath = System.getProperty("ddw-interface.root") + virtualPath;//ServletActionContext.getServletContext().getRealPath(virtualPath);
		filePath = trimEnd(filePath, File.separator);
		if(virtualPath.contains("."))
			return filePath;
		else 
			return filePath+ File.separator;
	}
	
	
}
