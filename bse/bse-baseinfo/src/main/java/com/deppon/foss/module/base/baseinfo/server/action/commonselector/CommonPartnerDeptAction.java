package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPartnerDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
/**
 * 
 * TODO(合伙人网点查询公共组件)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-352676-yuahb,date:2016-9-21 上午9:30:32,content:TODO </p>
 * @author Foss-352676-YUANHB 
 * @date 2016-9-21 上午9:30:32
 * @since
 * @version
 */
public class CommonPartnerDeptAction  extends AbstractAction implements IQueryAction{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 124521838425190907L;
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(CommonPartnerDeptAction.class);
	
	private ICommonPartnerDeptService commonPartnerDeptService;
	
	private SaleDepartmentEntity saleDepartmentEntity = new SaleDepartmentEntity();
	
	private List<SaleDepartmentEntity> saleDepartmentEntitys;
	@Override
	public String query() {
		//查询合伙人list
		
		saleDepartmentEntitys = commonPartnerDeptService.queryPartnerDeptsByCondition(saleDepartmentEntity, limit, start);
		//查询合伙人count
		this.setTotalCount(commonPartnerDeptService.queryPartnerDeptDeptsCount(saleDepartmentEntity));

		return returnSuccess();
	}
	
	
	public SaleDepartmentEntity getSaleDepartmentEntity() {
		return saleDepartmentEntity;
	}

	public void setSaleDepartmentEntity(SaleDepartmentEntity saleDepartmentEntity) {
		this.saleDepartmentEntity = saleDepartmentEntity;
	}

	public List<SaleDepartmentEntity> getSaleDepartmentEntitys() {
		return saleDepartmentEntitys;
	}

	public void setSaleDepartmentEntitys(
			List<SaleDepartmentEntity> saleDepartmentEntitys) {
		this.saleDepartmentEntitys = saleDepartmentEntitys;
	}

	public void setCommonPartnerDeptService(
			ICommonPartnerDeptService commonPartnerDeptService) {
		this.commonPartnerDeptService = commonPartnerDeptService;
	}

}
