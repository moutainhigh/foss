package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OrgSettlementInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillFinanceInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillSettlementInfoDto;

/**
 * 查询运单的到付金额、代收货款、装卸费、发票信息Service接口 [提供给综合管理使用]
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-21 上午9:23:01
 * @since
 * @version
 */
public interface ISettlementInfoQueryService extends IService {

	/**
	 * 根据运单号和开单日期查询运单的代收货款信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 上午8:43:05
	 * @param waybillNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Deprecated
	WaybillSettlementInfoDto queryCodFeeByWaybillNO(String waybillNo, Date startDate, Date endDate);

	/**
	 * 根据运单号和开单日期查询运单的装卸费信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 上午8:44:02
	 * @param waybillNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Deprecated
	WaybillSettlementInfoDto queryServiceFeeByWaybillNO(String waybillNo, Date startDate,
			Date endDate);

	/**
	 * 根据运单号和开单日期查询运单的发票信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 上午8:45:32
	 * @param waybillNo  startDate
	 * @param endDate 始发部门编码 origOrgCode           
	 * @param 到达部门编码  destOrgCode        
	 * @return
	 */
	@Deprecated
	WaybillSettlementInfoDto queryInvoiceByWaybillNO(String waybillNo, String origOrgCode,
			String destOrgCode, Date startDate, Date endDate);

	/**
	 * 根据部门编码和部门创建日期查找该部门的应收、应付、预收、预付未核销的金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-26 下午8:52:10
	 * @param orgCode
	 *            部门编码
	 * @param orgCreateTime
	 *            部门创建时间
	 * @return
	 */
	@Deprecated
	OrgSettlementInfoDto queryOrgSettlementInfo(String orgCode, Date orgCreateTime);
	
	/**
	 * 查询运单的财务情况
	 * @param waybill
	 * @return
	 */
	WaybillFinanceInfoDto queryWaybillFinanceInfo(String waybill);

}
