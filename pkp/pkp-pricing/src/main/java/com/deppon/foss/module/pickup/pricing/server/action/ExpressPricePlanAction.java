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

import org.apache.commons.lang.StringUtils;
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
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.NumericConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressPricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricePlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceManageMentVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递价格方案管理
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-8-2 下午1:59:18
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-8-2 下午1:59:18
 * @since
 * @version
 */
public class ExpressPricePlanAction extends AbstractAction {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 2710507487534435247L;

    /**
     * 日志处理.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(ExpressPricePlanAction.class);

    /**
     * 导入文件.
     */
    private File uploadFile;

    /**
     * 导出Excel 文件流.
     */
    private InputStream inputStream;

    /**
     * 产品信息service.
     */
    private IProductService productService;

    /**
     * 导出文件名称.
     */
    private String downloadFileName;

    /**
     * 区域信息service.
     */
    private IRegionExpressService regionExpressService;

    /**
     * 最大读取10000列.
     */
    private static final int CELL_COUNT = 10000;

    /**
     * 替代空白字符串"".
     */
    private static final String BLANK = "";

    /**
     * 快递价格方案Service 接口.
     */
    private IExpressPricePlanService expressPricePlanService;

    /**
     * 汽运价格方案 VO.
     */
    private PriceManageMentVo priceManageMentVo = new PriceManageMentVo();
    /**
     * 客户Service
     */
    private ICustomerService customerService;
    
    public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	/**
     * 设置 快递价格方案Service 接口.
     * 
     * @param expressexpressPricePlanService
     *            the expressPricePlanService to set
     */
    public void setExpressPricePlanService(
	    IExpressPricePlanService expressPricePlanService) {
	this.expressPricePlanService = expressPricePlanService;
    }

    /**
     * 获得汽运价格方案 VO.
     * 
     * @return the 汽运价格方案 VO
     */
    public PriceManageMentVo getPriceManageMentVo() {
	return priceManageMentVo;
    }

    /**
     * <p>
     * 设置汽运价格方案 VO
     * </p>
     * .
     * 
     * @param priceManageMentVo
     *            the new 汽运价格方案 VO
     * @author DP-Foss-YueHongJie
     * @date 2013-3-17 上午10:52:51
     * @see
     */
    public void setPriceManageMentVo(PriceManageMentVo priceManageMentVo) {
	this.priceManageMentVo = priceManageMentVo;
    }

    /**
     * <p>
     * 设置文件
     * </p>
     * .
     * 
     * @param uploadFile
     *            the new 导入文件
     * @author DP-Foss-YueHongJie
     * @date 2013-3-17 上午10:53:08
     * @see
     */
    public void setUploadFile(File uploadFile) {
	this.uploadFile = uploadFile;
    }

    /**
     * <p>
     * 注入产品服务
     * </p>
     * .
     * 
     * @param productService
     *            the new 产品信息service
     * @author DP-Foss-YueHongJie
     * @date 2013-3-17 上午10:53:23
     * @see
     */
    public void setProductService(IProductService productService) {
	this.productService = productService;
    }

    /**
     * <p>
     * 注入汽运价格区域
     * </p>
     * .
     * 
     * @param regionService
     *            the new 区域信息service
     * @author DP-Foss-YueHongJie
     * @date 2013-3-17 上午10:53:46
     * @see
     */

 

    
    public void setRegionExpressService(IRegionExpressService regionExpressService) {
        this.regionExpressService = regionExpressService;
    }
 

    /**
     * <p>
     * 获得文件
     * </p>
     * .
     * 
     * @return the 导出文件名称
     * @author DP-Foss-YueHongJie
     * @date 2013-3-17 上午10:54:01
     * @see
     */
    public String getDownloadFileName() {
	return downloadFileName;
    }


    /**
     * <p>
     * 设置文件名称
     * </p>
     * .
     * 
     * @param downloadFileName
     *            the new 导出文件名称
     * @author DP-Foss-YueHongJie
     * @date 2013-3-17 上午10:54:13
     * @see
     */
    public void setDownloadFileName(String downloadFileName) {
	this.downloadFileName = downloadFileName;
    }

