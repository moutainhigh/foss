package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.deppon.gis.inteface.domain.CollectAddressResponse;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
//import com.deppon.esb.api.ESBJMSAccessor;
//import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.oms.CourierSend2OmsRequest;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IAddressCollectionEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IGisAddressCollectionService;
import com.deppon.foss.module.pickup.order.api.server.service.IPdaAddressCollectionService;
import com.deppon.foss.module.pickup.order.api.shared.define.AddressCollectionConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.AddressCollectionEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.AddressCollectionRetDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.GpsAddressCollectDto;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.gis.gisservice.CommonException;
import com.opensymphony.xwork2.inject.Inject;


public class PdaAddressCollectionService implements IPdaAddressCollectionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PdaAddressCollectionService.class);
	/**
	 * 地址信息Dao
	 * */
	private IAddressCollectionEntityDao addressCollectionEntityDao;
	private IGisAddressCollectionService gisAddressCollectionService;
	/**
	 * 无效参数异常编码
	 */
	public static final String ERRCODE_SYS_INVALID_ARG = "BDM-1101";
	
	public static final String ESB_FOSS2ESB_DRIVER_SIGN = "ESB_FOSS2ESB_DRIVER_SIGN";
	public static final String SYSTEM_CONFIG_PARM__PKP = "SYSTEM_CONFIG_PARM__PKP";
	public static final String REMOVE_EXPRESS_CHECK_SWITCH = "REMOVE_EXPRESS_CHECK_SWITCH";
    
	/**
	 * 下面是dao对象的声明及get,set方法：
	 */
	@Inject
	private IConfigurationParamsDao configurationParamsDao;


	public void setConfigurationParamsDao(
			IConfigurationParamsDao configurationParamsDao) {
		this.configurationParamsDao = configurationParamsDao;
	}
	
	
	/***
	 * author lianghaisheng
	 * 功能：记录PDA采集信息服务
	 **/
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public AddressCollectionRetDto fossAddressCollection(GpsAddressCollectDto gpsAddressCollectionDto) {
		//封装传递参数
		AddressCollectionEntity entity = new AddressCollectionEntity();
		//封装返回结果
		AddressCollectionRetDto retDto = new AddressCollectionRetDto();
		//生成相关ID记录使用
		String entityId = UUID.randomUUID().toString();
		entity.setId(entityId);
		
		//运单号不能为空
		if(gpsAddressCollectionDto.getBillNo() == null 
				|| "".equals(gpsAddressCollectionDto.getBillNo())){
			retDto.setErrMessage("订单号或者运单号为空！");
			retDto.setRetStatus(AddressCollectionConstants.FAILED);
			throw new BusinessException(ERRCODE_SYS_INVALID_ARG, "订单号或者运单号为空！");
		}
		//经纬度不能为空
		if(gpsAddressCollectionDto.getGpsLatitude() == null
				|| "".equals(gpsAddressCollectionDto.getGpsLatitude())){
			retDto.setErrMessage("采集地址信息经度为空！");
			retDto.setRetStatus(AddressCollectionConstants.FAILED);
			throw new BusinessException(ERRCODE_SYS_INVALID_ARG, "采集地址信息经度为空！");
		}
		if(gpsAddressCollectionDto.getGpsLongitude() == null
				|| "".equals(gpsAddressCollectionDto.getGpsLongitude() )){
			retDto.setErrMessage("采集地址信息纬度为空！");
			retDto.setRetStatus(AddressCollectionConstants.FAILED);
			throw new BusinessException(ERRCODE_SYS_INVALID_ARG, "采集地址信息纬度为空！");
		}
		
		if(gpsAddressCollectionDto.getCollectTime() == null){
			retDto.setErrMessage("采集时间为空！");
			retDto.setRetStatus(AddressCollectionConstants.FAILED);
			throw new BusinessException(ERRCODE_SYS_INVALID_ARG, "采集时间为空！");
		}
		//地址类型是否在范围之内
		if(gpsAddressCollectionDto.getAddressType() != null &&
				(!"".equals(gpsAddressCollectionDto.getAddressType()))){
			//判断地址是否在范围之内
			if(!AddressCollectionConstants.LTL_DELIVER.equals(gpsAddressCollectionDto.getAddressType())
					&&!AddressCollectionConstants.LTL_PICKUP.equals(gpsAddressCollectionDto.getAddressType())
					 &&!AddressCollectionConstants.PACAKGE_DELIVER.equals(gpsAddressCollectionDto.getAddressType())
					 &&!AddressCollectionConstants.PACKAGE_PICKUP.equals(gpsAddressCollectionDto.getAddressType())){
				    retDto.setErrMessage("地址类型为："+gpsAddressCollectionDto.getAddressType()+"不在范围之内！");
				    retDto.setRetStatus(AddressCollectionConstants.FAILED);
					throw new BusinessException(ERRCODE_SYS_INVALID_ARG, gpsAddressCollectionDto.getBillNo()+"地址类型为："+gpsAddressCollectionDto.getAddressType()+"不在范围之内！");
					 }
		}else{
			retDto.setErrMessage("地址类型为空！");
			throw new BusinessException(ERRCODE_SYS_INVALID_ARG,"地址类型为空！");
		}
		LOGGER.info("地址采集信息记录进入FOSS:"+gpsAddressCollectionDto.getBillNo());
		buildAddressEntity(entity,gpsAddressCollectionDto);
		//插入进入数据库
		addressCollectionEntityDao.insertSelective(entity);		
		try {
			CollectAddressResponse response =   gisAddressCollectionService.fossToGisAddressInfo(entity);
			//如果正常返回则记录相关信息
			if(response.isIsSuccess()){
			entity = new AddressCollectionEntity();
			entity.setId(entityId);
			entity.setScopeoordinatesId(response.getAddressID());
			entity.setModifyDate(new Date());
			addressCollectionEntityDao.updateByPrimaryKeySelective(entity);
			retDto.setRetStatus(AddressCollectionConstants.SUCCED);
			LOGGER.info("地址采集信息记录完成:"+gpsAddressCollectionDto.getBillNo());
			}else{
				throw new BusinessException(ERRCODE_SYS_INVALID_ARG,"GIS入库失败！");
			}
		} catch (CommonException e) {
			//异常处理
			retDto.setRetStatus(AddressCollectionConstants.FAILED);
			retDto.setErrMessage(e.getMessage());
			throw new BusinessException(ERRCODE_SYS_INVALID_ARG,"GIS入库失败！");
		}
		return retDto;
	}
	/**
	 * author lianghaisheng
	 * 封装存入数据库的实体
	 * */
	private AddressCollectionEntity buildAddressEntity(AddressCollectionEntity entity ,
			GpsAddressCollectDto gpsAddressCollectionDto){
		entity.setBillNo(gpsAddressCollectionDto.getBillNo());
		entity.setAddressType(gpsAddressCollectionDto.getAddressType());
		entity.setDriverCode(gpsAddressCollectionDto.getDriverCode());
		entity.setDriverDept(gpsAddressCollectionDto.getDriverDept());
		entity.setGpsLatitude(gpsAddressCollectionDto.getGpsLatitude());
		entity.setGpsLongitude(gpsAddressCollectionDto.getGpsLongitude());
		entity.setCollectionTime(gpsAddressCollectionDto.getCollectTime());
		entity.setCreateDate(new Date());
		entity.setCreateUser(gpsAddressCollectionDto.getDriverCode());
		return entity;
	}
	
	/**
	 * 推送pda司机签到信息给oms
	 * @param pdaEntity
	 * @return
	 */
	@Override
	public void sendModifySignInfo(PdaSignEntity pdaEntity) {
		LOGGER.info("OMS PDA签到数据JMS服务 sendModifySignInfo start : " + ReflectionToStringBuilder.toString(pdaEntity));
		//判断是否为空
		if (pdaEntity != null) {
			// 准备消息头信息
			AccessHeader header = new AccessHeader();
			header.setEsbServiceCode(ESB_FOSS2ESB_DRIVER_SIGN);
			header.setVersion("1.0");
			header.setBusinessId(pdaEntity.getDriverCode());

			CourierSend2OmsRequest req = new CourierSend2OmsRequest();
			req.setId(pdaEntity.getId());
			req.setDeviceNo(pdaEntity.getDeviceNo());
			req.setCourierName(pdaEntity.getDriverName());
			req.setCourierCode(pdaEntity.getDriverCode());
			req.setVehicleNo(pdaEntity.getVehicleNo());
			req.setCreateTime(pdaEntity.getCreateTime());
			req.setUnbundler(pdaEntity.getUnbundler());
			req.setUnbundlerCode(pdaEntity.getUnbundlerCode());
			req.setUnbundleReason(pdaEntity.getUnbundleReason());
			req.setUnbundleTime(pdaEntity.getUnbundleTime());
			req.setStatus(pdaEntity.getStatus());
			req.setUserType(pdaEntity.getUserType());
			req.setOrgCode(pdaEntity.getOrgCode());
			LOGGER.info("OMS PDA签到数据JMS服务 sendModifySignInfo ing : " + ReflectionToStringBuilder.toString(req));
			// 发送请求
			try {
				ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
				entity.setConfType(SYSTEM_CONFIG_PARM__PKP);
				entity.setCode(REMOVE_EXPRESS_CHECK_SWITCH);
				entity.setOrgCode(FossConstants.ROOT_ORG_CODE);
				entity.setActive(FossConstants.YES);
				String value = configurationParamsDao.querySysConfig(entity);
				LOGGER.info("根据配置项代码等条件查询 解除快递员签到接口开关的状态 配置项的值(CONF_VALUE)，从DB取值为" + value);
			 
				if(value != null && ! "".equals(value )){
					if(FossConstants.YES.equals(value)){
						ESBJMSAccessor.asynReqeust(header, req);
					}
				}

			} catch (Exception e) {
				// 对异常进行处理
				LOGGER.error("PDA签到数据修改失败：", e);
			}
		} else {
			//写异常信息到日志中
			LOGGER.error("OMS PDA签到数据JMS服务修改失败：传入的实体为空");
		}
		LOGGER.info("OMS PDA签到数据JMS服务 sendModifySignInfo end : " + pdaEntity.getDeviceNo());
	}
	
	
	public IAddressCollectionEntityDao getAddressCollectionEntityDao() {
		return addressCollectionEntityDao;
	}
	public void setAddressCollectionEntityDao(
			IAddressCollectionEntityDao addressCollectionEntityDao) {
		this.addressCollectionEntityDao = addressCollectionEntityDao;
	}
	public IGisAddressCollectionService getGisAddressCollectionService() {
		return gisAddressCollectionService;
	}
	public void setGisAddressCollectionService(
			IGisAddressCollectionService gisAddressCollectionService) {
		this.gisAddressCollectionService = gisAddressCollectionService;
	}
	
	



}
