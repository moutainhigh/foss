package com.deppon.foss.module.settlement.dubbo.api.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.dubbo.uip.api.define.ReceivableEntity;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.ActualFreightEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.dubbo.api.define.LabeledGoodEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillChargeDtlEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillDisDtlEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillDto;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillExpressEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillPaymentEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WoodenRequirementsEntity;

public interface IWaybillRfcService4dubbo extends IService {
	/**
	 * 传入运单号或者运单号数组集合，判断传入的运单号是否存在未处理的更改单
	 * @author 327090
	 * @param waybillNo
	 * @return
	 */
	List<String> isExsitsWayBillRfcs(List<String> waybillNoList) throws WaybillRfcException;
	
	/**
	 * 快递开单到付，限制补码前使用支付宝支付
     * @author 327090
     * @date 2016-12-01
	 */
	public void noPayNotAddCode(List<String> waybillNos);
	
	/**
	 * 
	 * <p>
	 * 通过运单号查询运单信息
	 * </p>
	 * 
	 * @author 327090
	 * @date 2016-12-01 上午10:17:33
	 * @param waybillNo
	 * @return WaybillDto
	 * @seeIWaybillManagerService#queryWaybillByNo(java.lang.String)
	 */
	public WaybillDto queryWaybillByNo(String waybillNo);
	
	/**
     * <p>
     * 根据客户编码查询客户合同中月结客户的最大欠款天数
     * </p>
     * .
     * 
     * @param custCode 客户编码
     * @return CusBargainEntity(int debtDays:月结客户的最大欠款天数)
     * @author 327090
     * @date 2016-12-2 下午2:03:26
     * 
     */
    public CusBargainEntity queryCusBargainByCustCode(String custCode);
    
    /**
     * 根据运单号查询
     * @author 327090
     * @date 2016-12-2 上午11:40:26
     */
    public List<WaybillChargeDtlEntity> queryChargeDtlEntityByNo(String waybillNo);
    
    /**
	 * 根据运单号查询WaybillDisDtlEntity列表
	 * 
	 * @author 327090
	 * @date 2016-12-2 下午4:19:24
	 */
	public List<WaybillDisDtlEntity> queryDisDtlEntityByNo(String waybillNo);
	
	/**
	 * 查询付款信息
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-7
	 * @param waybillNo
	 * @return
	 * @return List<WaybillPaymentEntity>
	 * @see
	 */
	public List<WaybillPaymentEntity> queryWaybillPayment(String waybillNo);
	
	/**
	 * 查询代打木架信息
	 * @author 327090
	 * @date 2016-12-2
	 * @param waybillNo
	 * @return
	 */
	public WoodenRequirementsEntity queryWoodenRequirements(String waybillNo);
	
	/**
	 * 根据运单号查询ActualFreightEntity对象
	 * 
	 * @author 327090
	 * @date 2016-12-2 下午4:42:19
	 */
	public ActualFreightEntity queryActualFreightByNo(String waybillNo);
	
	/**
	 * 判定是否快递
	 * @author 327090
	 * @date 2016-12-3 19:21:26
	 */
	public boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime);
	
	/**
	 * 小件
	 * @author 327090
	 * @param waybillId
	 * @return
	 */
	public WaybillExpressEntity getWabillExpressEntityByWaybillId(String waybillId);
	
	/**
	 * 
	 * <p>
	 * 通过运单号查询所有流水号
	 * </p>
	 * 
	 * @author 327090
	 * @date 2016-12-2 下午4:55:34
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo);
	
	/**
     * 给中转组提供 判断货物是否已签收的接口，参数（运单号、流水号）
     * @author 327090
     * @date 2016-12-2 上午16:12:15
     * @param waybillNo 运单号
     * @param serialNo 流水号
     * @return String
     * @see
     */
   public String querySerialNoIsSign(String waybillNo,String serialNo);
   
   /**
	 * 根据运单号查询运单部分数据（查询付款方式，到付金额，外发代理和币种, 运输性质,是否整车运单,提货方式,总费用,保价声明价值,代收货款,
	 * 货物总件数,最终配载部门，订单编号...)
	 * 
	 * @author 327090
	 * @date 2016-12-1 下午2:50:00
	 */
	public WaybillEntity queryPartWaybillByNo(String waybillNo);
	
	/**
	 * 根据运单号查询运单签结果里的运单信息
	 * @author 327090
	 * @date 2016-2-10 下午5:03:21
	 * @param entity.waybillNo 运单号
	 * @param entity.active 是否有效 
	 * @return
	 * @see
	 */
	WaybillSignResultEntity queryWaybillSignResultByWaybillNo(WaybillSignResultEntity entity);
	
	/**
	 * 根据传入的一到多个运单单号，获取一到多条应收单信息
     * @author 327090
     * @date 2016-12-2
     * @param wayBillNos  运单单号集合
	 */
	public List<ReceivableEntity> queryByWaybillNOs(List<String> waybillNos,List<String> waybillList);
	
	/**
 	 * 锁定应收单信息
 	 * @author 327090
 	 * @date 2016-12-2 上午9:05:22
 	 */
 	public int updateReceiveBillInfoForLock(BillReceivableOnlineQueryDto queryDto);
 	
 	/**
	 * 精确查询
	 * 
	 * 根据“系统配置编码”查询第一个系统参数配置的“配置项值”，适用于无部门编码的系统配置
	 * 
	 * @author 327090
	 * @date 2016-12-2 上午11:11:15
	 * @param code
	 * 系统配置的编码，对应表中的CODE
	 */
	public String queryConfValueByCode(String code);
	
	/**
	 * 精确查询 根据“系统配置编码”查询第一个系统参数配置，适用于无部门编码的系统配置
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-24 上午11:11:15
	 * @param code
	 * 系统配置的编码，对应表中的CODE
	 */
	public ConfigurationParamsEntity queryConfigurationParamsOneByCode(
			String code);
	
	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 327090
	 * @date 2016-12-2上午11:11:15
	 * @see 
	 */
	public List<ConfigurationParamsEntity> queryConfigurationParamsExactByEntity(
			ConfigurationParamsEntity entity, int start, int limit);

	/**
	 * @author 327090
	 * @param entitys
	 * @return
	 */
	List<ConfigurationParamsEntity> attachOrg(List<ConfigurationParamsEntity> entitys);

	/**
	 * @author 327090
	 * @param entity
	 * @return
	 */
	ConfigurationParamsEntity attachOrg(ConfigurationParamsEntity entity);

}
