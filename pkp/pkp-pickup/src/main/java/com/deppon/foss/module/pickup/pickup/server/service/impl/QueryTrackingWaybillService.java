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
 * PROJECT NAME	: pkp-pickup
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/server/service/impl/QueryTrackingWaybillService.java
 * 
 * FILE NAME        	: QueryTrackingWaybillService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 修订记录 
	日期 	修订内容 	修订人员 	版本号 
	2012-06-12 	
		初版 新增	邓亭亭	V0.1
	2012-6-27	
	根据魏总、朱俊评审会议修改	邓亭亭	V0.2
	2012-7-25	
	7.20审核完毕，版本升至0.9	邓亭亭	V0.9
	
	
	1.	
	SUC-731-跟踪运单
	1.1	
	相关业务用例
	      BUC_FOSS_5.20.30_560 跟踪运单
	1.2	用例描述
	营业员通过条件查询货物的在途状态以及签收状态、
	签收单的返单状态;对客户货物的全程实时记录、
	了解货物的运输状态和签收单返回情况的过程。
	1.3	用例条件
	条件类型	
	描述	引用系统用例
	前置条件	
	系统开单成功。	
	后置条件	
	营业员电话或短信通知客户货物已到达。	
	1.4	操作用户角色
	操作用户	描述
	营业部客服	
	1、	
	选择在某一时间段从本部门出发的货物，输入查询条件，进入查询列表清单，
	2、	
	选中需要进行查看的货物，查看其当时时间点的运输状态。或对货物进行跟踪。
	1.5	
	界面要求
	1.5.1	
	表现方式
	WEB界面
	
	1.5.2	
	界面原型
	
	 
	图1
	注意：
	1. 列表元素已增加“返单快递单号”。上图显示不完全
	2. 查询区域中“退出”按钮修改为“重置”按钮
	
	 
	图2
	1.5.3	界面描述
	图一：
	界面主要由三部分组成：
	查询条件区域、
	查询结果显示区域、
	跟踪操作区域。
	界面初始化：
	显示：
	查询条件区域、查询结果显示区域（无数据但保留表头）。
	隐藏：
	结果详细信息区域。
	1.	查询条件区域：
	输入查询的条件。
	跟踪类别、
	运输方式、
	是否有签收单、
	运输性质、
	签收情况默认显示所有
	2.	查询结果区域：
	如上图显示查询结果列表并分页：
	3.	跟踪操作区域：
	跟踪操作内容。
	1.6	操作步骤
	基本步骤写正常操作过程
	如果是后台批处理用例，
	此处写逻辑
	序号	基本步骤	相关数据	补充步骤
	1	
	输入条件 点击“查询”	查询条件	
	系统将查询结果以列表的形式显示在结果显示区域。
	业务规则参照SR1
	2	
	选中某条运单记录，双击。	
	运单详细信息	
	此时页面显示“跟踪操作区域”并弹出图二所示对话框 。
	业务规则参照SR2
	
	
	3	
	输入跟踪信息，
	选择“下次继续跟踪”或“完成本单跟踪”	
	跟踪信息	
	4	
	点击“确认”	
	跟踪信息	
	结果详细信息区域隐藏。
	系统记录一条跟踪记录，
	显示在跟踪信息区域内。
	内容参照SR4。业务规则参照SR3
	5	
	点击 “退出”		
	系统弹出确认框“是否确认关闭？”选是关闭 选否则退出关闭。
	
	扩展事件写非典型或异常操作过程
	序号	扩展事件	相关数据	备注
	1a	
	输入条件点击“查询”	
	查询条件	
	“收货日期（起）”与“收货日期（止）”为必填。
	如未填则提示“起止收货日期为必填项！”若起止日期相差大于3天
	 提示“起止日期相隔不能超过3天！”
	1b	
	输入条件点击“查询”	
	查询条件	
	若根据条件未查到订单信息列表显示 “无此信息”
	2a	
	点击弹出框“关闭”按钮		
	关闭该弹出框。
	
	1.7	业务规则
	序号	描述
	SR1	1、
	查询只能查找出跟踪状态为“未跟踪”或“未跟踪完”的本部门的运单。
	SR2	1、	
	双击某条运单时操作区域自动带出该单的 联系方式和联系对象。
	2、1、	
	如查询出来该单已经签收完 操作区域灰掉 
	不能输入信息确认跟踪。
	SR3	1、	
	如选择“下次继续跟踪”则 结果详细信息区域隐藏。
	此信息还在列表中显示。
	2、	
	如选择“完成本单跟踪”则 结果详细信息区域隐藏，
	此条信息从结果列表中自动清除。
	SR4	1、	
	跟踪记录内容包括：
	记录人、
	记录人部门、
	记录人编号、
	跟踪时间、
	跟踪列别、
	跟踪方式、
	单号、
	联系方式、
	跟踪对象。
	
	1.8	
	数据元素
	1.8.1	
	查询条件
	字段名称	说明	输入限制	长度	是否必填	可编辑
	角色	备注
	单号	运单的编号，唯一识别码	文本框	20	否	否	
	跟踪类别	跟踪类别	选择框	40			
	收货日期	收货部门开单日期	文本框	40	是	否	
	运输性质	采用何种方式运输，如卡航、普货	文本	40	否	否	
	运输方式	汽运/空运	文本	40	否	否	
	库存天数	库存在中转部门和目的站部门的时间长短	文本	10	否	否	
	收货人	收货人名字	文本	20	否	否	
	发货人	收货人名字	文本	20	否	否	
	签收情况	签收情况。	选择框	40	否		
	1.8.2	输入操作信息
	字段名称	说明	输入限制	长度	是否必填	可编辑
	角色	备注
	收货人电话	收货人电话号码	文本		是	否	
	跟踪人	当前系统的使用人	文本		是	否	
	跟踪对象	跟踪谁的货物	文本		是	否	
	跟踪类别	跟踪的类型	文本		是	否	
	跟踪内容	跟踪人录入跟踪的内容	文本		是	否	
	
	1.8.3	输出信息
	字段名称	说明	输入限制	长度	是否必填	可编辑
	角色	备注
	单号	运单的编号，唯一识别码	文本输出			否	
	收货日期	收货部门开单日期	文本输出			否	
	目的站	货物到达的最终地方	文本输出			否	
	运输性质	采用何种方式运输，如卡航、普货	文本输出			否	
	运输方式	汽运/空运	文本输出			否	
	托运人	发货客户	文本输出				
	客户编码	系统生成的发货客户识别码	文本输出			否	
	收货人电话	收货人电话号码	文本输出			否	
	是否配载	是否已经配载走货	文本输出			否	
	签收时间	在系统上签收的时间（出库时间）	文本输出			否	
	签收单状态	客户签收单的罚单状态，与快递单号相关联	文本输出			否	
	返单快递单号	返单快递单号	文本输出				
	库存天数	库存在中转部门和目的站部门的时间长短	文本输出			否	
	收货人姓名	收货人名字	文本输出			否	
	签收人	货物的实际签收人	文本输出			否	
	跟踪人	当前系统的使用人	文本输出			否	
	跟踪对象	跟踪谁的货物	文本输出			否	
	交接单保存时间	在系统已做交接保存，但为发车的时间	文本输出			否	
	已配件数	货物配载的件数	文本输出			否	
	库存件数	货物所在部门的实际件数	文本输出			否	
	库存状态	货物在运输过程中在中转和部门的的存在状态：已出库、库存中	文本输出			否	
	交接单编号	用于走货货物清单的编号	文本输出			否	
	操作时间	生成交接单的时间	文本输出			否	
	发车时间	系统生成的车辆出发时间	文本输出			否	
	到达时间	货物到达某一地点的入库时间	文本输出			否	
	发货人		文本输出			否	
	收货人		文本输出			否	
	跟踪类别	跟踪的类型	文本输出			否	
	跟踪时间	跟踪人录入跟踪信息的时间	文本输出			否	
	车次/航班	车辆信息和航班信息	文本输出			否	
	交接部门	从哪个部门出发	文本输出			否	
	交接类型	交接单的类型	文本输出			否	
	库存部门	货物在某一时间点所在的地方	文本输出			否	
	库存状态	库存中/已出库	文本输出			否	
	
	1.9	非功能性需求
	使用量	4000次/天
	2012年全网估计用户数	3000-4000用户
	响应要求（如果与全系统要求 不一致的话）	一般系统要求
	使用时间段	Am 8:00-PM 20:00
	高峰使用时间段	AM 8:00-AM 12:00  PM 14:00- PM 18:00
	
	
	1.10	接口描述：
	接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 */
