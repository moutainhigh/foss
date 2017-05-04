/**
 *  initial comments.
 *  
 *  SR1	页面红色符号“*” 元素都为必填项。
 *  SR2	提交前系统校验“货物类型名称”如已存在则提示“货物类型已存在！
 *  	”系统提交失败。其中校验忽略大小写及左右空格。
 *  SR3	提交前系统校验“类型代码”如已存在则提示“类型代码已存在！
 *  	”系统提交失败。其中校验忽略大小写及左右空格。
 *  SR4	类型代码生成规则： 参照产品定义中的生成规则，
 *  	也是由管理员按照协商规则来录入暂规则定位: H+5个数字如：H00001
 *  SR5	提交货物类型时可选择是否激活来确定该货物类型是否立即可使用，
 *  	若没有在新增时做激活操作后续在货物类型列表中也可针对未做激活的货物类型记录做激活操作。
 *  SR6	在录入完货物类型信息前台根据所录入的货物编号与名称安全通过校验后，检查是否选择了立即激活,
 *  	做激活的货物类型会和去所有已激活的产品进行自动绑定生成条目信息。生成的条目信息如下：产品编号，
 *  	货物编号，条目名称=产品名称（货物名称），条目编号=由大写字母T+五位数字组成，如：T00001记录每增一条自增1。
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/GoodsTypeService.java
 * 
 * FILE NAME        	: GoodsTypeService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductItemDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductItemService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GoodsTypeDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.GoodsTypeException;
import com.deppon.foss.module.pickup.pricing.server.cache.GoodsTypeCacheDeal;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;


/**
 * 
 * 货物类型定义
 * @author DP-Foss-YueHongJie
 * @date 2013-1-10 下午2:54:30
 * @version 1.0
 */
public class GoodsTypeService implements IGoodsTypeService{
	
