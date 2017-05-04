/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/BusinessUtils.java
 * 
 * FILE NAME        	: BusinessUtils.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-common
 * PACKAGE NAME: com.deppon.foss.module.pickup.common.client.utils
 * FILE    NAME: BusinessUtils.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.common.client.utils;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.common.client.service.IAddressService;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.AddressService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisDepartmentDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IAddressServiceHessianRemoting;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Injector;

/**
 * 业务工具类：提供公共的方法供各模块使用
 * @author 026123-foss-lifengteng
 * @date 2013-1-25 下午6:43:17
 */
public class BusinessUtils {
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(BusinessUtils.class);
	
	/**
	 * 运单基础数据服务
	 */
	private IBaseDataService baseDataService;
	
	/**
	 * 地址栏Service
	 */
	private IAddressService addressService;
	
	/**
	 * 在线查询 地址栏服务    zxy 20140331 MANA-2018
	 */
	IAddressServiceHessianRemoting addressServiceRemote = null;

	/**
	 * 构造方法：初始化相关数据
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-25 下午6:43:17
	 */
	public BusinessUtils() {
		Injector injector = GuiceContextFactroy.getInjector();
		baseDataService = injector.getInstance(BaseDataService.class);
		addressService = injector.getInstance(AddressService.class);
		addressServiceRemote = DefaultRemoteServiceFactory.getService(IAddressServiceHessianRemoting.class);
	}
	
	/**
	 * 获取提货网点信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-24 下午3:34:24
	 */
	public BranchVo getCustomerPickupOrg(String customerPickupOrgCode,String productCode,Date billTime){
		if(customerPickupOrgCode!=null&&productCode!=null){
    		//根据部门编码查询部门简称
    		String simpleName = getSimpleName(customerPickupOrgCode,billTime);
    		if("".equals(simpleName)){
    			simpleName = i18n.get("foss.gui.common.businessUtils.simpleName.label");
    		}
    		    		
    		//根据产品来判断是查询自有网点还是代理网点
    		if(!CommonUtils.isAgentDept(productCode)){
    			return getPickupOrg(customerPickupOrgCode,simpleName,billTime);
    		}else{
    			return getOuterBranch(customerPickupOrgCode,billTime);
    		}
		}else{
			return null;
		}
	}
	
	/**
	 * 根据部门dto获取提货网点相关信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-29 上午9:29:12
	 */
	public BranchVo getCustomerPickupOrg(GisDepartmentDto dto){
		//部门编码
		String deptNo = StringUtil.defaultIfNull(dto.getDeptNo());
		//是否偏线外发
		Boolean isAgent = (dto.getIsAgentCollected() == null ? true : dto.getIsAgentCollected());
		
		return getCustomerPickupOrg(deptNo,isAgent,null);
	}
	
	/**
	 * 根据部门编码和是否外发来获取提货网点相关信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-29 上午9:29:12
	 */
	public BranchVo getCustomerPickupOrg(String deptNo,Boolean isAgent,Date billTime){
		//是否偏线外发
		boolean agent = (isAgent == null ? true : isAgent.booleanValue());
		//部门简称
		String simpleName = getSimpleName(deptNo,billTime);
		if("".equals(simpleName)){
			simpleName = i18n.get("foss.gui.common.businessUtils.simpleName.label");
		}
		
		//判断是查询自有网点还是外发网点
		if(agent){
			return getOuterBranch(deptNo,billTime);
		}else{
			return getPickupOrg(deptNo,simpleName,billTime);
		}
	}
	
