package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.SyncExpressSmallZoneRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendExpressSmallZoneToOMSService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.oms.inteface.domain.ExpressSmallZoneInfo;
import com.deppon.oms.inteface.domain.SyncExpressSmallZoneRequest;

public class SendExpressSmallZoneToOMSService implements
		ISendExpressSmallZoneToOMSService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendExpressSmallZoneToOMSService.class);

	/**
	 * 服务编码
	 */
	private static final String SYNC_EXPRESSSMALLZONE_SERVE_CODE = "ESB_FOSS2ESB_EXPRESS_DELIVERY";

	/**
     * 版本编号
     */
    private static final String version = "1.0";
     private IEsbErrorLoggingDao esbErrorLoggingDao;
	public void setEsbErrorLoggingDao(IEsbErrorLoggingDao esbErrorLoggingDao) {
		this.esbErrorLoggingDao = esbErrorLoggingDao;
	}

	/**
	 * 同步快递收派小区信息到OMS
	 * 
	 * @author 313353
	 * @date 2016-3-21 上午11:50:25
	 */
	@Override
	public void syncExpressSmallZoneToOMS(List<ExpressDeliverySmallZoneEntity> entitys)
			 {
		// 声明要传递的值
		SyncExpressSmallZoneRequest request = new SyncExpressSmallZoneRequest();

		StringBuilder versionNos = new StringBuilder();
		StringBuilder codes = new StringBuilder();

		List<ExpressSmallZoneInfo> esbInfos = request.getExpressSmallZoneInfos();
		for (ExpressDeliverySmallZoneEntity fossEntity : entitys) {
			if (fossEntity == null) {
				continue;
			}
			versionNos.append(SymbolConstants.EN_COMMA);
			versionNos.append(fossEntity.getVersionNo());
			codes.append(SymbolConstants.EN_COMMA);
			codes.append(fossEntity.getRegionCode());
			esbInfos.add(transFossToEsb(fossEntity));
		}
		AccessHeader header = new AccessHeader();

		header.setBusinessId(codes.toString());
		// 服务编码
		header.setEsbServiceCode(SYNC_EXPRESSSMALLZONE_SERVE_CODE);
		// 处理工作流审批结果
		header.setVersion(version);
		header.setBusinessDesc1("同步快递收派小区接口到OMS");
		header.setBusinessDesc2(versionNos.toString());
		long startTime = System.currentTimeMillis();
		EsbErrorLogging log = new EsbErrorLogging();
		try {
			log.setParamenter(new SyncExpressSmallZoneRequestTrans().fromMessage(request));
			log.setRequestSystem("ESB");
			log.setRequestTime(new Date());
			log.setMethodDesc("同步快递收派小区接口到OMS");
			log.setMethodName(this.getClass().getName()+".syncExpressSmallZoneToOMS");
			// 发送申请到ESB
			LOGGER.info("start send T_BAS_EXPRESS_SMALLZONE info to OMS.FOSS开始发送同步快递收派小区接口到OMS 的报文："
					+ new SyncExpressSmallZoneRequestTrans().fromMessage(request));
		    ESBJMSAccessor.asynReqeust(header, request);
		    long responseTime = (System.currentTimeMillis()-startTime); 
			if(responseTime>FossConstants.MAX_RESPONSE_TIME){
				log.setErrorMessage("响应超时");
				log.setResponseTime(responseTime);
				log.setCreateTime(new Date());
				esbErrorLoggingDao.addErrorMessage(log);
			}
		LOGGER.info("end send T_BAS_EXPRESS_SMALLZONE info to other platform.FOSS结束发送同步快递收派小区接口到OMS 的报文："
				+ new SyncExpressSmallZoneRequestTrans().fromMessage(request));
		}catch (ESBException e) {
			log.setResponseTime((System.currentTimeMillis()-startTime));
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.setCreateTime(new Date());
			log.setErrorMessage(sw.toString());
			esbErrorLoggingDao.addErrorMessage(log);
			throw new BusinessException("同步快递收派小区信息到OMS失败",e);
			
		}
		
	}

	/**
	 * foss对象转ESB
	 * 
	 * @author 313353-foss-qiupeng
	 * @date 2016-3-21 下午1:44:19
	 */
	private ExpressSmallZoneInfo transFossToEsb(ExpressDeliverySmallZoneEntity fossEntity) {
		if (fossEntity == null) {
			return null;
		}
		ExpressSmallZoneInfo esbInfo = new ExpressSmallZoneInfo();
		
		//id
	    esbInfo.setId(fossEntity.getId());
	    
	    //小区编码
	    esbInfo.setRegionCode(fossEntity.getRegionCode());
	    
	    //小区名称
	    esbInfo.setRegionName(fossEntity.getRegionName());
	    
	    //管理部门
	    esbInfo.setManagement(fossEntity.getManagement());

	    //创建时间
	    esbInfo.setCreateTime(fossEntity.getCreateDate());

	    //更新时间
	    esbInfo.setModifyTime(fossEntity.getModifyDate());

	    //是否启用
	    esbInfo.setActive(fossEntity.getActive());

	    //创建人
	    esbInfo.setCreateUserCode(fossEntity.getCreateUser());

	    //更新人
	    esbInfo.setModifyUserCode(fossEntity.getModifyUser());

	    //备注
	    esbInfo.setNotes(fossEntity.getNotes());
	    
	    //虚拟编码
	    esbInfo.setVirtualCode(fossEntity.getVirtualCode());
	    
	    //小区类型：CBD区域、专业市场、商业区、住宅区、商住混合区、其他
	    esbInfo.setRegionType(fossEntity.getRegionType());
	    
	    //GIS系统地图范围ID
	    esbInfo.setGisid(fossEntity.getGisid());
	    
	    //所属大区虚拟编码
	    esbInfo.setBigZoneCode(fossEntity.getBigzonecode());
	    
	    //所在省
	    esbInfo.setProvCode(fossEntity.getProvCode());
	    
	    //所在市
	    esbInfo.setCityCode(fossEntity.getCityCode());
	    
	    //所在区县
	    esbInfo.setCountyCode(fossEntity.getCountyCode());

	    //面积
	    esbInfo.setGisArea(fossEntity.getGisArea());

	    //操作人工号
	    esbInfo.setOperatorCode(fossEntity.getOperatorCode());

	    //主责快递员编码
	    esbInfo.setCourierCode(fossEntity.getCourierCode());
	    
	    //主责快递员姓名
	    esbInfo.setCourierName(fossEntity.getCourierName());

	    //营业部到小区的最短距离
	    esbInfo.setSalesToSmallzone(fossEntity.getSalesToSmallZone());
		
		return esbInfo;
	}

}
