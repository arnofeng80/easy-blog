package com.arno.blog.framework.utils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 增強StringUtils
 *
 * @author
 */
public final class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final String LOCAL_HOST_IP = "127.0.0.1";

//    /**
//     * Excel科學計數法關鍵字
//     */
//    private static final String EXCEL_F_E = "E";

    private StringUtils() {
    }

    /**
     * 底線轉駝峰
     */
    public static String upperTable(String str) {
        StringBuilder result = new StringBuilder();
        String[] a = str.split("_");
        for (String s : a) {
            if (!str.contains("_")) {
                result.append(s);
                continue;
            }
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 駝峰轉底線
     */
    public static String upperCharToUnderLine(String param) {
        StringBuilder sb = new StringBuilder(param);
        int temp = 0;
        if (!param.contains("_")) {
            for (int i = 0; i < param.length(); i++) {
                if (Character.isUpperCase(param.charAt(i))) {
                    sb.insert(i + temp, "_");
                    temp += 1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 獲得用戶遠端地址
     */
    public static String getRemoteIp(HttpServletRequest request) {

        String remoteAddr = request.getRemoteAddr();
        String remoteIp = request.getHeader("X-Forwarded-For");
        if (isBlank(remoteIp) || LOCAL_HOST_IP.equals(remoteIp)) {
            remoteIp = request.getHeader("X-Real-IP");
        }
        if (isBlank(remoteIp) || LOCAL_HOST_IP.equals(remoteIp)) {
            remoteIp = request.getHeader("Proxy-Client-IP");
        }
        if (isBlank(remoteIp) || LOCAL_HOST_IP.equals(remoteIp)) {
            remoteIp = request.getHeader("WL-Proxy-Client-IP");
        }
        String ip = remoteIp != null ? remoteIp : remoteAddr;

        int pos = ip.indexOf(',');
        if (pos >= 0) {
            ip = ip.substring(0, pos);
        }

        return ip;
    }

    /**
     * 替換集合中的字串
     *
     * @param str
     * @param beforeList
     * @param after
     * @return
     */
    public static String getReplaced(String str, List<String> beforeList, String after) {
        String result = trim(str);
        if (StringUtils.isNotBlank(result)) {
            for (String before : beforeList) {
                result = StringUtils.replace(result, before, after);
            }
        }
        return result;
    }

    /**
     * 獲取列對應的下標字母
     */
    public static String getExcelCellIndex(int index) {
        StringBuilder rs = new StringBuilder();
        while (index > 0) {
            index--;
            rs.insert(0, ((char) (index % 26 + 'A')));
            index = (index - index % 26) / 26;
        }
        return rs.toString();
    }

    public static String toString(Object obj) {
        return Objects.toString(obj, "");
    }

    public static Boolean equal(Object obj1, Object obj2) {
        return toString(obj1).equals(toString(obj2));
    }

    /**
     * 把中文轉成Unicode碼
     *
     * @param str 字元
     * @return 轉碼後的字元
     */
    public static String chinaToUnicode(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int chr1 = str.charAt(i);
            // 漢字範圍 \u4e00-\u9fa5 (中文)
            if (chr1 >= 19968 && chr1 <= 171941) {
                result.append("\\u").append(Integer.toHexString(chr1));
            } else {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }

    /**
     * 判斷字串是否是連結
     *
     * @param href
     * @return
     */
    public static Boolean isHref(String href) {
        boolean foundMatch;
        try {
            foundMatch = href.matches("(?sm)^https?://[-\\w+&@#/%=~_|$?!:,.\\\\*]+$");
        } catch (Exception e) {
            return false;
        }
        return foundMatch;
    }

//    /**
//     * 清除bom
//     *
//     * @param str
//     * @return
//     */
//    public static String clearUtf8bm4(String str) {
//        if (isEmpty(str)) {
//            return "";
//        }
//        return str.replaceAll("(?sm)[^\u0000-\uD7FF\uE000-\uFFFF]", "");
//    }

//    public static void assertString(String object, String objectName) {
//        Assert.notNull(object, objectName + " is null");
//        Assert.hasLength(object.trim(), objectName + " is empty");
//    }

//    /**
//     * 字串是否包含字串集合中的某一個字串
//     *
//     * @param str      字串
//     * @param contains 被包含字串集合
//     * @return
//     */
//    public static boolean stringContainsList(String str, List<String> contains) {
//        for (String item : contains) {
//            if (str.contains(item)) {
//                return true;
//            }
//        }
//        return false;
//    }

//    /**
//     * 判斷字串是否包含陣列中的某一項
//     */
//    public static boolean stringContainsArr(String uri, String[] arr) {
//        for (String s : arr) {
//            if (uri.contains(s)) {
//                return true;
//            }
//        }
//        return false;
//    }

//    /**
//     * 判斷字串是否包含陣列中的某一項，不區分大小寫
//     */
//    public static boolean stringContainsArrIgnoreCase(String uri, String[] arr) {
//        for (String s : arr) {
//            if (uri.toLowerCase().contains(s.toLowerCase())) {
//                return true;
//            }
//        }
//        return false;
//    }

//    /**
//     * 獲取異常堆疊資訊
//     *
//     * @param e
//     * @return
//     */
//    public static String getStackTraceInfo(Throwable e) {
//        StringWriter writer = new StringWriter();
//        e.printStackTrace(new PrintWriter(writer));
//        return writer.getBuffer().toString();
//    }

//    /**
//     * 獲取指定包名的異常
//     *
//     * @param e
//     * @return
//     */
//    public static String getPackageException(Throwable e, String packageName) {
//        String exception = getStackTraceInfo(e);
//        StringBuilder returnStr = new StringBuilder();
//        Pattern pattern = Pattern.compile("^.*(" + packageName + "|Exception:|Cause).*$", Pattern.MULTILINE);
//        Matcher matcher = pattern.matcher(exception);
//        while (matcher.find()) {
//            returnStr.append(matcher.group()).append("\n");
//        }
//        return returnStr.toString();
//    }

    /**
     * 物件是否為無效值
     *
     * @param obj 要判斷的物件
     * @return 是否為有效值（不為null 和 "" 字串）
     */
    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || "".equals(obj.toString());
    }

    /**
     * 參數是否是有效整數
     *
     * @param obj 參數（物件將被調用string()轉為字串類型）
     * @return 是否是整數
     */
    public static boolean isInt(Object obj) {
        if (isNullOrEmpty(obj)) {
            return false;
        }
        if (obj instanceof Integer) {
            return true;
        }
        return obj.toString().matches("[-+]?\\d+");
    }

    /**
     * 判斷一個物件是否為boolean類型,包括字串中的true和false
     *
     * @param obj 要判斷的物件
     * @return 是否是一個boolean類型
     */
    public static boolean isBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return true;
        }
        String strVal = String.valueOf(obj);
        return "true".equalsIgnoreCase(strVal) || "false".equalsIgnoreCase(strVal);
    }

    /**
     * 物件是否為true
     *
     * @param obj
     * @return
     */
    public static boolean isTrue(Object obj) {
        return "true".equals(String.valueOf(obj));
    }

    public static boolean matches(String regex, String input) {
        return (null != regex && null != input) && Pattern.matches(regex, input);
    }

//    /**
//     * 公用脫敏
//     *
//     * @param bankNumber 需要脫敏的字串
//     * @param startNum   保留前幾位
//     * @param endNum     保留後幾位
//     */
//    public static String hideStr(String bankNumber, int startNum, int endNum) {
//        if (isEmpty(bankNumber)) {
//            return "";
//        } else if (bankNumber.length() < (startNum + endNum)) {
//            return bankNumber;
//        } else {
//            StringBuilder temp = new StringBuilder();
//            if (startNum > 0) {
//                temp.append(bankNumber, 0, startNum);
//            }
//            for (int i = 0; i < bankNumber.length() - startNum - endNum; i++) {
//                temp.append("*");
//            }
//            temp.append(bankNumber.substring(bankNumber.length() - endNum));
//            return temp.toString();
//        }
//    }

//    /**
//     * 生成指定位數的亂數
//     *
//     * @return
//     */
//    public static String getRandom(int num) {
//        Random random = new Random();
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < num; i++) {
//            result.append(random.nextInt(10));
//        }
//        return result.toString();
//    }

//    /**
//     * double to String 防止科學計數法
//     *
//     * @param value
//     * @return
//     */
//    public static String doubleToString(Double value) {
//        String temp = value.toString();
//        if (temp.contains(EXCEL_F_E)) {
//            BigDecimal bigDecimal = new BigDecimal(temp);
//            temp = bigDecimal.toPlainString();
//        }
//        return temp;
//    }

}

