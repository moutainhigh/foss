/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWayGXGOrderDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillGXGService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillGXGEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResponseGXG;
import com.deppon.foss.util.UUIDUtils;
/**
 * @author 
 *
 */
public class WaybillGXGService implements IWaybillGXGService{
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillGXGService.class);
	
	private IWayGXGOrderDao wayGXGOrderDao;

	public ResponseGXG addWaybillExpressEntity(WaybillGXGEntity waybillGXGEntity) {
		LOGGER.info("----"+waybillGXGEntity.toString());
		ResponseGXG responseDto=new ResponseGXG();
		int sum=wayGXGOrderDao.checkGXGOrderbill(waybillGXGEntity.getCustomerLableNum());
		if(sum==1){
			responseDto.setResult("false");
			responseDto.setResultCode("3001");
			responseDto.setDesc("标签编号重复：customerLableNum");
			return responseDto;
		}else{
			waybillGXGEntity.setId(UUIDUtils.getUUID());
			int num=wayGXGOrderDao.createGXGOrderbill(waybillGXGEntity);
			if(num==1){
				responseDto.setResult("true");
				responseDto.setResultCode("1000");
				responseDto.setDesc("成功");
			}
		}
		return responseDto;
	}

	public IWayGXGOrderDao getWayGXGOrderDao() {
		return wayGXGOrderDao;
	}

	public void setWayGXGOrderDao(IWayGXGOrderDao wayGXGOrderDao) {
		this.wayGXGOrderDao = wayGXGOrderDao;
	}
	
}
