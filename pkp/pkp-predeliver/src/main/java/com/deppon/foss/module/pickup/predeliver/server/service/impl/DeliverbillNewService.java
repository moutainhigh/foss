package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnDriverException;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSSendLogException;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckScheduleDao;
import com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutService;
import com.deppon.foss.module.pickup.order.api.server.service.ISpecialDeliveryAddressService;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.TruckConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillNewDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillNewDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillNewService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsDeliverService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillSMSDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DriverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PermissionControlDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillTrackDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadTaskQueryService;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadWayBillDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 派送单(新)Service实现.
 *
 * @since
 * @version
 */
public class DeliverbillNewService implements IDeliverbillNewService
{ 
	
	/** 
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliverbillNewService.class);
	

	
	/** 
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;
	
	
	

	
	/** 
	 * 部门查询起始. 
	 */
	private static final int BEGIN_NUM = 0;
	
	/** 
	 * 派送部查询页面大小. 
	 */
	private static final int PAGE_SIZE = 1;
	
	private static final int PAGE_NUM = 100;
	
	/**
	 * 装载率百分比.
	 */
	private static final int LOADRATE = 100;
	
	/** 
	 * 零
	 */
	private static final int ZERO = 0;
	
	
	
	/**
	 * 字符一
	 */
	private static final String STRING_ONE = "1";
	
	/** 
	 * 用于改变正负
	 */
	private static final int MINUS_ONE = -1;
	

	
	/**
	 * 短信模板service接口
	 */
	private ISMSTempleteService sMSTempleteService;
	/**
	 * 发送短信接口
	 */
	private ISMSSendLogService smsSendLogService;
	
	/**
	 * 排版service
	 */
	private ITruckSchedulingTaskService truckSchedulingTaskService;
	
	/** 
	 * 签收
	 */
	private static final String WAYBILL_IS_SIGN = "签收";
	/** 
	 * 未签收
	 */
	private static final String WAYBILL_IS_NOT_SIGN = "未签收";

	/** 
	 *  派送单DAO接口
	 */
	private IDeliverbillNewDao deliverbillNewDao;
	

	/**
	 * 产品定义
	 */
	private IProductService productService;
	
	
	/** 
	 * 派送单明细DAO接口
	 */
	private IDeliverbillNewDetailDao deliverbillNewDetailDao;
	
	/** 
	 * 运单状态数据持久层
	 */
	private IActualFreightDao actualFreightDao;
	
	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	
	/** 
	 * “公司司机”的数据库对应数据访问Service接口
	 */
	private IOwnDriverService ownDriverService;
	
	/**
	 * 注入通知客户DAO
	 */
	private INotifyCustomerDao notifyCustomerDao;
	
	/** 
	 * “外请车司机”的数据库对应数据访问
	 */
	private ILeasedDriverService leasedDriverService;
	
	/** 
	 * 用来提供交互所有关于“车辆（公司、外请）”的数据库对应数据访问复杂的Service接口
	 */
	private IVehicleService vehicleService;
	

	
	/** 
	 * 派送装车任务明细列表
	 */
	private IDeliverLoadTaskService deliverLoadTaskService;
	

	
	/** 
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/** 
	 * 调度解除司机签到、调度解除司机签到 签到注销服务
	 */
	private ISignInAndLogOutService signInAndLogOutService;
	
	/** 
	 * 排班表DAO接口
	 */
	private ITruckScheduleDao truckScheduleDao;
	
	/** 
	 * 营业部 Service接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	
	/**
	 * 派送单状态更新记录表Service 
	 */
	private IDeliverBillVaryStatusService deliverBillVaryStatusService;
	
	
	/** 
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	
	
	/**
	 * 营业部车队对应
	 */
	private ISalesMotorcadeService salesMotorcadeService;
	
	
	
	private ISpecialDeliveryAddressService specialDeliveryAddressService;
	
	
	/**
	 * 定车定区
	 */
	private IRegionalVehicleService regionalVehicleService;
	
	/** 
	 * 查询装车任务
	 */
	private ILoadTaskQueryService loadTaskQueryService;
	
	/**
	 * 获取派送单明细
	 */
	private IDeliverbillDetailDao deliverbillDetailDao;
	
	private IGpsDeliverService gpsDeliverService;
	
	// 注册BigDecimal转换器，否则Bigdecimal转换报错
	static {
		BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
		DateConverter dateConverter = new DateConverter(null);
		ConvertUtils.register(bigDecimalConverter, BigDecimal.class);
		ConvertUtils.register(dateConverter, Date.class);
	}
	
	public void setGpsDeliverService(IGpsDeliverService gpsDeliverService) {
		this.gpsDeliverService = gpsDeliverService;
	}
	
	public void setSalesMotorcadeService(
			ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}

	
	

