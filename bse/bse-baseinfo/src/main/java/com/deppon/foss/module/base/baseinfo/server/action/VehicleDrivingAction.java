package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleDrivingService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.VehicleDrivingVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

public class VehicleDrivingAction extends AbstractAction {
	/**
	 * 长途车队信息action接口
	 * @author 323136
	 */
	private static final long serialVersionUID = -25864353743313214L;
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDrivingAction.class);
    private IVehicleDrivingService vehicleDrivingService;
    

	private VehicleDrivingVo vehicleDrivingVo = new VehicleDrivingVo();

	
	/**
	 * 查询长途车队信息
	 * @return
	 */
	@JSON
	public String queryVehicleDriving(){
		
		try{
			
			//验证参数
			if(vehicleDrivingVo.getVehicleDrivingQueryDto()==null){
				throw new BusinessException("查询长途车队信息失败,参数异常");
			}
			//先查询总条数
			long total = vehicleDrivingService.queryTotalByCondition(vehicleDrivingVo.getVehicleDrivingQueryDto());
			//如果总数大于0
			if(total>0){
				//查询长途车队信息
				List<VehicleDrivingEntity> returnDto = vehicleDrivingService.queryVehicleDrivingByCondition(vehicleDrivingVo.getVehicleDrivingQueryDto(), this.getStart(), this.getLimit());
				//设置返回值
				vehicleDrivingVo.setVehicleDrivingEntityList(returnDto);
				this.setTotalCount(total);
			}
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 新增长途车队信息
	 * @return
	 */
	@JSON
	public String addVehicleDriving(){
		LOGGER.info("新增长途车队开始");
		try{
			
			//验证参数
			if(vehicleDrivingVo.getVehicleDrivingEntity()==null){
				throw new BusinessException("新增长途车队信息失败,参数异常");
			}
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//新增
			vehicleDrivingService.addVehicleDriving(vehicleDrivingVo.getVehicleDrivingEntity(), currentInfo);
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	
	/**
	 * 作废长途车队信息
	 * @return
	 */
	@JSON
	public String deleteVehicleDriving(){
		
		LOGGER.info("作废开始");
		try{
			
			//验证参数
			if(vehicleDrivingVo.getVehicleDrivingQueryDto()==null){
				throw new BusinessException("删除长途车队信息失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//作废
			vehicleDrivingService.deleteVehicleDriving(vehicleDrivingVo.getVehicleDrivingQueryDto(), currentInfo);
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}



	
	public void setVehicleDrivingService(
			IVehicleDrivingService vehicleDrivingService) {
		this.vehicleDrivingService = vehicleDrivingService;
	}

	public VehicleDrivingVo getVehicleDrivingVo() {
		return vehicleDrivingVo;
	}

	public void setVehicleDrivingVo(VehicleDrivingVo vehicleDrivingVo) {
		this.vehicleDrivingVo = vehicleDrivingVo;
	}



}
