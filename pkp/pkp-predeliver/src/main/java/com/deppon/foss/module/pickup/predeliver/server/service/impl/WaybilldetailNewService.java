package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IWaybillDetailNewDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsDeliverService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IVisibleOrderService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWaybilldetailNewService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverHandoverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PermissionControlDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliverNewCountDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailNewQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 创建派送单(新) ---运单明细
 * @author 159231 meiying
 * 2015-6-3  下午3:45:41
 */
public class WaybilldetailNewService  implements IWaybilldetailNewService {
	
	//日志属性
	private static final Logger LOGGER = LogManager.getLogger(WaybilldetailNewService.class);
	/**
     * 部门查询起始.
     */
    private static final int BEGIN_NUM = 0;

    /**
     * 派送部查询页面大小.
     */
    private static final int PAGE_SIZE = 1;
  //零
  	private static final int ZERO = 0;
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IWaybillDetailNewDao waybillDetailNewDao;
	private IAdministrativeRegionsService administrativeRegionsService;
    /**
     * 营业部 Service接口
     */
    private ISaleDepartmentService saleDepartmentService;
//  派送单DAO接口
	private IDeliverbillDao deliverbillDao;
	//派送单明细DAO接口
	private IDeliverbillDetailDao deliverbillDetailDao;
	//业务互斥锁服务
	private IBusinessLockService businessLockService;
	// 业务监控服务
	private IGpsDeliverService gpsDeliverService;
	private IArriveSheetManngerService arriveSheetManngerService;
	// 业务监控服务
	private IBusinessMonitorService businessMonitorService;
	private IActualFreightDao actualFreightDao;
	private IWaybillManagerService waybillManagerService;
	private IWaybillSignResultService waybillSignResultService;
	private IWaybillRfcService waybillRfcService;
	private IDeliverBillVaryStatusService deliverBillVaryStatusService;
	//添加派送单明细操作. 
	private static final int ADD_OPERATION = 1;
	// 移除派送单明细操作. 
	private static final int DELETE_OPERATION = 2;
	/** 
	 * 排单失败原因
	 */
	private static final String FAILED_REASON_GOODS_ARRANGED = "运单已排单";
	
	/** 
	 * 排单数量超过限制
	 */
	private static final String FAILED_REASON_NOT_ENOUGH_TO_ARRANGE = "排单数量超过限制";
	
	/** 
	 * 排单运行时错误
	 */
	private static final String FAILED_REASON_RUNTIME_EXCEPTION = "排单运行时错误";
	private IGoodsAreaService goodsAreaService;
	private IVisibleOrderService visibleOrderService;
	/**
	 * 设置truckSchedulingTaskService  
	 * @param truckSchedulingTaskService truckSchedulingTaskService 
	 */
	public void setTruckSchedulingTaskService(
			ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}
	private ITruckSchedulingTaskService truckSchedulingTaskService;

