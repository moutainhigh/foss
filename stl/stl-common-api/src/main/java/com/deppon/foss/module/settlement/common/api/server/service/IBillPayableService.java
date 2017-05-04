package com.deppon.foss.module.settlement.common.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 应付单服务
 * @author ibm-zhuwei
 * @date 2012-12-25 下午4:04:12
 */
public interface IBillPayableService extends IService {

	// -------------------- write methods --------------------
	
	/**
	 * 新增应付单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午4:38:10
	 * @param entity
	 * @param currentInfo
	 */
	void addBillPayable(BillPayableEntity entity, CurrentInfo currentInfo);

	/**
	 * 生效应付单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-22 下午2:15:20
	 * @param entity
	 * @param signDate
	 * @param currentInfo
	 */
	void enableBillPayable(BillPayableEntity entity, Date signDate, CurrentInfo currentInfo);
	
	/**
	 * 失效应付单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-22 下午2:15:20
	 * @param entity
	 * @param currentInfo
	 */
	void disableBillPayable(BillPayableEntity entity, CurrentInfo currentInfo);

	/**
	 * 红冲应付单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-23 下午3:21:50
	 * @param entity
	 * @param currentInfo
	 */
	void writeBackBillPayable(BillPayableEntity entity, CurrentInfo currentInfo);

	/**
	 * 应付单核销/反核销
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-19 上午11:13:37
	 * @param entity
	 * @param writeoffAmount
	 * @param currentInfo
	 */
	void writeoffBillPayable(BillPayableEntity entity, BigDecimal writeoffAmount, CurrentInfo currentInfo);

	/**
	 * 批量审核应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-1 上午8:34:49
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void batchAuditBillPayable(BillPayableDto dto, CurrentInfo currentInfo);

	/**
	 * 批量反审核应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-1 上午8:35:35
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void batchReverseAuditBillPayable(BillPayableDto dto, CurrentInfo currentInfo);

	/**
	 * 冻结应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 下午6:00:40
	 * @param entity
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void frozenBillPayable(BillPayableEntity entity, CurrentInfo currentInfo);
	
	/**
	 * 批量冻结应付单
	 *
	 * @author foss-guxinhua
	 * @date 2013-5-3 下午5:43:50
	 * @param entity
	 * @param currentInfo
	 */
	void frozenBillPayableByBatch(BillPayableDto dto, CurrentInfo currentInfo);

	/**
	 * 取消冻结应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 下午6:01:58
	 * @param entity
	 * @param currentInfo
	 * @see
	 */
	void cancelFrozenBillPayable(BillPayableEntity entity, CurrentInfo currentInfo);
	
	/**
	 * 批量取消冻结应付单
	 * 和设置应付单的状态为：未支付
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 下午6:04:51
	 * @param dto
	 * @param currentInfo
	 */
	void cancelFrozenBillPayableByBatch(BillPayableDto dto, CurrentInfo currentInfo);

	/**
	 * 修改应付单的支付状态、付款单号、付款金额等信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午2:04:19
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void payForBillPayable(BillPayableDto dto, CurrentInfo currentInfo);

	/**
	 * 取消应付单的支付状态和付款单号、付款金额等信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午2:12:35
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void cancelPayForBillPayable(BillPayableDto dto, CurrentInfo currentInfo);
	
	
	/**
	 * 签收时，不能生效应付单的情况下，修改代收货款应付单的签收日期
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-15 下午5:18:44
	 * @param entity
	 * @param currentInfo
	 */
	void updateBillPayableSignDateByConfirmIncome(BillPayableEntity entity, CurrentInfo currentInfo);
	
	/**
	 * 反签收时，不能失效代收货款应付单的情况下，设置代收货款应付单的签收日期为空
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-15 下午5:20:14
	 * @param entity
	 * @param currentInfo
	 */
	void updateBillPayableSignDateByReverseConfirmIncome(BillPayableEntity entity, CurrentInfo currentInfo);
	