package com.deppon.foss.module.pickup.pickup.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pickup.api.server.dao.IQueryTrackingWaybillDao;
import com.deppon.foss.module.pickup.pickup.api.server.service.IQueryTrackingWaybillService;
import com.deppon.foss.module.pickup.pickup.api.shared.define.QueryTrackingWaybillConstants;
import com.deppon.foss.module.pickup.pickup.api.shared.domain.WaybillTrackInfoEntity;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.QueryTrackingWaybillDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackAssemblyDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackHandOverDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackStockDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillConditionDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillDto;
import com.deppon.foss.module.pickup.pickup.api.shared.exception.QueryTrackingWaybillException;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirHandOverBillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillDetailService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirHandOverStateDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.WayBillAssembleDto;
import com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService;
import com.deppon.foss.module.transfer.departure.api.shared.dto.StockTrackingDTO;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.dto.WayBillHandOverDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 追踪运单Service实现类
 * 
 * @author ibm-wangfei
 * @date Jan 4, 2013 3:23:29 PM
 */
public class QueryTrackingWaybillService implements IQueryTrackingWaybillService {
	/**
	 * Log日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryTrackingWaybillService.class);

	/**
	 * 运单管理接口
	 */
	IWaybillManagerService waybillManagerService;

	/**
	 * 注入运单追踪DAO
	 */
	private IQueryTrackingWaybillDao queryTrackingWaybillDao;

	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 分单合票service
	 */
	private IAirWaybillDetailService pointsSingleJointTicketService;

	/**
	 * 配载单service类
	 */
	private IVehicleAssembleBillService vehicleAssembleBillService;

	/**
	 * 任务车辆Service
	 */
	private ITruckTaskService truckTaskService;

	/**
	 * 航空正单service
	 */
	private IAirHandOverBillService airHandOverBillService;

