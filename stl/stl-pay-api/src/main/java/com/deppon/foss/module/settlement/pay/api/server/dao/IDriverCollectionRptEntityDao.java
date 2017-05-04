package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DriverCollectionRptDto;
/**
 * 司机缴款报表dao
 * @author 045738-foss-maojianqiang
 * @date 2012-12-18 下午7:52:40
 */
public interface IDriverCollectionRptEntityDao {

	/**
	 * 查询收款报表信息
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param
	 * @date 2012-12-18 下午7:33:19
	 * @return
	 */
	public List<DriverCollectionRptEntity> queryReceiveReportBill(DriverCollectionRptDto dto, int start, int limit);

	/**
	 * 保存司机收款报表
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param 缴款报表头
	 * @date 2012-12-18 下午7:33:36
	 * @return
	 */
	public void addReceiveReportBill(DriverCollectionRptEntity entity,CurrentInfo currentInfo);
	
	/**
	 * 获取查询日期内，该司机该车辆最近一次缴款报表日期
	 * @author 045738-foss-maojianqiang
	 * @param 
	 * @date 2012-12-18 下午7:35:54
	 * @return
	 */
	public Date selectMaxReportDate(DriverCollectionRptDto dto);

	/**
	 * 查询收款报表总条数
	 * @author 045738-foss-maojianqiang
	 * @param 查询条件dto
	 * @date 2012-12-19 下午2:14:07
	 * @return 结果条数
	 */
	public int queryReceiveReportBillCount(DriverCollectionRptDto dto);

	/**
	 * 查询生成报表时，是否已经有报表生成
	 * @author 045738-foss-maojianqiang
	 * @param 报表头实体
	 * @date 2012-12-21 下午3:30:56
	 * @return 符合条件条数
	 */
	int isExistReport(DriverCollectionRptEntity entity);

	/**
	 * 根据报表号查询报表
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-25 上午8:26:36
	 * @param reportNo
	 * @return 报表头
	 */
	public DriverCollectionRptEntity queryReceiveReportByReportNo(
			String reportNo);

	/**
	 * 获取查询日期内，该司机该车辆最近一次缴款报表日期 零担
	 * @author 045738-foss-maojianqiang
	 * @param 
	 * @date 2014-05-06
	 * @return
	 */
	public Date selectMaxReportDateByDate(DriverCollectionRptDto dto);

}
