package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.TruckArriveConfirmEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;

/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-05-29 13:49:29
 * FOSS结算配合VTS整车项目：外请车费用调整IService
 */
public interface IVtsOutvehicleFeeAdjustService {

	//根据单号、单据类型：查询整车尾款应付单TL2
	List<BillPayableEntity> queryTLBillPayableByWaybillNo(BillPayableConditionDto conDto);
	
	//核销整车尾款应付单+核销前的校验
	void writeBackBillPayable(BillPayableEntity entity, BillPayableEntity billPayableEntity,CurrentInfo currentInfo);
	
	//生成新的整车尾款应付单
	int insertBillPayable(BillPayableEntity entity, CurrentInfo currentInfo);
	
	//插入红冲应付单方法
	void add(BillPayableEntity entity);
	
	//插入财务变更消息
	void addChangeMsg(String waybillNo,String payableNo,String msgAction,Date date);
	
	//查询整车外请车到达确认凭证表
	List<TruckArriveConfirmEntity> queryTruckConfirmByEntity(TruckArriveConfirmEntity dto);
	
	//重新到达 整车外请车到达确认凭证表
	void updateTruckConfirmByEntity(TruckArriveConfirmEntity dto);
	
	
}
