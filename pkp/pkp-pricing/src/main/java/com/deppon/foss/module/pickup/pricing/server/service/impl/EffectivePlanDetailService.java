/**
 *  initial comments.
 *  
 *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成： 
 *  
*	出发信息包括：
*
* a)	生效日期: 设定生效日期。
* 
* 
* b)	始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表请先熟悉 DP-FOSS-
* 
* 
* 		接送货系统用例-产品价格-城市列表-录入区域SUC-587-V0.1用例。           
* 
* 
* 		C) 未被激活的版本信息部能被正常使用。D) 方案描述：  对建立新方案的一些描述信息。
* 
* 
* 目的地信息：  由于可以设置至少一个到N个元素信息.故使用明细列表的方式来暂存数据一起提交。
* 
* 
* SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
* 
* 
* SR3	添加目的地明细信息时可一选择基础产品设置运营时效和取货时间，是否长短途， 
* 
* 
* 	在提交目的地设置相关信息时点击 “提交”按钮需要校验，目的地、基础产品、长短途在后台不能有相同的数据已存在， 
* 
* 
* 	提示如“广州-精准卡航-短途时效信息已经存在不能重复添加!”
* 
* SR7	图3中产品下拉列表中数据需要过滤规则：  只能显示该始发区域与目的地区域在 区域与产品基础数据中所维护的产品信息。
* 
* 
* SR9	新增时，所选择的区域信息（始发区域/目的地区域）都需要过滤，
* 
* 
*       只能取时效相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
*       
*       
*       图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为时效的区域,”。
*       
*       SR11	新增时效方案信息时， 必须先保存出发地信息，然后目的地信息中的“新增”、
*       
*       “删除”、“修改”、功能才可以被使用。 否则为禁用状态。
*       
*       SR12	所有新建方案都以草稿数据存储。针对草稿数据，
*       
*       我们可以随时进行任意操作。
*       
*       一旦在查询列表中做过激活的方案，就不能再做删除与修改了。只能做方案的复制对该方案进行
*       
*       延续不同时间段内体现不同时效信息。
*       
*       SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的目的地明细是否在指定的生
*       
*       效日期是不是已经存在相同的重复数据检查。重复数据被检查的标准定义是： 
*       
*       生效日期+方案始发区域+目的地区域+产品（三级），如果存在则提示“该方案下xxx目的地xxx产品xxx已
*       
*       经在另一个xxx方案下存在，请确认是否以该方案为准,请将xxx方案中止。”
*       
*       SR14	立即中止功能： 在时效查询列表中，只能选择一条激活的数据点击列表中“立即中止
*       
*       ”按钮弹出 选择中止时间，点击中止，,选择“提交”系统将选中的方案截止时间调
*       
*       
*       整为当前设置的中止的时间，出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 。
*       
*       
*       SR15	立即激活功能： 在时效查询列表中，只能选择一条未激活的数据点击列表中“立即激活”
*       
*       
*       按钮弹出 选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时间调整为当前设置的生效时间，
*       
*       
*       出现小于当前营业日期系统提示“立即激活操作的生效时间必须大于等于营业日期!” 。
 * 
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/EffectivePlanDetailService.java
 * 
 * FILE NAME        	: EffectivePlanDetailService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveRegionOrgService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterEffectivePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterEffectivePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.EffectivePlanDetailException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 时效方案明细Service
 * 
 * @author foss-yuehongjie
 * @mail dpyhj@deppon.com
 * @date 2012-10-16 上午10:39:58
 * @since
 * @version
 */

public class EffectivePlanDetailService implements IEffectivePlanDetailService {
	
	private static final Logger log = Logger.getLogger(EffectivePlanDetailService.class);
    /**
     * 时效方案SERVICE
     */
    @Inject
    IEffectivePlanService effectivePlanService;
    /**
     * 时效方案部门SERVICE
     */
    @Inject
    IEffectiveRegionOrgService effectiveRegionOrgService;
    /**
     * 时效方案明细DAO
     */
    @Inject
    IEffectivePlanDetailDao effectivePlanDetailDao;
    /**
     * 区域接口
     */ 
    @Inject
    IRegionService regionService;
     /**
      * 产品接口
      */
    @Inject
    IProductService productService;
    
