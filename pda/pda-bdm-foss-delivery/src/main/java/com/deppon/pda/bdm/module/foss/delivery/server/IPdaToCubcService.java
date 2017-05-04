package com.deppon.pda.bdm.module.foss.delivery.server;


import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillParamEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayInfoDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.RequestDO;

/**
 * PDA将NCI信息同步至CUBC
 * @author 245955
 *
 */
public interface IPdaToCubcService {

	//通过单号查询对账单
	public String pdaToCubcAccountStatement(CommonQueryParamDto dto);
	
	//对账单刷卡上传
	public String getAccountStatementSuccess(PayInfoDO dto);
	
	//查询登录人对应部门的所有待刷卡数据
	public String setNotBushCard(WSCWayBillParamEntity entity);
	
	//查询运单号是否核销
	public String queryWaybill(String waybill);
	
	//通过单号查询结清货款模块数据
	//public String querySettlementOfPayment(CommonQueryParamDto entity);
    
	//CUBC灰度接口改造 --按对账单号
	public String cubcHuiduQuery(RequestDO requestDo);
	
	//CUBC灰度接口改造 --按方法名称
	public String cubcHuiduQueryMethod(RequestDO requestDo);
}