	/**
	 * 批量设置/取消应付单的对账单单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午6:29:32
	 * @param dto
	 * @param currentInfo
	 */
	void batchUpdateByMakeStatement(BillPayableDto dto, CurrentInfo currentInfo);
	
	/**
	 * 批量生效应付单信息
	 * （代收货款应付单）
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午9:16:59
	 * @param dto
	 */
	void batchUpdateByTakeEffect(BillPayableDto dto, CurrentInfo currentInfo);

	// -------------------- valid methods --------------------
	
	/**
	 * 验证同一个运单号相同类型的应付单是否存在多条记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-1 下午7:30:53
	 * @param wayBillNo
	 * @param list
	 * @see
	 */
	void validateWaybillForBillPayable(List<BillPayableEntity> list);
	
	// -------------------- read methods --------------------
	
	/**
	 * 
	 * 根据传入的一到多个应付单号，获取一到多条应付单信息
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-12 上午11:58:36
	 * @param payableNos
	 *            应付单号集合
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	List<BillPayableEntity> queryByPayableNOs(List<String> payableNos,
			String active);

	/**
	 * 根据传入的一个应付单号，获取一条应付单信息
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-12 上午11:58:36
	 * @param payableNo
	 *            应付单号
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	BillPayableEntity queryByPayableNO(String payableNo, String active);

	/**
	 * 根据传入的一到多个来源单号，获取一到多条应付单信息
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-12 上午11:58:36
	 * @param sourceBillNos
	 *            来源单号集合
	 * @param sourceBillType
	 *            来源单据类型
	 * @param active
	 *            是否有效
	 */
	List<BillPayableEntity> queryBySourceBillNOs(List<String> sourceBillNos,
			String sourceBillType, String active);

	/**
	 * 根据运单号和应付单类型等参数，查询有效应付单信息
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-19 上午10:47:50
	 * @param dto
	 */
	List<BillPayableEntity> queryBillPayableByCondition(
			BillPayableConditionDto dto);

	/**
	 * 根据运单号和外发单号、客户编码、判断是否已存在有效的偏线成本应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 下午4:00:17
	 * @param dto
	 * @return
	 */
	int queryBillPayableByExternalBillNo(BillPayableConditionDto dto);

	/**
	 * 
	 * 根据传入的一到多个应付单号，传入的部门范围，获取一到多条应付单信息
	 * 
	 * @author dp-maojianqiang
	 * @date 2012-11-22 上午11:58:36
	 * @param payableNos
	 *            应付单号集合
	 * @param deptCodeList  应付部门集合
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	List<BillPayableEntity> queryByPayableNosAndDeptCodes(List<String> payableNos,List<String> deptCodeList,
			String active,CurrentInfo currentInfo,String isPartner);
	
	/**
	 * 根据传入的一到多个来源单号，传入的部门范围,获取一到多条应付单信息
	 * 
	 * @author dp-maojianqiang
	 * @date 2012-11-22 上午11:58:36
	 * @param sourceBillNos
	 *            来源单号集合
	 *  @param orgCodes          
	 *            部门编码集合
	 * @param sourceBillType
	 *            来源单据类型
	 * @param active
	 *            是否有效
	 */
	List<BillPayableEntity> queryBySourceBillNosAndOrgCodes(List<String> sourceBillNos,List<String> orgCodes,
			String sourceBillType, String active,CurrentInfo currentInfo,String isPartner);
	
	/**
	 * 根据运单号集合和单据类型集合查询应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-22 下午5:11:12
	 * @param waybillNos
	 * @param billTypes
	 * @return
	 */
	List<BillPayableEntity> queryByWaybillNosAndByBillTypes(List<String> waybillNos,List<String> billTypes);
	
	/**
	 * 根据运单号集合和单据类型集合以及来源单号查询应付单信息
     * @author 367638-foss-caodajun
	 * @date 2016-12-13 下午3:49:32
     * @param waybillNos 运单号集合
     * @param billTypes  单据类型集合
     * @param sourceBillNo 来源单号集合
     * @return	
	 */
	List<BillPayableEntity> queryBysourceBillNoAndByBillTypes(List<String> waybillNos, List<String> billTypes,List<String>sourceBillNo) ;
		
