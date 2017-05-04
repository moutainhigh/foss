/**
 *  initial comments.
 *  *	SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *  
	     *  
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
	     *    
	     *    
	     *    
	     *    
	     *    
             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
             *
             *
             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
             *
             *
             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
             *
             *
             *		     比对生效日期必须大于上一个版本生效日期。
             *
             *
             *
             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
             *
             *
             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
             *
             *
             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
             *
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *
	     *
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *
	     *
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *  
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *  
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *
	     *
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *
	     *
	     *	即时间可以在今天和今天以后任意调整，
	     *
	     *	但是不能调整为昨天的时间
	     ************************************************************************************* 
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/action/AirPricePlanAction.java
 * 
 * FILE NAME        	: AirPricePlanAction.java
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
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionAirService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.NumericConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionAirEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceManageMentVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * (空运价格方案管理)
 * @author DP-Foss-YueHongJie
 * @date 2012-12-6 下午5:02:03
 * @version 1.0
 */
public class AirPricePlanAction extends AbstractAction {
    /**
     *  序列化 
     *  **/
    private static final long serialVersionUID = 8352568110414814807L;
    /**
     * 
     *  日志处理 
     *  
     *  **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AirPricePlanAction.class);
    /**
     * 
     *  导入文件
     *  
     *   */
    private File uploadFile;
    /**
     * 
     * 导出Excel 文件流
     * 
     */
    private InputStream inputStream;
    /**
     *
     * 产品信息service
     * 
     * 
     */
    private IProductService productService;
    /**
     * 
     * 
     * 导出文件名称
     * 
     * 
     */
    private String downloadFileName;
    /**
     * 
     * 
     * 区域信息service
     * 
     * 
     */
    private IRegionAirService regionAirService;  
    /**
     * 
     * 
     * 最大读取1000列
     * 
     * 
     */
    private final static int CELL_COUNT = 10000;

    /**
     * 
     * 
    * 替代空白字符串""
    * 
    * 
    */
    private static final String BLANK="";
    
