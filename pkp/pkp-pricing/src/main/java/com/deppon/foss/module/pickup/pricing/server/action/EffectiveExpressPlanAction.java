/**
 *  initial comments.
 **  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成： 
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/action/EffectivePlanAction.java
 * 
 * FILE NAME        	: EffectivePlanAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveExpressPlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveExpressPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveExpressPlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveExpressPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.EffectiveExpressPlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.EffectiveExpressPlanVo;

/**
 * 时效方案ACTION
 * 
 * @author DP-Foss-YueHongJie
 * @date 2012-11-21
 * @version 1.0
 */
public class EffectiveExpressPlanAction extends AbstractAction {
		/** 
    	 *
    	 * 支持序列化号
    	 *
    	 */
	private static final long serialVersionUID = 3623796337098362217L;
	/** 
   	 *
   	 * 日志处理
   	 *
   	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EffectiveExpressPlanAction.class);
	/** 
  	 *
  	 * 声明时效方案VO交互对象
  	 *
  	 */
	private EffectiveExpressPlanVo effectiveExpressPlanVo = new EffectiveExpressPlanVo();

	/** 
	 * 
	 *
	 * 获得时效方案VO交互对象 
	 * 
	 */
	public EffectiveExpressPlanVo getEffectiveExpressPlanVo() {
		return effectiveExpressPlanVo;
	}
	/** 
	 *
	 * 设置时效方案VO交互对象
	 *
	 */
	public void setEffectiveExpressPlanVo(
			EffectiveExpressPlanVo effectiveExpressPlanVo) {
		this.effectiveExpressPlanVo = effectiveExpressPlanVo;
	}

	/** 
	 *
	 * 声明时效方案服务
	 *
	 */
	private IEffectiveExpressPlanService effectiveExpressPlanService;
	/** 
	 *
	 * 声明时效方案明细服务
	 *
	 */
	private IEffectiveExpressPlanDetailService effectiveExpressPlanDetailService;
	/** 
	 *
	 * 声明产品服务
	 *
	 */
	private IProductService productService;
	/** 
	 *
	 * 注入产品服务
	 *
	 */
	public void setProductService(IProductService productService) {
	    this.productService = productService;
	}
	
	/** 
	 *
	 * 设置时效方案服务
	 *
	 */
	public void setEffectiveExpressPlanService(
			IEffectiveExpressPlanService effectiveExpressPlanService) {
		this.effectiveExpressPlanService = effectiveExpressPlanService;
	}
	
	/** 
	 *
	 * 设置时效方案明细服务
	 *
	 */
	public void setEffectiveExpressPlanDetailService(
			IEffectiveExpressPlanDetailService effectiveExpressPlanDetailService) {
		this.effectiveExpressPlanDetailService = effectiveExpressPlanDetailService;
	}

	/** 
	 *
	 * 导入文件
	 *
	 */
	private File uploadFile;
	

	/** 
	 *
	 * 导出Excel 文件流
	 *
	 */
	private InputStream inputStream;
	/** 
	 *
	 * 导出Excel 文件名
	 *
	 */
	private String downloadFileName;
	/**
	 * 
	 * <p>设置Excel文件 </p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-3-17 上午10:32:46
	 * @param uploadFile
	 * @see
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	/**
	 * 
	 * <p>获得文件流 </p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-3-17 上午10:32:34
	 * @return
	 * @see
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	/**
	 * 
	 * <p>获得文件名称</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-3-17 上午10:32:21
	 * @return
	 * @see
	 */
	public String getDownloadFileName() {
		return downloadFileName;
	}

