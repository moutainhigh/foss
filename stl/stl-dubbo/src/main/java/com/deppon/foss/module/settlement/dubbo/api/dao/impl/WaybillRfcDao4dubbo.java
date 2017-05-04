package com.deppon.foss.module.settlement.dubbo.api.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.settlement.dubbo.api.dao.IWaybillRfcDao4dubbo;
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
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillQueryArgsDto;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WoodenRequirementsEntity;
import com.deppon.foss.module.settlement.dubbo.api.util.PricingConstants;
import com.deppon.foss.util.define.FossConstants;

public class WaybillRfcDao4dubbo extends BaseDao implements IWaybillRfcDao4dubbo {

	private static final String RFC_NAMESPACE = "foss.uip.WaybillRfcEntity4DubboMapper.";
	private static final String NAMESPACE = "foss.uip.WaybillEntity4DubboMapper.";
	private static final String NAMESPACES = "foss.uip.WaybillChargeDtlEntityMapper.";
	private static final String NAMESPACEES = "foss.uip.WaybillDisDtlEntityMapper.";
	private static final String U_NAMESPACE = "foss.uip.WaybillPaymentEntityMapper.";
	private static final String I_NAMESPACE = "foss.uip.WoodenRequirementsEntityMapper.";
	private static final String P_NAMESPACE = "foss.uip.ActualFreightEntityMapper.";
	private static final String UI_NAMESPACE = "foss.uip.pkp-pricing.pricingProduct.";
	private static final String UIP_NAMESPACE = "foss.uip.WaybillExpressEntityMapper.";
	private static final String UP_NAMESPACE = "foss.uip.LabeledGoodMapper.";
	private static final String U_NAMESPACES = "foss.uip.WaybillEntityMapper.";
	private static final String I_NAMESPACES = "foss.uip.dubbo.pickup.sign.api.shared.domain.SignDetailEntity.";
	private static final String P_NAMESPACES = "foss.uip.pickup.sign.api.shared.domain.WaybillSignResultEntity.";
	private static final String UI_NAMESPACES = "foss.stl.dubbo.BillReceivableEntityDao.";
	private static final String UIP_NAMESPACES = "foss.bse.bse-dict.configurationParams.";
	private static final int THREE = 3;
	
	/**
	 * @author 327090
	 * @date
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWaybillRfcByWaybillNos(
			WaybillFRcQueryByWaybillNosDto waybillFRcQueryByWaybillNosDto) {

		return getSqlSession().selectList(
				RFC_NAMESPACE + "queryWaybillRFcByWaybillNos",
				waybillFRcQueryByWaybillNosDto);
	}

	/**
	 * @author 327090
	 */
	@Override
	public List<WaybillChargeDtlEntity> queryChargeDtlEntityByNo(
			String waybillNo) {
		// 封装参数
		WaybillQueryArgsDto args = new WaybillQueryArgsDto();
		args.setWaybillNo(waybillNo);
		args.setActive(FossConstants.ACTIVE);
		List<WaybillChargeDtlEntity> list = this.getSqlSession().selectList(
				NAMESPACES + "selectEntityListByNo", args);
		return list;
	}

	/**
	 * @author 327090
	 */
	@Override
	public WaybillEntity queryWaybillBasicInfoByNo(String waybillNo) {
		// 封装查询条件
		WaybillQueryArgsDto argsDto = new WaybillQueryArgsDto();
		argsDto.setWaybillNo(waybillNo);
		argsDto.setActive(FossConstants.YES);
		return (WaybillEntity) this.getSqlSession().selectOne(
				NAMESPACE + "selectWaybillBasicInfoByWaybillNo", argsDto);
	}

	
	/**
	 * @author 327090
	 */
	@Override
	public List<WaybillDisDtlEntity> queryDisDtlEntityByNo(String waybillNo) {
		// 封装参数
		WaybillQueryArgsDto args = new WaybillQueryArgsDto();
		args.setWaybillNo(waybillNo);
		args.setActive(FossConstants.ACTIVE);

		@SuppressWarnings("unchecked")
		List<WaybillDisDtlEntity> list = this.getSqlSession().selectList(
				NAMESPACEES + "selectEntityListByNo", args);
		return list;
	}

	/**
	 * @author 327090
	 */
	@Override
	public List<WaybillPaymentEntity> queryPaymentEntityByNo(String waybillNo) {
		// 封装参数
		WaybillQueryArgsDto args = new WaybillQueryArgsDto();
		args.setWaybillNo(waybillNo);
		args.setActive(FossConstants.ACTIVE);

		@SuppressWarnings("unchecked")
		List<WaybillPaymentEntity> list = this.getSqlSession().selectList(
				U_NAMESPACE + "selectEntityListByNo", args);
		return list;
	}

