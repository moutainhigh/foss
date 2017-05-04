/**
 *  initial comments.
 *  
 *  空运底价方案信息：
 *  
 *  1、	运价编号-航空公司下发的运价编号
 *  
 *  
 *  2、	配载部门-空运总调信息通过点击右边的“选择”按钮弹出配载部门界面
 *  
 *  
 *  3、	出发机场-根据航班号显示的某个出发城市机场，自动带出不可编辑。
 *  
 *  
 *  4、	航空公司-包含所有与德班业务往来的航空公司,根据录入的航班号默认自动带出不可修改
 *  
 *  
 *  5、	起飞时间 - 根据航班号显示，不可修改
 *  
 *  
 *  6、	到达时间-  根据航班号显示，不可修改
 *  
 *  
 *  7、	生效时间-当前配置信息开始时间
 *  
 *  
 *  8、	截止时间-当前配置信息结束时间
 *  
 *  
 *  9、	最低费用-当重量 * X费率 = X运费价格费率<最低费用时就用此处最低费用来代替目的为了控制成本价格
 *  
 *  
 *  10、	是否激活-选择是否激活可让管理员抉择是否立即激活本次维护的配置信息，默认为“否”
 *  
 *  
 *  11、	航班运费-默认显示按照重量标准费率表格支持增、删、改、导入、导出操作来管理运价费率。
 *  
 *  
 *  12、	备注描述： 备注信息
 *  
 *  
 *  	空运底价方案明细信息：
 *  
 *  
 *  	1.	目的地-到达机场所在城市
 *  
 *  
 *  	2.	货物类别-根据不同的航空公司显示不同货物类别提供选择录入
 *  
 *  
 *  	3.	航班号-航空公司规划的航班号
 *  
 *  
 *  	4.	最低一票-航空公司给出的最低一票
 *  
 *  
 *  	5.	45-3000kg在45-3000公斤固定范围内给予不同费率点设置（其规则一样不一一列举）
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/action/FlightPriceAction.java
 * 
 * FILE NAME        	: FlightPriceAction.java
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.NumericConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.FlightPriceVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 
 * 航空代理运价ACTION
 * 
 * 
 * 航空代理运价信息维护，参与者通过航空代理运价功能可以设置
 * 
 * 
 * 固定区间的价格信息如 45公斤一下、45公斤以上、100公斤以上、300公斤以上
 * 
 * 500公斤以上、1000公斤以上、2000公斤以上、3000公斤以上的不同运价费率
 * 
 * 信息。由配载部门、出发机场、航空公司，早中晚班。来决定一套运价的维度。
 * 
 * @author 张斌
 * 
 * @date 2012-11-21
 * 
 * @version 1.0
 */
public class FlightPriceAction extends AbstractAction {
    	/**
    	 * 
    	 * 序列化
    	 * 
    	 */
	private static final long serialVersionUID = 2883644272419312426L;
	/**
	 *
	 *日志处理
	 *
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AirPricePlanAction.class);
	/**
	 *
	 *航空运价 VO 交互对象 
	 *
	 */
	private FlightPriceVo flightPriceVo = new FlightPriceVo();
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
	 * <p>获得航空运价 VO 交互对象</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * 
	 * @date 2013-3-17 上午10:37:49
	 * 
	 * @return
	 * 
	 * @see
	 */
	public FlightPriceVo getFlightPriceVo() {
		return flightPriceVo;
	}
	/**
	 * 
	 * <p>设置航空运价 VO 交互对象</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-17 上午10:38:07
	 * 
	 * @param flightPriceVo
	 * 
	 * @see
	 */
	public void setFlightPriceVo(FlightPriceVo flightPriceVo) {
		this.flightPriceVo = flightPriceVo;
	}
	/**
	 * 
	 * 航空运价方案服务
	 * 
	 */
	private IFlightPricePlanService flightPricePlanService;
	
	/**
	 * 航空公司
	 */
	IAirlinesService airlinesService;
	
	
	/**
	 * 设置 航空公司.
	 *
	 * @param airlinesService the new 航空公司
	 */
	public void setAirlinesService(IAirlinesService airlinesService) {
	    this.airlinesService = airlinesService;
	}
	/**
	 * 组织信息服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 设置 组织信息服务.
	 *
	 * @param orgAdministrativeInfoService the new 组织信息服务
	 */
	public void setOrgAdministrativeInfoService(
		IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	    this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 货物类型服务
	 */
	private IGoodsTypeService goodsTypeService;
	
	/**
	 * 设置 货物类型服务.
	 *
	 * @param goodsTypeService the new 货物类型服务
	 */
	public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
	    this.goodsTypeService = goodsTypeService;
	}
	/**
	 * 
	 * <p>设置航空运价方案服务</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-17 上午10:38:35
	 * 
	 * @param flightPricePlanService
	 * 
	 * @see
	 */
	public void setFlightPricePlanService(
			IFlightPricePlanService flightPricePlanService) {
		this.flightPricePlanService = flightPricePlanService;
	}
	/**
	 * 
	 * 航空运价方案明细服务
	 *
	 */
	private IFlightPricePlanDetailService flightPricePlanDetailService;
	
