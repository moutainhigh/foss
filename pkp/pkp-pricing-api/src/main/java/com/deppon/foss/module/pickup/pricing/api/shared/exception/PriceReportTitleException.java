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
 * 汽运价格报表表头信息异常类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-1-10 下午2:15:34 </p>
 * @author 094463-foss-xieyantao
 * @date 2014-1-10 下午2:15:34
 * @since
 * @version
 */
public class PriceReportTitleException extends BusinessException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7620281732608894260L;

    public PriceReportTitleException(String errCode) {
	super();
	super.errCode = errCode;
    }

    public PriceReportTitleException(String code, String msg) {

	super(code, msg);
    }
}