	private static final int NUMBER_2999 = 2999;
	/**
	 * 日志
	 */
	private static final Logger log = Logger.getLogger(GoodsTypeService.class);
	/**
	 * 货物类型DAO
	 */
    @Inject
    IGoodsTypeDao goodsTypeDao;
    /**
	 * 产品SERVICE
	 */
    @Inject
    IProductService productService;
    /**
	 * 产品条目SERVICE
	 */
    @Inject
    IProductItemService productItemService;
    /**
	 * 产品条目DAO
	 */
    @Inject
    IProductItemDao productItemDao;
    /**
	 * 员工SERVICE
	 */
    @Inject
    IEmployeeService employeeService;
    /**
	 * 货物类型缓存
	 */
    @Inject
    GoodsTypeCacheDeal goodsTypeCacheDeal;
    /**
     * 设置 员工SERVICE.
     *
     * @param employeeService the new 员工SERVICE
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    /**
     * 设置 产品条目SERVICE.
     *
     * @param productItemService the new 产品条目SERVICE
     */
    public void setProductItemService(IProductItemService productItemService) {
        this.productItemService = productItemService;
    }
    /**
     * 设置 货物类型DAO.
     *
     * @param goodsTypeDao the new 货物类型DAO
     */
    public void setGoodsTypeDao(IGoodsTypeDao goodsTypeDao) {
        this.goodsTypeDao = goodsTypeDao;
    }
    /**
     * 设置 货物类型缓存.
     *
     * @param goodsTypeCacheDeal the new 货物类型缓存
     */
    public void setGoodsTypeCacheDeal(GoodsTypeCacheDeal goodsTypeCacheDeal) {
		this.goodsTypeCacheDeal = goodsTypeCacheDeal;
	}
    /**
     * 添加货物定义
     * 
     * <p>(传入goodsTypeEntity添加货物定义信息)</p> 
     * 
     * @author 岳洪杰
     * 
     * @date 2012-10-16 上午11:32:12
     * 
     * @param goodsTypeEntity
     */
	@Override
    public int addGoodsType(GoodsTypeEntity goodsTypeEntity) {
	//判断货物信息不可为空
	if(null == goodsTypeEntity || StringUtil.isNotEmpty(goodsTypeEntity.getId())){
	    return 0;
	}
	//根据当前货物名称查询是否已经存在、存在则提示客户端
	if(isTheSameGoodsTypeName(goodsTypeEntity, false)){
	    throw new GoodsTypeException("货物类型名称已经存在，不能添加!", GoodsTypeException.GOODS_TYPE_CHECK_PARAMETER_ERROR_CODE);
	}
	//创建货物UUID
	goodsTypeEntity.setId(UUIDUtils.getUUID());
	//生成货物编码 取当前最大累加1
	String goodsTypeCode = generateGoodsTypeCode();
	if(StringUtil.isNotBlank(goodsTypeCode)) {
		goodsTypeEntity.setCode(goodsTypeCode);
	} else {
		throw new GoodsTypeException("货物类型编号生成错误", GoodsTypeException.GOODS_TYPE_CHECK_PARAMETER_ERROR_CODE);
	}
	//当前时间
	Date currentDate = new Date();
	//设置当前时间
	goodsTypeEntity.setCreateDate(currentDate);
	//设置开始时间
	goodsTypeEntity.setBeginTime(currentDate);
	//设置结束时间
	goodsTypeEntity.setEndTime(getMaxBusinessDate());
	//版本号
	goodsTypeEntity.setVersionNo(currentDate.getTime());
	//设置修改日期
	goodsTypeEntity.setModifyDate(currentDate);
	return goodsTypeDao.insert(goodsTypeEntity);
    }
	/**
	 * 
     * 修改货物定义
     * 
     * <p>(传入goodsTypeEntity修改货物定义信息)</p> 
     * 
     * @author 岳洪杰
     * 
     * @date 2012-10-16 上午11:32:46
     * 
     * @param goodsTypeEntity
     */
    @Override
    public int updateGoodsType(GoodsTypeEntity goodsTypeEntity) {
    	//根据当前货物名称查询是否已经存在、存在则提示客户端
    	if(isTheSameGoodsTypeName(goodsTypeEntity, true)){
    	    throw new GoodsTypeException("货物类型名称已经存在!", GoodsTypeException.GOODS_TYPE_CHECK_PARAMETER_ERROR_CODE);
    	}
    	return goodsTypeDao.updateByPrimaryKey(goodsTypeEntity);
    }
    /**
     * 
     * 根据货物编号查询有效货物信息
     * 
     * @author 岳洪杰
     * 
     * @date 2012-10-16 上午11:33:47
     * 
     * @param goodsTypeCode
     * 
     * @return
     * 
     * @see
     */
    @Override
    public GoodsTypeEntity queryGoodsTypeByGoodTypeCode(String goodsTypeCode,Date billDate) {
	return goodsTypeDao.queryGoodsTypeByGoodTypeCode(goodsTypeCode,billDate);
    }
    /**
     * 
     * 激活货物定义
     * 
     * <p>(负责激活货物信息，自动衍生出条目信息。当前操作自动匹配产品类型(已激活状态)进行条目组装)</p>
     * 
     * @author 岳洪杰
     * 
     * @date 2012-10-16 上午11:35:31
     * 
     * @param goodsTypeEntity
     * 
     * @see
     */
    @Transactional
    @SuppressWarnings("rawtypes")
    @Override
    public void activationGoodsType(List<String> goodIds) {
	if(CollectionUtils.isNotEmpty(goodIds)){
	    GoodsTypeEntity  goodsTypeEntity;
	    //迭代所有有效的产品信息，并且组织生成条目信息
	    ProductEntity queryBean = new ProductEntity();
	    queryBean.setActive(FossConstants.ACTIVE);
            List<ProductEntity> productEntities = productService.findProductByCondition(queryBean);
            String id = null;
            Iterator  goodsIterator = goodIds.iterator();
	    while(goodsIterator.hasNext()){
		id = (String)goodsIterator.next();
		goodsTypeEntity  = goodsTypeDao.selectByPrimaryKey(id);
		if(CollectionUtils.isNotEmpty(productEntities)){
		    Iterator iterator = (Iterator) productEntities.iterator();
		    while(iterator.hasNext()){
			ProductEntity productEntity = (ProductEntity) iterator.next();
			//过滤一级产品
			if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LEVEL_1.equals(productEntity.getLevels())){
			    continue;
			}
			// 产品条目
			ProductItemEntity productItemEntity = new ProductItemEntity();
			//craete uuid
			String productItemEntityUUId = UUIDUtils.getUUID();
			//set uuid
			productItemEntity.setId(productItemEntityUUId);
			//设置货物ID
			productItemEntity.setGoodstypeId(goodsTypeEntity.getId());
			//设置货物CODE
			productItemEntity.setGoodstypeCode(goodsTypeEntity.getCode());
			//设置产品ID
			productItemEntity.setProductId(productEntity.getId());
			//设置产品CODE
			productItemEntity.setProductCode(productEntity.getCode());
			//设置产品条目code
			productItemEntity.setCode(String.valueOf(System.currentTimeMillis()));
			//设置产品条目名称  货物中文名+ 符号“-”+产品中文名
			productItemEntity.setName(productEntity.getName()+"-"+goodsTypeEntity.getName());
			//设置草稿状态
		    	productItemEntity.setActive(FossConstants.INACTIVE);
		    	//版本号
		    	productItemEntity.setVersionNo(new Date().getTime());
		    	//开始时间
		    	productItemEntity.setBeginTime(new Date());
		    	//默认最低一票为0
		    	productItemEntity.setFeeBottom(Long.valueOf(0));
		    	//设置结束时间、默认是日期2999-01-01格式
		    	productItemEntity.setEndTime(new Date(PricingConstants.ENDTIME));
		    	//创建时间
		    	productItemEntity.setCreateDate(new Date());
		    	//持久数据库
		    	productItemDao.insert(productItemEntity);
		    }
		 }
		 goodsTypeEntity.setActive(FossConstants.ACTIVE);
		 goodsTypeDao.updateByPrimaryKey(goodsTypeEntity);
	    }
	}
    }
    /**
     * 设置 产品SERVICE.
     *
     * @param productService the new 产品SERVICE
     */
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
    /**
     * 设置 产品条目DAO.
     *
     * @param productItemDao the new 产品条目DAO
     */
    public void setProductItemDao(IProductItemDao productItemDao) {
        this.productItemDao = productItemDao;
    }
    /**
      * @Description: 获取有效期最大值
      * 
      * Company:IBM
      * 
      * @author FOSSDP-sz
      * 
      * @date 2012-12-25 下午2:50:01
      * 
      * @return
      * 
      * @version V1.0
     */
    private Date getMaxBusinessDate() {
	Calendar cal = Calendar.getInstance();
	cal.set(NUMBER_2999, NumberConstants.NUMBER_11, NumberConstants.NUMBER_31);
	Date maxDate = cal.getTime();
	return maxDate;
    }
    /**
     * 
     * 分页查询货物信息（按照不同条件分页查询货物信息）
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午3:02:50
     */
    @Override
    public List<GoodsTypeEntity> findGoodsTypePagingByCondition(
	    GoodsTypeEntity entity, int start, int limit) {
	//根据不同条件获取相关货物所有信息
	List<GoodsTypeEntity> goodsTypeEntitys = goodsTypeDao.findGoodsTypePagingByCondiction(entity, start, limit);
	//填充货物名称以及员工、部门信息
	List<GoodsTypeEntity> list = new ArrayList<GoodsTypeEntity>();
	if(CollectionUtils.isNotEmpty(goodsTypeEntitys)){
	    EmployeeEntity createEmployee = null;
	    EmployeeEntity modifyEmployee = null;
	    for (GoodsTypeEntity goodsTypeEntity : goodsTypeEntitys) {
	    	if(StringUtil.isNotEmpty(goodsTypeEntity.getCreateUser())){
	    		createEmployee = employeeService.queryEmployeeByEmpCode(goodsTypeEntity.getCreateUser());
	    		if(null != createEmployee){
	    		    goodsTypeEntity.setCreateUserName(createEmployee.getEmpName());
	    		}
	    	}
	    	if(StringUtil.isNotEmpty(goodsTypeEntity.getModifyUser())){
	    		modifyEmployee = employeeService.queryEmployeeByEmpCode(goodsTypeEntity.getModifyUser());	    		
	    		if(null != modifyEmployee){
	    		    goodsTypeEntity.setModifyUserName(modifyEmployee.getEmpName());
	    		}
	    	}		
		list.add(goodsTypeEntity);
	    }
	}
	return list;
    }
    /**
     * 
     * 货物定义总数查询（按照不同条件查询货物信息总数）
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午3:02:50
     */
    @Override
    public Long countGoodsTypePagingByCondition(
	    GoodsTypeEntity entity) {
	return goodsTypeDao.countGoodsTypePagingByCondiction(entity);
	
    }
    /**
     * 
     * 查询货物定义信息
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午5:13:38
     */
    @Override
    public List<GoodsTypeEntity> findGoodsTypeByCondiction(
	    GoodsTypeEntity entity) {
	return goodsTypeDao.findGoodsTypeByCondiction(entity);
    }
    /**
     * 
     * 查询唯一产品
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午5:15:25
     */
    @Override
    public GoodsTypeEntity findGoodsTypeById(String id) {
	 return goodsTypeDao.selectByPrimaryKey(id);
    }
    /**
     * 
     * 查询货物缓存数据
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-30 下午2:35:36
     * 
     */
    @Override
    public List<GoodsTypeEntity> findGoodsTypeByCache(GoodsTypeDto entity,
	    Date billDate) {
    	return goodsTypeDao.findGoodsTypeByCache(entity, billDate);
    }
    /**
     * 
     * @Description: 查询货物缓存数据
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-19 下午3:31:07
     * 
     * @param goodsTypeCode
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public GoodsTypeEntity getGoodsTypeByCache(String goodsTypeCode, Date billDate) {
	//判断货物是否为空
    	if(StringUtil.isEmpty(goodsTypeCode)) {
    		return null;
    	}
    	//判断业务日期是否为空
    	if(billDate == null) {
    		billDate = new Date();
    	}
    	//从货物缓存中获取货物信息
    	GoodsTypeEntity goodsTypeEntity = null;
    	if(SqlUtil.loadCache){
	    	try {
	    	    goodsTypeEntity = goodsTypeCacheDeal.getGoodsTypeEntityByCache(goodsTypeCode, billDate);
	    	    return goodsTypeEntity;
	    	} catch (Exception e) {
	    		log.info("无法从货物类型缓存中获取数据",e);
	    	}
    	}
    	//判断缓存获取出来的信息是否为空，如果为NULL则按照同等条件扫描数据库查询记录
    	GoodsTypeDto entity = new GoodsTypeDto();
		entity.setCode(goodsTypeCode);
		List<GoodsTypeEntity> goodsTypeEntities = goodsTypeDao.findGoodsTypeByCache(entity, billDate);
		if(CollectionUtils.isNotEmpty(goodsTypeEntities)) {
			return goodsTypeEntities.get(0);
		}
    	return null;
    }
    /**
     * 
     * @Description: 刷新货物缓存数据
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-22 上午10:09:46
     * 
     * @param goodsTypeCode
     * 
     * @version V1.0
     */
    public void refreshGoodsTypeCache(String goodsTypeCode) {
    	if(StringUtil.isNotBlank(goodsTypeCode)) {
    		try {
        		goodsTypeCacheDeal.getGoodsTypeCache().invalid(goodsTypeCode);
    		} catch (Exception e) {
    			log.info("无法刷新货物类型缓存数据",e);
    		}
    	}
    }
    /**
     * 
     * 生成货物类型编号
     * @return 
     */
    private String generateGoodsTypeCode() {
    	String goodsTypeCode = goodsTypeDao.getMaxGoodsTypeCode();
    	String code = null;
    	if(StringUtil.isNotBlank(goodsTypeCode)) {
    		goodsTypeCode = goodsTypeCode.substring(1, NumberConstants.NUMBER_6);
    		int num = Integer.parseInt(goodsTypeCode);
    		num = num + 1;
    		code  = "H" + String.format("%05d", num);
    	} else {
    		code = "H00001";
    	}
    	return code;
    }
    
    /**
     * 判断货物名是否已经存在
     * @param goodsTypeEntity
     * @param isUpdate
     * @return
     */
    private boolean isTheSameGoodsTypeName(GoodsTypeEntity goodsTypeEntity, boolean isUpdate){
    	boolean flag = false;
    	List<GoodsTypeEntity>  goodsTypes = goodsTypeDao.isTheSameGoodsTypeName(goodsTypeEntity.getName());
    	if(goodsTypes != null && goodsTypes.size() > 1){
    		flag = true;
    	}else if(goodsTypes != null && goodsTypes.size() == 1){
    		// 如果修改还得判断是否ID相同
    		if(isUpdate){
    			if(StringUtil.equals(goodsTypeEntity.getId(), goodsTypes.get(0).getId())){
    				flag = false;
    			}else{
    				flag = true;
    			}
    		}else{
    			flag = true;
    		}
    	}else{
    		flag = false;
    	}
    	return flag;
    }
}

