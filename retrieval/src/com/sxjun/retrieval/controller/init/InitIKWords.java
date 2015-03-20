package com.sxjun.retrieval.controller.init;

import java.util.List;

import org.apache.log4j.Logger;

import com.sxjun.core.common.proxy.ServiceProxy;
import com.sxjun.core.common.service.CommonService;
import com.sxjun.core.interceptor.SessionInterceptor;
import com.sxjun.retrieval.pojo.IKWords;

import frame.retrieval.oth.ik.IKWordsUtil;

public class InitIKWords implements InitBase{
	private static final Logger logger = Logger.getLogger(InitIKWords.class);
	private CommonService<IKWords> commonservice = new ServiceProxy<IKWords>().getproxy();
	@Override
	public void init() {
		logger.debug("开始添加词元");
		List<IKWords> iks =commonservice.getObjs(IKWords.class);
		for(IKWords ik : iks){
			IKWordsUtil.addWord(ik.getWords());
			logger.debug("添加词元“"+ik.getWords()+"”");
		}
		logger.debug("词元添加结束，共完成"+(iks!=null&&iks.size()>0?iks.size():"0")+"个词的添加");
	}

}