	/**
	 * 获得提货网点信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-14 上午11:02:52
	 */
	public BranchVo getPickupOrg(String code,String targetOrgName,Date billTime){
		SaleDepartmentEntity selectedSaleDepartmentInfo = baseDataService.querySaleDepartmentByCode(code,billTime);
		if(null == selectedSaleDepartmentInfo){
			return null;
		}
		BranchVo branchVO = new BranchVo();

		branchVO.setCode(selectedSaleDepartmentInfo.getCode());
		branchVO.setName(selectedSaleDepartmentInfo.getName());
		branchVO.setSingleBillLimitkg(selectedSaleDepartmentInfo.getSingleBillLimitkg());
		branchVO.setSingleBillLimitvol(selectedSaleDepartmentInfo.getSingleBillLimitvol());
		branchVO.setSinglePieceLimitkg(selectedSaleDepartmentInfo.getSinglePieceLimitkg());
		branchVO.setSinglePieceLimitvol(selectedSaleDepartmentInfo.getSinglePieceLimitvol());
		branchVO.setCanAgentCollected(selectedSaleDepartmentInfo.getCanAgentCollected());
		//是否可自提
		branchVO.setPickupSelf(selectedSaleDepartmentInfo.getPickupSelf());
		//是否送货上门
		branchVO.setDelivery(selectedSaleDepartmentInfo.getDelivery());
		//所属城市被临时存放在PICKUP_AREA_DESC列当中传来出来
		branchVO.setCityCode(selectedSaleDepartmentInfo.getPickupAreaDesc());
		//取消到达日期
		branchVO.setCancelArrivalDate(selectedSaleDepartmentInfo.getCancelArrivalDate());
		//转货部门
		branchVO.setTransferGoodDept(selectedSaleDepartmentInfo.getTransferGoodDept());
		//目的站
		branchVO.setTargetOrgName(targetOrgName);
		//是否支持货到付款
		branchVO.setArriveCharge(selectedSaleDepartmentInfo.getCanCashOnDelivery());
		return branchVO;
	}
	
	/**
	 * 查询外发网点信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-24 下午3:16:32
	 */
	public BranchVo getOuterBranch(String code,Date billTime) {
		OuterBranchEntity selectedOuterBranchEntity = baseDataService.queryOuterBranchByCode(code,billTime);
		if (null == selectedOuterBranchEntity) {
			return null;
		}

		BranchVo branchVO = new BranchVo();
		//代理编号
		branchVO.setCode(selectedOuterBranchEntity.getAgentDeptCode());
		//代理名称
		branchVO.setName(selectedOuterBranchEntity.getAgentDeptName());
		//目的站
		branchVO.setTargetOrgName(selectedOuterBranchEntity.getSimplename());
		//是否代收货款
		branchVO.setCanAgentCollected(selectedOuterBranchEntity.getHelpCharge());
		//是否可自提
		branchVO.setPickupSelf(selectedOuterBranchEntity.getPickupSelf());
		//是否送货上门
		branchVO.setDelivery(selectedOuterBranchEntity.getPickupToDoor());
		//所属城市code
		branchVO.setCityCode(selectedOuterBranchEntity.getCityCode());
		//是否支持货到付款
		branchVO.setArriveCharge(selectedOuterBranchEntity.getArriveCharge());
		return branchVO;
	}
	
	/**
	 * 查询外发网点信息
	 * @param code 部门编码
	 * @param billTime
	 * @return OuterBranchEntity
	 * 版本		用户			时间			内容
	 * 0001		zxy			20130913	新增外网机构查询,返回OuterBranchEntity实体
	 */
	public OuterBranchEntity getOuterBranchEntity(String code,Date billTime) {
		OuterBranchEntity selectedOuterBranchEntity = baseDataService.queryOuterBranchByCode(code,billTime);
		if (null == selectedOuterBranchEntity) {
			return null;
		}

		return selectedOuterBranchEntity;
	}
	
	/**
	 * 根据部门编码获取
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-26 下午12:37:56
	 */
	public String getSimpleName(String deptCode,Date billTime){
		//根据部门编码查询部门简称
		OrgAdministrativeInfoEntity org = baseDataService.queryOrgAdministrativeInfoEntityByCode(deptCode,billTime); 
		String simpleName = "";
		if(null != org){
			simpleName = StringUtil.defaultIfNull(org.getOrgSimpleName());
		}
		return simpleName;
	}
	
	/**
	 * 填充国家、省份、城市、区县信息
	 * @author 354805-taodongguo
	 * @date 2016-10-21 15:54:41
	 * @param dto
	 * @return
	 */
	public AddressFieldDto getDistrict(AddressFieldDto dto){
		//add by taodongguo 354805
		if (null == dto) {
			return null;
		}
		AddressFieldDto addressFieldDto = new AddressFieldDto();
		if(StringUtils.isNotEmpty(dto.getNationId())){
			//填充国家信息
			addressFieldDto.setNationId(dto.getNationId());
			addressFieldDto.setNationName(dto.getNationName());
		}
		if(StringUtils.isNotEmpty(dto.getProvinceId())){
			//填充省份信息
			addressFieldDto.setProvinceId(dto.getProvinceId());
			addressFieldDto.setProvinceName(dto.getProvinceName());
		}
		if(StringUtils.isNotEmpty(dto.getCityId())){
			//填充城市信息
			addressFieldDto.setCityId(dto.getCityId());
			addressFieldDto.setCityName(dto.getCityName());
		}
		if(StringUtils.isNotEmpty(dto.getCountyId())){
			//填充区县信息
			addressFieldDto.setCountyId(dto.getCountyId());
			addressFieldDto.setCountyName(dto.getCountyName());
		}
		return addressFieldDto;
	}
	

