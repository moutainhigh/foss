/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.pricing.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 快递代理理网点运价方案明细异常类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-22 下午12:30:15 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-22 下午12:30:15
 * @since
 * @version
 */
public class OubrPlanDetailException extends BusinessException {


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -229602322488350091L;

    public OubrPlanDetailException(String errCode) {
	super();
	super.errCode = errCode;
    }

    public OubrPlanDetailException(String code, String msg) {

	super(code, msg);
    }
}
