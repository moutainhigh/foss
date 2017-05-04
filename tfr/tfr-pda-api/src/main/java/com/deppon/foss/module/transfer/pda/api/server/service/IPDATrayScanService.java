package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryTrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressTransferScanDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDATrayOfflineScanDto;

public interface IPDATrayScanService extends IService{
	/**
	 * 生成托盘扫描任务
	 * 
	 * @param trayScanTask  托盘任务
	 * @return String
	 * 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-6 9:54:50
	 */
	public String createTrayScanTask(PDATrayScanTaskEntity trayScanTask);
	
	/**
	 * 查询托盘任务明细
	 * 
	 * @param waybillNo  运单号
	 * @param seriaNo 流水号
	 * @return PDATrayScanTaskEntity
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-6 18:02:36
	 */
	public PDATrayScanTaskEntity  queryTrayScanDetail(QueryTrayScanTaskEntity querytask);
	
	/**
	 * 更新托盘任务扫描时间和扫描人
	 * 
	 * @param PDATrayScanTaskEntity  	 
	 * @return String
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-6 18:05:59
	 */
	public String updateTrayScanTask(PDATrayScanTaskEntity trayScanTask);
	
	/**
	 * 叉车离线扫描信息PDA接口
	 * @param PDATrayOfflineScanDto
	 * @return String
	 */
	public String createTrayOfflineScanTask(PDATrayOfflineScanDto trayOfflineScanDto);
	 
	
	/**
	 * 根据操作人工号、操作人部门及当前时间，返回该时间往前  hours 个小时内的叉车票数统计值给PDA
	 * @param createrCode 创建人code
	 * @param createrOrgCode 创建人部门code
	 * @param currentTime 当前查询时间
	 * @param hours 查询递归小时
	 * @return PDATrayScanTaskEntity
	 * @author 105795-foss-wqh
	 * @date 2014-01-11
	 * */
	public List<PDATrayScanTaskEntity> queryTraybindforkLiftTicks(String ceaterCode,String createrOrgCode, Date currentTime,int hours);
	
	/**
	 * 根据操作人工号、操作人部门及当前时间，返回该时间往前 hours 个小时内的运单票数统计值给PDA
	 * @param createrCode 创建人code
	 * @param createrOrgCode 创建人部门code
	 * @param currentTime 当前查询时间
	 * @param hours 查询递归小时
	 * @return PDATrayOfflineScanDto
	 * @author 105869-foss-heyondong
	 * @date 2014年5月5日 14:26:44
	 * */
	List<PDATrayOfflineScanDto> queryTrayOfflineScanWaybillQty(String ceaterCode, String createrOrgCode, Date currentTime,int hours);
	
	/**
	 * 快递转货扫描PDA接口
	 * @param ExpressTransferScanDto
	 * @return String
	 * @author 218442-foss-zhuyunrong
	 * @date 2014-12-23
	 */
	public String queryGoodsAreaForPdaTrayScan(ExpressTransferScanDto expressTransferScanDto);
}
