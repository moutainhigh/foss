package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffAmountDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WaybillReceivableDto;

/**
 * 应收单DAO
 * @author ibm-zhuwei
 * @date 2012-10-19 上午11:03:46
 */
public interface IBillReceivableEntityDao {

	/**
	 * 保存应收单记录
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-10 下午3:26:32
	 * @param entity 应收单
	 * @return
	 */
    int add(BillReceivableEntity entity);

    /**
     * 根据ID与分区键查询应收单
     * @author ibm-zhuwei
     * @date 2012-11-29 上午11:34:42
     * @param dto 应收单DTO
     * @return
     */
    List<BillReceivableEntity> queryPartitionsByIds(BillReceivableDto dto);

    /**
     * 根据应收单号与分区键查询应收单
     * @author ibm-zhuwei
     * @date 2012-11-29 上午11:34:42
     * @param dto 应收单DTO
     * @return
     */
    List<BillReceivableEntity> queryPartitionsByReceivableNos(BillReceivableDto dto);
    
    /**
     * 
     * 根据传入的一到多个应收单号，获取一到多条应收单信息
     * @author 099995-foss-wujiangtao
     * @date 2012-10-12 上午11:07:58
     * @param receivableNos  应收单号集合
     * @param active         是否有效
     * @return
     * @see
     */
    List<BillReceivableEntity> queryByReceivableNOs(List<String> receivableNos, String active);
    
    /**
     * 
     * 根据传入的一到多个来源单号，获取一到多条应收单信息
     * @author 099995-foss-wujiangtao
     * @date 2012-10-15 上午11:52:05
     * @param sourceBillNos  来源单号集合
     * @param sourceBillType 来源单据类型
     * @param active         是否有效
     * @return
     * @see
     */
    List<BillReceivableEntity> queryBySourceBillNOs(List<String> sourceBillNos,String sourceBillType, String active);
    
    /**
     * 
     * 根据传入的运单号和单据类型等参数，查询[到付运费/始发/代收货款]有效应收单信息
     * @author 099995-foss-wujiangtao
     * @date 2012-10-15 下午7:17:05
	 * @param dto 应收单条件DTO
     * @return
     * @see
     */
    List<BillReceivableEntity> queryBillReceivableByCondition(BillReceivableConditionDto dto);
    
    /**
	 * 查询始发月结
	 * ECS-327090
	 * @date 2016-5-24 
	 * 
	 */
    List<BillReceivableEntity> queryBillReceivableMonthlyStatement(BillReceivableConditionDto dto);
    
    /**
     * 
     * 根据传入应收单红冲原应收单信息
     * @author 099995-foss-wujiangtao
     * @date 2012-10-15 下午7:33:16
     * @param entity   应收单实体信息
     * @return
     * @see
     */
    int updateBillReceivableByWriteBack(BillReceivableEntity entity);
    
    /**
     * 应收单核销
     * @author ibm-zhuwei
     * @date 2012-10-19 上午11:03:14
     * @param dto 核销DTO
     * @return
     */
    int updateBillReceivableByWriteoff(BillWriteoffAmountDto dto);
    
    /**
     * (反确认)签收时 确认收入日期
     * @author 099995-foss-wujiangtao
     * @date 2012-10-22 上午9:50:33
     * @param entity 应收单
     * @return
     * @see
     */
    int updateBillReceivableByConfirmIncome(BillReceivableEntity entity);
    
    /**
     * 批量审核/反审核空运其他应收单
     * @author 099995-foss-wujiangtao
     * @date 2012-10-30 下午7:18:51
     * @param dto 应收单DTO
     * @return
     * @see
     */
    int updateBillReceivableByAirAudit(BillReceivableDto dto);
    
    /**
     * 根据运单号查询客户的应收单到付金额和应收代收货款金额
     * @author 099995-foss-wujiangtao
     * @date 2012-11-2 上午8:13:55
     * @param dto 应收单条件DTO
     * @see
     */
    List<BillReceivableEntity> queryReceivableAmountByCondition(BillReceivableConditionDto dto);
    
    /**
     * 锁定/解锁应收单
     * @author 099995-foss-wujiangtao
     * @date 2012-11-7 上午10:18:19
     * @param dto 应收单DTO
     * 【
     *   billReceivables:应收单实体集合； 
     * 】
     * @return
     * @see
     */
    int updateBillReceivableByLock(BillReceivableDto dto);
    
    /**
     * 根据运单号和外发单号、客户编码、判断是否已存在有效的偏线到达应收单
     *  
     * @author 099995-foss-wujiangtao
     * @date 2012-11-19 下午3:10:32
     * @param dto 应收单条件DTO
     * @return
     */
    int queryReceivableByExternalBillNo(BillReceivableConditionDto dto);
    
