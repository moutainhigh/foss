package com.deppon.foss.module.base.baseinfo.server.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.dpap.esb.mqc.core.exception.ESBBusinessException;
import com.deppon.dpap.esb.mqc.core.process.IProcess;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressBigRegionDistrDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.oms.inteface.domain.ExpressBigRegionDistrInfo;
import com.deppon.oms.inteface.domain.SendExpressBigRegionDistrRequest;
import com.deppon.oms.inteface.domain.SendExpressBigRegionDistrResponse;

public class SyncExpressBigRegionDistrListener implements IProcess{

	//日志记录
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncExpressBigRegionDistrListener.class);
	private IExpressBigRegionDistrDao expressBigRegionDistrDao;
	/**
	 * 设置快递大区与行政区域映射关系DAO接口
	 * @param expressBigRegionDistrDao
	 */
	public void setExpressBigRegionDistrDao(
			IExpressBigRegionDistrDao expressBigRegionDistrDao) {
		this.expressBigRegionDistrDao = expressBigRegionDistrDao;
	}
	@Override
	public Object process(Object req) throws ESBBusinessException {
		LOGGER.info("------------接收快递大区与行政区域映射关系信息开始--------------");
		SendExpressBigRegionDistrRequest requset = (SendExpressBigRegionDistrRequest) req;
		SendExpressBigRegionDistrResponse response = new SendExpressBigRegionDistrResponse();
		
		for (ExpressBigRegionDistrInfo dto: requset.getAreaMappingRequest()) {	
				try {
					LOGGER.info("请求参数"
							+ com.alibaba.fastjson.JSONObject.toJSONString(dto));
					if (StringUtils.isBlank(dto.getId())) {
						throw new ESBBusinessException("id is null");
					}
					//将OMS传递过来的数据设置到实体中ExpressBigRegionDistrEntity
					ExpressBigRegionDistrEntity entity = new ExpressBigRegionDistrEntity();
					entity.setId(dto.getId());
					entity.setActive(dto.getActive());
					entity.setOrgCode(dto.getOrgCode());
					entity.setCreateDate(dto.getCreateTime());
					entity.setModifyDate(dto.getModifyTime());
					entity.setDistrictCode(dto.getDistrictCode());
					entity.setCreateUser(dto.getCreateUserCode());
					entity.setModifyUser(dto.getModifyUserCode());
					entity.setVersionNo(dto.getVersionNo());
					//同步做校验
					//ActiveEntity:FOSS表中原有数据,以OMS同步过来的数据查询FOSS原有表中的数据
					ExpressBigRegionDistrEntity	activeEntity = expressBigRegionDistrDao.queryInfoByDistrictCodeAndActive
							 (dto.getOrgCode(), dto.getDistrictCode(), FossConstants.ACTIVE);
					 if(activeEntity!=null){    			 
						/*判断已有数据与更新数据的创建时间，如果已有数据创建时间小于新来数据创建时间,以传递过来的数据为准，
						将原有数据删除,新增传递过来的数据即可*/		 
						 if(activeEntity.getVersionNo().compareTo(dto.getVersionNo()) < 0){
							 List<String> ids = new ArrayList<String>();
							 ids.add(activeEntity.getId());
							 expressBigRegionDistrDao.deleteInfo(ids, activeEntity.getModifyUser());			 
							 if(FossConstants.ACTIVE.equals(entity.getActive())){
								 expressBigRegionDistrDao.addInfo(entity);   
							 }              
						 }
					 }else{	
						 if(FossConstants.ACTIVE.equals(entity.getActive())){
							 expressBigRegionDistrDao.addInfo(entity);	
						 }						
					 }	
														
				} catch (Exception e) {
					LOGGER.error("接收快递大区与行政区域映射关系信息出错:" + e.getMessage(),e);
					response.setIsSuccess("失败");
					response.setExceptionMsg(e.getMessage());
					return response;
				}				
		}
		LOGGER.info("------------接收快递大区与行政区域映射关系信息结束--------------");
		response.setIsSuccess("成功");
		return response;
	}
	
	@Override
	public Object errorHandler(Object arg0) throws ESBBusinessException {
		
		return null;
	}

}
