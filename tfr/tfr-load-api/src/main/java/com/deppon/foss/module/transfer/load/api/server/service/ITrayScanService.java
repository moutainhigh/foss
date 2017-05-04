package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanExpressEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto;

public interface ITrayScanService {
	/**
	 * 获取上级组织code
	 * @author 105869-foss-heyongdong
	 * @date 2013-7-30 下午4:19:20
	 */
	String querySuperiorOrgCode();
	/**
	 * @function: 查询托盘任务列表  零担
	 * @author：105869-foss-heyongdong
	 * @date：2013-8-1 8:56:59
	 * @param: queryTrayScanConditiondto
	 * */
	List<TrayScanDto> queryTrayScanList(
			QueryTrayScanConditionDto queryTrayScanConditiondto,int limit,int start);
	
	/**
	 * @function: 查询托盘任务列表
	 * @author：105869-foss-heyongdong
	 * @date：2013-8-1 8:56:59
	 * @param: queryTrayScanConditiondto
	 * */
	List<TrayScanExpressEntity> queryTrayScanListExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto,int limit,int start);
	/**
	 * @function: 查询托盘任务总数  零担
	 * @author：105869-foss-heyongdong
	 * @date：2013-8-1 8:56:59
	 * @param: queryTrayScanConditiondto
	 * */
	Long getTrayScanListCount(
			QueryTrayScanConditionDto queryTrayScanConditiondto);
	/**
	 * @function: 查询托盘任务总数  快递
	 * @author：105869-foss-heyongdong
	 * @date：2013-8-1 8:56:59
	 * @param: queryTrayScanConditiondto
	 * */
	Long getTrayScanListCountExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto);
	/** 零担
	 * @function: 查询托盘任务明细
	 * @author：105869-foss-heyongdong
	 * @date：2013-8-2 15:26:27
	 * @param: traytaskCode
	 * */
	List<TrayScanDetaiEntity> queryWaybillByTaskNo(String traytaskCode);
	
	/** 快递
	 * @function: 查询托盘任务明细
	 * @author：105869-foss-heyongdong
	 * @date：2013-8-2 15:26:27
	 * @param: traytaskCode
	 * */
	List<TrayScanExpressEntity> queryWaybillByTaskNoExpress(String traytaskCode);
	
	/**
	 * @function: 查询导出托盘扫描任务时需要的输入流和list
	 * @author：105869-foss-heyongdong
	 * @date：2013-8-2 16:03:20
	 * @param: queryTrayScanConditiondto
	 * */
	List getTrayScanTaskInputStream(
			QueryTrayScanConditionDto queryTrayScanConditiondto);
	
	/**  零担
	 * @function: 查询导出托盘扫描任务的叉车总票数
	 * @author：105869-foss-heyongdong
	 * @date：2013-8-5 12:20:55
	 * @param: queryTrayScanConditiondto
	 * */
	List<TrayScanDto> queryTrayScanSummary(
			QueryTrayScanConditionDto queryTrayScanConditiondto);
	
	/**  快递
	 * @function: 查询导出托盘扫描任务的叉车总票数
	 * @author：105869-foss-heyongdong
	 * @date：2013-8-5 12:20:55
	 * @param: queryTrayScanConditiondto
	 * */
	List<TrayScanDto> queryTrayScanSummaryExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto);
	
	/**
	 *提供装车接口，按运单，流水，外场编码，判断是否已绑定托盘兵扫描
	 *@author 045923 205109-foss-zenghaibin
	 *@date 2014/11/29 15:55
	 *@param wayBillNo seriano outFieldCode
	 ***/
	public Long queryTrayScanTaskDtailCount(String wayBillNo,String seriano,String outFieldCode);
	List getTrayScanTaskInputStreamExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto);
}
