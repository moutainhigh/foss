package com.deppon.foss.module.settlement.dubbo.api.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.ActualFreightEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.dubbo.api.define.LabeledGoodEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.StockDto;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillChargeDtlEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillDisDtlEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillExpressEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillFRcQueryByWaybillNosDto;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillPaymentEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WoodenRequirementsEntity;

public interface IWaybillRfcDao4dubbo {
	
	/**
	 * 通过运单号集合拿到待处理的更改单
	 * @author 327090
	 * @param WaybillFRcQueryByWaybillNosDto
	 * @return
	 */
	List<String> queryWaybillRfcByWaybillNos(WaybillFRcQueryByWaybillNosDto WaybillFRcQueryByWaybillNosDto);
	
	/**
	 * 查询运单基本信息（包含免费接货）
	 * 
	 * @author 327090
	 * @date 2016年12月2日09:09:56
	 */
	public WaybillEntity queryWaybillBasicInfoByNo(String waybillNo);
	
	/**
	 * 根据运单号查询对应费用明细实体List
	 * 
	 * @author 327090
	 * @date 2016-12-5 下午2:56:18
	 * 
	 */
	public List<WaybillChargeDtlEntity> queryChargeDtlEntityByNo(String waybillNo);
	
	/**
	 * 根据运单号查询折扣明细列表
	 * 
	 * @author 327090
	 * @date 2016-12-2 下午5:38:57
	 * 
	 */
	public List<WaybillDisDtlEntity> queryDisDtlEntityByNo(String waybillNo);
	
	/**
	 * 根据运单号查询折扣明细列表
	 * 
	 * @author 327090
	 * @date 2016-12-2 下午5:38:57
	 */
	public List<WaybillPaymentEntity> queryPaymentEntityByNo(String waybillNo);
	
	/**
     * 根据运单号查询代打木架实体信息
     * 
     * @author 327090
     * @date 2016-12-2 下午5:53:34
     * 
     */
    public WoodenRequirementsEntity queryWoodenByNo(String waybillNo);
    
    /**
     * 根据运单号查询ActualFreightEntity
     * @author 327090
     * @date 2016-12-2
     * @param waybillNo
     * @return ActualFreightEntity
     */
    public ActualFreightEntity queryByWaybillNo(String waybillNo);
    
    /**
	 * 判定是否快递
	 * @author 327090
	 * @date 2016-12-3 19:21:26
	 */
	public boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime);
	
	/**
	 * 通过运单Id查询运单快递
	 * @author 327090
	 * @param waybill
	 */
	public WaybillExpressEntity queryWaybillExpressByWaybillId(String waybillId);
	
	/**
	 * 
	 * <p>
	 * 通过运单号查询所有流水号
	 * </p>
	 * @author 327090
	 * @author foss-jiangfei
	 * @date 2016-12-3 下午4:55:34
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo);
	
	/**
	 * 根据运单号查询运单部分数据（查询付款方式，到付金额，外发代理和币种,
	 * 运输性质,是否整车运单,提货方式,总费用,保价声明价值,
	 * 代收货款,货物总件数,最终配载部门，订单编号）
	 * @author 327090
	 * @date 2016-12-3 下午2:14:48
	 */
	public WaybillEntity queryPartWaybillByNo(String waybillNo);
	
	 /**
     * 给中转组提供 判断货物是否已签收的接口，参数（运单号、流水号）
     * @author 327090
     * @date 2016-12-2 上午16:12:15
     * @param waybillNo 运单号
     * @param serialNo 流水号
     * @return String
     * @see
     */
	public String querySerialNoIsSign(StockDto dto);
	
	/**
	 * 据运单号,active = 'Y'查询运单签结果里的运单信息
	 * @author 327090
	 * @date 2016-2-18 下午8:03:01
	 * @param entity
	 * @return 
	 */
	public WaybillSignResultEntity queryWaybillSignResult(WaybillSignResultEntity entity);
	
	/**
	 * 根据传入的一到多个运单单号，获取一到多条应收单信息
     * @author 327090
     * @date 2016-12-2
     * @param wayBillNos  运单单号集合
	 */
	public List<BillReceivableEntity> queryByWaybillNOs(List<String> waybillNos);
	
	/**
	 * 锁定应收单信息
	 * @author 327090
	 * @date 2016-12-2 上午9:05:22
	 */
	public int updateReceiveBillInfoForLock(BillReceivableOnlineQueryDto queryDto);

	/**
	 * @author 327090
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<ConfigurationParamsEntity> queryConfigurationParamsExactByEntity(
			ConfigurationParamsEntity entity, int start, int limit);
	
}