    /**
     * <p>
     * 获取excel
     * </p>
     * .
     * 
     * @return the 导出Excel 文件流
     * @author DP-Foss-YueHongJie
     * @date 2012-12-27 上午11:33:22
     * @see
     */
    public InputStream getInputStream() {
	return inputStream;
    }
    
    /**
     * <p>
     * (查询价格方案信息)
     * </p>
     * .
     * 
     * @return String
     * @author DP-Foss-YueHongJie
     * @date 2012-12-6 下午5:03:29
     * @see
     */
    @JSON
    public String queryPricePlanBatchInfo() {
	try {
	    priceManageMentVo.setPricePlanEntityList(expressPricePlanService
		    .queryPricePlanBatchInfo(
			    priceManageMentVo.getPricePlanEntity(), getStart(),
			    getLimit()));
	    this.setTotalCount(expressPricePlanService
		    .queryPricePlanBatchInfoCount(priceManageMentVo
			    .getPricePlanEntity()));
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error("查询价格方案信息出现异常: " + e.getMessage());
	    return returnError(e);
	}
    }

   /**
    * <p>新增快递价格方案</p> 
    * @author 094463-foss-xieyantao
    * @date 2013-8-9 下午5:39:10
    * @return
    * @see
    */
    @JSON
    public String addPricePlan() {
	try {
	    PricePlanEntity pricePlanEntity = expressPricePlanService
		    .addPricePlan(priceManageMentVo.getPricePlanEntity());
	    priceManageMentVo.setPricePlanEntity(pricePlanEntity);
	    return returnSuccess(MessageType.SAVE_PRICE_PLAN_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("新增价格方案信息出现异常: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 新增价格方案明细信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-8 上午7:57:35
     * @return
     * @see
     */
    @JSON
    public String addPricePlanDetail() {
	try {
	    // 新增快递价格方案明细
	    ExpressPricePlanDetailDto dto = expressPricePlanService
		    .addDetailInfo(priceManageMentVo
			    .getExpressPricePlanDetailDto());
	    priceManageMentVo.setExpressPricePlanDetailDto(dto);

	    return returnSuccess(MessageType.SAVE_PRICE_PLAN_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("新增价格方案明细信息: " + e.getMessage());
	    return returnError(e);
	}
    }
    
    /**
     * <p>修改价格方案明细信息（1条计费规则，3条计价明细）</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-9 下午2:16:31
     * @return
     * @see
     */
    @JSON
    public String updatePriceDetailPlan() {
	try {
	    ExpressPricePlanDetailDto dto = expressPricePlanService.updateDetailInfo(priceManageMentVo.getExpressPricePlanDetailDto());
	    priceManageMentVo.setExpressPricePlanDetailDto(dto);
	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("修改保存方案主信息以及明细信息: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * <p>查询快递价格方案明细</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-8 下午2:45:37
     * @return
     * @see
     */
    @JSON
    public String queryPricePlanDetailInfo() {
	try {
	    //查询价格方案明细分页
	    List<ExpressPricePlanDetailDto> dtoList = expressPricePlanService
		    .queryPricePlanDetailInfo(priceManageMentVo.getQueryPricePlanDetailBean(),start, limit);
	    priceManageMentVo.setDetailDtoList(dtoList);
	    
	    Long count = expressPricePlanService.queryPricePlanDetailInfoCount(priceManageMentVo.getQueryPricePlanDetailBean());
	    this.setTotalCount(count);
	    
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error("查询价格方案明细: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * <p>
     * (删除价格方案)
     * </p>
     * .
     * 
     * @return
     * @author DP-Foss-YueHongJie
     * @date 2012-12-12 上午11:16:32
     * @see
     */
    @JSON
    public String deletePricePlan() {
	try {
	    expressPricePlanService.deletePricePlan(priceManageMentVo
		    .getPricePlanIds());
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("删除价格方案: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * <p>删除价格方案明细</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-9-17 下午3:59:02
     * @return
     * @see
     */
    @JSON
    public String deletePricePlanDetail() {
	try {
	    expressPricePlanService.deletePriceDetailPlan(priceManageMentVo
			    .getPricePlanDetailIds());
//	    priceManageMentVo.setPricePlanDetailDtoList(list);
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("删除价格方案明细: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * <p>激活方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-19 下午4:04:45
     * @return
     * @see
     */
    @JSON
    public String activePricePlan() {
	try {
	    expressPricePlanService.activePricePlan(priceManageMentVo
		    .getPricePlanIds());
	    return returnSuccess(MessageType.ACTIVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("激活价格方案: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * <p>立即激活方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-19 下午4:04:26
     * @return
     * @see
     */
    @JSON
    public String immediatelyActivePricePlan() {
	try {
	    expressPricePlanService
		    .immediatelyActivePricePlan(priceManageMentVo
			    .getPricePlanEntity());
	    return returnSuccess(MessageType.ACTIVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("立即激活价格方案: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * <p>
     * (中止价格方案)
     * </p>
     * .
     * 
     * @return
     * @author DP-Foss-YueHongJie
     * @date 2012-12-18 下午4:31:32
     * @see
     */
    @JSON
    public String stopPricePlan() {
	try {
	    expressPricePlanService.stopPricePlan(priceManageMentVo
		    .getPricePlanEntity());
	    return returnSuccess(MessageType.STOP_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("中止价格方案: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * <p>
     * (根据方案ID查询计价方案与所有明细信息)
     * </p>
     * .
     * 
     * @return
     * @author DP-Foss-YueHongJie
     * @date 2012-12-20 下午3:29:14
     * @see
     */
    @JSON
    public String queryPricePlanAndDetailInfoById() {
	try {
	    priceManageMentVo = expressPricePlanService
		    .queryCopyPricePlanInfo(priceManageMentVo.getPricePlanId());
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error("根据方案ID查询计价方案与所有明细信息: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * <p>根据价格方案计价规则ID查询计价规则和计价明细信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-9 上午9:25:09
     * @return
     * @see
     */
    @JSON
    public String queryBeforeModifyPricePlanDetail() {
	ExpressPricePlanDetailDto dto = expressPricePlanService.queryDetailDto(priceManageMentVo.getQueryPricePlanDetailBean());
	priceManageMentVo.setExpressPricePlanDetailDto(dto);
	return returnSuccess();
    }

    /**
     * <p>
     * (修改保存方案主信息以及明细信息)
     * </p>
     * .
     * 
     * @return
     * @author DP-Foss-YueHongJie
     * @date 2012-12-20 下午3:29:14
     * @see
     */
    @JSON
    public String updatePricePlan() {
	try {
	    PricePlanEntity dbEntity = expressPricePlanService
		    .modifyPricePlan(priceManageMentVo.getPricePlanEntity());
	    priceManageMentVo.setPricePlanEntity(dbEntity);
	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("修改保存方案主信息以及明细信息: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * <p>复制方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-26 下午2:54:22
     * @return
     * @see
     */
    @JSON
    public String copyPricePlan() {
	try {
	    String newPricePlanId = expressPricePlanService
		    .copyPricePlan(priceManageMentVo.getPricePlanId());
	    priceManageMentVo = expressPricePlanService
		    .queryCopyPricePlanInfo(newPricePlanId);
	    return returnSuccess(MessageType.COPY_PRICE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("复制价格方案: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * 导入方案和方案明细.
     * 
     * @return
     * @author zhangdongping
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
		// 收集excel文件中的产品信息map，key为产品名称，值为产品名称
		Map<String, String> productMap = new HashMap<String, String>();
		//收集excel文件中客户信息map,key为客户编码，值为客户编码
		Map<String,String> customerMap = new HashMap<String, String>();
		// 将Excel表格每行数据读入列表，开始收集信息，并作必要的检查
		makeExcelDtos(detailMap, regionMap, productMap,customerMap, sheet);
		if (detailMap == null || detailMap.size() < 1) {
		    throw new FileException("导入数据为空,请检查", "导入数据为空,请检查");
		}
		// 把产品信息map通过service查询出相关产品的实体
		Map<String, ProductEntity> productEntityMap = processProductInfo(productMap);
		// 把区域信息map通过service查询出相关的区域信息
		Map<String, PriceRegionExpressEntity> priceRegionEntityMap = processPriceRegionInfo(regionMap);
		//把客户信息map通过service查询出相关产品的实体
		Map<String,CustomerEntity> customerEntityMap = processCustomerInfo(customerMap);
		// 批量新增价格数据
		expressPricePlanService.addPricePlanBatch(detailMap,
			priceRegionEntityMap, productEntityMap,customerEntityMap);
	    }
	    return super.returnSuccess();
	} catch (FileException e) {
	    return super.returnError(e);
	} catch (IOException e) {
	    LOGGER.error(e.getMessage(), e);
	    return returnError("数据文件被破坏，请重新制作导入文件" + e);
	}catch(PricePlanException e){
		 LOGGER.error(e.getMessage(), e);
		 return returnError(e);
	} catch (Exception e) {
	    LOGGER.error(e.getMessage(), e);
	    return returnError("数据文件不符合规范，请重新制作导入文件" + e);
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
     * zhangdongping.
     * 
     * @param detailMap
     * @param regionMap
     * @param productMap
     * @param sheet
     * @date 2012-12-10 上午10:46:31
     */
    private void makeExcelDtos(Map<String, List<PricePlanDetailDto>> detailMap,
	    Map<String, String> regionMap, Map<String, String> productMap,
	    Map<String,String> customerMap,Sheet sheet) {
	StringBuilder builder = new StringBuilder();
	Map<String, String> detailCheckMap = new HashMap<String, String>();
	if (sheet != null && detailMap != null) {
	    // 获取物理行数
	    int rowCount = sheet.getPhysicalNumberOfRows();
	    // 根据行数循环
	    Row row = null;
	    // EXCEL行记录
	    if (rowCount < NumericConstants.NUM_3) {
		return;// no data
	    }
	    // 取得第一行数据
	    row = sheet.getRow(0);
	    // 看第一行到底有多少列数据，从第5列开始，是目的城市数据
	    Map<String, String> destRegionNameMap = new HashMap<String, String>();
	    Map<String, String> checkdestRegionNameMap = new HashMap<String, String>();
	    for (int colNum = 1; colNum < CELL_COUNT; colNum++) {
		Cell cell = row.getCell(colNum*NumericConstants.NUM_3+1);
		if (cell != null) {
		    // 取单元格值
		    String cellVal = ExcelHandleUtil.obtainStringVal(cell);
		    if (StringUtil.isNotBlank(cellVal)) {
			destRegionNameMap.put(colNum*NumericConstants.NUM_3 +1+ BLANK, cellVal);
			if (checkdestRegionNameMap.get(cellVal) == null) {
			    checkdestRegionNameMap.put(cellVal, cellVal);// 把目的地放入map，作为目的地只能有一条的判断数据源
			} else {
			    builder.append("目的地" + cellVal + "重复，请检查数据"); 

			}
		    } else {
			break;// 结束，没有数据了
		    }
		}
	    }
	    int totalColume = destRegionNameMap.size();
	    // 根据行数循环
	    for (int rowNum = 0; rowNum < rowCount / NumericConstants.NUM_3; rowNum++) {
		// 获取每行数据
		// 取得一行的数据
		Row row1 = sheet.getRow(rowNum * NumericConstants.NUM_3 + NumericConstants.NUM_2);
		//取得客户编码
		Cell cell = row1.getCell(0);
		String customerCode = null;
		if(cell != null) {
			customerCode = ExcelHandleUtil.obtainStringVal(cell);
		} else {
		    builder.append("第" + (rowNum + 1) + "行第1列数据不正确，请检查");
		}
		// 取始发区域名字
		cell = row1.getCell(1);
		String deptRegionName = null;
		if (cell != null) {
		    // 取单元格值
		    deptRegionName = ExcelHandleUtil.obtainStringVal(cell);
		    if (StringUtil.isBlank(deptRegionName)) {
			// 结束，没有数据了
			break;
		    }
		} else {
		    // 结束，没有数据了
		    break;
		}
		List<PricePlanDetailDto> alist = null;
		if(StringUtil.isBlank(customerCode)) {
			alist = detailMap.get(deptRegionName);
		} else {
			alist = detailMap.get(customerCode+"#"+deptRegionName);
		}
		
		if (CollectionUtils.isEmpty(alist)) {
		    alist = new ArrayList<PricePlanDetailDto>();
		    if(StringUtil.isBlank(customerCode)) {
		    	detailMap.put(deptRegionName, alist);
		    }else {
		    	detailMap.put(customerCode+"#"+deptRegionName, alist);
		    }
		    
		}
		 
		 
		// 取产品信息
		String productNames = null;
		cell = row1.getCell(NumericConstants.NUM_2);
		if (cell != null) {
		    // 取单元格值
		    productNames = ExcelHandleUtil.obtainStringVal(cell);
		    if (StringUtil.isBlank(productNames)) {
			builder.append("第" + (rowNum + NumericConstants.NUM_2) + "行第3列数据不正确，请检查");			 
		    }
		} else {
		    builder.append("第" + (rowNum + NumericConstants.NUM_2) + "行第3列数据不正确，请检查");
		    
		}
		// 取计费类型
		String caculateTypeNames = null;
		cell = row1.getCell(NumericConstants.NUM_3);
		if (cell != null) {
		    // 取单元格值
		    caculateTypeNames = ExcelHandleUtil.obtainStringVal(cell);
		    if (StringUtil.isBlank(caculateTypeNames)) {
			builder.append("第" + (rowNum + NumericConstants.NUM_3) + "行第4列数据不正确，请检查"); 
		    }
		} else {
		    builder.append("第" + (rowNum + NumericConstants.NUM_3) + "行第4列数据不正确，请检查");		    
		}
		// 连取两行数据，准备分析。因为三行数据是一个整体，所以一次取出来
		Row row2 = sheet.getRow(rowNum * NumericConstants.NUM_3 + NumericConstants.NUM_3);
		Row row3 = sheet.getRow(rowNum * NumericConstants.NUM_3 + NumericConstants.NUM_4);
		//列循环读取
		for (int colNum = NumericConstants.NUM_4; colNum < totalColume + NumericConstants.NUM_4; colNum++) {
		    PricePlanDetailDto detailDto = new PricePlanDetailDto();
		    //首重 下限
		    Cell cell11 = row1.getCell((colNum-NumericConstants.NUM_3)*NumericConstants.NUM_3+1);
		    //首重 上限
		    Cell cell12 = row1.getCell((colNum-NumericConstants.NUM_3)*NumericConstants.NUM_3+NumericConstants.NUM_2);
		    //首重 费用
		    Cell cell13 = row1.getCell((colNum-NumericConstants.NUM_3)*NumericConstants.NUM_3+NumericConstants.NUM_3);
		     //续重1 下限
		    Cell cell21 = row2.getCell((colNum-NumericConstants.NUM_3)*NumericConstants.NUM_3+1);
		     //续重1 上限
		    Cell cell22 = row2.getCell((colNum-NumericConstants.NUM_3)*NumericConstants.NUM_3+NumericConstants.NUM_2);
		     //续重1  费率
		    Cell cell23 = row2.getCell((colNum-NumericConstants.NUM_3)*NumericConstants.NUM_3+NumericConstants.NUM_3);
		     //续重2  下限
		    Cell cell31 = row3.getCell((colNum-NumericConstants.NUM_3)*NumericConstants.NUM_3+1);
		     //续重2  上限
		    Cell cell32 = row3.getCell((colNum-NumericConstants.NUM_3)*NumericConstants.NUM_3+NumericConstants.NUM_2);
		     //续重2  费率
		    Cell cell33 = row3.getCell((colNum-NumericConstants.NUM_3)*NumericConstants.NUM_3+NumericConstants.NUM_3);
		    
		    // 判断数据格式正确否 首重
		    if (cell11 != null
			    && cell11.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			builder.append("第" + (rowNum * NumericConstants.NUM_3 + NumericConstants.NUM_3) + "行第"
				+ (colNum + 1) + "列数据不正确，请检查");
			 
		    }
		    if (cell12 != null
			    && cell11.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			builder.append("第" + (rowNum * NumericConstants.NUM_3 + NumericConstants.NUM_3) + "行第"
				+ (colNum + NumericConstants.NUM_2) + "列数据不正确，请检查");
			 
		    }
		    if (cell13 != null
			    && cell11.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			builder.append("第" + (rowNum * NumericConstants.NUM_3 + NumericConstants.NUM_3) + "行第"
				+ (colNum + NumericConstants.NUM_3) + "列数据不正确，请检查");
			 
		    }
		    //续重1
		    if (cell21 != null
			    && cell21.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			builder.append("第" + (rowNum * NumericConstants.NUM_3 + NumericConstants.NUM_4) + "行第"
				+ (colNum + 1) + "列数据不正确，请检查");
			 
		    }
		    if (cell22 != null
			    && cell21.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			builder.append("第" + (rowNum * NumericConstants.NUM_3 + NumericConstants.NUM_4) + "行第"
				+ (colNum + NumericConstants.NUM_2) + "列数据不正确，请检查");
			 
		    }
		    if (cell23 != null
			    && cell21.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			builder.append("第" + (rowNum * NumericConstants.NUM_3 + NumericConstants.NUM_4) + "行第"
				+ (colNum + NumericConstants.NUM_3) + "列数据不正确，请检查");
			 
		    }
		  //续重2
		    if (cell31 != null
			    && cell31.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			builder.append("第" + (rowNum * 1 + NumericConstants.NUM_5) + "行第"
				+ (colNum + 1) + "列数据不正确，请检查");
			 
		    }
		    if (cell32 != null
			    && cell32.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			builder.append("第" + (rowNum * 1 + NumericConstants.NUM_5) + "行第"
				+ (colNum + NumericConstants.NUM_2) + "列数据不正确，请检查");
			 
		    }
		    if (cell33 != null
			    && cell33.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			builder.append("第" + (rowNum * 1 + NumericConstants.NUM_5) + "行第"
				+ (colNum + NumericConstants.NUM_3) + "列数据不正确，请检查");
			 
		    }
		    if (cell31 == null || cell11 == null || cell21 == null||cell32 == null || cell12 == null || cell22 == null||cell33 == null || cell13 == null || cell23 == null) {
			continue;// 目的区域没数据停止读取，直接下一个处理
		    }
		    // 读出清货价格和重货价格
		    double  weightDownline= cell11.getNumericCellValue();
		    double  weightOnline= cell12.getNumericCellValue();
		    double  firstFee= cell13.getNumericCellValue();
		    
		    double  weightDownlineOne= cell21.getNumericCellValue();
		    double  weightOnlineOne= cell22.getNumericCellValue();
		    double  feeRateOne= cell23.getNumericCellValue();
		    
		    double  weightDownlineTwo= cell31.getNumericCellValue();
		    double  weightOnlineTwo= cell32.getNumericCellValue();
		    double  feeRateTwo= cell33.getNumericCellValue();
		    // 开始组装数据
		    // 设置目的区域名称
		    detailDto.setArrvRegionName(destRegionNameMap.get((colNum-NumericConstants.NUM_3)*NumericConstants.NUM_3+1+ BLANK));
		    // 设置是否接货
		    detailDto.setCentralizePickup(FossConstants.YES);
		    detailDto.setSelfPickUp(FossConstants.YES);
		    // 设置产品名称
		    detailDto.setProductItemName(productNames);
		    // 设置首重
		    detailDto.setWeightDownline(new BigDecimal(weightDownline + BLANK));		   
		    detailDto.setWeightOnline(new BigDecimal(weightOnline + BLANK));
		    detailDto.setFirstFee(new BigDecimal(firstFee + BLANK));
		    //设置续重1
		    detailDto.setWeightDownlineOne(new BigDecimal(weightDownlineOne + BLANK));
		    detailDto.setWeightOnlineOne(new BigDecimal(weightOnlineOne + BLANK));
		    detailDto.setFeeRateOne(new BigDecimal(feeRateOne + BLANK));
		    //设置续重2
		    detailDto.setWeightDownlineTwo(new BigDecimal(weightDownlineTwo + BLANK));
		    detailDto.setWeightOnlineTwo(new BigDecimal(weightOnlineTwo + BLANK));
		    detailDto.setFeeRateTwo(new BigDecimal(feeRateTwo + BLANK)); 
		    
		    String checkKey = deptRegionName + "到"
				    + detailDto.getArrvRegionName() +  ",产品类型:"
				    + detailDto.getProductItemName() + BLANK;
		    if(!StringUtils.isBlank(customerCode)) {
		    	 checkKey = "客户编码为【"+customerCode+"】"+checkKey;
		    }
		    if (detailCheckMap.get(checkKey) == null) {
			detailCheckMap.put(checkKey, checkKey);
		    } else {
			builder.append(checkKey + "数据重复，请检查！");
			 
		    }
		    if (builder.length() > 0) {
			throw new FileException("价格导入时发生错误： "+builder.toString(),
				builder.toString());
		    }
		    // 收集校验后的信息
		    alist.add(detailDto);
		    if(StringUtils.isNotEmpty(detailDto.getArrvRegionName()))
			  {
			 regionMap.put(detailDto.getArrvRegionName(),
				    detailDto.getArrvRegionName());
			   }
		    if(StringUtils.isNotEmpty(deptRegionName))
		    {
		    	regionMap.put(deptRegionName, deptRegionName);
		    }
		    
		    productMap.put(productNames, productNames);
		    
		}
		if(!StringUtils.isBlank(customerCode)) {
			customerMap.put(customerCode,customerCode);
		}
		
	    }
	}
    }

    /**
     * 处理产品信息，避免service中事物处理过长.
     * 
     * @param nameMap
     * @return
     * @author zhangdongping
     * @date 2012-12-24 下午5:46:52
     * @see
     */
    private Map<String, ProductEntity> processProductInfo(
	    Map<String, String> nameMap) {
	// 非空判断
	if (nameMap == null || nameMap.size() < 1) {
	    throw new FileException("导入数据中没有产品信息，请检查", "导入数据中没有产品信息，请检查");
	}
	// 读取二级产品，因为价格数据都是二级的
	List<ProductEntity> productInfoList = productService
		.queryLevel2ProductInfo();
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
		throw new FileException("数据库中没有产品:" + productname + "，请检查",
			"数据库中没有产品:" + productname + "，请检查");
	    }
	}
	return nameEntityMap;
    }

    /**
     * 处理价格区域信息，批量根据名字把区域查询出来.
     * 
     * @param regionMap
     * @return
     * @author zhangdongping
     * @date 2012-12-24 下午5:49:54
     * @see
     */
    private Map<String, PriceRegionExpressEntity> processPriceRegionInfo(
	    Map<String, String> regionMap) {
	// 非空判断
	if (regionMap == null || regionMap.size() < 1) {
	    throw new FileException("导入数据中没有区域信息，请检查", "导入数据中没有区域与信息，请检查");
	}
	List<String> names = new ArrayList<String>();
	names.addAll(regionMap.keySet());
	// 批量查找区域数据
	Map<String, PriceRegionExpressEntity> findResult = regionExpressService
		.findRegionByNames(names, PricingConstants.PRICING_REGION,
			new Date());
	if (findResult == null || findResult.size() < 1) {

	    throw new FileException("数据库中没有匹配到导入的任何区域数据，请检查",
		    "数据库中没有匹配到导入的任何区域数据，请检查");
	}

	Iterator<String> it = regionMap.keySet().iterator();
	while (it.hasNext()) {
	    // 逐条验证有没有价格区域
	    String object = it.next();
	    if (findResult.get(object) == null) {
		throw new FileException("区域：" + object + "，在数据库中没有找到，请检查",
			"区域：" + object + "，在数据库中没有找到，请检查");
	    }
	}
	return findResult;
    }
    /**
     * 处理客户信息.
     * 
     * @param customerList
     * @return
     * @author dongjialing
     * @date 2015-01-12 下午2:00:30
     * @see
     */
    private Map<String,CustomerEntity> processCustomerInfo(
    		Map<String,String> customerMap) {
    	if(customerMap == null || customerMap.size()<1) {
    		return null;
    	}
    	Map<String,CustomerEntity> result = new HashMap<String, CustomerEntity>();
    	CustomerEntity customerEntity =null;
    	for (String c : customerMap.keySet()) {
    		if(!StringUtils.isBlank(c)) {
    			customerEntity=customerService.queryCustInfoByCode(c);
    			if(customerEntity !=null) {
        			result.put(c,customerEntity);
        		}else {
        			throw new FileException("客户编码为：" + c + "，在数据库中没有找到，请检查",
        					"客户编码为：" + c + "，在数据库中没有找到，请检查");
        			    }
        		
    		}
		}
    	if(result ==null || result.size()<1) {
    		throw new FileException("数据库中没有匹配到导入的任何客户数据，请检查",
    			    "数据库中没有匹配到导入的任何客户数据，请检查");
    	}
    	return result;
    	
    }
    /**
     * <p>
     * 价格方案导出
     * </p>
     * .
     * 
     * @return
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 上午10:54:12
     * @see
     */
    public String exportPricePlan() {
	try {
	    String fileName = PricingColumnConstants.EXPORT_PRICE_PLAN;
	    downloadFileName = URLEncoder.encode(
		    DateUtils.convert(new Date(), DateUtils.DATE_FORMAT)
			    + fileName, "UTF-8");
	    if (null == priceManageMentVo.getPricePlanEntity()) {
		priceManageMentVo.setPricePlanEntity(new PricePlanEntity());
	    }
	    // 导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    exportSetting
		    .setSheetName(PricingColumnConstants.EXPORT_PRICE_PLAN);
	    exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
	    // 存储导出数据
	    ExportResource exportResource = expressPricePlanService
		    .exportPricePlan(priceManageMentVo.getPricePlanEntity());
	    // 根据存储的数据调用导出类
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    inputStream = objExporterExecutor.exportSync(exportResource,
		    exportSetting);
	    return returnSuccess();
	} catch (UnsupportedEncodingException e) {
	    LOGGER.error("价格方案导出，出现异常: " + e.getMessage());
	    return returnError("价格方案明细导出，出现异常", e.getMessage());
	}
    }

    /**
     * <p>价格方案明细导出</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-22 下午5:26:14
     * @return
     * @see
     */
    public String exportPricePlanDetail() {
	try {
	    String fileName = PricingColumnConstants.EXPORT_PRICE_PLAN_DETAIL;
	    downloadFileName = URLEncoder.encode(
		    DateUtils.convert(new Date(), DateUtils.DATE_FORMAT)
			    + fileName, "UTF-8");
	    // 导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    exportSetting
		    .setSheetName(PricingColumnConstants.EXPORT_PRICE_PLAN_DETAIL);
	    exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
	    // 存储导出数据
	    ExportResource exportResource = expressPricePlanService
		    .exportPricePlanDetail(priceManageMentVo
			    .getQueryPricePlanDetailBean());
	    // 根据存储的数据调用导出类
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    inputStream = objExporterExecutor.exportSync(exportResource,
		    exportSetting);
	    return returnSuccess();
	} catch (UnsupportedEncodingException e) {
	    LOGGER.error("价格方案明细导出，出现异常: " + e.getMessage());
	    return returnError("价格方案明细导出，出现异常", e.getMessage());
	}
    }
    
    public static void main(String are[]) 
    {
	ExpressPricePlanAction ob=new ExpressPricePlanAction();
	File uploadFile=new File("c:\\test.xlsx");
	ob.setUploadFile(uploadFile);
	ob.importPrice();
    }
   
}
