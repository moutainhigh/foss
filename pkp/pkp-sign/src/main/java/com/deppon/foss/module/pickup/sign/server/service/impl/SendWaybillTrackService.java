/**
 * 
 */
package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillTrackDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsService;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.trackings.api.shared.domain.ExpressTrackExternalEntity;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
/**
 * 运单轨迹接口，快递100-轨迹推送
 * @author 231438_chenjunying
 * 2015-4-10 下午5:26:58 
 */
public class SendWaybillTrackService implements ISendWaybillTrackService {
	/**
	 * Logger实例
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SendWaybillTrackService.class);
	/**
	 * 人员接口
	 */
	private IEmployeeService employeeService; 
	/**
	 * 货物轨迹服务接口（对接快递100）
	 * @return
	*/
	private IWaybillTrackingsService  waybillTrackingsService; 
	/**
	 * 菜鸟轨迹推送接口 
	 */
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;
	/**
	 * 综合服务接口 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 运单管理接口
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 签收变更结果接口
	 */
	private ISignChangeService signChangeService;

	/**
	 *  
	 */
	private ICommonExpressVehicleService commonExpressVehicleService;
	/**
	 * 公司自有司机 
	 */
	private IOwnDriverService ownDriverService;
	/**
	 * 外请车牌号"来获取公司司机相关的数据service
	 */
	private ILeasedDriverService leasedDriverService;
	/**
	 * 人员接口 注入
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * 菜鸟轨迹推送接口 注入
	 */
	public void setPushTrackForCaiNiaoService(IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}
	public void setCommonExpressVehicleService(
			ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
	}

	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}

	public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
		this.leasedDriverService = leasedDriverService;
	}

	/**
	 * 验证参数是否合法
	 */
	private boolean checkParams(WaybillTrackDto trackingsDto){
		if(null == trackingsDto.getWaybillNo()){
			return false;
		}
		if(null == trackingsDto.getOperateType()){
			return false;
		}
		if(null == trackingsDto.getCurrentInfo().getCurrentDeptCode()){
			return false;
		}
		if(null == trackingsDto.getCurrentInfo().getCurrentDeptName()){
			return false;
		}
		return true;
	}
	/**
	 * @author 231438 chenjunying
	 * 快递100轨迹推送封装方法：
	 * 客户通知、派送、派送拉回（PDA/FOSS）、
	 * 签收【正常、异常（丢货、弃货、违禁品签收）-PDA/FOSS】、反签收
	 * 调用方法给货物轨迹状态
	 */
	@Override
	public void sendTrackings(WaybillTrackDto trackingsDto){
		//货物轨迹dto
		WaybillTrackingsDto dto = new WaybillTrackingsDto();
		//非空验证方法参数实体
		if(null == trackingsDto){
			return;
		}
		LOGGER.info("主方法调轨迹接口，推送轨迹开始...."+trackingsDto.toString());
		//验证具体参数
		if(!checkParams(trackingsDto)){
			return;
		}
		
		//设置运单号
		dto.setWaybillNo(trackingsDto.getWaybillNo());
		//操作部门编码
		dto.setOperateDeptCode(trackingsDto.getCurrentInfo().getCurrentDeptCode());
		//操作部门名称
		dto.setOperateDeptName(trackingsDto.getCurrentInfo().getCurrentDeptName());
		//操作时间
		dto.setOperateTime(new Date());
		//操作类型,根据实际业务给dto操作类型赋值
		dto.setOperateType(trackingsDto.getOperateType());
		if(null != trackingsDto.getOperateDesc()){
			//操作描述/内容，有则赋值，可以为空
			dto.setOperateDesc(trackingsDto.getOperateDesc());
		}
		//正常签收,异常签收  签收人取提货人(丢货、弃货、违禁品不给签收人)
		if(WaybillTrackingsConstants.OPERATE_TYPE_NORMAL_SIGN.equals(trackingsDto.getOperateType())
				|| WaybillTrackingsConstants.OPERATE_TYPE_EXCEPTION_SIGN.equals(trackingsDto.getOperateType())){
			//签收人，丢货、弃货、违禁品不给签收人需要判断不为空才赋值，为空不管
			if(null != trackingsDto.getOperatorName()){
				dto.setOperatorName(trackingsDto.getOperatorName());
			}
			//签收人电话；一般没给
			if(null != trackingsDto.getOperatorName()){
				//有则给，无则不给
				dto.setOperatorPhone(trackingsDto.getOperatorPhone());
			}
		}else{ //通知客户、派送拉回、反签收
			//操作人
			dto.setOperatorName(trackingsDto.getCurrentInfo().getEmpName());
			//人员接口--用于获取操作人的电话号码
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCodeNoCache(trackingsDto.getCurrentInfo().getEmpCode());
			if(null != emp){
				//操作人电话
				dto.setOperatorPhone(emp.getMobilePhone());//有则给，无则不给
			}
		}
		//调用中转接口，给相应操作的轨迹
		waybillTrackingsService.addOneWaybillTrack(dto);
		LOGGER.info("主方法调轨迹接口，推送轨迹结束....");
	}
	/**
	 * 封装的方法 签收推送轨迹：签收时判断正常/异常签收，赋值，调用方法推送轨迹
	 * @author 231438_chenjunying
	 * 2015-4-14 下午4:26:58
	 */
	@Override
	public void packagingMethodForSign(WaybillSignResultEntity wayEntity,CurrentInfo currentInfo){
		LOGGER.info("签收轨迹开始：赋值，调用主方法调轨迹接口....");
		//推送货物轨迹 签收
		//--add by 231438，快递100，轨迹推送需求RFOSS2015031706
		WaybillTrackDto trackDto = new WaybillTrackDto();  //货物轨迹推动Dto
		//运单号
		trackDto.setWaybillNo(wayEntity.getWaybillNo());
		//设置用户登录信息
		trackDto.setCurrentInfo(currentInfo);
		//签收人  DN201511090005  添加“签收人类型”字段    243921
		if(StringUtils.isNotEmpty(wayEntity.getDeliverymanName()) && !"N/A".equals(wayEntity.getDeliverymanName())){
			trackDto.setOperatorName(wayEntity.getDeliverymanName());
		}else if(StringUtils.isNotEmpty(wayEntity.getDeliverymanType())){
			trackDto.setOperatorName(DictUtil.rendererSubmitToDisplay(wayEntity.getDeliverymanType(), DictionaryConstants.PKP_SIGN_PERSON_TYPE));
		}else{
			trackDto.setOperatorName(null);
		}		
		//全部签收（正、异常）推送轨迹时取发货人；部分签收不发送
		if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){
			if(SignConstants.NORMAL_SIGN.equals(wayEntity.getSignSituation())){ 
				//正常签收，不需要描述信息
				trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_NORMAL_SIGN);
			}else if(StringUtils.contains(wayEntity.getSignSituation(),"UNNORMAL")){//异常签收
				//异常签收，不需要描述信息
				trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_EXCEPTION_SIGN);
			}
			//调用轨迹接口，推送轨迹
			this.sendTrackings(trackDto);
		}
	}
	/**
	 * 封装的方法 反签收推送轨迹：赋值，调用方法推送轨迹
	 * @author 231438_chenjunying
	 * 2015-4-14 下午4:26:58
	 */
	@Override
	public void packagingMethodForSignRfc(SignRfcEntity entity,CurrentInfo currentInfo){
		LOGGER.info("反签收轨迹开始：赋值，调用主方法调轨迹接口....");
		//推送货物轨迹 反签收
		//--add by 231438，快递100，轨迹推送需求RFOSS2015031706
		WaybillTrackDto trackDto = new WaybillTrackDto();  //货物轨迹推动Dto
		//运单号
		trackDto.setWaybillNo(entity.getWaybillNo());
		//设置用户登录信息
		trackDto.setCurrentInfo(currentInfo);
		//操作类型：反签收
		trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_SIGN_RFC);
		//调用轨迹接口，推送轨迹
		this.sendTrackings(trackDto); 
	}
	/**
	 * 封装的方法 派送（零担确认派送单）
	 * 赋值，调用方法推送轨迹
	 * @author 231438_chenjunying
	 * 2015-4-14 下午4:26:58
	 */
	@Override
	public void packagingMethodForConfirm(DeliverbillEntity entity,List<DeliverbillDetailEntity> entityList){
		LOGGER.info("派送(零担)轨迹开始：赋值，调用主方法调轨迹接口....");
		if(entityList != null){//不为空，发送轨迹，为空不做处理
			//遍历取根据派送单查出的派送单详细列表取运单号 推送轨迹
			for(DeliverbillDetailEntity detailEntity:entityList){
				if(null != detailEntity){  //派送单详情不为空
					this.packagingMethodForConfirm(entity, detailEntity);
				}
			}
		}
	}
	/**
	 * 封装的方法 派送（快递确认派送单）
	 * 赋值，调用方法推送轨迹
	 * @author 231438_chenjunying
	 * 2015-4-14 下午4:26:58
	 */
	public void packagingMethodForConfirm(DeliverbillEntity entity,DeliverbillDetailEntity detailEntity){
		if(!checkValidate(entity,detailEntity)){
			return;
		}
		//货物轨迹dto
		WaybillTrackingsDto dto = new WaybillTrackingsDto();
		//运单号
		dto.setWaybillNo(detailEntity.getWaybillNo());
		//操作部门编码
		dto.setOperateDeptCode(entity.getOperateOrgCode());
		//操作部门名称
		dto.setOperateDeptName(entity.getOperateOrgName());
		//操作时间
		dto.setOperateTime(new Date());
		//操作类型：派送
		dto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_DELIVERY);
		
		if (StringUtils.equals(entity.getIsExpress(), FossConstants.YES) && 
				(StringUtils.isNotEmpty(entity.getDriverCode()) || StringUtils.isNotEmpty(entity.getVehicleNo()))) {
			// 如果是小件派送单,并且司机工号或者司机车牌号不为空
			ExpressVehicleDto expressVeh = new ExpressVehicleDto();
			// 司机工号
			expressVeh.setEmpCode(entity.getDriverCode());
			// 车牌号
			expressVeh.setVehicleNo(entity.getVehicleNo());
			List<ExpressVehicleDto> expressVehicleDtos = commonExpressVehicleService.queryExpressVehicleNoListBySelectiveCondition(expressVeh, NumberConstants.NUMBER_10, NumberConstants.ZERO);
			if (CollectionUtils.isNotEmpty(expressVehicleDtos)) {
				//司机姓名(送货人)
				dto.setOperatorName(expressVehicleDtos.get(0).getEmpName());
	        	//司机电话(送货人电话)
	        	dto.setOperatorPhone(expressVehicleDtos.get(0).getMobilePhone());
			}
		} else {
			//若司机工号不为空
			if (StringUtil.isNotEmpty(entity.getDriverCode())){
				// 内部司机根据工号查询相关信息
				DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(entity.getDriverCode());
				//用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
				if (driverAssociationDto != null){
					//司机姓名
					dto.setOperatorName(driverAssociationDto.getDriverName());
					// 司机电话
					dto.setOperatorPhone(driverAssociationDto.getDriverPhone());
				} else{
					// 外请司机根据车牌号进行查询
					List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(entity.getVehicleNo());
					
					if (CollectionUtils.isNotEmpty(driverAssociationDtos)){
						//司机姓名
						dto.setOperatorName(driverAssociationDtos.get(0).getDriverName());
						// 司机电话
						dto.setOperatorPhone(driverAssociationDtos.get(0).getDriverPhone());
					}
				}
				//如果车牌号不为空
			} else if (StringUtil.isNotEmpty(entity.getVehicleNo())){
				// 外请司机根据车牌号进行查询
				List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(entity.getVehicleNo());
				
				if (CollectionUtils.isNotEmpty(driverAssociationDtos)){
					//司机姓名
					dto.setOperatorName(driverAssociationDtos.get(0).getDriverName());
					//司机电话
					dto.setOperatorPhone(driverAssociationDtos.get(0).getDriverPhone());
				}
			}	
		}
		if(dto != null){
			//调用中转接口，给相应操作的轨迹
			waybillTrackingsService.addOneWaybillTrack(dto);
			LOGGER.info("主方法调轨迹接口，推送轨迹结束....");
		}
	}
	/**
	 * 封装的方法 派送拉回 
	 * 赋值，调用方法推送轨迹
	 * @author 231438_chenjunying
	 * 2015-4-14 下午4:26:58
	 */
	public void packagingMethodForDeliveryBack(ArriveSheetEntity entity,CurrentInfo currentInfo){
		LOGGER.info("派送拉回轨迹开始：赋值，调用主方法调轨迹接口....");
		//FOSS派送拉回 轨迹推送，派送拉回操作人及部门相关信息从CurrentInfo中取
		//--add by 231438,快递100，轨迹推送需求RFOSS2015031706
		WaybillTrackDto trackDto = new WaybillTrackDto();  //货物轨迹推动Dto
		//运单号
		trackDto.setWaybillNo(entity.getWaybillNo());
		//当前用户登录信息
		trackDto.setCurrentInfo(currentInfo);
		//拿到派送拉回原因转换，有可能是code，根据数据字典转换后再推送
		String reason = DictUtil.rendererSubmitToDisplay(entity.getSignNote(), DictionaryConstants.PKP_PULLBACK_REASON);
		//操作描述拉回原因
		trackDto.setOperateDesc("派送失败("+ reason +")");
		//操作类型，配送拉回
		trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_DELIVERY_RETURN);
		//调用轨迹接口，推送轨迹
		this.sendTrackings(trackDto); //--add by 231438
	}
	/**
	 * 验证参数是否合法
	 */
	private boolean checkValidate(DeliverbillEntity entity,
			DeliverbillDetailEntity detailEntity){
		if(null==detailEntity.getWaybillNo()){
			return false;
		}
		if(null==entity.getOperateOrgCode()){
			return false;
		}
		if(null==entity.getOperateOrgName()){
			return false;
		}
		return true;
	}
	
	/**
	 * 菜鸟轨迹 推送（快递）
	 * 天猫轨迹
	 */
	public void rookieTrackingsForSign(WaybillSignResultEntity signEntity){
		LOGGER.info("菜鸟轨迹开始：1，赋值；2，调轨迹接口....");
		//天猫轨迹实体
		ExpressTrackExternalEntity entity = new ExpressTrackExternalEntity();
		//菜鸟轨迹 待同步轨迹实体
		SynTrackingEntity synTrackingEntity=new SynTrackingEntity();
		Date dateTime = new Date();//获取当前操作时间
		//运单实体
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(signEntity.getWaybillNo());
		//组织信息实体
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCodeNoCache(waybillEntity.getLastLoadOrgCode());
		if(signEntity == null || waybillEntity == null || orgAdministrativeInfoEntity == null){
			LOGGER.info("菜鸟轨迹推送，必须参数不能为空");
			return;
		}
		//天猫轨迹开始,orderChannel订单来源
		if("TAOBAOJZ".equals(waybillEntity.getOrderChannel()) || "JIAWAYUN".equals(waybillEntity.getOrderChannel())){  //天猫家装和家哇云轨迹
			//验证运单是否符合条件，符合推送轨迹，不符合不做任何处理
			if(isFitCondition(signEntity,waybillEntity)){
			//原需求是只发快递的，加入淘宝家装后 TAOBAOJZ不分零担快递都给轨迹  2015-7-24 汤延飞修改需求去掉订单来源和运输性质的限制，所有运单签收都给信息给中转
//			if("TAOBAOJZ".equals(waybillEntity.getOrderChannel())){//淘宝家装
//				//订单来源为淘宝家装不做处理，向下继续执行发送轨迹
//			}else if("TAOBAO".equals(waybillEntity.getOrderChannel())){//淘宝
//				//快递否
//				if(!WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){
//					return;  //如果订单来源为TAOBAO,运单运输性质为快递才发送轨迹，不是则返回
//				}
//			}else{
//				//不是TAOBAOJZ、TAOBAO的运单，返回不做处理
//				return;
//			}
				//校验参数实体所需的必须属性
				checkParams(waybillEntity,orgAdministrativeInfoEntity);
				//waybillEntity.setOrderChannel("TAOBAOJZ");
			
				LOGGER.info("天猫轨迹赋值开始....");
				//如果订单来源是天猫家装和家哇云，也往天猫轨迹表给数据
				entity.setId(UUIDUtils.getUUID());//id
				//运单号
				entity.setWayBillNo(waybillEntity.getWaybillNo());
				//订单号
				entity.setChannelOrder(waybillEntity.getOrderNo());
				//签收时间
				entity.setOperaTime(dateTime);
				//跟踪信息描述 （天猫和家哇云是零担需求，不考虑签收人类型）
				entity.setTrackInfo("签收成功，签收人："+(StringUtils.isNotEmpty(signEntity.getDeliverymanName())?signEntity.getDeliverymanName():null));
				//发生城市
				entity.setOperateCity(orgAdministrativeInfoEntity.getCityName());
				//站点类型
				entity.setOrgType("1");
				//编号(到达部门编号)
				entity.setOrgCode(waybillEntity.getLastLoadOrgCode());
				//名称(到达部门名称)
				entity.setOrgName(orgAdministrativeInfoEntity.getName());
				//事件
				entity.setEventType("SIGNED");
				
				//开单件数
				entity.setGoodsQtyTotal(waybillEntity.getGoodsQtyTotal().toString());
				//订单来源
				entity.setOrderChannel(waybillEntity.getOrderChannel());
				//到达部门code
				entity.setArriveOrgCode(waybillEntity.getLastLoadOrgCode());
				//到达部门名称
				entity.setArriveOrgName(orgAdministrativeInfoEntity.getName());
				//到达城市
				entity.setArriveCity(orgAdministrativeInfoEntity.getCityName());
				//产品类型--运输性质
				entity.setProductCode(waybillEntity.getProductCode());
				//是否快递
				entity.setExpressIs(waybillEntity.getIsExpress());
				//淘宝家装
				if("TAOBAOJZ".equals(waybillEntity.getOrderChannel())){
					entity.setRecvMethod(waybillEntity.getReceiveMethod());
					entity.setEventType("SIGNSUCCESS");
				}
				//调用接口
				pushTrackForCaiNiaoService.addCarGoTrack(entity);
				LOGGER.info("天猫轨迹推送结束...."+entity.toString());
			}
			//天猫轨迹结束
		}
		//天猫家装和家哇云 既要推给家装轨迹也要通过给菜鸟轨迹给官网
		//验证运单是否符合条件，符合推送轨迹，不符合不做任何处理
		if(isFitConditionForWQS(signEntity,waybillEntity)){
			//校验参数实体所需的必须属性
			checkParams(waybillEntity,orgAdministrativeInfoEntity);
				
			//wqs菜鸟轨迹开始
			synTrackingEntity.setId(UUIDUtils.getUUID());
			//运单号
			synTrackingEntity.setWayBillNo(waybillEntity.getWaybillNo());
			//物流订单号(渠道订单号)
			synTrackingEntity.setOrderNo(waybillEntity.getOrderNo());

			//操作时间--签收时间
			synTrackingEntity.setOperateTime(dateTime);
			//签收人--有签收人为空的情况    DN201511090005 添加“签收人类型”字段  243921
			if(StringUtils.isNotEmpty(signEntity.getDeliverymanName())){
				synTrackingEntity.setSigner(signEntity.getDeliverymanName());
				//跟踪信息描述
				synTrackingEntity.setTrackInfo("签收成功，签收人：" + signEntity.getDeliverymanName());
			}else if(StringUtils.isNotEmpty(signEntity.getDeliverymanType())){
				synTrackingEntity.setSigner(DictUtil.rendererSubmitToDisplay(signEntity.getDeliverymanType(), DictionaryConstants.PKP_SIGN_PERSON_TYPE));
				//跟踪信息描述
				synTrackingEntity.setTrackInfo("签收成功，签收人：" + DictUtil.rendererSubmitToDisplay(signEntity.getDeliverymanType(), DictionaryConstants.PKP_SIGN_PERSON_TYPE));
			}else{
				synTrackingEntity.setSigner("代理");
				//跟踪信息描述
				synTrackingEntity.setTrackInfo("签收成功，签收人：代理");
			}
			//发生城市
			synTrackingEntity.setOperateCity(orgAdministrativeInfoEntity.getCityName());
			//站点类型
			synTrackingEntity.setOrgType("1");
			//编号(到达部门编号)
			synTrackingEntity.setOrgCode(waybillEntity.getLastLoadOrgCode());
			//名称(到达部门名称)
			synTrackingEntity.setOrgName(orgAdministrativeInfoEntity.getName());
			//事件 轨迹接口迁移添加“部分签收”、“异常签收”轨迹接口 by 243921
			if(SignConstants.SIGN_STATUS_ALL.equals(signEntity.getSignStatus())){//全部签收
				if(SignConstants.NORMAL_SIGN.equals(signEntity.getSignSituation())){//正常签收
					synTrackingEntity.setEventType("SIGNED");
				}else if(StringUtils.contains(signEntity.getSignSituation(),"UNNORMAL")){//异常签收
					//synTrackingEntity.setEventType(WaybillTrackingsConstants.OPERATE_TYPE_EXCEPTION_SIGN);
					synTrackingEntity.setEventType("SIGNED");
					//跟踪信息描述
					synTrackingEntity.setTrackInfo("异常签收");
				}
			}else if(SignConstants.SIGN_STATUS_PARTIAL.equals(signEntity.getSignStatus())){//部分签收
				synTrackingEntity.setEventType(WaybillTrackingsConstants.OPERATE_TYPE_PART_SIGN);
			}
			//目的站
			synTrackingEntity.setDestinationDeptName(orgAdministrativeInfoEntity.getName());
			//目的部门所在城市名称
			synTrackingEntity.setDestinationCityName(orgAdministrativeInfoEntity.getCityName());
			//订单来源
			synTrackingEntity.setOrderChannel(waybillEntity.getOrderChannel());
			//产品类型--运输性质
			synTrackingEntity.setProductCode(waybillEntity.getProductCode());
			//创建时间
			synTrackingEntity.setCreateDate(dateTime);
			//调用接口
			//签收轨迹新增同步轨迹表 DN201609280013   add by 321603
			//pushTrackForCaiNiaoService.addSynTrack(synTrackingEntity);
			pushTrackForCaiNiaoService.addSynLpsTrack(synTrackingEntity);
			LOGGER.info("菜鸟轨迹推送结束....");
		} 
	}
	
	
	/**
	 * 将通知客户的信息传到中转 参数:通知客户实体
	 */
	@Override
	public void rookieTrackingByNotification(NotificationEntity entity) {
		LOGGER.info("将通知客户实体封装成菜鸟轨迹开始...");
		if (null == entity) {
			LOGGER.info("通知客户实体不能为空...");
			return;
		}
		// 菜鸟待同步实体
		SynTrackingEntity trackEntity = new SynTrackingEntity();
		// 运单实体
		WaybillEntity waybillEntity = waybillManagerService
				.queryWaybillBasicByNo(entity.getWaybillNo());
		// 组织信息实体
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCodeNoCache(waybillEntity
						.getLastLoadOrgCode());
		Date date = new Date();
		//目的站
		trackEntity.setDestinationDeptName(orgAdministrativeInfoEntity.getName());
		//目的城市
		trackEntity.setDestinationCityName(orgAdministrativeInfoEntity.getCityName());
		// 菜鸟轨迹赋值开始
		trackEntity.setId(UUIDUtils.getUUID());
		// 运单号
		trackEntity.setWayBillNo(entity.getWaybillNo());
		// 订单号
		trackEntity.setOrderNo(waybillEntity.getOrderNo());
		// 操作时间
		trackEntity.setOperateTime(date);
		// 发生城市
		trackEntity.setOperateCity(orgAdministrativeInfoEntity.getCityName());
		// 站点类型
		trackEntity.setOrgType("1");		
		//轨迹信息,通知结果
		if(entity.getNoticeType()!=null&&!entity.getNoticeType().equals("")){
			if(entity.getNoticeType().trim().equals(NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE)){
				//通知结果有两种，success|failure
				if(entity.getNoticeResult().trim().equals("SUCCESS")){
					trackEntity.setTrackInfo("客户通知，电话_通知成功");
				}else if(entity.getNoticeResult().trim().equals("FAILURE")){
					trackEntity.setTrackInfo("客户通知,电话_通知失败");
				}
			}else if(entity.getNoticeType().trim().equals(NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE)){
				if(entity.getNoticeResult().trim().equals("SUCCESS")){
					trackEntity.setTrackInfo("客户通知,语音_通知成功");
				}else if(entity.getNoticeResult().trim().equals("FAILURE")){
					trackEntity.setTrackInfo("客户通知,语音_通知失败");
				}
			}else if(entity.getNoticeType().trim().equals(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE)){
				if(entity.getNoticeResult().trim().equals("SUCCESS")){
					trackEntity.setTrackInfo("客户通知,短信_通知成功");
				}else if(entity.getNoticeResult().trim().equals("FAILURE")){
					trackEntity.setTrackInfo("客户通知,短信_通知失败");
				}	
		}
			}
		// 编号(到达部门编号)
		trackEntity.setOrgCode(waybillEntity.getLastLoadOrgCode());
		// 名称(到达部门名称)
		trackEntity.setOrgName(orgAdministrativeInfoEntity.getName());
		trackEntity.setOrderChannel(waybillEntity.getOrderChannel());
		// 产品类型--运输性质
		trackEntity.setProductCode(waybillEntity.getProductCode());
		// 创建时间
		trackEntity.setCreateDate(date);
		// 操作类型
		if (StringUtil.equals(FossConstants.YES, entity.getIsPreNotify())) {
			trackEntity.setEventType(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY_PRE);
			trackEntity.setOperateContent("通知成功,提前通知...");
		} else if (waybillEntity.getReceiveMethod() != null
				&& waybillEntity.getReceiveMethod().startsWith(WaybillConstants.DELIVER_FREE)) {
			trackEntity.setEventType(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY_DELIVER);
			trackEntity.setOperateContent("通知成功,送货上门...");
		} else {
			// 自取
			trackEntity.setEventType(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY_PICKUP);
			trackEntity.setOperateContent("通知成功,自取...");
		}
		// 调用接口
		pushTrackForCaiNiaoService.addSynTrack(trackEntity);
		LOGGER.info("菜鸟轨迹推送结束....");
	}
	
	/**
	 * 校验运单是否符合条件：
	 *  1.全部签收；
	 *  2.订单来源TAOBAO(快递)；TAOBAOJZ不区分零担快递
	 *  3.原需求是只发快递的，加入淘宝家装后 TAOBAOJZ部分零担快递都给轨迹
	 *  4.不是反签收后的订单；
	 */
	private boolean isFitCondition(WaybillSignResultEntity signEntity,WaybillEntity wayEntity){
		//全部签收否
		if(!SignConstants.SIGN_STATUS_ALL.equals(signEntity.getSignStatus())){
			return false;
		}
/*		//快递否
		if(!wayEntity.getProductCode().equals(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE) 
				&& !wayEntity.getProductCode().equals(ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE)){
			return false;
		}
		//订单来源 -- 淘宝家装 //淘宝
		if(!"TAOBAOJZ".equals(wayEntity.getOrderChannel())){ //&&!wayEntity.getOrderChannel().equals("TAOBAOJZ")
			return false;
		}*/
		
		//运单号
		String waybillNo = signEntity.getWaybillNo();
		//反签收状态
		String status = "CHANGE_SIGN_PASSED";
		//反签收表中的记录集合
		List<SignRfcEntity> list = signChangeService.queryWayBillRfcReverseList(waybillNo, status);
		if(CollectionUtils.isNotEmpty(list)){ //有反签收记录 
			for(SignRfcEntity rfcEntity:list){
				//反签收，不发送轨迹；RfcType包含REVERSESIGN时是反签收(包括反结清，反到达联，反签收结果)，其他为变更
				if(rfcEntity.getRfcType().contains("REVERSESIGN") && 
						(SignConstants.REVERSE_SIGN_TYPE_ARRIVESHEET.equals(rfcEntity.getRfcDetailType()) || 
								SignConstants.REVERSE_SIGN_TYPE_WAYBILLSIGNRESULT.equals(rfcEntity.getRfcDetailType()))){
					return false;
				}
			}
		}
		//符合条件（1.全部签收；2.快递；3.订单来源TAOBAO;4.不是反签收后的订单；）
		//2,3因需求加入淘宝家装(家装不分零担快递)，在上边单独判断了
		return true;
	}
	/**
	 * 校验运单是否符合条件：
	 *  1.全部签收、部分签收、异常签收；
	 *  2.订单来源TAOBAO(快递)；TAOBAOJZ不区分零担快递
	 *  3.原需求是只发快递的，加入淘宝家装后 TAOBAOJZ部分零担快递都给轨迹
	 *  4.不是反签收后的订单；
	 */
	private boolean isFitConditionForWQS(WaybillSignResultEntity signEntity,WaybillEntity wayEntity){
		//运单号
		String waybillNo = signEntity.getWaybillNo();
		//反签收状态
		String status = "CHANGE_SIGN_PASSED";
		//反签收表中的记录集合
		List<SignRfcEntity> list = signChangeService.queryWayBillRfcReverseList(waybillNo, status);
		if(CollectionUtils.isNotEmpty(list)){ //有反签收记录 
			for(SignRfcEntity rfcEntity:list){
				//反签收，不发送轨迹；RfcType包含REVERSESIGN时是反签收(包括反结清，反到达联，反签收结果)，其他为变更
				if(rfcEntity.getRfcType().contains("REVERSESIGN") && 
						(SignConstants.REVERSE_SIGN_TYPE_ARRIVESHEET.equals(rfcEntity.getRfcDetailType()) || 
								SignConstants.REVERSE_SIGN_TYPE_WAYBILLSIGNRESULT.equals(rfcEntity.getRfcDetailType()))){
					return false;
				}
			}
		}
		//符合条件（1.签收；2.快递；3.订单来源TAOBAO;4.不是反签收后的订单；）
		//2,3因需求加入淘宝家装(家装不分零担快递)，在上边单独判断了
		return true;
	}
	private void checkParams(WaybillEntity waybillEntity,OrgAdministrativeInfoEntity orgAdministrativeInfoEntity){
		//运单号
		if(waybillEntity.getWaybillNo() == null){
			LOGGER.info("菜鸟轨迹推送，运单号不能为空");
			return;
		}
		//到达部门编号
		if(waybillEntity.getLastLoadOrgCode() == null){
			LOGGER.info("菜鸟轨迹推送，到达部门编号不能为空");
			return;
		}
		//到达部门名称
		if(orgAdministrativeInfoEntity.getName() == null){
			LOGGER.info("菜鸟轨迹推送，到达部门名称不能为空");
			return;
		}
		//城市
		if(orgAdministrativeInfoEntity.getCityName() == null){
			LOGGER.info("菜鸟轨迹推送，到达部门城市不能为空");
			return;
		}
	}
	/**
	 * WQS(菜鸟)轨迹 推送:派送拉回
	 * 赋值，调用方法推送轨迹
	 * @author 243921_zhangtingitng
	 * 2016-2-22 上午8:48:34
	 */
	@Override
	public void rookieTrackingsForDeliveryBack(ArriveSheetEntity entity) {
		LOGGER.info("菜鸟轨迹开始：1，赋值；2，调轨迹接口....");
		//菜鸟轨迹 待同步轨迹实体
		SynTrackingEntity synTrackingEntity=new SynTrackingEntity();
		Date dateTime = new Date();//获取当前操作时间
		//运单实体
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(entity.getWaybillNo());
		//组织信息实体
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCodeNoCache(waybillEntity.getLastLoadOrgCode());
		if(entity == null || waybillEntity == null || orgAdministrativeInfoEntity == null){
			LOGGER.info("菜鸟轨迹推送，必须参数不能为空");
			return;
		}
		//校验参数实体所需的必须属性
		checkParams(waybillEntity,orgAdministrativeInfoEntity);
		//wqs菜鸟轨迹开始
		synTrackingEntity.setId(UUIDUtils.getUUID());
		//运单号
		synTrackingEntity.setWayBillNo(waybillEntity.getWaybillNo());
		//物流订单号(渠道订单号)
		synTrackingEntity.setOrderNo(waybillEntity.getOrderNo());
		// 操作人姓名
		synTrackingEntity.setOperatorName(entity.getOperator());
		//操作时间--拉回时间
		synTrackingEntity.setOperateTime(dateTime);
		//操作内容--派送拉回的原因
		synTrackingEntity.setOperateContent(DictUtil.rendererSubmitToDisplay(entity.getSignNote(), DictionaryConstants.PKP_PULLBACK_REASON));
		//发生城市
		synTrackingEntity.setOperateCity(orgAdministrativeInfoEntity.getCityName());
		//站点类型
		synTrackingEntity.setOrgType("1");
		//目的站
		synTrackingEntity.setDestinationDeptName(orgAdministrativeInfoEntity.getName());
		//编号(到达部门编号)
		
		
		synTrackingEntity.setOrgCode(waybillEntity.getLastLoadOrgCode());
		//名称(到达部门名称)
		synTrackingEntity.setOrgName(orgAdministrativeInfoEntity.getName());
		//事件  派送拉回轨迹接口 by 243921
		synTrackingEntity.setEventType("ERROR");
		//跟踪信息描述--modify by 353654 DN201608120002
		synTrackingEntity.setTrackInfo("派送拉回，"+DictUtil.rendererSubmitToDisplay(entity.getSignNote(), DictionaryConstants.PKP_PULLBACK_REASON));
		//订单来源
		synTrackingEntity.setOrderChannel(waybillEntity.getOrderChannel());
		//产品类型--运输性质
		synTrackingEntity.setProductCode(waybillEntity.getProductCode());
		//创建时间
		synTrackingEntity.setCreateDate(dateTime);
		//调用接口
		pushTrackForCaiNiaoService.addSynTrack(synTrackingEntity);
		LOGGER.info("菜鸟轨迹推送结束....");
	}
	/**
	 * 中转货物轨迹接口(对接快递100)  注入
	 * @param employeeService the employeeService to set
	 */
	public void setWaybillTrackingsService(
				IWaybillTrackingsService waybillTrackingsService) {
			this.waybillTrackingsService = waybillTrackingsService;
		}
	/**
	 * 运单管理接口 注入
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 * 签收变更结果接口 注入
	 */
	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}
	/**
	 * 组织信息 注入
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
}
