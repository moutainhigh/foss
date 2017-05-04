package com.deppon.foss.module.transfer.dubbo.api.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.transfer.dubbo.api.dao.IWaybillDao4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.define.ActualFreightEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.SignDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillExpressEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillInfoDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillQueryCityProvinceDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillQueryInfoDto;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillRelateDetailEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillSignDetailVo;
import com.deppon.foss.module.transfer.dubbo.api.service.IWaybillCommonService;
import com.deppon.foss.module.transfer.dubbo.api.service.IWaybillQueryService4dubbo;

public class WaybillQueryService4dubbo implements IWaybillQueryService4dubbo {
	private static final String Y = "Y";

	private static final Logger LOGGER = LogManager.getLogger(WaybillQueryService4dubbo.class);

	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IExpressPartSalesDeptService expressPartSalesDeptService;
	private IWaybillCommonService waybillCommonService;

	private ICommonAgencyDeptDao commonAgencyDeptDao;
	private IWaybillDao4dubbo waybillDao;

	@Override
	public List<WaybillInfoDto> queryWaybillInfoForSOC(List<String> waybillList) {
		if (!CollectionUtils.isEmpty(waybillList)) {
			WaybillQueryInfoDto waybillQueryInfoDto = new WaybillQueryInfoDto();// 创建对象
			waybillQueryInfoDto.setState(Y);
			waybillQueryInfoDto.setWaybillList(waybillList);
			List<WaybillInfoDto> waybillInfoDtoList = getWaybillDao().queryWaybillInfoForSOC(waybillQueryInfoDto);
			List<WaybillInfoDto> waybillInfoDtoListNew = new ArrayList<WaybillInfoDto>();
			WaybillInfoDto waybillInfoDtoNew = null;
			for (WaybillInfoDto waybillInfoDto : waybillInfoDtoList) {
				waybillInfoDtoNew = new WaybillInfoDto();
				try {
					PropertyUtils.copyProperties(waybillInfoDtoNew, waybillInfoDto);
				} catch (IllegalAccessException e) {
					// 出现异常
					LOGGER.error("error", e);
				} catch (InvocationTargetException e) {
					// 出现异常
					LOGGER.error("error", e);
				} catch (NoSuchMethodException e) {
					// 出现异常
					LOGGER.error("error", e);
				}

				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity0 = getOrgAdministrativeInfoService()
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getStartExpressOrgCode());
				if (orgAdministrativeInfoEntity0 != null) {
					waybillInfoDtoNew.setStartExpressUnfiedCode(orgAdministrativeInfoEntity0.getUnifiedCode());// 标杆编码

					List<OrgAdministrativeInfoEntity> list = getOrgAdministrativeInfoComplexService()
							.queryOrgAdministrativeInfoUpByBizType(waybillInfoDtoNew.getStartExpressOrgCode(),
									BizTypeConstants.EXPRESS_BIG_REGION);

					if (list != null && list.size() > 0) {
						OrgAdministrativeInfoEntity entity = list.get(0);
						waybillInfoDtoNew.setDistrictCode(entity.getCode());
						waybillInfoDtoNew.setDistrictName(entity.getName());
						waybillInfoDtoNew.setDistrictUnifiedCode(entity.getUnifiedCode());
					}

				}

				ExpressPartSalesDeptResultDto dtoExp = null;
				SignDto dto = getWaybillDao().getWaybillSignInfo(waybillInfoDto.getWaybillNo());
				if (dto != null) {
					if (StringUtils.isNotEmpty(dto.getEmpCode())) {
						waybillInfoDtoNew.setEndExpressEmpCode(dto.getEmpCode());
						waybillInfoDtoNew.setEndExpressEmpName(dto.getEmpName());
					} else {
						waybillInfoDtoNew.setEndExpressEmpCode(dto.getCreateUserCode());
						waybillInfoDtoNew.setEndExpressEmpName(dto.getCreateUserName());
					}
					if (StringUtils.isNotEmpty(dto.getOrgCode())) {
						waybillInfoDtoNew.setEndExpressOrgCode(dto.getOrgCode());
						waybillInfoDtoNew.setEndExpressOrgName(dto.getOrgName());
					} else {
						dtoExp = getExpressPartSalesDeptService()
								.queryExpressPartSalesDeptBySalesCodeAndTime(dto.getCreateOrgCode(), new Date());
						if (dtoExp != null) {
							waybillInfoDtoNew.setEndExpressOrgCode(dtoExp.getPartCode());
							waybillInfoDtoNew.setEndExpressOrgName(dtoExp.getPartName());
						}
					}
					if (StringUtils.isNotEmpty(waybillInfoDtoNew.getEndExpressOrgCode())) {

						OrgAdministrativeInfoEntity tmp = getOrgAdministrativeInfoService()
								.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getEndExpressOrgCode());
						if (tmp != null) {
							waybillInfoDtoNew.setEndExpressUnfiedCode(tmp.getUnifiedCode());
						}
					}

					if (StringUtils.isNotEmpty(waybillInfoDtoNew.getEndExpressOrgCode())) {

						List<OrgAdministrativeInfoEntity> list = getOrgAdministrativeInfoComplexService()
								.queryOrgAdministrativeInfoUpByBizType(waybillInfoDtoNew.getEndExpressOrgCode(),
										BizTypeConstants.EXPRESS_BIG_REGION);

						if (list != null && list.size() > 0) {
							OrgAdministrativeInfoEntity entity = list.get(0);
							waybillInfoDtoNew.setEndDistrictCode(entity.getCode());
							waybillInfoDtoNew.setEndDistrictName(entity.getName());
							waybillInfoDtoNew.setEndDistrictUnifiedCode(entity.getUnifiedCode());
						}
					}

				} else {
					// 在没有签收的情况下 返回快递提货网点的快递点部信息
					String customerPickUpOrgCode = waybillInfoDtoNew.getCustomerPickupOrgCode();
					dtoExp = getExpressPartSalesDeptService()
							.queryExpressPartSalesDeptBySalesCodeAndTime(customerPickUpOrgCode, new Date());

					if (dtoExp != null) {

						waybillInfoDtoNew.setEndExpressOrgCode(dtoExp.getPartCode());
						OrgAdministrativeInfoEntity tmp = getOrgAdministrativeInfoService()
								.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getEndExpressOrgCode());
						if (tmp != null) {
							waybillInfoDtoNew.setEndExpressOrgName(dtoExp.getPartName());
						}

						if (StringUtils.isNotEmpty(dtoExp.getPartCode())) {

							List<OrgAdministrativeInfoEntity> list = getOrgAdministrativeInfoComplexService()
									.queryOrgAdministrativeInfoUpByBizType(dtoExp.getPartCode(),
											BizTypeConstants.EXPRESS_BIG_REGION);

							if (list != null && list.size() > 0) {
								OrgAdministrativeInfoEntity entity = list.get(0);

								waybillInfoDtoNew.setEndDistrictCode(entity.getCode());
								waybillInfoDtoNew.setEndDistrictName(entity.getName());
								waybillInfoDtoNew.setEndDistrictUnifiedCode(entity.getUnifiedCode());

							}
						}
					}

				}

