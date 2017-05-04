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
 * 快递代理理公司运价方案异常类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-22 下午12:30:15 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-22 下午12:30:15
 * @since
 * @version
 */
public class PartbussPlanException extends BusinessException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5653893560225788162L;

    public PartbussPlanException(String errCode) {
	super();
	super.errCode = errCode;
    }

    public PartbussPlanException(String code, String msg) {

	super(code, msg);
    }
}
