package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPartnerDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PartnerDepartmentVo;

/**
 * 
 * 公共选择器   网点映射关系  一级合伙人网点名称
 * @author 308861 
 * @date 2016-8-20 下午3:43:24
 * @since
 * @version
 */
public class CommonLeagueSaleDeptAction extends AbstractAction implements IQueryAction{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 124521838425190907L;

	/**
	 * 注入ICommonPartnerDepartmentService
	 */	
	private ICommonPartnerDepartmentService partnerDepartmentService;

	private PartnerDepartmentVo departmentVo=new PartnerDepartmentVo();
	
	@Override
	public String query() {
		//查询一级合伙人网点名称
		departmentVo.setDepEntitys(partnerDepartmentService.
				queryByIsLeagueSaleDept(departmentVo.getDepEntity(), start, limit));
		//统计一级合伙人网点总数
		this.setTotalCount(partnerDepartmentService.
				queryByIsLeagueSaleDeptCount(departmentVo.getDepEntity()));
		return returnSuccess();
	}	
	/**
	 * 
	 * setter 
	 * @author 308861 
	 * @date 2016-8-22 上午10:32:28
	 * @param partnerDepartmentService
	 * @see
	 */
	public void setPartnerDepartmentService(
			ICommonPartnerDepartmentService partnerDepartmentService) {
		this.partnerDepartmentService = partnerDepartmentService;
	}

	public PartnerDepartmentVo getDepartmentVo() {
		return departmentVo;
	}

	public void setDepartmentVo(PartnerDepartmentVo departmentVo) {
		this.departmentVo = departmentVo;
	}
}
