package com.deppon.foss.module.transfer.dubbo.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.dubbo.crm.api.define.exception.TfrBusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.transfer.dubbo.api.dao.ITransfer2CrmIntegratedDao4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.dao.IWaybillDao4dubbo;
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
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillRelateDetailEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillStockEntity;
import com.deppon.foss.module.transfer.dubbo.api.service.ITransfer2CrmIntegratedService4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.service.IWaybillManagerService4dubbo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

public class Transfer2CrmIntegratedService4dubbo implements ITransfer2CrmIntegratedService4dubbo {
	private IDataDictionaryValueService dataDictionaryValueService;
	private ITransfer2CrmIntegratedDao4dubbo transfer2CrmIntegratedDao4dubbo;
	private IWaybillManagerService4dubbo waybillManagerService4dubbo;
	private IWaybillDao4dubbo waybillDao;

	public IWaybillDao4dubbo getWaybillDao() {
		return waybillDao;
	}

	@Autowired
	public void setWaybillDao(IWaybillDao4dubbo waybillDao) {
		this.waybillDao = waybillDao;
	}

	public IDataDictionaryValueService getDataDictionaryValueService() {
		return dataDictionaryValueService;
	}

	@Autowired
	public void setDataDictionaryValueService(IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public ITransfer2CrmIntegratedDao4dubbo getTransfer2CrmIntegratedDao4dubbo() {
		return transfer2CrmIntegratedDao4dubbo;
	}

	@Autowired
	public void setTransfer2CrmIntegratedDao4dubbo(ITransfer2CrmIntegratedDao4dubbo transfer2CrmIntegratedDao4dubbo) {
		this.transfer2CrmIntegratedDao4dubbo = transfer2CrmIntegratedDao4dubbo;
	}

	public IWaybillManagerService4dubbo getWaybillManagerService4dubbo() {
		return waybillManagerService4dubbo;
	}

	@Autowired
	public void setWaybillManagerService4dubbo(IWaybillManagerService4dubbo waybillManagerService4dubbo) {
		this.waybillManagerService4dubbo = waybillManagerService4dubbo;
	}

	@Override
	public WaybillInfoByWaybillNoReultEntity queryWaybillInfoByWaybillNo(String waybillNO) {
		if (StringUtils.isEmpty(waybillNO)) {
			throw new TfrBusinessException("单号不可为空");
		}
		// 转换航班类型
		WaybillInfoByWaybillNoReultEntity result = transfer2CrmIntegratedDao4dubbo
				.queryWaybillInfoByWaybillNo(waybillNO);

		/**
		 * 根据 单号来判断是否为字母件 如果为字母件则货物总重量和总体积为字母件的总重量和体积
		 */
		if (result != null) {
			List<String> list = new ArrayList<String>();
			list.add(waybillNO);
			List<WaybillRelateDetailEntity> entity = getWaybillDao().queryWaybillRelateDetailByWaybillNos(list, 0, 0,
					false);
			if (CollectionUtils.isNotEmpty(entity)) {
				WaybillRelateDetailEntity dateilEntity = entity.get(0);
				result.setCzmGoodsVolumeTotal(dateilEntity.getGoodsVolumeTotal());
				result.setCzmGoodsWeightTotal(dateilEntity.getGoodsWeightTotal());
			}
		}

		if (result != null) {
			if (StringUtils.isEmpty(result.getCustomerPickupOrgName())) {
				String orgcode = result.getCustomerPickupOrgCode();
				String productCode = result.getProductCode();
				String customerPickupOrgName = transfer2CrmIntegratedDao4dubbo
						.queryCustomerPickupOrgNameByWayno(orgcode, productCode);
				result.setCustomerPickupOrgName(customerPickupOrgName);
			}
			if (StringUtils.isEmpty(result.getReceiveOrgName())) {
				String orgcode = result.getReceiveOrgCode();
				String receiveOrgName = transfer2CrmIntegratedDao4dubbo.queryReceiveOrgNameByWayno(orgcode);
				result.setReceiveOrgName(receiveOrgName);
			}
			// 收货地址备注
			if (StringUtils.isNotEmpty(result.getReceiveCustomerAddressNote())) {
				String receiveCustomerAddress = result.getReceiveCustomerAddress() + "("
						+ result.getReceiveCustomerAddressNote() + ")";
				result.setReceiveCustomerAddress(receiveCustomerAddress);
			}
			// 发货地址备注
			if (StringUtils.isNotEmpty(result.getDeliveryCustomerAddressNote())) {
				String deliveryCustomerAddress = result.getDeliveryCustomerAddress() + "("
						+ result.getDeliveryCustomerAddressNote() + ")";
				result.setDeliveryCustomerAddress(deliveryCustomerAddress);
			}
			/**
			 * DMANA-8928 FOSS开单品名自动匹配货源品类需求 货源品类编码转换成名称
			 * 
			 * @author Foss-206860
			 */
			DataDictionaryValueEntity categoryEntity = new DataDictionaryValueEntity();
			if (StringUtils.isNotEmpty(result.getCategory())) {
				categoryEntity = getDataDictionaryValueService().queryDataDictionaryValueByTermsCodeValueCode(
						DictionaryConstants.BSE_SOURCE_CATEGORY, result.getCategory());
				// 设置
				if (categoryEntity == null) {
					result.setCategory("");
				} else {
					result.setCategory(categoryEntity.getValueName());
				}
			}
		}

		DataDictionaryValueEntity resultFlightType = new DataDictionaryValueEntity();
		// 获取航班的中文描述
		if (result != null) {
			resultFlightType = getDataDictionaryValueService().queryDataDictionaryValueByTermsCodeValueCode(
					DictionaryConstants.AIR_FLIGHT_TYPE, result.getFlightNumberType());
			// 设置出发部门
			result.setStartOrgCode(getStartOrgCode(waybillNO));
		} else {
			return null;
		}
		// 设置
		if (resultFlightType == null) {
			result.setFlightNumberType("");
		} else {
			result.setFlightNumberType(resultFlightType.getValueName());
		}
		return result;
	}

	private String getStartOrgCode(String waybillNO) {
		WaybillEntity waybillEntity = transfer2CrmIntegratedDao4dubbo.queryWaybillByNo(waybillNO);
		if (waybillEntity != null) {
			/**
			 * 判断是否是集中开单
			 */
			if (FossConstants.YES.equals(waybillEntity.getPickupCentralized())) {
				// 集中开单查开单组所属中转场的默认出发部门
				SaleDepartmentEntity saleDepartment = waybillManagerService4dubbo
						.queryPickupCentralizedDeptCode(waybillEntity.getCreateOrgCode());
				if (saleDepartment != null && saleDepartment.getCode() != null) {
					return saleDepartment.getCode();
				} else {
					return null;
				}
			} else {
				// 非集中开单收货部门为出发部门
				return waybillEntity.getReceiveOrgCode();
			}
		} else {
			return null;
		}
	}

	@Override
	public List<StockEntity> queryStockByWaybillNo(WaybillStockEntity waybillStockEntity) {
		// 查询库存表
		return transfer2CrmIntegratedDao4dubbo.queryStockByWaybillNo(waybillStockEntity);
	}

	@Override
	public List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) {
		return transfer2CrmIntegratedDao4dubbo.queryAllSerialByWaybillNo(waybillNo);
	}

