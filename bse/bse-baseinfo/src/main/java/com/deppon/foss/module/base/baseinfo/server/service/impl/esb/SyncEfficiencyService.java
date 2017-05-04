package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.ecs.inteface.domain.EfficiencyManInfo;
import com.deppon.ecs.inteface.domain.EfficiencyTonInfo;
import com.deppon.ecs.inteface.domain.EfficiencyVehicleInfo;
import com.deppon.ecs.inteface.domain.SyncEfficiencyRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncEfficiencyRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncEfficiencyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyManEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;

/**
 * 装卸车标准信息接口Service
 * @author 310854
 * @date 2016-4-6
 */
public class SyncEfficiencyService implements ISyncEfficiencyService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncEfficiencyService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_RECEIVE_ZXCARTIME";

	private static final String VERSION = "1.0";

	/**
     * 系统配置参数 Service接口
     */
    private IConfigurationParamsService configurationParamsService;
    
    /**
     * @param configurationParamsService the configurationParamsService to set
     */
    public void setConfigurationParamsService(
    	IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }
	
	@Override
	public void syncEfficiency(List<?> entitys,int operationType)  throws BusinessException{
		// TODO Auto-generated method stub
		if(!configurationParamsService.queryBseSwitch4Ecs()){
			return;
		}
		StringBuilder logs = new StringBuilder();
		try {
			if (CollectionUtils.isEmpty(entitys)) {
				throw new BusinessException("传入对象为空");
			}
			SyncEfficiencyRequest efficiencyRequest = new SyncEfficiencyRequest();
			List<EfficiencyTonInfo> efficiencyTonInfos = new ArrayList<EfficiencyTonInfo>();
			List<EfficiencyManInfo> efficiencyManInfos = new ArrayList<EfficiencyManInfo>();
			List<EfficiencyVehicleInfo> efficiencyVehicleInfos = new ArrayList<EfficiencyVehicleInfo>();
			

			StringBuilder versionNos = new StringBuilder();
			StringBuilder codes = new StringBuilder();
			
			for(int i = 0;i < entitys.size(); i++){
				if(entitys.get(i) instanceof LoadAndUnloadEfficiencyManEntity){
					logs.append("装卸车标准-吨-人天");
					EfficiencyManInfo efficiencyManInfo = transFossToEsb((LoadAndUnloadEfficiencyManEntity)entitys.get(i),operationType);
					efficiencyManInfos.add(efficiencyManInfo);
					codes.append(SymbolConstants.EN_COMMA);
				} else if(entitys.get(i) instanceof LoadAndUnloadEfficiencyTonEntity){
					logs.append("装卸车标准-吨-时间");
					EfficiencyTonInfo efficiencyTonInfo =transFossToEsb((LoadAndUnloadEfficiencyTonEntity)entitys.get(i),operationType);
					efficiencyTonInfos.add(efficiencyTonInfo);
					codes.append(SymbolConstants.EN_COMMA);
				} else if(entitys.get(i) instanceof LoadAndUnloadEfficiencyVehicleEntity){
					logs.append("装卸车标准-车-时间");
					EfficiencyVehicleInfo efficiencyVehicleInfo = transFossToEsb((LoadAndUnloadEfficiencyVehicleEntity)entitys.get(i),operationType);
					efficiencyVehicleInfos.add(efficiencyVehicleInfo);
					codes.append(SymbolConstants.EN_COMMA);
				}
			}

			efficiencyRequest.getEfficiencyMan().addAll(efficiencyManInfos);
			efficiencyRequest.getEfficiencyTon().addAll(efficiencyTonInfos);
			efficiencyRequest.getEfficiencyVehicle().addAll(efficiencyVehicleInfos);
			
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(codes.toString());
			accessHeader
					.setBusinessDesc1("send Efficiency info to other platform.同步"+logs.toString()+"信息 到其它平台");
			accessHeader.setBusinessDesc2(versionNos.toString());
			accessHeader.setVersion(VERSION);

			log.info("start to send Efficiency info to other platform 开始 同步"+logs.toString()+"信息 到其它平台：\n"
					+ new SyncEfficiencyRequestTrans()
							.fromMessage(efficiencyRequest));

			ESBJMSAccessor.asynReqeust(accessHeader, efficiencyRequest);

			log.info("end to send Efficiency info to other platform 结束 同步"+logs.toString()+"信息 到其它平台：\n"
					+ new SyncEfficiencyRequestTrans()
							.fromMessage(efficiencyRequest));

		} catch (Exception e) {
			// TODO: handle exception
			log.error("同步"+logs.toString()+" 到其快递,发送数据到ESB错误 :     "+e.getMessage(), e);
			throw new BusinessException("同步"+logs.toString()+" 到其快递,发送数据到ESB错误");
		}

	}

	/**
	 * 装卸车标准-吨-人天
	 * @author 310854
	 * @date 2016-4-7
	 */
	private EfficiencyManInfo transFossToEsb(LoadAndUnloadEfficiencyManEntity entity,int operationType) {
		EfficiencyManInfo efficiencyManInfo = new EfficiencyManInfo();
		efficiencyManInfo.setActive(entity.getActive());
		efficiencyManInfo.setCreateTime(entity.getCreateDate());
		efficiencyManInfo.setCreateUserCode(entity.getCreateUser());
		efficiencyManInfo.setId(entity.getId());
		if(null != entity.getLoadVolumeStd()){
			efficiencyManInfo.setLoadVolumeStd(entity.getLoadVolumeStd().toString());
		}
		if(null != entity.getLoadVolumeStd()){
			efficiencyManInfo.setLoadWeightStd(entity.getLoadWeightStd().toString());
		}
		efficiencyManInfo.setModifyTime(entity.getModifyDate());
		efficiencyManInfo.setModifyUserCode(entity.getModifyUser());
		efficiencyManInfo.setOrgCode(entity.getOrgCode());
		efficiencyManInfo.setOrgName(entity.getOrgName());
		efficiencyManInfo.setOperationType(operationType);
		
		return efficiencyManInfo;
	}
	
	/**
	 * 装卸车标准-吨-时间
	 * @author 310854
	 * @date 2016-4-7
	 */
	private EfficiencyTonInfo transFossToEsb(LoadAndUnloadEfficiencyTonEntity entity,int operationType) {
		EfficiencyTonInfo efficiencyTonInfo = new EfficiencyTonInfo();
		efficiencyTonInfo.setActive(entity.getActive());
		efficiencyTonInfo.setCreateTime(entity.getCreateDate());
		efficiencyTonInfo.setCreateUserCode(entity.getCreateUser());
		efficiencyTonInfo.setId(entity.getId());
		if(null != entity.getLoadVolumeStd()){
			efficiencyTonInfo.setLoadVolumeStd(entity.getLoadVolumeStd().toString());
		}
		if(null != entity.getLoadWeightStd()){
			efficiencyTonInfo.setLoadWeightStd(entity.getLoadWeightStd().toString());
		}
		efficiencyTonInfo.setModifyTime(entity.getModifyDate());
		efficiencyTonInfo.setModifyUserCode(entity.getModifyUser());
		efficiencyTonInfo.setOrgCode(entity.getOrgCode());
		efficiencyTonInfo.setOrgName(entity.getOrgName());
		if(null != entity.getUnloadVolumeStd()){
			efficiencyTonInfo.setUnloadVolumeStd(entity.getUnloadVolumeStd().toString());
		}
		if(null != entity.getUnloadWeightStd()){
			efficiencyTonInfo.setUnloadWeightStd(entity.getUnloadWeightStd().toString());
		}
		efficiencyTonInfo.setOperationType(operationType);
		
		return efficiencyTonInfo;
	}
	
	/**
	 * 装卸车标准-车-时间
	 * @author 310854
	 * @date 2016-4-7
	 */
	private EfficiencyVehicleInfo transFossToEsb(LoadAndUnloadEfficiencyVehicleEntity entity,int operationType) {
		EfficiencyVehicleInfo efficiencyVehicleInfo = new EfficiencyVehicleInfo();
		efficiencyVehicleInfo.setActive(entity.getActive());
		efficiencyVehicleInfo.setCreateTime(entity.getCreateDate());
		efficiencyVehicleInfo.setCreateUserCode(entity.getCreateUser());
		efficiencyVehicleInfo.setGlCpLoadHours(entity.getGlCpLoadHours());
		efficiencyVehicleInfo.setGlCpLoadMins(entity.getGlCpLoadMins());
		efficiencyVehicleInfo.setGlCpUnloadHours(entity.getGlCpUnloadHours());
		efficiencyVehicleInfo.setGlCpUnloadMins(entity.getGlCpUnloadMins());
		efficiencyVehicleInfo.setId(entity.getId());
		efficiencyVehicleInfo.setModifyTime(entity.getModifyDate());
		efficiencyVehicleInfo.setModifyUserCode(entity.getModifyUser());
		efficiencyVehicleInfo.setNcpLoadHours(entity.getNcpLoadHours());
		efficiencyVehicleInfo.setNcpLoadMins(entity.getNcpLoadMins());
		efficiencyVehicleInfo.setNcpUnloadHours(entity.getNcpUnloadHours());
		efficiencyVehicleInfo.setNcpUnloadMins(entity.getNcpUnloadMins());
		efficiencyVehicleInfo.setOrgCode(entity.getOrgCode());
		efficiencyVehicleInfo.setOrgName(entity.getOrgName());
		if(null != entity.getVehicleLength()){
			efficiencyVehicleInfo.setVehicleLength(entity.getVehicleLength().toString());
		}
		efficiencyVehicleInfo.setVehicleTypeLength(entity.getVehicleTypeLength());
		efficiencyVehicleInfo.setVirtualCode(entity.getVirtualCode());
		efficiencyVehicleInfo.setOperationType(operationType);
		efficiencyVehicleInfo.setNglCpLoadHours(entity.getNglCpLoadHours());
		efficiencyVehicleInfo.setNglCpLoadMins(entity.getNglCpLoadMins());
		efficiencyVehicleInfo.setNglCpUnloadHours(entity.getNglCpUnloadHours());
		efficiencyVehicleInfo.setNglCpUnloadMins(entity.getNglCpUnloadMins());
		
		return efficiencyVehicleInfo; 
	}

}
