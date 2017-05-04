package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAllAgentService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonAllAgentVo;

public class CommonAgentAction extends AbstractAction implements IQueryAction {
	/** serialVersionUID. */
	private static final long serialVersionUID = -3383631039625626863L;

	/**
	 * 代理vo
	 */
	private CommonAllAgentVo commonAllAgentVo;
	/**
	 * 代理service
	 */
	private ICommonAllAgentService commonAllAgentService;

	/**
	 * 空运代理查询.
	 * 
	 * @return the string
	 * @author 130346-foss-lifanghong
	 * @date 2013-05-09
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.commonselector.CommonAgentAction#query()
	 */
	@JSON
	public String query() {
		commonAllAgentVo.setAllAgentEntitys(commonAllAgentService
				.queryAgentByCondtion(commonAllAgentVo.getAllAgentEntity(),
						limit, start));
		setTotalCount(commonAllAgentService
				.countRecordByCondtion(commonAllAgentVo.getAllAgentEntity()));
		return returnSuccess();
	}

	public CommonAllAgentVo getCommonAllAgentVo() {
		return commonAllAgentVo;
	}

	public void setCommonAllAgentVo(CommonAllAgentVo commonAllAgentVo) {
		this.commonAllAgentVo = commonAllAgentVo;
	}

	public void setCommonAllAgentService(
			ICommonAllAgentService commonAllAgentService) {
		this.commonAllAgentService = commonAllAgentService;
	}

}
