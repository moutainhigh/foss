package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillClaimEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimQueryDto;

/**
 * 理赔、服务补救、退运费单据Dao接口
 * @author foss-qiaolifeng
 * @date 2013-1-25 上午10:19:32
 */
public interface IBillClaimDao {

	/**
	 * 新增理赔、服务补救、退运费单据
	 * @author foss-qiaolifeng
	 * @date 2013-1-25 上午10:20:43
	 */
	int addBillClaimEntity(BillClaimEntity entity);
	
	/**
	 * 查询理赔、服务补救、退运费单据根据根据运单号
	 * @author foss-qiaolifeng
	 * @date 2013-1-25 上午10:20:48
	 */
	BillClaimEntity queryBillClaimEntity(String waybillNo,String active);
	
	/**
	 * 根据参数查询理赔总条数
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午3:50:54
	 */
	Long queryBillClaimCount(BillClaimQueryDto billClaimQueryDto);
	
	/**
	 * 根据参数分页查询理赔单信息
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午3:51:10
	 */
	List<BillClaimEntity> queryBillClaimListByPage(BillClaimQueryDto billClaimQueryDto,int start,int limit);
	
	/**
	 * 根据运单号查询理赔信息
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午4:26:39
	 */
	List<BillClaimEntity> queryBillClaimListByWayBillNos(BillClaimQueryDto billClaimQueryDto);
	
	/**
	 * 修改理赔信息
	 * @author foss-qiaolifeng
	 * @date 2013-1-30 下午3:59:17
	 */
	int updateBillClaimEntity(BillClaimEntity entity);
	
}
