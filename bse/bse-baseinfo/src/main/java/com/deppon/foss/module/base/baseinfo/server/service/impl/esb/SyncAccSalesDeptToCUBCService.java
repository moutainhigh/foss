package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.annotation.Resource;

import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.inteface.domain.AcceptPointSalesChildrenDept;
import com.deppon.cubc.inteface.domain.AcceptPointSalesDept;
import com.deppon.cubc.inteface.domain.SyncAccSalesDeptRequest;
import com.deppon.esb.pojo.transformer.jaxb.SyncAccSalesDeptRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAccSalesDeptToCUBCService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesChildrenDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AcceptPointSalesDeptDto;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;


public class SyncAccSalesDeptToCUBCService implements ISyncAccSalesDeptToCUBCService{

	private static final Logger log = LoggerFactory
			.getLogger(SyncInformationService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYN_TRANSHIP";
	
	private static final String VERSION = "1.0";
	
	private IEsbErrorLoggingDao esbErrorLoggingDao;
	
	public void setEsbErrorLoggingDao(IEsbErrorLoggingDao esbErrorLoggingDao) {
		this.esbErrorLoggingDao = esbErrorLoggingDao;
	}
	
	/**
	 * 
	 * 同步接驳点与营业部映射关系信息给CUBC
	 * 
	 * @author 269231
	 * @date 
	 */
	
	@Override
	public void syncAccSalesDept(AcceptPointSalesDeptDto entitys, String operateType) {
		
		// TODO Auto-generated method stub
		if (null == entitys) {
			throw new BusinessException("传入的对象为空");
		}
		SyncAccSalesDeptRequest request = ConvertEsbEntity(entitys,operateType);
		
		// 创建服务头信息
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUID.randomUUID().toString());
		accessHeader.setBusinessDesc1("同步接驳点与营业部映射关系信息给CUBC");
		accessHeader.setVersion(VERSION);
		long startTime = System.currentTimeMillis();
		EsbErrorLogging erlog = new EsbErrorLogging();
		try {
			erlog.setParamenter(new SyncAccSalesDeptRequestTrans().fromMessage(request));
			erlog.setRequestSystem("ESB");
			erlog.setRequestTime(new Date());
			erlog.setMethodDesc("同步接驳点与营业部映射关系信息给CUBC");
			erlog.setMethodName(this.getClass().getName()+".syncAccSalesDept");
			log.info("开始调用 同步接驳点与营业部映射关系信息给CUBC：\n"
					+ new SyncAccSalesDeptRequestTrans().fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			long responseTime = (System.currentTimeMillis()-startTime); 
			if(responseTime>FossConstants.MAX_RESPONSE_TIME){
				erlog.setCreateTime(new Date());
				erlog.setErrorMessage("响应超时");
				erlog.setResponseTime(responseTime);
				esbErrorLoggingDao.addErrorMessage(erlog);
			}
			log.info("结束调用 同步接驳点与营业部映射关系信息给CUBC：\n"+ new SyncAccSalesDeptRequestTrans().fromMessage(request));
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
		log.info("send ExpressDeliveryRegions to GW System ：同步接驳点与营业部映射关系信息给CUBC \n");	
	
	}

	private SyncAccSalesDeptRequest ConvertEsbEntity(
			AcceptPointSalesDeptDto entity, String operateType) {
		// TODO Auto-generated method stub
		SyncAccSalesDeptRequest request = new SyncAccSalesDeptRequest();
					
			if(entity.getAcceptPointEntitys() != null){					
					for(AcceptPointSalesDeptEntity acceptPointSalesDeptEntity: entity.getAcceptPointEntitys()){			
						AcceptPointSalesDept acceptPointSalesDept = new AcceptPointSalesDept();
				
						acceptPointSalesDept.setAcceptPointCode(acceptPointSalesDeptEntity.getAcceptPointCode());
						acceptPointSalesDept.setActive(acceptPointSalesDeptEntity.getActive());
						acceptPointSalesDept.setBigRegion(acceptPointSalesDeptEntity.getBigRegion());
						acceptPointSalesDept.setCreateDate(acceptPointSalesDeptEntity.getCreateDate());
						acceptPointSalesDept.setCreateUserCode(acceptPointSalesDeptEntity.getCreateUser());
						acceptPointSalesDept.setCreateUserName(acceptPointSalesDeptEntity.getCreateUserName());
						acceptPointSalesDept.setId(acceptPointSalesDeptEntity.getId());
						acceptPointSalesDept.setModifyDate(acceptPointSalesDeptEntity.getModifyDate());
						acceptPointSalesDept.setModifyUserCode(acceptPointSalesDeptEntity.getModifyUser());
						acceptPointSalesDept.setStatus(acceptPointSalesDeptEntity.getStatus());
						acceptPointSalesDept.setTransferCode(acceptPointSalesDeptEntity.getTransferCode());			
					
						request.getAcceptPointSalesDept().add(acceptPointSalesDept);
					}
			}
				if(entity.getChildAcceptPointEntitys() != null){			
					for(AcceptPointSalesChildrenDeptEntity acceptPointSalesChildrenDeptEntity: entity.getChildAcceptPointEntitys()){
						AcceptPointSalesChildrenDept acceptPointSalesChildrenDept = new AcceptPointSalesChildrenDept();
						
						acceptPointSalesChildrenDept.setAcceptPointCode(acceptPointSalesChildrenDeptEntity.getAcceptPointCode());
						acceptPointSalesChildrenDept.setActive(acceptPointSalesChildrenDeptEntity.getActive());
						acceptPointSalesChildrenDept.setId(acceptPointSalesChildrenDeptEntity.getId());
						acceptPointSalesChildrenDept.setSalesDepartmentCode(acceptPointSalesChildrenDeptEntity.getSalesDepartmentCode());
						acceptPointSalesChildrenDept.setSmallRegion(acceptPointSalesChildrenDeptEntity.getSmallRegion());
						acceptPointSalesChildrenDept.setStatus(acceptPointSalesChildrenDeptEntity.getStatus());
						
						request.getAcceptPointSalesChildrenDept().add(acceptPointSalesChildrenDept);
					}			
			}		
		request.setOperationType(operateType);
		return request;
	}
	

}
