package com.deppon.foss.module.base.baseinfo.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICenterAndDeliveryAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CenterAndDeliveryAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliveryAreaDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CenterAndDeliveryAreaVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class CenterAndDeliveryAreaAction  extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5134221161855814206L;
	/**
	 * 
	 */
	private ICenterAndDeliveryAreaService centerAndDeliveryAreaService;
	/**
	 * 
	 * @param centerAndDeliveryAreaService
	 */
	public void setCenterAndDeliveryAreaService(
			ICenterAndDeliveryAreaService centerAndDeliveryAreaService) {
		this.centerAndDeliveryAreaService = centerAndDeliveryAreaService;
	}
	/**
	 * 
	 */
	private CenterAndDeliveryAreaVo centerAndDeliveryAreaVo=new CenterAndDeliveryAreaVo();
	
	
	/**
	 * 
	 * @return
	 */
	public CenterAndDeliveryAreaVo getCenterAndDeliveryAreaVo() {
		return centerAndDeliveryAreaVo;
	}


    /**
     * 
     * @param centerAndDeliveryAreaVo
     */
	public void setCenterAndDeliveryAreaVo(
			CenterAndDeliveryAreaVo centerAndDeliveryAreaVo) {
		this.centerAndDeliveryAreaVo = centerAndDeliveryAreaVo;
	}

	 
    /**
     * 根据用户传入的查询条件查询地图显示信息
     * @return
     */
	@JSON
	public String queryVehicleInfo(){
		try{
			boolean flag=centerAndDeliveryAreaService.queryRoles(FossUserContext.getCurrentInfo().getEmpCode());	
			CenterAndDeliveryAreaDto dto=centerAndDeliveryAreaVo.getCenterAndDeliveryAreaDto();
			long l=centerAndDeliveryAreaService.queryDataPermissions(dto.getManageMent(), FossUserContext.getCurrentInfo().getEmpCode());
	         
			CenterAndDeliveryAreaEntity entity=centerAndDeliveryAreaService.queryVehicleInfo(dto);
	        if(flag==true&&l>0){
	            entity.setDrawMap("Y");
	        }else{
	        	entity.setDrawMap("N");
	        }
	         
			centerAndDeliveryAreaVo.setCenterAndDeliveryAreaEntity(entity);
		}catch (BusinessException e) {
			 return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	 * 新增地图画图后的信息
	 * @return
	 */
	@JSON
	public String addVehicleInfo(){
		
		CenterAndDeliveryAreaEntity entity=centerAndDeliveryAreaVo.getCenterAndDeliveryAreaEntity();
		try{
		boolean flag=centerAndDeliveryAreaService.addVehicleInfo(entity);
			if(flag){
				return returnSuccess("保存成功！");
			}else{
				centerAndDeliveryAreaVo.setStatus("add");
				return returnError("保存失败");
			}
		}catch(BusinessException e){
			return returnError(e.getMessage());
		}
	}
	/**
	 * 更新地图信息
	 * @return
	 */
	@JSON
	public String updateVehicleInfo(){
		CenterAndDeliveryAreaEntity entity=centerAndDeliveryAreaVo.getCenterAndDeliveryAreaEntity();
		try{
			centerAndDeliveryAreaService.updateVehicleInfo(entity);
		}catch(BusinessException e){
			LOG.info(e.getMessage());
		}	
		return returnSuccess("更新成功！");
	}
	/**
	 * 删除地图信息
	 * @return
	 */
	@JSON
	public String deleteVehicleInfo(){
		CenterAndDeliveryAreaEntity entity=centerAndDeliveryAreaVo.getCenterAndDeliveryAreaEntity();
		try{
		boolean flag=centerAndDeliveryAreaService.deleteVehicleInfo(entity);
			if(flag){
				return returnSuccess("删除成功！");
			}else{
				centerAndDeliveryAreaVo.setStatus("delete");
				return returnError("删除失败");
			}
		}catch(BusinessException e){
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 角色权限
	 * @return
	 */
	@JSON
	public String queryRolePermissions(){
		
	//车队最高负责人 TFR_TRUCK_HIGHEST_RESPONSE_PERSON
		try{
		CenterAndDeliveryAreaEntity entity=centerAndDeliveryAreaService.queryRolePermissions();
		centerAndDeliveryAreaVo.setCenterAndDeliveryAreaEntity(entity);
		}catch (BusinessException e) {
//		CenterAndDeliveryAreaEntity entity=new CenterAndDeliveryAreaEntity();	
//		centerAndDeliveryAreaVo.setCenterAndDeliveryAreaEntity(entity);
			 LOG.info(e.getMessage());
		}
		return returnSuccess();
	}

}
