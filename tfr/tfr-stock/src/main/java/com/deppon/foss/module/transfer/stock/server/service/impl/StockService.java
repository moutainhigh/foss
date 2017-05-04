/**
 *  initial comments.
 */
/*******************************************************************************
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
 *  PROJECT NAME  : tfr-stock
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/server/service/impl/StockService.java
 *  
 *  FILE NAME          :StockService.java
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
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  ****************库存******************
 *  查询库存：
 *  SR-1	
 *  部门默认为本部门不可以编辑，
 *  查询结果只能是本部门的库存信息，具有较高操作权限部门可以切换部门。
 *  
 *  
 *	SR-2	
 *	根据用户所属部门不同，货区有不同的显示：
 *	外场：可以选择本外场所有货区。
 *	营业部：货区不可选，默认查询本部门的货物。
 *	派送部：货区默认为派送部且不可选。
 *	偏线部：货区默认为偏线部门且不可选。
 *	空运总调：货区不可选，默认查询本部门的货物。
 *
 *
 *	SR-3
 *	按照入库时间降序排序即入库时间越晚顺序越靠前。
 *
 *
 *	SR-4
 *	选择货物点击【登出】按钮时需判断货物的货区必须是
 *  异常货区 或
 *  包装货区 或
 *  贵重物品货区，
 *  如果是其它货区则提示“不可登出”；
 *  
 *  
 *  单件入库：
 *  SR-1:
 *  货区为操作人所在部门的所有货区：
 *	中转外场：显示本外场所有货区
 *	营业部：“货区”隐藏不可见
 *	派送部：“货区”显示为派送部货区且不可选
 *	偏线部门：“货区”显示为偏线货区且不可选
 *	空运总调：“货区”隐藏不可见
 *
 *	SR-2:
 *	流水号有两种录入方式：
 *	直接在表单域录入流水号，各流水号之间用逗号隔开，能够自动匹配，避免完全人工输入。
 *	填写运单号，点击明细按钮，系统将列出此运单下所有货物明细，
 *		如果货物已完成代打木架，这里显示货物新的流水号，勾选需入库的货物复选框。
 *
 *
 *	SR-3:
 *	入库类型包括少货找到、签收异常
 *	当入库类型为：
 * 	少货找到，标记相应的差异报告记录为已处理。
 * 	签收异常，调用接送货模块登记异常信息接口。
 * 
 * 
 *	SR-4:
 *	需货物是否在当前货区：
 *		1.如果不在当前货区则查询货物是否已从上一部门出库，已出库则直接入库当前货区，没出库则向从上一部门出库再做入库并发消息通知上一部门。
 *		2.如果在则提示用户无需入库
 *
 *
 *   SR-5:
 *   如果货物为代打包装货物且状态为未打包装则只允许入库代包装货区。
 *	如果货物为贵重物品只允许入库贵重物品货区。
 *	
 *	SR-6:
 *	入库时需判断本部门是否在货物的走货路径上，如果不在则弹出提示框提示用户，并修改货物走货路径。
 *
 *  ****************必走货**********************
 *  
 *  截止当天16：00未在规定出发时间前出发的卡货（城运）及晚发24小时以上的普货。
 *  规定出发时间：
 *  根据货物的开单时间、线路运行时刻表计算出每个经受运作的规定发车时间
 *  （有规定多班卡车的线路规定出发时间以可以兑现的最晚出发时间为准).
 *  即规定出发时间 <= 当前时间且规定出发时间 <= 当天16:00的卡货城运及当前时间-规定出发时间 >= 24小时的普货。
 *  必走货列表按优先级排序，优先级由运输性质和入库时间到当前时间的时间长度两个因素决定，
 *  运输类型优先级由高到低依次为：精准城运、精准卡航、精准汽运（短途）、精准汽运（长途）、汽运偏线，
 *  时间长度越长优先级越高；
 *  先根据类型排序再按时间长度排序。
 *  
 *  
 *  必走货需求变更2013-08-01 begin
 *  专业部门给出新的必走货定义：
 *  必走货判断：当运输性质为："精准卡航"和"精准城运"，当日查询系统时间减去规定出发时间>0；
 *  或当运输性质为："精准汽运（长）"和"精准汽运（短）、汽运偏线"，当日查询系统时间减去规定发车时间>24小时，即为必走货（其中不包括已装车的货物） 
 *	必走货应用货物规定出发时间与库存时间对比。
 *	下一部门为空或者下一部为驻地派送部门时，不需要查询。
 *
 *  必走货需求变更2013-08-01 end
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDeptTransferMappingService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHeavyBubbleRatioService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IStorageService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DeptTransferMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.WayBillNoLocusConstant;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionOperateDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.ProductDto;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.StockMoveDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.trackings.api.server.service.IPushForWaybillToPTPService;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirHandOverBillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferDictionaryConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.server.dao.ITrackingDao;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackOutService;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.WaybillNoLogingDateDto;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IChangePathDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangePathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.FeedbackDto;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.IChangeGoodsAreaDao;
import com.deppon.foss.module.transfer.stock.api.server.dao.IInOutStockDao;
import com.deppon.foss.module.transfer.stock.api.server.dao.IMoveGoodsStockDao;
import com.deppon.foss.module.transfer.stock.api.server.dao.IStockAreaLogDao;
import com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao;
import com.deppon.foss.module.transfer.stock.api.server.dao.ITogetherTruckStockDao;
import com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.ChangeGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsDepartmentEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.NewAndOldGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockPositionNumberEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockSaleEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.TogetherTruckStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockSaleEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.ChangeGoodsAreaQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.StockOrgDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.UpdateStockPreHandOverStateDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDetailDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockStatisticsDto;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * 实现了对库存的各种操作方法
 * 查询必走货
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 上午11:37:33
 */
public class StockService implements IStockService { 
	
	/**
	 */
	private static final Logger LOGGER = LogManager.getLogger(StockService.class);
	
	/**
	 * 
	 * 判断登录的是不是快递员
	 */
	private ICommonExpressEmployeeService commonExpressEmployeeService;
	public void setCommonExpressEmployeeService(
			ICommonExpressEmployeeService commonExpressEmployeeService) {
		this.commonExpressEmployeeService = commonExpressEmployeeService;
	}
	
	
	/**
	 * 用户组织角色信息service
	 */
	private IUserOrgRoleService userOrgRoleService;
	/**
	 * 获取营业部外场service
	 */
    private ILineService lineService;
	
	/**
	* @description 设置用户组织角色信息
	* @param userOrgRoleService
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015-1-8 下午3:11:27
	*/
	public void setUserOrgRoleService(IUserOrgRoleService userOrgRoleService) {
		this.userOrgRoleService = userOrgRoleService;
	}
	/**
	 * 库区编号修改DAO
	 */
	private IChangeGoodsAreaDao changeGoodsAreaDao;
	
	/**
	* @description 设置库区编号修改DAO
	* @param changeGoodsAreaDao
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午5:06:47
	*/
	public void setChangeGoodsAreaDao(IChangeGoodsAreaDao changeGoodsAreaDao) {
		this.changeGoodsAreaDao = changeGoodsAreaDao;
	}
	/**
	 * 库存迁移DAO
	 */
	private IMoveGoodsStockDao moveGoodsStockDao;
	/**
	 * 
	* @description 设置库存迁移DAO
	* @param moveGoodsStockDao
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-13 上午10:57:24
	 */
	public void setMoveGoodsStockDao(IMoveGoodsStockDao moveGoodsStockDao) {
		this.moveGoodsStockDao = moveGoodsStockDao;
	}
	
	/**
	 * 运单库存DAO
	 */
	private ITrackingDao trackingDao;

	public void setTrackingDao(ITrackingDao trackingDao) {
		this.trackingDao = trackingDao;
	}
	
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}
	
	/** 
	 * 货件库存DAO
	 * */
	private IStockDao stockDao;
	/** 
	 * 运单库存DAO
	 * */
	private IWaybillStockDao waybillStockDao; 
	/** 
	 * 出入库DAO
	 * */
	private IInOutStockDao inOutStockDao;
	/** 
	 * 合车DAO
	 * */
	private ITogetherTruckStockDao togetherTruckStockDao;
	/** 
	 * 产品Service
	 * */
	@Resource
	private IProductService  productService4Dubbo;
	/** 
	 * 修改走货路径DAO
	 * */
	private IChangePathDao changePathDao;
	/** 
	 * 合伙人映射
	 * */
	private IDeptTransferMappingService deptTransferMappingService;
	
	/** 走货路径 service*/
	private ICalculateTransportPathService calculateTransportPathService;
	/** 综合管理 货区service*/
	private IGoodsAreaService goodsAreaService;
	/** 综合管理 货区的库位service*/
	private IStorageService storageService;
	/** 综合管理 组织信息 Service*/
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/** 综合管理 组织信息 Service*/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/** 综合管理 员工信息 Service*/
	private IEmployeeService employeeService;
	/** 接送货 异常签收Service*/
	private IExceptionProcessService exceptionProcessService;
	/** 交接单Service*/
	private IHandOverBillService handOverBillService;
	/** 营业部Service*/
	private ISaleDepartmentService saleDepartmentService;
	/** 接送货*/
	private IArriveSheetManngerService arriveSheetManngerService;
	/** 派送service*/
	private IDeliverbillService deliverbillService;
	/** 站内消息*/
	private IMessageService messageService;
	/** 空运*/
	private IAirWaybillService airWaybillService;
	/** 外发代理*/
	private IVehicleAgencyDeptService vehicleAgencyDeptService; 
	/** 卸车差异报告*/
	private IUnloadDiffReportService unloadDiffReportService;
	/** 查询运单信息接口*/
	private IWaybillManagerService waybillManagerService;
	/** 清仓差异报告接口*/
	private IStReportService stReportService;
	/** 外发代理接口*/
	private IOutfieldService outfieldService;
	/** 查询综合配置信息*/
	private IConfigurationParamsService configurationParamsService;
	/** 签收结果*/
	private IWaybillSignResultService waybillSignResultService;
	/** 包装*/
	private IPackOutService packOutService;
	/** 通知模板*/
	private ISMSTempleteService sMSTempleteService;
	/**接送货*/
	private IWaybillRfcService waybillRfcService;
	/**签收明细service*/
	private ISignDetailService signDetailService;
	/**运单状态服务接口 **/
	private IActualFreightService actualFreightService;
	/**
	 * 货签服务接口 
	 */
	private ILabeledGoodService labeledGoodService;
	/**
	* 对库位操作的接口
	*/
	private IStockAreaLogDao stockAreaLogDao;
	
	/**
	* 空运航空正单交接单
	*/
	private IAirHandOverBillService airHandOverBillService;
	
	/**
	* 对库存的管理Service
	*/
	private IStockService stockService;
	/**
	 * 重泡比 zwd
	 */
	private IHeavyBubbleRatioService heavyBubbleRatioService;
	/**查询快递运单接口 **/
	private IWaybillExpressService waybillExpressService;
	
	
	/**
	 * 货物轨迹推送接口
	* @fields pushTrackForCaiNiaoService
	* @author 14022-foss-songjie
	* @update 2015年5月8日 下午4:24:02
	* @version V1.0
	*/
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;
	/**
	* @description 货物轨迹推送接口
	* @param pushTrackForCaiNiaoService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月8日 下午4:45:59
	*/
	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}
	/**
	 * 重泡比 zwd
	 */
	public void setHeavyBubbleRatioService(
			IHeavyBubbleRatioService heavyBubbleRatioService) {
		this.heavyBubbleRatioService = heavyBubbleRatioService;
	}
	
	/**
	 * 合伙人映射
	 */
	public void setDeptTransferMappingService(
			IDeptTransferMappingService deptTransferMappingService) {
		this.deptTransferMappingService = deptTransferMappingService;
	}
	/**
	 * 常量(保留三位小数)
	 */
	private static final int three = 3;

	/**
	* @param waybillExpressService
	* @description 
	* @version 1.0
	* @author 216208
	* @update 2015-03-09
	*/
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	/**
	* @param airHandOverBillService
	* @description 设置空运航空正单交接单
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-8-5 上午11:07:44
	*/
	public void setAirHandOverBillService(
			IAirHandOverBillService airHandOverBillService) {
		this.airHandOverBillService = airHandOverBillService;
	}

	
	
	/**
	* @param stockService
	* @description 设置对库存的管理Service
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-8-5 上午11:07:26
	*/
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	/**
	* @param storageService
	* @description 设置库位服务
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-8 上午11:05:19
	*/
	public void setStorageService(IStorageService storageService) {
		this.storageService = storageService;
	}
	/**
	* @param stockAreaLogDao
	* @description 设置对库位操作的接口
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-4 下午1:47:39
	*/
	public void setStockAreaLogDao(IStockAreaLogDao stockAreaLogDao) {
		this.stockAreaLogDao = stockAreaLogDao;
	}
	
	/**
	* @param labeledGoodService
	* @description  设置货签服务
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-6-20 下午5:03:37
	*/
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}

	
	/**
	 * 设置 货件库存DAO.
	 *
	 * @param stockDao the new 货件库存DAO
	 */
	public void setStockDao(IStockDao stockDao) {
		this.stockDao = stockDao;
	}
	
	/**
	 * 设置 运单库存DAO.
	 *
	 * @param waybillStockDao the new 运单库存DAO
	 */
	public void setWaybillStockDao(IWaybillStockDao waybillStockDao) {
		this.waybillStockDao = waybillStockDao;
	}
	
	/**
	 * 设置 合车DAO.
	 *
	 * @param togetherTruckStockDao the new 合车DAO
	 */
	public void setTogetherTruckStockDao(
			ITogetherTruckStockDao togetherTruckStockDao) {
		this.togetherTruckStockDao = togetherTruckStockDao;
	}

	/**
	 * 设置 出入库DAO.
	 *
	 * @param inOutStockDao the new 出入库DAO
	 */
	public void setInOutStockDao(IInOutStockDao inOutStockDao) {
		this.inOutStockDao = inOutStockDao;
	}
	
	/**
	 * 设置 修改走货路径DAO.
	 *
	 * @param changePathDao the new 修改走货路径DAO
	 */
	public void setChangePathDao(IChangePathDao changePathDao) {
		this.changePathDao = changePathDao;
	}

	/**
	 * 设置 走货路径 service.
	 *
	 * @param calculateTransportPathService the new 走货路径 service
	 */
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	/**
	 * 设置 综合管理 货区service.
	 *
	 * @param goodsAreaService the new 综合管理 货区service
	 */
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}

	/**
	 * 设置 综合管理 组织信息 Service.
	 *
	 * @param orgAdministrativeInfoService the new 综合管理 组织信息 Service
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 * 设置 产品Service.
	 *
	 * @param productService the new 产品Service
	 */
//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}
	
	/**
	 * 设置 综合管理 组织信息 Service.
	 *
	 * @param orgAdministrativeInfoComplexService the new 综合管理 组织信息 Service
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	/**
	 * 设置 综合管理 员工信息 Service.
	 *
	 * @param employeeService the new 综合管理 员工信息 Service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	/**
	 * 设置 接送货 异常签收Service.
	 *
	 * @param exceptionProcessService the new 接送货 异常签收Service
	 */
	public void setExceptionProcessService(
			IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}
	
	/**
	 * 设置 交接单Service.
	 *
	 * @param handOverBillService the new 交接单Service
	 */
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	
	/**
	 * 设置 营业部Service.
	 *
	 * @param saleDepartmentService the new 营业部Service
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	/**
	 * 设置 接送货.
	 *
	 * @param arriveSheetManngerService the new 接送货
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
	
	/**
	 * 设置 派送service.
	 *
	 * @param deliverbillService the new 派送service
	 */
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}
	
	/**
	 * 设置 空运.
	 *
	 * @param airWaybillService the new 空运
	 */
	public void setAirWaybillService(IAirWaybillService airWaybillService) {
		this.airWaybillService = airWaybillService;
	}
	
	/**
	 * 设置 外发代理.
	 *
	 * @param vehicleAgencyDeptService the new 外发代理
	 */
	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}
	
	/**
	 * 设置 卸车差异报告.
	 *
	 * @param unloadDiffReportService the new 卸车差异报告
	 */
	public void setUnloadDiffReportService(
			IUnloadDiffReportService unloadDiffReportService) {
		this.unloadDiffReportService = unloadDiffReportService;
	}
	
	/**
	 * 设置 清仓差异报告接口.
	 *
	 * @param stReportService the new 清仓差异报告接口
	 */
	public void setStReportService(IStReportService stReportService) {
		this.stReportService = stReportService;
	}
	
	/**
	 * 设置 站内消息.
	 *
	 * @param messageService the new 站内消息
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	
	/**
	 * 设置 查询运单信息接口.
	 *
	 * @param waybillManagerService the new 查询运单信息接口
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	/**
	 * 设置 外发代理接口.
	 *
	 * @param outfieldService the new 外发代理接口
	 */
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	
	/**
	 * 设置 查询综合配置信息.
	 *
	 * @param configurationParamsService the new 查询综合配置信息
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	/**
	 * 设置 签收结果.
	 *
	 * @param waybillSignResultService the new 签收结果
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	
	/**
	 * 设置 包装.
	 *
	 * @param packOutService the new 包装
	 */
	public void setPackOutService(IPackOutService packOutService) {
		this.packOutService = packOutService;
	}
	
	/**
	* @param sMSTempleteService
	* @description 设置通知模板
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-8-5 上午11:08:37
	*/
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}
	
	/**
	* @param waybillRfcService
	* @description 设置接送货
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-8-5 上午11:08:50
	*/
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	
	/**
	* @param signDetailService
	* @description  设置签收明细
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-6-14 下午6:22:31
	*/
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}
	
	/**
	* @param actualFreightService
	* @description  设置运单状态服务
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-6-17 上午10:31:16
	*/
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	/**
	 * 根据运单号、部门、货区查询货件库存
	 * @param waybillStockEntity.waybillNO  运单号
	 * @param waybillStockEntity.orgCode  部门编号
	 * @param waybillStockEntity.goodsAreaCode  货区编号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午11:21:15
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryStock(com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity)
	 */
	@Override
	public List<StockEntity> queryStock(WaybillStockEntity waybillStockEntity) {
		//查询库存表
		return stockDao.queryStock(waybillStockEntity);
	}
	
	/**
	 * 根据运单号查询货件库存
	 * @param waybillStockEntity
	 * @return
	 */
	@Override
	public List<StockEntity> queryStockByWaybillNo(
			WaybillStockEntity waybillStockEntity) {
		//查询库存表
		return stockDao.queryStockByWaybillNo(waybillStockEntity);
	}

	
   /**
    * 根据运单号查询货物库存，并根据返回类型判断 如是3天外发返货则入异常库区 
    * @param waybillStockEntity
    * @author 218427 hongwy-foss
    */
//	public StockEntity queryStockGoodsByWaybillNo(WaybillStockEntity waybillStockEntity){
//		InOutStockEntity inOutStockEntity = new InOutStockEntity();
//		StockEntity entity = new StockEntity();
//		ReturnGoodsRequestEntity returnGoodsRequestEntity = returnGoodsRequestEntityService.queryReturnGoodsRequestEntityByWayBillNo(waybillStockEntity.getWaybillNO());
//		entity = (StockEntity) stockDao.queryStockByWaybillNo(waybillStockEntity);
//		if(entity.getWaybillNO()!=null && StringUtils.equals("OUTSIDE_THREE_DAYS_RETURN", returnGoodsRequestEntity.getReturnType())){
//			this.inStock(inOutStockEntity);
//		}
//		return entity;
//	}


	/**
	 * 分页查询运单库存
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午11:24:38
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryWaybillStock(com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity, java.util.Date, java.util.Date, int, int)
	 */
	@Override
	public List<WaybillStockQueryDto> queryWaybillStock(WaybillStockEntity waybillStockEntity,
			Date beginInStockTime,Date endInStockTime, int limit, int start) {
		
		//封装查询参数
		WaybillStockDto waybillStockDto = new WaybillStockDto();
		//设置货区参数
		List<String> goodsAreaList = new ArrayList<String>();
		//前台传入的散货货区编号、整车货区编号
		String otherGoodsAreaGode = waybillStockEntity.getOtherGoodsAreaGode();
		//解析前后传入的以逗号分隔的货区编号
		if(StringUtils.isNotBlank(otherGoodsAreaGode)){
			String[] goodsAreaCodes = otherGoodsAreaGode.split(",");
			for(int i=0; i<goodsAreaCodes.length; i++){
				goodsAreaList.add(goodsAreaCodes[i]);
			}
		}
		//货区公共选择器 选中的货区编号
		if(StringUtils.isNotBlank(waybillStockEntity.getGoodsAreaCode())){
			//是驻地部门的话,如果没有输入运单号传过来的库区code是"code1,code2" 格式的
			String[] goodsAreaCodes = waybillStockEntity.getGoodsAreaCode().split(",");
			for(int i=0; i<goodsAreaCodes.length; i++){
				goodsAreaList.add(goodsAreaCodes[i]);
			}
			//goodsAreaList.add(waybillStockEntity.getGoodsAreaCode());
		}
		if(CollectionUtils.isNotEmpty(goodsAreaList)){
			waybillStockDto.setGoodsAreaList(goodsAreaList);
		}
		//设置查询参数
		waybillStockDto.setWaybillStock(waybillStockEntity);
		//开始时间
		waybillStockDto.setBeginInStockTime(beginInStockTime);
		//截止时间
		waybillStockDto.setEndInStockTime(endInStockTime);
		//查询运单库存
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
			System.out.println("快递员>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			 List<WaybillStockQueryDto> waybillStockQueryDtoList = new ArrayList<WaybillStockQueryDto>();
			 waybillStockQueryDtoList = waybillStockDao.queryExpressWaybillStock(waybillStockDto, limit, start);
			//遍历运单去获取到达时间
			 for(WaybillStockQueryDto dto : waybillStockQueryDtoList){
				 Date arrivalTime = waybillStockDao.queryExpressArrayTime(dto);
				 dto.setArrivalTime(arrivalTime);
			 }
			 return waybillStockQueryDtoList;
		}else{
			System.out.println("零担<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			 List<WaybillStockQueryDto> waybillStockQueryDtoList = new ArrayList<WaybillStockQueryDto>();
			 waybillStockQueryDtoList = waybillStockDao.queryWaybillStock(waybillStockDto, limit, start);
			 //遍历运单去获取到达时间
			 for(WaybillStockQueryDto dto : waybillStockQueryDtoList){
				 Date arrivalTime = waybillStockDao.queryArrayTime(dto);
				 dto.setArrivalTime(arrivalTime);
			 }
			 return waybillStockQueryDtoList;
		}
	}
	/**
	 * 查询运单库存总数用于分页
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午11:26:15
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryCount(com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity, java.util.Date, java.util.Date)
	 */
	public Long queryCount(WaybillStockEntity waybillStockEntity,Date beginInStockTime,Date endInStockTime){
		WaybillStockDto waybillStockDto = new WaybillStockDto();
		//设置货区参数
		List<String> goodsAreaList = new ArrayList<String>();
		String otherGoodsAreaGode = waybillStockEntity.getOtherGoodsAreaGode();
		if(StringUtils.isNotBlank(otherGoodsAreaGode)){
			String[] goodsAreaCodes = otherGoodsAreaGode.split(",");
			for(int i=0; i<goodsAreaCodes.length; i++){
				goodsAreaList.add(goodsAreaCodes[i]);
			}
		}
		if(StringUtils.isNotBlank(waybillStockEntity.getGoodsAreaCode())){
			//是驻地部门的话,如果没有输入运单号传过来的库区code是"code1,code2" 格式的
			String[] goodsAreaCodes = waybillStockEntity.getGoodsAreaCode().split(",");
			for(int i=0; i<goodsAreaCodes.length; i++){
				goodsAreaList.add(goodsAreaCodes[i]);
			}
			//goodsAreaList.add(waybillStockEntity.getGoodsAreaCode());
		}
		if(CollectionUtils.isNotEmpty(goodsAreaList)){
			waybillStockDto.setGoodsAreaList(goodsAreaList);
		}
		
		waybillStockDto.setWaybillStock(waybillStockEntity);
		waybillStockDto.setBeginInStockTime(beginInStockTime);
		waybillStockDto.setEndInStockTime(endInStockTime);
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
			System.out.println("快递员>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return waybillStockDao.queryExpressWaybillCount(waybillStockDto);
		}else{
			System.out.println("零担<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return waybillStockDao.queryCount(waybillStockDto);
		}
		
	}
	
	/**
	 * PC端页面单件入库
	 * 解析流水号，循环调用PC端入库方法
	 * @author dp-wangqiang
	 * @date 2012-10-12 上午11:52:05
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#inStock(com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity)
	 */
	@Override
	@Transactional
	public void inStockSerialNOs(InOutStockEntity inOutStockEntity, String serialNOs,String confirmFlag, String inStockConfirmFlag) throws StockException{
		
		//设置操作出库设备类型
		inOutStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
		//设置当前调用时间为扫描时间
		inOutStockEntity.setScanTime(new Date());
		//根据运单号和返货开单类型查询运单实体
		WaybillExpressEntity waybillExpress=null;
		try{
		      waybillExpress = waybillExpressService.queryWaybillByOriginalWaybillNo(inOutStockEntity.getWaybillNO(),WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
		}catch (Exception e) {
			throw new StockException("调用 waybillExpressService.queryWaybillByOriginalWaybillNo接口失败");
		}
		//如果查询到的实体不为空则抛异常
		if(waybillExpress!=null){
				throw new StockException("运单号：" + inOutStockEntity.getWaybillNO() +" 为返货开单, 不能入库");
		 }
		
		//页面传入的入库类型为空则表示该操作为登入特殊货区
		if(!StringUtils.isNotBlank(inOutStockEntity.getInOutStockType())){
			//设置入库类型为登入特殊货区
			inOutStockEntity.setInOutStockType(StockConstants.SPECIAL_AREA_IN_STOCK_TYPE);
			//区分具体的特殊货区类型
			if(inOutStockEntity.getGoodsAreaCode()!=null){
				GoodsAreaEntity goodsAreaPojo = goodsAreaService.queryGoodsAreaByCode(inOutStockEntity.getOrgCode(),inOutStockEntity.getGoodsAreaCode());
				//包装货区
				if(StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING, goodsAreaPojo.getGoodsAreaType())){
					inOutStockEntity.setInOutStockType(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE);
				}
				//贵重货区
				if(StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE, goodsAreaPojo.getGoodsAreaType())){
					inOutStockEntity.setInOutStockType(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE);
				}
				//异常货区
				if(StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION, goodsAreaPojo.getGoodsAreaType())){
					inOutStockEntity.setInOutStockType(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE);
				}
			}
			 
		}
		String[] serialNOArray = serialNOs.split(",");
		//将每个流水号入库
		String uuidStr = UUIDUtils.getUUID();
		HashMap<String, String> pushMapToPtp=new HashMap<String, String>();
		for(int i=0;i<serialNOArray.length;i++){
			// 由于单票入库到ptp循环调用 存在性能风险，目前暂定在 inOutStockEntity加一个参数 Map判断运单是否加过（4.10后优化做zx）
		    if(StringUtils.isEmpty(pushMapToPtp.get(inOutStockEntity.getWaybillNO()))){
			    pushMapToPtp.put(inOutStockEntity.getWaybillNO(), "N");
				//map中查询到此运单的值 说明推送过状态变为Y
			}else if(StringUtils.equals("N",pushMapToPtp.get(inOutStockEntity.getWaybillNO()) )){
				pushMapToPtp.put(inOutStockEntity.getWaybillNO(), "Y");
			}
			inOutStockEntity.setPushMapToPtp(pushMapToPtp);
			//出入库单据号
			inOutStockEntity.setInOutStockBillNO(uuidStr);
			//设置流水号
			inOutStockEntity.setSerialNO(serialNOArray[i]);
			//调用入库方法
			this.inStock(inOutStockEntity, confirmFlag, inStockConfirmFlag, true);
		}
	}
	/**
	 * PC端页面 散货货区货物入库
	 * @author 097457-foss-wangqiang
	 * @date 2013-2-26 上午10:22:29
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#bulkGoodsInStock(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public void  bulkGoodsInStock(List<InOutStockEntity> inStockList, String userCode, String userName) throws StockException{
		if(CollectionUtils.isNotEmpty(inStockList)){
			HashMap<String,String> pushMapToPtp = new HashMap<String,String>();
			//遍历提交的货件List
			for(InOutStockEntity inStock : inStockList){
				//操作人工号
				inStock.setOperatorCode(userCode);
				//操作人姓名
				inStock.setOperatorName(userName);
				//设置操作出库设备类型
				inStock.setDeviceType(StockConstants.PC_DEVICE_TYPE);
				//设置当前调用时间为扫描时间
				inStock.setScanTime(new Date());
				//设置入库类型：散货入库
				inStock.setInOutStockType(StockConstants.BULK_GOODS_IN_STOCK_TYPE);
				//pushMapToPtp为空  或者 pushMapToPtp根据运单号获取的值为空，那么表示运单为推送过，添加到map值为N
				if(StringUtils.isEmpty(pushMapToPtp.get(inStock.getWaybillNO()))){
					pushMapToPtp.put(inStock.getWaybillNO(), "N");
					//map中查询到此运单的值 说明推送过状态变为Y
				}else if(StringUtils.equals("N",pushMapToPtp.get(inStock.getWaybillNO()))){
					pushMapToPtp.put(inStock.getWaybillNO(), "Y");
				}
				inStock.setPushMapToPtp(pushMapToPtp);
				//入库
				this.inStock(inStock, StockConstants.CONFIRM, StockConstants.CONFIRM, true);
			}
		}
	}

	/**
	 * 增加库存
	 * 1.增加货件库存
	 * 2.更新运单库存
	 * 3.保存入库动作
	 * @author dp-wangqiang
	 * @date 2012-10-12 上午11:52:05
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#inStock(com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity)
	 */
	@Override
	@Transactional
	public void addStock(InOutStockEntity inOutStockEntity) {
		//***********增加货件库存***************
		//封装入库参数
		StockEntity stockEntity = new StockEntity();
		//入库时间
		Date inStockTime = new Date();
		stockEntity = convertInOutStockToStockEntity(inOutStockEntity,stockEntity);
		boolean isMoveGoodArea = false;
		if(stockEntity.getInStockTime() == null){
			stockEntity.setInStockTime(inStockTime);
		}else{
			//在本部门库存的货区间移动
			isMoveGoodArea = true;
		}
		
		//保存货件库存
		stockDao.addStock(stockEntity);
		//**************更新运单库存****************
		//封装查询运单库存参数
		WaybillStockEntity waybillStock = new WaybillStockEntity();
		//运单号
		waybillStock.setWaybillNO(stockEntity.getWaybillNO());
		//部门
		waybillStock.setOrgCode(stockEntity.getOrgCode());
		//货区
		waybillStock.setGoodsAreaCode(stockEntity.getGoodsAreaCode());
		//下一部门编号
		waybillStock.setNextOrgCode(stockEntity.getNextOrgCode());
		//查询运单库存
		List<WaybillStockEntity> waybillStockList = waybillStockDao.queryWaybillStock(waybillStock);
		//不存在该运单库存
		if(waybillStockList.size() == 0){
			//该票第一件入库时间 及最后一件入库时间
			if(isMoveGoodArea){
				waybillStock.setInStockTime(stockEntity.getInStockTime());
				waybillStock.setLastInStockTime(stockEntity.getInStockTime());
			}else{
				waybillStock.setInStockTime(inStockTime);
				waybillStock.setLastInStockTime(inStockTime);
			}
			
			//库存件数
			waybillStock.setStockGoodsCount(1);
			//计划出发时间
			waybillStock.setPlanStartTime(inOutStockEntity.getPlanStartTime());
			//增加运单库存
			waybillStockDao.addWaybillStock(waybillStock);
			LOGGER.info("入库操作：增加新运单库存：运单号--" + waybillStock.getWaybillNO() + " 货区--" + waybillStock.getGoodsAreaCode() + " 库存件数--" + waybillStock.getStockGoodsCount());
		}else{//已存在该运单库存
			WaybillStockEntity  waybillStockOriginal = waybillStockList.get(0);
			LOGGER.info("入库操作：当前运单库存：运单号--" + waybillStockOriginal.getWaybillNO() + " 货区--" + waybillStockOriginal.getGoodsAreaCode() + " 库存件数--" + waybillStockOriginal.getStockGoodsCount());
			//不是在本部门 货区中移动 则更新该票最后一件入库时间
			if(!isMoveGoodArea){
				waybillStockOriginal.setLastInStockTime(inStockTime);
			//在本部门 货区间移动
			}else{
				//该件入库时间大于运单库存的最后一件入库时间 则更新最后一件入库时间
				if(stockEntity.getInStockTime().after(waybillStockOriginal.getLastInStockTime())){
					waybillStockOriginal.setLastInStockTime(stockEntity.getInStockTime());
				}else{
					//该件入库时间小于运单库存的最后一件入库时间 不更新最后一件入库时间
				}
			}
			//下一部门编号
			waybillStockOriginal.setNextOrgCode(stockEntity.getNextOrgCode());
			//计划出发时间
			waybillStockOriginal.setPlanStartTime(inOutStockEntity.getPlanStartTime());
			//更新运单库存
			waybillStockDao.updateWaybillStockInStock(waybillStockOriginal);
		}
		//********************保存入库动作**************************
		inOutStockEntity.setInOutStockTime(inStockTime);
		//设置动作有效
		inOutStockEntity.setIsValid(FossConstants.ACTIVE);
		//保存入库动作
		inOutStockDao.addInStock(inOutStockEntity);
		
		/**
		 * 空运拉回(更改单调整入库) 的入库类型需要调用此方法
		 **/
		if(StringUtils.equals(StockConstants.AIR_FREIGHT_RETURN_SIGN_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())
			|| StringUtils.equals(StockConstants.WAYBILL_RFC_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			try{
				//查询运单
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(inOutStockEntity.getWaybillNO());
				if(StringUtils.equals(WaybillConstants.AIR_FLIGHT, waybillEntity.getProductCode())){
					airHandOverBillService.updateStockStatusByWaybillNo(inOutStockEntity.getWaybillNO(),inOutStockEntity.getSerialNO());
				}
			}catch (Exception e) {
				LOGGER.error("空运拉回 (更改单调整入库)的入库类型需要调用此方法", e);
			}
		}
		
		/**入库类型是 入库包装货区时 需要调用下 包装的接口  begin*/
		if(StringUtils.equals(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			packInOutServerComm(inOutStockEntity,PackagingConstants.PACKAGING_LOGING_IN);
		}
		/**入库类型是 入库包装货区时 需要调用下 包装的接口  end*/
		//FOSS推送入库时间至PTP
		//2017.1.6  注释 原来的 推送入库时间到ptp 扣款 改到营业部卸车扣款
		/*try {
			LOGGER.error("营业部交接推送入库时间到PTP start");
			List<InOutStockEntity> inOutStockEntityList = new ArrayList<InOutStockEntity>();
			inOutStockEntityList.add(inOutStockEntity);
			this.fossToPTP(inOutStockEntityList);
			LOGGER.error("营业部交接推送入库时间到PTP end");
		} catch (Exception e) {
			LOGGER.error("营业部交接单入库推送PTP入库时间，异常详情："+e);
		}*/
	}
	
	/**
	 * 删除库存记录
	 * 1.删库存
	 * 2.更新运单库存
	 * 3.保存出库动作信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午11:35:34
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#outStock(java.lang.String)
	 */
	@Transactional
	public void deleteStock(InOutStockEntity inOutStockEntity) {
		
		//************删除货件库存*******************
		stockDao.deleteStock(inOutStockEntity);
		//**************更新运单库存****************
		//设置查询运单库存参数
		WaybillStockEntity waybillStock = new WaybillStockEntity();
		//运单号
		waybillStock.setWaybillNO(inOutStockEntity.getWaybillNO());
		//部门
		waybillStock.setOrgCode(inOutStockEntity.getOrgCode());
		//货区编号
		waybillStock.setGoodsAreaCode(inOutStockEntity.getGoodsAreaCode());
		
		//更新运单库存件数
		waybillStockDao.updateWaybillStockOutStock(inOutStockEntity.getWaybillNO(),inOutStockEntity.getGoodsAreaCode(),inOutStockEntity.getOrgCode());
		
		//查询运单库存
		List<WaybillStockEntity> waybillStockList = waybillStockDao.queryWaybillStock(waybillStock);		
		if(waybillStockList != null && waybillStockList.size() > 0){
			WaybillStockEntity  waybillStockOriginal = waybillStockList.get(0);
			if(waybillStockOriginal.getStockGoodsCount() < 1){//该运单库存中的库存件数小于1件
				//删除运单库存
				waybillStockDao.deleteWaybillStock(waybillStockOriginal);
			}
		}
		//**************保存出库动作********************
		//设置出库时间
		Date outStockTime = new Date();
		inOutStockEntity.setInOutStockTime(outStockTime);
		//动作有效
		inOutStockEntity.setIsValid(FossConstants.ACTIVE);
		//保存出库动作信息
		inOutStockDao.addOutStock(inOutStockEntity);
		
	}
	private IPushForWaybillToPTPService pushForWaybillToPTPService;
	
	/**
	 * @param pushForWaybillToPTPService the pushForWaybillToPTPService to set
	 */
	public void setPushForWaybillToPTPService(
			IPushForWaybillToPTPService pushForWaybillToPTPService) {
		this.pushForWaybillToPTPService = pushForWaybillToPTPService;
	}
	/**
	 * 单件入库
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-24 上午9:59:15
	 */
	@SuppressWarnings("finally")
	@Override
	public int inStock(InOutStockEntity inOutStockEntity, String confirmFlag, String inStockConfirmFlag, boolean isPage) {
		/**
		 * 查询运单状态 如果为 已作废 则不能入库
		 */
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(inOutStockEntity.getWaybillNO());
		if(actualFreightEntity!=null){
			if(StringUtils.equals(WaybillConstants.OBSOLETE, actualFreightEntity.getStatus()) || StringUtils.equals(WaybillConstants.ABORTED, actualFreightEntity.getStatus())){
				if(isPage || StringUtils.equals(StockConstants.PDA_DEVICE_TYPE, inOutStockEntity.getDeviceType())){
					throw new StockException("运单号：" + inOutStockEntity.getWaybillNO() +" 已作废或者已中止  ","");
				}else{
					return FossConstants.FAILURE;
				}
			}
		}
		
		
		/**
		 * 已签收出库的不能入库
		 */
		//无标签或者异常货 
				if(StringUtils.equals(StockConstants.NO_LABEL_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())
						|| StringUtils.equals(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ){
			//无标签货物 无法根据运单号和流水号查询
		}else{
			//断货物是否已签收的接口，参数（运单号、流水号）
			String checkSerialNoIsSign = signDetailService.querySerialNoIsSign(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO());
			//已签收出库 
			if(StringUtils.equals(FossConstants.YES, checkSerialNoIsSign)){
				//非反签收
				if(!StringUtils.equals(StockConstants.REVERSE_SIGN_IN_STOCK_TYPE,inOutStockEntity.getInOutStockType())){
					//卸车差异报告少货找到时 返回-2 (卸车差异报告需要写入数据)
					if(StringUtils.equals(StockConstants.UNLOAD_LOSE_GOODS_FOUND_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) && StringUtils.isNotBlank(inOutStockEntity.getNotes())){
						return -2;
					}
					if(isPage || StringUtils.equals(StockConstants.PDA_DEVICE_TYPE, inOutStockEntity.getDeviceType())){
						throw new StockException("运单号：" + inOutStockEntity.getWaybillNO() + "流水号："+ inOutStockEntity.getSerialNO()+" 已签收出库 ","");
					}else{
						return FossConstants.FAILURE;
					}
				}else{
					//反签收是可以入库
				}
			}
		}
		
		/**
		 * 根据运单和流水号 检验流水号是否有效
		 */
		//无标签或者异常货 
		if(StringUtils.equals(StockConstants.NO_LABEL_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())
				|| StringUtils.equals(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//不需要检验运单号和流水号。
			
		}else{
			String checkStautsByWaybillNoAndSerialNo = labeledGoodService.getStautsByWaybillNoAndSerialNo(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO());
			if(StringUtils.equals(FossConstants.YES, checkStautsByWaybillNoAndSerialNo)){
				//流水号有效 继续
			}else if(StringUtils.equals(FossConstants.NO, checkStautsByWaybillNoAndSerialNo)){
				//流水号无效
				if(isPage || StringUtils.equals(StockConstants.PDA_DEVICE_TYPE, inOutStockEntity.getDeviceType())){
					throw new StockException("运单号：" + inOutStockEntity.getWaybillNO() + "流水号："+ inOutStockEntity.getSerialNO()+"无效 ","");
				}
				/**
				 * BUG-45749
				 * 异常货区PDA对"无标签多货"入库出库扫描后，FOSS后台无显示。 begin
				 */
				//无标签或者异常货 入库正常货区
				else if(StringUtils.equals(StockConstants.NO_LABEL_ORIGINAL_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())
							|| StringUtils.equals(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE_NORMAL, inOutStockEntity.getInOutStockType())){
					throw new StockException("运单号：" + inOutStockEntity.getWaybillNO() + "流水号："+ inOutStockEntity.getSerialNO()+"无效 ","");
				}
				/**
				 * BUG-45749
				 * 异常货区PDA对"无标签多货"入库出库扫描后，FOSS后台无显示。 end
				 */
				else{
					return FossConstants.FAILURE;
				}
			}else{
				//没有此流水号
				if(isPage || StringUtils.equals(StockConstants.PDA_DEVICE_TYPE, inOutStockEntity.getDeviceType())){
					throw new StockException("运单号：" + inOutStockEntity.getWaybillNO() + "流水号："+ inOutStockEntity.getSerialNO()+"不存在 ","");
				}
				/**
				 * BUG-45749
				 * 异常货区PDA对"无标签多货"入库出库扫描后，FOSS后台无显示。 begin
				 */
				//无标签或者异常货 入库正常货区
				else if(StringUtils.equals(StockConstants.NO_LABEL_ORIGINAL_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())
							|| StringUtils.equals(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE_NORMAL, inOutStockEntity.getInOutStockType())){
					throw new StockException("运单号：" + inOutStockEntity.getWaybillNO() + "流水号："+ inOutStockEntity.getSerialNO()+"不存在 ","");
				}
				/**
				 * BUG-45749
				 * 异常货区PDA对"无标签多货"入库出库扫描后，FOSS后台无显示。 end
				 */
				else{
					return FossConstants.FAILURE;
				}
			}
		}
		
		
		/**
		 * 提供更新走货路径的状态为入库接口时需要用到的参数。如果异常只记录日志 begin
		 */
		//获取库存部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=null;
		boolean updateTransportPathActionForInstoreFlag = false;
		//根据运单号查询返货类型
		String returntype = waybillManagerService.selectReturnType(inOutStockEntity.getWaybillNO());
		/**
		 * 提供更新走货路径的状态为入库接口时需要用到的参数。如果异常只记录日志 end
		 */
		//判断入库类型是否是 少货上报
		if(StringUtils.equals(StockConstants.LOSE_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//获取配置参数里配置的处理少货的特殊组织部门CODE
			ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
							ConfigurationParamsConstants.TFR_PARM__LOSE_GOODS_SPECIAL_ORG,
							inOutStockEntity.getOrgCode());
			
			if(entityStart != null && StringUtils.isNotBlank(entityStart.getConfValue())){
				//设置库存部门
				inOutStockEntity.setOrgCode(entityStart.getConfValue());
			}else{
				throw new StockException(StockException.QUERY_LOSE_GOODS_SPECIAL_ORG_FAILURE_ERROR_CODE,"");
			}
		}else if(StockConstants.SEVEN_DAYS_RETURN.equals(returntype)){
			//设置库存部门
			inOutStockEntity.setOrgCode(inOutStockEntity.getOrgCode());
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(inOutStockEntity.getOrgCode());
			if(saleDepartmentEntity!=null){
				inOutStockEntity.setGoodsAreaCode(StockConstants.VIRTUAL_GOODS_AREA_CODE);
				inOutStockEntity.setGoodsAreaAdd(StockConstants.VIRTUAL_GOODS_AREA_CODE);
			}else{
				//入库异常货物
				String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				inOutStockEntity.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
			}
			
			//this.returngoodsBills(inOutStockEntity.getWaybillNO());
		}else if(StockConstants.OUTBOUND_THREE_DAYS_RETURN.equals(returntype)){
			inOutStockEntity.setOrgCode(inOutStockEntity.getOrgCode());
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(inOutStockEntity.getOrgCode());
			if(saleDepartmentEntity!=null){
				inOutStockEntity.setGoodsAreaCode(StockConstants.VIRTUAL_GOODS_AREA_CODE);
				inOutStockEntity.setGoodsAreaAdd(StockConstants.VIRTUAL_GOODS_AREA_CODE);
			}else{
				//入库异常货物
				String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
				inOutStockEntity.setInOutStockType(StockConstants.OUTBOUND_THREE_DAYS_RETURN);
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				inOutStockEntity.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
			}
		}
		else{
			orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(inOutStockEntity.getOrgCode());
			//校验设置入库信息：部门、货区、计划出发时间、下一部门
			inOutStockEntity = this.verifyInOutStockInfo(inOutStockEntity, confirmFlag, inStockConfirmFlag,orgAdministrativeInfoEntity);
			updateTransportPathActionForInstoreFlag = inOutStockEntity.getUpdateTransportPathActionForInstoreFlag();
		}
		//异常签收 不做入库 直接返回
		if(StringUtils.equals(StockConstants.EXCEPTION_SIGN_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			return FossConstants.SUCCESS;
		}
		//*************在库存表中查询该货件，校验货件是否已在库存中****************
		List<StockEntity> stockList = stockDao.queryUniqueStock(inOutStockEntity);
		if (CollectionUtils.isNotEmpty(stockList)) {// 该货件存在库存中
			//获取该货件库存记录
			StockEntity stockOriginal = stockList.get(0);
			// 在本部门库存中
			if (StringUtils.equals(stockOriginal.getOrgCode(),inOutStockEntity.getOrgCode())){
				
				//页面调用 时，库存中已有该记录则抛出异常
				if(isPage){
					//如果货区为空设置为N/A
					String goodsAreaCodeTemp = inOutStockEntity.getGoodsAreaCode();
					if(StringUtils.isBlank(inOutStockEntity.getGoodsAreaCode())){
						goodsAreaCodeTemp = StockConstants.VIRTUAL_GOODS_AREA_CODE;
					}else{
						goodsAreaCodeTemp = inOutStockEntity.getGoodsAreaCode();
					}
					//该库存中已存在该货件
					if(StringUtils.equals(stockOriginal.getGoodsAreaCode(), goodsAreaCodeTemp)){
						LOGGER.error("运单号：" + inOutStockEntity.getWaybillNO() + "流水号："+ inOutStockEntity.getSerialNO() 
								+" 库存中已存在该货件");
						throw new StockException(StockException.GOODS_EXIST_STOCK_ERROR_CODE,"");
					}else{
						//不存在
					}
				}else{
					//非页面调用此方法
				}
				
				// 比较扫描时间
				if (inOutStockEntity.getScanTime().after(stockOriginal.getScanTime())){// 当前请求的扫描时间晚于库中的扫描时间
					// 封装该货件本部门库存记录的出库动作
					InOutStockEntity outStock = new InOutStockEntity();
					//************将该货件在当前库存中的货区编号和部门编号设置给出库动作对象***************** 
					//货区
					outStock.setGoodsAreaCode(stockOriginal.getGoodsAreaCode());
					//部门
					outStock.setOrgCode(stockOriginal.getOrgCode());
					//************将请求参数中的信息设置给出库动作对象***************
					//运单号
					outStock.setWaybillNO(inOutStockEntity.getWaybillNO());
					//流水号
					outStock.setSerialNO(inOutStockEntity.getSerialNO());
					//设备类型
					outStock.setDeviceType(inOutStockEntity.getDeviceType());
					//入库类型
					outStock.setInOutStockType(inOutStockEntity.getInOutStockType());
					//操作人工号
					outStock.setOperatorCode(inOutStockEntity.getOperatorCode());
					//操作人姓名
					outStock.setOperatorName(inOutStockEntity.getOperatorName());
					//扫描时间
					outStock.setScanTime(inOutStockEntity.getScanTime());
					//从本部门原库存中出库
					this.deleteStock(outStock);
					// 入库本部门新库存
					inOutStockEntity.setInOutStockTime(stockOriginal.getInStockTime());
					this.addStock(inOutStockEntity);
				} else {// 当前请求的扫描时间早于库中的扫描时间
					inOutStockEntity.setInOutStockTime(new Date());
					//设置入库动作无效
					inOutStockEntity.setIsValid(FossConstants.INACTIVE);
					//保存入库动作
					inOutStockDao.addInStock(inOutStockEntity);
				}
			} else {// 在其它部门库存中
				
				// 入三种特殊货区  判断 货件是否在本部门库存中
				if(StringUtils.equals(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ||
						StringUtils.equals(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ||
							StringUtils.equals(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ||
								StringUtils.equals(StockConstants.SPECIAL_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
					//抛出异常 货物不在本部门库存
					if(StringUtils.equals(StockConstants.PDA_DEVICE_TYPE, inOutStockEntity.getDeviceType())){
						throw new StockException("货物不在本部门库存中","");
					}else{
						throw new StockException(StockException.GOODS_NOT_EXIST_LOCAL_STOCK_ERROR_CODE,"");
					}
					
				}
				
				// 封装该货件库存记录的出库动作
				InOutStockEntity outStock = new InOutStockEntity();
				//**************将该货件在当前库存中的货区编号和部门编号设置给出库动作对象 ************
				//货区
				outStock.setGoodsAreaCode(stockOriginal.getGoodsAreaCode());
				//部门
				outStock.setOrgCode(stockOriginal.getOrgCode());
				//*************将请求参数中的信息设置给出库动作对象*******************
				//运单号
				outStock.setWaybillNO(inOutStockEntity.getWaybillNO());
				//流水号
				outStock.setSerialNO(inOutStockEntity.getSerialNO());
				//设备类型
				outStock.setDeviceType(inOutStockEntity.getDeviceType());
				//入库类型
				outStock.setInOutStockType(inOutStockEntity.getInOutStockType());
				//操作人编号
				outStock.setOperatorCode(inOutStockEntity.getOperatorCode());
				//操作人姓名
				outStock.setOperatorName(inOutStockEntity.getOperatorName());
				//扫描时间
				outStock.setScanTime(inOutStockEntity.getScanTime());
				// 从上一部门出库
				this.deleteStock(outStock);
				
				// 入库本部门库存
				this.addStock(inOutStockEntity);
				//判断入库类型是否是 少货上报
				if(StringUtils.equals(StockConstants.LOSE_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
					//不做移动代办事项、发送出库通知消息
				}else{
					//移动代办事项
					waybillRfcService.queryTodoWhenDumpTruck(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO(), 
									inOutStockEntity.getOrgCode(), stockOriginal.getOrgCode());
					//发送出库通知消息
					this.sentMsg(inOutStockEntity, stockOriginal.getOrgCode());
				}
				
			}
		// 库存中没有该货件
		} else {
			// 入三种特殊货区   
			if(StringUtils.equals(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ||
					StringUtils.equals(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ||
						StringUtils.equals(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ||
							StringUtils.equals(StockConstants.SPECIAL_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
				//抛出异常 货物不在本部门库存 不能入库特殊货区
				if(StringUtils.equals(StockConstants.PDA_DEVICE_TYPE, inOutStockEntity.getDeviceType())){
					throw new StockException("货物不在本部门库存中","");
				}else{
					throw new StockException(StockException.GOODS_NOT_EXIST_LOCAL_STOCK_ERROR_CODE,"");
				}
			}
			if(StringUtils.equals(StockConstants.NORMAL_AREA_FROM_SPECIAL_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())||
					StringUtils.equals(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ||
					StringUtils.equals(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ||
					StringUtils.equals(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ||
						StringUtils.equals(StockConstants.SPECIAL_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
				//抛出异常 货物不库存 无法登出
				LOGGER.error("没有货件" + inOutStockEntity.getWaybillNO() + "---" + inOutStockEntity.getSerialNO() + "库存记录");
				if(StringUtils.equals(StockConstants.PDA_DEVICE_TYPE, inOutStockEntity.getDeviceType())){
					throw new StockException(StockException.GOODS_NOT_EXIST_STOCK_ERROR_CODE_CN,new Object[]{inOutStockEntity.getWaybillNO(),inOutStockEntity.getSerialNO()});
				}else{
					throw new StockException(StockException.GOODS_NOT_EXIST_STOCK_ERROR_CODE,new Object[]{inOutStockEntity.getWaybillNO(),inOutStockEntity.getSerialNO()});
				}
				
			}
			
			//移动代办事项
			waybillRfcService.queryTodoWhenDumpTruck(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO(), 
							inOutStockEntity.getOrgCode(), null);
			
			//入库
			this.addStock(inOutStockEntity);
		}
		
		//单票入库，出虚拟库存    360903
		this.outStockSalePC(inOutStockEntity);
		
		/**
		 * 更新走货路径的状态为入库。如果异常只记录日志 begin
		 */
		if(updateTransportPathActionForInstoreFlag){
			updateTransportPathActionForInstoreComm(inOutStockEntity,orgAdministrativeInfoEntity);
		}
		LOGGER.info("已经发送ptp的map"+inOutStockEntity.getPushMapToPtp());
		Boolean isSend=inOutStockEntity.getPushMapToPtp()==null||StringUtils.equals("N", inOutStockEntity.getPushMapToPtp().get(inOutStockEntity.getWaybillNO()));
		/**
		 * inStockSerialNOs PC端 单票入库 会循环调用多次
		 */
		if(isSend&&isPTPOrg(inOutStockEntity.getOrgCode())){
			try {
				List<WaybillTrackingsDto> waybillList=new ArrayList<WaybillTrackingsDto>();
				WaybillTrackingsDto waybillTrackingsDto=new WaybillTrackingsDto();
				waybillTrackingsDto.setWaybillNo(inOutStockEntity.getWaybillNO());
				waybillTrackingsDto.setNextDeptCode(inOutStockEntity.getOrgCode());
				//新增入库时间 modify by 332219
				Date arrivalTime = this.queryArrivalTimeByWaybillNo(inOutStockEntity);
				LOGGER.info("入库时间------------"+arrivalTime);
				if(arrivalTime!=null){
					waybillTrackingsDto.setInStockTime(arrivalTime);
				}
				waybillList.add(waybillTrackingsDto);
				pushForWaybillToPTPService.pushWaybillToPTP(waybillList, inOutStockEntity.getOperatorCode(), inOutStockEntity.getOperatorName(),"单票入库  合伙人扣款");
			} catch (Exception e) {
				LOGGER.info("单票入库同步运单信息到合伙人扣款  异常 ");
				e.printStackTrace();
			}finally{
				/**
				 * 更新走货路径的状态为入库。如果异常只记录日志 end
				 */
				return FossConstants.SUCCESS;	
			}
		}
		/**
		 * 更新走货路径的状态为入库。如果异常只记录日志 end
		 */
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 
	 * <p>获取运单到达时间</p> 
	 * @author 332219
	 * @date 2016-10-19 上午10:14:09
	 * @param inOutStockEntity
	 * @return
	 * @see
	 */
	private Date queryArrivalTimeByWaybillNo(InOutStockEntity inOutStockEntity){
		//到达时间,初始化当前时间
		Date arrivalTime = new Date();
		//根据运单号查询入库类型
		String inStockType = trackingDao.getInStockTypesWayBillNoToPTP(inOutStockEntity.getWaybillNO());
	    //单票入库
		if(inStockType != null&&inStockType != ""){
			if(StringUtils.equals(inStockType, "LOSS_GOODS_FIND")||StringUtils.equals(inStockType, "SEND_RETURN")||
			   StringUtils.equals(inStockType, "PARTIALLINE_RETURN")||StringUtils.equals(inStockType, "AIR_RETURN")){
				//设置到达时间
				arrivalTime = inOutStockEntity.getInOutStockTime();
			}
		}else{
			arrivalTime = null;
		}
		return arrivalTime;
	}
	
	/**
	 * 
	 * <p>判断是否合伙人部门</p> 
	 * @author 189284 
	 * @date 2016-2-19 上午10:14:09
	 * @param destOrgCode
	 * @return
	 * @see
	 */
	public Boolean isPTPOrg(String destOrgCode){
		SaleDepartmentEntity saleDepartmentEntity=saleDepartmentService.querySaleDepartmentInfoByCode(destOrgCode);
		if(saleDepartmentEntity==null){
			return false;
		}else{
		 return StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getIsLeagueSaleDept());
		}
	}
	
	/**
	 * 根据部门查询货区
	 * @param orgCode 部门编号
	 * @param goodsAreaType 货区类型
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-1 上午11:08:12
	 */
	private String queryGoodsAreaByType(String orgCode,String goodsAreaType) throws StockException{
		//查询货区
		List<String> codes = stockDao.queryGoodsAreaCodes(orgCode, goodsAreaType);
		String goodsAreaCode = null;
		if(CollectionUtils.isNotEmpty(codes)){
			//货区编号
			goodsAreaCode = codes.get(0);
		}else{
			//根据部门查询货区失败
			LOGGER.error("根据部门：" + orgCode + "查询类型为：" + goodsAreaType + "的货区失败");
			throw new StockException(StockException.QUERY_GOODS_AREA_BY_ORGCODE_FAILURE_ERROR_CODE,new Object[]{"部门编号:"+orgCode});
		}
		return goodsAreaCode;
	}
	
	
	/**
	 * 校验设置入库信息：部门、货区、计划出发时间、下一部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-12 下午7:25:08
	 */
	private InOutStockEntity verifyInOutStockInfo(InOutStockEntity inOutStockEntity, String confirmFlag, String inStockConfirmFlag,OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) throws StockException{
		inOutStockEntity.setUpdateTransportPathActionForInstoreFlag(false);
		//验证操作人工号、姓名
		if(StringUtils.isBlank(inOutStockEntity.getOperatorName())){
			//姓名为空，则根据工号查询姓名
			String userName = this.getUserNameByCode(inOutStockEntity.getOperatorCode());
			inOutStockEntity.setOperatorName(userName);
		}
		
		//标识是否是驻地部门
		boolean isStationSalesDepart = false;
		String currentOrgCode = inOutStockEntity.getOrgCode();
		if(orgAdministrativeInfoEntity != null){
			//营业部
			if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getSalesDepartment())){
				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(inOutStockEntity.getOrgCode());
				//驻地部门 
				if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())){
					isStationSalesDepart = true;
					//设置驻地营业部所在外场为库存部门
					inOutStockEntity.setOrgCode(saleDepartmentEntity.getTransferCenter());
					orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(saleDepartmentEntity.getTransferCenter());
					if(orgAdministrativeInfoEntity == null){
						LOGGER.error("查询驻地部门：" + currentOrgCode + "所属外场：" + saleDepartmentEntity.getTransferCenter()+"失败");
						throw new StockException("查询驻地部门：" + currentOrgCode + "所属外场：" + saleDepartmentEntity.getTransferCenter()+"失败","");
					}
					
				}
			//外场或空运总调
			}else if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter()) ||
						StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getAirDispatch())){
				inOutStockEntity.setOrgCode(orgAdministrativeInfoEntity.getCode());
			}else{
				//获取当前库存部门  外场或空运总调
				orgAdministrativeInfoEntity = this.getBigOrg(inOutStockEntity.getOrgCode());
				inOutStockEntity.setOrgCode(orgAdministrativeInfoEntity.getCode());
				currentOrgCode = orgAdministrativeInfoEntity.getCode();
			}
		}else{//部门不存在
			throw new StockException("库存部门不存在");
		}
		
		/**  根据入库类型 判断货区  */
		//偏线拉回
		if(StringUtils.equals(StockConstants.PARTIALLINE_RETURN_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//用户未点击确定 入库  
			if(!StringUtils.equals(StockConstants.CONFIRM, inStockConfirmFlag)){
				//判断该货件是否在本部门做过偏线外发
				if(!this.isPartialline(inOutStockEntity.getWaybillNO(), 
						inOutStockEntity.getSerialNO(), inOutStockEntity.getOrgCode())){
					throw new StockException(StockException.NO_PARTIALLINE_ERROR_CODE,"");
				}
			}
			//驻地派送部
			if(this.isStaionDelivery(inOutStockEntity,currentOrgCode)){
				//派送货区
				String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			}else{
				inOutStockEntity = setPlanTimeNextOrgCodeByPath(inOutStockEntity, orgAdministrativeInfoEntity, isStationSalesDepart, currentOrgCode);
			}
			
		//整车开单入库	
		}else if(StringUtils.equals(StockConstants.WHOLE_VEHICLE_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//设置的计划出发时间
			inOutStockEntity.setPlanStartTime(new Date());
		
		//整车到达入库,整车修改交接单,整车作废交接单	
		}else if(StringUtils.equals(StockConstants.WHOLE_VEHICLE_ARRIVAL_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ||
					StringUtils.equals(StockConstants.WHOLE_VEHICLE_MODIFY_HANDOVERBILL, inOutStockEntity.getInOutStockType()) ||
					StringUtils.equals(StockConstants.WHOLE_VEHICLE_CANCEL_HANDOVERBILL, inOutStockEntity.getInOutStockType())){
			//外场
			if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
				//驻地派送部
				if(isStationSalesDepart){
					//派送货区
					String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
					inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				}else{
					//整车货区
					inOutStockEntity.setGoodsAreaCode(StockConstants.WHOLE_VEHICLE_GOODS_AREA_CODE);
					//计划出发时间
					inOutStockEntity.setPlanStartTime(new Date());
					//下一部门编号
					inOutStockEntity.setNextOrgCode(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
				}
			//营业部
			}else{
				//计划出发时间
				inOutStockEntity.setPlanStartTime(new Date());
				//下一部门编号
				inOutStockEntity.setNextOrgCode(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
			}
			
		//异常签收	
		}else if(StringUtils.equals(StockConstants.EXCEPTION_SIGN_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//用户未点击确定 入库  
			if(!StringUtils.equals(StockConstants.CONFIRM, inStockConfirmFlag)){
				//判断该票是否做过自提
				boolean isArrive = arriveSheetManngerService.checkArriveSheetByWaybillNo(inOutStockEntity.getWaybillNO(), inOutStockEntity.getOrgCode());
				if(!isArrive){
					//抛出 该货件没有在本部门做过自提 异常
					throw new StockException(StockException.NO_ARRIVE_SHEET_ERROR_CODE,"");
				}
			}
			//是驻地营业派送部 入到当前外场的派送货区
			if(isStationSalesDepart){
				//派送货区
				String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			}
			//调用接送货接口 标记异常
			ExceptionOperateDto exceptionOperateDto = new ExceptionOperateDto();
			ExceptionEntity exceptionEntity = new ExceptionEntity();
			//运单号
			exceptionEntity.setWaybillNo(inOutStockEntity.getWaybillNO());
			//流水号
			exceptionEntity.setSerialNo(inOutStockEntity.getSerialNO());
			//异常签收时间
			exceptionEntity.setExceptionTime(new Date());
			//异常类型   货物异常
			exceptionEntity.setExceptionType(ExceptionProcessConstants.LABELEDGOOD_EXCEPTION);
			//异常环节   客户自提
			exceptionEntity.setExceptionLink(ExceptionProcessConstants.CUSTOMER_PICKUP);
			//异常状态   处理中
			exceptionEntity.setStatus(ExceptionProcessConstants.HANDLING);
			//工号
			exceptionEntity.setCreateUserCode(inOutStockEntity.getOperatorCode());
			//姓名
			exceptionEntity.setCreateUserName(inOutStockEntity.getOperatorName());
			//部门编号
			exceptionEntity.setCreateOrgCode(inOutStockEntity.getOrgCode());
			//部门名称
			exceptionEntity.setCreateOrgName(orgAdministrativeInfoEntity.getName());
			exceptionOperateDto.setExceptionEntity(exceptionEntity);
			//保存
			exceptionProcessService.operateExceptionProcessInfo(exceptionOperateDto);
		//派送拉回	
		}else if(StringUtils.equals(StockConstants.SEND_RETURN_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			inOutStockEntity.setUpdateTransportPathActionForInstoreFlag(true);
			//用户未点击确定 入库
			if(!StringUtils.equals(StockConstants.CONFIRM, inStockConfirmFlag)){
				//判断该票是否做过派送
				boolean isArrangedSend = deliverbillService.queryWaybillArrangedFlag(inOutStockEntity.getWaybillNO(), inOutStockEntity.getOrgCode());
				if(!isArrangedSend){
					//抛出 该货件没有在本部门做过派送 异常
					throw new StockException(StockException.NO_ARRANGE_SEND_ERROR_CODE,"");
				}
			}
			//驻地派送部
			if(this.isStaionDelivery(inOutStockEntity,currentOrgCode)){
				//派送货区
				String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			}else{
				inOutStockEntity = setPlanTimeNextOrgCodeByPath(inOutStockEntity, orgAdministrativeInfoEntity, isStationSalesDepart, currentOrgCode);
			}
		//反签收
		}else if(StringUtils.equals(StockConstants.REVERSE_SIGN_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//如果库存部门是外场则设置货区为派送货区
			if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
				//派送货区
				String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			//空运总调
			}else if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getAirDispatch())){
				//空运总调对应的外场
				String transferCode = queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
				//设置外场为库存部门
				inOutStockEntity.setOrgCode(transferCode);
				//空运货区
//				String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
//				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
//				//计划出发时间
//				inOutStockEntity.setPlanStartTime(new Date());
//				//设置下一部门
//				inOutStockEntity.setNextOrgCode(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
				inOutStockEntity = airNextOrgComm(inOutStockEntity,transferCode,currentOrgCode,FossConstants.YES);
			}else{
				//其它部门类型，不设置货区
			}
			
		//空运拉回	
		}else if(StringUtils.equals(StockConstants.AIR_FREIGHT_RETURN_SIGN_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){	
			//用户未点击确定 入库  
			if(!StringUtils.equals(StockConstants.CONFIRM, inStockConfirmFlag)){
				boolean isAirWaybill = false;
				if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
					OutfieldEntity outfieldEntity= outfieldService.queryOutfieldByOrgCode(orgAdministrativeInfoEntity.getCode());
					if(outfieldEntity != null && StringUtils.isNotBlank(outfieldEntity.getAirDispatchCode())){
						isAirWaybill = airWaybillService.queryWaybillNoExists(inOutStockEntity.getWaybillNO(), outfieldEntity.getAirDispatchCode());
					}
				}else{
					//判断该票是否做过派送
					isAirWaybill = airWaybillService.queryWaybillNoExists(inOutStockEntity.getWaybillNO(), inOutStockEntity.getOrgCode());
				}
				
				if(!isAirWaybill){
					throw new StockException(StockException.NO_AIR_WAYBILL_ERROR_CODE,"");
				}
			}
			//驻地派送部
			if(this.isStaionDelivery(inOutStockEntity,currentOrgCode)){
				//派送货区
				String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			}else{
				inOutStockEntity = setPlanTimeNextOrgCodeByPath(inOutStockEntity, orgAdministrativeInfoEntity, isStationSalesDepart, currentOrgCode);
			}
		//空运 落货入库 
		}else if(StringUtils.equals(StockConstants.AIR_UNSHIPPED_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){	
			//**********入库到相应外场空运货区**************
			//外场编号
			String transferCode = queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
			//设置外场为库存部门
			inOutStockEntity.setOrgCode(transferCode);
			//空运货区
//			String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
//			//设置货区
//			inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
//			//设置下一部门
//			inOutStockEntity.setNextOrgCode(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
			inOutStockEntity = airNextOrgComm(inOutStockEntity,transferCode,currentOrgCode,FossConstants.YES);
		//页面单件入库到特殊货区	
		}else if(StringUtils.equals(StockConstants.SPECIAL_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//设置计划出发时间和下一部门
			inOutStockEntity = this.setPlanStartTimeAndNextOrgCode(inOutStockEntity,confirmFlag);
			
		//无标签货物入库到异常货区	
		}else if(StringUtils.equals(StockConstants.NO_LABEL_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//异常货区
			String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
			inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
		
		//违禁品入库到驻地派送部货区	
		}else if(StringUtils.equals(StockConstants.CONTRABAND_STATION_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//派送货区
			String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
			inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			
		//入库包装货区  
		}else if(StringUtils.equals(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//包装货区
//			String goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
//			inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			inOutStockEntity.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
			inOutStockEntity = queryGoodsAreaCodeComm(inOutStockEntity,null);
			//设置计划出发时间和下一部门
			inOutStockEntity = this.setPlanStartTimeAndNextOrgCode(inOutStockEntity,confirmFlag);
			
			
		//入库贵重货区
		}else if(StringUtils.equals(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//贵重货区
//			String goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
//			inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			inOutStockEntity.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
			inOutStockEntity = queryGoodsAreaCodeComm(inOutStockEntity,null);
			//设置计划出发时间和下一部门
			inOutStockEntity = this.setPlanStartTimeAndNextOrgCode(inOutStockEntity,confirmFlag);
			
		//入库异常货区
		}else if(StringUtils.equals(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//异常货区
//			String goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
//			inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			inOutStockEntity.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
			inOutStockEntity = queryGoodsAreaCodeComm(inOutStockEntity,null);
			//设置计划出发时间和下一部门
			inOutStockEntity = this.setPlanStartTimeAndNextOrgCode(inOutStockEntity,confirmFlag);
		
		//货件完成包装后生成新的流水号入库包装货区
		}else if(StringUtils.equals(StockConstants.AFTER_PACKAGE_NEW_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//包装货区
//			String goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
//			inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			inOutStockEntity.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
			inOutStockEntity = queryGoodsAreaCodeComm(inOutStockEntity,null);
			//设置计划出发时间和下一部门
			inOutStockEntity = this.setPlanStartTimeAndNextOrgCode(inOutStockEntity,confirmFlag);
		//清仓多货入库
		}else if(StringUtils.equals(StockConstants.STOCKCHECKING_MORE_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			try{
				LOGGER.info("清仓多货入库: " + inOutStockEntity.getWaybillNO() + "---" + inOutStockEntity.getSerialNO() + 
								",路径部门：" + currentOrgCode + ",库存部门：" + inOutStockEntity.getOrgCode());
				inOutStockEntity = setPlanTimeNextOrgCodeByPath(inOutStockEntity, orgAdministrativeInfoEntity, isStationSalesDepart, currentOrgCode);
			}catch(Exception e){
				LOGGER.warn("清仓多货入库---根据走货路径设置下一部门、计划出发时间或更新走货路径状态异常---运单号：" + inOutStockEntity.getWaybillNO()
						+ " 流水号：" + inOutStockEntity.getSerialNO() + " 异常信息：" + ExceptionUtils.getFullStackTrace(e));
				//外场
				if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
					inOutStockEntity.setGoodsAreaCode(StockConstants.BULK_GOODS_AREA_CODE);
				//营业部	
				}else{
					//不做设置 直接入库
				}
			}
			//更新走货路径状态
			changeTransportPathStatus(inOutStockEntity,currentOrgCode);
		//散货入库	
		}else if(StringUtils.equals(StockConstants.BULK_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			LOGGER.info("散货入库: " + inOutStockEntity.getWaybillNO() + "---" + inOutStockEntity.getSerialNO() + 
					",路径部门：" + currentOrgCode + ",库存部门：" + inOutStockEntity.getOrgCode());
			inOutStockEntity = setPlanTimeNextOrgCodeByPath(inOutStockEntity, orgAdministrativeInfoEntity, isStationSalesDepart, currentOrgCode);
			
			//如果还是散货货区，需要抛出异常
			if(StringUtils.equals(StockConstants.BULK_GOODS_AREA_CODE, inOutStockEntity.getGoodsAreaCode())){
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(inOutStockEntity.getWaybillNO());
				if(waybillEntity == null){
					LOGGER.error("运单 " + inOutStockEntity.getWaybillNO() + " 不存在");
					//抛出 运单不存异常
					throw new StockException(StockException.WAYBILL_NOT_EXIST_ERROR_CODE,"");
				}
				
				//外场
				if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
					LOGGER.error("根据部门编号获取货区失败");
					throw new StockException(StockException.QUERY_GOODS_AREA_BY_NEXT_ORG_ERROR_CODE,new Object[]{" 部门编号:"+inOutStockEntity.getNextOrgCode()+" 运输类型："+waybillEntity.getProductCode()});
				//营业部	
				}else{
					//不做设置 直接入库
				}
			}
			
			//更新走货路径状态
			changeTransportPathStatus(inOutStockEntity,currentOrgCode);
		}else{
			inOutStockEntity.setUpdateTransportPathActionForInstoreFlag(true);
			//卸车少货找到
			if(StringUtils.equals(StockConstants.UNLOAD_LOSE_GOODS_FOUND_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
				UnloadDiffReportDetailEntity detailEntity = new UnloadDiffReportDetailEntity();
				//部门
				if(StringUtils.equals(inOutStockEntity.getUnloadSCType(), UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)){
					//部门
					detailEntity.setUnloadOrgCode(inOutStockEntity.getUnloadSCOrgCode());
				}else{
					detailEntity.setUnloadOrgCode(inOutStockEntity.getOrgCode());
				}
				//运单号
				detailEntity.setWaybillNo(inOutStockEntity.getWaybillNO());
				//流水号
				detailEntity.setSerialNo(inOutStockEntity.getSerialNO());
				//查询少货差异明细
				List<UnloadDiffReportDetailEntity> diffReportDetailList = unloadDiffReportService.queryUnresolvedLackGoodsException(detailEntity);
				if(!CollectionUtils.isNotEmpty(diffReportDetailList)){
					//差异明细里没有该货件、抛出异常
					throw new StockException(StockException.NO_UNLODA_DIFF, "");
				}else{
					//处理卸车少货差异
					UnloadDiffReportDetailEntity diffReportDetail = diffReportDetailList.get(0);
					//处理时间
					diffReportDetail.setExceptionHandleTime(new Date());
					//部门编号
					diffReportDetail.setHandleOrgCode(inOutStockEntity.getOrgCode());
					//部门名称
					diffReportDetail.setHandleOrgName(orgAdministrativeInfoEntity.getName());
					//处理人工号
					diffReportDetail.setHandlerCode(inOutStockEntity.getOperatorCode());
					//处理人名称
					diffReportDetail.setHandlerName(inOutStockEntity.getOperatorName());
					//备注
					diffReportDetail.setNote(inOutStockEntity.getNotes());
					//处理差错
					unloadDiffReportService.handleUnloadLackDiffReport(diffReportDetail,inOutStockEntity.getOperatorCode(),inOutStockEntity.getOperatorName(),inOutStockEntity.getOrgCode());
				}
			}
			//少货找到
			if(StringUtils.equals(StockConstants.LOSE_GOODS_FOUND_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
				boolean stDiffFlag = false;
				//*****************清仓少货*******************
				//查询清仓少货明细
				List<StDifferDetailEntity> stDiffDetailList = stReportService.queryStDifferDetailEntityList(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO(), inOutStockEntity.getOrgCode());
				if(!CollectionUtils.isNotEmpty(stDiffDetailList)){
					stDiffFlag = true;
				}else{
					StDifferDetailEntity stDiffDetail = stDiffDetailList.get(0);
					//处理时间
					stDiffDetail.setHandleTime(new Date());
					//处理状态
					stDiffDetail.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DONE);
					//处理人工号
					stDiffDetail.setUserCode(inOutStockEntity.getOperatorCode());
				    //更新处理结果
					stReportService.updateReportDetailList(stDiffDetail);
				}
				//************卸车少货****************
				UnloadDiffReportDetailEntity detailEntity = new UnloadDiffReportDetailEntity();
				//部门
				detailEntity.setUnloadOrgCode(inOutStockEntity.getOrgCode());
				//运单号
				detailEntity.setWaybillNo(inOutStockEntity.getWaybillNO());
				//流水号
				detailEntity.setSerialNo(inOutStockEntity.getSerialNO());
				//查询卸车少货明细
				List<UnloadDiffReportDetailEntity> diffReportDetailList = unloadDiffReportService.queryUnresolvedLackGoodsException(detailEntity);
				if(!CollectionUtils.isNotEmpty(diffReportDetailList)){
					//即没有清仓少货也没有卸车少货
					if(!StringUtils.equals(StockConstants.CONFIRM, inStockConfirmFlag)){
						if(stDiffFlag){
							//差异明细里没有该货件、抛出异常
							throw new StockException(StockException.NO_DIFF, "");
						}
					}
				}else{
					//处理卸车少货差错
					UnloadDiffReportDetailEntity diffReportDetail = diffReportDetailList.get(0);
					//处理时间
					diffReportDetail.setExceptionHandleTime(new Date());
					//处理部门编号
					diffReportDetail.setHandleOrgCode(inOutStockEntity.getOrgCode());
					//处理部门名称
					diffReportDetail.setHandleOrgName(orgAdministrativeInfoEntity.getName());
					//处理人工号
					diffReportDetail.setHandlerCode(inOutStockEntity.getOperatorCode());
					//处理人姓名
					diffReportDetail.setHandlerName(inOutStockEntity.getOperatorName());
					//备注
					diffReportDetail.setNote(inOutStockEntity.getNotes());
					//处理差错
					unloadDiffReportService.handleUnloadLackDiffReport(diffReportDetail,inOutStockEntity.getOperatorCode(),inOutStockEntity.getOperatorName(),inOutStockEntity.getOrgCode());
					
				}
				//驻地派送部
				if(this.isStaionDelivery(inOutStockEntity,currentOrgCode)){
					//派送货区
					String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
					inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
					return inOutStockEntity;
				}
			}
			
			//查询运单
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(inOutStockEntity.getWaybillNO());
			if(waybillEntity == null){
				LOGGER.error("运单 " + inOutStockEntity.getWaybillNO() + " 不存在");
				//抛出 运单不存异常
				//throw new StockException(StockException.WAYBILL_NOT_EXIST_ERROR_CODE,"");
				//BUG-53100 mod by songjie
				throw new StockException("运单 " + inOutStockEntity.getWaybillNO() + " 不存在","");
			}else{
				//部门是外场类型
				if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
					
					//空运 
					if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, waybillEntity.getProductCode())){
						//空运货区
//						String goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
//						inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
//						inOutStockEntity.setNextOrgCode(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
//						//计划出发时间
//						inOutStockEntity.setPlanStartTime(new Date());
						inOutStockEntity = airNextOrgComm(inOutStockEntity,inOutStockEntity.getOrgCode(),currentOrgCode,FossConstants.YES);
					}else{
//						this.setStockInfoByPath(inOutStockEntity, currentOrgCode, isStationSalesDepart, waybillEntity, confirmFlag);
						inOutStockEntity = setPlanTimeNextOrgCodeByPath(inOutStockEntity, orgAdministrativeInfoEntity, isStationSalesDepart, currentOrgCode);
					}
					
				}else{//非外场类型部门
					//空运 
					if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, waybillEntity.getProductCode())){
//						inOutStockEntity.setNextOrgCode(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
//						//计划出发时间
//						inOutStockEntity.setPlanStartTime(new Date());
						inOutStockEntity = airNextOrgComm(inOutStockEntity,null,null,FossConstants.NO);
					}else{
						//设置下一部门、计划出发时间
						inOutStockEntity = this.setPlanStartTimeAndNextOrgCode(inOutStockEntity, confirmFlag);
						//inOutStockEntity = setPlanTimeNextOrgCodeByPath(inOutStockEntity, orgAdministrativeInfoEntity, isStationSalesDepart, currentOrgCode);
					}					
					
					//部门是空运总调，入库到相应外场空运货区
					if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getAirDispatch())){
						//外场编号
						String transferCode = queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
						//设置库存部门
						inOutStockEntity.setOrgCode(transferCode);
						//空运货区
//						String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
//						//设置货区
//						inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
//						inOutStockEntity.setNextOrgCode(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
//						//计划出发时间
//						inOutStockEntity.setPlanStartTime(new Date());
						inOutStockEntity = airNextOrgComm(inOutStockEntity,null,null,FossConstants.NO);
					}
				}
			}
			
		}
		
	return 	inOutStockEntity;
}
	/**
	 * 根据走货路径设置计划出发时间、下一部门。
	 * 当路径不存在时新增走货路径
	 * 当部门不在路径节点上时修改走货路径
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-9 下午3:22:44
	 */
	private InOutStockEntity setPlanTimeNextOrgCodeByPath(InOutStockEntity inOutStockEntity, 
											               OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
														   boolean isStationSalesDepart,
														   String currentOrgCode){
		//外场
		if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
			//获取运单信息
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(inOutStockEntity.getWaybillNO());
			//*************判断是否是整车  如果是整车就不处理走货路径**************
			if(waybillEntity == null){
				LOGGER.error("运单 " + inOutStockEntity.getWaybillNO() + " 不存在");
				//抛出 运单不存异常
				throw new StockException(StockException.WAYBILL_NOT_EXIST_ERROR_CODE,"");
			}
			//整车
			if(StringUtils.equals(FossConstants.YES, waybillEntity.getIsWholeVehicle())){
				//驻地派送部
				if(isStationSalesDepart){
					//派送货区
					String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
					inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				}else{
					//整车货区
					inOutStockEntity.setGoodsAreaCode(StockConstants.WHOLE_VEHICLE_GOODS_AREA_CODE);
					//计划出发时间
					inOutStockEntity.setPlanStartTime(new Date());
					//下一部门编号
					inOutStockEntity.setNextOrgCode(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
				}
			//空运	
			}else if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, waybillEntity.getProductCode())){
				//空运货区
//				String goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
//				//设置货区
//				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
//				//下一部门编号
//				inOutStockEntity.setNextOrgCode(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
//				//计划出发时间
//				inOutStockEntity.setPlanStartTime(new Date());
				String transferCode = null;
				if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getAirDispatch())){
					 transferCode = queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
				}else{
					transferCode = inOutStockEntity.getOrgCode();
				}
				inOutStockEntity = airNextOrgComm(inOutStockEntity,transferCode,currentOrgCode,FossConstants.YES);
			}else{
				try{
					inOutStockEntity = goodsWalkPath(inOutStockEntity,currentOrgCode,StockConstants.CONFIRM,FossConstants.YES);
				}catch (Exception e) {
					//散货货区
					inOutStockEntity.setGoodsAreaAdd(null);
				}
				
				//驻地派送部
				if(isStationSalesDepart){
					//派送货区
					inOutStockEntity.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				}
				
				inOutStockEntity = queryGoodsAreaCodeComm(inOutStockEntity,waybillEntity);
				
			}
		//非外场部门	即营业部
		}else{
				//*************判断是否是整车  如果是整车就不处理走货路径**************
				//获取运单信息
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(inOutStockEntity.getWaybillNO());
				if(waybillEntity == null){
					LOGGER.error("运单 " + inOutStockEntity.getWaybillNO() + " 不存在");
					//抛出 运单不存异常
					throw new StockException(StockException.WAYBILL_NOT_EXIST_ERROR_CODE,"");
				}
				//整车
				if(StringUtils.equals(FossConstants.YES, waybillEntity.getIsWholeVehicle())){
					//计划出发时间
					inOutStockEntity.setPlanStartTime(new Date());
					//下一部门编号
					inOutStockEntity.setNextOrgCode(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
				//空运	
				}else if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, waybillEntity.getProductCode())){
//					//下一部门编号
//					inOutStockEntity.setNextOrgCode(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
//					//计划出发时间
//					inOutStockEntity.setPlanStartTime(new Date());
					inOutStockEntity = airNextOrgComm(inOutStockEntity,null,null,FossConstants.NO);
				}else{
					inOutStockEntity = goodsWalkPath(inOutStockEntity,currentOrgCode,StockConstants.CONFIRM,FossConstants.NO);
				}
		}
		
		return inOutStockEntity;
	}
	/**
	 * 生成或修改走货路径后，查询新的路径并设置计划出发时间和下一部门
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-10 上午9:04:45
	 */
	private InOutStockEntity setPlanTimeNextOrgCodeAfterChangePath(InOutStockEntity inOutStockEntity, String currentOrgCode,String checkGoodsArea){
		
		//获取修改后的新走货路径
		FeedbackDto newFeedbackDto = this.queryPathDetail(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO(), currentOrgCode);
		
		//本部门在修改后走货路径中
		if(TransportPathConstants.STATUS_RIGHT == newFeedbackDto.getResult()){
			PathDetailEntity newPathDetail = newFeedbackDto.getPathDetailEntity();
			
			//获取下一部门code
			String nextOrgCode = newPathDetail.getObjectiveOrgCode();
			inOutStockEntity.setNextOrgCode(nextOrgCode);
			
			//设置计划出发时间
			inOutStockEntity.setPlanStartTime(newPathDetail.getPlanStartTime());
			
			//需要查询货区并设置货区的开关
			if(StringUtils.equals(FossConstants.YES, checkGoodsArea)){
				//货区的查询类型
				inOutStockEntity.setGoodsAreaAdd("ProductCode");
			}
		
		//本部门不在修改后的走货路径中 设置散货货区
		}else if(TransportPathConstants.STATUS_WRONG == newFeedbackDto.getResult()){
//			inOutStockEntity.setGoodsAreaCode(StockConstants.BULK_GOODS_AREA_CODE);
//			LOGGER.warn("本部门不在修改后的走货路径中");
			//需要查询货区并设置货区的开关
			if(StringUtils.equals(FossConstants.YES, checkGoodsArea)){
				//货区的查询类型
				inOutStockEntity.setGoodsAreaAdd(null);
			}
		//本部门是修改后路径中最后部门没有下一部门
		}else{
//			//派送货区
//			String goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
//			inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			//货区的查询类型
			if(StringUtils.equals(FossConstants.YES, checkGoodsArea)){
				//货区的查询类型
				inOutStockEntity.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				return inOutStockEntity;
			}
		}
		
		return inOutStockEntity;
	}
	
	/**
	 * 校验货件库存唯一性，并入库
	 * @author 097457-foss-wangqiang
	 * @date 2013-2-1 下午4:19:05
	 */
	@Transactional
	private int inStock(InOutStockEntity inOutStockEntity) {
		
		List<StockEntity> stockList = stockDao.queryUniqueStock(inOutStockEntity);
		if (CollectionUtils.isNotEmpty(stockList)) {// 该货件存在库存中
			//获取该货件库存记录
			StockEntity stockOriginal = stockList.get(0);
			// 在本部门库存中
			if (StringUtils.equals(stockOriginal.getOrgCode(),inOutStockEntity.getOrgCode())){
				
				// 比较扫描时间
				if (inOutStockEntity.getScanTime().after(stockOriginal.getScanTime())){// 当前请求的扫描时间晚于库中的扫描时间
					// 封装该货件本部门库存记录的出库动作
					InOutStockEntity outStock = new InOutStockEntity();
					//将该货件在当前库存中的货区编号和部门编号设置给出库动作对象 
					outStock.setGoodsAreaCode(stockOriginal.getGoodsAreaCode());
					outStock.setOrgCode(stockOriginal.getOrgCode());
					//*************将请求参数中的信息设置给出库动作对象*************
					//运单号
					outStock.setWaybillNO(inOutStockEntity.getWaybillNO());
					//流水号
					outStock.setSerialNO(inOutStockEntity.getSerialNO());
					//设备类型
					outStock.setDeviceType(inOutStockEntity.getDeviceType());
					//入库类型
					outStock.setInOutStockType(inOutStockEntity.getInOutStockType());
					//操作人工号
					outStock.setOperatorCode(inOutStockEntity.getOperatorCode());
					//操作人姓名
					outStock.setOperatorName(inOutStockEntity.getOperatorName());
					//扫描时间
					outStock.setScanTime(inOutStockEntity.getScanTime());
					//从本部门原库存中出库
					this.deleteStock(outStock);
					// 入库本部门新库存
					this.addStock(inOutStockEntity);
				} else {// 当前请求的扫描时间早于库中的扫描时间
					inOutStockEntity.setInOutStockTime(new Date());
					//设置入库动作无效
					inOutStockEntity.setIsValid(FossConstants.INACTIVE);
					//保存入库动作
					inOutStockDao.addInStock(inOutStockEntity);
				}
			} else {// 在其它部门库存中
				
				// 封装该货件库存记录的出库动作
				InOutStockEntity outStock = new InOutStockEntity();
				//将该货件在当前库存中的货区编号和部门编号设置给出库动作对象 
				outStock.setGoodsAreaCode(stockOriginal.getGoodsAreaCode());
				outStock.setOrgCode(stockOriginal.getOrgCode());
				//***********将请求参数中的信息设置给出库动作对象****************
				//运单号
				outStock.setWaybillNO(inOutStockEntity.getWaybillNO());
				//流水号
				outStock.setSerialNO(inOutStockEntity.getSerialNO());
				//设备类型
				outStock.setDeviceType(inOutStockEntity.getDeviceType());
				//入库类型
				outStock.setInOutStockType(inOutStockEntity.getInOutStockType());
				//操作人工号
				outStock.setOperatorCode(inOutStockEntity.getOperatorCode());
				//操作人姓名
				outStock.setOperatorName(inOutStockEntity.getOperatorName());
				//扫描时间
				outStock.setScanTime(inOutStockEntity.getScanTime());
				// 从上一部门出库
				this.deleteStock(outStock);
				// 入库本部门库存
				this.addStock(inOutStockEntity);
				//发送出库通知消息
				this.sentMsg(inOutStockEntity, stockOriginal.getOrgCode());
				
			}
		} else {// 库存中没有该货件
			//入库
			this.addStock(inOutStockEntity);
		}
		return FossConstants.SUCCESS;
	}
	
	
	/**
	 * 卸车入库 
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-31 上午9:49:05
	 */
	@Override
	@Transactional
	public int inStockUnload(InOutStockEntity inOutStockEntity) throws StockException{
		/**
		 * 查询运单状态 如果为 已作废 则不能入库
		 */
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(inOutStockEntity.getWaybillNO());
		if(actualFreightEntity!=null){
			if(StringUtils.equals(WaybillConstants.OBSOLETE, actualFreightEntity.getStatus()) || StringUtils.equals(WaybillConstants.ABORTED, actualFreightEntity.getStatus())){
				return FossConstants.FAILURE;
			}
		}
		
		//断货物是否已签收的接口，参数（运单号、流水号）
		String checkSerialNoIsSign = signDetailService.querySerialNoIsSign(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO());
		//已签收出库 
		if(StringUtils.equals(FossConstants.YES, checkSerialNoIsSign)){
			return FossConstants.FAILURE;
		}
		
		/**
		 * 根据运单和流水号 检验流水号是否有效
		 */
		//无标签或者异常货
		if(StringUtils.equals(StockConstants.NO_LABEL_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) || StringUtils.equals(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ){
			//不需要检验运单号和流水号。
		}else{
			String checkStautsByWaybillNoAndSerialNo = labeledGoodService.getStautsByWaybillNoAndSerialNo(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO());
			if(StringUtils.equals(FossConstants.YES, checkStautsByWaybillNoAndSerialNo)){
				//流水号有效 继续
			}else{
				//流水号无效
				//没有此流水号
				return FossConstants.FAILURE;
			}
		}
		//设置操作出库设备类型
		inOutStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
		//设置当前调用时间为扫描时间
		inOutStockEntity.setScanTime(new Date());
		
		//操作人信息
		if(StringUtils.isBlank(inOutStockEntity.getOperatorName())){
			//操作人姓名
			String userName = this.getUserNameByCode(inOutStockEntity.getOperatorCode());
			inOutStockEntity.setOperatorName(userName);
		}
		//获取库存部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = this.verifyStockOrg(inOutStockEntity.getOrgCode());
		inOutStockEntity.setOrgCode(orgAdministrativeInfoEntity.getCode());
		//获取运单信息
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(inOutStockEntity.getWaybillNO());
		
		//返回获取的交接单实体 by linhua.yan 360903
		HandOverBillEntity billEntity = handOverBillService.queryHandOverBillByNo(inOutStockEntity.getInOutStockBillNO());
		
		String productCode;
		if(waybillEntity != null){
			//产品编号
			productCode = waybillEntity.getProductCode();
		}else{
			LOGGER.error("运单 " + inOutStockEntity.getWaybillNO() + " 不存在");
			//抛出 运单不存异常
			throw new StockException(StockException.WAYBILL_NOT_EXIST_ERROR_CODE,"");
		}
		
		//根据走货路径设置下一部门、计划出发时间、货区
		//部门是外场类型
		if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
			//整车
			if(StringUtils.equals(FossConstants.YES, waybillEntity.getIsWholeVehicle())){
				//整车货区
				inOutStockEntity.setGoodsAreaCode(StockConstants.WHOLE_VEHICLE_GOODS_AREA_CODE);
				//计划出发时间
				inOutStockEntity.setPlanStartTime(new Date());
				//下一部门编号
				inOutStockEntity.setNextOrgCode(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
			//空运	
			}else if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, waybillEntity.getProductCode())){
//				//空运货区
//				String goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
//				//设置货区
//				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
//				//下一部门编号
//				inOutStockEntity.setNextOrgCode(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
//				//计划出发时间
//				inOutStockEntity.setPlanStartTime(new Date());
				//外场编号
				inOutStockEntity = airNextOrgComm(inOutStockEntity,inOutStockEntity.getOrgCode(),inOutStockEntity.getOrgCode(),FossConstants.YES);
			}else{
				try{
					inOutStockEntity = goodsWalkPath(inOutStockEntity,inOutStockEntity.getOrgCode(),StockConstants.CONFIRM,FossConstants.YES);
				}catch (Exception e) {
					//散货货区
					inOutStockEntity.setGoodsAreaAdd(null);
				}
				inOutStockEntity = queryGoodsAreaCodeComm(inOutStockEntity,waybillEntity);
			}
		//部门是空运总调，入库到相应外场空运货区
		}else if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getAirDispatch())){
			//外场编号
			String transferCode = queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
			inOutStockEntity.setOrgCode(transferCode);
//			//计划出发时间
//			inOutStockEntity.setPlanStartTime(new Date());
//			//下一部门编号
//			inOutStockEntity.setNextOrgCode(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
			
//			try{
//				//空运货区
//				String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
//				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
//			}catch(BusinessException e){
//				LOGGER.warn("卸车入库--部门：" + inOutStockEntity.getOrgCode()+ " 货物：" + inOutStockEntity.getWaybillNO() + "---" + inOutStockEntity.getSerialNO() +
//						"，查询走货路径或设置货区失败，入库到虚拟散货货区，异常信息：" + ExceptionUtils.getFullStackTrace(e));
//				//获取货区失败，设置散货虚拟货区
//				inOutStockEntity.setGoodsAreaCode(StockConstants.BULK_GOODS_AREA_CODE);
//			}
			inOutStockEntity = airNextOrgComm(inOutStockEntity,transferCode,inOutStockEntity.getOrgCode(),FossConstants.YES);
			
		}else{//入库部门为营业部
			//胡岳改动 判断是否快递货   
			if((StringUtils.equals(productCode,WaybillConstants.PACKAGE)||
					StringUtils.equals(productCode,"RCP")
					||StringUtils.equals(productCode,"EPEP")
					||StringUtils.equals(productCode,"DEAP"))){
				//快递新规则 有可能卸车入开单营业部 所以要找下一部门和计划出发时间
				//设置下一部门、计划出发时间
				inOutStockEntity = this.setPlanStartTimeAndNextOrgCode(inOutStockEntity, StockConstants.CONFIRM);
			}else{
				//零担原有规则
				//营业部 无需设置货区，如果卸车部门是营业部则是最终到达部门 所以也无需设置 计划出发时间、下一部门
			}
			//整车
			if(StringUtils.equals(FossConstants.YES, waybillEntity.getIsWholeVehicle())){
				//计划出发时间
				inOutStockEntity.setPlanStartTime(new Date());
				//下一部门编号
				inOutStockEntity.setNextOrgCode(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
			//空运	
			}else if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, waybillEntity.getProductCode())){
//				//下一部门编号
//				inOutStockEntity.setNextOrgCode(nextOrgCode);
//				//计划出发时间
//				inOutStockEntity.setPlanStartTime(planStartTime);
				
				inOutStockEntity = airNextOrgComm(inOutStockEntity,null,null,FossConstants.NO);
			}
			//合伙人入库虚拟库存 2016-09-29 alfred
			if(!StringUtils.equals(orgAdministrativeInfoEntity.getCode(), waybillEntity.getCustomerPickupOrgCode())
					&& billEntity != null){
				//if(billEntity==null){
					//LOGGER.error("交接单 " + inOutStockEntity.getWaybillNO() + " 不存在");
					////抛出 交接单不存在异常
					//throw new StockException("交接单号不存在","");
				//}
				//出发部门
				SaleDepartmentEntity departDeptEntity = saleDepartmentService.
						querySaleDepartmentInfoByCode(billEntity.getDepartDeptCode());
				//当前部门
				SaleDepartmentEntity arrivalDeptEntity = saleDepartmentService.
						querySaleDepartmentInfoByCode(orgAdministrativeInfoEntity.getCode());
				List<DeptTransferMappingEntity> deptTransferMappinglist  = deptTransferMappingService.
						queryDeptTransferMappingListByCode(waybillEntity.getCustomerPickupOrgCode());
				//出发部门映射集
				List<DeptTransferMappingEntity> deptTrans = deptTransferMappingService.
						queryDeptTransferMappingListByCode(billEntity.getDepartDeptCode());
				boolean inStockSalefalg =  isPTPStock(orgAdministrativeInfoEntity.getCode(), waybillEntity.getCustomerPickupOrgCode(),billEntity.getHandOverType());
				if(inStockSalefalg){
					if(null!=departDeptEntity
							&& CollectionUtils.isNotEmpty(deptTrans)){
						//二级到一级
						if(StringUtils.equals(departDeptEntity.getIsTwoLevelNetwork(), FossConstants.YES)){
							inOutStockEntity.setNextOrgCode(deptTrans.get(0).getDeptCode());
						}//营业部到一级
						else if(StringUtils.equals(departDeptEntity.getIsLeagueSaleDept(), FossConstants.NO)){
							inOutStockEntity.setNextOrgCode(waybillEntity.getCustomerPickupOrgCode());
						}//一级到营业部
						else if(StringUtils.equals(departDeptEntity.getIsLeagueSaleDept(), FossConstants.YES)&&
								StringUtils.equals(departDeptEntity.getIsTwoLevelNetwork(), FossConstants.NO)){
							inOutStockEntity.setNextOrgCode(lineService.
									queryTransferCodeListBySourceCode(orgAdministrativeInfoEntity.getCode()).get(0));
						}
					}else if(arrivalDeptEntity != null){
						//外场到营业部
						if(StringUtils.equals(arrivalDeptEntity.getIsLeagueSaleDept(), FossConstants.NO)){
							inOutStockEntity.setNextOrgCode(deptTransferMappinglist.get(0).getFthNetworkCode());
						}else{//外场到一级
							inOutStockEntity.setNextOrgCode(waybillEntity.getCustomerPickupOrgCode());
						}
					}
					inOutStockEntity.setGoodsAreaCode(StockConstants.VIRTUAL_GOODS_AREA_CODE);
					inOutStockEntity.setGoodsAreaAdd(StockConstants.VIRTUAL_GOODS_AREA_CODE);
					this.inStockSale(inOutStockEntity);
					return FossConstants.SUCCESS;
			}
		}
		}
		
		/**
		 * denghanchao  7天返货 
		 */
		//根据运单号查询返货类型
		String returntype = waybillManagerService.selectReturnType(inOutStockEntity.getWaybillNO());
		//如果返货类型为7天返货则入异常货区不往下执行跳出该方法
		if(StockConstants.SEVEN_DAYS_RETURN.equals(returntype)){
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(inOutStockEntity.getOrgCode());
			if(saleDepartmentEntity!=null){
				inOutStockEntity.setGoodsAreaCode(StockConstants.VIRTUAL_GOODS_AREA_CODE);
				inOutStockEntity.setGoodsAreaAdd(StockConstants.VIRTUAL_GOODS_AREA_CODE);
			}else{
				//入库异常货物
				String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				inOutStockEntity.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
			}
		}
		
		//入库
		this.inStock(inOutStockEntity);
		
		//移动代办事项
		waybillRfcService.queryTodoWhenDumpTruck(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO(), 
						inOutStockEntity.getOrgCode(), null);
		
		/**
		 * 更新走货路径的状态为入库。如果异常只记录日志 begin
		 */
		//updateTransportPathActionForInstoreComm(inOutStockEntity,orgAdministrativeInfoEntity);
		/**
		 * 更新走货路径的状态为入库。如果异常只记录日志 end
		 */
		
		return FossConstants.SUCCESS;
		
	}
	
	
	
	/**
	 * 判断该货件是否在本部门做过外发
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @param orgCode 部门编号
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-12 下午6:30:29
	 */
	private boolean isPartialline(String waybillNO, String serialNO, String orgCode){
		List<String> handoverTypeList = handOverBillService.queryHandoverType(waybillNO, 
				serialNO, orgCode);
		boolean flag = false;
		if(CollectionUtils.isNotEmpty(handoverTypeList)){
			for(String handoverType : handoverTypeList){
				//偏线外发
				if(StringUtils.endsWith(LoadConstants.HANDOVER_TYPE_PARTIALLINE,handoverType)){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 根据当前部门和下一部门得到该外场相应货区编号   
	 * 查询失败抛出异常
	 * @param orgCode 部门
	 * @param nextOrgCode 下一部门
	 * @param waybillNo 运单
	 * @return 获取货区失败时 抛出异常
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-30 下午5:02:39
	 */
	private String getGoodsAreaCodeByNextOrgCode(String orgCode, String nextOrgCode, String waybillNo) throws StockException{
		//判断下一部门是否是外发网点
		OuterBranchEntity outerBranchEntity = vehicleAgencyDeptService.queryOuterBranchByBranchCode(nextOrgCode, DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
		//判断下一部门是否是当前外场的驻地派送部
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(nextOrgCode);
		String goodsAreaCode;
		String errorMsgStr="";
		InOutStockEntity inOutStockEntityPojo = new InOutStockEntity();
		inOutStockEntityPojo.setWaybillNO(waybillNo);
		inOutStockEntityPojo.setNextOrgCode(nextOrgCode);
		inOutStockEntityPojo.setOrgCode(orgCode);
		if(outerBranchEntity != null){
			//偏线货区
			inOutStockEntityPojo.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER);
			inOutStockEntityPojo = queryGoodsAreaCodeComm(inOutStockEntityPojo,null);
//			goodsAreaCode = this.queryGoodsAreaByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER);
			errorMsgStr = " 货区类型:偏线货区";
		}else if(saleDepartmentEntity != null && StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation()) 
					&& StringUtils.equals(saleDepartmentEntity.getTransferCenter(), orgCode) ){
			//派送货区
			inOutStockEntityPojo.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
			inOutStockEntityPojo = queryGoodsAreaCodeComm(inOutStockEntityPojo,null);
//			goodsAreaCode = this.queryGoodsAreaByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
			errorMsgStr = " 货区类型:派送货区";
		}else{
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			if(waybillEntity != null){
				//查询货区
				//goodsAreaCode = goodsAreaService.queryCodeByArriveRegionCode(orgCode, nextOrgCode, waybillEntity.getProductCode());
				inOutStockEntityPojo.setGoodsAreaAdd("ProductCode");
				inOutStockEntityPojo = queryGoodsAreaCodeComm(inOutStockEntityPojo,waybillEntity);
			}else{
				LOGGER.error("运单 " + waybillNo + " 不存在");
				throw new StockException(StockException.WAYBILL_NOT_EXIST_ERROR_CODE,"");
			}
		}
		goodsAreaCode = inOutStockEntityPojo.getGoodsAreaCode();
		
		//根据下一部门获取货区失败
		if(StringUtils.isBlank(goodsAreaCode)){
			LOGGER.error("根据下一部门获取货区失败");
			throw new StockException(StockException.QUERY_GOODS_AREA_BY_NEXT_ORG_ERROR_CODE,new Object[]{" 下一部门编号:"+nextOrgCode +errorMsgStr});
		}
		return goodsAreaCode;
	}
	/**
	 * 根据当前部门、下一部门、产品类型查询货区
	 * @param orgCode 部门
	 * @param nextOrgCode 下一部门
	 * @param productCode 产品编号
	 * @author 097457-foss-wangqiang
	 * @date 2013-2-2 上午10:51:17
	 */
	private String queryGoodsAreaCode(WaybillEntity waybillEntity,String orgCode, String nextOrgCode, String productCode) throws StockException{
		//判断下一部门是否是外发网点
		OuterBranchEntity outerBranchEntity = vehicleAgencyDeptService.queryOuterBranchByBranchCode(nextOrgCode, DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
		//判断下一部门是否是当前外场的驻地派送部
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(nextOrgCode);
		String goodsAreaCode;
		String errorMsgStr="";
		if(outerBranchEntity != null){
			//偏线货区
			goodsAreaCode = this.queryGoodsAreaByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER);
			errorMsgStr = " 货区类型:偏线货区";
		}else if(saleDepartmentEntity != null && StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation()) 
					&& StringUtils.equals(saleDepartmentEntity.getTransferCenter(), orgCode) ){
			if(isExpress(waybillEntity)){
				//BSE_GOODSAREA_TYPE_EXPRESS_STATION   快递驻地库区 对应的code
				goodsAreaCode = this.queryGoodsAreaByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS_STATION);
			}else{
				//派送货区
				goodsAreaCode = this.queryGoodsAreaByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
			}
			errorMsgStr = " 货区类型:派送货区";
		}else{
			//查询货区
			goodsAreaCode = goodsAreaService.queryCodeByArriveRegionCode(orgCode, nextOrgCode, productCode);
		}
		
		//根据下一部门获取货区失败
		if(StringUtils.isBlank(goodsAreaCode)){
			LOGGER.error("根据下一部门获取货区失败");
			throw new StockException(StockException.QUERY_GOODS_AREA_BY_NEXT_ORG_ERROR_CODE,new Object[]{"  下一部门编号:"+nextOrgCode+errorMsgStr});
		}
		return goodsAreaCode;
	}
	
	/**
	 * 根据走货路径获取计划出发时间 (调用走货路径接口) 
	 * @param  inOutStockEntity 货件信息
	 * @param  confirmFlag 标识是否页面调用
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-30 下午5:03:45
	 */
	/**
	* @param inOutStockEntity
	* @param confirmFlag
	* @return
	* @description 整理走货路径接口，统一调用同一接口goodsWalkPath  (设置计划出发时间和下一部门)
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-11 下午3:57:54
	*/
	private InOutStockEntity setPlanStartTimeAndNextOrgCode(InOutStockEntity inOutStockEntity, String confirmFlag){
		inOutStockEntity = goodsWalkPath(inOutStockEntity,inOutStockEntity.getOrgCode(),confirmFlag,FossConstants.NO);
		return inOutStockEntity;
	}
	
	/**
	 * 非合车调整走货路径，修改走货路径
	 * @param inOutStockEntity.getWaybillNO() 运单
	 * @param inOutStockEntity.getSerialNO() 流水号
	 * @param inOutStockEntity.getGoodsAreaCode() 货区编号
	 * @param inOutStockEntity.getOrgCode() 部门编号
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-4 上午9:40:26
	 */
	private void notJoinCarChangePath(InOutStockEntity inOutStockEntity){
		//try{
			//修改走货路径
			calculateTransportPathService.notJoinCarModify(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO(), 
					inOutStockEntity.getGoodsAreaCode(), new Date(), inOutStockEntity.getOrgCode());
		//}catch(TfrBusinessException e){
		//	LOGGER.error("非合车调整走货路径,修改走货路径失败", e);
		//	throw new StockException(StockException.CHANGE_TRANSPORT_PATH_FAILURE_ERROR_CODE,"");
		//}
	}
	
	/**
	 * 查询走货路径 (调用走货路径接口)
	 * @param waybillNo 运单
	 * @param serialNo 流水号
	 * @param currentOrgCode 当前部门编号
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-30 下午3:19:46
	 */
	private FeedbackDto queryPathDetail(String waybillNo, String serialNo, String currentOrgCode){
		//try{
			//查询走货路径
//			FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTime(waybillNo, 
//					serialNo, currentOrgCode);

			FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTimeForRWSplitting(waybillNo, serialNo, currentOrgCode);
			return feedbackDto;
		//}catch(TfrBusinessException e){
			//查询走货路径失败 抛出异常
		//	LOGGER.error("查询走货路径失败", e);
		//	throw new StockException(StockException.QUERY_TRANSPORT_PATH_FAILURE_ERROR_CODE,"");
		//}
	}
	/**
	 * 校验非合车调整
	 * @param inOutStockEntity.getOrgCode() 部门
	 * @param inOutStockEntity.getGoodsAreaCode() 货区
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-30 下午2:47:31
	 */
	private InOutStockEntity verifyChangePath(InOutStockEntity inOutStockEntity){
		
		//查询非合车调整
		ChangePathEntity changePath = changePathDao.queryModifyChangePath(inOutStockEntity.getOrgCode(), inOutStockEntity.getGoodsAreaCode());
		//有非合车调整
		if(changePath != null){
			//修改走货路径     
			this.notJoinCarChangePath(inOutStockEntity);
			try{
			//根据路径调整信息中的下一部门获取相应的新货区
			String newGoodsAreaCode = this.getGoodsAreaCodeByNextOrgCode(inOutStockEntity.getOrgCode(), changePath.getDestOrgCode(), inOutStockEntity.getWaybillNO());
			inOutStockEntity.setGoodsAreaCode(newGoodsAreaCode);
			//货区的查询类型
			inOutStockEntity.setGoodsAreaAdd("ProductCode_WaybillNO");
			}catch (Exception e) {
				LOGGER.error("根据下一部门获取货区失败");
				throw new StockException(StockException.QUERY_GOODS_AREA_BY_NEXT_ORG_ERROR_CODE,new Object[]{" 下一部门编号:"+inOutStockEntity.getNextOrgCode()});
			}
		}else{
			//无非合车调整
		}
		return inOutStockEntity;
	}
	/**
	 * 校验是否有合车调整，有则保存合车记录到合车表
	 * @param inOutStockEntity.getOrgCode() 部门
	 * @param inOutStockEntity.getGoodsAreaCode() 货区
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-4 上午9:57:38
	 */
	private void verifyJoinCarChangePath(InOutStockEntity inOutStockEntity){
		//查询合车调整记录
		List<ChangePathEntity> changePathList = changePathDao.queryTogetherTruckChangePath(inOutStockEntity.getOrgCode(), inOutStockEntity.getGoodsAreaCode());
		if(CollectionUtils.isNotEmpty(changePathList)){
			for(ChangePathEntity togetherChangePath : changePathList){
				//根据路径调整信息中的下一部门获取相应的新货区
				String newGoodsAreaCode = this.getGoodsAreaCodeByNextOrgCode(inOutStockEntity.getOrgCode(), togetherChangePath.getDestOrgCode(), inOutStockEntity.getWaybillNO());
				//封装合车信息
				TogetherTruckStockEntity togetherTruckStock = new TogetherTruckStockEntity();
				//新货区
				togetherTruckStock.setNewGoodsAreaCode(newGoodsAreaCode);
				//原货区
				togetherTruckStock.setOrigGoodsAreaCode(inOutStockEntity.getGoodsAreaCode());
				//部门
				togetherTruckStock.setOrgCode(inOutStockEntity.getOrgCode());
				//运单号
				togetherTruckStock.setWaybillNO(inOutStockEntity.getWaybillNO());
				//流水号
				togetherTruckStock.setSerialNO(inOutStockEntity.getSerialNO());
				//保存合车信息
				togetherTruckStockDao.addTogetherTruckStock(togetherTruckStock);
			}
		}else{
			//无合车调整
		}
	}
	
	/**
	 * 根据用户当前部门获取相应大部门编号
	 * @param currentOrgCode 当前部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午2:43:00
	 */
	@Override
	public String getBigOrgCode(String currentOrgCode){
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoByCode(currentOrgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门编号
			return orgAdministrativeInfoEntity.getCode();
		}else{
			//库存部门不存在
			LOGGER.error("查询当前用户所属大部门（外场或空运总调类型部门）失败");
			throw new StockException("库存部门不存在");
		}
	}
	
	/**
	 * 根据用户当前部门获取相应大部门
	 * @param currentOrgCode 当前部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午2:43:00
	 */
	private OrgAdministrativeInfoEntity getBigOrg(String currentOrgCode){
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoByCode(currentOrgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			//库存部门不存在
			LOGGER.error("查询当前用户所属大部门（外场或空运总调类型部门）失败");
			throw new StockException("库存部门不存在");
		}
	}
	
	
	
	/**
	 * 判断库存中是否有该货件记录
	 * 有则调用删除库存方法并返回 FossConstants.SUCCESS
	 * 没有则只记录出库动作信息并返回 FossConstants.FAILURE
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 （可为空）
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-24 上午9:57:18
	 */
	@Override
	@Transactional
	public int outStock(InOutStockEntity inOutStockEntity){
		//查询该货件的库存记录
		List<StockEntity> stockList = stockDao.queryUniqueStock(inOutStockEntity);
		//设置操作人信息
		if(StringUtils.isBlank(inOutStockEntity.getOperatorName())){
			String userName = this.getUserNameByCode(inOutStockEntity.getOperatorCode());
			inOutStockEntity.setOperatorName(userName);
		}
		if(CollectionUtils.isNotEmpty(stockList)){//存在该货件库存记录
			StockEntity stockEntity = stockList.get(0);
			//货区
			inOutStockEntity.setGoodsAreaCode(stockEntity.getGoodsAreaCode());
			//部门
			inOutStockEntity.setOrgCode(stockEntity.getOrgCode());
			//出库
			this.deleteStock(inOutStockEntity);
			//从特殊货区登出到正常货区
			if(StringUtils.equals(StockConstants.LONOUT_SPECIAL_GOODSAREA_OUT_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
				inOutStockEntity.setInOutStockType(StockConstants.NORMAL_AREA_FROM_SPECIAL_IN_STOCK_TYPE);
				//入库
				this.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
			}
			
			//从包装货区登出到正常货区  
			if(StringUtils.equals(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE_NORMAL, inOutStockEntity.getInOutStockType())){
				//包装货区
				inOutStockEntity.setInOutStockType(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE_NORMAL);
				//入库
				this.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
					
			}
			//从贵重货区登出到正常货区  
			if(StringUtils.equals(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE_NORMAL, inOutStockEntity.getInOutStockType())){
				//贵重货区
				inOutStockEntity.setInOutStockType(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE_NORMAL);
				//入库
				this.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
			}
			//从异常货区登出到正常货区  
			if(StringUtils.equals(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE_NORMAL, inOutStockEntity.getInOutStockType())){
				//异常货区
				inOutStockEntity.setInOutStockType(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE_NORMAL);
				//入库
				this.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
			}
			
			
			//如果是装车出库则删除合车表记录   LOAD_GOODS_OUT_STOCK_TYPE
			if(StringUtils.equals(StockConstants.LOAD_GOODS_OUT_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
				//删除合车表记录
				togetherTruckStockDao.deleteTogetherTruckStock(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO(), inOutStockEntity.getOrgCode());
			}
			
		}else{//没有该货件库存记录
			if(StringUtils.equals(StockConstants.SIGN_OUT_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
				LOGGER.error("签收 运单号" + inOutStockEntity.getWaybillNO() + "或者 流水号" + inOutStockEntity.getSerialNO() + " 不存在。");
				throw new StockException("运单号或流水号无效","");
			}else{
				inOutStockEntity.setInOutStockTime(new Date());
				//设置动作无效
				inOutStockEntity.setIsValid(FossConstants.INACTIVE);
				//保存出库动作信息
				inOutStockDao.addOutStock(inOutStockEntity);
				
				return FossConstants.FAILURE;
			}
		}
		
		return FossConstants.SUCCESS;
	}
	/**
	 * 页面出库
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 （可为空）
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-14 上午10:36:09
	 */
	@Transactional
	public void outStockPage(InOutStockEntity inOutStockEntity){
		//查询该货件的库存记录
		List<StockEntity> stockList = stockDao.queryUniqueStock(inOutStockEntity);
		//存在该货件库存记录
		if(CollectionUtils.isNotEmpty(stockList)){
			StockEntity stockEntity = stockList.get(0);
			//货区
			inOutStockEntity.setGoodsAreaCode(stockEntity.getGoodsAreaCode());
			//部门
			inOutStockEntity.setOrgCode(stockEntity.getOrgCode());
			//出库
			this.deleteStock(inOutStockEntity);
		//没有该货件库存记录
		}else{
			//抛出货物不存在异常
			LOGGER.error("没有货件" + inOutStockEntity.getWaybillNO() + "---" + inOutStockEntity.getSerialNO() + "库存记录");
			throw new StockException(StockException.GOODS_NOT_EXIST_STOCK_ERROR_CODE,new Object[]{inOutStockEntity.getWaybillNO(),inOutStockEntity.getSerialNO()});
		}
	}
	
	
	/**
	 * 将出入库类属性信息赋值给货件库存类属性
	 * @author dp-wangqiang
	 * @date 2012-10-12 下午5:08:06
	 */
	public StockEntity convertInOutStockToStockEntity(InOutStockEntity inOutStockEntity,StockEntity stockEntity){
		//运单号
		stockEntity.setWaybillNO(inOutStockEntity.getWaybillNO());
		//流水号
		stockEntity.setSerialNO(inOutStockEntity.getSerialNO());
		//操作人工号
		stockEntity.setOperatorCode(inOutStockEntity.getOperatorCode());
		//操作人姓名
		stockEntity.setOperatorName(inOutStockEntity.getOperatorName());
		//货区编号
		stockEntity.setGoodsAreaCode(inOutStockEntity.getGoodsAreaCode());
		//设备类型
		stockEntity.setDeviceType(inOutStockEntity.getDeviceType());
		//部门编号
		stockEntity.setOrgCode(inOutStockEntity.getOrgCode());
		//入库时间
		stockEntity.setInStockTime(inOutStockEntity.getInOutStockTime());
		//扫描时间
		stockEntity.setScanTime(inOutStockEntity.getScanTime());
		//下一部门编号
		stockEntity.setNextOrgCode(inOutStockEntity.getNextOrgCode());
		//是否建包扫描
		stockEntity.setBePackage(inOutStockEntity.getBePackage());
		return stockEntity;
	}
	
	/**
	 * PC查询库存界面  出库
	 * @param outStockList 货件List
	 * @param userCode 工号
	 * @param userName 姓名
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-25 上午8:42:20
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#outStockList(java.util.List, java.util.Date)
	 */
	@Override
	@Transactional
	public void outStockList(List<InOutStockEntity> outStockList, String userCode, String userName){
		for(InOutStockEntity inOutStockEntity : outStockList){
			//工号
			inOutStockEntity.setOperatorCode(userCode);
			//姓名
			inOutStockEntity.setOperatorName(userName);
			//出库类型
			inOutStockEntity.setInOutStockType(StockConstants.SINGLE_OUT_STOCK_TYPE);
			//设备类型
			inOutStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
			//扫描时间
			inOutStockEntity.setScanTime(new Date());
			this.outStockPage(inOutStockEntity);
		}
	}
	/**
	 * 根据运单号分页查询货件
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-17 下午4:41:19
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryGoods(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity, int, int)
	 */
	@Override
	public List<StockEntity> queryGoods(InOutStockEntity inOutStockEntity,
			int limit, int start) {
		//分页参数
		RowBounds rowBounds = new RowBounds(start, limit);
		//查询货件
		return stockDao.queryGoods(inOutStockEntity, rowBounds);
	}

	/**
	 * 根据运单号查询货件总数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-17 下午4:42:42
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryGoodsCount(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public Long queryGoodsCount(InOutStockEntity inOutStockEntity) {
		//查询总数
		return stockDao.queryGoodsCount(inOutStockEntity);
	}
	
	/**
	 * 根据运单号查询货件
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-17 下午4:47:12
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryGoods(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public List<StockEntity> queryGoods(InOutStockEntity inOutStockEntity,String orgCode) {
		//查询货件
		List<StockEntity> stockList = stockDao.queryGoods(inOutStockEntity, null);
		if(stockList!=null && stockList.size()>0){
			for (StockEntity stockEntity : stockList) {
				//在库存
				StockEntity stockPojo = this.queryUniqueStock(stockEntity.getWaybillNO(),stockEntity.getSerialNO());
				if(stockPojo!=null){
					//是否为当前部门的库存
					/**
					 * 根据当前部门编号获取 对应库存表里的库存部门orgCode   begin
					 */
					inOutStockEntity.setOrgCode(orgCode);
//					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
//					if(orgAdministrativeInfoEntity != null){
//						//营业部
//						if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getSalesDepartment())){
//							SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(inOutStockEntity.getOrgCode());
//							//驻地部门 
//							if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())){
//								//设置驻地营业部所在外场为库存部门
//								inOutStockEntity.setOrgCode(saleDepartmentEntity.getTransferCenter());
//							}
//						}
//					}
					/**
					 * 根据当前部门编号获取 对应库存表里的orgCode   end
					 */
					if(StringUtils.equals(inOutStockEntity.getOrgCode(), stockPojo.getOrgCode())){
						stockEntity.setStockStatic(StockConstants.STOCK_AT);
					}
					
				}else{
					//已签收
					String checkSerialNoIsSign = signDetailService.querySerialNoIsSign(stockEntity.getWaybillNO(),stockEntity.getSerialNO());
					if(StringUtils.equals(FossConstants.YES, checkSerialNoIsSign)){
						stockEntity.setStockStatic(StockConstants.STOCK_SIGN);
					}else{
						//其他
					}
				}
			}
		}
		return stockList;
	}
	/**
	 * PC页面登出特殊货区（贵重物品货区、异常货区、代包装货区）的货件到正常货区
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-18 下午1:50:47
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#logoutSpecialGoodsArea(com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity)
	 */
	@Override
	@Transactional
	public void logoutSpecialGoodsArea(List<InOutStockEntity> outStockList, String userCode, String userName) {
		HashMap<String,String> pushMapToPtp = new HashMap<String,String>();
		for(InOutStockEntity inOutStockEntity : outStockList){
			inOutStockEntity.setOperatorCode(userCode);
			inOutStockEntity.setOperatorName(userName);
			inOutStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
			//inOutStockEntity.setInOutStockType(StockConstants.LONOUT_SPECIAL_GOODSAREA_OUT_STOCK_TYPE);
			inOutStockEntity.setScanTime(new Date());
			//出库
			//this.outStockPage(inOutStockEntity);
			//设置入库类型
			inOutStockEntity.setInOutStockType(StockConstants.NORMAL_AREA_FROM_SPECIAL_IN_STOCK_TYPE);
			if(inOutStockEntity.getGoodsAreaCode()!=null){
				GoodsAreaEntity goodsAreaPojo = goodsAreaService.queryGoodsAreaByCode(inOutStockEntity.getOrgCode(),inOutStockEntity.getGoodsAreaCode());
				//包装货区
				if(StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING, goodsAreaPojo.getGoodsAreaType())){
					inOutStockEntity.setInOutStockType(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE_NORMAL);
				}
				//贵重货区
				if(StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE, goodsAreaPojo.getGoodsAreaType())){
					inOutStockEntity.setInOutStockType(StockConstants.VALUABLE_AREA_IN_STOCK_TYPE_NORMAL);
				}
				//异常货区
				if(StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION, goodsAreaPojo.getGoodsAreaType())){
					inOutStockEntity.setInOutStockType(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE_NORMAL);
				}
			}
			//pushMapToPtp为空  或者 pushMapToPtp根据运单号获取的值为空，那么表示运单为推送过，添加到map值为N
			if(StringUtils.isEmpty(pushMapToPtp.get(inOutStockEntity.getWaybillNO()))){
				pushMapToPtp.put(inOutStockEntity.getWaybillNO(), "N");
				//map中查询到此运单的值 说明推送过状态变为Y
			}else if(StringUtils.equals("N",pushMapToPtp.get(inOutStockEntity.getWaybillNO()))){
				pushMapToPtp.put(inOutStockEntity.getWaybillNO(), "Y");
			}
			inOutStockEntity.setPushMapToPtp(pushMapToPtp);
			//入库
			this.inStock(inOutStockEntity,StockConstants.CONFIRM, StockConstants.CONFIRM, true);
			
			/**入库类型是 入库包装货区时 需要调用下 包装的接口  begin*/
			if(StringUtils.equals(StockConstants.PACKAGE_AREA_IN_STOCK_TYPE_NORMAL, inOutStockEntity.getInOutStockType())){
				packInOutServerComm(inOutStockEntity,PackagingConstants.PACKAGING_LOGING_OUT);
			}
			/**入库类型是 入库包装货区时 需要调用下 包装的接口  end*/
		}
	}
	
	/**
	 * 导出货件库存到Excel
	 * @param ids 货件库存ID组成的以逗号分隔的字符串
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-26 上午10:49:44
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#exportExcelStream(java.lang.String)
	 */
	@Override
	public InputStream exportExcelStream(String ids,WaybillStockEntity waybillStockEntity,Date beginInStockTime,Date endInStockTime){
		InputStream excelStream = null;
		
		try{
				List<WaybillStockQueryDto> stockQueryDtoList;
				//用户勾选了需要导出的库存信息
				if(StringUtils.isNotBlank(ids)){
					List<String> idsList = Arrays.asList(ids.split(","));
					stockQueryDtoList = stockDao.queryExportStock(idsList);
				}else{//用户没有勾选，导出当前查询条件下查询出的所有的库存
					WaybillStockDto waybillStockDto = new WaybillStockDto();
					waybillStockDto.setWaybillStock(waybillStockEntity);
					List<String> goodsAreaList = new ArrayList<String>();
					String otherGoodsAreaGode = waybillStockEntity.getOtherGoodsAreaGode();
					if(StringUtils.isNotBlank(otherGoodsAreaGode)){
						String[] goodsAreaCodes = otherGoodsAreaGode.split(",");
						for(int i=0; i<goodsAreaCodes.length; i++){
							goodsAreaList.add(goodsAreaCodes[i]);
						}
					}
					if(StringUtils.isNotBlank(waybillStockEntity.getGoodsAreaCode())){
						goodsAreaList.add(waybillStockEntity.getGoodsAreaCode());
					}
					if(CollectionUtils.isNotEmpty(goodsAreaList)){
						waybillStockDto.setGoodsAreaList(goodsAreaList);
					}
					
					//BUG-57623  begin
					//提货方式
					if(StringUtils.isNotBlank(waybillStockEntity.getReceiveMethod())){
						waybillStockDto.setReceiveMethod(waybillStockEntity.getReceiveMethod());
					}
					
					//运输性质
					if(StringUtils.isNotBlank(waybillStockEntity.getProductCode())){
						waybillStockDto.setProductCode(waybillStockEntity.getProductCode());
					}
					//BUG-57623  end
					
					waybillStockDto.setBeginInStockTime(beginInStockTime);
					waybillStockDto.setEndInStockTime(endInStockTime);
					stockQueryDtoList = stockDao.queryExportStock(waybillStockDto);
				}
				//行List
				List<List<String>> rowList = new ArrayList<List<String>>();
				for(WaybillStockQueryDto stock : stockQueryDtoList){
					//每行的列List
					List<String> columnList = new ArrayList<String>();
					columnList.add(stock.getWaybillNO()+"");
					columnList.add(stock.getSerialNO()+"");
					columnList.add(stock.getGoodsName()+"");
					columnList.add(stock.getProductCode()+"");
					if(stock.getWeightTotal() != null){
						columnList.add(stock.getWeightTotal().toString()+"");
					}else{
						columnList.add(null);
					}
					if(stock.getVolumeTotal() != null){
						columnList.add(stock.getVolumeTotal().toString()+"");
					}else{
						columnList.add(null);
					}
					columnList.add(stock.getPackageType()+"");
					columnList.add(stock.getDepartureCode()+"");
					columnList.add(stock.getReceiveOrgCode()+"");
					columnList.add(stock.getOperatorName()+"");
					columnList.add(stock.getReceiveCustomerContact()+"");
					columnList.add(DateUtils.convert(stock.getInStockTime(), DateUtils.DATE_TIME_FORMAT)+"");
					columnList.add(DateUtils.convert(stock.getCreateWaybillTime(), DateUtils.DATE_TIME_FORMAT)+"");
					columnList.add(stock.getWaybillGoodsCount()+"");
					if(stock.getAdministrativeArea() != null){
						columnList.add(stock.getAdministrativeArea()+"");
					}else{
						columnList.add(null);
					}
					
					
					if(StringUtils.isNotBlank(stock.getGoodsAreaCode())){
						if(StringUtils.equals(StockConstants.VIRTUAL_GOODS_AREA_CODE, stock.getGoodsAreaCode())){
							columnList.add(StockConstants.VIRTUAL_GOODS_AREA_CODE);
						}else if(StringUtils.equals(StockConstants.BULK_GOODS_AREA_CODE, stock.getGoodsAreaCode())){
							columnList.add("散货货区");
						}else if(StringUtils.equals(StockConstants.WHOLE_VEHICLE_GOODS_AREA_CODE, stock.getGoodsAreaCode())){
							columnList.add("整车虚拟货区");
						}else{
							columnList.add(stock.getGoodsAreaName()+"");
						}
					}else{
						columnList.add(StockConstants.VIRTUAL_GOODS_AREA_CODE);
					}
					//加入到达时间和在本部门库存件数 
					columnList.add(DateUtils.convert(stock.getArrivalTime(), DateUtils.DATE_TIME_FORMAT)+"");
					columnList.add(stock.getStockGoodsCount() +"");
						
					rowList.add(columnList);
				}
				SheetData sheetData = new SheetData();
				sheetData.setRowHeads(StockConstants.ROW_HEADS);
				sheetData.setRowList(rowList);
				
				ExcelExport excelExportUtil = new ExcelExport();
				excelStream = excelExportUtil.inputToClient(excelExportUtil.exportExcel(sheetData, StockConstants.SHEET_NAME, StockConstants.SHEET_SIZE));
		}catch (BusinessException e) {
			LOGGER.error("导出库存异常", e);
			throw new BusinessException("导出库存异常",e);
		}
        return excelStream;
	}
	
	/** 
	 * 转换编码
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午7:56:00
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#encodeFileName(java.lang.String)
	 */
	@Override
	public String encodeFileName(String fileName) throws StockException {
		try {
			return URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("转换文件名编码失败", e);
			throw new StockException(StockException.EXPORT_FILE_ERROR_CODE,"");
		}
	}
	/**
	 * 分页查询必走货
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-30 下午2:57:08
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryPriorityGoods(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto)
	 */
	@Override
	public List<WaybillStockQueryDto> queryPriorityGoods(
			WaybillStockQueryDto waybillStockQueryDto,int limit, int start) {
		
		List<WaybillStockQueryDto> list = waybillStockDao.queryPriorityGoods(waybillStockQueryDto,limit,start);
		//判断下一部门是否为空
		for(WaybillStockQueryDto w : list){
			if(StringUtils.isBlank(w.getNextOrgCode())){
				w.setNextOrgCode(w.getReceiveOrgCode());
			}
		}
		
		//xiaobc  update  start.....................
	/*		for (WaybillStockQueryDto stockDto : list) {
				if(stockDto.getProductTypeCode().equals("PACKAGE")||stockDto.getProductTypeCode().equals("RCP")){//是否为标准快递
					BigDecimal weight = stockDto.getWeightTotal();//拿到重量
					BigDecimal ratio = this.queryStockParameter();//拿到体积比率 单位：方/吨
					BigDecimal volume = weight.multiply(ratio).divide(new BigDecimal(1000)).setScale(two,BigDecimal.ROUND_HALF_DOWN);//单位同意除以1000
					stockDto.setVolumeTotal(volume);
				}
			}*/
		//xiaobc  update  end.....................
		return list;
		
	//	return waybillStockDao.queryPriorityGoods(waybillStockQueryDto,limit,start);
		
	}
	/**
	 * 查询必走货总记录数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-30 下午4:02:20
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryPriorityGoodsCount(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto)
	 */
	@Override
	public Long queryPriorityGoodsCount(
			WaybillStockQueryDto waybillStockQueryDto) {
		
		return waybillStockDao.queryPriorityGoodsCount(waybillStockQueryDto);
	}
	/**
	 * 查询弃货入库时间
	 * @param waybillNO 运单号
	 * @param orgCode 部门编号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-31 下午3:11:21
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryAbandonedGoodsInstockTime(java.lang.String, java.lang.String)
	 */
	@Override
	public Date queryAbandonedGoodsInstockTime(String waybillNO, String orgCode) {
		WaybillStockEntity waybillStock = new WaybillStockEntity();
		waybillStock.setWaybillNO(waybillNO);
		waybillStock.setOrgCode(orgCode);
		List<WaybillStockEntity> waybillStockList = waybillStockDao.queryWaybillStock(waybillStock);
		Date inStockTime = null;
		if(waybillStockList.size() > 0){
			inStockTime = waybillStockList.get(0).getLastInStockTime();
		}
		return inStockTime;
	}
	
	/**
 	 * 根据运单号s、部门CODE查询运单库存总件数
 	 * @author zyr
 	 * @date 2015-08-28 上午10:31:04
 	 */
	@Override
	public Map<String, Integer> querySumStockGoodsQtyByWaybillsOrgCode(String[] waybillNos, String orgCode) {
		List<WaybillStockEntity> waybillStockEntitys = waybillStockDao.querySumStockGoodsQtyByWaybillsOrgCode(waybillNos,orgCode);
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(CollectionUtils.isNotEmpty(waybillStockEntitys)) {
			for(WaybillStockEntity waybillStockEntity : waybillStockEntitys) {
				
				map.put(waybillStockEntity.getWaybillNO(), waybillStockEntity.getStockGoodsCount());
			}
		}
		return map;
	}
	
	/**
	 * PC端操作出库
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 （可为空）
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-6 上午8:46:52
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#outStockPC(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public int outStockPC(InOutStockEntity inOutStockEntity) {
		//设置操作出库设备类型
		inOutStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
		//设置当前调用时间为扫描时间
		inOutStockEntity.setScanTime(new Date());
		//调用出库
		return this.outStock(inOutStockEntity);
	}
	
	/**
	 * PC端操作入库
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param goodsAreaCode.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-6 上午9:21:05
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#inStockPC(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Transactional
	@Override
	public int inStockPC(InOutStockEntity inOutStockEntity) {
		//设置操作出库设备类型
		inOutStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
		//设置当前调用时间为扫描时间
		inOutStockEntity.setScanTime(new Date());
		//根据运单号和返货开单类型查询运单实体
		WaybillExpressEntity waybillExpress = waybillExpressService.queryWaybillByOriginalWaybillNo(inOutStockEntity.getWaybillNO(),WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
		//如果查询到的实体不为空则抛异常
		if(waybillExpress!=null){
		throw new StockException("运单号：" + inOutStockEntity.getWaybillNO() +" 为返货开单  ","");
		}
		//卸车 入库 或者空运落货 需要页面 抛出 错误。
		if(StringUtils.equals(StockConstants.UNLOAD_LOSE_GOODS_FOUND_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ||
				StringUtils.equals(StockConstants.AIR_UNSHIPPED_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
			//调用入库
			return this.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, true);
		}else{
			//调用入库
			return this.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
		}
		
	}
	
	/** 
	 * <pre>
	 * 此方法逻辑同inStockPC方法，区别为使用REQUIRES_NEW事务，对应于外围调用时，此方法出错抛出异常后，需继续外围其他业务的情况
	 * 可解决外围调用可能出现的事务问题(Transaction rolled back because it has been marked as rollback-only)
	 * </pre>
	 * 
	 * @author foss-wuyingjie
	 * @date 2013-1-18 下午3:34:24
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#inStockRequiresNewTransactional(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	//@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void inStockRequiresNewTransactional(InOutStockEntity inOutStockEntity){
		//设置操作出库设备类型
		inOutStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
		//设置当前调用时间为扫描时间
		inOutStockEntity.setScanTime(new Date());
		
		//调用入库
		this.inStock(inOutStockEntity, StockConstants.CONFIRM, StockConstants.CONFIRM, false);
	}
	
	/**
	 * 判断货件是否存在该部门库存
	 * @param orgCode 部门
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @param goodsAreaCode 货区   （可以为空）
	 * @return true： 不存在    false： 存在
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-14 下午4:18:46
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#isNotExistStock(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isNotExistStock(String orgCode, String waybillNO,
			String serialNO, String goodsAreaCode) {
		//设置查询参数
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO(waybillNO);
		inOutStockEntity.setOrgCode(orgCode);
		inOutStockEntity.setSerialNO(serialNO);
		inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
		StockEntity stock = stockDao.queryStockEntityByNos(inOutStockEntity);
		if(stock == null){
			return true;
		}
		return false;
	}
	
	/**
	 * 查询三级产品列表
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-16 下午2:37:08
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryProductList()
	 */
	@Override
	public List<BaseDataDictDto> queryProductList() {
		List<BaseDataDictDto> list = new ArrayList<BaseDataDictDto>();
		BaseDataDictDto baseDataDictDto = new BaseDataDictDto();
		baseDataDictDto.setValueName(TransferDictionaryConstants.DEFAULT_COMBO_TEXT);
		baseDataDictDto.setValueCode(TransferDictionaryConstants.DEFAULT_COMBO_VALUE);
		list.add(baseDataDictDto);
		
		ProductDto productDto = new ProductDto();
		productDto.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
		List<ProductEntity> productList = productService4Dubbo.findExternalProductByCondition(productDto, null);
		if(CollectionUtils.isNotEmpty(productList)){
			for(ProductEntity product : productList){
				BaseDataDictDto dict = new BaseDataDictDto();
				dict.setValueName(product.getName());
				dict.setValueCode(product.getCode());
				list.add(dict);
			}
		}
		return list;    
	}
	/**
	 * 根据运单号、流水号查询唯一的库存记录
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @return StockEntity or null
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-23 下午3:43:26
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryUniqueStock(java.lang.String, java.lang.String)
	 */
	@Override
	public StockEntity queryUniqueStock(String waybillNO, String serialNO) {
		//设置查询参数
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO(waybillNO);
		inOutStockEntity.setSerialNO(serialNO);
		//查询货件库存
		List<StockEntity> stockList = stockDao.queryUniqueStock(inOutStockEntity);
		if(CollectionUtils.isNotEmpty(stockList)){
			return stockList.get(0);
		}
		return null;
	}
	
	/**
	 * 更新流水号库存预配状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午6:49:43
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#updatePreHandOverState(java.util.List)
	 */
	@Override
	public boolean updatePreHandOverState(
			List<UpdateStockPreHandOverStateDto> dtoList,String targetStatus) throws StockException{
		for(int i = 0;i < dtoList.size();i++){
			//查询实体库存
			StockEntity stockEntity = queryStockEntityByNos(dtoList.get(i).getDeptCode(),dtoList.get(i).getWaybillNo(),dtoList.get(i).getSerialNo(),null);
			//如果置空库存预配状态
			if(targetStatus == null){
				if(stockEntity != null){
					//部门
					stockEntity.setOrgCode(dtoList.get(i).getDeptCode());
					//运单号
					stockEntity.setWaybillNO(dtoList.get(i).getWaybillNo());
					//流水号
					stockEntity.setSerialNO(dtoList.get(i).getSerialNo());
					//预配状态
					stockEntity.setPreHandOverState(targetStatus);
					//更新库存预配状态
					stockDao.updatePreHandOverState(stockEntity);
				}
				//如果更新库存的预配状态
			}else{
				if(stockEntity == null){
					LOGGER.info("更新预配状态失败：运单号：" + dtoList.get(i).getWaybillNo() + " 流水号：" + dtoList.get(i).getSerialNo() + " 不在库存");
					//throw new StockException(StockException.GOODS_NOT_EXIST_LOCAL_STOCK_ERROR_CODE,new Object[]{dtoList.get(i).getWaybillNo(),dtoList.get(i).getSerialNo()});
				}else if(StringUtils.equals(stockEntity.getPreHandOverState(), StockConstants.PRE_HANDOVER_STATUS)){
					LOGGER.error("更新预配状态失败：运单号：" + dtoList.get(i).getWaybillNo() + " 流水号：" + dtoList.get(i).getSerialNo() + " 已被预配");
					throw new StockException(StockException.GOODS_ALREADY_PRE_HANDOVER_ERROR_CODE,new Object[]{dtoList.get(i).getWaybillNo(),dtoList.get(i).getSerialNo()});
				}else{
					//部门
					stockEntity.setOrgCode(dtoList.get(i).getDeptCode());
					//运单号
					stockEntity.setWaybillNO(dtoList.get(i).getWaybillNo());
					//流水号
					stockEntity.setSerialNO(dtoList.get(i).getSerialNo());
					//预配状态
					stockEntity.setPreHandOverState(targetStatus);
					//更新库存预配状态
					stockDao.updatePreHandOverState(stockEntity);
				}
			}
		}
		return true;
	}
	
	/**
	 * 根据库存部门、运单号、流水号获取件库存
	 * @param orgCode 库存部门
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @param goodsAreaCode 货区编号
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午7:08:24
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryStockEntityByNos(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public StockEntity queryStockEntityByNos(String orgCode, String waybillNO,
			String serialNO, String goodsAreaCode) {
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setOrgCode(orgCode);
		inOutStockEntity.setWaybillNO(waybillNO);
		inOutStockEntity.setSerialNO(serialNO);
		return stockDao.queryStockEntityByNos(inOutStockEntity);
	}
	
	/**
	 * 根据库存部门、运单号、流水号获取件库存
	 * @param orgCode 库存部门
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @param goodsAreaCode 货区编号
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午7:08:24
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryStockEntityByNos(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public StockSaleEntity querySaleStockEntityByNos(String orgCode, String waybillNO,
			String serialNO, String goodsAreaCode) {
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setOrgCode(orgCode);
		inOutStockEntity.setWaybillNO(waybillNO);
		inOutStockEntity.setSerialNO(serialNO);
		return stockDao.querySaleStockEntityByNos(inOutStockEntity);
	}
	
	/**
	 * 根据走货路径的调整，修改相应库存（从原货区出库，入库到调整后的新货区）
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @param orgCode 部门
	 * @param userCode 工号
	 * @param userName 姓名
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-30 上午11:35:14
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#adjustGoodsAreaStock(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public int adjustGoodsAreaStock(String waybillNO, String serialNO,
			String orgCode, String userCode, String userName) {
		
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		//设置出库参数
		inOutStockEntity.setWaybillNO(waybillNO);
		//流水号
		inOutStockEntity.setSerialNO(serialNO);
		//工号
		inOutStockEntity.setOperatorCode(userCode);
		//姓名
		inOutStockEntity.setOperatorName(userName);
		//入库类型
		inOutStockEntity.setInOutStockType(StockConstants.TRANSPORT_PATH_CHANGE_OUT_STOCK_TYPE);
		//出库
		this.outStockPC(inOutStockEntity);
		//设置入库参数
		inOutStockEntity.setOrgCode(orgCode);
		inOutStockEntity.setInOutStockType(StockConstants.TRANSPORT_PATH_CHANGE_IN_STOCK_TYPE);
		//入库
		this.inStockPC(inOutStockEntity);
		
		return FossConstants.SUCCESS;
	}
	/**
	 * 根据合车调整，增加合车库存
	 * @param waybillNOList 运单List
	 * @param originalGoodsAreaCodeList 原货区List
	 * @param newGoodsAreaCode 合成目标货区编号
	 * @param orgCode 部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-30 上午11:35:22
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#adjustTogetherTruckStock(java.util.List, java.util.List, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public int adjustTogetherTruckStock(List<String> waybillNOList,
			List<String> originalGoodsAreaCodeList, String newGoodsAreaCode,
			String orgCode) {
		
		WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
		//合成记录对象
		TogetherTruckStockEntity togetherTruckStock = new TogetherTruckStockEntity();
		if(CollectionUtils.isNotEmpty(waybillNOList) && CollectionUtils.isNotEmpty(originalGoodsAreaCodeList)){
			for(int i=0; i< waybillNOList.size(); i++){
				//**********设置查询货件库存参数*************
				//运单号
				waybillStockEntity.setWaybillNO(waybillNOList.get(i));
				//部门
				waybillStockEntity.setOrgCode(orgCode);
				//货区
				waybillStockEntity.setGoodsAreaCode(originalGoodsAreaCodeList.get(i));
				//查询货件库存
				List<StockEntity> stockList = this.queryStock(waybillStockEntity);
				for(StockEntity stock : stockList){
					//*************封装合车对象************
					//运单号
					togetherTruckStock.setWaybillNO(stock.getWaybillNO());
					//流水号
					togetherTruckStock.setSerialNO(stock.getSerialNO());
					//部门
					togetherTruckStock.setOrgCode(orgCode);
					//合车目标货区
					togetherTruckStock.setNewGoodsAreaCode(newGoodsAreaCode);
					//原货区
					togetherTruckStock.setOrigGoodsAreaCode(originalGoodsAreaCodeList.get(i));
					//保存合车记录
					togetherTruckStockDao.addTogetherTruckStock(togetherTruckStock);
				}
			}
		}else{
			return FossConstants.FAILURE;
		}
		
		return FossConstants.SUCCESS;
	}
	/**
	 *  判断运单是否有出库记录
	 * @param waybillNO 运单号
	 * @param createBillTime 开单时间
	 * @return 有出库记录返回 true, 否则 false
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 上午9:53:03
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#isOutStock(java.lang.String, java.util.Date)
	 */
	@Override
	public boolean isOutStock(String waybillNO, Date createBillTime) {
		//查询出库记录
		List<String> outStockIdList = inOutStockDao.queryOutStock(waybillNO, createBillTime);
		if(CollectionUtils.isNotEmpty(outStockIdList)){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取当前大部门的特殊货区
	 * @param orgCode 部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午5:17:31
	 */
	@Override
	public List<BaseDataDictDto> querySpecialAreaList(String orgCode){
		List<BaseDataDictDto> specialAreaList = new ArrayList<BaseDataDictDto>();
		//贵重
		List<GoodsAreaEntity> valuableAreaList = goodsAreaService.queryGoodsAreaListByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE); 
		if(CollectionUtils.isNotEmpty(valuableAreaList)){
			//封装字典对象
			BaseDataDictDto dictDto = new BaseDataDictDto();
			//货区
			GoodsAreaEntity goodsArea = valuableAreaList.get(0);
			//货区编号
			dictDto.setValueCode(goodsArea.getGoodsAreaCode());
			//货区名称
			dictDto.setValueName(goodsArea.getGoodsAreaName());
			specialAreaList.add(dictDto);
		}
		//包装
		List<GoodsAreaEntity> packingAreaList = goodsAreaService.queryGoodsAreaListByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING); 
		if(CollectionUtils.isNotEmpty(packingAreaList)){
			//封装字典对象
			BaseDataDictDto dictDto = new BaseDataDictDto();
			//货区
			GoodsAreaEntity goodsArea = packingAreaList.get(0);
			//货区编号
			dictDto.setValueCode(goodsArea.getGoodsAreaCode());
			//货区名称
			dictDto.setValueName(goodsArea.getGoodsAreaName());
			specialAreaList.add(dictDto);
		}
		//异常
		List<GoodsAreaEntity> exceptionAreaList = goodsAreaService.queryGoodsAreaListByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION); 
		if(CollectionUtils.isNotEmpty(exceptionAreaList)){
			//封装字典对象
			BaseDataDictDto dictDto = new BaseDataDictDto();
			//货区
			GoodsAreaEntity goodsArea = exceptionAreaList.get(0);
			//货区编号
			dictDto.setValueCode(goodsArea.getGoodsAreaCode());
			//货区名称
			dictDto.setValueName(goodsArea.getGoodsAreaName());
			specialAreaList.add(dictDto);
		}
		
		return specialAreaList;
	}
	/**
	 * 判断货件是否存在于该部门非某一货区
     * @param orgCode 部门
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @param goodsAreaCode 货区
	 * @return true： 存在    false：不 存在
	 * @author foss-wuyingjie
	 * @date 2012-11-30 下午5:26:44
	 */
	@Override
	public boolean isExistOtherGoodsAreaStock(String orgCode, String waybillNO, String serialNO, String goodsAreaCode) {
		/**
		 * BUG-50571 begin
		 * 西安雁塔区电子二路营业部清仓多货118件，显示放错货区，实际上货物并无异常，没有放错货区
		 */
		if(StringUtils.isBlank(goodsAreaCode)){
			goodsAreaCode = StockConstants.VIRTUAL_GOODS_AREA_CODE;
		}
		/**
		 * BUG-50571 end
		 * 西安雁塔区电子二路营业部清仓多货118件，显示放错货区，实际上货物并无异常，没有放错货区
		 */
		
		Integer count = stockDao.isExistOtherGoodsAreaStock(orgCode, waybillNO, serialNO, goodsAreaCode);
		if(count!=null && count > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 通过用户CODE调用综合接口获取用户姓名
	 * @param userCode 工号
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午2:38:21
	 */
	private String getUserNameByCode(String userCode){
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(userCode);
		if(employee != null){
			return employee.getEmpName();
		//查询 不到用户 
		}else{
			//返回 工号
			return userCode;
		}
	}
	
	/**
	 * 根据当前部门获取有库存的大部门
	 * @param currentOrg 当前部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-6 下午5:29:23
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryStockOrgCode(java.lang.String)
	 */
	@Override
	public OrgAdministrativeInfoEntity queryStockOrg(OrgAdministrativeInfoEntity currentOrg) {
		//查询部门实体
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCode(currentOrg.getCode());
		
		if(orgAdministrativeInfoEntity != null){
			//非库存大部门
			if(!StringUtils.endsWith(FossConstants.YES, orgAdministrativeInfoEntity.getSalesDepartment()) &&
					!StringUtils.endsWith(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter()) &&
						!StringUtils.endsWith(FossConstants.YES, orgAdministrativeInfoEntity.getAirDispatch())){
				//获取库存部门
				return this.getBigOrg(currentOrg.getCode());
			}else{
				//库存部门
				return currentOrg;
			}
		//部门为空	
		}else{
			LOGGER.error("查询部门：" + currentOrg.getCode() + " 失败");
			throw new StockException("库存部门不存在");
		}
	}
	
	/**
	 * 根据当前部门查询库存部门信息（是否是外场、驻地派送部、空运总调）
	 * @param currentOrg 当前部门
	 * @author 097457-foss-wangqiang
	 * @date 2013-2-22 下午4:15:28
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryStockOrgInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity)
	 */
	@Override
	public StockOrgDto queryStockOrgInfo(OrgAdministrativeInfoEntity currentOrg) {
		//查询部门实体
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCode(currentOrg.getCode());
		
		OrgAdministrativeInfoEntity stockOrg = new OrgAdministrativeInfoEntity();
		//封装部门信息
		StockOrgDto stockOrgDto = new StockOrgDto();
		if(orgAdministrativeInfoEntity != null){
			//非库存大部门
			if(!StringUtils.endsWith(FossConstants.YES, orgAdministrativeInfoEntity.getSalesDepartment()) &&
					!StringUtils.endsWith(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter()) &&
						!StringUtils.endsWith(FossConstants.YES, orgAdministrativeInfoEntity.getAirDispatch())){
				//获取库存部门
				stockOrg = this.getBigOrg(currentOrg.getCode());
			}else{
				stockOrg = currentOrg;
			}	
			//编号
			stockOrgDto.setOrgCode(stockOrg.getCode());
			//名称
			stockOrgDto.setOrgName(stockOrg.getName());
			//营业部
			if(StringUtils.endsWith(FossConstants.YES, stockOrg.getSalesDepartment())){
				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(currentOrg.getCode());
				//驻地部门 
				if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())){
					//驻地可到达
					if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getArrive())){
						//设置类型 驻地派送部门
						stockOrgDto.setIsStationDelivery(FossConstants.YES);
						//外场编号
						String transferCode = saleDepartmentEntity.getTransferCenter();
						//查询外场组织实体
						OrgAdministrativeInfoEntity transferOrg = orgAdministrativeInfoService.
								queryOrgAdministrativeInfoByCode(transferCode);
						if(transferOrg != null){
							stockOrgDto.setOrgCode(transferCode);
							stockOrgDto.setOrgName(transferOrg.getName());
						}else{
							throw new StockException("查询驻地部门:" + saleDepartmentEntity.getCode() + " 所属外场失败","");
						}
						//派送货区
						List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(saleDepartmentEntity.getTransferCenter(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
						if(CollectionUtils.isNotEmpty(goodsAreaList)){
							String goodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();
							String goodsAreaName = goodsAreaList.get(0).getGoodsAreaName();
							//货区编号
							stockOrgDto.setGoodsAreaCode(goodsAreaCode);
							//货区名称
							stockOrgDto.setGoodsAreaName(goodsAreaName);
						}else{
							//根据部门查询货区失败
							throw new StockException(StockException.QUERY_GOODS_AREA_BY_ORGCODE_FAILURE_ERROR_CODE,new Object[]{"部门编号："+saleDepartmentEntity.getTransferCenter()});
						}
					
					}
					
				}
			}
			//外场
			if(StringUtils.endsWith(FossConstants.YES, stockOrg.getTransferCenter())){
				stockOrgDto.setIsTransferCenter(FossConstants.YES);
			}
			//空运总调
			if(StringUtils.endsWith(FossConstants.YES, stockOrg.getAirDispatch())){
				//设置空运总调类型
				stockOrgDto.setIsAirDispatch(FossConstants.YES);
				//空运总调对应的外场编号
				String transferCode = queryTransferCenterByAirDispatchCode(stockOrg.getCode());
				//查询外场组织实体
				OrgAdministrativeInfoEntity transferOrg = orgAdministrativeInfoService.
						queryOrgAdministrativeInfoByCode(transferCode);
				
				stockOrgDto.setOrgCode(transferCode);
				stockOrgDto.setOrgName(transferOrg.getName());
				//空运货区
				List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
				if(CollectionUtils.isNotEmpty(goodsAreaList)){
					String goodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();
					String goodsAreaName = goodsAreaList.get(0).getGoodsAreaName();
					//货区编号
					stockOrgDto.setGoodsAreaCode(goodsAreaCode);
					//货区名称
					stockOrgDto.setGoodsAreaName(goodsAreaName);
				}else{
					//根据部门查询货区失败
					throw new StockException(StockException.QUERY_GOODS_AREA_BY_ORGCODE_FAILURE_ERROR_CODE,new Object[]{"部门编号："+transferCode});
				}
			}
				
			return stockOrgDto;
		}else{
			LOGGER.error("查询部门：" + currentOrg.getCode() + " 失败");
			throw new StockException("库存部门不存在");
		}
		
	}
	/**
	 * 判断部门类型是否是外场
	 * @param orgCode 部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-7 上午11:05:50
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#isTransferCenter(java.lang.String)
	 */
	@Override
	public String isTransferCenter(String orgCode) {
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCode(orgCode);
		if(StringUtils.endsWith(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
			return FossConstants.YES;
		}
		return FossConstants.NO;
	}
	/**
	 * 违禁品出库异常货区，
	 * 入库到驻地派送部货区
	 * 修改走货路径，
	 * 将驻地派送部作为终点站
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-17 上午11:06:47
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#handoverContrabandToStationStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Transactional
	@Override
	public int handoverContrabandToStationStock(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setInOutStockType(StockConstants.CONTRABAND_EXCEPTION_AREA_OUT_STOCK_TYPE);
		//出库异常货区
		this.outStockPC(inOutStockEntity);
		//调用综合接口 根据外场CODE获取驻地派送部CODE
		SaleDepartmentEntity departmentEntity = orgAdministrativeInfoComplexService.queryStationDeliverOrgOneByOutfieldCode(inOutStockEntity.getOrgCode());
		if(departmentEntity == null){
			LOGGER.error("查询当前外场:" + inOutStockEntity.getOrgCode() + "对应的驻地派送部门失败");
			throw new StockException(StockException.QUERY_STATION_ORG_FAILURE_ERROR_CODE,new Object[]{inOutStockEntity.getOrgCode()});
		}
		inOutStockEntity.setInOutStockType(StockConstants.CONTRABAND_STATION_IN_STOCK_TYPE);
		//入库到驻地派送部货区
		this.inStockPC(inOutStockEntity);
		calculateTransportPathService.contraband(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO(), 
				inOutStockEntity.getOrgCode(), departmentEntity.getCode());
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 获取某一部门、货区中所有货件的快照
	 * @param deptCode
	 * @param goodsAreaCode
	 * @return List<StockEntity>
	 * @author foss-wuyingjie
	 * @date 2012-12-19 上午10:17:54
	 */
	@Override
	public List<StockEntity> queryStockByGoodsAreaCode(String deptCode,	String goodsAreaCode) {
		return stockDao.queryStockByGoodsAreaCode(deptCode, goodsAreaCode);
	}
	/**
	 * 根据部门查询库存件数
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 下午8:49:26
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryStockGoodsQty(java.lang.String)
	 */
	@Override
	public Long queryStockGoodsQty(String orgCode){
		List<Long> goodsQtyList = stockDao.queryStockGoodsQty(orgCode);
		if(CollectionUtils.isNotEmpty(goodsQtyList)){
			return goodsQtyList.get(0);
		}
		return null;
	}
	
	/**
	 * 根据部门查询库存总件数和总票数
	 * @author 272681-foss-chenlei
	 * @date 2016-02-19 下午14:49:26
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryStockGoodsQtyAndWaybillQty(java.lang.String)
	 */
	@Override
	public WaybillStockStatisticsDto queryStockGoodsQtyAndWaybillQty(String orgCode){
		return  stockDao.queryStockGoodsQtyAndWaybillQty(orgCode);
	}
	
	/**
	 * 查询库存 统计
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-6 下午2:30:01
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryWaybillStockStatistics(com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity, java.util.Date, java.util.Date)
	 */
	@Override
	public WaybillStockStatisticsDto queryWaybillStockStatistics(
			WaybillStockEntity waybillStockEntity, Date beginInStockTime,
			Date endInStockTime) {
		WaybillStockDto waybillStockDto = new WaybillStockDto();
		waybillStockDto.setWaybillStock(waybillStockEntity);
		//设置货区参数
		List<String> goodsAreaList = new ArrayList<String>();
		String otherGoodsAreaGode = waybillStockEntity.getOtherGoodsAreaGode();
		if(StringUtils.isNotBlank(otherGoodsAreaGode)){
			String[] goodsAreaCodes = otherGoodsAreaGode.split(",");
			for(int i=0; i<goodsAreaCodes.length; i++){
				goodsAreaList.add(goodsAreaCodes[i]);
			}
		}
		if(StringUtils.isNotBlank(waybillStockEntity.getGoodsAreaCode())){
			//是驻地部门的话,如果没有输入运单号传过来的库区code是"code1,code2" 格式的
			String[] goodsAreaCodes = waybillStockEntity.getGoodsAreaCode().split(",");
			for(int i=0; i<goodsAreaCodes.length; i++){
				goodsAreaList.add(goodsAreaCodes[i]);
			}
			//goodsAreaList.add(waybillStockEntity.getGoodsAreaCode());
		}
		if(CollectionUtils.isNotEmpty(goodsAreaList)){
			waybillStockDto.setGoodsAreaList(goodsAreaList);
		}
		waybillStockDto.setBeginInStockTime(beginInStockTime);
		waybillStockDto.setEndInStockTime(endInStockTime);
		
		WaybillStockStatisticsDto waybillStockStatisticsDto = null;
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
			System.out.println("快递员>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			waybillStockStatisticsDto = waybillStockDao.queryExpressWaybillStockStatistics(waybillStockDto);
		}else{
			System.out.println("零担<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			waybillStockStatisticsDto = waybillStockDao.queryWaybillStockStatistics(waybillStockDto);
		}
		if(waybillStockStatisticsDto.getWeightTotal() == null){
			waybillStockStatisticsDto.setWeightTotal(new BigDecimal(0));
		}
		if(waybillStockStatisticsDto.getVolumeTotal() == null){
			waybillStockStatisticsDto.setVolumeTotal(new BigDecimal(0));
		}
		return waybillStockStatisticsDto;
	}
	/**
	 * 查询必走货统计
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-6 下午2:29:42
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryPriorityGoodsStatistics(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto)
	 */
	@Override
	public WaybillStockStatisticsDto queryPriorityGoodsStatistics(
			WaybillStockQueryDto waybillStockQueryDto) {
		
		WaybillStockStatisticsDto waybillStockStatisticsDto = waybillStockDao.queryPriorityGoodsStatistics(waybillStockQueryDto);
		//xiaobc update start.........................
				waybillStockStatisticsDto.setSrcVolumeTotal(waybillStockStatisticsDto.getVolumeTotal());//设置原始总体积
				BigDecimal bExpressVolume = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);//上计泡机的快递运单总体积
				BigDecimal bExpressWeight = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);//上计泡机的快递运单总重量
				BigDecimal nBExpressVolume = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);//不上计泡机的快递运单总体积
				BigDecimal nBExpressWeight = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);//不上计泡机的快递运单总重量
				WaybillStockStatisticsDto waybillStockStatisticsDtoOnlyExpress = new WaybillStockStatisticsDto();
				//如果是查询所有
				if(waybillStockQueryDto.getProductCode()==null || StringUtils.equals(waybillStockQueryDto.getProductCode(), "")){
				//查询标准快递的
					waybillStockQueryDto.setProductCode("PACKAGE");
					//只查快递的数据统计
					WaybillStockStatisticsDto waybillStockStatisticsDtoOnlyExpress1 = new WaybillStockStatisticsDto();
					waybillStockStatisticsDtoOnlyExpress1 = waybillStockDao.queryPriorityGoodsStatistics(waybillStockQueryDto);
					
					//上计泡机运单的重量总和
					WaybillStockStatisticsDto bcmtotal1 =waybillStockDao.queryBcmWaybillPriorityGoodsStatistics(waybillStockQueryDto);
					if(bcmtotal1 !=null){
						bExpressVolume = bExpressVolume.add(bcmtotal1.getVolumeTotal()==null?BigDecimal.ZERO:bcmtotal1.getVolumeTotal());
						bExpressWeight = bExpressWeight.add(bcmtotal1.getWeightTotal()==null?BigDecimal.ZERO:bcmtotal1.getWeightTotal());
					}
					
					
					//不上计泡机的运单重量总和
					WaybillStockStatisticsDto noBcmtotal1 =waybillStockDao.queryNoBcmWaybillPriorityGoodsStatistics(waybillStockQueryDto);
					if(noBcmtotal1 != null){
						nBExpressVolume = nBExpressVolume.add(noBcmtotal1.getVolumeTotal()==null?BigDecimal.ZERO:noBcmtotal1.getVolumeTotal());
						nBExpressWeight = nBExpressWeight.add(noBcmtotal1.getWeightTotal()==null?BigDecimal.ZERO:noBcmtotal1.getWeightTotal());
					}
					
					
					//查询3.60的
					waybillStockQueryDto.setProductCode("RCP");
					//只查快递的数据统计
					WaybillStockStatisticsDto waybillStockStatisticsDtoOnlyExpress2 = new WaybillStockStatisticsDto();
					waybillStockStatisticsDtoOnlyExpress2 = waybillStockDao.queryPriorityGoodsStatistics(waybillStockQueryDto);
					
					//上计泡机运单的重量总和
					WaybillStockStatisticsDto bcmtotal2 =waybillStockDao.queryBcmWaybillPriorityGoodsStatistics(waybillStockQueryDto);
					if(bcmtotal2 !=null){
						bExpressVolume = bExpressVolume.add(bcmtotal2.getVolumeTotal()==null? BigDecimal.ZERO:bcmtotal2.getVolumeTotal());
						bExpressWeight = bExpressWeight.add(bcmtotal2.getWeightTotal()==null? BigDecimal.ZERO:bcmtotal2.getWeightTotal());
					}
					//不上计泡机的运单重量总和
					WaybillStockStatisticsDto noBcmtotal2 =waybillStockDao.queryNoBcmWaybillPriorityGoodsStatistics(waybillStockQueryDto);
					if(noBcmtotal2 !=null){
						nBExpressVolume = nBExpressVolume.add(noBcmtotal2.getVolumeTotal()==null? BigDecimal.ZERO:noBcmtotal2.getVolumeTotal());
						nBExpressWeight = nBExpressWeight.add(noBcmtotal2.getWeightTotal()==null? BigDecimal.ZERO:noBcmtotal2.getWeightTotal());
					}
					
					//查询"电商尊享"的
					waybillStockQueryDto.setProductCode("EPEP");
					//只查快递的数据统计
					WaybillStockStatisticsDto waybillStockStatisticsDtoOnlyExpress3 = new WaybillStockStatisticsDto();
					waybillStockStatisticsDtoOnlyExpress3 = waybillStockDao.queryPriorityGoodsStatistics(waybillStockQueryDto);
					
					//上计泡机运单的重量总和
					WaybillStockStatisticsDto bcmtotal3 =waybillStockDao.queryBcmWaybillPriorityGoodsStatistics(waybillStockQueryDto);
					if(bcmtotal3 !=null){
						bExpressVolume = bExpressVolume.add(bcmtotal3.getVolumeTotal()==null? BigDecimal.ZERO:bcmtotal3.getVolumeTotal());
						bExpressWeight = bExpressWeight.add(bcmtotal3.getWeightTotal()==null? BigDecimal.ZERO:bcmtotal3.getWeightTotal());
					}
					//不上计泡机的运单重量总和
					WaybillStockStatisticsDto noBcmtotal3 =waybillStockDao.queryNoBcmWaybillPriorityGoodsStatistics(waybillStockQueryDto);
					if(noBcmtotal3 !=null){
						nBExpressVolume = nBExpressVolume.add(noBcmtotal3.getVolumeTotal()==null? BigDecimal.ZERO:noBcmtotal3.getVolumeTotal());
						nBExpressWeight = nBExpressWeight.add(noBcmtotal3.getWeightTotal()==null? BigDecimal.ZERO:noBcmtotal3.getWeightTotal());
					}
					
					//查询商务专递的    218381    start...  
					waybillStockQueryDto.setProductCode("DEAP");
					//只查快递的数据统计
					WaybillStockStatisticsDto waybillStockStatisticsDtoOnlyExpress4 = new WaybillStockStatisticsDto();
					waybillStockStatisticsDtoOnlyExpress4 = waybillStockDao.queryPriorityGoodsStatistics(waybillStockQueryDto);
					
					//上计泡机运单的重量总和
					WaybillStockStatisticsDto bcmtotal4 =waybillStockDao.queryBcmWaybillPriorityGoodsStatistics(waybillStockQueryDto);
					if(bcmtotal4 !=null){
						bExpressVolume = bExpressVolume.add(bcmtotal4.getVolumeTotal()==null?BigDecimal.ZERO:bcmtotal4.getVolumeTotal());
						bExpressWeight = bExpressWeight.add(bcmtotal4.getWeightTotal()==null?BigDecimal.ZERO:bcmtotal4.getWeightTotal());
					}
					
					
					//不上计泡机的运单重量总和
					WaybillStockStatisticsDto noBcmtotal4 =waybillStockDao.queryNoBcmWaybillPriorityGoodsStatistics(waybillStockQueryDto);
					if(noBcmtotal4 != null){
						nBExpressVolume = nBExpressVolume.add(noBcmtotal4.getVolumeTotal()==null?BigDecimal.ZERO:noBcmtotal4.getVolumeTotal());
						nBExpressWeight = nBExpressWeight.add(noBcmtotal4.getWeightTotal()==null?BigDecimal.ZERO:noBcmtotal4.getWeightTotal());
					}
					//查询商务专递的    218381    end...  
					
					//将标准快递和特惠件3.60  和电商尊享,商务专递累加
					waybillStockStatisticsDtoOnlyExpress
							.setWaybillQty((waybillStockStatisticsDtoOnlyExpress1 == null ? 0 : waybillStockStatisticsDtoOnlyExpress1 .getWaybillQty())
									+ (waybillStockStatisticsDtoOnlyExpress2 == null ? 0 : waybillStockStatisticsDtoOnlyExpress2.getWaybillQty())
									+ (waybillStockStatisticsDtoOnlyExpress3 == null ? 0 : waybillStockStatisticsDtoOnlyExpress3.getWaybillQty())
									+(waybillStockStatisticsDtoOnlyExpress4 == null ? 0 : waybillStockStatisticsDtoOnlyExpress4.getWaybillQty()));
					waybillStockStatisticsDtoOnlyExpress
							.setStockGoodsQty((waybillStockStatisticsDtoOnlyExpress1 == null ? 0 : waybillStockStatisticsDtoOnlyExpress1.getStockGoodsQty())
									+ (waybillStockStatisticsDtoOnlyExpress2==null? 0 :waybillStockStatisticsDtoOnlyExpress2.getStockGoodsQty())
									+(waybillStockStatisticsDtoOnlyExpress3==null? 0 :waybillStockStatisticsDtoOnlyExpress3.getStockGoodsQty())
									+(waybillStockStatisticsDtoOnlyExpress4==null? 0 :waybillStockStatisticsDtoOnlyExpress4.getStockGoodsQty()));
					waybillStockStatisticsDtoOnlyExpress
							.setWeightTotal((waybillStockStatisticsDtoOnlyExpress1 == null ? BigDecimal.ZERO : waybillStockStatisticsDtoOnlyExpress1.getWeightTotal())
											.add(waybillStockStatisticsDtoOnlyExpress2==null?BigDecimal.ZERO : waybillStockStatisticsDtoOnlyExpress2.getWeightTotal())
											.add(waybillStockStatisticsDtoOnlyExpress3==null?BigDecimal.ZERO : waybillStockStatisticsDtoOnlyExpress3.getWeightTotal())
											.add(waybillStockStatisticsDtoOnlyExpress4==null?BigDecimal.ZERO : waybillStockStatisticsDtoOnlyExpress4.getWeightTotal()));
					waybillStockStatisticsDtoOnlyExpress
							.setVolumeTotal((waybillStockStatisticsDtoOnlyExpress1 == null ? BigDecimal.ZERO : waybillStockStatisticsDtoOnlyExpress1 .getVolumeTotal())
									.add(waybillStockStatisticsDtoOnlyExpress2 ==null?BigDecimal.ZERO : waybillStockStatisticsDtoOnlyExpress2.getVolumeTotal())
									.add(waybillStockStatisticsDtoOnlyExpress3 ==null?BigDecimal.ZERO : waybillStockStatisticsDtoOnlyExpress3.getVolumeTotal())
									.add(waybillStockStatisticsDtoOnlyExpress4 ==null?BigDecimal.ZERO : waybillStockStatisticsDtoOnlyExpress4.getVolumeTotal()));

					//累加end.................
				}else if(StringUtils.equals(waybillStockQueryDto.getProductCode(), "PACKAGE") 
						|| StringUtils.equals(waybillStockQueryDto.getProductCode(), "RCP")
						||StringUtils.equals(waybillStockQueryDto.getProductCode(), "EPEP")
						||StringUtils.equals(waybillStockQueryDto.getProductCode(), "DEAP")){
					
					waybillStockStatisticsDtoOnlyExpress.setWaybillQty(waybillStockStatisticsDto==null?null:waybillStockStatisticsDto.getWaybillQty());
					waybillStockStatisticsDtoOnlyExpress.setStockGoodsQty(waybillStockStatisticsDto==null?null:waybillStockStatisticsDto.getStockGoodsQty());
					waybillStockStatisticsDtoOnlyExpress.setWeightTotal(waybillStockStatisticsDto==null?null:waybillStockStatisticsDto.getWeightTotal());
					waybillStockStatisticsDtoOnlyExpress.setVolumeTotal(waybillStockStatisticsDto==null?null:waybillStockStatisticsDto.getVolumeTotal());
					
					//上计泡机快递运单的重量 和体积
					WaybillStockStatisticsDto bcmtotal =waybillStockDao.queryBcmWaybillPriorityGoodsStatistics(waybillStockQueryDto);
					if(bcmtotal !=null){
						
						bExpressVolume = bExpressVolume.add(bcmtotal.getVolumeTotal()==null?BigDecimal.ZERO:bcmtotal.getVolumeTotal());
						bExpressWeight = bExpressWeight.add(bcmtotal.getWeightTotal()==null?BigDecimal.ZERO:bcmtotal.getWeightTotal());
					}
					
					//不上计泡机的运单重量总和
					WaybillStockStatisticsDto noBcmtotal =waybillStockDao.queryNoBcmWaybillPriorityGoodsStatistics(waybillStockQueryDto);
					if(noBcmtotal !=null){
						nBExpressVolume = nBExpressVolume.add(noBcmtotal.getVolumeTotal()==null? BigDecimal.ZERO:noBcmtotal.getVolumeTotal());
						nBExpressWeight = nBExpressWeight.add(noBcmtotal.getWeightTotal()==null? BigDecimal.ZERO:noBcmtotal.getWeightTotal());
						
					}
				}
				
				
				if(waybillStockStatisticsDtoOnlyExpress != null && waybillStockStatisticsDtoOnlyExpress.getWaybillQty() > 0){
					BigDecimal expressVolume = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
					expressVolume = waybillStockStatisticsDtoOnlyExpress.getVolumeTotal();//拿到快递总体积(方)
					BigDecimal expressWeight = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
					expressWeight = waybillStockStatisticsDtoOnlyExpress.getWeightTotal();//拿到快递总重量（公斤）
					//zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
				    String orgCode = waybillStockQueryDto.getOrgCode();//获取库存部门
				    
					BigDecimal volumeRatio=null;
					try {
						volumeRatio = this.queryStockParameter(orgCode);
					} catch (Exception e) {
						volumeRatio= BigDecimal.ZERO;
					}
					
					BigDecimal newExpressVolume = nBExpressWeight.multiply(volumeRatio).divide(new BigDecimal(1000));
					BigDecimal volumeTotal = waybillStockStatisticsDto.getVolumeTotal();
					BigDecimal weightTotal = waybillStockStatisticsDto.getWeightTotal();
					//查询出的总体积-快递总体积+上计泡机的总体积+没上计泡机换算后的总体积
					waybillStockStatisticsDto.setVolumeTotal(volumeTotal.subtract(expressVolume).add(bExpressVolume).add(newExpressVolume).setScale(three,BigDecimal.ROUND_HALF_DOWN));
					//查询总重量-查询出来的快递总重量+上计泡机的总重量+没有上疾跑机的总重量
					waybillStockStatisticsDto.setWeightTotal(weightTotal.subtract(expressWeight).add(nBExpressWeight).add(bExpressWeight).setScale(three,BigDecimal.ROUND_HALF_DOWN));
				}
		//xiaobc update end.........................
		if(waybillStockStatisticsDto.getWeightTotal() == null){
			waybillStockStatisticsDto.setWeightTotal(new BigDecimal(0));
		}
		if(waybillStockStatisticsDto.getVolumeTotal() == null){
			waybillStockStatisticsDto.setVolumeTotal(new BigDecimal(0));
		}
		return waybillStockStatisticsDto;
	}
	
	/**
	 * 查询入库动作记录
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-17 下午2:34:48
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryInStockInfo(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public List<InOutStockEntity> queryInStockInfo(String waybillNo, String serialNo, String orgCode, Date createBillTime) {

		return inOutStockDao.queryInStockInfo(waybillNo, serialNo, orgCode, createBillTime);
	}
	
	
	/**
	* @param waybillNo
	* @param serialNo
	* @param orgCode
	* @param createBillTime 
	* @return
	* @description 查询小于入库时间的入库动作记录
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-27 下午1:52:19
	*/
	@Override
	public List<InOutStockEntity> queryInStockInfoSmall(String waybillNo,
			String serialNo, String orgCode, Date createBillTime) {
		return inOutStockDao.queryInStockInfoSmall(waybillNo, serialNo, orgCode, createBillTime);
	}



	@Override
	public List<InOutStockEntity> queryOutStockInfo(String waybillNo, String serialNo, String orgCode, Date createBillTime) {

		return inOutStockDao.queryOutStockInfo(waybillNo, serialNo, orgCode, createBillTime);
	}
	
	/**
	 * 开单入库
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-14 下午4:03:43
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#inStockBatchPC(java.util.List)
	 */
	@Override
	public int inStockCreateBill(List<InOutStockEntity> inStockList) throws StockException {

		if (CollectionUtils.isNotEmpty(inStockList)) {
			String waybillNo = inStockList.get(0).getWaybillNO();
			String currentOrgCode = inStockList.get(0).getOrgCode();
			String inStockType = inStockList.get(0).getInOutStockType();
			// 获取库存部门
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService
					.querySimpleOrgAdministrativeInfoByCode(currentOrgCode);

			if (orgInfo == null) {
				LOGGER.error("开单：" + waybillNo + ", 入库库存部门CODE：" + currentOrgCode + " 不存在");
				throw new StockException("库存部门不存在");
			}

			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			// 整车开单
			if (StringUtils.equals(StockConstants.WHOLE_VEHICLE_IN_STOCK_TYPE, inStockType)) {
				Date currentTime = new Date();
				// 外场
				if (StringUtils.equals(FossConstants.YES, orgInfo.getTransferCenter())) {
					for (InOutStockEntity inStock : inStockList) {
						inStock.setNextOrgCode(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
						inStock.setGoodsAreaCode(StockConstants.WHOLE_VEHICLE_GOODS_AREA_CODE);
						inStock.setPlanStartTime(currentTime);
						inStock.setDeviceType(StockConstants.PC_DEVICE_TYPE);
						inStock.setScanTime(currentTime);
					}
					// 营业部
				} else {
					for (InOutStockEntity inStock : inStockList) {
						inStock.setPlanStartTime(currentTime);
						inStock.setDeviceType(StockConstants.PC_DEVICE_TYPE);
						inStock.setScanTime(currentTime);
					}
				}
				// 空运
			} else if (StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT,
					waybillEntity.getProductCode())) {
				String goodsAreaCode = "";
				String nextOrgCode = StockConstants.AIR_FREIGHT_NEXT_ORG_CODE;
				Date currentTime = new Date();
				// 外场
				if (StringUtils.equals(FossConstants.YES, orgInfo.getTransferCenter())) {
					// 空运货区
					try {
						goodsAreaCode = this.queryGoodsAreaByType(orgInfo.getCode(),
								DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
					} catch (BusinessException e) {
						// 查询货区异常 设置成散货货区
						goodsAreaCode = StockConstants.BULK_GOODS_AREA_CODE;
					}
					/******这里的setxxx方法和下面的this.queryPathDetail都是为了拿nextOrgCode，planStartTime********/
					InOutStockEntity tempStock = new InOutStockEntity();
					tempStock = setPlanTimeNextOrgCodeAfterChangePath(tempStock, currentOrgCode, FossConstants.NO);
					nextOrgCode = tempStock.getNextOrgCode();
					currentTime = tempStock.getPlanStartTime();
					// 空运总调
				} else if (StringUtils.equals(FossConstants.YES, orgInfo.getAirDispatch())) {
					currentOrgCode = queryTransferCenterByAirDispatchCode(orgInfo.getCode());
					try {
						goodsAreaCode = this.queryGoodsAreaByType(currentOrgCode,
								DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
					} catch (BusinessException e) {
						// 查询货区异常 设置成散货货区
						goodsAreaCode = StockConstants.BULK_GOODS_AREA_CODE;
					}
				}

				for (InOutStockEntity inStock : inStockList) {
					inStock.setOrgCode(currentOrgCode);
					inStock.setNextOrgCode(nextOrgCode);
					inStock.setGoodsAreaCode(goodsAreaCode);
					inStock.setDeviceType(StockConstants.PC_DEVICE_TYPE);
					inStock.setScanTime(currentTime);
					// 计划出发时间
					inStock.setPlanStartTime(currentTime);
				}

				// 正常开单
			} else {
				// 查询路径，计划时间、下一部门、货区
				FeedbackDto feedbackDto = this.queryPathDetail(waybillNo, null, currentOrgCode);
				String goodsAreaCode = "";
				if (TransportPathConstants.STATUS_RIGHT == feedbackDto.getResult()) {
					PathDetailEntity pathDetail = feedbackDto.getPathDetailEntity();
					String nextOrgCode = pathDetail.getObjectiveOrgCode();
					// 外场
					if (StringUtils.equals(FossConstants.YES, orgInfo.getTransferCenter())) {
						// 根据下一部门得到该外场相应货区编号 综合管理接口
						try {
							goodsAreaCode = this.getGoodsAreaCodeByNextOrgCode(currentOrgCode, nextOrgCode, waybillNo);
						} catch (BusinessException e) {
							// 查询货区异常 设置成散货货区
							goodsAreaCode = StockConstants.BULK_GOODS_AREA_CODE;
						}

						// 空运总调
					} else if (StringUtils.equals(FossConstants.YES, orgInfo.getAirDispatch())) {
						currentOrgCode = queryTransferCenterByAirDispatchCode(orgInfo.getCode());
						try {
							goodsAreaCode = this.queryGoodsAreaByType(currentOrgCode,
									DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
						} catch (BusinessException e) {
							// 查询货区异常 设置成散货货区
							goodsAreaCode = StockConstants.BULK_GOODS_AREA_CODE;
						}
					} else {
						// 其它部门类型，不设置货区
					}

					// 计划出发时间
					Date planStartTime = pathDetail.getPlanStartTime();

					for (InOutStockEntity inStock : inStockList) {
						inStock.setOrgCode(currentOrgCode);
						inStock.setNextOrgCode(nextOrgCode);
						inStock.setGoodsAreaCode(goodsAreaCode);
						inStock.setPlanStartTime(planStartTime);
						inStock.setDeviceType(StockConstants.PC_DEVICE_TYPE);
						inStock.setScanTime(new Date());
					}
				} else {
					LOGGER.info("开单入库：" + waybillNo + ", 查询走货路径失败！");
					// 外场
					if (StringUtils.equals(FossConstants.YES, orgInfo.getTransferCenter())) {
						// 散货货
						goodsAreaCode = StockConstants.BULK_GOODS_AREA_CODE;
						// 空运总调
					} else if (StringUtils.equals(FossConstants.YES, orgInfo.getAirDispatch())) {
						currentOrgCode = queryTransferCenterByAirDispatchCode(orgInfo.getCode());
						try {
							goodsAreaCode = this.queryGoodsAreaByType(currentOrgCode,
									DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
						} catch (BusinessException e) {
							// 查询货区异常 设置成散货货区
							goodsAreaCode = StockConstants.BULK_GOODS_AREA_CODE;
						}
					} else {
						// 其它部门类型，不设置货区
					}

					for (InOutStockEntity inStock : inStockList) {
						inStock.setOrgCode(currentOrgCode);
						inStock.setGoodsAreaCode(goodsAreaCode);
						inStock.setDeviceType(StockConstants.PC_DEVICE_TYPE);
						inStock.setScanTime(new Date());
					}
				}
			}

			@SuppressWarnings("rawtypes")
			Map paramsMap = stockDao.inStockCreateBill(inStockList);
			String exceptionInfo = (String) paramsMap.get("exceptionInfo");
			if (StringUtils.isNotBlank(exceptionInfo)) {
				String excWaybillNo = (String) paramsMap.get("waybillNo");
				String excSerialNo = (String) paramsMap.get("serialNo");
				LOGGER.error("开单入库失败，运单号：" + excWaybillNo + " 流水号：" + excSerialNo + " 异常信息：" + exceptionInfo);
				throw new StockException("开单入库失败，运单号：" + excWaybillNo + " 流水号：" + excSerialNo + " 异常信息："
						+ exceptionInfo, "");
			}

			return FossConstants.SUCCESS;
		} else {
			LOGGER.warn("开单入库，参数为空");
			return FossConstants.FAILURE;
		}
	}
	
	/**
	 * 批量出库
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-26 上午10:39:40
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#outStockBatchPC(java.util.List)
	 */
	@Override
	public int outStockBatchPC(List<InOutStockEntity> outStockList) throws StockException{
		@SuppressWarnings("rawtypes")
		Map paramsMap = stockDao.outStockBatchPC(outStockList);
		String exceptionInfo = (String)paramsMap.get("exceptionInfo");
		if(StringUtils.isNotBlank(exceptionInfo)){
			String waybillNo = (String)paramsMap.get("waybillNo");
			String serialNo = (String)paramsMap.get("serialNo");
			//货物不在库存中
			if(StringUtils.equals(StockConstants.STOCK_NOT_EXIST, exceptionInfo)){
				LOGGER.error("出库失败：运单号：" + waybillNo + " 流水号：" + serialNo + " 不在库存中");
				throw new StockException(StockException.GOODS_NOT_EXIST_STOCK_ERROR_CODE,new Object[]{waybillNo,serialNo});
			//货物在特殊货区
			}else if(StringUtils.equals(StockConstants.SPECIAL_GOODSAREA, exceptionInfo)){
				LOGGER.error("出库失败：运单号：" + waybillNo + " 流水号：" + serialNo + " 在特殊货区中");
				throw new StockException(StockException.GOODS_IN_SPECIAL_AREA_ERROR_CODE,new Object[]{waybillNo,serialNo});
			}else{
				LOGGER.error("出库失败：运单号：" + waybillNo + " 流水号：" + serialNo + "," + exceptionInfo);
				//throw new StockException(StockException.OUT_STOCK_FAILURE_ERROR_CODE,new Object[]{waybillNo,serialNo});
				throw new StockException("出库失败：运单号：" + waybillNo + " ,流水号：" + serialNo + ",异常：" + exceptionInfo);
			}
		}
		return FossConstants.SUCCESS;
	}
	/**
	 * 按照类型批量出库 
	 * （装车）LOADING，（交接单）HANDOVER 
	 * id (交接单时：交接单号，装车时：taskId)
	* @return: int
	* @author: Xingmin , 2016-9-23 上午11:00:46
	 */
	public int outStockBatchPCByType(List<InOutStockEntity> outStockList, String type, String id) throws StockException{
		@SuppressWarnings("rawtypes")
		Map paramsMap = stockDao.outStockBatchPCByType(outStockList,type,id);
		
		String exceptionInfo = (String)paramsMap.get("exceptionInfo");
		if(StringUtils.isNotBlank(exceptionInfo)){
			String waybillNo = (String)paramsMap.get("waybillNo");
			String serialNo = (String)paramsMap.get("serialNo");
			//货物不在库存中
			if(StringUtils.equals(StockConstants.STOCK_NOT_EXIST, exceptionInfo)){
				LOGGER.error("出库失败：运单号：" + waybillNo + " 流水号：" + serialNo + " 不在库存中");
				throw new StockException(StockException.GOODS_NOT_EXIST_STOCK_ERROR_CODE,new Object[]{waybillNo,serialNo});
				//货物在特殊货区
			}else if(StringUtils.equals(StockConstants.SPECIAL_GOODSAREA, exceptionInfo)){
				LOGGER.error("出库失败：运单号：" + waybillNo + " 流水号：" + serialNo + " 在特殊货区中");
				throw new StockException(StockException.GOODS_IN_SPECIAL_AREA_ERROR_CODE,new Object[]{waybillNo,serialNo});
			}else{
				LOGGER.error("出库失败：运单号：" + waybillNo + " 流水号：" + serialNo + "," + exceptionInfo);
				//throw new StockException(StockException.OUT_STOCK_FAILURE_ERROR_CODE,new Object[]{waybillNo,serialNo});
				throw new StockException("出库失败：运单号：" + waybillNo + " ,流水号：" + serialNo + ",异常：" + exceptionInfo);
			}
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 调整外场库存到派送货区库存
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-9 下午2:24:55
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#adjustStockToStation(java.lang.String, java.lang.String, java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public void adjustStockToStation(String waybillNo, String orgCode, List<String> serialNoList, String userCode, String userName){
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(orgCode);
		if(saleDepartmentEntity == null){
			LOGGER.error("调整外场库存到派送货区库存,部门不存在:" + orgCode);
			throw new StockException("库存部门不存在");
		}else{
			if(!StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())){
				LOGGER.error("调整外场库存到派送货区库存,部门不是驻地派送部:" + orgCode);
				throw new StockException(StockException.NOT_STATION_ORG_ERROR_CODE, new Object[]{orgCode});
			}else{
				//查询驻地派送部所在外场
				String transferCode = saleDepartmentEntity.getTransferCenter();
				//派送货区
				//String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				//设置入库参数
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				inOutStockEntity.setWaybillNO(waybillNo);
				inOutStockEntity.setOrgCode(transferCode);
				inOutStockEntity.setOperatorCode(userCode);
				inOutStockEntity.setOperatorName(userName);
				//把上面找货区code的方法,放到这里来,参数改为 InOutStockEntity
				String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				inOutStockEntity.setInOutStockType(StockConstants.ADJUST_STOCK_TO_STATION_IN_STOCK_TYPE);
				//设置操作出库设备类型
				inOutStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
				//设置当前调用时间为扫描时间
				inOutStockEntity.setScanTime(new Date());
				
				if(CollectionUtils.isNotEmpty(serialNoList)){
					for(String serialNo : serialNoList){
						inOutStockEntity.setSerialNO(serialNo);
						//入库
						this.inStock(inOutStockEntity);
					}
				}
			}
			
		}
		
	}
	/**
	 * 反签收批量入库
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-13 下午2:11:34
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#reverseSignInStock(java.util.List)
	 */
	@Override
	public int inStockReverseSign(List<InOutStockEntity> inStockList){
		if(CollectionUtils.isNotEmpty(inStockList)){
			String currentOrgCode = inStockList.get(0).getOrgCode();
			String goodsAreaCode = "";
			//获取库存部门
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = this.verifyStockOrg(currentOrgCode);
			//外场
			if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
				//货区inOutStockEntity
				InOutStockEntity inOutStockEntity = inStockList.get(0);
				//派送货区
				goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
//				goodsAreaCode = this.queryGoodsAreaByType(orgAdministrativeInfoEntity.getCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
			//空运总调
			}else if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getAirDispatch())){
				currentOrgCode = queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
				goodsAreaCode = this.queryGoodsAreaByType(currentOrgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
			}else{
				//其它部门类型，不设置货区
			}
			for(InOutStockEntity inStock : inStockList){
				inStock.setOrgCode(currentOrgCode);
				inStock.setGoodsAreaCode(goodsAreaCode);
				inStock.setDeviceType(StockConstants.PC_DEVICE_TYPE);
				inStock.setScanTime(new Date());
			}
			
			@SuppressWarnings("rawtypes")
			Map paramsMap = stockDao.inStockReverseSign(inStockList);
			String exceptionInfo = (String)paramsMap.get("exceptionInfo");
			if(StringUtils.isNotBlank(exceptionInfo)){
				String excWaybillNo = (String)paramsMap.get("waybillNo");
				String excSerialNo = (String)paramsMap.get("serialNo");
				LOGGER.error("反签收入库失败，运单号："+ excWaybillNo + " 流水号：" + excSerialNo +" 异常信息：" + exceptionInfo);
				throw new StockException("反签收入库失败，运单号："+ excWaybillNo + " 流水号：" + excSerialNo +" 异常信息：" + exceptionInfo,"");
			}
		}else{
			LOGGER.warn("反签收入库，参数为空");
			return FossConstants.FAILURE;
		}
		return FossConstants.SUCCESS;
	}
	/**
	 * 验证库存部门
	 * 如果是驻地部门则返回所在外场部门
	 * @param currentOrgCode 部门编号
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-13 下午5:00:04
	 */
	private OrgAdministrativeInfoEntity verifyStockOrg(String currentOrgCode){
		//获取库存部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentOrgCode);
		if(orgAdministrativeInfoEntity != null){
			//营业部
			if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getSalesDepartment())){
				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(currentOrgCode);
				//驻地部门 
				if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())){
					//设置驻地营业部所在外场为库存部门
					orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDepartmentEntity.getTransferCenter());
				}
			//外场或空运总调
			}else if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter()) ||
						StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getAirDispatch())){
				//
			}else{
				//获取当前库存部门  外场或空运总调
				orgAdministrativeInfoEntity = this.getBigOrg(currentOrgCode);
			}
		}else{//部门不存在
			LOGGER.error("部门：" + currentOrgCode + " 不存在");
			throw new StockException("库存部门不存在");
		}
		return orgAdministrativeInfoEntity;
	}
	
	/**
	 * 移除货件
	 * 1.货件在库存中：
	 * 		出库、删除走货路径
	 * 2.货件不在库存中：
	 *      不做任何操作
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-14 下午5:58:14
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#outStockInvalidGoods(java.lang.String, java.util.List)
	 */
	@Override
	public int outStockInvalidGoods(String waybillNo, List<String> serialNosList,String orgCode,String operatorCode,String operatorName){
		
		if(CollectionUtils.isNotEmpty(serialNosList)){
			List<InOutStockEntity> outStockList = new ArrayList<InOutStockEntity>();
			for(String serialNo : serialNosList){
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				inOutStockEntity.setWaybillNO(waybillNo);
				inOutStockEntity.setSerialNO(serialNo);
				inOutStockEntity.setOrgCode(orgCode);
				inOutStockEntity.setOperatorCode(operatorCode);
				inOutStockEntity.setOperatorName(operatorName);
				inOutStockEntity.setInOutStockType(StockConstants.INVALID_GOODS_OUT_STOCK_TYPE);
				outStockList.add(inOutStockEntity);
			}
			@SuppressWarnings("rawtypes")
			Map paramsMap = stockDao.outStockInvalidGoods(outStockList);
			String exceptionInfo = (String)paramsMap.get("exceptionInfo");
			if(StringUtils.isNotBlank(exceptionInfo)){
				String excWaybillNo = (String)paramsMap.get("waybillNo");
				String excSerialNo = (String)paramsMap.get("serialNo");
				LOGGER.error("移除货件失败，运单号："+ excWaybillNo + " 流水号：" + excSerialNo +" 异常信息：" + exceptionInfo);
				throw new StockException(StockException.INVALID_GOODS_FAILURE_ERROR_CODE,new Object[]{excWaybillNo,excSerialNo});
			}
			
		}else{
			LOGGER.warn("移除货件，参数为空");
			return FossConstants.FAILURE;
		}
		
		return FossConstants.SUCCESS;
	}
	/**
	 * 提供给结算模块：到付清查报表-查询库存状态
	 * 1：通过运单号，查询少货表（卸车／清仓少货）
	 *	     存在少货未找到，则：【库存少货】
	 * 2：通过运单号，查签收结果表 签收状态：
	 * 		正常签收：【正常签收】
	 *      其它状态：【异常签收】
	 * 3：通过运单号/到达部门，查库存表
	 *	          不存在 ：【未入库】
	 *	          存在：
	 *	              如果库存件数=开单件数：【库存中】
	 *	              如果库存件数！=开单件数：【库存异常】
	 * @param waybillNo 运单号
	 * @param createBillGoodsQty 开单件数
	 * @param arriveOrgCode 到达部门
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 上午11:56:52
	 */
	@Override
	public String queryWaybillStockStatus(String waybillNo,
			int createBillGoodsQty, String arriveOrgCode) {
		//查询卸车少货
		UnloadDiffReportDetailEntity detailEntity = new UnloadDiffReportDetailEntity();
		detailEntity.setWaybillNo(waybillNo);
		List<UnloadDiffReportDetailEntity> unloadDiffList= unloadDiffReportService.queryUnresolvedLackGoodsException(detailEntity);
		if(CollectionUtils.isNotEmpty(unloadDiffList)){
			return StockConstants.LOSE_STOCK_STATUS;
		}
		//查询清仓少货
		List<StDifferDetailEntity> stDiffList = stReportService.queryStDifferDetailListByWaybillNo(waybillNo);
		if(CollectionUtils.isNotEmpty(stDiffList)){
			return StockConstants.LOSE_STOCK_STATUS;
		}
		//查询签收结果
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		//设置查询参数
		entity.setWaybillNo(waybillNo);
		entity.setActive(FossConstants.YES);
		WaybillSignResultEntity waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
		if(waybillSignResultEntity != null){
			//正常签收
			if(StringUtils.equals(SignConstants.NORMAL_SIGN, waybillSignResultEntity.getSignSituation())){
				return StockConstants.NORMAL_SIGN_STOCK_STATUS;
		    //异常签收
			}else{
				return StockConstants.UNNORMAL_SIGN_STOCK_STATUS;
			}
		}
		
		//查询库存
		List<WaybillStockEntity> waybillStockList = waybillStockDao.queryWaybillStockByWaybillOrgCode(waybillNo, arriveOrgCode);
		if(CollectionUtils.isNotEmpty(waybillStockList)){
			int goodsStockQty = 0;
			//遍历运单库存 累加库存件数
			for(WaybillStockEntity waybillStockEntity : waybillStockList){
				goodsStockQty += waybillStockEntity.getStockGoodsCount();
			}
			//库存中
			if(goodsStockQty == createBillGoodsQty){
				return StockConstants.IN_STOCK_STOCK_STATUS;
			//库存异常	
			}else{
				return StockConstants.EXCEPTION_STOCK_STOCK_STATUS;
			}
		//未入库	
		}else{
			/**
			 * BUG-50196 
			 * 到付明细报表库存状态错误  begin
			 */
			try{
				//营业部
				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(arriveOrgCode);
				//驻地部门 
				if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())){
					//设置驻地营业部所在外场为库存部门
					arriveOrgCode = saleDepartmentEntity.getTransferCenter();
				}
			}catch (Exception e) {
				//arriveOrgCode值不变，继续后面的逻辑
			}
			
			List<WaybillStockEntity> waybillStockListTemp = waybillStockDao.queryWaybillStockByWaybillOrgCode(waybillNo, arriveOrgCode);
			if(CollectionUtils.isNotEmpty(waybillStockListTemp)){
				int goodsStockQty = 0;
				//遍历运单库存 累加库存件数
				for(WaybillStockEntity waybillStockEntity : waybillStockListTemp){
					goodsStockQty += waybillStockEntity.getStockGoodsCount();
				}
				//库存中
				if(goodsStockQty == createBillGoodsQty){
					return StockConstants.IN_STOCK_STOCK_STATUS;
				//库存异常	
				}else{
					return StockConstants.EXCEPTION_STOCK_STOCK_STATUS;
				}
			/**
			 * BUG-50196 
			 * 到付明细报表库存状态错误  end
			 */
			//未入库	
			}else{
				return StockConstants.NO_STOCK_STOCK_STATUS;
			}
		}
		
	}
	/**
	 * 运单在出发部门库存，且未做交接单时，更改运单号
	 * 更改中转相关数据：
	 * 1.库存数据
	 * 2.走货路径数据
	 * 3.包装信息
	 * @param oldWaybillNo 原运单号
	 * @param newWaybillNo 新运单号
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午4:25:42
	 */
	@Override
	public void modifyWaybillNo(String oldWaybillNo, String newWaybillNo, Date createBillTime) {
		//更新运单库存
		waybillStockDao.updateWaybillNo(oldWaybillNo, newWaybillNo);
		//更新库存
		stockDao.updateWaybillNo(oldWaybillNo, newWaybillNo);
		//更新入库记录
		inOutStockDao.updateWaybillNoInStock(oldWaybillNo, newWaybillNo, createBillTime, StockConstants.MODIFY_WAYBILL_NO_IN_STOCK_TYPE);
		//更新出库记录
		inOutStockDao.updateWaybillNoOutStock(oldWaybillNo, newWaybillNo, createBillTime, StockConstants.MODIFY_WAYBILL_NO_IN_STOCK_TYPE);
		//更新走货路径
			calculateTransportPathService.modifyWaybillNo(oldWaybillNo, newWaybillNo);
		//更新包装信息
		packOutService.updateWaybillNo(oldWaybillNo, newWaybillNo);
	}
	
	
	/**
	 * DEFECT-2021
			整车运单更改运单号，在审核该更改单时提示“查询不到走货路径”  
	 * 运单在出发部门库存，且未做交接单时，更改运单号
	 * 更改中转相关数据：
	 * 1.库存数据
	 * 2.走货路径数据
	 * 3.包装信息
	* @description 用一句话说明这个方法做什么
	* @param oldWaybillNo 原运单号
	* @param newWaybillNo 新运单号
	* @param createBillTime 开单时间
	* @param ProductCode 运输性质
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月11日 下午4:43:01
	*/
	@Override
	public void modifyWaybillNo(String oldWaybillNo, String newWaybillNo,
		Date createBillTime, String productCode) {
		//更新运单库存
		waybillStockDao.updateWaybillNo(oldWaybillNo, newWaybillNo);
		//更新库存
		stockDao.updateWaybillNo(oldWaybillNo, newWaybillNo);
		//更新入库记录
		inOutStockDao.updateWaybillNoInStock(oldWaybillNo, newWaybillNo, createBillTime, StockConstants.MODIFY_WAYBILL_NO_IN_STOCK_TYPE);
		//更新出库记录
		inOutStockDao.updateWaybillNoOutStock(oldWaybillNo, newWaybillNo, createBillTime, StockConstants.MODIFY_WAYBILL_NO_IN_STOCK_TYPE);
		
		//整车要修改走货路径
		/**DEFECT-2021
			整车运单更改运单号，在审核该更改单时提示“查询不到走货路径”  
		*/
		if(!StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE, productCode)){
			//更新走货路径
			calculateTransportPathService.modifyWaybillNo(oldWaybillNo, newWaybillNo);
		}
		//更新包装信息
		packOutService.updateWaybillNo(oldWaybillNo, newWaybillNo);
	}



	/**
	 * 查询运单库存明细
	 * @author 097457-foss-wangqiang
	 * @date 2013-4-8 上午9:22:43
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryWaybillStockDetail(java.lang.String)
	 */
	@Override
	public List<WaybillStockDetailDto> queryWaybillStockDetail(String waybillNo) {
		
		List<WaybillStockDetailDto> list = stockDao.queryWaybillStockDetail(waybillNo);
		if(CollectionUtils.isNotEmpty(list)){
			for(WaybillStockDetailDto stockDetailDto : list){
				if(StringUtils.isBlank(stockDetailDto.getStockId())){
					stockDetailDto.setStockStatus(FossConstants.NO);
					List<WaybillStockDetailDto> outStockList = inOutStockDao.queryGoodsOutStock(stockDetailDto.getWaybillNo(), stockDetailDto.getSerialNo(), stockDetailDto.getCreateBillTime());
					if(CollectionUtils.isNotEmpty(outStockList)){
						WaybillStockDetailDto outStock = outStockList.get(0);
						stockDetailDto.setInOutStockTime(outStock.getInOutStockTime());
						stockDetailDto.setOperatorName(outStock.getOperatorName());
						stockDetailDto.setUnifiedOrgCode(outStock.getUnifiedOrgCode());
						stockDetailDto.setUnifiedOrgName(outStock.getUnifiedOrgName());
					}
				}else{
					stockDetailDto.setStockStatus(FossConstants.YES);
				}
			}
		}
		
		return list;
		
	}
	/**
	 * 发送出库通知
	 * @author 097457-foss-wangqiang
	 * @date 2013-4-16 上午11:53:52
	 */
	public void sentMsg(InOutStockEntity inOutStockEntity, String originalOrgCode){
		
		//查询部门实体
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(inOutStockEntity.getOrgCode());
		String orgName = orgAdministrativeInfoEntity.getName();
		SmsParamDto smsParamDto = new SmsParamDto();
		Map<String,String> smsParam = new HashMap<String,String>();
		smsParam.put("waybillNo", inOutStockEntity.getWaybillNO());
		smsParam.put("serialNo", inOutStockEntity.getSerialNO());
		smsParam.put("inStockTime", DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT));
		smsParam.put("orgName", orgName);
		smsParamDto.setMap(smsParam);
		smsParamDto.setOrgCode(null);
		smsParamDto.setSmsCode("TFR_OUT_STOCK_NOTICE");
		String msg = sMSTempleteService.querySmsByParam(smsParamDto);
		if(StringUtils.isNotBlank(msg)){
			//设置消息参数
			InstationJobMsgEntity msgEntity = new InstationJobMsgEntity();
			//消息ID
			msgEntity.setId(UUIDUtils.getUUID());
			//发送人工号
			msgEntity.setSenderCode(inOutStockEntity.getOperatorCode());
			//发送人姓名
			msgEntity.setSenderName(inOutStockEntity.getOperatorName());
			//发送部门编号
			msgEntity.setSenderOrgCode(inOutStockEntity.getOrgCode());
			
			//发送部门名称
			msgEntity.setSenderOrgName(orgName);
			//接收部门编号
			msgEntity.setReceiveOrgCode(originalOrgCode);
		    //消息内容
			msgEntity.setContext(msg);
			//接受者类型
			msgEntity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
			//消息类型
			msgEntity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
			//发送时间
			msgEntity.setPostDate(new Date());
			//发送消息
			messageService.createBatchInstationMsg(msgEntity);
		}else{
			LOGGER.warn("获取出库通知模板失败！");
		}
	}
	/**
	 * 查询空运总调对应外场
	 * @author 097457-foss-wangqiang
	 * @date 2013-4-20 上午11:47:36
	 */
	private String queryTransferCenterByAirDispatchCode(String airDispatchCode){
		String transferCode = outfieldService.queryTransferCenterByAirDispatchCode(airDispatchCode);
		if(StringUtils.isBlank(transferCode)){
			throw new StockException("查询空运总调对应外场失败！","");
		}
		return transferCode;
	}

	@Override
	public InputStream exportPriorityGoods(WaybillStockQueryDto waybillStockQueryDto) {
		
		InputStream excelStream = null;
		
		List<WaybillStockQueryDto> list = waybillStockDao.queryPriorityGoods(waybillStockQueryDto);
		
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(WaybillStockQueryDto dto : list){

			List<String> columnList = new ArrayList<String>();
			//如果重量或体积未填写,则设置为0
			if(dto.getWeightTotal().equals("null")||dto.getWeightTotal().equals(" ")){
				dto.setWeightTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
			}
			if(dto.getVolumeTotal().equals("null")||dto.getVolumeTotal().equals(" ")){
				dto.setVolumeTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
			}
			
			columnList.add(dto.getWaybillNO());
			columnList.add(dto.getGoodsAreaName());
			columnList.add(dto.getWaybillGoodsCount().toString());
			columnList.add(dto.getStockGoodsCount().toString());
			columnList.add(dto.getGoodsName());
			columnList.add(dto.getPackageType());
			columnList.add(dto.getProductCode());
			columnList.add(dto.getWeightTotal().toString());
			columnList.add(dto.getVolumeTotal().toString());
			columnList.add(dto.getDepartureCode());
			columnList.add(dto.getNextOrgCode());
			columnList.add(dto.getReceiveOrgCode());
			columnList.add(DateUtils.convert(dto.getInStockTime(), DateUtils.DATE_TIME_FORMAT));
			columnList.add(dto.getInStockDuration().toString());
			
			rowList.add(columnList);
		}
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(StockConstants.ROW_HEADS_PRIORITYGOODS);
		sheetData.setRowList(rowList);
		
		ExcelExport excelExportUtil = new ExcelExport();
		excelStream = excelExportUtil.inputToClient(excelExportUtil.exportExcel(sheetData, StockConstants.SHEET_NAME, StockConstants.SHEET_SIZE));
		
        return excelStream;
	}
	
	/**
	 * 根据库存信息查询运单是否分批
	 * @return return FossConstants.YES 分批
	 * 		   return FossConstants.NO 未分批
	 * 		   null 库存没有该票数据
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-27 上午10:44:49
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#querySeparateStatusByStock(java.lang.String)
	 */
	@Override
	public String querySeparateStatusByStock(String waybillNo) {
		List<String> stockOrgList = waybillStockDao.queryStockOrgCodeByWaybillNo(waybillNo);
		if(CollectionUtils.isNotEmpty(stockOrgList)){
			if(stockOrgList.size() == 1){
				List<Integer> stockQtyList = waybillStockDao.queryStockQtyByWaybillNo(waybillNo);
				if(CollectionUtils.isNotEmpty(stockQtyList)){
					Integer stockQty = stockQtyList.get(0);
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
					if(waybillEntity.getGoodsQtyTotal() == stockQty){
						return FossConstants.NO;
					}else{
						return FossConstants.YES;
					}
				}else{
					return FossConstants.YES;
				}
			}else{
				return FossConstants.YES;
			}
		}else{
			return null;
		}
		
	}
	/**
	 * 判断当前用户部门是否驻地派送部
	 * @author 097457-foss-wangqiang
	 * @date 2013-6-9 下午4:27:41
	 */
	private boolean isStaionDelivery(InOutStockEntity inOutStockEntity,String currentOrgCode){
		boolean isStationDelivery = false;
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = null;
		if(StringUtils.equals(StockConstants.PDA_DEVICE_TYPE, inOutStockEntity.getDeviceType())){
			//获取库存部门
			orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentOrgCode);
		}else{
			orgAdministrativeInfoEntity = FossUserContext.getCurrentDept();
		}
		
		//营业部
		if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getSalesDepartment())){
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(orgAdministrativeInfoEntity.getCode());
			//驻地部门  可到达
			if (saleDepartmentEntity != null
					&& StringUtils.isNotEmpty(saleDepartmentEntity.getStation())
					&& StringUtils.isNotEmpty(saleDepartmentEntity.getArrive())
					&& StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())
					&& StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getArrive())) {
				// 驻地派送部
				isStationDelivery = true;
			}
		}
		return isStationDelivery;
	}
	
	/**
	* 
	* @description 更新走货路径状态 如果更新失败 只记录日志
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-6-21 下午5:59:29
	*/
	private void changeTransportPathStatus(InOutStockEntity inOutStockEntity,String currentOrgCode){
		try{
			calculateTransportPathService.inStorage(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO(), currentOrgCode, TransportPathConstants.TRANSPORTPATH_STATUS_INSTORE);
		}catch (Exception e) {
			LOGGER.warn("更新走货路径状态失败---运单号：" + inOutStockEntity.getWaybillNO()+ " 流水号：" + inOutStockEntity.getSerialNO() + " 异常信息：" + ExceptionUtils.getFullStackTrace(e));
		}
		
	}
	
	/**
	* @param inOutStockEntity 运单号
	* @param currentOrgCodeComm 流水号
	* @description 更新走货路径的状态为入库。如果异常只记录日志
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-6-26 下午5:35:41
	*/
	private void updateTransportPathActionForInstoreComm(InOutStockEntity inOutStockEntity,OrgAdministrativeInfoEntity orgAdministrativeInfoEntity){
		String currentOrgCodeComm;
			//标识是否是驻地部门
			currentOrgCodeComm = inOutStockEntity.getOrgCode();
			if(orgAdministrativeInfoEntity != null){
				//营业部
				if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getSalesDepartment())){
					//营业部
					//驻地部门 
				}else if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter()) ||
							StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getAirDispatch())){
					//外场或空运总调
				}else{
					//获取当前库存部门  外场或空运总调
					currentOrgCodeComm = orgAdministrativeInfoEntity.getCode();
				}
			}else{//部门不存在
				//部门不存在
			}
		try{
			calculateTransportPathService.updateTransportPathActionForInstore(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO(), currentOrgCodeComm);
		}catch (Exception e) {
			LOGGER.warn("更新走货路径的状态为入库。失败---INSTORE  运单号：" + inOutStockEntity.getWaybillNO()+ " 流水号：" + inOutStockEntity.getSerialNO()+"部门编号："+currentOrgCodeComm+ " 异常信息：" + ExceptionUtils.getFullStackTrace(e));
		}
		
	}

	/**
	* @param waybillNo 运单号
	* @param orgCode 当前部门编号
	* @return  有记录：Y  无记录：N
	* @description 根据运单号和当前部门编号查询库存
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-6-22 上午11:32:02
	*/
	@Override
	public String queryStockByWaybillNoOrgCode(String waybillNo, String orgCode) {
		List<StockEntity> stockList = stockDao.queryStockByWaybillNoOrgCode(waybillNo, orgCode);
		if(stockList!=null&&stockList.size()>0){
			return FossConstants.YES;
		}else{
			return FossConstants.NO;
		}
	}
	
	/**
	* @param inOutStockEntity
	* @param type:1、in;2、out
	* @description 出入库类型是 入库包装货区时  入库或出库结束后 需要调用 包装的对应接口 insertWaybillNoLogingDate
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-4 下午2:33:25
	*/
	private void packInOutServerComm(InOutStockEntity inOutStockEntity,String type){
		WaybillNoLogingDateDto waybillNoLogingDateDto = new WaybillNoLogingDateDto();
		waybillNoLogingDateDto.setWaybillNo(inOutStockEntity.getWaybillNO());
		waybillNoLogingDateDto.setLogingDate(new Date());
		waybillNoLogingDateDto.setType(type);
		packOutService.insertWaybillNoLogingDate(waybillNoLogingDateDto);
	}
	/**
	* @param inStockList 运单号、流水号、货区编码、部门编号、库位 不能为空
	* @param orgCode 修改库位的部门编号
	* @param userCode 修改人code
	* @param userName 修改人名称
	* @return
	* @throws StockException 修改库存表的库位
	* @description 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-10 上午8:22:33
	*/
	@Override
	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateStockStockPosition(List<InOutStockEntity> inStockList,String position,String orgCode,String userCode,String userName) throws StockException {
		if(position==null || position.trim().length()==0){
			throw new StockException("库位不能为空","");
		}
		
		if(CollectionUtils.isNotEmpty(inStockList)){
			boolean  checkAreaFalg = false;
			boolean  checkTranFlag = false;//事物是否有回滚
			String msgStr = "";
			try {
				for (InOutStockEntity stockEntity : inStockList) {
					//根据运单和流水号查询对应的库存
					List<StockEntity>  tmpList = stockDao.queryUniqueStock(stockEntity);
					if(tmpList!=null&&tmpList.size()>0){
						StockEntity pojoDb = tmpList.get(0);
						//查询当前部门的驻地派送部获取
						String goodsAreaCode = this.queryGoodsAreaByEntity(stockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
			//			String goodsAreaCode = this.queryGoodsAreaByType(pojoDb.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
						if(StringUtils.isNotBlank(goodsAreaCode)){
							//如果和库存里的获取一致 ；都是驻地派送部货区
							if(pojoDb.getGoodsAreaCode().equals(goodsAreaCode)){
								stockEntity.setGoodsAreaCode(pojoDb.getGoodsAreaCode());//从库存中获取 货区Code
								stockEntity.setOperatorCode(userCode);//修改人code
								stockEntity.setOperatorName(userName);//修改人名称
//								stockEntity.setOrgCode(orgCode);//修改库位的部门编号
								stockEntity.setOrgCode(pojoDb.getOrgCode());//库存的部门编号
								stockEntity.setInOutStockType(StockConstants.POSITION_MOD);
								if(StringUtils.isBlank(stockEntity.getDeviceType())){
									stockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
								}
								stockEntity.setInOutStockTime(new Date());
								stockEntity.setPosition(position);//库位
								//mod by songjie BUG-53677 begin
								try{
									updateStockStockPositionComm(stockEntity);
								}catch (Exception e) {
									LOGGER.warn("更新库位失败。异常信息：" + ExceptionUtils.getFullStackTrace(e));
									checkTranFlag = true;
									msgStr +="运单号:"+stockEntity.getWaybillNO()+"流水号:"+stockEntity.getSerialNO()+" ";
								}
								//mod by songjie BUG-53677 end
							}else{
								checkAreaFalg = true;
								break;
							}
						}else{
							checkAreaFalg = true;
							break;
						}
					}
					
				}
			} catch (Exception e) {
				LOGGER.warn("更新库位失败。异常信息：" + ExceptionUtils.getFullStackTrace(e));
				//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				throw new StockException("更新库位失败。","");
			} finally{
				if(checkAreaFalg){
					throw new StockException("库位更新只对驻地派送部货区开放");
				}
				if(checkTranFlag){
					throw new StockException(msgStr+"更新库位失败。","");
				}
			}
		}
	}
	
	/**
	* @param stockEntity
	* @description 更新库位时需要做事物处理
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-9-4 下午4:51:09
	*/
	@Transactional
	private void updateStockStockPositionComm(InOutStockEntity stockEntity){
			this.stockAreaLogDao.updateStockStockPosition(stockEntity);
			this.stockAreaLogDao.insertStockAreaLog(stockEntity);
			this.stockAreaLogDao.updateWaybillStockOutStockPosition(stockEntity);
	}
	
	/**
	* @param orgCode
	* @return
	* @description 根据外场编码查询对应的货区(驻地派送部)
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-9 上午10:50:45
	*/
	@Override
	public List<BaseDataDictDto>  queryGoodsAreaListByOrganizationCode(
			String orgCode) {
		List<BaseDataDictDto> specialAreaList = new ArrayList<BaseDataDictDto>();
		//驻地派送部的货区
		List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
		if(goodsAreaList!=null&&goodsAreaList.size()>0){
			for (GoodsAreaEntity goodsAreaEntity : goodsAreaList) {
				BaseDataDictDto bdd = new BaseDataDictDto();
					bdd.setValueCode(goodsAreaEntity.getGoodsAreaCode());
					bdd.setValueName(goodsAreaEntity.getGoodsAreaName());
					specialAreaList.add(bdd);
			}
		}
		return specialAreaList;
	}
	
	/**
	* @param orgCode 外场编号
	* @param goodsAreaCode 货区code
	* @return
	* @description 获取对应的库位集合
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-8 上午11:00:19
	*/
	@Override
	public List<BaseDataDictDto> queryStorageListByGoodsAreaFrom(String orgCode,
			String goodsAreaCode) {
		GoodsAreaEntity goodsAreaEntity = goodsAreaService.queryGoodsAreaByCode(orgCode, goodsAreaCode);
		List<StorageEntity> storageList = storageService.queryStorageListByGoodsAreaFromCache(orgCode, goodsAreaEntity.getVirtualCode());
		List<BaseDataDictDto> specialAreaList = new ArrayList<BaseDataDictDto>();
		if(storageList!=null&&storageList.size()>0){
			for (StorageEntity storageEntity : storageList) {
				BaseDataDictDto bdd = new BaseDataDictDto();
				bdd.setValueCode(storageEntity.getStorageCode());
				bdd.setValueName(storageEntity.getStorageCode());
				specialAreaList.add(bdd);
			}
		}
		return specialAreaList;
	}
	/**
	* @param goodsAreaCode
	* @param orgCode
	* @return true 货区是 驻地派送部的货区
	* @description 检验货区是否是驻地派送部的货区
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-10 下午3:10:04
	*/
	@Override
	public boolean checkGoodsArea(String goodsAreaCode, String orgCode) {
		boolean backFlage = false;
		//驻地派送部的货区
		List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
		if(goodsAreaList!=null && goodsAreaList.size()>0){
			for (GoodsAreaEntity goodsAreaEntity : goodsAreaList) {
				if(StringUtils.equals(goodsAreaEntity.getGoodsAreaCode(), goodsAreaCode)){
					//验证货区是 驻地派送部的货区
					backFlage=true;
					break;
				}
			}
		}		
		return backFlage;
	}
	
	/**
	* @param inOutStockEntity
	* @param currentOrgCode
	* @return
	* @description 调用走货路径的接口  根据走货路径设置下一部门code 和 计划出发时间
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-11 下午3:47:37
	*/
	private InOutStockEntity goodsWalkPath(InOutStockEntity inOutStockEntity,String currentOrgCode,String confirmFlag,String checkGoodsArea){
		try{
			stockService.goodsWalkPathTransactional(inOutStockEntity,currentOrgCode,confirmFlag,checkGoodsArea);
		}catch(Exception e){
			LOGGER.error("调用走货路径接口：" + inOutStockEntity.getOrgCode()+ " 货物：" + inOutStockEntity.getWaybillNO() + "---" + inOutStockEntity.getSerialNO() +
									"，查询走货路径或设置货区失败，入库到虚拟散货货区，异常信息：" + ExceptionUtils.getFullStackTrace(e));
			if(StringUtils.equals(FossConstants.NO, checkGoodsArea)){
				//不需要设置货区
				inOutStockEntity.setGoodsAreaCode(StockConstants.VIRTUAL_GOODS_AREA_CODE);
				inOutStockEntity.setGoodsAreaAdd(StockConstants.VIRTUAL_GOODS_AREA_CODE);
			}else{
				//获取货区失败，设置散货虚拟货区
//				inOutStockEntity.setGoodsAreaCode(StockConstants.BULK_GOODS_AREA_CODE);
				inOutStockEntity.setGoodsAreaAdd(null);
				throw new StockException("走货路径有异常入散货货区","");
			}
			
			 
		}
		return inOutStockEntity;
	}
	
	/**
	* @param inOutStockEntity
	* @param waybillEntity
	* @return
	* @description 查询货区并设置货区
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-11 下午1:59:21
	*/
	private InOutStockEntity queryGoodsAreaCodeComm(InOutStockEntity inOutStockEntity,WaybillEntity waybillEntity){
		String goodsAreaCode = null;
		String type = inOutStockEntity.getGoodsAreaAdd();//查询货区的接口类型
		try{
			if(StringUtils.equals("ProductCode", type)) {
				//根据下一部门得到该外场相应货区编号   综合管理接口
				try{
					if(waybillEntity!=null && waybillEntity.getProductCode()!=null){
						//向这个方法添加一个参数,waybillno 用来判断是否是快递
						
						goodsAreaCode = queryGoodsAreaCode(waybillEntity,inOutStockEntity.getOrgCode(), inOutStockEntity.getNextOrgCode(), waybillEntity.getProductCode());
						//设置货区
						inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
					}else{
						LOGGER.error("运单 " + waybillEntity.getWaybillNo() + " 不存在");
						throw new StockException(StockException.WAYBILL_NOT_EXIST_ERROR_CODE,"");
					}
				}catch (Exception e) {
						LOGGER.error("根据下一部门获取货区失败");
						throw new StockException(StockException.QUERY_GOODS_AREA_BY_NEXT_ORG_ERROR_CODE,new Object[]{" 下一部门编号:"+inOutStockEntity.getNextOrgCode()+"运输性质"+waybillEntity.getProductCode()});
				}
			}
			//偏线货区
			else if(StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER, type)){
				try{
					goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER);
					//设置货区
					inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				}catch (Exception e) {
					//根据部门查询货区失败
					LOGGER.error("根据部门：" + inOutStockEntity.getOrgCode() + "查询类型为： 偏线货区 的货区失败");
					throw new StockException(StockException.QUERY_GOODS_AREA_BY_ORGCODE_FAILURE_ERROR_CODE,new Object[]{"部门编号:"+inOutStockEntity.getOrgCode()+" 偏线货区"});
				}
			}
			//驻地派送货区
			else if(StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION, type)){
				try{
				goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				//设置货区
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				}catch (Exception e) {
					//根据部门查询货区失败
					LOGGER.error("根据部门：" + inOutStockEntity.getOrgCode() + "查询类型为： 驻地派送货区 的货区失败");
					throw new StockException(StockException.QUERY_GOODS_AREA_BY_ORGCODE_FAILURE_ERROR_CODE,new Object[]{"部门编号:"+inOutStockEntity.getOrgCode()+" 驻地派送货区"});
				}
			}
			//包装货区
			else if(StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING, type)){
				try{
				goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
				//设置货区
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				}catch (Exception e) {
					//根据部门查询货区失败
					LOGGER.error("根据部门：" + inOutStockEntity.getOrgCode() + "查询类型为：包装货区 的货区失败");
					throw new StockException(StockException.QUERY_GOODS_AREA_BY_ORGCODE_FAILURE_ERROR_CODE,new Object[]{"部门编号:"+inOutStockEntity.getOrgCode()+" 包装货区"});
				}
			}
			//贵重物品库区
			else if(StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE, type)){
				try{
				goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
				//设置货区
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				}catch (Exception e) {
					//根据部门查询货区失败
					LOGGER.error("根据部门：" + inOutStockEntity.getOrgCode() + "查询类型为： 贵重物品库区 的货区失败");
					throw new StockException(StockException.QUERY_GOODS_AREA_BY_ORGCODE_FAILURE_ERROR_CODE,new Object[]{"部门编号:"+inOutStockEntity.getOrgCode()+" 贵重物品库区"});
				}
			}
			//异常货库区
			else if(StringUtils.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION, type)){
				try{
				goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
				//设置货区
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				}catch (Exception e) {
					//根据部门查询货区失败
					LOGGER.error("根据部门：" + inOutStockEntity.getOrgCode() + "查询类型为：异常货库区 的货区失败");
					throw new StockException(StockException.QUERY_GOODS_AREA_BY_ORGCODE_FAILURE_ERROR_CODE,new Object[]{"部门编号:"+inOutStockEntity.getOrgCode()+" 异常货库区"});
				}
			}
			//已查询出并设置了货区
			else if(StringUtils.equals("ProductCode_WaybillNO", type)){
				goodsAreaCode="ProductCode_WaybillNO";
			}
			//无货区(N/A)
			else if(StringUtils.equals(StockConstants.VIRTUAL_GOODS_AREA_CODE, type)){
				goodsAreaCode=StockConstants.VIRTUAL_GOODS_AREA_CODE;
				//设置货区
				inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
			}
			/*if(waybillEntity!=null){
			//快递
			if(StringUtils.equals(waybillEntity.getProductCode(),WaybillConstants.PACKAGE)){
				try{
					goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS);
					if(goodsAreaCode!=null){
						//设置货区
						inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
					}
					}catch (Exception e) {
						//根据部门查询货区失败
						LOGGER.error("根据部门：" + inOutStockEntity.getOrgCode() + "查询类型为：快递 的货区失败");
						//无快递货区 就按零担的货区逻辑处理
					}
			}
		}*/
			
			if(goodsAreaCode==null){
				//没有明确的货区类型
				inOutStockEntity.setGoodsAreaCode(StockConstants.BULK_GOODS_AREA_CODE);
			}
		}catch (Exception e) {
			LOGGER.warn("货区查询失败：--部门：" + inOutStockEntity.getOrgCode()+ " 货物：" + inOutStockEntity.getWaybillNO() + "---" + inOutStockEntity.getSerialNO() +
					"入库到虚拟散货货区，异常信息：" + ExceptionUtils.getFullStackTrace(e));
			//查询货区失败入散货货区
			inOutStockEntity.setGoodsAreaCode(StockConstants.BULK_GOODS_AREA_CODE);
		}
		return inOutStockEntity;
	}
	/**
	* @param inOutStockEntity
	* @param currentOrgCode
	* @param confirmFlag
	* @return
	* @description 走货路径调用PROPAGATION_NESTED事物
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-11 下午8:15:05
	*/
	@Override
	@Transactional(propagation=Propagation.NESTED)
	public InOutStockEntity goodsWalkPathTransactional(
			InOutStockEntity inOutStockEntity, String currentOrgCode,
			String confirmFlag,String checkGoodsArea) {
		       //查询走货路径
				FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTimeForDetailResult(inOutStockEntity.getWaybillNO(), 
																							inOutStockEntity.getSerialNO(), currentOrgCode);
				
				//本部门在走货路径中
				if(TransportPathConstants.STATUS_RIGHT == feedbackDto.getResult()){
					PathDetailEntity pathDetail = feedbackDto.getPathDetailEntity();
					//获取下一部门code
					String nextOrgCode = pathDetail.getObjectiveOrgCode();
					inOutStockEntity.setNextOrgCode(nextOrgCode);
					//计划出发时间
					Date planStartTime = pathDetail.getPlanStartTime();
					inOutStockEntity.setPlanStartTime(planStartTime);
					
					//需要查询货区并设置货区的开关
					if(StringUtils.equals(FossConstants.YES, checkGoodsArea)){
						//根据下一部门得到该外场相应货区编号   综合管理接口
						try{
						String goodsAreaCode = this.getGoodsAreaCodeByNextOrgCode(inOutStockEntity.getOrgCode(), nextOrgCode, inOutStockEntity.getWaybillNO());
						//设置货区
						inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
						//货区的查询类型
						inOutStockEntity.setGoodsAreaAdd("ProductCode_WaybillNO");
						}catch (Exception e) {
							LOGGER.error("根据下一部门获取货区失败");
							throw new StockException(StockException.QUERY_GOODS_AREA_BY_NEXT_ORG_ERROR_CODE,new Object[]{" 下一部门编号:"+inOutStockEntity.getNextOrgCode()});
						}
						//非调整走货路径入库时
						if(!StringUtils.equals(StockConstants.TRANSPORT_PATH_CHANGE_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType())){
							//校验 (方法内部需要查询货区)
							inOutStockEntity = verifyChangePath(inOutStockEntity);
						}else{
							//由调整走货路径 驱动入库时，无需校验非合车调整，因为走货路径已是最新的
						}
						//校验合车调整 (方法内部需要查询货区)
						this.verifyJoinCarChangePath(inOutStockEntity);
					}
					
				//本部门不在货物走货路径中:	
				//	     TransportPathConstants.STATUS_TRANPATHDETAILNULL 没有路径明细
				//	     TransportPathConstants.STATUS_TRANPATHNULL 没有路径主表
				//	     TransportPathConstants.STATUS_CURRENTORGNOPATHDETAIL 当前部门不在路径节点上
				}else if(TransportPathConstants.STATUS_TRANPATHDETAILNULL == feedbackDto.getResult() ||
							TransportPathConstants.STATUS_TRANPATHNULL == feedbackDto.getResult() ||
								TransportPathConstants.STATUS_CURRENTORGNOPATHDETAIL == feedbackDto.getResult() ||
										TransportPathConstants.STATUS_NODATA == feedbackDto.getResult()	
						){
//						//用户未点击确定 修改走货路径  
//						if(!StringUtils.equals(StockConstants.CONFIRM, confirmFlag)){
//							//抛出异常到界面 让用户确认是否要修改走货路径
//							throw new StockException(StockException.NEED_CHANGE_TRANSPORT_PATH_ERROR_CODE,"");
//						}else{
//							//用户已确认 或非界面Action调用该方法
//						}
					
						//没有路径明细  或 没有路径主表 
						if(TransportPathConstants.STATUS_TRANPATHDETAILNULL == feedbackDto.getResult() ||
								TransportPathConstants.STATUS_TRANPATHNULL == feedbackDto.getResult()){
							//生成走货路径
							calculateTransportPathService.createTransportPathForStorage(inOutStockEntity.getWaybillNO(), currentOrgCode, inOutStockEntity.getSerialNO());
//							inOutStockEntity = this.setPlanTimeNextOrgCodeAfterChangePath(inOutStockEntity, currentOrgCode);
							inOutStockEntity = this.setPlanTimeNextOrgCodeAfterChangePath(inOutStockEntity, currentOrgCode,checkGoodsArea);
//							//根据下一部门得到该外场相应货区编号   综合管理接口
//							String goodsAreaCode = queryGoodsAreaCode(inOutStockEntity.getOrgCode(), inOutStockEntity.getNextOrgCode(), waybillEntity.getProductCode());
//							//设置货区
//							inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
						//当前部门不在路径节点上
						}else{
							//修改走货路径
							calculateTransportPathService.arriveMistake(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO(), currentOrgCode,null);
							inOutStockEntity = this.setPlanTimeNextOrgCodeAfterChangePath(inOutStockEntity, currentOrgCode,checkGoodsArea);
//							//根据下一部门得到该外场相应货区编号   综合管理接口
//							String goodsAreaCode = queryGoodsAreaCode(inOutStockEntity.getOrgCode(), inOutStockEntity.getNextOrgCode(), waybillEntity.getProductCode());
//							//设置货区
//							inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
						}
						
				//路径中最后部门没有下一部门	
				}else{
					//派送货区
//					String goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
//					inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
					if(StringUtils.equals(FossConstants.YES, checkGoodsArea)){
						//货区的查询类型
						inOutStockEntity.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
					}
				}
		return inOutStockEntity;
	}
	
	/**
	 * 派送签收时调用出库动作
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @param inOutStockEntity.orgCode 部门编号
	 * @param inOutStockEntity.operatorCode 操作人工号           
	 * @param inOutStockEntity.operatorName 操作人姓名
	 * @param inOutStockEntity.inOutStockType  出入库类型 参见常量类 com.deppon.foss.module.transfer.stock.api.define.StockConstants
	 * @param inOutStockEntity.inOutStockBillNO 出入库单据号 （可为空）
	 * @return 无返回 
	 * @description 
	 * @version 1.0
	 * @author 140022-foss-songjie
	 * @update 2013-7-12 上午11:10:39
	*/
	@Override
	public void outStockDelivery(InOutStockEntity inOutStockEntity) {
		//判断出库设备类型是否为空,为空的时候设置设备类型
		if("".equals(inOutStockEntity.getDeviceType())||inOutStockEntity.getDeviceType()==null){
		//设置操作出库设备类型
		inOutStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
		}
		//设置当前调用时间为扫描时间
		inOutStockEntity.setScanTime(new Date());
		//调用出库
		try{
			this.outStock(inOutStockEntity);
		}catch (Exception e) {
			LOGGER.error("出库异常。运单号："+inOutStockEntity.getWaybillNO()+" 流水号："+inOutStockEntity.getSerialNO());
		}
	}
	
	/**
	* @param currentOrg
	* @return
	* @description 根据当前部门获取对应的外场编码 (没有外场的返回当前部门的编码)
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-13 下午4:26:50
	*/
	@Override
	public String transferCenterCodeByBigOrg(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		String transferCenterCode = orgAdministrativeInfoEntity.getCode();
		if(orgAdministrativeInfoEntity != null){
			//营业部
			if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getSalesDepartment())){
				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(orgAdministrativeInfoEntity.getCode());
				//驻地部门 
				if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())){
					//设置驻地营业部所在外场为库存部门
					transferCenterCode = saleDepartmentEntity.getTransferCenter();
				}
			}else{
				List<String> bizTypes = new ArrayList<String>();
				bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(orgAdministrativeInfoEntity.getCode(),bizTypes);
				if(org!=null){
					transferCenterCode= org.getCode();
				}else{
					transferCenterCode =null;
				}
				//返回当前部门编号
			}
		}
		
		return transferCenterCode;
	}

	/**
	* @param inOutStockEntity
	* @param transferCode 
	* @param currentOrgCode
	* @param areaCodeFlag
	* @return
	* @description ISSUE-3555 空运也使用走货路径中下一部门和计划出发时间
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-8-16 下午5:30:56
	*/
	private InOutStockEntity airNextOrgComm(InOutStockEntity inOutStockEntity,String transferCode,String currentOrgCode,String areaCodeFlag){
		String nextOrgCode = StockConstants.AIR_FREIGHT_NEXT_ORG_CODE;
		Date planStartTime = new Date();
			if(StringUtils.equals(FossConstants.YES, areaCodeFlag)){
				
				try{
				FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTimeForRWSplitting(inOutStockEntity.getWaybillNO(), 
						inOutStockEntity.getSerialNO(), currentOrgCode);
					//本部门在走货路径中
					if(TransportPathConstants.STATUS_RIGHT == feedbackDto.getResult()){
						PathDetailEntity pathDetail = feedbackDto.getPathDetailEntity();
						//获取下一部门code
						String nextOrgCodex = pathDetail.getObjectiveOrgCode();
						inOutStockEntity.setNextOrgCode(nextOrgCodex);
						//计划出发时间
						Date planStartTimex = pathDetail.getPlanStartTime();
						inOutStockEntity.setPlanStartTime(planStartTimex);
					}else{//走货路径有问题时
						inOutStockEntity.setNextOrgCode(nextOrgCode);
						inOutStockEntity.setPlanStartTime(planStartTime);
					}
				}catch(Exception e){
					//FeedbackDto feedbackDto 查询有异常 
					//空运货区
					String tempGoodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
					inOutStockEntity.setGoodsAreaCode(tempGoodsAreaCode);
					return inOutStockEntity;
				}
					
				//走货路径的下一部门不为空
				if(StringUtils.isNotBlank(inOutStockEntity.getNextOrgCode()) ){
					if(StringUtils.equals(nextOrgCode, inOutStockEntity.getNextOrgCode())){
						String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
						inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
					}else{
						OrgAdministrativeInfoEntity orgAdministrativeInfoPojo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(inOutStockEntity.getNextOrgCode());
						if(orgAdministrativeInfoPojo!=null){
							//空运总调/外场
							if(StringUtils.equals(FossConstants.YES,orgAdministrativeInfoPojo.getAirDispatch())) {
								String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
								inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
							}else if(StringUtils.equals(FossConstants.YES,orgAdministrativeInfoPojo.getTransferCenter())){
								WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(inOutStockEntity.getWaybillNO());
								//根据下一部门得到该外场相应货区编号   综合管理接口
								try{
									if(waybillEntity!=null && waybillEntity.getProductCode()!=null){
										String goodsAreaCode = queryGoodsAreaCode(waybillEntity,inOutStockEntity.getOrgCode(), inOutStockEntity.getNextOrgCode(), waybillEntity.getProductCode());
										//设置货区
										inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
									}
								}catch (Exception e) {
										LOGGER.error("空运的走货路径 根据下一部门获取货区失败");
										String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
										//设置货区
										inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
								}
							}else{
								String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
								//设置货区
								inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
							}
						}else{
							String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
							//设置货区
							inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
						}
					}
				}else{
					//外场的对应空运货区
					String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
					inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
				}
			}else{
				//
				inOutStockEntity.setNextOrgCode(nextOrgCode);
				inOutStockEntity.setPlanStartTime(planStartTime);
				if(StockConstants.EXPRESS_AIR_DEL_HANDOVER_IN_STOCK.equals(inOutStockEntity.getInOutStockType())){
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(inOutStockEntity.getWaybillNO());
					if(isExpress(waybillEntity)){
						//BSE_GOODSAREA_TYPE_EXPRESS  将从航空正单交接单中的删除的商务专递运单入库到快递货区
						String goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS);
						//设置货区
						inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
					}
					
				}
				
			}
		
		
		if(StringUtils.endsWith(StockConstants.BULK_GOODS_AREA_CODE, inOutStockEntity.getGoodsAreaCode())){
			//空运货区
			String tempGoodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
			inOutStockEntity.setGoodsAreaCode(tempGoodsAreaCode);
		}
		
		return inOutStockEntity;
	}
	/**
	 * @param waybillNo
	 * @param serialNo
	 * @param orgCode
	 * @description 更新是否已经建包
	 */
	@Override
	public void updateIsPackage(
			String waybillNo, String serialNo, String orgCode) {
		try{
			stockDao.updateIsPackage(waybillNo,serialNo,orgCode);
		}catch (Exception e){
			LOGGER.error("取消快递包时更新库存是否建包字段异常", e);
		}
		
	}


	/**
	* @description 根据当前部门code 以及库区编码 查询库存里下一部门的code以及下一部门的Name(去除重复部门) 用于下拉菜单使用
	* @param orgCode
	* @param goodArea
	* @param orgName 部门名称 用于模糊查询 非必填
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月14日 下午3:40:23
	*/
	@Override
	public List<BaseDataDictDto> queryNextOrgByStock(String orgCode,
			String goodArea,String orgName,int start,int limit) {
		List<BaseDataDictDto> list = new ArrayList<BaseDataDictDto>();
//		BaseDataDictDto baseDataDictDto = new BaseDataDictDto();
//		baseDataDictDto.setValueName(TransferDictionaryConstants.DEFAULT_COMBO_TEXT);
//		baseDataDictDto.setValueCode(TransferDictionaryConstants.DEFAULT_COMBO_VALUE);
//		list.add(baseDataDictDto);
		
		List<StockEntity> stockList = stockDao.queryNextOrgByStock(orgCode, goodArea,orgName,start,limit);
		if(CollectionUtils.isNotEmpty(stockList)){
			for(StockEntity stock : stockList){
				BaseDataDictDto dict = new BaseDataDictDto();
				dict.setValueName(stock.getPosition());//库位字段临时充当 部门名称
				dict.setValueCode(stock.getNextOrgCode());
				list.add(dict);
			}
		}
		return list;    
	}


	/**
	* @description 根据当前部门code 以及库区编码 查询库存里下一部门的code以及下一部门的Name(去除重复部门) 用于下拉菜单使用
	* @param orgCode
	* @param goodArea
	* @param orgName 部门名称 用于模糊查询 非必填
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月14日 下午3:40:23
	*/
	@Override
	public Integer queryNextOrgByStockCount(String orgCode, String goodArea,
			String orgName) {
		return stockDao.queryNextOrgByStockCount(orgCode, goodArea, orgName);
	}
	
	/**
	 * @author niuly
	 * @date 2014-5-24上午8:48:34
	 * @function 根据运单号查询所有的入库部门编码
	 * @param waybillNo
	 * @return
	 */
	@Override
	public List<String> queryInDeptCodeByWaybillNo(String waybillNo) {
		List<String> deptCodes = null;
		if(StringUtils.isNotEmpty(waybillNo)) {
			deptCodes = inOutStockDao.queryInDeptCodeByWaybillNo(waybillNo);
		}
		return deptCodes;
	}
	
	/**
	 * 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
	 * 查询数据字典，查出快递货物体积的比率
	 * @author 200978-foss-xiaobingcheng
	 * 2014-8-9
	 * @return
	 */
	public BigDecimal queryStockParameter(String orgCode){
		// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
				BigDecimal converParameter=BigDecimal.ZERO;
				String  stringValue = "";
				try{
					if(StringUtils.isNotEmpty(orgCode)){
						// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
						stringValue = heavyBubbleRatioService.queryHeavyBubbleParamByOutfield(orgCode);
					}
				}catch(Exception e){
					throw new TfrBusinessException("调综合接口根据外场编码来查询重泡比参数异常"+e.toString());
				}
				
				if(stringValue!=null && StringUtils.isNotEmpty(stringValue)){
					double doubleValue = Double.valueOf(stringValue.toString());
					converParameter = new BigDecimal(doubleValue);
					BigDecimal a =new BigDecimal("1.000");
					//重泡比为重量体积转换参数分之一
					 converParameter = a.divide(converParameter,3);
					 return converParameter;
				}else{
					ConfigurationParamsEntity paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
							ConfigurationParamsConstants.TFR_PARM_EXPRESS_CONVERTVOLUME_PARAMETERS, 
							"DIP");
					if(paramEntity!=null){
						String value=paramEntity.getConfValue();
						try {
							double dvalue = Double.parseDouble(value);
							converParameter = new BigDecimal(dvalue);
						} catch (Exception e) {
							throw new TfrBusinessException("快递转换体积参数转换错误："+e.toString());
						}
						return converParameter;
						
					}else{
						throw new TfrBusinessException("请配置快递转换体积参数！");
					}
				}

	}
	

	/**
	* @description 查询库存迁移
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryMoveGoods(com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockDto, int, int)
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午5:26:34
	* @version V1.0
	*/
	@Override
	public List<MoveGoodsStockQueryDto> queryMoveGoods(
			MoveGoodsStockDto moveGoodsStockDto, int limit, int start) {
		List<MoveGoodsStockQueryDto> list = moveGoodsStockDao.queryMoveGoodsStock(moveGoodsStockDto,limit,start);
		return list;
	}

	/**
	* @param waybillNo 运单号
	* @param orgCode   部门编号
	* @param stockPositionNumber  定位编号
	* @return 根据运单号、部门编号来获取运单号、流水号、定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-18 下午5:21:46
	*/

	public List<StockPositionNumberEntity> queryStockPositionNumber(String waybillNo,
			String orgCode) {
		List<StockPositionNumberEntity> stockPositionNumberEntity = new ArrayList<StockPositionNumberEntity>();
		if (StringUtils.isNotEmpty(waybillNo)
				&& StringUtils.isNotEmpty(orgCode)) {
				stockPositionNumberEntity = stockDao.queryStockPositionNumber(
						waybillNo,orgCode);
		} 
		return stockPositionNumberEntity;

	}
	/**
	 * @param waybillNo 运单号
	 * @param serialNo  流水号
	 * @param orgCode 部门编号
	 * @param stockPositionNumber  定位编号
	 * @return 根据运单号、流水号、部门编号来存储定位编号到库存表中
	 * @description 
	 * @version 1.0
	 * @author 200968-foss-zwd
	 * @update 2014-12-19 上午14:44:46
	 */
	public void saveStockPositionNumber(
			List<StockPositionNumberEntity> stockPositionNumberEntityList) {
		if (CollectionUtils.isNotEmpty(stockPositionNumberEntityList))
			for (StockPositionNumberEntity stockPositionNumberEntity : stockPositionNumberEntityList) {
				// ID 给ID赋值
				stockPositionNumberEntity.setId(UUIDUtils.getUUID());
				stockDao.saveStockPositionNumber(stockPositionNumberEntity);
			}
	}

	

	/**
	* @description 撤销申请
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#revocationStockList(java.util.List, java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2014-12-22 上午9:38:51
	* @version V1.0
	*/
	@Override
	public void revocationStock(MoveGoodsEntity moveGoodsEntity,
			String userCode, String userName) {
			//根据id查询记录是否存在
			MoveGoodsEntity m = moveGoodsStockDao.queryMoveGoodsEntityById(moveGoodsEntity.getId());
			if(m!=null){
			//姓名
			moveGoodsEntity.setRevocation_name(userName);
			//工号
			moveGoodsEntity.setRevocation_code(userCode);
			Date revocationTime = new Date();
			//时间
			moveGoodsEntity.setRevocation_time(revocationTime);
			moveGoodsStockDao.revocationStock(moveGoodsEntity);
			}else{
				throw new TfrBusinessException("数据不存在");
			}
			
	}
	
	
	/**
	* @description 审核申请
	* @param moveGoodsEntity
	* @param userCode
	* @param userName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午5:40:00
	*/
	public void auditorStock(MoveGoodsEntity moveGoodsEntity,
			String userCode, String userName) {
			//根据id查询记录是否存在
			MoveGoodsEntity m = moveGoodsStockDao.queryMoveGoodsEntityById(moveGoodsEntity.getId());
			if(m!=null){
			//姓名
			moveGoodsEntity.setAuditor_name(userName);
			//工号
			moveGoodsEntity.setAuditor_code(userCode);
			Date auditorTime = new Date();
			//时间
			moveGoodsEntity.setAuditor_time(auditorTime);
			moveGoodsStockDao.auditorStock(moveGoodsEntity);
			}else{
				throw new TfrBusinessException("数据不存在");
			}
			
	}
	
	
	/**
	* @description 作废申请
	* @param moveGoodsEntity
	* @param userCode
	* @param userName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午7:25:54
	*/
	public void invalidateStock(MoveGoodsEntity moveGoodsEntity,
			String userCode, String userName) {
			//根据id查询记录是否存在
			MoveGoodsEntity m = moveGoodsStockDao.queryMoveGoodsEntityById(moveGoodsEntity.getId());
			if(m!=null){
			//姓名
			moveGoodsEntity.setInvalidate_name(userName);
			//工号
			moveGoodsEntity.setInvalidate_code(userCode);
			Date invalidateTime = new Date();
			//时间
			moveGoodsEntity.setInvalidate_time(invalidateTime);
			moveGoodsStockDao.invalidateStock(moveGoodsEntity);
			}else{
				throw new TfrBusinessException("数据不存在");
			}
			
	}
	/**
	* @description 退回申请
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#returnStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity, java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午7:39:34
	* @version V1.0
	*/
	public void returnStock(MoveGoodsEntity moveGoodsEntity,
			String userCode, String userName) {
			//根据id查询记录是否存在
			MoveGoodsEntity m = moveGoodsStockDao.queryMoveGoodsEntityById(moveGoodsEntity.getId());
			if(m!=null){
			//姓名
			moveGoodsEntity.setReturn_name(userName);
			//工号
			moveGoodsEntity.setReturn_code(userCode);
			Date returnTime = new Date();
			//时间
			moveGoodsEntity.setReturn_time(returnTime);
			moveGoodsStockDao.returnStock(moveGoodsEntity);
			}else{
				throw new TfrBusinessException("数据不存在");
			}
			
	}
	
	/**
	* @description 根据id查询库存迁移明细
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#viewMoveGoodsById(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2014-12-24 上午11:05:27
	* @version V1.0
	*/
	@Override
	public MoveGoodsStockQueryDto viewMoveGoodsById(String id) {
		//根据id查询记录是否存在
		MoveGoodsEntity moveGoodsEntity = moveGoodsStockDao.queryMoveGoodsEntityById(id);
		if(moveGoodsEntity!=null){
			MoveGoodsStockQueryDto m = moveGoodsStockDao.viewMoveGoodsById(id);
			return m;
		}else{
			throw new TfrBusinessException("数据不存在");
		}
	}
	
	/**
	* @description 根据部门code 获得货区code,name(如果是外场,返回库区name,code    如果是营业部返回,营业部name   其余返回空)
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryAreaCodeAndAreaNameByOrgCode(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2014-12-27 下午4:18:16
	* @version V1.0
	*/
	@Override
	public List<BaseDataDictDto> queryAreaCodeAndAreaNameByOrgCode(
			String orgCode,String orgName) {
		//根据部门code获取组织信息
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = 
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		String str = stockService.isTransferCenter(orgCode);
		//是外场
		if(StringUtils.equals(str, FossConstants.YES)){
			List<BaseDataDictDto> list = stockService.queryGoodsAreaListByOrganizationCode(orgCode);
			return list;
		//如果是营业部就返回 部门name, 库区为"N/A"
		}else if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getSalesDepartment())){
			List<BaseDataDictDto> list = new ArrayList<BaseDataDictDto>();
			BaseDataDictDto bddd = new BaseDataDictDto();
			bddd.setValueCode("N/A");
			bddd.setValueName(orgName);
			list.add(bddd);
			return list;
		}else{
			return null;
		}
	}
	
	/**
	* @description 把要库存迁移的信息写入数据库表中
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#moveGoodsInStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity, com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsDepartmentEntity)
	* @author 218381-foss-lijie
	* @update 2014-12-29 下午2:33:56
	* @version V1.0
	*/
	@Transactional//一个失败,全部回滚
	@Override
	public void moveGoodsInStock(MoveGoodsEntity moveGoodsEntity,
			MoveGoodsDepartmentEntity moveGoodsDepartmentEntity) {
		//这方法应该会有问题,他们两个有一个失败了怎么办?
		moveGoodsStockDao.moveGoodsInStock(moveGoodsEntity);
		moveGoodsStockDao.moveGoodsDepartmentInStock(moveGoodsDepartmentEntity);
		
	}
	
	/**
	* @description 把修改后的信息写入数据库
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#moveGoodsModifyInStock(com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity, com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsDepartmentEntity)
	* @author 218381-foss-lijie
	* @update 2014-12-30 上午11:08:21
	* @version V1.0
	*/
	@Transactional//一个失败,全部回滚
	@Override
	public void moveGoodsModifyInStock(MoveGoodsEntity moveGoodsEntity,
			MoveGoodsDepartmentEntity moveGoodsDepartmentEntity) {
		moveGoodsStockDao.moveGoodsModifyInStock(moveGoodsEntity);
		moveGoodsStockDao.moveGoodsModifyDepartmentInStock(moveGoodsDepartmentEntity);	
	}
	
	/**
	* @description 查询库存迁移总记录数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryMoveGoodsCount(com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockDto)
	* @author 218381-foss-lijie
	* @update 2015-1-4 上午9:22:09
	* @version V1.0
	*/
	@Override
	public Long queryMoveGoodsCount(MoveGoodsStockDto moveGoodsStockDto) {
		return (Long)moveGoodsStockDao.queryMoveGoodsCount(moveGoodsStockDto);
	}
	
	/**
	* @description 判断部门是否是外场,是外场才会显示页面信息,如果部门的顶级部门是外场也能显示出来
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryIsTransferCenter(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015-1-4 下午2:47:40
	* @version V1.0
	*/
	@Override
	public String queryIsTransferCenter(String orgCode) {
		//判断部门或部门的顶级部门是否是外场
		String transferCenterCode = null;
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(orgCode,bizTypes);
		if(org!=null){
			transferCenterCode= org.getCode();
		}
		if(transferCenterCode != null){
			return transferCenterCode;
		}else{
			//查询用户所属外场部门失败
			LOGGER.error("查询用户所属外场部门失败");
			throw new StockException(StockException.QUERY_USER_TRANSFER_CENTER_ERROR_CODE,"");
		}
	}
	
	/**
	* @description 根据员工工号查询员工角色信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryOrgRoleByCode(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015-1-7 下午5:04:39
	* @version V1.0
	*/
	@Override
	public List<UserOrgRoleEntity> queryOrgRoleByCode(String userCode) {
		UserOrgRoleEntity userOrgRole = new UserOrgRoleEntity();
		userOrgRole.setEmpCode(userCode);
		userOrgRole.setActive("Y");
		List<UserOrgRoleEntity> list = userOrgRoleService.queryUserOrgRoleListBySelective(userOrgRole);
		return list;
	}
	
	
	/**
	* @description 从库存表中根据部门code和库区code查询出库区的货物
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryGoodsByOrgAndGoodsArea(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年1月27日 上午11:00:02
	* @version V1.0
	*/
	@Override
	public List<StockEntity> queryGoodsByOrgAndGoodsArea(String orgCode,String goodsAreaCode) {
		List<StockEntity> list = moveGoodsStockDao.queryGoodsByOrgAndGoodsArea(orgCode,goodsAreaCode);
		return list;
	}
	
	/**
	* @description 从运单库存表中根据部门code和库区code查询出库区的货物
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryGoodsByOrgAndGoodsAreaFromWaybillStock(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年1月27日 下午4:14:03
	* @version V1.0
	*/
	@Override
	public List<WaybillStockEntity> queryGoodsByOrgAndGoodsAreaFromWaybillStock(
			String orgCode, String goodsAreaCode) {
		List<WaybillStockEntity> list = moveGoodsStockDao.queryGoodsByOrgAndGoodsAreaFromWaybillStock(orgCode,goodsAreaCode);
		return list;
	}
	
	/**
	* @description 把库存表 中移出部门信息更新为移入部门
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#insertintoMoveInArea(com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity)
	* @author 218381-foss-lijie
	* @update 2015年1月27日 下午4:49:11
	* @version V1.0
	*/
	@Override
	public void updateMoveInArea(StockEntity stockEntity,String moveoutCode,String moveoutAreacode) {
		moveGoodsStockDao.updateMoveInArea(stockEntity,moveoutCode,moveoutAreacode);
	}
	
	
	/**
	* @description 把运单库存表 中移出部门信息更新为移入部门
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#insertintoMoveInAreaFromWaybillStock(com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity, java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年1月28日 下午3:19:07
	* @version V1.0
	*/
	@Override
	public void updateMoveInAreaFromWaybillStock(
			WaybillStockEntity waybillStockEntity,String moveoutCode,String moveoutAreacode) {
		moveGoodsStockDao.updateMoveInAreaFromWaybillStock(waybillStockEntity,moveoutCode,moveoutAreacode);
	}
	
	/**
	* @description 判断移出部门的库区的目的部门编码和移出部门的库区的目的部门编码是否一致
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryMoveOutCodeAndMoveInCodeIsEqual(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年3月6日 下午4:17:39
	* @version V1.0
	*/
	@Override
	public void queryMoveOutCodeAndMoveInCodeIsEqual(String moveoutCode,String moveoutAreacode,String moveinCode,String moveinAreacode){
		//移出部门库区的目的部门code
		String moveoutcode = null;
		//移入部门库区的目的部门code
		String moveincode = null;
		//判断是否是营业部  
		SaleDepartmentEntity saleDepartmentEntity = 
				saleDepartmentService.querySaleDepartmentByCode(moveoutCode);
		if(saleDepartmentEntity != null){
			
			//移出部门库区的目的部门code
			moveoutcode = moveoutCode;
		}else{
			//移出部门库区的目的部门code
			moveoutcode = moveGoodsStockDao.queryArriveCode(moveoutCode,moveoutAreacode);
		}
		
		SaleDepartmentEntity saleDepartmentEntityIn = 
				saleDepartmentService.querySaleDepartmentByCode(moveinCode);
		if(saleDepartmentEntityIn != null){
			
			//移入部门库区的目的部门code
			moveincode = moveinCode;
		}else{
			//移入部门库区的目的部门code
			moveincode = moveGoodsStockDao.queryArriveCode(moveinCode,moveinAreacode);
		}
		
		
		if(moveoutcode != null && moveincode != null){
			if(!moveoutcode.equals(moveincode)){
				throw new TfrBusinessException("移出部门的目的部门编码和移出部门的目的部门编码不一致");
			}
		}else{
			throw new TfrBusinessException("移出部门的目的部门编码和移出部门的目的部门编码不一致");
		}
		
	}
	
	/**
	* @description 从部门移出部门移入到部门移入部门
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#moveGoodsFromMoveOutToMoveInCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年1月29日 上午9:19:46
	* @version V1.0
	*/
	@Transactional//一个失败,全部回滚
	@Override
	public void moveGoodsFromMoveOutToMoveInCode(String moveoutCode,
		String moveoutAreacode, String moveinCode, String moveinAreacode,String id,int goodsType) {
		
		MoveGoodsEntity m = moveGoodsStockDao.queryMoveGoodsEntityById(id);
		//状态'2' 为'已审核'
		if(m!=null && "2".equals(m.getState())){
			//根据  移出部门code和库区code  查询出移出库区的货物       从库存表中查询   tfr.t_opt_stock
			List<StockEntity> listStock = stockService.queryGoodsByOrgAndGoodsArea(moveoutCode,moveoutAreacode);
			if(listStock != null){
				for(StockEntity s : listStock){
					//改变货物的入库时间,部门code和库区code
					s.setInStockTime(new Date());
					s.setGoodsAreaCode(moveinAreacode);
					s.setOrgCode(moveinCode);
					try {
						stockService.updateMoveInArea(s,moveoutCode,moveoutAreacode);
					} catch (Exception e) {
						LOGGER.error("根据部门code和库区code从库存表中迁移货物失败：" + ExceptionUtils.getFullStackTrace(e));
					}
					//更新库存表
				}
			}
			//从运单库存表中查询   tfr.t_opt_waybill_stock
			List<WaybillStockEntity> listWaybillstock = stockService.queryGoodsByOrgAndGoodsAreaFromWaybillStock(moveoutCode,moveoutAreacode);
			//遍历list集合   把list中的每一个WaybillStockEntity的部门code和库区code改为移入部门code,库区code
			if(listWaybillstock != null){
				for(WaybillStockEntity s : listWaybillstock){
					//改变货物的入库时间,部门code和库区code
					s.setInStockTime(new Date());
					s.setGoodsAreaCode(moveinAreacode);
					s.setOrgCode(moveinCode);
					try {
						stockService.updateMoveInAreaFromWaybillStock(s,moveoutCode,moveoutAreacode);
					} catch (Exception e) {
						LOGGER.error("根据部门code和库区code从运单库存表中迁移货物失败：" + ExceptionUtils.getFullStackTrace(e));
					}
					//更新运单库存表
				}
			}
			//把状态'已审核'改为'已迁移'
			//工号
			String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
			//姓名
			String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
			MoveGoodsEntity moveGoodsEntity = new MoveGoodsEntity();
		
			moveGoodsEntity.setId(id);
			//姓名
			moveGoodsEntity.setConfirm_name(userName);
			//工号
			moveGoodsEntity.setConfirm_code(userCode);
			Date confirmTime = new Date();
			//时间
			moveGoodsEntity.setConfirm_time(confirmTime);
			moveGoodsStockDao.confirmStock(moveGoodsEntity);
			
			//调用结算的借口
			StockMoveDto stockMoveDto = new StockMoveDto();
			stockMoveDto.setOldFinalDeptCode(moveoutCode);
			stockMoveDto.setOldAccessCode(moveoutAreacode);
			stockMoveDto.setNewFinalDeptCode(moveinCode);
			stockMoveDto.setNewAccessCode(moveinAreacode);
			//下面这个方法的参数是   货物类型(零担 1 或快递 0快递待用) Y是快递N是零担;
			String type = null;
			if(goodsType ==1){
				type = "N";
			}else{
				type = "Y";
			}
			stockMoveDto.setLTLorExpress(type);
			try {
				actualFreightService.stockMove(stockMoveDto);
				
			} catch (Exception e) {
				throw new TfrBusinessException("调用结算接口出错");
			}
		}else{
			throw new TfrBusinessException("数据不存在");
		}
	}

	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @param stockPositionNumber  定位编号
	* @return 查找所有的运单号、流水号、定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-20 下午14:08:46
	*/
	public List<StockPositionNumberEntity> queryAllStockPositionNumber() {

		List<StockPositionNumberEntity> stockPositionNumberEntityList = new ArrayList<StockPositionNumberEntity>();
		stockPositionNumberEntityList = stockDao.queryAllStockPositionNumber();
		return stockPositionNumberEntityList;
	}

	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @param orgCode   部门编号
	* @param stockPositionNumber  定位编号
	* @return 根据运单号、流水号、部门编号来更新定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-20 下午14:10:46
	*/
	public void updateStockPositionNumber(String waybillNo, String serialNo,
			String orgCode, String stockPositionNumber) {
		if (StringUtils.isNotEmpty(waybillNo)
				&& StringUtils.isNotEmpty(serialNo)
				&& StringUtils.isNotEmpty(orgCode)
				&& StringUtils.isNotEmpty(stockPositionNumber)) {
			stockDao.updateStockPositionNumber(waybillNo, serialNo, orgCode,
					stockPositionNumber);
		}
	}

	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @param stockPositionNumber 定位编号
	* @return 根据运单号、流水号来删除定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-21 上午9:10:46
	*/
	public void deleteStockPositionNumber(String waybillNo, String serialNo,String orgCode) {
		if (StringUtils.isNotEmpty(waybillNo)
				&& StringUtils.isNotEmpty(serialNo)
				&& StringUtils.isNotEmpty(orgCode)) {
			stockDao.deleteStockPositionNumber(waybillNo, serialNo,orgCode);
		}
	}

	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
    * @param orgCode   部门编号
	* @return 根据运单号、流水号、部门编号来获取库存表
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-25 下午14:21:46
	*/
	public StockEntity queryStockByWSO(String waybillNo,String serialNo,String orgCode) {
		StockEntity stockEntity = new StockEntity();
		if (StringUtils.isNotEmpty(waybillNo)
				&& StringUtils.isNotEmpty(serialNo)
				&& StringUtils.isNotEmpty(orgCode)){
			
			stockEntity = stockDao.queryStockByWSO(waybillNo, serialNo, orgCode);
		}
		return stockEntity;
	}

  /**
   * @author 200968
   * 
   */
   public void stockPositionNumberJobRun(){
	   List<StockPositionNumberEntity> stockPositionNumberEntityList = new ArrayList<StockPositionNumberEntity>();
	   
	   List<StockPositionNumberEntity> stockPositionNumberEntityList2 = new ArrayList<StockPositionNumberEntity>();
		// 查找定位编号
		stockPositionNumberEntityList = stockService.queryAllStockPositionNumber();
		if(CollectionUtils.isNotEmpty(stockPositionNumberEntityList)){
			for(StockPositionNumberEntity temp : stockPositionNumberEntityList){
				// 根据运单号和流水号去查找库存表？？？？？？？？？？？？？？？？？？有了当前部门才能唯一定位在不在当前库存
				// pda传递的参数需要运单号、流水号、部门编码、定位编号 --部门编码才能唯一定位
				StockEntity stockEntity = stockService.queryStockByWSO(temp.getWaybillNO(), temp.getSerialNO(),temp.getOrgCode());
				if(stockEntity!=null){
				stockEntity.setStockPositionNumber(temp.getStockPositionNumber());
				String waybillNo = stockEntity.getWaybillNO();
				String serialNo = stockEntity.getSerialNO();
				String orgCode = stockEntity.getOrgCode();
				String stockPositionNumber = temp.getStockPositionNumber();
				//根据运单号、流水号、部门编号来更新库存表中的定位编号
				stockService.updateStockPositionNumber(waybillNo, serialNo, orgCode, stockPositionNumber);	
				//根据运单号、流水号来删除物理表中的定位编号
				stockService.deleteStockPositionNumber(waybillNo, serialNo,orgCode);
				// 重新查询定位编号
				stockPositionNumberEntityList2 = stockService.queryAllStockPositionNumber();
				if(CollectionUtils.isNotEmpty(stockPositionNumberEntityList2)){
					stockPositionNumberEntityList = stockPositionNumberEntityList2;
				 }
			   }
			}
			
		}
		  
   }

   
   //**********************************************************************************库区编号修改
   
	/**
	* @description 查询库区号变更记录
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryChangeGoodsArea(com.deppon.foss.module.transfer.stock.api.shared.dto.ChangeGoodsAreaQueryDto, int, int)
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:40:28
	* @version V1.0
	*/
	@Override
	public List<ChangeGoodsAreaEntity> queryChangeGoodsArea(
			   ChangeGoodsAreaQueryDto changeGoodsAreaQueryDto, int limit, int start) {
			List<ChangeGoodsAreaEntity> list = changeGoodsAreaDao.queryChangeGoodsArea(changeGoodsAreaQueryDto,limit,start);
			return list;
		}



	 /**
	    * @param String  waybillNo   原单单号
	 	* @return 0:success   1:error
	 	* @description  返货开单 入库    改变原单的库存状态
	 	* @version 1.0
	 	* @author 311396
	 	* @update 2015-02-02 下午14:21:46    String waybillNo
	 	*/
	   @Transactional
	  public int returngoodsBills(String  waybillNo){
		  //生成个新对象
		   WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
		   //设置运单号
		   waybillStockEntity.setWaybillNO(waybillNo);
		   //查出该运单的库存信息
			List<StockEntity> stockList = stockDao.queryStockByWaybillNo(waybillStockEntity);
			//把库存货区编码改成返货开单虚拟库存更新库存
			
			//用于轨迹表只选一条流水号插入轨迹的操作map
			Map<String, String> oneSerMap = new HashMap<String, String>();
			for(StockEntity stock:stockList){
				//把货区编码设置为返货虚拟货区
				stock.setGoodsAreaCode(StockConstants.OPERATED_RETURN_CODE);
				//设置入库时间
				stock.setInStockTime(new Date());
				//更新库存数据
				stockDao.updateReturnGoodsState(stock);
				//********************保存入库动作**************************
				   //封装 入库信息
				   InOutStockEntity inOutStockEntity =new InOutStockEntity();
				   //id
				   inOutStockEntity.setId(stock.getId());
				   //运单号
				   inOutStockEntity.setWaybillNO(stock.getWaybillNO());
				   //流水号
				   inOutStockEntity.setSerialNO(stock.getSerialNO());
				   //操作人工号
				   inOutStockEntity.setOperatorCode(stock.getOperatorCode());
				   //操作人姓名
				   inOutStockEntity.setOperatorName(stock.getOperatorName());
				   //入库时间
					inOutStockEntity.setInOutStockTime(stock.getInStockTime());
					//出入库类型
					inOutStockEntity.setInOutStockType(StockConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
					//设备类型
					inOutStockEntity.setDeviceType(stock.getDeviceType());
					//入库时间
					inOutStockEntity.setInOutStockTime(stock.getInStockTime());
					//入库时间
					inOutStockEntity.setOrgCode(stock.getOrgCode());
					//货区编码
					inOutStockEntity.setGoodsAreaCode(stock.getGoodsAreaCode());
					//设置动作有效
					inOutStockEntity.setIsValid(FossConstants.ACTIVE);
					//保存入库动作
					inOutStockDao.addInStock(inOutStockEntity);
					
					//插入中转轨迹推送表 (类型：返货开单)
					//@author liuyi 283250
					//多件只插流水号只要一件
					//使用map的运单号作为 key，如果key存在，表示运单已经有流水号轨迹被加入了，不再处理
					try{
						if(stockList.size()==1 || StringUtils.isEmpty(oneSerMap.get(stock.getWaybillNO()))){
							SynTrackingEntity track = new SynTrackingEntity();
							track.setId(UUIDUtils.getUUID());
							// 运单
							track.setWayBillNo(stock.getWaybillNO());
							// 发生时间
							track.setOperateTime(new Date());
							track.setCreateDate(new Date());
							track.setModifyDate(new Date());
							//操作类型
							String eventType = WayBillNoLocusConstant.OPERATE_TYPE_RETURN_CARGO;
							// 跟踪信息描述
							track.setTrackInfo(WayBillNoLocusConstant.map.get(eventType));
							// 跟踪信息描述
							//track.setTrackInfo("已进行返货开单，返货单号【"+waybill.getOrderNo()+"】");
							// 开单部门所在城市
							OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
									.queryOrgAdministrativeInfoByCode(stock.getOrgCode());
							if (org != null) {
								track.setOperateCity(org.getCityName());
							}
							// 站点类型
							track.setOrgType(String.valueOf(1));
							// 操作部门编码
							track.setOrgCode(stock.getOrgCode());
							// 操作部门名称
							track.setOrgName(org.getName());
							//当前操作人工号
							track.setOperatorCode(stock.getOperatorCode());
							//当前操作人姓名
							track.setOperatorName(stock.getOperatorName());
							// 事件
							track.setEventType(eventType);
							pushTrackForCaiNiaoService.addSynTrack(track);
							
							//运单、流水号添加到map里面
							oneSerMap.put(stock.getWaybillNO(), stock.getSerialNO());
						}
					} catch(Exception e){
						LOGGER.error("插入中转轨迹推送表 (类型：返货开单)失败，原因：" + e.getMessage());
					}
				
		}
		//根据运单号查询运单库存
		List<WaybillStockEntity> waybillStocklist = waybillStockDao.queryWaybillStockByWaybillNo(waybillNo);
		//新建一个运单库存对象，用来查询和删除当条件用
		WaybillStockEntity waybillStock = new WaybillStockEntity();
		//遍历运单库存信息用部门和单号去查询然后判断是否有重复的数据
		for(WaybillStockEntity waybillstock:waybillStocklist){
			//根据运单号和部门号查询运单库存信息
			List<WaybillStockEntity>  isStcoklist = waybillStockDao.queryWaybillStockByWaybillOrgCode(
					waybillstock.getWaybillNO(),waybillstock.getOrgCode());
			//得到部门和运单号相同的运单库存的总件数
			int sumStockGoodsQty = waybillStockDao.querySumStockGoodsQtyByWaybillOrgCode(
					waybillstock.getWaybillNO(), waybillstock.getOrgCode());
			//如果运单号和部门号查询到的数据大于1说明运单号和部门号相同的数据有多条 删除重复的数据，如果不大于1就没有重复数据直接更新数据
			if(isStcoklist.size()>1){
				//遍历isStcoklist删除数据，从第2条数据开始删除
				for(int i=1;i<isStcoklist.size();i++){
					//设置运单号
					waybillStock.setWaybillNO(isStcoklist.get(i).getWaybillNO());
					//设置部门编码
					waybillStock.setOrgCode(isStcoklist.get(i).getOrgCode());
					//设置货区编码
					waybillStock.setGoodsAreaCode(isStcoklist.get(i).getGoodsAreaCode());
					//删除数据
					waybillStockDao.deleteWaybillStock(waybillStock);
				}
				//设置货区编码
				waybillStock.setGoodsAreaCode(StockConstants.OPERATED_RETURN_CODE);
				//设置运单库存总件数
				waybillStock.setStockGoodsCount(sumStockGoodsQty);
				//设置运单号
				waybillStock.setWaybillNO(waybillstock.getWaybillNO());
				//设置部门编码
				waybillStock.setOrgCode(waybillstock.getOrgCode());
				//更新运单库存数据
				waybillStockDao.updateWaybillStockGoodsArea(waybillStock);
			}else{
				//设置货区编码
				waybillStock.setGoodsAreaCode(StockConstants.OPERATED_RETURN_CODE);
				//设置运单库存总件数
				waybillStock.setStockGoodsCount(sumStockGoodsQty);
				//设置运单号
				waybillStock.setWaybillNO(waybillstock.getWaybillNO());
				//设置部门编码
				waybillStock.setOrgCode(waybillstock.getOrgCode());
				//更新运单库存数据
				waybillStockDao.updateWaybillStockGoodsArea(waybillStock);
			}
		}
		return 0;
  }
	 /**
	* @param String  waybillNo   原单单号
	* @return 0:success   1:error
	* @description 返货开单 入库  恢复原单的库存状态
	* @version 1.0
	* @author 216208
	* @update 2015-02-02 下午14:21:46    String waybillNo
	*/
	@Override
	@Transactional
	public int backreturngoodsBills(String waybillNo) {
		//判断运单号是否为空，为空返回1，不为空执行下面的代码
		if("".equals(waybillNo)||waybillNo==null){
			return 1;
		} else{
		  	//生成个新对象
		   WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
		   //查询运单信息
		   WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		   
		   if(waybill==null){
				LOGGER.error("运单 " + waybillNo + " 不存在");
			 //抛出 运单不存异常
			throw new StockException(StockException.WAYBILL_NOT_EXIST_ERROR_CODE,"");
		   }
		   //设置运单号
		   waybillStockEntity.setWaybillNO(waybillNo);
		   //查出该运单的库存信息
		   List<StockEntity> stockList = stockDao.queryStockByWaybillNo(waybillStockEntity);
		   //判断库存List是否为null，为空则不执行下面的方法
		   if(stockList.size()!=0){
		   //遍历出库
		   for(StockEntity stock:stockList){
			   InOutStockEntity outStockEntity = new InOutStockEntity();
			   outStockEntity.setWaybillNO(stock.getWaybillNO());
			   outStockEntity.setSerialNO(stock.getSerialNO());
			   outStockEntity.setOperatorCode(stock.getOperatorCode());
			   outStockEntity.setOperatorName(stock.getOperatorName());
			   outStockEntity.setInOutStockType(StockConstants.BACK_RETURNGOODS_STOCK_TYPE);
			   this.outStockPC(outStockEntity);
		   }
		   //遍历入库
		   for(StockEntity stock:stockList){
			   InOutStockEntity inStockEntity = new InOutStockEntity();
			   inStockEntity.setWaybillNO(stock.getWaybillNO());
			   inStockEntity.setSerialNO(stock.getSerialNO());
			   inStockEntity.setOperatorCode(stock.getOperatorCode());
			   inStockEntity.setOperatorName(stock.getOperatorName());
			   inStockEntity.setInOutStockType(StockConstants.BACK_RETURNGOODS_STOCK_TYPE);
			   inStockEntity.setOrgCode(stock.getOrgCode());
			   inStockEntity.setNextOrgCode(stock.getNextOrgCode());
			  //查询部门是否营业部
			   SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(stock.getOrgCode());
			   //如果不为空部门就是营业部
			   if(saleDepartmentEntity!=null){
				   //设置货区
					  inStockEntity.setGoodsAreaCode("N/A");
			   } else{
			 //根据下一部门得到该外场相应货区编号   综合管理接口
			   try{
			   if(waybill!=null && waybill.getProductCode()!=null&&stock.getNextOrgCode()!=null){
				   WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(inStockEntity.getWaybillNO());  
				   String  goodsAreaCode = queryGoodsAreaCode(waybillEntity,stock.getOrgCode(), stock.getNextOrgCode(),waybill.getProductCode());
			   //设置货区
			  inStockEntity.setGoodsAreaCode(goodsAreaCode);
			   }else{
			   LOGGER.error("运单 " + waybill.getWaybillNo() + " 不存在"+"或者 下一部门 为空 ");
			   throw new StockException(StockException.WAYBILL_NOT_EXIST_ERROR_CODE,"");
			   }
			   }catch (Exception e) {
			   LOGGER.error("根据下一部门获取货区失败");
			   throw new StockException(StockException.QUERY_GOODS_AREA_BY_NEXT_ORG_ERROR_CODE,new Object[]{" 下一部门编号:"+stock.getNextOrgCode()+"运输性质"+waybill.getProductCode()});
			   }		   
			   }
			   if("".equals(stock.getDeviceType())||stock.getDeviceType()==null){
				   inStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
			   }else{
				   inStockEntity.setDeviceType(stock.getDeviceType());
			   }
			   this.addStock(inStockEntity);
		   }
		return 0;
	}else{
		return 1;
	}
		}
	}


	/**
	* @description 查询库区编号修改总记录数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryChangeGoodsAreaCount(com.deppon.foss.module.transfer.stock.api.shared.dto.ChangeGoodsAreaQueryDto)
	* @author 218381-foss-lijie
	* @update 2015年4月7日 上午10:35:40
	* @version V1.0
	*/
	@Override
	public Long queryChangeGoodsAreaCount(
			ChangeGoodsAreaQueryDto changeGoodsAreaQueryDto) {
		return (Long)changeGoodsAreaDao.queryChangeGoodsAreaCount(changeGoodsAreaQueryDto);
	}
	
	
	/**
	* @description 作废库区编号修改的申请
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#invalidateChangeGoodsArea(com.deppon.foss.module.transfer.stock.api.shared.domain.ChangeGoodsAreaEntity)
	* @author 218381-foss-lijie
	* @update 2015年4月7日 下午3:24:26
	* @version V1.0
	*/
	@Override
	public void invalidateChangeGoodsArea(ChangeGoodsAreaEntity changeGoodsAreaEntity){
		ChangeGoodsAreaEntity c = changeGoodsAreaDao.queryChangeGoodsAreaEntityById(changeGoodsAreaEntity.getId());
		if(c!=null){
			changeGoodsAreaDao.invalidateChangeGoodsArea(changeGoodsAreaEntity);
		}else{
			throw new TfrBusinessException("数据不存在");
		}
	}

	
	/**
	* @description 根据部门code查询库区编码
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#lookGoodsAreaByOrgcode(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月8日 上午10:30:46
	* @version V1.0
	*/
	@Override
	public List<NewAndOldGoodsAreaEntity> lookGoodsAreaByOrgcode(String orgCode) {
		List<NewAndOldGoodsAreaEntity> list = changeGoodsAreaDao.lookGoodsAreaByOrgcode(orgCode);
		return list;
	}

	/**
	* @description 将库区编号变更的信息写入主副表中
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#changeGoodsAreaInStock(com.deppon.foss.module.transfer.stock.api.shared.domain.ChangeGoodsAreaEntity)
	* @author 218381-foss-lijie
	* @update 2015年4月9日 下午3:31:17
	* @version V1.0
	*/
	@Transactional//一个失败,全部回滚
	@Override
	public void changeGoodsAreaInStock(ChangeGoodsAreaEntity changeGoodsAreaEntity,
			List<NewAndOldGoodsAreaEntity> list) throws Exception{
		changeGoodsAreaDao.changeGoodsAreaInStock(changeGoodsAreaEntity);
		for(NewAndOldGoodsAreaEntity n : list){
			changeGoodsAreaDao.changeGoodsAreaNewAndOldInStock(n);
		}
	}


	
	/**
	* @description 根据部门code和id查询库区编码对应关系
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#lookModifyGoodsAreaByOrgcode(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月11日 下午3:52:22
	* @version V1.0
	*/
	@Override
	public List<NewAndOldGoodsAreaEntity> lookModifyGoodsAreaByOrgcode(
			String orgCode, String id) {
		List<NewAndOldGoodsAreaEntity> list = changeGoodsAreaDao.lookModifyGoodsAreaByOrgcode(orgCode,id);
		return list;
	}

	
	/**
	* @description 根据部门code和id查询库区编码对应关系(查询页面)
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#lookLookGoodsAreaByOrgcode(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月16日 下午3:43:49
	* @version V1.0
	*/
	@Override
	public List<NewAndOldGoodsAreaEntity> lookLookGoodsAreaByOrgcode(
			String orgCode, String id) {
		List<NewAndOldGoodsAreaEntity> list = changeGoodsAreaDao.lookLookGoodsAreaByOrgcode(orgCode,id);
		return list;
	}

	
	/**
	* @description 将修改后的库区编号变更的信息写入数据库表中
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#modifyGoodsAreaInStock(java.util.List)
	* @author 218381-foss-lijie
	* @update 2015年4月15日 上午10:34:29
	* @version V1.0
	*/
	@Transactional//一个失败,全部回滚
	@Override
	public void updateGoodsAreaInStockById(List<NewAndOldGoodsAreaEntity> list,ChangeGoodsAreaEntity changeGoodsAreaEntity) {
		changeGoodsAreaDao.updateGoodsAreaInStockById(changeGoodsAreaEntity);
		for(NewAndOldGoodsAreaEntity n : list){
			changeGoodsAreaDao.updateNewCodeById(n);
		}
	}

	
	/**
	* @description 根据id插入新旧库区编号数据且将新库区code改为新库区code_new
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#createAndInsertDataById(java.util.List, java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月27日 上午9:59:27
	* @version V1.0
	*/
	@Transactional
	@Override
	public void insertDataById(List<NewAndOldGoodsAreaEntity> list,
			String orgCode, String id) throws StockException{
		//插入表tfr.t_opt_change_goodsarea_area  change_goodsarea_area_id为id
		String changeGoodsareaAreaId = id+"a";
		
		//将list中的数据插入到表中     
		for(NewAndOldGoodsAreaEntity n : list){
			//主键为id1
			String id1 = UUIDUtils.getUUID();
			n.setId(id1);
			n.setChange_goodsarea_area_id(changeGoodsareaAreaId);
			n.setNew_goods_area_code(n.getNew_goods_area_code()+"_new");
			changeGoodsAreaDao.insertNewCodeById(n);
		}
		//把tfr.t_opt_stock 的库区code变为    新库区code_new
		changeGoodsAreaDao.update_T_opt_stock_to_code_new(orgCode,changeGoodsareaAreaId);
		//把tfr.t_opt_waybill_stock的库区code变为   新库区code_new
		changeGoodsAreaDao.update_T_opt_waybill_stock_to_code_new(orgCode,changeGoodsareaAreaId);
		//把tfr.t_opt_stock 库区code带_new 的'_new'去掉
		changeGoodsAreaDao.update_T_opt_stock_delete_new(orgCode);
		//把tfr.t_opt_waybill_stock 库区code带_new 的'_new'去掉         但是合库区的时候可能会有问题,跟上一个不同
		//比如说   3  是部门就存在的,且有运单 xxxx 在里面了,那么  2号库区要变成-->3号库区(此库区里有xxxx运单号的话,就有问题了)
		//因为WAYBILL_NO, GOODS_AREA_CODE, ORG_CODE 是联合唯一索引,xxxx的库区编号是不能从2变为3的,因为3里面已经有xxxx了
		//出现这种情况 就要通过改 STOCK_GOODS_QTY(库存件数) 了    
		//此时要分3部来弄了,1 改   2 改不掉的就去加数量  3 把改不掉的数据删除
		// 1  把能改的改掉
		changeGoodsAreaDao.update_T_opt_waybill_stock_delete_new(orgCode);
		//2 有一些 '_new' 可能因为唯一索引没改掉  此时将 数量加上去
		changeGoodsAreaDao.update_T_opt_waybill_stock_add_new(orgCode);
		//3将改不掉的数据物理删除
		changeGoodsAreaDao.delete_T_opt_waybill_stock_new(orgCode);
		//将数据的状态改为已修改
		ChangeGoodsAreaEntity c = changeGoodsAreaDao.queryChangeGoodsAreaEntityById(id);
		if(c!=null){
			changeGoodsAreaDao.modifiedChangeGoodsArea(id);
		}else{
			throw new TfrBusinessException("数据不存在");
		}
	}


	/**
	* @description 判断货物类型是否为快递
	* @param waybillEntity
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015-1-14 下午5:19:50
	*/
	private Boolean isExpress(WaybillEntity waybillEntity){
		if(StringUtils.equals(waybillEntity.getProductCode(),WaybillConstants.PACKAGE)||
				StringUtils.equals(waybillEntity.getProductCode(),"RCP")
				||StringUtils.equals(waybillEntity.getProductCode(),"EPEP")
				||StringUtils.equals(waybillEntity.getProductCode(),"DEAP")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据运输性质查询库区
	 * @param inOutStockEntity
	 * @return goodsAreaCode
	 * @author 218381-foss-lijie
	 * @date 2015-1-13 上午11:08:12
	 */
	private String queryGoodsAreaByEntity(InOutStockEntity inOutStockEntity,String goodsAreaType) {
		String goodsAreaCode = null;
		//这里应该来判断是否是驻地部门
		if(goodsAreaType.equals(DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION)){
			if(inOutStockEntity != null && inOutStockEntity.getWaybillNO() !=null){
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(inOutStockEntity.getWaybillNO());
			
				//快递
				if(isExpress(waybillEntity)){
					//BSE_GOODSAREA_TYPE_EXPRESS_STATION   快递驻地库区 对应的code
					goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS_STATION);
				}else{
					goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), goodsAreaType);
				}
			}else{
				if(inOutStockEntity != null){
					goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), goodsAreaType);
				}
			}
		}else{
			if(inOutStockEntity != null){
				goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), goodsAreaType);
			}
		}
		
		return goodsAreaCode;
	}
	

	
	
	/**
	* @description 增加库存(批量)
	* 1.增加货件库存
	* 2.更新运单库存
	* 3.保存入库动作
	* @param inOutStockEntity
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年3月9日 上午8:50:08
	*/
	@Transactional
	private void addStockBatch(List<InOutStockEntity> inOutStockEntityList) {
		String wayBillNo = "";
		Date planStartTime = null;
		
		
		//***********增加货件库存***************
		//********************保存入库动作**************************
		for (InOutStockEntity inOutStockEntity : inOutStockEntityList) {
			try{
				wayBillNo = inOutStockEntity.getWaybillNO();
				planStartTime = inOutStockEntity.getPlanStartTime();
				
				//***********增加货件库存***************
				//入库时间
				Date inStockTime = new Date();
				//封装入库参数
				StockEntity stockEntity = new StockEntity();
			
				stockEntity = convertInOutStockToStockEntity(inOutStockEntity,stockEntity);
				if(stockEntity.getInStockTime() == null){
					stockEntity.setInStockTime(inStockTime);
				}
				//保存货件库存
				stockDao.addStock(stockEntity);
				
				//********************保存入库动作**************************
				inOutStockEntity.setInOutStockTime(inStockTime);
				//设置动作有效
				inOutStockEntity.setIsValid(FossConstants.ACTIVE);
				//保存入库动作
				inOutStockDao.addInStock(inOutStockEntity);
			
			}catch(Exception ex){
				inOutStockEntity.setInOutStockTime(new Date());
				inOutStockEntity.setDeviceType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
				inOutStockEntity.setGoodsAreaCode(StockConstants.VIRTUAL_GOODS_AREA_CODE);
				stockDao.insertPdaUnloadmsg(inOutStockEntity);
			}
		}
		
		//根据运单号分析库存表里存在的库存件数以及入库时间
		List<WaybillStockEntity> stockGroupList = stockDao.queryStockByWaybillNoForGroup(wayBillNo);
		for (WaybillStockEntity waybillStockEntity : stockGroupList) {
			//**************更新运单库存****************
			//封装查询运单库存参数
			WaybillStockEntity waybillStock = new WaybillStockEntity();
			//运单号
			waybillStock.setWaybillNO(waybillStockEntity.getWaybillNO());
			//部门
			waybillStock.setOrgCode(waybillStockEntity.getOrgCode());
			//货区
			waybillStock.setGoodsAreaCode(waybillStockEntity.getGoodsAreaCode());
			//下一部门编号
			waybillStock.setNextOrgCode(waybillStockEntity.getNextOrgCode());
			//查询运单库存
			List<WaybillStockEntity> waybillStockList = waybillStockDao.queryWaybillStock(waybillStock);
			//不存在该运单库存
			if(waybillStockList.size() == 0){
				//最后一件的入库时间
				waybillStock.setInStockTime(waybillStockEntity.getInStockTime());
				waybillStock.setLastInStockTime(waybillStockEntity.getInStockTime());
				//库存件数
				waybillStock.setStockGoodsCount(waybillStockEntity.getStockGoodsCount());
				//计划出发时间
				waybillStock.setPlanStartTime(planStartTime);
				//库存件数 stockGoodsCount
				waybillStock.setStockGoodsCount(waybillStockEntity.getStockGoodsCount());
				//增加运单库存
				waybillStockDao.addWaybillStock(waybillStock);
				LOGGER.info("入库操作：增加新运单库存：运单号--" + waybillStock.getWaybillNO() + " 货区--" + waybillStock.getGoodsAreaCode() + " 库存件数--" + waybillStock.getStockGoodsCount());
			}else{//已存在该运单库存
				WaybillStockEntity  waybillStockOriginal = waybillStockList.get(0);
				LOGGER.info("入库操作：当前运单库存：运单号--" + waybillStockOriginal.getWaybillNO() + " 货区--" + waybillStockOriginal.getGoodsAreaCode() + " 库存件数--" + waybillStockOriginal.getStockGoodsCount());
				//最后一件的入库时间
				waybillStockOriginal.setInStockTime(waybillStockEntity.getInStockTime());
				waybillStockOriginal.setLastInStockTime(waybillStockEntity.getInStockTime());
				//下一部门编号
				waybillStockOriginal.setNextOrgCode(waybillStockEntity.getNextOrgCode());
				//计划出发时间
				waybillStockOriginal.setPlanStartTime(planStartTime);
				//库存件数 stockGoodsCount
				waybillStockOriginal.setStockGoodsCount(waybillStockEntity.getStockGoodsCount());
				//更新运单库存
				waybillStockDao.updateWaybillStockInStockBatch(waybillStockOriginal);
			}
		}
		
	}
	
	/**
	* @description  校验货件库存唯一性，并入库
	* @param inOutStockEntityList
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年3月9日 上午10:58:03
	*/
	@Transactional
	private int inStockBatch(List<InOutStockEntity> inOutStockEntityList) {
		List<InOutStockEntity> addStockEntityList = new ArrayList<InOutStockEntity>();
		for (InOutStockEntity inOutStockEntity : inOutStockEntityList) {
			List<StockEntity> stockList = stockDao.queryUniqueStock(inOutStockEntity);
			if (CollectionUtils.isNotEmpty(stockList)) {// 该货件存在库存中
				//获取该货件库存记录
				StockEntity stockOriginal = stockList.get(0);
				// 在本部门库存中
				if (StringUtils.equals(stockOriginal.getOrgCode(),inOutStockEntity.getOrgCode())){
					
					// 比较扫描时间
					if (inOutStockEntity.getScanTime().after(stockOriginal.getScanTime())){// 当前请求的扫描时间晚于库中的扫描时间
						// 封装该货件本部门库存记录的出库动作
						InOutStockEntity outStock = new InOutStockEntity();
						//将该货件在当前库存中的货区编号和部门编号设置给出库动作对象 
						outStock.setGoodsAreaCode(stockOriginal.getGoodsAreaCode());
						outStock.setOrgCode(stockOriginal.getOrgCode());
						//*************将请求参数中的信息设置给出库动作对象*************
						//运单号
						outStock.setWaybillNO(inOutStockEntity.getWaybillNO());
						//流水号
						outStock.setSerialNO(inOutStockEntity.getSerialNO());
						//设备类型
						outStock.setDeviceType(inOutStockEntity.getDeviceType());
						//入库类型
						outStock.setInOutStockType(inOutStockEntity.getInOutStockType());
						//操作人工号
						outStock.setOperatorCode(inOutStockEntity.getOperatorCode());
						//操作人姓名
						outStock.setOperatorName(inOutStockEntity.getOperatorName());
						//扫描时间
						outStock.setScanTime(inOutStockEntity.getScanTime());
						//从本部门原库存中出库
						this.deleteStock(outStock);
						
						addStockEntityList.add(inOutStockEntity);
					} else {// 当前请求的扫描时间早于库中的扫描时间
					}
				} else {// 在其它部门库存中
					
					// 封装该货件库存记录的出库动作
					InOutStockEntity outStock = new InOutStockEntity();
					//将该货件在当前库存中的货区编号和部门编号设置给出库动作对象 
					outStock.setGoodsAreaCode(stockOriginal.getGoodsAreaCode());
					outStock.setOrgCode(stockOriginal.getOrgCode());
					//***********将请求参数中的信息设置给出库动作对象****************
					//运单号
					outStock.setWaybillNO(inOutStockEntity.getWaybillNO());
					//流水号
					outStock.setSerialNO(inOutStockEntity.getSerialNO());
					//设备类型
					outStock.setDeviceType(inOutStockEntity.getDeviceType());
					//入库类型
					outStock.setInOutStockType(inOutStockEntity.getInOutStockType());
					//操作人工号
					outStock.setOperatorCode(inOutStockEntity.getOperatorCode());
					//操作人姓名
					outStock.setOperatorName(inOutStockEntity.getOperatorName());
					//扫描时间
					outStock.setScanTime(inOutStockEntity.getScanTime());
					// 从上一部门出库
					this.deleteStock(outStock);
					//发送出库通知消息
					this.sentMsg(inOutStockEntity, stockOriginal.getOrgCode());
					
					addStockEntityList.add(inOutStockEntity);
				}
			}else{//不在库存直接添加库存
				addStockEntityList.add(inOutStockEntity);
			}
		}
		//批量入库
		this.addStockBatch(addStockEntityList);
		return FossConstants.SUCCESS;
	}
	
	/**
	* @description  卸车入库 (批量卸车优化) 
	* @param inOutStockEntityList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年3月9日 上午11:20:11
	*/
	@Override
	public void batchInStockUnload(List<InOutStockEntity> inOutStockEntityList){
		//有记录且只有1件
		if(inOutStockEntityList!=null && inOutStockEntityList.size()>0 && inOutStockEntityList.size() ==1){
			inStockUnload(inOutStockEntityList.get(0));
		}//有记录且大于1件
		else if(inOutStockEntityList!=null && inOutStockEntityList.size()>0 && inOutStockEntityList.size() >1){
			inStockUnloadBatch(inOutStockEntityList);
		}
		//2017.1.6  注释 原来的 推送入库时间到ptp 扣款 改到营业部卸车扣款
		/*//PC端卸车入库推送数据到PTP---332219
		try {
			LOGGER.error("营业部交接推送入库时间到PTP start");
			this.fossToPTP(inOutStockEntityList);
			LOGGER.error("营业部交接推送入库时间到PTP end");
		} catch (Exception e) {
			LOGGER.error("营业部交接单入库推送PTP入库时间，异常详情："+e);
			
		}*/
		
	}

	/**
	* @description 卸车入库 (批量卸车优化)
	* @param inOutStockEntityList
	* @return
	* @throws StockException
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年3月9日 上午10:44:43
	*/
	@Transactional
	private int inStockUnloadBatch(List<InOutStockEntity> inOutStockEntityList) throws StockException{
		int backInt = FossConstants.SUCCESS;
		
		List<InOutStockEntity> sendEntityList  = new ArrayList<InOutStockEntity>();
		//营业部交接  by linhua.yan 360903 存放特殊库存
		List<InOutStockEntity> sendSaleEntityList  = new ArrayList<InOutStockEntity>();
		//inOutStockEntity
		if(inOutStockEntityList!=null && inOutStockEntityList.size()>0){
			for (InOutStockEntity inOutStockEntity : inOutStockEntityList) {
				backInt = FossConstants.SUCCESS;
				//用于存储是否为入虚拟库 by 360903
				boolean isSale = false;
				
				/**
				 * 查询运单状态 如果为 已作废 则不能入库
				 */
				ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(inOutStockEntity.getWaybillNO());
				if(actualFreightEntity!=null){
					if(StringUtils.equals(WaybillConstants.OBSOLETE, actualFreightEntity.getStatus()) || StringUtils.equals(WaybillConstants.ABORTED, actualFreightEntity.getStatus())){
						backInt = FossConstants.FAILURE;
					}
				}
				//断货物是否已签收的接口，参数（运单号、流水号）
				String checkSerialNoIsSign = signDetailService.querySerialNoIsSign(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO());
				//已签收出库 
				if(StringUtils.equals(FossConstants.YES, checkSerialNoIsSign)){
					backInt = FossConstants.FAILURE;
				}
				
				
				/**
				 * 根据运单和流水号 检验流水号是否有效
				 */
				//无标签或者异常货
				if(StringUtils.equals(StockConstants.NO_LABEL_GOODS_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) || StringUtils.equals(StockConstants.EXCEPTION_AREA_IN_STOCK_TYPE, inOutStockEntity.getInOutStockType()) ){
					//不需要检验运单号和流水号。
				}else{
					String checkStautsByWaybillNoAndSerialNo = labeledGoodService.getStautsByWaybillNoAndSerialNo(inOutStockEntity.getWaybillNO(), inOutStockEntity.getSerialNO());
					if(StringUtils.equals(FossConstants.YES, checkStautsByWaybillNoAndSerialNo)){
						//流水号有效 继续
					}else{
						//流水号无效
						//没有此流水号
						backInt = FossConstants.FAILURE;
					}
				}
				
				if(backInt == FossConstants.SUCCESS ){
					//设置操作出库设备类型
					inOutStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
					//设置当前调用时间为扫描时间
					inOutStockEntity.setScanTime(new Date());
					
					//操作人信息
					if(StringUtils.isBlank(inOutStockEntity.getOperatorName())){
						//操作人姓名
						String userName = this.getUserNameByCode(inOutStockEntity.getOperatorCode());
						inOutStockEntity.setOperatorName(userName);
					}
					//获取库存部门
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = this.verifyStockOrg(inOutStockEntity.getOrgCode());
					inOutStockEntity.setOrgCode(orgAdministrativeInfoEntity.getCode());
					//获取运单信息
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(inOutStockEntity.getWaybillNO());			
					//返回获取的交接单实体 by linhua.yan 360903
					HandOverBillEntity billEntity = handOverBillService.queryHandOverBillByNo(inOutStockEntity.getInOutStockBillNO());
					String productCode;
					if(waybillEntity != null){
						//产品编号
						productCode = waybillEntity.getProductCode();
					}else{
						LOGGER.error("运单 " + inOutStockEntity.getWaybillNO() + " 不存在");
						//抛出 运单不存异常
						throw new StockException(StockException.WAYBILL_NOT_EXIST_ERROR_CODE,"");
					}
					
					//根据走货路径设置下一部门、计划出发时间、货区
					//部门是外场类型
					if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
						//整车
						if(StringUtils.equals(FossConstants.YES, waybillEntity.getIsWholeVehicle())){
							//整车货区
							inOutStockEntity.setGoodsAreaCode(StockConstants.WHOLE_VEHICLE_GOODS_AREA_CODE);
							//计划出发时间
							inOutStockEntity.setPlanStartTime(new Date());
							//下一部门编号
							inOutStockEntity.setNextOrgCode(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
						//空运	
						}else if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, waybillEntity.getProductCode())){
//							//空运货区
//							String goodsAreaCode = this.queryGoodsAreaByType(inOutStockEntity.getOrgCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
//							//设置货区
//							inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
//							//下一部门编号
//							inOutStockEntity.setNextOrgCode(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
//							//计划出发时间
//							inOutStockEntity.setPlanStartTime(new Date());
							//外场编号
							inOutStockEntity = airNextOrgComm(inOutStockEntity,inOutStockEntity.getOrgCode(),inOutStockEntity.getOrgCode(),FossConstants.YES);
						}else{
							try{
								inOutStockEntity = goodsWalkPath(inOutStockEntity,inOutStockEntity.getOrgCode(),StockConstants.CONFIRM,FossConstants.YES);
							}catch (Exception e) {
								//散货货区
								inOutStockEntity.setGoodsAreaAdd(null);
							}
							inOutStockEntity = queryGoodsAreaCodeComm(inOutStockEntity,waybillEntity);
						}
					//部门是空运总调，入库到相应外场空运货区
					}else if(StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getAirDispatch())){
						//外场编号
						String transferCode = queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity.getCode());
						inOutStockEntity.setOrgCode(transferCode);
//						//计划出发时间
//						inOutStockEntity.setPlanStartTime(new Date());
//						//下一部门编号
//						inOutStockEntity.setNextOrgCode(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
						
//						try{
//							//空运货区
//							String goodsAreaCode = this.queryGoodsAreaByType(transferCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
//							inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
//						}catch(BusinessException e){
//							LOGGER.warn("卸车入库--部门：" + inOutStockEntity.getOrgCode()+ " 货物：" + inOutStockEntity.getWaybillNO() + "---" + inOutStockEntity.getSerialNO() +
//									"，查询走货路径或设置货区失败，入库到虚拟散货货区，异常信息：" + ExceptionUtils.getFullStackTrace(e));
//							//获取货区失败，设置散货虚拟货区
//							inOutStockEntity.setGoodsAreaCode(StockConstants.BULK_GOODS_AREA_CODE);
//						}
						inOutStockEntity = airNextOrgComm(inOutStockEntity,transferCode,inOutStockEntity.getOrgCode(),FossConstants.YES);
						
					}else{
						//胡岳改动 判断是否快递货
						if(StringUtils.equals(productCode,WaybillConstants.PACKAGE)||
								StringUtils.equals(productCode,"RCP")
								||StringUtils.equals(productCode,"EPEP")
								||StringUtils.equals(productCode,"DEAP")){
							//快递新规则 有可能卸车入开单营业部 所以要找下一部门和计划出发时间
							//设置下一部门、计划出发时间
							inOutStockEntity = this.setPlanStartTimeAndNextOrgCode(inOutStockEntity, StockConstants.CONFIRM);
						}else{
							//零担原有规则
							//营业部 无需设置货区，如果卸车部门是营业部则是最终到达部门 所以也无需设置 计划出发时间、下一部门
						}
						//整车
						if(StringUtils.equals(FossConstants.YES, waybillEntity.getIsWholeVehicle())){
							//计划出发时间
							inOutStockEntity.setPlanStartTime(new Date());
							//下一部门编号
							inOutStockEntity.setNextOrgCode(StockConstants.WHOLE_VEHICLE_NEXT_ORG_CODE);
						//空运	
						}else if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, waybillEntity.getProductCode())){
//							//下一部门编号
//							inOutStockEntity.setNextOrgCode(nextOrgCode);
//							//计划出发时间
//							inOutStockEntity.setPlanStartTime(planStartTime);
							
							inOutStockEntity = airNextOrgComm(inOutStockEntity,null,null,FossConstants.NO);
						}
						
						//合伙人入库虚拟库存 2016-09-29 alfred
						if(!StringUtils.equals(orgAdministrativeInfoEntity.getCode(), 
								waybillEntity.getCustomerPickupOrgCode())
								&& billEntity != null){
							//if(billEntity==null){
							//	LOGGER.error("运单 " + inOutStockEntity.getWaybillNO() + " 不存在");
								//抛出 交接单不存异常
							//	throw new StockException("交接单号不存在","");
						//	}
							//出发部门
							SaleDepartmentEntity departDeptEntity = saleDepartmentService.
									querySaleDepartmentInfoByCode(billEntity.getDepartDeptCode());
							//当前部门
							SaleDepartmentEntity arrivalDeptEntity = saleDepartmentService.
									querySaleDepartmentInfoByCode(orgAdministrativeInfoEntity.getCode());
							List<DeptTransferMappingEntity> deptTransferMappinglist  = deptTransferMappingService.
									queryDeptTransferMappingListByCode(waybillEntity.getCustomerPickupOrgCode());
							//出发部门映射集
							List<DeptTransferMappingEntity> deptTrans = deptTransferMappingService.
									queryDeptTransferMappingListByCode(billEntity.getDepartDeptCode());
							//该标识用于判断入库的营业部有合伙人映射，且和运单提货网点有关联
							boolean inStockSalefalg =  isPTPStock(orgAdministrativeInfoEntity.getCode(), waybillEntity.getCustomerPickupOrgCode(),billEntity.getHandOverType());
							if( inStockSalefalg){
								//入虚拟库存 设值为true
								isSale = true;
								if(null!=departDeptEntity
										&& CollectionUtils.isNotEmpty(deptTrans)){
									//二级到一级
									if(StringUtils.equals(departDeptEntity.getIsTwoLevelNetwork(), FossConstants.YES)){
										inOutStockEntity.setNextOrgCode(deptTrans.get(0).getDeptCode());
									}//营业部到一级
									else if(StringUtils.equals(departDeptEntity.getIsLeagueSaleDept(), FossConstants.NO)){
										inOutStockEntity.setNextOrgCode(waybillEntity.getCustomerPickupOrgCode());
									}//一级到营业部
									else if(StringUtils.equals(departDeptEntity.getIsLeagueSaleDept(), FossConstants.YES)&&
											StringUtils.equals(departDeptEntity.getIsTwoLevelNetwork(), FossConstants.NO)){
										inOutStockEntity.setNextOrgCode(lineService.
												queryTransferCodeListBySourceCode(orgAdministrativeInfoEntity.getCode()).get(0));
									}
								}else if(arrivalDeptEntity != null){
									//外场到营业部
									if(StringUtils.equals(arrivalDeptEntity.getIsLeagueSaleDept(), FossConstants.NO)){
										inOutStockEntity.setNextOrgCode(deptTransferMappinglist.get(0).getFthNetworkCode());
									}else{//外场到一级
										inOutStockEntity.setNextOrgCode(waybillEntity.getCustomerPickupOrgCode());
									}
								}
								inOutStockEntity.setGoodsAreaCode(StockConstants.VIRTUAL_GOODS_AREA_CODE);
								inOutStockEntity.setGoodsAreaAdd(StockConstants.VIRTUAL_GOODS_AREA_CODE);
								sendSaleEntityList.add(inOutStockEntity);								
							}
						}
					}
					
					/**
					 * denghanchao  7天返货 
					 */
					//根据运单号查询返货类型
					String returntype = waybillManagerService.selectReturnType(inOutStockEntity.getWaybillNO());
					//如果返货类型为7天返货则入异常货区不往下执行跳出该方法
					if(StockConstants.SEVEN_DAYS_RETURN.equals(returntype)){
						SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(inOutStockEntity.getOrgCode());
						if(saleDepartmentEntity!=null){
							inOutStockEntity.setGoodsAreaCode(StockConstants.VIRTUAL_GOODS_AREA_CODE);
							inOutStockEntity.setGoodsAreaAdd(StockConstants.VIRTUAL_GOODS_AREA_CODE);
						}else{
							//入库异常货物
							String goodsAreaCode = this.queryGoodsAreaByEntity(inOutStockEntity, DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
							inOutStockEntity.setGoodsAreaCode(goodsAreaCode);
							inOutStockEntity.setGoodsAreaAdd(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
						}
					}
					//如果没入虚拟库存，入实体 by 360903
					if(!isSale){
						sendEntityList.add(inOutStockEntity);
					}
				}else{//不能入库
					
				}
			}
		}
		//营业部批量入库 360903
		if(sendSaleEntityList.size()>0){
				this.inStockSaleBatch(sendSaleEntityList);
		}	
		//批量入库
		if(CollectionUtils.isNotEmpty(sendEntityList)){
			this.inStockBatch(sendEntityList);
			for (InOutStockEntity dto : sendEntityList) {
				//移动代办事项
				waybillRfcService.queryTodoWhenDumpTruck(dto.getWaybillNO(),dto.getSerialNO(),dto.getOrgCode(), null);	
			}
		}
	
		return backInt;		
	}
	/**
	 * @author nly
	 * @date 2015年4月22日 上午8:51:14
	 * @function 根据入库类型获取入库记录
	 * @param waybillNo
	 * @param serialNo
	 * @param inStockTypeList
	 * @return
	 */
	@Override
	public List<InOutStockEntity> queryInStockInfoByType(String waybillNo,String serialNo,List<String> inStockTypeList) {
		if(StringUtils.isEmpty(waybillNo)) {
			return null;
		}
		List<InOutStockEntity> list = stockDao.queryInStockInfoByType(waybillNo, serialNo, inStockTypeList);
		return list;
	}
	

	/**
	* @description 根据驻地部门code查询对应的驻地库区
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#stationAreaByOrgcodeList(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年6月8日 下午6:45:28
	* @version V1.0
	*/
	@Override
	public List<BaseDataDictDto> stationAreaByOrgcodeList(String orgCode) {
		List<BaseDataDictDto> specialAreaList = new ArrayList<BaseDataDictDto>();
		//驻地派送部的货区
		List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaByStationSalesDept(orgCode);
		if(goodsAreaList!=null&&goodsAreaList.size()>0){
			for (GoodsAreaEntity goodsAreaEntity : goodsAreaList) {
				if(goodsAreaEntity!=null){
					BaseDataDictDto bdd = new BaseDataDictDto();
					bdd.setValueCode(goodsAreaEntity.getGoodsAreaCode());
					bdd.setValueName(goodsAreaEntity.getGoodsAreaName());
					specialAreaList.add(bdd);
				}
			}
		}
		return specialAreaList;
	}
	

	
	/**
	* @description 在丢货改善小组超过28天的运单
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月8日 下午4:16:10
	*/
	@Override
	public void dayLoseGoodsForGuiji28() {
		List<String> loseGoodsList=null;
		try {
			loseGoodsList = stockDao.dayLoseGoodsForGuiji28();
		} catch (Exception e) {
			LOGGER.error("在丢货改善小组超过28天的运单:dayLoseGoodsForGuiji28 查询方法出错");
		}
		if(loseGoodsList!=null && loseGoodsList.size()>0){
			try {
				pushTrackForCaiNiaoService.judgeTrakcForExpctionStock(loseGoodsList);
			} catch (Exception e) {
				LOGGER.error("在丢货改善小组超过28天的运单:货物轨迹推送接口 judgeTrakcForExpctionStock方法出错");
			}
		}
		
		
	}
	
	/*
	 * 通过运单号来判断是否在外场库存(外发类型有一票流水号在外场库存 则入异常库存)
	 * 218427-foss-hongwy
	 * 
	 * */
	public void  isInTransferCenter(String waybillNo){
		List<WaybillStockEntity> list = stockDao.queryStockByWaybillNoForGroup(waybillNo);
		//是否在外场库存，有一票在外场库存 即入外场库存
		if(CollectionUtils.isNotEmpty(list)){
			for(WaybillStockEntity waybillStockEntity : list){
				 String orgCodeT = waybillStockEntity.getOrgCode();
				 OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCodeT);
				if(orgAdministrativeInfoEntity.checkTransferCenter()){
					List<StockEntity> mxList = stockDao.queryStockByWaybillNoOrgCode(waybillNo,orgCodeT);
					for (StockEntity stockEntity : mxList) {
						InOutStockEntity inOutStockEntity = new InOutStockEntity();
						inOutStockEntity.setWaybillNO(stockEntity.getWaybillNO());
						inOutStockEntity.setSerialNO(stockEntity.getSerialNO());						
						if(StringUtils.isBlank(stockEntity.getOperatorCode())){
							inOutStockEntity.setOperatorCode("OUTBOUND_THREE_DAYS_RETURN");
						}else{
							inOutStockEntity.setOperatorCode(stockEntity.getOperatorCode());
						}
						
						
						if(StringUtils.isBlank(stockEntity.getOperatorName())){
							inOutStockEntity.setOperatorName("OUTBOUND_THREE_DAYS_RETURN");
						}else{
							inOutStockEntity.setOperatorName(stockEntity.getOperatorName());
						}
						
						inOutStockEntity.setOrgCode(stockEntity.getOrgCode());
						inOutStockEntity.setGoodsAreaCode(stockEntity.getGoodsAreaCode());
						inOutStockEntity.setDeviceType(stockEntity.getDeviceType());
						inOutStockEntity.setScanTime(stockEntity.getScanTime());
						inOutStockEntity.setNextOrgCode(stockEntity.getNextOrgCode());
						inOutStockEntity.setPosition(stockEntity.getPosition());
						inStockPC(inOutStockEntity);
					}
				}
			}
		}
	}
	/**
	 * 判断是不是快递运单号
	 * author 268084
	 * 
	 */
	@Override
	public boolean ifIsExpressWaybill(String waybillNo){
		if(waybillNo==null||waybillNo.equals("")){
			return false;
		}
		try{
			return waybillStockDao.ifIsExpressWaybill(waybillNo);
		}catch(Exception e){
			LOGGER.error("在判断是否为快递运单号是出错！");
			return false;
		}
		
	}
	

	/**
	 * @description 根据运单号查询库存
	 * @author 273247
	 * @param waybillNO
	 * @return
	 */
	@Override
	public boolean  queryStockByWaybillNo(String waybillNO) {
		//封装查询运单库存参数
				WaybillStockEntity waybillStock = new WaybillStockEntity();
				waybillStock.setWaybillNO(waybillNO);
				//获取运单库存信息
				List<StockEntity>  stockStatus = stockService.queryStockByWaybillNo(waybillStock);
				//获取运单信息
				WaybillEntity entity = waybillManagerService.queryWaybillBasicByNo(waybillNO);
				//获取运单到达部门code
				String destOrgCode =entity.getCustomerPickupOrgCode();
				//查询达到营业部的部门信息
				SaleDepartmentEntity dept = saleDepartmentService.querySaleDepartmentByCode(destOrgCode);
				boolean stock=true;
				boolean isWaiFa = handOverBillService
						.queryBeLdpHandOveredByWaybillNo(waybillNO);
				if(!isWaiFa){
					if(CollectionUtils.isNotEmpty(stockStatus)){
						if(stockStatus.size() ==entity.getGoodsQtyTotal()){
							//运单到达部门为驻地营业部                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
							if(dept !=null && dept.getStation().equals(FossConstants.ACTIVE)){
								//取外场部门CODE
								String transferCenter = dept.getTransferCenter();
								for(StockEntity stockStatu:stockStatus){
									String orgCode =stockStatu.getOrgCode();
									//匹配库存部门编码
									if(transferCenter.equals(orgCode)){
										stock=false;
									}else{
										return true;
									}
								}
							}else{
								//非驻地营业部
								for(StockEntity stockStatu:stockStatus){
									String orgCode =stockStatu.getOrgCode();
									if(destOrgCode.equals(orgCode)){
										stock=false;
									}else{
										return true;
									}
								}
							}		
							
						}
					}
				}else{
					return false;
				}
				return stock;
	}

	//菜鸟子母件判断 当前单号是否为丢货找到 hwy 218427
	public String queryLostFindGoods(String waybillNo,String serialNo){
		
		InOutStockEntity entities = null;
		//子母件丢货找到查询
		entities = stockDao.queryLostFindGoods(waybillNo,serialNo);
	    if(entities!=null ){
	    	return FossConstants.YES;
	    }else{
	    	return FossConstants.NO;
	    }
	}
	
	
	@Override
	public List<StockEntity> queryStockByWaybillNoInStockTime(
			WaybillStockEntity waybillStockEntity) {
		//查询库存表
		return stockDao.queryStockByWaybillNoInStockTime(waybillStockEntity);	
	}
	/**
	 * 根据CRM传过来的运单号查询货件库存
	 * @author 273247
	 * @return
	 */
	public List<StockEntity> queryStockByCrmWaybillNo(String waybillNo)
	{
		return stockDao.queryStockByCrmWaybillNo(waybillNo);
	}
	
	/**
	 * 根据运单编号查询库存中的当前部门
	 * @author 336540
	 * @date 2016-10-16 11:20:50
	 */
	public String stockQueryOrgCodeByWaybillNo(String waybillNo){
		return  stockDao.stockQueryOrgCodeByWaybillNo(waybillNo);
	}
	
	/**
	 * @function 根据运单号,部门code查询运单件数
	 * @author 218381-foss-lijie
	 * @date 2016年03月15日 上午11:15:14
	 * @param waybillNo
	 * @param OrgCode
	 * @return
	 */
	@Override
	public int queryCountByWaybillNoAndOrgCode(String waybillNo, String orgCode) {
		if(waybillNo == null || orgCode == null || waybillNo.equals("") || orgCode.equals("")){
			LOGGER.error("调用queryCountByWaybillNoAndOrgCode()运单号,部门编码不能为空");
			throw new StockException("调用StockService.queryCountByWaybillNoAndOrgCode()运单号,部门编码不能为空");
		}
		return stockDao.queryCountByWaybillNoAndOrgCode(waybillNo,orgCode);
	}
	
	/**
	 * 校验货件库存唯一性，并入库
	 * @author 360903  linhua.yan 
	 * @date 2016年9月19日
	 */
	@Transactional
	private int inStockSale(InOutStockEntity inOutStockEntity) {
		//实体库存
		List<StockEntity> stockList = stockDao.queryUniqueStock(inOutStockEntity);
		//卸车入库时，若存在实体库存数据，则只保留入库动作
		if(CollectionUtils.isNotEmpty(stockList)){
			inOutStockEntity.setInOutStockTime(new Date());
			//设置入库动作无效
			inOutStockEntity.setIsValid(FossConstants.INACTIVE);
			//保存入库动作
			inOutStockDao.addInStock(inOutStockEntity);
			return FossConstants.SUCCESS;
		}
		List<StockSaleEntity> stockSaleList = stockDao.queryUniqueStockSale(inOutStockEntity);
		if (CollectionUtils.isNotEmpty(stockSaleList)) {
			inOutStockEntity.setInOutStockTime(new Date());
			//设置入库动作无效
			inOutStockEntity.setIsValid(FossConstants.INACTIVE);
			//保存入库动作
			inOutStockDao.addInStock(inOutStockEntity);
		}else{
			//入库
			this.addStockSale(inOutStockEntity);
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 校验货件库存唯一性，并出库
	 * @author  360903 
	 * @date 2016年10月20日 19:24:28
	 */
	@Transactional
	private void outStockSalePC(InOutStockEntity inOutStockEntity){	
		List<StockSaleEntity> stockList = stockDao.queryUniqueStockSale(inOutStockEntity);
		// 该货件存虚拟库存中
		if (CollectionUtils.isNotEmpty(stockList)) {
				//出库部门
				inOutStockEntity.setOrgCode(stockList.get(0).getOrgCode());
			   //虚拟流水出库
				stockDao.outStockSalePC(inOutStockEntity)	;
				//虚拟运单库存出库
        	  	waybillStockDao.outWaybillStockSaleSerialNo(inOutStockEntity);
    			//封装查询运单库存参数
    			WaybillStockSaleEntity waybillStockPram = new WaybillStockSaleEntity();
    			waybillStockPram.setWaybillNo(inOutStockEntity.getWaybillNO());
    			waybillStockPram.setOrgCode(inOutStockEntity.getOrgCode());			
    			//所有流水都出库后，改变运单库存状态
        	  	List<WaybillStockSaleEntity> saleList = 	waybillStockDao.queryWaybillStockSale(waybillStockPram);
        	  	if(CollectionUtils.isNotEmpty(saleList)){
        	  		int stockNum = saleList.get(0).getStockGoodsCount();
        	  		if(stockNum == 0){
        	  			List<String> waybillStockAll = new ArrayList<String>();
        	  			waybillStockAll.add(inOutStockEntity.getWaybillNO());
        	  			waybillStockDao.outWaybillStockSaleAll(inOutStockEntity.getOrgCode(),waybillStockAll);
        	  		}
        	  	}
		 }		
	}
	
	
	/**营业部交接
	 * 增加库存
	 * 1.增加货件库存
	 * 2.更新运单库存
	 * 3.保存入库动作
	 * @author 360903 linhua.yan
	 * @date 2016年9月19日 22:20:39
	 */
	@Override
	@Transactional
	public void addStockSale(InOutStockEntity inOutStockEntity) {
		//***********增加货件库存***************
		//封装入库参数
		StockSaleEntity stockSaleEntity = new StockSaleEntity();
		//入库时间
		Date inStockTime = new Date();
		//运单号
		stockSaleEntity.setWaybillNo(inOutStockEntity.getWaybillNO());
		//流水号
		stockSaleEntity.setSerialNO(inOutStockEntity.getSerialNO());
		//操作人工号
		stockSaleEntity.setOperatorCode(inOutStockEntity.getOperatorCode());
		//操作人姓名
		stockSaleEntity.setOperatorName(inOutStockEntity.getOperatorName());
		//货区编号
		stockSaleEntity.setGoodsAreaCode(inOutStockEntity.getGoodsAreaCode());
		//设备类型
		stockSaleEntity.setDeviceType(inOutStockEntity.getDeviceType());
		//部门编号
		stockSaleEntity.setOrgCode(inOutStockEntity.getOrgCode());
		//入库时间
		stockSaleEntity.setInStockTime(inStockTime);
		//扫描时间
		stockSaleEntity.setScanTime(inOutStockEntity.getScanTime());
		//下一部门编号
		stockSaleEntity.setNextOrgCode(inOutStockEntity.getNextOrgCode());	
		//保存货件库存
		stockDao.addStockSale(stockSaleEntity);
		//**************更新运单库存临时表****************
		//封装查询运单库存参数
		WaybillStockSaleEntity waybillStockSale = new WaybillStockSaleEntity();
		//运单号
		waybillStockSale.setWaybillNo(stockSaleEntity.getWaybillNo());
		//部门
		waybillStockSale.setOrgCode(stockSaleEntity.getOrgCode());
		//货区
		waybillStockSale.setGoodsAreaCode(stockSaleEntity.getGoodsAreaCode());
		//下一部门编号
		waybillStockSale.setNextOrgCode(stockSaleEntity.getNextOrgCode());
		//查询运单库存
		List<WaybillStockSaleEntity> waybillStockSaleList = waybillStockDao.queryWaybillStockSale(waybillStockSale);
		//不存在该运单库存
		if(waybillStockSaleList.size() == 0){
			//该票第一件入库时间 及最后一件入库时间
			waybillStockSale.setInStockTime(inStockTime);
			waybillStockSale.setLastInStockTime(inStockTime);	
			//库存件数
			waybillStockSale.setStockGoodsCount(1);
			//计划出发时间
			waybillStockSale.setPlanStartTime(inStockTime);
			//增加运单库存
			waybillStockDao.addWaybillStockSale(waybillStockSale);
			LOGGER.info("入库操作：增加新运单库存临时表：运单号--" + waybillStockSale.getWaybillNo() + " 货区--" + waybillStockSale.getGoodsAreaCode() + " 库存件数--" + waybillStockSale.getStockGoodsCount());
		}else{//已存在该运单库存
			WaybillStockSaleEntity  waybillStockSaleOriginal = waybillStockSaleList.get(0);
			LOGGER.info("入库操作：当前运单库存：运单号--" + waybillStockSaleOriginal.getWaybillNo() + " 货区--" + waybillStockSaleOriginal.getGoodsAreaCode() + " 库存件数--" + waybillStockSaleOriginal.getStockGoodsCount());
			//不是在本部门 货区中移动 则更新该票最后一件入库时间
			waybillStockSaleOriginal.setLastInStockTime(inStockTime);
			//下一部门编号
			waybillStockSaleOriginal.setNextOrgCode(stockSaleEntity.getNextOrgCode());
			//计划出发时间
			waybillStockSaleOriginal.setPlanStartTime(inStockTime);
			//更新运单库存
			waybillStockDao.updateWaybillStockSaleInStock(waybillStockSaleOriginal);
		}
		//********************保存入库动作**************************
		inOutStockEntity.setInOutStockTime(inStockTime);
		//设置动作有效
		inOutStockEntity.setIsValid(FossConstants.ACTIVE);
		//保存入库动作
		inOutStockDao.addInStock(inOutStockEntity);
		////2017.1.6  注释 原来的 推送入库时间到ptp 扣款 改到营业部卸车扣款
		/*//FOSS推送入库时间至PTP
		try {
			LOGGER.error("营业部交接推送入库时间到PTP start");
			List<InOutStockEntity> inOutStockEntityList = new ArrayList<InOutStockEntity>();
			inOutStockEntityList.add(inOutStockEntity);
			this.fossToPTP(inOutStockEntityList);
			LOGGER.error("营业部交接推送入库时间到PTP end");
		} catch (Exception e) {
			LOGGER.error("营业部交接单入库推送PTP入库时间，异常详情："+e);
		}*/
	}
	
	/**营业部交接
	* @description  校验货件库存唯一性，并入库
	* @param inOutStockEntityList
	* @return
	* @version 1.0
	* @author 360903
	* @date 2016年9月20日 02:08:44
	*/
	@Transactional
	private int inStockSaleBatch(List<InOutStockEntity> inOutStockEntityList) {
		List<InOutStockEntity> addStockEntityList = new ArrayList<InOutStockEntity>();
		for (InOutStockEntity inOutStockEntity : inOutStockEntityList) {
			//实体库存
			List<StockEntity> stockList = stockDao.queryUniqueStock(inOutStockEntity);
			//虚拟入库时，若存在实体库存，则不入库 只记录入库动作
			if(CollectionUtils.isNotEmpty(stockList)){
				//********************保存入库动作**************************
				inOutStockEntity.setInOutStockTime(new Date());
				//设置动作有效
				inOutStockEntity.setIsValid(FossConstants.INACTIVE);
				//保存入库动作
				inOutStockDao.addInStock(inOutStockEntity);
				continue;
			}else{
				//虚拟库存
				List<StockSaleEntity> stockSaleList = stockDao.queryUniqueStockSale(inOutStockEntity);		
				if (CollectionUtils.isEmpty(stockSaleList)) {
					addStockEntityList.add(inOutStockEntity);
				}
		   }
		}
		if(CollectionUtils.isNotEmpty(addStockEntityList)){
			//批量入库
			this.addStockSaleBatch(addStockEntityList);
		}
		return FossConstants.SUCCESS;
	}


		/**
		* @description 增加库存(批量)
		* 1.增加货件库存
		* 2.更新运单库存
		* 3.保存入库动作
		* @param inOutStockEntity
		* @version 1.0
		* @author 14022-foss-songjie
		* @update 2015年3月9日 上午8:50:08
		*/
		@Transactional
		private void addStockSaleBatch(List<InOutStockEntity> inOutStockEntityList) {
			String wayBillNo = "";
		Date planStartTime = null;
		//***********增加货件库存***************
		//********************保存入库动作**************************
		for (InOutStockEntity inOutStockEntity : inOutStockEntityList) {
			try{
				wayBillNo = inOutStockEntity.getWaybillNO();
				planStartTime = inOutStockEntity.getPlanStartTime();	
				//***********增加货件库存***************
				//入库时间
				Date inStockTime = new Date();
				//封装入库参数
				StockSaleEntity stockSaleEntity = new StockSaleEntity();
				//运单号
				stockSaleEntity.setWaybillNo(inOutStockEntity.getWaybillNO());
				//流水号
				stockSaleEntity.setSerialNO(inOutStockEntity.getSerialNO());
				//操作人工号
				stockSaleEntity.setOperatorCode(inOutStockEntity.getOperatorCode());
				//操作人姓名
				stockSaleEntity.setOperatorName(inOutStockEntity.getOperatorName());
				//货区编号
				stockSaleEntity.setGoodsAreaCode(inOutStockEntity.getGoodsAreaCode());
				//设备类型
				stockSaleEntity.setDeviceType(inOutStockEntity.getDeviceType());
				//部门编号
				stockSaleEntity.setOrgCode(inOutStockEntity.getOrgCode());
				//入库时间
				stockSaleEntity.setInStockTime(inStockTime);
				//扫描时间
				stockSaleEntity.setScanTime(inOutStockEntity.getScanTime());
				//下一部门编号
				stockSaleEntity.setNextOrgCode(inOutStockEntity.getNextOrgCode());	
				//保存货件库存
				stockDao.addStockSale(stockSaleEntity);
				
				//********************保存入库动作**************************
				inOutStockEntity.setInOutStockTime(inStockTime);
				//设置动作有效
				inOutStockEntity.setIsValid(FossConstants.ACTIVE);
				//保存入库动作
				inOutStockDao.addInStock(inOutStockEntity);
			
			}catch(Exception ex){
				inOutStockEntity.setInOutStockTime(new Date());
				inOutStockEntity.setDeviceType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
				inOutStockEntity.setGoodsAreaCode(StockConstants.VIRTUAL_GOODS_AREA_CODE);
				stockDao.insertPdaUnloadmsg(inOutStockEntity);
			}
		}
		//根据运单号分析库存表里存在的库存件数以及入库时间
		List<WaybillStockSaleEntity> stockGroupList = stockDao.queryStockSaleByWaybillNoForGroup(wayBillNo);
		for (WaybillStockSaleEntity waybillStockEntity : stockGroupList) {
			//**************更新运单库存****************
			//封装查询运单库存参数
			WaybillStockSaleEntity waybillStock = new WaybillStockSaleEntity();
			//运单号
			waybillStock.setWaybillNo(waybillStockEntity.getWaybillNo());
			//部门
			waybillStock.setOrgCode(waybillStockEntity.getOrgCode());
			//货区
			waybillStock.setGoodsAreaCode(waybillStockEntity.getGoodsAreaCode());
			//下一部门编号
			waybillStock.setNextOrgCode(waybillStockEntity.getNextOrgCode());
			//查询运单库存
			List<WaybillStockSaleEntity> waybillStockSaleList = waybillStockDao.queryWaybillStockSale(waybillStock);
			//不存在该运单库存
			if(waybillStockSaleList.size() == 0){
				//最后一件的入库时间
				waybillStock.setInStockTime(waybillStockEntity.getInStockTime());
				waybillStock.setLastInStockTime(waybillStockEntity.getInStockTime());
				//库存件数
				waybillStock.setStockGoodsCount(waybillStockEntity.getStockGoodsCount());
				//计划出发时间
				waybillStock.setPlanStartTime(planStartTime);
				//库存件数 stockGoodsCount
				waybillStock.setStockGoodsCount(waybillStockEntity.getStockGoodsCount());
				//增加运单库存
				waybillStockDao.addWaybillStockSale(waybillStock);
				LOGGER.info("入库操作：增加新运单库存：运单号--" + waybillStock.getWaybillNo() + " 货区--" + waybillStock.getGoodsAreaCode() + " 库存件数--" + waybillStock.getStockGoodsCount());
			}else{//已存在该运单库存
				WaybillStockSaleEntity  waybillStockOriginal = waybillStockSaleList.get(0);
				LOGGER.info("入库操作：当前运单库存：运单号--" + waybillStockOriginal.getWaybillNo() + " 货区--" + waybillStockOriginal.getGoodsAreaCode() + " 库存件数--" + waybillStockOriginal.getStockGoodsCount());
				//最后一件的入库时间
				waybillStockOriginal.setInStockTime(waybillStockEntity.getInStockTime());
				waybillStockOriginal.setLastInStockTime(waybillStockEntity.getInStockTime());
				//下一部门编号
				waybillStockOriginal.setNextOrgCode(waybillStockEntity.getNextOrgCode());
				//计划出发时间
				waybillStockOriginal.setPlanStartTime(new Date());
				//库存件数 stockGoodsCount
				waybillStockOriginal.setStockGoodsCount(waybillStockEntity.getStockGoodsCount());
				//更新运单库存
				waybillStockDao.updateWaybillStockSaleInStockBatch(waybillStockOriginal);
			}
		}
		}
		
		/**营业部交接
		 * 批量出库
		 * @author 360903
		 * @date 2016年9月20日 15:42:16
		 */
		@Override
		public int outStockSaleBatchPC(List<InOutStockEntity> outStockList) throws StockException{
			for(InOutStockEntity outStockEntity:outStockList){
				stockDao.outStockSaleBatchPC(outStockEntity);
			}
			return FossConstants.SUCCESS;
		}
		
		/**营业部交接
		 * 更新流水号库存预配状态
		 * @author 360903
		 * @date 2016年9月20日 16:55:05
		 */
		@Override
		public boolean updatePreHandOverStateSale(
				List<UpdateStockPreHandOverStateDto> dtoList,String targetStatus) throws StockException{
			for(int i = 0;i < dtoList.size();i++){
				//查询库存
				StockSaleEntity stockSaleEntity = queryStockSaleEntityByNos(dtoList.get(i).getDeptCode(),dtoList.get(i).getWaybillNo(),dtoList.get(i).getSerialNo(),null);
				//如果置空库存预配状态
				if(targetStatus == null){
					if(stockSaleEntity != null){
						//部门
						stockSaleEntity.setOrgCode(dtoList.get(i).getDeptCode());
						//运单号
						stockSaleEntity.setWaybillNo(dtoList.get(i).getWaybillNo());
						//流水号
						stockSaleEntity.setSerialNO(dtoList.get(i).getSerialNo());
						//预配状态
						stockSaleEntity.setPreHandOverState(targetStatus);
						//更新库存预配状态
						stockDao.updatePreHandOverStateSale(stockSaleEntity);
					}
					//如果更新库存的预配状态
				}else{
					if(stockSaleEntity == null){
						LOGGER.info("更新预配状态失败：运单号：" + dtoList.get(i).getWaybillNo() + " 流水号：" + dtoList.get(i).getSerialNo() + " 不在库存");
						//throw new StockException(StockException.GOODS_NOT_EXIST_LOCAL_STOCK_ERROR_CODE,new Object[]{dtoList.get(i).getWaybillNo(),dtoList.get(i).getSerialNo()});
					}else if(StringUtils.equals(stockSaleEntity.getPreHandOverState(), StockConstants.PRE_HANDOVER_STATUS)){
						LOGGER.error("更新预配状态失败：运单号：" + dtoList.get(i).getWaybillNo() + " 流水号：" + dtoList.get(i).getSerialNo() + " 已被预配");
						throw new StockException(StockException.GOODS_ALREADY_PRE_HANDOVER_ERROR_CODE,new Object[]{dtoList.get(i).getWaybillNo(),dtoList.get(i).getSerialNo()});
					}else{
						//部门
						stockSaleEntity.setOrgCode(dtoList.get(i).getDeptCode());
						//运单号
						stockSaleEntity.setWaybillNo(dtoList.get(i).getWaybillNo());
						//流水号
						stockSaleEntity.setSerialNO(dtoList.get(i).getSerialNo());
						//预配状态
						stockSaleEntity.setPreHandOverState(targetStatus);
						//更新库存预配状态
						stockDao.updatePreHandOverStateSale(stockSaleEntity);
					}
				}
			}
			return true;
		}
		
		/**营业部交接
		 * 根据库存部门、运单号、流水号获取件库存
		 * @param orgCode 库存部门
		 * @param waybillNO 运单号
		 * @param serialNO 流水号
		 * @param goodsAreaCode 货区编号
		 * @author 360903
		 * @date 2016年9月20日 16:57:49
		 */
		@Override
		public StockSaleEntity queryStockSaleEntityByNos(String orgCode, String waybillNO,
				String serialNO, String goodsAreaCode) {
			InOutStockEntity inOutStockEntity = new InOutStockEntity();
			inOutStockEntity.setOrgCode(orgCode);
			inOutStockEntity.setWaybillNO(waybillNO);
			inOutStockEntity.setSerialNO(serialNO);
			return stockDao.queryStockSaleEntityByNos(inOutStockEntity);
		}	
		
		/**营业部交接
		 * 根据库存部门、运单号、流水号获取件库存
		 * @author 360903
		 * @date 2016年9月20日 16:57:49
		 */
		@Override
		public StockSaleEntity queryUniqueStockSale(InOutStockEntity outStock){
			return stockDao.queryStockSaleEntityByNos(outStock);
		}
		
		/**营业部交接
		 * 根据出库集合查询是否存在虚拟库存，并出库
		 * @author 360903
		 * @date 2016年9月26日 22:30:38
		 */
		@Override
		public List<InOutStockEntity> queryStockInfoSale(List<InOutStockEntity> outStockList) {
			//将出库集合转换为运单集合
			List<String> waybillList = new ArrayList<String>();
			//将出库集合转换为运单集合
			List<String> stockSaleList = new ArrayList<String>();
			for(InOutStockEntity entity:outStockList){
					waybillList.add(entity.getWaybillNO());
			}		
			
			List<StockSaleEntity> stockList = stockDao.queryInStockInfoBywayBillList(waybillList,outStockList.get(0).getOrgCode());	
			if(CollectionUtils.isNotEmpty(stockList)){
				//虚拟库存有该运单提取放在stockSaleList
				for(StockSaleEntity stockSale:stockList){
						stockSaleList.add(stockSale.getWaybillNo());
				}
				//
				//记录虚拟出库的运单号，供运单库存出库
				List<String> waybillStockSaleList = new ArrayList<String>();
				//虚拟库存出库
				for(int i=0;i<outStockList.size();i++){
		          if (stockSaleList.contains(outStockList.get(i).getWaybillNO())) {
		        	  	//存在添加到虚拟出库的运单号集合
		        	  waybillStockSaleList.add(outStockList.get(i).getWaybillNO());
		        	    //运单存在出库，并移除
		        	  	stockDao.outStockSaleBatchPC(outStockList.get(i));
		        	  	//虚拟运单库存出库
		        	  	waybillStockDao.outWaybillStockSaleSerialNo(outStockList.get(i));
		    			//封装查询运单库存参数
		    			WaybillStockSaleEntity waybillStockPram = new WaybillStockSaleEntity();
		    			//运单号
		    			waybillStockPram.setWaybillNo(outStockList.get(i).getWaybillNO());
		    			//部门
		    			waybillStockPram.setOrgCode(outStockList.get(i).getOrgCode());
		    			
		    			//所有流水都出库后，改变运单库存状态
		        	  	List<WaybillStockSaleEntity> saleList = 	waybillStockDao.queryWaybillStockSale(waybillStockPram);
		        	  	if(CollectionUtils.isNotEmpty(saleList)){
		        	  		int stockNum = saleList.get(0).getStockGoodsCount();
		        	  		if(stockNum == 0){
		        	  			List<String> waybillStockAll = new ArrayList<String>();
		        	  			waybillStockAll.add(outStockList.get(i).getWaybillNO());
		        	  			waybillStockDao.outWaybillStockSaleAll(outStockList.get(i).getOrgCode(),waybillStockAll);
		        	  		}
		        	  	}
		        	  	outStockList.remove(i);
		        	  	i=i-1;
		           }
				}
			}
				return outStockList;
		}
		
		/**营业部交接
		 * 判定入库部门，存在合伙人映射，且提货网点存在该映射关系中
		 * @param currentOrgCode 当前部门
		 * @param customerPickupOrgCode 提货网点
		 * @author 360903
		 * @date 2016年9月26日 22:30:38
		 */
		public boolean isPTPStock(String currentOrgCode,String customerPickupOrgCode,String handOverType){
			//在不为终点站情况下，如果为营业部交接类型，直接如虚拟库存
			if(StringUtils.equals(LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT,handOverType)){
				return true;
			}
			//当前部门映射集
			List<DeptTransferMappingEntity> currentDeptTrans = deptTransferMappingService.
					queryDeptTransferMappingListByCode(currentOrgCode);
			if(CollectionUtils.isNotEmpty(currentDeptTrans)){
				//存放当前部门所有映射的部门code
				List<String> orgCodeList = new ArrayList<String>();
				for(DeptTransferMappingEntity cur:currentDeptTrans){
					//添加每行记录的一级、二级
					orgCodeList.add(cur.getDeptCode());
					orgCodeList.add(cur.getFthNetworkCode());
					orgCodeList.add(cur.getSecNetworkCode());		
				}
				if(orgCodeList.contains(customerPickupOrgCode)){
					return true;
				}
			}
			return false;
		}
		
		/**营业部交接
		 * 交接单作废，恢复库存信息
		 * @author 360903  linhua.yan 
		 * @date 2016年10月19日 15:08:44
		 */
		@Transactional
		private int inStockSalePC(InOutStockEntity inOutStockEntity) {
			//恢复虚拟库存数据
			stockDao.inStockSalePC(inOutStockEntity);
			//封装查询运单库存参数
			WaybillStockSaleEntity waybillStockSale = new WaybillStockSaleEntity();
			//运单号
			waybillStockSale.setWaybillNo(inOutStockEntity.getWaybillNO());
			//部门
			waybillStockSale.setOrgCode(inOutStockEntity.getOrgCode());
			//查询运单库存
			List<WaybillStockSaleEntity> waybillStockSaleList = waybillStockDao.queryWaybillStockSale(waybillStockSale);
			//恢复虚拟运单库存数据
			if (CollectionUtils.isNotEmpty(waybillStockSaleList)) {
					 waybillStockDao.inWaybillStockSalePC(inOutStockEntity.getOrgCode(), inOutStockEntity.getWaybillNO());
			} 	
			inOutStockEntity.setInOutStockTime(new Date());
			//设置入库动作无效
			inOutStockEntity.setIsValid(FossConstants.ACTIVE);
			//保存入库动作
			inOutStockDao.addInStock(inOutStockEntity);
			return FossConstants.SUCCESS;
		}
		/**合伙人三批，提供给接送货接口
		 * 是否经过第一外场
		 * @author 360903  linhua.yan 
		 * @date 2016年11月1日 11:00:24
		 */
		@Override
		public int whetherPass(String waybillNo){
			//运单号不能为空
			if(waybillNo == null || waybillNo.equals("") ){
				LOGGER.error("传入参数错误，运单号不能为空");
				throw new StockException("接送货调用StockService.whetherPass()传入运单号为空");
			}
			//获取运单的走货路径
			TransportPathEntity transportPath =  calculateTransportPathService.queryTransportPath(waybillNo);
			if(transportPath !=null){
					//通过开单部门取第一外场
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(transportPath.getBillingOrgCode());
					
					//开单部门为营业部
					if(orgAdministrativeInfoEntity !=null && StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getSalesDepartment())){
						//获取第一外场
						List<String> transferCodes = lineService.queryTransferCodeListBySourceCode(transportPath.getBillingOrgCode());	
					    //获取运单入库记录
						 List<String> inStockOrgCodes =  inOutStockDao.queryInDeptCodeByWaybillNo(waybillNo);
						 if(CollectionUtils.isNotEmpty(inStockOrgCodes)
								 && CollectionUtils.isNotEmpty(transferCodes)){
								//是否有第一外场的入库记录
								if(inStockOrgCodes.contains(transferCodes.get(0))){
									return FossConstants.SUCCESS;
								}
						 }
					}
					//开单部门为外场，则视为经过第一外场
					if(orgAdministrativeInfoEntity !=null  && StringUtils.equals(FossConstants.YES, orgAdministrativeInfoEntity.getTransferCenter())){
						return  FossConstants.SUCCESS;
					}
				}
					return FossConstants.FAILURE;
			}
		
		
		/** 
		 * @Title: querystockOrgListByWaybillNo 
		 * @Description: 查看运单是否存在于最终站
		 * @param waybillNo
		 * @return    
		 * @author: tfr-360903
		 * Date:2016年11月7日 11:04:30
		 */ 
		@Override
		public boolean querystockOrgListByWaybillNo(String waybillNo,String orgCode) {
			List<String> stockOrgList = waybillStockDao.queryStockOrgCodeByWaybillNo(waybillNo);
				if(CollectionUtils.isNotEmpty(stockOrgList)){
					if(stockOrgList.contains(orgCode)){
						return true;
					}
				}
				return false;
			}
		/**
		 * 营业部交接推送数据至PTP
		 * @param inOutStockEntityList
		 * @author 332219-foss
		 * @update 2016年12月16日
		 * @return
		 */
		//2017.1.6 ouy 要求注释 原来的 推送入库时间到ptp 扣款
		/*private void fossToPTP(List<InOutStockEntity> inOutStockEntityList){
			//做一个校验推过的运单号不在推送
			List<String> list = new ArrayList<String>();
			//集合不能为空
			if(CollectionUtils.isNotEmpty(inOutStockEntityList)){
				for(InOutStockEntity stockEntity:inOutStockEntityList){
					//获取运单号
					String waybillNO = stockEntity.getWaybillNO();
					LOGGER.error("营业部交接推送入库时间到PTP单号："+waybillNO);
					//该单号未被推送过才进行推送
					if(!list.contains(waybillNO)){
						//返回获取的交接单实体
						HandOverBillEntity billEntity = handOverBillService.queryHandOverBillByWaybillNo(waybillNO);
						LOGGER.error("测试交接单号："+waybillNO+" , 部门编码 "+stockEntity.getOrgCode() + " 是否营业部 " + falgArriveDept(stockEntity.getOrgCode()));
						//只有营业交接才推送
						if(billEntity != null && StringUtils.equals(billEntity.getHandOverType(), LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT) && falgArriveDept(stockEntity.getOrgCode())){
							try {
								List<WaybillTrackingsDto> waybillList=new ArrayList<WaybillTrackingsDto>();
								WaybillTrackingsDto waybillTrackingsDto=new WaybillTrackingsDto();
								waybillTrackingsDto.setWaybillNo(waybillNO);
								waybillTrackingsDto.setNextDeptCode(stockEntity.getOrgCode());
								waybillTrackingsDto.setInStockTime(stockEntity.getInOutStockTime());
								waybillList.add(waybillTrackingsDto);
								pushForWaybillToPTPService.pushWaybillToPTP(waybillList, stockEntity.getOperatorCode(), stockEntity.getOperatorName());
							} catch (Exception e) {
								LOGGER.info("营业部推送入库时间到PTP异常： "+waybillNO);
								e.printStackTrace();
							}
							list.add(waybillNO);
						}
					}
				}
			}
		}
		*/
		/**
		 * 营业部交接根据当前部门判断是否推送
		 * @param superOrgCode
		 * @param arriveDeptCode
		 * @author 332219-foss
		 * @return
		 */
		private boolean falgArriveDept(String superOrgCode){
			 //查询出发部门获取是否是营业部、一级网点、二级网点
			 SaleDepartmentEntity departDept = saleDepartmentService.querySaleDepartmentByCode(superOrgCode);
			 //营业部数据非空
			 if(departDept != null){
				//判断是否是营业部
				 String departModel = departDept.getIsLeagueSaleDept();
				 //一级网点或者二级网点
				 if("Y".equals(departModel)){
					 return true;
				 }
			 }else{
				 LOGGER.error("当前部门营业部数据为空！");
			 }
			return false;
		}
	    @Override
		@Transactional(propagation = Propagation.REQUIRES_NEW)
		public void outStockSaleStockInfoSale(List<InOutStockEntity> outStockList) {
	    	if(CollectionUtils.isEmpty(outStockList)){
	    		return;
	    	}
	    	String orgCode = outStockList.get(0).getOrgCode();
	    	if(StringUtils.isEmpty(orgCode)){
	    		return;
	    	}
	    	
			Map<String, List<InOutStockEntity>> map = new HashMap<String, List<InOutStockEntity>>();
			// 将List<InOutStockEntity>按运单号分组
			for (InOutStockEntity idx : outStockList) {
				String waybillNo = idx.getWaybillNO();
				if (map.containsKey(waybillNo)) {
					List<InOutStockEntity> list=map.get(waybillNo);
					list.add(idx);
				} else {
					List<InOutStockEntity> list=new ArrayList<InOutStockEntity>();
					list.add(idx);
					map.put(waybillNo, list);
				}
			}
			for (Map.Entry<String, List<InOutStockEntity>> me : map.entrySet()) {
				List<InOutStockEntity> value = me.getValue();
				int totalCnt = 0;
				for (InOutStockEntity idx : value) {
					int cnt = stockDao.outStockSaleBatchPC(idx);
					if (cnt > 0) {
						totalCnt = totalCnt + cnt;
					}						
				}
				String waybillNo = me.getKey();	
				// tfr.t_opt_waybill_stock_sale先查询运单在此部门的库存件数，记为ptpStockQty
				int ptpStockQty =stockDao.querySaleCount(waybillNo,orgCode);
				if (ptpStockQty > totalCnt) {
					// tfr.t_opt_waybill_stock_sale将库存减totalCnt  更新库存件数 减去对应的件数
					int m =ptpStockQty-totalCnt;
					stockDao.updateSaleCount(m,waybillNo,orgCode);
				} else {
					// tfr.t_opt_waybill_stock_sale删除运单在对应部门的记录
					stockDao.deleteSaleCount(waybillNo,orgCode);
				}
			}
			

		}
}			