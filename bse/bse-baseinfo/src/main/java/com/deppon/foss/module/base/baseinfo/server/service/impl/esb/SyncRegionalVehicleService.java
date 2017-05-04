package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncRegionVehicleRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.oms.inteface.domain.RegionVehicleInfo;
import com.deppon.oms.inteface.domain.SyncRegionVehicleRequest;

/**
 * 定人定区异步接口
 * @author 310854
 * @date 2016-4-6
 */
public class SyncRegionalVehicleService implements ISyncRegionalVehicleService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncRegionalVehicleService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_FIXED_AREA";

	private static final String VERSION = "1.0";

	@Override
	public void syncRegionalVehicle(List<RegionVehicleEntity> entitys) {
		// TODO Auto-generated method stub
		try {
			if (CollectionUtils.isEmpty(entitys)) {
				throw new BusinessException("传入对象为空");
			}
			SyncRegionVehicleRequest regionVehicleRequest = new SyncRegionVehicleRequest();
			List<RegionVehicleInfo> infos = new ArrayList<RegionVehicleInfo>();

			StringBuilder versionNos = new StringBuilder();
			StringBuilder codes = new StringBuilder();
			for (RegionVehicleEntity entity : entitys) {
				if (entity == null) {
					continue;
				}
				RegionVehicleInfo info = this.transFossToEsb(entity);
				infos.add(info);

				codes.append(SymbolConstants.EN_COMMA);
				codes.append(entity.getCode());
			}

			regionVehicleRequest.getDistrictInfo().addAll(infos);

			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(codes.toString());
			accessHeader
					.setBusinessDesc1("send region_vehicle info to other platform.同步定人定区信息 到其它平台");
			accessHeader.setBusinessDesc2(versionNos.toString());
			accessHeader.setVersion(VERSION);

			log.info("start to send region_vehicle info to other platform 开始 定人定区域信息 到其它平台：\n"
					+ new SyncRegionVehicleRequestTrans()
							.fromMessage(regionVehicleRequest));

			ESBJMSAccessor.asynReqeust(accessHeader, regionVehicleRequest);

			log.info("end to send region_vehicle info to other platform 结束 定人定区域信息 到其它平台：\n"
					+ new SyncRegionVehicleRequestTrans()
							.fromMessage(regionVehicleRequest));

		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		/*	throw new SynchronousExternalSystemException("同步定人定区信息 到其它平台",
					"同步定人定区信息 到其它平台 发送数据到ESB错误");*/
		}

	}

	private RegionVehicleInfo transFossToEsb(RegionVehicleEntity entity) {
		if(null == entity){
			throw new BusinessException("传入对象为空");
		}
		RegionVehicleInfo info = new RegionVehicleInfo();
		info.setActive(entity.getActive());
		info.setCreateTime(entity.getCreateDate());
		info.setCreateUserCode(entity.getCreateUser());
		info.setId(entity.getId());
		info.setModifyTime(entity.getModifyDate());
		info.setModifyUserCode(entity.getModifyUser());
		info.setRegionCode(entity.getRegionCode());
		info.setRegionName(entity.getRegionName());
		info.setRegionNature(entity.getRegionNature());
		info.setRegionType(entity.getRegionType());
		info.setVehicleDept(entity.getVehicleDept());
		info.setVehicleNo(entity.getVehicleNo());
		info.setVehicleType(entity.getVehicleType());
		info.setVirtualCode(entity.getVirtualCode());
		return info;
	}

}