	/**
	 * 运单轨迹服务跟踪
	 */
	private ITrackingService trackingService;
	private IProductService productService;
	/** 
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;
	private IDataDictionaryValueService dataDictionaryValueService;
	/**
	 * 
	 * 运单追踪记录数查询
	 * 
	 * @param conditionDto 
	 * 			  conditionDto.trackType 
	 * 						追踪类型 
	 * 			  conditionDto.billTimeFrom 
	 *  					开单时间起/收货日期起
	 * 			  conditionDto.billTimeTo 
	 *   					开单时间止/收货日期止
	 *            conditionDto.waybillNo 
	 *  					运单号
	 *            conditionDto.deliveryCustomerContact 
	 *            			发货客户联系人
	 *            conditionDto.receiveCustomerContact 
	 *            			收货客户联系人
	 *            conditionDto.transportType 
	 *            			运输类型
	 *            conditionDto.productCode 
	 *            			运输性质
	 *            conditionDto.situation 
	 *            			签收情况 
	 *            conditionDto.returnBillType 
	 *            			返单类别
	 *            conditionDto.returnBillFlg 
	 *            			返单类别 -- 页面录入
	 *            conditionDto.returnBillFlgNum 
	 *            			为mybatis判断进行添加
	 *            conditionDto.storageDay 
	 *            			在库天数 
	 *            conditionDto.receiveOrgCode
	 *            			收货部门(出发部门) or 目的站 
	 *            conditionDto.active 
	 *            			默认状态
	 * @return Long
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:40:29 PM
	 * @see com.deppon.foss.module.pickup.pickup.api.server.service
	 *      .IQueryTrackingWaybillService#queryTrackingWaybillCount
	 *      (com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillConditionDto)
	 */
	@Override
	public Long queryTrackingWaybillCount(TrackingWaybillConditionDto conditionDto) {
		// 查询条件必须实例化
		if (conditionDto == null) {
			throw new QueryTrackingWaybillException("查询条件必须实例化");
		}
		// 设置查询条件
		initQueryCondition(conditionDto);

		// 打印页面传入参数
		LOGGER.info(ReflectionToStringBuilder.toString(conditionDto));
		// 判断页面传入的跟踪类型
		if (StringUtil.equals(QueryTrackingWaybillConstants.TRACK_NEXT, conditionDto.getTrackType())) {
			// 接上次追踪
			return this.queryTrackingWaybillDao.queryTrackingWaybillCountForBefore(conditionDto);
		}else if(StringUtil.equals(QueryTrackingWaybillConstants.TRACK_NEW, conditionDto.getTrackType())){
		// 新追踪
		return this.queryTrackingWaybillDao.queryTrackingWaybillCountForNew(conditionDto);
	    }else if(StringUtil.equals(QueryTrackingWaybillConstants.TRACK_COMPLETE, conditionDto.getTrackType())){
		// 已完成追踪
		return this.queryTrackingWaybillDao.queryTrackingWaybillCountForBefore(conditionDto);
	   }else{
		//全部
		conditionDto.setTrackType(null);
		return this.queryTrackingWaybillDao.queryTrackingWaybillCountForAll(conditionDto);
	}
}
	/**
	 * 
	 * 运单追踪查询
	 * 
	 * @param conditionDto
	 * 			  conditionDto.trackType 
	 * 						追踪类型 
	 * 			  conditionDto.billTimeFrom 
	 * 						开单时间起/收货日期起
	 * 			  conditionDto.billTimeTo 
	 * 						开单时间止/收货日期止
	 *            conditionDto.waybillNo 
	 *            			运单号
	 *            conditionDto.deliveryCustomerContact 
	 *            			发货客户联系人
	 *            conditionDto.receiveCustomerContact 
	 *            			收货客户联系人
	 *            conditionDto.transportType 
	 *            			运输类型
	 *            conditionDto.productCode 
	 *            			运输性质
	 *            conditionDto.situation 
	 *            			签收情况 
	 *            conditionDto.returnBillType 
	 *            			返单类别
	 *            conditionDto.returnBillFlg 
	 *            			返单类别 -- 页面录入
	 *            conditionDto.returnBillFlgNum 
	 *            			为mybatis判断进行添加
	 *            conditionDto.storageDay 
	 *            			在库天数 
	 *            conditionDto.receiveOrgCode
	 *            			收货部门(出发部门) or 目的站 
	 *            conditionDto.active 
	 *            			默认状态
	 * @param start
	 * @param limit
	 * @return  List<TrackingWaybillDto>
	 * 			TrackingWaybillDto.waybillNo 
	 * 					运单号	
	 * 			TrackingWaybillDto.billTime  
	 * 					开单时间 ==收货日期	
	 * 			TrackingWaybillDto.targetOrgCode 
	 * 					目的站编码	
	 * 			TrackingWaybillDto.targetOrgName 
	 * 					目的站名称	
	 * 			TrackingWaybillDto.productCode 
	 * 					运输性质	
	 * 			TrackingWaybillDto.deliveryCustomerMobilephone 
	 * 					发货客户手机 == 托运人电话	
	 * 			TrackingWaybillDto.deliveryCustomerPhone 
	 * 					发货客户电话	
	 * 			TrackingWaybillDto.deliveryCustomerContact 
	 * 					发货客户联系人 == 托运人	
	 * 			TrackingWaybillDto.deliveryCustomerCode 
	 * 					发货客户编码 == 页面上 客户编码	
	 * 			TrackingWaybillDto.receiveCustomerMobilephone 
	 * 					收货客户手机 ==收货人电话	
	 * 			TrackingWaybillDto.receiveCustomerPhone 
	 * 					收货客户电话	
	 * 			TrackingWaybillDto.receiveCustomerContact 
	 * 					收货客户联系人 ==收货人	
	 * 			TrackingWaybillDto.receiveCustomerCode 
	 * 					收货客户编码	
	 * 			TrackingWaybillDto.stowageActive 
	 * 					是否配置	
	 * 			TrackingWaybillDto.signTime 
	 * 					签收时间	
	 * 			TrackingWaybillDto.stock 
	 * 					库存外场 ==库存专线	
	 * 			TrackingWaybillDto.storageDay 
	 * 					在库天数	
	 * 			TrackingWaybillDto.situation 
	 * 					签收情况 == 签收状态	
	 * 			TrackingWaybillDto.stockStatus 
	 * 					库存状态	
	 * 			TrackingWaybillDto.deliverymanName 
	 * 					签收人	
	 * 			TrackingWaybillDto.returnBillType 
	 * 					返单类型	
	 * 			TrackingWaybillDto.returnBillStatus 
	 * 					返单状态	
	 * 			TrackingWaybillDto.expressNo 
	 * 					返单快递单号
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Jan 4, 2013 3:24:08 PM
	 * @see 
	 * 			com.deppon.foss.module.pickup.pickup.api.server.service.IQueryTrackingWaybillService#queryTrackingWaybillDtoLit
	 * 			(com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillConditionDto, int, int)
	 */
	@Override
	public List<TrackingWaybillDto> queryTrackingWaybillDtoLit(TrackingWaybillConditionDto conditionDto, int start, int limit) {
		List<TrackingWaybillDto> trackingWaybillDtoList;
		// 设置默认查询条件
//		initQueryCondition(conditionDto);
		// 判断跟踪类型
		if (StringUtil.equals(QueryTrackingWaybillConstants.TRACK_NEXT, conditionDto.getTrackType())) {
			LOGGER.info("当前查询为：接上次追踪");
			// 接上次追踪
			trackingWaybillDtoList = this.queryTrackingWaybillDao.queryTrackingWaybillDtoListForBefore(conditionDto, start, limit);
		}else if(StringUtil.equals(QueryTrackingWaybillConstants.TRACK_NEW, conditionDto.getTrackType())){
			LOGGER.info("当前查询为：新追踪");
			// 新追踪
			trackingWaybillDtoList = this.queryTrackingWaybillDao.queryTrackingWaybillDtoListForNew(conditionDto, start, limit);
		}else if(StringUtil.equals(QueryTrackingWaybillConstants.TRACK_COMPLETE, conditionDto.getTrackType())){
			LOGGER.info("当前查询为：已完成追踪");
			// 已完成追踪
			trackingWaybillDtoList = this.queryTrackingWaybillDao.queryTrackingWaybillDtoListForBefore(conditionDto, start, limit);
		}else{
			//全部
			LOGGER.info("当前查询为：全部");
			conditionDto.setTrackType(null);
			trackingWaybillDtoList = this.queryTrackingWaybillDao.queryTrackingWaybillDtoListForAll(conditionDto, start, limit);
			
		}
		// 判断查询列表是否为empyt
		if (CollectionUtils.isNotEmpty(trackingWaybillDtoList)) {
			LOGGER.info(ReflectionToStringBuilder.toString(trackingWaybillDtoList));
			// 设置查询出列表的其他属性
			setDtoOtherPreperty(trackingWaybillDtoList);
			// 调用中转 处理运输状态 业务
			trackingWaybillDtoList = trackingService.queryTrackingWaybillDtoList(trackingWaybillDtoList);
		
		} else {
			// 不处理
			LOGGER.info("不处理");
		}
		// 返回列表
		return trackingWaybillDtoList;
	}

	/**
	 * 
	 * 新增运单追踪记录
	 * 
	 * @param waybillTrackInfoEntity
	 * 			waybillTrackInfoEntity.waybillNo 运单号
	 *			waybillTrackInfoEntity.trackType 跟踪类型
	 *			waybillTrackInfoEntity.trackMethod 跟踪方式
	 *  		waybillTrackInfoEntity.contactMethod 联系方式
	 *  		waybillTrackInfoEntity.trackMan 联系对象
	 *  		waybillTrackInfoEntity.trackContent 跟踪内容
	 *  		waybillTrackInfoEntity.trackStatus 跟踪状态
	 *  		waybillTrackInfoEntity.operator 操作人名称
	 *  		waybillTrackInfoEntity.operatorCode 操作人编码
	 *  		waybillTrackInfoEntity.operateOrgCode 操作部门code
	 *  		waybillTrackInfoEntity.operateOrgName 操作部门名称
	 *  		waybillTrackInfoEntity.operateTime 操作时间
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Jan 4, 2013 3:24:31 PM
	 * @see 
	 * 			com.deppon.foss.module.pickup.pickup.api.server.service.IQueryTrackingWaybillService#addTrackingWaybillEntity
	 * 			(com.deppon.foss.module.pickup.pickup.api.shared.domain.WaybillTrackInfoEntity)
	 */
	@Override
	public void addTrackingWaybillEntity(WaybillTrackInfoEntity waybillTrackInfoEntity) {
		// 打印新增运单追踪的录入信息
		LOGGER.info(ReflectionToStringBuilder.toString(waybillTrackInfoEntity));
		// 设置运单追踪实体的默认属性
		initWaybillTrackInfoEntity(waybillTrackInfoEntity);
		// 新增运单追踪记录
		this.queryTrackingWaybillDao.addTrackingWaybillEntity(waybillTrackInfoEntity);
	}

