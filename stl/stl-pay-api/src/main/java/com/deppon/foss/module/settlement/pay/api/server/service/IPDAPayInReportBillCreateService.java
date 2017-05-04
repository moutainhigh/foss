package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DriverCollectionRptDto;

/**
 * PDA缴款报表生成服务
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-18 下午6:35:42
 */
public interface IPDAPayInReportBillCreateService extends IService {

	/**
	 * njs 接货
	 */
	public DriverCollectionRptDto queryReceiptGoodsInfo(DriverCollectionRptDto dto,
			CurrentInfo currentInfo);
	
	/**
	 * njs 送货
	 */
	public DriverCollectionRptDto querySendGoodsInfo(DriverCollectionRptDto dto,		
			CurrentInfo currentInfo);
		
	/**
	 * 查询PDA司机接送货信息
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param 查询条件dto
	 *            ,当前登录信息
	 * @date 2012-12-18 下午6:37:29
	 * @return 接送货信息
	 */
	public DriverCollectionRptDto queryReceiptSendGoodsInfo(
			DriverCollectionRptDto dto, CurrentInfo currentInfo);

	/**
	 * 保存司机PDA报表和明细
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param 保存数据
	 *            、当前登录用户
	 * @date 2012-12-18 下午6:37:52
	 * @return
	 */
	public DriverCollectionRptDto addReceiveReportBill(
			DriverCollectionRptDto dto, CurrentInfo currentInfo);

	/**
	 * 查询PDA报表
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param 查询条件dto
	 *            ,当前登录信息,分页参数
	 * @date 2012-12-18 下午6:38:20
	 * @return 缴款信息列表
	 */
	public DriverCollectionRptDto queryReceiveReportBill(
			DriverCollectionRptDto dto, CurrentInfo currentInfo, int start,
			int limit);

	/**
	 * 根据报表编号查询报表
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-25 上午8:22:03
	 * @return 报表实体
	 */
	public DriverCollectionRptEntity queryReceiveReportByReportNo(
			String reportNo);

	/**
	 * 根据报表编号查询报表明细
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-25 上午9:29:00
	 * @param reportNo
	 * @return
	 */
	List<DriverCollectionRptDEntity> queryRptDEntityByReportNo(String reportNo,Date createTime);

	/**
	 * 根据报表编号查询报表明细 --将明细分成接货和送货列表
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-23 下午5:25:18
	 * @return
	 */
	DriverCollectionRptDto queryReportDetail(String reportNo,Date createTime);
}
