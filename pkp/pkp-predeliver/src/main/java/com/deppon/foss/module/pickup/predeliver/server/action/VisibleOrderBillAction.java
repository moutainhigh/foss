/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2015 PKP
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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/action/VisibleOrderBillAction.java
 * 
 * FILE NAME        	: VisibleOrderBillAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2015  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.service.ISpecialDeliveryAddressService;
import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialDeliveryAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.util.GisInterfaceUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverHandoverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeleteGisAddressService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverHandoverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillExceptionLogService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IVisibleOrderService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWaybilldetailNewService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.HandoverBillExceptionConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.HandoverBillExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VisibleHandBillReturnEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillOtherDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PermissionControlDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualAddressMarkDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillPoint;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.PropertyValues;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillNewVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.VisualBillVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.util.define.FossConstants;


/**
 * 
 * 可视化派单
 * @author 239284-foss-xiedejie
 * @date 2013-12-27 上午11:01:28
 */
public class VisibleOrderBillAction extends AbstractAction {
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(VisibleOrderBillAction.class);
	
	private IVisibleOrderService visibleOrderService;
	
	// 派送单service
	private IDeliverbillService deliverbillService;
	//业务互斥锁服务
	private IBusinessLockService businessLockService;
	//中转service-带货、车辆信息等
	private ITruckSchedulingTaskService truckSchedulingTaskService;
	//组织信息 dao 
	private IOrgAdministrativeInfoDao orgAdministrativeInfoDao;
	//特殊地址服务
	private ISpecialDeliveryAddressService specialDeliveryAddressService;
	//新交单dao
	private IDeliverHandoverbillDao deliverHandoverbillDao;
	//派送交单service
	private IDeliverHandoverbillService deliverHandoverbillService;
	//外场相关共通接口
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	//集中接送小区DAO接口.
	private IPickupAndDeliverySmallZoneDao pickupAndDeliverySmallZoneDao;
	// 交单自动匹配小区和车辆异常日志Service
	private IHandoverBillExceptionLogService handoverBillExceptionLogService;
	//历史地址作废service
	private IDeleteGisAddressService deleteGisAddressService;
	//营业部 Service接口
	private ISaleDepartmentService saleDepartmentService;
	// 部门 复杂查询 service
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	//创建派送单新service
	private IWaybilldetailNewService waybilldetailNewService;
	//运单service
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 送货地址
	 */
	private String deliveryAddress;
	
	/**
	 * 特殊送货地址Entity
	 */
	private SpecialDeliveryAddressEntity specialDeliveryAddressEntity;
	
	/**
	 * 配置
	 */
	private PropertyValues propertyValues;

	//部门查询起始. 
	private static final int BEGIN_NUM = 0;
	// 派送部查询页面大小. 
	private static final int PAGE_SIZE = 1;
	
	private static final String CONSTANTS_TIP_DELIVER_CANCEL = "当前派送单已取消不能继续操作，请关闭当前页面后再次进入操作!";
	
	//可视化排单vo
	private VisualBillVo vo = new  VisualBillVo();
	//派送单vo
	private DeliverbillVo deliverbillVo = new DeliverbillVo();
	
	private DeliverbillNewVo deliverbillNewVo =new DeliverbillNewVo();
	
	/**
	 * serial version Id
	 */
	private static final long serialVersionUID = -1767685094019086295L;
	