	/**
	 * 初始化运单明细查询条件
	 * @author 159231 meiying
	 * 2015-6-3  下午3:51:51
	 * @param pre
	 */
	private void initWaybilldetailNewQuery(WaybillDetailNewQueryDto pre){
		pre.setActive(FossConstants.YES);
		if (StringUtil.isNotBlank(pre.getWaybillNo())) {
            // 解析运单号为列表
            pre.setWaybillNoList(pre.getWaybillNo().split("\\n"));
        }
		
		//送货小区
		if(StringUtil.isNotBlank(pre.getDeliverSmallArea())) {
			pre.setDeliverSmallAreas(pre.getDeliverSmallArea().replace(" ", "").split(","));
			pre.setDeliverGrandArea(null);
			pre.setVehicleType(null);
		}
		//送货大区
		if (StringUtil.isNotBlank(pre.getDeliverGrandArea())) {
			pre.setDeliverGrandAreas(pre.getDeliverGrandArea().replace(" ", "").split(","));
			pre.setVehicleType(null);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IWaybilldetailNewService#initOrgRole()
	 */
	@Override
	public PermissionControlDto initOrgRole(){
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
	 * 根据条件查询运单明细 总数
	 * @author 159231 meiying
	 * 2015-6-3  下午3:48:51
	 * @param pre
	 * @return
	 */
	@Override
	public WaybillDeliverNewCountDto queryWaybilldetailNewCount(WaybillDetailNewQueryDto pre) {
		PermissionControlDto preDto=initOrgRole();
        if(pre!= null && preDto.isNext()){
        	pre.setLastLoadOrgCode(preDto.getLastLoadOrgCode());
        	pre.setEndStockOrgCode(preDto.getEndStockOrgCode());
        	pre.setGoodsAreaCode(preDto.getGoodsAreaCode());
        	pre.setOrgRoleType(preDto.getOrgRoleType());
        }else{
            return null;
        }
        initWaybilldetailNewQuery(pre);
        //如果是根据运单号查
        if(pre.getWaybillNoList()!=null && pre.getWaybillNoList().length>0){
        	return waybillDetailNewDao.queryWaybillDetailNewByWaybillsOtherCount(pre);
        }//如果默认查询30天内的数据
        else if(StringUtils.isNotBlank(pre.getDefaultOneMonth()) && pre.getDefaultOneMonth().equals(FossConstants.YES)){
        	return waybillDetailNewDao.queryWaybillDetailNewOneMonthCount(pre);
        }//如果货物状态为库存中
        else if(StringUtils.isNotBlank(pre.getGoodsStatus()) && DeliverHandoverbillConstants.IN_STOCK.equals(pre.getGoodsStatus())){
        	return waybillDetailNewDao.queryWaybillDetailNewByInStockCount(pre);
        }//如果货物状态为已到达
        else if(StringUtils.isNotBlank(pre.getGoodsStatus()) && TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED.equals(pre.getGoodsStatus())){
        	return waybillDetailNewDao.queryWaybillDetailNewArrivedCount(pre);
        }//如果货物状态为预计到达
        else if(StringUtils.isNotBlank(pre.getGoodsStatus()) && TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY.equals(pre.getGoodsStatus())){
        	return waybillDetailNewDao.queryWaybillDetailNewPreArriveCount(pre);
        }else{
        	return waybillDetailNewDao.queryWaybillDetailNewByWaybillsOtherCount(pre);
        }
	}
	/**
	 * 根据条件查询运单明细
	 * @author 159231 meiying
	 * 2015-6-3  下午3:48:55
	 * @param pre
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<WaybillDetailDto> queryWaybillDetailNewList(
			WaybillDetailNewQueryDto pre, int start, int limit) {
		List<WaybillDetailDto> dtos =new ArrayList<WaybillDetailDto>();
		//如果是根据运单号查
        if(pre.getWaybillNoList()!=null && pre.getWaybillNoList().length>0){
        	dtos= waybillDetailNewDao.queryWaybillDetailNewByWaybillsOtherList(pre, start, limit);
        }//如果默认查询30天内的数据
        else if(StringUtils.isNotBlank(pre.getDefaultOneMonth()) && pre.getDefaultOneMonth().equals(FossConstants.YES)){
        	dtos= waybillDetailNewDao.queryWaybillDetailNewOneMonthList(pre, start, limit);
        }//如果货物状态为库存中
        else if(StringUtils.isNotBlank(pre.getGoodsStatus()) && DeliverHandoverbillConstants.IN_STOCK.equals(pre.getGoodsStatus())){
        	dtos= waybillDetailNewDao.queryWaybillDetailNewInStockList(pre, start, limit);
        }//如果货物状态为已到达
        else if(StringUtils.isNotBlank(pre.getGoodsStatus()) && TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED.equals(pre.getGoodsStatus())){
        	dtos= waybillDetailNewDao.queryWaybillDetailNewArrivedList(pre, start, limit);
        }//如果货物状态为预计到达
        else if(StringUtils.isNotBlank(pre.getGoodsStatus()) && TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY.equals(pre.getGoodsStatus())){
        	dtos= waybillDetailNewDao.queryWaybillDetailNewPreArriveList(pre, start, limit);
        }else{
        	dtos= waybillDetailNewDao.queryWaybillDetailNewByWaybillsOtherList(pre, start, limit);
        }
        if(CollectionUtils.isNotEmpty(dtos) && dtos.size()>0){
        	for (WaybillDetailDto dto : dtos) {
        		StringBuffer sb = new StringBuffer();
        		AdministrativeRegionsEntity entity;
        		// 根据编码查询 -- 省
        		if (StringUtil.isNotBlank(dto.getReceiveCustomerProvCode())) {
        			entity = administrativeRegionsService.queryAdministrativeRegionsByCode(dto.getReceiveCustomerProvCode());
        			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
        				sb.append(entity.getName());
        				dto.setActualProvN(entity.getName());
        				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
        			}
        		}
        		// 根据编码查询 -- 市
        		if (StringUtil.isNotBlank(dto.getReceiveCustomerCityCode())) {
        			entity = administrativeRegionsService.queryAdministrativeRegionsByCode(dto.getReceiveCustomerCityCode());
        			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
        				sb.append(entity.getName());
        				dto.setActualCityN(entity.getName());
        				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
        			}
        		}
        		// 根据编码查询 -- 区
        		if (StringUtil.isNotBlank(dto.getReceiveCustomerDistCode())) {
        			entity = administrativeRegionsService.queryAdministrativeRegionsByCode(dto.getReceiveCustomerDistCode());
        			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
        				sb.append(entity.getName());
        				dto.setActualDistrictN(entity.getName());
        				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
        			}
        		}
        		String add="";
        		if (StringUtil.isNotBlank(sb.toString()) && sb.toString().length() > 0) {
        			add= StringUtil.isNotBlank(dto.getReceiveCustomerAddress()) ? sb.append(dto.getReceiveCustomerAddress()).toString() : sb.substring(0, sb.length() - 1);
        		} else {
        			add= StringUtil.isNotBlank(dto.getReceiveCustomerAddress()) ? dto.getReceiveCustomerAddress() : "";
        		}
        		dto.setConsigneeAddress(StringUtils.isNotEmpty(dto.getReceiveCustomerAddressNote()) ? add+"-"+dto.getReceiveCustomerAddressNote() : add);
        	}
        }
        return dtos;
	}
	/**
	 * 保存(新增/更新)派送单
	 * @param deliverbill
	 * @return
	 */
	@Transactional
	public DeliverbillEntity saveDeliverbill(DeliverbillEntity deliverbill) {
		MutexElement mutexElement = new MutexElement(deliverbill.getDeliverbillNo(), "运单明细-派送单编号", MutexElementType.DELIVERBILL_NO);
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
			deliverbillForGps = deliverbill;
		} else {
			try {
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
				// 添加库存外场、库区默认查询条件
				String areaCode = null;
				if(deliverbillForGps!=null && StringUtils.isNotBlank(deliverbillForGps.getTransferCenter())){
					List<GoodsAreaEntity> goodsAreas =goodsAreaService.queryGoodsAreaListByType(deliverbill.getTransferCenter(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
					if (CollectionUtils.isNotEmpty(goodsAreas)) {
						   //传入库区code
						   areaCode = goodsAreas.get(0).getGoodsAreaCode();
					}
				}
				if(StringUtils.isBlank(areaCode)){
					areaCode=NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE;//如果为空 就改为N/A
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
			} catch (BusinessException e) {
				//解锁
				businessLockService.unlock(mutexElement);
				throw new DeliverbillException(e.getErrorCode(),e);
			}
		}
		//解锁
		businessLockService.unlock(mutexElement);
		return deliverbillEntity;
	}
	/**
	 * 第一次保存派送单号
	 * @author 159231 meiying
	 * 2015-6-23  下午5:44:34
	 * @param deliverbill
	 * @return
	 */
	public DeliverbillEntity initDeliverbill(DeliverbillEntity deliverbill){
		DeliverbillEntity deliverbillEntity = null;
		//更新派送单状态
		deliverbill.setStatus(DeliverbillConstants.STATUS_SAVED);
		this.updateDeliverbillCreateInfo(deliverbill);
		deliverbillEntity = this.deliverbillDao.add(deliverbill);
		//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
		if (StringUtils.isNotBlank(deliverbill.getDeliverbillNo())) {
			DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
			deliverBillVary.setDeliverBillNo(deliverbill.getDeliverbillNo());//派送单号
			deliverBillVary.setDeliverBillStatus(deliverbill.getStatus());//派送单状态
			deliverBillVary.setOperatorName(deliverbill.getOperator());//操作人
			deliverBillVary.setOperatorCode(deliverbill.getOperatorCode());//操作人编码
			deliverBillVary.setOperateOrgName(deliverbill.getOperateOrgName());//操作部门
			deliverBillVary.setOperateOrgCode(deliverbill.getOperateOrgCode());//操作部门编码
			//添加纪录
			deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
			return deliverbillEntity;
		}else{
			throw new DeliverbillException("传入的派送单号为空,请重试!");
		}
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
		deliverbill.setCreateType(FossConstants.YES);

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
		//BUG-38291  当派送单为已下拉状态 已签收确认 取消时  不能再修改状态
		DeliverbillEntity deliverbillEntity = deliverbillDao.queryById(deliverbill.getId());	
		if(deliverbillEntity!= null && 
			(DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbillEntity.getStatus())
			|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbillEntity.getStatus())
			||DeliverbillConstants.STATUS_CANCELED.equals(deliverbillEntity.getStatus()))) {
			throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
		}
		if(deliverbillEntity!= null && !DeliverbillConstants.STATUS_SAVED.equals(deliverbillEntity.getStatus())) {
			deliverbill.setStatus(null);
		}
		return deliverbill;
	}
	/**
	 * 输入单号右移操作
	 * @author 159231 meiying
	 * 2015-6-10  下午2:43:23
	 * @param deliverbill
	 * @param waybillNo
	 */
	@Transactional
	@Override
    public DeliverbillEntity waybillDetailAddToArrangeByWaybillNo(DeliverbillEntity deliverbill,String waybillNo,Date deliverDate){
        WaybillToArrangeDto waybillFailed= new WaybillToArrangeDto();
        //查询运单是否满足条件
    	List<String> waybillNoList =new ArrayList<String>();
    	waybillNoList.add(waybillNo);
    	List<String> rfc=new ArrayList<String>();
    	rfc=waybillRfcService.isExsitsWayBillRfcs(waybillNoList);
		if(rfc!= null && rfc.size()>0){
			throw new DeliverbillException("该运单有更改单未受理,不能排单");
		}
		rfc=waybillRfcService.queryUnActiveRfcWaybillNo(waybillNoList);
		if(rfc!= null && rfc.size()>0){
			throw new DeliverbillException("该运单有更改单申请未受理,不能排单");
		}
		WaybillDetailNewQueryDto pre=new WaybillDetailNewQueryDto();
		PermissionControlDto preDto=initOrgRole();
        if(pre!= null && preDto.isNext()){
        	pre.setLastLoadOrgCode(preDto.getLastLoadOrgCode());
        	pre.setEndStockOrgCode(preDto.getEndStockOrgCode());
        	pre.setGoodsAreaCode(preDto.getGoodsAreaCode());
        	pre.setOrgRoleType(preDto.getOrgRoleType());
        }else{
        	throw new DeliverbillException("当前部门没有权限!");
        }
        pre.setActive(FossConstants.YES);
        initWaybilldetailNewQuery(pre);
        String [] waybillNos ={waybillNo};
        pre.setWaybillNoList(waybillNos);
        //如果满足确认是否存在派送单
        List<WaybillDetailDto> dtos= waybillDetailNewDao.queryWaybillDetailNewByWaybillsOtherList(pre, ZERO, PAGE_SIZE);
        if(CollectionUtils.isNotEmpty(dtos)&& dtos.size()>0){
        	if(StringUtils.isBlank(deliverbill.getId())){
	        	//如果存在添加运单明细信息
	       	     this.initDeliverbill(deliverbill);
        	}
             WaybillDetailDto waybillDetailDto=dtos.get(0);
             if(deliverDate!=null){//如果送货日期不为空
            	if(waybillDetailDto.getDeliverDate()!=null){
            		String newDate=DateUtils.convert(waybillDetailDto.getDeliverDate(),DateUtils.DATE_FORMAT);
            		String oldDate=DateUtils.convert(deliverDate,DateUtils.DATE_FORMAT);
            		if(DateUtils.getTimeDiff(newDate,oldDate)!=0){
                        throw new DeliverbillException("当前待排单的送货日期必须与已排单的送货日期一致!");
                    }
            	}
             }
             if(StringUtils.isBlank(waybillDetailDto.getActualSmallzoneCode())&& StringUtils.isBlank(waybillDetailDto.getAddressType())){
            	 if(StringUtils.isBlank(waybillDetailDto.getUitraLongDelivery())||FossConstants.NO.equals(waybillDetailDto.getUitraLongDelivery())){
                 	 throw new DeliverbillException("除特殊地址和超远派送运单外，无送货小区不能排单，请添加小区后再排单！");
            	 }
             }
             String add=handleQueryOutfieldService.getCompleteAddress(waybillDetailDto.getReceiveCustomerProvCode(),waybillDetailDto.getReceiveCustomerCityCode(),waybillDetailDto.getReceiveCustomerDistCode(),waybillDetailDto.getReceiveCustomerAddress());
             waybillDetailDto.setConsigneeAddress(StringUtils.isNotEmpty(waybillDetailDto.getReceiveCustomerAddressNote()) ? add+"-"+waybillDetailDto.getReceiveCustomerAddressNote() : add);
    		 WaybillToArrangeDto waybill=new WaybillToArrangeDto();
        	try {
				BeanUtils.copyProperties(waybill,waybillDetailDto);//把waybillDetailDto里的内容复制到waybill里
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
        	//BUG-38291  当派送单为已下拉状态 已签收确认 取消时  不能再修改状态
    		DeliverbillEntity deliverbillEntity = deliverbillDao.queryById(deliverbill.getId());	
    		if(deliverbillEntity!= null && 
    			(DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbillEntity.getStatus())
    			||DeliverbillConstants.STATUS_LOADED.equals(deliverbillEntity.getStatus())
    			||DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillEntity.getStatus())
    			|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbillEntity.getStatus())
    			||DeliverbillConstants.STATUS_CANCELED.equals(deliverbillEntity.getStatus()))) {
    			throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
    		}
             // 单票运单排单
    	   waybillFailed = this.arrangeWaybill(deliverbillEntity, waybill);
    	   if(waybillFailed!=null && StringUtils.isNotBlank(waybillFailed.getFailedToArrangeReason())){
    		   throw new DeliverbillException(waybillFailed.getFailedToArrangeReason()); 
    	   }
    	   return deliverbillEntity;
    	   
       }else{
    	   throw new DeliverbillException("当前运单不满足排单条件,不能排单"); 
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
	@Transactional
	public List<WaybillToArrangeDto> addWaybillToArrange(DeliverbillEntity deliverbill, List<WaybillToArrangeDto> waybillToArrangeDtoList, 	CurrentInfo currentInfo) {
		if(StringUtils.isBlank(deliverbill.getId())){
        	//如果存在添加运单明细信息
       	     this.initDeliverbill(deliverbill);
    	}
		// 定义排单失败的运单列表，作为返回
		List<WaybillToArrangeDto> waybillListFailed = new ArrayList<WaybillToArrangeDto>();
		
		DeliverbillEntity deliverbillEntity = deliverbillDao.queryById(deliverbill.getId());	
		
		if(deliverbillEntity!= null && (DeliverbillConstants.STATUS_LOADED.equals(deliverbillEntity.getStatus())
			|| DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillEntity.getStatus()) 
			|| DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbillEntity.getStatus()) 
			|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbillEntity.getStatus())
			||DeliverbillConstants.STATUS_CANCELED.equals(deliverbillEntity.getStatus()))) {
			throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
		}
		int i = 0;
		//循环运单列表
		for (WaybillToArrangeDto waybill : waybillToArrangeDtoList) {
			// 单票运单排单
			WaybillToArrangeDto waybillFailed = this.arrangeWaybill(deliverbillEntity, waybill);
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
	
	public WaybillToArrangeDto arrangeWaybill(DeliverbillEntity deliverbill, WaybillToArrangeDto waybill) {
		MutexElement mutexElement = new MutexElement(waybill.getWaybillNo(), "运单明细-运单排单", MutexElementType.DELIVERBILLDETAIL_NO);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, ZERO);
		//如果没有上锁
		if(!isLocked){
			throw new DeliverbillException(DeliverbillException.WAYBILL_LOCKED);
		}	
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybill.getWaybillNo());
		if(waybillEntity!=null){
			waybill.setWaybillGoodsQty(waybillEntity.getGoodsQtyTotal()==null?0:waybillEntity.getGoodsQtyTotal());
			waybill.setGoodsName(waybillEntity.getGoodsName());
			waybill.setTransportType(waybillEntity.getProductCode());
			waybill.setDeliverType(waybillEntity.getReceiveMethod());
			waybill.setConsigneeContact(waybillEntity.getReceiveCustomerMobilephone());
			waybill.setReturnBillType(waybillEntity.getReturnBillType());
			waybill.setPaymentType(waybillEntity.getPaidMethod());
			waybill.setEstimatedDeliverTime(waybillEntity.getPreCustomerPickupTime());
			waybill.setConsignee(waybillEntity.getReceiveCustomerName());
			waybill.setGoodsStatus(null);
			waybill.setCurrencyCode(waybillEntity.getCurrencyCode());
		}else{
			businessLockService.unlock(mutexElement);
			//抛出异常 参数错误
			throw new DeliverbillException("");
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
			waybill.setArriveTime(actualFreight.getArriveTime());
			//可排件数 = 交单件数 - 排单件数
			Integer arrangableGoodsQty = actualFreight.getDeliverBillQty() - actualFreight.getArrangeGoodsQty();
				
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
		return deliverbill;
	}
	/**
	 * 查询派送单已排单下的明细信息
	 */
	public List<WaybillDetailBillArrageDto>  queryDeliverbillDetailList(String deliverbillId) {
		
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("deliverbillId", deliverbillId);
		
		//根据派送单ID查找派送单明细列表
		List<WaybillDetailBillArrageDto> entitys = this.waybillDetailNewDao.queryDeliverbillDetailList(map);
		
		return entitys;
	}
	/**
	 * 查询已排运单里的( 装载率(容积/体积): 额定净空(方)/额定载重(吨)) 
	 * @author 159231 meiying
	 * 2015-6-12  上午10:26:49
	 * @param detail
	 * @param querydto
	 * @return
	 */
	@Override
	public WaybillDeliverNewCountDto queryRightCount(WaybillDeliverNewCountDto detail,WaybillDetailNewQueryDto querydto, String deliverId){
		//SimpleTruckScheduleDto truckDto = truckSchedulingTaskService.queryTruckByVehicle(querydto.getVehicleNo(), querydto.getDeliverDate());
		SimpleTruckScheduleDto truckDto = truckSchedulingTaskService.queryTruckByVehicleAndDiliverGoodsTime(querydto.getVehicleNo(), querydto.getDeliverDate());
		if (null != truckDto) {
				//净空
				BigDecimal  selfVolume = truckDto.getSelfVolume() == null ? BigDecimal.ZERO : truckDto.getSelfVolume();
				//载重
				BigDecimal deadLoad = truckDto.getDeadLoad() == null ? BigDecimal.ZERO : truckDto.getDeadLoad();
				
				/*//带货(方)
				BigDecimal expectedBringVolume = truckDto.getExpectedBringVolume() == null ? BigDecimal.ZERO : truckDto.getExpectedBringVolume();
				//是否带货
				String isBringExpress = truckDto.getIsBringExpress();*/
				
				DeliverbillEntity deliverEntity = this.queryDeliverbillById(deliverId);
				
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
				
				//重量装载率 （排单重量/车辆载重(以千克计算)）%
				BigDecimal  weightRateNum = BigDecimalOperationUtil.div(detail.getTotalWeight(), deadLoad.multiply(new BigDecimal(NumberConstants.NUMBER_1000)), 2);
				String weightRate = (BigDecimalOperationUtil.mul(weightRateNum, new BigDecimal(100), 0)).toString() + "%";
				//体积装载率 分带货 or 不带货 
				String volumRate = "";
				if (StringUtils.isNotBlank(carTaskInfo)) { //带货  装载率（体积）=（总体积+带快递货体积）/净空*100%
					if (DeliverbillConstants.CAR_TASK_TAKE_DELIVE_PICK.equals(carTaskInfo) || DeliverbillConstants.CAR_TASK_TAKE_DELIVE_TRAN.equals(carTaskInfo) ||
							DeliverbillConstants.CAR_TASK_SECOND_TAKE.equals(carTaskInfo)) {
						BigDecimal remainderVolumn = detail.getTotalVolumn()==null?BigDecimal.ZERO:detail.getTotalVolumn().add(expectedBringVolume);
						BigDecimal volumRateNum = BigDecimalOperationUtil.div(remainderVolumn, selfVolume, 2);
						volumRate = (BigDecimalOperationUtil.mul(volumRateNum, new BigDecimal(NumberConstants.NUMBER_100), 0)).toString() + "%";
					} else {
						BigDecimal volumRateNum = BigDecimalOperationUtil.div(detail.getTotalVolumn(), selfVolume, 2);
						volumRate = (BigDecimalOperationUtil.mul(volumRateNum, new BigDecimal(NumberConstants.NUMBER_100), 0)).toString() + "%";
					}
				} else { //非带货 （排单体积/车辆净空）%
					BigDecimal volumRateNum = BigDecimalOperationUtil.div(detail.getTotalVolumn(), selfVolume, 2);
					volumRate = (BigDecimalOperationUtil.mul(volumRateNum, new BigDecimal(NumberConstants.NUMBER_100), 0)).toString() + "%";
				}
				//nominalRate  额定净空（方）/额定载重（吨） 下面 /是分隔符，不是值比
				detail.setNominalRate(String.valueOf(selfVolume) + " / " + String.valueOf(deadLoad));
				detail.setExpectedBringVolume(expectedBringVolume);//带货(方)
				detail.setFrequencyNo(truckDto.getFrequencyNo());//班次
				detail.setCarTaskinfo(truckDto.getDispatchVehicleTask());
				//二期排程加入字段
				List<VisualBillArrageDto> listVisualBillArrageDtos = visibleOrderService.queryDeliverbillDetailAllList(deliverId);
				/**
				 * 若选择预计送货日期但未排单，
				 * 若派送车辆的首次出车任务为“二次派送”/“带货+二次派送”且此车辆的派送单状态有“已确认”记录，
				 * 则选择的预计送货时，再使用此车辆进行第二次派单时，
				 * “预计出车时间”，将对应关联排班表中的预计二次出车时间字段；
				 */
				if (StringUtil.isBlank(deliverId) || (StringUtil.isNotBlank(deliverId) && null == listVisualBillArrageDtos)) {
					if (DeliverbillConstants.CAR_TASK_SECOND.equals(detail.getCarTaskinfo()) || DeliverbillConstants.CAR_TASK_SECOND_TAKE.equals(detail.getCarTaskinfo())) {
						Long  cunt = this.visibleOrderService.visibleVehilceNoExistDeliverEntity(querydto.getDeliverDate(), querydto.getVehicleNo(), deliverId,  
								DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED, new String[]{DeliverbillConstants.CAR_TASK_SECOND, DeliverbillConstants.CAR_TASK_SECOND_TAKE});
						if (cunt.intValue() > 0) {
							detail.setPreCartaskTime(truckDto.getIsTheTwoTripTime());
						} else {
							detail.setPreCartaskTime(truckDto.getExpectedDispatchVehicleTime());
						}
					} else {
						detail.setPreCartaskTime(truckDto.getExpectedDispatchVehicleTime());
					}
				} else {
					detail.setPreCartaskTime(truckDto.getExpectedDispatchVehicleTime());
				}
				//detail.setPreCartaskTime(truckDto.getExpectedDispatchVehicleTime());
				detail.setTakeGoodsDeptcode(truckDto.getTakeGoodsDepartment());
				detail.setTakeGoodsDeptname(truckDto.getTakeGoodsDepartmentName());
				detail.setTransferDeptcode(truckDto.getTransferGoodsDepartment());
				detail.setTransferDeptname(truckDto.getTransferGoodsDepartmentName());

				
				//loadRate 装载率(体积和重量)  下面 /是分隔符，不是值比
				detail.setLoadRate(volumRate + " / " + weightRate);
				return detail;
		}else{
			detail.setNominalRate("0/0");
			detail.setLoadRate("0%/0%");
			detail.setExpectedBringVolume(null);//带货(方)
			detail.setFrequencyNo(null);//班次
			detail.setCarTaskinfo("");
			detail.setPreCartaskTime("");
			detail.setTakeGoodsDeptcode("");
			detail.setTakeGoodsDeptname("");
			detail.setTransferDeptcode("");
			detail.setTransferDeptname("");
			return detail;
		}
	}
	/**
	 * 设置handleQueryOutfieldService  
	 * @param handleQueryOutfieldService handleQueryOutfieldService 
	 */
	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}
	/**
	 * 设置orgAdministrativeInfoComplexService  
	 * @param orgAdministrativeInfoComplexService orgAdministrativeInfoComplexService 
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/**
	 * 设置saleDepartmentService  
	 * @param saleDepartmentService saleDepartmentService 
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	/**
	 * 设置waybillDetailNewDao  
	 * @param waybillDetailNewDao waybillDetailNewDao 
	 */
	public void setWaybillDetailNewDao(IWaybillDetailNewDao waybillDetailNewDao) {
		this.waybillDetailNewDao = waybillDetailNewDao;
	}
	/**
	 * 设置deliverbillDao  
	 * @param deliverbillDao deliverbillDao 
	 */
	public void setDeliverbillDao(IDeliverbillDao deliverbillDao) {
		this.deliverbillDao = deliverbillDao;
	}
	/**
	 * 设置deliverbillDetailDao  
	 * @param deliverbillDetailDao deliverbillDetailDao 
	 */
	public void setDeliverbillDetailDao(IDeliverbillDetailDao deliverbillDetailDao) {
		this.deliverbillDetailDao = deliverbillDetailDao;
	}
	/**
	 * 设置businessLockService  
	 * @param businessLockService businessLockService 
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	/**
	 * 设置gpsDeliverService  
	 * @param gpsDeliverService gpsDeliverService 
	 */
	public void setGpsDeliverService(IGpsDeliverService gpsDeliverService) {
		this.gpsDeliverService = gpsDeliverService;
	}
	/**
	 * 设置arriveSheetManngerService  
	 * @param arriveSheetManngerService arriveSheetManngerService 
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
	/**
	 * 设置businessMonitorService  
	 * @param businessMonitorService businessMonitorService 
	 */
	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}
	/**
	 * 设置actualFreightDao  
	 * @param actualFreightDao actualFreightDao 
	 */
	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}
	/**
	 * 设置waybillManagerService  
	 * @param waybillManagerService waybillManagerService 
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 * 设置waybillSignResultService  
	 * @param waybillSignResultService waybillSignResultService 
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	/**
	 * 设置waybillRfcService  
	 * @param waybillRfcService waybillRfcService 
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	/**
	 * 设置goodsAreaService  
	 * @param goodsAreaService goodsAreaService 
	 */
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	public void setDeliverBillVaryStatusService(
			IDeliverBillVaryStatusService deliverBillVaryStatusService) {
		this.deliverBillVaryStatusService = deliverBillVaryStatusService;
	}

	/**
	 * 设置administrativeRegionsService  
	 * @param administrativeRegionsService administrativeRegionsService 
	 */
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/**
	 * 根据查询条件查询待排运单.
	 *
	 * @param waybillDetailNewQueryDto 
	 * 		查询条件
	 * 
	 * @param start 
	 * 			the start
	 * @param limit 
	 * 			the limit
	 * @return the list
	 * @author foss-sunyanfei
	 * @date 2015-8-4  上午11:32:27
	 */
	@Transactional
	public List<WaybillDetailDto> queryWaybillDetailNewListByCondition(
			WaybillDetailNewQueryDto waybillDetailNewQueryDto, int start,
			int limit) {

		LOGGER.info("创建派送单（新）-----运单详情实体"+ReflectionToStringBuilder.toString(waybillDetailNewQueryDto));
				
		List<WaybillDetailDto> waybillDetailDto = new ArrayList<WaybillDetailDto>();
		
		waybillDetailDto = this.queryWaybillDetailNewList(waybillDetailNewQueryDto, start, limit);
		if (null != waybillDetailDto && waybillDetailDto.size() > 0) {
			if (StringUtil.isNotBlank(waybillDetailNewQueryDto.getWaybillNo()))  {
				HashMap<String , WaybillDetailDto> map = new HashMap<String, WaybillDetailDto>();
				for (int i = 0; i < waybillDetailDto.size(); i++) {
					map.put(waybillDetailDto.get(i).getWaybillNo(), waybillDetailDto.get(i));
				}
				List<WaybillDetailDto> WaybillDetailDto1 = new ArrayList<WaybillDetailDto>();
				for (String waybill : waybillDetailNewQueryDto.getWaybillNoList()) {
					if (map.containsKey(waybill)) {
						WaybillDetailDto1.add(map.get(waybill));
					}
				}
				return WaybillDetailDto1;
			}
		}
		
		return waybillDetailDto;
	
	}

	/**
	 *  查询派送单相关信息
	 * @author 159231 meiying
	 * 2015-6-7  下午4:39:55
	 * @param deliverbill
	 * @return
	 */
	public DeliverbillEntity queryDeliverbillById(String deliverbillId) {
		return deliverbillDao.queryById(deliverbillId);
	}

	public IVisibleOrderService getVisibleOrderService() {
		return visibleOrderService;
	}

	public void setVisibleOrderService(IVisibleOrderService visibleOrderService) {
		this.visibleOrderService = visibleOrderService;
	}

	//根据运单号查询收货联系人
	@Override
	public String queryReceiveCustomerContactByWaybillNo(
			String waybillNo) {
		return waybillDetailNewDao.queryWaybillDetailNewByWaybillNo(waybillNo);
		
	}
	
	//根据运单号查询交单时间
	@Override
	public Date queryBilltimeByWaybillNo(String waybillNo) {
		return waybillDetailNewDao.queryBilltimeByWaybillNo(waybillNo);
	}
	
	
}