	/**
	 * 根据运单号集合和单据类型集合以及到达部门集合查询应付单信息
	 * 
	 * @author 367638-foss-caodajun
	 * @date 2016-10-24 下午5:11:12
	 * @param waybillNos
	 * @param billTypes
	 * @param destOrgCode
	 * @return
	 */
	List<BillPayableEntity> queryDByWaybillNosAndByBillTypes(List<String> waybillNos,List<String> billTypes,List<String> destOrgCode);
	
	
	/**
	 * 根据来源单号查询应付单是否已经核销
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-28 下午6:29:07
	 * @param sourceBillNo   来源单号
	 * @param isAdjust   是否是调整外请车费用调用查询
	 * @return
	 */
	boolean queryBillPayableIsWriteOff(String sourceBillNo,String isAdjust);
	
	/**
	 * 生效应付单(装卸费)Service接口--生效应付单Batch
	 * @author foss-zhangxiaohui
	 * @date Nov 20, 2012 9:46:57 AM
	 */
	void updatePayableBillLaborFeeStatus(List<BillPayableEntity> billPayableEntityList) throws SettlementException;
	
	/**
	 * 按照运单号和应付部门编码集合查询应付单信息
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-28 下午3:22:29
	 * @param waybillNos
	 * @param orgCodeList
	 * @param active
	 * @param currentInfo
	 * @return
	 */
	List<BillPayableEntity> queryByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList,String active,CurrentInfo currentInfo,String isPartner);
	
	/**
	 * 根据付款单号集合查询应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午3:25:06
	 * @param paymentNos
	 * @param active
	 * @param isRedBack
	 * @return
	 */
	List<BillPayableEntity> queryByPaymentNos(List<String> paymentNos,String active,String isRedBack);
	
	/**
	 *  根据运单号集合和单据类型，查询已签收且未生效（且运输性质不为空运的）应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午9:40:40
	 * @param waybillNos
	 * @param billType
	 * @return
	 */
	List<BillPayableEntity> queryByWaybillNosAndBillType(List<String> waybillNos,String billType);
	
	
	/**
	 *  根据运单号集合和单据类型，查询已签收且未生效（且运输性质不为空运的）应付单信息
	 * 
	 * @author 218392
	 * @date 2015-12-31 18:28:00
	 * @param waybillNos
	 * @param billType
	 * @return
	 */
	List<BillPayableEntity> queryByWaybillNosByBillType(List<String> waybillNos,String billType);
	
	
	/**
	 * 根据来源单号集合和客户（代理）编码，查询应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 下午2:49:38
	 * @param sourceBillNos
	 * @param sourceBillType
	 * @param active
	 * @return
	 */
	List<BillPayableEntity> queryBySourceBillNOsAndCustomerCode(
			List<String> sourceBillNos,String customerCode, String sourceBillType, String active);

	 
	 /**
	  * 根据运单号、客户编码、单据类型批量查询应付单  供空运变更清单服务调用
	  * @author 045738-foss-maojianqiang
	  * @date 2013-4-12 上午9:39:56
	  * @param list
	  * @param active
	  * @return
	  */
	 List<BillPayableEntity> selectBySourceBillNOsAndCustomerCodeAndBillType(List<BillPayableEntity> list,String active);

	 /**
	  * 根据来源单号，来源单据类型查询对应的应付单 供空运中转提货调用
	  * 	查询中转提货清单运单对应的送货费应付单集合
	  * @author 092036-foss-bochenlong
	  * @date 2013-8-20 下午14:50:55
	  * @param sourceBillNos 来源单号集合 
	  * @param sourceBillTypes 来源单据类型集合
	  * @param active 是否有效
	  * @return List<BillPayableEntity>
	  */
	 List<BillPayableEntity> selectBySourceBillNosTypes(List<String> sourceBillNos,List<String> sourceBillTypes,String active);
	 
	/**
	 * 
	 * 功能：给临时租车使用，更新应付单上的工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-24
	 * @return
	 */
	public void updateWorkFlowNoByPayNo(List<BillPayableEntity> list,String workFlowNo,CurrentInfo cInfo);
	
	/**
	 * 功能：给临时租车使用，查询应付单数据，顺带关联查询出临时租车预提数据
	 * @author 045738-foss-maojianqiang
	 * @date 2014-8-05
	 * @return
	 */
    public List<BillPayableEntity> queryByPayableNOsForRentCar(List<String> payableNos);
    
    /**
	 * 功能：给临时租车使用，根据付款单号查询
	 * @author 045738-foss-maojianqiang
	 * @date 2014-8-09
	 * @return
	 */
    public List<BillPayableEntity> queryByPaymentNosForRentCar(List<String> payableNos);
   
    /**
	 * 功能：当更新报账预提接口不成功时，版本号-1
	 * @author 045738-foss-maojianqiang
	 * @date 2014-11-4
	 * @return
	*/
    public void updatePayableVersion(List<BillPayableEntity> list, CurrentInfo cInfo);

	String queryDiscountPayable(String waybillNo);
	
	/**
	 *  根据付款单号查询应付单
	 * @author foss-269044
	 * @date 2015-10-26
	 * @param List<BillPayableEntity> payableList
	 * @return List<BillPayableEntity> payableList
	 */
	List<BillPayableEntity> queryPaymentListByPaymentNo(List<BillPayableEntity> payableList);

    /**
     * 生成生效时间使应付单生效
     *
     *@author foss-hemingyu
     * @date 2016-01-21 下午18:33:33
     * @param entity
     *            应收单实体
     *@param currentInfo
     *            操作用户
     */
    public void updateBillPayableEffective(BillPayableEntity entity, CurrentInfo currentInfo);

    /**
     * 根据运单号集合查询应付单信息
     *@author foss-hemingyu
     * @date 2016-01-28 下午18:33:33
     * @param waybillNo 运单号集合
     * @return List<BillPayableEntity>
     */
    public List<BillPayableEntity> selectByWaybillNo(String waybillNo);
    
    /**
     * 根据运单号集合查询应付单信息
     *@author 331556 fanjingwei
     * @date 2016-05-28 
     * @param waybillNo 运单号集合
     * @return List<BillPayableEntity>
     */
    public List<BillPayableEntity> selectByWaybillNos(String waybillNo);
    
    /**
     * 长期未支付有效应付单自动限制付款
     * @author 340778-foss-zf
     * @date 2016-7-22 下午2:59:35
     * @description
     */
    public void updateBillPayableAutoLimit();
    
    /**
     * 长期未支付有效应付单自动限制付款解除
     * @author 340778-foss-zf
     * @date 2016-7-22 下午3:02:01
     * @description
     * @param payableNo 应付单号
     * @param currentInfo 当前用户信息
     */
    public void updateBillPayableAutoLimitRestore(List<String> payableNos,CurrentInfo currentInfo);
    /**
     * 根据运单号查询第一条代收退款应付单金额
     * @author 326181
     * @param waybillNo 运单号
     * @return amount
     */
    BigDecimal selectFirstPayableAmountByWaybillNo(String waybillNo);
   
    
    
    /**
     * 更新尾款应付单的生效时间及状态
     * @author 347251 
     * @param entity 
     * @param signDate 生效时间
     * @param EffectiveStatus 状态
     * @param currentInfo
     */
    void updateBillPayableEffectiveStatus(BillPayableEntity entity, Date signDate,String effectiveStatus,CurrentInfo currentInfo);

	/**
	 * 根据条件查询合伙人奖罚应付单是否重复
	 * @author gpz
	 * @date 2016年11月25日
	 * @param queryEntity 查询条件
	 */
	List<BillPayableEntity> checkPayableBillRepeated(
			BillPayableEntity queryEntity);
    
}
