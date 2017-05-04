package com.deppon.foss.module.base.baseinfo.server.action;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PressureTestVo;

/**
 * 压力测试action.
 *
 * @author foss-zhangxiaohui
 * @date 2013-4-8 下午4:23:17
 */
public class PressureTestAction  extends AbstractAction{

	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = -7204236011847138586L;
	
	/**
	 * 压力测试Vo.
	 */
	private PressureTestVo vo = new PressureTestVo();
	
	/**
	 * 组织信息 Service接口.
	 */
	IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 
	 *
	 * @return 
	 */
	@JSON
	public String pressTestByCode(){
		try{
			if(StringUtils.isNotEmpty(vo.getCode())){
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(vo.getCode());
			}
		}catch(BusinessException e){
			return returnError("查询失败");
		}
		// 返回结果
		return returnSuccess();
		
	}
	
	
	/**
	 * 设置 组织信息 Service接口.
	 *
	 * @param orgAdministrativeInfoService the new 组织信息 Service接口
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	
	/**
	 * 获取 压力测试Vo.
	 *
	 * @return  the vo
	 */
	public PressureTestVo getVo() {
	    return vo;
	}

	
	/**
	 * 设置 压力测试Vo.
	 *
	 * @param vo the vo to set
	 */
	public void setVo(PressureTestVo vo) {
	    this.vo = vo;
	}

	
}
