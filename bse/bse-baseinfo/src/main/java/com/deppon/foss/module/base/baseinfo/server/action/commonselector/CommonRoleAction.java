package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonRoleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonRoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonRoleVo;

public class CommonRoleAction extends AbstractAction {

	private static final long serialVersionUID = 2883644272469312426L;
    //vo
	public CommonRoleVo commonRoleVo ;
	//航空代理人service
	private ICommonRoleService commonRoleService;
	/**
	 * .
	 * <p>
	 * 查询角色信息根据条件<br/>
	 * 方法名：query
	 * </p>
	 * 
	 * @author 078838-foss-lifanghong
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@JSON
	public String query() {		
			List<CommonRoleEntity> commonRoleEntitys = commonRoleService.queryRoleByEntity(commonRoleVo.getCommonRoleEntity(), start, limit);
			commonRoleVo.setCommonRoleEntitys(commonRoleEntitys);
			long total = commonRoleService.queryRoleByEntityCount(commonRoleVo.getCommonRoleEntity());
			setTotalCount(total);
			return returnSuccess();		
	}
	public CommonRoleVo getCommonRoleVo() {
		return commonRoleVo;
	}
	public void setCommonRoleVo(CommonRoleVo commonRoleVo) {
		this.commonRoleVo = commonRoleVo;
	}
	public void setCommonRoleService(ICommonRoleService commonRoleService) {
		this.commonRoleService = commonRoleService;
	}
	
	
	
	




}
