package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WaybillReceivableDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 应收单服务
 * 
 * @author 099995-foss-wujiangtao
 * 
 */
public interface IBillReceivableService extends IService {

	// -------------------- write methods --------------------
	
	/**
	 * 新加应收单
	 * @author ibm-zhuwei
	 * @date 2012-11-5 下午3:29:01
	 * @param entity
	 * @param currentInfo
	 */
	void addBillReceivable(BillReceivableEntity entity, CurrentInfo currentInfo);

	/**
	 * 红冲应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午2:36:52
	 * @param entity
	 * @param user
	 * @return
	 * @see
	 */
	void writeBackBillReceivable(BillReceivableEntity entity, CurrentInfo currentInfo);

	/**
	 * 应收单核销/反核销
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-19 上午11:13:37
	 * @param entity
	 * @param writeoffAmount
	 * @param currentInfo
	 */
	void writeoffBillReceivable(BillReceivableEntity entity, BigDecimal writeoffAmount, CurrentInfo currentInfo);

	/**
	 * 签收时 确认收入日期
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-22 上午11:09:58
	 * @param entity
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void updateBillReceivableByConfirmIncome(BillReceivableEntity entity, CurrentInfo currentInfo);

	/**
	 * 反签收时取消确认收入日期
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-22 上午11:10:52
	 * @param entity
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void updateBillReceivableByReverseConfirmIncome(BillReceivableEntity entity, CurrentInfo currentInfo);

	/**
	 * 批量审核空运其他应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-30 下午7:27:11
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void auditAirOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo);

	/**
	 * 批量反审核空运其他应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-30 下午7:27:18
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void reverseAuditAirOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo);
	

	/**
	 * 批量审核快递代理其他应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-30 下午7:27:11
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void auditLandOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo);

	/**
	 * 批量反审核快递代理其他应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-30 下午7:27:18
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void reverseAuditLandOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo);

	/**
	 * 批量审核偏线其他应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-30 下午7:27:11
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void auditPAOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo);

	/**
	 * 批量反审核偏线其他应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-30 下午7:27:18
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void reversePAAuditOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo);


	/**
	 * 锁定应收单 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午10:20:46
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	void lockBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo);
	
	/**
	 * 解锁应收单
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午10:21:10
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	void unlockBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo);
	
	/**
	 * 批量设置/取消应收单的对账单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午6:21:52
	 * @param dto
	 * @param currentInfo
	 */
	void batchUpdateByMakeStatement(BillReceivableDto dto, CurrentInfo currentInfo);

	// -------------------- valid methods --------------------
	
	/**
	 * 验证一个运单是否存在相同类型的多条应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-1 下午7:26:12
	 * @param list
	 * @see
	 */
	void validateWaybillForBillReceivable(List<BillReceivableEntity> list);
	
	// -------------------- read methods --------------------
	
	/**
	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-12 上午11:58:36
	 * @param receivableNos
	 *            应收单号集合
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	List<BillReceivableEntity> queryByReceivableNOs(List<String> receivableNos,
			String active);

	/**
	 * 根据传入的一个应收单号，获取一条应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-12 上午11:58:36
	 * @param receivableNo
	 *            应收单号
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	BillReceivableEntity queryByReceivableNO(String receivableNo, String active);

	/**
	 * 根据传入的一到多个来源单号，获取一到多条应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-15 上午11:52:05
	 * @param sourceBillNos
	 *            来源单号集合
	 * @param active
	 *            是否有效
	 * @param sourceBillType  来源单据类型
	 * @return
	 * @see
	 */
	List<BillReceivableEntity> queryBySourceBillNOs(List<String> sourceBillNos,
			String sourceBillType, String active);


	/**
	 * 根据传入的运单号和单据类型等参数，查询[到付运费/始发/代收货款]有效应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-16 上午9:55:06
	 * @param dto
	 * @return
	 * @see
	 */
	List<BillReceivableEntity> queryBillReceivableByCondition(
			BillReceivableConditionDto dto);
	
	/**
	 * 查询始发月结
	 * ECS-327090
	 * @date 2016-5-24 
	 * 
	 */
	List<BillReceivableEntity> queryBillReceivableMonthlyStatement(
			BillReceivableConditionDto dto);

	/**
	 * 根据运单号查询客户的应收单到付金额和应收代收货款金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 上午8:48:04
	 * @param dto
	 * @return
	 * @see
	 */
	List<BillReceivableEntity> queryReceivableAmountByCondition(
			BillReceivableConditionDto dto);

