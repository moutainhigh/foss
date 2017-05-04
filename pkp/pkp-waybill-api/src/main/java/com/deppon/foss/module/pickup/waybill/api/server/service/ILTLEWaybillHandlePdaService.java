package com.deppon.foss.module.pickup.waybill.api.server.service;


import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaAppInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WoodenRequirePdaDto;

public interface ILTLEWaybillHandlePdaService {

	/**
	 * @description APP扫描后返回标签信息
	 * @param waybill 运单号
	 * @param originateOrgCode 始发部门
	 * @return
	 */
	public LabelInfoDto appScan(String waybillNo, String originateOrgCode)throws Exception;
	
	/**
	 * @description 处理外场Pda扫描信息
	 * @param woodenPdaDto
	 * @throws BusinessException
	 */
	public void handlePdaInfo(WoodenRequirePdaDto woodenPdaDto)throws BusinessException;
	
	/**
	 * @description 处理重量体积，添加激活运单线程
	 * @param pdaAppInfo
	 */
	public void handleAppInfo(PdaAppInfoDto pdaAppInfo,String operationType,WoodenRequirePdaDto woodenPdaDto) throws BusinessException;
}
