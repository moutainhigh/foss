package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.HashMap;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanExpressEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto;

/** 
 * @className: ITrayScanDao
 * @author: 105869-foss-heyongdong
 * @description: 托盘扫描模块dao接口
 * @date: 2013-8-1 9:03:15
 * 
 */
public interface ITrayScanDao {
	
	/**
	 * 查询托盘扫描任务  零担
	 * @author heyongdong
	 * @date 2013-8-1 9:52:40
	 * */
	List<TrayScanDto> queryTrayScanList(
			QueryTrayScanConditionDto queryTrayScanConditiondto, int limit,
			int start);
	
	/**
	 * 查询托盘扫描任务  快递
	 * @author heyongdong
	 * @date 2013-8-1 9:52:40
	 * */
	String queryTrayScanListExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto, int limit,
			int start);
	/**
	 * 查询托盘扫描任务总数   零担
	 * @author heyongdong
	 * @date 2013-8-1 9:52:40
	 * */
	Long getTrayScanListCount(
			QueryTrayScanConditionDto queryTrayScanConditiondto);
	
	/**
	 * 查询托盘扫描任务总数   快递
	 * @author heyongdong
	 * @date 2013-8-1 9:52:40
	 * */
	Long getTrayScanListCountExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto);
	
	/** 零担
	 * 查询托盘扫描任务明细
	 * @author heyongdong
	 * @date 2013-8-2 15:31:00
	 * */
	List<TrayScanDetaiEntity> queryWaybillByTaskNo(String traytaskCode);
	
	/** 快递
	 * 查询托盘扫描任务明细
	 * @author heyongdong
	 * @date 2013-8-2 15:31:00
	 * */
	String queryWaybillByTaskNoExpress(String traytaskCode);
	
	/**
	 * 查询托盘扫描任务不用分页查询
	 * @author heyongdong
	 * @date 2013-8-2 16:18:31
	 * */
	List<TrayScanDto> queryTrayScanListNoPage(
			QueryTrayScanConditionDto queryTrayScanConditiondto);

	/**  零担
	 * 查询托盘扫描任务总票数
	 * @author heyongdong
	 * @date 2013-8-5 12:26:59
	 * */
	List<TrayScanDto> queryTrayScanSummary(
			QueryTrayScanConditionDto queryTrayScanConditiondto);
	
	/**  快递
	 * 查询托盘扫描任务总票数
	 * @author heyongdong
	 * @date 2013-8-5 12:26:59
	 * */
	List<TrayScanDto> queryTrayScanSummaryExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto);
	
	/**
	 *提供装车接口，按运单，流水，外场编码，判断是否已绑定托盘兵扫描
	 *@author 205109-foss-zenghaibin
	 *@date 2014/11/29 15:55
	 *@param wayBillNo seriano outFieldCode
	 ***/
	public Long queryTrayScanTaskDtailCount(HashMap map);
	
	
	/**
	 *提供装车接口，按运单，流水，外场编码，判断是否已绑定托盘兵扫描
	 *@author 205109-foss-zenghaibin
	 *@date 2014/11/29 15:55
	 *@param wayBillNo seriano outFieldCode
	 ***/
	public Long queryOfflienTrayScanTaskDtailCount(HashMap map);

	List<TrayScanExpressEntity> queryTrayScanListNoPageExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto);
}
