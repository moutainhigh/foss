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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/WoodenRequirementsService.java
 * 
 * FILE NAME        	: WoodenRequirementsService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.service.impl
 * FILE    NAME: WoodenRequirementsService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWoodenRequirementsService;
import com.deppon.foss.module.pickup.waybill.server.utils.StringHandlerUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackOutService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 代打木架服务
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-5 下午1:47:22
 */
public class WoodenRequirementsService implements IWoodenRequirementsService {

	// 打木架DAO
	private IWoodenRequirementsDao woodenRequirementsDao;

	// 包装服务
	private IPackOutService packOutService;
	
	/**
	 * 组织服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 国际化信息
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-30 上午8:59:57
	 */
	private IMessageBundle messageBundle; 
	
	/**
	 * @param messageBundle the
	 *            messageBundle to set
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-30 上午8:59:57
	 */
	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}
	/**
	 * 
	 * 打木架DAO的set方法
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-24 下午10:15:18
	 */
	public void setWoodenRequirementsDao(IWoodenRequirementsDao woodenRequirementsDao) {
		this.woodenRequirementsDao = woodenRequirementsDao;
	}

	/**
	 * 包装服务的set方法
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-24 下午10:16:24
	 */
	public void setPackOutService(IPackOutService packOutService) {
		this.packOutService = packOutService;
	}
	
	
	/**
	 *@param orgAdministrativeInfoService the orgAdministrativeInfoService to set.
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 新增代打木架信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午1:47:22
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWoodenRequirements#addWoodenRequirements(com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity)
	 */
	@Override
	public void addWoodenRequirements(WoodenRequirementsEntity wooden, WaybillSystemDto systemDto) {
		if (wooden != null) {
			// 设置创建时间
			wooden.setCreateTime(systemDto.getCreateTime());
			//设置修改时间
			wooden.setModifyTime(systemDto.getModifyTime());
			// 设置是否有效
			wooden.setActive(FossConstants.ACTIVE);
			//新增打木架信息
			woodenRequirementsDao.addWoodenRequirementsEntity(wooden);
		}

	}

	/**
	 * 新增需要包装运单信息到包装需求表中
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-6 上午10:09:33
	 */
	@Override
	public void addPackagingRequire(WaybillDto waybillDto) {
		try {
			//判空操作
			/**
			 * 修改了此语句，防止空指针异常
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-12-7下午14:24
			 */
			if (waybillDto.getWoodenRequirementsEntity() != null &&
				((waybillDto.getWoodenRequirementsEntity().getStandGoodsNum()!=null)
				||(waybillDto.getWoodenRequirementsEntity().getBoxGoodsNum()!=null)
				||(waybillDto.getWoodenRequirementsEntity().getSalverGoodsNum()!=null)
					)) {
				CurrentInfo currentInfo = waybillDto.getCurrentInfo();
				//用户信息不允许为空
				if (currentInfo == null) {
					throw new WaybillSubmitException(WaybillSubmitException.CURRENT_INFO_NULL);
				}
				//运单基本信息
				WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
				//打木架信息
				WoodenRequirementsEntity wooden = waybillDto.getWoodenRequirementsEntity();

				// 将对象进行封装
				PackagingRequireEntity packagingRequire = new PackagingRequireEntity();
				//设置运单号
				packagingRequire.setWaybillNo(waybillEntity.getWaybillNo());
				//设置开单部门code
				packagingRequire.setWaybillCreateDeptCode(waybillEntity.getReceiveOrgCode());
				//设置开单部门名称
				packagingRequire.setWaybillCreateDept(getDeptNameByCode(waybillEntity.getReceiveOrgCode()));
				//开单件数
				packagingRequire.setWaybillNumber(waybillEntity.getGoodsQtyTotal());
				//开单体积
				packagingRequire.setWaybillVolume(waybillEntity.getGoodsVolumeTotal());
				// 创建日期
				packagingRequire.setCreateDate(waybillEntity.getBillTime());
				//运输性质
				packagingRequire.setProductType(waybillEntity.getProductCode());
				//货物名称
				packagingRequire.setGoodsName(waybillEntity.getGoodsName());
				//开单时间
				packagingRequire.setWaybillCreateDate(waybillDto.getWaybillEntity().getCreateTime());
				//代包装部门Code
				packagingRequire.setPackagingDeptCode(wooden.getPackageOrgCode());

				// 需要包装的总体积
				/**
				 * 修改了此语句，防止空指针异常
				 * @author:218371-foss-zhaoyanjun
				 * @date:2014-12-7下午14:24
				 */
				BigDecimal volume = BigDecimal.valueOf(0).add(wooden.getBoxGoodsVolume()==null?BigDecimal.ZERO:wooden.getBoxGoodsVolume()).add(wooden.getStandGoodsVolume()==null?BigDecimal.ZERO:wooden.getStandGoodsVolume());
				packagingRequire.setNeedPackVolume(volume);
				// 需要包装的总件数
				//zxy 20131118 ISSUE-4391 start 新增：计算包装总件数，即打木架又打木托算一件
				/**
				 * 修改了此语句，防止空指针异常
				 * @author:218371-foss-zhaoyanjun
				 * @date:2014-12-7下午14:24
				 */
				int pieces = (wooden.getBoxGoodsNum()==null?0:wooden.getBoxGoodsNum()) + (wooden.getStandGoodsNum()==null?0:wooden.getStandGoodsNum());
				int salverPieces = 0;
				List<String> salverSerialNo = null;
				List<LabeledGoodEntity> labeledGoodEntitys = waybillDto.getLabeledGoodEntities();
				if(labeledGoodEntitys != null && labeledGoodEntitys.size() > 0){
					salverSerialNo = new ArrayList<String>();
					for(int i = 0; i < labeledGoodEntitys.size(); i++){
						LabeledGoodEntity labeledGoodEntity = labeledGoodEntitys.get(i);
						if(WaybillConstants.PACKAGE_TYPE_SALVER.equals(labeledGoodEntity.getPackageType())){
							if(i + 1 > pieces){		//因为流水号列表是按顺序排的001-00n，所以可以用i
								salverPieces ++;
							}
							salverSerialNo.add(labeledGoodEntity.getSerialNo());
						}
					}
				}
				int allPieces = pieces + salverPieces;
				//zxy 20131118 ISSUE-4391 end 新增：计算包装总件数，即打木架又打木托算一件
				packagingRequire.setNeedPackNum(allPieces);
				// 代包装要求
				String requirements = "".concat(wooden.getBoxRequirement()==null?"":wooden.getBoxRequirement())
						.concat(wooden.getStandRequirement()==null?"":wooden.getStandRequirement())
						.concat(wooden.getSalverRequirement()==null?"":wooden.getSalverRequirement());
				packagingRequire.setPackagingRequire(requirements);
				packagingRequire.setPackagingDept(wooden.getPackageOrgCode());

				// 需要打包装流水号
				List<String> serialNo = new ArrayList<String>();
				for (int i = 1; i <= pieces; i++) {
					String s = StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, "0");
					serialNo.add(s);
				}
				packagingRequire.setAddWSerialNo(serialNo);
				packagingRequire.setAddMSerialNo(salverSerialNo);
				packOutService.addPackagingRequire(packagingRequire);
			}
		} catch (BusinessException e) {
			throw new WaybillSubmitException(WaybillSubmitException.ADD_PACKAGING_REQUIRE_FAIL,new Object[]{messageBundle.getMessage(e.getMessage())});
		}
	}
	
	/**
	 * 根据部门编码查询部门名称
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 下午5:22:53
	 */
	private String getDeptNameByCode(String code) {
		//获得组织对象
		OrgAdministrativeInfoEntity e = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(StringUtil.defaultIfNull(code));
		//对象非空判断
		if (e != null) {
			//返回部门名称
			return e.getName();
		} else {
			return "";
		}
	}

	/**
	 * 
	 * <p>
	 * 新版运单代打木架信息<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-1
	 * @param waybillNo
	 * @return
	 * WoodenRequirementsEntity
	 */
	@Override
	public WoodenRequirementsEntity queryNewWoodenRequirements(LastWaybillRfcQueryDto queryDto) {
		return woodenRequirementsDao.queryNewWoodenRequirements(queryDto);
	}

	/**
	 * 
	 * <p>
	 * 更新打木架信息<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param oldWoodRequirements
	 * void
	 */
	@Override
	public void updateWoodenRequirements(
			WoodenRequirementsEntity oldWoodRequirements) {
		woodenRequirementsDao.updateWoodenRequirements(oldWoodRequirements);
	}
	
	public void updateAllOtherWoodenRequirementsToNo(
			WoodenRequirementsEntity oldWoodRequirements) {
		woodenRequirementsDao.updateAllOtherWoodenRequirementsToNo(oldWoodRequirements);
	}
	
	

	/**
	 * 
	 * <p>
	 * 删除打木架<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param id
	 * void
	 */
	@Override
	public int deleteWoodRequirementsById(String id) {
		return woodenRequirementsDao.deleteWoodRequirementsById(id);
	}

    /**
     * 
     * 更改单起草后追加付款明细实体
     * @author 102246-foss-shaohongliang
     * @date 2012-12-3 下午4:09:39
     */
	@Override
	public void appendWoodenRequirementsAfterChange(
			WoodenRequirementsEntity wooden, WaybillSystemDto systemDto) {
		if (wooden != null) {
			// 设置创建人、创建时间、更新人、更新时间
			wooden.setCreateTime(systemDto.getCreateTime());
			wooden.setModifyTime(systemDto.getModifyTime());
			// 设置是否有效
			wooden.setActive(FossConstants.INACTIVE);
			woodenRequirementsDao.addWoodenRequirementsEntity(wooden);
		}

	}

	/**
	 * 查询代打木架信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-7
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWoodenRequirementsService#queryWoodenRequirements(java.lang.String)
	 */
	@Override
	public WoodenRequirementsEntity queryWoodenRequirements(String waybillNo) {
		return woodenRequirementsDao.queryWoodenByNo(waybillNo);
	}

}