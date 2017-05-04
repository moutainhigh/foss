package com.deppon.foss.module.base.baseinfo.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExhibitionAreaPlanService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionAreaPlanEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExhibitionAreaPlanException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExhibitionAreaPlanVo;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 展馆区域规划Action
 * @author 187862
 * @date 2015-7-7 下午5:19:51
 *
 */
public class ExhibitionAreaPlanAction extends AbstractAction {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 3230920700191765098L;
	/**
	 * 加载日志
	 */
	private static final Logger LOGGER= LoggerFactory.getLogger(ExhibitionAreaPlanAction.class);
	/**
	 * 展馆区域规划Service
	 */
	private IExhibitionAreaPlanService exhibitionAreaPlanService;
	/**
	 * 展馆区域规划Vo
	 */
	private ExhibitionAreaPlanVo objectVo;
	/**
	 * 配置参数的Service
	 */
	private IConfigurationParamsService configurationParamsService;
	
	public ExhibitionAreaPlanVo getObjectVo() {
		return objectVo;
	}

	public void setObjectVo(ExhibitionAreaPlanVo objectVo) {
		this.objectVo = objectVo;
	}

	public void setExhibitionAreaPlanService(
			IExhibitionAreaPlanService exhibitionAreaPlanService) {
		this.exhibitionAreaPlanService = exhibitionAreaPlanService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * 根据条件查询展馆区域规划信息
	 * @author 187862-dujunhui
	 * @date 2015-7-8 上午10:28:14
	 */
	@JSON
	public String queryExhibitionAreaPlanByCondition(){
		try{
			if(objectVo==null){
				throw new ExhibitionAreaPlanException("前台获取Vo为空！");
			}else if(objectVo.getExhibitionAreaPlanEntity()==null){
				throw new ExhibitionAreaPlanException("前台获取查询实体为空！");
			}
			//获取查询实体
			ExhibitionAreaPlanEntity queryEntity=objectVo.
					getExhibitionAreaPlanEntity();
			//将查询实体列表封装进Vo
			objectVo.setAreaPlanEntityList(exhibitionAreaPlanService.
					queryExhibitionAreaPlanByCondition(queryEntity, limit, start));
			//设置查询条数
			this.setTotalCount(exhibitionAreaPlanService.queryRecordCount(queryEntity));
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 新增展馆区域规划信息
	 * @author 187862-dujunhui
	 * @date 2015-7-9 上午10:08:46
	 */
	@JSON
	public String addExhibitionAreaPlan(){
		try{
			ExhibitionAreaPlanEntity addEntity=objectVo.getExhibitionAreaPlanEntity();
			exhibitionAreaPlanService.addExhibitionAreaPlan(addEntity);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 自动生成展馆编码
	 * @author 187862-dujunhui
	 * @date 2015-7-9 上午10:34:12
	 */
	@JSON
	public String createCodeByManagement(){
		try{
			ExhibitionAreaPlanEntity myEntity=objectVo.getExhibitionAreaPlanEntity();
			String exhibitionCode= exhibitionAreaPlanService.createCodeByManagement(myEntity);
			myEntity.setExhibitionCode(exhibitionCode);
			objectVo.setExhibitionAreaPlanEntity(myEntity);
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 判断展馆名称唯一性
	 * @author 187862-dujunhui
	 * @date 2015-7-10 上午9:03:14
	 */
	@JSON
	public String queryAreaPlanByName(){
		try{
			ExhibitionAreaPlanEntity myEntity=objectVo.getExhibitionAreaPlanEntity();
			ExhibitionAreaPlanEntity entity=exhibitionAreaPlanService.
					queryExhibitionAreaPlanByName(myEntity.getExhibitionName(),myEntity.getVirtualCode());
			return entity==null? returnSuccess():returnError("该展馆名称已存在，不允许重复！");
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 修改展馆区域规划信息
	 * @author 187862-dujunhui
	 * @date 2015-7-10 下午2:05:26
	 */
	@JSON
	public String updateExhibitionAreaPlan(){
		try{
			ExhibitionAreaPlanEntity updateEntity=objectVo.getExhibitionAreaPlanEntity();
			int i=exhibitionAreaPlanService.updateExhibitionAreaPlanByCode(updateEntity);
			if(i>0){
				return returnSuccess(MessageType.UPDATE_SUCCESS);
			}else{
				return returnError("更新失败！");
			}
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 作废展馆区域规划信息
	 * @author 187862-dujunhui
	 * @date 2015-7-10 下午4:03:16
	 */
	@JSON
	public String deleteExhibitionAreaPlan(){
		try{
			String[] codes=objectVo.getCodes();
			String modifyUser=FossUserContext.getCurrentUser().getEmployee().getEmpCode();
			exhibitionAreaPlanService.deleteExhibitionAreaPlanByCode(codes, modifyUser);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 展馆区域规划特殊角色配置参数中的值（系统默认的配置参数）
	 * @author 187862-dujunhui
	 * @date 2015-7-13 下午2:48:21
	 */
	@JSON
	public String querySpecialActorParam(){
		try{
			//展馆区域规划特殊角色配置参数
			String code ="EHXIBITION_AREAPLAN_SPECIAL_ACTOR";
			ConfigurationParamsEntity entity =configurationParamsService.queryConfigurationParamsOneByCode(code);
			if(entity !=null && StringUtil.isNotEmpty(entity.getConfValue())){
				//设置默认参数配置值
				objectVo.setConfValue(entity.getConfValue());
			}else{
				return returnError("展馆区域规划特殊角色配置参数为空！请联系管理员");
			}
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}	
	
}
