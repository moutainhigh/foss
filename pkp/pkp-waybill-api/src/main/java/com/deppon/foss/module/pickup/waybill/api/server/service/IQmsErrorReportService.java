package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.QmsErrorReportResponseDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.RecordUnnormalSignResponseDto;

/**
 * Qms差错自动上报
 * @author 231434-foss-bieyexiong
 * @date 2015-5-13 上午9:12:20
 */
public interface IQmsErrorReportService extends IService {

	/**
	 * qms K反签收差错自动上报
	 * @author 231434-foss-bieyexiong
	 * @date 2015-5-13 上午9:23:01
	 */
	QmsErrorReportResponseDto reportQmsReverseSignError(Map<String,Object> errorInfoMap);
	
	/**
	 * qms K内物短少差错自动上报
	 * @author 231434-foss-bieyexiong
	 * @date 2015-5-13 上午9:24:32
	 */
	QmsErrorReportResponseDto reportQmsShortError(Map<String,Object> errorInfoMap);
	
	/**
	 * qms 异常签收线上划责差错自动上报
	 * @author 231434-foss-bieyexiong
	 * @date 2016-2-23 上午10:28:32
	 */
	RecordUnnormalSignResponseDto reportQmsUnnormalSignError(String errorInfo);
	
	/**
	 * qms qms 重大货物异常自动上报
	 * @author 306548-foss-honglujun
	 */
	QmsErrorReportResponseDto reportQmsImportantError(String errorInfo);
}
