package com.taxiong.tx_open_platform.util;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.taxiong.tx_open_platform.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串工具类
 */
public class StringUtil {

    private static final int OX03 = 3;

    private static final int OX04 = 4;

    private static final int OX05 = 5;

    private static final int OX06 = 6;

    private static final int OX07 = 7;

    private static final int OX08 = 8;

    private static final int OX09 = 9;

    private static final int OX10 = 16;

    private static final int OX0A = 10;

    private static final int OX0B = 11;

    private static final int OX0C = 12;

    private static final int OX0D = 13;

    private static final int OX0E = 14;

    private static final int OX0F = 15;

    private static final int OX3F = 63;

    private static final int OXFF = 255;

    private static final int THREE = 3;

    private static final int FOUR = 4;

    private static final int FIVE = 5;

    private static final int SIX = 6;

    private static final int SEVEN = 7;

    private static final int TEN = 10;

    private static final int SIXTEEN = 16;

    private static final int SECOND_OF_MINUTE = 60;

    private static final int MILLION_SECOND_OF_SECOND = 1000;

    private static final int MINUTE_OF_HOUR = 60;

    private static final int HOUR_OF_DAY = 24;

    private static final int ONE_THOUSENT_NINE_HUNDRED = 1900;

    private static final int OXF0 = 240;

    private static final int OXC0 = 192;

    private static final int ONE_HUNDRED_EIGTH = 128;

    private static final int LENGTH = 8;

    private static final double ONE_POINT_THREE = 1.3D;

    private static MessageDigest digest = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();

    private static final char[] AMP_ENCODE = "&amp;".toCharArray();

    private static final char[] LT_ENCODE = "&lt;".toCharArray();

    private static final char[] GT_ENCODE = "&gt;".toCharArray();

    private static final int FILL_CHAR = 61;

    private static final String CVT = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";



    public static synchronized String hash(String data) throws NoSuchAlgorithmException {

        if (data == null) {

            return "";

        } else {

            if (digest == null) {

                digest = MessageDigest.getInstance("MD5");

            }


            digest.update(data.getBytes(Charset.forName("UTF-8")));

            return encodeHex(digest.digest());

        }

    }


    public static String encodeHex(byte[] bytes) {

        StringBuilder buf = new StringBuilder(bytes.length * 2);


        for (int i = 0; i < bytes.length; ++i) {

            if ((bytes[i] & 255) < 16) {

                buf.append("0");

            }


            buf.append(Long.toString((long) (bytes[i] & 255), 16));

        }


        return buf.toString();

    }


    public static byte[] decodeHex(String hex) {

        char[] chars = hex.toCharArray();

        byte[] bytes = new byte[chars.length / 2];

        int byteCount = 0;


        for (int i = 0; i < chars.length; i += 2) {

            byte newByte = 0;

            byte var6 = (byte) (newByte | hexCharToByte(chars[i]));

            var6 = (byte) (var6 << 4);

            var6 |= hexCharToByte(chars[i + 1]);

            bytes[byteCount] = var6;

            ++byteCount;

        }


        return bytes;

    }


    private static byte hexCharToByte(char ch) {

        switch (ch) {

        case '0':

            return (byte) 0;

        case '1':

            return (byte) 1;

        case '2':

            return (byte) 2;

        case '3':

            return (byte) 3;

        case '4':

            return (byte) 4;

        case '5':

            return (byte) 5;

        case '6':

            return (byte) 6;

        case '7':

            return (byte) 7;

        case '8':

            return (byte) 8;

        case '9':

            return (byte) 9;

        case ':':

        case ';':

        case '<':

        case '=':

        case '>':

        case '?':

        case '@':

        case 'A':

        case 'B':

        case 'C':

        case 'D':

        case 'E':

        case 'F':

        case 'G':

        case 'H':

        case 'I':

        case 'J':

        case 'K':

        case 'L':

        case 'M':

        case 'N':

        case 'O':

        case 'P':

        case 'Q':

        case 'R':

        case 'S':

        case 'T':

        case 'U':

        case 'V':

        case 'W':

        case 'X':

        case 'Y':

        case 'Z':

        case '[':

        case '\\':

        case ']':

        case '^':

        case '_':

        case '`':

            default:

                return (byte) 0;

        case 'a':

            return (byte) 10;

        case 'b':

            return (byte) 11;

        case 'c':

            return (byte) 12;

        case 'd':

            return (byte) 13;

        case 'e':

            return (byte) 14;

        case 'f':

            return (byte) 15;

        }

    }


    public static String encodeBase64(String data) {

        return encodeBase64((byte[]) data.getBytes(Charset.forName("UTF-8")));

    }


    public static String encodeBase64(byte[] data) {

        int len = data.length;

        StringBuilder ret = new StringBuilder((len / 3 + 1) * 4);


        for (int i = 0; i < len; ++i) {

            int c = data[i] >> 2 & 63;

            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));

            c = data[i] << 4 & 63;

            ++i;