	/**
	 * @author 327090
	 */
	@Override
	public WoodenRequirementsEntity queryWoodenByNo(String waybillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("waybillNo", waybillNo);
    	map.put("active", FossConstants.ACTIVE);
    	return (WoodenRequirementsEntity) this.getSqlSession().selectOne(I_NAMESPACE + "selectByWaybillNo", map);
	}

	
	/**
	 * @author 327090
	 */
	@Override
	public ActualFreightEntity queryByWaybillNo(String waybillNo) {
		return (ActualFreightEntity) this.getSqlSession().selectOne(P_NAMESPACE + "selectByWaybillNo", waybillNo);
	}

	
	/**
	 * @author 327090
	 */
	@Override
	public boolean onlineDetermineIsExpressByProductCode(String productCode,Date billTime) {
		// 判定时间是否为空
		if (billTime == null) {
			billTime = new Date();
		}
		// 快递一级汽运
		List<String> productCodeList = new ArrayList<String>();
		productCodeList
				.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003);
		// 封装查询参数
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("levels", THREE);
		parameter.put("productCode", productCode);
		parameter.put("billTime", billTime);
		parameter.put("active", FossConstants.ACTIVE);
		parameter.put("levels", THREE);
		parameter.put("productCodeList", productCodeList);
		List<ProductEntity> list = this.getSqlSession().selectList(
				UI_NAMESPACE + "onlineDetermineIsExpressByProductCode", parameter);
		return CollectionUtils.isEmpty(list) ? false : true;
	}

	/**
	 * @author 327090
	 */
	@Override
	public WaybillExpressEntity queryWaybillExpressByWaybillId(String waybillId) {
		List<WaybillExpressEntity> list =  this.getSqlSession().selectList(
				UIP_NAMESPACE + "queryWaybillExpressByWaybillId", waybillId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * @author 327090
	 */
	@Override
	public List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.YES);
		List<LabeledGoodEntity> labeledGoodList = this.getSqlSession()
				.selectList(UP_NAMESPACE + "selectlastSerialNoBywaybillNo",map);
		return labeledGoodList;
	}

	/**
	 *@author 327090 
	 */
	@Override
	public WaybillEntity queryPartWaybillByNo(String waybillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);//运单号
		map.put("active", FossConstants.ACTIVE);//有效		
		List<WaybillEntity> list=this.getSqlSession().selectList(U_NAMESPACES + "queryPartWaybillByNo", map);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	
	/**
	 * @author 327090
	 */
	@Override
	public String querySerialNoIsSign(StockDto dto) {
		if((Integer)this.getSqlSession().selectOne(I_NAMESPACES + "selectSerialNoIsSign", dto)>0){
			return FossConstants.YES;
		}else {
			return FossConstants.NO;
		}
	}

	
	/**
	 * @author 327090
	 */
	@Override
	public WaybillSignResultEntity queryWaybillSignResult(
			WaybillSignResultEntity entity) {
		List<WaybillSignResultEntity> waybillSignResultEntitys =this.getSqlSession().selectList(P_NAMESPACES + "queryWaybillSignResult",entity);
		return CollectionUtils.isEmpty(waybillSignResultEntitys) ? null : waybillSignResultEntitys.get(0);
	}

	
	/**
	 * @author 327090
	 */
	@Override
	public List<BillReceivableEntity> queryByWaybillNOs(List<String> waybillNos) {
		if (CollectionUtils.isEmpty(waybillNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		return this.getSqlSession().selectList(UI_NAMESPACES + "selectByWayBillNos", map);
	}

	/**
	 * @author 327090
	 */
	@Override
	public int updateReceiveBillInfoForLock(
			BillReceivableOnlineQueryDto queryDto) {
		return this.getSqlSession().update(UI_NAMESPACES + "updateByUnlockDate", queryDto);
	}
	
	/** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 327090
     * @date 2016-12-1 上午11:11:15
     * @see ByEntity(com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ConfigurationParamsEntity> queryConfigurationParamsExactByEntity(
	    ConfigurationParamsEntity entity, int start, int limit) {
	ConfigurationParamsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new ConfigurationParamsEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession()
		.selectList(UIP_NAMESPACES + "queryConfigurationParamsExactByEntity",
			queryEntity,rowBounds);
    }

}
