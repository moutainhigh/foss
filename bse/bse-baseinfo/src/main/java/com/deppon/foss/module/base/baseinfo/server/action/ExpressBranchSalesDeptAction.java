package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBranchSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBranchSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressBranchSalesDeptVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
/**
 * 快递分部与营业部关系Action
 * @author WeiXing
 *
 */
public class ExpressBranchSalesDeptAction extends AbstractAction {
	
	private static final long serialVersionUID = 9033163481315747560L;
	//日志
	private static final Logger LOGGER =LoggerFactory.getLogger(ExpressBranchSalesDeptAction.class);
	//与前台交互的vo
	private ExpressBranchSalesDeptVo vo;
	//快递分部营业部映射的Service
	private IExpressBranchSalesDeptService expressBranchSalesDeptService;

	public ExpressBranchSalesDeptVo getVo() {
		return vo;
	}

	public void setVo(ExpressBranchSalesDeptVo vo) {
		this.vo = vo;
	}

	public void setExpressBranchSalesDeptService(IExpressBranchSalesDeptService expressBranchSalesDeptService){
		this.expressBranchSalesDeptService = expressBranchSalesDeptService;
	}
	/**
	 * <P>
	 * 根据条件分页查询
	 * <P>
	 * @author :WeiXing
	 * @date : 2014-9-23上午11:23:49
	 * @return
	 */
	@JSON
	public String queryExpressBranchSalesList() {
		try {
			ExpressBranchSalesDeptEntity entity = vo
					.getExpressBranchSalesDeptEntity();
			vo.setExpressBranchSalesList(expressBranchSalesDeptService
					.queryExpressBranchSalesList(entity, start, limit));
			long totalCount = expressBranchSalesDeptService
					.queryExpressBranchSalesCount(entity);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<P>根据id作废<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午5:59:17
	 * @return
	 */
	@JSON
	public String deleteExpressBranchSalesById(){
		try {
			String id =vo.getExpressBranchSalesDeptEntity().getId();
			//获取当前用户
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			ExpressBranchSalesDeptEntity entity =new ExpressBranchSalesDeptEntity();
			entity.setModifyUser(currentInfo.getEmpCode());
			entity.setId(id);
			//作废
			expressBranchSalesDeptService.deleteExpressBranchSalesById(entity);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 *<P>批量作废<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午6:17:41
	 * @return
	 */
	@JSON
	public String deleteExpressBranchSalesByIdList(){
		try {
			//idlist集合
			List<String> idList =vo.getIdList();
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			//批量作废
			expressBranchSalesDeptService.deleteExpressBranchSalesByIdList(idList,currentInfo);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<P>根据快递分部查询<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午6:21:13
	 * @return
	 */
	@JSON
	public String queryExpressBranchSaleByExpressBranchSalesDeptCode(){
		try {
			//获取快递分部编码
			String expressBranchCode =vo.getExpressBranchSalesDeptEntity().getExpressBranchCode();
			String salesDeptCode=vo.getExpressBranchSalesDeptEntity().getSalesDeptCode();
			ExpressBranchSalesDeptEntity entity =expressBranchSalesDeptService.queryExpressBranchsalesByExpressBranchSalesDeptCode(expressBranchCode,salesDeptCode);
			vo.setExpressBranchSalesDeptEntity(entity);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<P>添加方法<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午7:06:12
	 * @return
	 */
	@JSON
	public String addExpressBranchSales(){
		try {
			//获取新增的实体
			List<ExpressBranchSalesDeptEntity> addDeptEntities =vo.getAddExpressBranchSalesList();
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			expressBranchSalesDeptService.addExpressBranchSalesList(addDeptEntities,currentInfo.getEmpCode());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	
}
