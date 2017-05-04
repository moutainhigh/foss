package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.OverdueSFPaymentApplyEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyQueryDto;

/**
 * 超时装卸费付款申请Dao
 * <p style="display:none">
 * modifyRecord</p>
 * <p style="display:none">
 * version:V1.0,author:105762,date:2014-4-23 下午3:30:39,content:TODO</p>
 * @author 105762
 * @date 2014-4-23 下午3:30:39
 * @since
 * @version
 */
public interface IOverdueSFPaymentApplyDao {

	/**
	 * <p>
	 * 按记账日期查询
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-4-23 下午3:30:29
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	List<OverdueSFPaymentApplyDto> queryByAccountDate(OverdueSFPaymentApplyQueryDto dto);

	/**
	 * <p>
	 * 按记账日期查询总数
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-4-23 下午3:30:29
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	int queryCountByAccountDate(OverdueSFPaymentApplyQueryDto dto);

	/**
	 * <p>
	 * 按来源单号查询
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-4-23 下午3:31:03
	 * @param list
	 * @return
	 * @see
	 */
	List<OverdueSFPaymentApplyDto> queryBySourceBillNo(OverdueSFPaymentApplyQueryDto dto);

	/**
	 * <p>
	 * 按应付单号查询
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-4-23 下午3:31:03
	 * @param dto
	 * @return
	 * @see
	 */
	List<OverdueSFPaymentApplyDto> queryByPayableNo(OverdueSFPaymentApplyQueryDto dto);

	/**
	 * <p>
	 * 插入或更新超时装卸费付款申请单
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-7 下午5:23:02
	 * @param en
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IOverdueSFPaymentApplyDao#update(com.deppon.foss.module.settlement.consumer.api.shared.domain.OverdueSFPaymentApplyEntity)
	 */
	int insertOrUpdate(OverdueSFPaymentApplyEntity en);

	/**
	 * <p>审核</p> 
	 * @author 105762
	 * @date 2014-5-14 上午11:05:33
	 * @param en
	 * @return
	 * @see
	 */
	int updateForAudit(OverdueSFPaymentApplyEntity en);
}
