package com.deppon.foss.module.base.baseinfo.server.action;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LclDeliveryToCashManagementUnloadingVo;

public class LclDeliveryToCashManagementUnloadingAction extends AbstractAction {
    /**
     * 
     * <p>规定卸出时间管理  新增</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:08:29
     * @return
     * @see
     */
	@JSON
	public String addlclDeliveryToCashManagementUnloading() {
		if (lclDeliveryToCashManagementUnloadingVo == null
				|| lclDeliveryToCashManagementUnloadingVo
						.getLclDeliveryToCashManagementUnloadingEntity() == null
				|| lclDeliveryToCashManagementUnloadingVo
						.getLclDeliveryToCashManagementUnloadingEntity()
						.getStartOrgCode() == null
				|| lclDeliveryToCashManagementUnloadingVo
						.getLclDeliveryToCashManagementUnloadingEntity()
						.getReachOrgCode() == null) {
			// 校验不通过怎返回错误信息
			return returnError("派送部门出发部门不能为空");
		}
		try {
			// 获取VO中的实体对象
			LclDeliveryToCashManagementUnloadingEntity entityView = lclDeliveryToCashManagementUnloadingVo
					.getLclDeliveryToCashManagementUnloadingEntity();
				// 执行新增
				LclDeliveryToCashManagementUnloadingEntity entityCondition = lclDeliveryToCashManagementUnloadingService
						.addlclDeliveryToCashManagementUnloading(entityView);
				if (entityCondition == null) {
					return returnError("新增失败");
				}
				
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		} catch (Exception e2) {
			return returnError("新增失败" + e2.getMessage(), e2);
		}
		 return returnSuccess();

	}
     /**
      * 
      * <p>规定卸出时间管理  更新</p> 
      * @author 273311 
      * @date 2016-8-23 下午4:09:03
      * @return
      * @see
      */
	@JSON
	public String updatelclDeliveryToCashManagementUnloading() {
		if (lclDeliveryToCashManagementUnloadingVo == null
				|| lclDeliveryToCashManagementUnloadingVo
						.getLclDeliveryToCashManagementUnloadingEntity() == null
				|| lclDeliveryToCashManagementUnloadingVo
						.getLclDeliveryToCashManagementUnloadingEntity()
						.getStartOrgCode() == null
				|| lclDeliveryToCashManagementUnloadingVo
						.getLclDeliveryToCashManagementUnloadingEntity()
						.getReachOrgCode() == null) {
			// 校验不通过怎返回错误信息
			return returnError("派送部门出发部门不能为空");
		}
		try {
			// 获取VO中的实体对象
			LclDeliveryToCashManagementUnloadingEntity entityView = lclDeliveryToCashManagementUnloadingVo
					.getLclDeliveryToCashManagementUnloadingEntity();
				LclDeliveryToCashManagementUnloadingEntity updateEntity = lclDeliveryToCashManagementUnloadingService
						.updatelclDeliveryToCashManagementUnloading(entityView);
				if (updateEntity == null) {
					return returnError("更新失败");
				}	
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		 return returnSuccess();

	}
   /**
    * 
    * <p>规定卸出时间管理    删除</p> 
    * @author 273311 
    * @date 2016-8-23 下午4:09:32
    * @return
    * @see
    */
	@JSON
	public String deletelclDeliveryToCashManagementUnloading() {
		if (lclDeliveryToCashManagementUnloadingVo == null
				|| lclDeliveryToCashManagementUnloadingVo
						.getLclDeliveryToCashManagementUnloadingEntity() == null) {
			// 校验不通过怎返回错误信息
			return returnError("派送部门出发部门不能为空");
		}
		try {
			// 获取VO中的实体对象
			LclDeliveryToCashManagementUnloadingEntity entityView = lclDeliveryToCashManagementUnloadingVo
					.getLclDeliveryToCashManagementUnloadingEntity();
			// 执行删除
			LclDeliveryToCashManagementUnloadingEntity deleteEntity = lclDeliveryToCashManagementUnloadingService
					.deletelclDeliveryToCashManagementUnloading(entityView);
			if (deleteEntity == null) {
				return returnError("删除失败");
			}
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
    /**
     * 
     * <p>规定卸出时间管理   批量删除</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:09:50
     * @return
     * @see
     */
	@JSON
	public String deletelclDeliveryToCashManagementUnloadingMore() {
		if (lclDeliveryToCashManagementUnloadingVo == null
				|| lclDeliveryToCashManagementUnloadingVo
						.getLclDeliveryToCashManagementUnloadingEntity() == null
				|| StringUtils.isBlank(lclDeliveryToCashManagementUnloadingVo
						.getLclDeliveryToCashManagementUnloadingEntity()
						.getId())) {
			// 校验不通过怎返回错误信息
			return returnError("部门不能为空");
		}
		String[] ids = lclDeliveryToCashManagementUnloadingVo
				.getLclDeliveryToCashManagementUnloadingEntity().getId()
				.split(SymbolConstants.EN_COMMA);
		try {
			// 执行批量删除
			LclDeliveryToCashManagementUnloadingEntity deleteEntity = lclDeliveryToCashManagementUnloadingService
					.deletelclDeliveryToCashManagementUnloadingMore(ids);
			if (deleteEntity == null) {
				return returnError("删除失败");
			}
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();

	}
    /**
     * 
     * <p>规定卸出时间管理 查询   分页</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:10:09
     * @return
     * @see
     */
	@JSON
	public String querylclDeliveryToCashManagementUnloadingExactByEntity() {
		if (lclDeliveryToCashManagementUnloadingVo == null) {
			lclDeliveryToCashManagementUnloadingVo = new LclDeliveryToCashManagementUnloadingVo();
		}
		// 前台查询条件
		LclDeliveryToCashManagementUnloadingEntity entityCondition = lclDeliveryToCashManagementUnloadingVo
				.getLclDeliveryToCashManagementUnloadingEntity();
		if (limit == 0) {
			limit = Integer.MAX_VALUE;
		}
		// 返回的结果显示在表格中
		lclDeliveryToCashManagementUnloadingVo
				.setLclDeliveryToCashManagementUnloadingList(lclDeliveryToCashManagementUnloadingService
						.queryLclDeliveryToCashManagementUnloadingEntityExactByEntity(
								entityCondition, start, limit));
		// 查询合计信息
		totalCount = lclDeliveryToCashManagementUnloadingService
				.queryLclDeliveryToCashManagementUnloadingExactByEntityCount(entityCondition);
		return returnSuccess();
	}
   //一些声明
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -6124572804733423606L;
	/**
	 *  get   set 方法
	 */
	private LclDeliveryToCashManagementUnloadingVo lclDeliveryToCashManagementUnloadingVo;
	private ILclDeliveryToCashManagementUnloadingService lclDeliveryToCashManagementUnloadingService;
	
	public LclDeliveryToCashManagementUnloadingVo getLclDeliveryToCashManagementUnloadingVo() {
		return lclDeliveryToCashManagementUnloadingVo;
	}

	public void setLclDeliveryToCashManagementUnloadingVo(
			LclDeliveryToCashManagementUnloadingVo lclDeliveryToCashManagementUnloadingVo) {
		this.lclDeliveryToCashManagementUnloadingVo = lclDeliveryToCashManagementUnloadingVo;
	}

	public void setLclDeliveryToCashManagementUnloadingService(
			ILclDeliveryToCashManagementUnloadingService lclDeliveryToCashManagementUnloadingService) {
		this.lclDeliveryToCashManagementUnloadingService = lclDeliveryToCashManagementUnloadingService;
	}
}
