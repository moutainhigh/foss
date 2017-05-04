package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonTransTeamService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonTransTeamVo;

/**
 * 公共组件查询 ACTION.
 *
 * @author dujunhui-187862
 * @date 2014-08-13 上午9:11:33
 */
public class CommonTransTeamAction extends AbstractAction {
	
	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = 9158210148442159861L;
	
	// 前台传值
	private CommonTransTeamVo objectVo = new CommonTransTeamVo();
	
	// service
	private ICommonTransTeamService commonTransTeamService;

	/**
	 * @return  the objectVo
	 */
	public CommonTransTeamVo getObjectVo() {
		return objectVo;
	}

	/**
	 * @param objectVo the objectVo to set
	 */
	public void setObjectVo(CommonTransTeamVo objectVo) {
		this.objectVo = objectVo;
	}

	/**
	 * @return  the commonTransTeamService
	 */
	public ICommonTransTeamService getCommonTransTeamService() {
		return commonTransTeamService;
	}

	/**
	 * @param commonTransTeamService the commonTransTeamService to set
	 */
	public void setCommonTransTeamService(
			ICommonTransTeamService commonTransTeamService) {
		this.commonTransTeamService = commonTransTeamService;
	}

	/**
	 * 根据传入组织编码查询下属车队组信息
	 * @return the string
	 * @description 公共组件--查询
	 * @author dujunhui-187862
	 * @date 2014-08-13 上午9:16:41
	 */
	public String searchTransTeam() {
		List<OrgAdministrativeInfoEntity> transTeamList = commonTransTeamService
				.queryTransTeams(objectVo.getEntity(),limit,start);
		totalCount = commonTransTeamService.countTransTeams(objectVo.getEntity());
		objectVo.setEntityList(transTeamList);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}

}
