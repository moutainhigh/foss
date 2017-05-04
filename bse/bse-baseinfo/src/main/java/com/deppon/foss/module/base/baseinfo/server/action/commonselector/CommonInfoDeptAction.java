package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonInfoDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.InfoDeptVo;

public class CommonInfoDeptAction extends AbstractAction implements
IQueryAction {
	/** serializeId. */
	private static final long serialVersionUID = -6327316619927198728L;
	private ICommonInfoDeptService commonInfoDeptService;
	private InfoDeptVo infoDeptVo;
	/**
	 * 信息部公共查询方法.
	 *
	 * @return the string
	 * @author lifanghong
	 * @date 2013-02-18
	 * @see com.deppon.foss.module.base.baseinfo.server.action.commonselector.CommonInfoDeptAction#query()
	 */
	@JSON
	@Override
	public String query() {
		this.setTotalCount(commonInfoDeptService
				.countRecordByCondition(infoDeptVo.getInfoDeptEntity()));
		List<InfoDeptEntity> infoDeptEntitys= commonInfoDeptService.queryInfoDeptEntityByCondtion(infoDeptVo.getInfoDeptEntity(),start, limit );
		infoDeptVo.setInfoDeptEntityList(infoDeptEntitys);
		// TODO Auto-generated method stub
		return returnSuccess();
	}
	public InfoDeptVo getInfoDeptVo() {
		return infoDeptVo;
	}
	public void setInfoDeptVo(InfoDeptVo infoDeptVo) {
		this.infoDeptVo = infoDeptVo;
	}
	public void setCommonInfoDeptService(
			ICommonInfoDeptService commonInfoDeptService) {
		this.commonInfoDeptService = commonInfoDeptService;
	}

}
