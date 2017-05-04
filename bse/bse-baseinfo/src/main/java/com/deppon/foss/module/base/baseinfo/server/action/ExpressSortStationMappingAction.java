package com.deppon.foss.module.base.baseinfo.server.action;



import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressSortStationMappingService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSortStationMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressSortStationMappingVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
/**
 * 快递分拣目的站映射action
 * @author 130566
 *
 */
public class ExpressSortStationMappingAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1662107794518001192L;
	//前后台交互vo
	private ExpressSortStationMappingVo mappingVo;
	//快递分拣目的站映射service
	private IExpressSortStationMappingService expressSortStationMappingService;
	
	
	public ExpressSortStationMappingVo getMappingVo() {
		return mappingVo;
	}
	public void setMappingVo(ExpressSortStationMappingVo mappingVo) {
		this.mappingVo = mappingVo;
	}
	public void setExpressSortStationMappingService(
			IExpressSortStationMappingService expressSortStationMappingService) {
		this.expressSortStationMappingService = expressSortStationMappingService;
	}
	/**
	 * 分页查询
	 * @return
	 */
	@JSON
	public String queryMoreMappingList(){
		//按条件查询
		mappingVo.setMappingEntityList(expressSortStationMappingService.queryExpressSortStationMappingEntities(mappingVo.getEntity(), start, limit));
		//总数
		this.setTotalCount(expressSortStationMappingService.queryMappingCount(mappingVo.getEntity()));
		return returnSuccess();
	}
	/**
	 * 新增映射信息
	 * @return
	 */
	@JSON
	public String addExpressSortStationMapping(){
		try {
			ExpressSortStationMappingEntity entity =mappingVo.getEntity();
			//设置修改人
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			entity.setCreateUser(currentInfo.getEmpCode());
			expressSortStationMappingService.addExpSortStationMappingEntity(entity);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage(),e);
		}
	}
	/**
	 * 修改映射信息
	 * @return
	 */
	@JSON
	public String updateExpressSortStationMapping(){
		try {
			ExpressSortStationMappingEntity entity =mappingVo.getEntity();
			//设置修改人
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			entity.setModifyUser(currentInfo.getEmpCode());
			expressSortStationMappingService.updateExpSortStationMappingEntity(entity);
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage(),e);
		}
	}
	/**
	 * 作废操作
	 * @return
	 */
	@JSON
	public String delectMappingByvirualCodeList(){
		try {
			List<String> virtualCodeList =mappingVo.getVirtualCodeList();
			//设置修改人
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			List<ExpressSortStationMappingEntity> entityList = new ArrayList<ExpressSortStationMappingEntity>();
			for (String virtualCode : virtualCodeList) {
				ExpressSortStationMappingEntity entity =new ExpressSortStationMappingEntity();
				entity.setVirtualCode(virtualCode);
				entity.setModifyUser(currentInfo.getEmpCode());
				entityList.add(entity);
			}
			expressSortStationMappingService.deleteMoreMappingByvirtualCode(entityList);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage(),e);
		}
	}
}
