package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressAutoComplementManageService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoComplementManageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressAutoComplementManageVo;

/**
 * 
 * @author 218392
 * @date 2015-05-15 下午4:32:39
 * 快递自动补码管理
 *expressAutoComplementManage
 */
public class ExpressAutoComplementManageAction extends AbstractAction{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TemporaryRentalCarMarkTimeManagementAction.class);
	
	/**
	 * 快递自动补码管理Service接口
	 */
	private IExpressAutoComplementManageService expressAutoComplementManageService;
	
	/**
	 * 快递自动补码管理 VO
	 */
	private ExpressAutoComplementManageVo expressAutoComplementManageVo = new ExpressAutoComplementManageVo();
	
	/**
	 * 添加自动补码管理信息
	 * @author 218392 zhangyongxue
	 * @date 2015-05-16
	 */
	@JSON
	public String addExpressAutoComplementManage(){
		try{
			//判断addExpressAutoComplementManageEntity是否为空
			if(null == expressAutoComplementManageVo.getExpressAutoComplementManageEntity()){
				LOGGER.info("增加自动补码信息参数不能为空！");
				return null;
			}
			ExpressAutoComplementManageEntity expressAutoComplementManageEntity = 
					expressAutoComplementManageVo.getExpressAutoComplementManageEntity();
			if(StringUtils.isEmpty(expressAutoComplementManageEntity.getCityCode()) || StringUtils.isEmpty(expressAutoComplementManageEntity.getStatus())){
				LOGGER.info("城市或者状态为空！");
				return null;
			}
			expressAutoComplementManageService.addExpressAutoComplementManage(expressAutoComplementManageEntity);
			//保存成功返回一个对象
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 修改快递自动补码管理信息--修改备注信息
	 */
	@JSON
	public String updateExpressAutoComplementManage(){
		try{
			//获取form信息
			ExpressAutoComplementManageEntity entity = expressAutoComplementManageVo.getExpressAutoComplementManageEntity();
			if(null == entity){
				LOGGER.info("修改快递自动补码管理的备注信息传入的参数不能为空!");
		    	return null;
			}
			expressAutoComplementManageService.updateExpressAutoComplementManage(entity);
			return returnSuccess(MessageType.UPDATE_SUCCESS);
			
		}catch (BusinessException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError(e);
		  }
	}
	
	
	

	/**
     * 分页查询快递自动补码管理信息
     */
    @JSON
    public String queryExpressAutoComplementManage() {
	try {
	    List<ExpressAutoComplementManageEntity> entityList =
	    		expressAutoComplementManageService.queryExpressAutoComplementManage(expressAutoComplementManageVo.getExpressAutoComplementManageEntity(), limit, start);
	    expressAutoComplementManageVo.setExpressAutoComplementManageEntityList(entityList);
	    // 查询总记录数
	    Long totalCount = expressAutoComplementManageService.queryCount(expressAutoComplementManageVo.getExpressAutoComplementManageEntity());
	    // 设置totalCount
	    this.setTotalCount(totalCount);
	    
//	    //测试提供给中转的接口通不通
//	    List<String> cityCodeList1 = expressAutoComplementManageService.queryListCityCodeByCodeStatus("","N");
//	    for(String s : cityCodeList1){
//	    	System.out.println("城市code是：-----1.只传状态----------中转调用------------------"+s);
//	    }
//	    List<String> cityCodeList2 = expressAutoComplementManageService.queryListCityCodeByCodeStatus("331100","");
//	    for(String s : cityCodeList2){
//	    	System.out.println("城市code是：-----2.只传城市code----------中转调用------------------"+s);
//	    }
//	    List<String> cityCodeList3 = expressAutoComplementManageService.queryListCityCodeByCodeStatus("","");
//	    for(String s : cityCodeList3){
//	    	System.out.println("城市code是：-----3.什么都不传----------中转调用------------------"+s);
//	    }
//	    
	    
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.info(e.getMessage(), e);
	    return returnError(e);
	  }
    }
	
    /**
     * 开启快递自动补码管理信息
     * @param expressAutoComplementManageService
     */
	@JSON
	public String openExpressAutoComplementManage(){
		try {
			if(null == expressAutoComplementManageVo.getIdList()){
		    	LOGGER.info("开启快递自动补码管理信息传入的参数不能为空!");
		    	return null;
		    }
			expressAutoComplementManageService.openExpressAutoComplementManage(expressAutoComplementManageVo.getIdList());
		    return returnSuccess(MessageType.OPEN_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError(e);
		}
	}
	
	  /**
     * 关闭快递自动补码管理信息
     * @param expressAutoComplementManageService
     */
	@JSON
	public String closeExpressAutoComplementManage(){
		try {
			if(null == expressAutoComplementManageVo.getIdList()){
		    	LOGGER.info("关闭快递自动补码管理信息传入的参数不能为空!");
		    	return null;
		    }
			expressAutoComplementManageService.closeExpressAutoComplementManage(expressAutoComplementManageVo.getIdList());
		    return returnSuccess(MessageType.CLOSE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError(e);
		}
	}
	/**
	 *  静默操作
	 * @author 232607 
	 * @date 2015-7-23 下午7:23:59
	 * @return
	 * @see
	 */
	@JSON
	public String silentExpressAutoComplementManage(){
		try {
			if(null == expressAutoComplementManageVo.getIdList()){
		    	LOGGER.info("静默快递自动补码管理信息传入的参数不能为空!");
		    	return null;
		    }
			expressAutoComplementManageService.silentExpressAutoComplementManage(expressAutoComplementManageVo.getIdList());
		    return returnSuccess(MessageType.SILENT_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError(e);
		}
	}
	

	/**
	 * set注入
	 * @param expressAutoComplementManageService
	 */
	public void setExpressAutoComplementManageService(
			IExpressAutoComplementManageService expressAutoComplementManageService) {
		this.expressAutoComplementManageService = expressAutoComplementManageService;
	}

	public ExpressAutoComplementManageVo getExpressAutoComplementManageVo() {
		return expressAutoComplementManageVo;
	}

	public void setExpressAutoComplementManageVo(
			ExpressAutoComplementManageVo expressAutoComplementManageVo) {
		this.expressAutoComplementManageVo = expressAutoComplementManageVo;
	}
	
	
}
