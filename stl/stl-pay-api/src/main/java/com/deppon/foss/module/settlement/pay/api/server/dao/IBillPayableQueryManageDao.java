package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageResultDto;


/**
 * 应付单dao
 * @author 045738-foss-maojianqiang
 * @date 2012-11-19 下午1:42:31
 */
public interface IBillPayableQueryManageDao {
	/**
	 * 根据日期查询应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-19 下午1:45:32
	 */
	List<BillPayableEntity> queryBillPayableByDate(RowBounds rb,BillPayableManageDto dto);
	
	/**
	 * 计算日期查询的总条数和总金额
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-19 下午7:37:36
	 */
	BillPayableManageResultDto countBillPayableByDate(BillPayableManageDto dto);
	
	/**
	 *  根据付款单号和付款单明细来源单号查询应付单
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-3-20 下午6:04:09
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	List<BillPayableEntity> queryListByPaymentNo(BillPayableManageDto dto);
	
	/**
	 * 根据应付单号查询应付单信息
	 * @param map
	 * @return
	 */
	BillPayableEntity queryPayableByPayableNo(String payableNo);
}