	/**
	 * Sets 
	 * 		the 
	 * 			handle query outfield service.
	 *
	 * @param handleQueryOutfieldService 
	 * 		the 
	 * 			new handle query outfield service
	 */
	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}


	
	/**
	 * Sets 
	 * 		the 
	 * 			deliverbill detail dao.
	 *
	 * @param deliverbillDetailDao 
	 * 		the 
	 * 			new deliverbill detail dao
	 */
	public void setDeliverbillNewDetailDao(IDeliverbillNewDetailDao deliverbillNewDetailDao)
	{
		this.deliverbillNewDetailDao = deliverbillNewDetailDao;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			actual freight dao.
	 *
	 * @param actualFreightDao 
	 * 		the 
	 * 			new actual freight dao
	 */
	public void setActualFreightDao(IActualFreightDao actualFreightDao)
	{
		this.actualFreightDao = actualFreightDao;
	}

	

	/**
	 * Sets 
	 * 		the 
	 * 			leased driver service.
	 *
	 * @param leasedDriverService 
	 * 		the 
	 * 			new leased driver service
	 */
	public void setLeasedDriverService(ILeasedDriverService leasedDriverService)
	{
		this.leasedDriverService = leasedDriverService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			vehicle service.
	 *
	 * @param vehicleService 
	 * 		the 
	 * 			new vehicle service
	 */
	public void setVehicleService(IVehicleService vehicleService)
	{
		this.vehicleService = vehicleService;
	}

	

	/**
	 * Sets 
	 * 		the 
	 * 			deliver load task service.
	 *
	 * @param deliverLoadTaskService 
	 * 		the 
	 * 			new deliver load task service
	 */
	public void setDeliverLoadTaskService(IDeliverLoadTaskService deliverLoadTaskService)
	{
		this.deliverLoadTaskService = deliverLoadTaskService;
	}

	

	

	/**
	 * Sets 
	 * 		the 
	 * 			org administrative info complex service.
	 *
	 * @param orgAdministrativeInfoComplexService 
	 * 		the 
	 * 			new org administrative info complex service
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService)
	{
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			sign in and log out service.
	 *
	 * @param signInAndLogOutService 
	 * 		the 
	 * 			new sign in and log out service
	 */
	public void setSignInAndLogOutService(ISignInAndLogOutService signInAndLogOutService)
	{
		this.signInAndLogOutService = signInAndLogOutService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 			truck schedule dao.
	 *
	 * @param truckScheduleDao 
	 * 		the 
	 * 			new truck schedule dao
	 */
	public void setTruckScheduleDao(ITruckScheduleDao truckScheduleDao)
	{
		this.truckScheduleDao = truckScheduleDao;
	}


	/**
	 * Sets 
	 * 		the 
	 * 			sale department service.
	 *
	 * @param saleDepartmentService 
	 * 		the 
	 * 			new sale department service
	 */
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService)
	{
		this.saleDepartmentService = saleDepartmentService;
	}

	


	@Resource
	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}
	

	public void setDeliverbillNewDao(IDeliverbillNewDao deliverbillNewDao) {
		this.deliverbillNewDao = deliverbillNewDao;
	}
	
	
	
	
	/**
	 * 根据输入条件，查询符合条件的派送单数量.
	 *
	 * @param deliverbillNewDto 查询条件
	 * 		id		
	 * 派送单ID
	 * 		deliverbillNo	
	 * 派送单编号
	 * 		vehicleNo		
	 * 车辆车牌号
	 * 		driverName		
	 * 司机姓名
	 * 		driverCode		
	 * 司机工号
	 * 		status			
	 * 派送单状态
	 * 		submitTimeBegin	
	 * 查询条件 提交开始时间
	 * 		submitTimeEnd	
	 * 查询条件 提交结束时间
	 * 		createTimeBegin	
	 * 查询条件 创建开始时间
	 * 		createTimeEnd	
	 * 查询条件 创建结束时间
	 * 		createUserName	
	 * 提交人
	 * 		submitTime		
	 * 提交时间
	 * 		operateTime		
	 * 确认时间
	 * 		createOrgName	
	 * 创建部门
	 * 		createOrgCode	
	 * 创建部门编码
	 * 		weightTotal		
	 * 总重量
	 * 		volumeTotal		
	 * 总体积
	 * 		deliverWaybillQty	
	 * 派送成功票数
	 * 		pullbackWaybillQty	
	 * 派送拉回票数
	 * 		orgCode			
	 * 部门Code
	 * 
	 * @return 符合条件的派送单数量
	 * @author 
	 * @date 
	 */
	@Transactional
	public Long queryDeliverbillCountByCondition(DeliverbillNewDto deliverbillNewDto)
	{
		// 当前部门编码
		String orgCode = FossUserContextHelper.getOrgCode();
		if(null != deliverbillNewDto){
			if("P".equals(deliverbillNewDto.getDeliverbillNo()))
			{
				deliverbillNewDto.setDeliverbillNo("");
			}
			//如果派送单号或者运单号不为空
			if(StringUtils.isNotBlank(deliverbillNewDto.getDeliverbillNo())||StringUtils.isNotBlank(deliverbillNewDto.getWaybillNo())){
				deliverbillNewDto.setLoadTimeBegin(null);
				deliverbillNewDto.setLoadTimeEnd(null);
				deliverbillNewDto.setDeliverLargeArea(null);
				deliverbillNewDto.setDeliverSmallArea(null);
			}
			//如果派送单状态为全部，则赋空
			if (StringUtils.isNotBlank(deliverbillNewDto.getStatus())) {
				if (deliverbillNewDto.getStatus().equals(DeliverbillConstants.STATUS_ALL)) {
					deliverbillNewDto.setStatus(null);
				}
			}
		}		
		//若当前部门编码不为空
		if (StringUtils.isNotEmpty(orgCode))
		{	
			
			deliverbillNewDto.setOrgCode(orgCode);
			deliverbillNewDto.setTransferCenter(initOrgRole().getEndStockOrgCode());
			return this.deliverbillNewDao.queryCountByCondition(deliverbillNewDto);
		} else
		{
			return 0L;
		}
	}
	
	/**
	 * 打印派送单到达联查询.
	 *
	 * @param deliverbillNewDto 
	 * 	id					
	 * 派送单ID
	 * 	deliverbillNo		
	 * 派送单编号
	 * 	vehicleNo			
	 * 车辆车牌号	
	 * 	driverName			
	 * 司机姓名
	 * 	driverCode			
	 * 司机工号
	 *	status				
	 * 派送单状态
	 *	submitTimeBegin		
	 * 查询条件提交开始时间
	 * 	submitTimeEnd		
	 * 查询条件提交结束时间
	 * 	createTimeBegin		
	 * 查询条件创建开始时间
	 * 	createTimeEnd		
	 * 查询条件创建结束时间
	 * 	createUserName		
	 * 提交人	
	 * 	submitTime			
	 * 提交时间
	 * 	operateTime			
	 * 确认时间
	 * 	createOrgName		
	 * 创建部门
	 * 	createOrgCode		
	 * 创建部门编码
	 * 	weightTotal			
	 * 总重量
	 * 	volumeTotal			
	 * 总体积
	 * 	deliverWaybillQty	
	 * 派送成功票数
	 * 	pullbackWaybillQty	
	 * 派送拉回票数
	 * 	orgCode				
	 * 部门Code
	 * 
	 * @param start 
	 * 		the start
	 * @param limit 
	 * 		the limit
	 * @return 
	 * 		the list
	 * @author 
	 * @date 
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService#queryDeliverbillList
	 * (com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto,int, int)
	 */
	@Override
	public List<DeliverbillNewDto> queryDeliverbillList(DeliverbillNewDto deliverbillNewDto, int start, int limit)
	{
		// 当前部门编码
		String orgCode = FossUserContextHelper.getOrgCode();
		if("P".equals(deliverbillNewDto.getDeliverbillNo()))
		{
			deliverbillNewDto.setDeliverbillNo("");
		}
		//若传入编码不为空
		if (StringUtils.isNotEmpty(orgCode))
		{	
			deliverbillNewDto.setOrgCode(orgCode);
			deliverbillNewDto.setTransferCenter(initOrgRole().getEndStockOrgCode());
			
			//如果派送单状态为全部，则赋空
			if (StringUtils.isNotBlank(deliverbillNewDto.getStatus())) {
				if (deliverbillNewDto.getStatus().equals(DeliverbillConstants.STATUS_ALL)) {
					deliverbillNewDto.setStatus(null);
				}
			}
			
			//根据输入条件，查询派送单
			List<DeliverbillNewDto> deliverbillList = this.deliverbillNewDao.queryByCondition(deliverbillNewDto, start, limit);
			List<DeliverbillNewDto> deliverbillInfo = new ArrayList<DeliverbillNewDto>();
			
			
			for (DeliverbillNewDto deliverbillDto2 : deliverbillList) {
				  //若司机工号不为空
			      if (StringUtil.isNotEmpty(deliverbillDto2.getDriverCode()))
			      {
			        // 内部司机根据工号查询相关信息
			        DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(deliverbillDto2.getDriverCode());
			        //用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
			        if (driverAssociationDto != null)
			        {
			          // 司机电话
			        deliverbillDto2.setDriverTel(driverAssociationDto.getDriverPhone());
			        } else
			        {
			          // 外请司机根据车牌号进行查询
			          List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbillDto2.getVehicleNo());
			          
			          if (CollectionUtils.isNotEmpty(driverAssociationDtos))
			          {
				        	//司机姓名
				        	deliverbillDto2.setDriverName(driverAssociationDtos.get(0).getDriverName());
				        	// 司机电话
			        	    deliverbillDto2.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
			          }
			        }

				    
			        //如果车牌号不为空
			      } else if (StringUtil.isNotEmpty(deliverbillDto2.getVehicleNo()))
			      {
			        // 外请司机根据车牌号进行查询
			        List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbillDto2.getVehicleNo());
			        
			        if (CollectionUtils.isNotEmpty(driverAssociationDtos))
			        {
			        	//司机姓名
			        	deliverbillDto2.setDriverName(driverAssociationDtos.get(0).getDriverName());
			        	// 司机电话
			        	deliverbillDto2.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
			        	
			        }
			      }
			      
			      
	/*		      //查询 所属区域
			      RegionVehicleEntity  regionVehicle =new RegionVehicleEntity();
			      regionVehicle.setVehicleNo(deliverbillDto2.getVehicleNo());
			      List<RegionVehicleInfoDto> regionVehicleInfo= regionalVehicleService.queryRegionalVehicles(regionVehicle,limit,start);
			      if (regionVehicleInfo!=null) {
			    	  deliverbillDto2.setReceiveCustomerDistCode(regionVehicleInfo.get(0).getRegionName());
			      }*/
			      //查询当前车辆的车型、班次、装载率
			      if (deliverbillDto2.getDeliverDate()!=null) {
			    	  queryRightCount(deliverbillDto2);
			      }
			     
			      deliverbillInfo.add(deliverbillDto2);
			}
			return deliverbillInfo;
		} else
		{
			//返回空
			return null;
		}
	}
	
	
	
	/**
	 * 查询派送单序列.
	 *
	 * @return 
	 * the string
	 * @author 
	 * @date 
	 */
	@Override
	public String querySequence() {
		return deliverbillNewDao.querySequence();
	}
	
	
	
	/**
	 * 取消已保存的派送单.
	 *
	 * @param deliverbillId t
	 * he deliverbill id
	 * @param deliverbillNo 
	 * the deliverbill no
	 * @return 
	 * the int
	 * @author 
	 * @date 
	 */
	@Override
	public int cancelDeliverbillForSaved(String deliverbillId, String deliverbillNo)
	{			
		
		//PDA-1588  当派送单非已保存 已装车  不能取消状态
		DeliverbillEntity deliverbillEntity = deliverbillNewDao.queryById(deliverbillId);	
		
		if(deliverbillEntity!= null && (DeliverbillConstants.STATUS_ASSIGNED.equals(deliverbillEntity.getStatus())
				|| DeliverbillConstants.STATUS_LOADING.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbillEntity.getStatus())))
		{
			throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("deliverbillId", deliverbillId);

		// 根据派送单ID查找派送单明细列表
		List<DeliverbillDetailEntity> deliverbillDetailList = this.deliverbillNewDetailDao.queryByDeliverbillIdForPrint(map, 0, 9999);

		if (deliverbillDetailList.size() > 0) {
			for (DeliverbillDetailEntity deliverbillDetail : deliverbillDetailList) {
				if (deliverbillDetail.getArrangeGoodsQty()!=null) {
					// 调整运单的可排单数量
					this.updateActualFreightArrangeGoodsQty(deliverbillDetail.getWaybillNo(), deliverbillDetail.getArrangeGoodsQty() * DeliverbillNewService.MINUS_ONE);
				}
			}
		}else{
			throw new DeliverbillException(DeliverbillException.DELIVERBILLDELETE_NOTFOUND);
		}
		
		// 更新派送单状态为“已取消”
		DeliverbillEntity deliverbill = new DeliverbillEntity();
		deliverbill.setId(deliverbillId);
		deliverbill.setStatus(DeliverbillConstants.STATUS_CANCELED);
		//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
		if (StringUtils.isNotBlank(deliverbill.getDeliverbillNo()) && StringUtils.isNotBlank(deliverbill.getStatus()) ) {
			// 获取当前用户
			UserEntity currentUser = FossUserContext.getCurrentUser();
			DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
			deliverBillVary.setDeliverBillNo(deliverbill.getDeliverbillNo());//派送单号
			deliverBillVary.setDeliverBillStatus(deliverbill.getStatus());//派送单状态
			deliverBillVary.setOperatorName(currentUser.getEmployee().getEmpName());//操作人
			deliverBillVary.setOperatorCode(currentUser.getEmployee().getEmpCode());//操作人编码
			deliverBillVary.setOperateOrgName(FossUserContext.getCurrentDeptName());//操作部门
			deliverBillVary.setOperateOrgCode(FossUserContext.getCurrentDeptCode());//操作部门编码
			//添加纪录
			deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
		}
		return  this.deliverbillNewDao.updateDeliverBill(deliverbill);	
	}
	
	/**
	 * 取消预派送单.
	 *
	 * @param deliverbillId 
	 * 派送单ID
	 * @param deliverbillNo 
	 * 派送单编号
	 * @return 若成功，
	 * 返回大于0；
	 * 否则返回0
	 * @author 
	 * @date 
	 */
	@Override
	@Transactional
	public int cancelDeliverbill(String deliverbillId, String deliverbillNo)
	{
		//PDA-1588  当派送单非已保存 已装车  不能取消状态
		DeliverbillEntity deliverbillEntity = deliverbillNewDao.queryById(deliverbillId);	
		
		if(deliverbillEntity!= null && (DeliverbillConstants.STATUS_ASSIGNED.equals(deliverbillEntity.getStatus())
				|| DeliverbillConstants.STATUS_LOADING.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(deliverbillEntity.getStatus()) 
				|| DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(deliverbillEntity.getStatus())))
		{
			throw new DeliverbillException(DeliverbillException.DELIVERBILLSTATE_CANT_OPERATE);
		}
		
		// SUC-459-确认_取消预派送单 SR4 已装车和已确认状态下的预派送单取消后生成卸车任务
		// 现已更新为退回装车任务，并将装车货物重新入库。
		int i = this.deliverLoadTaskService.takeBackDeliverBill(deliverbillNo);
		if (i != ZERO) {
			// 更新派送单状态为“已取消”
			DeliverbillEntity deliverbill = new DeliverbillEntity();
			deliverbill.setId(deliverbillId);
			deliverbill.setStatus(DeliverbillConstants.STATUS_CANCELED);
			deliverbill = this.deliverbillNewDao.update(deliverbill);

			if (deliverbill != null) {
				//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
				if (StringUtils.isNotBlank(deliverbill.getDeliverbillNo()) && StringUtils.isNotBlank(deliverbill.getStatus()) ) {
					// 获取当前用户
					UserEntity currentUser = FossUserContext.getCurrentUser();
					DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
					deliverBillVary.setDeliverBillNo(deliverbill.getDeliverbillNo());//派送单号
					deliverBillVary.setDeliverBillStatus(deliverbill.getStatus());//派送单状态
					deliverBillVary.setOperatorName(currentUser.getEmployee().getEmpName());//操作人
					deliverBillVary.setOperatorCode(currentUser.getEmployee().getEmpCode());//操作人编码
					deliverBillVary.setOperateOrgName(FossUserContext.getCurrentDeptName());//操作部门
					deliverBillVary.setOperateOrgCode(FossUserContext.getCurrentDeptCode());//操作部门编码
					//添加纪录
					deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
				}
				// SUC-459-确认_取消预派送单 SR4 已装车和已确认状态下的预派送单取消后生成卸车任务，现已更新为退回装车任务。
				// 更新已排单运单的可排单数量
				
				Map<Object,Object> map = new HashMap<Object,Object>();
				map.put("deliverbillId", deliverbillId);
				
				List<DeliverbillDetailEntity> deliverbillDetailList = this.deliverbillNewDetailDao.queryByDeliverbillIdForPrint(map,0, 99999);
				if (deliverbillDetailList.size() > 0) {
					for (DeliverbillDetailEntity deliverbillDetail : deliverbillDetailList) {
						if (deliverbillDetail.getArrangeGoodsQty()!=null) {
							// 调整运单的可排单数量
							this.updateActualFreightArrangeGoodsQty(deliverbillDetail.getWaybillNo(), deliverbillDetail.getArrangeGoodsQty() * DeliverbillNewService.MINUS_ONE);
						}
					}
				}else{
					throw new DeliverbillException(DeliverbillException.DELIVERBILLDELETE_NOTFOUND);
				}
			} else {
				throw new DeliverbillException(DeliverbillException.CANCELDELIVERBILL_ERROR);
			}
		}
		return i;
	}
	
	
	
	/**
	 * 更新运单已排单件数.
	 *
	 * @param waybillNo 
	 * 运单号
	 * @param arrangeGoodsQtyMargin 
	 * 排单件数差
	 * @return 
	 * 更新条数
	 * @author
	 * @date 
	 */
	private int updateActualFreightArrangeGoodsQty(String waybillNo, int arrangeGoodsQtyMargin){
		ActualFreightEntity actualFreightEntity = this.actualFreightDao.queryByWaybillNo(waybillNo);

		if (actualFreightEntity != null) {
			String id = actualFreightEntity.getId();
			int newArrangeGoodsQty = actualFreightEntity.getArrangeGoodsQty() + arrangeGoodsQtyMargin;
			
			// 需要保证已排单件数大于0，且小于开单件数
			newArrangeGoodsQty = newArrangeGoodsQty > actualFreightEntity.getGoodsQty() ? actualFreightEntity.getGoodsQty() : newArrangeGoodsQty;
			newArrangeGoodsQty = newArrangeGoodsQty > 0 ? newArrangeGoodsQty : 0;

			actualFreightEntity = new ActualFreightEntity();
			actualFreightEntity.setId(id);
			actualFreightEntity.setArrangeGoodsQty(newArrangeGoodsQty);

			return this.actualFreightDao.updateByPrimaryKeySelective(actualFreightEntity);
		} else {
			throw new DeliverbillException(DeliverbillException.ACTUALFREIGHT_NOT_FOUND_ERROR);
		}
		
	}
	
	
	/**
	 * 根据派送单ID查找派送单信息.
	 *
	 * @param id 
	 * 		the id
	 * @return 
	 * 		the deliverbill entity
	 * @author 
	 * @date 
	 */
	@Override
	public DeliverbillEntity queryDeliverbill(String id)
	{
		//派送单实体
		DeliverbillEntity entity = null;
		//根据ID查找派送单
		entity = this.deliverbillNewDao.queryById(id);
		//如果实体不为空
		if (entity != null)
		{
			// 派送部 -取派送单创建部门
			entity.setDeliveryDepartment(entity.getCreateOrgName());
			// 根据新旧派送单使用不同的装载率计算方法
			if (StringUtil.isNotBlank(entity.getCreateType()) && entity.getCreateType().equals("Y")) {
			   if (entity.getDeliverDate()!=null) {
				   DeliverbillNewDto deliverbillNewDto=new DeliverbillNewDto();
				    deliverbillNewDto.setVehicleNo(entity.getVehicleNo());
				    deliverbillNewDto.setDeliverDate(entity.getDeliverDate());
				    deliverbillNewDto.setVolumeTotal(entity.getVolumeTotal());
				    deliverbillNewDto.setWeightTotal(entity.getWeightTotal());
					queryRightCount(deliverbillNewDto);
					entity.setLoadingRate(deliverbillNewDto.getLoadingRate());
			   }
			}else{
				setLoadingRate(entity);
			}
			
			
			// 设置派送单上的司机相关信息
			setDriverInfo(entity);
		} else
		{

		}
		//返回实体
		return entity;
	}
	
	
	/**
	 * 根据派送单ID查找派送单明细列表大小.
	 *
	 * @param deliverbillId 派送单ID
	 * @return 派送单明细列表大小
	 * 派送单明细列表大小
	 * @author 
	 * @date 
	 */
	@Transactional
	public Long queryDeliverbillDetailCountByDeliverbillId(String deliverbillId)
	{
		return this.deliverbillNewDetailDao.queryCountByDeliverbillId(deliverbillId);
	}
	
	
	/**
	 * 根据派送单ID查找派送单明细列表.
	 *
	 * @param deliverbillId 
	 * 		派送单ID
	 * @param start 
	 * 		the start
	 * @param limit 
	 * 		the limit
	 * @return 
	 * 		the list
	 * @author 
	 * @date 
	 */
	@Override
	public List<DeliverbillDetailEntity> queryDeliverbillDetailList(String deliverbillId, int start, int limit)
	{
		// 获取当前部门
		String currOrgCode = FossUserContextHelper.getOrgCode();
		String areaCode = null;
		//若当前部门编码 不为空时
		if (!StringUtils.isEmpty(currOrgCode))
		{	
		//获取当前用户设置的当前部门
		OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
		String salesDepartment = org.getSalesDepartment();

		// 若当前部门为营业部，则取最终配载部门为当前部门的运单进行排单
		if (FossConstants.YES.equals(salesDepartment))
		{
			// 添加库存外场、库区默认查询条件
			List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currOrgCode);
			if (CollectionUtils.isNotEmpty(list)) {
				areaCode = list.get(1);
			}
		} else
			{
				// 查询排单服务外场
				OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(currOrgCode);
				String orgCode1 = null;
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
					}
				}
				// 添加库存外场、库区默认查询条件
				List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode1);
				if (CollectionUtils.isNotEmpty(list)) {
					 //传入库区code
					 areaCode = list.get(1);
				}
			}
		}
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("deliverbillId", deliverbillId);
		map.put("areaCode", areaCode);
		
		//根据派送单ID查找派送单明细列表
		List<DeliverbillDetailEntity> entitys = this.deliverbillNewDetailDao.queryByDeliverbillId(map, start, limit);
		//如果集合不为空
		if (CollectionUtils.isNotEmpty(entitys))
		{	//循环派送单明细列表
			for (DeliverbillDetailEntity entity : entitys)
			{	
				//包装
				entity.setGoodsPackage(entity.getGoodsPackage());
				//返单类别
				entity.setSingleSignStatus(WaybillConstants.NOT_RETURN_BILL.equals(entity.getReturnBillType()) ? "否" : "是");
				// 货物信息
				entity.setGoodsInfo(joinString(entity.getWeight(), entity.getGoodsVolumeTotal(), entity.getArrangeGoodsQty()));
				// 客户信息
				entity.setConsigneeInfo(joinString(entity.getConsignee(), entity.getConsigneeContact(), entity.getConsigneeAddress()));
			}
		} else
		{
			
		}
		//返回派送单明细列表
		return entitys;
	}


	/**
	 * 设置装载率.
	 *
	 * @param entity 
	 * 	deliverbillNo 	
	 * 派送单号
	 * 	vehicleNo		
	 * 车牌号	
	 * 	driverCode		
	 * 司机工号
	 * 	driverName		
	 * 司机姓名
	 * 	waybillQtyTotal	
	 * 总票数
	 *  goodsQtyTotal	
	 *  总件数
	 * 	payAmountTotal	
	 * 总到付金额
	 * 	weightTotal		
	 * 总重量
	 * 	volumeTotal		
	 * 总体积
	 * 	createUserName	
	 * 创建人
	 * 	createUserCode	
	 * 创建人编码
	 * 	submitTime		
	 * 创建时间(提交时间)
	 * 	tOptTruckDepartId	
	 * 车辆放行ID
	 * 	status			
	 * 状态
	 * 	createOrgName	
	 * 创建部门
	 * 	createOrgCode	
	 * 创建部门编码
	 * 	operator		
	 * 操作人
	 * 	operatorCode	
	 * 操作人编码
	 * 	operateOrgName	
	 * 操作部门
	 * 	operateOrgCode	
	 * 操作部门编码
	 * 	operateTime		
	 * 操作时间(确认时间)
	 * 	fastWaybillQty	
	 * 卡货票数
	 * 	transferCenter	
	 * 车队服务外场
	 * 	currencyCode	
	 * 币种
	 * 	deliveryDepartment	
	 * 派送部
	 * 	driverTel		
	 * 司机电话号码
	 * 	motorcade		
	 * 车队
	 * 	loadingRate		
	 * 装载率(重量/体积)
	 * 
	 * @author 
	 * @date 
	 */
	private void setLoadingRate(DeliverbillEntity entity)
	{
		try
		{
			// 装载率(重量/体积)
			VehicleAssociationDto vehicleAssociationDto = this.vehicleService.queryVehicleAssociationDtoByVehicleNo(entity.getVehicleNo());
			//若装载率(重量/体积)不为空
			if (vehicleAssociationDto != null)
			{
				String vehicleLoad = null;
				String vehicleVolume = null;
				// 装载率(重量)
				BigDecimal vehicleLoadRate = BigDecimalOperationUtil.div(entity.getWeightTotal(), vehicleAssociationDto.getVehicleDeadLoad()==null?BigDecimal.ZERO:vehicleAssociationDto.getVehicleDeadLoad().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_1000)),2);
				//如果装载率(重量)与零相等的话，
				if(BigDecimalOperationUtil.compare(vehicleLoadRate, new BigDecimal(ZERO)))
				{	
					//还没有开始装载
					vehicleLoad = "0.00%";
				}
				else
				{
					vehicleLoad = (vehicleLoadRate.multiply(new BigDecimal(LOADRATE))).toString()+"%";
				}
				// 装载率(体积)
				BigDecimal vehicleVolumeRate = BigDecimalOperationUtil.div(entity.getVolumeTotal(), vehicleAssociationDto.getVehicleSelfVolume()==null?BigDecimal.ZERO:vehicleAssociationDto.getVehicleSelfVolume(),2);
				
				//如果装载率(体积)与零相等的话，
				if(BigDecimalOperationUtil.compare(vehicleVolumeRate, new BigDecimal(ZERO)))
				{	
					//还没有开始装载
					vehicleVolume = "0.00%";
				}
				else
				{
					vehicleVolume = (vehicleVolumeRate.multiply(new BigDecimal(LOADRATE))).toString()+"%";
				}
				//设置装载率(重量/体积)
				entity.setLoadingRate(joinString(vehicleLoad, vehicleVolume));
			}
		} catch (LeasedDriverException le)
		{	
			//日志记录
			LOGGER.error("error", le);
		}
	}
	
	/**
	 * 计算装载率、车型、班次
	 * @param deliverbillDto2
	 */
	public void queryRightCount(DeliverbillNewDto deliverbillDto2){
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		SimpleTruckScheduleDto truckDto = truckSchedulingTaskService.queryTruckByVehicle(deliverbillDto2.getVehicleNo(), sdf.format(deliverbillDto2.getDeliverDate()));
		if (null != truckDto) {
				//净空
				BigDecimal  selfVolume = truckDto.getSelfVolume() == null ? BigDecimal.ZERO : truckDto.getSelfVolume();
				//载重
				BigDecimal deadLoad = truckDto.getDeadLoad() == null ? BigDecimal.ZERO : truckDto.getDeadLoad();
				//带货(方)
				BigDecimal expectedBringVolume = truckDto.getExpectedBringVolume() == null ? BigDecimal.ZERO : truckDto.getExpectedBringVolume();
				//是否带货
				String isBringExpress = truckDto.getIsBringExpress();
				
				
				//重量装载率 （排单重量/车辆载重(以千克计算)）%
				BigDecimal  weightRateNum = BigDecimalOperationUtil.div(deliverbillDto2.getWeightTotal(), deadLoad.multiply(new BigDecimal(NumberConstants.NUMBER_1000)), 2);
				String weightRate = (BigDecimalOperationUtil.mul(weightRateNum, new BigDecimal(NumberConstants.NUMBER_100), 0)).toString() + "%";
				//体积装载率 分带货 or 不带货 
				String volumRate = "";
				if ("Y".equals(isBringExpress)) { //带货  装载率（体积）=（总体积+带快递货体积）/净空*100%
					BigDecimal remainderVolumn = deliverbillDto2.getVolumeTotal()==null?BigDecimal.ZERO:deliverbillDto2.getVolumeTotal().add(expectedBringVolume);
					BigDecimal volumRateNum = BigDecimalOperationUtil.div(remainderVolumn, selfVolume, 2);
					volumRate = (BigDecimalOperationUtil.mul(volumRateNum, new BigDecimal(NumberConstants.NUMBER_100), 0)).toString() + "%";
				} else { //非带货 （排单体积/车辆净空）%
					BigDecimal volumRateNum = BigDecimalOperationUtil.div(deliverbillDto2.getVolumeTotal(), selfVolume, 2);
					volumRate = (BigDecimalOperationUtil.mul(volumRateNum, new BigDecimal(NumberConstants.NUMBER_100), 0)).toString() + "%";
				}
				//nominalRate  额定净空（方）/额定载重（吨） 下面 /是分隔符，不是值比
				deliverbillDto2.setFrequencyNo(truckDto.getFrequencyNo());//班次
				deliverbillDto2.setTruckModel(truckDto.getTruckModelValue());
				//loadRate 装载率(体积和重量)  下面 /是分隔符，不是值比
				deliverbillDto2.setLoadingRate(volumRate + " / " + weightRate);
		}else{
			deliverbillDto2.setLoadingRate("0%/0%");
		}
	}
	
	
	/**
	 * 拼接页面信息.
	 *
	 * @param objects 
	 * the objects
	 * @return the string
	 * @author 
	 * @date 
	 */
	private String joinString(Object... objects)
	{
		StringBuffer sb = new StringBuffer();
		for (Object o : objects)
		{
			if (o != null && StringUtil.isNotEmpty(o.toString()))
			{
				sb.append(o.toString()).append(DeliverbillConstants.SPLIT_CHAR);
			}
		}
		return sb.length() > 0 ? sb.toString().substring(0, sb.length() - 1) : sb.toString();
	}
	
	
	
	/**
	 * 设置派送单上的司机相关信息.
	 *
	 * @param entity 
	 * 	deliverbillNo 	
	 * 派送单号
	 * 	vehicleNo		
	 * 车牌号	
	 * 	driverCode		
	 * 司机工号
	 * 	driverName		
	 * 司机姓名
	 * 	waybillQtyTotal	
	 * 总票数
	 *  goodsQtyTotal	
	 *  总件数
	 * 	payAmountTotal	
	 * 总到付金额
	 * 	weightTotal		
	 * 总重量
	 * 	volumeTotal		
	 * 总体积
	 * 	createUserName	
	 * 创建人
	 * 	createUserCode	
	 * 创建人编码
	 * 	submitTime		
	 * 创建时间(提交时间)
	 * 	tOptTruckDepartId	
	 * 车辆放行ID
	 * 	status			
	 * 状态
	 * 	createOrgName	
	 * 创建部门
	 * 	createOrgCode	
	 * 创建部门编码
	 * 	operator		
	 * 操作人
	 * 	operatorCode	
	 * 操作人编码
	 * 	operateOrgName	
	 * 操作部门
	 * 	operateOrgCode	
	 * 操作部门编码
	 * 	operateTime		
	 * 操作时间(确认时间)
	 * 	fastWaybillQty	
	 * 卡货票数
	 * 	transferCenter	
	 * 车队服务外场
	 * 	currencyCode	
	 * 币种
	 * 	deliveryDepartment	
	 * 派送部
	 * 	driverTel		
	 * 司机电话号码
	 * 	motorcade		
	 * 车队
	 * 	loadingRate		
	 * 装载率(重量/体积)
	 * 
	 * @author 
	 * @date 
	 */
	private void setDriverInfo(DeliverbillEntity entity)
	{
		try
		{	//若司机工号不为空
			if (StringUtil.isNotEmpty(entity.getDriverCode()))
			{
				// 内部司机根据工号查询相关信息
				DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(entity.getDriverCode());
				//用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
				if (driverAssociationDto != null)
				{
					// 司机电话
					entity.setDriverTel(driverAssociationDto.getDriverPhone());
					// 所属车队
					entity.setMotorcade(driverAssociationDto.getDriverOrganizationName());
				} else
				{
					// 外请司机根据车牌号进行查询
					List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(entity.getVehicleNo());
					
					if (CollectionUtils.isNotEmpty(driverAssociationDtos))
					{
			        	//司机姓名
						entity.setDriverName(driverAssociationDtos.get(0).getDriverName());
						// 司机电话
						entity.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
					}
				}
				//如果车牌号不为空
			} else if (StringUtil.isNotEmpty(entity.getVehicleNo()))
			{
				// 外请司机根据车牌号进行查询
				List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(entity.getVehicleNo());
				
				if (CollectionUtils.isNotEmpty(driverAssociationDtos))
				{
		        	//司机姓名
					entity.setDriverName(driverAssociationDtos.get(0).getDriverName());
					// 司机电话
					entity.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
				}
			}
		} catch (OwnDriverException oe)
		{	
			//日志记录
			LOGGER.error("error", oe);
			return;
		} catch (LeasedDriverException le)
		{	
			//日志记录
			LOGGER.error("error", le);
			return;
		}
	}
	
	
	/**
	 * 根据派送单ID查找已生成到达联的派送明细列表.
	 *
	 * @param deliverbillId 
	 * 派送单ID
	 * @return the 
	 * list
	 * @author
	 * @date 
	 */
	@Override
	public List<DeliverbillDetailEntity> queryDeliverbillArrivesheetList(String deliverbillId, String status)
	{
		List<DeliverbillDetailEntity> detailList = null;
		DeliverbillDetailDto deliverbillDetailDto = null;
		if (DeliverbillConstants.STATUS_CONFIRMED.equals(status) 
			|| DeliverbillConstants.STATUS_PDA_DOWNLOADED.equals(status)) {
			deliverbillDetailDto = new DeliverbillDetailDto();
			deliverbillDetailDto.setDeliverbillId(deliverbillId);
			detailList= deliverbillNewDetailDao.queryArrivesheetListByDeliverbillById(deliverbillDetailDto);
		}else if(DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED.equals(status)){
			return detailList;
		}else{
			deliverbillDetailDto = new DeliverbillDetailDto();
			deliverbillDetailDto.setDeliverbillId(deliverbillId);
			deliverbillDetailDto.setArrivesheetNo(DeliverbillConstants.NULL_ARRIVESHEET_NO);
			detailList= deliverbillNewDetailDao.queryArrivesheetListByDeliverbillId(deliverbillDetailDto);
		}
		
		/*for (int i = 0; i < detailList.size(); i++) {
			if(StringUtils.isEmpty(detailList.get(i).getArrivesheetNo()))
			{
				detailList.remove(i);
				i--;
			}
		} */
		return detailList;
	}
	
	
	/**
	 * 短信通知司机
	 */
	@Override
	@Transactional
	public void isSendSMSToDriver(String deliverbillId){
		
		//根据Id获取派送单
		DeliverbillEntity deliverbill = deliverbillNewDao.queryById(deliverbillId);
		if (deliverbill==null) {
			LOGGER.info("没有找到对应派送信息！");
			throw new NotifyCustomerException("没有找到对应派送信息！");
		}
		  //若司机工号不为空
	      if (StringUtil.isNotEmpty(deliverbill.getDriverCode())){
	        // 内部司机根据工号查询相关信息
	        DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(deliverbill.getDriverCode());
	        //用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
	        if (driverAssociationDto != null) {
	        	// 司机电话
	        	deliverbill.setDriverTel(driverAssociationDto.getDriverPhone());
	        } else{
	          // 外请司机根据车牌号进行查询
	          List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbill.getVehicleNo());
	          if (CollectionUtils.isNotEmpty(driverAssociationDtos)){
		        //司机姓名
	        	deliverbill.setDriverName(driverAssociationDtos.get(0).getDriverName());
		        // 司机电话
	        	deliverbill.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
	          }
	        }
	      //如果车牌号不为空
	      } else if (StringUtil.isNotEmpty(deliverbill.getVehicleNo())){
	        // 外请司机根据车牌号进行查询
	        List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbill.getVehicleNo());
	        if (CollectionUtils.isNotEmpty(driverAssociationDtos)){
	        	//司机姓名
	        	deliverbill.setDriverName(driverAssociationDtos.get(0).getDriverName());
	        	// 司机电话
	        	deliverbill.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
	        }
	      }
		
	    DeliverbillSMSDto deliverbillSMS =new DeliverbillSMSDto();
		if (deliverbill.getDriverTel() !=null) {
			// 派送单编号
			deliverbillSMS.setDeliverbillNo(deliverbill.getDeliverbillNo());
			// 通知类型
//			deliverbillSMS.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
			// 接收人姓名
			deliverbillSMS.setConsignee(deliverbill.getDriverName());
			// 手机号
			deliverbillSMS.setMobile(deliverbill.getDriverTel());
			// 车牌号
			deliverbillSMS.setVehicleNo(deliverbill.getVehicleNo());
			// 总票数
			deliverbillSMS.setTotalGoodsQty(deliverbill.getWaybillQtyTotal()!=null?deliverbill.getWaybillQtyTotal().toString():"*");
			// 到付金额
			deliverbillSMS.setPayAmountTotal(deliverbill.getPayAmountTotal());
			// 操作时间
			deliverbillSMS.setOperateTime(new Date());
			// 模块名称
			deliverbillSMS.setModuleName(DeliverbillConstants.SMS_CODE_PKP_NOTIFY_DRIVER);
			
			//获取特殊地址数量、总件数、送货日
			Integer specialAddressNum=0;
			Integer waybillGoodsQtyNum=0;
			Integer deliverTime = 0;
			List<DeliverbillDetailEntity> deliverbillDetaills= deliverbillNewDetailDao.queryDeliverbillDetailEntityById(deliverbill.getId());
			if (deliverbillDetaills.size() > 0) {
				for (DeliverbillDetailEntity deliverbillDetail : deliverbillDetaills) {
					//特殊地址匹配
					if (specialDeliveryAddressService.selectSpecialDeliveryAddress(deliverbillDetail.getConsigneeAddress()) !=null) {
						specialAddressNum+=1;
					}
					//货物件数计算
					if (deliverbillDetail.getWaybillGoodsQty()!=null) {
						waybillGoodsQtyNum+=deliverbillDetail.getWaybillGoodsQty();
					}
				}
				//获取送货日
				if ( deliverbillDetaills.get(0).getDeliverDate()!=null) {
					Calendar cal = Calendar. getInstance(); 
					cal.setTime(deliverbillDetaills.get(0).getDeliverDate());
					deliverTime=cal.get(Calendar.DATE);
				}
			}
			deliverbillSMS.setSpecialAddressNum(specialAddressNum.toString());
			deliverbillSMS.setWaybillDoodsQty(waybillGoodsQtyNum.toString());
			deliverbillSMS.setDeliveryDate(deliverTime.toString());
			
			//派送单状态
			deliverbillSMS.setStatus(DictUtil.rendererSubmitToDisplay(deliverbill.getStatus(), DictionaryConstants.PKP_DELIVERBILL_STATUS));
			
			//设置装载率 
			if (deliverbill != null)
			{
				// 根据新旧派送单使用不同的装载率计算方法
				if (StringUtil.isNotBlank(deliverbill.getCreateType()) && deliverbill.getCreateType().equals("Y")) {
				    if (deliverbill.getDeliverDate()!=null) {
				    	DeliverbillNewDto deliverbillNewDto=new DeliverbillNewDto();
					    deliverbillNewDto.setVehicleNo(deliverbill.getVehicleNo());
					    deliverbillNewDto.setDeliverDate(deliverbill.getDeliverDate());
					    deliverbillNewDto.setVolumeTotal(deliverbill.getVolumeTotal());
					    deliverbillNewDto.setWeightTotal(deliverbill.getWeightTotal());
						queryRightCount(deliverbillNewDto);
						deliverbillSMS.setLoadingRate(deliverbillNewDto.getLoadingRate());
					}
				}else{
					setLoadingRate(deliverbill);
					deliverbillSMS.setLoadingRate(deliverbill.getLoadingRate());
				}
			}
		    
		    //获取当前操作人、部门
			UserEntity currentUser = FossUserContext.getCurrentUser();
			OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();
			if (currentUser != null){
				EmployeeEntity employee = currentUser.getEmployee();
				if (employee != null){
					// 操作人
					deliverbillSMS.setOperator(employee.getEmpName());
					// 操作人编码
					deliverbillSMS.setOperatorNo(employee.getEmpCode());
				}
			}
			if (currentOrg != null){
				// 操作部门编码
				deliverbillSMS.setOperateOrgCode(currentOrg.getCode());
				// 操作部门
				deliverbillSMS.setOperateOrgName(currentOrg.getName());
			}
			
			//字段验证
			validateDeliverbillSMS(deliverbillSMS);
			
			
			String voiceConten="";
			
			// 模版参数
		   	SmsParamDto smsParamDto = new SmsParamDto();
		   	// 语音编码
		   	smsParamDto.setSmsCode(DeliverbillConstants.SMS_CODE_PKP_NOTIFY_DRIVER);
		   	// 部门编码
		   	smsParamDto.setOrgCode(deliverbillSMS.getOperateOrgCode());
		   	// 参数Map
		 	smsParamDto.setMap(this.getSmsParam(deliverbillSMS));
		 	try {
				voiceConten = sMSTempleteService.querySmsByParam(smsParamDto);
			} catch (SMSTempleteException e) {
				LOGGER.error(e.getMessage(), e);
				throw new NotifyCustomerException(NotifyCustomerException.MESSAGE_EMPTY, e);
			}
			LOGGER.info("语音信内容：{}", voiceConten);
			LOGGER.info("语音模版CODE:{}", DeliverbillConstants.SMS_CODE_PKP_NOTIFY_DRIVER);
			deliverbillSMS.setNoticeContent(voiceConten);
		}else{
			LOGGER.info("手机号码不能为空");
			throw new NotifyCustomerException("手机号码不能为空");
		}
		
		try {
			//发送短信
			sendSms(deliverbillSMS);
			//更新派送单通知司机状态
			updateDeliverbillisSendSMS(deliverbillId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new NotifyCustomerException(NotifyCustomerException.SMS_FAIL, e);
		}
		
		
	}
	
	/**
	 * 短信通知客户
	 * @author 306548
	 */
	public void isSendSMSToConsumers(String deliverbillId,String taskNo ){
		if(StringUtil.isNotEmpty(deliverbillId)){
			//根据Id获取派送单明细
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("deliverbillId", deliverbillId);
			List<DeliverbillDetailEntity> deliverbillDetailList =  deliverbillDetailDao.queryByDeliverbillIdForPrint(map, 0, 9999);
			for (DeliverbillDetailEntity deliverbillDetail : deliverbillDetailList) {
				LOGGER.info("====================收获联系人=======123========"+deliverbillDetail.getReceiveCustomerContact());
				// 根据Id获取派送单
				DeliverbillEntity deliverbill = deliverbillNewDao.queryById(deliverbillId);
				if (deliverbill == null) {
					LOGGER.info("没有找到对应派送信息！");
					throw new NotifyCustomerException("没有找到对应派送信息！");
				}
				// 若司机工号不为空
				if (StringUtil.isNotEmpty(deliverbill.getDriverCode())) {
					// 内部司机根据工号查询相关信息
					DriverAssociationDto driverAssociationDto = ownDriverService
							.queryOwnDriverAssociationDtoByDriverCode(deliverbill.getDriverCode());
					// 用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
					if (null !=driverAssociationDto) {
						// 司机姓名
						deliverbill.setDriverName(driverAssociationDto.getDriverName());
						// 司机电话
						deliverbill.setDriverTel(driverAssociationDto.getDriverPhone());
					} else {
						// 外请司机根据车牌号进行查询
						List<DriverAssociationDto> driverAssociationDtos = leasedDriverService
								.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbill.getVehicleNo());
						if (CollectionUtils.isNotEmpty(driverAssociationDtos)) {
							// 司机姓名
							deliverbill.setDriverName(driverAssociationDtos.get(0).getDriverName());
							// 司机电话
							deliverbill.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
						}
					}
					// 如果车牌号不为空
				} else if (StringUtil.isNotEmpty(deliverbill.getVehicleNo())) {
					// 外请司机根据车牌号进行查询
					List<DriverAssociationDto> driverAssociationDtos = leasedDriverService
							.queryLesasedDriverAssociationDtoByTruckVehicleNo(deliverbill.getVehicleNo());
					if (CollectionUtils.isNotEmpty(driverAssociationDtos)) {
						// 司机姓名
						deliverbill.setDriverName(driverAssociationDtos.get(0).getDriverName());
						// 司机电话
						deliverbill.setDriverTel(driverAssociationDtos.get(0).getDriverPhone());
					}
				}
				LOGGER.info("============装车任务号======="+taskNo);
				//装车件数（调用中转的接口）,根据装车任务号查询装车任务明细
				if(null !=taskNo){
					List<LoadWayBillDetailDto> loadWaybillDetailList = this.loadTaskQueryService.queryLoadWayBillByTaskNo(taskNo);
					if (CollectionUtils.isNotEmpty(loadWaybillDetailList)){
						for (LoadWayBillDetailDto loadWaybillDetail : loadWaybillDetailList){
							LOGGER.info("============已装车数量======="+loadWaybillDetail.getLoadQty());
							LOGGER.info("============开单数量========"+deliverbillDetail.getWaybillGoodsQty());
							LOGGER.info("============状态==========="+deliverbill.getStatus());
							// 派送单的状态为“已装车”&& DeliverbillConstants.STATUS_LOADED.equals(deliverbill.getStatus())
							//开单件数等于已装车数发送短信
							if (deliverbillDetail.getWaybillGoodsQty().equals(loadWaybillDetail.getLoadQty())) {
								LOGGER.info("============222=111111======");
								DeliverbillSMSDto deliverbillSMS = new DeliverbillSMSDto();
								// 运单号
								deliverbillSMS.setWaybillNo(deliverbillDetail.getWaybillNo());
								//收货人
//								deliverbillSMS.setConsignee(deliverbillDetail.getConsignee());
								//收货人手机号
								deliverbillSMS.setMobile(deliverbillDetail.getConsigneeContact());
								if(null !=deliverbillSMS.getMobile()){
									// 司机手机号
									deliverbillSMS.setDriverTel(deliverbill.getDriverTel());
									//件数
									deliverbillSMS.setWaybillDoodsQty(deliverbillDetail.getWaybillGoodsQty().toString());
									// 操作时间
									deliverbillSMS.setOperateTime(new Date());
									// 模块名称
									deliverbillSMS.setModuleName(NotifyCustomerConstants.SMS_CODE_PKP_NOTIFY_DRIVER_C);
									// 获取当前操作人、部门
									UserEntity currentUser = FossUserContext.getCurrentUser();
									OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();
									if (currentUser != null) {
										EmployeeEntity employee = currentUser.getEmployee();
										if (employee != null) {
											// 操作人
											deliverbillSMS.setOperator(employee.getEmpName());
											// 操作人编码
											deliverbillSMS.setOperatorNo(employee.getEmpCode());
										}
									}
									if (currentOrg != null) {
										// 操作部门编码
										deliverbillSMS.setOperateOrgCode(currentOrg.getCode());
										// 操作部门
										deliverbillSMS.setOperateOrgName(currentOrg.getName());
									}

									String voiceConten = "";

									// 模版参数
									SmsParamDto smsParamDto = new SmsParamDto();
									// 语音编码
									smsParamDto.setSmsCode(NotifyCustomerConstants.SMS_CODE_PKP_NOTIFY_DRIVER_C);
									// 部门编码
									smsParamDto.setOrgCode(deliverbillSMS.getOperateOrgCode());
									// 参数Map
									smsParamDto.setMap(this.getSmsParamC(deliverbillSMS, deliverbill));
									try {
										voiceConten = sMSTempleteService.querySmsByParam(smsParamDto);
									} catch (SMSTempleteException e) {
										LOGGER.error(e.getMessage(), e);
										throw new NotifyCustomerException(NotifyCustomerException.MESSAGE_EMPTY, e);
									}
									LOGGER.info("语音信内容：{}", voiceConten);
									LOGGER.info("语音模版CODE:{}", NotifyCustomerConstants.SMS_CODE_PKP_NOTIFY_DRIVER_C);
									deliverbillSMS.setNoticeContent(voiceConten);
								}else{
									LOGGER.info("手机号码不能为空");
									throw new NotifyCustomerException("手机号码不能为空");
								}
								
								try {
									LOGGER.info("============333=111111======");
									// 发送短信给客户
									sendSmss(deliverbillSMS,deliverbillDetail,deliverbill);
									LOGGER.info("============444=111111======");
								} catch (Exception e) {
									LOGGER.error(e.getMessage(), e);
									throw new NotifyCustomerException(NotifyCustomerException.SMS_FAIL, e);
								}
							}else{
								LOGGER.info("已装车件数与开单件数不一致,不发送短信！");
								throw new NotifyCustomerException("已装车件数与开单件数不一致,不发送短信！");
							}
						}
					}else{
						LOGGER.info("装车任务明细为空,不发送短信！");
						throw new NotifyCustomerException("装车任务明细为空,不发送短信！");
					}
				}else{
					LOGGER.info("装车任务号为空,不发送短信！");
					throw new NotifyCustomerException("装车任务号为空,不发送短信！");
				}
			}
		}else{
			LOGGER.info("派送单ID为空,不发送短信！");
			throw new NotifyCustomerException("派送单ID为空,不发送短信！");
		}
	}
	
	/**
	 * 获取最终配载部门
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
                    permissionControlDto.setOrgRoleType(0);
                    //
                    if (transferCenter != null)
                    {
                       permissionControlDto.setEndStockOrgCode(transferCenter.getCode());
                    }
                }
            } 
        }
        return permissionControlDto;
    }
	
	
	/**
	 * 发送短信
	 * 
	 */
	private void sendSms(DeliverbillSMSDto deliverbillSMS) {
		SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
		try {
			//发送部门编码
			smsSendLog.setSenddeptCode(deliverbillSMS.getOperateOrgCode());
			//发送人员编码
			smsSendLog.setSenderCode(deliverbillSMS.getOperatorNo());
			// 电话
			smsSendLog.setMobile(deliverbillSMS.getMobile());
			// 短信内容
			smsSendLog.setContent(deliverbillSMS.getNoticeContent());
			// 发送部门
			smsSendLog.setSenddept(deliverbillSMS.getOperateOrgName());
			// 发送人
			smsSendLog.setSender(deliverbillSMS.getOperator());
			// 业务类型
			smsSendLog.setMsgtype(DeliverbillConstants.PKP_NOTIFY_DRIVER);
			// 短信来源
			smsSendLog.setMsgsource(NotifyCustomerConstants.SYS_SOURCE);
			// 唯一标识
			smsSendLog.setUnionId(UUIDUtils.getUUID());
			// 运单号
			smsSendLog.setWaybillNo(deliverbillSMS.getDeliverbillNo());
			// 发送时间
			smsSendLog.setSendTime(new Date());
			// 服务类型（1:短信）
			smsSendLog.setServiceType(NumberConstants.NUMERAL_S_ONE);
			LOGGER.info("短信内容：" + ReflectionToStringBuilder.toString(smsSendLog));
			// 发送短信内容
			smsSendLogService.sendSMS(smsSendLog);
		} catch (SMSSendLogException se) {
			LOGGER.error(NotifyCustomerException.ERROR, se);
			throw new NotifyCustomerException(se.getMessage(), se);
		}
	}
	
	/**
	 * 发送短信--客户306548
	 * 
	 */
	private void sendSmss(DeliverbillSMSDto deliverbillSMS,DeliverbillDetailEntity deliverbillDetail,DeliverbillEntity deliverbill) {
		SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
		try {
			//发送部门编码
			smsSendLog.setSenddeptCode(deliverbillSMS.getOperateOrgCode());
			//发送人员编码
			smsSendLog.setSenderCode(deliverbillSMS.getOperatorNo());
			// 电话
			smsSendLog.setMobile(deliverbillSMS.getMobile());
			// 短信内容
			smsSendLog.setContent(deliverbillSMS.getNoticeContent());
			// 发送部门
			smsSendLog.setSenddept(deliverbillSMS.getOperateOrgName());
			// 发送人
			smsSendLog.setSender(deliverbillSMS.getOperator());
			// 业务类型
			smsSendLog.setMsgtype(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
			// 短信来源
			smsSendLog.setMsgsource(NotifyCustomerConstants.SYS_SOURCE);
			// 唯一标识
			smsSendLog.setUnionId(UUIDUtils.getUUID());
			// 运单号
			smsSendLog.setWaybillNo(deliverbillSMS.getWaybillNo());
			// 发送时间
			smsSendLog.setSendTime(new Date());
			// 服务类型（1:短信）
			smsSendLog.setServiceType(NumberConstants.NUMERAL_S_ONE);
			LOGGER.info("短信内容：" + ReflectionToStringBuilder.toString(smsSendLog));
			LOGGER.info("=============打印发送短信的内容==============="+smsSendLog.getContent().toString());
			// 发送短信内容
			smsSendLogService.sendSMS(smsSendLog);
			//向语音短信通知表中添加一条数据、更新实际承运表中的通知结果
			addCustomerNotificationEntity(deliverbillSMS,deliverbillDetail,deliverbill);
			
		} catch (SMSSendLogException se) {
			LOGGER.error(NotifyCustomerException.ERROR, se);
			throw new NotifyCustomerException(se.getMessage(), se);
		}
	}
	
	//向语音短信通知表中添加一条数据、更新实际承运表中的通知结果306548 
	private void addCustomerNotificationEntity(DeliverbillSMSDto deliverbillSMS,DeliverbillDetailEntity deliverbillDetail,DeliverbillEntity deliverbill){
		NotificationEntity notificationEntity = new NotificationEntity();
		// 运单号
		notificationEntity.setWaybillNo(deliverbillSMS.getWaybillNo());
		if(StringUtil.isNotEmpty( deliverbillSMS.getNoticeContent())){
			// 通知内容 
			notificationEntity.setNoticeContent(deliverbillSMS.getNoticeContent());
		}else{
			// 通知内容 
			notificationEntity.setNoticeContent("*");
		}
		if(StringUtil.isNotEmpty(deliverbillSMS.getOperator())){
			// 操作人
			notificationEntity.setOperator(deliverbillSMS.getOperator());
		}else{
			// 操作人
			notificationEntity.setOperator("*");
		}
		if(StringUtil.isNotEmpty(deliverbillSMS.getOperateOrgName())){
			// 操作人编码
			notificationEntity.setOperatorNo(deliverbillSMS.getOperateOrgName());
		}else{
			// 操作人编码
			notificationEntity.setOperatorNo("*");
		}
		if(StringUtil.isNotEmpty(deliverbillSMS.getOperateOrgName())){
			// 操作部门
			notificationEntity.setOperateOrgName(deliverbillSMS.getOperateOrgName());
		}else{
			// 操作部门
			notificationEntity.setOperateOrgName("*");
		}
		if(StringUtil.isNotEmpty(deliverbillSMS.getOperateOrgCode())){
			// 操作部门编码
			notificationEntity.setOperateOrgCode(deliverbillSMS.getOperateOrgCode());
		}else{
			// 操作部门编码
			notificationEntity.setOperateOrgCode("*");
		}
		//预计送货时间
		notificationEntity.setDeliverDate(DateUtils.convert(deliverbill.getDeliverDate()));
		
		if(StringUtil.isNotEmpty(deliverbillSMS.getMobile())){
			// 手机号
			notificationEntity.setMobile(deliverbillSMS.getMobile());
		}else{
			// 手机号
			notificationEntity.setMobile("*");
		}
		
		if(StringUtil.isNotEmpty(deliverbillDetail.getReceiveCustomerContact())){
			//接收人姓名
			notificationEntity.setConsignee(deliverbillDetail.getReceiveCustomerContact());
		}else{
			//接收人姓名
			notificationEntity.setConsignee("*");
		}
		if(StringUtil.isNotEmpty(deliverbillSMS.getWaybillDoodsQty())){
			// 通知件数
			notificationEntity.setArriveGoodsQty(Integer.valueOf(deliverbillSMS.getWaybillDoodsQty()));
		}else{
			// 通知件数
			notificationEntity.setArriveGoodsQty(Integer.valueOf("*"));
		}
		if(StringUtil.isNotEmpty(deliverbillDetail.getReceiveMethod())){
			// 派送方式（提货方式）
			notificationEntity.setDeliverType(DictUtil.rendererSubmitToDisplay(deliverbillDetail.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
		}else{
			//派送方式（提货方式）
			notificationEntity.setDeliverType("*");
		}
		// 操作时间
		notificationEntity.setOperateTime(new Date());
		// 通知类型为短信通知
		notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
		// 通知成功
		notificationEntity.setNoticeResult(NotifyCustomerConstants.SUCCESS);
		// 模块名称
		notificationEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		// 运单实际货物entity
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		// 运单号
		actualFreightEntity.setWaybillNo(deliverbillSMS.getWaybillNo());
		// 通知状态
		actualFreightEntity.setNotificationResult(NotifyCustomerConstants.SUCCESS);
		LOGGER.info("=============运单号==============="+deliverbillSMS.getWaybillNo()+"=============语音短信通知表中添加一条数据==============="+notificationEntity.toString());
		notifyCustomerDao.addNotificationEntity(notificationEntity);
		//更新实际承运表中的通知结果
		notifyCustomerDao.updateActualFreightEntityByWaybillNo(actualFreightEntity);
	}
	
	/**
	 * 通知内容验证.
	 * 
	 */
	private void validateDeliverbillSMS(DeliverbillSMSDto deliverbillSMS) {
		if (deliverbillSMS == null) {
			LOGGER.info("短信entity不能为null");
			throw new NotifyCustomerException("短信entity不能为null");
		}
		// 验证电话号码
		if (StringUtil.isBlank(deliverbillSMS.getMobile())) {
			LOGGER.info("手机号码不能为空");
			throw new NotifyCustomerException("手机号码不能为空");
		}
/*		// 验证通知类型
		if (StringUtil.isBlank(deliverbillSMS.getNoticeType())) {
			LOGGER.info("通知类型不能为空");
			throw new NotifyCustomerException("通知类型不能为空");
		}*/
		// 模块名称
		if (StringUtil.isBlank(deliverbillSMS.getModuleName())) {
			LOGGER.info("模块名称不能为空");
			throw new NotifyCustomerException("模块名称不能为空");
		}
		// 验证派送单号
		if (StringUtil.isBlank(deliverbillSMS.getDeliverbillNo())) {
			deliverbillSMS.setDeliverbillNo("*");
		}
		// 验证接收人姓名
		if (StringUtil.isBlank(deliverbillSMS.getConsignee())) {
			deliverbillSMS.setConsignee("*");
		}
		// 送货日期
		if (StringUtil.isBlank(deliverbillSMS.getDeliveryDate()) ||deliverbillSMS.getDeliveryDate().equals("0")) {
			deliverbillSMS.setDeliveryDate("*");
		}
		// 派送单号
		if (StringUtil.isBlank(deliverbillSMS.getDeliverbillNo())) {
			deliverbillSMS.setDeliverbillNo("*");
		}
		// 派送单状态
		if (StringUtil.isBlank(deliverbillSMS.getStatus())) {
			deliverbillSMS.setStatus("*");
		}
		// 车牌号
		if (StringUtil.isBlank(deliverbillSMS.getVehicleNo())) {
			deliverbillSMS.setVehicleNo("*");
		}
		// 派送单票数
		if (StringUtil.isBlank(deliverbillSMS.getTotalGoodsQty())) {
			deliverbillSMS.setTotalGoodsQty("*");
		}
		// 派送单货物总件数
		if (StringUtil.isBlank(deliverbillSMS.getWaybillDoodsQty())) {
			deliverbillSMS.setWaybillDoodsQty("*");
		}
		// 总到付金额
		if (deliverbillSMS.getPayAmountTotal()==null) {
			deliverbillSMS.setWaybillDoodsQty("*");
		}
		// 装载率
		if (StringUtil.isBlank(deliverbillSMS.getLoadingRate())) {
			deliverbillSMS.setLoadingRate("*");
		}
		// 操作人
		if (StringUtil.isBlank(deliverbillSMS.getOperator())) {
			deliverbillSMS.setOperator("*");
		}
	}

	/**
	 * 设置短信模版内容的参数
	 * @param dto
	 * @return
	 */
	private Map<String, String> getSmsParam(DeliverbillSMSDto dto) {
		Map<String, String> paramMap = new HashMap<String, String>();
		// 接收人姓名
		if (StringUtil.isNotEmpty(dto.getConsignee())) {
			paramMap.put("consignee", dto.getConsignee());
		} else {
			paramMap.put("consignee", "*");
		}
		// 送货日期
		if (StringUtil.isNotEmpty(dto.getDeliveryDate())) {
			paramMap.put("deliveryDate", dto.getDeliveryDate());
		} else {
			paramMap.put("deliveryDate", "*");
		}
		// 派送单号
		if (StringUtil.isNotEmpty(dto.getDeliverbillNo())) {
			paramMap.put("deliverbillNo", dto.getDeliverbillNo());
		} else {
			paramMap.put("deliverbillNo", "*");
		}
		// 派送单状态
		if (StringUtil.isNotEmpty(dto.getStatus())) {
			paramMap.put("status", dto.getStatus());
		} else {
			paramMap.put("status", "*");
		}
		// 车牌号
		if (StringUtil.isNotEmpty(dto.getVehicleNo())) {
			paramMap.put("vehicleNo", dto.getVehicleNo());
		} else {
			paramMap.put("vehicleNo", "*");
		}
		// 派送单票数
		if (StringUtil.isNotEmpty(dto.getTotalGoodsQty())) {
			paramMap.put("totalGoodsQty", dto.getTotalGoodsQty());
		} else {
			paramMap.put("totalGoodsQty", "*");
		}
		// 特殊地址数量
		if (StringUtil.isNotEmpty(dto.getSpecialAddressNum())) {
			paramMap.put("specialAddressNum", dto.getSpecialAddressNum());
		} else {
			paramMap.put("specialAddressNum", "*");
		}
		// 派送单货物总件数
		if (StringUtil.isNotEmpty(dto.getWaybillDoodsQty())) {
			paramMap.put("waybillDoodsQty", dto.getWaybillDoodsQty());
		} else {
			paramMap.put("waybillDoodsQty", "*");
		}
		// 总到付金额
		if (dto.getPayAmountTotal()!=null) {
			paramMap.put("payAmountTotal", dto.getPayAmountTotal().toString());
		} else {
			paramMap.put("payAmountTotal", "*");
		}
		// 装载率
		if (StringUtil.isNotEmpty(dto.getLoadingRate())) {
			paramMap.put("loadingRate", dto.getLoadingRate());
		} else {
			paramMap.put("loadingRate", "*");
		}
		// 操作人
		if (StringUtil.isNotEmpty(dto.getOperator())) {
			paramMap.put("operator", dto.getOperator());
		} else {
			paramMap.put("operator", "*");
		}
		return paramMap;
	}
	
	/**
	 * 设置发送客户短信模版内容的参数--306548
	 * @param dto
	 * @return
	 */
	private Map<String, String> getSmsParamC(DeliverbillSMSDto dto,DeliverbillEntity deliverbill) {
		Map<String, String> paramMap = new HashMap<String, String>();
		// 接收人姓名consignee
//		if (StringUtil.isNotEmpty(dto.getConsignee())) {
//			paramMap.put("receiveCustomerContact", dto.getConsignee());
//		} else {
//			paramMap.put("receiveCustomerContact", "");
//		}
		// 运单号--重新查询派送交单表
		if (StringUtil.isNotEmpty(dto.getWaybillNo())) {
			paramMap.put("waybillNo", dto.getWaybillNo());
		} else {
			paramMap.put("waybillNo", "");
		}
		// 通知件数---重新查派送交单表
		if (StringUtil.isNotEmpty(dto.getWaybillDoodsQty())) {
			paramMap.put("arriveGoodsQty", dto.getWaybillDoodsQty());
		} else {
			paramMap.put("arriveGoodsQty", "");
		}
		// 司机姓名
		if (StringUtil.isNotEmpty(deliverbill.getDriverName())) {
			paramMap.put("driverName", deliverbill.getDriverName());
		} else {
			paramMap.put("driverName", "");
		}
		// 司机电话
		if (StringUtil.isNotEmpty(dto.getDriverTel())) {
			paramMap.put("driverPhone", dto.getDriverTel());
		} else {
			paramMap.put("driverPhone", "");
		}
		return paramMap;
	}
	
	
	
	
	/**
	 * 
	 * 根据派送单ID更新通知司机状态
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @author 
	 * @date 
	 */
	public int updateDeliverbillisSendSMS(String deliverbillId){
		DeliverbillEntity deliverbillEntity=new DeliverbillEntity();
		deliverbillEntity.setId(deliverbillId);
		deliverbillEntity.setIsSendSMS(STRING_ONE);
		return this.deliverbillNewDao.updateDeliverBill(deliverbillEntity);
	}
	
	
	/**
	 * 
	 * 根据接送货车辆车牌号查询接送货司机(SUC-447 创建派送单 SR9 
	 * 1.若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机 
	 * 2.若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改 
	 * 3.当排班和PDA绑定同时存在时，以PDA绑定为准)
	 * 
	 * @param 
	 * vehicleNo
	 *  车牌号
	 * @return 
	 * 接送货司机
	 * @author 
	 * @date 
	 */
	@Override
	public DriverDto queryDriverByVehicleNo(String vehicleNo)
	{
		DriverDto driver = null;

		// 查询车辆是否与司机通过PDA绑定
		PdaSignDto pdaSignDto = new PdaSignDto();
		pdaSignDto.setVehicleNo(vehicleNo);
		pdaSignDto.setStatus(PdaSignStatusConstants.BUNDLE);

		List<PdaSignDto> pdaSignDtoList = this.signInAndLogOutService.querySignedInfo(pdaSignDto);

		if (CollectionUtils.isNotEmpty(pdaSignDtoList))
		{
			pdaSignDto = pdaSignDtoList.get(0);
			driver = new DriverDto();
			driver.setEmpCode(pdaSignDto.getDriverCode());
			driver.setEmpName(pdaSignDto.getDriverName());
			driver.setPdaSigned(FossConstants.YES);
		} else
		{
			// 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机
			OwnTruckConditionDto ownTruckConditionDto = new OwnTruckConditionDto();

			// 排班类型--接送货
			ownTruckConditionDto.setSchedulingType(TruckConstants.SCHEDULE_TYPE_DELIVERY);
			// 排班状态--可用
			ownTruckConditionDto.setSchedulingStatus(FossConstants.ACTIVE);
			// 司机状态--工作
			ownTruckConditionDto.setSchedulingPlanType(TruckConstants.PLAN_TYPE_WORK);
			ownTruckConditionDto.setVehicleNo(vehicleNo);
			ownTruckConditionDto.setActive(FossConstants.ACTIVE);

			List<OwnTruckDto> schedulingResult = truckScheduleDao.queryTruckSchedulingByVehicleNo(ownTruckConditionDto);

			if (CollectionUtils.isNotEmpty(schedulingResult))
			{
				OwnTruckDto ownTruckDto = schedulingResult.get(0);

				driver = new DriverDto();
				driver.setEmpCode(ownTruckDto.getDriverCode());
				driver.setEmpName(ownTruckDto.getDriverName());
				driver.setPdaSigned(FossConstants.NO);
			}else
			{
				// 外请司机根据车牌号进行查询
		          List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(vehicleNo);
		          if (CollectionUtils.isNotEmpty(driverAssociationDtos))
		          {
		        	  driver = new DriverDto();
		        	  driver.setEmpCode(driverAssociationDtos.get(0).getDriverIdCard());
		        	  driver.setEmpName(driverAssociationDtos.get(0).getDriverName());
		        	  driver.setPdaSigned(FossConstants.NO);
		          }
		        }
		}
		return driver;
	}

	
	
	/**
	 * 根据查询条件(工号/姓名/电话号码)查询公司司机.
	 *
	 * @param driverDto 
	 * 查询条件
	 * @return 
	 * 符合条件的公司司机列表
	 * @author 
	 * @date 
	 */
	@Override
	public List<DriverDto> queryDriverListByDriverDto(DriverDto driverDto)
	{
		OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
		String isSale = org.getSalesDepartment();
		List<String> orgIdList = new ArrayList<String>();
		if (FossConstants.YES.equals(isSale))
		{
			//如果是营业部派送部 则通过营业部-车队对应关系表获取车队code
			SalesMotorcadeEntity entity = new SalesMotorcadeEntity();
			entity.setSalesdeptCode(FossUserContextHelper.getOrgCode());
			List<SalesMotorcadeEntity> salesMotorcadeList = salesMotorcadeService.querySalesMotorcadeExactByEntity(entity, BEGIN_NUM, PAGE_NUM);
			if (!CollectionUtils.isEmpty(salesMotorcadeList))
			{
				for (SalesMotorcadeEntity salesMotorcadeEntity : salesMotorcadeList) 
				{
					orgIdList.add(salesMotorcadeEntity.getMotorcadeCode());
				}
				driverDto.setOrgIdList(orgIdList);
			}else
			{	
				orgIdList.add(FossUserContextHelper.getOrgCode());
				driverDto.setOrgIdList(orgIdList);
			}
		} else
		{
			// 获取当前组织对应车队
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.getTopFleetByCode(FossUserContextHelper.getOrgCode());
			if(orgAdministrativeInfoEntity != null)
			{
				orgIdList.add(orgAdministrativeInfoEntity.getCode());
				driverDto.setOrgIdList(orgIdList);
			}else
			{
				orgIdList.add(FossUserContextHelper.getOrgCode());
				driverDto.setOrgIdList(orgIdList);
			}
		}
		List<String> subOrgCodeList = new ArrayList<String>();
		for (String string : orgIdList) {
			//根据IBM-罗越决议 找顶级车队下所有子组织code
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(string);
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (OrgAdministrativeInfoEntity orgEntity : orgList) {
					subOrgCodeList.add(orgEntity.getCode());
				}
			}
		}
		//添加本部门司机-应用于单点营业部自有车辆
		subOrgCodeList.add(FossUserContextHelper.getOrgCode());
		//添加顶级车队code
		subOrgCodeList.add(driverDto.getOrgId());
		driverDto.setSubOrgCodeList(subOrgCodeList);
		
		driverDto.setActive(FossConstants.ACTIVE);
		return this.deliverbillNewDao.queryDriverListByDriverDto(driverDto);
	}
	
	
	
	/**
	 * 分配派送任务.
	 *
	 * @param deliverbillId 
	 * 派送单id
	 * @param driver 
	 * 分配的司机
	 * @return 分配成功标志。
	 * 若成功，则返回1；否则不成功
	 * @author 
	 * @date 
	 */
	@Override
	@Transactional
	public int assignDriver(String deliverbillId, DriverDto driver)
	{
		DeliverbillEntity deliverbill = new DeliverbillEntity();
		deliverbill.setId(deliverbillId);
		deliverbill.setDriverCode(driver.getEmpCode());
		deliverbill.setDriverName(driver.getEmpName());

		/*
		 * SUC-447 -创建预派送 SR-9 1. 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机
		 * 2. 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改 当排班和PDA绑定同时存在时，以PDA绑定为准
		 */
		deliverbill = this.deliverbillNewDao.update(deliverbill);

		if (deliverbill == null)
		{
			throw new DeliverbillException(DeliverbillException.DELIVERBILL_NOTFOUND);
		}
		DeliverbillEntity deliverbillForGps = deliverbillNewDao.queryById(deliverbillId);
		if(deliverbillForGps != null && DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillForGps.getStatus())){
			//修改派送单
			LOGGER.info("修改派送单开始");
			com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto resultDto = 
					gpsDeliverService.syscModifyDeliverTaskToGps(deliverbillForGps, null);
			if(ResultDto.FAIL.equals(resultDto.getCode())){
				LOGGER.info("修改派送单失败，错误详情："+resultDto.getMsg());
			}
			LOGGER.info("修改派送单结束");
		}
		return 1;
	}
	
	/**
	 * 更新派送单
	 */
	@Override
	public int updateDeliverBill(DeliverbillEntity deliverbill) {
		return this.deliverbillNewDao.updateDeliverBill(deliverbill);
	}
	
	
	/**
	 * 
	 * 根据条件查询派送单
	 * @author 
	 * @date 
	 */
	@Override
	public InputStream queryDeliverbillList(DeliverbillNewDto deliverbillNewDto) 
	{
		// 当前部门编码
		String orgCode = FossUserContextHelper.getOrgCode();
		//若传入编码不为空
		if (StringUtils.isNotEmpty(orgCode))
		{	
			
			deliverbillNewDto.setOrgCode(orgCode);
			deliverbillNewDto.setTransferCenter(initOrgRole().getEndStockOrgCode());
			//如果派送单状态为全部，则赋空
			if (StringUtils.isNotBlank(deliverbillNewDto.getStatus())) {
				if (deliverbillNewDto.getStatus().equals(DeliverbillConstants.STATUS_ALL)) {
					deliverbillNewDto.setStatus(null);
				}
			}
			//根据输入条件，查询派送单
			List<DeliverbillNewDto> deliverbillList =  this.deliverbillNewDao.queryByCondition(deliverbillNewDto);
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (DeliverbillNewDto deliverbill : deliverbillList) {
				if (deliverbill.getDeliverDate()!=null) {
					 //查询当前车辆的车型、班次
					queryRightCount(deliverbill);
				}
				
				List<String> columnList = new ArrayList<String>();
				//派送单号
				if (deliverbill.getDeliverbillNo()!=null) {
					columnList.add(deliverbill.getDeliverbillNo());
				}else{
					columnList.add("");
				}
				//预排单状态
				if (deliverbill.getStatus()!=null) {
					columnList.add(DictUtil.rendererSubmitToDisplay(deliverbill.getStatus(), DictionaryConstants.PKP_DELIVERBILL_STATUS));
				}else{
					columnList.add("");
				}
				//车辆
				if (deliverbill.getVehicleNo()!=null) {
					columnList.add(deliverbill.getVehicleNo());
				}else{
					columnList.add("");
				}
				//司机姓名
				if (deliverbill.getDriverName()!=null) {
					columnList.add(deliverbill.getDriverName());
				}else{
					columnList.add("");
				}
				//装车完成时间
				if (deliverbill.getLoadEndTime()!=null) {
					columnList.add(DateUtils.convert(deliverbill.getLoadEndTime(), DateUtils.DATE_TIME_FORMAT));
				}else{
					columnList.add("");
				}
				//出车时间
				if (deliverbill.getDepartTime()!=null) {
					columnList.add(DateUtils.convert(deliverbill.getDepartTime(), DateUtils.DATE_TIME_FORMAT));
				}else{
					columnList.add("");
				}
				//到付金额
				if (deliverbill.getPayAmountTotal()!=null) {
					columnList.add(deliverbill.getPayAmountTotal().toString());
				}else{
					columnList.add("0");
				}
				//派车类型
				if(StringUtil.isNotBlank(deliverbill.getDeliverType())){
					if(DeliverbillConstants.NOMAL.equals(deliverbill.getDeliverType())){//正常
						columnList.add("正常");//派车类型
					}else if(DeliverbillConstants.SPECIAL.equals(deliverbill.getDeliverType())){// 专车
						columnList.add("专车");//派车类型
					}else {//带人
						columnList.add("带人");//派车类型
					}
				}else {
					columnList.add("");
				}
				//车型/班次
				if (deliverbill.getTruckModel()==null) {
					deliverbill.setTruckModel("");
				}
				if (deliverbill.getFrequencyNo()==null) {
					deliverbill.setFrequencyNo("");
				}
				columnList.add(deliverbill.getTruckModel()+" / "+deliverbill.getFrequencyNo());
				//预计送货日期
				if (deliverbill.getDeliverDate()!=null) {
					columnList.add(DateUtils.convert(deliverbill.getDeliverDate(), DateUtils.DATE_TIME_FORMAT));
				}else{
					columnList.add("");
				}
				//排单票数
				if (deliverbill.getTotalGoodsQty()!=null) {
					columnList.add(deliverbill.getTotalGoodsQty());
				}else{
					columnList.add("0");
				}
				//排单重量/体积
				columnList.add(deliverbill.getWeightTotal().toString()+" / "+deliverbill.getVolumeTotal().toString());
				//设置装载率
				columnList.add(deliverbill.getLoadingRate());
				//创建人
				if (deliverbill.getCreateUserName()!=null) {
					columnList.add(deliverbill.getCreateUserName());
				}else{
					columnList.add("");
				}
				//创建时间
				if (deliverbill.getSubmitTime()!=null) {
					columnList.add(DateUtils.convert(deliverbill.getSubmitTime(), DateUtils.DATE_TIME_FORMAT));
				}else{
					columnList.add("");
				}
				rowList.add(columnList);
			}
			String[] rowHeads = {"派送单号","派送单状态","车辆","司机姓名","装车完成时间","出车时间","到付金额","派车类型","车型/班次","预计送货日期","排单票数","排单重量/体积","装载率","创建人","创建时间"};
			
		    ExportResource exportResource = new ExportResource();
		    exportResource.setHeads(rowHeads);
		    exportResource.setRowList(rowList);
		    ExportSetting exportSetting = new ExportSetting();
		    exportSetting.setSheetName("派送单列表");
		    exportSetting.setSize(NUMBER);
		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	        return objExporterExecutor.exportSync(exportResource, exportSetting);
		} else
		{
			//返回空
			return null;
		}
	}
	
	
	
	/**
	 * 
	 * 根据条件查询派送单明细
	 * @author 
	 * @date 
	 */
	@Override
	public InputStream queryDeliverbillDetailLists(DeliverbillNewDto deliverbillNewDto) {
		// 当前部门编码
			String orgCode = FossUserContextHelper.getOrgCode();
		//若当前部门编码不为空
		if (StringUtils.isNotEmpty(orgCode))
		{	
			deliverbillNewDto.setOrgCode(orgCode);
			deliverbillNewDto.setTransferCenter(initOrgRole().getEndStockOrgCode());
			
			//如果派送单状态为全部，则赋空
			if (StringUtils.isNotBlank(deliverbillNewDto.getStatus())) {
				if (deliverbillNewDto.getStatus().equals(DeliverbillConstants.STATUS_ALL)) {
					deliverbillNewDto.setStatus(null);
				}
			}
			//根据输入条件，查询派送单
			List<DeliverbillDetailDto> deliverbillDetailList =  deliverbillNewDetailDao.queryByDeliverbillNos(deliverbillNewDto);
			List<List<String>> rowList = new ArrayList<List<String>>();
			if(CollectionUtils.isNotEmpty(deliverbillDetailList)){
				for (DeliverbillDetailDto deliverbill : deliverbillDetailList) {
					List<String> columnList = new ArrayList<String>();
					//派送单号
					columnList.add(deliverbill.getDeliverbillNo());
					columnList.add(deliverbill.getWaybillNo());//单号
					if(StringUtil.isNotBlank(deliverbill.getArrivesheetNo()) && DeliverbillConstants.NULL_ARRIVESHEET_NO.equals(deliverbill.getArrivesheetNo()))
					{
						columnList.add(null);//到达联编号
					}else {
						columnList.add(deliverbill.getArrivesheetNo());//到达联编号
					}
					if(null != deliverbill.getWaybillGoodsQty()){
						columnList.add( deliverbill.getWaybillGoodsQty().toString());//开单件数
					}else {
						columnList.add(BigDecimal.ZERO.toString());//开单件数
					}
					if(null != deliverbill.getStockGoodsQty()){
						columnList.add(deliverbill.getStockGoodsQty().toString());//库存件数
					}else {
						columnList.add(BigDecimal.ZERO.toString());//库存件数
					}
					if(null != deliverbill.getArrangeGoodsQty()){
						columnList.add(deliverbill.getArrangeGoodsQty().toString());//排单件数
					}else {
						columnList.add(BigDecimal.ZERO.toString());//排单件数
					}
					if(null != deliverbill.getWeight()){
						columnList.add(deliverbill.getWeight().toString());//排单重量
					}else {
						columnList.add(BigDecimal.ZERO.toString());//排单重量
					}
					if(null != deliverbill.getVolumeTotal()){
						columnList.add(deliverbill.getVolumeTotal().toString());//体积
					}else {
						columnList.add(BigDecimal.ZERO.toString());//体积
					}
					columnList.add(deliverbill.getGoodsPackage());//包装
					ProductEntity  pro =productService.getProductByCache(deliverbill.getTransportType(),new Date());
					if(null != pro){
						columnList.add(pro.getName());//运输性质
					}else {
						columnList.add(deliverbill.getTransportType());//运输性质
					}
					//以下两个字段不导出
					/*columnList.add(deliverbill.getConsignee());//收货人
					columnList.add(deliverbill.getConsigneeContact());//联系方式*/
					
					columnList.add(deliverbill.getConsigneeAddress());//送货地址
					columnList.add(DictUtil.rendererSubmitToDisplay(deliverbill.getDeliverType(), DictionaryConstants.PICKUP_GOODS));//提货方式
					if(null != deliverbill.getSignTime()){
						columnList.add(WAYBILL_IS_SIGN);
					}else {
						columnList.add(WAYBILL_IS_NOT_SIGN);
					}
					columnList.add(DateUtils.convert(deliverbill.getSignTime(), DateUtils.DATE_TIME_FORMAT));//签收时间
					//车辆
					columnList.add(deliverbill.getVehicleNo());
					if(StringUtil.isNotBlank(deliverbill.getSendCarType())){
						if(DeliverbillConstants.NOMAL.equals(deliverbill.getSendCarType())){//正常
							columnList.add("正常");//派车类型
						}else if(DeliverbillConstants.SPECIAL.equals(deliverbill.getSendCarType())){// 专车
							columnList.add("专车");//派车类型
						}else {//带人
							columnList.add("带人");//派车类型
						}
						
					}else {
						columnList.add("");
					}
					columnList.add(deliverbill.getDriverCode());//司机工号
					//司机姓名
					columnList.add(deliverbill.getDriverName());
					//排单时间
					columnList.add(DateUtils.convert(deliverbill.getOperateTime(), DateUtils.DATE_TIME_FORMAT));
					rowList.add(columnList);
				}
				String[] rowHeads = {"派送单号","单号","到达联编号","开单件数","库存件数","排单件数","排单重量",
						"体积","包装","运输性质",/*"收货人","联系方式",*/"送货地址","提货方式",
						"是否已签收","签收时间","车牌号","派车类型","司机工号","司机姓名","排单时间"};
				
				ExportResource exportResource = new ExportResource();
				exportResource.setHeads(rowHeads);
				exportResource.setRowList(rowList);
				ExportSetting exportSetting = new ExportSetting();
				exportSetting.setSheetName("派送单明细列表");
				exportSetting.setSize(NUMBER);
				ExporterExecutor objExporterExecutor = new ExporterExecutor();
				return objExporterExecutor.exportSync(exportResource, exportSetting);
			}else {
				return null;
			}
		} else
		{
			return null;
		}
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	public void setRegionalVehicleService(
			IRegionalVehicleService regionalVehicleService) {
		this.regionalVehicleService = regionalVehicleService;
	}
	
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}
	public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
		this.smsSendLogService = smsSendLogService;
	}
	public void setSpecialDeliveryAddressService(
			ISpecialDeliveryAddressService specialDeliveryAddressService) {
		this.specialDeliveryAddressService = specialDeliveryAddressService;
	}
	public void setDeliverBillVaryStatusService(
			IDeliverBillVaryStatusService deliverBillVaryStatusService) {
		this.deliverBillVaryStatusService = deliverBillVaryStatusService;
	}

	public void setTruckSchedulingTaskService(
			ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}

	public void setDeliverbillDetailDao(IDeliverbillDetailDao deliverbillDetailDao) {
		this.deliverbillDetailDao = deliverbillDetailDao;
	}

	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}

	public void setNotifyCustomerDao(INotifyCustomerDao notifyCustomerDao) {
		this.notifyCustomerDao = notifyCustomerDao;
	}

	public void setLoadTaskQueryService(ILoadTaskQueryService loadTaskQueryService) {
		this.loadTaskQueryService = loadTaskQueryService;
	}

}