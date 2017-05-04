package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffAmountDto;

/**
 * 还款单DAO
 * 
 * @author ibm-zhuwei
 * @date 2012-11-13 下午3:58:17
 */
public interface IBillRepaymentEntityDao {

	// -------------------- write methods --------------------

	/**
	 * 新增还款单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-13 下午4:53:42
	 * @param 还款单
	 * @return
	 */
	int add(BillRepaymentEntity entity);

	/**
	 * 红冲还款单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-18 上午11:24:19
	 * @param 还款单
	 * @return
	 */
	int updateByWriteBack(BillRepaymentEntity entity);

	/**
	 * 反核销-修改还款单的反核销金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-29 下午2:26:41
	 * @param dto
	 *            核销单DTO
	 * @return
	 * @see
	 */
	int updateByReverseWriteoff(BillWriteoffAmountDto dto);

	/**
	 * 批量审核/反审核还款单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-12 上午11:07:07
	 * @param dto
	 *            还款单DTO
	 * @return
	 */
	int updateByBatchAudit(BillRepaymentDto dto);

	/**
	 * 批量确认收银还款单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-12-18 下午2:19:05
	 * @param dto
	 *            还款单DTO
	 * @return
	 */
	int updateByConfirmCashier(BillRepaymentDto dto);

	// -------------------- read methods --------------------

	/**
     * 
     * 根据传入的一到多个还款单号，获取一到多条还款单信息
     * @author foss-pengzhen
     * @date 2013-03-25 下午6:55:54
     * @param repaymentNos   还款单id号集合
     * @param active		 是否有效
     * @return
     * @see
     */
    List<BillRepaymentEntity> queryByRepaymentIds(List<String> repaymentIds,String active);
	
	/**
	 * 根据传入的一到多个还款单号，获取一到多条还款单信息
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-12 下午6:55:54
	 * @param repaymentNos
	 *            还款单号集合
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	List<BillRepaymentEntity> queryByRepaymentNOs(List<String> repaymentNos,
			String active);

	/**
	 * 
	 * 根据传入的一到多个来源单号，获取一到多条还款单信息
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-17 下午7:55:56
	 * @param sourceBillNos
	 *            来源单据号集合
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	List<BillRepaymentEntity> queryBySourceBillNOs(List<String> sourceBillNos,
			String active);

	/**
	 * 
	 * 根据传入的到达实收单编号，查询是否存在有效的还款单 [到达实收单编号存储在还款单的来源单号属性]
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-17 下午8:08:20
	 * @param sourceBillNo
	 *            来源单号
	 * @param sourceBillType
	 *            来源单据类型
	 * @return
	 * @see
	 */
	String queryBySourceBillNO(String sourceBillNo, String sourceBillType);

	/**
	 * 根据外发单号和外发代理编码是否已存在有效的还款单记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 下午7:01:29
	 * @param dto
	 *            还款单条件DTO
	 * @return
	 */
	int queryBillRepaymentByExternalBillNo(BillRepaymentConditionDto dto);

	/**
	 * 查询符合条件的还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-21 上午8:20:33
	 * @param dto
	 *            还款单条件DTO
	 * @return
	 */
	List<BillRepaymentEntity> queryBillRepaymentByCondition(
			BillRepaymentConditionDto dto);

	/**
	 * 根据来源单号集合和部门编码集合，查询还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 上午9:33:42
	 * @param sourceBillNos
	 * @param orgCodes
	 * @param active
	 * @return
	 */
	List<BillRepaymentEntity> queryBySourceBillNOsAndOrgCodes(List<String> sourceBillNos,
			List<String> orgCodes, String active,CurrentInfo currentInfo);

	/**
	 * 根据应收单号查询还款单信息
	 * @author 231434-foss-bieyexiong
	 * @date 2016-10-03 
	 */
	List<BillRepaymentEntity> queryRepaymentByReceivableNo(String receivableNo,String active,String writeoffType);
}
