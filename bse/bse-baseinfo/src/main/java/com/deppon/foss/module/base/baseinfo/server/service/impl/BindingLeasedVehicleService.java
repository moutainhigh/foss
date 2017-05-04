package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBindingLeasedTruckDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleException;
import com.deppon.foss.module.pickup.pda.api.server.service.IBindingLeasedVehicleService;
import com.deppon.foss.module.pickup.pda.api.shared.domain.BindingLeasedTruckEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 外请车司机登录APP
 * PDA调用，通过车牌号获取车队code、操作人code
 * @author 310854
 * @date 2016-5-28
 */
public class BindingLeasedVehicleService implements
		IBindingLeasedVehicleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BindingLeasedVehicleService.class);
	
	//  外请车绑定车队
    private IBindingLeasedTruckDao bindingLeasedTruckDao;
	
    private ILeasedVehicleDao leasedVehicleDao;
    
    public void setBindingLeasedTruckDao(
			IBindingLeasedTruckDao bindingLeasedTruckDao) {
		this.bindingLeasedTruckDao = bindingLeasedTruckDao;
	}
    
	public void setLeasedVehicleDao(ILeasedVehicleDao leasedVehicleDao) {
		this.leasedVehicleDao = leasedVehicleDao;
	}

	/**
     * 外请车司机登录APP
     * PDA调用，通过车牌号获取车队code、操作人code
     * @author 310854
     * @date 2016-5-28
     */
	@Override
	public BindingLeasedTruckEntity queryBindingLeasedDate(String vehicleNo) throws LeasedVehicleException{
		try{
			if(StringUtils.isEmpty(vehicleNo)){
				throw new LeasedVehicleException("车牌号为空");
			}
			LeasedTruckEntity leasedTruck = new LeasedTruckEntity();
			leasedTruck.setVehicleNo(vehicleNo);
			leasedTruck.setActive(FossConstants.ACTIVE);
	
			//   查询此车牌号是否存在
			List<LeasedTruckEntity> leasedTruckList = leasedVehicleDao
					.queryLeasedServiceDateList(leasedTruck,
							0, NumberConstants.NUMBER_10);
			
			if(null != leasedTruckList && 0 < leasedTruckList.size()){
				//   查询此车牌号是否绑定顶级车队
				com.deppon.foss.module.base.baseinfo.api.shared.domain.BindingLeasedTruckEntity entity = 
						new com.deppon.foss.module.base.baseinfo.api.shared.domain.BindingLeasedTruckEntity();
				
				entity = bindingLeasedTruckDao.queryBindingLeasedDate(leasedTruck);
				
				BindingLeasedTruckEntity bindingLeasedTruckEntity = new BindingLeasedTruckEntity();
				
				bindingLeasedTruckEntity.setVehicleNo(vehicleNo);
				if(null != entity){
					if( null != entity.getOrgCode()){
						bindingLeasedTruckEntity.setOrgCode(entity.getOrgCode());
					}
					if(null != entity.getCreateUser()){
						bindingLeasedTruckEntity.setOperatorCode(entity.getCreateUser());
					}
					if(null != entity.getDeiverName()){
						bindingLeasedTruckEntity.setDeiverName(entity.getDeiverName());
					}
				}
				return bindingLeasedTruckEntity;
			}else {
				return null;
			}
			
		}catch(TooManyResultsException e){
			LOGGER.debug("调试：根据提供的条件检索的结果数据有误，唯一的数据出现多条。");
			throw new LeasedVehicleException("根据提供的条件检索的结果数据有误，唯一的数据出现多条。");
		}catch(LeasedVehicleException e){
			LOGGER.debug("外请车绑定车队有误！"+e.getMessage());
			throw e;
		}
	}

}
