package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleInfoDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverHandoverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillNewDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IVisibleOrderDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverHandoverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsDeliverService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverbillReturnService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IVisibleOrderService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VisibleHandBillReturnEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillOtherDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillReturnDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ParamsInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SmallzoneVehicleno;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.TogetherInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisibleAutoSortDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisibleAutoSortNodeInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisibleAutoSortNodeReturnInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisibleAutoSortReturnDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisiblePageMapAutoSortDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualAddressMarkDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.PropertyValues;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.VisualBillVo;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingZoneEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 *  可视化排单ServiceImpl
 * @author 239284
 *
 */
public class VisibleOrderService implements IVisibleOrderService{
	
	//日志属性
	private static final Logger LOGGER = LogManager.getLogger(VisibleOrderService.class);
	
	//零
	private static final int ZERO = 0;
	private static final int ONE = 1;
	
	// 部门查询起始. 
	private static final int BEGIN_NUM = 0;
	
	// 派送部查询页面大小. 
	private static final int PAGE_SIZE = 1;
	private static final int PAGE_NUM = 100;
	
	//添加派送单明细操作. 
	private static final int ADD_OPERATION = 1;
	// 移除派送单明细操作. 
	private static final int DELETE_OPERATION = 2;
	
	private static final double DOUBLE_ZERO = 0.00;
	private static final String PKP_GODOWNENTRY_NULLCAR = "PKP_GODOWNENTRY_NULLCAR";	//进仓货-空车出
	private static final String PKP_GODOWNENTRY_NOTNULLCAR = "PKP_GODOWNENTRY_NOTNULLCAR";	////进仓货-非空车出
	private static final String DELIVER_UP = "DELIVER_UP";	//送货方式为送货上楼
	private static final String AGING_PRIORITY="AGING_PRIORITY";//计算方式为时效排序
	private static final String ARTIFICIAL_PRIORITY = "ARTIFICIAL_PRIORITY";//人工排序
	//特殊地址类型为 开箱逐件验货
	private static final String PKP_DELIVERYADDRESS_UNPACKINGINSPECTION = "PKP_DELIVERYADDRESS_UNPACKINGINSPECTION";
	
	
	private static final String DELIVERBILL_CREATE_TYPE_NEW = "Y"; // 创建派送类型：Y新创建派送单
	private static final int QUERY_TOGETHER_LEVEL_PC = 2;  //聚合级别(省、市)
	private static final int QUERY_TOGETHER_LEVEL_PCD = 3; //聚合级别(省、市、区)
	
	//非(4)送货日期未到,  同时生成一条记录到派送管理中的“派送管理—处理异常“界面
	//字典表-bse.t_bas_data_dictionary_value -'PKP_VISIBLE_WAYBILL_RETURN', 
	private static final String RETURN_TYPE_DELAYTIME = "DELIVER_TIME_ADVANCE";  //取字典表中待排运单退回原因-送货日期未到
	
	//排单失败原因
	private static final String FAILED_REASON_GOODS_ARRANGED = "运单已排单";
	// 排单数量超过限制
	private static final String FAILED_REASON_NOT_ENOUGH_TO_ARRANGE = "排单数量超过限制";
	//排单运行时错误
	private static final String FAILED_REASON_RUNTIME_EXCEPTION = "排单运行时错误";
	
	/**判断货物类型*/
	private static final BigDecimal GD_WEIGHT_CONST = new BigDecimal("500"); //大票货-单票货物重量大于500kg
	private static final BigDecimal GD_VOLUMN_CONST = new BigDecimal("2.5"); //大票货-体积大于2.5F的货物;
	private static final BigDecimal GD_PAY_AMOUNT_CONST = new BigDecimal("10000"); //到付金额1万以上
	private static final String GD_LONGDELIVERY_CONST  = "Y";  //超远派送：开单为超远派送的货物
	private static final String GD_EXHIBITION_CONST = "Y";  //派送部通知客户时标记、调度人工处理
	private static final String GD_RECEIVEMETHOD_LARGE_DELIVER_UP = "LARGE_DELIVER_UP"; //大件上楼：提货方式为大件上楼的货物 (PICKUPGOODSHIGHWAYS)
	private static final String GD_RECEIVEMETHOD_DELIVER_INGA = "DELIVER_INGA"; //提货方式为“送货进仓”的运单 (PICKUPGOODSHIGHWAYS)
	
	//运单坐标点上的分隔符
	private static final String CONSTANT_SEPARATE_SHOW_WAYBILL = "/";
	
	//货物状态-库中中(JS页面中的货物状态)
	private static final String CONSTANT_GOODS_STATUS_KC = "KC";

	private static final int NUMBER_80 = 80;

	private static final int NUMBER_60 = 60;

	private static final int NUMBER_45 = 45; 
	
	//  派送单DAO接口
	private IDeliverbillDao deliverbillDao;
	//派送单明细DAO接口
	private IDeliverbillDetailDao deliverbillDetailDao;
	private IDeliverbillNewDetailDao deliverbillNewDetailDao;
	//业务互斥锁服务
	private IBusinessLockService businessLockService;
	// 派单服务
	private IDeliverbillService deliverbillService;
	// 业务监控服务
	private IBusinessMonitorService businessMonitorService;
	private IWaybillManagerService waybillManagerService;
	private IGpsDeliverService gpsDeliverService;
	// 外场相关共通接口
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	private IWaybillSignResultService waybillSignResultService;
	//到达联管理接口
	private IArriveSheetManngerService arriveSheetManngerService;
	// 部门 复杂查询 service
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	//更改单服务类
	private IWaybillRfcService waybillRfcService;
	//营业部 Service接口
	private ISaleDepartmentService saleDepartmentService;
	//定人定区service接口 
	private IRegionalVehicleService regionalVehicleService;
	//待排运单退回记录service
	private IHandoverbillReturnService handoverbillReturnService;
	// 处理异常 主数据Service层
	private IExceptionProcessService exceptionProcessService;
	//中转班次信息服务
	private ITruckSchedulingTaskService truckSchedulingTaskService;
	//派送排单Service接口
	private IDeliverHandoverbillService deliverHandoverbillService;
	//派送单状态记录service
	private IDeliverBillVaryStatusService deliverBillVaryStatusService;
	//中转区域service
	private IPickupAndDeliverySmallZoneDao pickupAndDeliverySmallZoneDao;
	//组织信息 dao 
	private IOrgAdministrativeInfoDao orgAdministrativeInfoDao;
	
	//集中接送货小区service接口 
	private IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService;
	
	//可视化dao
	private IVisibleOrderDao visibleOrderDao; 
	//运单状态数据持久层
	private IActualFreightDao actualFreightDao;
	//派送交单dao
	private IDeliverHandoverbillDao deliverHandoverbillDao;
	
	/**
	 * 配置
	 */
	private PropertyValues propertyValues;
	
	
	/**
	 * 获取运单聚合分组2GIS 
	 * @param wayBills
	 * @param leval 2(省，市); 3(省、市、区)
	 * @return
	 */
	public List<VisualBillDto> queryWaybillTogetherInfo(String[] wayBills, int leval) {
		return visibleOrderDao.queryWayBillTogether(wayBills, leval);
	} 
	
