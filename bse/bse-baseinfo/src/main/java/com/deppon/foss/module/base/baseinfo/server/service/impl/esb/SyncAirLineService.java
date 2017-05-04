package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.inteface.domain.AirlineInfo;
import com.deppon.cubc.inteface.domain.SyncAirlineRequest;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.pojo.transformer.jaxb.SyncAirlineRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAirLineService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;

public class SyncAirLineService implements ISyncAirLineService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncAirLineService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYN_AIRLINE";

	private static final String VERSION = "1.0";
    /**
     * 
     * <p>同步航空公司给cubc</p> 
     * @author 273311 
     * @date 2016-10-29 上午11:02:21
     * @param entitys
     * @param operateType 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAirLineService#SyncAirLine(java.util.List, java.lang.String)
     */
	@Override
	public void SyncAirLine(List<AirlinesEntity> entitys, String operateType) {
		try {
			if (CollectionUtils.isEmpty(entitys)) {
				throw new BusinessException("传入对象为空");
			}
			SyncAirlineRequest request = new SyncAirlineRequest();
			List<AirlineInfo> airlineInfos = new ArrayList<AirlineInfo>();
			for (AirlinesEntity entity : entitys) {
				if (entity == null) {
					continue;
				}
				AirlineInfo info = this.transFossToEsb(entity);
				airlineInfos.add(info);
			}
			request.getAirline().addAll(airlineInfos);
			request.setOperationType(operateType);
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(UUIDUtils.getUUID());
			accessHeader.setVersion(VERSION);
			accessHeader.setBusinessDesc1("同步航空公司基础资料到cubc");
			log.info("start to send Airline info to cubc 开始同步航空公司信息 cubc：\n"
					+ new SyncAirlineRequestTrans().fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			log.info("end to send Airline info to cubc 结束 同步航空公司信息 到cubc：\n"
					+ new SyncAirlineRequestTrans().fromMessage(request));
		} catch (ESBException e) {
			log.error("同步航空公司 到cubc平台，发送数据到ESB错误:      " + e.getMessage(), e);
			e.printStackTrace();
		}

	}
    /**
     * 
     * <p>转换方法</p> 
     * @author 273311 
     * @date 2016-10-29 上午11:02:29
     * @param entity
     * @return
     * @see
     */
	private AirlineInfo transFossToEsb(AirlinesEntity entity) {
		AirlineInfo airlineInfo = new AirlineInfo();
		/**
		 * ID
		 */
		airlineInfo.setID(entity.getId());
		/**
		 * 航空公司代码
		 */
		airlineInfo.setCode(entity.getCode());
		/**
		 * 航空公司名称
		 */
		airlineInfo.setName(entity.getName());
		/**
		 * 航空公司简称
		 */
		airlineInfo.setSimpleName(entity.getSimpleName());
		/**
		 * 航空公司LOGO
		 */
		airlineInfo.setLogo(entity.getLogo());
		/**
		 * 航空公司数字前缀
		 */
		airlineInfo.setPrifixName(entity.getPrifixName());
		/**
		 * 是否启用
		 */
		airlineInfo.setActive(entity.getActive());
		/**
		 * 备注
		 */
		airlineInfo.setNotes(entity.getNotes());
		/**
		 * 创建时间
		 */
		airlineInfo.setCreateDate(entity.getCreateDate());
		/**
		 * 更新时间
		 */
		airlineInfo.setModifyDate(entity.getModifyDate());
		/**
		 * 创建人
		 */
		airlineInfo.setCreateUser(entity.getCreateUser());
		/**
		 * 修改人
		 */
		airlineInfo.setModifyUser(entity.getModifyUser());
		return airlineInfo;

	}
}