	/**
	 * 国家省市区
	 * @update author 354805-taodongguo
	 * @update date 2016-10-19 14:35:13
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-23 上午9:19:49
	 */
	public AddressFieldDto getProvCityCounty(String provCode, String cityCode,String distCode) {
		AddressFieldDto addressFieldDto = new AddressFieldDto();
		if (StringUtil.isNotEmpty(provCode)) {
			//填充省份名称
			addressFieldDto.setProvinceId(provCode);
			//zxy 20140331 MANA-2018 start 修改:在线时查在线，离线时查本地
			String province = null;
			if(WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())){
				province = addressServiceRemote.queryProviceByCode(provCode);
			}else
				province = addressService.queryProviceByCode(provCode);
			//zxy 20140331 MANA-2018 end 修改:在线时查在线，离线时查本地
			if (StringUtil.isNotEmpty(province)) {
				addressFieldDto.setProvinceName(province);
			}
			//填充国家信息 taodongguo - 2016-10-19 14:35:30
			AddressFieldDto addr = addressServiceRemote.queryNationByProvinceCode(provCode);
			if(null != addr){
				addressFieldDto.setNationId(addr.getNationId());
				addressFieldDto.setNationName(addr.getNationName());
			}
		}
		if (StringUtil.isNotEmpty(cityCode)) {
			//填充城市名称
			addressFieldDto.setCityId(cityCode);
			//zxy 20140331 MANA-2018 start 修改:在线时查在线，离线时查本地
			String city = null;
			if(WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())){
				city = addressServiceRemote.queryCityByCode(cityCode);
			}else
				city = addressService.queryCityByCode(cityCode);
			//zxy 20140331 MANA-2018 end 修改:在线时查在线，离线时查本地
			if (StringUtil.isNotEmpty(city)) {
				addressFieldDto.setCityName(city);
			}
		}
		if (StringUtil.isNotEmpty(distCode)) {
			//填充区域名称
			addressFieldDto.setCountyId(distCode);
			//zxy 20140331 MANA-2018 start 修改:在线时查在线，离线时查本地
			String county = null;
			if(WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())){
				county = addressServiceRemote.queryCountyByCode(distCode);
			}else
				county = addressService.queryCountyByCode(distCode);
			//zxy 20140331 MANA-2018 end 修改:在线时查在线，离线时查本地
			if (StringUtil.isNotEmpty(county)) {
				addressFieldDto.setCountyName(county);
			}
		}
		return addressFieldDto;
	}
	

	/**
	 * 新增 用于快递收货人 选择乡镇(街道)
	 * @author 218459-foss-dongsiwei
	 */
	public AddressFieldDto getProvCityCountyVillage(String provCode, String cityCode,String distCode,String villageCode) {
		AddressFieldDto addressFieldDto = new AddressFieldDto();
		if (StringUtil.isNotEmpty(provCode)) {
			//填充省份名称
			addressFieldDto.setProvinceId(provCode);
			//zxy 20140331 MANA-2018 start 修改:在线时查在线，离线时查本地
			String province = null;
			if(WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())){
				province = addressServiceRemote.queryProviceByCode(provCode);
			}else
				province = addressService.queryProviceByCode(provCode);
			//zxy 20140331 MANA-2018 end 修改:在线时查在线，离线时查本地
			if (StringUtil.isNotEmpty(province)) {
				addressFieldDto.setProvinceName(province);
			}
		}
		if (StringUtil.isNotEmpty(cityCode)) {
			//填充城市名称
			addressFieldDto.setCityId(cityCode);
			//zxy 20140331 MANA-2018 start 修改:在线时查在线，离线时查本地
			String city = null;
			if(WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())){
				city = addressServiceRemote.queryCityByCode(cityCode);
			}else
				city = addressService.queryCityByCode(cityCode);
			//zxy 20140331 MANA-2018 end 修改:在线时查在线，离线时查本地
			if (StringUtil.isNotEmpty(city)) {
				addressFieldDto.setCityName(city);
			}
		}
		if (StringUtil.isNotEmpty(distCode)) {
			//填充区域名称
			addressFieldDto.setCountyId(distCode);
			//zxy 20140331 MANA-2018 start 修改:在线时查在线，离线时查本地
			String county = null;
			if(WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())){
				county = addressServiceRemote.queryCountyByCode(distCode);
			}else
				county = addressService.queryCountyByCode(distCode);
			//zxy 20140331 MANA-2018 end 修改:在线时查在线，离线时查本地
			if (StringUtil.isNotEmpty(county)) {
				addressFieldDto.setCountyName(county);
			}
		}
		if (StringUtil.isNotEmpty(villageCode)) {
			//填充区域名称
			addressFieldDto.setVillageTownId(villageCode);
			//zxy 20140331 MANA-2018 start 修改:在线时查在线，离线时查本地
			String village = null;
			if(WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())){
				village = addressServiceRemote.queryCountyByCode(villageCode);
			}else
				village = addressService.queryCountyByCode(villageCode);
			//zxy 20140331 MANA-2018 end 修改:在线时查在线，离线时查本地
			if (StringUtil.isNotEmpty(village)) {
				addressFieldDto.setVillageTownName(village);
			}
		}
		
		return addressFieldDto;
	}
	
	/**
	 * 将Dto转换为字符串对象
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-23 下午3:27:57
	 */
	public String getAddressAreaText(AddressFieldDto addressFieldDto){
		//判断对像是否为空，若对象为空
		if(addressFieldDto == null){
			return "";
		}
		//定义字符对象
		StringBuffer sb = new StringBuffer();
		//国家编码不为空
		if(StringUtil.isNotEmpty(addressFieldDto.getNationName()) && !WaybillConstants.CHINA.equals(addressFieldDto.getNationName())){
			sb.append(addressFieldDto.getNationName());
			sb.append("-");
		}
		//省份编码不为空
		if(StringUtil.isNotEmpty(addressFieldDto.getProvinceName())){
			sb.append(addressFieldDto.getProvinceName());
			sb.append("-");
		}
		//城市编码不为空
		if(StringUtil.isNotEmpty(addressFieldDto.getCityName())){
			sb.append(addressFieldDto.getCityName());
			sb.append("-");
		}
		//区县编码不为空
		if(StringUtil.isNotEmpty(addressFieldDto.getCountyName())){
			sb.append(addressFieldDto.getCountyName());
			sb.append("-");
		}
		//乡镇(街道)编码不为空
		if(StringUtil.isNotEmpty(addressFieldDto.getVillageTownName())){
			sb.append(addressFieldDto.getVillageTownName());
			sb.append("-");
		}
		//判断长度是否为0
		if(sb.length()>0){
			return sb.substring(0, sb.length() - 1);
		}else{
			return sb.toString();
		}
	}
	
	/**
	 * 根据组织编码查询是否是集中接送货开单组
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-1 下午5:05:41
	 */
	public boolean isBillGroup(String orgCode){
		//组织编码
		String code = StringUtil.defaultIfNull(orgCode);
		//获得组织对象
		OrgAdministrativeInfoEntity org = baseDataService.queryOrgAdministrativeInfoEntityByCode(code);
		//判断对象是否为空
		if(null != org){
			//判断是否是开单组
			if(FossConstants.YES.equals(org.getBillingGroup())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 查询各种数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-5-23 14:48:24
	 */
	public String getSimpleOrgName(String deptCode, String productCode, Date billTime){		
		if(StringUtils.isNotEmpty(productCode)){
			String simpleName = null;
			if(WaybillConstants.AIR_FLIGHT.equals(productCode) || WaybillConstants.HIGHWAYS_REFERRALS.equals(productCode)){
				OuterBranchEntity selectedOuterBranchEntity = baseDataService.queryOuterBranchByCode(deptCode,billTime);
				if(selectedOuterBranchEntity == null){
					selectedOuterBranchEntity = baseDataService.queryOuterBranchByCode(deptCode,new Date());
				}
				if(selectedOuterBranchEntity != null){
					simpleName = StringUtil.defaultIfNull(selectedOuterBranchEntity.getAgentDeptName());
				}
			}else{
				//根据部门编码查询部门简称
				OrgAdministrativeInfoEntity org = baseDataService.queryOrgAdministrativeInfoEntityByCode(deptCode,billTime);
				if(org == null){
					org = baseDataService.queryOrgAdministrativeInfoEntityByCode(deptCode,new Date()); 
				}
				if(null != org){
					simpleName = StringUtil.defaultIfNull(org.getOrgSimpleName());
				}
			}
			return simpleName;
		}
		return null;		
	}
}