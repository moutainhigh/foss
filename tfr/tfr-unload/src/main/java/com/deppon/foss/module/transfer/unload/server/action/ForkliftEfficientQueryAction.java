package com.deppon.foss.module.transfer.unload.server.action;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.unload.api.server.service.IForkliftEfficientQueryService;
import com.deppon.foss.module.transfer.unload.api.shared.vo.ForkliftEfficientVo;

/**
 *待叉区货物查询
 *页面跳转action
 *@author zenghaibin
 *@date 2014-07-01  
 **/
public class ForkliftEfficientQueryAction extends AbstractAction{
	/**序列化**/
	private static final long serialVersionUID = 4087294908525422062L;
	/**日志对象**/
//	private static final Logger LOGGER = LoggerFactory.getLogger(ForkliftEfficientQueryAction.class);
	private IForkliftEfficientQueryService  forkliftEfficientQueryService;
	private ForkliftEfficientVo forkliftEfficientVo = new ForkliftEfficientVo();
	
	/**
	 *获取部门编号和标识
	 *@author zenghain
	 *@date 2014-7-8
	 *@return String
	 **/
	@JSON
	public String queryForkliftEfficientindex(){
//		String departmentSignle = forkliftEfficientQueryService.queryOrgCode();//获取部门标识
		return returnSuccess();
	}

	/**
	 *查询电叉数量信息
	 *@author zenghain
	 *@date 2014-7-8 
	 *@return String
	 **/
	@JSON
	public String queryForkliftData(){
		forkliftEfficientVo.setForkliftEfficientEntityList(forkliftEfficientQueryService.queryForkliftData(forkliftEfficientVo,this.getLimit(),this.getStart()));
		Long totalCount= forkliftEfficientQueryService.queryForkliftDataCount(forkliftEfficientVo);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
	/**
	 *新增电叉数配置
	 *@author zenghain
	 *@date 2014-7-8
	 *@return String
	 **/
	@JSON
	public String addForkliftData(){
		try {
			forkliftEfficientQueryService.addForkliftData(forkliftEfficientVo);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}catch (Exception e) {
			return returnError(e.toString());
		}
		return returnSuccess();
	}
	
	/**
	 *修改电叉数配置
	 *@author zenghain
	 *@date 2014-7-8
	 *@return 
	 **/
	@JSON
	public String updateForkliftData(){
		try {
			forkliftEfficientQueryService.updateForkliftData(forkliftEfficientVo);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}catch (Exception e) {
			return returnError(e.toString());
		}
		return returnSuccess();
	}
	
	
	/**
	 *电叉司机数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return 
	 **/
	@JSON
	public String queryForkliftDriverData(){
		forkliftEfficientVo.setForkliftDriverEntityList(forkliftEfficientQueryService.queryForkliftDriverData(forkliftEfficientVo,this.getLimit(),this.getStart()));
		Long totalCount= forkliftEfficientQueryService.queryForkliftDriverDataCount(forkliftEfficientVo);
		this.setTotalCount(totalCount);
		return returnSuccess();
	} 
	
	/**
	 *转运场数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return 
	 **/
	@JSON
	public String queryTransferFieldData(){
		forkliftEfficientVo.setTransferFieldEntityList(forkliftEfficientQueryService.queryTransferFieldData(forkliftEfficientVo,this.getLimit(),this.getStart()));
		Long totalCount= forkliftEfficientQueryService.queryTransferFieldDataCount(forkliftEfficientVo);
		this.setTotalCount(totalCount);
		return returnSuccess();
	} 
	public void setForkliftEfficientQueryService(
			IForkliftEfficientQueryService forkliftEfficientQueryService) {
		this.forkliftEfficientQueryService = forkliftEfficientQueryService;
	}

	public ForkliftEfficientVo getForkliftEfficientVo() {
		return forkliftEfficientVo;
	}

	public void setForkliftEfficientVo(ForkliftEfficientVo forkliftEfficientVo) {
		this.forkliftEfficientVo = forkliftEfficientVo;
	}
	
	
	
}
