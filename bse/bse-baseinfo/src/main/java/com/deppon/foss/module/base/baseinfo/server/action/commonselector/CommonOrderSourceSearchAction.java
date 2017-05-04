package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrderSourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrderSourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonOrderSourceVo;

/**
 * 公共组件查询 ACTION.
 *
 * @author dujunhui-187862
 * @date 2014-9-22 下午2:50:43
 */
public class CommonOrderSourceSearchAction extends AbstractAction {
	
	/**
	 * （序列化）
	 */
	private static final long serialVersionUID = 9158210148442159861L;
	
	// 前台传值
	private CommonOrderSourceVo objectVo = new CommonOrderSourceVo();
	
	// service
	private ICommonOrderSourceService commonOrderSourceService;

	public CommonOrderSourceVo getObjectVo() {
		return objectVo;
	}

	public void setObjectVo(CommonOrderSourceVo objectVo) {
		this.objectVo = objectVo;
	}

	public void setCommonOrderSourceService(
			ICommonOrderSourceService commonOrderSourceService) {
		this.commonOrderSourceService = commonOrderSourceService;
	}

	/**
	 * 根据传入对象查询符合条件所有订单来源信息
	 * @return the string
	 * @description 公共组件--查询
	 * @author dujunhui-187862
	 * @date 2014-9-22 下午2:52:35
	 */
	public String searchOrderSource() {
		List<CommonOrderSourceEntity> titleList = commonOrderSourceService
				.searchOrderSourceByCondition(objectVo.getEntity(),limit,start);
		totalCount = commonOrderSourceService.countOrderSourceByCondition(objectVo.getEntity());
		objectVo.setOrderSourceEntityList(titleList);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}

}
