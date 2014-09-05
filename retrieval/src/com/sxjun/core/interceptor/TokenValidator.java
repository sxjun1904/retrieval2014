package com.sxjun.core.interceptor;

import com.jfinal.core.Controller;
import com.jfinal.render.Render;
import com.jfinal.validate.Validator;

public class TokenValidator extends Validator{

	@Override
	protected void validate(Controller c) {
		validateToken("tokenName", "msg", "token过期");
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson("{}");
	}

}