	/**
	 * 可视化排单-根据条件查询运单信息
	 */
	public  List<VisualBillDto> queryBillInfoByCondition(VisualBillConditionDto conditionDto) {
        
         if (CONSTANT_GOODS_STATUS_KC.equals(conditionDto.getGoodsStatus().trim())) {//库存中
        	 return visibleOrderDao.queryBillInfoInStockByCondition(conditionDto);
        } else { //已到达、预计到达
        	return visibleOrderDao.queryBillInfoByCondition(conditionDto); 
        }
        
	}
	
	
	/**
	 * 根据条件查询运单信息-作为参数返回给GIS
	 */
	@Override
	public String queryBillInfoToMap(VisualBillConditionDto conditionDto) {
		//根据页面查询条件查询运单信息
		List<VisualBillDto> listDto = queryBillInfoByCondition(conditionDto); 
		
		//返回的json对象
		JSONArray jsArray = null;
		
		//返回参数列表list
		List<ParamsInfo>  listParamsInfo = new ArrayList<ParamsInfo>();
		
		//获取查询到的运单号集合
		String[] waybillNos = null;
		if (null != listDto  && listDto.size() > 0) {
			//获取运单号集合
			waybillNos = new String[listDto.size()];
			int i = 0;
			for (VisualBillDto billDto : listDto) {
				waybillNos[i] = billDto.getWaybillNo();
				i++;
			}
			
			
			//根据运单号进行省、市汇总(浙江-杭州)
			List<VisualBillDto> pcList = queryWaybillTogetherInfo(waybillNos, QUERY_TOGETHER_LEVEL_PC);
			if (null != pcList && pcList.size() > 0) {
				for (VisualBillDto entity : pcList) {
					//返回参数信息
					ParamsInfo paramsInfo = new ParamsInfo();
					VisualBillConditionDto topCondtion = conditionDto;
					topCondtion.setProvCode(entity.getReceiveCustomerProvCode()); //把省作为查询条件
					topCondtion.setCityCode(entity.getReceiveCustomerCityCode()); //把市作为查询条件
					topCondtion.setDistCode(null);
					topCondtion.setWaybillNos(waybillNos);
					
					//浙江省-杭州市
					paramsInfo.setAdministrationCity(entity.getProvCityN().toString());
					paramsInfo.setTotalTicket(String.valueOf(handlerNull(entity.getTotalCount(), "Integer")));
					paramsInfo.setTotalWeight(String.valueOf(handlerNull(entity.getGoodsWeight(), "BigDecimal"))); 
					paramsInfo.setTotalVolum(String.valueOf(handlerNull(entity.getGoodsVolume(), "BigDecimal")));  
					
					
					//通过省、市及其包含运单号查询的运单信息
					List<VisualBillDto> secondListDto =queryBillInfoByCondition(topCondtion); 
					String[] topWaybillNos = null;
					if (null != secondListDto  && secondListDto.size() > 0) {
						topWaybillNos = new String[secondListDto.size()];
						int j = 0;
						for (VisualBillDto billDto : secondListDto) {
							topWaybillNos[j] = billDto.getWaybillNo();
							j++;
						}
						
						
						//根据运单号进行省、市、区汇总
						List<VisualBillDto> pcdList = queryWaybillTogetherInfo(topWaybillNos, QUERY_TOGETHER_LEVEL_PCD);
						if (null != pcdList && pcdList.size() > 0) {
							List<TogetherInfo> distTogetherInfoList = new ArrayList<TogetherInfo>(); //区级聚合
							for (VisualBillDto entitySon : pcdList) {
								TogetherInfo togetherInfo = new TogetherInfo();
								VisualBillConditionDto lastCondtion = topCondtion;
								lastCondtion.setProvCode(entitySon.getReceiveCustomerProvCode());
								lastCondtion.setCityCode(entitySon.getReceiveCustomerCityCode());
								lastCondtion.setDistCode(entitySon.getReceiveCustomerDistCode());
								lastCondtion.setWaybillNos(topWaybillNos);
								
								togetherInfo.setTogetherCount(String.valueOf(handlerNull(entitySon.getTotalCount(), "Integer")));
								togetherInfo.setTogetherProvCity(entitySon.getProvCityN().toString());
								
								//根据区域聚合小区，返回小区-车牌号信息
								List<VisualBillDto> smallVehicleNos = visibleOrderDao.querySmallVehicleNo(lastCondtion);
								//区下图层、车牌号集合
								List<SmallzoneVehicleno> smallVehicleNoList = new ArrayList<SmallzoneVehicleno>();
								//查询结果不为空
								if (null != smallVehicleNos && smallVehicleNos.size() > 0) {
									//遍历查询结果
									for (VisualBillDto vDto : smallVehicleNos) {
										if (null != vDto && StringUtil.isNotBlank(vDto.getActualSmallzoneCode())) {
											//根据小区code调中转接口查询小区对应的gsid
											SmallZoneEntity zoneEntity = pickupAndDeliverySmallZoneDao.querySmallZoneByCode(vDto.getActualSmallzoneCode());
											//查询结果不为空
											if (null != zoneEntity) {
												SmallzoneVehicleno smZoneVehicleNo = new SmallzoneVehicleno();
												smZoneVehicleNo.setSmallzoneId(zoneEntity.getGisid());
												smZoneVehicleNo.setVehicleNo(vDto.getActualVehicleNo());
												smallVehicleNoList.add(smZoneVehicleNo);
											}
										}
									}
								}
								togetherInfo.setSmallVehicleNoList(smallVehicleNoList);
								
								
								
								//查询聚合省、市、区下的运单明细结果
								List<VisualBillDto> lastListDto = queryBillInfoByCondition(lastCondtion); //通过省、市、区及其所含运单号查询运单信息
								//遍历以省、市、区及其包含运单号查询的运单信息
								if (null != lastListDto && lastListDto.size() > 0) {
									//返回参数省、市、区下的运单集合
									List<WayBillInfo> waybillInfoList = new ArrayList<WayBillInfo>();
									for (VisualBillDto billDto : lastListDto) {
										WayBillInfo billInfo = new WayBillInfo();
										billInfo.setWaybillNo(billDto.getWaybillNo()); //设置运单号
										billInfo.setPositionLng(String.valueOf(handlerNull(billDto.getLongitude(), "String"))); //经度
										billInfo.setPositionLat(String.valueOf(handlerNull( billDto.getLatitude(), "String"))); //纬度
										
										String address = getAddressInfo(billDto.getActualProvN(), billDto.getActualCityN(), billDto.getActualDistrictN(), billDto.getReceiveCustomerAddress(), billDto.getReceiveCustomerAddressNote());
										billInfo.setAddress("送货地址: " + address); //送货地址
										billInfo.setQtyWeightVolum( "可排件数/重量(KG)/体积(m³): " + handlerNull(billDto.getArrangeToQty(), "Integer") + CONSTANT_SEPARATE_SHOW_WAYBILL + handlerNull(billDto.getGoodsWeight(), "BigDecimal") + CONSTANT_SEPARATE_SHOW_WAYBILL + handlerNull(billDto.getGoodsVolume(), "BigDecimal"));
										String size = handlerNull(billDto.getGoodsSize(), "String").toString();
										//尺寸的长度大于100，则截断
										size = size.replaceAll("(\r\n|\r|\n|\n\r)", "").trim(); //去除换行符合
										if (size.length() > NUMBER_80) {
											size = size.substring(0, NUMBER_80).trim();
										}
										if (size.length() > NumberConstants.NUMBER_40) {
											size = size.substring(0, NumberConstants.NUMBER_40) + "<br/>" + size.substring(NumberConstants.NUMBER_40);
										}
										
										billInfo.setGoodsSize("尺寸:" + size);
										billInfo.setGoodPackage("包装: " + handlerNull(billDto.getGoodsPackage(), "String"));
										billInfo.setDeliveryTime("预计送货日期/时间: " + getDeliverTime(billDto.getPreDeliverDate(),  billDto.getDeliveryTimeStart(), billDto.getDeliveryTimeOver()));
										billInfo.setGoodStatusLate("货物状态/是否晚交单: " + handlerNull(billDto.getGoodStatus(), "String") + CONSTANT_SEPARATE_SHOW_WAYBILL + isOrLateNo(billDto.getLateNo())); //数据库转换
										billInfo.setSpecialBillType("特殊运单类型: " + getSpecialTypeName(billDto.getSpecialAddressType(), billDto));
										billInfo.setReturnReason("理货员退回原因: " + handlerNull(billDto.getTallymanReturnReason(), "String")); 
										
										//参数4-运单状态
										billInfo.setWayBillStatus(getWayBillStatus(billDto, billDto.getSpecialAddressType())); //特殊运单标识1， 其他标识0
										//重量-地图求和
										billInfo.setGoodsWeight(String.valueOf(handlerNull(billDto.getGoodsWeight(), "BigDecimal"))); 
										//体积-地图求和
										billInfo.setGoodsVolume(String.valueOf(handlerNull(billDto.getGoodsVolume(), "BigDecimal"))); 
										
										waybillInfoList.add(billInfo);
									}
									togetherInfo.setWaybillInfoList(waybillInfoList);
								}
								distTogetherInfoList.add(togetherInfo);
							}
							paramsInfo.setDistrictTogetherList(distTogetherInfoList);
						}
						listParamsInfo.add(paramsInfo);
					}
				}
			}
			
			jsArray = JSONArray.fromObject(listParamsInfo);
			LOGGER.info("可视化排单调用GIS接口展现地图参数：" + jsArray.toString());
			return jsArray.toString();
		} else {
			LOGGER.info("可视化排单调用GIS接口展现地图参数：null");
			return null;
		}
}
		

	
	
	
	/**
	 *add.1  添加运单至派送单. "->" 右移动
	 * @param DeliverbillEntity 派送单实体
	 * @param waybillToArrangeDtoList 添加的运单列表
	 * @param currentInfo the current info
	 * @return 添加失败的运单列表
	 * @return
	 */
	@Override
	public List<WaybillToArrangeDto> addWaybillToArrange(DeliverbillEntity deliverbill, List<WaybillToArrangeDto> waybillToArrangeDtoList, 	CurrentInfo currentInfo) {
	
		// 定义排单失败的运单列表，作为返回
		List<WaybillToArrangeDto> waybillListFailed = new ArrayList<WaybillToArrangeDto>();
		
		DeliverbillEntity deliverbillEntity = deliverbillDao.queryById(deliverbill.getId());	
		
		if(deliverbillEntity!= null && (DeliverbillConstants.STATUS_LOADED.equals(deliverbillEntity.getStatus())
			|| DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillEntity.getStatus()) 
			|| DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbillEntity.getStatus()) 
			|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbillEntity.getStatus()))) {
			throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
		}
		int i = 0;
		//循环运单列表
		for (WaybillToArrangeDto waybill : waybillToArrangeDtoList) {
			// 单票运单排单
			WaybillToArrangeDto waybillFailed = this.arrangeWaybill(deliverbill, waybill);
			//若单票运单排单不为空
			if (waybillFailed != null) {
				//添加到定义失败的运单列表
				waybillListFailed.add(waybillFailed);
			}
			i++;
		}
		Map<BusinessMonitorIndicator, Number> map = new HashMap<BusinessMonitorIndicator, Number>();
		map.put(BusinessMonitorIndicator.DELIVERY_SCHEDULE_COUNT, i);
		businessMonitorService.counter(map, currentInfo);
		return waybillListFailed;
	}

	/**
	 * add.2 运单排单 '->'
	 * @param deliverbill 派送单
	 * @param waybill 待排运单信息
	 * @return 排单成功 返回null, 否则返回排单信息
	 */
	@Transactional
	public WaybillToArrangeDto arrangeWaybill(DeliverbillEntity deliverbill, WaybillToArrangeDto waybill) {
		MutexElement mutexElement = new MutexElement(waybill.getWaybillNo(), "可视化排单-运单排单", MutexElementType.NEW_DELIVERBILLDETAIL_NO);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, ZERO);
		//如果没有上锁
		if(!isLocked){
			throw new DeliverbillException(DeliverbillException.WAYBILL_LOCKED);
		}
		
		//校验排单日期是否一致
		//已排单明细
		List<VisualBillArrageDto> listVisualBillArrageDtos = this.queryDeliverbillDetailAllList(deliverbill.getId());
		if(null != listVisualBillArrageDtos && listVisualBillArrageDtos.size() > 0)  {
			VisualBillArrageDto alreadyBill  = listVisualBillArrageDtos.get(0);
			Date deliverDate = alreadyBill.getDeliverDate() ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (!(sdf.format(waybill.getDeliverDate())).equals(sdf.format(deliverDate))) {
				waybill.setFailedToArrangeReason("所选的运单的送货日期必须与已排运单的送货日期一致!");
				return waybill;
			}
		}
		
		
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybill.getWaybillNo());
		//不是暂存已开单 或 不是PDA已补录
		if(!WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillEntity.getPendingType()) 
				&& !WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybillEntity.getPendingType())) 	{
			//解锁
			businessLockService.unlock(mutexElement);
			//抛出异常 参数错误
			throw new DeliverbillException(DeliverbillException.WAYBILLSTATE_CANT_OPERATE);
		}	
		
		/**
		 * BUG-53568 排单时 再校验一遍签收结果
		 */
		WaybillSignResultEntity entity = new WaybillSignResultEntity();
		entity.setWaybillNo(waybill.getWaybillNo());
		entity.setActive(FossConstants.ACTIVE); 
		WaybillSignResultEntity waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
		//全部签收
		if(waybillSignResultEntity != null  && SignConstants.SIGN_STATUS_ALL.equals(waybillSignResultEntity.getSignStatus())) {
			//解锁
			businessLockService.unlock(mutexElement);
			//抛出异常 参数错误
			throw new DeliverbillException(DeliverbillException.DELIVERBILL_CANT_OPERATE);
		}
		
		//实际货物
		ActualFreightEntity actualFreight = this.actualFreightDao.queryByWaybillNo(waybill.getWaybillNo());
		if(actualFreight!=null){
			//电话通知成功过
			if(actualFreight.getEverTelnoticeSuccess() != null && FossConstants.ACTIVE.equals(actualFreight.getEverTelnoticeSuccess()))  {
				actualFreight.setArriveGoodsQty(waybill.getWaybillGoodsQty());
			}
			//可排件数 = 交单件数 - 排单件数
			Integer arrangableGoodsQty = waybill.getBillQty() - actualFreight.getArrangeGoodsQty();
				
			if (arrangableGoodsQty >= waybill.getArrangeGoodsQty()) { 
				// 运单可排单
				waybill.settSrvDeliverbillId(deliverbill.getId());

				// 根据派送单编号和运单号查询派送单明细
				DeliverbillDetailEntity deliverbillDetail = this.deliverbillDetailDao.queryByCondition(waybill);
				//若派送单明细不为空
				if (deliverbillDetail != null) {
					// 运单已部分排单
					// 更新派送单的统计信息
					this.updateDeliverbillStat(deliverbill, deliverbillDetail, DELETE_OPERATION);

					// 更新派送单明细的排单数量
					deliverbillDetail.setPreArrangeGoodsQty(deliverbillDetail.getPreArrangeGoodsQty() + waybill.getPreArrangeGoodsQty());

					// 重置派送单明细的件数/体积/重量
					this.updateDeliverbillDetailStat(deliverbillDetail, deliverbillDetail.getPreArrangeGoodsQty(), waybill.getWaybillGoodsQty(), waybill.getWeight(), waybill.getGoodsVolumeTotal());
					//更新派送单明细
					this.deliverbillDetailDao.update(deliverbillDetail);
					
				} else { // 新建派送单明细
						deliverbillDetail = new DeliverbillDetailEntity();
	
						try 	{	
							BeanUtils.copyProperties(deliverbillDetail, waybill);
	
							// 更新排单重量/体积/序号
							deliverbillDetail.setWeight(BigDecimalOperationUtil.div(waybill.getWeight().multiply(new BigDecimal(waybill.getPreArrangeGoodsQty())),
									new BigDecimal(waybill.getWaybillGoodsQty()), DeliverbillConstants.WEIGHT_PRECISION));
							
							deliverbillDetail.setGoodsVolumeTotal(BigDecimalOperationUtil.div(waybill.getGoodsVolumeTotal().multiply(new BigDecimal(waybill.getPreArrangeGoodsQty())),
									new BigDecimal(waybill.getWaybillGoodsQty()), DeliverbillConstants.VOLUME_PRECISION));
	
							deliverbillDetail.setSerialNo(this.deliverbillDetailDao.queryMaxSerialNoByDeliverbillId(deliverbill.getId()) + 1);
							//规定兑现时间
							deliverbillDetail.setCashTime(waybill.getCashTime());
						} catch (IllegalAccessException e) {	
								//日志记录
								LOGGER.error(e.getMessage(), e);
								waybill.setFailedToArrangeReason(FAILED_REASON_RUNTIME_EXCEPTION);
								return waybill;
						} catch (InvocationTargetException e) {	
								//日志记录
								LOGGER.error(e.getMessage(), e);
								waybill.setFailedToArrangeReason(FAILED_REASON_RUNTIME_EXCEPTION);
								return waybill;
						}
						
						deliverbillDetail.setArrangeGoodsQty(deliverbillDetail.getPreArrangeGoodsQty());
						this.deliverbillDetailDao.add(deliverbillDetail);
	
					}
	
					// 更新派送单的统计信息
					this.updateDeliverbillStat(deliverbill, deliverbillDetail, ADD_OPERATION);
	
					this.deliverbillDao.update(deliverbill);
	
					// 更新运单排单件数
					String actualFreightId = actualFreight.getId();
					int newArrangeGoodsQty = actualFreight.getArrangeGoodsQty() + waybill.getArrangeGoodsQty();
	
					actualFreight = new ActualFreightEntity();
					actualFreight.setId(actualFreightId);
					actualFreight.setArrangeGoodsQty(newArrangeGoodsQty);
	
					this.actualFreightDao.updateByPrimaryKeySelective(actualFreight);
					//解锁
					businessLockService.unlock(mutexElement);
					return null;
				} else {
							// 运单不可排单
							if (arrangableGoodsQty == 0) {
								waybill.setFailedToArrangeReason(FAILED_REASON_GOODS_ARRANGED); // 运单已排单
							} else {	
								waybill.setFailedToArrangeReason(FAILED_REASON_NOT_ENOUGH_TO_ARRANGE); //排单数量超过限制
								waybill.setArrangableGoodsQty(arrangableGoodsQty);
							}
							//解锁
							businessLockService.unlock(mutexElement);
							return waybill;
				}
		}else{
			//解锁
			businessLockService.unlock(mutexElement);
			throw new DeliverbillException(DeliverbillException.ACTUALFREIGHT_NOT_FOUND_ERROR); 
		}
	}
	
	/**
	 * 重置派送单明细的件数/体积/重量.
	 * @param deliverbillDetail
	 * @param arrangeGoodsQty 更改后的排单件数
	 * @param waybillGoodsQty 运单件数
	 * @param waybillWeight 运单重量
	 * @param waybillVolume 运单体积
	 * @return 更新后的派送单明细信息
	 */
	private DeliverbillDetailEntity updateDeliverbillDetailStat(DeliverbillDetailEntity deliverbillDetail, int arrangeGoodsQty, int waybillGoodsQty, BigDecimal waybillWeight, BigDecimal waybillVolume) {
		deliverbillDetail.setArrangeGoodsQty(arrangeGoodsQty);
		// 更新排单重量/体积
		deliverbillDetail.setWeight(BigDecimalOperationUtil.div(waybillWeight.multiply(new BigDecimal(arrangeGoodsQty)), new BigDecimal(waybillGoodsQty), DeliverbillConstants.WEIGHT_PRECISION));
		deliverbillDetail.setGoodsVolumeTotal(BigDecimalOperationUtil.div(waybillVolume.multiply(new BigDecimal(arrangeGoodsQty)), new BigDecimal(waybillGoodsQty),
				DeliverbillConstants.VOLUME_PRECISION));
		return deliverbillDetail;
	}
	
	/**
	 * 更新派送单统计信息.
	 * @param deliverbill
	 * @param deliverbillDetail
	 * @param operation 操作类型(添加/移除)
	 * @return  更新后的派送单
	 */
	private DeliverbillEntity updateDeliverbillStat(DeliverbillEntity deliverbill, DeliverbillDetailEntity deliverbillDetail, int operation) {
		MutexElement mutexElement = new MutexElement(deliverbillDetail.getWaybillNo(), "可视化排单-运单排单", MutexElementType.NEW_DELIVERBILL_NO);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, ZERO);
		//如果没有上锁
		if(!isLocked){
			throw new DeliverbillException(DeliverbillException.WAYBILL_LOCKED);
		}
		//运单还未补录
		if(deliverbillDetail.getPayAmount() == null || deliverbillDetail.getWeight() == null){
			throw new DeliverbillException(DeliverbillException.WAYBILLNO_NOT_COMPLEMENT,new Object[]{deliverbillDetail.getWaybillNo()});
		}
		//若操作类型为添加派送单明细操作时
		if (operation == ADD_OPERATION) {
			// 添加派送单明细
			deliverbill.setWaybillQtyTotal(deliverbill.getWaybillQtyTotal() + 1);
			deliverbill.setGoodsQtyTotal(deliverbill.getGoodsQtyTotal() + deliverbillDetail.getArrangeGoodsQty());
			deliverbill.setPayAmountTotal(deliverbill.getPayAmountTotal().add(deliverbillDetail.getPayAmount()));
			deliverbill.setWeightTotal(deliverbill.getWeightTotal().add(deliverbillDetail.getWeight()));
			deliverbill.setVolumeTotal(deliverbill.getVolumeTotal().add(deliverbillDetail.getGoodsVolumeTotal()));
			deliverbill.setFastWaybillQty(deliverbill.getFastWaybillQty() + deliverbillDetail.getFastWaybillFlag());
		} else if (operation == DELETE_OPERATION) { //如若操作类型为移除派送单明细操作时
			deliverbill.setWaybillQtyTotal(deliverbill.getWaybillQtyTotal() - 1);
			deliverbill.setGoodsQtyTotal(deliverbill.getGoodsQtyTotal() - deliverbillDetail.getArrangeGoodsQty());
			deliverbill.setPayAmountTotal(deliverbill.getPayAmountTotal().subtract(deliverbillDetail.getPayAmount()));
			deliverbill.setWeightTotal(deliverbill.getWeightTotal().subtract(deliverbillDetail.getWeight()));
			deliverbill.setVolumeTotal(deliverbill.getVolumeTotal().subtract(deliverbillDetail.getGoodsVolumeTotal()));
			deliverbill.setFastWaybillQty(deliverbill.getFastWaybillQty() - deliverbillDetail.getFastWaybillFlag());
		}
		//解锁
		businessLockService.unlock(mutexElement);
		return deliverbill;
	}
	
	
	
	
	/**
	 * 根据运单号查询待排运单信息列表
	 */
	@Override
	public List<WaybillToArrangeDto> queryWaybillToArrangeByCondition(WaybillToArrangeDto waybillToArrangeDto) {
		
		//过滤重复的运单号
		if (StringUtil.isNotBlank(waybillToArrangeDto.getWaybillNo())) {
			String [] a = getDistinct(waybillToArrangeDto.getWaybillNo().split(","));
			waybillToArrangeDto.setArrayWaybillNos(a);
		}
		
		List<WaybillToArrangeDto> waybillToArrangeDtoList = new ArrayList<WaybillToArrangeDto>();
		
		waybillToArrangeDtoList = this.visibleOrderDao.queryWaybillToArrangeByCondition(waybillToArrangeDto);
		
		//返回结果不为空
		if (null != waybillToArrangeDtoList && waybillToArrangeDtoList.size() > 0) {
			
			//转换拼接省市区编码
			for (WaybillToArrangeDto waybillToArrangeDto2 : waybillToArrangeDtoList) {
				String custAddr = handleQueryOutfieldService.getCompleteAddress(waybillToArrangeDto2.getReceiveCustomerProvCode(), waybillToArrangeDto2.getReceiveCustomerCityCode(), waybillToArrangeDto2.getReceiveCustomerDistCode(), waybillToArrangeDto2.getConsigneeAddress());
				if (StringUtil.isNotBlank(waybillToArrangeDto2.getConsigneeAddressNote())) {
					custAddr = custAddr + NotifyCustomerConstants.SPLIT_CHAR_DASH + waybillToArrangeDto2.getConsigneeAddressNote();
				}
				waybillToArrangeDto2.setConsigneeAddress(custAddr);
			}
			
			//返回传入运单号的集合
			if (StringUtil.isNotBlank(waybillToArrangeDto.getWaybillNo()))  {
				HashMap<String , WaybillToArrangeDto> map = new HashMap<String, WaybillToArrangeDto>();
				for (int i = 0; i < waybillToArrangeDtoList.size(); i++) {
					map.put(waybillToArrangeDtoList.get(i).getWaybillNo(), waybillToArrangeDtoList.get(i));  // 运单号  -> 待排列表 
				}
				List<WaybillToArrangeDto> waybillToArrangeDtoList1 = new ArrayList<WaybillToArrangeDto>();
				for (String waybill : waybillToArrangeDto.getArrayWaybillNos()) {
					if (map.containsKey(waybill)) {
						waybillToArrangeDtoList1.add(map.get(waybill));
					}
				}
				return waybillToArrangeDtoList1;
			}
		}
		
		return waybillToArrangeDtoList;
		
	}
	
	/**
	 *  根据派送单ID查询已排单明细
	 */
	public List<VisualBillArrageDto> alreadyArrangeListDto(String deliverbill_id) {
		return null;
	}
	
	
	/**
	  * 校验是否存在未处理的更改单(或目的站发生变化)
	  * @param waybillNos
	 */
	@Override
	public DeliverbillVo checkWaybillNos(List<String> waybillNos) {
		DeliverbillVo vo = new DeliverbillVo();
		vo.setWaybillNos(waybillRfcService.isExsitsWayBillRfcs(waybillNos)); 
		vo.setNotWaybillrfcNos(waybillRfcService.queryUnActiveRfcWaybillNo(waybillNos));
		return vo;
	}
	
	/**
	 * 保存(新增/更新)派送单
	 * @param deliverbill
	 * @return
	 */
	public DeliverbillEntity saveDeliverbill(DeliverbillEntity deliverbill) {
		MutexElement mutexElement = new MutexElement(deliverbill.getDeliverbillNo(), "可视化排单-派送单编号", MutexElementType.NEW_DELIVERBILL_NO);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, ZERO);
		//若未上锁
		if(!isLocked){
			//抛出派送单异常
			throw new DeliverbillException(DeliverbillException.DELIVERBILL_LOCKED);
		}
		DeliverbillEntity deliverbillEntity = null;
		//特为修改确认中的派送单传递到Gps的变量
		DeliverbillEntity deliverbillForGps = null;
		//若派送单id号为空时
		if (deliverbill.getId() == null || "".equals(deliverbill.getId())) {	
			//更新派送单状态
			deliverbill.setStatus(DeliverbillConstants.STATUS_SAVED);
			this.updateDeliverbillCreateInfo(deliverbill);
			deliverbillEntity = this.deliverbillDao.add(deliverbill);
			//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
			if (StringUtils.isNotBlank(deliverbillEntity.getDeliverbillNo()) && StringUtils.isNotBlank(deliverbillEntity.getStatus()) ) {
				DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
				deliverBillVary.setDeliverBillNo(deliverbillEntity.getDeliverbillNo());//派送单号
				deliverBillVary.setDeliverBillStatus(deliverbillEntity.getStatus());//派送单状态
				deliverBillVary.setOperatorName(deliverbillEntity.getOperator());//操作人
				deliverBillVary.setOperatorCode(deliverbillEntity.getOperatorCode());//操作人编码
				deliverBillVary.setOperateOrgName(deliverbillEntity.getOperateOrgName());//操作部门
				deliverBillVary.setOperateOrgCode(deliverbillEntity.getOperateOrgCode());//操作部门编码
				//添加纪录
				deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
			}
			deliverbillForGps = deliverbill;
		} else {
			
			this.updateDeliverbillOperateInfo(deliverbill);
			deliverbillEntity = this.deliverbillDao.update(deliverbill);
		
			if (StringUtils.isNotBlank(deliverbill.getStatus())) {
					if (!DeliverbillConstants.STATUS_SAVED.equals(deliverbill.getStatus())) {
						//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
						if (StringUtils.isNotBlank(deliverbill.getDeliverbillNo()) && StringUtils.isNotBlank(deliverbill.getStatus()) ) {
								DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
								deliverBillVary.setDeliverBillNo(deliverbill.getDeliverbillNo());//派送单号
								deliverBillVary.setDeliverBillStatus(deliverbill.getStatus());//派送单状态
								deliverBillVary.setOperatorName(deliverbill.getOperator());//操作人
								deliverBillVary.setOperatorCode(deliverbill.getOperatorCode());//操作人编码
								deliverBillVary.setOperateOrgName(deliverbill.getOperateOrgName());//操作部门
								deliverBillVary.setOperateOrgCode(deliverbill.getOperateOrgCode());//操作部门编码
								//添加纪录
								deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
						}
					}
			}
			
			//发现上面的deliverbillEntity数据不存在，所以重新查询一次吧
			deliverbillForGps = deliverbillDao.queryById(deliverbillEntity.getId());
			
			// 获取当前部门
			String currOrgCode = FossUserContextHelper.getOrgCode();
			// 添加库存外场、库区默认查询条件
			List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currOrgCode);
			String areaCode = null;
			if (CollectionUtils.isNotEmpty(list)) {
				   //传入库区code
				   areaCode = list.get(1);
			}
			Map<Object,Object> map = new HashMap<Object,Object>();
			map.put("deliverbillId", deliverbill.getId());
			map.put("areaCode", areaCode);
			
			// 得到派送单明细
			List<DeliverbillDetailEntity> deliverbillDetailList = this.deliverbillDetailDao.queryByDeliverbillId(map);

			for (DeliverbillDetailEntity deliverbillDetail : deliverbillDetailList) {
				ArriveSheetEntity entity = new ArriveSheetEntity();
				entity.setWaybillNo(deliverbillDetail.getWaybillNo());
				// 生成到达联
				arriveSheetManngerService.checkGenerateArriveSheet(entity);
			}
			//只能是派送单确认之后才能修改派送单
			if(DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbill.getDelStatus())){
				LOGGER.info("可视化排单-Foss修改送货任务至Gps开始");
				//修改派送单
				com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto resultDto = 
						gpsDeliverService.syscModifyDeliverTaskToGps(deliverbillForGps, null);
				if(ResultDto.FAIL.equals(resultDto.getCode())){
					LOGGER.info("可视化排单-Foss修改送货任务至Gps失败，错误详情:"+resultDto.getMsg());
				}
				LOGGER.info("可视化排单-Foss修改送货任务至Gps结束");
			}
		}
		//解锁
		businessLockService.unlock(mutexElement);
		return deliverbillEntity;
	}
	
	/**
	 * 更新派送单创建信息
	 * @param deliverbill
	 * @return 带创建/ 操作时间/ 操作人/ 操作部门的派送单
	 */
	private DeliverbillEntity updateDeliverbillCreateInfo(DeliverbillEntity deliverbill) {
		UserEntity currentUser = FossUserContext.getCurrentUser();
		OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();

		if (currentUser != null) {
			EmployeeEntity employee = currentUser.getEmployee();
			//如果雇员不为空的话
			if (employee != null) {	
				
				deliverbill.setCreateUser(employee.getEmpCode());
				deliverbill.setCreateUserCode(employee.getEmpCode());
				deliverbill.setCreateUserName(employee.getEmpName());
				deliverbill.setModifyUser(employee.getEmpCode());
				deliverbill.setOperatorCode(employee.getEmpCode());
				deliverbill.setOperator(employee.getEmpName());
			}
		}

		if (currentOrg != null) {
			deliverbill.setCreateOrgCode(currentOrg.getCode());

			// 查询车队服务外场，设置派送单派送外场
			OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(currentOrg.getCode());

			if (transferCenter != null) {
				deliverbill.setTransferCenter(transferCenter.getCode());
			}

			deliverbill.setCreateOrgName(currentOrg.getName());
			deliverbill.setOperateOrgCode(currentOrg.getCode());
			deliverbill.setOperateOrgName(currentOrg.getName());
		}

		Date now = new Date();
		deliverbill.setCreateDate(now);
		deliverbill.setOperateTime(now);
		deliverbill.setModifyDate(now);
		deliverbill.setCreateType(DELIVERBILL_CREATE_TYPE_NEW);

		// submitTime为第一次保存的时间
		if (deliverbill.getSubmitTime() == null) 	{
			deliverbill.setSubmitTime(now);
		}

		return deliverbill;
	}
	
	/**
	 * 更新派送单修改操作信息
	 * @param deliverbill
	 * @return 	 带操作时间/  操作人/  操作部门的派送单
	 */
	private DeliverbillEntity updateDeliverbillOperateInfo(DeliverbillEntity deliverbill) {
		UserEntity currentUser = FossUserContext.getCurrentUser();
		OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();

		if (currentUser != null) {
			EmployeeEntity employee = currentUser.getEmployee();
			//如果雇员不为空时
			if (employee != null)	{	
				//更新操作人/操作人编码/修改人号的信息
				deliverbill.setModifyUser(employee.getEmpCode());
				deliverbill.setOperatorCode(employee.getEmpCode());
				deliverbill.setOperator(employee.getEmpName());
			}
		}
		
		// 仅当保存和提交环节计算派送外场
		if (currentOrg != null && (DeliverbillConstants.STATUS_SAVED.equals(deliverbill.getStatus()) || DeliverbillConstants.STATUS_SUBMITED.equals(deliverbill.getStatus()))) 	{
			deliverbill.setOperateOrgCode(currentOrg.getCode());
			deliverbill.setOperateOrgName(currentOrg.getName());

			// 查询车队服务外场，设置派送单派送外场
			OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(currentOrg.getCode());

			if (transferCenter != null) {
				deliverbill.setTransferCenter(transferCenter.getCode());
			}else{
				deliverbill.setTransferCenter(currentOrg.getCode());
			}
		}
		Date now = new Date();
		//更新操作/修改的时间日期
		deliverbill.setOperateTime(now);
		deliverbill.setModifyDate(now);

		//BUG-38291  当派送单为已下拉状态时  不能再修改状态
		DeliverbillEntity deliverbillEntity = deliverbillDao.queryById(deliverbill.getId());	
		
		/* (DeliverbillConstants.STATUS_LOADED.equals(deliverbillEntity.getStatus())
			|| DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillEntity.getStatus()) */
		if(deliverbillEntity!= null && DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbillEntity.getStatus()) 
			|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbillEntity.getStatus())) {
			throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
		}
		
		if(deliverbillEntity!= null && !DeliverbillConstants.STATUS_SAVED.equals(deliverbillEntity.getStatus())) {
			deliverbill.setStatus(null);
		}

		return deliverbill;
	}
	
	 /**
	  * 查询派送单已排单页面下的汇总条数
	  * @param deliverbillVo (派送单vo.车牌号、送货日期计算装载率)
	  * @return
	  */
	public VisualBillVo queryDeliverbillDetailCount(DeliverbillVo deliverbillVo, VisualBillVo vo) {
		
		VisualBillVo billVo = new VisualBillVo();
		
		Map<String, Object> map = this.visibleOrderDao.queryDeliverbillDetailCount(deliverbillVo.getDeliverbillDto().getId());
		if (null != map && map.size() > 0) {
			//如果查询的结果条数大于0则有记录
			BigDecimal totalticket = map.get("TOTALTICKET") == null ? BigDecimal.ZERO : new BigDecimal( map.get("TOTALTICKET") .toString());
			if ( totalticket.intValue() > 0 ) {
					BigDecimal totalcount = map.get("TOTALCOUNT") == null ? BigDecimal.ZERO : new BigDecimal( map.get("TOTALCOUNT") .toString());
					BigDecimal totalweight = map.get("TOTALWEIGHT") == null ? BigDecimal.ZERO : new BigDecimal( map.get("TOTALWEIGHT") .toString());
					BigDecimal totalvolumn = map.get("TOTALVOLUMN") == null ? BigDecimal.ZERO : new BigDecimal( map.get("TOTALVOLUMN") .toString());
					BigDecimal totalpayamount = map.get("TOTALPAYAMOUNT") == null ? BigDecimal.ZERO : new BigDecimal( map.get("TOTALPAYAMOUNT") .toString());
				
					billVo.setTotalTicket(String.valueOf(totalticket));
					billVo.setTotalCount(String.valueOf(totalcount));
					billVo.setTotalWeight(String.valueOf(totalweight));
					billVo.setTotalVolumn(String.valueOf(totalvolumn));
					billVo.setTotalPayAmount(String.valueOf(totalpayamount));
					
					// 测试数据
					/*SimpleTruckScheduleDto truckDto  = new SimpleTruckScheduleDto();
					truckDto.setSelfVolume(new BigDecimal(14));
					truckDto.setDeadLoad(new BigDecimal(90));
					truckDto.setIsBringExpress("Y");
					truckDto.setExpectedBringVolume(new BigDecimal(5));*/
				    SimpleTruckScheduleDto truckDto = truckSchedulingTaskService.queryTruckByVehicleAndDiliverGoodsTime(vo.getVehilceNo(), vo.getDeliverDate());
//					SimpleTruckScheduleDto truckDto = truckSchedulingTaskService.queryTruckByVehicle(vo.getVehilceNo(), vo.getDeliverDate());
					if (null != truckDto) {
							//净空
							BigDecimal  selfVolume = truckDto.getSelfVolume() == null ? BigDecimal.ZERO : truckDto.getSelfVolume();
							//载重(吨) -> 转千克
							BigDecimal deadLoad = truckDto.getDeadLoad() == null ? BigDecimal.ZERO : truckDto.getDeadLoad();
							
							
							DeliverbillEntity deliverEntity = this.deliverbillService.queryDeliverbill(deliverbillVo.getDeliverbillDto().getId());
							
							//带货(方)
							BigDecimal expectedBringVolume = BigDecimal.ZERO;
							//出车任务
							String carTaskInfo = "";
							if (null != deliverEntity) {
								expectedBringVolume = deliverEntity.getExpectedbringvolume();
								carTaskInfo = deliverEntity.getCarTaskinfo();
							} else {
								expectedBringVolume = truckDto.getExpectedBringVolume() == null ? BigDecimal.ZERO : truckDto.getExpectedBringVolume();
							}
							
						/*	//带货(方)
							BigDecimal expectedBringVolume = truckDto.getExpectedBringVolume() == null ? BigDecimal.ZERO : truckDto.getExpectedBringVolume();
							//是否带货
							String isBringExpress = truckDto.getIsBringExpress();*/
							
							
							//nominalRate  额定净空（方）/额定载重（吨） 下面 /是分隔符，不是值比
							billVo.setNominalRate(String.valueOf(selfVolume) + " / " + String.valueOf(deadLoad));
							
							//重量装载率 （排单重量/车辆载重(以千克计算)）%
							BigDecimal  weightRateNum = BigDecimalOperationUtil.div(totalweight, deadLoad.multiply(new BigDecimal(NumberConstants.NUMBER_1000)), 2);
							String weightRate = (BigDecimalOperationUtil.mul(weightRateNum, new BigDecimal(NumberConstants.NUMBER_100), 0)).toString() + "%";
							//体积装载率 分带货 or 不带货 
							String volumRate = "";
							
							//带货  装载率（体积）=（总体积+带快递货体积）/净空*100%;  通过出车任务类型来判断是否带货
							if (StringUtils.isNotBlank(carTaskInfo)) {
								if (DeliverbillConstants.CAR_TASK_TAKE_DELIVE_PICK.equals(carTaskInfo) || DeliverbillConstants.CAR_TASK_TAKE_DELIVE_TRAN.equals(carTaskInfo) ||
										DeliverbillConstants.CAR_TASK_SECOND_TAKE.equals(carTaskInfo)) {
									BigDecimal remainderVolumn = totalvolumn.add(expectedBringVolume);
									BigDecimal volumRateNum = BigDecimalOperationUtil.div(remainderVolumn, selfVolume, 2);
									volumRate = (BigDecimalOperationUtil.mul(volumRateNum, new BigDecimal(NumberConstants.NUMBER_100), 0)).toString() + "%";
								} else {
									 //非带货 （排单体积/车辆净空）%
									BigDecimal volumRateNum = BigDecimalOperationUtil.div(totalvolumn, selfVolume, 2);
									volumRate = (BigDecimalOperationUtil.mul(volumRateNum, new BigDecimal(NumberConstants.NUMBER_100), 0)).toString() + "%";
								}
							} else { //非带货 （排单体积/车辆净空）%
								BigDecimal volumRateNum = BigDecimalOperationUtil.div(totalvolumn, selfVolume, 2);
								volumRate = (BigDecimalOperationUtil.mul(volumRateNum, new BigDecimal(NumberConstants.NUMBER_100), 0)).toString() + "%";
							}
							
							//loadRate 装载率(容量和体积)  下面 /是分隔符，不是值比
							billVo.setLoadRate(weightRate + " / " + volumRate);
					} else {
						billVo.setNominalRate("0  /  0 ");
						billVo.setLoadRate("0% / 0%");
					}
					
			} else {
				billVo = null;
			}
		} else {
			billVo = null;
		}
		return billVo;
	}
	
	/**
	 * 查询派送单已排单下的明细信息,分页
	 */
	public List<VisualBillArrageDto>  queryDeliverbillDetailList(String deliverbillId, int start, int limit) {
		
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("deliverbillId", deliverbillId);
		
		//根据派送单ID查找派送单明细列表
		List<VisualBillArrageDto> entitys = this.visibleOrderDao.alreadyArrangeListDto(map, start, limit);
		
		return entitys;
	}
	
	 /**
	  * 查询派送单已排单下的明细信息 不分页
	  * @param deliverbillId
	  * @param start
	  * @param limit
	  * @return
	  */
	public List<VisualBillArrageDto> queryDeliverbillDetailAllList(String deliverbillId) {
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("deliverbillId", deliverbillId);
		
		//根据派送单ID查找派送单明细列表
		List<VisualBillArrageDto> entitys = this.visibleOrderDao.alreadyArrangeListAllDto(map);
		
		return entitys;
	}
	
	 /**
	  * 运单退回
	  * @param wayBillNos 待退回运单集合
	  * @param returnReason 退回原因
	  * @param returnReasonNotes 退回原因备注
	  * @return 退回失败的运单list
	  */
	@Transactional
	public List<VisibleHandBillReturnEntity> visibleBillReturn(String[] wayBillNos, String returnReason, String returnReasonNotes) {
		//过滤重复运单
		wayBillNos = getDistinct(wayBillNos);
		List<String> newWayBillNos = new ArrayList<String>();
		//把运单集合赋给交单查询条件
		Collections.addAll(newWayBillNos, wayBillNos);
		
		//退回失败的运单信息
		List<VisibleHandBillReturnEntity>  failedList = new ArrayList<VisibleHandBillReturnEntity>();
		
		//查询交单表的运单,过滤已经退回的运单
		DeliverHandoverbillOtherDto preDto = new DeliverHandoverbillOtherDto();
		preDto.setActive(FossConstants.ACTIVE);
		preDto.setWaybillNos(newWayBillNos);
		List<DeliverHandoverbillEntity> handOverEntity = deliverHandoverbillService.queryDeliverHandoverbillByWaybillNos(preDto);
		//如果存在，则可以进行运单退回，否则过滤其他不存在的运单(不进行运单退回)
		if (null != handOverEntity && handOverEntity.size() > 0) {
			for (DeliverHandoverbillEntity handoverBill : handOverEntity) {
				VisibleHandBillReturnEntity billReturn = new VisibleHandBillReturnEntity();
				billReturn.setWaybillNo(handoverBill.getWaybillNo());
				billReturn.setReturnReason(returnReason);
				billReturn.setReturnReasonNotes(returnReasonNotes);
				
				VisibleHandBillReturnEntity failedEntity  = returnOperate(billReturn);
				//返回退回失败的运单对象
				if (null != failedEntity) {
					failedList.add(failedEntity);
				} 
			}
		}
		
		
		return failedList;
	}
	
	/**
	 * 运单退回具体操作
	 * @param entity 待退回的运单相关对象
	 * @return 退回失败的运单相关对象
	 */
	private VisibleHandBillReturnEntity returnOperate(VisibleHandBillReturnEntity entity) {
		
		//派送排单Service接口，退回到待交单
		DeliverHandoverbillReturnDto dto = new DeliverHandoverbillReturnDto();
		dto.setWaybillNos(new String[]{entity.getWaybillNo()});
		List<String> failedWaybillNos = deliverHandoverbillService.returnPreDeliverbill(dto);
		
		//退回到待交单失败的运单
		if (null != failedWaybillNos && failedWaybillNos.size() > 0) {
			VisibleHandBillReturnEntity failedEntity = new VisibleHandBillReturnEntity();
			failedEntity.setWaybillNo(failedWaybillNos.get(0));
			return failedEntity;
		}  else { // 退回到待交单成功的运单
			
			List<String> existsWaybills = this.handoverbillReturnService.queryIsWaitWaybillReturn(entity.getWaybillNo());
			 //如果退回记录不为空，说明以前有退回记录，将其更新为无效，再插入一个本次新的有效数据
			if (existsWaybills != null && existsWaybills.size() > 0) {
				for (String id : existsWaybills) {
					VisibleHandBillReturnEntity newEntity = new VisibleHandBillReturnEntity();
					newEntity.setActive(FossConstants.INACTIVE);
					newEntity.setId(id);
					//更新为无效
					this.handoverbillReturnService.updateWaitWaybillReturn(newEntity);
				}
			}
			
			//初始化退回记录表信息
			entity.setId(UUIDUtils.getUUID());
			entity.setCreaterCode(FossUserContextHelper.getUserCode());
			entity.setCreaterName(FossUserContextHelper.getUserName());
			entity.setCreateOrgCode(FossUserContextHelper.getOrgCode());
			entity.setCreateOrgName(FossUserContextHelper.getOrgName());
			entity.setActive(FossConstants.ACTIVE);
			entity.setCreateDate(new Date());
			entity.setModifyDate(new Date());
			
			//新增一条运单退回记录(T_SRV_HANDOVERBILL_RETURN)
			this.handoverbillReturnService.addWaitWaybillReturn(entity);
			
			// 注意替换本类异常原因产量、非（4）送货日期未到,同时生成一条记录到派送管理中的“派送管理—处理异常“界面 
			if (StringUtil.isNotBlank(entity.getReturnReason()) && !RETURN_TYPE_DELAYTIME.equals(entity.getReturnReason())) {
				ExceptionEntity exceptionEntity = new ExceptionEntity();
				exceptionEntity.setWaybillNo(entity.getWaybillNo());
				exceptionEntity.setExceptionType(ExceptionProcessConstants.WAYBILL_EXCEPTION);
				
				exceptionEntity.setExceptionLink(ExceptionProcessConstants.SEND_ROW_OF_SINGLE);
				exceptionEntity.setExceptiOperate(ExceptionProcessConstants.PKP_EXCEPTION_DISPATCH_RETURN);
				String returnName= DictUtil.rendererSubmitToDisplay(entity.getReturnReason(), DictionaryConstants.PKP_VISIBLE_WAYBILL_RETURN);
				if (StringUtil.isNotBlank(entity.getReturnReasonNotes())) {
					returnName = returnName + "-" + entity.getReturnReasonNotes();
				}
				exceptionEntity.setExceptionReason("排单运单退回-" + returnName);
				exceptionEntity.setStatus(ExceptionProcessConstants.HANDLING);
				exceptionEntity.setExceptionTime(new Date());
				exceptionEntity.setCreateUserCode(FossUserContextHelper.getUserCode());
				exceptionEntity.setCreateUserName(FossUserContextHelper.getUserName());
				exceptionEntity.setCreateOrgCode(FossUserContextHelper.getOrgCode());
				exceptionEntity.setCreateOrgName(FossUserContextHelper.getOrgName());
				this.exceptionProcessService.addExceptionProcessInfo(exceptionEntity);
			}
			
			return null;
		}
		
	}
	
	 /**
	  * 自动排序
	  * @param listPoints 运单号，顺序号
	  * @param deliverbillId 派送单id
	  */
	public void autoSortWaybill(Map<String, String> sortMap, String deliverbillId) {
			if ((null != sortMap && sortMap.size() > 0) && StringUtil.isNotBlank(deliverbillId)) {
				for (Map.Entry<String, String>  entry : sortMap.entrySet()) {
					this.visibleOrderDao.sortWaybillByDeliverbillId(entry.getKey().toString(),  Integer.valueOf(entry.getValue()),  deliverbillId);
				}
			} else {
				throw new DeliverbillException("8809", "可视化自动排序异常：有参数为空");
			}
	}	
	
	 /**
	  * PAD退回接口(把已排单移动到未排单)
	  * @param waybillNo
	  * @param deliverNo
	  * @param returnReason
	  * @return
	  */
	public String deleteDeliverDetailForPDA(String waybillNo, String deliverNo,
			String returnReason) {
		try {
			if (StringUtil.isNotBlank(waybillNo) && StringUtil.isNotBlank(deliverNo)) {
				Map<String, String> map = this.visibleOrderDao.queryDeliverIdAndDeliverDetailId(waybillNo, deliverNo);
				if (map != null && map.size() > 0) {
					String deliverId = map.get("DELIVERID").toString();
					String deliverDetailId = map.get("DELIVERDEAILID").toString();
					deliverbillService.deleteDeliverbillDetails(deliverId, new String[]{deliverDetailId});

					//更新交单表退回原因 deliverHandoverbillDao  updateByWaybillNoSelective
					DeliverHandoverbillEntity entity = new DeliverHandoverbillEntity();
					entity.setWaybillNo(waybillNo);
					entity.setActive(FossConstants.ACTIVE);
					//根据运单号查询交单表实体
					DeliverHandoverbillEntity newEntity = deliverHandoverbillDao.queryByWaybillNo(entity);
					if (newEntity != null) {
						DeliverHandoverbillEntity newEntity1=new DeliverHandoverbillEntity();
						newEntity1.setWaybillNo(waybillNo);
						newEntity1.setTallymanReturnReason(returnReason);
						
						newEntity1.setModifyTime(new Date());
						newEntity1.setOldActive(FossConstants.YES);
						deliverHandoverbillDao.updateByWaybillNoSelective(newEntity1);
					}
				} else {
					throw new DeliverbillException("8809-T-2", "PDA运单退回异常：没有可退回的运单");
				}
			} else {
				throw new DeliverbillException("8809-T-1", "PDA运单退回异常：运单号或派送单号为空");
			}

		} catch (Exception e) {
			return "0";
		}
		return "1";
	}
	
	 /**
	  * 根据运单号查询
	  * 实际收货地址，包括对应的四级结构
	  * 经纬度，小区编码、小区名称，电话等
	  * @return
	  */
	public List<VisualAddressMarkDto> visibleQueryForCoordMark(String[] wayBillNos) {
		List<VisualAddressMarkDto>  listMarks =  this.visibleOrderDao.visibleQueryForCoordMark(wayBillNos);
		if (null != listMarks && listMarks.size() > 0) {
			for (VisualAddressMarkDto markdto : listMarks) {
				String address = handleQueryOutfieldService.getCompleteAddress(markdto.getReceiveCustomerProvCode(),  markdto.getReceiveCustomerCityCode(),
						markdto.getReceiveCustomerDistCode(),  markdto.getReceiveCustomerAddress()).replace(NotifyCustomerConstants.SPLIT_CHAR_DASH , "") + 
						(markdto.getReceiveCustomerAddressNote() == null ? "": markdto.getReceiveCustomerAddressNote());
				markdto.setActualAddress(address);
				
				String juheAdress = handleQueryOutfieldService.getCompleteAddress(markdto.getReceiveCustomerProvCode(),  markdto.getReceiveCustomerCityCode(),
						markdto.getReceiveCustomerDistCode(), markdto.getReceiveCustomerAddress()).replace(NotifyCustomerConstants.SPLIT_CHAR_DASH , "");
				markdto.setJuheAdress(juheAdress);
				
				markdto.setReceiveCustomerProvCode(handleQueryOutfieldService.getCompleteAddress(markdto.getReceiveCustomerProvCode(), null, null, null));
				markdto.setReceiveCustomerCityCode(handleQueryOutfieldService.getCompleteAddress(null, markdto.getReceiveCustomerCityCode(), null, null));
				markdto.setReceiveCustomerDistCode(handleQueryOutfieldService.getCompleteAddress(null, null, markdto.getReceiveCustomerDistCode(), null));
			}
		}
		return listMarks;
	}
	
	 /**
	  *  根据送货日期、车牌号、派送单id(忽略当前)查询该车辆是否已经存在预排状态(SVAED)
	  * @param deliverDate 送货日期
	  * @param vehilceNo  车牌号
	  * @param deliverId 派送单id
	  * @param deliverStatus 派送单状态
	  * @param carTaskType[] 出车任务类型
	  * @return 存在记录数
	  */
	public Long visibleVehilceNoExistDeliverEntity(String deliverDate, String vehilceNo, String deliverId, String deliverStatus, String[] carTaskType) {
		return this.visibleOrderDao.visibleVehilceNoExistDeliverEntity(deliverDate, vehilceNo, deliverId, deliverStatus, carTaskType);
	}
	
	 /**
	  * 根据送货日期、车牌号、派送单id(忽略当前)查询该车辆相关提示
	  * 提示内容：此车牌号XX，送货日期XX，有派送单X，为XX状态
	  * @param deliverDate 送货日期
	  * @param vehilceNo 车牌号
	  * @param deliverId 派送单id
	  * @return 派送单实体
	  */
	public DeliverbillEntity visibleFindVehilceNoTips(String deliverDate, String vehilceNo, String deliverId) {
		//派送单状态(已保存、已提交、已分配、装车中)
		String queryStatus = DeliverbillConstants.STATUS_SAVED + "," + DeliverbillConstants.STATUS_SUBMITED + "," +
											   DeliverbillConstants.STATUS_ASSIGNED + "," + DeliverbillConstants.STATUS_LOADING;
		String[] deliverStatus = queryStatus.split(",");
		
		//取最新的一条记录
		List<DeliverbillEntity> list = this.visibleOrderDao.visibleFindVehilceNoTips(deliverDate, vehilceNo, deliverId, deliverStatus);
		if (null != list && list.size() > 0) {
			DeliverbillEntity entity = list.get(0);
			return entity;
		}
		return  null;
	}

	
	
	
	
	
	/**
	 * 空值转换
	 * @param obj
	 * @param fieldType
	 * @return
	 */
	private Object handlerNull(Object obj, String fieldType) {
		if ("String".equals(fieldType)) {
			if (obj == null) {
				return "";
			} 
		}
		if ("Integer".equals(fieldType) || "BigDecimal".equals(fieldType)) {
			if (null == obj) {
				return  0;
			} 
		}
		return obj;
	}
	
	/**
	 * 获取实际收货地址完整信息
	 * @param provN
	 * @param cityN
	 * @param distN
	 * @param streetN
	 * @param addresDetail
	 * @return
	 */
	private String getAddressInfo(String provN, String cityN, String distN, String streetN, String addresDetail) {
		StringBuffer  strBuffer = new StringBuffer("");
		if (provN != null && provN.length() > 0) {
			strBuffer.append(provN);
		}
		if (cityN != null && cityN.length() > 0) {
			strBuffer.append(cityN);
		}
		if (distN != null && distN.length() > 0) {
			strBuffer.append(distN);
		}
		if (streetN != null && streetN.length() > 0) {
			strBuffer.append(streetN);
		}
		if (addresDetail != null && addresDetail.length() > 0) {
			strBuffer.append(addresDetail);
		}
		return strBuffer.toString();
	}
	
	/**
	 * 获取预计送货日期/时间
	 * @param deliverDate
	 * @param deliverTimeStart
	 * @param deliverTimeEnd
	 * @return
	 */
	private String getDeliverTime(String deliverDate, String deliverTimeStart, String deliverTimeEnd) {
		StringBuffer  strBuffer = new StringBuffer("");
		if (deliverDate != null && deliverDate.length() > 0) {
			strBuffer.append(deliverDate);
			strBuffer.append(CONSTANT_SEPARATE_SHOW_WAYBILL);
		}
		strBuffer.append("(");
		if (deliverTimeStart != null && deliverTimeStart.length() > 0) {
			strBuffer.append(deliverTimeStart);
			strBuffer.append("-");
		}
		if (deliverTimeEnd != null && deliverTimeEnd.length() > 0) {
			strBuffer.append(deliverTimeEnd);
		}
		strBuffer.append(")");
		return strBuffer.toString();
	}
	
	/**
	 * 特殊货物类型
	 * @param billDto
	 * @return
	 */
	public String getGoodStatus(VisualBillDto billDto) {
		StringBuffer strApp = new StringBuffer(" ");
		BigDecimal weight = (BigDecimal)handlerNull(billDto.getGoodsWeight(), "BigDecimal");
		BigDecimal volumn = (BigDecimal)handlerNull(billDto.getGoodsVolume(), "BigDecimal");
		BigDecimal payAmount = (BigDecimal)handlerNull(billDto.getToPayAmount(), "BigDecimal");
		if (weight.compareTo(GD_WEIGHT_CONST) > 0 || volumn.compareTo(GD_VOLUMN_CONST) > 0) {
			strApp.append("大票货");
			strApp.append(",");
		}
		if (payAmount.compareTo(GD_PAY_AMOUNT_CONST)  >= 0 )  {
			strApp.append("到付金额1万以上");
			strApp.append(",");
		}
		if (GD_RECEIVEMETHOD_LARGE_DELIVER_UP.equals(billDto.getReceiveMethod())) {
			strApp.append("大件上楼");
			strApp.append(",");
		}
		if (GD_LONGDELIVERY_CONST.equals(billDto.getUitraLongDelivery())) {
			strApp.append("超远派送");
			strApp.append(",");
		}
		
		if (GD_EXHIBITION_CONST.equals(billDto.getIsExhibition()) && GD_EXHIBITION_CONST.equals(billDto.getIsEmptyCar())) {
			strApp.append("会展货-空车出");
			strApp.append(",");
		}
		
		if (GD_EXHIBITION_CONST.equals(billDto.getIsExhibition()) && !GD_EXHIBITION_CONST.equals(billDto.getIsEmptyCar())) {
			strApp.append("会展货-非空车出");
			strApp.append(",");
		}
		
		if (GD_RECEIVEMETHOD_DELIVER_INGA.equals(billDto.getReceiveMethod())) {
			strApp.append("送货进仓");
			strApp.append(",");
		}
		return strApp.substring(0, strApp.length() - 1);
	}
	
	/**
	 * 获取特殊运单类型对应的名称(特殊地址；特殊货物)
	 * @param specialAddressType
	 * @param specialNoType
	 * @return
	 */
	private String getSpecialTypeName(String specialAddressType, VisualBillDto billDto) {
		//返回特殊地址类型； 特殊货物类型多个
		return DictUtil.rendererSubmitToDisplay(handlerNull(specialAddressType, "String").toString(), DictionaryConstants.PKP_SPECIAL_DELIVERYADDRESS_TYPE) + CONSTANT_SEPARATE_SHOW_WAYBILL + getGoodStatus(billDto);
	}
	
	/**
	 * 获取运单状态: 特殊运单标识1，正常运单标识0 (特殊货物类型, 特殊地址类型)
	 * @param specialNoType 特殊货物类型
	 * @param specialAddressType 特殊地址类型
	 * @return
	 */
	private String getWayBillStatus( VisualBillDto billDto, String specialAddressType) {
		if (StringUtil.isBlank(getGoodStatus(billDto)) && StringUtil.isBlank(specialAddressType))  {
			return "0";
		} else {
			return "1";
		}
	}
	
	/**
	 * 根据派送单id查询已排单运单详细信息
	 */
	@Override
	public List<VisibleAutoSortDto> queryVisiblebillInfoList(String id) {
		List<VisibleAutoSortDto> visibleAutoSortDtoList = visibleOrderDao.queryVisiblebillInfoList(id);
		if (visibleAutoSortDtoList.size()>0) {
			for (VisibleAutoSortDto visibleAutoSortDto : visibleAutoSortDtoList) {
				//特殊货物类型
				visibleAutoSortDto.setGoodStatus(getVisibleGoodStatus(visibleAutoSortDto));
			}
		}
		
		return visibleAutoSortDtoList;
	}
	
	/**
	 * 根据派送单id查询派送单信息
	 */
	@Override
	public DeliverbillNewDto visiblebillInfo(String id) {
		
		return visibleOrderDao.visiblebillInfo(id);
	}
	
	
	/**
	 * 更新派送明细建议送货时间
	 */
	@Override
	public void updateVisibleDeliverbill(List<VisibleAutoSortDto> visibleAutoSortDtoList,DeliverbillNewDto deliverbillNewDto){
		try {
			if (visibleAutoSortDtoList.size() > 0) {
				for (VisibleAutoSortDto visibleAutoSortDto : visibleAutoSortDtoList) {
					DeliverbillDetailEntity deliverbillDetail = new DeliverbillDetailEntity();
					deliverbillDetail.setId(visibleAutoSortDto.getId());
					deliverbillDetail.setRecommendedDeliveryTime(visibleAutoSortDto.getRecommendedDeliveryTime());
					deliverbillDetail.setSerialNo(visibleAutoSortDto.getSerialNo());
					deliverbillDetail.setModifyDate(new Date());
					deliverbillDetailDao.update(deliverbillDetail);
				}
			}
			if (null != deliverbillNewDto.getCalculateType()) {
				if (!ARTIFICIAL_PRIORITY.equals(deliverbillNewDto.getCalculateType())) {
					DeliverbillEntity deliverbill = new DeliverbillEntity();
					deliverbill.setId(deliverbillNewDto.getId());
					deliverbill.setTotalDistance(deliverbillNewDto.getTotalDistance());
					deliverbill.setAutoSortCalculateType(deliverbillNewDto.getCalculateType());
					deliverbillDao.update(deliverbill);
				}
			}
			
		} catch (Exception e) {
			throw new DeliverbillException("建议送货时间更新失败！");
		}
		
	}
	
	/**
	 * 自动排序计算
	 */
	@Override
	@Transactional
	public Map<String,Object> visibleAutoSortCalculate(DeliverbillNewDto deliverbillNewDto,String deliverbillId){
		//最终返回结果map
		Map<String,Object> map =new HashMap<String, Object>();
		//用来存放派送单查询结果
		List<VisibleAutoSortDto> visibleAutoSortDtoList = null;
		//用来存放接口所需json
		String visibleAutoSortNodeJSONObject = "";
		//用来存放地图所需json
		String visibleAutoSortMapJSONObject ="";
		//计算预计出车时间
		//虚拟预计出车时间为 出车时间+20+带货部门操作时间
		//如果为时效排序 
		if (deliverbillNewDto.getCalculateType().equals(AGING_PRIORITY)) {
			deliverbillNewDto.setVirtualPreCarTaskTime(deliverbillNewDto.getPreCarTaskTime());
			if (deliverbillNewDto.getCarTaskInfo().equals("3") || deliverbillNewDto.getCarTaskInfo().equals("4") 
					|| deliverbillNewDto.getCarTaskInfo().equals("6")) {
				deliverbillNewDto.setVirtualPreCarTaskTime(this.addTime(deliverbillNewDto.getPreCarTaskTime(),NumberConstants.NUMBER_20));
				if (deliverbillNewDto.getExpectedbringVolume().compareTo(GD_VOLUMN_CONST) >= 0) {
					deliverbillNewDto.setVirtualPreCarTaskTime(this.addTime(deliverbillNewDto.getPreCarTaskTime(),NumberConstants.NUMBER_40));
				}else{
					deliverbillNewDto.setVirtualPreCarTaskTime(this.addTime(deliverbillNewDto.getPreCarTaskTime(),NumberConstants.NUMBER_15));
				}
			}
		}
		
		
		try {
			//根据派送单id查询详情
			visibleAutoSortDtoList = visibleOrderDao.queryVisiblebillInfoList(deliverbillId);
			if (visibleAutoSortDtoList == null || visibleAutoSortDtoList.size() <= 0 ) {
				throw new DeliverbillException("NO_WAYBILLINFO","查不到对应运单信息！");
			}
			//将查询出的信息转换为接口所需json格式
			visibleAutoSortNodeJSONObject= this.getVisibleAutoSortNodeJSONObject(deliverbillNewDto, visibleAutoSortDtoList);
		} catch (Exception e) {
			throw new DeliverbillException(e.getMessage(),e);
		}
		
		PostMethod postMethod = null;
		try {
			 HttpClient httpclient = new HttpClient(); 
	         //设置编码格式
	         httpclient.getParams().setContentCharset("UTF-8");	
	        //访问地址
	         postMethod  = new PostMethod(propertyValues.getGisWaybillAutoSortUrl());
	         RequestEntity entity = new StringRequestEntity(visibleAutoSortNodeJSONObject, "application/json",  "UTF-8");
	         postMethod.setRequestEntity(entity);
	         postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
	         httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(20000); 
	         httpclient.getHttpConnectionManager().getParams().setSoTimeout(20000);
	         
			// 执行postMethod
			int statusCode = httpclient.executeMethod(postMethod);
			//访问成功200
			if (statusCode == HttpStatus.SC_OK) {
				//获取返回值
				String responseBody = postMethod.getResponseBodyAsString();
				//将返回值转换为对象
				ObjectMapper objMapper = new ObjectMapper(); 
				VisibleAutoSortReturnDto visibleAutoSortReturn = (VisibleAutoSortReturnDto)objMapper.readValue(responseBody,VisibleAutoSortReturnDto.class);
				//gis返回成功表示数据获取正常
				if (visibleAutoSortReturn.getResponseCode().equals("gisSuccess")) {
					if (visibleAutoSortReturn.getWaybillList() != null && visibleAutoSortReturn.getWaybillList().size() > 0) {
						//将返回的运单List进行排序
						//List<VisibleAutoSortNodeReturnInfoDto> visibleAutoSortNodeInfoList = this.sortWaybillList(visibleAutoSortReturn.getWaybillList(),deliverbillNewDto.getCarTaskInfo());
						visibleAutoSortReturn.setWaybillList(this.sortWaybillList(visibleAutoSortReturn.getWaybillList(),deliverbillNewDto.getCarTaskInfo()));
						//跟新派送单排序总距离和排序方式(人工排序不做保存)
						if (!deliverbillNewDto.getCalculateType().equals(ARTIFICIAL_PRIORITY)) {
							updateSeliverbill(deliverbillId,deliverbillNewDto,visibleAutoSortReturn);
						}
						//计算建议送货时间并更新
						updateDeliverbillDetail(deliverbillNewDto,visibleAutoSortReturn,visibleAutoSortDtoList);
						//转换成前台Map所需json
						visibleAutoSortMapJSONObject = this.getVisibleAutoSortMapJSONObject(deliverbillNewDto,visibleAutoSortReturn,visibleAutoSortDtoList);
						map.put("visibleAutoSortMapJSONObject", visibleAutoSortMapJSONObject);
						double result = new BigDecimal(visibleAutoSortReturn.getTotalDistance()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						map.put("totalDistance",result);
					}
				}else if(visibleAutoSortReturn.getResponseCode().equals("gis000")){
					throw new DeliverbillException("gis000","请求GIS数据异常！");
				}else if(visibleAutoSortReturn.getResponseCode().equals("gis001")){
					throw new DeliverbillException("gis001","无法找到地址对应百度坐标！");
				}else if(visibleAutoSortReturn.getResponseCode().equals("gis002")){
					throw new DeliverbillException("gis002","运单中经纬度为空！");
				}else if(visibleAutoSortReturn.getResponseCode().equals("gis003")){
					throw new DeliverbillException("gis003","导航距离计算失败！");
				}else if(visibleAutoSortReturn.getResponseCode().equals("gis004")){
					throw new DeliverbillException("gis004","GIS解析数据异常！");
				}else if(visibleAutoSortReturn.getResponseCode().equals("gis005")){
					throw new DeliverbillException("gis005","请求数据为空！");
				}else if(visibleAutoSortReturn.getResponseCode().equals("gis006")){
					throw new DeliverbillException("gis0006","GIS百度转图吧时失败！");
				}
			} else {
				throw new DeliverbillException("GisFail","调用GIS接口请求异常！");
			}
		} catch (Exception e) {
			if (postMethod!=null) {
				postMethod.releaseConnection();
			}
			if ("READ TIMED OUT".equals(e.getMessage().trim().toUpperCase())) {
				throw new DeliverbillException("请求排序计算超时(20S),请重试！");
			}
			throw new DeliverbillException(e.getMessage(),e);
		}finally {
			if (postMethod!=null) {
				postMethod.releaseConnection();
			}
		}
		
		return map;
		
	
	}
	
	/**
	 * 将返回的运单集合按照序号排序
	 * @param visibleAutoSortNodeInfoDtoList
	 * @param carTaskInfo
	 * @return
	 */
	private List<VisibleAutoSortNodeReturnInfoDto> sortWaybillList(List<VisibleAutoSortNodeReturnInfoDto> visibleAutoSortNodeInfoDtoList,String carTaskInfo){
/*		//默认第一票运单序号为2
		int it = 2;
		//返回结果List
		List<VisibleAutoSortNodeReturnInfoDto> visibleAutoSortNodeInfoList =new ArrayList<VisibleAutoSortNodeReturnInfoDto>();
		while(it<visibleAutoSortNodeInfoDtoList.size()+2){
			int num =it;
			//如果有带货
			if (carTaskInfo.equals("3") || carTaskInfo.equals("4") || carTaskInfo.equals("6")) {
				num +=1;
			}
			for(int i=0;i<visibleAutoSortNodeInfoDtoList.size();i++){
				if(visibleAutoSortNodeInfoDtoList.get(i).getSerialNo()==num){
					visibleAutoSortNodeInfoList.add(visibleAutoSortNodeInfoDtoList.get(i));
				}
			}
			it++;
		}*/
//		return visibleAutoSortNodeInfoList;
		
		Collections.sort(visibleAutoSortNodeInfoDtoList,new Comparator<VisibleAutoSortNodeReturnInfoDto>(){
            public int compare(VisibleAutoSortNodeReturnInfoDto arg0, VisibleAutoSortNodeReturnInfoDto arg1) {
                return arg0.getSerialNo().compareTo(arg1.getSerialNo());
            }
        });
		
		return visibleAutoSortNodeInfoDtoList;
		
	}
	
	/**
	 * 将信息转换为页面Map所需json
	 * @param deliverbillNewDto	
	 * @param visibleAutoSortReturn 需要此返回对象的部门经纬度
	 * @param visibleAutoSortDtoList 需要此对象的运单详细信息
	 * @return
	 */
	private String getVisibleAutoSortMapJSONObject(DeliverbillNewDto deliverbillNewDto,VisibleAutoSortReturnDto visibleAutoSortReturn,List<VisibleAutoSortDto> visibleAutoSortDtoList){
		JSONObject json =new JSONObject();
		//计算方式
		json.put("calculateType", deliverbillNewDto.getCalculateType());
		//出车任务
		json.put("carTaskInfo", deliverbillNewDto.getCarTaskInfo());
		//运单
		List<VisiblePageMapAutoSortDto> visiblePageMapList = new ArrayList<VisiblePageMapAutoSortDto>();
		VisiblePageMapAutoSortDto visiblePageMap =null;
		
		//起点
		visiblePageMap = new VisiblePageMapAutoSortDto();
		visiblePageMap.setDeptCode(visibleAutoSortReturn.getStartDeptEntity().getId());
		OrgAdministrativeInfoEntity startDeptEntity = orgAdministrativeInfoDao.queryLastestOrgAdministrativeInfoByCode(visibleAutoSortReturn.getStartDeptEntity().getId());
		if (startDeptEntity!=null) {
			visiblePageMap.setDeptName(startDeptEntity.getName());
		}
		visiblePageMap.setDeptLat(visibleAutoSortReturn.getStartDeptEntity().getBaiduLat());
		visiblePageMap.setDeptLng(visibleAutoSortReturn.getStartDeptEntity().getBaiduLng());
		visiblePageMap.setSerialNo(visibleAutoSortReturn.getStartDeptEntity().getSerialNo());
		json.put("startDeptEntity", visiblePageMap);
		
		//终点
		visiblePageMap = new VisiblePageMapAutoSortDto();
		//当最后一票运单为空时 终点为空
		if (visibleAutoSortReturn.getEndDeptEntity() != null) {
			if (deliverbillNewDto.getCarTaskInfo().equals("1") || deliverbillNewDto.getCarTaskInfo().equals("3")){
				SmallZoneEntity smallZoneEntity=pickupAndDeliverySmallZoneService.querySmallZoneByCode(visibleAutoSortReturn.getEndDeptEntity().getId());
				visiblePageMap.setDeptCode(smallZoneEntity.getRegionCode());
				visiblePageMap.setDeptName(smallZoneEntity.getRegionName());
			}else{
				visiblePageMap.setDeptCode(visibleAutoSortReturn.getEndDeptEntity().getId());
				OrgAdministrativeInfoEntity endDeptEntity = orgAdministrativeInfoDao.queryLastestOrgAdministrativeInfoByCode(visibleAutoSortReturn.getEndDeptEntity().getId());
				if (endDeptEntity!=null) {
					visiblePageMap.setDeptName(endDeptEntity.getName());
				}
			}
			visiblePageMap.setDeptLat(visibleAutoSortReturn.getEndDeptEntity().getBaiduLat());
			visiblePageMap.setDeptLng(visibleAutoSortReturn.getEndDeptEntity().getBaiduLng());
			visiblePageMap.setSerialNo(visibleAutoSortReturn.getEndDeptEntity().getSerialNo());
		}
		json.put("endDeptEntity", visiblePageMap);
		
		int num = visibleAutoSortReturn.getWaybillList().get(0).getSerialNo() - 1;
		//运单
		if (visibleAutoSortReturn.getWaybillList().size() > 0) {
			for (int i = 0; i < visibleAutoSortReturn.getWaybillList().size(); i++) {
				
				visiblePageMap = new VisiblePageMapAutoSortDto();
				
				//用来存放运单详细信息
				VisibleAutoSortDto transitDto = null;
				for (int j = 0; j < visibleAutoSortDtoList.size(); j++) {
					//如果当前运单等于运单集合
					if (visibleAutoSortReturn.getWaybillList().get(i).getId().equals(visibleAutoSortDtoList.get(j).getId())) {
						transitDto = visibleAutoSortDtoList.get(j);
						break;
					}
				}
				
				//运单
				visiblePageMap.setWaybillNo(transitDto.getWaybillNo());
				//地址
				visiblePageMap.setDeliveryAddress(transitDto.getReceiveCustomerAddress());
				//拼装排单信息（件数/重量/体积）
				visiblePageMap.setCanData((transitDto.getGoodsQtyTotal() != null ? transitDto.getGoodsQtyTotal().toString() : "0") 
						+"/"+ (transitDto.getGoodsWeight() != null ? transitDto.getGoodsWeight().toString() : "0") 
						+"/"+(transitDto.getGoodsVolume()!=null ? transitDto.getGoodsVolume().toString() : "0"));
				//包装
				visiblePageMap.setWaybillPacking(transitDto.getGoodsPackage() != null ? transitDto.getGoodsPackage() : "");
				//拼装送货时间（送货时间段/开始送货时间-结束送货时间）
				visiblePageMap.setDeliveryData((transitDto.getDeliveryTimeInterval() !=null ? transitDto.getDeliveryTimeInterval() : "无") 
						+"/"+(transitDto.getDeliveryTimeStart() !=null ? transitDto.getDeliveryTimeStart() : "0:00") 
								+"-"+(transitDto.getDeliveryTimeOver() !=null ?transitDto.getDeliveryTimeOver() : "0:00"));
				visiblePageMap.setWaybillTagType(this.getVisibleStatus(transitDto, transitDto.getSpecialAddressType()));
				visiblePageMap.setGoodStatus(this.getVisibleGoodStatus(transitDto));
				
				visiblePageMap.setSerialNo(visibleAutoSortReturn.getWaybillList().get(i).getSerialNo()-num);
				visiblePageMap.setWaybillLat(visibleAutoSortReturn.getWaybillList().get(i).getBaiduLat());
				visiblePageMap.setWaybillLng(visibleAutoSortReturn.getWaybillList().get(i).getBaiduLng());
				visiblePageMapList.add(visiblePageMap);
			
			}
			json.put("waybillList", visiblePageMapList);
		}
		
		return "["+json.toJSONString()+"]";
	}
	
	
	/**
	 * 转接口所需json
	 * @param deliverbillNewDto
	 * @param visibleAutoSortDtoList
	 * @return
	 */
	public String getVisibleAutoSortNodeJSONObject(DeliverbillNewDto deliverbillNewDto,List<VisibleAutoSortDto> visibleAutoSortDtoList){
		JSONObject json = new JSONObject();
		String gisId = "";
		try {
			json.put("calculateType", deliverbillNewDto.getCalculateType());
			//根据运单号获取最终配载部门
			WaybillEntity waybillEntity=waybillManagerService.queryWaybillBasicByNo(visibleAutoSortDtoList.get(0).getWaybillNo());
			if (waybillEntity == null) {
				throw new DeliverbillException("NOT_WAYBILL","没有找到运单信息！");
			}
			//派送部(外场)
			VisibleAutoSortNodeInfoDto nodeInfoWC = new VisibleAutoSortNodeInfoDto();
			try {
				if (StringUtils.isNotBlank(waybillEntity.getLastLoadOrgCode())) {
					nodeInfoWC.setId(waybillEntity.getLastLoadOrgCode());//节点id 部门取部门code
					if (StringUtils.isNotBlank(waybillEntity.getLastLoadOrgCode())) {
						gisId = this.getGisId(waybillEntity.getLastLoadOrgCode());
						if (StringUtils.isNotBlank(gisId)) {
							nodeInfoWC.setGisID(gisId);
						}else{
							throw new DeliverbillException("NOT_WAYBILL_LASTLOADORGCODE","没有找到外场对应派送部GisId信息！");
						}
					}else{
						throw new DeliverbillException("NOT_WAYBILL_LASTLOADORGCODE","没有找到外场对应派送部GisId信息！");
					}
					nodeInfoWC.setDeliveryTimeStart(DOUBLE_ZERO);//送货开始时间
					nodeInfoWC.setDeliveryTimeOver(DOUBLE_ZERO);//送货结束时间
					nodeInfoWC.setFlag(false);//是否小区
					nodeInfoWC.setSerialNo(0);//序号
				}else{
					throw new DeliverbillException("NOT_WAYBILL_LASTLOADORGCODE","运单没有最终配载部门！");
				}
			} catch (Exception e) {
				throw new DeliverbillException(e.getMessage(),e);
			}
			
			//带货部门(营业部)
			VisibleAutoSortNodeInfoDto nodeInfoYYB = null;
			if (deliverbillNewDto.getCarTaskInfo().equals("3") || 
					deliverbillNewDto.getCarTaskInfo().equals("4") || deliverbillNewDto.getCarTaskInfo().equals("6")) {
				try {
					if (StringUtils.isNotBlank(deliverbillNewDto.getTakeGoodsDeptCode())) {
						nodeInfoYYB = new VisibleAutoSortNodeInfoDto();
						nodeInfoYYB.setId(deliverbillNewDto.getTakeGoodsDeptCode());
						if (StringUtils.isNotBlank(deliverbillNewDto.getTakeGoodsDeptCode())) {
							gisId = this.getGisId(deliverbillNewDto.getTakeGoodsDeptCode());
							if (StringUtils.isNotBlank(gisId)) {
								nodeInfoYYB.setGisID(gisId);
							}else{
								throw new DeliverbillException("YYB_FAIL","获取带货营业部GisId信息失败！");
							}
						}else{
							throw new DeliverbillException("YYB_FAIL","获取带货营业部GisId信息失败！");
						}
						nodeInfoYYB.setDeliveryTimeStart(DOUBLE_ZERO);
						nodeInfoYYB.setDeliveryTimeOver(DOUBLE_ZERO);
						nodeInfoYYB.setFlag(false);
						nodeInfoYYB.setSerialNo(0);
					}
				} catch (Exception e) {
					throw new DeliverbillException(e.getMessage(),e);
				}
			}
			
			//转货部门
			VisibleAutoSortNodeInfoDto nodeInfoZH = null;
			if (deliverbillNewDto.getCarTaskInfo().equals("2") || deliverbillNewDto.getCarTaskInfo().equals("4")){
				try {
					if (StringUtils.isNotBlank(deliverbillNewDto.getTransferDeptCode())) {
						nodeInfoZH =  new VisibleAutoSortNodeInfoDto();
						nodeInfoZH.setId(deliverbillNewDto.getTransferDeptCode());
						if (deliverbillNewDto.getTransferDeptCode() != null && StringUtils.isNotBlank(deliverbillNewDto.getTransferDeptCode())) {
							gisId = this.getGisId(deliverbillNewDto.getTransferDeptCode());
							if (StringUtils.isNotBlank(gisId)) {
								nodeInfoZH.setGisID(gisId);
							}else{
								throw new DeliverbillException("ZH_FAIL","获取转货部门GisId信息失败！");
							}
						}else{
							throw new DeliverbillException("ZH_FAIL","获取转货部门GisId信息失败！");
						}
						nodeInfoZH.setDeliveryTimeStart(DOUBLE_ZERO);
						nodeInfoZH.setDeliveryTimeOver(DOUBLE_ZERO);
						nodeInfoZH.setFlag(false);
						nodeInfoZH.setSerialNo(0);
					}
				} catch (Exception e) {
					throw new DeliverbillException(e.getMessage(),e);
				}	
			}
				
			//定车定区信息封装类 
			//根据车牌号查询定人定区信息
			RegionVehicleInfoDto regionVehicle = new RegionVehicleInfoDto();
			List<RegionVehicleInfoDto> regionVehicleInfo = null;
			SmallZoneEntity smallZoneEntity = null;
			//查看是否有节假日排班
			List<TruckSchedulingZoneEntity> truckSchedulingZoneEntityList = new ArrayList<TruckSchedulingZoneEntity>();
			//节假日绑定小区
			VisibleAutoSortNodeInfoDto nodeInfoJJR = null;
			//定人定区绑定小区
			VisibleAutoSortNodeInfoDto nodeInfoDRDQ = null;
			if (deliverbillNewDto.getCarTaskInfo().equals("1") || deliverbillNewDto.getCarTaskInfo().equals("3")){
				try {
					regionVehicle.setVehicleNo(deliverbillNewDto.getVehicleNo());
					regionVehicleInfo= regionalVehicleService.queryRegionalVehicles(regionVehicle,NumberConstants.NUMBER_10,0);
					if (regionVehicleInfo != null && regionVehicleInfo.size() > 0 ) {
						//用来判断是否有接货小区
						boolean bool = false;
						for (int i = 0; i < regionVehicleInfo.size(); i++) {
							if (StringUtils.isNotBlank(regionVehicleInfo.get(i).getRegionType()) && regionVehicleInfo.get(i).getRegionType().equals("PK") 
									&& StringUtils.isNotBlank(regionVehicleInfo.get(i).getRegionNature()) && regionVehicleInfo.get(i).getRegionNature().equals("SQ")) {
								bool = true;
								if (regionVehicleInfo.get(i).getCode() != null){
									smallZoneEntity=pickupAndDeliverySmallZoneService.querySmallZoneByCode(regionVehicleInfo.get(i).getCode());
									nodeInfoDRDQ = new VisibleAutoSortNodeInfoDto();
									nodeInfoDRDQ.setId(regionVehicleInfo.get(i).getCode());
									if (smallZoneEntity != null && StringUtils.isNotBlank(smallZoneEntity.getGisid())) {
										nodeInfoDRDQ.setGisID(smallZoneEntity.getGisid());
									}else{
										throw new DeliverbillException("DRDQ_FAIL","获取定人定区GisId信息失败！");
									}
									nodeInfoDRDQ.setDeliveryTimeStart(DOUBLE_ZERO);
									nodeInfoDRDQ.setDeliveryTimeOver(DOUBLE_ZERO);
									nodeInfoDRDQ.setFlag(true);
									nodeInfoDRDQ.setSerialNo(0);
									break;
								}
							}
						}
						//无接货小区 将对象赋空
						if (!bool) {
							regionVehicleInfo = null;
						}
						/*if (regionVehicleInfo.get(0).getCode() != null && regionVehicleInfo.get(0).getRegionType() != null 
								&&regionVehicleInfo.get(0).getRegionType().equals("PK")) {
								smallZoneEntity=pickupAndDeliverySmallZoneService.querySmallZoneByCode(regionVehicleInfo.get(0).getCode());
								nodeInfoDRDQ = new VisibleAutoSortNodeInfoDto();
								nodeInfoDRDQ.setId(regionVehicleInfo.get(0).getCode());
								if (smallZoneEntity != null && StringUtils.isNotBlank(smallZoneEntity.getGisid())) {
									nodeInfoDRDQ.setGisID(smallZoneEntity.getGisid());
								}else{
									throw new DeliverbillException("DRDQ_FAIL","获取定人定区GisId信息失败！");
								}
								nodeInfoDRDQ.setDeliveryTimeStart(DOUBLE_ZERO);
								nodeInfoDRDQ.setDeliveryTimeOver(DOUBLE_ZERO);
								nodeInfoDRDQ.setFlag(true);
								nodeInfoDRDQ.setSerialNo(0);
					}*/
					}
				} catch (Exception e) {
					throw new DeliverbillException(e.getMessage(),e);
				}
				
			
				try {
					//查看是否有节假日排班
					truckSchedulingZoneEntityList = new ArrayList<TruckSchedulingZoneEntity>();
					if (visibleAutoSortDtoList.get(0).getDeliverDate() != null) {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							String dateStr = format.format(visibleAutoSortDtoList.get(0).getDeliverDate());
							//查询节假日车辆绑定信息
							truckSchedulingZoneEntityList =truckSchedulingTaskService.queryReceivingAreaByVehicleAndDate(deliverbillNewDto.getVehicleNo(),dateStr);
							if (truckSchedulingZoneEntityList !=null && truckSchedulingZoneEntityList.size() > 0) {
								if (truckSchedulingZoneEntityList.get(0).getZoneCode() != null) {
									nodeInfoJJR = new VisibleAutoSortNodeInfoDto();
									nodeInfoJJR.setId(truckSchedulingZoneEntityList.get(0).getZoneCode());
									smallZoneEntity=pickupAndDeliverySmallZoneService.querySmallZoneByCode(truckSchedulingZoneEntityList.get(0).getZoneCode());
									if (smallZoneEntity != null && StringUtils.isNotBlank(smallZoneEntity.getGisid())) {
										nodeInfoJJR.setGisID(smallZoneEntity.getGisid());
									}else{
										throw new DeliverbillException("JJR_FAIL","获取节假日小区GisId信息失败！");
									}
									nodeInfoJJR.setDeliveryTimeStart(DOUBLE_ZERO);
									nodeInfoJJR.setDeliveryTimeOver(DOUBLE_ZERO);
									nodeInfoJJR.setFlag(true);
									nodeInfoJJR.setSerialNo(0);
								}
							}
						
					}else{
						throw new DeliverbillException("DELIVERDATE_NOT_NULL","预计送货日期不能为空！");
					}
				} catch (Exception e) {
					throw new DeliverbillException(e.getMessage(),e);
				}	
				
				
			}
			
			//空
			VisibleAutoSortNodeInfoDto nodeInfoNull = null;
			
			//用来存放排序终点(初始值同车辆实际停靠点)
			VisibleAutoSortNodeInfoDto endDeptEntity = new VisibleAutoSortNodeInfoDto();
			
			//copy外场信息,避免json ref
			VisibleAutoSortNodeInfoDto copyVisibleInfo = new VisibleAutoSortNodeInfoDto();
			BeanUtils.copyProperties(copyVisibleInfo, nodeInfoWC);
			VisibleAutoSortNodeInfoDto copyNodeInfoJJR = null;
			if (nodeInfoJJR != null) {
				copyNodeInfoJJR = new VisibleAutoSortNodeInfoDto();
				BeanUtils.copyProperties(copyNodeInfoJJR, nodeInfoJJR);
			}
			
			//出车任务为送+接
			if (deliverbillNewDto.getCarTaskInfo().equals("1")) {
				//车辆实际出发地--无
				json.put("vehicleRealityOut", nodeInfoNull);
				//起点--派送部(外场)
				json.put("startDeptEntity", nodeInfoWC);
				//车辆实际停靠点--如果绑定了节假日小区 取其中一个小区，
				//如果没绑定节假日小区 取车辆定人定区终一个小区，如果都没有 取最后一票运单
				if (truckSchedulingZoneEntityList.size() > 0) {
					json.put("vehicleRealityOver", nodeInfoJJR);
					endDeptEntity = copyNodeInfoJJR;
				}else{
					if (regionVehicleInfo != null && regionVehicleInfo.size() > 0 ) {
						json.put("vehicleRealityOver", nodeInfoDRDQ);
						endDeptEntity = nodeInfoDRDQ;
					}else{
						json.put("vehicleRealityOver", nodeInfoNull);
						endDeptEntity = null;
					}
				}
				
			//出车任务为送+转
			}else if(deliverbillNewDto.getCarTaskInfo().equals("2")) {
				//车辆实际出发地--无
				json.put("vehicleRealityOut", nodeInfoNull);
				//起点--派送部(外场)
				json.put("startDeptEntity", nodeInfoWC);
				//车辆实际停靠点--有转货部门取转货部门，没有转货部门取最后一票运单
				if (StringUtils.isNotBlank(deliverbillNewDto.getTransferDeptCode())) {
					json.put("vehicleRealityOver", nodeInfoZH);
					endDeptEntity = nodeInfoZH;
				}else{
					json.put("vehicleRealityOver", nodeInfoNull);
					endDeptEntity = null;
				}
				
			//出车任务为带+送+接
			}else if(deliverbillNewDto.getCarTaskInfo().equals("3")) {
				//车辆实际出发地--派送部(外场)
				json.put("vehicleRealityOut", nodeInfoWC);
				//起点--带货部门(营业部)
				json.put("startDeptEntity", nodeInfoYYB);
				//车辆实际停靠点--如果绑定了节假日小区 取其中一个小区，
				//如果没绑定节假日小区 取车辆定人定区终一个小区，如果都没有 取最后一票运单
				if (truckSchedulingZoneEntityList.size() > 0) {
					json.put("vehicleRealityOver", nodeInfoJJR);
					endDeptEntity = nodeInfoJJR;
				}else{
					if (regionVehicleInfo != null && regionVehicleInfo.size() > 0 ) {
						json.put("vehicleRealityOver", nodeInfoDRDQ);
						endDeptEntity = nodeInfoDRDQ;
					}else{
						json.put("vehicleRealityOver", nodeInfoNull);
						endDeptEntity = null;
					}
				}
				
			//出车任务为带+送+转
			}else if(deliverbillNewDto.getCarTaskInfo().equals("4")) {
				//车辆实际出发地--派送部(外场)
				json.put("vehicleRealityOut", nodeInfoWC);
				//起点--带货部门(营业部)
				json.put("startDeptEntity", nodeInfoYYB);
				//车辆实际停靠点--有转货部门取转货部门，没有转货部门取最后一票运单
				if (StringUtils.isNotBlank(deliverbillNewDto.getTransferDeptCode())) {
					json.put("vehicleRealityOver", nodeInfoZH);
					endDeptEntity = nodeInfoZH;
				}else{
					json.put("vehicleRealityOver", nodeInfoNull);
					endDeptEntity = null;
				}
			
			//出车任务为二次派送
			}else if(deliverbillNewDto.getCarTaskInfo().equals("5")) {
				//车辆实际出发地--无
				json.put("vehicleRealityOut", nodeInfoNull);
				//起点--派送部(外场)
				json.put("startDeptEntity", nodeInfoWC);
				
				//车辆实际停靠点--派送部(外场)
				json.put("vehicleRealityOver", copyVisibleInfo);
				endDeptEntity = nodeInfoWC;
				
				//出车任务为带+二次派送
			}else if(deliverbillNewDto.getCarTaskInfo().equals("6")) {
				//车辆实际出发地--派送部(外场)
				json.put("vehicleRealityOut", nodeInfoWC);
				//起点--带货部门(营业部)
				json.put("startDeptEntity", nodeInfoYYB);
				//车辆实际停靠点--派送部(外场)
				json.put("vehicleRealityOver", copyVisibleInfo);
				endDeptEntity = nodeInfoWC;
			}
			
			//用来存放运单
			List<VisibleAutoSortNodeInfoDto> waybillList = new ArrayList<VisibleAutoSortNodeInfoDto>();
			List<VisibleAutoSortNodeInfoDto> waybillSpeciallList = null;
			//根据不同的计算方式 取预计出车时间
			//时效排序
			String autoSortType = "";
			if (deliverbillNewDto.getCalculateType().equals(AGING_PRIORITY)) {
				autoSortType = deliverbillNewDto.getVirtualPreCarTaskTime();
			}else{
				autoSortType = deliverbillNewDto.getPreCarTaskTime();
			}
			if (visibleAutoSortDtoList.size() > 0) {
				//如果为人工排序 则不考虑特殊地址
				if (deliverbillNewDto.getCalculateType().equals(ARTIFICIAL_PRIORITY)) {
					for (VisibleAutoSortDto visibleAutoSort : visibleAutoSortDtoList) {
						waybillList.add(this.getVisibleAutoSortNodeInfoDto(visibleAutoSort,autoSortType));
					}
				}else{
					boolean oneSpecialWaybill  = true ;	//是否为第一票空车出特殊运单 默认为 是
					for (VisibleAutoSortDto visibleAutoSort : visibleAutoSortDtoList) {
						//如果有空车出特殊运单
						if (visibleAutoSort.getSpecialAddressType()!=null && (visibleAutoSort.getSpecialAddressType().equals(PKP_GODOWNENTRY_NULLCAR))
								|| (visibleAutoSort.getIsExhibition().equals("Y") 
										&& (StringUtils.isNotBlank(visibleAutoSort.getIsEmptyCar()) 
												&& visibleAutoSort.getIsEmptyCar().equals("Y")))) {
							//如果为第一票空车出特殊运单
							if (oneSpecialWaybill) {
								waybillSpeciallList =new ArrayList<VisibleAutoSortNodeInfoDto>();
								//修改排序终点
								endDeptEntity = this.getVisibleAutoSortNodeInfoDto(visibleAutoSort,autoSortType);
								oneSpecialWaybill = false;
							//第一票以后的空车出运单
							}else{
								waybillSpeciallList.add(this.getVisibleAutoSortNodeInfoDto(visibleAutoSort,autoSortType));
							}
							
						//如果没有空车车特殊运单	
						}else{
							waybillList.add(this.getVisibleAutoSortNodeInfoDto(visibleAutoSort,autoSortType));
						}
					}
				}
				
			}
			//用来存放排序终点的实体，避免json ref
			VisibleAutoSortNodeInfoDto copyEndDeptEntity = null;
			if (endDeptEntity != null) {
				copyEndDeptEntity = new VisibleAutoSortNodeInfoDto();
				BeanUtils.copyProperties(copyEndDeptEntity, endDeptEntity);
			}
			
			//赋值排序终点
			json.put("endDeptEntity", copyEndDeptEntity);
			//赋值运单
			json.put("needSortWaybillList", waybillList);
			//赋值空车出特殊运单
			json.put("specialWaybillList", waybillSpeciallList);
		} catch (Exception e) {
			throw new DeliverbillException(e.getMessage(),e);
		}
		
		return json.toJSONString();
	}
	
	/**
	 * 将VisibleAutoSort转换为接口所需VisibleAutoSortNodeInfoDto对象
	 * @param visibleAutoSort
	 * @param preCarTaskTime
	 * @return
	 */
	public VisibleAutoSortNodeInfoDto getVisibleAutoSortNodeInfoDto(VisibleAutoSortDto visibleAutoSort , String preCarTaskTime){
		VisibleAutoSortNodeInfoDto nodeInfo = new VisibleAutoSortNodeInfoDto();
		if (visibleAutoSort != null) {
			nodeInfo.setId(visibleAutoSort.getId());
			nodeInfo.setBaiduLng(visibleAutoSort.getLongitude());
			nodeInfo.setBaiduLat(visibleAutoSort.getLatitude());
			nodeInfo.setSerialNo(visibleAutoSort.getSerialNo());
			nodeInfo.setFlag(false);
			//送货时间点
			//如果送货时间点不为空
			if (StringUtils.isNotBlank(visibleAutoSort.getDeliveryTimeStart()) 
					&& StringUtils.isNotBlank(visibleAutoSort.getDeliveryTimeOver())) {
				nodeInfo.setDeliveryTimeStart(this.getDeliveryTime(visibleAutoSort.getDeliveryTimeStart(),preCarTaskTime));
				nodeInfo.setDeliveryTimeOver(this.getDeliveryTime(visibleAutoSort.getDeliveryTimeOver(),preCarTaskTime));
			//送货时间点为空
			}else {
				if (StringUtils.isNotBlank(visibleAutoSort.getDeliveryTimeInterval())) {
					//如果送货时间段为上午
					if (visibleAutoSort.getDeliveryTimeInterval().equals("上午")) {
						nodeInfo.setDeliveryTimeStart(this.getDeliveryTime("0:00",preCarTaskTime));
						nodeInfo.setDeliveryTimeOver(this.getDeliveryTime("12:00",preCarTaskTime));
					//如果送货时间段为下午
					}else if (visibleAutoSort.getDeliveryTimeInterval().equals("下午")) {
						nodeInfo.setDeliveryTimeStart(this.getDeliveryTime("12:00",preCarTaskTime));
						nodeInfo.setDeliveryTimeOver(this.getDeliveryTime("24:00",preCarTaskTime));
					//如果送货时间段为全天
					}else {
						nodeInfo.setDeliveryTimeStart(DOUBLE_ZERO);
						nodeInfo.setDeliveryTimeOver(DOUBLE_ZERO);
					}
				}
				
			}
		}else{
			nodeInfo.setDeliveryTimeStart(DOUBLE_ZERO);
			nodeInfo.setDeliveryTimeOver(DOUBLE_ZERO);
			nodeInfo.setId("");
			nodeInfo.setGisID("");
			nodeInfo.setBaiduLat("");
			nodeInfo.setBaiduLng("");
			nodeInfo.setSerialNo(0);
			nodeInfo.setFlag(false);
		}
		return nodeInfo;
	}
	
	
	/**
	 * 更新派送单排序总距离和排序方式
	 * @param visibleAutoSortReturn
	 */
	private void updateSeliverbill(String deliverbillId, DeliverbillNewDto deliverbillNewDto, VisibleAutoSortReturnDto visibleAutoSortReturn){
		DeliverbillEntity deliverbill = new DeliverbillEntity();
		deliverbill.setId(deliverbillId);
		deliverbill.setTotalDistance(visibleAutoSortReturn.getTotalDistance());
		deliverbill.setAutoSortCalculateType(deliverbillNewDto.getCalculateType());
		deliverbill.setModifyDate(new Date());
		deliverbillDao.update(deliverbill);
	}
	
	/**
	 * 更新建议送货时间
	 * @param deliverbillNewDto
	 * @param visibleAutoSortList
	 * @return
	 */
	private void updateDeliverbillDetail(DeliverbillNewDto deliverbillNewDto,VisibleAutoSortReturnDto visibleAutoSortReturn,List<VisibleAutoSortDto> visibleAutoSortDtoList){
		List<VisibleAutoSortNodeReturnInfoDto> visibleAutoSortNodeInfoDtoList = visibleAutoSortReturn.getWaybillList();
		DeliverbillDetailEntity deliverbillDetailEntity = null ;
		try {
			if (visibleAutoSortNodeInfoDtoList.size() > 0) {
				//用来记录上一票运单建议送货时间
				String onNodeTime ="";
				//序号为从1开始排列，所以拿最小序号减1
				int num = visibleAutoSortNodeInfoDtoList.get(0).getSerialNo() - 1;
				
				for (int i = 0; i < visibleAutoSortNodeInfoDtoList.size(); i++) {
					deliverbillDetailEntity = new DeliverbillDetailEntity();
					deliverbillDetailEntity.setId(visibleAutoSortNodeInfoDtoList.get(i).getId());
					//当前序号减去num中转数，将序号从1开始排列
					deliverbillDetailEntity.setSerialNo(visibleAutoSortNodeInfoDtoList.get(i).getSerialNo() - num);
					//调度排序序号 同序号
					deliverbillDetailEntity.setDispatchSortSerialNo(visibleAutoSortNodeInfoDtoList.get(i).getSerialNo() - num);
					//导航时间(分钟)
					int nextNodeTimeMin = 0;
					//第一票运单计算方法
					if (i == 0) {
						//导航距离
						double sumDistance = 0.00;
						//如果为时效排序
						if (deliverbillNewDto.getCalculateType().equals(AGING_PRIORITY)) {
							//如果有带货  建议送货时间为 = 虚拟出车时间 + 带货部门到第一票运单导航时间
							//如果无带货 建议及送货时间就为虚拟出车时间
							if (deliverbillNewDto.getCarTaskInfo().equals("3") 
									|| deliverbillNewDto.getCarTaskInfo().equals("4")
									|| deliverbillNewDto.getCarTaskInfo().equals("6")){
								//计算带货部门到第一票运单导航时间
								if (visibleAutoSortReturn.getStartDeptEntity() != null) {
									//带货部门到第一票运单距离
									sumDistance += visibleAutoSortReturn.getStartDeptEntity().getNavDistance();
									//根据导航距离计算出导航时间(分钟)
									nextNodeTimeMin = (int)(sumDistance/35*60);
								}
							}
							//赋值建议送货时间
							deliverbillDetailEntity.setRecommendedDeliveryTime(this.addTime(deliverbillNewDto.getVirtualPreCarTaskTime(),nextNodeTimeMin));
						//其他排序方式 建议送货时间 = 按照导航距离计算出导航时间 + 预计出车时间
						}else{
							//如果有带货 加上带货时间
							if (deliverbillNewDto.getCarTaskInfo().equals("3") || deliverbillNewDto.getCarTaskInfo().equals("4") 
									|| deliverbillNewDto.getCarTaskInfo().equals("6")) {
								if (deliverbillNewDto.getExpectedbringVolume().compareTo(GD_VOLUMN_CONST) >= 0) {
									deliverbillNewDto.setPreCarTaskTime(this.addTime(deliverbillNewDto.getPreCarTaskTime(),NumberConstants.NUMBER_40));
								}else{
									deliverbillNewDto.setPreCarTaskTime(this.addTime(deliverbillNewDto.getPreCarTaskTime(),NumberConstants.NUMBER_15));
								}
							}
							//实际出车点到排序逻辑起点距离
							if (visibleAutoSortReturn.getVehicleRealityOut() != null) {
								sumDistance += visibleAutoSortReturn.getVehicleRealityOut().getNavDistance();
							}
							//排序逻辑起点到第一票运单距离
							if (visibleAutoSortReturn.getStartDeptEntity() != null) {
								sumDistance += visibleAutoSortReturn.getStartDeptEntity().getNavDistance();
							}
							//根据导航距离计算出导航时间(分钟)
							nextNodeTimeMin = (int)(sumDistance/ NumberConstants.NUMBER_35 * NUMBER_60);
							//赋值建议送货时间
							deliverbillDetailEntity.setRecommendedDeliveryTime(this.addTime(deliverbillNewDto.getPreCarTaskTime(),nextNodeTimeMin));
						}
						
						//记录当前运单建议送货时间 为下一票运单计算建议送货时间提供参数
						onNodeTime = deliverbillDetailEntity.getRecommendedDeliveryTime();
					//第一票以后运单计算方法
					}else{
						//根据导航距离计算出导航时间(分钟)
						nextNodeTimeMin = (int)(visibleAutoSortNodeInfoDtoList.get(i-1).getNavDistance()/ NumberConstants.NUMBER_35 * NUMBER_60);
						//用来存放运单详细信息
						VisibleAutoSortDto transitDto = null;
						for (int j = 0; j < visibleAutoSortDtoList.size(); j++) {
							//在运单集合中找到当前遍历对象详情
							if (visibleAutoSortDtoList.get(j).getId().equals(visibleAutoSortNodeInfoDtoList.get(i-1).getId())) {
								transitDto = visibleAutoSortDtoList.get(j);
								break;
							}
						}
						
						//赋值建议送货时间
						deliverbillDetailEntity.setRecommendedDeliveryTime(this.addTime(onNodeTime,nextNodeTimeMin));
						//计算运单所需操作时间 多种情况下以时间长的为准
						//特殊地址类型为 进仓货-空车出/进仓货-非空车出 或特殊货物类型为 送货进仓 操作时间为60分钟
						if ((StringUtils.isNotBlank(transitDto.getSpecialAddressType()) && transitDto.getSpecialAddressType().equals(PKP_GODOWNENTRY_NULLCAR)) 
								|| (StringUtils.isNotBlank(transitDto.getSpecialAddressType()) && transitDto.getSpecialAddressType().equals(PKP_GODOWNENTRY_NOTNULLCAR)) 
								||GD_RECEIVEMETHOD_DELIVER_INGA.equals(transitDto.getReceiveMethod())) {
							deliverbillDetailEntity.setRecommendedDeliveryTime(this.addTime(deliverbillDetailEntity.getRecommendedDeliveryTime(),NUMBER_60));
							onNodeTime = deliverbillDetailEntity.getRecommendedDeliveryTime();
						//特殊地址类型为 开箱逐件验货 或特殊货物类型为 大件上楼  操作时间为45分钟
						}else if (StringUtils.isNotBlank(transitDto.getSpecialAddressType()) && transitDto.getSpecialAddressType().equals(PKP_DELIVERYADDRESS_UNPACKINGINSPECTION) 
								|| StringUtils.isNotBlank(transitDto.getReceiveMethod()) && GD_RECEIVEMETHOD_LARGE_DELIVER_UP.equals(transitDto.getReceiveMethod())) {
							deliverbillDetailEntity.setRecommendedDeliveryTime(this.addTime(deliverbillDetailEntity.getRecommendedDeliveryTime(), NUMBER_45));
							onNodeTime = deliverbillDetailEntity.getRecommendedDeliveryTime();
						//特殊货物类型为会展货 大票货  操作时间为40分钟
						}else if (StringUtils.isNotBlank(transitDto.getIsExhibition()) && (transitDto.getIsExhibition().equals("Y")) 
								|| transitDto.getGoodsWeight() != null && transitDto.getGoodsWeight().compareTo(GD_WEIGHT_CONST) > 0 
								|| transitDto.getGoodsVolume()!=null && transitDto.getGoodsVolume().compareTo(GD_VOLUMN_CONST) > 0) {
							deliverbillDetailEntity.setRecommendedDeliveryTime(this.addTime(deliverbillDetailEntity.getRecommendedDeliveryTime(), NumberConstants.NUMBER_40));
							onNodeTime = deliverbillDetailEntity.getRecommendedDeliveryTime();
						//送货方式为 送货上楼 操作时间为30分钟
						}else if ( StringUtils.isNotBlank(transitDto.getReceiveMethod()) && transitDto.getReceiveMethod().equals(DELIVER_UP)) {
							deliverbillDetailEntity.setRecommendedDeliveryTime(this.addTime(deliverbillDetailEntity.getRecommendedDeliveryTime(),NumberConstants.NUMBER_30));
							onNodeTime = deliverbillDetailEntity.getRecommendedDeliveryTime();
						//其他  操作时间一律为15分钟
						}else{
							deliverbillDetailEntity.setRecommendedDeliveryTime(this.addTime(deliverbillDetailEntity.getRecommendedDeliveryTime(),15));
							onNodeTime = deliverbillDetailEntity.getRecommendedDeliveryTime();
						}
					}
					deliverbillDetailEntity.setModifyDate(new Date());
					//跟新派送单详情
					deliverbillDetailDao.update(deliverbillDetailEntity);
				}
			}
			
		} catch (Exception e) {
			throw new DeliverbillException("updateRecommendedDeliveryTimeFail", "建议送货时间计算失败！");
		}
		
	}
	
	
	/**
	 * 将字符格式的时间转double类型
	 * @param deliveryTime
	 * @param preCarTaskTime
	 * @return
	 */
	public double getDeliveryTime(String deliveryTime , String preCarTaskTime){
		if (StringUtils.isNotBlank(deliveryTime)) {
			
			double deliveryTimeHourDou = Double.parseDouble(deliveryTime.substring(0,deliveryTime.indexOf(":"))); 
			double deliveryTimeMinDou = Double.parseDouble(deliveryTime.substring(deliveryTime.indexOf(":")+1,deliveryTime.length()))/ NUMBER_60;
			double deliveryTimeDou = deliveryTimeHourDou + deliveryTimeMinDou;
			
			double preCarTaskTimeHourDou = Double.parseDouble(preCarTaskTime.substring(0,preCarTaskTime.indexOf(":"))) ;
			double preCarTaskTimeMinDou = Double.parseDouble(preCarTaskTime.substring(preCarTaskTime.indexOf(":")+1,preCarTaskTime.length()))/ NUMBER_60;
			double preCarTaskTimeDou = preCarTaskTimeHourDou + preCarTaskTimeMinDou;
			if (preCarTaskTimeDou > deliveryTimeDou) {
				return DOUBLE_ZERO;
			}else{
				return deliveryTimeDou - preCarTaskTimeDou;
			}
		}else{
			return DOUBLE_ZERO;
		}
	}
	
	
	/**
	 * 通过部门code获取
	 * 传给GIS的目的站id
	 * @param code
	 * @return
	 */
	private String getGisId(String code){
		String autoOrgId = null;
		if (StringUtils.isNotBlank(code)) {
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoDao.queryLastestOrgAdministrativeInfoByCode(code);
			if (orgEntity != null) {
				autoOrgId = orgEntity.getDepCoordinate();
			}
		}
		return autoOrgId;
	}
	
	
	/**
	 * 获取运单状态: 特殊运单标识1，正常运单标识0 (特殊货物类型, 特殊地址类型)
	 * @param specialNoType 特殊货物类型
	 * @param specialAddressType 特殊地址类型
	 * @return
	 */
	private String getVisibleStatus( VisibleAutoSortDto visibleAutoSortDto, String specialAddressType) {
		if (StringUtil.isBlank(getVisibleGoodStatus(visibleAutoSortDto)) && StringUtil.isBlank(specialAddressType))  {
			return "0";
		} else {
			return "1";
		}
	}
	/**
	 * 特殊货物类型
	 * @param billDto
	 * @return
	 */
	public String getVisibleGoodStatus(VisibleAutoSortDto billDto) {
		StringBuffer strApp = new StringBuffer(" ");
		BigDecimal weight = (BigDecimal)handlerNull(billDto.getGoodsWeight() != null ? billDto.getGoodsWeight() : new BigDecimal(0), "BigDecimal");
		BigDecimal volumn = (BigDecimal)handlerNull(billDto.getGoodsVolume() != null ? billDto.getGoodsVolume() : new BigDecimal(0), "BigDecimal");
		BigDecimal payAmount = (BigDecimal)handlerNull(billDto.getToPayAmount() != null ? billDto.getToPayAmount() : new BigDecimal(0), "BigDecimal");
		if (weight.compareTo(GD_WEIGHT_CONST) > 0 || volumn.compareTo(GD_VOLUMN_CONST) > 0) {
			strApp.append("大票货");
			strApp.append(",");
		}
		if (payAmount.compareTo(GD_PAY_AMOUNT_CONST)  >= 0 )  {
			strApp.append("到付金额1万以上");
			strApp.append(",");
		}
		if (GD_RECEIVEMETHOD_LARGE_DELIVER_UP.equals(billDto.getReceiveMethod())) {
			strApp.append("大件上楼");
			strApp.append(",");
		}
		if (GD_LONGDELIVERY_CONST.equals(billDto.getUitraLongDelivery())) {
			strApp.append("超远派送");
			strApp.append(",");
		}
		if (GD_EXHIBITION_CONST.equals(billDto.getIsExhibition()) && GD_EXHIBITION_CONST.equals(billDto.getIsEmptyCar())) {
			strApp.append("会展货-空车出");
			strApp.append(",");
		}
		
		if (GD_EXHIBITION_CONST.equals(billDto.getIsExhibition()) && !GD_EXHIBITION_CONST.equals(billDto.getIsEmptyCar())) {
			strApp.append("会展货-非空车出");
			strApp.append(",");
		}
		if (GD_RECEIVEMETHOD_DELIVER_INGA.equals(billDto.getReceiveMethod())) {
			strApp.append("送货进仓");
			strApp.append(",");
		}
		return strApp.substring(0, strApp.length() - 1);
	}
	
	/**
	 * 计算建议送货时间
	 * @param oTime 为上一时间
	 * @param addMin 为要加上的分钟数
	 * @return
	 */
	private String addTime(String oTime, Integer addMin ){
		StringBuffer sb =new StringBuffer();
		if (StringUtils.isNotBlank(oTime)) {
			Integer hour = Integer.valueOf(oTime.substring(0,oTime.indexOf(":")));
			Integer min = Integer.valueOf(oTime.substring(oTime.indexOf(":")+1,oTime.length()));
			Integer maxMin = min + addMin;
			while (maxMin >= NUMBER_60) {
				hour +=1;
				maxMin -= NUMBER_60;
			}
			while (hour >= NumberConstants.NUMBER_24) {
				hour -= NumberConstants.NUMBER_24;
			}
			sb.append(hour < NumberConstants.NUMBER_10 ? "0"+hour : hour);
			sb.append(":");
			sb.append(maxMin < NumberConstants.NUMBER_10 ? "0"+maxMin : maxMin);
			
		}
		return sb.toString();
	}
	
	/**
	 * 晚交单转汉字
	 * @param lateno
	 * @return
	 */
	private String isOrLateNo(String lateno) {
		if (null == lateno) {
			return "正常运单";
		}
		if ("Y".equals(lateno)) {
			return "晚交单";
		} else {
			return "正常运单";
		}
	}
	
	//数组去重
   private  static String[] getDistinct(String num[]) {
         List<String> list = new java.util.ArrayList<String>();
         for (int i = 0; i < num.length; i++) {
             if (!list.contains(num[i])) {//如果list数组不包括num[i]中的值的话，就返回true。
                 list.add(num[i]); //在list数组中加入num[i]的值。已经过滤过。
             }
         }
         return list.toArray(new String[0]);  
     }
	
	// 拼接页面信息.
	private String joinString(Object... objects) {
		StringBuffer sb = new StringBuffer();
		for (Object o : objects) {
			if (o != null && StringUtil.isNotEmpty(o.toString())) {
				sb.append(o.toString()).append(DeliverbillConstants.SPLIT_CHAR);
			}
		}
		return sb.length() > 0 ? sb.toString().substring(0, sb.length() - 1) : sb.toString();
	}

	/**
	 * 判断该派送单是否存在无坐标
	 * @param deliverId 派送单id
	 * @return 是否存在无坐标(true:存在无坐标运单， false:不存在无坐标运单)
	 */
	public boolean existsNoCoordForDeliverBill(String deliverId) {
		//如果派送单id为空，则返回不存在无坐标运单
		if (StringUtil.isEmpty(deliverId)) {
			return false;
		}
		List<String> resultList = this.visibleOrderDao.existsNoCoordForDeliverBill(deliverId);
		if (null != resultList && resultList.size() > 0) {
			return true;
		}
		return false;
	}

	/*
        * 从此到底是set方法注入
        */
	public void setVisibleOrderDao(IVisibleOrderDao visibleOrderDao) {
		this.visibleOrderDao = visibleOrderDao;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setDeliverbillDao(IDeliverbillDao deliverbillDao) {
		this.deliverbillDao = deliverbillDao;
	}

	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}

	public void setDeliverbillDetailDao(
			IDeliverbillDetailDao deliverbillDetailDao) {
		this.deliverbillDetailDao = deliverbillDetailDao;
	}

	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setGpsDeliverService(IGpsDeliverService gpsDeliverService) {
		this.gpsDeliverService = gpsDeliverService;
	}

	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}

	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setHandoverbillReturnService(
			IHandoverbillReturnService handoverbillReturnService) {
		this.handoverbillReturnService = handoverbillReturnService;
	}

	public void setExceptionProcessService(
			IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}

	public void setTruckSchedulingTaskService(
			ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}

	public void setDeliverHandoverbillService(
			IDeliverHandoverbillService deliverHandoverbillService) {
		this.deliverHandoverbillService = deliverHandoverbillService;
	}

	public void setDeliverHandoverbillDao(
			IDeliverHandoverbillDao deliverHandoverbillDao) {
		this.deliverHandoverbillDao = deliverHandoverbillDao;
	}

	public void setDeliverBillVaryStatusService(
			IDeliverBillVaryStatusService deliverBillVaryStatusService) {
		this.deliverBillVaryStatusService = deliverBillVaryStatusService;
	}

	public void setPickupAndDeliverySmallZoneDao(
			IPickupAndDeliverySmallZoneDao pickupAndDeliverySmallZoneDao) {
		this.pickupAndDeliverySmallZoneDao = pickupAndDeliverySmallZoneDao;
	}

	public void setRegionalVehicleService(
			IRegionalVehicleService regionalVehicleService) {
		this.regionalVehicleService = regionalVehicleService;
	}

	public void setOrgAdministrativeInfoDao(
			IOrgAdministrativeInfoDao orgAdministrativeInfoDao) {
		this.orgAdministrativeInfoDao = orgAdministrativeInfoDao;
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	public void setDeliverbillNewDetailDao(
			IDeliverbillNewDetailDao deliverbillNewDetailDao) {
		this.deliverbillNewDetailDao = deliverbillNewDetailDao;
	}

	public void setPickupAndDeliverySmallZoneService(
			IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService) {
		this.pickupAndDeliverySmallZoneService = pickupAndDeliverySmallZoneService;
	}


}
