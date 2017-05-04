/**
 * 
 *  initial comments.
 *  
 */
/*******************************************************************************
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/AirWaybillDetailService.java
 *  
 *  FILE NAME          :AirWaybillDetailService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  显示数据规则：
 * 1、	如果输入的航空公司和正单号已经生成合大票清单，则清单明细、到达网点、航班号、日期和目的站来源于已有的合大票清单，
 * 可以修改；
 * 2、	如果输入的航空公司和正单号还没有生成合大票清单，则清单明细、到达网点、航班号、日期和目的站来源于正单信息，
 * 可以修改，清单明细中的是否中转默认为否；
 *  
 *  导出按钮必须在保存之后才能使用；
 *  
 *  1、	到达网点为合大票清单中的到达网点名称；

 *2、	正单号为合大票清单中的正单号；

 *3、	航班为合大票清单中的航班号；

 *4、	航班日期为合大票清单中的航班时间；

 *5、	列表中的相应字段均来自清单明细中字段信息，且列出清单明细每一行数据，其中收货人为：收货人姓名+联系方式，合计信息为所有行的部分字段之和，求和字段为件数、毛重、计费重量、派送费、到付款、代收款；

 * 6、	制表人为新增合大票清单人员姓名；

 *7、	日期为新增合大票清单时间；

 *8、	德邦物流：86769350/51/52/53为固定字样；

 *9、	模板中根据当前空运总调为广州空运总调则为“德 邦 物 流 合 票 提 货 清 单（广州）”，如果是深圳空运总调则为“德 邦 物 流 合 票 提 货 清 单（深圳）”；

 *10、	文件名称为航班号+“-”+正单号；

 *  
 *  
 *  
SR-1	目的站输入限制为航空线路信息中所在城市；

SR-2	到达网点选择范围为已输入的目的站城市名称模糊匹配提货网点基础信息中的目的站名称；

SR-3	配载类型为合大票和合大票外发时，收货人信息为到达网点代理；

SR-4	配载货物为单独开单和单独开单外发，收货人信息为“分单合票”中的运单的收货人信息；

SR-5	结算事项为所输入的航空公司的航空费率中的运价号，可手动修改；

SR-6	填开代理为输入的航空公司的航空公司代理人基础信息中代理人编码，不可修改；

SR-7	储运事项根据前面“正单号”下拉框中选择的航空公司二字代码（如CZ、CA）自动生成，生成规则为：“仅限”+“所选航空公司简称”+“承运”，如“仅限南航承运”，可添加其他信息；

SR-8	毛重来源于分单合票提交时运单的总毛重，可修改，只能大于0，精度为保留小数点后一位有效数字；计费重量计算规则为：如果（分单合票提交时的总体积*1000）/6大于分单合票提交的总毛重，则计费重量为（分单合票提交时的总体积*1000）/6，否则计费重量即为分单合票提交的总毛重，精度为保留小数点后一位有效数字，可修改；

SR-9	航班选择范围是始发城市和到达城市为当前页面中的始发城市和到达城市；

SR-10	航班日期默认为当天，格式为yyyy-mm-dd,起飞时间和到达时间为所选航班信息中的时间点，可修改；

SR-11	当配载类型为合大票和单独开单，承运人/外发代理为所选航空公司二字代码，否则为空运外发代理信息，可以选择；

SR-12	申明价值默认为NVD，商品代号默认为9000，不可更改

SR-13	相关费用规则，：
1、	航空运费：计费重量*运价，当航空运费小于航空公司费率基础资料的最低收费时，航空运费等于基础资料最低收费；当航空运费大于且等于航空公司费率基础资料的最低收费时，航空运费保持不变；小数点后保留2位；
2、	地面运费：计费重量*地面运输费率，当地面运费小于基础资料里最低搬运费时，地面运费等于基础资料最低搬运费；当地面运费大于等于基础资料里最低搬运费时，地面运费保持不变；小数点后保留2位；
3、	燃油附加税：计费重量*燃油附加费率，当燃油附加税小于基础资料最低燃油费时，燃油附加税等于基础资料最低燃油费；当燃油附加税大于且等于基础资料最低燃油费时，燃油附加税保持不变；
4、	保险费：保险费为航空公司费率中的基础资料的最低保险；小数点后保留2位；
5、	附加费：地面运费+燃油附加税；
6、	运输保险：手动输入；
7、	总金额：航空运费+地面运费+保险费+燃油附加税，总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，大于并且等于航空公司费率基础资料的最低总金额时，总金额保持不变；小数点后保留2位；
8、	费用说明来源基础资料，打开录入航空正单界面时，根据当前总调部门，得到相应的费用说明信息；
9、	 总金额、航空运费、附加费、地面运费、燃油附加税、保险费不可以编辑；
10、	当配载类型为单独开单外发和合大票外发时，制单费变为可编辑状态，否则不可编辑且为空；

SR-14	提货方式默认为机场自提，不可修改；

SR-15	配载类型为单独开单和单独开单外发，运单明细只能有一个运单；

SR-16	1、	合票号生成规则：DP+不重复的七位数字，且在系统中保持唯一；
2、	如果配载类型为单独开单外发和合大票外发，则正单号默认为合票号，且允许修改，当外发代理反馈正单号后再进行修改为实际正单号；
3、	如果配载类型为单独开单和合大票则正单号为空，需要手工填入指定正单号

SR-17	保存后，航空正单的跟踪状态为待跟踪状态，交接情况为未交接，相关运单的流水号配载状态为已配载；

SR-18	分单合票查询条件所查运单为始发配载部门为本部门且营业部已交接空运运单，如果一个运单有不同的库存状态，则分行显示不同状态下的运单，件数为当前库存状态件数，重量和体积按（当前库存状态件数/总件数）*总重量（总体积）显示；查询显示前，清空待选信息列表和合票信息列表；

SR-19	提交合票按钮是提交合票信息所有运单明细，提交合票后，合票信息列表中清空成功提交的数据，可以再次查询出来；

SR-20	对于相同运单，如果提交的流水号有重复则覆盖前一次提交的，如果有新的，则提交到正单界面包含有最新的流水号；

SR-21	提交合票时，运单目的站不统一，提示“该单号的目的站与合票中的目的站不符，请确定是否添加”，由空运总调操作员选择，选择“是”则提交，选择“否”则不提交；

SR-22	配载类型为合大票或合大票外发，提交合票时当合票内有需要单独开单的货物，提示“包含单独开单的运单，你确要合大票吗？”，由空运总调操作员选择，选择“是”则提交合票信息至航空正单界面运单明细中，选择“否”则不提交合票信息；

SR-23	制作正单里要检查流水的重复性，需要保证合大票不重复，且一票货只能有一个到达代理

 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IAirlinesValueAddService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
//import com.deppon.foss.module.pickup.pricing.api.server.service.IAirlinesValueAddService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IFlightPricePlanDetailService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanDetailEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirStowageService;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDetailDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirHandOverBillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirLockWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillDetailService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillToCubcService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IPushAirPickUpInfoService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirLockWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSerialNoDetail;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillTempEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWaybillDetailDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.WaybillDetailDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.WaybillInfoForNoticeDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirWayBillException;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.PointsSingleJointTicketException;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToTangYiService;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.dto.AwbBasicDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.AwbBeanUtils;
import com.deppon.foss.module.transfer.common.api.shared.dto.AwbChargeDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseToCubcCallBack;
import com.deppon.foss.module.transfer.common.api.shared.dto.WayBillAssembleDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

import javax.annotation.Resource;

/**
 * 录入航空正单service实现类.
 * @author 099197-foss-zhoudejun
 * @date 2012-12-25 下午5:59:44
 */ 
public class AirWaybillDetailService implements IAirWaybillDetailService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AirWaybillDetailService.class);
	
	/** 查询航空正单dao. */
	private IAirWaybillDetailDao pointsSingleJointTicketDao;
	
	/** 结算service. */
	private IAirStowageService airStowageService;
	
	/** 综合service. */
	private IAirlinesService airlinesService;
	
	/** 航空基础费率service. */
	@Resource
	private IAirlinesValueAddService airlinesValueAddService4Dubbo;
	
	/** 调用系统参数. */
	private IConfigurationParamsService configurationParamsService;
	
	/** 调用航空最低运费. */
	@Resource
	private IFlightPricePlanDetailService flightPricePlanDetailService4Dubbo;
	
	/** 注入中转公共服务. */
	private ITfrCommonService tfrCommonService;
	
	/** 注入航空正单dto. */
	private AirWaybillDetailDto airWaybillDetailDto =  new AirWaybillDetailDto ();
	
	/** 注入外场服务. */
	private IOutfieldService outfieldService;
	
	/** 注入库区服务. */
	private IGoodsAreaService goodsAreaService;
	
	/** 部门 复杂查询 service*/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/** 注入唐翼制单service*/
	private IFOSSToTangYiService fossToTangYiService;
	
	/** 注入交接单service*/
	private IAirHandOverBillService airHandOverBillService;
	
	/** 查找空运总调 service*/
	private IAirDispatchUtilService airDispatchUtilService;
	
	/** 空运索票service*/
	private IAirLockWaybillService airLockWaybillService;
	
	//注入运单service
	private IWaybillManagerService waybillManagerService;
	
	//产品接口
	@Resource
	private IProductService productService4Dubbo;
	
	/**FOSS推送数据至OPP Service**/
	private IPushAirPickUpInfoService pushAirPickUpInfoService;
	
	/** FOSS同步推送数据至CUBC Service*/
	private IFossToCubcService fossToCubcService;

	/** FOSS异步推送数据至CUBC Service*/
	private IAirWaybillToCubcService airWaybillToCubcService;
	
	/** 灰度工具类 */
	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	/**
	 * 查询运单明细.
	 * @param ticketDto the ticket dto
	 * @return 返回空运货量明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-19 下午5:30:01
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AirWaybillDetailEntity> queryWaybillEntity(
			AirWaybillDetailDto ticketDto) {
		LOGGER.info("获取运单明细开始.");
        //快递空运不能选择未入库
        /*if(AirfreightConstants.PACKAGE_AIR.equals(ticketDto.getTransportType())
                &&StringUtil.equals(TransportPathConstants.TRANSPORTPATH_STATUS_NOTINSTORE,ticketDto.getStockStatus())){
            throw new AirWayBillException("快递空运不能选择未入库！");
        }else if(AirfreightConstants.PACKAGE_AIR.equals(ticketDto.getTransportType())&&
                StringUtils.isBlank(ticketDto.getNextDestOrg())){
            throw new AirWayBillException("快递空运没有选择到达网点！");
        }*/
		List<AirWaybillDetailEntity> wbList = new ArrayList<AirWaybillDetailEntity>();
		List<String> freightMethodList = new ArrayList<String>();
		List<String> handoverbillStateList = new ArrayList<String>();
		//库存状态
		String stockStatus = ticketDto.getStockStatus();
		List<String> stockStatusList = new ArrayList<String>();
		//开单方式
		String makeWaybillWay = ticketDto.getMakeWaybillWay();
		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		/**
		 * ISSUE-3555 ISSUE-3554 录入航空正单明细-分单合票调整 
		 * wqh 2013-08-19
		 * */
		
		OutfieldEntity outfiledEntry=new OutfieldEntity();
		outfiledEntry.setAirDispatchCode(orgAdministrativeInfoEntity.getCode());
		String transferCode;
		ticketDto.setHandoverbillOrgCode(orgAdministrativeInfoEntity.getCode());
		//根据当前空运总调获取空运总调对应的外场
			try {
				 transferCode = outfieldService.queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());		
				
			} catch (BusinessException e) {
				throw new AirWayBillException(e.getErrorCode());
			}
            //设置库区类型
		    String goodsAreaType = "";
            if(AirfreightConstants.PACKAGE_AIR.equals(ticketDto.getTransportType())) {
                goodsAreaType = DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS;
            }else {
                goodsAreaType = DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT;
            }
        //判断当前登录部门是否是空运总调
			if(StringUtil.equals(FossConstants.YES,orgAdministrativeInfoEntity.getAirDispatch())){
					// transferCode = outfieldService.queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
					//更加外场编码获取库区
					List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,goodsAreaType);
					GoodsAreaEntity goodsAreaEntity = null;
					if(!CollectionUtils.isEmpty(goodsAreaList)){
						goodsAreaEntity = (GoodsAreaEntity)goodsAreaList.get(0);
						//设置库区编码
						ticketDto.setGoodsAreaCode(goodsAreaEntity.getGoodsAreaCode());
					}
					ticketDto.setDestOrgCode(transferCode);
