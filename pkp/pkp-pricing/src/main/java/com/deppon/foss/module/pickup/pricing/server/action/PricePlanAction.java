/**
 * 
 * 
 * SR1	
 * 
 * 弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
 *  
*出发信息包括：
*
* a) 生效日期: 
* 
* 设定生效日期在提交的时候判断该新建的版本信息以出发地作为标准来判断是否已经存在，
* 
* 没有存在则正常录入且截止日期默认为2999-01-01，
* 
* 生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况），
* 
*  如果存在则是需要对原有数据作变更的概念，
*  
*  需要校验生效日期在上一个最新启用的版本中
*  
*  （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
*  
*  比对生效日期必须大于上一个版本生效日期。
*  
*b) 始发区域: 
*
*区域信息来自产品基础数据维护下的区域来填充该下拉表请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
*
*（区域分别有时效与价格，在此只需要获取价格区域）        
*
*   c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
*   
*   D) 方案描述：  对建立新方案的一些描述信息。
*   
*目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为。
*
*每一笔明细保存都会记录在数据库中以确保数据的完整性。
*
*e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
*
*SR2	
*
*目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
*
*SR3	
*
*1．	最低一票根据所选择的条目信息带出默认最低一票设置，
*
*在此可做修改并将其绑定到区域与价格上
*
*SR4	
*
* 版本号无意义， 都是根据生效时间来做版本控制。譬如有一个汽运价格方案生效日期为2010-05-31 截止日期为2999-01-01，升级时候生效日期为2012-12-11，那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。这样在两段不同时间轴中出现两个不同价格的版本信息。
*SR5	
*
*新增价格方案时，所选择的区域信息（始发区域/目的地区域）都需要过滤（由于区域管理按照价格时效部门不同做以划分），只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
*
*SR6	
*
*图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，校验提示、“****到达城市已经存在， 请检查excel模版”
*
*SR7	
*
*汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,是否立即添加相应目的地价格明细信息?” 选择“确定”，回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
*
*SR8	
*
*在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，请确认是否以该方案为准,请将xxx方案中止。”
*
*SR9	
*
*立即中止功能： 在价格查询列表中，只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” ，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
*
*SR10	
*
*立即激活功能： 在价格查询列表中，只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时间调整为当前设置的生效时间，出现小于当前营业日期系统提示“立即激活操作的生效时间必须大于等于营业日期!” ，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间
*
*
*出发地信息
*
*始发区域	区域信息	下拉列表		100	是
*
*生效日期	生效日期	文本框	请输入价格方案名称	200	是
*
*是否生效	是否生效	单选按钮		50	是
*
*方案描述	备注	文本域		500	是
*
*
*
*
*目的城市价格设定信息
*
*目的地	绑定目的城市	输出	100
*
*产品条目	绑定产品条条目设定价格信息	输出	 100
*
*重货价格	设置从出发城市到目的地城市的重货价格	输出	20
*
*轻货价格	设置从出发城市到目的地城市的轻货价格	输出	20
*
*最低一票	最低一票设定	输出	20
*
*是否集中接货	用于区别是否上门不上门的价格维护	输出	20
*
*备注	其他备注信息	输出	500
*
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * 
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/action/PricePlanAction.java
 * 
 * FILE NAME        	: PricePlanAction.java
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
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.NumericConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceManageMentVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 汽运价格方案管理
 * 
 * 汽运价格方案管理主要维护参与者能够通过该功能进行维护出发地到目的地以及产品、
 * 
 * 货物类型进行设置重货价格、轻货价格信息、最低一票
 * 
 * 汽运价格和空运价格区别在于由于空运的多变性价格调价频率比汽运要高出很多。
 * 
 * @author DP-Foss-YueHongJie
 * 
 * @date 2012-12-6 下午5:02:03
 * 
 * @version 1.0
 */
public class PricePlanAction extends AbstractAction {
    /**
     * 
     * 序列化
     * 
     */
    private static final long serialVersionUID = 8352568110414814807L;
    /**
     * 
     * 日志处理
     * 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PricePlanAction.class);
    /**
     * 
     *
     * 导入文件
     *
     */
    private File uploadFile;
    /**
     * 
     * 
     * 导出Excel 文件流
     * 
     */
    private InputStream inputStream;
    /**
     * 
     * 产品信息service
     * 
     */
    private IProductService productService;
    /**
     * 
     * 导出文件名称
     * 
     */
    private String downloadFileName;
    /**
     * 
     * 区域信息service
     * 
     */
    private IRegionService regionService;
    
    
    /**
     * 汽运到达区域
     */
    private IRegionArriveService regionArriveService;
    
    /**
     * 汽运到达区域
     */
    public void setRegionArriveService(IRegionArriveService regionArriveService) {
		this.regionArriveService = regionArriveService;
	}
	/**
     * 
     * 最大读取10000列
     * 
     */
    private static final int CELL_COUNT = 2000;

