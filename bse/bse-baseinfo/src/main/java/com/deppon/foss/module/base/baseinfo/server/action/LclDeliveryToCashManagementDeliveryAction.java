package com.deppon.foss.module.base.baseinfo.server.action;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LclDeliveryToCashManagementDeliveryVo;

/**
 * 
 * 派送兑现时效管理 Action
 * @author 273311
 * @date 2016-8-23 下午3:23:54
 */
public class LclDeliveryToCashManagementDeliveryAction extends AbstractAction {
	/**
	 * <p>
	 * 派送兑现时效管理     新增
	 * </p>
	 * @author 273311
	 * @date 2016-8-23 下午3:25:20
	 * @return
	 * @see
	 */
	@JSON
	public String addlclDeliveryToCashManagementDelivery() {
		// 校验前台传入的VO是否为空
		if (lclDeliveryToCashManagementDeliveryVo == null
				|| lclDeliveryToCashManagementDeliveryVo
						.getLclDeliveryToCashManagementDeliveryEntity() == null
				|| StringUtils.isBlank(lclDeliveryToCashManagementDeliveryVo
						.getLclDeliveryToCashManagementDeliveryEntity()
						.getOrgCode())) {
			// 校验不通过怎返回错误信息
			return returnError("派送部门不能为空");
		}
		try {
			// 获取VO中的实体对象
			LclDeliveryToCashManagementDeliveryEntity entityView = lclDeliveryToCashManagementDeliveryVo
					.getLclDeliveryToCashManagementDeliveryEntity();
		
				// 执行新增
				LclDeliveryToCashManagementDeliveryEntity entityCondition = lclDeliveryToCashManagementDeliveryService
						.addLclDeliveryToCashManagementDelivery(entityView);
				// 保存失败，则返回错误信息
				if (entityCondition == null) {
					return returnError("保存失败");
				}
			// 发生异常，则返回异常信息
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		} catch (Exception e2) {
			return returnError("新增失败" + e2.getMessage(), e2);
		}
		 return returnSuccess();
	}
     /**
      * 
      * <p>派送兑现时效管理   删除</p> 
      * @author 273311 
      * @date 2016-8-23 下午3:30:58
      * @return
      * @see
      */
	@JSON
	public String deletelclDeliveryToCashManagementDelivery() {
		// 检验前台传入的VO是否为空
		if (lclDeliveryToCashManagementDeliveryVo == null
				|| lclDeliveryToCashManagementDeliveryVo
						.getLclDeliveryToCashManagementDeliveryEntity() == null
				|| StringUtils.isBlank(lclDeliveryToCashManagementDeliveryVo
						.getLclDeliveryToCashManagementDeliveryEntity()
						.getOrgCode())) {
			// 校验不通过怎返回错误信息
			return returnError("派送部门不能为空");
		}
		try {
			// 获取VO中的实体对象
			LclDeliveryToCashManagementDeliveryEntity entityView = lclDeliveryToCashManagementDeliveryVo
					.getLclDeliveryToCashManagementDeliveryEntity();
			// 执行删除
			LclDeliveryToCashManagementDeliveryEntity deleteEntity = lclDeliveryToCashManagementDeliveryService
					.deletelclDeliveryToCashManagementDelivery(entityView);
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
    * <p>派送兑现时效管理   批量删除</p> 
    * @author 273311 
    * @date 2016-8-23 下午3:31:45
    * @return
    * @see
    */
	@JSON
	public String deletelclDeliveryToCashManagementDeliveryMore() {
		// 检验前台传入的VO是否为空
		if (lclDeliveryToCashManagementDeliveryVo == null
				|| lclDeliveryToCashManagementDeliveryVo
						.getLclDeliveryToCashManagementDeliveryEntity() == null
				|| StringUtils
						.isBlank(lclDeliveryToCashManagementDeliveryVo
								.getLclDeliveryToCashManagementDeliveryEntity()
								.getId())) {
			// 校验不通过怎返回错误信息
			return returnError("派送部门不能为空");
		}
		String[] ids = lclDeliveryToCashManagementDeliveryVo
				.getLclDeliveryToCashManagementDeliveryEntity().getId()
				.split(SymbolConstants.EN_COMMA);
		try {
			// 执行批量删除
			LclDeliveryToCashManagementDeliveryEntity deleteEntity = lclDeliveryToCashManagementDeliveryService
					.deletelclDeliveryToCashManagementDeliveryMore(ids);
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
     * <p>派送兑现时效管理   更新</p> 
     * @author 273311 
     * @date 2016-8-23 下午3:32:56
     * @return
     * @see
     */
	@JSON
	public String updatelclDeliveryToCashManagementDelivery() {
		// 检验前台传入的VO是否为空
		if (lclDeliveryToCashManagementDeliveryVo == null
				|| lclDeliveryToCashManagementDeliveryVo
						.getLclDeliveryToCashManagementDeliveryEntity() == null
				|| StringUtils.isBlank(lclDeliveryToCashManagementDeliveryVo
						.getLclDeliveryToCashManagementDeliveryEntity()
						.getOrgCode())) {
			// 校验不通过怎返回错误信息
			return returnError("派送部门不能为空");
		}
		try {
			// 获取VO中的实体对象
			LclDeliveryToCashManagementDeliveryEntity entityView = lclDeliveryToCashManagementDeliveryVo
					.getLclDeliveryToCashManagementDeliveryEntity();
				// 更新
				LclDeliveryToCashManagementDeliveryEntity updateEntity = lclDeliveryToCashManagementDeliveryService
						.updatelclDeliveryToCashManagementDelivery(entityView);
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
     * <p>派送兑现时效管理     查询</p> 
     * @author 273311 
     * @date 2016-8-23 下午3:33:19
     * @return
     * @see
     */
	@JSON
	public String querylclDeliveryToCashManagementDeliveryExactByEntity() {
		// 校验前台是否为空
		if (lclDeliveryToCashManagementDeliveryVo == null) {
			lclDeliveryToCashManagementDeliveryVo = new LclDeliveryToCashManagementDeliveryVo();
		}
		// 前台查询条件
		LclDeliveryToCashManagementDeliveryEntity entityCondition = lclDeliveryToCashManagementDeliveryVo
				.getLclDeliveryToCashManagementDeliveryEntity();
		if (limit == 0) {
			limit = Integer.MAX_VALUE;
		}
		// 返回的结果显示在表格中：
		lclDeliveryToCashManagementDeliveryVo
				.setLclDeliveryToCashManagementDeliveryList(lclDeliveryToCashManagementDeliveryService
						.queryLclDeliveryToCashManagementDeliveryExactByEntity(
								entityCondition, start, limit));
		// 查询合计信息
		totalCount = lclDeliveryToCashManagementDeliveryService
				.queryLclDeliveryToCashManagementDeliveryExactByEntityCount(entityCondition);
		return returnSuccess();
	}

	// 一些变量声明
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 2370033628169879237L;
	/**
	 * get set  方法
	 */
	private LclDeliveryToCashManagementDeliveryVo lclDeliveryToCashManagementDeliveryVo;

	private IlclDeliveryToCashManagementDeliveryService lclDeliveryToCashManagementDeliveryService;

	public LclDeliveryToCashManagementDeliveryVo getLclDeliveryToCashManagementDeliveryVo() {
		return lclDeliveryToCashManagementDeliveryVo;
	}

	public void setLclDeliveryToCashManagementDeliveryVo(
			LclDeliveryToCashManagementDeliveryVo lclDeliveryToCashManagementDeliveryVo) {
		this.lclDeliveryToCashManagementDeliveryVo = lclDeliveryToCashManagementDeliveryVo;
	}

	public void setLclDeliveryToCashManagementDeliveryService(
			IlclDeliveryToCashManagementDeliveryService lclDeliveryToCashManagementDeliveryService) {
		this.lclDeliveryToCashManagementDeliveryService = lclDeliveryToCashManagementDeliveryService;
	}
}
