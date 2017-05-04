package com.deppon.foss.module.transfer.platform.api.server.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TruckEfficiencyEntity;
/**
 * 装卸车 效率统计service
 * @author 200978  xiaobingcheng
 * 2015-1-19
 */
public interface ITruckEfficiencyService extends IService{
	
	/**
	 * 装车效率 统计方法
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 */
	void loadAndUnloadEfficiency();
	
	/**
	 * 查询给定部门code所属外场
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param code
	 * @return
	 */
	Map<String, String> queryParentTfrCtrCode(String code);
	
	/**
	 * 查询给定部门code所对应的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param code
	 * @return
	 */
	Map<String, String> queryOperationDeptCode(String code);
	
	/**
	 * 按日期查询装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	List<TruckEfficiencyEntity> queryTruckEfficiencyByDay(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount);
	
	/**
	 * 按日期查询装卸车效率 总条数
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	Long queryTruckEfficiencyByDayCount(TruckEfficiencyEntity truckEfficiencyEntity);
	
	/**
	 * 按日期查询装卸车效率    对于有转运场的 部门查询结果显示  当月1号到当前查询日期的记录
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	List<TruckEfficiencyEntity> queryTruckEfficiencyByDayOfTransfer(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount);
	
	/**
	 * 按日期查询装卸车效率    对于有转运场的 部门查询结果显示  当月1号到当前查询日期的记录 总条数
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	Long queryTruckEfficiencyByDayOfTransferCount(TruckEfficiencyEntity truckEfficiencyEntity);
	
	/**
	 * 按月份查询装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	List<TruckEfficiencyEntity> queryTruckEfficiencyByMonth(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount);
	
	/**
	 * 按月份查询装卸车效率 总条数
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	Long queryTruckEfficiencyByMonthCount(TruckEfficiencyEntity truckEfficiencyEntity);
	
	/**
	 * 生成导出文件名称
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param fileName
	 * @return
	 * @throws TfrBusinessException
	 */
	String encodeFileName(String fileName) throws TfrBusinessException;
	
	/**
	 * 按日期导出装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param truckEfficiencyEntity
	 * @return
	 * @throws TfrBusinessException
	 */
	InputStream queryTruckEfficiencyByDayExcelStream(TruckEfficiencyEntity truckEfficiencyEntity) throws TfrBusinessException;
	
	/**
	 * 按月导出装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param truckEfficiencyEntity
	 * @return
	 * @throws TfrBusinessException
	 */
	InputStream queryTruckEfficiencyByMonthExcelStream(TruckEfficiencyEntity truckEfficiencyEntity) throws TfrBusinessException;

}
