package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerDegreeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerDegreeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonCustomerDegreeVo;
/**
 * 公共组件查询 ACTION.
 *
 * @author dujunhui-187862
 * @date 2014-9-23 上午8:41:25
 */
public class CommonCustomerDegreeSearchAction extends AbstractAction {
	
	/**
	 * （序列化）
	 */
	private static final long serialVersionUID = 9158210148442159861L;
	
	// 前台传值
	private CommonCustomerDegreeVo objectVo = new CommonCustomerDegreeVo();
	
	// service
	private ICommonCustomerDegreeService commonCustomerDegreeService;
	
	public CommonCustomerDegreeVo getObjectVo() {
		return objectVo;
	}

	public void setObjectVo(CommonCustomerDegreeVo objectVo) {
		this.objectVo = objectVo;
	}

	public void setCommonCustomerDegreeService(
			ICommonCustomerDegreeService commonCustomerDegreeService) {
		this.commonCustomerDegreeService = commonCustomerDegreeService;
	}

	/**
	 * 根据传入对象查询符合条件所有客户等级信息
	 * @return the string
	 * @description 公共组件--查询
	 * @author dujunhui-187862
	 * @date 2014-9-23 上午8:44:38
	 */
	public String searchCustomerDegree() {
		List<CommonCustomerDegreeEntity> titleList = commonCustomerDegreeService
				.searchCustomerDegreeByCondition(objectVo.getEntity(),limit,start);
		totalCount = commonCustomerDegreeService.countCustomerDegreeByCondition(objectVo.getEntity());
		objectVo.setCustomerDegreeEntityList(titleList);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
}