package com.deppon.foss.module.settlement.pay.api.server.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;

/**
 * 未核销预收账款报表service
 * @author foss-pengzhen
 * @date 2012-11-19 下午4:29:16
 * @since
 * @version
 */
public interface IBillDepositReceivedUnverifyService {


	/**
	 * 查询对应的预收单信息
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	public BillDepositReceivedPayDto queryDepositReceived(
			BillDepositReceivedPayDto billDepositReceivedPayDto,
			CurrentInfo currentInfo, int start, int limit) ;

	/**
	 * 导出预收单报表
	 * @author foss-pengzhen
	 * @date 2012-12-11 下午4:16:38
	 * @param billDepositReceivedPayDto
	 * @param currentInfo
	 * @see
	 */
	public HSSFWorkbook depositReceivedUnverifyListExport(BillDepositReceivedPayDto billDepositReceivedPayDto
			,CurrentInfo currentInfo);
}
