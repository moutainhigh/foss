package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncLeasedTruckTeamRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncLeasedTruckTeamService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.oms.inteface.domain.LeasedTruckTeam;
import com.deppon.oms.inteface.domain.SyncLeasedTruckTeamRequest;

public class SyncLeasedTruckTeamService implements ISyncLeasedTruckTeamService {
	private static final Logger log = LoggerFactory
			.getLogger(SyncLeasedTruckTeamService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_CAR_BASIC_INFOR";

	private static final String VERSION = "1.0";

	@Override
	public void syncLeasedTruckTeam(List<LeasedTruckEntity> entitys) throws LeasedVehicleException{
		// TODO Auto-generated method stub
		try {
			if (CollectionUtils.isEmpty(entitys)) {
				throw new BusinessException("传入对象为空");
			}
			SyncLeasedTruckTeamRequest leasedTruckTeamRequest = new SyncLeasedTruckTeamRequest();
			List<LeasedTruckTeam> infos = new ArrayList<LeasedTruckTeam>();

			StringBuilder versionNos = new StringBuilder();
		//	StringBuilder codes = new StringBuilder();
			for (LeasedTruckEntity entity : entitys) {
				if (entity == null) {
					continue;
				}
				LeasedTruckTeam info = this.transFossToEsb(entity);
				infos.add(info);

			//	codes.append(SymbolConstants.EN_COMMA);
			//	codes.append(entity.getRegionCode());
			}

			leasedTruckTeamRequest.getLeasedTruckTeams().addAll(infos);

			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(UUIDUtils.getUUID());
			accessHeader
					.setBusinessDesc1("send info to OMS.同步外请车服务资料信息 到其它平台");
			accessHeader.setBusinessDesc2(versionNos.toString());
			accessHeader.setVersion(VERSION);

			log.info("start to send info to OMS 开始 同步外请车服务资料信息 到其它平台：\n"
					+ new SyncLeasedTruckTeamRequestTrans()
							.fromMessage(leasedTruckTeamRequest));

			ESBJMSAccessor.asynReqeust(accessHeader, leasedTruckTeamRequest);

			log.info("end to send info to OMS 结束 同步外请车服务资料信息 到其它平台：\n"
					+ new SyncLeasedTruckTeamRequestTrans()
							.fromMessage(leasedTruckTeamRequest));

		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
			throw new LeasedVehicleException("同步外请车服务资料信息到OMS，发送数据到ESB错误");
		}
	}

	private LeasedTruckTeam transFossToEsb(LeasedTruckEntity entity){
		LeasedTruckTeam team = new LeasedTruckTeam();
		team.setActive(entity.getActive());
		team.setCreateTime(entity.getCreateDate());
		team.setCreateUserCode(entity.getCreateUser());
		team.setModifyTime(entity.getModifyDate());
		team.setModifyUserCode(entity.getModifyUser());
		team.setOperatorName(entity.getOpenVehicle());
		team.setOrgCode(entity.getOrgCode());
		team.setVehicleNo(entity.getVehicleNo());
		return team;
		
	}
	
}