	/**
	 * 可视化查询结果作参数掉GIS展现地图
	 * @return
	 */
	@JSON
	public String queryWayBillInfoIndex() {
		try {
				// 查询符合条件的记录列表
			VisualBillConditionDto condition = this.vo.getConditionDto();
			System.out.println(".....");
			if (initCondition(condition)) {
				String jsonParam = visibleOrderService.queryBillInfoToMap(condition);
				if (null != jsonParam && jsonParam.trim().length() > 0) {
					this.vo.setParamForGIS(jsonParam);
				}
			} else {
				this.vo.setParamForGIS(null);
			}
				
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		
		// 返回成功信息
		return super.returnSuccess();
	}
	
	/**
	 * 0 查询派送单状态
	 * @return
	 */
	@JSON
	public String queryDeliverbillInfoById() {
		try {
			String deliverId = this.deliverbillVo.getDeliverbillDto().getId();
			if (StringUtil.isNotBlank(deliverId)) {
				DeliverbillEntity deliverEntity = this.deliverbillService.queryDeliverbill(deliverId);
				if (null != deliverEntity) {
					this.vo.setDeliverStatus(deliverEntity.getStatus());
				} else {
					this.vo.setDeliverStatus(null);
				}
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 1.根据运单号查询运单列表待办信息
	 * @param waybillToArrangeDto
	 * @return
	 */
	@JSON
	public String visibleQueryWaybillToArrange() {
		try {
			//通过页面获取查询条件(运单号集合)
			WaybillToArrangeDto waybillToArrangeDto = this.vo.getWaybillToArrangeDto();
			//初始化查询条件
			initQueryWaybillToArrangeCondition(waybillToArrangeDto);
			
			List<WaybillToArrangeDto> waybillToArrangeDtoList = this.visibleOrderService.queryWaybillToArrangeByCondition(waybillToArrangeDto);
			if (null != waybillToArrangeDtoList && waybillToArrangeDtoList.size() > 0) {
				this.vo.setWaybillToArrangeDtoList(waybillToArrangeDtoList);
			} else {
				this.vo.setWaybillToArrangeDtoList(null);
			}
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		return super.returnSuccess();
	}
	
	/**
	 *2. 校验是否存在未处理的更改单
	 * @return
	 */
	@JSON
	public String visibleIsExsitsWayBillRfcs(){
		try {
			if(deliverbillVo!=null && deliverbillVo.getWaybillNos()!=null && deliverbillVo.getWaybillNos().size()>0){
				deliverbillVo =visibleOrderService.checkWaybillNos(deliverbillVo.getWaybillNos()); 
			}else{
				return super.returnError("请选择待排运单!");
			}
			//返回成功
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
		
	}
	
	/**
	 * 2. 保存新增(更新)预派送单
	 * @return
	 */
	@JSON
	public String visibleSaveDeliverbill() {
		try {
			//外请司机不能修改
			if(this.vo.getDeliverbill().getDelStatus().equals("LOADED")|| this.vo.getDeliverbill().getDelStatus().equals("CONFIRMED")){
				int driverCode=this.vo.getDeliverbill().getDriverCode().length();
				if(driverCode == NumberConstants.NUMBER_15 || driverCode == NumberConstants.NUMBER_18) {
					return super.returnError("外请司机暂时不能更改，请选择其他类型司机");
				}
			}
			
			//拖动排序保存--239284
			String dragStr = this.vo.getDragStr();
			if (StringUtils.isNotEmpty(dragStr)) {
				JSONArray arr = new JSONArray().fromObject(dragStr);
		        Iterator iters = arr.iterator();
		        List<String> deliverDetailIds = new ArrayList<String>();
		        while (iters.hasNext()) {
		        	Map map = (Map)iters.next();
		        	deliverDetailIds.add((String)map.get("id"));
		        }
		       if (deliverDetailIds.size() > 0) {
		    	   this.deliverbillService.updateDeliverDetailSeriNoByDrag(deliverDetailIds);
		       }
			}
			
			//保存(新增/更新)派送单
			DeliverbillEntity deliverbill = this.visibleOrderService.saveDeliverbill(this.vo.getDeliverbill());
			//若派送单为空
			if (deliverbill == null) {
				//返回异常
				return super.returnError("保存失败");
			}
			//设置派送单
			this.vo.setDeliverbill(deliverbill);
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 4. 点击"添加到派送单->"按钮
	 * @return
	 */
	@JSON
	public String  visibleAddWaybillToArrange() {
		//根据派送单ID查找派送单信息
		DeliverbillEntity deliverbill = this.deliverbillService.queryDeliverbill(this.vo.getDeliverbill().getId());
		MutexElement mutexElement = new MutexElement(deliverbill.getDeliverbillNo(), "可视化排单-派送单编号", MutexElementType.NEW_DELIVERBILL_NO);
		try {
			//若派送单不为空
			if (deliverbill != null) {
				// 获取当前用户
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				//互斥锁定
				boolean isLocked = businessLockService.lock(mutexElement, BEGIN_NUM);
				//若未上锁
				if(!isLocked){
					//抛出派送单异常
					throw new DeliverbillException(DeliverbillException.DELIVERBILL_LOCKED);
				}
				//添加运单至派送单
				this.vo.setWaybillToArrangeDtoList(this.visibleOrderService.addWaybillToArrange(deliverbill,this.vo.getWaybillToArrangeDtoList(),currentInfo));
				
				this.deliverbillVo.setDeliverbill(deliverbill);
				
				//解锁
				businessLockService.unlock(mutexElement);
			}
			//返回成功
			return super.returnSuccess("排单成功");
		} catch (BusinessException e) {
			//解锁
			businessLockService.unlock(mutexElement);
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 *3  输入运单号右移
	 * @return
	 */
	@JSON
	public String  visibleAddRightToArrangeByWaybillNo() {
		try {
			if(vo.getDeliverbill()!=null && StringUtils.isNotBlank(this.vo.getWayBillNo())){
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date deliverDate = null;
				try {
					if (StringUtil.isNotBlank(this.vo.getRightDeliverDate())) {
						deliverDate = sdf.parse(this.vo.getRightDeliverDate());
					}
				} catch (ParseException e) { }
				
				//查询交单表的运单,过滤已经退回的运单
				DeliverHandoverbillOtherDto preDto = new DeliverHandoverbillOtherDto();
				preDto.setActive(FossConstants.ACTIVE);
				List<String> waybills = new ArrayList<String>();
				waybills.add(this.vo.getWayBillNo());
				preDto.setWaybillNos(waybills);
				List<DeliverHandoverbillEntity> handOverEntityList = deliverHandoverbillService.queryDeliverHandoverbillByWaybillNos(preDto);
				if (null != handOverEntityList && handOverEntityList.size() > 0) {
					DeliverHandoverbillEntity entity = handOverEntityList.get(0);
					if (StringUtil.isNotBlank(entity.getLongitude()) && StringUtil.isNotBlank(entity.getLatitude())) {
						DeliverbillEntity deliverbill =waybilldetailNewService.waybillDetailAddToArrangeByWaybillNo(this.vo.getDeliverbill(), this.vo.getWayBillNo(), deliverDate);
						//设置派送单
						this.vo.setDeliverbill(deliverbill);
					} else {
						super.returnError("当前单号没有坐标，不能排单!");
					}
				} else {
					super.returnError("当前单号不能操作!");
				}
				
			}else{
				return super.returnError("请输入运单号!");
			}
			
			//返回成功
			return super.returnSuccess("排单成功");
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 5-1.查询派送单下的已排单信息，分页显示.
	 * @param 派送单id、车牌号、预计送货时间(vo.getVehilceNo(), vo.getDeliverDate())
	 * @return the string
	 */
	@JSON
	public String visibleQueryDeliverbillDetailList() {
		try {
			//deliverbillVo.getDeliverbillDto().getId()
			
			//根据派送单id、车牌号、送货日期, 查询汇总统计相关信息， 如果有条数则newVo不为空
			VisualBillVo newVo = this.visibleOrderService.queryDeliverbillDetailCount(deliverbillVo, vo);
			if (null != newVo) {
				//已排单明细
				List<VisualBillArrageDto> listVisualBillArrageDtos = this.visibleOrderService.queryDeliverbillDetailList(deliverbillVo.getDeliverbillDto().getId(), this.getStart(), this.getLimit());
				this.vo.setArrageListDto(listVisualBillArrageDtos);
				
				this.vo.setTotalTicket(newVo.getTotalTicket());
				this.vo.setTotalCount(newVo.getTotalCount());
				this.vo.setTotalWeight(newVo.getTotalWeight());
				this.vo.setTotalVolumn(newVo.getTotalVolumn());
				this.vo.setTotalPayAmount(newVo.getTotalPayAmount());
				this.vo.setLoadRate(newVo.getLoadRate());
				// 测试数据
//				this.vo.setLoadRate(" 65% / 87%");
				this.vo.setNominalRate(newVo.getNominalRate());
				
				this.totalCount = Long.valueOf(newVo.getTotalTicket()); //总记录条数
				
			} else {
				this.vo.setArrageListDto(null);
			}
		
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 5-2. 自动排序，查询派送单下的已排单信息调用GIS自动排序接口
	 * 	返回排序后的运单和序号，
	 * @param 派送单id
	 * @return the string
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@JSON
	public String visibleQueryDeliverbillDetailAllList() {
		try {
			
				//自动排序前判断派送单是否已取消
				String deliverId = this.deliverbillVo.getDeliverbillDto().getId();
				if (StringUtil.isNotBlank(deliverId)) {
					DeliverbillEntity deliverEntity = this.deliverbillService.queryDeliverbill(deliverId);
					if (null != deliverEntity) {
						if (DeliverbillConstants.STATUS_CANCELED.equals(deliverEntity.getStatus())) {
							return super.returnError(CONSTANTS_TIP_DELIVER_CANCEL);
						}
					}
				}
			
				//传给GIS的运单坐标信息
				List<VisualBillPoint> listPoints = new ArrayList<VisualBillPoint>();
				//传给GIS的目的站id
				String autoOrgId = "";
				
				//目的站代码(查询的目的站一样，故取一条运单的目的站)
				String lastLoadOrgCode = "";
				
			
				//已排单明细
				List<VisualBillArrageDto> listVisualBillArrageDtos = this.visibleOrderService.queryDeliverbillDetailAllList(this.deliverbillVo.getDeliverbillDto().getId());
				
				if (listVisualBillArrageDtos != null && listVisualBillArrageDtos.size() > 0) {
					for (VisualBillArrageDto visDto : listVisualBillArrageDtos) {
						VisualBillPoint point = new VisualBillPoint(Double.parseDouble(visDto.getLongitude()), Double.parseDouble(visDto.getLatitude()));
						lastLoadOrgCode = visDto.getLastLoadOrgCode();
						//GIS接受id就是运单号
						point.setId(visDto.getWaybillNo()); 
						listPoints.add(point);
					}
					OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoDao.queryLastestOrgAdministrativeInfoByCode(lastLoadOrgCode);
					if (orgEntity != null) {
						autoOrgId = orgEntity.getDepCoordinate();
					}
					
					//调GIS自动排序接口
					if (null != listPoints && listPoints.size() > 0  && StringUtil.isNotBlank(autoOrgId)) {
						try {
							Map autoParam = new HashMap<String, Object>();
							autoParam.put("pointlist",  listPoints);
							autoParam.put("lastLoadOrgCode", autoOrgId);
							
							//TODO 以后修正，该接口参数是对象类型，不能使用下面方法
							Map<String, Object> resultAutoMap  = GisInterfaceUtil.callGisInterface(propertyValues.getGisAutoSortUrl(), autoParam);
							if (null != resultAutoMap && resultAutoMap.size() > 0) {
								boolean flag = (Boolean) resultAutoMap.get("success");
								if (flag) {
									Map  autoMap = (Map)resultAutoMap.get("resmap");
									this.vo.setSortMap(autoMap);
								} else {
									return super.returnError("自动排序失败!");
								}
							} 
						}catch (Exception e) {
							 handoverBillExceptionLogService.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(deliverbillVo.getDeliverbillDto().getId(),
								"deliver_id", HandoverBillExceptionConstants.EXCEPTION_GIS_AUTO_SORT, e.getMessage(), new Date()));
							 return super.returnError("自动排序失败!");
						}
					} else {
						return super.returnError("没有已排运单和对应目的站，自动排序失败!");
					}
					
				} else {
					return super.returnError("没有已排运单,自动排序失败!");
				}
				
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	/**
	 * 6 运单退回(包含批量)
	 * @return
	 */
	@JSON
	public String visibleBillReturn() {
		try {
			String[] wayBillNos = null;
			//多个运单退回
			int  index = this.vo.getHandBillReturnEntity().getWaybillNo().indexOf(",");
			if (index > 0) {
				 wayBillNos = this.vo.getHandBillReturnEntity().getWaybillNo().split(",");
			} else { //单个运单退回
				wayBillNos = new String[]{this.vo.getHandBillReturnEntity().getWaybillNo()};
			}
			
			if (null != wayBillNos && wayBillNos.length > 0 ) {
				//运单退回操作，如有失败的运单返回失败的运单
				List<VisibleHandBillReturnEntity>  failedList = this.visibleOrderService.visibleBillReturn(wayBillNos, this.vo.getHandBillReturnEntity().getReturnReason(), this.vo.getHandBillReturnEntity().getReturnReasonNotes());
				if (null != failedList && failedList.size() > 0) {
					StringBuffer waybillNos = new StringBuffer();
					for (VisibleHandBillReturnEntity returnFailedEntity : failedList) {
						waybillNos.append(returnFailedEntity.getWaybillNo());
						waybillNos.append(",");
					}
					this.vo.getHandBillReturnEntity().setWaybillNo(waybillNos.substring(0, waybillNos.length() - 1).toString());
				} else {
					this.vo.getHandBillReturnEntity().setWaybillNo(null);
					this.vo.getHandBillReturnEntity().setReturnReason(null);
					this.vo.getHandBillReturnEntity().setReturnReasonNotes(null);
				}
			}
			
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 7 根据车辆和送货日期查询车辆排班、带货信息(一辆车只能保存一个预排)
	 * @param  送货日期
	 *  @param  车牌号
	 *  @param 当前派送单id
	 * @return
	 */
	@JSON
	public String visibleVehicleSchedue() {
		try {
			String deliverId = this.vo.getDeliverbill().getId();
			String deliverDate = this.vo.getDeliverDate();
			Long count = this.visibleOrderService.visibleVehilceNoExistDeliverEntity(deliverDate, this.vo.getVehilceNo(), deliverId,  DeliverbillConstants.STATUS_SAVED, null);
			if (null != count && count.intValue() > 0) {
				this.vo.setFrequencyNo("");
				this.vo.setExpectedBringVolume("");
				return super.returnSuccess("此车" + deliverDate  + "已有已保存状态派送单，不能再生成预排!");
			} else {
				SimpleTruckScheduleDto truckDto = truckSchedulingTaskService.queryTruckByVehicleAndDiliverGoodsTime(vo.getVehilceNo(), deliverDate);
				if (null != truckDto) {
					vo.setFrequencyNo(truckDto.getFrequencyNo()); //"102"); //(
					String expectedBringVolume = "";

					DeliverbillEntity deliverEntity = this.deliverbillService.queryDeliverbill(deliverId);
					if (null != deliverEntity) {
						expectedBringVolume = deliverEntity.getExpectedbringvolume() == null ? "0":String.valueOf(deliverEntity.getExpectedbringvolume());
					} else {
						expectedBringVolume = truckDto.getExpectedBringVolume() == null ? "0" : String.valueOf(truckDto.getExpectedBringVolume());
					}

					vo.setExpectedBringVolume(expectedBringVolume) ; //(expectedBringVolume) ; //34
					vo.setCarTaskinfo(truckDto.getDispatchVehicleTask());

					List<VisualBillArrageDto> listVisualBillArrageDtos = this.visibleOrderService.queryDeliverbillDetailAllList(deliverId);
					/**
					 * 若选择预计送货日期但未排单，
					 * 若派送车辆的首次出车任务为“二次派送”/“带货+二次派送”且此车辆的派送单状态有“已确认”记录，
					 * 则选择的预计送货时，再使用此车辆进行第二次派单时，
					 * “预计出车时间”，将对应关联排班表中的预计二次出车时间字段；
					 */
					if (StringUtil.isBlank(deliverId) || (StringUtil.isNotBlank(deliverId) && null == listVisualBillArrageDtos)) {
						if (DeliverbillConstants.CAR_TASK_SECOND.equals(vo.getCarTaskinfo()) || DeliverbillConstants.CAR_TASK_SECOND_TAKE.equals(vo.getCarTaskinfo())) {
							Long  cunt = this.visibleOrderService.visibleVehilceNoExistDeliverEntity(deliverDate, this.vo.getVehilceNo(),  deliverId,  
									DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED, new String[]{DeliverbillConstants.CAR_TASK_SECOND, DeliverbillConstants.CAR_TASK_SECOND_TAKE});
							if (cunt.intValue() > 0) {
								vo.setPreCartaskTime(truckDto.getIsTheTwoTripTime());
							} else {
								vo.setPreCartaskTime(truckDto.getExpectedDispatchVehicleTime());
							}
						} else {
							vo.setPreCartaskTime(truckDto.getExpectedDispatchVehicleTime());
						}
					} else {
						vo.setPreCartaskTime(truckDto.getExpectedDispatchVehicleTime());
					}
					vo.setTakeGoodsDeptcode(truckDto.getTakeGoodsDepartment());
					vo.setTakeGoodsDeptname(truckDto.getTakeGoodsDepartmentName());
					vo.setTransferDeptcode(truckDto.getTransferGoodsDepartment());
					vo.setTransferDeptname(truckDto.getTransferGoodsDepartmentName());
				}
				//此车牌号XX，送货日期XX，有派送单X，为XX状态
				//复用字段, 本sevice方法查询：OPERATETIME -> 预计送货日期
				DeliverbillEntity entity = this.visibleOrderService.visibleFindVehilceNoTips(deliverDate, this.vo.getVehilceNo(), deliverId);
				if (null != entity) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String status = "";
					if (DeliverbillConstants.STATUS_SAVED.equals(entity.getStatus())) {
						status = "已保存";
					} else if (DeliverbillConstants.STATUS_SUBMITED.equals(entity.getStatus())) {
						status = "已提交";
					} else if (DeliverbillConstants.STATUS_ASSIGNED.equals(entity.getStatus())) {
						status = "已分配";
					} else {
						status = "装车中";
					}
					
					this.vo.setVehilceNoTips("此车牌号" + entity.getVehicleNo() + ", 送货日期" + sdf.format(entity.getOperateTime()) + ", 有派送单" + entity.getDeliverbillNo() +
							", 为" + status + "状态!");
				}
			}
			
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 8.1 自动排序 (派送单号加锁)
	 * @param [运单号、顺序]集合,  派送单号， 派送单id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String visibleAutoSort() {
		try {
			
			Map<String, String>  sortMap = this.vo.getSortMap();
			if (sortMap != null && sortMap.size() > 0) {
				visibleOrderService.autoSortWaybill(sortMap , this.deliverbillVo.getDeliverbill().getId());
			}
			
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 9 特殊地址标记
	 * 如果运单号不为空，则返回
	 *  是否存在特殊地址，如果已经存在，则读取； 不存在返回null
	 * 
	 * @return
	 */
	@JSON
	public String visibleSpecialAddress() {
		try {
			
			String deliveryAddress = "";
			
			//情况一，有运单号则表示从左边地图传入的，
			//   (1)需要查询该运单的地址、小区编码、小区名称
			//   (2)再根据该地址判断是否存在特殊送货地址库
			if (StringUtil.isNotBlank(this.vo.getSpecialWayBillNO())) {
				DeliverHandoverbillEntity conEntity = new DeliverHandoverbillEntity();
				conEntity.setWaybillNo(this.vo.getSpecialWayBillNO());
				DeliverHandoverbillEntity handEntity = deliverHandoverbillDao.queryByWaybillNo(conEntity);
				if(null != handEntity) {
					this.vo.setSpecialSmallZoneCode(handEntity.getActualSmallzoneCode());
					this.vo.setSpecialSmallZoneName(handEntity.getActualSmallzoneName());
					/**
					 * 设置送货地址 上海市青浦区明珠路1018号近徐祥路800米 
					 * (把T_SRV_DELIVER_HANDOVERBILL表五级地址拼接起来不加中划线和括号)
					 */
					deliveryAddress = handleQueryOutfieldService.getCompleteAddress(handEntity.getReceiveCustomerProvCode(),  handEntity.getReceiveCustomerCityCode(),
							handEntity.getReceiveCustomerDistCode(),  handEntity.getReceiveCustomerAddress()).replace(NotifyCustomerConstants.SPLIT_CHAR_DASH, "") + 
							(handEntity.getReceiveCustomerAddressNote() == null ? "" : handEntity.getReceiveCustomerAddressNote());
					
					/**
					 * 校验该地址是否存在特殊地址库
					 * 如果已经存在，则取历史地址库的特殊地址类型
					 */
					if(StringUtil.isNotBlank(deliveryAddress)) {
						specialDeliveryAddressEntity  = specialDeliveryAddressService.selectSpecialDeliveryAddress(deliveryAddress);
					}
					
					this.vo.setSpecialAddress(deliveryAddress);
				} 
			} else {
					//情况二，没有运单号，则表示从右边已排传入的地址
					specialDeliveryAddressEntity = specialDeliveryAddressService.selectSpecialDeliveryAddress(deliveryAddress);
					this.vo.setSpecialAddress(deliveryAddress);
			}
			
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/***
	 * 10  地址坐标标记
	 *  如果运单号不为空，则表示从右边已排单进入
	 */
	@JSON
	public String visibleAddressMark() {
		try {
			
			
			/**
			 * localAddress  地址库(司机采集的)    
			 * hispoint 地址标记的坐标    
			 *  latlng百度解析 
			 *  hisexp 历史经验
			 *  腾讯
			 */
			
			//如果运单号不为空
			if (StringUtil.isNotBlank(this.vo.getSpecialWayBillNO())) {
				List<VisualAddressMarkDto> listAddresMarks = this.visibleOrderService.visibleQueryForCoordMark(this.vo.getSpecialWayBillNO().split(","));
				if(null != listAddresMarks) {
					this.vo.setListAddresMarks(listAddresMarks);
				}
			}
			
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/***
	 * 10.1 根据地址坐标找小区
	 * @return
	 */
	@JSON
	public String visibleFindSmallByCrood() {
		try {
				if (this.vo.getMarkDto() != null) {
					VisualAddressMarkDto  markDto = this.vo.getMarkDto();
				    //参数map
				    Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("appNum", markDto.getWaybillNo());
					paramMap.put("province", markDto.getReceiveCustomerProvCode());
					paramMap.put("city", markDto.getReceiveCustomerCityCode());
					paramMap.put("county", markDto.getReceiveCustomerDistCode());
					paramMap.put("otherAddress", "");  //地址和坐标二传一
					paramMap.put("tel", markDto.getTel());
					paramMap.put("phone", markDto.getPhone());
					paramMap.put("lat", markDto.getLatitude().trim()); //修改后的坐标
					paramMap.put("lng", markDto.getLongitude().trim());//修改后的坐标
					paramMap.put("matchtype", "DE");
					
//					this.vo.setActualSmallzoneCode("SCODEB");
//					this.vo.setActualSmallzoneName("小区B");
					
					try {
						//http://10.224.70.16:8090/gis-ws/matchservice/centralizedAreaMatch.action
						Map<String, Object> resultMap  = GisInterfaceUtil.callGisInterface(propertyValues.getGisMatchCommunityUrl(), paramMap);
						if (null != resultMap && resultMap.size() > 0) {
							boolean flag = (Boolean) resultMap.get("success");
							if (flag) {
								String  smallzoneGisId = (String)resultMap.get("scopeCoordinatesId");
								if (StringUtil.isNotBlank(smallzoneGisId)) {
									List<SmallZoneEntity> smallZoneList = pickupAndDeliverySmallZoneDao.querySmallZoneByGisId(smallzoneGisId.toString());
									if(smallZoneList != null && smallZoneList.size() > 0) {
										SmallZoneEntity zoneEntity = smallZoneList.get(0);
										this.vo.setActualSmallzoneCode(zoneEntity.getRegionCode());
										this.vo.setActualSmallzoneName(zoneEntity.getRegionName());
									} else {
										return super.returnError("您选择的坐标没有匹配到对应小区!");
									}
								} else {
									return super.returnError("您选择的坐标没有匹配到对应小区!");
								}
							}
						}
					} catch (Exception e) {
				        handoverBillExceptionLogService.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(markDto.getWaybillNo()+"-query",
				            markDto.getWaybillNo(), HandoverBillExceptionConstants.EXCEPTION_GIS_FIND_CROOD, e.getMessage(), new Date()));
				        throw new DeliverbillException("您选择的坐标没有匹配到对应小区!");
					}
					
				}
				//返回成功
				return super.returnSuccess();
			} catch (BusinessException e) {
				//记录日志
				LOGGER.error(e.getMessage(), e);
				//返回异常
				return super.returnError(e);
			}
	}
	
	/**
	 * 10.2 修改坐标后保存到交单表
	 * @return
	 */
	@JSON
	public String visibleSaveCrood() {
		try {
			
			if (null != this.vo.getMarkDto()) {
				VisualAddressMarkDto markDto = this.vo.getMarkDto();
			 
				DeliverHandoverbillEntity handEntity = new DeliverHandoverbillEntity();
				handEntity.setOldActive(FossConstants.YES);
				handEntity.setActualSmallzoneCode(markDto.getActualSmallzoneCode());
				handEntity.setActualSmallzoneName(markDto.getActualSmallzoneName());
				handEntity.setLongitude(markDto.getLongitude().trim());
				handEntity.setLatitude(markDto.getLatitude().trim());
				handEntity.setWaybillNo(markDto.getWaybillNo());
				handEntity.setModifyTime(new Date());
				deliverHandoverbillService.updateByWaybillNoSelective(handEntity);
				
				//新增、更新保存历史经验库
				markCroodOperate(markDto, true,  true);
			}
			
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 10.3  作废历史坐标，司机重新采集
	 * @return
	 */
	@JSON
	public String visibleDeleteCrood() {
		try {
			//this.vo.getMarkDto() 坐标修改前的markDto
			if (null != this.vo.getMarkDto()) {
				VisualAddressMarkDto mark = this.vo.getMarkDto();
				
				//调用作废特殊地址历史库
				specialDeliveryAddressService.deleteSpecialDeliveryAddressByAddress(mark.getActualAddress());
				
				try {
					markCroodOperate(mark, false,  false);
				} catch (Exception e) {
					handoverBillExceptionLogService.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(mark.getWaybillNo()+"-gis_D_his",
							mark.getWaybillNo(),  HandoverBillExceptionConstants.EXCEPTION_GIS_DELETE_FOR_HIS,  e.getMessage(), new Date()));
				        throw new DeliverbillException("作废历史坐标司机重新采集失败!");
				}
				
			}
			
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	/**
	 * 11 判断该派送单下是否存在无坐标的运单
	 * @return
	 */
	@JSON
	public String visibleIfExistsNoCoord() {
		try {
			String deliverId = this.deliverbillVo.getDeliverbill().getId();
			if (StringUtil.isEmpty(deliverId)) {
				throw new DeliverbillException("该派送单未进行任何排单，不能进行可视化排序!");
			}
			
			List<DeliverbillDetailEntity> deliverbillDetailList = this.deliverbillService.queryDeliverbillDetailList(deliverId);
			if (null == deliverbillDetailList || deliverbillDetailList.size() ==  0) {
				throw new DeliverbillException("该派送单未进行任何排单，不能进行可视化排序!");
			}
			if (deliverbillDetailList.size() <= NumberConstants.NUMBER_3) {
				throw new DeliverbillException("3票以内运单请人工排序!");
			}
			
			boolean flag = this.visibleOrderService.existsNoCoordForDeliverBill(this.deliverbillVo.getDeliverbill().getId());
			if (flag) {
				throw new DeliverbillException("已排运单列表存在无坐标运单，请标记坐标后再排序！");
			}
			return super.returnSuccess();
		} catch(BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}


	/**
	 * 初始化查询条件
	 * @param condition
	 */
	private boolean initCondition(VisualBillConditionDto condition) {

		//车队只能查询对应派送部交过的单
		//营业部只能查询自己交单的运单
		PermissionControlDto pre= initOrgRole();
        if(pre!= null && pre.isNext()){
        	condition.setLastLoadOrgCode(pre.getLastLoadOrgCode());
        	condition.setEndStockOrgCode(pre.getEndStockOrgCode());
        	condition.setGoodsAreaCode(pre.getGoodsAreaCode());
        }else{
            return false;
        }
		
		//运输性质
		if (StringUtil.isNotBlank(condition.getProductCode())) {
			condition.setProductCodes(condition.getProductCode().split(","));
		}

		//送货大区,多选
		if (StringUtil.isNotBlank(condition.getDeliverGrandArea())) {
			condition.setDeliverGrandAreas(condition.getDeliverGrandArea().replace(" ", "").split(","));
		}
		//送货小区,多选
		if (StringUtil.isNotBlank(condition.getDeliverSmallArea())) {
			condition.setDeliverSmallAreas(condition.getDeliverSmallArea().replace(" ", "").split(","));
		}
		
		//是否选中晚交单
		if (StringUtil.isNotBlank(condition.getLateNo())) {
			condition.setLateNo(FossConstants.YES);
		} else {
			condition.setLateNo(FossConstants.NO);
		}
		
		//是否选中理货员退回
		if (StringUtil.isNotBlank(condition.getTallymanReturnReason())) {
			condition.setTallymanReturnReason(FossConstants.YES);
		} else {
			condition.setTallymanReturnReason(FossConstants.NO);
		}
		
		//是否选中特殊运单(特殊货物/特殊地址)
		if (StringUtil.isNotBlank(condition.getSpecialNoType())) {
			//初始化设置特殊货物类型
			condition.setSpecialNoType(FossConstants.YES);
			//初始化特殊地址类型的参数
			String[] addressTypes = null;
			
			DataDictionaryEntity entity = DictUtil.getDataByTermsCode(DictionaryConstants.PKP_SPECIAL_DELIVERYADDRESS_TYPE);
			if (entity != null) {
				if (entity.getDataDictionaryValueEntityList() != null & entity.getDataDictionaryValueEntityList().size() > 0) {
					List<DataDictionaryValueEntity> values = entity.getDataDictionaryValueEntityList();
					addressTypes = new String[values.size()];
					int i = 0;
					for (DataDictionaryValueEntity valueEntity : values) {
						addressTypes[i] = valueEntity.getValueCode();
						i++;
					}
					condition.setSpecialAddressType(addressTypes);
				}
			}
		} else {
			condition.setSpecialNoType(FossConstants.NO);
		}
		
		//进仓货
		if (StringUtils.isNotBlank(condition.getDeliverInga())) {
			condition.setDeliverInga(FossConstants.YES);
		} else {
			condition.setDeliverInga(FossConstants.NO);
		}
		
		//非进仓货
		if (StringUtils.isNotBlank(condition.getNoDeliverInga())) {
			condition.setNoDeliverInga(FossConstants.YES);
		} else {
			condition.setNoDeliverInga(FossConstants.NO);
		}
		
		//超远派送
		if (StringUtils.isNotBlank(condition.getUitraLongDelivery())) {
			condition.setUitraLongDelivery(FossConstants.YES);
		} else {
			condition.setUitraLongDelivery(FossConstants.NO);
		}
		
		//会展货
		if (StringUtils.isNotBlank(condition.getIsExhibition())) {
			condition.setIsExhibition(FossConstants.YES);
		} else {
			condition.setIsExhibition(FossConstants.NO);
		}
		
		//逐件验货
		if (StringUtils.isNotBlank(condition.getPieceInspection())) {
			condition.setPieceInspection(FossConstants.YES);
		} else {
			condition.setPieceInspection(FossConstants.NO);
		}
		

		//查询条件优先级顺序：送货小区>送货大区>归属车队组；
		if (StringUtil.isNotBlank(condition.getDeliverSmallArea())) {
			//只要小区不为空，以小区查询， 忽略大区、车辆组
			
			condition.setVehicleType(null);
			condition.setDeliverGrandAreas(null);
		}
		//查询条件优先级顺序：送货小区>送货大区>归属车队组；
		if (StringUtil.isNotBlank(condition.getDeliverGrandArea()) && StringUtil.isBlank(condition.getDeliverSmallArea())) {
			//只要小区为空，大区不为空， 以大区查询，忽略车辆组
			
			condition.setVehicleType(null);
		}
		
		
		//运单号
		if (StringUtil.isNotBlank(condition.getWaybillNo())) {
			condition.setWaybillNos(condition.getWaybillNo().split("\\n"));
			
			//排他性, 忽略其他查询条件
//			condition.setDeliverDate(null); //日期为比选项,与班次带货、方数有关
			condition.setProductCodes(null);
//			condition.setGoodsStatus(null);
			condition.setVehicleType(null);
			condition.setDeliverGrandAreas(null);
			condition.setDeliverSmallAreas(null);
			condition.setSpecialNoType(FossConstants.NO);
			condition.setSpecialAddressType(null);
			condition.setLateNo(FossConstants.NO);
			condition.setTallymanReturnReason(FossConstants.NO);
			condition.setDeliverInga(FossConstants.NO);
			condition.setNoDeliverInga(FossConstants.NO);
			condition.setUitraLongDelivery(FossConstants.NO);
			condition.setIsExhibition(FossConstants.NO);
			condition.setPieceInspection(FossConstants.NO);
		} 
		
		condition.setActive(FossConstants.ACTIVE);
		
		LOGGER.info("可视化-默认查询条件为：" + ReflectionToStringBuilder.toString(condition));
		return true;
	}
	
	/**
	 * 设置查询待排运单初始条件.
	 *
	 * @param waybillToArrangeDto 查询待排运单初始条件
	 * @author ibm-wangxiexu
	 * @date 2012-11-14 上午10:09:09
	 */
	private void initQueryWaybillToArrangeCondition(WaybillToArrangeDto waybillToArrangeDto) {
		// --- 运单状态为ACTIVE ---
		waybillToArrangeDto.setWaybillActive(FossConstants.ACTIVE);

		// --- 精准卡航产品代码 ---
		waybillToArrangeDto.setFastWaybillCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);

		// --- 通知客户成功标志 ---
		waybillToArrangeDto.setNotifySuccessFlag(NotifyCustomerConstants.SUCCESS);

		// --- 更改处理状态为已受理 ---
//		waybillToArrangeDto.setRfcStatus(WaybillRfcConstants.ACCECPT);
		
		// --- 更改处理状态为受理拒绝 ---
//		waybillToArrangeDto.setRfcStatusAcceptDeny(WaybillRfcConstants.ACCECPT_DENY);
		
		// --- 更改处理状态为审核拒绝---
//		waybillToArrangeDto.setRfcStatusAuditDeny(WaybillRfcConstants.AUDIT_DENY);

		// --- 异常类型为运单异常 ---
//		waybillToArrangeDto.setExceptionType(ExceptionProcessConstants.WAYBILL_EXCEPTION);

		// --- 异常处理状态为已受理 ---
//		waybillToArrangeDto.setExceptionStatus(ExceptionProcessConstants.HANDLING);

		// --- 关联的到达联有效 ---
		waybillToArrangeDto.setArrivesheetActive(FossConstants.ACTIVE);

		// --- 关联的到达联未删除
		waybillToArrangeDto.setArrivesheetDestroyed(FossConstants.INACTIVE);

		// --- 关联的到达联为“生成”状态
		waybillToArrangeDto.setArrivesheetStatus(ArriveSheetConstants.STATUS_GENERATE);
		
	}
	
	
	/**
	 * 现有的  是 交单件数-已排件数(actual_friehgt 和排单件数)   如果 已排件数小于0 查出来后默认为0
	 * 
	 * 交单表交单件数  -  承运表中已排件数
	 * 
	 * 查询条件 排单件数< 交单件数 and <= 开单件数
	 * 
	 * 最终配载部门-目的站code，+ 运单小区坐标  --自动排序  30s
	 * 
	 * 是否更改单，是否发生目的站更改, 已排过的
	 * 
	 * and ws.GOODS_AREA_CODE = #{goodsAreaCode, jdbcType=VARCHAR}
	 * 
	 * 
	 * 
	select  w.*  
	from pkp.t_srv_waybill  w 
	left join tfr.t_opt_handoverbill_detail hd  on w.waybill_no = hd.waybill_no
	left join tfr.t_opt_handoverbill h  on hd.handover_no = h.handover_no   and h.handoverbill_state != 90
	 ==and  h.handoverbill_state in (30,40); //预计到达，已到达      做子查询，有重复数据

	--库存中
	left join tfr.t_opt_waybill_stock ws  on w.waybill_no = ws.waybill_no  and ws.stock_goods_qty > 0 --库存中  
	where ws.org_code = 'W3100020616'    
	 */
	
	
	
	
	/**
	 * 保存、更新、作废历史库
	 * @param markDto 参数实体
	 * @param isActive false作废，true如果已存在则更新、否则新增
	 * @param logDelActive 日志记录操作状态  false作废操作日志记录， true更新、新增日志记录
	 */
	private void markCroodOperate(VisualAddressMarkDto markDto, boolean isActive, boolean logDelActive) {
		PostMethod postMethod = null;
		try {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(new Date());
				
	            JSONObject jsObj = new JSONObject();
	            jsObj.put("province", markDto.getReceiveCustomerProvCode());
		        jsObj.put("isActive",  isActive);
				jsObj.put("city",markDto.getReceiveCustomerCityCode());
				jsObj.put("country", markDto.getReceiveCustomerDistCode());
				jsObj.put("address",markDto.getReceiveCustomerAddress());
				jsObj.put("gpsLNG",markDto.getLongitude());
				jsObj.put("gpsLAT",markDto.getLatitude());
				jsObj.put("collectTime", cal.getTime());
	            
	            HttpClient httpclient = new HttpClient();  
	            //设置编码格式
	            httpclient.getParams().setContentCharset("UTF-8");	
	            //http://10.224.70.16:8090/gis-ws/com
	            postMethod  = new PostMethod(propertyValues.getGisSaveDelUpdateCroodUrl());
	            RequestEntity entity = new StringRequestEntity(jsObj.toString(), "application/json",  "UTF-8");
	            postMethod.setRequestEntity(entity);
	            postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
				// 执行postMethod
				int statusCode = httpclient.executeMethod(postMethod);
				//访问成功200
				if (statusCode == HttpStatus.SC_OK) {
					String returnInfo = new String(postMethod.getResponseBodyAsString());  
					if ("false".equals(returnInfo)) {
						throw new DeliverbillException("同步坐标信息到历史库异常！");
					}
				} else {
					throw new DeliverbillException("同步请求异常！");
				}

		} catch (Exception e) {
			postMethod.releaseConnection();
			String logErrorText = HandoverBillExceptionConstants.EXCEPTION_GIS_SAVE_UPDATE_FOR_HIS;
			String joinStr = "-gis_SU_his";
			//作废历史库日志
			if (!logDelActive) {
				logErrorText = HandoverBillExceptionConstants.EXCEPTION_GIS_DELETE_FOR_HIS;
				joinStr = "-gis_D_his";
			}
			handoverBillExceptionLogService.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(markDto.getWaybillNo()+ joinStr,
					markDto.getWaybillNo(), logErrorText,  e.getMessage(), new Date()));
		        throw new DeliverbillException("同步坐标信息到历史库异常!");
		} finally {
			postMethod.releaseConnection();
		}
	}
	


	/**
	 * 初始化部门权限
	 * @return
	 */
   private PermissionControlDto initOrgRole(){
        String orgCode = FossUserContextHelper.getOrgCode();
        PermissionControlDto permissionControlDto = new PermissionControlDto();
        permissionControlDto.setNext(true);
        permissionControlDto.setOrgRoleType(0);
        //若当前部门编码不为空时
        if (!StringUtils.isEmpty(orgCode))
        {
            //获取当前用户设置的当前部门
            OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
            //若用户设置的当前部门不为空
            if (org != null)
            {
                String salesDepartment = org.getSalesDepartment();

                // 若当前部门为营业部，则取最终配载部门为当前部门的运单进行排单
                if (FossConstants.YES.equals(salesDepartment))
                {
                    permissionControlDto.setOrgRoleType(1);
                    permissionControlDto.setLastLoadOrgCode(orgCode);
                    // 添加库存外场、库区默认查询条件
                    List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode);
                    if (CollectionUtils.isNotEmpty(list)) {
                        permissionControlDto.setEndStockOrgCode(list.get(0));
                        permissionControlDto.setGoodsAreaCode(list.get(1));
                    }
                } else
                {
                    // 查询排单服务外场
                    OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(orgCode);
                    String orgCode1 = null;
                    permissionControlDto.setOrgRoleType(0);
                    //
                    if (transferCenter != null)
                    {
                        // 取外场驻地派送部的运单进行排单
                        SaleDepartmentEntity sale = new SaleDepartmentEntity();
                        sale.setTransferCenter(transferCenter.getCode());
                        sale.setDelivery(FossConstants.YES);
                        sale.setActive(FossConstants.ACTIVE);
                        sale.setStation(FossConstants.ACTIVE);
                        List<SaleDepartmentEntity> salesList = saleDepartmentService.querySaleDepartmentExactByEntity(sale, BEGIN_NUM, PAGE_SIZE);
                        //若salelist集合不为空
                        if (!CollectionUtils.isEmpty(salesList))
                        {
                            orgCode1 = salesList.get(0).getCode();
                            // 添加库存外场、库区默认查询条件
                            List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode1);
                            if (CollectionUtils.isNotEmpty(list)) {
                                permissionControlDto.setEndStockOrgCode(list.get(0));
                                permissionControlDto.setGoodsAreaCode(list.get(1));
                            }
                            permissionControlDto.setLastLoadOrgCode(orgCode1);
                        }else{
                            permissionControlDto.setNext(false);
                            return permissionControlDto;
                        }
                    }else{
                        permissionControlDto.setNext(false);
                        return permissionControlDto;
                    }

                }
                return permissionControlDto;
            } else
            {
                permissionControlDto.setNext(false);
                return permissionControlDto;
            }

        } else
        {
            permissionControlDto.setNext(false);
            return permissionControlDto;
        }
    }

   /**
	 * @Title: selectSpecialAddress
	 * @Description: 根据送货地址查询特殊送货地址
	 * @return
	 */
	public String selectSpecialAddress() {
		try {
			if (StringUtils.isBlank(deliveryAddress)) {
				// 返回失败
				return returnError("送货地址不能为空");
			}
			specialDeliveryAddressEntity = specialDeliveryAddressService.selectSpecialDeliveryAddress(deliveryAddress);
			// 返回成功
			return super.returnSuccess();
		} catch (DispatchException e) {
			// 返回失败
			return returnError(e);
		}
	}
	
	/**
	 * @Title: updateSpecialAddressType
	 * @Description: 修改交单特殊地址类型和添加特殊地址库
	 * @return
	 */
	public String updateSpecialAddressType() {
		
		try {
			if (specialDeliveryAddressEntity == null) {
				return super.returnError("传入对象参数不能为空");
			}
			
			if (StringUtils.isBlank(waybillNo)) {
				return super.returnError("运单号不能为空");
			}
			
			if (StringUtils.isBlank(specialDeliveryAddressEntity.getDeliveryAddress())) {
				return super.returnError("送货地址不能空");
			}
			
				
			SpecialDeliveryAddressEntity selectSpecialDeliveryAddress = specialDeliveryAddressService.selectSpecialDeliveryAddress(specialDeliveryAddressEntity.getDeliveryAddress());
				
				if (selectSpecialDeliveryAddress == null) {
				
					specialDeliveryAddressService.insertSpecialDeliveryAddress(specialDeliveryAddressEntity);
				} else {
					specialDeliveryAddressService.updateSpecialDeliveryAddress(specialDeliveryAddressEntity);
				}
				DeliverHandoverbillEntity handEntity = new DeliverHandoverbillEntity();
				handEntity.setOldActive(FossConstants.YES);
				handEntity.setSpecialAddressType(specialDeliveryAddressEntity.getAddressType());
				handEntity.setWaybillNo(waybillNo);
				deliverHandoverbillService.updateByWaybillNoSelective(handEntity);
				// 返回成功
				return super.returnSuccess("保存成功");
			} catch (DispatchException e) {
				// 返回失败
				return returnError(e.getMessage());
			}
		}
	
	
	/**
	 * 可视化自动排序 
	 * @return
	 */
	@JSON
	public String visiblebillInfoList(){
		try {
			
			vo.setVisibleAutoSortDtoList(visibleOrderService.queryVisiblebillInfoList(deliverbillVo.getDeliverbillDto().getId()));
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 可视化自动排序 
	 * @return
	 */
	@JSON
	public String visiblebillInfo(){
		try {
			deliverbillNewVo.setDeliverbillNewDto(visibleOrderService.visiblebillInfo(deliverbillVo.getDeliverbillDto().getId()));
//			vo.setVisibleAutoSortDto(visibleOrderService.visiblebillInfo(deliverbillVo.getDeliverbillDto().getId()));
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 可视化自动排序 --保存
	 * @return
	 */
	@JSON
	public String updateVisibleDeliverbillInfo(){
		try {
			visibleOrderService.updateVisibleDeliverbill(vo.getVisibleAutoSortDtoList(),deliverbillNewVo.getDeliverbillNewDto());
			
			//拖动排序保存--239284
			String dragStr = this.vo.getDragStr();
			if (StringUtils.isNotEmpty(dragStr)) {
				JSONArray arr = new JSONArray().fromObject(dragStr);
		        Iterator iters = arr.iterator();
		        List<String> deliverDetailIds = new ArrayList<String>();
		        while (iters.hasNext()) {
		        	Map map = (Map)iters.next();
		        	deliverDetailIds.add((String)map.get("id"));
		        }
		       if (deliverDetailIds.size() > 0) {
		    	   this.deliverbillService.updateDeliverDetailSeriNoByDrag(deliverDetailIds);
		       }
			}
			
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 可视化自动排序 --排序计算
	 * @return
	 */
	@JSON
	public String visibleAutoSortCalculate(){
		try {
			Map map=visibleOrderService.visibleAutoSortCalculate(deliverbillNewVo.getDeliverbillNewDto(),deliverbillVo.getDeliverbillDto().getId());
			vo.setVisibleMapJSON(map.get("visibleAutoSortMapJSONObject").toString());
			vo.setTotalDistance(map.get("totalDistance").toString());
			vo.setVisibleAutoSortDtoListBak(visibleOrderService.queryVisiblebillInfoList(deliverbillVo.getDeliverbillDto().getId()));
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	public void setVisibleOrderService(IVisibleOrderService visibleOrderService) {
		this.visibleOrderService = visibleOrderService;
	}

	public VisualBillVo getVo() {
		return vo;
	}
	

	public void setVo(VisualBillVo vo) {
		this.vo = vo;
	}

	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public DeliverbillVo getDeliverbillVo() {
		return deliverbillVo;
	}

	public void setDeliverbillVo(DeliverbillVo deliverbillVo) {
		this.deliverbillVo = deliverbillVo;
	}

	public void setOrgAdministrativeInfoDao(
			IOrgAdministrativeInfoDao orgAdministrativeInfoDao) {
		this.orgAdministrativeInfoDao = orgAdministrativeInfoDao;
	}

	public void setTruckSchedulingTaskService(
			ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}

	public void setSpecialDeliveryAddressService(
			ISpecialDeliveryAddressService specialDeliveryAddressService) {
		this.specialDeliveryAddressService = specialDeliveryAddressService;
	}
	
	public void setDeliverHandoverbillDao(
			IDeliverHandoverbillDao deliverHandoverbillDao) {
		this.deliverHandoverbillDao = deliverHandoverbillDao;
	}

	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	public void setPickupAndDeliverySmallZoneDao(
			IPickupAndDeliverySmallZoneDao pickupAndDeliverySmallZoneDao) {
		this.pickupAndDeliverySmallZoneDao = pickupAndDeliverySmallZoneDao;
	}
	
	public void setHandoverBillExceptionLogService(
			IHandoverBillExceptionLogService handoverBillExceptionLogService) {
		this.handoverBillExceptionLogService = handoverBillExceptionLogService;
	}

	public void setDeliverHandoverbillService(
			IDeliverHandoverbillService deliverHandoverbillService) {
		this.deliverHandoverbillService = deliverHandoverbillService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setDeleteGisAddressService(
			IDeleteGisAddressService deleteGisAddressService) {
		this.deleteGisAddressService = deleteGisAddressService;
	}

	/**
	 * 获取waybillNo
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置waybillNo
	 * @param waybillNo 要设置的waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取specialDeliveryAddressEntity
	 * @return the specialDeliveryAddressEntity
	 */
	public SpecialDeliveryAddressEntity getSpecialDeliveryAddressEntity() {
		return specialDeliveryAddressEntity;
	}

	/**
	 * 设置specialDeliveryAddressEntity
	 * @param specialDeliveryAddressEntity 要设置的specialDeliveryAddressEntity
	 */
	public void setSpecialDeliveryAddressEntity(SpecialDeliveryAddressEntity specialDeliveryAddressEntity) {
		this.specialDeliveryAddressEntity = specialDeliveryAddressEntity;
	}

	/**
	 * 获取deliveryAddress
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	/**
	 * 设置deliveryAddress
	 * @param deliveryAddress 要设置的deliveryAddress
	 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	
	public void setWaybilldetailNewService(
			IWaybilldetailNewService waybilldetailNewService) {
		this.waybilldetailNewService = waybilldetailNewService;
	}

	public void setDeliverbillNewVo(DeliverbillNewVo deliverbillNewVo) {
		this.deliverbillNewVo = deliverbillNewVo;
	}

	public DeliverbillNewVo getDeliverbillNewVo() {
		return deliverbillNewVo;
	}
	
}