    /**
     * 批量修改应收单的对账单号
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-12-4 下午6:17:54
     * @param dto 应收单DTO
     * 【对账单号，是否有效active
     * 应收单实体集合
     * 】
     * @return
     */
    int updateBatchByMakeStatement(BillReceivableDto dto);
    
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
     * @date 2013-1-15 下午5:25:15
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
     * 根据客户编码，获取客户的应收未核销金额 
     * @author 099995-foss-wujiangtao
     * @date 2013-1-16 下午8:28:59
     * @param customerCode
     * @return
     */
    List<BillReceivableEntity> queryReceivableAmountByCustomerCode(String customerCode);
    
    /**
     * 根据来源单号集合和应收部门编码集合，查询应收单信息
     * @author 099995-foss-wujiangtao
     * @date 2013-1-22 下午4:34:03
     * @param sourceBillNos
     * @param orgCodes
     * @param sourceBillType
     * @param active
     * @param currentInfo
     * @return
     */
    List<BillReceivableEntity> queryBySourceBillNOsAndOrgCodes(List<String> sourceBillNos,List<String> orgCodes,String sourceBillType, String active,CurrentInfo currentInfo);

    /**
     * 
     * 根据应收单号集合批量标记和反标记状态
     * @author 045738-foss-maojianqiang
     * @date 2013-5-21 下午6:21:21
     * @param numbers
     * @param active
     * @return
     */
    int updateStampByNumbers(BillReceivableDto dto);
    
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
 	 * 锁定应收单信息
 	 * @author 088933-foss-zhangjiheng
 	 * @date 2012-11-23 上午9:05:22
 	 */
 	int updateReceiveBillInfoForLock(BillReceivableOnlineQueryDto queryDto);
 	
 	/**
 	 * 根据条件查询最小时间
 	 * @param 073615 lianghaisheng
 	 * @return date 最小时间
 	 */
 	Date queryMinTimebyCondition(BillReceivableConditionDto dto);

	/**
	 * 根据传入的一到多个运单单号，获取一到多条应收单信息
     * @author 邓大伟
     * @date 2014-12-12
     * @param wayBillNos  运单单号集合
	 */
	List<BillReceivableEntity> queryByWaybillNOs(List<String> waybillNos);

	/**
	 * 是否开单付款方式为到付(其他)且已在网上支付(其他)的运单
	 * @author 239284-foss-xiedejie
	 * @date 2015-4-2 上午9:30:00
	 * @param   billTypes 单据类型集合 <br/>
	 *                  wayBillNo 运单号  <br/>
	 *                  rePaymentNo 还款单号  <br/>
	 *                  sourceNo   来源单号  <br/>
	 *                  actualPayType  实际付款方式  
	 */
	int queryIsOrPayByBillNo(String[] billTypes, String wayBillNo, String rePaymentNo, String sourceNo,  String actualPayType);

    /**
     * 根据id与应收单号修改应收单扣款状态:扣款失败,扣款成功
     *@author 099995-foss-hemingyu
     * @date 2016-01-08 上午11:23:05
     * @param entity 应收单
     * @return
     * @see
     */
    int updateBillReceivableWithholdStatus(BillReceivableEntity entity);

    /*
    *@author 099995-foss-hemingyu
     * @date 2016-01-14 上午15:47:38
     * @param wayBillNo
     *            运单号
     * @param billType
     *            应收类型
     */
    List<BillReceivableEntity> selectByWayBillNoAndBillType(String wayBillNo,String billType);

    /**
     * @author 尤坤
     * 根据应收号单更新更新对账单单号
     * @param map
     * @return
     */
    int updateByReceivableNo(Map<String,Object> map);

	/**
	 * @author ddw
	 * 根据对账单单号查询应收单
	 * @param statementBillNoList
	 * @return
	 */
	List<BillReceivableEntity> queryReceivableByStatementBillNo(List<String> statementBillNoList);
	/**
     * @author zlp 根据对账单单号查询应收单
     * @param statementBillNoList
     * @return
     */
    List<BillReceivableEntity> queryReceivableBySBNO(List<String> statementBillNoList);
	/**
	 * @author ddw
	 * 根据应收单号查询应收单
	 * @param statementBillNoList
	 * @return
	 */
	BillReceivableEntity queryBillReceivableByReceivableNO(String receivableNo,	String active);
	
	/**
	 * 根据对账单号查询应收单
	 * @param statementBillNo
	 * @return
	 */
	List<BillReceivableEntity> queryReceivableByStatementBillNoAuto(String statementBillNo);

	/**
	 * 根据查询条件校验合伙人应收单是否重复
	 * @author gpz
	 * @date 2016年11月21日
	 * @param queryEntity 查询条件
	 * @return  List<BillReceivableEntity>
	 */
	List<BillReceivableEntity> checkReceivableBillRepeated(
			BillReceivableEntity queryEntity);


	/**
	 * 根据运单号查询应收单已核销金额之和
	 * @author  379106
	 * @param waybills
	 * @return
     */
	List<BillReceivableEntity> queryReceivableVeryfyAmountsByWaybill(List<String> waybills);

}
