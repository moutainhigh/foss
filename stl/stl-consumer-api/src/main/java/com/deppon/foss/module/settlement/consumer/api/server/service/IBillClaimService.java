package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillClaimEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimResultDto;


/**
 * 理赔、服务补救、退运费单据service接口
 * @author foss-qiaolifeng
 * @date 2013-1-25 上午9:55:52
 */
public interface IBillClaimService extends IService{

	/**
	 * 新增理赔、服务补救、退运费单据
	 * @author foss-qiaolifeng
	 * @date 2013-1-25 上午9:58:02
	 */
	int addBillClaimEntity(BillClaimEntity entity,CurrentInfo currentInfo);
	
	
	/**
	 * 查询理赔、服务补救、退运费单据根据根据运单号
	 * @author foss-qiaolifeng
	 * @date 2013-1-25 上午10:04:38
	 */
	BillClaimEntity queryBillClaimEntity(String waybillNo);
	
	/**
	 * 查询理赔、服务补救、退运费单据列表
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午3:06:17
	 */
	BillClaimResultDto queryBillClaimList(BillClaimQueryDto billClaimQueryDto,int start,int limit,CurrentInfo cInfo);
	
	/**
	 * 根据运单号退回理赔、服务补救、退运费单
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午3:07:23
	 */
	void returnBillClainEntity(String waybillNo,CurrentInfo currentInfo,String rtnReason);
	
}
