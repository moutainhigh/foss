package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncBigZoneRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.oms.inteface.domain.BigzoneInfo;
import com.deppon.oms.inteface.domain.SyncBigZoneRequest;

/**
 * 接送货大区接口Service
 * @author 310854
 * @date 2016-4-6
 */
public class SyncBigZoneService implements ISyncBigZoneService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncBigZoneService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_DELIVERY_AREA";

	private static final String VERSION = "1.0";

	@Override
	public void syncBigZone(List<BigZoneEntity> entitys) {
		// TODO Auto-generated method stub
		try {
			if (CollectionUtils.isEmpty(entitys)) {
				throw new BusinessException("传入对象为空");
			}
			SyncBigZoneRequest bigZoneRequest = new SyncBigZoneRequest();
			List<BigzoneInfo> infos = new ArrayList<BigzoneInfo>();

			StringBuilder versionNos = new StringBuilder();
			StringBuilder codes = new StringBuilder();
			for (BigZoneEntity entity : entitys) {
				if (entity == null) {
					continue;
				}
				BigzoneInfo info = this.transFossToEsb(entity);
				infos.add(info);

				codes.append(SymbolConstants.EN_COMMA);
				codes.append(entity.getRegionCode());
			}

			bigZoneRequest.getDistrictInfo().addAll(infos);

			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(codes.toString());
			accessHeader
					.setBusinessDesc1("send region_vehicle info to other platform.同步接送货大区信息 到其它平台");
			accessHeader.setBusinessDesc2(versionNos.toString());
			accessHeader.setVersion(VERSION);

			log.info("start to send region_vehicle info to other platform 开始 同步接送货大区信息 到其它平台：\n"
					+ new SyncBigZoneRequestTrans()
							.fromMessage(bigZoneRequest));

			ESBJMSAccessor.asynReqeust(accessHeader, bigZoneRequest);

			log.info("end to send region_vehicle info to other platform 结束 同步接送货大区信息 到其它平台：\n"
					+ new SyncBigZoneRequestTrans()
							.fromMessage(bigZoneRequest));

		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		/*	throw new SynchronousExternalSystemException("同步接送货大区信息 到其它平台",
					"同步接送货大区信息 到其它平台 发送数据到ESB错误");*/
		}

	}

	private BigzoneInfo transFossToEsb(BigZoneEntity entity) {
		if(null == entity){
			throw new BusinessException("传入对象为空");
		}
		BigzoneInfo info = new BigzoneInfo();
		info.setId(entity.getId());
		info.setRegionCode(entity.getRegionCode());
		info.setRegionName(entity.getRegionName());
		info.setActive(entity.getActive());
		info.setCityCode(entity.getCityCode());
		info.setCountyCode(entity.getCountyCode());
		info.setCreateTime(entity.getCreateDate());
		info.setCreateUserCode(entity.getCreateUser());
		info.setManagement(entity.getManagement());
		info.setModifyTime(entity.getModifyDate());
		info.setModifyUserCode(entity.getModifyUser());
		info.setNotes(entity.getNotes());
		info.setProvCode(entity.getProvCode());
		info.setRegionCode(entity.getRegionCode());
		info.setTransDepartmentCode(entity.getTransDepartmentCode());
		info.setType(entity.getType());
		info.setVirtualCode(entity.getVirtualCode());
		
		return info; 
	}

}
