package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonTitleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTitleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonTitleVo;

/**
 * 公共组件查询 ACTION.
 *
 * @author dujunhui-187862
 * @date 2014-08-07 下午5:58:33
 */
public class CommonTitleSearchAction extends AbstractAction {
	
	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = 9158210148442159861L;
	
	// 前台传值
	private CommonTitleVo objectVo = new CommonTitleVo();
	
	// service
	private ICommonTitleService commonTitleService;

	/**
	 * @return  the objectVo
	 */
	public CommonTitleVo getObjectVo() {
		return objectVo;
	}

	/**
	 * @param objectVo the objectVo to set
	 */
	public void setObjectVo(CommonTitleVo objectVo) {
		this.objectVo = objectVo;
	}

	/**
	 * @param commonTitleService the commonTitleService to set
	 */
	public void setCommonTitleService(ICommonTitleService commonTitleService) {
		this.commonTitleService = commonTitleService;
	}

	/**
	 * 根据传入对象查询符合条件所有职位信息
	 * @return the string
	 * @description 公共组件--查询
	 * @author dujunhui-187862
	 * @date 2014-08-08 上午8:52:41
	 */
	public String searchTitle() {
		List<CommonTitleEntity> titleList = commonTitleService
				.searchTitleByCondition(objectVo.getEntity(),limit,start);
		totalCount = commonTitleService.countTitleByCondition(objectVo.getEntity());
		objectVo.setTitleEntityList(titleList);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}

}
