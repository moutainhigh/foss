package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressSalesAgentMapService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressSalesAgentMapException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressSalesAgentMapVo;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 *   虚拟营业部快递代理网点映射的Action层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-5-21 下午2:54:29,content:TODO </p>
 * @author 232607 
 * @date 2015-5-21 下午2:54:29
 * @since
 * @version
 */
public class ExpressSalesAgentMapAction extends AbstractAction {
	/**
	 *  序列化UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  日志
	 */
	private static final Logger LOGGER =LoggerFactory.getLogger(ExpressSalesAgentMapAction.class);
	/**
	 *  实现本模块的VO
	 */
	private ExpressSalesAgentMapVo expressSalesAgentMapVo;
	/**
	 *  实现本模块的service
	 */
	private IExpressSalesAgentMapService expressSalesAgentMapService;
	/**
	 * <p> VO的get、set方法用来前后台传输数据，service的set方法用来注入service的Bean</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午3:54:34
	 * @return
	 * @see
	 */
	public ExpressSalesAgentMapVo getExpressSalesAgentMapVo() {
		return expressSalesAgentMapVo;
	}
	public void setExpressSalesAgentMapVo(
			ExpressSalesAgentMapVo expressSalesAgentMapVo) {
		this.expressSalesAgentMapVo = expressSalesAgentMapVo;
	}
	public void setExpressSalesAgentMapService(
			IExpressSalesAgentMapService expressSalesAgentMapService) {
		this.expressSalesAgentMapService = expressSalesAgentMapService;
	}
	/**
	 * <p>通过所有条件进行分页查询</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午3:05:20
	 * @return
	 * @see
	 */
	@JSON
	public String queryExpressSalesAgentMapListByCondition() {
		try {
			//调用service层的方法，通过所有条件进行分页查询
			List<ExpressSalesAgentMapEntity> expressSalesAgentMapEntityList = expressSalesAgentMapService.queryExpressSalesAgentMapListByCondition(expressSalesAgentMapVo.getExpressSalesAgentMapEntity(), start, limit);
			//将结果存入VO
			expressSalesAgentMapVo.setExpressSalesAgentMapEntityList(expressSalesAgentMapEntityList);
			//调用service层的方法，查询总数
			long totalCount =expressSalesAgentMapService.queryExpressSalesAgentMapListByConditionCount(expressSalesAgentMapVo.getExpressSalesAgentMapEntity());
			//将总数存入TotalCount
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (ExpressSalesAgentMapException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p> 批量作废（非物理删除，是将数据的状态改为不可用）</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午4:15:03
	 * @return
	 * @see
	 */
	@JSON
	public String deleteExpressSalesAgentMap() {
		try {
			//调用service层的方法，传入ID集合，批量作废数据
			expressSalesAgentMapService.deleteExpressSalesAgentMap(expressSalesAgentMapVo.getIds());
			return returnSuccess(MessageType.DELETE_SUCCESS);//删除成功！
		} catch (ExpressSalesAgentMapException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p> 新增映射关系</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午4:46:25
	 * @return
	 * @see
	 */
	@JSON
	public String addExpressSalesAgentMap() {
		try {
			//调用service层的方法，传入实体信息，新增映射关系
			expressSalesAgentMapService.addExpressSalesAgentMap(expressSalesAgentMapVo.getExpressSalesAgentMapEntity());
			return returnSuccess(MessageType.SAVE_SUCCESS);//保存成功！
		} catch (ExpressSalesAgentMapException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p> 修改映射关系</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午4:47:32
	 * @return
	 * @see
	 */
	@JSON
	public String updateExpressSalesAgentMap() {
		try {
			//调用service层的方法，传入实体信息，修改映射关系
			expressSalesAgentMapService.updateExpressSalesAgentMap(expressSalesAgentMapVo.getExpressSalesAgentMapEntity());
			return returnSuccess(MessageType.UPDATE_SUCCESS);//更新成功！
		} catch (ExpressSalesAgentMapException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
}