	/**
	 * 根据运单号和外发单号、客户编码、判断是否已存在有效的偏线到达应收单
	 *
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 下午3:34:09
	 * @param dto
	 * @return
	 */
	int queryReceivableByExternalBillNo(BillReceivableConditionDto dto);

	/**
	 * 根据ID（或应收单）与分区键查询应收单
	 * 根据【id,accountDate】或者【receiveNos,accountDate】批量查询
	 * @author ibm-zhuwei
	 * @date 2012-11-29 下午2:34:58
	 */
	List<BillReceivableEntity> queryPartitionsByConditions(BillReceivableDto dtos);
	
	
	/**
     * 按照运单号和应付部门编码集合查询应收单信息
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-12-28 下午3:14:31
     * @param waybillNos
     * @param orgCodeList
     * @param active
     * @return
     */
     List<BillReceivableEntity> queryByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList, String active,CurrentInfo currentInfo);
    
     /**
      * 根据运单号，查询开单付款方式为网上支付的应收单的未核销金额
      *  
      * @author 099995-foss-wujiangtao
      * @date 2013-1-15 下午5:23:55
      * @param waybillNo
      * @return
      */
     WaybillReceivableDto  queryReceivableAmountByWaybillNO(String waybillNo);
     
     /**
      * 根据运单号集合或来源单号结合，单据类型集合查询应收单信息
      * 
      * @author 099995-foss-wujiangtao
      * @date 2013-1-16 下午2:27:09
      * @param waybillNos
      * @param sourceBillNos
      * @param sourceBillType
      * @param billTypes
      * @param active
      * @param isRedBack
      * @return
      */
     List<BillReceivableEntity> queryByWaybillNosOrSourceBillNosAndBillTypes
     (List<String> waybillNos,List<String> sourceBillNos,String sourceBillType,List<String> billTypes,String active,String isRedBack);
     
     /**
      * 根据客户编码，获取客户是否存在应收未核销金额 
      * 
      * @author 099995-foss-wujiangtao
      * @date 2013-1-16 下午8:28:59
      * @param customerCode
      * @return
      */
     boolean queryIsExistsReceivableAmountByCustomerCode(String customerCode);
     
     /**
      * 根据来源单号集合和应收部门编码集合，查询应收单信息
      * 
      * @author 099995-foss-wujiangtao
      * @date 2013-1-22 下午4:35:02
      * @param sourceBillNos
      * @param orgCodes
      * @param sourceBillType
      * @param active
      * @return
      */
     List<BillReceivableEntity> queryBySourceBillNOsAndOrgCodes
     (List<String> sourceBillNos,List<String> orgCodes,String sourceBillType, String active,CurrentInfo currentInfo);
     
 	/**
 	 * 锁定应收单信息
 	 * @author 088933-foss-zhangjiheng
 	 * @date 2012-11-23 上午9:05:22
 	 */
 	int updateReceiveBillInfoForLock(BillReceivableOnlineQueryDto queryDto);

     
     /**
      * 
      * 根据应收单号集合批量标记和反标记状态
      * @author 045738-foss-maojianqiang
      * @date 2013-5-21 下午6:21:21
      * @param numbers
      * @param active
      * @return
      */
     void updateStampByNumbers(BillReceivableDto dto,CurrentInfo currentInfo);

     /**
      * 按应收单号和数据权限查询应收单
      * @author 045738-foss-maojianqiang
      * @date 2013-6-12 下午6:36:24
      * @param receivableNos
      * @param active
      * @param currentInfo
      * @return
      */
     List<BillReceivableEntity> queryByReceivableNosAndOrgCodes(
 			List<String> receivableNos, String active,CurrentInfo currentInfo);

     /**
 	 * 根据客户编码，获取客户存在的应收未核销金额
 	 * 
 	 * @author foss-pengzhen
 	 * @date 2013-1-16 下午8:36:19
 	 * @param customerCode
 	 * @return
 	 */
	List<BillReceivableEntity> queryReceivableAmountByCustomerCode(
			String customerCode);
	
    /**
     * 
     * @param dto 查询条件
     * @return 根据条件查询应收单的最小欠款时间
     */
    Date queryMinDebitTime(BillReceivableConditionDto dto);
     

	/**
	 * <p>审核包装其他应收</p> 
	 * @author 105762
	 * @date 2014-6-11 上午9:12:29
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	void auditPackingOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo);


	/**
	 * <p>反审核包装其他应收</p> 
	 * @author 105762
	 * @date 2014-6-11 上午9:12:43
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	void reverseAuditPackingOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo);
	/**
	 * <p>红冲包装其他应收</p> 
	 * @author 105762
	 * @date 2014-6-11 上午9:11:54
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	void writeBackPackingOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo);
	
	/**
	 * 根据传入的一到多个运单单号，获取一到多条应收单信息
     * @author 邓大伟
     * @date 2014-12-12
     * @param wayBillNos  运单单号集合
	 */
	List<ReceivableEntity> queryByWaybillNOs(List<String> waybillNos,List<String> waybillList);

	/**
	 * 根据传入的一到多个运单单号，获取一到多条应收单信息
     * @author 邓大伟
     * @date 2015-02-03
     * @param wayBillNos  运单单号集合
	 */
	List<BillReceivableEntity> queryReceivableByWaybillNOs(List<String> waybillNos);
	
	
	/**
	 * 是否开单付款方式为到付(其他)且已在网上支付(其他)的运单
	 * @author 239284-foss-xiedejie
	 * @date 2015-4-2 上午9:30:00
	 * @param   billTypes 单据类型集合 <br/>
	 *                  wayBillNo 运单号  <br/>
	 *                  rePaymentNo 还款单号  <br/>
	 *                  sourceNo   来源单号    <br/>
	 *                  actualPayType  实际付款方式 
	 */
	int queryIsOrPayByBillNo(String[] billTypes, String wayBillNo, String rePaymentNo, String sourceNo,  String actualPayType);
	
	/**
	 * 根据运单号查询有效的应收单（单据子类型为单一的一种）
	 * @author 231438
	 * @return
	 */
	BillReceivableEntity queryReceivableByWaybillNoAndBillType(BillReceivableConditionDto dto);
	/**
	 * 合伙人应收单红冲
	 * @author 231438
	 * @param entity 应收单实体<br/>
	 * 		  currentInfo 操作者信息<br/>
	 */
	void writeBackPartnerBillReceivable(BillReceivableEntity entity, CurrentInfo currentInfo);

    /**
     * 修改应收单扣款状态
     *
     *@author 099995-foss-hemingyu
     * @date 2016-01-08 上午10:59:38
     * @param entity
     *
     */
    void updateBillReceivableWithholdStatus(BillReceivableEntity entity);

    /*
   *@author 099995-foss-hemingyu
    * @date 2016-01-14 上午15:47:38
    * @param wayBillNo
    *            运单号
    * @param billType
    *            应收类型
    */
    BillReceivableEntity selectByWayBillNoAndBillType(String wayBillNo,String billType);

	/**
	 * @author 尤坤
	 * 根据应收号单更新更新对账单单号
	 * @param receivableNoList
	 * @return
	 */
	void updateByReceivableNo(Map<String,Object> map);

	/**
	 * @author ddw
	 * 根据对账单单号查询应收单
	 * @param statementBillNoList
	 * @return
	 */
	List<BillReceivableEntity> queryReceivableByStatementBillNo(List<String> statementBillNoList);
	
	/**
	 * @author ddw
	 * 根据应收单号查询应收单
	 * @param statementBillNoList
	 * @return
	 */
	BillReceivableEntity queryBillReceivableByReceivableNO(String receivableNo, String active);
	
	 /**
     * @author zlp 根据对账单单号查询应收单
     * @param statementBillNoList
     * @return
     */
    List<BillReceivableEntity> queryReceivableBySBNO(List<String> statementBillNoList);

	/**
	 * 根据对账单号查询应收单
	 * @param statementBillNo
	 * @return
	 */
	List<BillReceivableEntity> queryReceivableByStatementBillNoAuto(String statementBillNo);

	/**
	 * 根据查询条件（来源编号、客户编码、金额、单据子类型、有效）查询是否存在重复的应收单
	 * @author gpz
	 * @date 2016年11月21日
	 * @param queryEntity 查询条件
	 * @return List<BillReceivableEntity>
	 */
	List<BillReceivableEntity> checkReceivableBillRepeated(
			BillReceivableEntity queryEntity);

	/**
	 * 根据传入运单号查询其应收单已核销金额（如有多个应收单就返回其合）
	 * @author 379106
	 * @param waybills
	 * @return
     */
	Map<String,BigDecimal> queryReceivableVeryfyAmountsByWaybill(List<String> waybills);
}
