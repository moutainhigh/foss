package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.DestFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OrgSettlementInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OrigFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillSettlementInfoDto;

/**
 * 查询运单的到付金额、代收货款、装卸费、发票信息Dao接口
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-21 上午8:11:11
 * @since
 * @version
 */
public interface ISettlementInfoQueryDao {

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
	List<WaybillSettlementInfoDto> queryCodFeeByWaybillNO(String waybillNo, Date startDate,
			Date endDate);

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
	WaybillSettlementInfoDto queryServiceFeeByWaybillNO(String waybillNo, Date startDate,
			Date endDate);

	/**
	 * 根据运单号和开单日期查询运单的发票信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 上午8:45:32
	 * @param waybillNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<WaybillSettlementInfoDto> queryInvoiceByWaybillNO(String waybillNo, Date startDate,
			Date endDate);

	/**
	 * 根据部门编码和部门创建日期查找该部门的应收、应付、预收、预付未核销的金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-26 下午9:01:20
	 * @param orgCode
	 * @param orgCreateTime
	 * @return
	 */
	List<OrgSettlementInfoDto> queryOrgSettlementInfo(String orgCode, Date orgCreateTime);

	/**
	 * 查询始发运费
	 * 
	 * @param waybillNo
	 * @return
	 */
	OrigFeeInfo queryOrigFee(String waybillNo);

	/**
	 * 查询到付费用
	 * 
	 * @param waybillNo
	 * @return
	 */
	DestFeeInfo queryDestFee(String waybillNo);

	/**
	 * 查询代收货款费用
	 * 
	 * @param waybillNo
	 * @return
	 */
	CODFeeInfo queryCODFee(String waybillNo);

	/**
	 * 查询其它费用
	 * 
	 * @param waybillNo
	 * @return
	 */
	List<OtherFeeInfo> queryOtherFee(String waybillNo);

	/**
	 * 查询开发票费用
	 * 
	 * @param sourceBills
	 * @return
	 */
	List<InvoiceFeeInfo> queryInvoiceFee(List<String> sourceBills);
}
