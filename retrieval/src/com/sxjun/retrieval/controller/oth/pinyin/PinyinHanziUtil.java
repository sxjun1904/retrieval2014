package com.sxjun.retrieval.controller.oth.pinyin;

import java.util.List;

import com.sxjun.retrieval.constant.RKeyPrixConstant;
import com.sxjun.retrieval.controller.proxy.ServiceProxy;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.controller.service.impl.RedisCommonService;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.retrieval.oth.pinyin.PinyinUtil;

public class PinyinHanziUtil {
	private static CommonService<String> commonService = new ServiceProxy<String>().getproxy();
	public static void add(String hanzi){
		if(PinyinUtil.isCharacter(hanzi)){
			int len = hanzi.length();
			for(int i = len;i>0;i--){
				String hz = hanzi.substring(0,i);
				//加入拼音
				String pinyin = PinyinUtil.getPinYin(hz);
				addPinyinHanzi(pinyin,hanzi);
				//加入拼音首字母
				String pinyinHeadChar = PinyinUtil.getPinYinHeadChar(hz);
				addPinyinHanzi(pinyinHeadChar,hanzi);
				//加入汉字
				addPinyinHanzi(hz,hanzi);
			}
		}
	}
	
	public static List<String> getRangeWords(String pinyinhanzi){
		return commonService.lrange(RKeyPrixConstant.PINYIN, pinyinhanzi, 0, -1);
	}
	
	//存拼音-汉字
	//存首字母-汉字
	//存汉字-汉字
	public static void addPinyinHanzi(String pinyin,String hanzi){
		List<String> pyList = getRangeWords(pinyin);
		if(pyList!=null){
			boolean isfind = false;
			for(String s :pyList){
				if(s.equals(hanzi)){
					isfind = true;
					break;
				}
			}
			if(!isfind){
				commonService.lpush(RKeyPrixConstant.PINYIN, pinyin, hanzi);
				commonService.ltrim(RKeyPrixConstant.PINYIN, pinyin, 0, 9);
			}
		}
	}
}
