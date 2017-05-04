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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/BaseInfoDownloadService.java
 * 
 * FILE NAME        	: BaseInfoDownloadService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAsteriskSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillingGroupTransFerDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineItemDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupMixDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOutfieldDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesBillingGroupDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDescExpandDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadableEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.baseinfo.server.util.DownloadableComparator;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * baseinfo 下载服务实现类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-22 上午11:43:19
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-22 上午11:43:19
 * @since
 * @version
 */
public class BaseInfoDownloadService implements IBaseInfoDownloadService {

    /**
	 * 
	 */
	private static final int BEFOREAMOUNT = 200;

	/**
	 * 
	 */
	private static final int THOUSAND = 1000;

	/**
     * 
     * 日志
     * 
     */
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(BaseInfoDownloadService.class);
    
	/**
	 * 库区Dao
	 */
	private IGoodsAreaDao goodsAreaDao;

	/**
	 * 走货路径Dao
	 */
	private IFreightRouteDao freightRouteDao;

	/**
	 * 走货路径线路Dao
	 */
	private IFreightRouteLineDao freightRouteLineDao;

	/**
	 * 线路Dao
	 */
	private ILineDao lineDao;
	
	/**
	 * 线路Service
	 */
	@SuppressWarnings("unused")
	private ILineService lineService;

	/**
	 * 线段Dao
	 */
	private ILineItemDao lineItemDao;

	/**
	 * 网点组Dao
	 */
	@SuppressWarnings("unused")
	private INetGroupDao netGroupDao;

	/**
	 * 发车标准Dao
	 */
	private IDepartureStandardDao departureStandardDao;

	/**
	 * 偏线代理网点DAO
	 */
	private IVehicleAgencyDeptDao vehicleAgencyDeptDao;
	/**
	 * 银行DAO
	 */
	private IBankDao bankDao;

	/**
	 * 偏线代理公司DAO
	 */
	private IVehicleAgencyCompanyDao vehicleAgencyCompanyDao;

	/**
	 * 行政组织DAO
	 */
	private IAdministrativeRegionsDao administrativeRegionsDao;

	/**
	 * 限保物品DAO
	 */
	private ILimitedWarrantyItemsDao limitedWarrantyItemsDao;

	/**
	 * 组织信息DAO
	 */
	private IOrgAdministrativeInfoDao orgAdministrativeInfoDao;

	/**
	 * 禁运物品DAO
	 */
	private IProhibitedArticlesDao prohibitedArticlesDao;

	/**
	 * 营业部DAO
	 */
	private ISaleDepartmentDao saleDepartmentDao;
	
	/**
	 * 营业部拓展信息
	 */
	private ISalesDescExpandDao salesDescExpandDao;

	/**
	 * 外场DAO
	 */
	private IOutfieldDao outfieldDao;

	/**
	 * 权限DAO
	 */
	private IResourceDao resourceDao;

	/**
	 * 角色权限DAO
	 */
	private IRoleResourceDao roleResourceDao;

	/**
	 * 角色DAO
	 */
	private IRoleDao roleDao;

	/**
	 * 营业部适用产品DAO
	 */
	private ISalesProductDao salesProductDao;
	/**
	 * 财务组织
	 */
	private IFinancialOrganizationsDao financialOrganizationsDao;

	/**
	 * 用户信息"Service接口
	 */
	private IUserDao userDao;

	/**
	 * 用户组织角色信息"Service接口
	 */
	private IUserOrgRoleDao userOrgRoleDao;
	
	/**
	 * 营业部和集中开单组关系Dao
	 */
	private ISalesBillingGroupDao salesBillingGroupDao;
	
	/**
	 * 集中开单组合外场关系Dao
	 */
	private IBillingGroupTransFerDao billingGroupTransFerDao;
	
	
	/**
	 *加星标营业部Dao接口 
	 */
	private IAsteriskSalesDeptDao asteriskSalesDeptDao;

	/**
	 * 网点组Dao接口
	 */
	private INetGroupMixDao netGroupMixDao;
	
	/**
	 * @param netGroupMixDao the netGroupMixDao to set
	 */
	public void setNetGroupMixDao(INetGroupMixDao netGroupMixDao) {
	    this.netGroupMixDao = netGroupMixDao;
	}

	/**
	 * @return the asteriskSalesDeptDao
	 */
	public IAsteriskSalesDeptDao getAsteriskSalesDeptDao() {
		return asteriskSalesDeptDao;
	}

	/**
	 * @param salesDescExpandDao the salesDescExpandDao to set
	 */
	public void setSalesDescExpandDao(ISalesDescExpandDao salesDescExpandDao) {
		this.salesDescExpandDao = salesDescExpandDao;
	}

	/**
	 * @param billingGroupTransFerDao the billingGroupTransFerDao to set
	 */
	public void setBillingGroupTransFerDao(
		IBillingGroupTransFerDao billingGroupTransFerDao) {
	    this.billingGroupTransFerDao = billingGroupTransFerDao;
	}

