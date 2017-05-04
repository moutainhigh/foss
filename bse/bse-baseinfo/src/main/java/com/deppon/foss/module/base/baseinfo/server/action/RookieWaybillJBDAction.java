package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRookieWaybillJBDService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RookieWaybillJBDEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.RookieWaybillJBDException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.RookieWaybillJBDVo;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class RookieWaybillJBDAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5298461171723860765L;
	private static final Logger LOGGER =LoggerFactory.getLogger(RookieWaybillJBDAction.class);

	/**
	 *  实现本模块的VO
	 */
	private RookieWaybillJBDVo rookieWaybillJBDVo;
	/**
	 *  实现本模块的service
	 */
	private IRookieWaybillJBDService rookieWaybillJBDService;
	public RookieWaybillJBDVo getRookieWaybillJBDVo() {
		return rookieWaybillJBDVo;
	}
	public void setRookieWaybillJBDVo(RookieWaybillJBDVo rookieWaybillJBDVo) {
		this.rookieWaybillJBDVo = rookieWaybillJBDVo;
	}
	public void setRookieWaybillJBDService(
			IRookieWaybillJBDService rookieWaybillJBDService) {
		this.rookieWaybillJBDService = rookieWaybillJBDService;
	}
	
	/**
	 * <p> 分页查询</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
 public String queryRookieWaybillJBDByCondition() {
	 try {
			//调用service层的方法，通过所有条件进行分页查询
			List<RookieWaybillJBDEntity> entityList = rookieWaybillJBDService.queryRookieWaybillJBDByCondition(rookieWaybillJBDVo.getRookieWaybillJBDEntity(), start, limit);
			//将结果存入VO
			rookieWaybillJBDVo.setRookieWaybillJBDEntityList(entityList);
			//调用service层的方法，查询总数
			long totalCount =rookieWaybillJBDService.queryCount(rookieWaybillJBDVo.getRookieWaybillJBDEntity());
			//将总数存入TotalCount
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (RookieWaybillJBDException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
 }
 /**
	 * <p> 新增集包地接口信息</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	public String addRookieWaybillJBD(){
		try {
			//调用service层的方法，传入实体信息，新增映射关系
			rookieWaybillJBDService.addRookieWaybillJBD(rookieWaybillJBDVo.getRookieWaybillJBDEntity());
			return returnSuccess(MessageType.SAVE_SUCCESS);//保存成功！
		} catch (RookieWaybillJBDException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p> 作废集包地信息</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	public String deleteRookieWaybillJBD() {
		try {
			//调用service层的方法，传入ID集合，批量作废数据
			rookieWaybillJBDService.deleteRookieWaybillJBD(rookieWaybillJBDVo.getIds());
			return returnSuccess(MessageType.DELETE_SUCCESS);//删除成功！
		} catch (RookieWaybillJBDException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p> 跟新集包地接口信息</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	public String updateRookieWaybillJBD(){
		try {
			//调用service层的方法，传入实体信息，修改映射关系
			rookieWaybillJBDService.updateRookieWaybillJBD(rookieWaybillJBDVo.getRookieWaybillJBDEntity());
			return returnSuccess(MessageType.UPDATE_SUCCESS);//更新成功！
		} catch (RookieWaybillJBDException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
}
