package com.deppon.foss.module.settlement.common.api.server.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffAmountDto;

/**
 * 应付单公共DAO接口
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-18 上午9:26:53
 */
public interface IBillPayableEntityDao {

	// -------------------- write methods --------------------
	
    /**
     * 保存应付单
     * @author ibm-zhuwei
     * @date 2012-11-8 下午7:30:23
     * @param entity 应付单
     * @return
     */
    int add(BillPayableEntity entity);

    /**
     * 红冲应付单，原记录置为失效状态
     * @author ibm-zhuwei
     * @date 2012-10-23 下午3:38:43
     * @param entity 应付单
     * @return
     */
    int updateByWriteBack(BillPayableEntity entity);

    /**
     * 应付单核销
     * @author ibm-zhuwei
     * @date 2012-10-19 上午11:03:14
     * @param dto 核销DTO
     * @return
     */
    int updateByWriteoff(BillWriteoffAmountDto dto);

    /**
     * 应付单生效/失效
     * @author ibm-zhuwei
     * @date 2012-10-22 下午2:50:06
     * @param entity 应付单
     * @return
     */
    int updateByTakeEffect(BillPayableEntity entity);
    
    /**
     * 批量审核/反审核应付单
     * @author 099995-foss-wujiangtao
     * @date 2012-11-1 上午8:14:08
     * @param dto 应付单DTO
     * @return
     * @see
     */
    int updateByBatchAudit(BillPayableDto dto);
    
    /**
     * 冻结应付单
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-2 下午5:59:41
     * @param entity 应付单
     * @return
     * @see
     */
    int updateByFrozen(BillPayableEntity entity);
    
    /**
     * 批量冻结应付单
	 *
     * @author foss-guxinhua
     * @date 2013-5-3 下午5:45:26
     * @param entity
     * @return
     */
    int updateByFrozenByBatch(BillPayableDto dto);

    /**
     * 取消冻结应付单
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-2 下午5:59:41
     * @param entity 应付单
     * @return
     * @see
     */
    int updateByCancelFrozen(BillPayableEntity entity);
    
    /**
	 * 批量取消冻结应付单
	 * @author foss-guxinhua
	 * @date 2013-5-10 上午9:35:24
	 * @param dto
	 * @return
	 */
	int updateByCancelFrozenByBatch(BillPayableDto dto);
    
    /**
     * 修改应付单的支付状态、付款单号、付款金额等信息
     * @author 099995-foss-wujiangtao
     * @date 2012-11-6 下午1:57:31
     * @param dto 应付单DTO
     * @return
     */
    int updateByBatchPay(BillPayableDto dto);
    
    /**
     * 签收时修改代收货款应付单的签收日期--针对不能满足生效代收货款应付单时，调用此接口
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-15 下午5:12:55
     * @param entity 应付单
     * @return
     */
    int updateBySignDate(BillPayableEntity entity);
    
    /**
     * 批量生效应付单
     * 
     * @author 099995-foss-wujiangtao
     * @date 2013-1-17 上午9:14:01
     * @param dto
     * @return
     */
    int updateByBatchTakeEffect(BillPayableDto dto);
    
    
    // -------------------- read methods --------------------
    
    /**
     * 
     * 根据传入的一到多个应付单号，获取一到多条应付单信息
     * @author dp-wujiangtao
     * @date 2012-10-12 下午6:54:35
     * @param payableNos     应付单号集合
     * @param sourceBillNos  来源单号集合
     * @return
     * @see
     */
    List<BillPayableEntity> queryByPayableNOs(List<String> payableNos,String active);
    
    /**
     * 
     * 根据传入的一到多个来源单号，获取一到多条应付单信息
     * @author dp-wujiangtao
     * @date 2012-10-15 下午6:11:45
     * @param sourceBillNos  来源单号集合
     * @param active         是否有效
     * @param sourceBillType 来源单据类型
     * @return
     * @see
     */
    List<BillPayableEntity> queryBySourceBillNOs(List<String> sourceBillNos,String sourceBillType,String active);
    