    /**
     * 
     * 替代空白字符串""
     * 
     */
    private static final String BLANK="";
    /**
     *
     * 价格方案服务
     *  
     */
    private IPricePlanService pricePlanService;
    /**
     * 
     * 汽运价格方案 VO 
     * 
     */
    private PriceManageMentVo priceManageMentVo = new PriceManageMentVo();
    /**
     * 
     *  注入汽运价格方案服务 
     *  
     */
    public void setPricePlanService(IPricePlanService pricePlanService) {
        this.pricePlanService = pricePlanService;
    }
    /** 
     * 
     * 获得汽运价格方案 VO 
     * 
     */
    public PriceManageMentVo getPriceManageMentVo() {
        return priceManageMentVo;
    }
    /**
     * 
     * <p>设置汽运价格方案 VO</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-17 上午10:52:51
     * 
     * @param priceManageMentVo
     * 
     * @see
     */
    public void setPriceManageMentVo(PriceManageMentVo priceManageMentVo) {
        this.priceManageMentVo = priceManageMentVo;
    }
    /**
     * 
     * <p>设置文件</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-17 上午10:53:08
     * 
     * @param uploadFile
     * 
     * @see
     */
    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }
    /**
     * 
     * <p>注入产品服务</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-17 上午10:53:23
     * 
     * @param productService
     * 
     * @see
     */
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
    /**
     * 
     * <p>注入汽运价格区域</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-17 上午10:53:46
     * 
     * @param regionService
     * 
     * @see
     */
    public void setRegionService(IRegionService regionService) {
        this.regionService = regionService;
    }
    /**
     * 
     * <p>获得文件</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-17 上午10:54:01
     * 
     * @return
     * 
     * @see
     */
    public String getDownloadFileName() {
        return downloadFileName;
    }
    /**
     * 
     * <p>设置文件名称</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-17 上午10:54:13
     * 
     * @param downloadFileName
     * 
     * @see
     */
    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }
    /**
     * 
     * <p>获取excel</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-27 上午11:33:22
     * 
     * @return
     * @see
     */
    public InputStream getInputStream() {
        return inputStream;
    }
    /**
     * 
     * <p>(查询价格方案信息)</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-6 下午5:03:29
     * 
     * @return String
     * 
     * @see
     */
    @JSON
    public String queryPricePlanBatchInfo(){
		try {
		    priceManageMentVo.setPricePlanEntityList(pricePlanService.queryPricePlanBatchInfo(priceManageMentVo.getPricePlanEntity(),getStart(),getLimit()));
		    this.setTotalCount(pricePlanService.queryPricePlanBatchInfoCount(priceManageMentVo.getPricePlanEntity()));
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询价格方案信息出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
    /**
     * 
     * <p>(新增价格)</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-6 下午5:04:06
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String addPricePlan(){
		try {
		    PricePlanEntity pricePlanEntity = pricePlanService.addPricePlan(priceManageMentVo.getPricePlanEntity());
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
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-14 下午4:21:35
     * 
     * @return
     * 
     * @see
     */
    public String addPricePlanDetail(){
		try {
		    //保存后按照查询该方案下所有的方案明细信息
		    //priceManageMentVo.setPricePlanDetailDtoList(pricePlanService.addPricePlanDetail(priceManageMentVo.getPricePlanDetailDto()));
		    /**
		     * 将原来的getPricePlanDetailDto改为getPricePlanDetailDtoList
		     * @author Pop-Team-Luomengxiang
		     * @date 2014.11.3
		     */
			priceManageMentVo.setPricePlanDetailDtoList(pricePlanService.addPricePlanDetail(priceManageMentVo.getPricePlanDetailDtoList()));
			return returnSuccess(MessageType.SAVE_PRICE_PLAN_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("新增价格方案明细信息: "+e.getMessage());
		    return returnError(e);
		}
    }
    /**
     * 
     * <p>(查询价格方案明细)</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-6 下午5:04:49
     * 
     * @return String
     * 
     * @see
     */
    @JSON
    public String queryPricePlanDetailInfo(){
		try {
		    List<PricePlanDetailDto> pricePlanDetailDtoList = pricePlanService.queryPricePlanDetailInfo(priceManageMentVo.getQueryPricePlanDetailBean(), start*NumericConstants.NUM_2,limit*NumericConstants.NUM_2);
		    priceManageMentVo.setPricePlanDetailDtoList(pricePlanDetailDtoList);
		    Long count = pricePlanService.queryPricePlanDetailInfoCount(priceManageMentVo.getQueryPricePlanDetailBean());
		    this.setTotalCount(count/NumericConstants.NUM_2);
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询价格方案明细: "+e.getMessage());
		    return returnError(e);
		}
    }
    /**
     * 
     * <p>(删除价格方案)</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-12 上午11:16:32
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String deletePricePlan(){
		try {
		    pricePlanService.deletePricePlan(priceManageMentVo.getPricePlanIds());
		    return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("删除价格方案: "+e.getMessage());
		    return returnError(e);
		}
    }
    /**
     * 
     * <p>(删除价格方案明细)</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-12 上午11:16:32
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String deletePricePlanDetail(){
		try {
		    List<PricePlanDetailDto>  list = pricePlanService.deletePriceDetailPlan(priceManageMentVo.getPricePlanDetailIds());
		    priceManageMentVo.setPricePlanDetailDtoList(list);
		    return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("删除价格方案明细: "+e.getMessage());
		    return returnError(e);
		}
    }
    /**
     * 
     * <p>(激活方案)</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-15 上午11:35:50
     * 
     * @return String
     * 
     * @see
     */
    @JSON
    public String activePricePlan(){
		try {
		    pricePlanService.activePricePlan(priceManageMentVo.getPricePlanIds());
		    return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("激活价格方案: "+e.getMessage());
		    return returnError(e);
		}
    }
    /**
     * 
     * <p>(立即激活方案)</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * 
     * @date 2012-12-15 上午11:35:50
     * 
     * @return String
     * 
     * @see
     */
    @JSON
    public String immediatelyActivePricePlan(){
	try {
		//获得需要批量激活的价格方案的id集合
		List<String> activePricePlanIds = new ArrayList<String>();
		
		List<String> priceValuationIdsList = new ArrayList<String>();
		List<PricePlanEntity> pricePlanEntityList = new ArrayList<PricePlanEntity>(); 
		
		activePricePlanIds = priceManageMentVo.getPricePlanIds();
		
		for(int i=0;i<activePricePlanIds.size();i++){
			
			priceManageMentVo.getPricePlanEntity().setId(activePricePlanIds.get(i));
			pricePlanService.immediatelyActivePricePlan(priceManageMentVo.getPricePlanEntity(), pricePlanEntityList, priceValuationIdsList);
		    
		}
		
		pricePlanService.immediatelyActivePricePlanBatch(pricePlanEntityList, priceValuationIdsList);
		
		return returnSuccess(MessageType.ACTIVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("立即激活价格方案: "+e.getMessage());
	    return returnError(e);
	}
    }
    /**
     * 
     * <p>(中止价格方案)</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-18 下午4:31:32
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String stopPricePlan(){
    	try {
    		List<PricePlanEntity> pricePlanEntityList = new ArrayList<PricePlanEntity>();
    		List<PriceValuationEntity> priceValuationEntityList = new ArrayList<PriceValuationEntity>();
    		
    	    pricePlanService.stopPricePlan(priceManageMentVo.getPricePlanEntity(), pricePlanEntityList, priceValuationEntityList);
    		
    	    pricePlanService.stopPlanBatch(pricePlanEntityList, priceValuationEntityList);
    	    
    	    return returnSuccess(MessageType.STOP_SUCCESS);
    	} catch (BusinessException e) {
    	    LOGGER.error("中止价格方案: "+e.getMessage());
    	    return returnError(e);
    	}
        }
    /**
     * 
     * <p>(根据方案ID查询计价方案与所有明细信息)</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-20 下午3:29:14
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String queryPricePlanAndDetailInfoById(){
	try {
	    priceManageMentVo = pricePlanService.queryCopyPricePlanInfo(priceManageMentVo.getPricePlanId());
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error("根据方案ID查询计价方案与所有明细信息: "+e.getMessage());
	    return returnError(e);
	}
    }
    
    /**
     * 
     * <p>明细修改前的查询,根据价格方案与计费规则ID查询出信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-10 下午5:36:25
     * @return
     * @see
     */
    @JSON
    public String queryBeforeModifyPricePlanDetail(){
	priceManageMentVo.setPricePlanDetailDtoList(pricePlanService.queryPricePlanDetailInfo(priceManageMentVo.getQueryPricePlanDetailBean()));
	return returnSuccess();
    }
    /**
     * 
     * <p>(修改保存方案主信息以及明细信息)</p>
     *  
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-20 下午3:29:14
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String updatePricePlan(){
	try {
	    PricePlanEntity dbEntity = pricePlanService.modifyPricePlan(priceManageMentVo.getPricePlanEntity());
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
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-23 下午3:32:22
     * 
     * @return
     * 
     * @see
     * 
     */
    @JSON
    public String copyPricePlan(){
	try {
	    String newPricePlanId = pricePlanService.copyPricePlan(priceManageMentVo.getPricePlanId());
	    priceManageMentVo = pricePlanService.queryCopyPricePlanInfo(newPricePlanId);
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
	 * 
	 * @date 2012-12-10下午4:51:08
	 */
	public String importPrice() {
		// 初始化
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
				Sheet sheet = book.getSheetAt(0);// 默认第一个
				// 读取Excel的所用数据
				// 读取最终数据结果，key为始发区域名称 ，值为价格数据明细
				Map<String, List<PricePlanDetailDto>> detailMap = new HashMap<String, List<PricePlanDetailDto>>();
				// 收集excel文件中的价格区域map ，key为区域名称，值为区域名称
				Map<String, String> regionMap = new HashMap<String, String>();
				// 收集excel文件中的价格区域map ，key为区域名称，值为区域名称
				Map<String, String> arrvRegionMap = new HashMap<String, String>();
				
				// 收集excel文件中的产品信息map，key为产品名称，值为产品名称
				Map<String, String> productMap = new HashMap<String, String>();
				//key 为出发城市name+是否接货+价格段+产品类型名字+到达城市name,插入计价方式明细分段时用到
				Map<String, PricePlanDetailDto> detailSectionMap = new HashMap<String, PricePlanDetailDto>();
				// 将Excel表格每行数据读入列表，开始收集信息，并作必要的检查
				transformExcel(detailMap, regionMap,arrvRegionMap,productMap, sheet,detailSectionMap);
				if (detailMap == null || detailMap.size() < 1) {
					throw new FileException("导入数据为空,请检查", "导入数据为空,请检查");
				}
				// 把产品信息map通过service查询出相关产品的实体
				Map<String, ProductEntity> productEntityMap = processProductInfo(productMap);
				// 把区域信息map通过service查询出相关的区域信息
				Map<String, PriceRegionEntity> priceRegionEntityMap = processPriceRegionInfo(regionMap);
				// 把到达区域信息map通过service查询出相关的区域信息
				Map<String, PriceRegionArriveEntity> priceArrvRegionEntityMap = processPriceArrvRegionInfo(arrvRegionMap);
				// 批量新增价格数据
				pricePlanService.addPricePlanBatch(detailMap, priceRegionEntityMap,priceArrvRegionEntityMap,productEntityMap,detailSectionMap);
			}
			return super.returnSuccess();
		} catch (FileException e) {
			return super.returnError(e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("数据文件被破坏，请重新制作导入文件"+e);
		} catch (Exception e) {
		    LOGGER.error(e.getMessage(),e);
		    return returnError("数据文件不符合规范，请重新制作导入文件"+e);
		}finally {
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
	 * 导入excel数据
	 * @param detailSectionMap 
	 */
	private void transformExcel(Map<String, List<PricePlanDetailDto>> detailMap
			, Map<String, String> regionMap, Map<String, String> arrvRegionMap
			, Map<String, String> productMap
			, Sheet sheet
			, Map<String, PricePlanDetailDto> detailSectionMap){
		Map<String, String> detailCheckMap = new HashMap<String, String>();
		if (sheet != null && detailMap != null) {
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// EXCEL行记录
			if (rowCount < 2) {
				throw new FileException("excel中没有数据",null);
			}
			// 第一行数据
			Row firstRow = sheet.getRow(0);
			// 看第一行到底有多少列数据，从第7列开始，是目的城市数据 key:列的index,value:到达区域名字
			Map<String, String> destRegionNameMap = new HashMap<String, String>();
			//这个map只用于校验到达城市名字是否有重复的
			Map<String, String> checkdestRegionNameMap = new HashMap<String, String>();
			//这个循环用于校验到达城市是否有重复的
			for (int colNum = NumericConstants.NUM_6; colNum < CELL_COUNT; colNum++) {
				Cell cell = firstRow.getCell(colNum);
				if (cell != null) {
					// 取单元格值
					String cellVal = ExcelHandleUtil.obtainStringVal(cell);
					if (StringUtil.isNotBlank(cellVal)) {
						destRegionNameMap.put(colNum + BLANK, cellVal);
						if (checkdestRegionNameMap.get(cellVal) == null) {
							checkdestRegionNameMap.put(cellVal, cellVal);// 把目的地放入map，作为目的地只能有一条的判断数据源
						} else {
							throw new FileException("目的地" + cellVal + "重复，请检查数据",null);
						}
					} else {
						throw new FileException("第"+(colNum+1)+"列到达城市名字为空",null);
					}
				}
			}
			//总的数据的列数,也就是总的城市数
			int totalColume = destRegionNameMap.size();
			//这个循环遍历每一行,将每一行的数据封装到List<PricePlanDetailDto> alist中
			//遍历规则:价格段为1的,再找下面两行,为其他的,往下找一行
			Row row1 = null;
			Row row2 = null;
			Row row3 = null;
			for(int rowNum = 1; rowNum < rowCount; rowNum++){
				//当前遍历到的行1
				row1 = sheet.getRow(rowNum);
				//始发区域名字,excel中第一列数据
				String deptRegionName = null;
				//每一行中的第一列数据
				Cell cell1 = row1.getCell(0);
				// 取单元格值
				deptRegionName = ExcelHandleUtil.obtainStringVal(cell1);
				if (StringUtil.isBlank(deptRegionName)) {
					throw new FileException("第"+(rowNum+1)+"行出发城市名字为空",null);
				}
				// 是否接货CODE值,Y或者N,根据excel第二列数据得到
				String reacheGoods = null;
				//是否接货中文值,是或者否,excel第二列数据
				String reacheGoodsName = null;
				//每一行中的第二列数据
				Cell cell2 = row1.getCell(1);
				reacheGoodsName = ExcelHandleUtil.obtainStringVal(cell2);
				if("是".equals(reacheGoodsName)){
					reacheGoods = FossConstants.YES; 
				}else if("否".equals(reacheGoodsName)){
					reacheGoods = FossConstants.NO;
				}else{
					throw new FileException("第"+(rowNum+1)+"行是否接货数据有问题",null);
				}
				// 价格段,excel第三列数据
				Long priceLevel = null;
				//每一行中的第三列数据
				Cell cell3 = row1.getCell(NumericConstants.NUM_2);
				try {
					// 检查价格段必须为整数
					String mindV = cell3.getNumericCellValue() + BLANK;
					String value = mindV.substring(mindV.lastIndexOf("."));
					double valueD = new Double(value);
					if ( BigDecimal.ZERO.compareTo(BigDecimal.valueOf(valueD)) != 0 ) {
						throw new FileException("第" + (rowNum+1) + "行价格段必须为整数",null);
					}
					priceLevel = Double.valueOf(cell3.getNumericCellValue()).longValue();
				} catch (Exception e) {
					throw new FileException("第" + (rowNum+1) + "行价格段必须为整数",null);
				}
				//产品名称,excel第五列数据
				String productName = null;
				//每一行中的第五列数据
				Cell cell5 = row1.getCell(NumericConstants.NUM_4);
				productName = ExcelHandleUtil.obtainStringVal(cell5);
				if (StringUtil.isBlank(productName)) {
					throw new FileException("第"+(rowNum+1)+"行产品类型为空",null);
				}
				//计费类型,excel第六列数据
				String caculateTypeName = null;
				//每一行中的第六列数据
				Cell cell6 = row1.getCell(NumericConstants.NUM_5);
				caculateTypeName = ExcelHandleUtil.obtainStringVal(cell6);
				if (StringUtil.isBlank(caculateTypeName)) {
					throw new FileException("第"+(rowNum+1)+"行计费类型为空",null);
				}
				//是否价格段1
				boolean isLevelOne = false;
				//得到下一行数据
				row2 = sheet.getRow(++rowNum);
				//如果价格段为1,得到下两行的数据
				if(priceLevel==1){
					row3 = sheet.getRow(++rowNum);
					isLevelOne = true;
				}else{
					row3 = null;
				}
				//循环遍历每一列到达城市的数据值,价格为为1的每三行封装成一个实体,其他价格段的每2行一个实体
				for (int colNum = NumericConstants.NUM_6; colNum < totalColume + NumericConstants.NUM_6; colNum++){
					PricePlanDetailDto detailDto = new PricePlanDetailDto();
					Cell currCell1 = row1.getCell(colNum);
					Cell currCell2 = row2.getCell(colNum);
					Cell currCell3 = null;
					if(isLevelOne){
						currCell3 = row3.getCell(colNum);
					}
					// 判断数据格式正确否
					if (currCell1 != null && !(currCell1.getCellType() == HSSFCell.CELL_TYPE_NUMERIC||currCell1.getCellType() == HSSFCell.CELL_TYPE_BLANK)) {
						throw new FileException("第" + (rowNum-1) + "行第" + (colNum + 1) + "列数据不正确，请检查", null);
					}
					if (currCell2 != null && !(currCell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC||currCell2.getCellType() == HSSFCell.CELL_TYPE_BLANK)) {
						throw new FileException("第" + rowNum + "行第" + (colNum + 1) + "列数据不正确，请检查", null);
					}
					if (isLevelOne&&currCell3 != null && !(currCell3.getCellType() == HSSFCell.CELL_TYPE_NUMERIC||currCell3.getCellType() == HSSFCell.CELL_TYPE_BLANK)) {
						throw new FileException("第" + (rowNum+1) + "行第" + (colNum + 1) + "列数据不正确，请检查", null);
					}
					if(currCell1==null||currCell2==null){
						continue;
					}
					// 读出清货价格和重货价格
					double weightRate = currCell1.getNumericCellValue();
					double volumeRate = currCell2.getNumericCellValue();
					Long minFee = null;
					if(isLevelOne && currCell3 != null){
						try {
							// 检查最低一票必须是整形
							String mindoubleVal = currCell3.getNumericCellValue() + BLANK;
							String subValue = mindoubleVal.substring(mindoubleVal.lastIndexOf("."));
							double subValueD = new Double(subValue);
							if (BigDecimal.ZERO.compareTo(BigDecimal.valueOf(subValueD)) != 0) {
								throw new FileException("第" + (rowNum+1) + "行第" + (colNum + 1) + "最低一票数据必须为整数，请检查",null);
							}
							minFee = Double.valueOf(currCell3.getNumericCellValue()).longValue();
						} catch (Exception e) {
							throw new FileException("第" + (rowNum+1) + "行第" + (colNum + 1) + "最低一票数据必须为整数，请检查",null);
						}
					}
					// 开始组装数据
					// 设置目的区域名称
					detailDto.setArrvRegionName(destRegionNameMap.get(colNum + BLANK));
					// 设置是否接货
					detailDto.setCentralizePickup(reacheGoods);
					// 设置产品名称
					detailDto.setProductItemName(productName);
					// 设置重货价格
					detailDto.setHeavyPrice(new BigDecimal(weightRate + BLANK));
					// 设置轻货价格
					detailDto.setLightPrice(new BigDecimal(volumeRate + BLANK));
					// 设置最低一票
					detailDto.setMinimumOneTicket(minFee);
					// 设置价格段
					detailDto.setSectionID(Long.toString(priceLevel));
					//设置出发区域名字
					detailDto.setDeptRegionName(deptRegionName);
					//重货临界值临界值,excel第四列数据
					String weightCriticalValue = null;
					//轻货临界值临界值,excel第四列数据
					String lightCriticalValue = null;
					//第四列数据
					Cell row1Cell4 = row1.getCell(NumericConstants.NUM_3);
					//第四列数据
					Cell row2Cell4 = row2.getCell(NumericConstants.NUM_3);
					weightCriticalValue = ExcelHandleUtil.obtainStringVal(row1Cell4);
					lightCriticalValue = ExcelHandleUtil.obtainStringVal(row2Cell4);
					if (StringUtil.isBlank(weightCriticalValue)) {
						throw new FileException("第"+(rowNum-1)+"行临界值为空",null);
					}
					if (StringUtil.isBlank(lightCriticalValue)) {
						throw new FileException("第"+(rowNum)+"行临界值为空",null);
					}
					// 设置临界值
					detailDto.setLightCriticalVal(new BigDecimal(lightCriticalValue));
					detailDto.setHeavyCriticalVal(new BigDecimal(weightCriticalValue));
					//校验是否重复数据
					StringBuffer sb = new StringBuffer();
					sb.append(deptRegionName).append("到").append(detailDto.getArrvRegionName())
						.append(",是否接货：").append(reacheGoodsName).append(",产品类型:")
						.append(productName).append(",价格段:").append(priceLevel)
						.append(",重货临界值:").append(weightCriticalValue)
						.append(",轻货临界值:").append(lightCriticalValue).append(BLANK);
					String checkKey = sb.toString();
					if (detailCheckMap.get(checkKey) == null) {
						detailCheckMap.put(checkKey, checkKey);
					}else{
						throw new FileException("数据重复:\n"+checkKey,null);
					}
					// 收集校验后的信息
					List<PricePlanDetailDto> alist = detailMap.get(deptRegionName);
					if (CollectionUtils.isEmpty(alist)) {
						alist = new ArrayList<PricePlanDetailDto>();
						detailMap.put(deptRegionName, alist);
					}
					alist.add(detailDto);
					//key 为出发城市name+是否接货+价格段+产品类型名字+到达城市name,插入计价方式明细分段时用到
					String key = deptRegionName+reacheGoodsName+detailDto.getSectionID()+detailDto.getProductItemName()
							+detailDto.getArrvRegionName();
					if(detailSectionMap.get(key)==null){
						detailSectionMap.put(key, detailDto);
					}
					arrvRegionMap.put(detailDto.getArrvRegionName(), detailDto.getArrvRegionName());
					regionMap.put(deptRegionName, deptRegionName);
					productMap.put(productName, productName);
				}
			}
		}
	}

	 /**
	  * 
	 * 处理产品信息，避免service中事物处理过长
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-24 下午5:46:52
	 * 
	 * @param nameMap
	 * 
	 * @return
	 * 
	 * @see
	 */
	private Map<String, ProductEntity> processProductInfo(Map<String, String> nameMap) {
		// 非空判断
		if (nameMap == null || nameMap.size() < 1) {
			throw new FileException("导入数据中没有产品信息，请检查", "导入数据中没有产品信息，请检查");
		}
		// 读取二级产品，因为价格数据都是二级的
		List<ProductEntity> productInfoList = productService.queryLevel2ProductInfo();
		if (CollectionUtils.isEmpty(productInfoList)) {
			throw new FileException("数据库中没有产品信息，请检查", "数据库中没有产品信息，请检查");
		}
		Map<String, ProductEntity> nameEntityMap = new HashMap<String, ProductEntity>();
		Iterator<String> keyIt = nameMap.keySet().iterator();
		while (keyIt.hasNext()) {
			// 逐个比对，是否导入的产品在数据库里存在
			String productname = keyIt.next();
			for (int loop = 0; loop < productInfoList.size(); loop++) {
				ProductEntity productEntity = productInfoList.get(loop);
				if (StringUtil.equals(productname, productEntity.getName())) {
					nameEntityMap.put(productname, productEntity);
					// 找到后这次循环结束
					break;
				}
			}
			if (nameEntityMap.get(productname) == null) {
				// 最后都没找到，那就是出问题了
				throw new FileException("数据库中没有产品:" + productname + "，请检查", "数据库中没有产品:" + productname + "，请检查");
			}
		}
		return nameEntityMap;
	}
	/**
	 * 
	 *  处理价格区域信息，批量根据名字把区域查询出来
	 *  
	 * @author zhangdongping
	 * 
	 * @date 2012-12-24 下午5:49:54
	 * 
	 * @param regionMap
	 * 
	 * @return
	 * 
	 * @see
	 */
	private Map<String, PriceRegionEntity> processPriceRegionInfo(Map<String, String> regionMap) {
		// 非空判断
		if (regionMap == null || regionMap.size() < 1) {
			throw new FileException("导入数据中没有区域信息，请检查", "导入数据中没有区域与信息，请检查");
		}
		List<String> names = new ArrayList<String>();
		names.addAll(regionMap.keySet());
		// 批量查找区域数据
		Map<String, PriceRegionEntity> findResult = new HashMap<String, PriceRegionEntity>();
		Map<String, PriceRegionEntity> findResultTemp = null;
		int loop = 1;
		if(names.size() > NumberConstants.NUMBER_500){
			loop = (names.size()-1)/NumberConstants.NUMBER_500 + 1 ;
		}
		List<String> namesTemp = null;
		int tempNum = 0;
		for(int i = 0 ;i<loop ;i++){
			if(NumberConstants.NUMBER_500*(i+1)  > names.size()){
				tempNum = names.size();
			}else{
				tempNum = NumberConstants.NUMBER_500*(i+1);
			}
			namesTemp = names.subList(NumberConstants.NUMBER_500*i, tempNum);
			findResultTemp = regionService.findRegionByNames(namesTemp, PricingConstants.PRICING_REGION, new Date());
			if(findResultTemp != null && findResultTemp.size() > 0){
				findResult.putAll(findResultTemp);
			}
		}
		
		if (findResult == null || findResult.size() < 1) {

			throw new FileException("数据库中没有匹配到导入的任何区域数据，请检查", "数据库中没有匹配到导入的任何区域数据，请检查");
		}

		Iterator<String> it = regionMap.keySet().iterator();
		while (it.hasNext()) {
			// 逐条验证有没有价格区域
			String object = it.next();
			if (findResult.get(object) == null) {
				throw new FileException("出发区域：" + object + "，在数据库中没有找到，请检查", "区域：" + object + "，在数据库中没有找到，请检查");
			}
		}
		return findResult;
	}
	
	
	/**
	 * 
	 *  处理价格区域信息，批量根据名字把区域查询出来
	 *  
	 * @author zhangdongping
	 * 
	 * @date 2012-12-24 下午5:49:54
	 * 
	 * @param regionMap
	 * 
	 * @return
	 * 
	 * @see
	 */
	private Map<String, PriceRegionArriveEntity> processPriceArrvRegionInfo(Map<String, String> regionMap) {
		// 非空判断
		if (regionMap == null || regionMap.size() < 1) {
			throw new FileException("导入数据中没有区域信息，请检查", "导入数据中没有区域与信息，请检查");
		}
		List<String> names = new ArrayList<String>();
		names.addAll(regionMap.keySet());
		// 批量查找区域数据
		Map<String, PriceRegionArriveEntity> findResult = new HashMap<String, PriceRegionArriveEntity>();
		Map<String, PriceRegionArriveEntity> findResultTemp = null;
		int loop = 1;
		if(names.size() > NumberConstants.NUMBER_500){
			loop = (names.size()-1)/NumberConstants.NUMBER_500 + 1 ;
		}
		List<String> namesTemp = null;
		int tempNum = 0;
		for(int i = 0 ;i<loop ;i++){
			if(NumberConstants.NUMBER_500*(i+1)  > names.size()){
				tempNum = names.size();
			}else{
				tempNum = NumberConstants.NUMBER_500*(i+1);
			}
			namesTemp = names.subList(NumberConstants.NUMBER_500*i, tempNum);
			findResultTemp = regionArriveService.findRegionByNames(namesTemp, PricingConstants.PRICING_REGION, new Date());
			if(findResultTemp != null && findResultTemp.size() > 0){
				findResult.putAll(findResultTemp);
			} 
		}
		
		if (findResult == null || findResult.size() < 1) {

			throw new FileException("数据库中没有匹配到导入的任何区域数据，请检查", "数据库中没有匹配到导入的任何区域数据，请检查");
		}

		Iterator<String> it = regionMap.keySet().iterator();
		while (it.hasNext()) {
			// 逐条验证有没有价格区域
			String object = it.next();
			if (findResult.get(object) == null) {
				throw new FileException("到达区域：" + object + "，在数据库中没有找到，请检查", "区域：" + object + "，在数据库中没有找到，请检查");
			}
		}
		return findResult;
	}
	/**
	 * 
	 * <p>价格方案导出</p>
	 *  
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-12-25 上午10:54:12
	 * 
	 * @return
	 * 
	 * @see
	 */
	public String exportPricePlan() {
		try {
			LOGGER.info("pop-导出前excel设定");
			String fileName = PricingColumnConstants.EXPORT_PRICE_PLAN;
			downloadFileName = encodeFileName(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT) + fileName);
			if (null == priceManageMentVo.getPricePlanEntity()) {
				priceManageMentVo.setPricePlanEntity(new PricePlanEntity());
			}
			// 导出设置
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName(PricingColumnConstants.EXPORT_PRICE_PLAN);
			exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
			// 存储导出数据
			LOGGER.info("pop-进入service");
			ExportResource exportResource = pricePlanService.exportPricePlan(priceManageMentVo.getPricePlanEntity());
			LOGGER.info("pop-离开service");
			// 根据存储的数据调用导出类
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			LOGGER.info("pop-开始调用DPAP导出工具类导出excel");
			inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
			LOGGER.info("pop-导出结束，return success");
			return returnSuccess();
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("价格方案导出，出现异常: " + e.getMessage());
			return returnError("价格方案明细导出，出现异常", e.getMessage());
		}
	}
	/**
	 * 
	 * <p>价格方案明细导出</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-12-25 上午10:54:12
	 * 
	 * @return
	 * 
	 * @see
	 */
	public String exportPricePlanDetail() {
	    try {
		String fileName = PricingColumnConstants.EXPORT_PRICE_PLAN_DETAIL ;
		downloadFileName =encodeFileName(DateUtils.convert(new Date(),DateUtils.DATE_FORMAT) + fileName);
		//导出设置
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName(PricingColumnConstants.EXPORT_PRICE_PLAN_DETAIL);
		exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
		//存储导出数据
		ExportResource exportResource = pricePlanService.exportPricePlanDetail(priceManageMentVo.getQueryPricePlanDetailBean());
		//根据存储的数据调用导出类
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		return returnSuccess();
	    } catch (UnsupportedEncodingException e) {
		LOGGER.error("价格方案明细导出，出现异常: "+e.getMessage());
		return returnError("价格方案明细导出，出现异常",e.getMessage());
	    }
	}
   /**
	* 
	* <p>(修改价格明细信息)</p> 
	* 
	* @author DP-Foss-YueHongJie
	* 
	* @date 2012-12-20 下午3:29:14
	* 
	* @return
	* 
	* @see
	*/
	@JSON
	public String updatePriceDetailPlan(){
	    try {
		    priceManageMentVo.setPricePlanDetailDtoList(pricePlanService.modifyPricePlanDetail(priceManageMentVo));
		    return returnSuccess(MessageType.UPDATE_SUCCESS);
	    } catch (BusinessException e) {
		    LOGGER.error("修改保存方案主信息以及明细信息: "+e.getMessage());
		    return returnError(e);
	    }
	}
	
	/**
	 * 
	 * 转换导出文件的文件名
	 * @author 043258-foss-zhaobin
	 * @date 2013-5-2 上午9:52:18
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 * @see
	 */
    public String encodeFileName(String name) throws UnsupportedEncodingException {
    	String returnStr;
    	String agent = (String)ServletActionContext.getRequest().getHeader("USER-AGENT");
    	if(agent != null && agent.indexOf("MSIE") == -1) {
    		returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
    	} else {
    		returnStr = URLEncoder.encode(name, "UTF-8");
    	}
    	return returnStr;
    }
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.server.action.PricePlanAction.queryComparePricePlanInfo
     * @Description:查询与原价格方案名称相同  始发区域相同的价格方案信息
     *
     * @return
     *
     * @version:v1.0
     * @author:130376-YANGKANG
     * @date:2014-5-10 上午10:48:02
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-5-10    130376-YANGKANG      v1.0.0         create
     */
    @JSON
    public String queryComparePricePlanInfo(){
		try {
			//获得原价格方案ID
			String pricePlanIdOld = priceManageMentVo.getPricePlanId();
			//查询出包括原价格方案的集合
			List<PricePlanEntity> pricePlanEntityList = new ArrayList<PricePlanEntity>();
			pricePlanEntityList = pricePlanService.queryComparePricePlanBatchInfo(priceManageMentVo.getPricePlanEntity(),0,Integer.MAX_VALUE);
			//对集合进行过滤 排除原价格方案
			for(int i=0;i<pricePlanEntityList.size();i++){
				if(pricePlanIdOld.equals(pricePlanEntityList.get(i).getId())){
					pricePlanEntityList.remove(pricePlanEntityList.get(i));
					break;
				}
			}
			priceManageMentVo.setPricePlanEntityList(pricePlanEntityList);
		    
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询价格方案信息出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.server.action.PricePlanAction.queryPricePlanCompareResult
     * @Description:价格方案比对结果明细查询
     *
     * @return
     *
     * @version:v1.0
     * @author:130376-YANGKANG
     * @date:2014-5-8 下午5:01:41
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-5-8    130376-YANGKANG      v1.0.0         create
     */
    @JSON
    public String queryPricePlanCompareResult(){
		try {
			List<PricePlanDetailDto> pricePlanCompareResultList =pricePlanService.queryPricePlanCompareResult(priceManageMentVo.getPricePlanEntityOld(), priceManageMentVo.getPricePlanEntity());
		    priceManageMentVo.setPricePlanDetailDtoList(pricePlanCompareResultList);
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("价格方案比对结果: "+e.getMessage());
		    return returnError(e);
		}
    }
    
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.action.PricePlanAction.exportPricePlanCompareResult
	 * @Description:价格方案比对结果导出
	 *
	 * @return
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-9 上午10:22:45
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-9    130376-YANGKANG      v1.0.0         create
	 */
	public String exportPricePlanCompareResult() {
	    try {
		String fileName = PricingColumnConstants.EXPORT_PRICE_PLAN_COMPARE_RESULT ;
		downloadFileName =encodeFileName(DateUtils.convert(new Date(),DateUtils.DATE_FORMAT) + fileName);
		//导出设置
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName(PricingColumnConstants.EXPORT_PRICE_PLAN_COMPARE_RESULT);
		exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
		//查询价格方案比对结果集
//		List<PricePlanDetailDto> pricePlanCompareResultList =pricePlanService.queryPricePlanCompareResult(priceManageMentVo.getPricePlanEntityOld(), priceManageMentVo.getPricePlanEntity());
		//存储导出数据
		ExportResource exportResource = pricePlanService.exportPricePlanCompareResult(priceManageMentVo.getPricePlanEntityOld(), priceManageMentVo.getPricePlanEntity());
		//根据存储的数据调用导出类
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		return returnSuccess();
	    } catch (UnsupportedEncodingException e) {
		LOGGER.error("价格方案比对结果导出，出现异常: "+e.getMessage());
		return returnError("价格方案比对结果导出，出现异常",e.getMessage());
	    }
	}
    /**
     *
     *
     * @Function: com.deppon.foss.module.pickup.pricing.server.action.PricePlanAction.stopPricePlans
     * @Description:批量中止汽运价格方案
     *
     * @return
     *
     * @version:v1.0
     * @author:130376-YANGKANG
     * @date:2014-5-13 下午2:28:57
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-5-13    130376-YANGKANG      v1.0.0         create
     */
    @JSON
    public String stopPricePlans(){
	try {
		List<PricePlanEntity> pricePlanEntityList = new ArrayList<PricePlanEntity>();
		List<PriceValuationEntity> priceValuationEntityList = new ArrayList<PriceValuationEntity>();
		//获得需要批量中止的价格方案的id集合List<PricePlanEntity> pricePlanEntityList, List<PriceValuationEntity> priceValuationEntityList
		List<String> stopPricePlanIds = new ArrayList<String>();
		stopPricePlanIds = priceManageMentVo.getPricePlanIds();
		for(int i=0;i<stopPricePlanIds.size();i++){
			priceManageMentVo.getPricePlanEntity().setId(stopPricePlanIds.get(i));
			pricePlanService.stopPricePlan(priceManageMentVo.getPricePlanEntity(), pricePlanEntityList, priceValuationEntityList);
		}
		pricePlanService.stopPlanBatch(pricePlanEntityList, priceValuationEntityList);
	    return returnSuccess(MessageType.STOP_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("中止价格方案: "+e.getMessage());
	    return returnError(e);
	}
    }
}
