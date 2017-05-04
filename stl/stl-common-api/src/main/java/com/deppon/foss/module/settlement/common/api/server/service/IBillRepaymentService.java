package com.deppon.foss.module.settlement.common.api.server.service;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentDto;


/**
 * 还款单服务
 * @author ibm-zhuwei
 * @date 2012-11-13 下午3:58:17
 */
public interface IBillRepaymentService extends IService  {

	// -------------------- write methods --------------------
	
	/**
	 * 新增还款单
	 * @author ibm-zhuwei
	 * @date 2012-11-13 下午4:01:03
	 * @param entity
	 * @param currentInfo
	 */
	void addBillRepayment(BillRepaymentEntity entity, CurrentInfo currentInfo);

	/**
	 * 红冲还款单
	 * @author ibm-zhuwei
	 * @date 2012-11-13 下午4:39:30
	 * @param entity
	 * @param currentInfo
	 */
	void writeBackBillRepayment(BillRepaymentEntity entity, CurrentInfo currentInfo);

    /**
     * 反核销-修改还款单的反核销金额
     * @author 099995-foss-wujiangtao
     * @date 2012-10-29 下午2:41:54
     * @param id
     * @param bverifyAmount
     * @param currentInfo
     * @return
     * @see
     */
    void reverseWriteoffBillRepayment(BillRepaymentEntity entity, BigDecimal bverifyAmount, CurrentInfo currentInfo);
    
    /**
     * 批量审核还款单
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-12 上午11:08:48
     * @param dto
     * @param currentInfo
     */
    void auditBillRepayments(BillRepaymentDto dto, CurrentInfo currentInfo);
    
    /**
     * 批量反审核还款单
     * @author 099995-foss-wujiangtao
     * @date 2012-11-12 上午11:09:22
     * @param dto
     * @param currentInfo
     */
    void reverseAuditBillRepayments(BillRepaymentDto dto, CurrentInfo currentInfo);
    
    /**
     * 批量确认收银还款单
     * @author ibm-zhuwei
     * @date 2012-12-18 下午2:05:56
     * @param dto
     * @param currentInfo
     * @return void
     */
    void confirmCashierBillRepayments(BillRepaymentDto dto, CurrentInfo currentInfo);
    
	// -------------------- read methods --------------------
    
    /**
     * 
     * 根据传入的一到多个还款单ID号，获取一到多条还款单信息
     * @author foss-pengzhen
     * @date 2013-03-25 下午6:55:54
     * @param repaymentNos   还款单id号集合
     * @param active		 是否有效
     * @return
     * @see
     */
    List<BillRepaymentEntity> queryByRepaymentIds(List<String> repaymentIds,String active);
    
	/**
     * 
     * 根据传入的一到多个还款单号，获取一到多条还款单信息
     * @author dp-wujiangtao
     * @date 2012-10-12 下午6:55:54
     * @param repaymentNos   还款单号集合
     * @param active         是否有效
     * @return
     * @see
     */
    List<BillRepaymentEntity> queryByRepaymentNOs(List<String> repaymentNos, String active);
    
    /**
     * 
     * 根据传入的一个还款单号，获取一条还款单信息
     * @author dp-wujiangtao
     * @date 2012-10-12 下午6:55:54
     * @param repaymentNos   还款单号
     * @param active         是否有效
     * @return
     * @see
     */
    BillRepaymentEntity queryByRepaymentNO(String repaymentNo, String active);
    
    /**
     * 
     * 根据传入的一到多个来源单号，获取一到多条还款单信息
     * @author dp-wujiangtao
     * @date 2012-10-17 下午8:02:28
     * @param sourceBillNos
     * @param active
     * @return
     * @see
     */
    List<BillRepaymentEntity> queryBySourceBillNOs(List<String> sourceBillNos, String active);
    
    /**
     * 根据传入的到达实收单编号，查询是否存在有效的还款单
     * [到达实收单编号存储在还款单的来源单号属性]
     * @author dp-wujiangtao
     * @date 2012-10-17 下午8:42:47
     * @param sourceBillNo   来源单号
     * @param sourceBillType       来源单据类型
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
     * @return
     */
    int queryBillRepaymentByExternalBillNo(BillRepaymentConditionDto dto);
    
    /**
     * 查询符合条件的还款单信息
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-21 上午8:26:30
     * @param dto
     * @return
     */
    List<BillRepaymentEntity> queryBillRepaymentByCondition(BillRepaymentConditionDto dto);
    
    /**
     * 根据来源单号集合和部门编码集合，查询还款单信息
     *  
     * @author 099995-foss-wujiangtao
     * @date 2013-1-22 上午9:32:58
     * @param sourceBillNos
     * @param orgCodes
     * @param active
     * @param currentInfo
     * @return
     */
    List<BillRepaymentEntity> queryBySourceBillNOsAndOrgCodes
    (List<String> sourceBillNos,List<String> orgCodes, String active,CurrentInfo currentInfo);
    
    /**
     * 当还款单全部金额都反核消后，将原还款单作废
     *
     * @author foss-qiaolifeng
     * @date 2013-5-27 上午10:08:12
     * @param
     * @return 成功失败标记
     * @exception SettlementException
     * @see
     */
    void revereWriteoffBillRepayment(BillRepaymentEntity entity, CurrentInfo currentInfo);
    
    /**
	 * 根据应收单号查询还款单信息
	 * @author 231434-foss-bieyexiong
	 * @date 2016-10-03 
	 */
	List<BillRepaymentEntity> queryRepaymentByReceivableNo(String receivableNo,String active,String writeoffType);
}
