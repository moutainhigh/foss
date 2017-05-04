package com.deppon.foss.module.pickup.pda.api.server.service;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaAppInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WoodenRequirePdaDto;

/**
 * @description 零担电子运单PDA接口 
 * @author 297064
 * 2016-05-30
 */
public interface ILTLEWaybillPdaScanService {
	
	/**
	 * @description APP扫描后返回标签信息
	 * @param waybill 运单号
	 * @param originateOrgCode 始发部门
	 * @return
	 */
	public LabelInfoDto appScan(String waybillNo, String originateOrgCode)throws Exception;
	
	/**
	 * @description APP直接调用完成任务接口
	 * @param overPickupInfo
	 */
	public void appOverTask(List<PdaAppInfoDto> pdaAppInfos)throws BusinessException;
	
	/**
	 * @description 提供给PDA扫描的接口不区分集中接货和非集中接货
	 * @param woodenPdaDto PDA扫描信息
	 */
	public void pdaScan(WoodenRequirePdaDto woodenPdaDto)throws BusinessException;
}