				// 小件=-------------------11111111

				// 根据编码查询出发部门
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = getOrgAdministrativeInfoService()
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getCreateOrgCode());
				if (orgAdministrativeInfoEntity != null) {
					// 出发部门名称
					waybillInfoDtoNew.setDepartureDeptName(orgAdministrativeInfoEntity.getName());
					// 出发部门标杆编码
					waybillInfoDtoNew.setDepartureDeptNumber(orgAdministrativeInfoEntity.getUnifiedCode());
					// 出发部门地址
					waybillInfoDtoNew.setDepartureDeptAddr(orgAdministrativeInfoEntity.getAddress());
					// 出发部门电话
					waybillInfoDtoNew.setDepartureDeptPhone(orgAdministrativeInfoEntity.getDepTelephone());
					// 出发部门传真
					waybillInfoDtoNew.setDepartureDeptFax(orgAdministrativeInfoEntity.getDepFax());
				}

				/**
				 * 根据BUGKD-1616 修复 运单查询明细接口：收货部门对应值取值错误，应该取收入部门（即收货部门）对应的值。
				 */
				// 根据编码查询收货部门
				OrgAdministrativeInfoEntity org = getOrgAdministrativeInfoService()
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getReceiveOrgCode());
				if (org != null) {
					// 收货部门名称
					waybillInfoDtoNew.setReceiveOrgName(org.getName());
					// 收货部门标杆编码
					waybillInfoDtoNew.setReceiveOrgNumber(org.getUnifiedCode());
				}

				// 根据编码查询提货网点
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = getOrgAdministrativeInfoService()
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getCustomerPickupOrgCode());
				if (orgAdministrativeInfoEntity1 != null) {
					LOGGER.info("根据编码查询提货网点: " + orgAdministrativeInfoEntity1);
					// 到达网点名称
					waybillInfoDtoNew.setLadingStationName(orgAdministrativeInfoEntity1.getName());
					// 到达网点标杆编码
					waybillInfoDtoNew.setLadingStationNumber(orgAdministrativeInfoEntity1.getUnifiedCode());
					// 到达网点地址
					waybillInfoDtoNew.setLadingStationAddr(orgAdministrativeInfoEntity1.getAddress());
					// 到达网点电话
					waybillInfoDtoNew.setLadingStationPhone(orgAdministrativeInfoEntity1.getDepTelephone());
					// 到达网点传真
					waybillInfoDtoNew.setLadingStationFax(orgAdministrativeInfoEntity1.getDepFax());
				} else {
					LOGGER.info("根据编码查询空运代理------");
					OuterBranchEntity outerBranchEntity = new OuterBranchEntity();
					outerBranchEntity.setAgentDeptCode(waybillInfoDtoNew.getCustomerPickupOrgCode());
					outerBranchEntity.setActive(Y);
					List<OuterBranchEntity> outList = getCommonAgencyDeptDao()
							.queryAgencyDeptsByCondition(outerBranchEntity, 1, 0);
					LOGGER.info("根据编码查询空运代理: " + outList);
					// 若outList不为空的话
					if (!CollectionUtils.isEmpty(outList)) {
						OuterBranchEntity outerBranchNew = outList.get(0);
						if (outerBranchNew != null) {
							// 到达网点名称
							waybillInfoDtoNew.setLadingStationName(outerBranchNew.getAgentDeptName());
							// 到达网点标杆编码
							waybillInfoDtoNew.setLadingStationNumber(outerBranchNew.getAgentDeptCode());
							// 到达网点地址
							waybillInfoDtoNew.setLadingStationAddr(outerBranchNew.getAddress());
							// 到达网点电话
							waybillInfoDtoNew.setLadingStationPhone(outerBranchNew.getContactPhone());
						}
					}
				}

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("waybillNo", waybillInfoDtoNew.getWaybillNo());
				params.put("active", Y);
				// 根据运单号查出子母件信息
				List<WaybillRelateDetailEntity> waybillRelateDetailList = waybillDao
						.queryWaybillRelateDetailListByOrderOrWaybillNo(params);
				// 是子母件
				if (CollectionUtils.isNotEmpty(waybillRelateDetailList)) {
					// 是否子母件
					waybillInfoDtoNew.setParentChildWaybill(true);
					// 母件单号
					waybillInfoDtoNew.setParentWaybillNo(waybillRelateDetailList.get(0).getParentWaybillNo());
				} else {
					waybillInfoDtoNew.setParentChildWaybill(false);
				}

				// 验证签收情况
				List<WaybillSignDetailVo> waybillSignList = null;
				List<String> waybillNoList = null;
				if (StringUtils.isNotEmpty(waybillInfoDtoNew.getParentWaybillNo()))
					waybillNoList = waybillDao
							.queryWaybillNoListByParentWaybillNo(waybillInfoDtoNew.getParentWaybillNo());
				if (waybillInfoDtoNew.getSignSituation() != null) {
					waybillInfoDtoNew.setNormalSigned(false);
					waybillInfoDtoNew.setAbnormalSigned(false);
					waybillInfoDtoNew.setAbandonGoodsSigned(false);

					// 当单号为母件的情况
					if (CollectionUtils.isNotEmpty(waybillRelateDetailList)
							&& waybillInfoDtoNew.getWaybillNo().equals(waybillInfoDtoNew.getParentWaybillNo())) {
						waybillSignList = getWaybillDao().getWaybillSignList(waybillNoList);
					}

					if ("NORMAL_SIGN".equals(waybillInfoDtoNew.getSignSituation())) {
						// 非子母件是否正常签收
						if (CollectionUtils.isEmpty(waybillSignList)) {
							waybillInfoDtoNew.setNormalSigned(true);
						} else {
							// 子母件是否全部正常签收
							if (validateSignStatus(waybillSignList, "NORMAL_SIGN")) {
								waybillInfoDtoNew.setNormalSigned(true);
							}
						}
					}
					// 是否存在异常签收--理赔申请判断的依据
					waybillInfoDtoNew.setAbnormalSigned(!waybillInfoDtoNew.isNormalSigned());
					// 返单、子单签收、母件全部签收
					if (CollectionUtils.isEmpty(waybillNoList)
							|| (CollectionUtils.isNotEmpty(waybillNoList)
									&& !waybillInfoDtoNew.getWaybillNo().equals(waybillInfoDtoNew.getParentWaybillNo()))
							|| (CollectionUtils.isNotEmpty(waybillNoList) && CollectionUtils.isNotEmpty(waybillSignList)
									&& waybillSignList.size() >= waybillNoList.size())) {
						waybillInfoDtoNew.setSigned(true);
					}
				} else {
					waybillInfoDtoNew.setSigned(false);
				}
				// 去除除母单号
				if (CollectionUtils.isNotEmpty(waybillNoList))
					waybillNoList.remove(waybillInfoDtoNew.getParentWaybillNo());
				// 子单号集合
				waybillInfoDtoNew.setChildWaybillNoList(waybillNoList);
				// 查询返货单号集合
				WaybillExpressEntity expressEntity = new WaybillExpressEntity();
				expressEntity.setWaybillNo(waybillInfoDtoNew.getWaybillNo());
				List<WaybillExpressEntity> waybillExpressEntityList = waybillDao
						.queryWaybillReturnByWaybillNo(expressEntity);
				List<String> returnWaybillNoList = null;
				if (CollectionUtils.isNotEmpty(waybillExpressEntityList)) {
					returnWaybillNoList = new ArrayList<String>();
					for (WaybillExpressEntity entity : waybillExpressEntityList) {
						returnWaybillNoList.add(entity.getOriginalWaybillNo());
					}
				}
				// 返货单号集合
				waybillInfoDtoNew.setReturnWaybillNoList(returnWaybillNoList);

				// 拼接得到客户地址 省-市-区县-具体地址
				String deliveryCustomerAddress = waybillCommonService.getCompleteAddress(
						waybillInfoDtoNew.getDeliveryCustomerProvCode(),
						waybillInfoDtoNew.getDeliveryCustomerCityCode(),
						waybillInfoDtoNew.getDeliveryCustomerDistCode(),
						waybillInfoDtoNew.getDeliveryCustomerAddress());
				waybillInfoDtoNew.setDeliveryCustomerAddress(deliveryCustomerAddress);
				// 拼接得到客户地址 省-市-区县-具体地址

				String receiveCustomerAddress = waybillCommonService.getCompleteAddress(
						waybillInfoDtoNew.getReceiveCustomerProvCode(), waybillInfoDtoNew.getReceiveCustomerCityCode(),
						waybillInfoDtoNew.getReceiveCustomerDistCode(), waybillInfoDtoNew.getReceiveCustomerAddress());
				waybillInfoDtoNew.setReceiveCustomerAddress(receiveCustomerAddress);

				WaybillQueryCityProvinceDto provDto = waybillDao
						.queryCityProvince(waybillInfoDtoNew.getCreateOrgCode());

				if (provDto != null) {
					// 发货人城市名称
					waybillInfoDtoNew.setDeliveryCustomerCityName1(provDto.getCityName());
					// 发货人省份名称
					waybillInfoDtoNew.setDeliveryCustomerProvName(provDto.getProvName());
					// 发货人城市名称
					waybillInfoDtoNew.setDeliveryCustomerCityCode1(provDto.getCityCode());
					// 发货人省份名称
					waybillInfoDtoNew.setDeliveryCustomerProvCode(provDto.getProvCode());

					if (StringUtils.isNotEmpty(provDto.getCountyCode())) {
						waybillInfoDtoNew.setDeliveryCustomerDistCode(provDto.getCountyCode());
					}
				}

				WaybillQueryCityProvinceDto provDto2 = waybillDao
						.queryCityProvince(waybillInfoDtoNew.getCustomerPickupOrgCode());

				if (provDto2 != null) {
					// 收货人城市名称
					waybillInfoDtoNew.setReceiveCustomerCityName(provDto2.getCityName());
					// 收货人省份名称
					waybillInfoDtoNew.setReceiveCustomerProvName(provDto2.getProvName());
					// 收货人城市名称
					waybillInfoDtoNew.setReceiveCustomerCityCode(provDto2.getCityCode());
					// 收货人省份名称
					waybillInfoDtoNew.setReceiveCustomerProvCode(provDto2.getProvCode());

					if (StringUtils.isNotEmpty(provDto2.getCountyCode())) {
						waybillInfoDtoNew.setReceiveCustomerDistCode(provDto2.getCountyCode());
					}
				}

				// }

				// 第一次签收时间
				waybillInfoDtoNew
						.setFirstSignTime(waybillDao.queryFirstSignTimeByWaybillNo(waybillInfoDtoNew.getWaybillNo()));
				// 是否上门接货
				if (Y.equals(waybillInfoDtoNew.getPickupToDoor())) {
					waybillInfoDtoNew.setPickup(true);
				} else {
					waybillInfoDtoNew.setPickup(false);
				}

				// 运单状态
				waybillInfoDtoNew.setActive(getWaybillDao().queryWaybillStatus(waybillInfoDtoNew.getWaybillNo()));

				// 根据编码查询配载部门
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity2 = getOrgAdministrativeInfoService()
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getLoadOrgCode());
				if (orgAdministrativeInfoEntity2 != null) {
					waybillInfoDtoNew.setLoadOrgNumber(orgAdministrativeInfoEntity2.getUnifiedCode());
				}

				// 根据编码查询始发站
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity3 = getOrgAdministrativeInfoService()
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getDeliveryCustomerCityCode());
				waybillInfoDtoNew.setDeliveryCustomerCityName(orgAdministrativeInfoEntity3.getCityName());

				waybillInfoDtoListNew.add(waybillInfoDtoNew);
			}
			return waybillInfoDtoListNew;
		}
		return null;
	}

	private boolean validateSignStatus(List<WaybillSignDetailVo> waybillSignList, String signSituation) {
		boolean signFlag = false;
		if (CollectionUtils.isNotEmpty(waybillSignList)) {
			boolean unNormalFlag = false;
			for (WaybillSignDetailVo vo : waybillSignList) {
				String sign = vo.getSignStatus();
				if (!"NORMAL_SIGN".equals(sign)) {
					unNormalFlag = true;
					signFlag = false;
					break;
				}
			}
			if (!unNormalFlag)
				signFlag = true;
		}
		return signFlag;
	}

	@Override
	public WaybillExpressEntity queryWaybillByOriginalWaybillNo(String waybillNo,
			String waybillExpresstypeReturnCargo) {
		List<WaybillExpressEntity> entitys = waybillDao.queryWaybillByOriginalWaybillNo(waybillNo);
		if (CollectionUtils.isNotEmpty(entitys)) {
			for (WaybillExpressEntity entity : entitys) {
				ActualFreightEntity actualFreightEntity = waybillDao.queryByWaybillNo(entity.getWaybillNo());
				String status = actualFreightEntity.getStatus();
				if ("OBSOLETE".equals(status) || "ABORTED".equals(status)) {
					continue;
				}
				return entity;
			}
		}
		return null;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	@Autowired
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	@Autowired
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public ICommonAgencyDeptDao getCommonAgencyDeptDao() {
		return commonAgencyDeptDao;
	}

	@Autowired
	public void setCommonAgencyDeptDao(ICommonAgencyDeptDao commonAgencyDeptDao) {
		this.commonAgencyDeptDao = commonAgencyDeptDao;
	}

	public IWaybillDao4dubbo getWaybillDao() {
		return waybillDao;
	}

	@Autowired
	public void setWaybillDao(IWaybillDao4dubbo waybillDao) {
		this.waybillDao = waybillDao;
	}

	public IWaybillCommonService getWaybillCommonService() {
		return waybillCommonService;
	}

	@Autowired
	public void setWaybillCommonService(IWaybillCommonService waybillCommonService) {
		this.waybillCommonService = waybillCommonService;
	}

	@Override
	public List<WaybillInfoDto> queryWaybillInfo(List<String> waybillList) {
		if (CollectionUtils.isNotEmpty(waybillList)) {
			WaybillQueryInfoDto waybillQueryInfoDto = new WaybillQueryInfoDto();// 创建对象
			waybillQueryInfoDto.setState(Y);
			waybillQueryInfoDto.setWaybillList(waybillList);
			List<WaybillInfoDto> waybillInfoDtoList = waybillDao.queryWaybillInfo(waybillQueryInfoDto);
			List<WaybillInfoDto> waybillInfoDtoListNew = new ArrayList<WaybillInfoDto>();
			WaybillInfoDto waybillInfoDtoNew = null;
			for (WaybillInfoDto waybillInfoDto : waybillInfoDtoList) {
				waybillInfoDtoNew = new WaybillInfoDto();
				try {
					PropertyUtils.copyProperties(waybillInfoDtoNew, waybillInfoDto);
				} catch (IllegalAccessException e) {
					// 出现异常
					LOGGER.error("error", e);
				} catch (InvocationTargetException e) {
					// 出现异常
					LOGGER.error("error", e);
				} catch (NoSuchMethodException e) {
					// 出现异常
					LOGGER.error("error", e);
				}

				// 小件=------------------- ----
				// 出发网点点部code
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity0 = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getStartExpressOrgCode());
				if (orgAdministrativeInfoEntity0 != null) {
					waybillInfoDtoNew.setStartExpressUnfiedCode(orgAdministrativeInfoEntity0.getUnifiedCode());// 标杆编码

					List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService
							.queryOrgAdministrativeInfoUpByBizType(waybillInfoDtoNew.getStartExpressOrgCode(),
									BizTypeConstants.EXPRESS_BIG_REGION);

					if (list != null && list.size() > 0) {
						OrgAdministrativeInfoEntity entity = list.get(0);
						waybillInfoDtoNew.setDistrictCode(entity.getCode());
						waybillInfoDtoNew.setDistrictName(entity.getName());
						waybillInfoDtoNew.setDistrictUnifiedCode(entity.getUnifiedCode());
					}

				}

				ExpressPartSalesDeptResultDto dtoExp = null;
				SignDto dto = waybillDao.getWaybillSignInfo(waybillInfoDto.getWaybillNo());
				if (dto != null) {
					if (StringUtils.isNotEmpty(dto.getEmpCode())) {

						waybillInfoDtoNew.setEndExpressEmpCode(dto.getEmpCode());
						waybillInfoDtoNew.setEndExpressEmpName(dto.getEmpName());
					} else {

						waybillInfoDtoNew.setEndExpressEmpCode(dto.getCreateUserCode());
						waybillInfoDtoNew.setEndExpressEmpName(dto.getCreateUserName());
					}

					if (StringUtils.isNotEmpty(dto.getOrgCode())) {
						waybillInfoDtoNew.setEndExpressOrgCode(dto.getOrgCode());
						waybillInfoDtoNew.setEndExpressOrgName(dto.getOrgName());
					} else {
						dtoExp = getExpressPartSalesDeptService()
								.queryExpressPartSalesDeptBySalesCodeAndTime(dto.getCreateOrgCode(), new Date());
						if (dtoExp != null) {
							waybillInfoDtoNew.setEndExpressOrgCode(dtoExp.getPartCode());
							waybillInfoDtoNew.setEndExpressOrgName(dtoExp.getPartName());
						}
					}
					if (StringUtils.isNotEmpty(waybillInfoDtoNew.getEndExpressOrgCode())) {

						OrgAdministrativeInfoEntity tmp = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getEndExpressOrgCode());
						if (tmp != null) {
							waybillInfoDtoNew.setEndExpressUnfiedCode(tmp.getUnifiedCode());
						}
					}

					if (StringUtils.isNotEmpty(waybillInfoDtoNew.getEndExpressOrgCode())) {
						List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService
								.queryOrgAdministrativeInfoUpByBizType(waybillInfoDtoNew.getEndExpressOrgCode(),
										BizTypeConstants.EXPRESS_BIG_REGION);

						if (list != null && list.size() > 0) {
							OrgAdministrativeInfoEntity entity = list.get(0);
							waybillInfoDtoNew.setEndDistrictCode(entity.getCode());
							waybillInfoDtoNew.setEndDistrictName(entity.getName());
							waybillInfoDtoNew.setEndDistrictUnifiedCode(entity.getUnifiedCode());
						}
					}

				} else {
					// 在没有签收的情况下 返回快递提货网点的快递点部信息
					String customerPickUpOrgCode = waybillInfoDtoNew.getCustomerPickupOrgCode();
					dtoExp = getExpressPartSalesDeptService()
							.queryExpressPartSalesDeptBySalesCodeAndTime(customerPickUpOrgCode, new Date());

					if (dtoExp != null) {

						waybillInfoDtoNew.setEndExpressOrgCode(dtoExp.getPartCode());
						OrgAdministrativeInfoEntity tmp = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getEndExpressOrgCode());
						if (tmp != null) {
							waybillInfoDtoNew.setEndExpressOrgName(dtoExp.getPartName());
						}

						if (StringUtils.isNotEmpty(dtoExp.getPartCode())) {

							List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService
									.queryOrgAdministrativeInfoUpByBizType(dtoExp.getPartCode(),
											BizTypeConstants.EXPRESS_BIG_REGION);

							if (list != null && list.size() > 0) {
								OrgAdministrativeInfoEntity entity = list.get(0);

								waybillInfoDtoNew.setEndDistrictCode(entity.getCode());
								waybillInfoDtoNew.setEndDistrictName(entity.getName());
								waybillInfoDtoNew.setEndDistrictUnifiedCode(entity.getUnifiedCode());

							}
						}
					}
				}

				// 小件=-------------------11111111

				// 根据编码查询出发部门
				// OrgAdministrativeInfoEntity orgAdministrativeInfoEntity =
				// orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillInfoDtoNew.getCreateOrgCode());
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getReceiveOrgCode());
				if (orgAdministrativeInfoEntity != null) {
					// 出发部门名称
					waybillInfoDtoNew.setDepartureDeptName(orgAdministrativeInfoEntity.getName());
					// 出发部门标杆编码
					waybillInfoDtoNew.setDepartureDeptNumber(orgAdministrativeInfoEntity.getUnifiedCode());
					// 出发部门地址
					waybillInfoDtoNew.setDepartureDeptAddr(orgAdministrativeInfoEntity.getAddress());
					// 出发部门电话
					waybillInfoDtoNew.setDepartureDeptPhone(orgAdministrativeInfoEntity.getDepTelephone());
					// 出发部门传真
					waybillInfoDtoNew.setDepartureDeptFax(orgAdministrativeInfoEntity.getDepFax());
				}

				/**
				 * 根据BUGKD-1616 修复 运单查询明细接口：收货部门对应值取值错误，应该取收入部门（即收货部门）对应的值。
				 */
				// 根据编码查询收货部门
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getReceiveOrgCode());
				if (org != null) {
					// 收货部门名称
					waybillInfoDtoNew.setReceiveOrgName(org.getName());
					// 收货部门标杆编码
					waybillInfoDtoNew.setReceiveOrgNumber(org.getUnifiedCode());

				}

				// 根据编码查询提货网点
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getCustomerPickupOrgCode());
				if (orgAdministrativeInfoEntity1 != null) {
					// 到达网点名称
					waybillInfoDtoNew.setLadingStationName(orgAdministrativeInfoEntity1.getName());
					// 到达网点标杆编码
					waybillInfoDtoNew.setLadingStationNumber(orgAdministrativeInfoEntity1.getUnifiedCode());
					// 到达网点地址
					waybillInfoDtoNew.setLadingStationAddr(orgAdministrativeInfoEntity1.getAddress());
					// 到达网点电话
					waybillInfoDtoNew.setLadingStationPhone(orgAdministrativeInfoEntity1.getDepTelephone());
					// 到达网点传真
					waybillInfoDtoNew.setLadingStationFax(orgAdministrativeInfoEntity1.getDepFax());
				} else {
					OuterBranchEntity outerBranchEntity = new OuterBranchEntity();
					outerBranchEntity.setAgentDeptCode(waybillInfoDtoNew.getCustomerPickupOrgCode());
					outerBranchEntity.setActive(Y);
					List<OuterBranchEntity> outList = commonAgencyDeptDao.queryAgencyDeptsByCondition(outerBranchEntity,
							1, 0);
					// 若outList不为空的话
					if (!CollectionUtils.isEmpty(outList)) {
						OuterBranchEntity outerBranchNew = outList.get(0);
						if (outerBranchNew != null) {
							// 到达网点名称
							waybillInfoDtoNew.setLadingStationName(outerBranchNew.getAgentDeptName());
							// 到达网点标杆编码
							waybillInfoDtoNew.setLadingStationNumber(outerBranchNew.getAgentDeptCode());
							// 到达网点地址
							waybillInfoDtoNew.setLadingStationAddr(outerBranchNew.getAddress());
							// 到达网点电话
							waybillInfoDtoNew.setLadingStationPhone(outerBranchNew.getContactPhone());
						}
					}
				}
				if (waybillInfoDtoNew.getSignSituation() != null) {
					if ("NORMAL_SIGN".equals(waybillInfoDtoNew.getSignSituation())
							&& !"UNNORMAL_ABANDONGOODS".equals(waybillInfoDtoNew.getSignSituation())) {
						// 是否正常签收
						waybillInfoDtoNew.setNormalSigned(true);
					} else {
						// 是否正常签收
						waybillInfoDtoNew.setNormalSigned(false);
					}
					// 是否签收
					waybillInfoDtoNew.setSigned(true);
				} else {
					// 是否签收
					waybillInfoDtoNew.setSigned(false);
				}

				// 拼接得到客户地址 省-市-区县-具体地址

				String deliveryCustomerAddress = waybillCommonService.getCompleteAddress(
						waybillInfoDtoNew.getDeliveryCustomerProvCode(),
						waybillInfoDtoNew.getDeliveryCustomerCityCode(),
						waybillInfoDtoNew.getDeliveryCustomerDistCode(),
						waybillInfoDtoNew.getDeliveryCustomerAddress());
				waybillInfoDtoNew.setDeliveryCustomerAddress(deliveryCustomerAddress);

				// 拼接得到客户地址 省-市-区县-具体地址

				String receiveCustomerAddress = waybillCommonService.getCompleteAddress(
						waybillInfoDtoNew.getReceiveCustomerProvCode(), waybillInfoDtoNew.getReceiveCustomerCityCode(),
						waybillInfoDtoNew.getReceiveCustomerDistCode(), waybillInfoDtoNew.getReceiveCustomerAddress());
				waybillInfoDtoNew.setReceiveCustomerAddress(receiveCustomerAddress);

				WaybillQueryCityProvinceDto provDto = waybillDao
						.queryCityProvince(waybillInfoDtoNew.getCreateOrgCode());

				if (provDto != null) {
					// 发货人城市名称
					waybillInfoDtoNew.setDeliveryCustomerCityName1(provDto.getCityName());
					// 发货人省份名称
					waybillInfoDtoNew.setDeliveryCustomerProvName(provDto.getProvName());
					// 发货人城市名称
					waybillInfoDtoNew.setDeliveryCustomerCityCode1(provDto.getCityCode());
					// 发货人省份名称
					waybillInfoDtoNew.setDeliveryCustomerProvCode(provDto.getProvCode());

					if (StringUtils.isNotEmpty(provDto.getCountyCode())) {
						waybillInfoDtoNew.setDeliveryCustomerDistCode(provDto.getCountyCode());
					}
				}

				WaybillQueryCityProvinceDto provDto2 = waybillDao
						.queryCityProvince(waybillInfoDtoNew.getCustomerPickupOrgCode());

				if (provDto2 != null) {
					// 收货人城市名称
					waybillInfoDtoNew.setReceiveCustomerCityName(provDto2.getCityName());
					// 收货人省份名称
					waybillInfoDtoNew.setReceiveCustomerProvName(provDto2.getProvName());
					// 收货人城市名称
					waybillInfoDtoNew.setReceiveCustomerCityCode(provDto2.getCityCode());
					// 收货人省份名称
					waybillInfoDtoNew.setReceiveCustomerProvCode(provDto2.getProvCode());

					if (StringUtils.isNotEmpty(provDto2.getCountyCode())) {
						waybillInfoDtoNew.setReceiveCustomerDistCode(provDto2.getCountyCode());
					}
				}

				// }

				// 第一次签收时间
				waybillInfoDtoNew
						.setFirstSignTime(waybillDao.queryFirstSignTimeByWaybillNo(waybillInfoDtoNew.getWaybillNo()));
				// 是否上门接货
				if (Y.equals(waybillInfoDtoNew.getPickupToDoor())) {
					waybillInfoDtoNew.setPickup(true);
				} else {
					waybillInfoDtoNew.setPickup(false);
				}
				// 运单状态
				waybillInfoDtoNew.setActive(waybillDao.queryWaybillStatus(waybillInfoDtoNew.getWaybillNo()));

				// 根据编码查询配载部门
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity2 = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getLoadOrgCode());
				if (orgAdministrativeInfoEntity2 != null) {
					waybillInfoDtoNew.setLoadOrgNumber(orgAdministrativeInfoEntity2.getUnifiedCode());
				}

				// 根据编码查询始发站
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity3 = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getDeliveryCustomerCityCode());
				waybillInfoDtoNew.setDeliveryCustomerCityName(orgAdministrativeInfoEntity3.getCityName());

				waybillInfoDtoListNew.add(waybillInfoDtoNew);
			}
			return waybillInfoDtoListNew;
		}
		return null;
	}

	public IExpressPartSalesDeptService getExpressPartSalesDeptService() {
		return expressPartSalesDeptService;
	}

	@Autowired
	public void setExpressPartSalesDeptService(IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}

}
