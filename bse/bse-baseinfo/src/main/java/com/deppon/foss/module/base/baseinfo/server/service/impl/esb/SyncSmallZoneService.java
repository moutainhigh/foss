package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncSmallZoneRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncSmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.oms.inteface.domain.SmallzoneInfo;
import com.deppon.oms.inteface.domain.SyncSmallZoneRequest;

/**
 * 集中接送货小区接口Service
 * @author 310854
 * @date 2016-4-6
 */
public class SyncSmallZoneService implements ISyncSmallZoneService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncSmallZoneService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_DELIVERY_CELL";

	private static final String VERSION = "1.0";

	@Override
	public void syncSmallZone(List<SmallZoneEntity> entitys) {
		// TODO Auto-generated method stub
		try {
			if (CollectionUtils.isEmpty(entitys)) {
				throw new BusinessException("传入对象为空");
			}
			SyncSmallZoneRequest smallzoneRequest = new SyncSmallZoneRequest();
			List<SmallzoneInfo> infos = new ArrayList<SmallzoneInfo>();

			StringBuilder versionNos = new StringBuilder();
			StringBuilder codes = new StringBuilder();
			for (SmallZoneEntity entity : entitys) {
				if (entity == null) {
					continue;
				}
				SmallzoneInfo info = this.transFossToEsb(entity);
				infos.add(info);

				codes.append(SymbolConstants.EN_COMMA);
				//	codes.append(entity.getCode());
			}

			smallzoneRequest.getDistrictInfo().addAll(infos);

			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(codes.toString());
			accessHeader
					.setBusinessDesc1("send region_vehicle info to other platform.同步集中接送货小区信息 到其它平台");
			accessHeader.setBusinessDesc2(versionNos.toString());
			accessHeader.setVersion(VERSION);

			log.info("start to send region_vehicle info to other platform 开始 集中接送货小区信息 到其它平台：\n"
					+ new SyncSmallZoneRequestTrans()
							.fromMessage(smallzoneRequest));

			ESBJMSAccessor.asynReqeust(accessHeader, smallzoneRequest);

			log.info("end to send region_vehicle info to other platform 结束集中接送货小区信息 到其它平台：\n"
					+ new SyncSmallZoneRequestTrans()
							.fromMessage(smallzoneRequest));

		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		/*	throw new SynchronousExternalSystemException("同步集中接送货小区信息 到其它平台",
					"同步集中接送货小区信息 到其它平台 发送数据到ESB错误");*/
		}

	}

	private SmallzoneInfo transFossToEsb(SmallZoneEntity entity) {
		if(null == entity){
			throw new BusinessException("传入对象为空");
		}
		SmallzoneInfo info = new SmallzoneInfo();
		info.setActive(entity.getActive());
		info.setBigzonecode(entity.getBigzonecode());
		info.setCityCode(entity.getCityCode());
		info.setCountyCode(entity.getCountyCode());
		info.setCreateTime(entity.getCreateDate());
		info.setCreateUserCode(entity.getCreateUser());
		info.setGisArea(entity.getGisArea());
		info.setGisid(entity.getGisid());
		info.setId(entity.getId());
		info.setManagement(entity.getManagement());
		info.setModifyTime(entity.getModifyDate());
		info.setModifyUserCode(entity.getModifyUser());
		info.setNavigationDistance(entity.getNavigationDistance());
		info.setNotes(entity.getNotes());
		info.setProvCode(entity.getProvCode());
		info.setRegionCode(entity.getRegionCode());
		info.setRegionName(entity.getRegionName());
		info.setRegionType(entity.getRegionType());
		info.setVirtualCode(entity.getVirtualCode());
	
		return info;
	}

}