	@Override
	public List<AirWaybillEntity> queryAirWayBillListByWayBill(AirWaybillDetailDto airWaybillDetailDto) {
		return transfer2CrmIntegratedDao4dubbo.queryAirWayBillListByWayBill(airWaybillDetailDto);
	}

	@Override
	public List<ExternalBillDto> selectExternalByWayBillNo(ExternalBillDto dto) {
		return transfer2CrmIntegratedDao4dubbo.selectExternalByWayBillNo(dto);
	}

	@Override
	public List<LdpExternalBillDto> queryExternalBillListByWaybillNo(String waybillNo) {
		return transfer2CrmIntegratedDao4dubbo.queryExternalBillListByWaybillNo(waybillNo);
	}

	@Override
	public List<String> querySerialNoByWaybillNo(StockDto dto) {
		return transfer2CrmIntegratedDao4dubbo.querySerialNoByWaybillNo(dto);
	}

	@Override
	public List<InOutStockEntity> queryInStockInfoSmall(String waybillNo, String serialNo, String orgCode,
			Date createBillTime) {
		return transfer2CrmIntegratedDao4dubbo.queryInStockInfoSmall(waybillNo, serialNo, orgCode, createBillTime);
	}

	@Override
	public List<StockEntity> queryStockByWaybillNoInStockTime(WaybillStockEntity waybillStockEntity) {
		return transfer2CrmIntegratedDao4dubbo.queryStockByWaybillNoInStockTime(waybillStockEntity);
	}

}
