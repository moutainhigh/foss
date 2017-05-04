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
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 快递大区与行政区域映射关系异常类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-22 下午12:30:15 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-25 下午2:27:08
 * @since
 * @version
 */
public class ExpressBigRegionDistrException extends BusinessException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6554766957331341067L;

    public ExpressBigRegionDistrException(String errCode) {
	super();
	super.errCode = errCode;
    }

    public ExpressBigRegionDistrException(String code, String msg) {

	super(code, msg);
    }
}