	/**
	 * 
	 *  导入文件
	 *  
	 *   */
	private File uploadFile;
	/**
	 * 
	 * <p>注入航空运价方案明细服务</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-17 上午10:39:01
	 * 
	 * @param flightPricePlanDetailService
	 * 
	 * @see
	 */
	public void setFlightPricePlanDetailService(
			IFlightPricePlanDetailService flightPricePlanDetailService) {
		this.flightPricePlanDetailService = flightPricePlanDetailService;
	}
	
	public void setUploadFile(File uploadFile) {
	    this.uploadFile = uploadFile;
	}
	/**
	 * 
	 * 航班服务
	 * 
	 */
	private IFlightService flightService;
	/**
	 * 
	 * <p>注入航班服务</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-17 上午10:39:37
	 * 
	 * @param flightService
	 * 
	 * @see
	 */
	public void setFlightService(IFlightService flightService) {
		this.flightService = flightService;
	}
	/**
	 * 
	 * 行政组织服务
	 * 
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	/**
	 * 
	 * <p>注入行政组织服务</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-17 上午10:39:51
	 * 
	 * @param administrativeRegionsService
	 * 
	 * @see
	 */
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	/**
	 * .
	 * <p>
	 * 查询所有的运价<br/>
	 * 方法名：searchFlightPriceByCondition
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-05
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String searchFlightPriceByCondition() {
		try {
		    	//根据条件查询所有航空代理运价信息
			List<FlightPricePlanEntity> flightPricePlanEntityList  = flightPricePlanService.queryFlightPricePlanByEntity(flightPriceVo.getFlightPricePlanEntity(), start, limit);
			flightPriceVo.setFlightPricePlanEntityList(flightPricePlanEntityList);
			this.setTotalCount(flightPricePlanService.queryFlightPricePlanByEntityCount(flightPriceVo.getFlightPricePlanEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("查询所有航班的运价: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 新增运价<br/>
	 * 方法名：addFlightPrice
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-05
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String addFlightPrice() {
		try {
			FlightPricePlanEntity flightPricePlanEntity = flightPricePlanService.addFlightPricePlanInfo(flightPriceVo.getFlightPricePlanEntity());
			flightPriceVo.setFlightPricePlanEntity(flightPricePlanEntity);
			return returnSuccess(MessageType.SAVE_FLIGHTPRICE_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("新增航班运价: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 删除运价<br/>
	 * 方法名：deleteFlightPrice
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-05
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String deleteFlightPrice() {
		try {
			flightPricePlanService.deleteFlightPricePlanByIds(flightPriceVo.getIdList());
			return returnSuccess(MessageType.DELETE_FLIGHTPRICE_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("删除航班运价: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 激活运价<br/>
	 * 方法名：activeFlightPrice
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-05
	 * 
	 * @since JDK1.6
	 * 
	 */
	@JSON
	public String activeFlightPrice() {
		try {
			flightPricePlanService.activeFlightPricePlanByIds(flightPriceVo.getIdList());
			return returnSuccess(MessageType.ACTIVE_FLIGHTPRICE_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("激活航班运价: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 修改运价<br/>
	 * 方法名：updateFlightPrice
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-05
	 * 
	 * @since JDK1.6
	 * 
	 */
	@JSON
	public String updateFlightPrice() {
		try {
			flightPricePlanService.updateFlightPricePlanByEntity(flightPriceVo.getFlightPricePlanEntity());
			return returnSuccess(MessageType.UPDATE_FLIGHTPRICE_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("修改航班运价: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 查询航空运价BY ID<br/>
	 * 方法名：queryFlightPriceById
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-05
	 * 
	 * @since JDK1.6
	 * 
	 */
	@JSON
	public String queryFlightPriceById() {
		try {
			FlightPricePlanEntity flightPricePlanEntity = flightPricePlanService.getFlightPricePlanEntityById(flightPriceVo.getFlightPricePlanEntity().getId());
			flightPriceVo.setFlightPricePlanEntity(flightPricePlanEntity);
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("查询航班运价: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 查询所有的运价明细<br/>
	 * 方法名：searchFlightPriceDetailByCondition
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-05
	 * 
	 * @since JDK1.6
	 * 
	 */
	@JSON
	public String searchFlightPriceDetailByCondition() {
		try {
			List<FlightPricePlanDetailEntity> flightPricePlanDetailEntityList = flightPricePlanDetailService.queryFlightPricePlanDetailPagging(flightPriceVo.getFlightPricePlanDetailEntity(), start, limit);
			flightPriceVo.setFlightPricePlanDetailEntityList(flightPricePlanDetailEntityList);
			this.setTotalCount(flightPricePlanDetailService.queryFlightPricePlanDetailPaggingCount(flightPriceVo.getFlightPricePlanDetailEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("修改航班运价明细: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 新增运价明细<br/>
	 * 方法名：addFlightPriceDetail
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-05
	 * 
	 * @since JDK1.6
	 * 
	 */
	@JSON
	public String addFlightPriceDetail() {
		try {
			flightPricePlanDetailService.addFlightPricePlanDetail(flightPriceVo.getFlightPricePlanDetailEntity());
			return returnSuccess(MessageType.SAVE_FLIGHTPRICEDETAIL_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("新增航班运价明细: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 删除运价明细<br/>
	 * 方法名：deleteFlightPriceDetail
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-05
	 * 
	 * @since JDK1.6
	 * 
	 */
	@JSON
	public String deleteFlightPriceDetail() {
		try {
			flightPricePlanDetailService.deleteFlightPricePlanDetailById(flightPriceVo.getIdList());
			return returnSuccess(MessageType.DELETE_FLIGHTPRICEDETAIL_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("删除航班运价明细: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 修改运价明细<br/>
	 * 方法名：updateFlightPriceDetail
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-05
	 * 
	 * @since JDK1.6
	 * 
	 */
	@JSON
	public String updateFlightPriceDetail() {
		try {
			flightPricePlanDetailService.updateFlightPricePlanDetail(flightPriceVo.getFlightPricePlanDetailEntity());
			return returnSuccess(MessageType.UPDATE_FLIGHTPRICEDETAIL_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("修改航班运价明细: "+e.getMessage());
		    return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 查询航空运价明细BY ID<br/>
	 * 方法名：queryFlightPriceDetailById
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-05
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String queryFlightPriceDetailById() {
		try {
			FlightPricePlanDetailEntity flightPricePlanDetailEntity = flightPricePlanDetailService.queryFlightPricePlanDetailById(flightPriceVo.getFlightPricePlanDetailEntity().getId());
			flightPriceVo.setFlightPricePlanDetailEntity(flightPricePlanDetailEntity);
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("查询航空运价明细: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 升级版本<br/>
	 * 方法名：copyFlightPricePlanEntity
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-24
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String copyFlightPricePlanEntity() {
		try {
			FlightPricePlanEntity flightPricePlanEntity = flightPricePlanService.copyFlightPricePlanEntity(flightPriceVo.getFlightPricePlanEntity().getId());
			flightPriceVo.setFlightPricePlanEntity(flightPricePlanEntity);
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("升级航空运价明细: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 查询航班信息<br/>
	 * 
	 * 方法名：searchFlightInfo
	 * 
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2013-1-11
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String searchFlightInfo() {
		try {
			// 始发城市
			String orgCoe = flightPriceVo.getFlightEntity().getOrigCode();
			AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(orgCoe);
			flightPriceVo.getFlightEntity().setOrigCode(null);
			List<FlightEntity> flightEntityList = flightService.queryFlightListBySelective(flightPriceVo.getFlightEntity());
			List<FlightDto> flightDtoList = new ArrayList<FlightDto>();
			if (CollectionUtils.isNotEmpty(flightEntityList)) {
				for (FlightEntity flightEntity : flightEntityList) {
					FlightDto flightDto = new FlightDto();
					if (administrativeRegionsEntity != null) {
						flightDto.setDepartureAirportName(administrativeRegionsEntity.getName());
					}
					flightEntity.getOrigCode();
					flightDto.setFlightNo(flightEntity.getFlightNo());
					flightDto.setDepartureAirport(flightEntity.getDepartureAirport());
					flightDto.setDestinationAirport(flightEntity.getDestinationAirport());
					flightDto.setPlanArriveTime(flightEntity.getPlanArriveTime());
					flightDto.setPlanLeaveTime(flightEntity.getPlanLeaveTime());
					flightDtoList.add(flightDto);
				}
			}
			flightPriceVo.setFlightDtoList(flightDtoList);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("查询航班信息: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>立即中止</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-1 下午4:25:27
	 * 
	 * @return
	 * 
	 * @see
	 */
	@JSON
	public String immediatelyStopFlightPrice(){
	    try {
		flightPricePlanService.immediatelyStopFlightPrice(flightPriceVo.getFlightPricePlanEntity());
		return returnSuccess(MessageType.STOP_SUCCESS);
            } catch (BusinessException e) {
        	LOGGER.error("立即中止航班信息: "+e.getMessage());
        	return returnError(e);
            }
	}
	/**
	 * 
	 * <p>立即激活</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-1 下午4:25:27
	 * 
	 * @return
	 * 
	 * @see
	 */
	@JSON
	public String immediatelyActiveFlightPrice() {
		try {
			 Date nowDate= new Date();
			    if(nowDate!= null && flightPriceVo.getFlightPricePlanEntity().getBeginTime()!= null && nowDate.compareTo( flightPriceVo.getFlightPricePlanEntity().getBeginTime())>0)
			    {
			    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
			    }
			flightPricePlanService.immediatelyActiveFlightPrice(flightPriceVo.getFlightPricePlanEntity());
			return returnSuccess(MessageType.ACTIVE_FLIGHTPRICE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("立即激活航班信息: " + e.getMessage());
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * <p>导入航空运价</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-4-7 上午8:48:18
	 * @return
	 * @see
	 */
	public String importFlightPrice(){
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
				//读取最终数据结果，key为运价编号 ，值为航空价格数据明细
				Map<String,List<FlightPriceDto>> detailMap = new HashMap<String,List<FlightPriceDto>>(); 
				//收集excel文件中的目的站城市map ，key为城市名称，值为城市名称 
				Map<String,String> destCityMap = new HashMap<String,String>();
				//收集excel文件中的货物类型map， key为货物名称，值为货物名称
				Map<String,String> goodsTypeMap = new HashMap<String,String>();
				//收集excel文件中的航空公司map，key为航空公司名称，值为航空公司名称
				Map<String,String> airlinesMap = new HashMap<String,String>(); 
				//收集excel文件中的配载部门map，key为配载部门名称，值为配载部门名称
				Map<String,String> loadOrgMap = new HashMap<String,String>();
				//收集excel文件中的航班号map，key为航班号，值为航班号
				Map<String,String> flightNoMap = new HashMap<String,String>();
				
				//收集excel文件中的将Excel表格每行数据读入列表，开始收集信息，并作必要的检查
				makeExcelDtos(detailMap,destCityMap,goodsTypeMap,airlinesMap,loadOrgMap,flightNoMap,sheet);
				if(detailMap==null||detailMap.size()<1)
				{
				    throw new FileException("导入数据为空,请检查", "导入数据为空,请检查");
				}
				//把航空公司信息map通过service查询出相关的航空公司
				Map<String,AirlinesEntity> airlinesMaps = processAirlinesInfo(airlinesMap);
				//把配载部门信息map通过service查询出相关的配载部门
				Map<String,OrgAdministrativeInfoEntity> loadOrgMaps = processLoadOrgsInfo(loadOrgMap);
				//把目的城市信息map通过service查询出相关的行政区域
				Map<String,AdministrativeRegionsEntity> destCityMaps = processRegionInfo(destCityMap);  
				//把产品信息map通过service查询出相关的区域信息
				Map<String,GoodsTypeEntity> goodsTypeMaps = processGoodsTypeInfo(goodsTypeMap);
				//把航班类别信息map通过service查询出相关的区域信息
				Map<String,FlightDto> flightNoMaps = processFlightNoInfo(flightNoMap);
				//批量新增价格数据
				String temp = flightPricePlanService.addFlightPricePlanBatch(detailMap, airlinesMaps,
						loadOrgMaps, destCityMaps, goodsTypeMaps, flightNoMaps);
				if (temp != null) {
					return returnError(temp);
				}
			}
			return super.returnSuccess();
		} catch (FileException e) { 
			return super.returnError(e);
		} catch (IOException e) {
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
	 * 
	 * <p>分别收集Excel中的数据信息</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-4-7 上午9:18:30
	 * @param detailMap
	 * @param destCityMap
	 * @param goodsTypeMap
	 * @param airlinesMap
	 * @param loadOrgMap
	 * @param flightShiftMap
	 * @param sheet
	 * @see
	 */
	private void makeExcelDtos(Map<String,List<FlightPriceDto>> detailMap,Map<String,String>destCityMap,Map<String,String>goodsTypeMap,Map<String,String> airlinesMap,Map<String,String>loadOrgMap,Map<String,String>flightNoMap,Sheet sheet) {	       
	    Map<String,String> detailCheckMap = new HashMap<String,String>();
		if (sheet != null && detailMap != null) {
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// EXCEL行记录
			if (rowCount<NumericConstants.NUM_2)
			{
			    return ;//no data
			}
			//取得第一行数据
			// 根据行数循环
			for (int rowNum = 0; rowNum < rowCount-1; rowNum++) {
			    	
				// 获取每行数据
				// 取得一行的数据
				Row row1 = sheet.getRow(rowNum + 1); 
				//取运价号				
				Cell cell = row1.getCell(0); 
				
				String  freightNo = null;
				if (cell != null) {
				    // 取单元格值
				    freightNo = ExcelHandleUtil.obtainStringVal(cell);
				    if (StringUtil.isBlank(freightNo)) {
					break;//结束，没有数据了 
				    }  
				}else
				{
				    break;//结束，没有数据了 
				}
				//取配载部门
				String loadOrgNames = null;				
				cell = row1.getCell(1);  
				if (cell != null) {
				    // 取单元格值
				    loadOrgNames = ExcelHandleUtil.obtainStringVal(cell);
				    if (StringUtil.isBlank(loadOrgNames)) {
					throw new FileException("第"+(rowNum)+"行第2列数据不正确，请检查", "第"+(rowNum)+"行第2列数据不正确，请检查");
				    }  
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第2列数据不正确，请检查", "第"+(rowNum)+"行第2列数据不正确，请检查");
				}
				
				//取航空公司
				String airlinesNames = null;				
				cell = row1.getCell(NumericConstants.NUM_2);  
				if (cell != null) {
				    // 取单元格值
				    airlinesNames = ExcelHandleUtil.obtainStringVal(cell);
				    if (StringUtil.isBlank(loadOrgNames)) {
					throw new FileException("第"+(rowNum)+"行第3列数据不正确，请检查", "第"+(rowNum)+"行第3列数据不正确，请检查");
				    }  
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第3列数据不正确，请检查", "第"+(rowNum)+"行第3列数据不正确，请检查");
				}
				
				
				//以运价编号、配载部门、航空公司作为key
				List<FlightPriceDto> alist = detailMap.get(freightNo+","+loadOrgNames+","+airlinesNames);
				if(CollectionUtils.isEmpty(alist))
				{
				    alist = new ArrayList<FlightPriceDto>();
				    detailMap.put(freightNo+","+loadOrgNames+","+airlinesNames, alist);
				}
				
				//取货物类型
				String goodsTypeNames = null;				
				cell = row1.getCell(NumericConstants.NUM_3);  
				if (cell != null){
				    // 取单元格值
				    goodsTypeNames = ExcelHandleUtil.obtainStringVal(cell);
				    if (StringUtil.isBlank(goodsTypeNames)) {
					throw new FileException("第"+(rowNum)+"行第4列数据不正确，请检查", "第"+(rowNum)+"行第4列数据不正确，请检查");
				    }  
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第4列数据不正确，请检查", "第"+(rowNum)+"行第4列数据不正确，请检查");
				}
				
				//取航班号
				String flightNo = null;				
				cell = row1.getCell(NumericConstants.NUM_4);  
				if (cell != null) {
				    //取单元格值
				    flightNo = ExcelHandleUtil.obtainStringVal(cell);
				    if (StringUtil.isBlank(flightNo)) {
					throw new FileException("第"+(rowNum)+"行第5列数据不正确，请检查", "第"+rowNum+"行第5列数据不正确，请检查");
				    }  
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第5列数据不正确，请检查", "第"+(rowNum)+"行第5列数据不正确，请检查");
				}
				
				//取目的城市
				String destCityNames = null;				
				cell = row1.getCell(NumericConstants.NUM_5);  
				if (cell != null) {
				    //取单元格值
				    destCityNames = ExcelHandleUtil.obtainStringVal(cell);
				    if (StringUtil.isBlank(destCityNames)) {
					throw new FileException("第"+(rowNum)+"行第6列数据不正确，请检查", "第"+rowNum+"行第6列数据不正确，请检查");
				    }  
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第7列数据不正确，请检查", "第"+(rowNum)+"行第7列数据不正确，请检查");
				}
				
				//取最低一票
				Long minFee = null;				
				cell = row1.getCell(NumericConstants.NUM_6);  
				if (cell != null) {
				    //取单元格值
				    //检查最低一票必须是整形
				    String mindoubleVal=cell.getNumericCellValue()+BLANK;
				    String subValue=mindoubleVal.substring(mindoubleVal.lastIndexOf("."));
				    double subValueD=new Double(subValue);
				    if(BigDecimal.ZERO.compareTo(BigDecimal.valueOf(subValueD)) != 0)
				    {
					throw new FileException("第"+(rowNum)+"行第8列最低一票数据必须为整数，请检查", "第"+(rowNum)+"行第8列最低一票数据必须为整数，请检查");
				    }
				    minFee = Double.valueOf(cell.getNumericCellValue()).longValue(); 
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第9列数据不正确，请检查", "第"+(rowNum)+"行第9列数据不正确，请检查");
				}
				
				//取45公斤以下
				BigDecimal down45 = null;
				cell = row1.getCell(NumericConstants.NUM_7);
				if(cell != null)
				{
				    //取单元格值
				    String decimalVal = cell.getNumericCellValue()+BLANK;
				    if(StringUtil.isNotBlank(decimalVal)){
					  double doubleValue = new Double(decimalVal);
					  if(doubleValue<0){
					      continue;
					  }
					  down45 = BigDecimal.valueOf(doubleValue);
				    }
				}else{
				    throw new FileException("第"+(rowNum)+"行第10列数据不正确，请检查", "第"+(rowNum)+"行第10列数据不正确，请检查");
				}
				
				//取45公斤以上
				BigDecimal up45 = null;
				cell = row1.getCell(NumericConstants.NUM_8);
				if(cell != null)
				{
				    //取单元格值
				    String decimalVal = cell.getNumericCellValue()+BLANK;
				    if(StringUtil.isNotBlank(decimalVal)){
					  double doubleValue = new Double(decimalVal);
					  if(doubleValue<0){
					      continue;
					  }
					  up45 = BigDecimal.valueOf(doubleValue);
				    }
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第10列数据不正确，请检查", "第"+(rowNum+NumericConstants.NUM_2)+"行第5列数据不正确，请检查");
				}
				//取100公斤以上
				BigDecimal up100 = null;
				cell = row1.getCell(NumericConstants.NUM_9);
				if(cell != null)
				{
				    //取单元格值
				    String decimalVal = cell.getNumericCellValue()+BLANK;
				    if(StringUtil.isNotBlank(decimalVal)){
					  double doubleValue = new Double(decimalVal);
					  if(doubleValue<0){
					      continue;
					  }
					  up100 = BigDecimal.valueOf(doubleValue);
				    }
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第11列数据不正确，请检查", "第"+(rowNum)+"行第5列数据不正确，请检查");
				}
				//取300公斤以上
				BigDecimal up300 = null;
				cell = row1.getCell(NumericConstants.NUM_10);
				if(cell != null)
				{
				    //取单元格值
				    String decimalVal = cell.getNumericCellValue()+BLANK;
				    if(StringUtil.isNotBlank(decimalVal)){
					  double doubleValue = new Double(decimalVal);
					  if(doubleValue<0){
					      continue;
					  }
					  up300 = BigDecimal.valueOf(doubleValue);
				    }
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第12列数据不正确，请检查", "第"+(rowNum)+"行第5列数据不正确，请检查");
				}
				//取500公斤以上
				BigDecimal up500 = null;
				cell = row1.getCell(NumericConstants.NUM_11);
				if(cell != null)
				{
				    //取单元格值
				    String decimalVal = cell.getNumericCellValue()+BLANK;
				    if(StringUtil.isNotBlank(decimalVal)){
					  double doubleValue = new Double(decimalVal);
					  if(doubleValue<0){
					      continue;
					  }
					  up500 = BigDecimal.valueOf(doubleValue);
				    }
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第13列数据不正确，请检查", "第"+(rowNum)+"行第13列数据不正确，请检查");
				}
				//取1000公斤以上
				BigDecimal up1000 = null;
				cell = row1.getCell(NumericConstants.NUM_12);
				if(cell != null)
				{
				    //取单元格值
				    String decimalVal = cell.getNumericCellValue()+BLANK;
				    if(StringUtil.isNotBlank(decimalVal)){
					  double doubleValue = new Double(decimalVal);
					  if(doubleValue<0){
					      continue;
					  }
					  up1000 = BigDecimal.valueOf(doubleValue);
				    }
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第14列数据不正确，请检查", "第"+(rowNum)+"行第14列数据不正确，请检查");
				}
				//取2000公斤以上
				BigDecimal up2000 = null;
				cell = row1.getCell(NumericConstants.NUM_13);
				if(cell != null)
				{
				    //取单元格值
				    String decimalVal = cell.getNumericCellValue()+BLANK;
				    if(StringUtil.isNotBlank(decimalVal)){
					  double doubleValue = new Double(decimalVal);
					  if(doubleValue<0){
					      continue;
					  }
					  up2000 = BigDecimal.valueOf(doubleValue);
				    }
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第15列数据不正确，请检查", "第"+(rowNum)+"行第15列数据不正确，请检查");
				}
				//取3000公斤以上
				BigDecimal up3000 = null;
				cell = row1.getCell(NumericConstants.NUM_14);
				if(cell != null)
				{
				    //取单元格值
				    String decimalVal = cell.getNumericCellValue()+BLANK;
				    if(StringUtil.isNotBlank(decimalVal)){
					  double doubleValue = new Double(decimalVal);
					  if(doubleValue<0){
					      continue;
					  }
					  up3000 = BigDecimal.valueOf(doubleValue);
				    }
				}else
				{
				    throw new FileException("第"+(rowNum)+"行第16列数据不正确，请检查", "第"+(rowNum)+"行第16列数据不正确，请检查");
				}
				//开始组装数据
				FlightPriceDto flightPriceDto = new FlightPriceDto();
				//运价号
				flightPriceDto.setPriceNo(freightNo);
				//配载部门
				flightPriceDto.setLoadOrgName(loadOrgNames);
				//航空公司
				flightPriceDto.setAirlinesName(airlinesNames);
				//设置目的区域名称
				flightPriceDto.setDestDistrictName(destCityNames+BLANK);
				//设置货物名称
				flightPriceDto.setGoodsTypeName(goodsTypeNames);
				//设置航班号
				flightPriceDto.setFlightNo(flightNo);
				//设置最低一票
				flightPriceDto.setMinFee(minFee);				 				   
				flightPriceDto.setDown45Kg(down45);
				flightPriceDto.setUp45Kg(up45);
				flightPriceDto.setUp100Kg(up100);
				flightPriceDto.setUp300Kg(up300);
				flightPriceDto.setUp500Kg(up500);
				flightPriceDto.setUp1000Kg(up1000);
				flightPriceDto.setUp2000Kg(up2000);
				flightPriceDto.setUp3000Kg(up3000);
				//检查 始发区域，到达区域 ，是否接货，货物类型，航班类型是否在detailCheckMap中出现重复
				//出现重复那么就要提示异常信息了。
				StringBuilder builder = new StringBuilder();
				builder.append("运价编号:"+freightNo);
				builder.append("航空公司:"+airlinesNames);
				builder.append("配载部门:"+loadOrgNames);
				builder.append("货物类型:"+goodsTypeNames);
				builder.append("航班号:"+flightNo);
				builder.append("目的站:"+destCityNames);
				String checkKey = builder.toString();
				if(detailCheckMap.get(checkKey)==null)
				{
				  detailCheckMap.put(checkKey, checkKey);
				}
				else
				{
				  throw new FileException(checkKey+"数据重复，请检查！",  checkKey+"，数据重复，请检查！");
				}
				alist.add(flightPriceDto);
				//处理相关通过校验后的数据信息
    				//到达城市
    				destCityMap.put(destCityNames, destCityNames);
    				//货物类型
    				goodsTypeMap.put(goodsTypeNames, goodsTypeNames);
    				//航班号
    				flightNoMap.put(flightNo, flightNo);
    				//航空公司
    				airlinesMap.put(airlinesNames, airlinesNames);
				//配载部门
				loadOrgMap.put(loadOrgNames, loadOrgNames);
			}
		}
	}
	
	/**
	 *  处理城市行政区域信息，批量根据名字把行政区域查询出来
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-24 下午5:49:54
	 * @param regionMap
	 * @return
	 * @see
	 */
	private Map<String, AdministrativeRegionsEntity> processRegionInfo(Map<String, String> regionMap) {
	    //非空判断
	     if(regionMap==null||regionMap.size()<1)
	     {
		 throw new FileException( "导入数据中没有区域信息，请检查", "导入数据中没有区域与信息，请检查" );
	     }
	     List< String>  names = new ArrayList<String>(); 
	     names.addAll(regionMap.keySet());
	     //通过检查后的数据集
	     Map<String, AdministrativeRegionsEntity> returnMap = new HashMap<String, AdministrativeRegionsEntity>();
	     //批量查找城市数据
	     for (String regionName : names) {
		 AdministrativeRegionsEntity resultBean = administrativeRegionsService.queryAdministrativeRegionsByName(regionName);
		 if(resultBean!=null){
		     returnMap.put(resultBean.getName(), resultBean);
		 }else{
		     throw new FileException( "城市："+regionName+"，在数据库中没有找到，请检查", "城市："+regionName+"，在数据库中没有找到，请检查" );
		 }
	     }
	     return returnMap;
	 }
	/**
	 * <p>处理航班号信息</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-3-14 下午4:51:03
	 * @param flightShiftMap
	 * @return
	 * @see
	 */
	private Map<String,FlightDto> processFlightNoInfo(Map<String, String> nameMap) {
	    //非空判断
	     if(nameMap==null||nameMap.size()<1)
	     {
		 throw new FileException( "导入数据中没有航班信息，请检查", "导入数据中没有航班信息，请检查" );
	     } 
	     Map<String, FlightDto> flightNoEntityMap = new HashMap<String,FlightDto>();
	     Iterator<String> keyIt = nameMap.keySet().iterator();
	     while(keyIt.hasNext())
	     {
		//逐个比对，是否导入的航班号在数据库里存在
		String flightNo = keyIt.next();
		FlightEntity flightEntity = new FlightEntity();
		flightEntity.setActive(FossConstants.ACTIVE);
		flightEntity.setFlightNo(flightNo);
		List<FlightEntity> infoList = flightService.queryFlightListBySelective(flightEntity);
		if(CollectionUtils.isEmpty(infoList))
		{
		    throw new FileException( "数据中没有对应的航班信息，请检查", "数据中没有对应的航班信息，请检查" );
		}
		for(int loop=0;loop<infoList.size();loop++)
		{
		   FlightEntity object = infoList.get(loop);
		   if(StringUtil.equals(flightNo, object.getFlightNo()))
		   {
		       FlightDto flightDto = new FlightDto();
		       flightDto.setFlightNo(object.getFlightNo());
		       flightDto.setDepartureAirport(object.getDepartureAirport());
		       flightDto.setDestinationAirport(object.getDestinationAirport());
		       flightDto.setPlanArriveTime(object.getPlanArriveTime());
		       flightDto.setPlanLeaveTime(object.getPlanLeaveTime());
		       flightNoEntityMap.put(flightNo,flightDto);
		       //找到后这次循环结束		       
		       break;
		   } 
		}
		if(flightNoEntityMap.get(flightNo)==null)
		{
		    //最后都没找到，那就是出问题了
		    throw new FileException( "导入数据中没有航班信息:"+flightNo+"，请检查", "导入数据中没有航班信息:"+flightNo+"，请检查" );
		}
	     } 
	     return flightNoEntityMap; 
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
	private Map<String, GoodsTypeEntity> processGoodsTypeInfo(Map<String, String> nameMap){
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
	 *处理航空公司信息
	 *该方法主要根据excel中的航空公司信息去数据库匹配
	 *是否存在该航空公司信息，以确保excel所录入的信息的正确性。
	 * @param airlines 
	 * @return 
	 */
	private Map<String, AirlinesEntity> processAirlinesInfo(Map<String,String> airlinesNames) {
	    List<AirlinesEntity> resultList = airlinesService.queryAllAirlines();
	    if(CollectionUtils.isEmpty(resultList))
	    {
		throw new FileException( "数据库中基础数据不完整，请检查", "数据库中基础数据不完整，请检查，请检查" );
	    }
	    Map<String,AirlinesEntity> rutrunMap = new HashMap<String, AirlinesEntity>();
	    Iterator<String> keyIt = airlinesNames.keySet().iterator();
	    while(keyIt.hasNext())
	    {
		//逐个对比在数据库中是否存在
		String airlinesName = keyIt.next();
		for (AirlinesEntity airlinesEntity : resultList) 
		{
		    if(airlinesName.equalsIgnoreCase(airlinesEntity.getName()))
		    {
			rutrunMap.put(airlinesName,airlinesEntity);
			break;
		    }
		}
		if(rutrunMap.isEmpty()){
		    //最后都没找到，那就是出问题了
		    throw new FileException( "导入数据中没有配载部门信息:"+airlinesName+"，请检查", "导入数据中没有配载部门信息:"+airlinesName+"，请检查" );
		}
	    }
	    return rutrunMap;
	}
	
	/**
	 * 
	 *处理配载部门信息
	 *该方法主要根据excel中的配载部门信息去数据库匹配
	 *是否存在该配载信息，以确保excel所录入的信息的正确性。
	 * @param airlines 
	 * @return 
	 */
	private Map<String,OrgAdministrativeInfoEntity> processLoadOrgsInfo(Map<String,String> loadOrgNames) {
	    OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = new OrgAdministrativeInfoEntity();
	    orgAdministrativeInfoEntity.setActive(FossConstants.ACTIVE);
	    orgAdministrativeInfoEntity.setDoAirDispatch(FossConstants.ACTIVE);
	    List<OrgAdministrativeInfoEntity> resultList = orgAdministrativeInfoService.queryOrgAdministrativeInfoExactByEntity(orgAdministrativeInfoEntity,0,NumberConstants.NUMBER_1000);
	    if(CollectionUtils.isEmpty(resultList))
	    {
		throw new FileException( "数据库中配载部门基础数据不完整，请检查", "数据库中配载部门基础数据不完整，请检查" );
	    }
	    Map<String,OrgAdministrativeInfoEntity> rutrunMap = new HashMap<String, OrgAdministrativeInfoEntity>();
	    Iterator<String> keyIt = loadOrgNames.keySet().iterator();
	    while(keyIt.hasNext())
	    {
		//逐个对比在数据库中是否存在
		String loadOrgName = keyIt.next();
		for (OrgAdministrativeInfoEntity orgEntity : resultList) 
		{
		    if(loadOrgName.equalsIgnoreCase(orgEntity.getName()))
		    {
			rutrunMap.put(loadOrgName,orgEntity);
			break;
		    }
		}
		if(rutrunMap.isEmpty()){
		    //最后都没找到，那就是出问题了
		    throw new FileException( "导入数据中没有配载部门信息:"+loadOrgName+"，请检查", "导入数据中没有配载部门信息:"+loadOrgName+"，请检查" );
		}
	    }
	    return rutrunMap;
	}

}