            if (i < len) {

                c |= data[i] >> 4 & 15;

            }


            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));

            if (i < len) {

                c = data[i] << 2 & 63;

                ++i;

                if (i < len) {

                    c |= data[i] >> 6 & 3;

                }


                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));

            } else {

                ++i;

                ret.append(" =");

            }


            if (i < len) {

                c = data[i] & 63;

                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));

            } else {

                ret.append(" =");

            }

        }


        return ret.toString();

    }


    public static String decodeBase64(String data) {

        return decodeBase64((byte[]) data.getBytes(Charset.forName("UTF-8")));

    }


    public static String decodeBase64(byte[] data) {

        int len = data.length;

        StringBuilder ret = new StringBuilder(len * 3 / 4);


        for (int i = 0; i < len; ++i) {

            int c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);

            ++i;

            int c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);

            c = c << 2 | c1 >> 4 & 3;

            ret.append((char) c);

            ++i;

            if (i < len) {

                byte var6 = data[i];

                if (61 == var6) {

                    break;

                }


                c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char) var6);

                c1 = c1 << 4 & 240 | c >> 2 & 240;

                ret.append((char) c1);

            }


            ++i;

            if (i < len) {

                byte var7 = data[i];

                if (61 == var7) {

                    break;

                }


                c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char) var7);

                c = c << 6 & 192 | c1;

                ret.append((char) c);

            }

        }


        return ret.toString();

    }


    public static String replaceWord(String str, String newStr, int len) {

        if (str == null) {

            return str;

        } else {

            char[] charArray = str.toCharArray();

            int sLen = str.length();

            return str.length() < len ? str : str.substring(0, len - 1) + newStr + str.substring(len, sLen);

        }

    }


    public static String replaceBlank(String str, boolean includeSpace) {

        String dest = "";

        if (str != null) {

            String regexIncludeSpace = "\\s*|\t|\r|\n";

            String regexExcludeSpace = "\\t|\r|\n";

            String regex;

            if (includeSpace) {

                regex = regexIncludeSpace;

            } else {

                regex = regexExcludeSpace;

            }


            Pattern p = Pattern.compile(regex);

            Matcher m = p.matcher(str);

            dest = m.replaceAll("");

        }


        return dest;

    }


    public static int wordAtNum(String str, int num) {

        if (str != null && !str.equalsIgnoreCase("")) {

            char ch = str.charAt(num - 1);

            return ch == 49 ? 1 : (ch == 50 ? 2 : (ch == 48 ? 0 : 0));

        } else {

            return 0;

        }

    }


    public static String nullToStr(String str) {

        return str == null ? "" : str.trim();

    }


    public static String replaceChar(String oldStr, int position, char character) {

        StringBuilder stringBuilder = new StringBuilder(oldStr);

        stringBuilder.setCharAt(position, character);

        return stringBuilder.toString();

    }


    public static String isoToGbk(String strIn) {

        String strOut = null;

        if (strIn == null) {

            return "";

        } else {

            try {

                byte[] e = strIn.getBytes("ISO8859_1");

                strOut = new String(e, "GBK");

            } catch (UnsupportedEncodingException var3) {

                var3.printStackTrace();

            }


            return strOut;

        }

    }


    public static String gbkToIso(String strIn) {

        String strOut = null;

        if (strIn == null) {

            return "";

        } else {

            try {

                byte[] b = strIn.getBytes("GBK");

                strOut = new String(b, "ISO8859_1");

            } catch (UnsupportedEncodingException var4) {

                var4.printStackTrace();

            }


            return strOut;

        }

    }


    public static String isoToGB2312(String strIn) {

        String strOut = null;

        if (strIn == null) {

            return "";

        } else {

            try {

                byte[] e = strIn.getBytes("ISO8859_1");

                strOut = new String(e, "GB2312");

            } catch (UnsupportedEncodingException var3) {

                var3.printStackTrace();

            }


            return strOut;

        }

    }


    public static boolean isEmpty(String str) {

        return str == null || str.length() == 0;

    }


    public static boolean isBlank(String str) {

        if (str != null && str.length() != 0) {

            for (int i = 0; i < str.length(); ++i) {

                if (!Character.isWhitespace(str.charAt(i))) {

                    return false;

                }

            }


            return true;

        } else {

            return true;

        }

    }


    public static boolean equals(String str1, String str2) {

        return str1 == null ? str2 == null : str1.equals(str2);

    }


    public static String gb2312ToIso(String strIn) {

        String strOut = null;

        if (strIn == null) {

            return "";

        } else {

            try {

                byte[] b = strIn.getBytes("GB2312");

                strOut = new String(b, "ISO8859_1");

            } catch (UnsupportedEncodingException var4) {

                var4.printStackTrace();

            }


            return strOut;

        }

    }


    public static String gb2312ToUTF(String strIn) {

        String strOut = null;

        if (strIn == null) {

            return "";

        } else {

            try {

                byte[] b = strIn.getBytes("GB2312");

                strOut = new String(b, "UTF-16");

            } catch (UnsupportedEncodingException var4) {

                LOGGER.error(var4.getMessage(), var4);

            }


            return strOut;

        }

    }


    public static String utfToGB2312(String strIn) {

        String strOut = null;

        if (strIn == null) {

            return "";

        } else {

            try {

                byte[] b = strIn.getBytes("UTF-16");

                strOut = new String(b, "GB2312");

            } catch (UnsupportedEncodingException var4) {

                LOGGER.error(var4.getMessage(), var4);

            }


            return strOut;

        }

    }


    public static String replace(String line, String oldString, String newString) {

        if (line == null) {

            return null;

        } else {

            byte i = 0;

            int i1 = line.indexOf(oldString, i);

            if (i1 < 0) {

                return line;

            } else {

                char[] line2 = line.toCharArray();

                char[] newString2 = newString.toCharArray();

                int oLength = oldString.length();

                StringBuilder buf = new StringBuilder(line2.length);

                buf.append(line2, 0, i1).append(newString2);

                i1 += oLength;


                int j;

                for (j = i1; (i1 = line.indexOf(oldString, i1)) > 0; j = i1) {

                    buf.append(line2, j, i1 - j).append(newString2);

                    i1 += oLength;

                }


                buf.append(line2, j, line2.length - j);

                return buf.toString();

            }

        }

    }


    public static String replaceIgnoreCase(String line, String oldString, String newString) {

        if (line == null) {

            return null;

        } else {

            String lcLine = line.toLowerCase();

            String lcOldString = oldString.toLowerCase();

            byte i = 0;

            int i1 = lcLine.indexOf(lcOldString, i);

            if (i1 < 0) {

                return line;

            } else {

                char[] line2 = line.toCharArray();

                char[] newString2 = newString.toCharArray();

                int oLength = oldString.length();

                StringBuilder buf = new StringBuilder(line2.length);

                buf.append(line2, 0, i1).append(newString2);

                i1 += oLength;


                int j;

                for (j = i1; (i1 = lcLine.indexOf(lcOldString, i1)) > 0; j = i1) {

                    buf.append(line2, j, i1 - j).append(newString2);

                    i1 += oLength;

                }


                buf.append(line2, j, line2.length - j);

                return buf.toString();

            }

        }

    }


    public static String replaceIgnoreCase(String line, String oldString, String newString, int[] count) {

        if (line == null) {

            return null;

        } else {

            String lcLine = line.toLowerCase();

            String lcOldString = oldString.toLowerCase();

            byte i = 0;

            int var13 = lcLine.indexOf(lcOldString, i);

            if (var13 < 0) {

                return line;

            } else {

                int counter = 0;

                char[] line2 = line.toCharArray();

                char[] newString2 = newString.toCharArray();

                int oLength = oldString.length();

                StringBuilder buf = new StringBuilder(line2.length);

                buf.append(line2, 0, var13).append(newString2);

                var13 += oLength;


                int j;

                for (j = var13; (var13 = lcLine.indexOf(lcOldString, var13)) > 0; j = var13) {

                    ++counter;

                    buf.append(line2, j, var13 - j).append(newString2);

                    var13 += oLength;

                }


                buf.append(line2, j, line2.length - j);

                count[0] = counter;

                return buf.toString();

            }

        }

    }


    public static String replace(String line, String oldString, String newString, int[] count) {

        if (line == null) {

            return null;

        } else {

            byte i = 0;

            int var11 = line.indexOf(oldString, i);

            if (var11 < 0) {

                return line;

            } else {

                byte counter = 0;

                int var12 = counter + 1;

                char[] line2 = line.toCharArray();

                char[] newString2 = newString.toCharArray();

                int oLength = oldString.length();

                StringBuilder buf = new StringBuilder(line2.length);

                buf.append(line2, 0, var11).append(newString2);

                var11 += oLength;


                int j;

                for (j = var11; (var11 = line.indexOf(oldString, var11)) > 0; j = var11) {

                    ++var12;

                    buf.append(line2, j, var11 - j).append(newString2);

                    var11 += oLength;

                }


                buf.append(line2, j, line2.length - j);

                count[0] = var12;

                return buf.toString();

            }

        }

    }


    public static String[] split(String line, String newString) {

        int begin = 0;

        ArrayList strList = new ArrayList();

        if (line == null) {

            return null;

        } else if ("".equals(newString)) {

            for (int i = 0; i < line.length(); ++i) {

                strList.add(line.substring(i, i + 1));

            }


            return (String[]) ((String[]) strList.toArray(new String[strList.size()]));

        } else {

            int end = line.indexOf(newString);

            if (end == -1) {

                strList.add(line);

                return (String[]) ((String[]) strList.toArray(new String[strList.size()]));

            } else {

                while (end >= 0) {

                    strList.add(line.substring(begin, end));

                    begin = end + newString.length();

                    end = line.indexOf(newString, begin);

                }


                strList.add(line.substring(begin, line.length()));

                return (String[]) ((String[]) strList.toArray(new String[strList.size()]));

            }

        }

    }


    public static int binsToDecs(String str) {

        int ret = 0;

        int v = 1;


        for (int i = 0; i < str.length(); ++i) {

            if (i != 0) {

                v *= 2;

            }


            if (str.charAt(i) != 48) {

                if (str.charAt(i) != 49) {

                    return -1;

                }


                ret += v;

            }

        }


        return ret;

    }


    public static String unescapeFromXML(String stringParam) {

        String string = replace(stringParam, "&lt;", "<");

        string = replace(string, "&gt;", ">");

        string = replace(string, "&quot;", "\"");

        return replace(string, "&amp;", "&");

    }


    public static String escapeForXML(String string1) {

        String string = processXMLMultLineText(string1);

        if (string == null) {

            return null;

        } else {

            int i = 0;

            int last = 0;

            char[] input = string.toCharArray();

            int len = input.length;


            StringBuilder out;

            for (out = new StringBuilder((int) ((double) len * 1.3D)); i < len; ++i) {

                char ch = input[i];

                if (ch <= 62) {

                    if (ch == 60) {

                        if (i > last) {

                            out.append(input, last, i - last);

                        }


                        last = i + 1;

                        out.append(LT_ENCODE);

                    } else if (ch == 38) {

                        if (i > last) {

                            out.append(input, last, i - last);

                        }


                        last = i + 1;

                        out.append(AMP_ENCODE);

                    } else if (ch == 34) {

                        if (i > last) {

                            out.append(input, last, i - last);

                        }


                        last = i + 1;

                        out.append(QUOTE_ENCODE);

                    }

                }

            }


            if (last == 0) {

                return string;

            } else {

                if (i > last) {

                    out.append(input, last, i - last);

                }


                return out.toString();

            }

        }

    }


    public static String subStr(int subnumParam, String strCmd) {

        int subnum = subnumParam;

        String tempSub = "";

        if (subnumParam <= 0) {

            subnum = 5;

        }


        for (int i = 0; i < strCmd.length(); ++i) {

            String tmpstr = strCmd.substring(i, i + 1);

            int codenum = tmpstr.hashCode();

            if (codenum >= 128) {

                subnum -= 2;

            } else {

                --subnum;

            }


            tempSub = tempSub + tmpstr;

            if (subnum <= 0) {

                tempSub = tempSub + "...";

                break;

            }

        }


        return tempSub;

    }


    public StringBuffer processMultLineText(StringBuffer strMultLineText) {

        String formatStr = replace(strMultLineText.toString(), "\\n", "\n");

        formatStr = replace(formatStr, "\n\r", "\n");

        byte before = 0;

        String head = "";

        int index = formatStr.indexOf("\n");

        String beforeStr;

        String showStr;

        if (index >= 0) {

            showStr = formatStr.substring(0, index);

            beforeStr = formatStr.substring(index);

            if (showStr.length() > 4) {

                head = showStr.substring(0, 4).trim();

                showStr = showStr.substring(4);

                head = replace(head, "　", "");

            }


            showStr = "&nbsp;&nbsp;&nbsp;&nbsp;" + head + showStr;

        } else {

            showStr = formatStr;

            if (formatStr.length() > 4) {

                head = formatStr.substring(0, 4).trim();

                showStr = formatStr.substring(4);

                head = replace(head, "　", "");

            }


            showStr = "&nbsp;&nbsp;&nbsp;&nbsp;" + head + showStr;

            beforeStr = "";

        }


        while ((index = beforeStr.indexOf("\n")) >= 0) {

            showStr = showStr + beforeStr.substring(before, index);

            showStr = showStr + "<br>&nbsp;&nbsp;&nbsp;&nbsp;";


            String nextStr;

            for (nextStr = beforeStr.substring(index + 1).trim(); nextStr.startsWith("　"); nextStr = nextStr.substring(2)) {

                ;

            }


            beforeStr = nextStr;

        }


        showStr = showStr + beforeStr;

        return new StringBuffer(showStr);

    }


    public static String processXMLMultLineText(String strMultLineText) {

        String formatStr = replace(strMultLineText, "\\n", "\n");

        String beforeStr = "";

        String nextStr = "";

        String showStr = "";

        formatStr = replace(formatStr, "\r", "");

        formatStr = replace(formatStr, "\n", "<br>&nbsp;&nbsp;&nbsp;&nbsp;");

        return formatStr;

    }


    public static String processPageMultLineText(String strMultLineText) {

        String formatStr = replace(strMultLineText, "\\n", "\n");

        String beforeStr = "";

        String nextStr = "";

        String showStr = "";

        formatStr = replace(formatStr, "\r", "");

        formatStr = replace(formatStr, "\n", "<br>");

        return formatStr;

    }


    public static Vector<String> getDatePeriod(String sDate, String eDate) {

        Vector v = new Vector();

        Calendar ecalendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        String[] sDates = sDate.split("-");

        String[] eDates = eDate.split("-");

        if (sDate.equals(eDate)) {

            ecalendar.set(1, Integer.parseInt(sDates[0]));

            ecalendar.set(2, Integer.parseInt(sDates[1]) - 1);

            ecalendar.set(5, 1);

            v.add(sdf.format(ecalendar.getTime()));

        } else {

            int syear = Integer.parseInt(sDates[0]);

            int smonth = Integer.parseInt(sDates[1]) - 1;

            ecalendar.set(1, Integer.parseInt(eDates[0]));

            ecalendar.set(2, Integer.parseInt(eDates[1]));

            ecalendar.set(5, 1);

            String endDate = sdf.format(ecalendar.getTime());

            Calendar calendar = Calendar.getInstance();

            calendar.set(1, syear);

            calendar.set(2, smonth);

            calendar.set(5, 1);

            v.add(sdf.format(calendar.getTime()));


            while (true) {

                calendar.add(2, 1);

                if (sdf.format(calendar.getTime()).equals(endDate)) {

                    break;

                }


                v.add(sdf.format(calendar.getTime()));

            }

        }


        return v;

    }


    public static Vector<String> getDatePeriodDay(String pDate, int count) throws ParseException {

        Vector v = new Vector();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(sdf.parse(pDate));

        v.add(sdf.format(calendar.getTime()));


        for (int i = 0; i < count - 1; ++i) {

            calendar.add(5, 1);

            v.add(sdf.format(calendar.getTime()));

        }


        return v;

    }


    public static String[] getDatePeriodDay(String sDate, String eDate) throws ParseException {

        if (dateCompare(sDate, eDate)) {

            return null;

        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar calendar = Calendar.getInstance();

            Calendar calendar2 = Calendar.getInstance();

            calendar.setTime(sdf.parse(sDate));

            long l1 = calendar.getTimeInMillis();

            calendar2.setTime(sdf.parse(eDate));

            long l2 = calendar2.getTimeInMillis();

            long days = (l2 - l1) / 86400000L + 1L;

            String[] dates = new String[(int) days];

            dates[0] = sdf.format(calendar.getTime());


            for (int i = 1; (long) i < days; ++i) {

                calendar.add(5, 1);

                dates[i] = sdf.format(calendar.getTime());

            }


            return dates;

        }

    }


    public static boolean dateCompare(String compareDate, String toCompareDate) {

        boolean comResult = false;

        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");


        try {

            Date e = parser.parse(compareDate);

            Date toComDate = parser.parse(toCompareDate);

            if (e.after(toComDate)) {

                comResult = true;

            }

        } catch (ParseException var6) {

            LOGGER.error(var6.getMessage(), var6);

        }


        return comResult;

    }


    public static String checkEmpty(String s) {

        return checkEmpty((String) s, (String) null);

    }


    public static String checkEmpty(String s, String defaultValue) {

        return s != null && !s.equals("null") && !s.trim().equals("") ? s : defaultValue;

    }


    public static String castString(Integer s) {

        return s == null ? "" : String.valueOf(s);

    }


    public static String castString(Double s) {

        return s == null ? "" : String.valueOf(s);

    }


    public static Integer checkEmpty(Integer s) {

        return checkEmpty((Integer) s, (Integer) null);

    }


    public static Integer checkEmpty(Integer s, Integer defaultValue) {

        return s == null ? defaultValue : s;

    }


    public static int checkEmpty(Integer s, int defalutValue) {

        return s == null ? defalutValue : s.intValue();

    }


    public static Double checkEmpty(Double s) {

        return checkEmpty((Double) s, (Double) null);

    }


    public static Double checkEmpty(Double s, Double defaultValue) {

        return s == null ? defaultValue : s;

    }


    public static double checkEmpty(Double s, double defaultValue) {

        return s == null ? defaultValue : s.doubleValue();

    }


    public static String toCN(String s) {

        return checkNull(s);

    }


    public static String checkNull(Object s) {

        return checkNull(s, "");

    }


    public static String checkNull(Object s, String defaultValue) {

        return s == null ? defaultValue : s.toString().trim();

    }


    public static String getCurrentDate(String format) {

        String f;

        if (format != null && !format.equals("")) {

            f = format;

        } else {

            f = "yyyy-MM-dd";

        }


        SimpleDateFormat sdf = new SimpleDateFormat(f);

        return sdf.format(new Date());

    }


    public static String getDateFormat(String format, String dateTime) {

        String dateTimeFormat = "";

        String f;

        if (format != null && !format.equals("")) {

            f = format;

        } else {

            f = "yyyy-MM-dd";

        }


        SimpleDateFormat sdf = new SimpleDateFormat(f);


        try {

            dateTimeFormat = sdf.format((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(dateTime));

        } catch (ParseException var6) {

            LOGGER.error(var6.getMessage(), var6);

        }


        return dateTimeFormat;

    }


    public static String getStringByEncoding(String string, String encoding) {

        String str = "";

        String encode = "utf-8";

        if (encoding != null && !encoding.equals("")) {

            encode = encoding;

        }


        if (string != null && !string.equals("")) {

            try {

                str = new String(string.getBytes("iso8859-1"), encode);

            } catch (UnsupportedEncodingException var5) {

                LOGGER.error(var5.getMessage(), var5);

            }

        }


        return str;

    }


    public static Vector<String> createDateArray(int year, int month) {

        Vector v = new Vector();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        ParsePosition pos = new ParsePosition(0);

        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date(year - 1900, month - 1, 10));

        int maxDay = cal.getActualMaximum(5);

        String startTime = year + "-" + (month < 10 ? "0" + month : Integer.valueOf(month)) + "-01";

        Date st = formatter.parse(startTime, pos);


        for (int i = 0; i < maxDay; ++i) {

            if (i > 0) {

                st.setDate(st.getDate() + 1);

            }


            v.add(formatter.format(st));

        }


        return v;

    }


    public static int getDaysByMonth(String date) {

        int days = -1;

        GregorianCalendar cal = new GregorianCalendar();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");


        try {

            cal.setTime(sdf.parse(date));

            days = cal.getActualMaximum(5);

        } catch (ParseException var5) {

            LOGGER.error(var5.getMessage(), var5);

        }


        return days;

    }


    public static Date parseDate(String str, String parsePattern) {

        if (str != null && !"".equals(str)) {

            SimpleDateFormat parser = new SimpleDateFormat(parsePattern);

            ParsePosition pos = new ParsePosition(0);

            Date date = parser.parse(str, pos);

            if (date != null) {

                return date;

            } else {

                throw new BusinessException(1, "日期错误: " + str);

            }

        } else {

            return null;

        }

    }


    public static String getDateAdd(String dateStrParam, int days) {

        String dateStr = dateStrParam;

        if (dateStrParam == null) {

            return null;

        } else {

            if (dateStrParam.length() > 10) {

                dateStr = dateStrParam.substring(0, 10);

            }


            Calendar cal = Calendar.getInstance();

            cal.setTime(parseDate(dateStr, "yyyy-MM-dd"));

            cal.add(5, days);

            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

            String date = parser.format(cal.getTime());

            return date;

        }

    }


    public static String getDateAdd(String dateStrParam, int days, String parsePattern) {

        String dateStr = dateStrParam;

        if (dateStrParam == null) {

            return null;

        } else {

            if (dateStrParam.length() > 10 && parsePattern.length() <= 10) {

                dateStr = dateStrParam.substring(0, 10);

            }


            Calendar cal = Calendar.getInstance();

            cal.setTime(parseDate(dateStr, parsePattern));

            cal.add(5, days);

            SimpleDateFormat parser = new SimpleDateFormat(parsePattern);

            String date = parser.format(cal.getTime());

            return date;

        }

    }


    public static Integer parseInt(String str) {

        return parseInt(str, (Integer) null);

    }


    public static Integer parseInt(String str, Integer defValue) {

        return str != null && !str.trim().equals("") && !str.equals("null") ? Integer.valueOf(Integer.parseInt(str)) : defValue;

    }


    public static Double parseDouble(String str) {

        return parseDouble(str, (Double) null);

    }


    public static Double parseDouble(String str, Double defValue) {

        return str != null && !str.trim().equals("") && !str.equals("null") ? Double.valueOf(Double.parseDouble(str)) : defValue;

    }


    public static Long parseLong(String str) {

        return parseLong(str, (Long) null);

    }


    public static Long parseLong(String str, Long defValue) {

        return str != null && !str.trim().equals("") && !str.equals("null") ? Long.valueOf(Long.parseLong(str)) : defValue;

    }


    public static boolean parseBoolean(String str) {

        return !isEmpty(str) && !str.equals("null") && (str.equals("1") || str.equals("true"));

    }


    public static String formatDouble(Double doubleValue) {

        return formatDouble(doubleValue, (String) null);

    }


    public static String formatDouble(Double doubleValue, String patten) {

        DecimalFormat df;

        if (patten == null) {

            df = new DecimalFormat("#");

        } else {

            df = new DecimalFormat(patten);

        }


        return df.format(doubleValue);

    }


    public static BigDecimal str2BigDecimal(String param) {

        BigDecimal dbNumber = new BigDecimal(-1);


        try {

            if (!isEmpty(param)) {

                dbNumber = new BigDecimal(param);

            }

        } catch (Exception var3) {

            LOGGER.error(var3.getMessage(), var3);

        }


        return dbNumber;

    }


    public static int convertInt(String str) {

        int number = 0;

        String matchStr = "^([+-]?)\\d*\\.?\\d+$";


        try {

            if (str != null && str.matches(matchStr)) {

                number = Integer.parseInt(str);

            }

        } catch (Exception var4) {

            var4.printStackTrace();

        }


        return number;

    }


    /**
     * @deprecated
     */

    @Deprecated

    public static String conveArrayToStr(String[] codes) {

        return convertArrayToStr(codes);

    }


    public static String convertArrayToStr(String[] codes) {

        return convertArrayToStr(codes, ",");

    }


    public static String convertArrayToStr(String[] codes, String separator) {

        if (codes != null && codes.length != 0) {

            String separatorStr = separator;

            if (isEmpty(separator)) {

                separatorStr = ",";

            }


            StringBuilder str = new StringBuilder("");


            for (int i = 0; i < codes.length; ++i) {

                if (i == codes.length - 1) {

                    str.append(codes[i]);

                } else {

                    str.append(codes[i]).append(separatorStr);

                }

            }


            return str.toString();

        } else {

            return "";

        }

    }


    public static boolean isIntegerNum(String str) {

        return !isEmpty(str) && (str.matches("^-?\\d+$") || str.matches("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$"));

    }


    public static String constrast(String equalIds, String equalToIds) {

        String[] equalId = equalIds.split(",");

        String[] equalToId = equalToIds.split(",");

        String str = compareIds((String[]) equalId, (String[]) equalToId);

        return str;

    }


    public static String compareIds(String[] equalId, String[] equalToId) {

        String str = "";

        int i = 0;


        while (i < equalId.length) {

            boolean flag = false;

            int j = 0;


            while (true) {

                if (j < equalToId.length) {

                    if (!equalId[i].equals(equalToId[j])) {

                        ++j;

                        continue;

                    }


                    flag = true;

                }


                if (!flag) {

                    str = str + equalId[i] + ",";

                }


                ++i;

                break;

            }

        }


        return str;

    }


    public static String compareIds(String[] equalIds, String equalToIds) {

        String[] equalToId = equalToIds.split(",");

        return compareIds((String[]) equalIds, (String[]) equalToId);

    }


    public static String compareIds(String equalIds, String[] equalToIds) {

        String[] equalId = equalIds.split(",");

        return compareIds((String[]) equalId, (String[]) equalToIds);

    }


    public static boolean hasAuthoritys(String powerStr, String[] authorities) {

        for (int i = 0; i < authorities.length; ++i) {

            if (authorities[i] != null && !authorities[i].equals("") && authorities[i].equals(powerStr)) {

                return true;

            }

        }


        return false;

    }


    public static boolean checkStr(String str) {

        return str != null && !"".equals(str.trim());

    }


    public String dealString(String str) {

        return str != null && !"".equals(str.trim()) ? str.trim() : "";

    }


    public static String dealSqlField(String str) {

        return str.trim().replace("\'", "\'\'");

    }


    public static String getWeekOfDate(Date dt) {

        String[] weekDays = new String[]{"星期日SUN", "星期一MON", "星期二TUE", "星期三WED", "星期四THU", "星期五FRI", "星期六SAT"};

        Calendar cal = Calendar.getInstance();

        cal.setTime(dt);

        int w = cal.get(7) - 1;

        if (w < 0) {

            w = 0;

        }


        return weekDays[w];

    }


    public static String getWeekOfDate(String date) {

        String dt = date;

        if (checkEmpty((String) date) == null) {

            dt = getCurrentDate("yyyy-MM-dd");

        }


        return getWeekOfDate((Date) parseDate(dt, "yyyy-MM-dd"));

    }


    public static long dateTimeDifferenceHour(String format, String date1, String date2) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date d1 = sdf.parse(date1);

        Date d2 = sdf.parse(date2);

        long l = d1.getTime() - d2.getTime();

        long hour = l / 1440000L;

        return hour;

    }


    public static long dateTimeDifferenceMinute(String format, String date1, String date2) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date d1 = sdf.parse(date1);

        Date d2 = sdf.parse(date2);

        long l = d1.getTime() - d2.getTime();

        int min = (int) (l / 60000L);

        return (long) min;

    }


    public static String formatDate(Date date) {

        return formatDate(date, (String) null);

    }


    public static String formatDate(Date date, String patternPram) {

        String pattern = patternPram;

        if (patternPram == null) {

            pattern = "yyyy-MM-dd HH:mm:ss";

        }


        SimpleDateFormat simpleFormat = new SimpleDateFormat(pattern);

        return simpleFormat.format(date);

    }


    public static int getYearByDateString(String date) {

        Integer year = Integer.valueOf(-1);

        if (checkEmpty((String) date) != null && date.length() >= 7) {

            String y = date.split("-")[0];

            parseInt(y, Integer.valueOf(-1));

        }


        return year.intValue();

    }


    public static int getMonthByDateString(String date) {

        Integer month = Integer.valueOf(-1);

        if (checkEmpty((String) date) != null && date.length() >= 7) {

            String m = date.split("-")[1];

            parseInt(m, Integer.valueOf(-1));

        }


        return month.intValue();

    }


    public static int getWeekByDate(String date) throws ParseException {

        GregorianCalendar cal = new GregorianCalendar();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        cal.setTime(sdf.parse(date));

        int week = cal.get(7) - 1;

        return week;

    }


    public static boolean isWeekend(String date) throws ParseException {

        int week = getWeekByDate(date);

        return week == 5 || week == 6 || week == 0;

    }


    public static boolean isMobileNO(String mobiles) {

        return isNationalMobileNO(mobiles);

    }


    public static boolean isInternationMobileNO(String mobileNO) {

        String mobile = commonCheckMobile(mobileNO);

        if (mobile == null) {

            return false;

        } else {

            String tempMobileNo = mobile;

            if (mobile.startsWith("0")) {

                while (tempMobileNo.startsWith("0")) {

                    tempMobileNo = tempMobileNo.substring(1);

                }


                if (tempMobileNo.startsWith("86")) {

                    return false;

                }

            }


            return true;

        }

    }


    public static boolean isNationalMobileNO(String mobileNO) {

        String mobile = commonCheckMobile(mobileNO);

        if (mobile == null) {

            return false;

        } else {

            String tempMobileNo = mobile;

            if (mobile.startsWith("0")) {

                while (tempMobileNo.startsWith("0")) {

                    tempMobileNo = tempMobileNo.substring(1);

                }


                if (!tempMobileNo.startsWith("86")) {

                    return false;

                }

            }


            if (tempMobileNo.startsWith("86")) {

                mobile = tempMobileNo.substring(2);

            }


            return matchNationalMobile(mobile);

        }

    }


    private static boolean matchNationalMobile(String mobile) {

        String cm = "^((13[4-9])|(142)|(147)|(148)|(154)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}$";

        Pattern p1 = Pattern.compile(cm);

        Matcher m1 = p1.matcher(mobile);

        if (m1.matches()) {

            return true;

        } else {

            String ct = "^((133)|(149)|(153)|(173)|(177)|(18[0,1,9]))\\d{8}$";

            Pattern p2 = Pattern.compile(ct);

            Matcher m2 = p2.matcher(mobile);

            if (m2.matches()) {

                return true;

            } else {

                String cu = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5-6]))\\d{8}$";

                Pattern p3 = Pattern.compile(cu);

                Matcher m3 = p3.matcher(mobile);

                if (m3.matches()) {

                    return true;

                } else {

                    Pattern p4 = Pattern.compile("(170)\\d{8}");

                    Matcher m4 = p4.matcher(mobile);

                    if (m4.matches()) {

                        return true;

                    } else {

                        Pattern p5 = Pattern.compile("(171)\\d{8}");

                        Matcher m5 = p5.matcher(mobile);

                        return m5.matches();

                    }

                }

            }

        }

    }


    private static String commonCheckMobile(String mobileNO) {

        if (mobileNO != null && !"".equals(mobileNO)) {

            String mobile = mobileNO.trim();

            if (mobile.startsWith("+")) {

                mobile = mobile.substring(1);

            }


            if (!isIntegerNum(mobile)) {

                return null;

            } else {

                String tempMobileNo;

                for (tempMobileNo = mobile; tempMobileNo.startsWith("0"); tempMobileNo = tempMobileNo.substring(1)) {

                    ;

                }


                return tempMobileNo.length() < 8 ? null : mobile;

            }

        } else {

            return null;

        }

    }


    public static boolean isIn(String substring, String[] source) {

        if (source != null && source.length != 0) {

            for (int i = 0; i < source.length; ++i) {

                String aSource = source[i];

                if (aSource.equals(substring)) {

                    return true;

                }

            }


            return false;

        } else {

            return false;

        }

    }


    public static Date dateAddDay(Date date, int dateAdd) {

        Calendar c = Calendar.getInstance();

        c.setTime(date);

        c.add(5, dateAdd);

        return c.getTime();

    }


    public static int getWordCount(String s) {

        String result = s.replaceAll("[^\\x00-\\xff]", "**");

        return result.length();

    }


    public static int getDateField(int field) {

        return getDateField(new Date(), field);

    }


    public static int getDateField(Date date, int field) {

        Calendar c = Calendar.getInstance();

        c.setTime(date);

        return field == 2 ? c.get(field) + 1 : c.get(field);

    }


    public static String removeCharAt(String str, Integer index) {

        if (index.intValue() >= 0 && index.intValue() <= str.length() - 1) {

            return str.substring(0, index.intValue()) + str.substring(index.intValue() + 1, str.length());

        } else {

            throw new StringIndexOutOfBoundsException(index.intValue());

        }

    }


    public static boolean areNotEmpty(String... values) {

        boolean result = true;

        if (values != null && values.length != 0) {

            String[] arr$ = values;

            int len$ = values.length;


            for (int i$ = 0; i$ < len$; ++i$) {

                String value = arr$[i$];

                result &= !isEmpty(value);

            }

        } else {

            result = false;

        }


        return result;

    }


    public static String convertToString(List list, String separator) {

        String separatorStr = separator;

        if (isEmpty(separator)) {

            separatorStr = ",";

        }


        StringBuilder sb = new StringBuilder();

        if (list != null && list.size() > 0) {

            for (int i = 0; i < list.size(); ++i) {

                if (i < list.size() - 1) {

                    sb.append(list.get(i) + separatorStr);

                } else {

                    sb.append(list.get(i));

                }

            }

        }


        return sb.toString();

    }

}