package com.deppon.foss.module.transfer.unload.server.action;

import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.unload.api.server.service.ISubForkAreaGoodsQueryService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SubForkAreaGoodsExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.SubForkAreaGoodsVo;

/**
 *待叉区货物查询
 *页面跳转action
 *@author zenghaibin
 *@date 2014-07-01  
 **/
public class SubForkAreaGoodsQueryAction extends AbstractAction{
	/**序列化**/
	private static final long serialVersionUID = 4087294908525422062L;
	/**日志对象**/
	/*private static final Logger LOGGER = LoggerFactory
			.getLogger(SubForkAreaGoodsQueryAction.class);*/
	/**页面和后台之间传递参数的vo**/
	private SubForkAreaGoodsVo subForkAreaGoodsVo = new SubForkAreaGoodsVo();
	/**处理逻辑的service对象**/
 	private ISubForkAreaGoodsQueryService subForkAreaGoodsQueryService;
 	
 	private IConfigurationParamsService configurationParamsService;
 	/**
 	 * 处理待叉区查询请求action 零担
 	 * @author zenghaibin
 	 ***/
 	@JSON
	public String querySubForkAreaGoods(){

 		subForkAreaGoodsVo.setSubForkAreaGoodsEntityList(subForkAreaGoodsQueryService.querySubForkAreaGoods(subForkAreaGoodsVo,this.limit,this.start));
		Long totalCount = subForkAreaGoodsQueryService.querySubForkAreaGoodsCount(subForkAreaGoodsVo);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
 	
 	/**
 	 * 处理待叉区查询请求action 快递
 	 * @author zenghaibin
 	 ***/
 	@JSON
	public String querySubForkAreaGoodsExpress(){
 		
 		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOG.error("理待叉区查询请求开关" + tfrSwitch4Ecs);
		if (!tfrSwitch4Ecs) {
			return this.returnSuccess();
		}
 		//分页转换
 		int page=(this.getStart()/this.getLimit())+1;
 		int size=this.getLimit();
 		//获取当前用户的部门
 		subForkAreaGoodsVo.setOptionOrgCode(FossUserContext.getCurrentDeptCode());
 		System.out.println("带进来的参数="+subForkAreaGoodsVo.toString());
 		List<SubForkAreaGoodsExpressEntity> resultList=subForkAreaGoodsQueryService.querySubForkAreaGoodsExpress(subForkAreaGoodsVo,size,page);
 		subForkAreaGoodsVo.setSubForkAreaGoodsExpressEntityList(resultList);
		Long totalCount = 0l;
		if(resultList.size() != 0){
			totalCount = resultList.get(0).getCount();
		}
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
	/**
 	 *获取运输性质下拉框值
 	 *@author zenghaibin
 	 ***/
	public String queryProductList(){
		subForkAreaGoodsVo.setProductList(subForkAreaGoodsQueryService.queryProductList());
		return returnSuccess();
	}
	
	public void setSubForkAreaGoodsQueryService(
			ISubForkAreaGoodsQueryService subForkAreaGoodsQueryService) {
		this.subForkAreaGoodsQueryService = subForkAreaGoodsQueryService;
	}
	public SubForkAreaGoodsVo getSubForkAreaGoodsVo() {
		return subForkAreaGoodsVo;
	}
	public void setSubForkAreaGoodsVo(SubForkAreaGoodsVo subForkAreaGoodsVo) {
		this.subForkAreaGoodsVo = subForkAreaGoodsVo;
	}
	
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
}
