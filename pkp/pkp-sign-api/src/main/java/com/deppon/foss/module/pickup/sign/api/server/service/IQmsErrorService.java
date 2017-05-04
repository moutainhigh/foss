package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.Map;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorImportantWaybillResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordUnnormalSignWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;


/**
 * QMS差错上报信息
 * @author 231434-FOSS-bieyexiong
 * @date 2015-4-15 上午10:23:35
 */
public interface IQmsErrorService {
	
	/**
	 * 手动上报，根据运单号，返回运单的部分签收信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-4-15 上午10:23:35
	 * @return
	 * @throws CommonException 
	 */
	String querySignInfo(String waybillNo,String businessType,String errorType);
	
	/**
	 * 快递差错-查询QMS反签收差错自动上报信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-4-15 下午15:41:16
	 */
	Map<String,Object> getReverseErrorQmsInfo(WaybillEntity waybill,CurrentInfo currentInfo);

	/**
	 * 零担差错-查询QMS反签收差错自动上报信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-6-13 上午11:10:23
	 */
	Map<String,Object> getLDReverseErrorQmsInfo(WaybillEntity waybill,CurrentInfo currentInfo);
	
	/**
	 * 快递差错-查询QMS内物短少差错自动上报信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-4-20 上午10:52:33
	 */
	Map<String,Object> getShortErrorQmsInfo(WaybillEntity waybill,
			RecordErrorWaybillResultDto recordErrorWaybillResultDto);
	
	/**
	 * 零担差错-查询QMS内物短少差错自动上报信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-6-13 下午15:13:11
	 */
	Map<String,Object> getLDShortErrorQmsInfo(WaybillEntity waybill,
			RecordErrorWaybillResultDto recordErrorWaybillResultDto);
	
	/**
	 * 零担差错-查询QMS异常签收线上划责自动上报信息
	 * @author 231434-FOSS-bieyexiong
	 * @date 2016-2-20 下午13:56:19
	 */
	String getLDUnnormalSignQmsInfo(WaybillEntity waybill,
			RecordUnnormalSignWaybillDto unnormalDto);
	
	/**
	 * 零担差错-查询QMS重大货物异常自动上报信息
	 * @author 306548-foss-honglujun
	 */
	String getLDImportantErrorQmsInfo(WaybillEntity waybill,
			RecordErrorImportantWaybillResultDto recordErroImportantWaybillResultDto);
}
