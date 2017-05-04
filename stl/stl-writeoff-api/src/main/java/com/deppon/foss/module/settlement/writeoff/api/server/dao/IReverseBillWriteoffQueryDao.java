package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;


import com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;

/**
 * 反核消查询Dao
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-24 上午11:28:51
 */
public interface IReverseBillWriteoffQueryDao {

	/**
	 * 根据参数集合，分页查询核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-24 上午11:33:15
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityListByParams(ReverseBillWriteoffDto reverseBillWriteoffDto,int start,int limit);
	
	/**
	 * 根据核销单号，分页查询核销单列表
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:47:12
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityByWriteoffBillNo(ReverseBillWriteoffDto reverseBillWriteoffDto,int start,int limit);
	
	/**
	 * 根据运单号，分页查询核销单列表
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:47:12
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityByWaybillNo(ReverseBillWriteoffDto reverseBillWriteoffDto,int start,int limit);

	/**
	 * 根据预付单号，分页查询核销单列表
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:47:12
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityByAdvPayillNo(ReverseBillWriteoffDto reverseBillWriteoffDto);

	/**
	 * 根据应付单号，分页查询核销单列表
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:47:12
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityByPayableBillNo(ReverseBillWriteoffDto reverseBillWriteoffDto);

	/**
	 * 根据参数集合，获取核销单统计信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-24 上午11:33:15
	 */
	ReverseBillWriteoffDto queryBillWriteoffTotalByParams(ReverseBillWriteoffDto reverseBillWriteoffDto);
	
	/**
	 * 根据核销单号，获取核销单统计信息
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:47:12
	 */
	ReverseBillWriteoffDto queryBillWriteoffTotalByWriteoffBillNo(ReverseBillWriteoffDto reverseBillWriteoffDto);
	
	/**
	 * 根据运单号，获取核销单统计信息
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:47:12
	 */
	ReverseBillWriteoffDto queryBillWriteoffTotalByWaybillNo(ReverseBillWriteoffDto reverseBillWriteoffDto);
	
	/**
	 * 根据参数集合，查询全部核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-24 上午11:33:15
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityALLByParams(ReverseBillWriteoffDto reverseBillWriteoffDto);
	
	/**
	 * 根据核销单号，查询全部核销单列表
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:47:12
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityAllByWriteoffBillNo(ReverseBillWriteoffDto reverseBillWriteoffDto);
	
	/**
	 * 根据运单号，查询全部核销单列表
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:47:12
	 * @param reverseBillWriteoffDto
	 *            核消单参数Dto
	 * @return List<BillWriteoffEntity>
	 * 				核销单集合
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityAllByWaybillNo(ReverseBillWriteoffDto reverseBillWriteoffDto);
	
	/**
	 * 根据核销单号，查询待核销的数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-24 上午11:33:15
	 * @param reverseBillWriteoffDto
	 *            核消单参数Dto
	 * @return List<BillWriteoffEntity>
	 * 				核销单集合
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityByNo(ReverseBillWriteoffDto reverseBillWriteoffDto);

	/**
	 * 根据预付单号，获取核销单统计信息
	 * 
	 * 杨书硕
	 * 2013-7-25 下午6:41:25
	 */
	ReverseBillWriteoffDto queryBillWriteoffTotalByAdvPayillNo(
			ReverseBillWriteoffDto reverseBillWriteoffDto);

	/**
	 * 根据应付单号，获取核销单统计信息
	 * 
	 * 杨书硕
	 * 2013-7-25 下午6:41:25
	 */
	ReverseBillWriteoffDto queryBillWriteoffTotalByPayableBillNo(
			ReverseBillWriteoffDto reverseBillWriteoffDto);

	/**
	 * 根据预收单号，获取核销单统计信息
	 * 
	 * 毛建强
	 * 2014-4-03 下午6:41:25
	 */
	ReverseBillWriteoffDto queryBillWriteoffTotalByDepositNo(
			ReverseBillWriteoffDto reverseBillWriteoffDto);
	/**
	 * 根据预收单号，获取核销单信息
	 * 
	 * 毛建强
	 * 2014-4-03 下午6:41:25
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityByDepositNo(
			ReverseBillWriteoffDto reverseBillWriteoffDto);
}