//				
			}else{
					OrgAdministrativeInfoEntity currentOrgCodeEntity = getBigOrgCode(orgAdministrativeInfoEntity.getCode());
					if(currentOrgCodeEntity==null){
						throw new AirWayBillException("当前库存部门不存在!");
					}
					ticketDto.setDestOrgCode(getBigOrgCode(orgAdministrativeInfoEntity.getCode()).getCode());
					ticketDto.setGoodsAreaCode("");
			}
			
			/**
			 * ISSUE-3555 ISSUE-3554 录入航空正单明细-分单合票调整 
			 * wqh 2013-08-19
			 * */
			//给对应的空运总调的外场赋值
			ticketDto.setHandOverbillTFROrgCode(transferCode);
			
		//设置库存中的和未入库两种状态
		if(StringUtil.equals(AirfreightConstants.StATUS_NULL, stockStatus)){
			handoverbillStateList.add(AirfreightConstants.STOCKSTATUS20);
			handoverbillStateList.add(AirfreightConstants.STOCKSTATUS30);
			ticketDto.setStockStatus(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME);
			stockStatusList.add(TransportPathConstants.TRANSPORTPATH_STATUS_INSTORE);
			stockStatusList.add(TransportPathConstants.TRANSPORTPATH_STATUS_NOTINSTORE);
		//设置未入库
		}else if(StringUtil.equals(TransportPathConstants.TRANSPORTPATH_STATUS_NOTINSTORE,stockStatus)){
			handoverbillStateList.add(AirfreightConstants.STOCKSTATUS20);
			handoverbillStateList.add(AirfreightConstants.STOCKSTATUS30);
			stockStatusList.add(TransportPathConstants.TRANSPORTPATH_STATUS_NOTINSTORE);
		}else{
		//否则设置库存中
			handoverbillStateList.add(stockStatus);
			stockStatusList.add(TransportPathConstants.TRANSPORTPATH_STATUS_INSTORE);
		}
		//设置合大票和单独开单两种状态
		if(StringUtil.equals(AirfreightConstants.StATUS_NULL,makeWaybillWay)){
			freightMethodList.add(AirfreightConstants.AIRFREIGHT_INDEPENDENTBILLING);
			freightMethodList.add(AirfreightConstants.AIRFREIGHT_TOTALBIGBILLNO);
		//设置合大票
		}else if(StringUtil.equals(AirfreightConstants.AIRFREIGHT_TOTALBIGBILLNO,makeWaybillWay)){
			freightMethodList.add(AirfreightConstants.AIRFREIGHT_TOTALBIGBILLNO);
		//设置单独开单
		}else{
			freightMethodList.add(AirfreightConstants.AIRFREIGHT_INDEPENDENTBILLING);
		}
        //如果为快递空运，并且选择是全部则不能根据合票类型查询
        if(StringUtil.equals(AirfreightConstants.StATUS_NULL,makeWaybillWay)&&
                AirfreightConstants.PACKAGE_AIR.equals(ticketDto.getTransportType())){
            freightMethodList = null;
        }
		//设置交接单状态
		ticketDto.setHandoverbillStateList(handoverbillStateList);
		//设置开单方式
		ticketDto.setFreightMethodList(freightMethodList);
		//设置库存状态集合
		ticketDto.setStockStatusList(stockStatusList);
        List<String> packageList = new ArrayList<String>();
        //如果为快递空运则使用
        if(AirfreightConstants.PACKAGE_AIR.equals(ticketDto.getTransportType())) {
            /*packageList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
            packageList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE);*/
        	//只拉快递库区总商务专递的货
            packageList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT);

            ticketDto.setPackageProductList(packageList);
            wbList = pointsSingleJointTicketDao.queryPackageWaybillEntity(ticketDto);
        }else {
            //如果是库存中或未入库走不同的查询条件
            wbList = pointsSingleJointTicketDao.queryWaybillEntity(ticketDto);
        }
		//处理运单解锁状态
		if(wbList!=null&&wbList.size()>0){
		    List<String> waybills=new ArrayList<String>();
		    for(int i=0;i<wbList.size();i++){
		    	waybills.add(wbList.get(i).getWaybillNo());
		    }
		    
		    List<AirLockWaybillDetailEntity> lockWaybillList=airLockWaybillService.queryLockAirWaybillList(waybills);
		    if(lockWaybillList!=null && lockWaybillList.size()>0){
		    	for(int k=0;k<wbList.size();k++){
		    		for(int i=0;i<lockWaybillList.size();i++){
		    			if(lockWaybillList.get(i).getWaybillNo().equals(wbList.get(k).getWaybillNo())){
		    				if(lockWaybillList.get(i).getLockRemark()!=null&&
		    						!"".equals(lockWaybillList.get(i).getLockRemark())){
		    					wbList.get(k).setLockRemark(lockWaybillList.get(i).getLockRemark());
		    					
		    				}
		    				wbList.get(k).setLockStatus(lockWaybillList.get(i).getLockStatus());
		    			}
		    		}
		    	}
		    }
		    /**查询当前空运总调库存件数*/
			List<AirWaybillDetailEntity> waybillStockQtyList =
					airLockWaybillService.queryWaybillStockQty(waybills, transferCode, ticketDto.getGoodsAreaCode());
			if(waybillStockQtyList!=null && waybillStockQtyList.size()>0){
				for(int i=0;i<waybillStockQtyList.size();i++){
					for(int j=0;j<wbList.size();j++){
						if(waybillStockQtyList.get(i).getWaybillNo().equals(wbList.get(j).getWaybillNo())){
							wbList.get(j).setStockQty(waybillStockQtyList.get(i).getStockQty());
						}
					}
				}
			}
		}
		
		
		LOGGER.info("获取运单明细结束.");
		return wbList;
	}
	
	public OrgAdministrativeInfoEntity getBigOrgCode(String currentOrgCode){
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoByCode(currentOrgCode, bizTypesList);
		return orgAdministrativeInfoEntity;
	}

	
	/**
	 * 根据航空正单号查询流水明细.
	 * @param waybillNo the waybill no
	 * @return 返回多条流水明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-29 下午5:00:47
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillDetailService#waybillNosQuery(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWaybillDetailDto)
	 */
	@Override
	public List<AirSerialNoDetail> queryStockAirSerialNoDetail(AirWaybillDetailDto dto) {
		LOGGER.info("查询运单流水明细,运单号:{}",dto.getWaybillNo());
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		
		dto.setDestOrgCode(orgAdministrativeInfoEntity.getCode());
		dto.setHandoverbillOrgCode(orgAdministrativeInfoEntity.getCode());
		if(StringUtil.equals(TransportPathConstants.TRANSPORTPATH_STATUS_INSTORE,dto.getStockStatus()) ||
				StringUtil.equals(AirfreightConstants.StATUS_NULL,dto.getStockStatus()) ||
					StringUtil.equals(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME,dto.getStockStatus()) ){
			if(StringUtil.equals(FossConstants.YES,orgAdministrativeInfoEntity.getAirDispatch())){
				try {
					WaybillEntity waybillEntity=null;
					if(!dto.getWaybillNo().substring(0, 1).equals("B")){
						waybillEntity= waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
					}else{
						waybillEntity=new WaybillEntity();
						waybillEntity.setProductCode(dto.getTransportType());
					}
					boolean isExp=false;
					if(waybillEntity==null){
						throw new PointsSingleJointTicketException(dto.getWaybillNo()+"  运单号不存在！");
					}
					
					/**
					 * 获取所有的快递3级产品code
					 * by wqh 
					 * 2015-09-06
					 * */
					List<String> productCodeList= productService4Dubbo.getAllLevels3ExpressProductCode();
					
					if(!CollectionUtils.isEmpty(productCodeList)
							&&productCodeList.size()>0
							&&productCodeList.contains(waybillEntity.getProductCode())){
						    isExp=true;
					}
					
					
					/*if(LoadConstants.PRODUCT_CODE_RCP.equalsIgnoreCase(waybillEntity.getProductCode())
							||LoadConstants.PRODUCT_CODE_EPEP.equalsIgnoreCase(waybillEntity.getProductCode())
							||LoadConstants.PRODUCT_CODE_PACKAGE.equalsIgnoreCase(waybillEntity.getProductCode()))
					{
						isExp=true;
					}*/
					List<GoodsAreaEntity> goodsAreaList=null ;
					String transferCode = outfieldService.queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
					//更加外场编码获取库区
					if(isExp){
						goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS);
					}else{
						
						goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
					}
					
					//根据空运总调获取外场编码
					
					GoodsAreaEntity goodsAreaEntity = null;
					if(!CollectionUtils.isEmpty(goodsAreaList)){
						goodsAreaEntity = (GoodsAreaEntity)goodsAreaList.get(0);
						//设置库区编码
						dto.setGoodsAreaCode(goodsAreaEntity.getGoodsAreaCode());
					}
					dto.setDestOrgCode(transferCode);
				} catch (BusinessException e) {
					throw new AirWayBillException(e.getErrorCode());
				}
			}
		}
		OrgAdministrativeInfoEntity currentOrgCodeEntity = getBigOrgCode(orgAdministrativeInfoEntity.getCode());
		if(currentOrgCodeEntity==null){
			throw new AirWayBillException("当前库存部门不存在!");
		}
		if(StringUtil.equals(AirfreightConstants.StATUS_NULL, dto.getStockStatus())){
			dto.setStockStatus(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME);
		}
		// 运单流水明细
		List<AirSerialNoDetail> listone = pointsSingleJointTicketDao
				.queryStockAirSerialNoDetail(dto);
		// 航空正单流水明细
		List<AirWaybillSerialNoEntity> listtwo = pointsSingleJointTicketDao
				.queryAirWaybillSerialNoEntityList(dto.getWaybillNo());
		if (listtwo == null) {
			listtwo = new ArrayList<AirWaybillSerialNoEntity>();
		}
		List<AirSerialNoDetail> resultList = new ArrayList<AirSerialNoDetail>();
		Map<String, AirWaybillSerialNoEntity> map = convertMap(listtwo);
		for (AirSerialNoDetail airSerialNoDetail : listone) {
			if (map.containsKey(airSerialNoDetail.getSerialNo()))
				continue;
			resultList.add(airSerialNoDetail);
		}
		return resultList;
	}
	
	/**
	 * 查询正单号是否存在
	 * @param airWaybillNo 正单号
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-23 上午11:13:42
	 */
	private boolean airWaybillNoExists(String airWaybillNo){
		AirWaybillEntity airWaybillEntity = pointsSingleJointTicketDao.queryWidthPrintAirWaybill(airWaybillNo);
		return airWaybillEntity!=null?true:false;
	}

	/**
	 * 保存航空正单.
	 * @param billEntity the bill entity
	 * @param waybillList the waybill list
	 * @param delParameterMap the delParameterMap
	 * @param parameter the parameter
	 * @return 成功或失败
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:52:46
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public synchronized AirWaybillDetailDto addAirWaybillEntity(AirWaybillEntity billEntity,
			List<AirWaybillDetailEntity> waybillList, Map<String, List> delParameterMap, Map<String, List> parameter) {
		if(airWaybillNoExists(billEntity.getAirWaybillNo())){
			throw new PointsSingleJointTicketException(AirfreightConstants.AIRFREIGHT_NOTAIRWAYBILLNO);
         //添加校验运输性质不能为空
		}else if (billEntity.getTransportType() == null ||
                  "".equals(billEntity.getTransportType())){
            billEntity.setTransportType(AirfreightConstants.PRECISION_AIR);
//            throw new PointsSingleJointTicketException("单号："+billEntity.getAirWaybillNo()+",的运输性质为不能为空");
        }
	      //BUG-57777 录入航空正单界面，分单合票毛重输入提示错误，但是提交合票后显示的是错误值 
			//105795 2013-10-22
			if(!CollectionUtils.isEmpty(waybillList)){
				//快递单个数
				boolean isPackage = false;
				boolean isAir =false;
				for(int i=0;i<waybillList.size();i++){
					//判断空运和快递是否同时存在
					if(AirfreightConstants.PACKAGE_AIR.equals(waybillList.get(i).getTransportType())){
						isPackage = true;
					}else if(AirfreightConstants.PRECISION_AIR.equals(waybillList.get(i).getTransportType())){
						isAir = true;
					}
			        
					if(isPackage&&isAir){
						throw new PointsSingleJointTicketException("快递空运和精准空运不能录入一个正单中！");
					}	
				
					//毛重小于0
					if(waybillList.get(i).getGrossWeight()!=null
							&&waybillList.get(i).getGrossWeight().compareTo(BigDecimal.ZERO)<0){
						throw new PointsSingleJointTicketException("运单："+waybillList.get(i).getWaybillNo()+" 毛重不正确");
					}
					//体积小于0
					if(waybillList.get(i).getVolume()!=null
							&&waybillList.get(i).getVolume().compareTo(BigDecimal.ZERO)<0){
						throw new PointsSingleJointTicketException("运单："+waybillList.get(i).getWaybillNo()+" 体积不正确");
					}
					
				}
			}
			
		// 校验运单明细是否为空 
		//2013-07-10 ISSUE-3269 允许不包含运单的航空正单保存，并且不包含运单的航空正单中重量体积和件数允许为0
//		if (CollectionUtils.isEmpty(waybillList)) {
//			throw new PointsSingleJointTicketException(
//					AirfreightConstants.AIRFREIGHT_NOTAIRWAYBILLINFO);
//		}
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		billEntity.setCreateOrgCode(orgAdministrativeInfoEntity.getCode());
		AirWaybillEntity newBillEntity = getWaybillEntity(billEntity);
		//支持商品代号为空情况
		if(newBillEntity.getItemCode()==null||"".equals(newBillEntity.getItemCode())){
			newBillEntity.setItemCode(AirfreightConstants.AIR_WAYBILL_ITEM_CODE);
		}
		// 保存航空正单信息
		pointsSingleJointTicketDao.addAirWaybillEntity(newBillEntity);
		LOGGER.info("完成航空正单新增.");
		if(waybillList.size()>0){
			List<String> cubcWaybills = new ArrayList<String>();
			// 航空正单明细list
			List<AirWaybillDetailEntity> airWaybillDetailList = new ArrayList<AirWaybillDetailEntity>();
			// 航空正单流水明细
			List<AirWaybillSerialNoEntity> airSerialNoDetailSerialno = new ArrayList<AirWaybillSerialNoEntity>();
			for (int i = 0; i < waybillList.size(); i++) {
				AirWaybillDetailEntity airWaybillDetailEntity = waybillList.get(i);
				String waybillNo = airWaybillDetailEntity.getWaybillNo();
				cubcWaybills.add(waybillNo);
				String stockStatus = airWaybillDetailEntity.getStockStatus();
				String airWaybillDetailId = UUIDUtils.getUUID();
				// 根据运单号取页面上不需要保存的流水明细
				List<String> delSerialNoList = delParameterMap.get(waybillNo);
				// 根据运单号取页面上显示的全部流水明细
				List<String> viewSerialNoList = parameter.get(waybillNo);
				//oldList 当前运单已做过航空正单的流水号
				List<AirWaybillSerialNoEntity> oldList = filterWaybillSerialNoList(waybillNo);
				
				//选择页面选择不保存的流水号
				Map<String, String> delSerialNoMap = new HashMap<String, String>();
				if(delSerialNoList != null){
					for (String str : delSerialNoList) {
						delSerialNoMap.put(str, str);
					}
				}
				//改运单在数据库中已保存的流水号
				Map<String, String> oldSerialNoMap = new HashMap<String, String>();
				if(oldList != null){
					for (AirWaybillSerialNoEntity entity : oldList) {
						oldSerialNoMap.put(entity.getSerialNo(), entity.getSerialNo());
					}
				}
				//改运单在数据库中已保存的流水号
				Map<String, String> viewSerialNoMap = new HashMap<String, String>();
				if(viewSerialNoList != null){
					for (String str : viewSerialNoList) {
						viewSerialNoMap.put(str, str);
					}
				}
				//判断是否是快递空运
//				if(!AirfreightConstants.PACKAGE_AIR.equals(waybillList.get(i).getTransportType())){
					// 获取运单流水明细
					List<AirSerialNoDetail> airSerialNoList = getAirSerialNoDetailList(waybillNo,stockStatus);

					// 将不保存的运单流水逐出
					for (AirSerialNoDetail airSerialNoDetail : airSerialNoList) {
						if (delSerialNoMap.containsKey(airSerialNoDetail.getSerialNo())) {
							continue;
						}
						//改运单的流水号已经做过正单，则提示错误信息返回
						if (oldSerialNoMap.containsKey(airSerialNoDetail.getSerialNo())
								&& viewSerialNoMap.containsKey(airSerialNoDetail.getSerialNo())) {
							LOGGER.info("运单号"+waybillNo+"中的流水号"+airSerialNoDetail.getSerialNo()+"已经做过航空正单！");
							throw new PointsSingleJointTicketException("运单号"+waybillNo+"中的流水号"+airSerialNoDetail.getSerialNo()+"已经做过航空正单！");
						}
						AirWaybillSerialNoEntity airWaybillSerialNoEntity = new AirWaybillSerialNoEntity();
						airWaybillSerialNoEntity.setId(UUIDUtils.getUUID());
						airWaybillSerialNoEntity.setCreateTime(new Date());
						airWaybillSerialNoEntity.setSerialNo(airSerialNoDetail.getSerialNo());
						airWaybillSerialNoEntity.setStockStatus(airSerialNoDetail.getStockStatus());
						airWaybillSerialNoEntity.setWaybillNo(waybillNo);
						airWaybillSerialNoEntity.setAirWaybillDetailId(airWaybillDetailId);
						airSerialNoDetailSerialno.add(airWaybillSerialNoEntity);
					}
//				}
				// 设置航空正单明细主键ID
				airWaybillDetailEntity.setAirWaybillId(newBillEntity.getId());
				// 设置航空正单明细组建id
				airWaybillDetailEntity.setId(airWaybillDetailId);
				airWaybillDetailEntity.setCreateTime(new Date());
				//开单订舱
				airWaybillDetailEntity.setBeBooking(FossConstants.ACTIVE);
				//已配载
				airWaybillDetailEntity.setIsLoading(FossConstants.ACTIVE);
				// 根据运单号获取航空运单流水明细
				airWaybillDetailList.add(airWaybillDetailEntity);
				// 根据运单号查询对应流水号明细
			}
			LOGGER.info("新增航空正单明细.");
			// 保存航空正单明细
			addAirWaybillDetailEntity(airWaybillDetailList);
			LOGGER.info("新增航空正单流水明细.");
            if(!CollectionUtils.isEmpty(airSerialNoDetailSerialno)){
			// 保存航空正单流行明细
			addAirWaybillSerialNoEntity(airSerialNoDetailSerialno);
            }
            //判断是否是快递空运
            if(AirfreightConstants.PACKAGE_AIR.equals(waybillList.get(0).getTransportType())){
            	//拿第一个运单的产品类型传给结算()
            	billEntity.setProductCode(waybillList.get(0).getProductCode());
            }
            
            
			//// cubcgray 335284-316759
			GrayParameterDto dto = new GrayParameterDto();
			dto.setSourceBillNos(new String[] { billEntity.getAirLineTwoletter() });
			dto.setSourceBillType(CUBCGrayUtil.SBType.HK.getName());
			VestResponse response = cubcUtil.getCubcGrayDataByCustomer(dto, new Throwable());
			if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(response.getVestBatchResult().get(0).getVestSystemCode())) {
				LOGGER.info("cubcgray ==STL");
				// 调用结算接口
				try {
					UserEntity user = FossUserContext.getCurrentInfo().getUser();
					airStowageService.addAirStowage(billEntity, new CurrentInfo(user, orgAdministrativeInfoEntity));
				} catch (BusinessException e) {
					LOGGER.info("调用结算接口出错:{}", e.getErrorCode());
					throw new PointsSingleJointTicketException(e.getErrorCode());
				}
			} else {
				/*
				 * 同步航空证单信息至CUBC
				 * @author 316759-foss-RuipengWang
				 * @date 2016-10-19 18:34:32 PM
				 */
				try {
					/*
					 * 给cubc推送同时要加上明细运单
					 */
					billEntity.setDetailWaybills(cubcWaybills);
					
					String requestStr = JSONObject.toJSONString(billEntity);
					LOGGER.info("推送给CUBC的参数 requestStr = " + requestStr);
					airWaybillToCubcService.pushAddAirWaybill(requestStr);
				} catch (BusinessException e) {
					// 推送失败
					LOGGER.error("推送航空证单信息至CUBC失败" + e.getMessage());
					throw new PointsSingleJointTicketException("cubc - " + e.getErrorCode());
				}
			}
			//// end cubcgray 335284-316759
		}
		//AiyWayBillId不为空,标明已保存
		AirWaybillDetailDto dto = new AirWaybillDetailDto();
		dto.setAiyWayBillId(newBillEntity.getId());
        /**
         * 同步航空正单信息至OPP
         * 1.新增航空正单 保存航空正单号以及航空正单id 保存数据至临时表
         */
		//2016-05-23 合票清单修改 数据同步至OPP
		//@author 269701
		//合票清单信息转换成JOB临时表的数据
        //如果配载类型是 单独开单外发和合大票外发 数据同步OPP系统
       /* if(StringUtils.equals("DDWFD", newBillEntity.getAirAssembleType()) ||
        		StringUtils.equals("HDPWF", newBillEntity.getAirAssembleType())){*/
    		AirWaybillTempEntity temEntity=new AirWaybillTempEntity();
    		//航空正单主表id
    		temEntity.setAirWaybillId(newBillEntity.getId());
    		//正单号
    		temEntity.setAirWaybillNo(newBillEntity.getAirWaybillNo());
    		//推送中
    		temEntity.setPushStatus(FossConstants.NO);
    		//清单：20 正单 10
    		temEntity.setBillType("10");
    		//新增数据
    		temEntity.setOperateStatus("INSERT");
    		LOGGER.info("FOSS新增航空正单信息，数据暂存临时表 等待job推送；正单号 {"+newBillEntity.getAirWaybillNo()+"} 主表信息 开始...."+new Date());
    		pushAirPickUpInfoService.addAirPickToTemp(temEntity);
    		LOGGER.info("FOSS新增航空正单信息，数据暂存临时表 等待job推送；正单号 {"+newBillEntity.getAirWaybillNo()+"} 主表信息 结束...."+new Date());
        //}
		return dto;
	}
	
	private AirWaybillEntity getWaybillEntity(AirWaybillEntity billEntity){
		//获取始发站编码
		String cityCode= FossUserContext.getCurrentDept().getCityCode();
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//设置币种
		billEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		//设置托运人编码
		billEntity.setShipperCode(AirfreightConstants.AIRFREIGHT_SHIPPERCODE);
		//设置收货人编码
		billEntity.setReceiverCode(AirfreightConstants.AIRFREIGHT_VIEW_FLAG_NO);
		//设置制单人编码
		billEntity.setCreateUserCode(currentInfo.getEmpCode());
		//设置默认修改日期
		billEntity.setModifyDate(new Date());
		//设置默认修改时间
		billEntity.setModifyTime(new Date());
		//设置城市编码
		billEntity.setDeptRegionCode(cityCode);
		//设置航空正单主键ID
		billEntity.setId( UUIDUtils.getUUID());
		//设置员工编码
		billEntity.setModifyUserCode(currentInfo.getEmpCode());
		//设置员工code
		billEntity.setModifyUserName(currentInfo.getEmpName());
		//设置交接状态为未交接
		billEntity.setHandoverState(AirfreightConstants.AIRFREIGHT_NOTHANDOVER);
		//设置航空正单根据状态为待跟中
		billEntity.setTrackState(AirfreightConstants.UN_TRACK);
		//设置航空正单为未付款
		billEntity.setIsNotPayment(AirfreightConstants.AIRFREIGHT_NOTPAYMENT);
		//设置制单时间
		billEntity.setCreateTime(new Date());
		return billEntity;
	}
	
	//获取流水明细
	private List<AirSerialNoDetail> getAirSerialNoDetailList(String waybillNo,String stockStatus){ //根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		
		AirWaybillDetailDto airWaybillDetailDto = new AirWaybillDetailDto();
		//此处设置当前登录人编码为查询交接中的运单流水条件
		airWaybillDetailDto.setHandoverbillOrgCode(orgAdministrativeInfoEntity.getCode());
		//设置运单号
		airWaybillDetailDto.setWaybillNo(waybillNo);
		//如果查询的是库存中或者(全部)并且当前登录部门是空运总调部门
		if(StringUtil.equals(TransportPathConstants.TRANSPORTPATH_STATUS_INSTORE,stockStatus) ||
				StringUtil.equals(AirfreightConstants.StATUS_NULL,stockStatus) ||
				StringUtil.equals(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME,stockStatus)){
			if(StringUtil.equals(FossConstants.YES,orgAdministrativeInfoEntity.getAirDispatch())){
				//根据当前登录人部门编码获取外场编码
				String transferCode = outfieldService.queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
				
				boolean bFlag = waybillNo.startsWith("B");
				//根据外场编码获取库区编码
				List<GoodsAreaEntity> goodsAreaList = null;
				if(bFlag){
					goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS);
				}else{
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillNo(waybillNo);
					if(waybillEntity == null){
						throw new PointsSingleJointTicketException(waybillNo+" 运单信息不存在！");
					}
					
					if(productService4Dubbo.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), new Date())){
						goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS);
					}else{
						goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
					}
					
					GoodsAreaEntity goodsAreaEntity = null;
					//当goodsAreaList不为空取出第一条记录中的库区编码
					if(CollectionUtils.isNotEmpty(goodsAreaList)){
						goodsAreaEntity = (GoodsAreaEntity)goodsAreaList.get(0);
						//设置库区编码
						airWaybillDetailDto.setGoodsAreaCode(goodsAreaEntity.getGoodsAreaCode());
					}
					//此处外场编码的设置是为了查询库存中的运单流水明细
					airWaybillDetailDto.setDestOrgCode(transferCode);
				}
				
			/*try {
					//获取运单信息
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillNo(waybillNo);
					if(waybillEntity != null){
						*//**
						 * 获取所有的快递3级产品code
						 * by wqh 
						 * 2015-09-06
						 * *//*
						List<String> productCodeList= productService.getAllLevels3ExpressProductCode();
						if(!CollectionUtils.isEmpty(productCodeList)
								&&productCodeList.size()>0
								&&productCodeList.contains(waybillEntity.getProductCode())){
							goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS);

						}
						if(LoadConstants.PRODUCT_CODE_RCP.equals(waybillEntity.getProductCode())
								||LoadConstants.PRODUCT_CODE_EPEP.equals(waybillEntity.getProductCode())
								||LoadConstants.PRODUCT_CODE_PACKAGE.equals(waybillEntity.getProductCode())){
							goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS);
						}else{
							goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
						}
						
					}else{
						throw new PointsSingleJointTicketException(waybillNo+" 运单信息不存在！");
					}
				
					GoodsAreaEntity goodsAreaEntity = null;
					//当goodsAreaList不为空取出第一条记录中的库区编码
					if(!CollectionUtils.isEmpty(goodsAreaList)){
						goodsAreaEntity = (GoodsAreaEntity)goodsAreaList.get(0);
						//设置库区编码
						airWaybillDetailDto.setGoodsAreaCode(goodsAreaEntity.getGoodsAreaCode());
					}
					//此处外场编码的设置是为了查询库存中的运单流水明细
					airWaybillDetailDto.setDestOrgCode(transferCode);
				} catch (BusinessException e) {
					throw new AirWayBillException(e.getErrorCode());
				}*/
			}
		}
		OrgAdministrativeInfoEntity currentOrgCodeEntity = getBigOrgCode(orgAdministrativeInfoEntity.getCode());
		//如未根据当前登录人所在部门编码为找到库存则提示异常
		if(currentOrgCodeEntity==null){
			throw new AirWayBillException("当前库存部门不存在!");
		}
		airWaybillDetailDto.setStockStatus(stockStatus);
		//当前台传过的stockstatus状态未空则默认设置查询全部
		if(StringUtils.equals(AirfreightConstants.StATUS_NULL, stockStatus)){
			airWaybillDetailDto.setStockStatus(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME);
		}
		return pointsSingleJointTicketDao.queryStockAirSerialNoDetail(airWaybillDetailDto);
	}

	/**
	 * 保存航空正单明细.
	 * @param airWaybillDetailList the air waybill detail list
	 * @return the int
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:53:35
	 */
	@Override
	@Transactional
	public int addAirWaybillDetailEntity(
			List<AirWaybillDetailEntity> airWaybillDetailList) {
		return pointsSingleJointTicketDao
				.addAirWaybillDetailEntity(airWaybillDetailList);
	}

	/**
	 * 新增航空正单流水明细.
	 * @param airSerialNoDetailSerialno the air serial no detail serialno
	 * @return the int
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:57:57
	 */
	@Override
	@Transactional
	public int addAirWaybillSerialNoEntity(
			List<AirWaybillSerialNoEntity> airSerialNoDetailSerialno) {
		return pointsSingleJointTicketDao
				.addAirWaybillSerialNoEntity(airSerialNoDetailSerialno);
	}
	
	public AirWaybillDetailDto queryWithModifySerailNo(AirWaybillDetailDto ticketDto){
		String waybillNo = ticketDto.getWaybillNo();
		String airWaybillDetailId = ticketDto.getAirWaybillDetailId();
		
		AirWaybillDetailDto waybillDetailDto = new AirWaybillDetailDto();
		   List<AirSerialNoDetail> waybillSerialNo = new ArrayList<AirSerialNoDetail>();
		   try {
			    // 运单流水明细
			   waybillSerialNo = pointsSingleJointTicketDao
						.queryStockAirSerialNoDetail(resultDto(ticketDto));
			} catch (BusinessException e) {
				throw new AirWayBillException(e.getErrorCode());
			}
			// 航空正单流水明细
			List<AirWaybillSerialNoEntity> airwayBillSerialNo = pointsSingleJointTicketDao
					.queryAirWaybillSerialNoEntityList(waybillNo);	
			if (airwayBillSerialNo == null) {
				airwayBillSerialNo = new ArrayList<AirWaybillSerialNoEntity>();
			}
			//需要添加的流水明细list
			List<AirSerialNoDetail> addList = new ArrayList<AirSerialNoDetail>();
			//需要删除的流水明细list
			List<AirSerialNoDetail> delList = new ArrayList<AirSerialNoDetail>();
			//比较流水号是否为空
			if(!CollectionUtils.isEmpty(waybillSerialNo) && !CollectionUtils.isEmpty(airwayBillSerialNo)
					 && waybillSerialNo.size() == airwayBillSerialNo.size()){
				//根据id合运单号查询流水号记录
				List<AirSerialNoDetail> oldList = pointsSingleJointTicketDao.queryAirWaybillDetailIdList(waybillNo,airWaybillDetailId);
				for(AirSerialNoDetail entity : oldList){
					entity.setFlag(AirfreightConstants.AIRFREIGHT_FLAG_DEL);
					entity.setOperateFlag(AirfreightConstants.AIRFREIGHT_ADD_FLAG_YES);
					entity.setViewFlag(AirfreightConstants.AIRFREIGHT_VIEW_FLAG_NO);
					delList.add(entity);
					waybillDetailDto.setDelAirSerialNoDetailList(delList);
				}
			}else{
				Map<String, AirWaybillSerialNoEntity> map = convertMap(airwayBillSerialNo);
				List<AirSerialNoDetail> queryAirSerialNoList = queryAirSerialNoList(waybillNo,airWaybillDetailId);
				for (AirSerialNoDetail airSerialNoDetail : waybillSerialNo) {
					if (map.containsKey(airSerialNoDetail.getSerialNo()))
						continue;
					    //航空正单流水明细中不存在的流水明细
						if(!CollectionUtils.isEmpty(queryAirSerialNoList)){
							airSerialNoDetail.setOperateFlag(AirfreightConstants.AIRFREIGHT_ADD_FLAG_NO);
							airSerialNoDetail.setViewFlag(AirfreightConstants.AIRFREIGHT_VIEW_FLAG_YES);
							airSerialNoDetail.setFlag(AirfreightConstants.AIRFREIGHT_FLAG_ADD);
						}else{
							airSerialNoDetail.setOperateFlag(AirfreightConstants.AIRFREIGHT_ADD_FLAG_YES);
							airSerialNoDetail.setViewFlag(AirfreightConstants.AIRFREIGHT_VIEW_FLAG_NO);
							airSerialNoDetail.setFlag(AirfreightConstants.AIRFREIGHT_FLAG_DEL);
						}
						addList.add(airSerialNoDetail);
						waybillDetailDto.setAddAirSerialNoDetailList(addList);
				}
				//已存在航空正单明细表中的流水号
				for(AirSerialNoDetail entity :queryAirSerialNoList){
					entity.setFlag(AirfreightConstants.AIRFREIGHT_FLAG_DEL);
					entity.setOperateFlag(AirfreightConstants.AIRFREIGHT_ADD_FLAG_YES);
					entity.setViewFlag(AirfreightConstants.AIRFREIGHT_VIEW_FLAG_NO);
					delList.add(entity);
					waybillDetailDto.setDelAirSerialNoDetailList(delList);
				}
			}
			
			List<AirSerialNoDetail> viewList = new ArrayList<AirSerialNoDetail>();
			if(!CollectionUtils.isEmpty(waybillDetailDto.getAddAirSerialNoDetailList())){
				for (int i = 0; i < waybillDetailDto.getAddAirSerialNoDetailList().size(); i++) {
					AirSerialNoDetail addSerialNoDetail = new AirSerialNoDetail();
					addSerialNoDetail = waybillDetailDto.getAddAirSerialNoDetailList().get(i);
					viewList.add(addSerialNoDetail);
				}
			}
			if(!CollectionUtils.isEmpty(waybillDetailDto.getDelAirSerialNoDetailList())){
				for (int j = 0; j < waybillDetailDto.getDelAirSerialNoDetailList().size(); j++) {
					AirSerialNoDetail delSerialNoDetail = new AirSerialNoDetail();
					delSerialNoDetail = waybillDetailDto.getDelAirSerialNoDetailList().get(j);
					viewList.add(delSerialNoDetail);
				}
			}
			waybillDetailDto.setAirSerialNoViewDetailList(viewList);
		return waybillDetailDto;
	}
	

	private AirWaybillDetailDto resultDto(AirWaybillDetailDto dto) {
		// 根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService
				.queryAirDispatchDept(deptCode);

		dto.setHandoverbillOrgCode(orgAdministrativeInfoEntity.getCode());
		if (StringUtil.equals(
				TransportPathConstants.TRANSPORTPATH_STATUS_INSTORE,
				dto.getStockStatus())
				|| StringUtil.equals(AirfreightConstants.StATUS_NULL,
						dto.getStockStatus())
				|| StringUtil.equals(null, dto.getStockStatus())) {
			if (StringUtil.equals(FossConstants.YES,
					orgAdministrativeInfoEntity.getAirDispatch())) {
				try {
					/******************* 335284 对接灰度空运逻辑改造 ***************************/
					WaybillEntity waybillEntity=null;
					if(!dto.getWaybillNo().substring(0, 1).equals("B")){
						waybillEntity= waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
					}else{
						waybillEntity=new WaybillEntity();
						waybillEntity.setProductCode(dto.getTransportType());
					}
					boolean isExp=false;
					if(waybillEntity==null){
						throw new PointsSingleJointTicketException(dto.getWaybillNo()+"  运单号不存在！");
					}
					List<String> productCodeList= productService4Dubbo.getAllLevels3ExpressProductCode();
					if(!CollectionUtils.isEmpty(productCodeList) && productCodeList.contains(waybillEntity.getProductCode()))
						isExp=true;
					
					List<GoodsAreaEntity> goodsAreaList=null ;
					String transferCode = outfieldService.queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
					//更加外场编码获取库区
					if(isExp){
						goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS);
					}else{
						goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
					}

					GoodsAreaEntity goodsAreaEntity = null;
					if (!CollectionUtils.isEmpty(goodsAreaList)) {
						goodsAreaEntity = (GoodsAreaEntity) goodsAreaList
								.get(0);
						// 设置库区编码
						dto.setGoodsAreaCode(goodsAreaEntity.getGoodsAreaCode());
					}
					dto.setDestOrgCode(transferCode);
				} catch (BusinessException e) {
					throw new AirWayBillException(e.getErrorCode());
				}
			}
		}
		OrgAdministrativeInfoEntity currentOrgCodeEntity = getBigOrgCode(orgAdministrativeInfoEntity.getCode());
		if (currentOrgCodeEntity == null) {
			throw new AirWayBillException("当前库存部门不存在!");
		}
		dto.setStockStatus(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME);
		return dto;
	}

	/**
	 * 查询待修改的正单流水明细.
	 * @param waybillNo the waybill no
	 * @param airWaybillDetailId the air waybill detail id
	 * @return the air waybill detail dto
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-6 上午9:36:42
	 * @see AirWaybillDetailService#queryWithModifySerailNo(AirWaybillDetailDto)
	 */
	@Deprecated
	public AirWaybillDetailDto queryWithModifySerailNo(String waybillNo,String airWaybillDetailId) {
		   AirWaybillDetailDto waybillDetailDto = new AirWaybillDetailDto();
		   List<AirSerialNoDetail> waybillSerialNo = new ArrayList<AirSerialNoDetail>();
		   try {
			    // 运单流水明细
			   waybillSerialNo = pointsSingleJointTicketDao
						.queryStockAirSerialNoDetail(resultDto(waybillNo));
			} catch (BusinessException e) {
				throw new AirWayBillException(e.getErrorCode());
			}
			// 航空正单流水明细
			List<AirWaybillSerialNoEntity> airwayBillSerialNo = pointsSingleJointTicketDao
					.queryAirWaybillSerialNoEntityList(waybillNo);	
			if (airwayBillSerialNo == null) {
				airwayBillSerialNo = new ArrayList<AirWaybillSerialNoEntity>();
			}
			//需要添加的流水明细list
			List<AirSerialNoDetail> addList = new ArrayList<AirSerialNoDetail>();
			//需要删除的流水明细list
			List<AirSerialNoDetail> delList = new ArrayList<AirSerialNoDetail>();
			//比较流水号是否为空
			if(!CollectionUtils.isEmpty(waybillSerialNo) && !CollectionUtils.isEmpty(airwayBillSerialNo)
					 && waybillSerialNo.size() == airwayBillSerialNo.size()){
				//根据id合运单号查询流水号记录
				List<AirSerialNoDetail> oldList = pointsSingleJointTicketDao.queryAirWaybillDetailIdList(waybillNo,airWaybillDetailId);
				for(AirSerialNoDetail entity : oldList){
					entity.setFlag(AirfreightConstants.AIRFREIGHT_FLAG_DEL);
					entity.setOperateFlag(AirfreightConstants.AIRFREIGHT_ADD_FLAG_YES);
					entity.setViewFlag(AirfreightConstants.AIRFREIGHT_VIEW_FLAG_NO);
					delList.add(entity);
					waybillDetailDto.setDelAirSerialNoDetailList(delList);
				}
			}else{
				Map<String, AirWaybillSerialNoEntity> map = convertMap(airwayBillSerialNo);
				List<AirSerialNoDetail> queryAirSerialNoList = queryAirSerialNoList(waybillNo,airWaybillDetailId);
				for (AirSerialNoDetail airSerialNoDetail : waybillSerialNo) {
					if (map.containsKey(airSerialNoDetail.getSerialNo()))
						continue;
					    //航空正单流水明细中不存在的流水明细
						if(!CollectionUtils.isEmpty(queryAirSerialNoList)){
							airSerialNoDetail.setOperateFlag(AirfreightConstants.AIRFREIGHT_ADD_FLAG_NO);
							airSerialNoDetail.setViewFlag(AirfreightConstants.AIRFREIGHT_VIEW_FLAG_YES);
							airSerialNoDetail.setFlag(AirfreightConstants.AIRFREIGHT_FLAG_ADD);
						}else{
							airSerialNoDetail.setOperateFlag(AirfreightConstants.AIRFREIGHT_ADD_FLAG_YES);
							airSerialNoDetail.setViewFlag(AirfreightConstants.AIRFREIGHT_VIEW_FLAG_NO);
							airSerialNoDetail.setFlag(AirfreightConstants.AIRFREIGHT_FLAG_DEL);
						}
						addList.add(airSerialNoDetail);
						waybillDetailDto.setAddAirSerialNoDetailList(addList);
				}
				//已存在航空正单明细表中的流水号
				for(AirSerialNoDetail entity :queryAirSerialNoList){
					entity.setFlag(AirfreightConstants.AIRFREIGHT_FLAG_DEL);
					entity.setOperateFlag(AirfreightConstants.AIRFREIGHT_ADD_FLAG_YES);
					entity.setViewFlag(AirfreightConstants.AIRFREIGHT_VIEW_FLAG_NO);
					delList.add(entity);
					waybillDetailDto.setDelAirSerialNoDetailList(delList);
				}
			}
			
			List<AirSerialNoDetail> viewList = new ArrayList<AirSerialNoDetail>();
			if(!CollectionUtils.isEmpty(waybillDetailDto.getAddAirSerialNoDetailList())){
				for (int i = 0; i < waybillDetailDto.getAddAirSerialNoDetailList().size(); i++) {
					AirSerialNoDetail addSerialNoDetail = new AirSerialNoDetail();
					addSerialNoDetail = waybillDetailDto.getAddAirSerialNoDetailList().get(i);
					viewList.add(addSerialNoDetail);
				}
			}
			if(!CollectionUtils.isEmpty(waybillDetailDto.getDelAirSerialNoDetailList())){
				for (int j = 0; j < waybillDetailDto.getDelAirSerialNoDetailList().size(); j++) {
					AirSerialNoDetail delSerialNoDetail = new AirSerialNoDetail();
					delSerialNoDetail = waybillDetailDto.getDelAirSerialNoDetailList().get(j);
					viewList.add(delSerialNoDetail);
				}
			}
			waybillDetailDto.setAirSerialNoViewDetailList(viewList);
		return waybillDetailDto;
	}
	
	private AirWaybillDetailDto resultDto(String waybillNo){
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		
		AirWaybillDetailDto dto = new AirWaybillDetailDto();
		dto.setHandoverbillOrgCode(orgAdministrativeInfoEntity.getCode());
		if(StringUtil.equals(TransportPathConstants.TRANSPORTPATH_STATUS_INSTORE,dto.getStockStatus()) ||
				StringUtil.equals(AirfreightConstants.StATUS_NULL,dto.getStockStatus()) ||
					StringUtil.equals(null,dto.getStockStatus())){
			if(StringUtil.equals(FossConstants.YES,orgAdministrativeInfoEntity.getAirDispatch())){
				try {
					String transferCode = outfieldService.queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
					//更加外场编码获取库区
					List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
					//根据空运总调获取外场编码
					
					GoodsAreaEntity goodsAreaEntity = null;
					if(!CollectionUtils.isEmpty(goodsAreaList)){
						goodsAreaEntity = (GoodsAreaEntity)goodsAreaList.get(0);
						//设置库区编码
						dto.setGoodsAreaCode(goodsAreaEntity.getGoodsAreaCode());
					}
					dto.setDestOrgCode(transferCode);
				} catch (BusinessException e) {
					throw new AirWayBillException(e.getErrorCode());
				}
			}
		}
		OrgAdministrativeInfoEntity currentOrgCodeEntity = getBigOrgCode(orgAdministrativeInfoEntity.getCode());
		if(currentOrgCodeEntity==null){
			throw new AirWayBillException("当前库存部门不存在!");
		}
		dto.setStockStatus(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME);
		dto.setWaybillNo(waybillNo);
		return dto;
	}
	/**
	 * Convert map.
	 * @param listtwo the listtwo
	 * @return the map
	 */
	private Map<String, AirWaybillSerialNoEntity> convertMap(
			List<AirWaybillSerialNoEntity> listtwo) {
		Map<String, AirWaybillSerialNoEntity> map = new HashMap<String, AirWaybillSerialNoEntity>();
		for (AirWaybillSerialNoEntity airWaybillSerialNoEntity : listtwo) {
			map.put(airWaybillSerialNoEntity.getSerialNo(),
					airWaybillSerialNoEntity);
		}
		return map;
	}
	
	/**
	 * 过滤不不属于当前正单下的流水号.
	 * @param waybillNo the waybill no
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-7 上午11:42:30
	 */
	private List<AirWaybillSerialNoEntity> filterWaybillSerialNoList(String waybillNo){
		 //运单总条数
		List<AirWaybillSerialNoEntity>  list = pointsSingleJointTicketDao.queryAirWaybillSerialNoEntityList(waybillNo);
		return list;
	}

	/**
	 * 根据运单号合id查询流水号条数.
	 * @param waybillNo the waybill no
	 * @param airWaybillDetailId the air waybill detail id
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-7 下午2:14:09
	 */
	private List<AirSerialNoDetail> queryAirSerialNoList(String waybillNo, String airWaybillDetailId){
		return pointsSingleJointTicketDao.queryAirWaybillDetailIdList(waybillNo,airWaybillDetailId);
	}
	
	/**
	 * 生成合票号.
	 * @return 返回合票号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-31 下午4:13:26
	 */
	@Override
	public String getGenerationJointTicketNo() {
		return tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.HP);
	}

	/**
	 * 批量打印运单明细.
	 * @param ids the ids
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-7 上午9:52:35
	 */
	@Override
	public List<AirWaybillDetailEntity> queryAirWaybillBatchPrint(String[] ids) {
		return pointsSingleJointTicketDao.queryAirWaybillBatchPrint(ids);
	}

	/**
	 * 修改航空正单、明细、流水.
	 * @param newbillEntity the newbill entity
	 * @param airWaybillDetailEntity the air waybill detail entity
	 * @param addParameterMap the add parameter map
	 * @param delParameterMap the del parameter map
	 * @return the air waybill detail dto
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-27 下午6:44:19
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillDetailService#modifyAirWaybill(com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity,
	 * java.util.List)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public AirWaybillDetailDto modifyAirWaybill(AirWaybillEntity newbillEntity,
			List<AirWaybillDetailEntity> airWaybillDetailEntity,
			Map<String,List> addParameterMap,Map<String,List> delParameterMap) {
         //BUG-57777 录入航空正单界面，分单合票毛重输入提示错误，但是提交合票后显示的是错误值 
		//105795 2013-10-22
		if(!CollectionUtils.isEmpty(airWaybillDetailEntity)){
			for(int i=0;i<airWaybillDetailEntity.size();i++){
				//毛重小于0
				if(airWaybillDetailEntity.get(i).getGrossWeight()!=null
						&&airWaybillDetailEntity.get(i).getGrossWeight().compareTo(BigDecimal.ZERO)<0){
					throw new PointsSingleJointTicketException("运单："+airWaybillDetailEntity.get(i).getWaybillNo()+" 毛重不正确");
				}
				//体积小于0
				if(airWaybillDetailEntity.get(i).getVolume()!=null
						&&airWaybillDetailEntity.get(i).getVolume().compareTo(BigDecimal.ZERO)<0){
					throw new PointsSingleJointTicketException("运单："+airWaybillDetailEntity.get(i).getWaybillNo()+" 体积不正确");
				}
				
			}
		}
		//支持商品代号可编辑
		if(newbillEntity.getItemCode()==null||"".equals(newbillEntity.getItemCode())){
			newbillEntity.setItemCode(AirfreightConstants.AIR_WAYBILL_ITEM_CODE);
		}
		//获得之前的航空正单
		AirWaybillEntity oldbillEntity = pointsSingleJointTicketDao.queryAirWaybillEntity(newbillEntity.getId());
		//设置修改时间
		newbillEntity.setModifyTime(new Date());
		// 默认为为付款
		newbillEntity.setIsNotPayment(AirfreightConstants.AIRFREIGHT_PAYMENT);
		LOGGER.info("更新航空正单,正单号为:{}",newbillEntity.getAirWaybillNo());
		// 更新航空正单
		pointsSingleJointTicketDao.updateAirWaybillEntity(newbillEntity);
		
		//记录更新信息
		LOGGER.info("正单号为:{}已更新完毕.",newbillEntity.getAirWaybillNo());
		
		// 比较录入航空正单时提交的明细与当前操作的航空正单明细的叉积找出
		//新的航空正单明细
		Map<String, AirWaybillDetailEntity> newAirWaybillDetailEntityMap =
				new HashMap<String, AirWaybillDetailEntity>();
		
		/*
		 * 比较当前操作航空正单明细
		 * 将航空正单明细的运单取出来
		 * */
		
		convertMap(newAirWaybillDetailEntityMap, airWaybillDetailEntity);
		Set<String> cubcWBset = newAirWaybillDetailEntityMap.keySet();
		//上一次航空正单明细保存的记录
		List<AirWaybillDetailEntity> oldAirWaybillDetailList = pointsSingleJointTicketDao
				.queryHistoryAirWaybillDetail(newbillEntity.getId());
		//将上一次航空正单明细保存的运单号取出来
		Map<String, AirWaybillDetailEntity> oldAirWaybillDetailEntityMap = 
				new HashMap<String, AirWaybillDetailEntity>();
		
		
		
		convertMap(oldAirWaybillDetailEntityMap, oldAirWaybillDetailList);
		//需要删除的航空正单明细map
		Map<String, String> delMap = new HashMap<String, String>();
		//将需要删除的航空正单明细取出来放入delMap中
		for (Map.Entry<String, AirWaybillDetailEntity> entry : oldAirWaybillDetailEntityMap
				.entrySet()) {
			String wayBillNo = entry.getKey();
			//2013-07-27 wqh
			//BUG-47643  航空正单问题
			if (!newAirWaybillDetailEntityMap.containsKey(wayBillNo)) {
				//continue;
				delMap.put(wayBillNo, entry.getValue().getId());
			}
		}
	    //将depMap 转换为delList
		List<String> delList = new ArrayList<String>(delMap.values());
		// 需新增的航空正单明细map
		Map<String, AirWaybillDetailEntity> addMap = 
				new HashMap<String, AirWaybillDetailEntity>();
		
		// 保存的航空正单明细list
		List<AirWaybillDetailEntity> addAirWaybillDetailList = 
				new ArrayList<AirWaybillDetailEntity>();
		// 修改的航空正单明细list
		List<AirWaybillDetailEntity> updateAirWaybillDetailList = 
				new ArrayList<AirWaybillDetailEntity>();
		
		//获取航空正单中的运单号
		 List existAirWaybillEntityDetail= pointsSingleJointTicketDao
					.queryWaybillNoList(newbillEntity.getAirWaybillNo());
		
		// 遍历需要删除、修改的航空正单明细
		for (Map.Entry<String, AirWaybillDetailEntity> entry : newAirWaybillDetailEntityMap
				.entrySet()) {
			String wayBillNo = entry.getKey();
			//2013-07-27 wqh
			//BUG-47643  航空正单问题
			String id = entry.getValue().getId();
			// 需要新增的流水明细list
			List<AirWaybillSerialNoEntity> addWaybillSerialNoList = 
					new ArrayList<AirWaybillSerialNoEntity>();
			
			//BUG-47643 航空正单问题 ,一个航空正单中存在多个运单
			//2013-07-27  wqh
            //保存航空正单明细之前判断在对应的航空正单里面是否已经存在了对应的明细
			
			//对应的航空正单中不存在该运单，则该航空正单明细为新增
			if(!existAirWaybillEntityDetail.contains(wayBillNo)){
//			boolean exists = pointsSingleJointTicketDao
//					.queryAirWaybillDetailEntityIsNotNull(id);
//			if (!exists) {
				//将需新增的运单号放入addMap中
				addMap.put(wayBillNo, entry.getValue());
			}else{
				//删除流水号
				List<String> delSerialNoList = delParameterMap.get(wayBillNo);
				//新增流水号
				
				List<String> addSerialNoList = addParameterMap.get(wayBillNo);
				//增加addSerialNoList非空判断
				if(!CollectionUtils.isEmpty(addSerialNoList)){
					for(String str : addSerialNoList){
						AirWaybillSerialNoEntity airWaybillSerialNoEntity = 
								new AirWaybillSerialNoEntity();
						airWaybillSerialNoEntity.setSerialNo(str.split(",")[0]);
						airWaybillSerialNoEntity.setId(UUIDUtils.getUUID());
						airWaybillSerialNoEntity.setAirWaybillDetailId(id);
						airWaybillSerialNoEntity.setStockStatus(str.split(",")[1]);
						airWaybillSerialNoEntity.setCreateTime(new Date());
						airWaybillSerialNoEntity.setWaybillNo(wayBillNo);
						addWaybillSerialNoList.add(airWaybillSerialNoEntity);
					}
					pointsSingleJointTicketDao.addAirWaybillSerialNoEntity(addWaybillSerialNoList);
				}
				if(!CollectionUtils.isEmpty(delSerialNoList)){
					LOGGER.info("删除流水号ID为:{}的所有流水号"+id);
					pointsSingleJointTicketDao.deleteModifyAirWaybillSerialNo(delSerialNoList,id);
					LOGGER.info("已将ID为:{}的所有流水号记录删除完毕"+id);
				}
				//add 2013-06-05如果这个运单addSerialNoList或者delSerialNoList不为空，说明对这个运单做了操作，则更新航空正单明细信息
				if(!CollectionUtils.isEmpty(delSerialNoList) || !CollectionUtils.isEmpty(addSerialNoList)){
					updateAirWaybillDetailList.add(entry.getValue());
				}
			}
		}
		//需要新增的航空正单明细LIST
		List<AirWaybillDetailEntity> addList = new ArrayList<AirWaybillDetailEntity>(
				addMap.values());
		// 需要新增的流水明细list
		List<AirWaybillSerialNoEntity> waybillSerialNoList = 
				new ArrayList<AirWaybillSerialNoEntity>();
		for (AirWaybillDetailEntity airWaybillDetailEntity2 : addList) {
			String waybillNo = airWaybillDetailEntity2.getWaybillNo();
			String detailId = UUIDUtils.getUUID();
			airWaybillDetailEntity2.setId(detailId);
			airWaybillDetailEntity2.setAirWaybillId(newbillEntity.getId());
			//是包的时候不走
			if(!waybillNo.substring(0, 1).equals("B")){
				// 设置航班号此处航班号同当前修改的航空正单航班号为同一航班号
				airWaybillDetailEntity2
						.setPlanFlightNo(newbillEntity.getFlightNo());
			}
			if(StringUtil.equals(AirfreightConstants.StATUS_NULL, airWaybillDetailEntity2.getStockStatus())){
				airWaybillDetailEntity2.setStockStatus(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME);
			}
			// 设置库存状态和是否开单订舱(此字段将删除)
			airWaybillDetailEntity2.setBeBooking(FossConstants.ACTIVE);
			airWaybillDetailEntity2.setCreateTime(new Date());
			// 将正单设置为已配载
			airWaybillDetailEntity2
					.setIsLoading(AirfreightConstants.AIRFREIGHT_ISYESLOADING);
			
			addAirWaybillDetailList.add(airWaybillDetailEntity2);
			Map<String, String> serialNoMap = new HashMap<String, String>();
			//增加
			
			List<String> delSerialNoList = delParameterMap.get(waybillNo);
			//if(!AirfreightConstants.PACKAGE_AIR.equals(airWaybillDetailEntity2.getTransportType())){
				// 获取运单流水明细
				List<AirSerialNoDetail> airSerialNoList = getAirSerialNoDetailList(waybillNo,airWaybillDetailEntity2.getStockStatus());
				//运单已保存过的流水号
				List<AirWaybillSerialNoEntity> airwayBillSerialNo = pointsSingleJointTicketDao
						.queryAirWaybillSerialNoEntityList(waybillNo);
				//将已保存的流水号加入到叉积比较的map中
				for(AirWaybillSerialNoEntity airWaybillSerialNoEntity : airwayBillSerialNo){
					//添加delSerialNoList为空的判断
					//2013-07-27
					if(delSerialNoList!=null){
						delSerialNoList.add(airWaybillSerialNoEntity.getSerialNo());
						
					}
				}
				if(!CollectionUtils.isEmpty(delSerialNoList)){
					for (String str : delSerialNoList) {
						serialNoMap.put(str, str);
					}
				}
				for (AirSerialNoDetail airSerialNoDetail : airSerialNoList) {
					if (serialNoMap.containsKey(airSerialNoDetail.getSerialNo())) {
						continue;
					}
					AirWaybillSerialNoEntity airWaybillSerialNoEntity = new AirWaybillSerialNoEntity();
					airWaybillSerialNoEntity.setId(UUIDUtils.getUUID());
					airWaybillSerialNoEntity.setCreateTime(new Date());
					airWaybillSerialNoEntity.setSerialNo(airSerialNoDetail.getSerialNo());
					airWaybillSerialNoEntity.setStockStatus(airSerialNoDetail.getStockStatus());
					airWaybillSerialNoEntity.setWaybillNo(waybillNo);
					airWaybillSerialNoEntity.setAirWaybillDetailId(detailId);
					waybillSerialNoList.add(airWaybillSerialNoEntity);
				}
				LOGGER.info("新增流水号明细成功.");
			//}
		}
		if(!CollectionUtils.isEmpty(waybillSerialNoList)){
			pointsSingleJointTicketDao.addAirWaybillSerialNoEntity(waybillSerialNoList);
		}
		// 如果航空正单明细list空则保存
		if (!CollectionUtils.isEmpty(addAirWaybillDetailList)) {
		
			
			addAirWaybillDetailEntity(addAirWaybillDetailList);
			//如果修改正单时新加入运单则将对应交接单入库状态为未入库
			airHandOverBillService.updateStockStatusByAirWaybillNo(newbillEntity.getAirWaybillNo());
		}
		// add 2013-06-05修改航空正单明细列表
		if (!CollectionUtils.isEmpty(updateAirWaybillDetailList)) {
			pointsSingleJointTicketDao.updateAirWaybillDetailList(updateAirWaybillDetailList);
		}
		// 如果dellist为空则没有删除操作
		if (!CollectionUtils.isEmpty(delList)) {
			LOGGER.info("批量删除航空正单明细、流水明细.");
			// 批量删除航空正单明细
			pointsSingleJointTicketDao
			.deleteAirWaybillDetail(delList.toArray());
			// 批量删除航空正单流水明细
			pointsSingleJointTicketDao.deleteAirWaybillSerialNo(delList
					.toArray());
			LOGGER.info("批量删除航空正单明细、流水明细成功.");
		}
		// 判断UCBC
		boolean ucbcFlag = false;
		try {
			// 调用结算
			if (oldbillEntity != null && newbillEntity != null) {
				LOGGER.info("正单号为:{}调用结算预收应付.",newbillEntity.getAirWaybillNo());
		  		//根据当前部门取空运总调
				String deptCode = FossUserContext.getCurrentDeptCode();
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
				UserEntity user = FossUserContext.getCurrentInfo().getUser();
				//封装灰度实体，类型是正单
				GrayParameterDto parDto = new GrayParameterDto();
				parDto.setSourceBillType(CUBCGrayUtil.SBType.HK.getName());
				parDto.setSourceBillNos(new String[]{newbillEntity.getAirWaybillNo()});
				//调用灰度
				VestResponse responseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
				//因为这边传入的只有一个单号，所以只会返回一条
				if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(responseDto.getVestBatchResult().get(0).getVestSystemCode())) {
					airStowageService.modifyAirStowage(oldbillEntity, newbillEntity,
							new CurrentInfo(user,orgAdministrativeInfoEntity));
				} else {
					ucbcFlag = true;
				}
			}		
		} catch (BusinessException e) {
			throw new PointsSingleJointTicketException(e.getErrorCode());
		}
		
		/**
		 * 修改航空正单同步至CUBC
		 * @author 316759-RuipengWang-foss
		 * @date 2016-10-12 09:04:34 AM
		 */
		if (ucbcFlag) {
			if (null != newbillEntity) {
				//335284 增加正单内运单明细
				List<String> cubcUpdateWBs = new ArrayList<String>();
				cubcUpdateWBs.addAll(cubcWBset);
				LOGGER.info("modify airwaybill cubcWBset " + cubcUpdateWBs);
				newbillEntity.setDetailWaybills(cubcUpdateWBs);
				
				String requestStr = JSONObject.toJSONString(newbillEntity);
				LOGGER.info("推送给CUBC的参数 requestStr = " + requestStr);
				ResponseToCubcCallBack responseDto = null;
				try {
					responseDto = fossToCubcService.pushUpdateAirWaybill(requestStr);
				} catch (TfrBusinessException e) {
					// 推送失败
					throw new PointsSingleJointTicketException("推送航空证单信息至CUBC失败-" + e.getMessage());
				}
				if (null == responseDto) { // 返回结果为空
					throw new PointsSingleJointTicketException("推送航空证单信息至CUBC失败");
				} else if (StringUtils.equals(responseDto.getResult(),
						AirfreightConstants.AIRFREIGHT_CUBC_FAILURE)) { // 结果为失败
					throw new PointsSingleJointTicketException("CUBC: " + responseDto.getReason());
				}
			}
		
        /**
         * 同步航空正单信息至OPP
         * 更新航空正单 保存航空正单号以及航空正单id 保存数据至临时表
         */
		//2016-05-23 合票清单修改 数据同步至OPP
		//@author 269701
		//合票清单信息转换成JOB临时表的数据
       /* if(StringUtils.equals("DDWFD", newbillEntity.getAirAssembleType()) ||
        		StringUtils.equals("HDPWF", newbillEntity.getAirAssembleType())){*/
    		AirWaybillTempEntity temEntity=new AirWaybillTempEntity();
    		//航空正单主表id
    		temEntity.setAirWaybillId(newbillEntity.getId());
    		//正单号
    		temEntity.setAirWaybillNo(newbillEntity.getAirWaybillNo());
    		//推送中
    		temEntity.setPushStatus(FossConstants.NO);
    		//清单：20 正单 10
    		temEntity.setBillType("10");
    		//修改航空正单数据
    		temEntity.setOperateStatus("UPDATE");
    		LOGGER.info("FOSS更新航空正单信息，数据暂存临时表 等待job推送；正单号 {"+newbillEntity.getAirWaybillNo()+"} 主表信息 开始...."+new Date());
    		pushAirPickUpInfoService.addAirPickToTemp(temEntity);
    		LOGGER.info("FOSS更新航空正单信息，数据暂存临时表 等待job推送；正单号 {"+newbillEntity.getAirWaybillNo()+"} 主表信息 结束...."+new Date());
        }

		return airWaybillDetailDto;
}

	/**
	 * Convert map.
	 * @param newAirWaybillDetailEntityMap the new air waybill detail entity map
	 * @param oldAirWaybillDetailList the old air waybill detail list
	 */
	private void convertMap(
			Map<String, AirWaybillDetailEntity> newAirWaybillDetailEntityMap,
			List<AirWaybillDetailEntity> oldAirWaybillDetailList) {
		if (CollectionUtils.isEmpty(oldAirWaybillDetailList)) {
			return;
		}
		for (AirWaybillDetailEntity oldAirWaybillDetailEntity : oldAirWaybillDetailList) {
			newAirWaybillDetailEntityMap.put(
					oldAirWaybillDetailEntity.getWaybillNo(),
					oldAirWaybillDetailEntity);
		}
	}

	/**
	 * 查询航空公司二字码.
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-29 下午1:51:04
	 */
	@Override
	public List<AirlinesEntity> queryAllAirlines() {
		return airlinesService.queryAllAirlines();
	}

	/**
	 * 调用航空正单基础费率.
	 * @param flightCode the flight code
	 * @param loadOrgCode the load org code
	 * @param deptAirfieldCode the dept airfield code
	 * @param billDate the bill date
	 * @return the airlines value add entity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-30 下午4:29:46
	 */
	@Override
	public AirlinesValueAddEntity queryRate(String flightCode,
			String loadOrgCode, String deptAirfieldCode, Date billDate) {
		LOGGER.info("根据航班号:{},配载部门编码:{},出发机场编码:{},录入运单时间:{}查询基础费率",
				new Object[]{flightCode,loadOrgCode,deptAirfieldCode,billDate});
		return airlinesValueAddService4Dubbo.findAirlinesValueAddBycondition(flightCode, loadOrgCode, deptAirfieldCode, billDate);
	}
	
	/**
	 * 调用系统参数.
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-5 上午11:42:46
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillDetailService#queryConfigurationParamsExactByEntity(int, int)
	 */
	@Override
	public List<ConfigurationParamsEntity> queryConfigurationParamsExactByEntity(
			int limit, int start) {
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		ConfigurationParamsEntity configurationParamsEntity = new ConfigurationParamsEntity();
		configurationParamsEntity.setOrgCode(orgAdministrativeInfoEntity.getCode());
		return configurationParamsService.queryConfigurationParamsExactByEntity(configurationParamsEntity, limit, start);
	}
	
	 /**
 	 * 根据以下条件确定唯一航空公司运价方案明细提供.
 	 * @param flightCode 航班公司编码
 	 * @param loadOrgCode 配载部门编码
 	 * @param destDistictCode 到达站编码
 	 * @param goodsTypeCode 货物类型
 	 * @param weight the weight
 	 * @param billDate 当前日期
 	 * @return the big decimal
 	 * @date 2012-10-31 下午2:36:28
 	 */
	@Override
	public FlightPricePlanDetailEntity queryFlightMinPriceRate(String flightNo , String flightCode,
			String loadOrgCode, String destDistictCode, String goodsTypeCode,
			BigDecimal weight, Date billDate) {
		LOGGER.info("根据航班号:{},配载部门编码:{},出发机场编码:{},货物类型:{},重量:{},录入运单时间查询最低运价",
				new Object[]{flightCode,loadOrgCode,destDistictCode,goodsTypeCode,weight,billDate});
		FlightPricePlanDetailEntity entity = flightPricePlanDetailService4Dubbo
				.findFlightPricePlanDetail(flightNo,flightCode, loadOrgCode, 
						destDistictCode, goodsTypeCode, weight, billDate);
		return entity;
	}
	
	/**
	 * 根据航空正单号查询所属运单的出发部门
	 * @param airWaybillNo 正单号
	 * @author liuzhaowei
	 * @date 2013-7-10 下午2:07:04
	 */
	
	/**
	 * 增加过滤到重复的查询记录功能，在映射文件中加
	 * @author  wqh
	 * @date2013-08-01
	 * */
	@Override
	public List<WaybillInfoForNoticeDto> queryWaybillInfoByAirwaybillNo(String airWaybillNo){
		return pointsSingleJointTicketDao.queryWaybillInfoByAirwaybillNo(airWaybillNo);
	}
	
	/**
	 * 根据正单号查询运单明细list.
	 * @param airWaybillNo the air waybill no
	 * @return List<String> 运单号list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-24 上午9:40:34
	 */
	@Override
	public List<String> queryWaybillNoList(String airWaybillNo) {
		return pointsSingleJointTicketDao.queryWaybillNoList(airWaybillNo);
	}
	
	
	/**
	 * 根据正单号查询待打印的航空正单
	 * @param airWaybillNo 正单号
	 * @return 航空正单实体 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-7 下午5:25:50
	 */
	@Override
	public AirWaybillEntity queryWidthPrintAirWaybill(String airWaybillNo) {
		return pointsSingleJointTicketDao.queryWidthPrintAirWaybill(airWaybillNo);
	}
	
	/**
	 * 根据运单号查询运单走货轨迹
	 * @param waybillNo 运单号
	 * @return WayBillAssembleDto
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-10 下午1:59:42
	 */
	@Override
	public List<WayBillAssembleDto> queryWaybillPath(String waybillNo) {
		return pointsSingleJointTicketDao.queryWaybillPath(waybillNo);
	}
	
	/**
	 * 制作唐翼制单
	 * @param airWaybillNo 正单号
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-15 下午2:07:04
	 */
	@Override
	public void saveTangYiAwb(String deptRegionCode,String arrvRegionCode,String airWaybillNo) {
		//根据正单号获取正单明细
		AirWaybillEntity airWaybillEntity = pointsSingleJointTicketDao
				.queryWidthPrintAirWaybill(airWaybillNo);
		//校验正单信息是否为空
		if(airWaybillEntity!= null){
				try {
						int isMake = fossToTangYiService
								.queryTangYiProtected(airWaybillNo);
						//当IS_MAKE返回1表示此正单未做过唐翼
						if(isMake==1){
							AwbBeanUtils awbUtils = setAwb(airWaybillEntity,deptRegionCode,arrvRegionCode);
							fossToTangYiService.createTangYiProtected(awbUtils);
							LOGGER.info("唐翼制单单号为{}制作成功!",airWaybillNo);
						}
					
				} catch (Exception e) {
					LOGGER.info("唐翼制单制作失败!{}",e.getMessage());
					throw new AirWayBillException(e.getMessage());
				}
			
		}

	}
	
	/**
	 * 处理运单基本信息、制单费用信息、其他信息...
	 * @param  airWaybillEntity 正单明细
	 * @param List<AirWaybillDetailEntity> 运单list
	 * @return AwbBeanUtils
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-15 下午2:37:13
	 */
	private AwbBeanUtils setAwb(AirWaybillEntity airWaybillEntity ,String deptRegionCode,String arrvRegionCode){
		AwbBeanUtils beanUtils = new  AwbBeanUtils();
		//运单基本信息
		AwbBasicDto awbBasicDto = new AwbBasicDto();
		//制单费用
		AwbChargeDto awbChargeDto = new AwbChargeDto();
			//当航空运费大于30则运价种类设置为Q、小于或等于30则设置为M
			if(airWaybillEntity.getAirFee().doubleValue() <= ConstantsNumberSonar.SONAR_NUMBER_30){
				awbBasicDto.setRateClass(AirfreightConstants.TANGYI_RATECLASS_LESS);
			}else {
				awbBasicDto.setRateClass(AirfreightConstants.TANGYI_RATECLASS_BIG);
			}
			/**
			 * 运单基本信息 
			 */
			//运单号
			awbBasicDto.setAwbNo(airWaybillEntity.getAirWaybillNo());
			//运单航程 routing 对应的是航空公司三字码
			awbBasicDto.setRouting(deptRegionCode+"/"+arrvRegionCode);
			//承运人
			awbBasicDto.setCarriers(airWaybillEntity.getAgenctCode());
			//发货人 
			awbBasicDto.setShipperName(airWaybillEntity.getShipperName());
			//发货人地址
			awbBasicDto.setShipperAddress(airWaybillEntity.getShipperAddress());
			//发货人电话
			awbBasicDto.setShipperTelephone(airWaybillEntity.getShipperContactPhone());
			//收货人姓名 
			awbBasicDto.setConsigneeName(airWaybillEntity.getReceiverName());
			//收货人地址
			awbBasicDto.setConsigneeAddress(airWaybillEntity.getReceiverAddress());
			//品名代码 
			awbBasicDto.setGoodsCode(airWaybillEntity.getItemCode());
			//品名
			awbBasicDto.setGoods(airWaybillEntity.getGoodsName());
			//件数
			awbBasicDto.setPiece(airWaybillEntity.getGoodsQty());
			//毛重
			awbBasicDto.setWeight(airWaybillEntity.getGrossWeight().doubleValue());
			//体积
			awbBasicDto.setVolume(airWaybillEntity.getVolume().doubleValue());
			//制单地址
			awbBasicDto.setCreatedPlace(airWaybillEntity.getCreateOrgName());
			//航班号
			awbBasicDto.setFlightNo1(airWaybillEntity.getFlightNo());
			//收货人电话
			awbBasicDto.setConsigneeTelephone(airWaybillEntity.getReceiverContactPhone());
			//储运事项
			awbBasicDto.setHandlingInfo(airWaybillEntity.getStorageItem());
			//商品代号
//			awbBasicDto.setItemNo(airWaybillEntity.getItemCode());
			//代理商code
			awbBasicDto.setAgentCode(airWaybillEntity.getAgenctCode());
			//代理人名称
			awbBasicDto.setAgentCode(airWaybillEntity.getAgencyName());
			//计量单位
			awbBasicDto.setUnit("K");
			beanUtils.setAwbBasicDto(awbBasicDto);

			/**
			 *  制单费信息
			 */
			awbChargeDto.setAwbNo(airWaybillEntity.getAirWaybillNo());
			//付款代码
//			awbChargeDto.setChgsCode("现金");
			//RateFlightDate  没有此字段
			//CWeight 计费重量 
			awbChargeDto.setcWeight(airWaybillEntity.getBillingWeight().doubleValue());
			//运价
			awbChargeDto.setRateCharge(airWaybillEntity.getFee().doubleValue());
			//航空运费
			awbChargeDto.setWeightCharge(airWaybillEntity.getAirFee().doubleValue());
			//运单类型
			awbChargeDto.setAwbType("普通");
			//结算文件号 
			awbChargeDto.setAccountingRule(airWaybillEntity.getAccountItem());
			//结算注意事项
			awbChargeDto.setAccountingInfo(airWaybillEntity.getAccountItem());
			//折扣率
			awbChargeDto.setDiscount(new Double(ConstantsNumberSonar.SONAR_NUMBER_100));
			//运价代码  
			awbChargeDto.setRateCode(airWaybillEntity.getItemCode());
			//运价品名 
			awbChargeDto.setRateName(airWaybillEntity.getGoodsName());
			//运价航班
			awbChargeDto.setRateFlightNo(airWaybillEntity.getFlightNo());
			//货币代码国内单
			awbChargeDto.setCurrencyName("CNY");
//			awbChargeDto.setWt("P");
//			awbChargeDto.setOther("p");
			//承运人声明价值
			awbChargeDto.setDvfCarrier(new Double(0));
			//海关声明价值
			awbChargeDto.setDvfCustomer(new Double(0));
			//保险声明价值
			awbChargeDto.setInsurance(airWaybillEntity.getInseranceFee().doubleValue());
			//声明价值附加费
			awbChargeDto.setValCharge(airWaybillEntity.getExtraFee().doubleValue());
			//保险费率
			awbChargeDto.setInsuranceRate(airWaybillEntity.getInseranceFee().doubleValue());
			//保险费默 
			awbChargeDto.setInsuranceFee(airWaybillEntity.getInseranceFee().doubleValue());
			//地面费率
			awbChargeDto.setGroundRate(airWaybillEntity.getGroundFee().doubleValue());
			//地面运费
			awbChargeDto.setGroundFee(airWaybillEntity.getGroundFee().doubleValue());
			//燃油附加费率
			Double oilRate = new Double(0.2);
			awbChargeDto.setOilRate(oilRate);
			//燃油附加费
			awbChargeDto.setOilFee(airWaybillEntity.getFuelSurcharge().doubleValue());
			//代理人其它费用
			awbChargeDto.setChargeDueAgent(new Double(0));
			//税 
			awbChargeDto.setTax(new Double(0));
			//货币兑美元的汇率 
			awbChargeDto.setCurrencyRate(new Double(0));
			
			beanUtils.setAwbChargeDto(awbChargeDto);
		return beanUtils;
	}
	
	/*
	 * 根据运单号查询运单详细信息
	 * @ waybillNo 运单号
	 * @ author wqh
	 * @ date    2013-09-11
	 * */
	public WaybillDetailDto queryWaybillDetailByWaybillNo(String waybillNo){
		
		WaybillDetailDto waybillDetailDto=pointsSingleJointTicketDao.queryWaybillDetailByWaybillNo(waybillNo);	
		return waybillDetailDto;
		
	}

    /**
     * 查询快递单号明细
     * @author lianghaisheng
     * @date 2014-09-17
     * @param airWaybillDetailDto
     * @return
     */
    @Override
    public List<AirWaybillDetailEntity> queryPackageEntity(AirWaybillDetailDto airWaybillDetailDto) {

        List<AirWaybillDetailEntity> list = new ArrayList<AirWaybillDetailEntity>();
        //遍历结果集将快递空运设置到运输性质
        for(AirWaybillDetailEntity entity : list){
            //设置运输性质为快递空运
           entity.setTransportType(AirfreightConstants.PACKAGE_AIR);
        }
        return list;
    }
	/**
	 * 获取 查询航空正单dao.
	 * @return the 查询航空正单dao
	 */
	public IAirWaybillDetailDao getPointsSingleJointTicketDao() {
		return pointsSingleJointTicketDao;
	}
	
	/**
	 * 设置 查询航空正单dao.
	 * @param pointsSingleJointTicketDao the new 查询航空正单dao
	 */
	public void setPointsSingleJointTicketDao(
			IAirWaybillDetailDao pointsSingleJointTicketDao) {
		this.pointsSingleJointTicketDao = pointsSingleJointTicketDao;
	}
	
	/**
	 * 设置 结算service.
	 * @param airStowageService the new 结算service
	 */
	public void setAirStowageService(IAirStowageService airStowageService) {
		this.airStowageService = airStowageService;
	}

	/**
	 * 设置 综合service.
	 * @param airlinesService the new 综合service
	 */
	public void setAirlinesService(IAirlinesService airlinesService) {
		this.airlinesService = airlinesService;
	}

	/**
	 * 设置 航空基础费率service.
	 * @param airlinesValueAddService the new 航空基础费率service
	 */
	/*public void setAirlinesValueAddService(
			IAirlinesValueAddService airlinesValueAddService) {
		this.airlinesValueAddService4Dubbo = airlinesValueAddService;
	}*/

	/**
	 * 设置 调用系统参数.
	 * @param configurationParamsService the new 调用系统参数
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	/**
	 * 设置 调用航空最低运费.
	 * @param flightPricePlanDetailService the new 调用航空最低运费
	 */
	/*public void setFlightPricePlanDetailService(
			IFlightPricePlanDetailService flightPricePlanDetailService) {
		this.flightPricePlanDetailService4Dubbo = flightPricePlanDetailService;
	}*/
	
	/**
	 * 设置 注入中转公共服务.
	 * @param tfrCommonService the new 注入中转公共服务
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	
	/**
	 * 获取 注入航空正单dto.
	 * @return the 注入航空正单dto
	 */
	public AirWaybillDetailDto getAirWaybillDetailDto() {
		return airWaybillDetailDto;
	}
	
	/**
	 * 设置 注入航空正单dto.
	 * @param airWaybillDetailDto the new 注入航空正单dto
	 */
	public void setAirWaybillDetailDto(AirWaybillDetailDto airWaybillDetailDto) {
		this.airWaybillDetailDto = airWaybillDetailDto;
	}

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setFossToTangYiService(IFOSSToTangYiService fossToTangYiService) {
		this.fossToTangYiService = fossToTangYiService;
	}
	
	public void setAirHandOverBillService(IAirHandOverBillService airHandOverBillService) {
		this.airHandOverBillService = airHandOverBillService;
	}

	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}

	@Override
	public List<String> queryWaybillNosByAirWaybillId(String airWaybillId) {
		// TODO Auto-generated method stub
		return pointsSingleJointTicketDao.queryWaybillNosByAirWaybillId(airWaybillId);
	}

    public void setAirLockWaybillService(
			IAirLockWaybillService airLockWaybillService) {
		this.airLockWaybillService = airLockWaybillService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}


	/**
	 * @param pushAirPickUpInfoService : set the property pushAirPickUpInfoService.
	 * @author 269701-foss-lln
	 * @update 2016年5月23日 下午5:55:33
	 * @version V1.0
	 */
	
	public void setPushAirPickUpInfoService(
			IPushAirPickUpInfoService pushAirPickUpInfoService) {
		this.pushAirPickUpInfoService = pushAirPickUpInfoService;
	}

	public void setAirWaybillToCubcService(
			IAirWaybillToCubcService airWaybillToCubcService) {
		this.airWaybillToCubcService = airWaybillToCubcService;
	}

	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}

}