    /** 
     * 
     * 价格方案服务 
     * 
     * **/
    private IAirPricePlanService airPricePlanService;
    /**
     * 
     * 
     *  货物类型
     *  
     *  
     */
    IGoodsTypeService goodsTypeService;
    /**
     * 
     * 
     * 数据字典
     * 
     * 
     */
    IDataDictionaryValueService dataDictionaryValueService;
    /** 
     * 
     * 价格方案Vo交互对象 
     * 
     * **/
    private PriceManageMentVo priceManageMentVo = new PriceManageMentVo();
    /**
     * 
     *  价格方案Vo交互对象
     *  
     ***/
    public void setAirPricePlanService(IAirPricePlanService airPricePlanService) {
        this.airPricePlanService = airPricePlanService;
    }
    /** 
     * 
     * 获得价格方案Vo交互对象 
     * 
     * **/
    public PriceManageMentVo getPriceManageMentVo() {
        return priceManageMentVo;
    }
    /** 
     * 
     * 设置价格方案Vo交互对象
     * 
     **/
    public void setPriceManageMentVo(PriceManageMentVo priceManageMentVo) {
        this.priceManageMentVo = priceManageMentVo;
    }
    /** 
     * 
     * 设置下载文件 
     *
     */
    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }
    /** 
     * 
     * 注入产品服务 
     * 
     **/
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
    /**
     * 
     * 获得导出文件名称
     * 
     */
    public String getDownloadFileName() {
        return downloadFileName;
    }

    
    /** 
     * 
     * 注入空运价格区域
     * 
     */
    public void setRegionAirService(IRegionAirService regionAirService) {
        this.regionAirService = regionAirService;
    }
    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }
    /**
     * 
     * <p>获取excel</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-27 上午11:33:22
     * @return
     * @see
     */
    public InputStream getInputStream() {
        return inputStream;
    }
    /**
     * 
     * <p>(查询价格方案信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-6 下午5:03:29
     * @return String
     * @see
     */
    @JSON
    public String queryAirPricePlanBatchInfo(){
		try {
		    priceManageMentVo.setPricePlanEntityList(airPricePlanService.queryAirPricePlanBatchInfo(priceManageMentVo.getPricePlanEntity(),getStart(),getLimit()));
		    this.setTotalCount(airPricePlanService.queryPricePlanBatchInfoCount(priceManageMentVo.getPricePlanEntity()));
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询价格方案信息出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
    /**
     * 
     * <p>(新增价格)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-6 下午5:04:06
     * @return
     * @see
     */
    @JSON
    public String addAirPricePlan(){
	try {
	    PricePlanEntity pricePlanEntity = airPricePlanService.addAirPricePlan(priceManageMentVo.getPricePlanEntity());
	    priceManageMentVo.setPricePlanEntity(pricePlanEntity);
	    return returnSuccess(MessageType.SAVE_PRICE_PLAN_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("新增价格方案信息出现异常: "+e.getMessage());
	    return returnError(e);
	}
    }
    
    /**
     * 
     * <p>(新增价格方案明细信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-14 下午4:21:35
     * @return
     * @see
     */
    public String addAirPricePlanDetail(){
	try {
	    //保存后按照查询该方案下所有的方案明细信息
	    List<PricePlanDetailDto> pricePlanDetailDtos = airPricePlanService.addAirPricePlanDetail(priceManageMentVo.getPricePlanDetailDto());
	    priceManageMentVo.setPricePlanDetailDtoList(pricePlanDetailDtos);
	    return returnSuccess(MessageType.SAVE_PRICE_PLAN_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("新增价格方案明细信息: "+e.getMessage());
	    return returnError(e);
	}
    }
    /**
     * 
     * <p>(查询价格方案明细)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-6 下午5:04:49
     * @return String
     * @see
     */
    @JSON
    public String queryAirPricePlanDetailInfo(){
	try {
	    List<PricePlanDetailDto> pricePlanDetailDtoList = airPricePlanService.queryAirPricePlanDetailInfo(priceManageMentVo.getQueryPricePlanDetailBean(), start*NumericConstants.NUM_2,limit*NumericConstants.NUM_2);
	    priceManageMentVo.setPricePlanDetailDtoList(pricePlanDetailDtoList);
	    Long count = airPricePlanService.queryAirPricePlanDetailInfoCount(priceManageMentVo.getQueryPricePlanDetailBean());
	    this.setTotalCount(count/NumericConstants.NUM_2);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error("查询价格方案明细: "+e.getMessage());
	    return returnError(e);
	}
    }
    /**
     * 
     * <p>
     * 根据空运主方案ID，
     * 删除价格方案信息
     * <br/>
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 上午11:16:32
     * @return
     * @see
     */
    @JSON
    public String deleteAirPricePlan(){
	try {
	    airPricePlanService.deleteAirPricePlan(priceManageMentVo.getPricePlanIds());
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("删除价格方案: "+e.getMessage());
	    return returnError(e);
	}
    }
    /**
     * 
     * <p>根据明细ID集合删除价格方案明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 上午11:16:32
     * @return
     * @see
     */
    @JSON
    public String deleteAirPricePlanDetail(){
	try {
	    List<PricePlanDetailDto>  list = airPricePlanService.deleteAirPriceDetailPlan(priceManageMentVo.getPricePlanDetailIds());
	    priceManageMentVo.setPricePlanDetailDtoList(list);
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("删除价格方案明细: "+e.getMessage());
	    return returnError(e);
	}
    }
    /**
     * 
     * <p>
     * 激活方案,空运方案只
     * 有被激活的才能被GU
     * I端开单所使用
     * <br/>
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-15 上午11:35:50
     * @return String
     * @see
     */
    @JSON
    public String activeAirPricePlan(){
	try {
	    airPricePlanService.activeAirPricePlan(priceManageMentVo.getPricePlanIds());
	    return returnSuccess(MessageType.ACTIVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("激活价格方案: "+e.getMessage());
	    return returnError(e);
	}
    }
    /**
     * 
     * <p>
     * 中止价格方案，根据参与者目的而决定是否要中止该方案的截止日期
     * 由于一个方案活动由开始时间到截止时间制定。参与者应具备对方案的中止功能用于调节
     * 方案的计划
     * </p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-18 下午4:31:32
     * @return
     * @see
     */
    @JSON
    public String stopAirPricePlan(){
	try {
	    airPricePlanService.stopAirPricePlan(priceManageMentVo.getPricePlanEntity());
	    return returnSuccess(MessageType.STOP_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("中止价格方案: "+e.getMessage());
	    return returnError(e);
	}
    }
    /**
     * 
     * <p>(
     * 根据方案ID查询计价方案与所有明细信息
     * )<br/>
     * </p> 
     * 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-20 下午3:29:14
     * @return
     * @see
     */
    @JSON
    public String queryAirPricePlanAndDetailInfoById(){
	try {
	    priceManageMentVo = airPricePlanService.queryCopyAirPricePlanInfo(priceManageMentVo.getPricePlanId());
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error("根据方案ID查询计价方案与所有明细信息: "+e.getMessage());
	    return returnError(e);
	}
    }
    
    @JSON
    public String queryBeforeModifyAirPricePlanDetail(){
	priceManageMentVo.setPricePlanDetailDtoList(airPricePlanService.queryAirPricePlanDetailInfo(priceManageMentVo.getQueryPricePlanDetailBean()));
	return returnSuccess();
    }
    /**
     * 
     * <p>(修改保存方案主信息以及明细信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-20 下午3:29:14
     * @return
     * @see
     */
    @JSON
    public String updateAirPricePlan(){
	try {
	    PricePlanEntity dbEntity = airPricePlanService.modifyAirPricePlan(priceManageMentVo.getPricePlanEntity());
	    priceManageMentVo.setPricePlanEntity(dbEntity);
	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("修改保存方案主信息以及明细信息: "+e.getMessage());
	    return returnError(e);
	}
    }
    /**
     * 
     * <p>复制方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-23 下午3:32:22
     * @return
     * @see
     */
    @JSON
    public String copyAirPricePlan(){
	try {
	    //根据界面已选空运价格主方案ID复制一份新的方案
	    //并且返回最新复制的方案ID信息
	    String pricePlanId = airPricePlanService.copyAirPricePlan(priceManageMentVo.getPricePlanId());
	    //根据最新返回的复制方案ID查询相关信息进行显示
	    priceManageMentVo = airPricePlanService.queryCopyAirPricePlanInfo(pricePlanId);
	    return returnSuccess(MessageType.COPY_PRICE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("复制价格方案: "+e.getMessage());
	    return returnError(e);
	}
    }
	/**
	 * 导入方案和方案明细
	 * 
	 * @author zhangdongping
	 * @date 2012-12-10下午4:51:08
	 */
	public String importAirPrice() {
	    //初始化
		Workbook book = null;
		FileInputStream inputStream = null;
		try {
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				try {
					// 如果是2003版本
					book = new XSSFWorkbook(inputStream);
				} catch (Exception ex) {
					// 如果是2007版本
					book = new HSSFWorkbook(inputStream);
				}
			} else {
				throw new FileException("请选择导入文件", "请选择导入文件");
			}
			if (book != null) {
				// 默认获取获取工作表1
				Sheet sheet = book.getSheetAt(0);//默认第一个
				// 读取Excel的所用数据 
				//读取最终数据结果，key为始发区域名称 ，值为价格数据明细
				Map<String,List<PricePlanDetailDto>> detailMap=new HashMap<String,List<PricePlanDetailDto>>(); 
				//收集excel文件中的价格区域map ，key为区域名称，值为区域名称 
				Map<String,String> regionMap=new HashMap<String,String>();
				//收集excel文件中的产品信息map，key为产品名称，值为产品名称
				 Map<String,String> productMap=new HashMap<String,String>();	
				 Map<String,String> goodsTypeMap=new HashMap<String,String>();
				 Map<String,String> flightShiftMap=new HashMap<String,String>();
				 Map<String,String> combBillTypeMap=new HashMap<String,String>();	//zxy 20140509 MANA-1253 新增
				// 将Excel表格每行数据读入列表，开始收集信息，并作必要的检查
				this.makeExcelDtos(detailMap, regionMap,productMap,goodsTypeMap,flightShiftMap,combBillTypeMap,sheet);
				if (detailMap == null || detailMap.size() < 1) {
					throw new FileException("导入数据为空,请检查", "导入数据为空,请检查");
				}
				//把产品信息map通过service查询出相关产品的实体
				Map<String,ProductEntity > productEntityMap = processProductInfo(productMap); 
				//把区域信息map通过service查询出相关的区域信息
				Map<String,PriceRegionAirEntity> priceRegionEntityMap = processPriceRegionInfo(regionMap);  
				//把产品信息map通过service查询出相关的区域信息
				Map<String,GoodsTypeEntity > goodsTypeEntityMap = processGoodsTypeInfo(goodsTypeMap);
				//把航班类别信息map通过service查询出相关的区域信息
				Map<String,String > flightShiftEntityMap = processFlightShiftInfo(flightShiftMap);
				//把航班类别信息map通过service查询出相关的区域信息
				Map<String,String > combBillTypeEntityMap = processCombBillTypeInfo(combBillTypeMap);	//zxy 20140509 MANA-1253 新增
				//批量新增价格数据
				airPricePlanService.addAirPricePlanBatch(detailMap,priceRegionEntityMap,productEntityMap,goodsTypeEntityMap,flightShiftEntityMap,combBillTypeEntityMap) ;				  
			}
			 return super.returnSuccess();
		} catch (FileException e) { 
		    	return super.returnError(e);
		}catch (IOException e) {
		    LOGGER.error(e.getMessage(), e);
			return returnError("数据文件被破坏，请重新制作导入文件");
		} finally {
			if (book != null) {
				book = null;
			}
			if (inputStream != null) {
				try {
				inputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				return returnError("文件关闭失败");
				}
			}
		}
	}
	/**
	 * 将Excel表格每行数据读入列表
	 * 
	 *  zhangdongping
	 * @date 2012-12-10 上午10:46:31
	 * @update 2014-05-09 zxy MANA-1253 修改:增加合票类型字段
	 */
	private void makeExcelDtos(Map<String,List<PricePlanDetailDto>> detailMap , Map<String,String> regionMap,Map<String,String> productMap ,Map<String,String> goodsTypeMap,Map<String,String> flightShiftMap,Map<String,String> combBillTypeMap,Sheet sheet) {	       
	    Map<String,String> detailCheckMap=new HashMap<String,String>();
		if (sheet != null && detailMap != null) {
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// 根据行数循环
			Row row = null;
			// EXCEL行记录
			if (rowCount<NumericConstants.NUM_2 )
			{
			    return ;//no data
			}
			//取得第一行数据
			row = sheet.getRow(0);
			//看第一行到底有多少列数据，从第6列开始，是目的城市数据
			Map<String,String> destRegionNameMap=new HashMap<String,String>();
			Map<String,String> checkdestRegionNameMap=new HashMap<String,String>();
			for (int colNum = NumericConstants.NUM_6; colNum <CELL_COUNT; colNum++) {				 
			    Cell cell = row.getCell(colNum);
				if (cell != null) {
					// 取单元格值
					String cellVal = ExcelHandleUtil.obtainStringVal(cell);
					if (StringUtil.isNotBlank(cellVal)) {
					    destRegionNameMap.put(colNum+BLANK, cellVal);
					    if(checkdestRegionNameMap.get(cellVal)==null)
					    {
						checkdestRegionNameMap.put(cellVal, cellVal);//把目的地放入map，作为目的地只能有一条的判断数据源						
					    }
					    else
					    {
					      //目的地重复了
					   throw new FileException("目的地"+cellVal+"重复，请检查数据", "目的地"+cellVal+"重复，请检查数据");
					    }
					}else
					{
					    break;//结束，没有数据了
					}
					    
				}
				 
		        }
			//目的地SIZE
			int totalColume = destRegionNameMap.size();
			// 根据行数循环
			for (int rowNum = 0; rowNum < rowCount/NumericConstants.NUM_2; rowNum++) {
				// 获取每行数据
			        
				// 取得一行的数据
				Row row1 = sheet.getRow(rowNum*NumericConstants.NUM_2 +1); 
				//取始发区域名字 				
				Cell cell = row1.getCell(0);  
				String  deptRegionName=null;
				if (cell != null) {
					// 取单元格值
					   deptRegionName = ExcelHandleUtil.obtainStringVal(cell);
					if (StringUtil.isBlank(deptRegionName)) {
					    break;//结束，没有数据了 
					}  
				}else
				{
				    break;//结束，没有数据了 
				}
				List<PricePlanDetailDto> alist = detailMap.get(deptRegionName);
				if(CollectionUtils.isEmpty(alist))
				{
				    alist=new ArrayList<PricePlanDetailDto>();
				    detailMap.put(deptRegionName, alist);
				}
				
				//取是否接货
				String reacheGoods=null;
				String reacheGoodsName=null;
				cell = row1.getCell(1);  
				if (cell != null) {
					// 取单元格值
					   reacheGoods = ExcelHandleUtil.obtainStringVal(cell);
					   reacheGoodsName=reacheGoods;
					if (StringUtil.isBlank(reacheGoods)) {
					    throw new FileException("第"+(rowNum+NumericConstants.NUM_2)+"行第2列数据不正确，请检查", "第"+(rowNum+NumericConstants.NUM_2)+"行第2列数据不正确，请检查");
					}  
					if(reacheGoods.equalsIgnoreCase("是"))
					{
					    reacheGoods=FossConstants.YES;
					}else
					{
					    reacheGoods=FossConstants.NO;
					}
				}else
				{
				    throw new FileException("第"+(rowNum+NumericConstants.NUM_2)+"行第2列数据不正确，请检查", "第"+(rowNum+NumericConstants.NUM_2)+"行第2列数据不正确，请检查");
				}
				//取产品信息
				String productNames=null;				
				cell = row1.getCell(NumericConstants.NUM_2);  
				if (cell != null) {
					// 取单元格值
				        productNames = ExcelHandleUtil.obtainStringVal(cell);
					if (StringUtil.isBlank(productNames)) {
					    throw new FileException("第"+(rowNum+NumericConstants.NUM_2)+"行第3列数据不正确，请检查", "第"+(rowNum+NumericConstants.NUM_2)+"行第3列数据不正确，请检查");
					}  
				}else
				{
				    throw new FileException("第"+(rowNum+NumericConstants.NUM_2)+"行第3列数据不正确，请检查", "第"+(rowNum+NumericConstants.NUM_2)+"行第3列数据不正确，请检查");
				}
				
				//取货物类型
				String goodsTypeNames=null;				
				cell = row1.getCell(NumericConstants.NUM_3);  
				if (cell != null) {
					// 取单元格值
				    goodsTypeNames = ExcelHandleUtil.obtainStringVal(cell);
					if (StringUtil.isBlank(goodsTypeNames)) {
					    throw new FileException("第"+(rowNum+NumericConstants.NUM_2)+"行第4列数据不正确，请检查", "第"+(rowNum+NumericConstants.NUM_2)+"行第4列数据不正确，请检查");
					}  
				}else
				{
				    throw new FileException("第"+(rowNum+NumericConstants.NUM_2)+"行第4列数据不正确，请检查", "第"+(rowNum+NumericConstants.NUM_2)+"行第4列数据不正确，请检查");
				}
				
				//zxy 20140509 MANA-1253 start 新增:合票类型
				//合票类型
				String combBillTypeNames=null;				
				cell = row1.getCell(NumericConstants.NUM_4);  
				if (cell != null) {
					// 取单元格值
					combBillTypeNames = ExcelHandleUtil.obtainStringVal(cell);
					if (StringUtil.isBlank(combBillTypeNames)) {
					    throw new FileException("第"+(rowNum+NumericConstants.NUM_2)+"行第5列数据不正确，请检查", "第"+rowNum+NumericConstants.NUM_2+"行第5列数据不正确，请检查");
					}  
				}else
				{
				    throw new FileException("第"+(rowNum+NumericConstants.NUM_2)+"行第5列数据不正确，请检查", "第"+(rowNum+NumericConstants.NUM_2)+"行第5列数据不正确，请检查");
				}
				//zxy 20140509 MANA-1253 end 新增:合票类型
				
				//取航班类型
				String flightShiftNames=null;				
				cell = row1.getCell(NumericConstants.NUM_5);  
				if (cell != null) {
					// 取单元格值
				    flightShiftNames = ExcelHandleUtil.obtainStringVal(cell);
					if (StringUtil.isBlank(flightShiftNames)) {
					    throw new FileException("第"+(rowNum+NumericConstants.NUM_2)+"行第6列数据不正确，请检查", "第"+rowNum+NumericConstants.NUM_2+"行第6列数据不正确，请检查");
					}  
				}else
				{
				    throw new FileException("第"+(rowNum+NumericConstants.NUM_2)+"行第6列数据不正确，请检查", "第"+(rowNum+NumericConstants.NUM_2)+"行第6列数据不正确，请检查");
				}
				
				
				//再取1行数据，准备分析。因为2行数据是一个整体，所以一次取出来
				Row row2 = sheet.getRow(rowNum*NumericConstants.NUM_2 +NumericConstants.NUM_2);
				 			 
				for (int colNum = NumericConstants.NUM_7; colNum <totalColume+NumericConstants.NUM_7; colNum++) {
				    PricePlanDetailDto detailDto=new PricePlanDetailDto();
				    Cell cell1 = row1.getCell(colNum);  
				    Cell cell2 = row2.getCell(colNum);  
				   
				    //判断数据格式正确否
				   if(cell1!=null&&cell1.getCellType()!= HSSFCell.CELL_TYPE_NUMERIC )
				   {
				       throw new FileException("第"+(rowNum*NumericConstants.NUM_2 +NumericConstants.NUM_2)+"行第"+(colNum+1)+"列数据不正确，请检查", "第"+(rowNum*NumericConstants.NUM_2 +NumericConstants.NUM_2)+"行第"+(colNum+1)+"列数据不正确，请检查");
				   }
				   if( cell2!=null&&cell2.getCellType()!= HSSFCell.CELL_TYPE_NUMERIC )
				   {
				       throw new FileException("第"+(rowNum*NumericConstants.NUM_2 +NumericConstants.NUM_3)+"行第"+(colNum+1)+"列数据不正确，请检查", "第"+(rowNum*NumericConstants.NUM_2+NumericConstants.NUM_3)+"行第"+(colNum+1)+"列数据不正确，请检查");
				   }
				 
				   if(cell1==null||cell2==null)
				    {
					continue;//目的区域没数据停止读取，直接下一个处理
				    }
				   //读出清货价格和重货价格
				   double weightRate = cell1.getNumericCellValue();  
				   if(weightRate<=0)
				   {
				       continue;
				   }
				   Long minFee = null;
				   try {
					   //检查最低一票必须是整形
					   String mindoubleVal=cell2.getNumericCellValue()+BLANK;
					   String subValue=mindoubleVal.substring(mindoubleVal.lastIndexOf("."));
					   double subValueD=new Double(subValue);
					   if(BigDecimal.ZERO.compareTo(BigDecimal.valueOf(subValueD)) != 0 )
					   {
						   throw new FileException("第"+(rowNum*NumericConstants.NUM_2+NumericConstants.NUM_3)+"行第"+(colNum+1)+"列最低一票数据必须为整数，请检查", "第"+(rowNum*NumericConstants.NUM_2+NumericConstants.NUM_3)+"行第"+(colNum+1)+"列最低一票数据必须为整数，请检查");
					   }
					   minFee = Double.valueOf(cell2.getNumericCellValue()).longValue() ;
				   } catch (Exception e) {
					   throw new FileException("第"+(rowNum*NumericConstants.NUM_2+NumericConstants.NUM_3)+"行第"+(colNum+1)+"列最低一票数据必须为整数，请检查", "第"+(rowNum*NumericConstants.NUM_2+NumericConstants.NUM_3)+"行第"+(colNum+1)+"列最低一票数据必须为整数，请检查");
				   }
				   //开始组装数据
				   
				   //设置目的区域名称
				   detailDto.setArrvRegionName(destRegionNameMap.get(colNum+BLANK));
				   //设置是否接货
				   detailDto.setCentralizePickup(reacheGoods);
				   //设置产品名称
				   detailDto.setProductItemName(productNames);
				   //设置货物名称
				   detailDto.setGoodsTypeName(goodsTypeNames);
				   //设置航班名称
				   detailDto.setFlightTypeName(flightShiftNames);
				   //设置合票类型
				   detailDto.setCombBillTypeName(combBillTypeNames);	//zxy 20140509 MANA-1253 新增
				   //设置重货价格
				   detailDto.setHeavyPrice(new BigDecimal(weightRate +BLANK));
				   //设置轻货价格
				   detailDto.setLightPrice(new BigDecimal(weightRate +BLANK));
				   //设置最低一票
				   detailDto.setMinimumOneTicket(minFee);				 				   
				  
				   //检查 始发区域，到达区域 ，是否接货，货物类型，航班类型是否在detailCheckMap中出现重复
				   //出现重复那么就要提示异常信息了。
				   String checkKey=deptRegionName+"到"+detailDto.getArrvRegionName()+",是否接货："+reacheGoodsName+",产品类型:"
				   + detailDto.getProductItemName()+BLANK+",货物类型:"+goodsTypeNames+",航班类型:"+flightShiftNames+",合票类型:"+combBillTypeNames;
				   if( detailCheckMap.get(checkKey)==null)
				   {
				       detailCheckMap.put(checkKey, checkKey);
				   }
				   else
				   {
				       throw new FileException( checkKey+"数据重复，请检查！",  checkKey+"，数据重复，请检查！");
				   }
				   alist.add(detailDto);
				   //处理相关通过校验后的数据信息
				   regionMap.put(detailDto.getArrvRegionName().trim(), detailDto.getArrvRegionName().trim());  
				   regionMap.put(deptRegionName.trim(), deptRegionName.trim());
				   productMap.put(productNames, productNames);
				   goodsTypeMap.put(goodsTypeNames, goodsTypeNames);
				   flightShiftMap.put(flightShiftNames, flightShiftNames);
				   combBillTypeMap.put(combBillTypeNames, combBillTypeNames);
				}
			}
		}

	}

	 
	 /**
	 * 处理产品信息，避免service中事物处理过长
	 * @author zhangdongping
	 * @date 2012-12-24 下午5:46:52
	 * @param nameMap
	 * @return
	 * @see
	 */
	private Map<String,ProductEntity> processProductInfo(Map<String,String> nameMap)
	 {
	    //非空判断
	     if(nameMap==null||nameMap.size()<1)
	     {
		 throw new FileException( "导入数据中没有产品信息，请检查", "导入数据中没有产品信息，请检查" );
	     }
	     //读取二级产品，因为价格数据都是二级的
	     List<ProductEntity> productInfoList= productService.queryLevel2ProductInfo( );
	     if(CollectionUtils.isEmpty(productInfoList))
	     {
		 throw new FileException( "数据库中没有产品信息，请检查", "数据库中没有产品信息，请检查" );
	     }
	     Map<String,ProductEntity> nameEntityMap=new HashMap<String,ProductEntity>();
	     Iterator<String> keyIt = nameMap.keySet().iterator();
	     while(keyIt.hasNext())
	     {
		 //逐个比对，是否导入的产品在数据库里存在
		String productname= keyIt.next();
		for(int loop=0;loop<productInfoList.size();loop++)
		{
		   ProductEntity productEntity = productInfoList.get(loop);
		   if(StringUtil.equals(productname, productEntity.getName()))
		   {
		       nameEntityMap.put(productname, productEntity);
		       //找到后这次循环结束		       
		       break;
		   } 
		}
		if(nameEntityMap.get(productname)==null)
		{
		    //最后都没找到，那就是出问题了
		    throw new FileException( "数据库中没有产品:"+productname+"，请检查", "数据库中没有产品:"+productname+"，请检查" );
		}
	     } 
	      return nameEntityMap;
	 }
	 /**
	 *  处理价格区域信息，批量根据名字把区域查询出来
	 * @author zhangdongping
	 * @date 2012-12-24 下午5:49:54
	 * @param regionMap
	 * @return
	 * @see
	 */
	private Map<String, PriceRegionAirEntity> processPriceRegionInfo(
			Map<String, String> regionMap) {
	    //非空判断
	     if(regionMap==null||regionMap.size()<1)
	     {
		 throw new FileException( "导入数据中没有区域信息，请检查", "导入数据中没有区域与信息，请检查" );
	     }
	     List< String>  names =new ArrayList<String>(); 
	     names.addAll(regionMap.keySet());
	     //批量查找区域数据
	     Map<String, PriceRegionAirEntity> findResult = regionAirService.findRegionByNames(names, PricingConstants.PRICING_REGION, new Date());
	     if(findResult==null||findResult.size()<1)
	     {
		 
		 throw new FileException( "数据库中没有匹配到导入的任何区域数据，请检查", "数据库中没有匹配到导入的任何区域数据，请检查" );
	     }
	    Iterator<String> it = regionMap.keySet().iterator();
	    while(it.hasNext())
	    {
		//逐条验证有没有价格区域
		String object = it.next();
		if(findResult.get(object)==null)
		{
		    throw new FileException( "区域："+object+"，在数据库中没有找到，请检查", "区域："+object+"，在数据库中没有找到，请检查" );
		}
	    }
	     return findResult;
	 }
	/**
	 * <p>处理航班类型信息</p> 
	 * @author zhangdongping
	 * @date 2013-3-14 下午4:51:03
	 * @param flightShiftMap
	 * @return
	 * @see
	 */
	private Map<String, String> processFlightShiftInfo(
		Map<String, String> nameMap) {
	    //非空判断
	     if(nameMap==null||nameMap.size()<1)
	     {
		 throw new FileException( "导入数据中没有航班信息，请检查", "导入数据中没有航班信息，请检查" );
	     } 
	     List<DataDictionaryValueEntity> infoList= dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.AIR_FLIGHT_TYPE);
	     if(CollectionUtils.isEmpty(infoList))
	     {
		 throw new FileException( "导入数据中没有航班信息，请检查", "导入数据中没有航班信息，请检查" );
	     }
	     Map<String, String > nameEntityMap=new HashMap<String,String>();
	     Iterator<String> keyIt = nameMap.keySet().iterator();
	     while(keyIt.hasNext())
	     {
		 //逐个比对，是否导入的航班在数据库里存在
		String name= keyIt.next();
		for(int loop=0;loop<infoList.size();loop++)
		{
		    DataDictionaryValueEntity goodsTypeEntity = infoList.get(loop);
		   if(StringUtil.equals(name, goodsTypeEntity.getValueName()))
		   {
		       nameEntityMap.put(name, goodsTypeEntity.getValueCode());
		       //找到后这次循环结束		       
		       break;
		   } 
		}
		if(nameEntityMap.get(name)==null)
		{
		    //最后都没找到，那就是出问题了
		    throw new FileException( "导入数据中没有航班信息:"+name+"，请检查", "导入数据中没有航班信息:"+name+"，请检查" );
		}
	     } 
	     
	      return nameEntityMap; 
	}
	
	/**
	 * <p>处理合票类型信息</p> 
	 * @param nameMap
	 * @return Map
	 * 版本号 		时间		用户			内容
	 * 0001		2014509  zxy		新增
	 */
	private Map<String, String> processCombBillTypeInfo(
		Map<String, String> nameMap) {
	    //非空判断
	     if(nameMap==null||nameMap.size()<1)
	     {
		 throw new FileException( "导入数据中没有航班信息，请检查", "导入数据中没有航班信息，请检查" );
	     } 
	     List<DataDictionaryValueEntity> infoList= dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.MAKE_WAYBILL_WAY);
	     if(CollectionUtils.isEmpty(infoList))
	     {
		 throw new FileException( "导入数据中没有航班信息，请检查", "导入数据中没有航班信息，请检查" );
	     }
	     Map<String, String > nameEntityMap=new HashMap<String,String>();
	     Iterator<String> keyIt = nameMap.keySet().iterator();
	     while(keyIt.hasNext())
	     {
		 //逐个比对，是否导入的航班在数据库里存在
		String name= keyIt.next();
		for(int loop=0;loop<infoList.size();loop++)
		{
		    DataDictionaryValueEntity goodsTypeEntity = infoList.get(loop);
		   if(StringUtil.equals(name, goodsTypeEntity.getValueName()))
		   {
		       nameEntityMap.put(name, goodsTypeEntity.getValueCode());
		       //找到后这次循环结束		       
		       break;
		   } 
		}
		if(nameEntityMap.get(name)==null)
		{
		    //最后都没找到，那就是出问题了
		    throw new FileException( "导入数据中没有航班信息:"+name+"，请检查", "导入数据中没有合并信息:"+name+"，请检查" );
		}
	     } 
	     
	      return nameEntityMap; 
	}

	/**
	 * <p>
	 * 处理货物类型信息
	 * 该方法主要根据excel中的货物信息去数据库匹配
	 * 是否存在该货物信息，以确保excel所录入的货物类型
	 * 信息的正确性。
	 * </p> 
	 * @author zhangdongping
	 * @date 2013-3-14 下午4:51:53
	 * @param goodsTypeMap
	 * @return
	 * @see
	 */
	private Map<String, GoodsTypeEntity> processGoodsTypeInfo(
		Map<String, String> nameMap) {
	    
	    //非空判断
	     if(nameMap==null||nameMap.size()<1)
	     {
		 throw new FileException( "导入数据中没有货物类型信息，请检查", "导入数据中没有货物类型信息，请检查" );
	     }
	     //读取货物类型信息
	     GoodsTypeEntity entity=new GoodsTypeEntity();
	     entity.setActive(FossConstants.ACTIVE);
	     entity.setBillDate(new Date());
	     
	     List<GoodsTypeEntity> infoList= goodsTypeService.findGoodsTypeByCondiction(entity);
	     if(CollectionUtils.isEmpty(infoList))
	     {
		 throw new FileException( "导入数据中没有货物类型信息，请检查", "导入数据中没有货物类型信息，请检查" );
	     }
	     Map<String, GoodsTypeEntity > nameEntityMap=new HashMap<String,GoodsTypeEntity>();
	     Iterator<String> keyIt = nameMap.keySet().iterator();
	     while(keyIt.hasNext())
	     {
		 //逐个比对，是否导入的货物类型在数据库里存在
		String name= keyIt.next();
		for(int loop=0;loop<infoList.size();loop++)
		{
		    GoodsTypeEntity goodsTypeEntity = infoList.get(loop);
		   if(StringUtil.equals(name, goodsTypeEntity.getName()))
		   {
		       nameEntityMap.put(name, goodsTypeEntity);
		       //找到后这次循环结束		       
		       break;
		   } 
		}
		if(nameEntityMap.get(name)==null)
		{
		    //最后都没找到，那就是出问题了
		    throw new FileException( "数据库中没有货物类型:"+name+"，请检查", "数据库中没有货物类型:"+name+"，请检查" );
		}
	     } 
	      return nameEntityMap;
	}
	/**
	 * 
	 * <p>
	 * 价格方案导出
	 * 导出当前所查询出价格方案的excel信息，
	 * 主要包括方案的名称，开始时间，结束时间，以及出发区域<br/>
	 * </p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-25 上午10:54:12
	 * @return
	 * @see
	 */
	public String exportAirPricePlan() {
	    try {
		//文件名称
		String fileName = PricingColumnConstants.EXPORT_PRICE_PLAN;
		//下载文件名称
		downloadFileName = URLEncoder.encode(DateUtils.convert(new Date(),DateUtils.DATE_FORMAT) + fileName ,"UTF-8");
		//判断输入参数是否为空且给以默认对象
		if(null == priceManageMentVo.getPricePlanEntity()){
		    priceManageMentVo.setPricePlanEntity(new PricePlanEntity());
		}
		//导出设置
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName(PricingColumnConstants.EXPORT_PRICE_PLAN);
		exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
		//导出工具类
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		// 存放导出数据
		ExportResource exportResource = airPricePlanService.exportAirPricePlan(priceManageMentVo.getPricePlanEntity());
		/** 超过1w笔不能以IO形势返回 **/
		inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		return returnSuccess();
	    } catch (UnsupportedEncodingException e) {
		LOGGER.error("价格方案导出，出现异常: "+e.getMessage());
		return returnError("价格方案明细导出，出现异常",e.getMessage());
	    }
	}
	/**
	 * 
	 * <p>
	 * 价格方案明细导出
	 * 导出信息主要包括，目的区域，产品信息， 
	 * 货物信息，航班类型。
	 * </p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-25 上午10:54:12
	 * @return
	 * @see
	 */
	public String exportAirPricePlanDetail() {
	    try {
		//文件名称
		String fileName = PricingColumnConstants.EXPORT_PRICE_PLAN_DETAIL;
		//文件下载名称
		downloadFileName = URLEncoder.encode(DateUtils.convert(new Date(),DateUtils.DATE_FORMAT) + fileName,"UTF-8");
		//导出设置
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName(PricingColumnConstants.EXPORT_AIR_PRICE_PLAN_DETAIL);
		exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
		// 存放导出数据
		ExportResource exportResource = airPricePlanService.exportAirPricePlanDetail(priceManageMentVo.getQueryPricePlanDetailBean());
		//导出工具类
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		/** 超过1w笔不能以IO形势返回 **/
		inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		return returnSuccess();
	    } catch (UnsupportedEncodingException e) {
		LOGGER.error("价格方案明细导出，出现异常: "+e.getMessage());
		return returnError("价格方案明细导出，出现异常",e.getMessage());
	    }
	}
	/**
	 * 
	 * <p>
	 *立即激活空运方案，
	 *对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
	 *立即激活功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即激活功能。
	 *防止一般用户进行当天频繁调价操作<br/>
	 * 方法名称：immediatelyActiveAirPricePlan </p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-3-17 上午10:15:13
	 * @return
	 * @see
	 */
	public String immediatelyActiveAirPricePlan(){
	    try {
		    airPricePlanService.immediatelyActiveAirPricePlan(priceManageMentVo.getPricePlanEntity());
		    return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("立即激活价格方案: "+e.getMessage());
		    return returnError(e);
		}
	}
	/**
	 * 
	 * <p>修改价格方案明细</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-2-28 下午7:54:52
	 * @return
	 * @see
	 */
	public String updateAirPriceDetailPlan(){
	    try {
		    priceManageMentVo.setPricePlanDetailDtoList(airPricePlanService.modifyAirPricePlanDetail(priceManageMentVo));
		    return returnSuccess(MessageType.UPDATE_SUCCESS);
	    } catch (BusinessException e) {
		    LOGGER.error("修改保存方案主信息以及明细信息: "+e.getMessage());
		    return returnError(e);
	    }
	}
	/**
	 * 
	 * <p>注入设置货物</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-3-14 下午5:40:52
	 * @param goodsTypeService
	 * @see
	 */
	public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
	       this.goodsTypeService = goodsTypeService;
	}
	/**
	 * 
	 * <p>注入数据字典</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-3-14 下午5:40:52
	 * @param goodsTypeService
	 * @see
	 */
	public void setDataDictionaryValueService(
		IDataDictionaryValueService dataDictionaryValueService) {
	    this.dataDictionaryValueService = dataDictionaryValueService;
	}
}