    @Inject
    IOuterEffectivePlanService outerEffectivePlanService;
    
    /**
	 * 走货路径
	 * 提供与走货路径相关的服务接口
	 */
	@Inject
	private IFreightRouteService freightRouteService;
	
	/**
     * 组织机构接口
     */
    @Inject
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 
     * <p>新增时效明细信息</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午2:41:23
     * 
     * @param effectivePlanDetail 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService#insertEffectivePlanDetail(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity)
     */
    @Override
    public void insertEffectivePlanDetail(
	    EffectivePlanDetailEntity effectivePlanDetailEntity) {
	if(null != effectivePlanDetailEntity){
	    Date currentTime = new Date();	
	    String productCode = effectivePlanDetailEntity.getProductCode();
	    /**
	     * 
	     * 出发区域ID,到达区域ID,产品CODE
	     * 
	     */
	    EffectivePlanDetailEntity effectiveDetailEntity = new EffectivePlanDetailEntity();
	    effectiveDetailEntity.setProductCode(effectivePlanDetailEntity.getProductCode());
	    effectiveDetailEntity.setDeptRegionId(effectivePlanDetailEntity.getDeptRegionId());
	    effectiveDetailEntity.setArrvRegionId(effectivePlanDetailEntity.getArrvRegionId());
	    effectiveDetailEntity.setEffectivePlanId(effectivePlanDetailEntity.getEffectivePlanId());
	    List<EffectivePlanDetailEntity>  detailList = effectivePlanDetailDao.queryEffectivePlanDetailByCondition(effectiveDetailEntity);
	    /**
	     * 
	     * 
	     * 如果存在相同数据则提示，
	     * 规则是按照 ：
	     * 同一个产品、同一个始发区域id，同一个目的地区域 ，同一个方案ID
	     * 寻找时效明细是否已经存在类似明细
	     * 
	     * 
	     */
	    if(CollectionUtils.isNotEmpty(detailList)){
		ProductEntity productEntity  = productService.getProductByCache(productCode, currentTime);
		PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(effectivePlanDetailEntity.getArrvRegionId(), PricingConstants.PRESCRIPTION_REGION);
		String isLong = null;
		if(StringUtil.equalsIgnoreCase(PricingConstants.LONG_OR_SHORT_L, effectivePlanDetailEntity.getLongOrShort())){
		    isLong = PricingConstants.LONG_OR_SHORT_L_NAME;
		}else{
		    isLong = PricingConstants.LONG_OR_SHORT_S_NAME;
		}
		throw new EffectivePlanDetailException("目的区域" + "["+priceRegionEntity.getRegionName()+"]产品["+productEntity.getName()+"]长短途["+isLong+"]已经存在不可重复添加!",null);
	    }else{
		effectivePlanDetailEntity.setId(UUIDUtils.getUUID());
		effectivePlanDetailEntity.setActive(FossConstants.INACTIVE);
		effectivePlanDetailEntity.setVersionNo(new Date().getTime());
		effectivePlanDetailDao.insertSelective(effectivePlanDetailEntity);
	    }
	}else{
	    throw new EffectivePlanDetailException("时效方案明细数据为空，请检查",null);
	}
    }