	/**
	 * @param asteriskSalesDeptDao the asteriskSalesDeptDao to set
	 */
	public void setAsteriskSalesDeptDao(IAsteriskSalesDeptDao asteriskSalesDeptDao) {
		this.asteriskSalesDeptDao = asteriskSalesDeptDao;
	}


	/**
	 * @return  the salesBillingGroupDao
	 */
	public ISalesBillingGroupDao getSalesBillingGroupDao() {
	    return salesBillingGroupDao;
	}

	
	/**
	 * @param salesBillingGroupDao the salesBillingGroupDao to set
	 */
	public void setSalesBillingGroupDao(ISalesBillingGroupDao salesBillingGroupDao) {
	    this.salesBillingGroupDao = salesBillingGroupDao;
	}

	/**
	 * 
	 * @author foss-zhujunyong
	 * @date Mar 18, 2013 11:36:34 AM
	 * @param lineService
	 * @see
	 */
	public void setLineService(ILineService lineService) {
	    this.lineService = lineService;
	}
	
	/**
	 * 
	 * @date Mar 18, 2013 11:36:41 AM
	 * @param vehicleAgencyCompanyDao
	 * @see
	 */
	public void setVehicleAgencyCompanyDao(IVehicleAgencyCompanyDao vehicleAgencyCompanyDao) {
	    this.vehicleAgencyCompanyDao = vehicleAgencyCompanyDao;
	}

