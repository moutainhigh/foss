package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerProfessionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerProfessionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonCustomerProfessionVo;
/**
 * 公共组件查询 ACTION.
 *
 * @author dujunhui-187862
 * @date 2014-9-23 上午8:41:25
 */
public class CommonCustomerProfessionSearchAction extends AbstractAction {
	
	/**
	 * （序列化）
	 */
	private static final long serialVersionUID = 9158210148442159861L;
	
	// 前台传值
	private CommonCustomerProfessionVo objectVo = new CommonCustomerProfessionVo();
	
	// service
	private ICommonCustomerProfessionService commonCustomerProfessionService;
	
	public CommonCustomerProfessionVo getObjectVo() {
		return objectVo;
	}

	public void setObjectVo(CommonCustomerProfessionVo objectVo) {
		this.objectVo = objectVo;
	}

	public void setCommonCustomerProfessionService(
			ICommonCustomerProfessionService commonCustomerProfessionService) {
		this.commonCustomerProfessionService = commonCustomerProfessionService;
	}

	/**
	 * 根据传入对象查询符合条件所有客户行业信息
	 * @return the string
	 * @description 公共组件--查询
	 * @author dujunhui-187862
	 * @date 2014-9-23 上午10:51:26
	 */
	public String searchCustomerProfession() {
		List<CommonCustomerProfessionEntity> titleList = commonCustomerProfessionService
				.searchCustomerProfessionByCondition(objectVo.getEntity(),limit,start);
		totalCount = commonCustomerProfessionService.countCustomerProfessionByCondition(objectVo.getEntity());
		objectVo.setCustomerProfessionEntityList(titleList);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
}