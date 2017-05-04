/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonPayeeInfoAction.java
 * 
 * FILE NAME        	: CommonPayeeInfoAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action.commonselector
 * FILE    NAME: CommonPayeeInfoAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.ArrayList;
import java.util.List;
import com.deppon.esb.inteface.domain.fins.PartnerAccountInfo;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBankService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPartnerAccountInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPayeeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity;
import com.deppon.foss.util.CollectionUtils;

/**
 * 公共查询选择器--付款方查询ACTION.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-15 上午11:53:25
 */
public class CommonPayeeInfoAction extends AbstractAction implements
		IQueryAction {
	// service
	/** The common payee info service. */
	private ICommonPayeeInfoService commonPayeeInfoService;
	// search condition
	/** The payee info entity. */
	private PayeeInfoEntity payeeInfoEntity;
	
	/** The payee info entities. */
	private List<PayeeInfoEntity> payeeInfoEntities;
	public void setPayeeInfoEntities(List<PayeeInfoEntity> payeeInfoEntities) {
		this.payeeInfoEntities = payeeInfoEntities;
	}

	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

	private IBankService bankService;
	/** serialVersionUID. */
	private static final long serialVersionUID = 1364059337185677858L;
	private IPartnerAccountInfoService partnerAccountInfoService;
	public void setPartnerAccountInfoService(
			IPartnerAccountInfoService partnerAccountInfoService) {
		this.partnerAccountInfoService = partnerAccountInfoService;
	}

	/**
	 * 行政区域Service
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/**
	 * 查询付款方信息.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-15 上午11:53:25
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		 //构造合伙人账户查询条件
		 PartnerAccountInfo partnerEntity = new PartnerAccountInfo();
		 partnerEntity.setBankAccName(getPayeeInfoEntity().getBeneficiaryName());
		 //默认只查询正常状态的账户，销户状态的不查
		 partnerEntity.setAccountStatus("正常");
		 partnerEntity.setEntryPerson(getPayeeInfoEntity().getCreateUser());
		 partnerEntity.setBankAcc(getPayeeInfoEntity().getAccountNo());
			//判断查询的账户性质，如果为空，只不查询合伙人账号，Y 则只查询合伙人账号，N全部查询
		   payeeInfoEntities = new ArrayList<PayeeInfoEntity>();
		 if(getPayeeInfoEntity().getIsOnlyPartnerAccount()==null){
		     payeeInfoEntities = commonPayeeInfoService.searchPayeeInfoByCondition(
		    		 getPayeeInfoEntity(), start, limit);
		     setTotalCount(commonPayeeInfoService.countPayeeInfoByCondition(getPayeeInfoEntity()));
		 }else if("Y".equals(getPayeeInfoEntity().getIsOnlyPartnerAccount())){
			 //查询合伙人账户信息
			 List<PartnerAccountInfo>  paInfos =   partnerAccountInfoService.queryParnerAccountInfoByCondition(partnerEntity, start, limit);
			 //将PartnerAccountInfo 转换为payeeInfoEntity 实体
			 covertPartnerAccountToPayeeInfo(payeeInfoEntities,paInfos);
			 long count = partnerAccountInfoService.countPartnerPayeeInfoByCondition(partnerEntity);
			 setTotalCount(count);
		 }else if("N".equals(getPayeeInfoEntity().getIsOnlyPartnerAccount())){
			 //既查询自有账户，也查询合伙人账户
			 payeeInfoEntities = commonPayeeInfoService.searchPayeeInfoByCondition(
					 getPayeeInfoEntity(), start, limit);
			 //查询合伙人账户信息
			 List<PartnerAccountInfo>  paInfos =   partnerAccountInfoService.queryParnerAccountInfoByCondition(partnerEntity, start, limit);
			 //将PartnerAccountInfo 转换为payeeInfoEntity 实体
			 covertPartnerAccountToPayeeInfo(payeeInfoEntities,paInfos);
			 //统计总查询条数
			 long a = partnerAccountInfoService.countPartnerPayeeInfoByCondition(partnerEntity);
			 long b = commonPayeeInfoService.countPayeeInfoByCondition(getPayeeInfoEntity());
			 setTotalCount(a+b);
			 
		 }
		return returnSuccess();
	}
 /**
  * //将PartnerAccountInfo 转换为payeeInfoEntity 实体
  * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
  * @author 268984 
  * @date 2016-3-15 上午10:17:18
  * @param payeeInfoEntities2
  * @param paInfos
  * @see
  */
	private void covertPartnerAccountToPayeeInfo(
			List<PayeeInfoEntity> payeeInfoEntities,
			List<PartnerAccountInfo> paInfos) {
		 for (int i = 0; i < paInfos.size(); i++) {
			 PayeeInfoEntity pe = new PayeeInfoEntity();
			 PartnerAccountInfo pa = paInfos.get(i);
			 pe.setAccountbankName(pa.getBankName());//开户行
			 pe.setAccountbankcityName(pa.getAcccity());//开户行城市
			 if(StringUtil.isNotEmpty(pa.getAcccity())){
				//封装城市及对应省份编码
				transformAdminRegion(pe,pa.getAcccity(),"CITY");
			 }
			 pe.setAccountbankstateName(pa.getAccprovice());//开户行省份
             pe.setAccountNo(pa.getBankAcc());
             pe.setBeneficiaryName(pa.getBankAccName());
             //账户类型默认6,PBP-1663(1668):财务自助传到FOSS的合伙人银行卡，卡号性质外部对公改为外部对私
             pe.setAccountType(NumberConstants.NUMERAL_S_SIX);
             pe.setAccountbranchbankName(pa.getFbranchBankName());
             pe.setAccountbranchbankCode(pa.getFbranchBankCode());
             pe.setPayeeNo(pa.getBankAcc());
             pe.setOperatorId(pa.getEntryPerson());
             //根据银行名称获取银行编号
             BankEntity bank =  bankService.queryBankInfoByName(pa.getBankName());
             pe.setAccountbankCode(bank!=null?bank.getCode():null);
             payeeInfoEntities.add(pe);
		}
	}

	/**
	 * 
	 * <p>私有方法根据行政区域名称封装编码</p> 
	 * @author 187862 
	 * @date 2016-4-2 上午10:25:33
	 * @param regionName
	 * @param degree
	 * @return
	 * @see
	 */
	private PayeeInfoEntity transformAdminRegion(PayeeInfoEntity pe,String regionName,String degree){
		//行政区域查询实体
		AdministrativeRegionsEntity regionEntity=new AdministrativeRegionsEntity();
		//封装行政区域查询条件
		regionEntity.setDegree(degree);
		regionEntity.setName(regionName);
		//查询行政区域
		List<AdministrativeRegionsEntity> regionList=administrativeRegionsService.
				queryAdministrativeRegionsExactByEntity(regionEntity, 0, Integer.MAX_VALUE);
		if(CollectionUtils.isNotEmpty(regionList)){
			pe.setAccountbankcityCode(regionList.get(0).getCode());
			pe.setAccountbankstateCode(regionList.get(0).getParentDistrictCode());
		}
		return pe;
	}
	/**
	 * getter.
	 *
	 * @return the payee info entity
	 */
	public PayeeInfoEntity getPayeeInfoEntity() {
		if(null == payeeInfoEntity){
			payeeInfoEntity = new PayeeInfoEntity ();
		}
		return payeeInfoEntity;
	}

	/**
	 * setter.
	 *
	 * @param payeeInfoEntity the new payee info entity
	 */
	public void setPayeeInfoEntity(PayeeInfoEntity payeeInfoEntity) {
		this.payeeInfoEntity = payeeInfoEntity;
	}

	/**
	 * getter.
	 *
	 * @return the payee info entities
	 */
	public List<PayeeInfoEntity> getPayeeInfoEntities() {
		return payeeInfoEntities;
	}

	/**
	 * setter.
	 *
	 * @param commonPayeeInfoService the new common payee info service
	 */
	public void setCommonPayeeInfoService(
			ICommonPayeeInfoService commonPayeeInfoService) {
		this.commonPayeeInfoService = commonPayeeInfoService;
	}

}