    /**
     * 
     * 根据运单号和应付单类型集合，获取一到多条应付单信息
     * @author dp-wujiangtao
     * @date 2012-10-19 上午10:20:58
     * @param dto 应付单查询条件DTO
     * @return
     */
    List<BillPayableEntity> queryBillPayableByCondition(BillPayableConditionDto dto);
    
    /**
     * 根据运单号和外发单号、客户编码、判断是否已存在有效的偏线成本应付单
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-11-19 下午3:56:12
     * @param dto 应付单查询条件DTO
     * @return
     */
    int queryBillPayableByExternalBillNo(BillPayableConditionDto dto);
    

	/**
	 * 
	 * 根据传入的一到多个应付单号，传入的部门范围，获取一到多条应付单信息
	 * 
	 * @author dp-maojianqiang
	 * @date 2012-11-22 上午11:58:36
	 * @param payableNos    应付单号集合
	 * @param deptCodeList  应付部门集合
	 * @param active        是否有效
	 * @param currentInfo
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
	 * @param sourceBillNos  来源单号集合
	 * @param orgCodes   部门编码集合
	 * @param sourceBillType 来源单据类型
	 * @param active         是否有效
	 * @return
	 */
	List<BillPayableEntity> queryBySourceBillNosAndOrgCodes(List<String> sourceBillNos,List<String> orgCodes,
			String sourceBillType, String active,CurrentInfo currentInfo,String isPartner);
	
	/**
	 * 根据运单号集合和单据类型集合查询应付单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-22 下午5:09:35
	 * @param waybillNos 运单号集合
	 * @param billTypes  单据类型集合
	 * @param active     是否有效
	 * @return
	 */
	List<BillPayableEntity> queryByWaybillNosAndByBillTypes(List<String> waybillNos,List<String> billTypes,String active);
	
	/**
	 * 根据运单号集合和单据类型集合以及来源单号查询应付单信息
     * @author 367638-foss-caodajun
	 * @date 2016-12-13 下午3:59:32
     * @param waybillNos 运单号集合
     * @param billTypes  单据类型集合
     * @param sourceBillNo 来源单号集合
     * @param active     是否有效
     * @return	
	 */
	List<BillPayableEntity> queryBysourceBillNoAndByBillTypes(List<String> waybillNos, List<String> billTypes, List<String>sourceBillNo,String active) ;
	
	/**
	 * 根据运单号集合和单据类型集合以及到达部门编码集合查询应付单信息
	 * @author 367638-foss-caodajun
	 * @date 2012-11-22 下午5:09:35
	 * @param waybillNos 运单号集合
	 * @param billTypes  单据类型集合
	 * @param active     是否有效
	 * @param destOrgCode 到达部门编码集合
	 * @return
	 */
	List<BillPayableEntity> queryByWaybillNosAndByBillDTypes(List<String> waybillNos,List<String> billTypes,String active,List<String> destOrgCode);
	
	/**
	 * 根据来源单号查询应付单是否已经核销
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-28 下午6:28:04
	 * @param sourceBillNo    来源单号 
	 * @param sourceBillType  来源单据类型
	 * @param active          是否有效
	 * @return
	 */
	List<BillPayableEntity> queryBillPayableIsWriteOff(String sourceBillNo,String sourceBillType,String active);

	/**
	 * 生效应付单(装卸费)Dao接口--生效应付单
	 * @author foss-zhangxiaohui
	 * @date Nov 20, 2012 9:54:39 AM
	 * @param billPayableEntityList 应付单集合
	 * @return
	 */
	int updatePayableBillLaborFee(List<BillPayableEntity> billPayableEntityList);
	
	/**
	 * 批量修改应付单的对账单单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午6:26:40
	 * @param dto 应付单DTO
	 * @return
	 */
	int updateBatchByMakeStatement(BillPayableDto dto);
	