	/**
	 * 
	 * 根据运单号查询追踪记录
	 * 
	 * @param 
	 * 		waybillNo
	 * @return QueryTrackingWaybillDto
	 *			queryTrackingWaybillDto.assemblyDtoList 运单配载信息dto列表
	 *			queryTrackingWaybillDto.handOverDtoList 配置单信息dto列表 
	 *			queryTrackingWaybillDto.stockDtoList 运单库存信息Dto
	 *			queryTrackingWaybillDto.waybillTrackInfoEntityList 运单追踪信息列表
	 * @author 
	 * 		ibm-wangfei
	 * @date 
	 * 		Jan 4, 2013 3:24:56 PM
	 * @see com.deppon.foss.module.pickup.pickup.api.server.service.IQueryTrackingWaybillService#queryByWaybillNo(java.lang.String)
	 */
	@Override
	public QueryTrackingWaybillDto queryByWaybillNo(String waybillNo) {
		if (StringUtil.isBlank(waybillNo)) {
			// 判断运单号
			LOGGER.info("运单号没有传入。");
			throw new QueryTrackingWaybillException("运单号没有传入。");
		}
		LOGGER.info("waybillNo : {}", waybillNo);
		// 通过运单号获取运单信息
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if (waybillEntity == null) {
			throw new QueryTrackingWaybillException("运单号{}对应的实体不存在", waybillNo);
		}
		LOGGER.info(ReflectionToStringBuilder.toString(waybillEntity));

		QueryTrackingWaybillDto queryTrackingWaybillDto = new QueryTrackingWaybillDto();
		// 设置运单跟踪信息列表
		List<WaybillTrackInfoEntity> waybillTrackInfoEntityList = this.queryTrackingWaybillDao.queryByWaybillNo(waybillNo);
		queryTrackingWaybillDto.setWaybillTrackInfoEntityList(waybillTrackInfoEntityList);
		// 设置配置信息
		setAssemblyDtoList(waybillEntity, queryTrackingWaybillDto);
		// 设置交接信息
		setTrackHandOverDto(waybillEntity, queryTrackingWaybillDto);
		// 设置库存信息
		setStockDtoList(waybillEntity, queryTrackingWaybillDto);
		return queryTrackingWaybillDto;
	}

	/**
	 * 
	 * 设置查询条件
	 * 
	 * @param condition
 * 			  conditionDto.trackType 
	 * 						追踪类型 
	 * 			  conditionDto.billTimeFrom 
	 *  					开单时间起/收货日期起
	 * 			  conditionDto.billTimeTo 
	 *   					开单时间止/收货日期止
	 *            conditionDto.waybillNo 
	 *  					运单号
	 *            conditionDto.deliveryCustomerContact 
	 *            			发货客户联系人
	 *            conditionDto.receiveCustomerContact 
	 *            			收货客户联系人
	 *            conditionDto.transportType 
	 *            			运输类型
	 *            conditionDto.productCode 
	 *            			运输性质
	 *            conditionDto.situation 
	 *            			签收情况 
	 *            conditionDto.returnBillType 
	 *            			返单类别
	 *            conditionDto.returnBillFlg 
	 *            			返单类别 -- 页面录入
	 *            conditionDto.returnBillFlgNum 
	 *            			为mybatis判断进行添加
	 *            conditionDto.storageDay 
	 *            			在库天数 
	 *            conditionDto.receiveOrgCode
	 *            			收货部门(出发部门) or 目的站 
	 *            conditionDto.active 
	 *            			默认状态
	 * @author 
	 * 			  ibm-wangfei
	 * @date 
	 * 			  Jan 6, 2013 10:58:34 AM
	 */
	private void initQueryCondition(TrackingWaybillConditionDto condition) {
		// 生效
		condition.setActive(FossConstants.ACTIVE);
		// 部门编码
		condition.setReceiveOrgCode(FossUserContextHelper.getOrgCode());
		// 签收单类型
		condition.setReturnBillType(WaybillConstants.NOT_RETURN_BILL);
		if (StringUtil.isNotBlank(condition.getReturnBillFlg())) {
			if (StringUtil.equals(FossConstants.YES, condition.getReturnBillFlg())) {
				// 有签收单
				condition.setReturnBillFlgNum(QueryTrackingWaybillConstants.RETURN_BILL_FLG_NUM_EXIST);
			} else if (StringUtil.equals(FossConstants.NO, condition.getReturnBillFlg())) {
				// 无签收单
				condition.setReturnBillFlgNum(QueryTrackingWaybillConstants.RETURN_BILL_FLG_NUM_NONE);
			}
		}
		if (StringUtil.isNotEmpty(condition.getStorageDay()) && Integer.valueOf(condition.getStorageDay()).intValue() == NumberConstants.ZERO) {
			// 在库天数为0是，忽略在库天数的查询条件
			condition.setStorageDay(null);
		}
		// 判断是否是未签收
		if (StringUtil.equals(QueryTrackingWaybillConstants.SITUATION_NO_SIGN, condition.getSituation())) {
			// 设置是否未签收为YES
			condition.setNoSignFlag(NumberConstants.ONE);
			// 设置签收属性为null
			condition.setSituation(null);
		} else {
			condition.setNoSignFlag(null);
		}
	}