	/**
	 * .
	 * <p>
	 * 查询所有的时效方案<br/>
	 * 方法名：searchEffectivePlanByCondition
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-11-21
	 * @since JDK1.6
	 */
	@JSON
	public String searchEffectiveExpressPlanByCondition() {
		try {
			List<EffectiveExpressPlanEntity> effectivePlanEntityList = effectiveExpressPlanService
					.searchEffectiveExpressByCondition(
							effectiveExpressPlanVo.getEffectivePlanEntity(), start,
							limit);
			effectiveExpressPlanVo.setEffectivePlanEntityList(effectivePlanEntityList);
			this.setTotalCount(effectiveExpressPlanService
					.searchEffectiveExpressByConditionCount(effectiveExpressPlanVo
							.getEffectivePlanEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询所有的时效方案: "+e.getMessage());
		    return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 查询所有的时效方案明细<br/>
	 * 方法名：searchEffectivePlanDetailByCondition
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-11-21
	 * @since JDK1.6
	 */
	@JSON
	public String searchEffectiveExpressPlanDetailByCondition() {
		try {
			List<EffectiveExpressPlanDetailEntity> effectivePlanDetailEntityList = effectiveExpressPlanService
					.queryEffectiveExpressPlanDetailInfoPagging(
							effectiveExpressPlanVo.getEffectivePlanDetailEntity(),
							start, limit);
			for(EffectiveExpressPlanDetailEntity entity:effectivePlanDetailEntityList){
				changeDate(entity);
			}
			effectiveExpressPlanVo
					.setEffectivePlanDetailEntityList(effectivePlanDetailEntityList);
			this.setTotalCount(effectiveExpressPlanService
					.queryEffectiveExpressPlanDetailInfoPaggingCount(effectiveExpressPlanVo
							.getEffectivePlanDetailEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询所有的时效方案明细: "+e.getMessage());
		    return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 新增时效方案<br/>
	 * 方法名：addEffectiveExpressPlanEntity
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-11-21
	 * @since JDK1.6
	 */
	@JSON
	public String addEffectiveExpressPlanEntity() {
		try {
			effectiveExpressPlanService.insertEffectiveExpressPlanEntity(effectiveExpressPlanVo.getEffectivePlanEntity());
			return returnSuccess(MessageType.SAVE_EFFECTIVEPLAN_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("新增时效方案: "+e.getMessage());
		    return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 新增时效方案明细<br/>
	 * 方法名：addEffectivePlanDetailEntity
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-11-21
	 * @since JDK1.6
	 */
	@JSON
	public String addEffectiveExpressPlanDetailEntity() {
		try {
			effectiveExpressPlanDetailService.insertEffectiveExpressPlanDetail(effectiveExpressPlanVo.getEffectivePlanDetailEntity());
			return returnSuccess(MessageType.SAVE_EFFECTIVEPLANDETAIL_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("新增时效方案明细: "+e.getMessage());
		    return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 修改时效方案明细<br/>
	 * 方法名：updateEffectivePlanDetailEntity
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-11-21
	 * @since JDK1.6
	 */
	@JSON
	public String updateEffectiveExpressPlanDetailEntity() {
		try {
			effectiveExpressPlanDetailService.updateEffectiveExpressPlanDetail(effectiveExpressPlanVo.getEffectivePlanDetailEntity());
			return returnSuccess(MessageType.UPDATE_EFFECTIVEPLANDETAIL_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("修改时效方案明细: "+e.getMessage());
		    return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 删除时效方案明细<br/>
	 * 方法名：deleteEffectivePlanDetailEntity
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-11-21
	 * @since JDK1.6
	 */
	@JSON
	public String deleteEffectiveExpressPlanDetailEntity() {
		try {
			effectiveExpressPlanDetailService.deleteEffectiveExpressPlanDetail(effectiveExpressPlanVo.getEffectivePlans());
			return returnSuccess(MessageType.DELETE_EFFECTIVEPLANDETAIL_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("删除时效方案明细: "+e.getMessage());
		    return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 修改时效方案<br/>
	 * 方法名：updateEffectivePlanEntity
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-11-21
	 * @since JDK1.6
	 */
	@JSON
	public String updateEffectiveExpressPlanEntity() {
		try {
			effectiveExpressPlanService.modifyEffectiveExpressPlan(effectiveExpressPlanVo
					.getEffectivePlanEntity());
			return returnSuccess(MessageType.UPDATE_EFFECTIVEPLAN_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("修改时效方案: "+e.getMessage());
		    return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 查询时效方案详细信息<br/>
	 * 方法名：searchEffectivePlanById
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-25
	 * @since JDK1.6
	 */
	@JSON
	public String searchEffectiveExpressPlanById() {
		try {
			EffectiveExpressPlanEntity effectivePlanEntity = effectiveExpressPlanService.getEffectiveExpressEntityById(effectiveExpressPlanVo.getEffectivePlanEntity().getId());
			effectiveExpressPlanVo.setEffectivePlanEntity(effectivePlanEntity);
			return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询时效方案详细信息: "+e.getMessage());
		    return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 查询时效方案明细详细信息<br/>
	 * 方法名：searchEffectiveDetailPlanById
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-25
	 * @since JDK1.6
	 */
	@JSON
	public String searchEffectiveExpressDetailPlanById() {
		try {
			EffectiveExpressPlanDetailEntity effectivePlanDetailEntity = effectiveExpressPlanDetailService.getEffectiveExpressDetailEntityById(effectiveExpressPlanVo.getEffectivePlanDetailEntity().getId());
			changeDate(effectivePlanDetailEntity);
			effectiveExpressPlanVo.setEffectivePlanDetailEntity(effectivePlanDetailEntity);
			return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询时效方案明细详细信息: "+e.getMessage());
		    return returnError(e);
		}
	}
	
	private void changeDate(EffectiveExpressPlanDetailEntity entity) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("HHmm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		Date d;
		String leaveTime;
		try {
			d = sdf1.parse(entity.getDeliveryTime());
			leaveTime = sdf2.format(d);
		} catch (ParseException e) {
			leaveTime = entity.getDeliveryTime();
		}
		entity.setDeliveryTime(leaveTime);

		String deadTime;
		try {
			d = sdf1.parse(entity.getArriveTime());
			deadTime = sdf2.format(d);
		} catch (ParseException e) {
			deadTime = entity.getArriveTime();
		}
		entity.setArriveTime(deadTime);
	}

	/**
	 * .
	 * <p>
	 * 升级版本<br/>
	 * 方法名：copyEffectivePlan
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-11-21
	 * @since JDK1.6
	 */
	@JSON
	public String copyEffectiveExpressPlan() {
		try {
			EffectiveExpressPlanEntity effectivePlanEntity = effectiveExpressPlanService
					.copyEffectiveExpressPlan(effectiveExpressPlanVo.getEffectivePlanEntity()
							.getId());
			effectiveExpressPlanVo.setEffectivePlanEntity(effectivePlanEntity);
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("升级版本: "+e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 激活时效方案<br/>
	 * 方法名：activeEffectivePlan
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-11-21
	 * @since JDK1.6
	 */
	@JSON
	public String activeEffectiveExpressPlan() {
		try {
			effectiveExpressPlanService.activeEffectiveExpressPlan(effectiveExpressPlanVo
					.getEffectivePlans());
			return returnSuccess(MessageType.ACTIVE_EFFECTIVEPLAN_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("激活时效方案: "+e.getMessage());
		    return returnError(e);
		}
	}

	/**
	 * 
	 * <p>
	 * 查询三级产品
	 * </p>
	 * 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-21 下午12:19:50
	 * @return
	 * @see
	 */
	@JSON
	public String searchThreeLevelProductExpress() {
		try {
			ProductDto condtion = new ProductDto();
			condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
			List<ProductEntity>  plist= productService.getLevel3ForExpress();
			effectiveExpressPlanVo.setProductEntityList(plist);//
			return returnSuccess();
			
		} catch (BusinessException e) {
		    LOGGER.error("查询三级产品: "+e.getMessage());
		    return returnError(e);
		}
	}

	/**
	 * 
	 * @Description: 导入时效方案 Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-24 下午2:04:08
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	public String importEffectiveExpressPlan() {
		Workbook book = null;
		FileInputStream inputStream = null;
		try {
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				try {
					// 2003
					book = new XSSFWorkbook(inputStream);
				} catch (Exception ex) {
					// 2007
					book = new HSSFWorkbook(inputStream);
				}
			} else {
				throw new FileException("请选择导入文件", "请选择导入文件");
			}
			if (book != null) {
				effectiveExpressPlanService.importEffectiveExpressPlanDetail(book);
			}
			return super.returnSuccess(MessageType.IMPORT_SUCCESS);
		} catch (FileException e) {
			return super.returnError(e);
		} catch (IOException e) {
			return returnError("数据文件被破坏，请重新制作导入文件");
		} catch (BusinessException e) {
		    	LOGGER.error("导入时效方案: "+e.getMessage());
			return returnError(e);
		}finally {
			if (book != null) {
				book = null;
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					return returnError("文件关闭失败");
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 导出时效方案 Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-24 下午2:04:47
	 * 
	 * @version V1.0
	 * 
	 */
	
	public String exportEffectiveExpressPlan() {
		try {
			if(effectiveExpressPlanVo.getEffectivePlanDetailEntity() == null || StringUtil.isEmpty(effectiveExpressPlanVo.getEffectivePlanDetailEntity().getEffectivePlanId())){
				throw new EffectiveExpressPlanException("请选择一个方案进行导出！", "请选择一个方案进行导出！");
			}
			downloadFileName = URLEncoder.encode(
					PricingColumnConstants.EXPORT_EFFECTIVE_PLAN_DETAIL,
					"UTF-8");
			
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName(PricingColumnConstants.EXPORT_EFFECTIVE_PLAN_DETAIL);
			exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
			    
			ExportResource  exportResource  = effectiveExpressPlanService
					.exportEffectiveExpressPlanDetailList(effectiveExpressPlanVo
							.getEffectivePlanDetailEntity());
			
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
		    	LOGGER.error("导出时效方案: "+e.getMessage());
			return returnError("UnsupportedEncodingException", "");
		}
	}
	
	
	
	/**
	 * @Description: 导出时效明细方案 Company:IBM
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-24 下午2:04:47
	 * @version V1.0
	 */
	public String exportEffectiveExpressPlanDetail() {
		try {
			downloadFileName = URLEncoder.encode(
					PricingColumnConstants.EXPORT_EFFECTIVE_PLAN_DETAIL,
					"UTF-8");
			
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName(PricingColumnConstants.EXPORT_EFFECTIVE_PLAN_DETAIL);
			exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
			
			ExportResource exportResource = effectiveExpressPlanService.exportEffectiveExpressPlanDetailList(effectiveExpressPlanVo.getEffectivePlanDetailEntity());
			ExporterExecutor objExporterExecutor = new ExporterExecutor();

			inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
			
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
		    	LOGGER.error("导出时效明细方案: "+e.getMessage());	
			return returnError("UnsupportedEncodingException", "");
		}
	}
	
	
	/**
	 * 
	 * <p>批量删除时效方案</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-1-5 下午8:25:06
	 * @return
	 * @see
	 */
	@JSON
	public String deleteEffectiveExpressPlan(){
	    try {
		effectiveExpressPlanService.deleteEffectiveExpressPlanBatch(effectiveExpressPlanVo.getEffectivePlans());
		return returnSuccess(MessageType.DELETE_SUCCESS);
    	    } catch (BusinessException e) {
    		LOGGER.error("批量删除时效方案: "+e.getMessage());
    		return returnError(e);
    	    }
	}
	
	/**
	 * 
	 * <p>立即停止方案</p> 
	 * @author DP-Foss-yangkang
	 * @date 2014-7-25下午8:42:30
	 * @return
	 * @see
	 */
	@JSON
	public String stopImmediatelyEffectiveExpressPlan(){
	    try {
	    	//采用循环的方式批量中止方案
	    	for(String effectiveStopPlanId:effectiveExpressPlanVo.getEffectivePlans()){
	    		EffectiveExpressPlanEntity effectiveStopPlanEntity = new EffectiveExpressPlanEntity();
	    		effectiveStopPlanEntity.setId(effectiveStopPlanId);
	    		effectiveStopPlanEntity.setEndTime(effectiveExpressPlanVo.getEndTime());
	    		effectiveExpressPlanService.terminateEffectiveExpressPlan(effectiveStopPlanEntity);
	    	}
		return returnSuccess(MessageType.STOP_SUCCESS);
	    } catch (BusinessException e) {
		LOGGER.error("停止方案: "+e.getMessage());
		return returnError(e);
	    }
	}
	
	/**
	 * 
	 * <p>停止方案</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-1-5 下午8:42:30
	 * @return
	 * @see
	 */
	@JSON
	public String stopEffectiveExpressPlan(){
	    try {
		effectiveExpressPlanService.terminateEffectiveExpressPlan(effectiveExpressPlanVo.getEffectivePlanEntity());
		return returnSuccess(MessageType.STOP_SUCCESS);
	    } catch (BusinessException e) {
		LOGGER.error("停止方案: "+e.getMessage());
		return returnError(e);
	    }
	}
	
	/**
	 * 
	 * <p>立即激活方案</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-1-5 下午8:42:30
	 * @return
	 * @see
	 */
	@JSON
	public String immediatelyActiveEffectiveExpressPlan(){
	    try {
	    	
	    	effectiveExpressPlanService.immediatelyActiveEffectiveExpressPlan(effectiveExpressPlanVo.getEffectivePlans(),effectiveExpressPlanVo.getBeginTime());
	    	return returnSuccess(MessageType.ACTIVE_SUCCESS);
	    	
	    } catch (BusinessException e) {
		LOGGER.error("立即激活方案: "+e.getMessage());
		return returnError(e);
	    }
	}
}