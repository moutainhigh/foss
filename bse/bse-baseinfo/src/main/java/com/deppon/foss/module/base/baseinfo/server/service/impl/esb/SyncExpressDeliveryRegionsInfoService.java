package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.inteface.domain.crm.DeliveryRegionInfo;
import com.deppon.esb.inteface.domain.crm.DeliveryRegionRequest;
import com.deppon.esb.pojo.transformer.jaxb.ExpressScopeListRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.KdpsSyncRequestTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendExpressDeliveryRegionsInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryRegionsEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.ows.inteface.domain.express.ExpressScope;
import com.deppon.ows.inteface.domain.express.ExpressScopeListRequest;
 
public class SyncExpressDeliveryRegionsInfoService implements
		ISendExpressDeliveryRegionsInfoService {
	// 记录日志
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncExpressDeliveryRegionsInfoService.class);
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_QUERY_SENDANDRECEIVE_RANGE";
	
	//推送给crm和gis的服务编码
	private static final String ESB_SERVICE_CODE_TO_CRM = "ESB_FOSS2ESB_EXPRESS_AREA_DATA";
	private static final String VERSION = "1.0";
    /**
     *esb响应超时日志记录
     */
    private IEsbErrorLoggingDao esbErrorLoggingDao;
	public void setEsbErrorLoggingDao(IEsbErrorLoggingDao esbErrorLoggingDao) {
		this.esbErrorLoggingDao = esbErrorLoggingDao;
	}

	@Override
	public void synRegionsToGW(List<ExpressDeliveryRegionsEntity> entitys,
			String operateType) {

		if (CollectionUtils.isEmpty(entitys)) {
			throw new BusinessException("传入的对象为空");
		}
		//ExpressScopeListRequest request = new ExpressScopeListRequest();
//		for (ExpressDeliveryRegionsEntity entity : entitys) {
//			if (!StringUtils.equals(entity.getDeliveryNature(),
//					"DELIVERY_NATURE_ZTBPS")) {
//				DataDictionaryValueEntity valueEntity = dataDictionaryValueService
//						.queryDataDictionaryValueByTermsCodeValueCode(
//								"DELIVERY_NATURE", entity.getDeliveryNature());
//			}
//		}
		// 实体
		ExpressScopeListRequest request = convertEsbEntity(entitys, operateType);
		// 创建服务头信息
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUID.randomUUID().toString());
		accessHeader
				.setBusinessDesc1("send ExpressDeliveryRegions to GW System ：同步快递派送区域数据到官网 \n");
		accessHeader.setVersion("0.1");
		long startTime = System.currentTimeMillis();
		EsbErrorLogging log = new EsbErrorLogging();
		try {
			log.setParamenter(new ExpressScopeListRequestTrans().fromMessage(request));
			log.setRequestSystem("ESB");
			log.setMethodDesc("同步快递派送区域到官网");
			log.setRequestTime(new Date());
			log.setMethodName(this.getClass().getName()+".synRegionsToGW");
			LOGGER.info("开始调用 同步快递派送区域：\n"
					+ new ExpressScopeListRequestTrans().fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
            long responseTime = (System.currentTimeMillis()-startTime)/NumberConstants.NUMBER_1000; 
			if(responseTime>FossConstants.MAX_RESPONSE_TIME){
				log.setErrorMessage("响应超时");
				log.setCreateTime(new Date());
				log.setResponseTime(responseTime);
				esbErrorLoggingDao.addErrorMessage(log);
			}
            LOGGER.info("结束调用 同步快递派送区域：\n"
					+ new ExpressScopeListRequestTrans().fromMessage(request));
		} catch (ESBException e) {
			//e.printStackTrace();
			log.setResponseTime((System.currentTimeMillis()-startTime));
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.setCreateTime(new Date());
			log.setErrorMessage(sw.toString());
			esbErrorLoggingDao.addErrorMessage(log);
			throw new BusinessException("发送数据至esb发生异常",e);
		} 
		LOGGER.info("send ExpressDeliveryRegions to GW System ：同步数据到官网 \n");
	}

	/**
	 * 将foss实体转换成esb 对象
	 * 
	 * @param entitys
	 * @param operateType
	 * @return
	 */
	private ExpressScopeListRequest convertEsbEntity(
			List<ExpressDeliveryRegionsEntity> entitys, String operateType) {
		ExpressScopeListRequest request = new ExpressScopeListRequest();
		// 非空校验
		if (CollectionUtils.isNotEmpty(entitys)) {
			for (ExpressDeliveryRegionsEntity expressDeliveryRegionsEntity : entitys) {
				// 实体转换
				ExpressScope expressScope = new ExpressScope();
				expressScope
						.setAreaCode(expressDeliveryRegionsEntity.getCode());
				expressScope
						.setAreaName(expressDeliveryRegionsEntity.getName());
				expressScope.setCityCode(expressDeliveryRegionsEntity
						.getParentDistrictCode());
				//非空校验
				if(StringUtils.isNotBlank(expressDeliveryRegionsEntity.getRemarkDe())){
					expressScope.setConAreaDesc(expressDeliveryRegionsEntity
							.getRemarkDe());
				}else{
					expressScope.setConAreaDesc("无");
				}
				//非空校验
				if(StringUtils.isNotBlank(expressDeliveryRegionsEntity.getRemark())){
					expressScope.setSendAreaDesc(expressDeliveryRegionsEntity
							.getRemark());
				}else{
					expressScope.setSendAreaDesc("无");
				}
				//非空校验
				if(StringUtils.isNotBlank(expressDeliveryRegionsEntity.getSpecialArea())){
					expressScope.setSpecialAreaDesc(expressDeliveryRegionsEntity
							.getSpecialArea());
				}else{
					expressScope.setSpecialAreaDesc("无");
				}
				
				expressScope.setOperation(operateType);
				//
				request.getExpressScope().add(expressScope);
			}
		}
		return request;
	}

	@Override
	public void synRegionsToCRM(List<ExpressDeliveryRegionsEntity> entitys,
			int operateType) {
		if (CollectionUtils.isEmpty(entitys)) {
			throw new BusinessException("传入的对象为空");
		}
		DeliveryRegionRequest request = converter(entitys,operateType);
		// 创建服务头信息
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE_TO_CRM);
		accessHeader.setBusinessId(UUID.randomUUID().toString());
		accessHeader.setBusinessDesc1("同步快递派送区域基础资料到crm");
		accessHeader.setBusinessDesc2("同步快递派送区域基础资料到gis");
		accessHeader.setVersion(VERSION);
		long startTime = System.currentTimeMillis();
		EsbErrorLogging log = new EsbErrorLogging();
		try {
			log.setParamenter(new KdpsSyncRequestTrans().fromMessage(request));
			log.setRequestSystem("ESB");
			log.setMethodDesc("同步快递派送区域至CRM");
			log.setRequestTime( new Date());
			log.setMethodName(this.getClass().getName()+".synRegionsToCRM");
			LOGGER.info("开始调用 同步快递派送区域至CRM：\n"
					+ new KdpsSyncRequestTrans().fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			  long responseTime = System.currentTimeMillis()-startTime; 
				if(responseTime>FossConstants.MAX_RESPONSE_TIME){
					log.setErrorMessage("响应超时");
					log.setCreateTime(new Date());
					log.setResponseTime(responseTime);
					esbErrorLoggingDao.addErrorMessage(log);
				}
			LOGGER.info("结束调用 同步快递派送区域至CRM：\n"
					+ new KdpsSyncRequestTrans().fromMessage(request));
		} catch (ESBException e) {
			log.setResponseTime(System.currentTimeMillis()-startTime);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			log.setCreateTime(new Date());
			e.printStackTrace(pw);
			log.setErrorMessage(sw.toString());
			esbErrorLoggingDao.addErrorMessage(log);
			throw new BusinessException("发送数据至esb发生异常",e);
		}
		LOGGER.info("send ExpressDeliveryRegions to GW System ：同步数据到CRM \n");
	}

	
	//推送给crm的转换类
	private DeliveryRegionRequest converter(List<ExpressDeliveryRegionsEntity> entitys,
			int operateType){
		DeliveryRegionRequest request = new DeliveryRegionRequest();
		List<DeliveryRegionInfo> deliveryRegionInfos = request.getDeliveryRegionInfos();
		DeliveryRegionInfo deliveryRegionInfo = null;
		for(ExpressDeliveryRegionsEntity entity:entitys){
			deliveryRegionInfo = new DeliveryRegionInfo();
			//设置区域编码
			deliveryRegionInfo.setRegionCode(entity.getCode());
			//设置区域名称
			deliveryRegionInfo.setRegionName(entity.getName());
			//上级行政区域编码
			deliveryRegionInfo.setParentCode(entity.getParentDistrictCode());
			//上级行政区域名称
			deliveryRegionInfo.setParentName(entity.getParentDistrictName());
			//行政级别
			deliveryRegionInfo.setDegree(entity.getDegree());
			//操作标识(1、新增；2、修改；3、删除)
			deliveryRegionInfo.setOperatorSign(operateType);
			//虚拟行政区域
			deliveryRegionInfo.setVirtualDistinct(entity.getVirtualDistrictId());
			//区号
			deliveryRegionInfo.setAreaCode(entity.getAreaCode());
			//非标准派送时效
			deliveryRegionInfo.setNonDeliveryTime(entity.getNonStandardDeliveryTime());
			//派送属性
			deliveryRegionInfo.setDeliveryNature(entity.getDeliveryNature());
			//所属营业部编码
			deliveryRegionInfo.setYybCode(entity.getExpressSalesDeptCode());
			//所属营业部名称
			deliveryRegionInfo.setYybName(entity.getExpressSalesDeptName());
			//派送区域
			deliveryRegionInfo.setPjRegion(entity.getRemark());
			//收件区域
			deliveryRegionInfo.setSjRegion(entity.getRemarkDe());
			//数据库版本号
			deliveryRegionInfo.setDbVersionCode(entity.getVersionNo()+"");
			//是否启用
			deliveryRegionInfo.setAviailable(entity.getActive());
			//创建时间
			deliveryRegionInfo.setCreateTime(entity.getCreateDate());
			//创建人
			deliveryRegionInfo.setCreateUser(entity.getCreateUser());
			//修改人
			deliveryRegionInfo.setModifyUser(entity.getModifyUser());
			//修改时间
			deliveryRegionInfo.setModifyTime(entity.getModifyDate());
			//是否大客户全境
			deliveryRegionInfo.setIsBigCustomerOwnRegion(entity.getIsBigCustomerOwnRegion());
			deliveryRegionInfos.add(deliveryRegionInfo);
		}
		return request;
	}
}
