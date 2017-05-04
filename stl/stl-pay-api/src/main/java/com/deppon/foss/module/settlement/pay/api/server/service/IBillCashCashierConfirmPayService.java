package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashCashierConfirmDto;

/**
 * 确认收银service
 * @author foss-pengzhen
 * @date 2012-12-13 下午2:50:05
 * @since
 * @version
 */
public interface IBillCashCashierConfirmPayService {

	/**
	 * 确认收银时间参数查询
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午2:50:36
	 * @param billCashCashierConfirmDto 确认收银Dto
	 * @param currentInfo 用户信息
	 * @return
	 * @see
	 */
	List<BillCashCashierConfirmDto> queryCashCashierConfirmPags(
			BillCashCashierConfirmDto billCashCashierConfirmDto,CurrentInfo currentInfo);
	
	/**
	 * 确认收银提交
	 * @author foss-pengzhen
	 * @date 2012-12-17 下午2:38:32
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @see
	 */
	void cashCashierConfirmCommit(List<BillCashCashierConfirmDto> billCashCashierConfirmDtos,CurrentInfo currentInfo);
	
	/**
	 * 收银确认判断部门
	 * @author foss-pengzhen
	 * @date 2012-12-21 下午4:39:37
	 * @return
	 * @see
	 */
	String deptJudge(CurrentInfo currentInfo);
	
	/**
	 * 查询运单号
	 * @author foss-pengzhen
	 * @date 2013-6-28 上午10:54:33
	 * @return
	 * @see
	 */
	StringBuffer cashCashierConfirmDetailWaybillNo(String waybillNo);
	
	/**
	 * 导出现金收银列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	ExportResource exportBillCashCashierConfirms(
			List<LinkedHashMap> billCashCashierConfirms,
			CurrentInfo currentInfo);

    /**
     * 查询未收银确认的代收货款相关单据
     * @param billCashCashierConfirmDto
     * @return 单号list
     */
    List<String> queryUnconfirmedCodRelatedBill(BillCashCashierConfirmDto billCashCashierConfirmDto);
}
