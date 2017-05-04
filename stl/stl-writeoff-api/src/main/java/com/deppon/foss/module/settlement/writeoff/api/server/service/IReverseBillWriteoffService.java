package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto;

/**
 * 反核消查询Service
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-24 上午11:35:42
 */
public interface IReverseBillWriteoffService {

	/**
	 * 根据参数集合，分页查询核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-24 上午11:33:15
	 * @param reverseBillWriteoffDto,limit,start
	 * 			cInfo
	 * 			核销单参数Dto,条数,序号
	 *  		当前登录用户
	 * @return ReverseBillWriteoffDto
	 * 			核销单返回dto
	 */
	ReverseBillWriteoffDto queryBillWriteoffEntityList(
			ReverseBillWriteoffDto reverseBillWriteoffDto,int start,int limit,CurrentInfo cInfo);
	
	/**
	 * 根据参数集合，查询全部核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-24 上午11:33:15
	 * @param reverseBillWriteoffDto,
	 * 			cInfo
	 * 			核销单参数Dto,
	 *  		当前登录用户
	 * @return ReverseBillWriteoffDto
	 * 			核销单返回dto
	 */
	ReverseBillWriteoffDto queryBillWriteoffEntityAll(
			ReverseBillWriteoffDto reverseBillWriteoffDto,CurrentInfo cInfo);
		
	/**
	 * 根据核销单号，查询待核销的数据
	 * @author foss-qiaolifeng
	 * @param reverseBillWriteoffDto,limit,start
	 * 			cInfo
	 * 			核销单参数Dto,条数,序号
	 *  		当前登录用户
	 * @return List<BillWriteoffEntity>
	 * 			核销单集合
	 * @date 2012-10-31 上午9:35:53
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityByNo(
			ReverseBillWriteoffDto reverseBillWriteoffDto,CurrentInfo cInfo);
	
	/**
	 * 根据核销单号，核销数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-24 上午11:33:15
	 * @param reverseBillWriteoffDto,
	 * 			cInfo
	 * 			核销单参数Dto,
	 *  		当前登录用户
	 * @return void
	 * 			
	 */
	void reverseBillWriteOff(ReverseBillWriteoffDto reverseBillWriteoffDto,CurrentInfo cInfo);

	/**
	 * 根据核销单号，代打木架反核销数据
	 * 
	 * @date 2014-6-24 上午11:33:15
	 */
	void reverseWoodenStatement(List<BillWriteoffEntity> writeoffEntities,
			CurrentInfo cInfo);
	
}
