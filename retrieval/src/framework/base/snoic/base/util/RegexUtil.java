package framework.base.snoic.base.util;

import java.util.regex.Pattern;

import framework.base.snoic.base.util.regex.Regex;

public class RegexUtil {
	/**
     * 过滤html文本
     * 
     * @author sxjun
     */
    public static String Html2Text(String inputHtmlText) {
        if (inputHtmlText == null || inputHtmlText.trim().length() == 0)
            return "";
        String htmlText = inputHtmlText; // 含html标签的字符串
        String text = "";

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            
            Regex regex = new Regex();
            String rm_Script = regex.getReplaceAll(htmlText, "",regEx_script, Pattern.CASE_INSENSITIVE);
            String rm_Style = regex.getReplaceAll(rm_Script, "",regEx_style, Pattern.CASE_INSENSITIVE);
            String rm_Html = regex.getReplaceAll(rm_Style, "",regEx_html, Pattern.CASE_INSENSITIVE);
            text = rm_Html;
        }
        catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        return text;// 返回文本字符串
    }

}
