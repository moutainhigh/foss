package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayConfirmDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;

/**
 * 
 * 代收货款汇款确认服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-6 上午8:42:11
 */
public interface IBillPayCODConfirmService extends IService {

	/**
	 * 
	 * 查询代收货款汇款确认
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 上午9:10:54
	 */
	List<CODDto> queryPayConfirm(BillCODPayConfirmDto dto, int start, int offset)
			throws SettlementException;

	/**
	 * 
	 * 查询代收货款汇款确认大小,合计金额
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 上午9:12:13
	 */
	CODDto queryPayConfirmSize(BillCODPayConfirmDto dto)
			throws SettlementException;

	/**
	 * 
	 * 更新代收货款汇款成功状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:48:54
	 */
	void updatePayCODOfflineSuccess(List<String> entityIds,
			CurrentInfo currentInfo) throws SettlementException;

	/**
	 * 
	 * 更新代收货款汇款失败状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:49:39
	 */
	void updatePayCODOfflineFailure(List<String> entityIds,
			CurrentInfo currentInfo, String failNotes)
			throws SettlementException;

	/**
	 * 
	 * 更新代收货款反汇款成功状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:51:08
	 */
	void updatePayCODOfflineAntiRemittance(List<String> entityIds,
			CurrentInfo currentInfo, String failNotes)
			throws SettlementException;
}
