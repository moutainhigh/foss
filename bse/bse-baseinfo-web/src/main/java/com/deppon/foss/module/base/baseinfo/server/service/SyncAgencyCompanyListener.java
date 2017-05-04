package com.deppon.foss.module.base.baseinfo.server.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.AgentCompanyInfo;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendAgentCompanyRequest;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendAgentCompanyResponse;
 
public class SyncAgencyCompanyListener implements IProcess{
	//日志记录
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncAgencyCompanyListener.class);
	
	private String success  = "success";
	
	private String failure  = "failure";
    /**
     * 偏线代理公司DAO接口.
     */
    
    private IVehicleAgencyCompanyDao vehicleAgencyCompanyDao;
    
    /**
     * 设置 偏线代理公司DAO接口.
     * 
     * @param vehicleAgencyCompanyDao
     *            the new 偏线代理公司DAO接口
     */
    public void setVehicleAgencyCompanyDao(
	    IVehicleAgencyCompanyDao vehicleAgencyCompanyDao) {
	this.vehicleAgencyCompanyDao = vehicleAgencyCompanyDao;
    }
    
	@Override
	@Transactional
	public Object process(Object req) {
		LOGGER.info("------------接收外发代理公司信息开始--------------");
		
		SendAgentCompanyResponse response = new SendAgentCompanyResponse();
		SendAgentCompanyRequest request = (SendAgentCompanyRequest) req;

		for(AgentCompanyInfo dto :  request.getAgentCompanyInfo()){
			{
				//创建对象
				try{
					LOGGER.info("请求参数:"+ com.alibaba.fastjson.JSONObject.toJSONString(dto));
					//设置转换类
					if(StringUtils.isBlank(dto.getId())){
						throw new ESBBusinessException("id is null");
					}
					
					BusinessPartnerEntity entity = new BusinessPartnerEntity();

					entity.setActive(dto.getActive());
					entity.setAgentCompanyCode(dto.getAgentCompanyCode());
					entity.setAgentCompanyName(dto.getAgentCompanyName());
					entity.setAgentCompanySort(dto.getAgentCompanySort());
					entity.setCityCode(dto.getCityCode());
					entity.setContact(dto.getContact());
					entity.setContactAddress(dto.getContactAddress());
					entity.setContactPhone(dto.getContactPhone());
					entity.setCreateDate(dto.getCreateDate());
					entity.setCreateUser(dto.getCreateUser());
					entity.setId(dto.getId());
					entity.setManagement(dto.getManagement());
					entity.setMobilePhone(dto.getMobilePhone());
					entity.setModifyDate(dto.getModifyDate());
					entity.setModifyUser("ECS");
					entity.setNotes(dto.getNotes());
					entity.setProvCode(dto.getProvCode());
					entity.setSimplename(dto.getSimplename());
					entity.setVersionNo(Long.parseLong(dto.getVersionNo()));
					entity.setVirtualCode(dto.getVirtualCode());
					entity.setInterFaceServiceCode(dto.getInterFaceServiceCode());
					//核销方式
					entity.setVerificationMethods(dto.getVerificationMethods());
					
					//如果flag为add，说明为新增数据，只有创建时间，用创建时间来比较
					if(dto.getFlag().equals("add")){
						//313353 sonar
						this.sonarSplitOne(dto, entity);
					}
					
					//如果flag为update，说明为修改数据,只有修改时间，用修改时间来比较
					if(dto.getFlag().equals("update")){
						BusinessPartnerEntity activeEntity = vehicleAgencyCompanyDao.queryEntityByCodeAndActive(dto.getAgentCompanyCode(),FossConstants.ACTIVE);
						//313353 sonar
						this.sonarSplitTwo(dto, entity, activeEntity);
						//如果数据不存在：两种情况，一种之前没有数据传来，一种只传来一条作废数据
						if(activeEntity == null){
							BusinessPartnerEntity inactiveEntity= vehicleAgencyCompanyDao.queryEntityByCodeAndActive(dto.getAgentCompanyCode(), FossConstants.INACTIVE);
							//如果存在作废数据，则直接新增作废此数据
							if(inactiveEntity != null){
								entity.setVirtualCode(inactiveEntity.getVirtualCode());
								vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
								vehicleAgencyCompanyDao.deleteVehicleAgencyCompanyById(entity.getId());
								}
							else
								//如果不存在任何数据，则新增此数据
								vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
						}					
					}
					//如果flag为delete，新增并作废所有数据即可
					if(dto.getFlag().equals("delete")){
						BusinessPartnerEntity activeEntity= vehicleAgencyCompanyDao.queryEntityByCodeAndActive(dto.getAgentCompanyCode(), FossConstants.ACTIVE);
						if(activeEntity!=null){
							entity.setVirtualCode(activeEntity.getVirtualCode());
							entity.setId(activeEntity.getId());
							}
						vehicleAgencyCompanyDao.updateVehicleAgencyCompanyById(entity);
					}

				}catch(Exception e) {
					LOGGER.error("接收外发代理公司信息出错：" + e.getMessage(),e);
					response.setId(dto.getId());
					response.setMessage(e.getMessage());
					response.setResult(failure);
					return response;
				}
			}	
		}
		LOGGER.info("------------接收外发代理公司信息结束--------------");
		response.setResult(success);
		return response;
	}

	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(AgentCompanyInfo dto, BusinessPartnerEntity entity) {
		BusinessPartnerEntity activeEntity = vehicleAgencyCompanyDao.queryEntityByCodeAndActive(dto.getAgentCompanyCode(),FossConstants.ACTIVE);
		//如果数据已存在
		if(activeEntity != null){
			//判断已有数据与更新数据的创建时间，如果已有数据创建时间小于新来数据创建时间,说明修改数据先到，将此数据插入作废即可
			if(activeEntity.getCreateDate().compareTo(dto.getCreateDate()) < 0){
				entity.setVirtualCode(activeEntity.getVirtualCode());
				vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
				vehicleAgencyCompanyDao.deleteVehicleAgencyCompanyById(entity.getId());
				}
			}
		//如果数据不存在，两种可能，一种删除数据先到，一种之前没有数据过来
		if(activeEntity == null){
			BusinessPartnerEntity inactiveEntity= vehicleAgencyCompanyDao.queryEntityByCodeAndActive(dto.getAgentCompanyCode(), FossConstants.INACTIVE);
			//如果存在删除数据，则直接新增作废此数据
			if(inactiveEntity != null){
				entity.setVirtualCode(inactiveEntity.getVirtualCode());
				vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
				vehicleAgencyCompanyDao.deleteVehicleAgencyCompanyById(entity.getId());
				}
			//如果不存在先来的数据，则新增此条数据
			else
				vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitTwo(AgentCompanyInfo dto, BusinessPartnerEntity entity, BusinessPartnerEntity activeEntity) {
		//如果数据已存在
		if(activeEntity != null){
			//如果创建时间不为空，则之前过来的数据为新增数据
			if(activeEntity.getCreateDate()!=null){
				//判断已有数据与新来数据的创建时间，如果已有数据创建时间小于新来数据创建时间,说明此条数据最新，作废之前的数据，新增此条即可
				if(activeEntity.getCreateDate().compareTo(dto.getModifyDate()) < 0){
					entity.setVirtualCode(activeEntity.getVirtualCode());
					vehicleAgencyCompanyDao.deleteVehicleAgencyCompanyById(activeEntity.getId());
					vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
				}
			}
			//如果创建时间为空，则之前过来的数据为删除或修改
			if(activeEntity.getCreateDate() == null){
				//如果已存在数据修改时间比同步数据时间早，则作废之前数据，新增同步数据
				if(activeEntity.getModifyDate().compareTo(dto.getModifyDate()) < 0){
					entity.setVirtualCode(activeEntity.getVirtualCode());
					vehicleAgencyCompanyDao.deleteVehicleAgencyCompanyById(activeEntity.getId());
					vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
				}
				//否则直接插入作废同步数据
				else{
					entity.setVirtualCode(activeEntity.getVirtualCode());
					vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
					vehicleAgencyCompanyDao.deleteVehicleAgencyCompanyById(entity.getId());		
					}
			}
		}
	}
	
	
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		return null;
	}


}
