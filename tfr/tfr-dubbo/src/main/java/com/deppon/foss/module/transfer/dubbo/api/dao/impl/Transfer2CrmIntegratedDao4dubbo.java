package com.deppon.foss.module.transfer.dubbo.api.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.dubbo.api.dao.ITransfer2CrmIntegratedDao4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.define.AirWaybillDetailDto;
import com.deppon.foss.module.transfer.dubbo.api.define.AirWaybillEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.ExternalBillDto;
import com.deppon.foss.module.transfer.dubbo.api.define.InOutStockEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.LabeledGoodEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.LdpExternalBillDto;
import com.deppon.foss.module.transfer.dubbo.api.define.StockDto;
import com.deppon.foss.module.transfer.dubbo.api.define.StockEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillInfoByWaybillNoReultEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillQueryArgsDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillStockEntity;
import com.deppon.foss.util.define.FossConstants;

public class Transfer2CrmIntegratedDao4dubbo extends BaseDao implements ITransfer2CrmIntegratedDao4dubbo {
	//sonar-352203
//	private static final Logger LOGGER = LogManager.getLogger(Transfer2CrmIntegratedDao4dubbo.class);

	@Override
	public WaybillInfoByWaybillNoReultEntity queryWaybillInfoByWaybillNo(String waybillNO) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNO", waybillNO);
		map.put("active", FossConstants.ACTIVE);
		return (WaybillInfoByWaybillNoReultEntity) this.getSqlSession().selectOne("queryWaybillInfoByWaybillNo4dubbo", map);
	}

	@Override
	public String queryCustomerPickupOrgNameByWayno(String orgcode, String productCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgcode", orgcode);
		map.put("active", FossConstants.ACTIVE);
		String customerPickupOrgName = null;
		if (productCode.equals("AF") || productCode.endsWith("PLF")) {
			customerPickupOrgName = (String) this.getSqlSession()
					.selectOne("queryBranchCustomerPickupOrgNameByWaynoActive4dubbo", map);
		} else {
			customerPickupOrgName = (String) this.getSqlSession()
					.selectOne("queryOrgCustomerPickupOrgNameByWaynoActive4dubbo", map);
		}
		if (StringUtil.isEmpty(customerPickupOrgName)) {
			if (productCode.equals("AF") || productCode.endsWith("PLF")) {
				customerPickupOrgName = (String) this.getSqlSession()
						.selectOne("queryBranchCustomerPickupOrgNameByWayno4dubbo", map);
			} else {
				customerPickupOrgName = (String) this.getSqlSession().selectOne("queryOrgCustomerPickupOrgNameByWayno4dubbo",
						map);
			}
		}
		return customerPickupOrgName;
	}

	@Override
	public String queryReceiveOrgNameByWayno(String orgcode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgcode", orgcode);
		return (String) this.getSqlSession().selectOne("queryReceiveOrgNameByWayno4dubbo", map);
	}

	@Override
	public WaybillEntity queryWaybillByNo(String waybillNo) {
		// 封装查询条件
		WaybillQueryArgsDto argsDto = new WaybillQueryArgsDto();
		argsDto.setWaybillNo(waybillNo);
		argsDto.setActive(FossConstants.YES);
		return (WaybillEntity) this.getSqlSession().selectOne("selectByWaybillNoAndOrderNo4dubbo", argsDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockEntity> queryStockByWaybillNo(WaybillStockEntity waybillStockEntity) {
		return this.getSqlSession().selectList("stockQueryByWaybillNo4dubbo", waybillStockEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.YES);
		List<LabeledGoodEntity> labeledGoodList = this.getSqlSession().selectList("selectlastSerialNoBywaybillNo4dubbo", map);
		return labeledGoodList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillEntity> queryAirWayBillListByWayBill(AirWaybillDetailDto airWaybillDetailDto) {
		return this.getSqlSession().selectList("queryAirWayBillListByWayBill4dubbo", airWaybillDetailDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExternalBillDto> selectExternalByWayBillNo(ExternalBillDto dto) {
		// 运单可用
		dto.setActive("Y");
		return this.getSqlSession().selectList("selectExternalByWayBillNo4dubbo", dto);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpExternalBillDto> queryExternalBillListByWaybillNo(
			String waybillNo) {
		LdpExternalBillDto ldpExternalBillDto = new LdpExternalBillDto();
		ldpExternalBillDto.setWaybillNo(waybillNo);
		return this.getSqlSession().selectList("queryExternalBillListByWaybillNo4dubbo", ldpExternalBillDto);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> querySerialNoByWaybillNo(StockDto dto){
		return (List<String>)this.getSqlSession().selectList("selectSerialNoByWaybillNo4dubbo", dto);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InOutStockEntity> queryInStockInfoSmall(String waybillNo,
			String serialNo, String orgCode, Date createBillTime) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("serialNo", serialNo);
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("isValid", FossConstants.YES);
		paramsMap.put("createBillTime", createBillTime);
		return this.getSqlSession().selectList("queryInStockInfoSmall4dubbo", paramsMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockEntity> queryStockByWaybillNoInStockTime(
			WaybillStockEntity waybillStockEntity) {
		return this.getSqlSession().selectList("queryStockByWaybillNoInStockTime4dubbo", waybillStockEntity);
	}
}
