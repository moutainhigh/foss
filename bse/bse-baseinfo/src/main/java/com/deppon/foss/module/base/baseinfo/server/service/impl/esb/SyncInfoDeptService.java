package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.cubc.inteface.domain.InfoDeptInfo;
import com.deppon.cubc.inteface.domain.SyncInfoDeptRequest;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.pojo.transformer.jaxb.SyncInfoDeptRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncInfoDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;

public class SyncInfoDeptService implements ISyncInfoDeptService{

	private static final Logger log = LoggerFactory
			.getLogger(SyncInfoDeptService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYN_INFORMATIONDEP";

	private static final String VERSION = "1.0";
	/**
	 * 
	 * <p>同步信息部给cubc</p> 
	 * @author 273311 
	 * @date 2016-10-29 下午3:44:39
	 * @param entitys
	 * @param operateType 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncInfoDeptService#SyncInfoDept(java.util.List, java.lang.String)
	 */
	@Override
	public void SyncInfoDept(List<InfoDeptEntity> entitys, String operateType) {
		try {
		if (CollectionUtils.isEmpty(entitys)) {
			throw new BusinessException("传入对象为空");
		}
		SyncInfoDeptRequest request = new SyncInfoDeptRequest();
		List<InfoDeptInfo> InfoDeptInfos = new ArrayList<InfoDeptInfo>();
		InfoDeptInfo info;
		for (InfoDeptEntity entity : entitys) {
			if (entity == null) {
				continue;
			}
			//实体转换
			info =new InfoDeptInfo();
			String requestInfoDept = JSONObject.toJSONString(entitys.get(0));
			info =JSONObject.parseObject(requestInfoDept,com.deppon.cubc.inteface.domain.InfoDeptInfo.class);
			InfoDeptInfos.add(info);
		}
		request.getInfoDept().addAll(InfoDeptInfos);
		request.setOperationType(operateType);
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUIDUtils.getUUID());
		accessHeader.setVersion(VERSION);
		accessHeader.setBusinessDesc1("同步信息部基础资料到cubc");
		log.info("start to send Airline info to cubc 开始同步信息部信息 cubc：\n"
				+ new SyncInfoDeptRequestTrans()
						.fromMessage(request));
		ESBJMSAccessor.asynReqeust(accessHeader, request);
		log.info("end to send Airline info to cubc 结束 同步信息部信息 到cubc：\n"
				+ new SyncInfoDeptRequestTrans()
						.fromMessage(request));	
		} catch (ESBException e) {
			log.error("同步信息部到cubc平台，发送数据到ESB错误:      "+e.getMessage(), e);
			e.printStackTrace();
		}
		
	}
}