	/**
	 * 按照运单号和应付部门编码集合查询应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-28 下午3:10:35
	 * @param waybillNos
	 * @param orgCodeList
	 * @param active
	 * @return
	 */
	List<BillPayableEntity> queryByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList, String active,CurrentInfo currentInfo,String isPartner);
	
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
	  * 根据运单号集合和单据类型，查询已签收且未生效（且运输性质不为空运的）应付单信息
	  * 
	  * @author 099995-foss-wujiangtao
	  * @date 2013-1-18 上午9:39:39
	  * @param waybillNos
	  * @param billType
	  * @param effectiveStatus 是否生效
	  * @param productCode 产品类型
	  * @return
	  */
	 List<BillPayableEntity> queryByWaybillNosAndBillType(List<String> waybillNos,String billType,String effectiveStatus,
			String productCode);
	 
	 /**
	  * 根据运单号集合和单据类型，查询已签收且未生效（且运输性质不为空运的）应付单信息
	  * 
	  * @author 218392
	  * @date 2015-12-31 18 :30:00
	  * @param waybillNos
	  * @param billType
	  * @param effectiveStatus 是否生效
	  * @param productCode 产品类型
	  * @return
	  */
	 List<BillPayableEntity> queryByWaybillNosByBillType(List<String> waybillNos,String billType,String effectiveStatus,
			String productCode);
	 
	 /**
	  * 根据来源单号集合和客户（代理）编码，查询应付单信息
	  * 
	  * @author 099995-foss-wujiangtao
	  * @date 2013-1-21 下午2:55:50
	  * @param sourceBillNos
	  * @param customerCode
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
	List<BillPayableEntity> selectBySourceBillNosTypes(List<String> sourceBillNos, List<String> sourceBillTypes,String active);

	/**
	 * 
	 * 功能：给临时租车使用，更新应付单上的工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-24
	 * @return
	 */
	public int updateWorkFlowNoByPayNo(List<BillPayableEntity> list,String workFlowNo,CurrentInfo cInfo);
	
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
    public int updatePayableVersion(List<BillPayableEntity> list, CurrentInfo cInfo);

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
     */
    public int updateBillPayableEffective(BillPayableEntity entity);

    /**
     * 根据运单号集合查询应付单信息
     * @author foss-hemingyu
     * @date 2012-11-22 下午5:09:35
     * @param  String waybillNos 运单号集合
     * @return List<BillPayableEntity>
     */
    public List<BillPayableEntity> queryByWaybillNo(String waybillNos);
   
    /**
     * @author 331556 fanjingwei
     * @date 2016-05-28
     * @param  String waybillNos 运单号集合
     * @return List<BillPayableEntity>
     */
    public List<BillPayableEntity> queryByWaybillNos(String waybillNos);
    
    /**
     * 长期未支付有效应付单自动限制付款
     * @author 340778-foss-zf
     * @date 2016-7-22 下午2:59:35
     * @description
     */
    public int updateBillPayableAutoLimit();
    
    /**
     * 长期未支付有效应付单自动限制付款解除
     * @author 340778-foss-zf
     * @date 2016-7-22 下午3:02:01
     * @description
     * @param payableNo 应付单号
     * @param currentInfo 当前用户信息
     */
    public int updateBillPayableAutoLimitRestore(Map<String, Object> map);
    
    /**
     * 根据运单号查询第一条代收退款应付单金额
     * @author 326181
     * @param waybillNo 运单号
     * @return amount
     */
    BigDecimal queryFirstPayableAmountByWaybillNo(String waybillNo);

	/**
	 * 根据来源单号，客户编码，单据子类型，金额 校验合伙人奖罚应付单是否重复
	 * @author gpz
	 * @date 2016年11月25日
	 * @param queryEntity 查询条件
	 * @return List<BillPayableEntity>
	 */
	List<BillPayableEntity> checkPayableBillRepeated(
			BillPayableEntity queryEntity);
 
}
