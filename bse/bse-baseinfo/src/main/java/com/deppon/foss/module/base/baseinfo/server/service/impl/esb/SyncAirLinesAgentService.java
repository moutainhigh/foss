package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.inteface.domain.AirlineAgentInfo;
import com.deppon.cubc.inteface.domain.SyncAirlineAgentRequest;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.pojo.transformer.jaxb.SyncAirlineAgentRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAirLinesAgentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;

public class SyncAirLinesAgentService implements ISyncAirLinesAgentService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncAirLinesAgentService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYN_AIRLINEAGENT";

	private static final String VERSION = "1.0";
   /**
    * 
    * <p>同步航空公司代理人</p> 
    * @author 273311 
    * @date 2016-10-29 上午10:45:27
    * @param entitys
    * @param operateType 
    * @see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAirLinesAgentService#SyncAirLinesAgent(java.util.List, java.lang.String)
    */
	@Override
	public void SyncAirLinesAgent(List<AirlinesAgentEntity> entitys,
			String operateType) {
		try {
			if (CollectionUtils.isEmpty(entitys)) {
				throw new BusinessException("传入对象为空");
			}
			SyncAirlineAgentRequest request = new SyncAirlineAgentRequest();
			List<AirlineAgentInfo> lineAgentInfos = new ArrayList<AirlineAgentInfo>();
			for (AirlinesAgentEntity entity : entitys) {
				if (entity == null) {
					continue;
				}
				AirlineAgentInfo info = this.transFossToEsb(entity);
				lineAgentInfos.add(info);
			}
			request.getAirlineAgent().addAll(lineAgentInfos);
			request.setOperationType(operateType);
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(UUIDUtils.getUUID());
			accessHeader.setVersion(VERSION);
			accessHeader.setBusinessDesc1("同步航空公司代理人基础资料到cubc");
			log.info("start to send Airline info to cubc 开始同步航空公司信息 cubc：\n"
					+ new SyncAirlineAgentRequestTrans().fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			log.info("end to send Airline info to cubc 结束 同步航空公司代理人信息 到cubc：\n"
					+ new SyncAirlineAgentRequestTrans().fromMessage(request));
		} catch (ESBException e) {
			log.error("同步航空公司 代理人到cubc平台，发送数据到ESB错误:      " + e.getMessage(), e);
			e.printStackTrace();
		}

	}
    /**
     * 
     * <p>转换方法</p> 
     * @author 273311 
     * @date 2016-10-29 上午10:49:42
     * @param entity
     * @return
     * @see
     */
	private AirlineAgentInfo transFossToEsb(AirlinesAgentEntity entity) {
		AirlineAgentInfo airlineAgentInfo = new AirlineAgentInfo();
		/**
		 * ID
		 */
		airlineAgentInfo.setID(entity.getId());
		/**
		 * 配载部门
		 */
		airlineAgentInfo.setAssemblyDeptId(entity.getAssemblyDeptId());
		/**
		 * 航空公司代码
		 */
		airlineAgentInfo.setAirlinesCode(entity.getAirlinesCode());
		/**
		 * 代理人编码
		 */
		airlineAgentInfo.setAgentCode(entity.getAgentCode());
		/**
		 * 代理人名称
		 */
		airlineAgentInfo.setAgentName(entity.getAgentName());
		/**
		 * 是否外部代理
		 */
		airlineAgentInfo.setIsOutAgent(entity.getIsOutAgent());
		/**
		 * 是否启用
		 */
		airlineAgentInfo.setActive(entity.getActive());
		/**
		 * 创建时间
		 */
		airlineAgentInfo.setCreateDate(entity.getCreateDate());
		/**
		 * 更新时间
		 */
		airlineAgentInfo.setModifyDate(entity.getModifyDate());
		/**
		 * 创建人
		 */
		airlineAgentInfo.setCreateUser(entity.getCreateUser());
		/**
		 * 修改人
		 */
		airlineAgentInfo.setModifyUser(entity.getModifyUser());
		return airlineAgentInfo;

	}
}
