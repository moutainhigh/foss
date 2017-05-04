package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.inteface.domain.AirPortInfo;
import com.deppon.cubc.inteface.domain.SyncAirPortRequest;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.pojo.transformer.jaxb.SyncAirPortRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAirPortService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;

public class SyncAirPortService implements ISyncAirPortService{

	private static final Logger log = LoggerFactory
			.getLogger(SyncAirPortService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYN_AIRPORTS";

	private static final String VERSION = "1.0";
	/**
	 * 
	 * <p>同步机场给cubc</p> 
	 * @author 273311 
	 * @date 2016-10-29 上午11:08:18
	 * @param entitys
	 * @param operateType 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAirPortService#SyncAirPort(java.util.List, java.lang.String)
	 */
	@Override
	public void SyncAirPort(List<AirportEntity> entitys,String operateType) {
		try {
		if (CollectionUtils.isEmpty(entitys)) {
			throw new BusinessException("传入对象为空");
		}
		SyncAirPortRequest request = new SyncAirPortRequest();
		List<AirPortInfo> airPortInfos = new ArrayList<AirPortInfo>();
		for (AirportEntity entity : entitys) {
			if (entity == null) {
				continue;
			}
			AirPortInfo info = this.transFossToEsb(entity);
			airPortInfos.add(info);
		}
		request.getAirPort().addAll(airPortInfos);
		request.setOperationType(operateType);
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUIDUtils.getUUID());
		accessHeader.setVersion(VERSION);
		accessHeader.setBusinessDesc1("同步机场信息到cubc");
		log.info("start to send AirPort info to cubc 开始同步机场信息 cubc：\n"
				+ new SyncAirPortRequestTrans()
						.fromMessage(request));
		ESBJMSAccessor.asynReqeust(accessHeader, request);
		log.info("end to send AirPort info to cubc 结束 同步机场信息 到cubc：\n"
				+ new SyncAirPortRequestTrans()
						.fromMessage(request));	
		} catch (ESBException e) {
			log.error("同步机场信息 到cubc平台，发送数据到ESB错误:      "+e.getMessage(), e);
			e.printStackTrace();
		}
		
	}
    /**
     * 
     * <p>转换方法</p> 
     * @author 273311 
     * @date 2016-10-29 上午11:09:02
     * @param entity
     * @return
     * @see
     */
	private AirPortInfo transFossToEsb(AirportEntity entity) {
		AirPortInfo airPortInfo = new AirPortInfo();
		// ID
		airPortInfo.setID(entity.getId());
		// 机场名称
		airPortInfo.setAirportName(entity.getAirportName());
		// 机场三字码
		airPortInfo.setAirportCode(entity.getAirportCode());
		// 所在省份
		airPortInfo.setProvCode(entity.getProvCode());
		// 扩展字段省份名称
		airPortInfo.setProvName(entity.getProvName());
		// 机场所在城市>
		airPortInfo.setCityCode(entity.getCityCode());
		// 扩展字段城市名称
		airPortInfo.setCityName(entity.getCityName());
		// 所属区县
		airPortInfo.setCountyCode(entity.getCountyCode());
		// 联系人
		airPortInfo.setContact(entity.getContact());
		// 联系电话
		airPortInfo.setContactPhone(entity.getContactPhone());
		// 联系人地址
		airPortInfo.setPickupAddress(entity.getPickupAddress());
		// 中文简称
		airPortInfo.setSimplename(entity.getSimplename());
		// 中文简称拼音
		airPortInfo.setPinyin(entity.getPinyin());
		// 是否启用
		airPortInfo.setActive(entity.getActive());
		// 机场描述信息
		airPortInfo.setNotes(entity.getNotes());
		// 创建时间
		airPortInfo.setCreateDate(entity.getCreateDate());
		// 更新时间
		airPortInfo.setModifyDate(entity.getModifyDate());
		// 创建人
		airPortInfo.setCreateUser(entity.getCreateUser());
		// 修改人
		airPortInfo.setModifyUser(entity.getModifyUser());
		return airPortInfo;

	}
}
