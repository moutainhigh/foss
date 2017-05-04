package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;

/**
 * 零担电子面单激活--数据集中处理Service
 * 
 * @author 325220-foss-liuhui
 * 
 */
public interface ILTLEWaybillService extends IService {

	/**
	 * 根据运单job记录批量激活运单
	 * 
	 * @param waybillProcessEntityList 运单job记录List
	 */
	void batchActiveLTLEWaybillByWaybillJobs(List<WaybillProcessEntity> waybillProcessEntityList);

	/**
	 * 
	 * addLTLEWaybillPending(添加零担电子运单代补录 ) 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * 
	 * @Title: addLTLEWaybillPending
	 * @Description: TODO
	 * @param  OmsOrderEntity
	 * @return WaybillPendingDto 返回类型
	 */
	void addLTLEWaybillPending(List<WaybillProcessEntity> waybillProcessEntityList);

	/**
	 * 提交运单DTO
	 * @param waybill
	 */
	void submitWaybill(WaybillDto waybillDto);
	/**
	 * @author 305082
	 * 零担电子运单导入重量体积
	 */
	public void ltlEWaybillChangeWeight(
			LTLEWaybillChangeWeightDto expBatchChangeWeightDto,CurrentInfo currentInfo);
	/**
	 * 查询更改重量体积的结果信息
	 * @param dto
	 * @return
	 */
	public List<LTLEWaybillChangeWeightDto> queryLTLEWaybillChangeWeightResult(LTLEWaybillChangeWeightQueryDto dto);
	
	public void insertAndUpdatePending(OmsOrderEntity omsOrder);
	
	public void handleActiveWaybillProcess(WaybillProcessEntity entity,boolean addActiveProcess,String waybillStatus,String exceptionCode);
}
