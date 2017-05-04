package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.inteface.domain.ExpressBigRegionDistrInfo;
import com.deppon.cubc.inteface.domain.SyncExpressBigRegionDistrRequest;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.pojo.transformer.jaxb.SyncExpressBigRegionDistrRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncExpressBigRegionDistrService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;

public class SyncExpressBigRegionDistrService implements ISyncExpressBigRegionDistrService{

	private static final Logger log = LoggerFactory
			.getLogger(SyncExpressBigRegionDistrService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYN_EXPRESSADMIN";

	private static final String VERSION = "1.0";
	/**
	 * 
	 * <p>快递大区-行政区域映射信息到cubc</p> 
	 * @author 273311 
	 * @date 2016-10-29 上午11:11:43
	 * @param entitys
	 * @param operateType 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncExpressBigRegionDistrService#SyncExpressBigRegionDistr(java.util.List, java.lang.String)
	 */
	@Override
	public void SyncExpressBigRegionDistr(List<ExpressBigRegionDistrEntity> entitys,String operateType) {
		try {
		if (CollectionUtils.isEmpty(entitys)) {
			throw new BusinessException("传入对象为空");
		}
		SyncExpressBigRegionDistrRequest request = new SyncExpressBigRegionDistrRequest();
		List<ExpressBigRegionDistrInfo> expressBigRegionDistrInfos = new ArrayList<ExpressBigRegionDistrInfo>();
		for (ExpressBigRegionDistrEntity entity : entitys) {
			if (entity == null) {
				continue;
			}
			ExpressBigRegionDistrInfo info = this.transFossToEsb(entity);
			expressBigRegionDistrInfos.add(info);
		}
		request.getExpressBigRegionDistr().addAll(expressBigRegionDistrInfos);
		request.setOperationType(operateType);
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUIDUtils.getUUID());
		accessHeader.setVersion(VERSION);
		accessHeader.setBusinessDesc1("快递大区-行政区域映射信息到cubc");
		log.info("start to send ExpressBigRegionDistr info to cubc 开始快递大区-行政区域映射信息 cubc：\n"
				+ new SyncExpressBigRegionDistrRequestTrans()
						.fromMessage(request));
		ESBJMSAccessor.asynReqeust(accessHeader, request);
		log.info("end to send ExpressBigRegionDistr info to cubc 结束 快递大区-行政区域映射信息 到cubc：\n"
				+ new SyncExpressBigRegionDistrRequestTrans()
						.fromMessage(request));	
		} catch (ESBException e) {
			log.error("快递大区-行政区域映射信息 到cubc平台，发送数据到ESB错误:      "+e.getMessage(), e);
			e.printStackTrace();
		}
		
	}
    /**
     * 
     * <p>转换方法</p> 
     * @author 273311 
     * @date 2016-10-29 上午11:11:55
     * @param entity
     * @return
     * @see
     */
	private ExpressBigRegionDistrInfo transFossToEsb(
			ExpressBigRegionDistrEntity entity) {
		ExpressBigRegionDistrInfo expressBigRegionDistrInfo = new ExpressBigRegionDistrInfo();
		// ID
		expressBigRegionDistrInfo.setID(entity.getId());
		// 组织CODE
		expressBigRegionDistrInfo.setOrgCode(entity.getOrgCode());
		// 组织名称
		expressBigRegionDistrInfo.setOrgName(entity.getOrgName());
		// 行政区域CODE
		expressBigRegionDistrInfo.setDistrictCode(entity.getDistrictCode());
		// 行政区域名称
		expressBigRegionDistrInfo.setDistrictName(entity.getDistrictName());
		// 数据版本
		expressBigRegionDistrInfo.setVersionNo(entity.getVersionNo());
		// 省份编码
		expressBigRegionDistrInfo.setProvCode(entity.getProvCode());
		// 省份名称
		expressBigRegionDistrInfo.setProvName(entity.getProvName());
		// 城市编码
		expressBigRegionDistrInfo.setCityCode(entity.getCityCode());
		// 城市名称
		expressBigRegionDistrInfo.setCityName(entity.getCityName());
		// 区县编码
		expressBigRegionDistrInfo.setCountyCode(entity.getCountyCode());
		// 区县名称
		expressBigRegionDistrInfo.setCountyName(entity.getCountyName());
		// 是否启用
		expressBigRegionDistrInfo.setActive(entity.getActive());
		// 创建时间
		expressBigRegionDistrInfo.setCreateDate(entity.getCreateDate());
		// 更新时间
		expressBigRegionDistrInfo.setModifyDate(entity.getModifyDate());
		// 创建人
		expressBigRegionDistrInfo.setCreateUser(entity.getCreateUser());
		// 修改人
		expressBigRegionDistrInfo.setModifyUser(entity.getModifyUser());
		return expressBigRegionDistrInfo;

	}
}