	/**
	 * @param lineItemDao
	 *            the lineItemDao to set
	 */
	public void setLineItemDao(ILineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:40:04 PM
	 * @param salesProductDao
	 * @see
	 */
	public void setSalesProductDao(ISalesProductDao salesProductDao) {
		this.salesProductDao = salesProductDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:40:14 PM
	 * @param financialOrganizationsDao
	 * @see
	 */
	public void setFinancialOrganizationsDao(
			IFinancialOrganizationsDao financialOrganizationsDao) {
		this.financialOrganizationsDao = financialOrganizationsDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:40:21 PM
	 * @param roleDao
	 * @see
	 */
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:40:26 PM
	 * @param roleResourceDao
	 * @see
	 */
	public void setRoleResourceDao(IRoleResourceDao roleResourceDao) {
		this.roleResourceDao = roleResourceDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:40:33 PM
	 * @param resourceDao
	 * @see
	 */
	public void setResourceDao(IResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:41:07 PM
	 * @param outfieldDao
	 * @see
	 */
	public void setOutfieldDao(IOutfieldDao outfieldDao) {
		this.outfieldDao = outfieldDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:41:13 PM
	 * @param saleDepartmentDao
	 * @see
	 */
	public void setSaleDepartmentDao(ISaleDepartmentDao saleDepartmentDao) {
		this.saleDepartmentDao = saleDepartmentDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:41:20 PM
	 * @param prohibitedArticlesDao
	 * @see
	 */
	public void setProhibitedArticlesDao(
			IProhibitedArticlesDao prohibitedArticlesDao) {
		this.prohibitedArticlesDao = prohibitedArticlesDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:41:27 PM
	 * @param orgAdministrativeInfoDao
	 * @see
	 */
	public void setOrgAdministrativeInfoDao(
			IOrgAdministrativeInfoDao orgAdministrativeInfoDao) {
		this.orgAdministrativeInfoDao = orgAdministrativeInfoDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:41:37 PM
	 * @param limitedWarrantyItemsDao
	 * @see
	 */
	public void setLimitedWarrantyItemsDao(
			ILimitedWarrantyItemsDao limitedWarrantyItemsDao) {
		this.limitedWarrantyItemsDao = limitedWarrantyItemsDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:41:42 PM
	 * @param administrativeRegionsDao
	 * @see
	 */
	public void setAdministrativeRegionsDao(
			IAdministrativeRegionsDao administrativeRegionsDao) {
		this.administrativeRegionsDao = administrativeRegionsDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:41:56 PM
	 * @param bankDao
	 * @see
	 */
	public void setBankDao(IBankDao bankDao) {
		this.bankDao = bankDao;
	}

	/**
	 * 
	 * @date Mar 5, 2013 5:42:03 PM
	 * @param vehicleAgencyDeptDao
	 * @see
	 */
	public void setVehicleAgencyDeptDao(
			IVehicleAgencyDeptDao vehicleAgencyDeptDao) {
		this.vehicleAgencyDeptDao = vehicleAgencyDeptDao;
	}

	/**
	 * @param netGroupDao
	 *            the netGroupDao to set
	 */
	public void setNetGroupDao(INetGroupDao netGroupDao) {
		this.netGroupDao = netGroupDao;
	}

	/**
	 * @param lineDao
	 *            the lineDao to set
	 */
	public void setLineDao(ILineDao lineDao) {
		this.lineDao = lineDao;
	}

	/**
	 * @param freightRouteLineDao
	 *            the freightRouteLineDao to set
	 */
	public void setFreightRouteLineDao(IFreightRouteLineDao freightRouteLineDao) {
		this.freightRouteLineDao = freightRouteLineDao;
	}

	/**
	 * @param departureStandardDao
	 *            the departureStandardDao to set
	 */
	public void setDepartureStandardDao(
			IDepartureStandardDao departureStandardDao) {
		this.departureStandardDao = departureStandardDao;
	}

	/**
	 * @param freightRouteDao
	 *            the freightRouteDao to set
	 */
	public void setFreightRouteDao(IFreightRouteDao freightRouteDao) {
		this.freightRouteDao = freightRouteDao;
	}

	/**
	 * @param goodsAreaDao
	 *            the goodsAreaDao to set
	 */
	public void setGoodsAreaDao(IGoodsAreaDao goodsAreaDao) {
		this.goodsAreaDao = goodsAreaDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param userOrgRoleDao
	 *            the userOrgRoleDao to set
	 */
	public void setUserOrgRoleDao(IUserOrgRoleDao userOrgRoleDao) {
		this.userOrgRoleDao = userOrgRoleDao;
	}

	/**
	 * 银行信息数据下载
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadBankData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadBankData(ClientUpdateDataPack clientInfo) {
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		BankEntity entity = new BankEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}

		return db.setObject(bankDao.queryBanksForDownLoad(entity));
	}

	/**
	 * 行政区域数据下载
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadDistrictData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadDistrictData(ClientUpdateDataPack clientInfo) {
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}
		//zxy 20140417 MANA-2018 start 修改:增加分页功能 
		 if(FossConstants.YES.equals(clientInfo.getPagination())){
			 db.setObject(administrativeRegionsDao
					 .queryAdministrativeRegionsForDownloadByPage(entity, (clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND));
		 }else{
			 db.setObject(administrativeRegionsDao
						.queryAdministrativeRegionsForDownload(entity));
		 }

		return db;
		//zxy 20140417 MANA-2018 end 修改:增加分页功能
	}

	/**
	 * 走货路径数据下载
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadFreightRouteData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadFreightRouteData(ClientUpdateDataPack clientInfo) {
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		FreightRouteEntity entity = new FreightRouteEntity();
		if (clientInfo.getLastUpdateTime() != null) {
		    entity.setVersion(clientInfo.getLastUpdateTime().getTime());
		}
		// TODO 按组织进行过滤
//		if (StringUtils.isNotBlank(clientInfo.getOrgCode())) {
//		    List<String> codeList = lineService.queryTransferCodeListBySourceCode(clientInfo.getOrgCode());
//		    entity.setTransferCodeList(codeList);
//		}
		
		if(FossConstants.YES.equals(clientInfo.getPagination())){
			//该表太大 需要分页
			return db.setObject(freightRouteDao.queryFreightRouteForDownloadByPage(entity,
					(clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage()) , THOUSAND));
			
		}else{
			return db.setObject(freightRouteDao.queryFreightRouteForDownload(entity));
		}
		
		
	}

	/**
	 * 走货路径线路数据下载
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadFreightRouteLineData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadFreightRouteLineData(ClientUpdateDataPack clientInfo) {
		// 实例化数据下载类
		DataBundle db = new DataBundle();
		// 判断客户端下载数据的请求包装类是否为空
		if (clientInfo == null) {
			return db;
		}
		// 实例化走货路径线路实体
		Map<String, Object> map = new HashMap<String, Object>();
		if (clientInfo.getLastUpdateTime() != null) {
		    // 设置查询条件
		    map.put("version", clientInfo.getLastUpdateTime().getTime());
		    //zxy 20140507 MANA-2019 start 新增:如果首次下载，则只下载Y数据
		    if(clientInfo.getLastUpdateTime().compareTo(new Date(0)) == 0)
		    	map.put("active", FossConstants.YES);
		    //zxy 20140507 MANA-2019 end 新增:如果首次下载，则只下载Y数据
		}
		// TODO 设置营业部编码作为下载限制条件
//		if (StringUtils.isNotBlank(clientInfo.getOrgCode())) {
//		    map.put("saleOrgCode", clientInfo.getOrgCode());
//		    map.put("lineSort", DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
//		}
		// 返回下载结果
		if(FossConstants.YES.equals(clientInfo.getPagination())){
			//该表太大 需要分页
			return db.setObject(freightRouteLineDao.queryFreightRouteLineForDownloadByPage(map,
					(clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND));
			
		}else{
			return  db.setObject(freightRouteLineDao.queryFreightRouteLineForDownload(map));
		}
		
		
	}

	/**
	 * 限保物品数据下载
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadInsurGoodsData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadInsurGoodsData(ClientUpdateDataPack clientInfo) {
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		LimitedWarrantyItemsEntity entity = new LimitedWarrantyItemsEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			// 设置查询条件
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}
		// 运回限保物品查询结果
		List<LimitedWarrantyItemsEntity> entitys = limitedWarrantyItemsDao
				.queryLimitedWarrantyItemsForDownload(entity);

		for (LimitedWarrantyItemsEntity e : entitys) {
			if (null != e.getLimitedAmount()) {
				e.setLimitedAmount(e.getLimitedAmount().multiply(
						new BigDecimal(NumberConstants.NUMERAL_HUNDRED)));
			}
		}

		return db.setObject(entitys);
	}

	/**
	 * 线路数据下载
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadLineData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadLineData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		// 必须按versionNo下载，此字段为必填
		if (clientInfo == null || clientInfo.getLastUpdateTime() == null) {
		    return db;
		}
		// 实例化 线路实体对象
		LineEntity entity = new LineEntity();
		// 设置查询条件
		entity.setVersion(clientInfo.getLastUpdateTime().getTime());
		//entity.setOrganizationCode(clientInfo.getOrgCode());
		
		
		if(FossConstants.YES.equals(clientInfo.getPagination())){
			//该表太大 需要分页
			return db.setObject(lineDao.queryLineListForDownloadByPage(entity,
					(clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage()) , THOUSAND));
			
		}else{
			return db.setObject(lineDao.queryLineListForDownload(entity));
		}
		
		
		
	}

    /**
     * 
     * <p>
     * 按指定营业部批量下载线路数据
     * </p>
     * 过滤指定营业部的始发线路，全部中转线路和全部到达线路
     * 
     * @author foss-zhujunyong
     * @date Mar 21, 2013 9:29:19 AM
     * @param clientInfoList
     * @return
     * @see
     */
    public DataBundle downloadLineData(List<ClientUpdateDataPack> clientInfoList) {
	    // 实例化 数据下载对象
	    if (CollectionUtils.isEmpty(clientInfoList)) {
		return new DataBundle();
	    }
	    // 该map的key为id，value为List<entity>，用于构造返回结果
	    Map<String, List<? extends DownloadableEntity>> map = new HashMap<String, List<? extends DownloadableEntity>>();
	    //  遍历传入的参数列表
	    for (ClientUpdateDataPack client : clientInfoList) {
		// 如果使用此接口，说明为按营业部下载，所以部门编码不允许为空
		// 必须按versionNo下载，此字段为必填
		if (client == null || StringUtils.isBlank(client.getOrgCode()) || client.getLastUpdateTime() == null) {
		    continue;
		}
		// 构造查询条件
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("version", client.getLastUpdateTime().getTime());
		entity.put("sourceCode", client.getOrgCode());
		entity.put("lineSortSource", DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
		// 查询指定营业部的下载结果
		List<LineEntity> list = lineDao.queryLineListForDownloadViaFilter(entity);
		// 把下载结果和营业部编码放到map中准备后续处理
		map.put(client.getOrgCode(), list);
	    }
	    // 构造返回结果
	    return processDownloadList(map);
    }	
	
	/**
	 * 线路数据下载
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadLineData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadLineItemData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		// 实例化 线段实体对象
		LineItemEntity entity = new LineItemEntity();
		if (clientInfo.getLastUpdateTime() != null) {
			// 设置查询条件
			entity.setVersion(clientInfo.getLastUpdateTime().getTime());
		}
		return db.setObject(lineItemDao.queryLineItemListForDownload(entity));
	}

	/**
	 * 网点组数据下载
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadNetGroupData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadNetGroupData(ClientUpdateDataPack clientInfo) {
	    return new DataBundle();
//		// 实例化 数据下载对象
//		DataBundle db = new DataBundle();
//		// 必须按versionNo下载，此字段为必填
//		if (clientInfo == null || clientInfo.getLastUpdateTime() == null) {
//			return db;
//		}
//		//实例化对象
//		NetGroupEntity entity = new NetGroupEntity();
//		entity.setVersion(clientInfo.getLastUpdateTime().getTime());
//
//		return db.setObject(netGroupDao.queryNetGroupListForDownload(entity));
	}

	
    /**
     * 
     * <p>
     * 按所给的营业部列表下载网点组数据
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 20, 2013 4:43:27 PM
     * @param clientInfoList
     * @return
     * @see
     */
	@Override
	public DataBundle downloadNetGroupData(List<ClientUpdateDataPack> clientInfoList) {
	    return new DataBundle();

//	    // 实例化 数据下载对象
//	    if (CollectionUtils.isEmpty(clientInfoList)) {
//		return new DataBundle();
//	    }
//	    // 该map的key为id，value为List<entity>，用于构造返回结果
//	    Map<String, List<? extends DownloadableEntity>> map = new HashMap<String, List<? extends DownloadableEntity>>();
//	    //  遍历传入的参数列表
//	    for (ClientUpdateDataPack client : clientInfoList) {
//		// 如果使用此接口，说明为按营业部下载，所以部门编码不允许为空
//		// 必须按versionNo下载，此字段为必填
//		if (client == null || StringUtils.isBlank(client.getOrgCode()) || client.getLastUpdateTime() == null) {
//		    continue;
//		}
//		// 构造查询条件
//		NetGroupEntity entity = new NetGroupEntity();
//		entity.setVersion(client.getLastUpdateTime().getTime());
//		entity.setSourceOrganizationCode(client.getOrgCode());
//		// 查询指定营业部的下载结果
//		List<NetGroupEntity> list = netGroupDao.queryNetGroupListForDownload(entity);
//		// 把下载结果和营业部编码放到map中准备后续处理
//		map.put(client.getOrgCode(), list);
//	    }
//	    // 构造返回结果
//	    return processDownloadList(map);
	}
	
	
    public DataBundle downloadNetGroupMixData(List<ClientUpdateDataPack> clientInfoList, String orgType) {
	// 实例化 数据下载对象
	if (CollectionUtils.isEmpty(clientInfoList) || StringUtils.isBlank(orgType)) {
	    return new DataBundle();
	}
	// 该map的key为id，value为List<entity>，用于构造返回结果
	Map<String, List<? extends DownloadableEntity>> map = new HashMap<String, List<? extends DownloadableEntity>>();
	// 遍历传入的参数列表
	for (ClientUpdateDataPack client : clientInfoList) {
	    // 如果使用此接口，说明为按营业部下载，所以部门编码不允许为空
	    // 必须按versionNo下载，此字段为必填
	    if (client == null || StringUtils.isBlank(client.getOrgCode()) || client.getLastUpdateTime() == null) {
		continue;
	    }
	    // 构造查询条件
	    Map<String, Object> c = new HashMap<String, Object>();
	    //zxy 20140507 MANA-2019 start 修改:如果首次下载，则只下载Y数据
	    if (null != client.getLastUpdateTime()) {
			c.put("version", client.getLastUpdateTime().getTime());
			if(client.getLastUpdateTime().compareTo(new Date(0)) == 0)
				c.put("active", FossConstants.YES);
		}
	    //zxy 20140507 MANA-2019 end 修改:如果首次下载，则只下载Y数据
	    c.put("orgType", orgType);
	    // 如果是下载始发网点数据,则需要指定下载营业部编码
	    if (StringUtils.equals(ComnConst.ORG_TYPE_SOURCE, orgType)) {
		c.put("orgCode", client.getOrgCode());
	    }
	    // 查询指定营业部的下载结果
	    List<NetGroupMixEntity> list = netGroupMixDao.queryNetGroupMixListForDownload(c);
	    // 把下载结果和营业部编码放到map中准备后续处理
	    map.put(client.getOrgCode(), list);
	}
	// 构造返回结果
	return processDownloadList(map);
    }
    
    /**
     * 
     * <p>网点组分页下载</p> 
     * @author foss-zhujunyong
     * @date Jun 17, 2013 1:46:56 PM
     * @param clientInfoList
     * @param orgType ComnConst.ORG_TYPE_SOURCE表示下载出发网点数据，ComnConst.ORG_TYPE_TARGET表示下载到达网点数据
     * @return
     * @see
     */
    public DataBundle downloadNetGroupMixData(ClientUpdateDataPack client, String orgType) {
	// 实例化 数据下载对象
	DataBundle db = new DataBundle();
	if (client == null || StringUtils.isBlank(orgType)) {
	    return db;
	}
	Map<String, Object> c = new HashMap<String, Object>();
	c.put("version", client.getLastUpdateTime().getTime());
	c.put("orgType", orgType);
	// 如果是下载始发网点数据,则需要指定下载营业部编码
	if (StringUtils.equals(ComnConst.ORG_TYPE_SOURCE, orgType)) {
	    c.put("orgCode", client.getOrgCode());
	}
	if(FossConstants.YES.equals(client.getPagination())){
	    //该表太大 需要分页
	    return db.setObject(netGroupMixDao.queryNetGroupMixListForDownload(c, (client.getSyncPage()) * THOUSAND - (BEFOREAMOUNT * client.getSyncPage()), THOUSAND));
	}else{
	    return db.setObject(netGroupMixDao.queryNetGroupMixListForDownload(c));
	}   
    }
	
	/**
	 * 
	 * <p>批量下载（一次下载多个营业部）情况处理</p> 
	 * @author foss-zhujunyong
	 * @date Mar 21, 2013 1:25:42 PM
	 * @param map
	 * @param c
	 * @return
	 * @see
	 */
	private DataBundle processDownloadList(Map<String, List<? extends DownloadableEntity>> map){
	    DataBundle db = new DataBundle();
	    // 检查参数
	    if (MapUtils.isEmpty(map)) {
		return db;
	    }
	    
	    List<ClientUpdateDataPack> updateList = new ArrayList<ClientUpdateDataPack>();
	    // 该map用于去重，只要id相等，就认为其对应的entity也相等
	    Map<String, DownloadableEntity> m = new HashMap<String, DownloadableEntity>();
	    for (Map.Entry<String, List<? extends DownloadableEntity>> entry : map.entrySet()) {
		String orgCode = entry.getKey();
		List<? extends DownloadableEntity> list = entry.getValue();
		// 如果该组织对应的entity列表没有数据，则跳过该组织
		if (CollectionUtils.isEmpty(list)) {
		    continue;
		}
		// 取该list的最大的version
		Long version = list.get(list.size() - 1).getVersion();
		// 构造 clientUpdateDataPack 并添加到列表
		ClientUpdateDataPack client = new ClientUpdateDataPack();
		client.setOrgCode(orgCode);
		client.setLastUpdateTime(new Date(version));
		updateList.add(client);
		// 去重
		for (DownloadableEntity entity : list) {
		    if (m.get(entity.getId()) == null) {
			m.put(entity.getId(), entity);
		    }
		}
	    }
	    // 构造返回的DataBundle结果
	    List<DownloadableEntity> dataList = new ArrayList<DownloadableEntity>(m.size());
	    dataList.addAll(m.values());
	    // 排序 
	    Collections.sort(dataList, new DownloadableComparator());
	    db.setObject(dataList);
	    db.setUpdateList(updateList);
	    return db;
	}
	
	
	
	/**
	 * 组织机构数据下载
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadOrganizationData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadOrganizationData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			//zxy 20140507 MANA-2019 start 新增:如果首次下载，则只下载Y数据
			if(clientInfo.getLastUpdateTime().compareTo(new Date(0)) == 0)
				entity.setActive(FossConstants.YES);
			//zxy 20140507 MANA-2019 end 新增:如果首次下载，则只下载Y数据
		}
		
		//entity.setCode(clientInfo.getOrgCode());
		
		
		if(FossConstants.YES.equals(clientInfo.getPagination())){
			//该表太大 需要分页
			return db.setObject(orgAdministrativeInfoDao.
					queryOrgAdministrativeInfoForDownloadByPage(entity,
					(clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage()), THOUSAND));
			
		}else{
			return db.setObject(orgAdministrativeInfoDao
					.queryOrgAdministrativeInfoForDownload(entity));
		}

	}

	/**
	 * 外部网点数据下载
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadOuterBranchData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadOuterBranchData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		OuterBranchEntity entity = new OuterBranchEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}
		//entity.setManagement(clientInfo.getOrgCode());

		if(FossConstants.YES.equals(clientInfo.getPagination())){
			return db.setObject(vehicleAgencyDeptDao
					.queryOuterBranchsForDownloadByPage(entity,
						(clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage()) , THOUSAND	));
			
		}else{
			return db.setObject(vehicleAgencyDeptDao
					.queryOuterBranchsForDownload(entity));
		}
	}

	/**
	 * <p>
	 * 合作伙伴（代理公司）数据下载
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-12-15 下午3:46:17
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadOuterBranchData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadBusinessPartnerData(
			ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		BusinessPartnerEntity entity = new BusinessPartnerEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}
		//entity.setManagement(clientInfo.getOrgCode());

		return db.setObject(vehicleAgencyCompanyDao
				.queryBusinessPartnersForDownload(entity));
	}

	/**
	 * 禁运数据下载
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadProhibitGoodsData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadProhibitGoodsData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		ProhibitedArticlesEntity entity = new ProhibitedArticlesEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}

		return db.setObject(prohibitedArticlesDao
				.queryProhibitedArticlesForDownload(entity));
	}

	/**
	 * 营业部数据下载
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadSalesDepartmentData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadSalesDepartmentData(
			ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		SaleDepartmentEntity entity = new SaleDepartmentEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			//zxy 20140507 MANA-2019 start 新增:如果首次下载，则只下载Y数据
			if(clientInfo.getLastUpdateTime().compareTo(new Date(0)) == 0)
				entity.setActive(FossConstants.YES);
			//zxy 20140507 MANA-2019 end 新增:如果首次下载，则只下载Y数据
		}
		
		List<SaleDepartmentEntity> entitys = null;
		
		if(FossConstants.YES.equals(clientInfo.getPagination())){
			//该表太大 需要分页
			entitys =saleDepartmentDao
					.querySaleDepartmentForDownloadByPage(entity, 
					(clientInfo.getSyncPage())*THOUSAND-(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND);
		}else{
			entitys =saleDepartmentDao
					.querySaleDepartmentForDownload(entity);
		}

		for (SaleDepartmentEntity e : entitys) {
			if(FossConstants.YES.equals(e.getActive())){
				if (null != e.getMaxTempArrears()) {
					e.setMaxTempArrears(e.getMaxTempArrears().multiply(
							new BigDecimal(NumberConstants.NUMERAL_HUNDRED)));
				}
				if (null != e.getUsedTempArrears()) {
					e.setUsedTempArrears(e.getUsedTempArrears().multiply(
							new BigDecimal(NumberConstants.NUMERAL_HUNDRED)));
				}
				
			}
		}

		return db.setObject(entitys);
	}

	/**
	 * 外场数据下载
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadTransferCenterData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadTransferCenterData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		OutfieldEntity entity = new OutfieldEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}
		//entity.setCode(clientInfo.getOrgCode());

		return db.setObject(outfieldDao.queryOutfieldForDownload(entity));
	}

	/**
	 * 库区数据下载
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadGoodsAreaData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadGoodsAreaData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		GoodsAreaEntity entity = new GoodsAreaEntity();
		if (clientInfo.getLastUpdateTime() != null) {
			entity.setVersion(clientInfo.getLastUpdateTime().getTime());
		}
		//entity.setOrganizationCode(clientInfo.getOrgCode());
		if(FossConstants.YES.equals(clientInfo.getPagination())){
			return db.setObject(goodsAreaDao
					.queryGoodsAreaListForDownloadByPage(entity,
							(clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage()) , THOUSAND));
		}else{
			return db.setObject(goodsAreaDao.queryGoodsAreaListForDownload(entity));
		}
	}

	/**
	 * 权限数据下载
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-7 下午8:26:5
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadResourceData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	public DataBundle downloadResourceData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		ResourceEntity entity = new ResourceEntity();
		entity.setModifyDate(clientInfo.getLastUpdateTime());
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}

		return db.setObject(resourceDao.queryResourceForDownload(entity));
	}

	/**
	 * 角色数据下载
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-7 下午8:50:46
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadRoleData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	public DataBundle downloadRoleData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		RoleEntity entity = new RoleEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}

		return db.setObject(roleDao.queryRoleForDownload(entity));
	}

	/**
	 * 角色权限数据下载
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-7 下午8:44:27
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadRoleResourceData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	public DataBundle downloadRoleResourceData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		RoleResourceEntity entity = new RoleResourceEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			//zxy 20140507 MANA-2019 start 新增:如果首次下载，则只下载Y数据
			if(clientInfo.getLastUpdateTime().compareTo(new Date(0)) == 0)
				entity.setActive(FossConstants.YES);
			//zxy 20140507 MANA-2019 end 新增:如果首次下载，则只下载Y数据
		}


		if(FossConstants.YES.equals(clientInfo.getPagination())){
			return db.setObject(roleResourceDao
					.queryRoleResourceForDownloadByPage(entity,
							(clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage()) , THOUSAND));
		}else{
			return db.setObject(roleResourceDao
					.queryRoleResourceForDownload(entity));
		}
	}

	/**
	 * 营业部适用产品 数据下载
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-7 下午8:44:27
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadRoleResourceData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadSalesProductData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		SalesProductEntity entity = new SalesProductEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			//zxy 20140507 MANA-2019 start 新增:如果首次下载，则只下载Y数据
			if(clientInfo.getLastUpdateTime().compareTo(new Date(0)) == 0)
				entity.setActive(FossConstants.YES);
			//zxy 20140507 MANA-2019 end 新增:如果首次下载，则只下载Y数据
		}

		if(FossConstants.YES.equals(clientInfo.getPagination())){
			//该表太大 需要分页
			return db.setObject(salesProductDao
					.querySalesProductForDownloadByPage(entity,  
							(clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage()) , THOUSAND));
		}else{
			return db.setObject(salesProductDao
					.querySalesProductForDownload(entity));
		}
		
	}

	/**
	 * 财务组织 数据下载
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-7 下午8:44:27
	 * @param clientInfo
	 * @return
	 */
	@Override
	public DataBundle downloadFinancialOrganizationsData(
			ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		FinancialOrganizationsEntity entity = new FinancialOrganizationsEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}

		return db.setObject(financialOrganizationsDao
				.queryFinancialOrganizationsForDownload(entity));
	}

	/**
	 * <p>
	 * "用户信息"数据下载
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-7 下午8:01:54
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadUserData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadUserData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		UserEntity entity = new UserEntity();
		if (clientInfo.getLastUpdateTime() != null) {
			entity.setVersion(clientInfo.getLastUpdateTime().getTime());
		}
		entity.setEmpCode(clientInfo.getEmpCode());
		return db.setObject(userDao.queryUserListForDownload(entity));
	}

	/**
	 * <p>
	 * "用户组织角色信息"数据下载
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-7 下午8:02:31
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadUseOrgRoleData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadUseOrgRoleData(ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		UserOrgRoleEntity entity = new UserOrgRoleEntity();
		if (clientInfo.getLastUpdateTime() != null) {
			entity.setVersion(clientInfo.getLastUpdateTime().getTime());
		}
		entity.setEmpCode(clientInfo.getEmpCode());
		entity.setOrgCode(clientInfo.getOrgCode());
		return db.setObject(userOrgRoleDao
				.queryUserOrgRoleListForDownload(entity));
	}

	/**
	 * <p>
	 * "班次信息"数据下载
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Dec 29, 2012 10:17:59 AM
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadDepartureStandardData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadDepartureStandardData(
			ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化发车标准实体
		DepartureStandardEntity entity = new DepartureStandardEntity();
		if (clientInfo.getLastUpdateTime() != null) {
			//设置查询条件
			entity.setVersion(clientInfo.getLastUpdateTime().getTime());
		}
		entity.setModifyDate(clientInfo.getLastUpdateTime());
		
		if(FossConstants.YES.equals(clientInfo.getPagination())){
			//该表太大 需要分页
		
			return db.setObject(departureStandardDao
					.queryDepartureStandardListForDownloadByPage(entity,  
							(clientInfo.getSyncPage())*THOUSAND-(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND));
		}else{
			return db.setObject(departureStandardDao
					.queryDepartureStandardListForDownload(entity));
		}
		
	}

	@Override
	public DataBundle downloadSalesBillingGroupData(ClientUpdateDataPack clientInfo) {
	    DataBundle db = new DataBundle();
	    if (clientInfo == null) {
		return db;
	    }
	    SalesBillingGroupEntity entity = new SalesBillingGroupEntity();
	    if (clientInfo.getLastUpdateTime() != null) {
		entity.setVersion(clientInfo.getLastUpdateTime().getTime());
	    }
	    if (StringUtils.isNotBlank(clientInfo.getOrgCode())) {
		entity.setSalesDeptCode(clientInfo.getOrgCode());
	    }
	    return db.setObject(salesBillingGroupDao.querySalesBillingGroupListForDownload(entity));
	}
	
	
	public DataBundle downloadSalesBillingGroupData(List<ClientUpdateDataPack> clientInfoList) {
	    // 实例化 数据下载对象
	    if (CollectionUtils.isEmpty(clientInfoList)) {
		return new DataBundle();
	    }
	    // 该map的key为id，value为List<entity>，用于构造返回结果
	    Map<String, List<? extends DownloadableEntity>> map = new HashMap<String, List<? extends DownloadableEntity>>();
	    //  遍历传入的参数列表
	    for (ClientUpdateDataPack client : clientInfoList) {
		// 如果使用此接口，说明为按营业部下载，所以部门编码不允许为空
		// 必须按versionNo下载，此字段为必填
		if (client == null || StringUtils.isBlank(client.getOrgCode()) || client.getLastUpdateTime() == null) {
		    continue;
		}
		// 构造查询条件
		SalesBillingGroupEntity entity = new SalesBillingGroupEntity();
		if (client.getLastUpdateTime() != null) {
		    entity.setVersion(client.getLastUpdateTime().getTime());
		}
		if (StringUtils.isNotBlank(client.getOrgCode())) {
		    entity.setSalesDeptCode(client.getOrgCode());
		}
		// 查询指定营业部的下载结果
		List<SalesBillingGroupEntity> list = salesBillingGroupDao.querySalesBillingGroupListForDownload(entity);
		// 把下载结果和营业部编码放到map中准备后续处理
		map.put(client.getOrgCode(), list);
	    }
	    // 构造返回结果
	    return processDownloadList(map);
	}

	
	public DataBundle downloadBillingGroupTransferData(ClientUpdateDataPack clientInfo) {
	    DataBundle db = new DataBundle();
	    if (clientInfo == null) {
		return db;
	    }
	    BillingGroupTransFerEntity entity = new BillingGroupTransFerEntity();
	    if (clientInfo.getLastUpdateTime() != null) {
		entity.setVersion(clientInfo.getLastUpdateTime().getTime());
	    }
	    return db.setObject(billingGroupTransFerDao.querybillingGroupTransferGroupListForDownload(entity));
	}
	

	/**
	 * 
	 * <p>营业部标星号</p> 
	 * @author foss-shixiaowei 
	 * @date May 17, 2013 3:33:33 PM
	 * @param clientInfo
	 * @return
	 * @see
	 */
	public DataBundle downloadAsteriskSalesDept(ClientUpdateDataPack clientInfo) {
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		AsteriskSalesDeptEntity entity = new AsteriskSalesDeptEntity();
		if (clientInfo.getLastUpdateTime() != null) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}
		if (StringUtils.isNotBlank(clientInfo.getOrgCode())) {
			entity.setSalesDeptCode(clientInfo.getOrgCode());
		}
		return db.setObject(asteriskSalesDeptDao.queryAsteriskSalesDepForDownload(entity));
	}
	
	
	/**
	 * 营业部描述数据下载
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-22 上午11:43:19
	 * @param clientInfo
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadSalesDepartmentData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downloadSalesDescExpandData(
			ClientUpdateDataPack clientInfo) {
		// 实例化 数据下载对象
		DataBundle db = new DataBundle();
		if (clientInfo == null) {
			return db;
		}
		//实例化对象
		SalesDescExpandEntity entity = new SalesDescExpandEntity();
		if (null != clientInfo.getLastUpdateTime()) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}
		
		List<SalesDescExpandEntity> entitys = null;
		
		if(FossConstants.YES.equals(clientInfo.getPagination())){
			//该表太大 需要分页
			entitys =salesDescExpandDao
					.querySalesDescExpandForDownloadByPage(entity, 
					(clientInfo.getSyncPage())*THOUSAND-(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND);
		}else{
			entitys =salesDescExpandDao
					.querySalesDescExpandForDownload(entity);
		}

		

		return db.setObject(entitys);
	}

	
	
}
