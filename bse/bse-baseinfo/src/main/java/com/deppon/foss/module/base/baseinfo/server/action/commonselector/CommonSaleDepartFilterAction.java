package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SaleDepartmentVo;

/** 
 * @author  313353-qiupeng
 * @date 创建时间：2016-9-30 上午9:41:03 
 * @version 1.0 
 **/
public class CommonSaleDepartFilterAction extends AbstractAction implements
		IQueryAction {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// vo
	/** The sale department vo. */
	private SaleDepartmentVo saleDepartmentVo = new SaleDepartmentVo();
	// service
	/** The common sale department service. */
	private ICommonSaleDepartmentService commonSaleDepartmentService;

	/**
	 * 查询营业部信息.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:22:20
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		saleDepartmentVo.setDepartmentEntities(commonSaleDepartmentService
				.querySaleDeptFilterExactByEntity(
						saleDepartmentVo.getDepartmentEntity(), start, limit));
		this.setTotalCount(commonSaleDepartmentService
				.querySaleDeptFilterExactByEntityCount(saleDepartmentVo
						.getDepartmentEntity()));
		return returnSuccess();
	}

	/**
	 * setter.
	 *
	 * @param commonSaleDepartmentService the new common sale department service
	 */
	public void setCommonSaleDepartmentService(
			ICommonSaleDepartmentService commonSaleDepartmentService) {
		this.commonSaleDepartmentService = commonSaleDepartmentService;
	}

	/**
	 * getter.
	 *
	 * @return the sale department vo
	 */
	public SaleDepartmentVo getSaleDepartmentVo() {
		return saleDepartmentVo;
	}

	/**
	 * setter.
	 *
	 * @param saleDepartmentVo the new sale department vo
	 */
	public void setSaleDepartmentVo(SaleDepartmentVo saleDepartmentVo) {
		this.saleDepartmentVo = saleDepartmentVo;
	}

}
