package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.inteface.domain.PackSupplierBaseInfo;
import com.deppon.cubc.inteface.domain.SyncPackSupplierRequest;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.pojo.transformer.jaxb.SyncPackSupplierRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendPackagingSupplierToCUBCService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

public class SyncPackagingSupplierToCUBCService implements ISendPackagingSupplierToCUBCService{
	
	private static final Logger log = LoggerFactory
			.getLogger(SyncInformationService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYN_PACKING";
	
	private static final String VERSION = "1.0";
	
	private IEsbErrorLoggingDao esbErrorLoggingDao;
	
	public void setEsbErrorLoggingDao(IEsbErrorLoggingDao esbErrorLoggingDao) {
		this.esbErrorLoggingDao = esbErrorLoggingDao;
	}
	
	/**
	 * 
	 * 同步包装商信息给CUBC
	 * 
	 * @author 269231
	 * @date 
	 */
	@Override
	public void SyncPackagingSupplier(
			List<PackagingSupplierEntity> packagingSupplier, Integer operateType) {
		// TODO Auto-generated method stub
		if (CollectionUtils.isEmpty(packagingSupplier)) {
			throw new BusinessException("传入的对象为空");
		}
		SyncPackSupplierRequest request = ConvertEsbEntity(packagingSupplier,operateType);
		
		// 创建服务头信息
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUID.randomUUID().toString());
		accessHeader.setBusinessDesc1("同步包装商信息给CUBC");
		accessHeader.setVersion(VERSION);
		long startTime = System.currentTimeMillis();
		EsbErrorLogging erlog = new EsbErrorLogging();
		try {
			erlog.setParamenter(new SyncPackSupplierRequestTrans().fromMessage(request));
			erlog.setRequestSystem("ESB");
			erlog.setRequestTime(new Date());
			erlog.setMethodDesc("同步包装商信息给CUBC");
			erlog.setMethodName(this.getClass().getName()+".SyncPackagingSupplier");
			log.info("开始调用 同步包装商信息给CUBC：\n"
					+ new SyncPackSupplierRequestTrans().fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			long responseTime = (System.currentTimeMillis()-startTime); 
			if(responseTime>FossConstants.MAX_RESPONSE_TIME){
				erlog.setCreateTime(new Date());
				erlog.setErrorMessage("响应超时");
				erlog.setResponseTime(responseTime);
				esbErrorLoggingDao.addErrorMessage(erlog);
			}
			log.info("结束调用 同步包装商信息给CUBC：\n"+ new SyncPackSupplierRequestTrans().fromMessage(request));
		} catch (ESBException e) {
			//esb发生异常未进行处理？？？？？是否要修改
			e.printStackTrace();
			log.error(e.getMessage(), e);
			erlog.setResponseTime(System.currentTimeMillis()-startTime);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			erlog.setCreateTime(new Date());
			e.printStackTrace(pw);
			erlog.setErrorMessage(sw.toString());
			esbErrorLoggingDao.addErrorMessage(erlog);
		}
		log.info("send ExpressDeliveryRegions to GW System ：同步包装商信息给CUBC \n");
	}

	private SyncPackSupplierRequest ConvertEsbEntity(
			List<PackagingSupplierEntity> packagingSupplier, Integer operateType) {
		// TODO Auto-generated method stub
		
		SyncPackSupplierRequest request = new SyncPackSupplierRequest();
		
		for(PackagingSupplierEntity entity : packagingSupplier){
			
			PackSupplierBaseInfo packSupplierBaseInfo = new PackSupplierBaseInfo();
			
			packSupplierBaseInfo.setAccount(entity.getAccount());
			packSupplierBaseInfo.setActive(entity.getActive());
			packSupplierBaseInfo.setBagLine(entity.getBagLine());
			packSupplierBaseInfo.setBreakageRate(entity.getBreakageRate());
			packSupplierBaseInfo.setBubblefilm(entity.getBubblefilm());
			packSupplierBaseInfo.setCreateDate(entity.getCreateDate());
			packSupplierBaseInfo.setCreateUser(entity.getCreateUser());
			packSupplierBaseInfo.setCusCode(entity.getCusCode());		
			packSupplierBaseInfo.setEffectiveDate(entity.getEffectiveDate());
			if(null != entity.getFactorBeginTime())
				packSupplierBaseInfo.setFactorBeginTime(new SimpleDateFormat("yyyy-MM-dd").format(entity.getFactorBeginTime()));			
			if(null != entity.getFactorEndTime())
				packSupplierBaseInfo.setFactorEndTime(new SimpleDateFormat("yyyy-MM-dd").format(entity.getFactorEndTime()));
			packSupplierBaseInfo.setFactoring(entity.getFactoring());
			packSupplierBaseInfo.setId(entity.getId());
			packSupplierBaseInfo.setModifyDate(entity.getModifyDate());
			packSupplierBaseInfo.setModifyUser(entity.getModifyUser());
			packSupplierBaseInfo.setOrg(entity.getOrgCode());		
			packSupplierBaseInfo.setOrgCode(entity.getOrgCodeCode());
			packSupplierBaseInfo.setPackagingSupplier(entity.getPackagingSupplier());				
			packSupplierBaseInfo.setPackagingSupplierCode(entity.getPackagingSupplierCode());
			packSupplierBaseInfo.setPackagingSupplierPhone(entity.getPackagingSupplierPhone());
			if(null != entity.getVersionNo())
				packSupplierBaseInfo.setVersionNo(entity.getVersionNo().toString());
			if(null != entity.getWood())
				packSupplierBaseInfo.setWood(entity.getWood());
			if(null != entity.getWoodBox())
				packSupplierBaseInfo.setWoodBox(entity.getWoodBox());
			if(null != entity.getWoodBoxMin())
				packSupplierBaseInfo.setWoodBoxMin(entity.getWoodBoxMin());		
			if(null != entity.getWoodBoxStartVolume())
				packSupplierBaseInfo.setWoodBoxStartVolume(entity.getWoodBoxStartVolume());
			if(null != entity.getWoodenFrame())
				packSupplierBaseInfo.setWoodenFrame(entity.getWoodenFrame());				
			if(null != entity.getWoodenFrameMin())
				packSupplierBaseInfo.setWoodenFrameMin(entity.getWoodenFrameMin());
			if(null != entity.getWoodenFrameStartVolume())
				packSupplierBaseInfo.setWoodenFrameStartVolume(entity.getWoodenFrameStartVolume());
			if(null != entity.getWoodPallet())
				packSupplierBaseInfo.setWoodPallet(entity.getWoodPallet());
			if(null != entity.getWrappingFilm())
				packSupplierBaseInfo.setWrappingFilm(entity.getWrappingFilm());
			
			request.getPackSupplierBaseInfo().add(packSupplierBaseInfo);
			
		}
		request.setOperationType(operateType.toString());
		return request;
	}
	
}
