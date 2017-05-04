package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.List;

import javax.xml.ws.WebServiceClient;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cc.module.foss.shared.domain.Network;
import com.deppon.cc.module.foss.shared.domain.SendPartialLineNetWorkRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncPartialLineNetworkToCCService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
/**
 * 
 *<p>Title: ISyncPartialLineNetworkToCCService</p>
 * <p>Description: 同步偏线网点信息给CC  Web Service客户端服务接口实现</p>
 * <p>Company: Deppon</p>
 * @author    195406-gcl
 * @date       2014-5-14
 */
@WebServiceClient
public class SyncPartialLineNetworkToCCService implements
		ISyncPartialLineNetworkToCCService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncPartialLineNetworkToCCService.class);
	
	
	/**
	 *<p>Title: syncPartialLine</p>
	 *<p>向CC同步偏线网点信息</p>
	 *@author 195406-gcl
	 *@date 2014-7-21
	 * @param dto
	 * @return
	 */
	@Override
	public boolean syncPartialLine(List<OuterBranchEntity> outlist) {
		boolean flag=false;
		try {
			if (CollectionUtils.isNotEmpty(outlist)){
				// 设置服务编码
				AccessHeader accessHeader = new AccessHeader();
				accessHeader.setEsbServiceCode("ESB_FOSS2ESB_SEND_DEVIATE_WEBSITE");
				accessHeader.setBusinessId("PX");//运输性质作为唯一标识
				accessHeader.setBusinessDesc1("同步 偏线网点信息 到CC平台");
				accessHeader.setBusinessDesc2("");
				accessHeader.setVersion("1.0");
				
				SendPartialLineNetWorkRequest request =new SendPartialLineNetWorkRequest();
				//封装request
				request =convertToCCEntity(request,outlist);
				
				LOGGER.info("syncOuterBranchEntityToCC:send info start.............");
				ESBJMSAccessor.asynReqeust(accessHeader, request);
				LOGGER.info("syncOuterBranchEntityToCC:send info end.............");
				flag = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	/**
	 * 
	 *<p>Title: convertToCCEntity</p>
	 *<p>将传过来的dto 转换成Esb对象</p>
	 *@author 195406-gcl
	 *@date 2014-7-21
	 * @param request
	 * @param dto
	 * @return
	 */
	private SendPartialLineNetWorkRequest convertToCCEntity(
			SendPartialLineNetWorkRequest request, List<OuterBranchEntity> outlist) {
		//运输性质
		request.setTransportType("PX");
		
		//网点信息集合
		for (OuterBranchEntity  dto: outlist) {
			Network entity=new Network();
			entity.setCode(dto.getAgentDeptCode());
			entity.setName(dto.getAgentDeptName());
			entity.setProvince(dto.getProvCode());
			entity.setCity(dto.getCityCode());
			entity.setCountry(dto.getCountyCode());
			entity.setState(dto.getActive());
			request.getNetWorkList().add(entity);
		}
		return request;
	}
	
}
