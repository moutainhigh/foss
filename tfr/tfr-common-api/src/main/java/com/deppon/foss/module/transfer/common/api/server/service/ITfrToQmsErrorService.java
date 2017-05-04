package com.deppon.foss.module.transfer.common.api.server.service;

import com.deppon.foss.module.transfer.common.api.shared.domain.QmsErrRequestParam;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsErrResponseParam;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsNolableRequestParam;
import com.deppon.foss.module.transfer.common.api.shared.dto.RequestParamEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseFromQmsDto;


/**
 * @author niuly
 * @function 上传QMS差错接口
 */
public interface ITfrToQmsErrorService {
	/**
	 * @author nly
	 * @date 2015年6月13日 下午2:07:03
	 * @function 用于上传卸车多货、分批配载
	 * @param request
	 * @return
	 */
	QmsErrResponseParam reportQmsError(QmsErrRequestParam request,String esbcode);
	
	/**
	 * @author nly
	 * @date 2015年6月13日 下午2:25:25
	 * @function 上报无标签多货
	 * @param request
	 * @return
	 */
	ResponseFromQmsDto reportQmsNolabel(QmsNolableRequestParam request);
	/**
	 * 自动将超方超重的上报给QMS
	 * @author 268084
	 * @param rpEntity
	 * @return
	 */
	String reportQmsOverWeight(RequestParamEntity rpEntity);
	
}
