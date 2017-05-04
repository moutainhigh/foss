package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageResultDto;



/**
 * 还款单管理Dao
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午9:01:18
 */
public interface IBillRepaymentManageDao {
	
	/**
	 *查询还款单记账日期条数
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午4:18:48
	 */
	List<BillRepaymentManageDto>queryBillAccountDateRepayment(BillRepaymentManageDto billRepaymentManageDto,int start,int limit);
	
	
	
	/**
	 * 查询还款单记账日期总条数
	 * @author 095793-foss-LiQin
	 * @date 2012-12-4 下午7:19:46
	 */
	BillRepaymentManageResultDto queryCountBillAccountDateRepayment(BillRepaymentManageDto billRepayDto);
	
	
	
	
	/**
	 * 按业务日期查询还款单条数
	 * @author 095793-foss-LiQin
	 * @date 2012-12-7 下午6:15:52
	 */
	List<BillRepaymentManageDto>queryBillBusinessDateRepayment(BillRepaymentManageDto billRepaymentManageDto,int start,int limit);
	
	
	/**
	 * 按业务日期查询还款单
	 * @author 095793-foss-LiQin
	 * @date 2012-12-7 下午6:16:18
	 */
	BillRepaymentManageResultDto queryCountBillBusinessDateRepayment(BillRepaymentManageDto billRepayDto);
	
	
	/**
	 * 根据还款单查询还款单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-9 下午4:04:16
	 */
	List <BillRepaymentManageDto> queryBillRepaymentByNos(BillRepaymentManageDto billRepaymentManageDto);
	
	/**
	 * 根据运单查询运单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-9 下午4:04:19
	 */
	List <BillRepaymentManageDto> queryBillRepaymentByWbNos(BillRepaymentManageDto billRepaymentManageDto);
	
	/**
	 * 根据对账单查询对账单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-9 下午4:04:22
	 */
	List <BillRepaymentManageDto> queryBillRepaymentByStNos(BillRepaymentManageDto billRepaymentManageDto);
	
	
	
	/**
	 * 根据还款单号查询是否存在反核销单据
	 * @author foss-qiaolifeng
	 * @date 2012-11-12 上午10:08:09
	 */
	BillRepaymentEntity queryRebackBillByNo(String repaymentNo,String active);
	
	
	/**
	 * 
	 * 根据传入的一到多个来源单号，获取一到多条还款单信息
	 * 
	 * @author dp-liqin
	 * @date 2013-01-23 下午6:55:56
	 * @param sourceBillNos
	 *            来源单据号集合
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	List<BillRepaymentManageDto> queryBySourceBillNOs(BillRepaymentManageDto billRepaymentManageDto);


	/**
	 * 
	 * 根据传入的一到多个银联交易流水号，获取一到多条还款单信息
	 * @author 045738-foss-maojianqiang
	 * @date 2013-7-22 下午4:47:29
	 * @param billRepayDto
	 * @return
	 */
	List<BillRepaymentManageDto> queryByBatchBillNOs(BillRepaymentManageDto billRepayDto);
}
