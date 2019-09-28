package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    /**
     * 获取匹配的结果,第一条
     *
     * @param content
     * @param regex
     * @return
     */
    public static String getMatchContent(String content, String regex) {
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(regex);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        if (m.find()) {
            return m.group(0);
        }
        return "";
    }


    /**
     * 将开头的一个大写字母小写
     *
     * @param name
     * @return
     */
    public static String changeFirstToLowCase(String name) {
        System.out.println("首字母小写转换-之前:" + name);
        String innerContent = name;
        String valiableClassName = (new StringBuilder()).append(Character.toLowerCase(innerContent.charAt(0))).append(innerContent.substring(1)).toString();
        System.out.println("首字母小写转换-之后:" + valiableClassName);
        return valiableClassName;
    }

    /**
     * 将大写字母换成小写字母加下划线
     *
     * @param name
     * @return
     */
    public static String changeUpCaseToLowCaseAndLine(String name) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isUpperCase(ch)) {
                // 大写
                // 转换小写
                char lowCase = Character.toLowerCase(ch);
                if (i == 0) {
                    buffer.append(lowCase);
                } else {
                    buffer.append("_" + lowCase);
                }
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }
}
