/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector
 * FILE    NAME: CommonPayeeInfoService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPayeeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBankService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPayeeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 公共查询选择器--付款方信息查询 service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-15 上午11:50:33
 */
public class CommonPayeeInfoService implements ICommonPayeeInfoService {
	
	/** The common payee info dao. */
	private ICommonPayeeInfoDao commonPayeeInfoDao;
	
	/** The bank service. */
	private IBankService bankService;
	
	/** The area address service. */
	private IAreaAddressService areaAddressService;
    

	/**
	 * 查询付款方信息.
	 *
	 * @param payeeInfoEntity the payee info entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-15 上午11:42:32
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPayeeInfoDao#searchPayeeInfoByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity,
	 * int, int)
	 */
	@Override
	public List<PayeeInfoEntity> searchPayeeInfoByCondition(
			PayeeInfoEntity payeeInfoEntity, int start, int limit) {
		List<PayeeInfoEntity> entities =null;
	
		//先判空，如果不为空，判断是否为保理类的账号选择器
		if(null != payeeInfoEntity.getAccountTypes())
		if(payeeInfoEntity.getAccountTypes().contains("1")){
			payeeInfoEntity.getAccountTypes().remove(2);
			payeeInfoEntity.setAccountType("1");
		}
		
		     entities = commonPayeeInfoDao
				.searchPayeeInfoByCondition(payeeInfoEntity, start, limit);
		
		  if (CollectionUtils.isNotEmpty(entities)) {
			BankEntity entity = null;			
			for (int i = 0; i < entities.size(); i++) {
				// 设置银行name
				entity = new BankEntity();
				entity.setCode(entities.get(i).getAccountbankCode());
				if(FossConstants.YES.equals(payeeInfoEntity.getExactQuery())){
					entity.setExactQuery(payeeInfoEntity.getExactQuery());
				}
				List<BankEntity> banks = bankService.queryBanks(entity, 1, 0);
				if (CollectionUtils.isNotEmpty(banks)) {
					entities.get(i).setAccountbankName(banks.get(0).getName());
				}
				// 设置支行name
				entity = new BankEntity();
				if(FossConstants.YES.equals(payeeInfoEntity.getExactQuery())){
					entity.setExactQuery(payeeInfoEntity.getExactQuery());
				}
				entity.setCode(entities.get(i).getAccountbranchbankCode());
				banks = bankService.queryBanks(entity, 1, 0);
				if (CollectionUtils.isNotEmpty(banks)) {
					entities.get(i).setAccountbranchbankName(
							banks.get(0).getName());
				}
				// 设置省名称
				AdministrativeRegionsEntity city = areaAddressService.queryRegionByCode(
						entities.get(i).getAccountbankcityCode());
				if (null!=city) {
					entities.get(i).setAccountbankcityName(city.getName());
				}
				// 设置城市名称
				AdministrativeRegionsEntity pro =  areaAddressService.queryRegionByCode(
						entities.get(i).getAccountbankstateCode());
				if (null!=pro) {
					entities.get(i).setAccountbankstateName(pro.getName());
				}
			}
		  
		
		}
		return entities;
	}

	/**
	 * 查询总条数.
	 *
	 * @param payeeInfoEntity the payee info entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-15 上午11:42:32
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPayeeInfoDao#countPayeeInfoByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity)
	 */
	@Override
	public long countPayeeInfoByCondition(PayeeInfoEntity payeeInfoEntity) {
		return commonPayeeInfoDao.countPayeeInfoByCondition(payeeInfoEntity);
	}

	/**
	 * setter.
	 *
	 * @param commonPayeeInfoDao the new common payee info dao
	 */
	public void setCommonPayeeInfoDao(ICommonPayeeInfoDao commonPayeeInfoDao) {
		this.commonPayeeInfoDao = commonPayeeInfoDao;
	}

	/**
	 * setter.
	 *
	 * @param bankService the new bank service
	 */
	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

	/**
	 * setter.
	 *
	 * @param areaAddressService the new area address service
	 */
	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}

}