	/**
	 * 设置dot的其他属性
	 * 
	 * @param trackingWaybillDtoList
	 * 			TrackingWaybillDto.waybillNo 运单号	
	 * 			TrackingWaybillDto.billTime  开单时间 ==收货日期	
	 * 			TrackingWaybillDto.targetOrgCode 目的站编码	
	 * 			TrackingWaybillDto.targetOrgName 目的站名称	
	 * 			TrackingWaybillDto.productCode 运输性质	
	 * 			TrackingWaybillDto.deliveryCustomerMobilephone 发货客户手机 == 托运人电话	
	 * 			TrackingWaybillDto.deliveryCustomerPhone 发货客户电话	
	 * 			TrackingWaybillDto.deliveryCustomerContact 发货客户联系人 == 托运人	
	 * 			TrackingWaybillDto.deliveryCustomerCode 发货客户编码 == 页面上 客户编码	
	 * 			TrackingWaybillDto.receiveCustomerMobilephone 收货客户手机 ==收货人电话	
	 * 			TrackingWaybillDto.receiveCustomerPhone 收货客户电话	
	 * 			TrackingWaybillDto.receiveCustomerContact 收货客户联系人 ==收货人	
	 * 			TrackingWaybillDto.receiveCustomerCode 收货客户编码	
	 * 			TrackingWaybillDto.stowageActive 是否配置	
	 * 			TrackingWaybillDto.signTime 签收时间	
	 * 			TrackingWaybillDto.stock 库存外场 ==库存专线	
	 * 			TrackingWaybillDto.storageDay 在库天数	
	 * 			TrackingWaybillDto.situation 签收情况 == 签收状态	
	 * 			TrackingWaybillDto.stockStatus 库存状态	
	 * 			TrackingWaybillDto.deliverymanName 签收人	
	 * 			TrackingWaybillDto.returnBillType 返单类型	
	 * 			TrackingWaybillDto.returnBillStatus 返单状态	
	 * 			TrackingWaybillDto.expressNo 返单快递单号
	 */
	private void setDtoOtherPreperty(List<TrackingWaybillDto> trackingWaybillDtoList) {
		for (TrackingWaybillDto dto : trackingWaybillDtoList) {
			// 目的站名称
			if (StringUtil.isNotEmpty(dto.getTargetOrgCode())) {
				// 根据目的站code转换为目的站名称
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getTargetOrgCode());
				dto.setTargetOrgName(orgAdministrativeInfoEntity == null ? "" : orgAdministrativeInfoEntity.getName());
			} else {
				// 不处理
				LOGGER.info("不处理");
			}
			// 库存专线和库存状态设置
			List<StockTrackingDTO> stockTrackingDTOList = trackingService.getInStockTrackingByWayBillNo(dto.getWaybillNo(), dto.getBillTime());
			if (CollectionUtils.isNotEmpty(stockTrackingDTOList)) {
				// 获取最新一条记录
				StockTrackingDTO stockTrackingDTO = stockTrackingDTOList.get(0);
				// 设置库存状态
				dto.setStockStatus(stockTrackingDTO.getStockStatus());
				// 设置库存专线
				if (StringUtil.isNotEmpty(stockTrackingDTO.getOrgCode())) {
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(stockTrackingDTO.getOrgCode());
					dto.setStock(orgAdministrativeInfoEntity == null ? "" : orgAdministrativeInfoEntity.getName());
				}

			}
		}
	}

	/**
	 * 
	 * 设置运单追踪实体的默认属性
	 * 
	 * @param entity
	 * 			waybillTrackInfoEntity.waybillNo 运单号
	 *			waybillTrackInfoEntity.trackType 跟踪类型
	 *			waybillTrackInfoEntity.trackMethod 跟踪方式
	 *  		waybillTrackInfoEntity.contactMethod 联系方式
	 *  		waybillTrackInfoEntity.trackMan 联系对象
	 *  		waybillTrackInfoEntity.trackContent 跟踪内容
	 *  		waybillTrackInfoEntity.trackStatus 跟踪状态
	 *  		waybillTrackInfoEntity.operator 操作人名称
	 *  		waybillTrackInfoEntity.operatorCode 操作人编码
	 *  		waybillTrackInfoEntity.operateOrgCode 操作部门code
	 *  		waybillTrackInfoEntity.operateOrgName 操作部门名称
	 *  		waybillTrackInfoEntity.operateTime 操作时间
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Jan 6, 2013 3:35:09 PM
	 */
	private void initWaybillTrackInfoEntity(WaybillTrackInfoEntity entity) {
		if (entity == null) {
			return;
		}
		// 操作时间
		entity.setOperateTime(new Date());
		// 操作人
		entity.setOperator(FossUserContextHelper.getUserName());
		// 操作人编码
		entity.setOperatorCode(FossUserContextHelper.getUserCode());
		// 操作部门
		entity.setOperateOrgName(FossUserContextHelper.getOrgName());
		// 操作部门编码
		entity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
	}

	/**
	 * 
	 * 设置配置信息
	 * 
	 * @param waybillEntity
	 * 			waybillTrackInfoEntity.waybillNo 运单号
	 *			waybillTrackInfoEntity.trackType 跟踪类型
	 *			waybillTrackInfoEntity.trackMethod 跟踪方式
	 *  		waybillTrackInfoEntity.contactMethod 联系方式
	 *  		waybillTrackInfoEntity.trackMan 联系对象
	 *  		waybillTrackInfoEntity.trackContent 跟踪内容
	 *  		waybillTrackInfoEntity.trackStatus 跟踪状态
	 *  		waybillTrackInfoEntity.operator 操作人名称
	 *  		waybillTrackInfoEntity.operatorCode 操作人编码
	 *  		waybillTrackInfoEntity.operateOrgCode 操作部门code
	 *  		waybillTrackInfoEntity.operateOrgName 操作部门名称
	 *  		waybillTrackInfoEntity.operateTime 操作时间
	 *  @param queryTrackingWaybillDto
	 *			queryTrackingWaybillDto.assemblyDtoList 运单配载信息dto列表
	 *			queryTrackingWaybillDto.handOverDtoList 配置单信息dto列表 
	 *			queryTrackingWaybillDto.stockDtoList 运单库存信息Dto
	 *			queryTrackingWaybillDto.waybillTrackInfoEntityList 运单追踪信息列表
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Jan 11, 2013 1:20:09 PM
	 */
	private void setAssemblyDtoList(WaybillEntity waybillEntity, QueryTrackingWaybillDto queryTrackingWaybillDto) {
		List<WayBillAssembleDto> wayBillAssembleDtoList = null;
		if (StringUtil.equals(DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN, waybillEntity.getTransportType())) {
			// 空运--根据运单号查询运单走货轨迹
			wayBillAssembleDtoList = pointsSingleJointTicketService.queryWaybillPath(waybillEntity.getWaybillNo());
		} else if (StringUtil.equals(DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN, waybillEntity.getTransportType())) {
			// 汽运--根据运单号查询该运单号的所有汽运配载记录，包括每次的出发时间和预计到达时间
			wayBillAssembleDtoList = vehicleAssembleBillService.queryVehicleAssembleRecordsByWaybillNo(waybillEntity.getWaybillNo());
		}
		List<TrackAssemblyDto> assemblyDtoList = new ArrayList<TrackAssemblyDto>();
		if (CollectionUtils.isNotEmpty(wayBillAssembleDtoList)) {
			TrackAssemblyDto trackAssemblyDto = null;
			for (WayBillAssembleDto wayBillAssembleDto : wayBillAssembleDtoList) {
				trackAssemblyDto = new TrackAssemblyDto();
				// 配载工具No（汽运配载单的车次 Or 空运正单的航班）
				if (StringUtil.equals(DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN, waybillEntity.getTransportType())) {
					// 空运
					trackAssemblyDto.setAssemblyToolNo(wayBillAssembleDto.getFlightNo());
				} else if (StringUtil.equals(DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN, waybillEntity.getTransportType())) {
					// 汽运
					trackAssemblyDto.setAssemblyToolNo(wayBillAssembleDto.getVehicleAssembleNo());
				}
				// 发车时间
				trackAssemblyDto.setDepartureTime(wayBillAssembleDto.getLeaveTime());
				// 预到时间
				trackAssemblyDto.setPlanArriveTime(wayBillAssembleDto.getPreArriveTime());
				assemblyDtoList.add(trackAssemblyDto);
			}
		} else {
			// 不处理
			LOGGER.info("不处理");
		}
		queryTrackingWaybillDto.setAssemblyDtoList(assemblyDtoList);
	}

	/**
	 * 
	 * 设置交接信息
	 * 
	 * @param waybillEntity
	 * 			waybillTrackInfoEntity.waybillNo 运单号
	 *			waybillTrackInfoEntity.trackType 跟踪类型
	 *			waybillTrackInfoEntity.trackMethod 跟踪方式
	 *  		waybillTrackInfoEntity.contactMethod 联系方式
	 *  		waybillTrackInfoEntity.trackMan 联系对象
	 *  		waybillTrackInfoEntity.trackContent 跟踪内容
	 *  		waybillTrackInfoEntity.trackStatus 跟踪状态
	 *  		waybillTrackInfoEntity.operator 操作人名称
	 *  		waybillTrackInfoEntity.operatorCode 操作人编码
	 *  		waybillTrackInfoEntity.operateOrgCode 操作部门code
	 *  		waybillTrackInfoEntity.operateOrgName 操作部门名称
	 *  		waybillTrackInfoEntity.operateTime 操作时间
	 * @param queryTrackingWaybillDto
	 *			queryTrackingWaybillDto.assemblyDtoList 运单配载信息dto列表
	 *			queryTrackingWaybillDto.handOverDtoList 配置单信息dto列表 
	 *			queryTrackingWaybillDto.stockDtoList 运单库存信息Dto
	 *			queryTrackingWaybillDto.waybillTrackInfoEntityList 运单追踪信息列表
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Jan 11, 2013 1:30:19 PM
	 */
	private void setTrackHandOverDto(WaybillEntity waybillEntity, QueryTrackingWaybillDto queryTrackingWaybillDto) {
		List<WayBillHandOverDto> wayBillHandOverDtoList = null;
		List<AirHandOverStateDto> airHandOverStateDtoList = null;
		List<TrackHandOverDto> handOverDtoList = new ArrayList<TrackHandOverDto>();
		if (StringUtil.equals(DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN, waybillEntity.getTransportType())) {
			// 空运--
			airHandOverStateDtoList = airHandOverBillService.queryAirHandOverBillInfo(waybillEntity.getWaybillNo());
			if (CollectionUtils.isNotEmpty(airHandOverStateDtoList)) {
				TrackHandOverDto trackHandOverDto = null;
				for (AirHandOverStateDto airHandOverStateDto : airHandOverStateDtoList) {
					trackHandOverDto = new TrackHandOverDto();
					// 拷贝属性
					BeanUtils.copyProperties(airHandOverStateDto, trackHandOverDto);
					handOverDtoList.add(trackHandOverDto);
				}
			} else {
				// 不处理
				LOGGER.info("不处理");
			}
		} else if (StringUtil.equals(DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN, waybillEntity.getTransportType())) {
			// 汽运--根据运单号查询该运单号的所有汽运配载记录，包括每次的出发时间和预计到达时间
			wayBillHandOverDtoList = truckTaskService.queryWayBillHandOverInfo(waybillEntity.getWaybillNo());
			if (CollectionUtils.isNotEmpty(wayBillHandOverDtoList)) {
				TrackHandOverDto trackHandOverDto = null;
				for (WayBillHandOverDto wayBillHandOverDto : wayBillHandOverDtoList) {
					trackHandOverDto = new TrackHandOverDto();
					// 拷贝属性
					BeanUtils.copyProperties(wayBillHandOverDto, trackHandOverDto);
					handOverDtoList.add(trackHandOverDto);
				}
			} else {
				// 不处理
				LOGGER.info("不处理");
			}
		} else {
			// 不处理
			LOGGER.info("不处理");
		}
		queryTrackingWaybillDto.setHandOverDtoList(handOverDtoList);
	}

	/**
	 * 
	 * 设置库存信息
	 * 
	 * @param waybillEntity
	 * 			waybillTrackInfoEntity.waybillNo 运单号
	 *			waybillTrackInfoEntity.trackType 跟踪类型
	 *			waybillTrackInfoEntity.trackMethod 跟踪方式
	 *  		waybillTrackInfoEntity.contactMethod 联系方式
	 *  		waybillTrackInfoEntity.trackMan 联系对象
	 *  		waybillTrackInfoEntity.trackContent 跟踪内容
	 *  		waybillTrackInfoEntity.trackStatus 跟踪状态
	 *  		waybillTrackInfoEntity.operator 操作人名称
	 *  		waybillTrackInfoEntity.operatorCode 操作人编码
	 *  		waybillTrackInfoEntity.operateOrgCode 操作部门code
	 *  		waybillTrackInfoEntity.operateOrgName 操作部门名称
	 *  		waybillTrackInfoEntity.operateTime 操作时间
	 * @param queryTrackingWaybillDto
	 *			queryTrackingWaybillDto.assemblyDtoList 运单配载信息dto列表
	 *			queryTrackingWaybillDto.handOverDtoList 配置单信息dto列表 
	 *			queryTrackingWaybillDto.stockDtoList 运单库存信息Dto
	 *			queryTrackingWaybillDto.waybillTrackInfoEntityList 运单追踪信息列表

	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Jan 14, 2013 8:04:45 PM
	 */
	private void setStockDtoList(WaybillEntity waybillEntity, QueryTrackingWaybillDto queryTrackingWaybillDto) {
		List<TrackStockDto> stockDtoList = new ArrayList<TrackStockDto>();
		// 库存状况查询、出库（接送货）
		List<StockTrackingDTO> stockTrackingDTOList = trackingService.getInStockTrackingByWayBillNo(waybillEntity.getWaybillNo(), waybillEntity.getBillTime());
		if (CollectionUtils.isNotEmpty(stockTrackingDTOList)) {

			for (StockTrackingDTO stockTrackingDTO : stockTrackingDTOList) {
				TrackStockDto trackStockDto = new TrackStockDto();
				// 拷贝属性
				BeanUtils.copyProperties(stockTrackingDTO, trackStockDto);
				if (StringUtil.isNotEmpty(trackStockDto.getOrgCode())) {
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(trackStockDto.getOrgCode());
					trackStockDto.setOrgName(orgAdministrativeInfoEntity == null ? "" : orgAdministrativeInfoEntity.getName());
				}
				stockDtoList.add(trackStockDto);
			}
		}
		queryTrackingWaybillDto.setStockDtoList(stockDtoList);
	}
	//RFOSS2015041738跟踪运单增加导出功能需求  065340 liutao
	/**
	 * 查询导出
	 * @author LiuTao 065340
	* @date 2015-04-28上午9:42:02
	 */
	@Override
	public InputStream queryExport(TrackingWaybillConditionDto conditionDto) {
		//如果传入conditionDto不为空
	  List<TrackingWaybillDto> trackingWaybillDtoList=null;
	  Long count=0L;
	  if(conditionDto != null){
		// 设置查询条件
		initQueryCondition(conditionDto);
		if (StringUtil.equals(QueryTrackingWaybillConstants.TRACK_NEXT, conditionDto.getTrackType())) {
			// 接上次追踪
			 count= this.queryTrackingWaybillDao.queryTrackingWaybillCountForBefore(conditionDto);
		}else if(StringUtil.equals(QueryTrackingWaybillConstants.TRACK_NEW, conditionDto.getTrackType())){
			// 新追踪
			 count= this.queryTrackingWaybillDao.queryTrackingWaybillCountForNew(conditionDto);
		}else if(StringUtil.equals(QueryTrackingWaybillConstants.TRACK_COMPLETE, conditionDto.getTrackType())){
			// 已完成追踪
			 count= this.queryTrackingWaybillDao.queryTrackingWaybillCountForBefore(conditionDto);
		}else{
			//全部
			conditionDto.setTrackType(null);
			 count= this.queryTrackingWaybillDao.queryTrackingWaybillCountForAll(conditionDto);
		}
			if(null!=count && count.intValue()>0){
			// 判断跟踪类型
			if (StringUtil.equals(QueryTrackingWaybillConstants.TRACK_NEXT, conditionDto.getTrackType())) {
				// 接上次追踪
				trackingWaybillDtoList = this.queryTrackingWaybillDao.queryTrackingWaybillDtoListForBefore(conditionDto,0,count.intValue());
			}else if(StringUtil.equals(QueryTrackingWaybillConstants.TRACK_NEW, conditionDto.getTrackType())){
				// 新追踪
				trackingWaybillDtoList = this.queryTrackingWaybillDao.queryTrackingWaybillDtoListForNew(conditionDto,0,count.intValue());
			}else if(StringUtil.equals(QueryTrackingWaybillConstants.TRACK_COMPLETE, conditionDto.getTrackType())){
				// 已完成追踪
				trackingWaybillDtoList = this.queryTrackingWaybillDao.queryTrackingWaybillDtoListForBefore(conditionDto,0,count.intValue());
			}else{
				//全部
				conditionDto.setTrackType(null);
				trackingWaybillDtoList = this.queryTrackingWaybillDao.queryTrackingWaybillDtoListForAll(conditionDto,0,count.intValue());
			}
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (TrackingWaybillDto trackingWaybillDto : trackingWaybillDtoList) {
				List<String> columnList = new ArrayList<String>();
				//运单号
				columnList.add(trackingWaybillDto.getWaybillNo());
				//收货日期
				columnList.add(DateUtils.convert(trackingWaybillDto.getBillTime(), DateUtils.DATE_TIME_FORMAT));
				//目的站
				if (StringUtil.isNotEmpty(trackingWaybillDto.getTargetOrgCode())) {
					// 根据目的站code转换为目的站名称
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(trackingWaybillDto.getTargetOrgCode());
					columnList.add(orgAdministrativeInfoEntity == null ? "" : orgAdministrativeInfoEntity.getName());
				}
				//运输性质
				ProductEntity condtion=new ProductEntity();
				condtion.setCode(trackingWaybillDto.getProductCode());
				condtion.setActive("Y");
				List<ProductEntity> productEntitys=productService.findProductByCondition(condtion);
				if(null!=productEntitys && productEntitys.size()>0)
				   columnList.add(productEntitys.get(0).getName());
				//托运人
				columnList.add(trackingWaybillDto.getDeliveryCustomerContact());
				//托运人电话
				columnList.add(trackingWaybillDto.getDeliveryCustomerMobilephone());
				//客户编码
				columnList.add(trackingWaybillDto.getDeliveryCustomerCode());
				//收货人
				columnList.add(trackingWaybillDto.getReceiveCustomerContact());
				//收货人电话
				columnList.add(trackingWaybillDto.getReceiveCustomerMobilephone());
				//是否配载
				if(null!=trackingWaybillDto.getStowageActive()&& trackingWaybillDto.getStowageActive().equals("Y")){
					columnList.add("是");
				}else if(null!=trackingWaybillDto.getStowageActive()&& trackingWaybillDto.getStowageActive().equals("N")){
					columnList.add("否");
				}
				//库存件数
				if(null!=trackingWaybillDto.getStockGoodsCount()){
			    	columnList.add(trackingWaybillDto.getStockGoodsCount().toString());
				}else{
			    	columnList.add("0");
				}
				//签收时间
				columnList.add(DateUtils.convert(trackingWaybillDto.getSignTime(), DateUtils.DATE_TIME_FORMAT));
				// 库存专线和库存状态设置
				List<StockTrackingDTO> stockTrackingDTOList = trackingService.getInStockTrackingByWayBillNo(trackingWaybillDto.getWaybillNo(), trackingWaybillDto.getBillTime());
				if (CollectionUtils.isNotEmpty(stockTrackingDTOList)) {
					// 获取最新一条记录
					StockTrackingDTO stockTrackingDTO = stockTrackingDTOList.get(0);
					// 设置库存状态
					if(null!=stockTrackingDTO.getStockStatus() && StringUtil.equalsIgnoreCase(stockTrackingDTO.getStockStatus(), QueryTrackingWaybillConstants.OUT_STOCK)){
						columnList.add("已出库");
					}else if(null!=stockTrackingDTO.getStockStatus() && StringUtil.equalsIgnoreCase(stockTrackingDTO.getStockStatus(), QueryTrackingWaybillConstants.IN_STOCK)){
						columnList.add("库存中");
					}else{
						columnList.add(null);
					}
					// 设置库存专线
					if (StringUtil.isNotEmpty(stockTrackingDTO.getOrgCode())) {
						OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(stockTrackingDTO.getOrgCode());
						columnList.add(orgAdministrativeInfoEntity == null ? "" : orgAdministrativeInfoEntity.getName());
					}else{
						columnList.add(null);
					}
				}else{
					// 设置库存状态
					columnList.add(null);
					// 设置库存专线
					columnList.add(null);
				}
				//库区
				if(null!=trackingWaybillDto.getGoodsAreaCode()){
				  columnList.add(trackingWaybillDto.getGoodsAreaCode());
				}else{
				  columnList.add(null);
				}
				//库存天数
				if(null!=trackingWaybillDto.getStorageDay()){
				    columnList.add(trackingWaybillDto.getStorageDay());
				}else{
					columnList.add("0");
				}
				//签收状态
				 String termsCode=QueryTrackingWaybillConstants.PKP_SIGN_SITUATION;
				 String valueCode=trackingWaybillDto.getSituation();
	    		    if(StringUtil.isNotEmpty(valueCode)){	    		    	
	    		    	DataDictionaryValueEntity dvEntity=dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(termsCode, valueCode);
	    		    	if(dvEntity!=null){	    		    		
	    		    		columnList.add(dvEntity.getValueName());
	    		    	}else{
	    		    		columnList.add(null);
	    		    	}
	    		    }else{
	    		    	columnList.add(null);
	    		    }
				//签收人
				columnList.add(trackingWaybillDto.getDeliverymanName());
				//签收返单状态
				 if(null!=trackingWaybillDto.getReturnBillStatus() && StringUtil.equalsIgnoreCase(trackingWaybillDto.getReturnBillStatus(), QueryTrackingWaybillConstants.ALREADY_RETURN_BILL)){
				     columnList.add("已返单");
				 }else if(null!=trackingWaybillDto.getReturnBillStatus() && StringUtil.equalsIgnoreCase(trackingWaybillDto.getReturnBillStatus(), QueryTrackingWaybillConstants.NONE_RETURN_BILL)){
					 columnList.add("未返单");
				 }else{
					 columnList.add(null);
				 }
				//运单类型
				 if(null!=trackingWaybillDto.getWaybillType() && StringUtil.equalsIgnoreCase(trackingWaybillDto.getWaybillType(), QueryTrackingWaybillConstants.EWAYBILL)){
					columnList.add("电子运单");
				}else{
					columnList.add("传统运单");
				}
				//返单快递单号
				columnList.add(trackingWaybillDto.getExpressNo());
				//运单跟踪详情信息
				List<String> columnListtemp=null;
				List<WaybillTrackInfoEntity> waybillTrackInfoEntityList = this.queryTrackingWaybillDao.queryByWaybillNo(trackingWaybillDto.getWaybillNo());
				if(null!=waybillTrackInfoEntityList && waybillTrackInfoEntityList.size()>0){
				   for(WaybillTrackInfoEntity waybillTrackInfoEntity:waybillTrackInfoEntityList){
					    columnListtemp = new ArrayList<String>();
					   if(columnList != null && columnList.size()>0) {
			    		 for(String column :columnList){
			    			columnListtemp.add(column);
				    	 }
					   }
					    //跟踪内容
					   columnListtemp.add(waybillTrackInfoEntity.getTrackContent());
						//跟踪类型
						if(null!=waybillTrackInfoEntity.getTrackType() && StringUtil.equalsIgnoreCase(waybillTrackInfoEntity.getTrackType(), QueryTrackingWaybillConstants.TRACKTYPE_INTO_PORT)){
							columnListtemp.add("进港跟踪");
						}else if(null!=waybillTrackInfoEntity.getTrackType() && StringUtil.equalsIgnoreCase(waybillTrackInfoEntity.getTrackType(), QueryTrackingWaybillConstants.TRACKTYPE_OUT_PORT)){
							columnListtemp.add("出港跟踪");
						}else if(null!=waybillTrackInfoEntity.getTrackType() && StringUtil.equalsIgnoreCase(waybillTrackInfoEntity.getTrackType(), QueryTrackingWaybillConstants.TRACKTYPE_STOWAGE)){
							columnListtemp.add("配载跟踪");
						}else if(null!=waybillTrackInfoEntity.getTrackType() && StringUtil.equalsIgnoreCase(waybillTrackInfoEntity.getTrackType(), QueryTrackingWaybillConstants.TRACKTYPE_OTHER_EXCEPTION)){
							columnListtemp.add("其他异常");
						}else if(null!=waybillTrackInfoEntity.getTrackType() && StringUtil.equalsIgnoreCase(waybillTrackInfoEntity.getTrackType(), QueryTrackingWaybillConstants.TRACKTYPE_SEARCH_REMARK)){
							columnListtemp.add("查询备注");
						}else{
							columnListtemp.add(null);
						}
						//跟踪对象
						columnListtemp.add(waybillTrackInfoEntity.getTrackMan());
						//跟踪人
						columnListtemp.add(waybillTrackInfoEntity.getOperator());
						//跟踪时间
						columnListtemp.add(DateUtils.convert(waybillTrackInfoEntity.getOperateTime(), DateUtils.DATE_TIME_FORMAT));
						if(null!=columnListtemp){
						     rowList.add(columnListtemp);
						}
				   }
				}else{
					  rowList.add(columnList);
				}
				
			}
			String[] rowHeads = {"运单号","收货日期","目的站","运输性质","托运人","托运人电话","客户编号","收货人","收货人电话","是否配载",
					"库存件数","签收时间","库存状态","库存专线","库区","库存天数","签收状态","签收人","签收返单状态","运单类型","返单快递单号","跟踪内容","跟踪类别","跟踪对象","跟踪人","跟踪时间"};
		    ExportResource exportResource = new ExportResource();
		    exportResource.setHeads(rowHeads);
		    exportResource.setRowList(rowList);
		    ExportSetting exportSetting = new ExportSetting();
		    exportSetting.setSheetName("跟踪运单列表");
		    exportSetting.setSize(NUMBER);
		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	        return objExporterExecutor.exportSync(exportResource, exportSetting);
		} else{
		   //返回空
				   return null;
			}
	  }else{
			//返回空
			return null;
	   }
	}

	/**
	 * 设置 注入运单追踪DAO.
	 * 
	 * @param 
	 * 		queryTrackingWaybillDao the new 注入运单追踪DAO
	 */
	public void setQueryTrackingWaybillDao(IQueryTrackingWaybillDao queryTrackingWaybillDao) {
		this.queryTrackingWaybillDao = queryTrackingWaybillDao;
	}

	/**
	 * 设置 组织信息 Service接口.
	 * 
	 * @param 
	 * 		orgAdministrativeInfoService the new 组织信息 Service接口
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置 配载单service类.
	 * 
	 * @param 
	 * 		vehicleAssembleBillService the new 配载单service类
	 */
	public void setVehicleAssembleBillService(IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	/**
	 * 设置 任务车辆Service.
	 * 
	 * @param 
	 * 		truckTaskService the new 任务车辆Service
	 */
	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	/**
	 * 设置 运单管理接口.
	 * 
	 * @param 
	 * 		waybillManagerService the new 运单管理接口
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * 设置 航空正单service.
	 * 
	 * @param 
	 * 		airHandOverBillService the new 航空正单service
	 */
	public void setAirHandOverBillService(IAirHandOverBillService airHandOverBillService) {
		this.airHandOverBillService = airHandOverBillService;
	}

	/**
	 * 设置 分单合票service.
	 * 
	 * @param 
	 * 		pointsSingleJointTicketService the new 分单合票service
	 */
	public void setPointsSingleJointTicketService(IAirWaybillDetailService pointsSingleJointTicketService) {
		this.pointsSingleJointTicketService = pointsSingleJointTicketService;
	}

	public void setTrackingService(ITrackingService trackingService) {
		this.trackingService = trackingService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
}