    /**
     * 
     * <p>批量删除时效明细信息</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午2:41:38
     * 
     * @param ids 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService#deleteEffectivePlanDetail(java.util.List)
     */
    @Override
    public void deleteEffectivePlanDetail(List<String> ids) {
	effectivePlanDetailDao.deleteEffectivePlanDetailByIds(ids);
    }
    /**
     * 
     * <p>批量激活时效方案明细信息</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午2:41:52
     * 
     * @param ids 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService#activeEffectivePlanDetail(java.util.List)
     */
    @Override
    public void activeEffectivePlanDetail(List<String> ids) {
	effectivePlanDetailDao.activeEffectivePlanDetailByIds(ids);
    }
    /**
     * 
     * <p>
     * (根据始发部门编号与目的站区域编号查询时效方案明细信息)
     * </p>
     * 
     * @author 岳洪杰
     * 
     * @date 2012-10-12 下午7:07:16
     * 
     * @param deptNo
     *            - 始发站编号
     *            
     * @param arrvieNo
     *            - 目的信息
     *            
     * @billDate 开单日期
     * 
     * @return billDate 开单日期
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService#queryCuurentEffectivePlanDetailInfo(java.lang.String,
     *      java.lang.String)
     */
	private List<EffectivePlanDetailEntity> queryCuurentEffectivePlanDetailInfo(String deptNo, String arrvieNo,
			String productCode, Date billDate) {
	    	//获得当前时间
		Date currentDateTime = null;
		if (billDate == null) {
			currentDateTime = new Date();
		} else {
			currentDateTime = billDate;
		}
		// 根据出发部门编号定位时效逻辑区域
		String deptRegionId = regionService.findRegionOrgByDeptNo(deptNo, currentDateTime, null,PricingConstants.PRESCRIPTION_REGION);
		if (StringUtil.isEmpty(deptRegionId)) {
			return null;
		}
		// 根据到达部门编号定位时效逻辑区域
		String arrvieRegionId = null;
		//最终配载部门，用户偏线时效的判断
		String lastLoadOrgCode = null;
		if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE, productCode)){
			lastLoadOrgCode = queryLastLoadOrgCode(deptNo, arrvieNo, productCode, billDate);
			arrvieRegionId = regionService.findRegionOrgByDeptNo(lastLoadOrgCode, currentDateTime, productCode,
					PricingConstants.PRESCRIPTION_REGION);
		}else{
			// 根据到达部门编号定位时效逻辑区域
			arrvieRegionId = regionService.findRegionOrgByDeptNo(arrvieNo, currentDateTime, productCode,
					PricingConstants.PRESCRIPTION_REGION);
		}
		if (StringUtil.isEmpty(arrvieRegionId)) {
			return null;
		}
		if(StringUtils.isNotEmpty(productCode) && productCode.equals(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE)){
			
			//查询偏线时效方案
			OuterEffectivePlanEntity entity = outerEffectivePlanService
					.queryOuterEffectPlanByFieldAndBranch(lastLoadOrgCode, arrvieNo, 
							PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001, billDate);
			if(entity != null){
				// 根据最新时效方案版本查询相关时效方案详细信息
				return effectivePlanDetailDao.queryEffectivePlanDetailListByCondition(deptRegionId, arrvieRegionId,
						null, currentDateTime);
			}else{
				return null;
			}
		}else{
			// 根据最新时效方案版本查询相关时效方案详细信息
			return effectivePlanDetailDao.queryEffectivePlanDetailListByCondition(deptRegionId, arrvieRegionId,
					productCode, currentDateTime);
		}
	}
	
	/**
	 * 
	 * 获取最终配载部门
	 * 
	 * @author WangQianJin
	 * @date 2014-01-01
	 */
	private String queryLastLoadOrgCode(String deptNo, String arrvieNo,
			String productCode, Date billDate) {
		List<FreightRouteLineDto> freightRouteList = freightRouteService.queryFreightRouteBySourceTarget(deptNo, arrvieNo, productCode, billDate);
		// 得到途径外场和 始发营业部, 到达营业部 编码集合LIST A-C C-D D-B 得到这种格式
		List<String> addressInfoList = new ArrayList<String>();

		for (FreightRouteLineDto f : freightRouteList) {//拼接走货路径
			addressInfoList.add(f.getSourceCode() + "-" + f.getTargetCode());
		}

		// 根据始发外场code 和外场集合 删除重复的外场 得到A C D B 格式的 外场集合 同时包含 出发部门到达部门
		List<String> departmentInfoList = removeDuplicateRoute(addressInfoList);
		if(CollectionUtils.isNotEmpty(departmentInfoList)){
			// 获取最终配置外场
			String lastOutLoadOrgCode = null;
			OrgAdministrativeInfoEntity lastOutLoadOrgDepartment = null;
			// 最终配置外场
			for (int i = departmentInfoList.size() - 1; i > -1; i--) {
				lastOutLoadOrgCode = departmentInfoList.get(i);
				lastOutLoadOrgDepartment = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lastOutLoadOrgCode);
				if (lastOutLoadOrgDepartment == null) {
					continue;
				} else {
					if (lastOutLoadOrgDepartment.checkTransferCenter()) {// 如果是外场，那么获取外场
						break;
					} else {
						continue;
					}
				}
			}
			return lastOutLoadOrgCode;	
		}
		return null;
	}
	
	private List<String> removeDuplicateRoute(List<String> routeList) {
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < routeList.size(); i++) {
			temp.add(routeList.get(i).substring(0,
					routeList.get(i).indexOf("-")));
		}		
		return temp;
	}

    /**
     * 
     * <p>查询产品时效，
     * 	    根据originalCityCode,destinationCityCode 
     * 	    关联T_SRV_EFFECTIVE_REGION，T_SRV_EFFECTIVE_PLAN
     * 	  T_SRV_EFFECTIVE_PLAN_DETAIL
     *    查询获取EFFECTIVE_PLAN_DETAIL明细信息和产品名称信息列表
     * </p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午2:34:52
     * 
     * @param originalCityCode  出发城市code
     * 
     * @param destinationCityCode 到达城市code
     * 
     * @param productCode  产品code
     * 
     * @param billDate 业务日期
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService#queryEffectivePlanDetailListByCityCode(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
     */
	public List<EffectivePlanDto> queryEffectivePlanDetailListByCityCode(
			String originalCityCode, String destinationCityCode,
			String productCode, Date billDate) {
		List<EffectivePlanDetailEntity> alist = new ArrayList<EffectivePlanDetailEntity>();
		Date currentDateTime = null;
		if (billDate == null) {
			currentDateTime = new Date();
		} else {
			currentDateTime = billDate;
		}
		// 根据出发部门编号定位时效逻辑区域
		String deptRegionId = regionService.findRegionIdByDistrictCode(
				originalCityCode, currentDateTime,
				PricingConstants.PRESCRIPTION_REGION);
		if (StringUtil.isEmpty(deptRegionId)) {
			return null;
		}
		// 根据到达部门编号定位时效逻辑区域
		String arrvieRegionId = regionService.findRegionIdByDistrictCode(
				destinationCityCode, currentDateTime,
				PricingConstants.PRESCRIPTION_REGION);
		if (StringUtil.isEmpty(arrvieRegionId)) {
			return null;
		}
		// 根据最新时效方案版本查询相关时效方案详细信息
		alist = effectivePlanDetailDao.queryEffectivePlanDetailListByCondition(deptRegionId, arrvieRegionId, productCode, billDate);
		return transObject(alist, billDate);
	}
    /**
     * 
     * <p>
     * Description: 查询产品时效 <br />
     * </p>
     * 
     * @author admin
     * 
     * @version 0.1 2012-10-25
     * 
     * @param originalOrgCode
     *            出发部门
     *            
     * @param destinationOrgCode
     *            到达部门
     *            
     * @param productCode
     *            产品code
     *            
     * @parm billDate 开单日期 可空 ，默认为当前时间
     * 
     * @return List<EffectivePlanDto>
     */
    @Override
	public List<EffectivePlanDto> queryEffectivePlanDetailListByOrgCode(String originalOrgCode,
			String destinationOrgCode, String productCode, Date billDate) {
		// 重用已有方法
		List<EffectivePlanDetailEntity> alist = this.queryCuurentEffectivePlanDetailInfo(originalOrgCode,
				destinationOrgCode, productCode, billDate);
		return transObject(alist, billDate);
	}
    /**
     * 
     * 获取缓存中的产品、并从中拿到产品名称赋予外部对象
     * @param alist 
     * @param billDate 
     * @return 
     */
	private List<EffectivePlanDto> transObject(List<EffectivePlanDetailEntity> alist, Date billDate) {
		List<EffectivePlanDto> blist = new ArrayList<EffectivePlanDto>();
		if (alist != null) {
			// 转换数据
			for (int i = 0; i < alist.size(); i++) {
				EffectivePlanDetailEntity oldEntity = alist.get(i);
				EffectivePlanDto newDto = new EffectivePlanDto();
				try {
					PropertyUtils.copyProperties(newDto, oldEntity);
				} catch (IllegalAccessException e) {
					log.info(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					log.info(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					log.info(e.getMessage(), e);
				}
				newDto.setProductCode(oldEntity.getProductCode());
				//缓存中获取产品信息
				ProductEntity product = productService.getProductByCache(oldEntity.getProductCode(), billDate);
				if (product != null) {
					newDto.setProductName(product.getName());
				}
				blist.add(newDto);
			}
			return blist;
		} else {
			return null;
		}
	}
    /**
     * @Description: 根据条件查询时效方案明细
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午2:05:11
     * 
     * @param effectivePlanDetailEntity
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailByConditions(EffectivePlanDetailEntity effectivePlanDetailEntity) {
    	return effectivePlanDetailDao.queryEffectivePlanDetailInfo(effectivePlanDetailEntity);
    }
    /**
     * 
     * 查询方案明细信息分页
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-11-10 下午7:50:06
     */
    @Override
    public List<EffectivePlanDetailEntity> searchEffectivePlanDetailEntityByCondition(
	    EffectivePlanDetailEntity effectivePlanEntity, int start, int limit) {
	//时效明细信息
	List<EffectivePlanDetailEntity> effectivePlanDetailEntitys = effectivePlanDetailDao.searchEffectivePlanDetailEntityByCondition(effectivePlanEntity, start, limit);
	//时效明细VO集合对象信息
	List<EffectivePlanDetailEntity> listVo = new ArrayList<EffectivePlanDetailEntity>();
	for (EffectivePlanDetailEntity effectivePlanDetailEntity : effectivePlanDetailEntitys) {
	    PriceRegionEntity  priceRegionEntity = regionService.searchRegionByID(effectivePlanDetailEntity.getArrvRegionId(), PricingConstants.PRESCRIPTION_REGION);
	    effectivePlanDetailEntity.setArrvRegionName(priceRegionEntity.getRegionName());
	    listVo.add(effectivePlanDetailEntity);
	}
	return listVo;
    }
    /**
     * 
     * 查询方案没明细信息分页总记录数
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-11-10 下午7:50:06
     */
    @Override
    public Long searchEffectivePlanDetailEntityCount(
	    EffectivePlanDetailEntity effectivePlanDetailEntity) {
	return effectivePlanDetailDao.searchEffectivePlanDetailEntityByConditionCount(effectivePlanDetailEntity);
    }
    /**
     * 
     * <p>根据ID查询明细实体信息</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-1-10 上午9:08:50
     * 
     * @param id
     * 
     * @return
     * 
     * @see
     */
    @Override
    public EffectivePlanDetailEntity getEffectiveDetailEntityById(String id) {
	EffectivePlanDetailEntity effectivePlanDetailEntity = effectivePlanDetailDao.selectByPrimaryKey(id);
	return boxEffectivePlanDetailName(effectivePlanDetailEntity);
    }
    /**
     * 
     * @Description: 封装名称
     * 
     * @author FOSSDP-Administrator
     * 
     * @date 2013-3-14 下午2:25:36
     * 
     * @param effectivePlanDetailEntity
     * 
     * @return
     * 
     * @version V1.0
     */
    private EffectivePlanDetailEntity boxEffectivePlanDetailName(EffectivePlanDetailEntity effectivePlanDetailEntity){
	if(null != effectivePlanDetailEntity){
	    PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(effectivePlanDetailEntity.getArrvRegionId(),PricingConstants.PRESCRIPTION_REGION);
	    if(null != priceRegionEntity){
		effectivePlanDetailEntity.setArrvRegionName(priceRegionEntity.getRegionName());
	    }
	}
	return effectivePlanDetailEntity;
    }
    /**
     * @Description: 根据条件查询时效
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-1-9 下午4:29:52
     * 
     * @param startCityCode
     * 
     * @param destinationCountryCode
     * 
     * @param destinationProvinceCode
     * 
     * @param destinationCityCode
     * 
     * @param destinationCountyCode
     * 
     * @param destinationAddress
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
	public List<EffectivePlanDto> queryEffectivePlanDetailListByCondition(
			String startCityCode, List<String> deptCodeList,
			String productCode, Date billDate) {
    	List<EffectivePlanDetailEntity> alist = new ArrayList<EffectivePlanDetailEntity>();
		Date currentDateTime = null;
		if (billDate == null) {
			currentDateTime = new Date();
		} else {
			currentDateTime = billDate;
		}
		// 根据出发部门编号定位时效逻辑区域
		String deptRegionId = regionService.findRegionIdByDistrictCode(
				startCityCode, currentDateTime,
				PricingConstants.PRESCRIPTION_REGION);
		if (StringUtil.isEmpty(deptRegionId)) {
			return null;
		}
		// 根据最新时效方案版本查询相关时效方案详细信息
		alist = effectivePlanDetailDao.queryEffectivePlanDetailListByArrvIds(
				deptRegionId, deptCodeList, productCode, FossConstants.ACTIVE, billDate);
		return transObject(alist, billDate);
	}
    /**
     * 
     * @Description: 根据LIST条件查询时效方法
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-1-25 上午11:37:12
     * 
     * @param startCodeList
     * 
     * @param arrvCodeList
     * 
     * @param productCode
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
    public List<EffectivePlanDto> queryEffectivePlanDetailListByCondition(
    		List<String> startCodeList, List<String> arrvCodeList,
			String productCode, Date billDate) {
    	List<EffectivePlanDetailEntity> alist = new ArrayList<EffectivePlanDetailEntity>();
		Date currentDateTime = null;
		if (billDate == null) {
			currentDateTime = new Date();
		} else {
			currentDateTime = billDate;
		}
		//整合营业部编码
		List<String> deptList = new ArrayList<String>();
		for (int i = 0; i < startCodeList.size(); i++) {
			if(!deptList.contains(startCodeList.get(i))) {
				deptList.add(startCodeList.get(i));
			}
		}
		for (int i = 0; i < arrvCodeList.size(); i++) {
			if(!deptList.contains(arrvCodeList.get(i))) {
				deptList.add(arrvCodeList.get(i));
			}
		}
		//根据营业部编码查询区域ID
		Map<String, String> regionMap = new HashMap<String, String>();
		for (int i = 0; i < deptList.size(); i++) {
			String deptNo = deptList.get(i);
			String regionId = regionService.findRegionOrgByDeptNo(deptNo,
					currentDateTime,productCode, PricingConstants.PRESCRIPTION_REGION);
			if(StringUtil.isNotBlank(regionId)) {
				regionMap.put(deptNo, regionId);
			}
		}
		//根据出发和到达过滤区域
		List<String> startRegionList = new ArrayList<String>();
		List<String> arrvRegionList = new ArrayList<String>();
		for (int i = 0; i < startCodeList.size(); i++) {
			String depNo = startCodeList.get(i);
			String regionId = regionMap.get(depNo);
			if(StringUtil.isNotBlank(regionId) && !startRegionList.contains(regionId)) {
				startRegionList.add(regionId);
			}
		}
		for (int i = 0; i < arrvCodeList.size(); i++) {
			String depNo = arrvCodeList.get(i);
			String regionId = regionMap.get(depNo);
			if(StringUtil.isNotBlank(regionId) && !arrvRegionList.contains(regionId)) {
				arrvRegionList.add(regionId);
			}
		}
		if(CollectionUtils.isEmpty(startRegionList) || CollectionUtils.isEmpty(arrvRegionList)) {
			return null;
		}
		// 根据最新时效方案版本查询相关时效方案详细信息
		alist = effectivePlanDetailDao.queryEffectivePlanDetailListByRegionIds(startRegionList, arrvRegionList,
				productCode, FossConstants.ACTIVE, currentDateTime);
		return transObject(alist, billDate);
    }
    
    /**
     * 
     * @Description: 根据SET条件查询时效方法
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-1-25 上午11:37:12
     * 
     * @param startCodeList
     * 
     * @param arrvCodeList
     * 
     * @param productCode
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
    public List<EffectivePlanDto> queryEffectivePlanDetailListByCondition(
    		Set<String> startRegionList, Set<String> arrvRegionList,
			String productCode, Date billDate) {
    	List<EffectivePlanDetailEntity> alist = new ArrayList<EffectivePlanDetailEntity>();
		Date currentDateTime = null;
		if (billDate == null) {
			currentDateTime = new Date();
		} else {
			currentDateTime = billDate;
		}
		if(CollectionUtils.isEmpty(startRegionList) || CollectionUtils.isEmpty(arrvRegionList)) {
			return null;
		}
		// 根据最新时效方案版本查询相关时效方案详细信息
		alist = effectivePlanDetailDao.queryEffectivePlanDetailListByRegionIds(startRegionList, arrvRegionList,
				productCode, FossConstants.ACTIVE, currentDateTime);
		return transObject(alist, billDate);
    }
    /**
     * 
     * <p>(修改时效明细信息)</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-1-10 上午9:35:16
     * 
     * @param effectivePlanDetail
     * 
     * @see
     */
    @Override
    public void updateEffectivePlanDetail(
	    EffectivePlanDetailEntity effectivePlanDetail) {
    	effectivePlanDetail.setVersionNo(new Date().getTime());
	effectivePlanDetailDao.updateByPrimaryKeySelective(effectivePlanDetail);
    }
    /**
     * 设置 时效方案SERVICE.
     *
     * @param effectivePlanService the new 时效方案SERVICE
     */
    public void setEffectivePlanService(
	    IEffectivePlanService effectivePlanService) {
	this.effectivePlanService = effectivePlanService;
    }

    /**
     * 设置 时效方案部门SERVICE.
     *
     * @param effectiveRegionOrgService the new 时效方案部门SERVICE
     */
    public void setEffectiveRegionOrgService(
	    IEffectiveRegionOrgService effectiveRegionOrgService) {
	this.effectiveRegionOrgService = effectiveRegionOrgService;
    }

    /**
     * 设置 时效方案明细DAO.
     *
     * @param effectivePlanDetailDao the new 时效方案明细DAO
     */
    public void setEffectivePlanDetailDao(
	    IEffectivePlanDetailDao effectivePlanDetailDao) {
	this.effectivePlanDetailDao = effectivePlanDetailDao;
    }

    /**
     * 设置 区域接口.
     *
     * @param regionService the new 区域接口
     */
    public void setRegionService(IRegionService regionService) {
	this.regionService = regionService;
    }
    
    /**
     * 获取 产品接口.
     *
     * @return the 产品接口
     */
    public IProductService getProductService() {
        return productService;
    }
    
    /**
     * 设置 产品接口.
     *
     * @param productService the new 产品接口
     */
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }

    
    /**
     * 获取 时效方案SERVICE.
     *
     * @return the 时效方案SERVICE
     */
    public IEffectivePlanService getEffectivePlanService() {
        return effectivePlanService;
    }

    
    /**
     * 获取 时效方案部门SERVICE.
     *
     * @return the 时效方案部门SERVICE
     */
    public IEffectiveRegionOrgService getEffectiveRegionOrgService() {
        return effectiveRegionOrgService;
    }

    
    /**
     * 获取 时效方案明细DAO.
     *
     * @return the 时效方案明细DAO
     */
    public IEffectivePlanDetailDao getEffectivePlanDetailDao() {
        return effectivePlanDetailDao;
    }

    
    /**
     * 获取 区域接口.
     *
     * @return the 区域接口
     */
    public IRegionService getRegionService() {
        return regionService;
    }
    
    public void setOuterEffectivePlanService(
			IOuterEffectivePlanService outerEffectivePlanService) {
		this.outerEffectivePlanService = outerEffectivePlanService;
	}
    public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}
    public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
}




























































































/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */














