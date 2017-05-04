package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.inteface.domain.crm.WxdbSyncInfo;
import com.deppon.esb.inteface.domain.crm.WxdbSyncRequest;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncWxdbService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SatellitePartSalesDeptEntity;
import com.deppon.foss.pojo.transformer.jaxb.WxdbSyncRequestTrans;

public class SyncWxdbService implements ISyncWxdbService {
	
	private static final Logger logger = LoggerFactory.getLogger(SyncWxdbService.class);
	
	//服务编码
	private static final String SYNC_WXDB_SERVE_CODE = "ESB_FOSS2ESB_SEND_SHOPSANDSATELLITE_RELATIONINFO"; 
	
	private static final String VERSION = "1.0";
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	@Override
	public void syncWxdbToCrm(List<SatellitePartSalesDeptEntity> addDeptEntities,int operatorSign) {
		WxdbSyncRequest wxdbSyncRequest = new WxdbSyncRequest();
		//卫星网点同步crm
		List<WxdbSyncInfo> wxdbSyncInfos = new ArrayList<WxdbSyncInfo>();
		for(SatellitePartSalesDeptEntity sate:addDeptEntities){
			//根据营业部编码和卫星点部编码获取标杆编码和部门名称
			List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntitys =null;
			orgAdministrativeInfoEntitys = orgAdministrativeInfoService.
					queryOrgAdministrativeInfoBatchByCode(
					new String[]{sate.getSalesDeptCode(),sate.getSatelliteDeptCode()});
			
			wxdbSyncInfos.add(transFossToEsb(orgAdministrativeInfoEntitys,sate,operatorSign));
		}
		wxdbSyncRequest.getWxdbSyncInfos().addAll(wxdbSyncInfos);
		StringBuilder versionNos = new StringBuilder();
		StringBuilder codes = new StringBuilder();
		for(SatellitePartSalesDeptEntity deptEntity:addDeptEntities){
			versionNos.append(SymbolConstants.EN_SEMICOLON);
			versionNos.append(deptEntity.getSalesDeptCode()).append(deptEntity.getSatelliteDeptCode());
			codes.append(SymbolConstants.EN_SEMICOLON);
			codes.append(deptEntity.getSalesDeptName()).append(deptEntity.getSatelliteDeptName());
		}
		AccessHeader header = new AccessHeader();
		header.setEsbServiceCode(SYNC_WXDB_SERVE_CODE);
		header.setBusinessId(codes.toString());
		header.setBusinessDesc1("同步卫星网点到crm");
		header.setBusinessDesc2(versionNos.toString());
		header.setVersion(VERSION);
		try {
			logger.info("开始调用 同步营业部卫星点部映射到crm：\n"
					+ new WxdbSyncRequestTrans()
							.fromMessage(wxdbSyncRequest));
			ESBJMSAccessor.asynReqeust(header, wxdbSyncRequest);
			logger.info("结束调用 同步营业部卫星点部映射到crm：\n"
					+ new WxdbSyncRequestTrans()
							.fromMessage(wxdbSyncRequest));
		} catch (ESBConvertException e) {
			e.printStackTrace();
		} catch (ESBException e) {
			e.printStackTrace();
		}
	}
	
	private WxdbSyncInfo transFossToEsb(List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntitys,SatellitePartSalesDeptEntity sate,int operatorSign){
		WxdbSyncInfo wxdbSyncInfo = new WxdbSyncInfo();
		if(orgAdministrativeInfoEntitys!=null){
			//判断获得的第一个部门是否是营业部
			if(orgAdministrativeInfoEntitys.get(0).getCode().equals(sate.getSalesDeptCode())){
				wxdbSyncInfo.setYybStandardCode(orgAdministrativeInfoEntitys.get(0).getUnifiedCode());
				wxdbSyncInfo.setYybName(orgAdministrativeInfoEntitys.get(0).getName());
				wxdbSyncInfo.setWxdbStandardCode(orgAdministrativeInfoEntitys.get(1).getUnifiedCode());
				wxdbSyncInfo.setWxdbName(orgAdministrativeInfoEntitys.get(1).getName());
			}else{
				wxdbSyncInfo.setYybStandardCode(orgAdministrativeInfoEntitys.get(1).getUnifiedCode());
				wxdbSyncInfo.setYybName(orgAdministrativeInfoEntitys.get(1).getName());
				wxdbSyncInfo.setWxdbStandardCode(orgAdministrativeInfoEntitys.get(0).getUnifiedCode());
				wxdbSyncInfo.setWxdbName(orgAdministrativeInfoEntitys.get(0).getName());
			}
			wxdbSyncInfo.setOperatorSign(operatorSign);
		}
		return wxdbSyncInfo;
	}
}
