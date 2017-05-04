package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISupplierService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SupplierVo;

/**
 * 供应商action
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-7-28 下午7:28:28,content:TODO </p>
 * @author 232607 
 * @date 2015-7-28 下午7:28:28
 * @since
 * @version
 */
public class SupplierAction extends AbstractAction {
    /**
     * 序列化
     */
	private static final long serialVersionUID = 2773644272419312421L;
	/**
	 * 日志处理
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierAction.class);
	/**
	 * 供应商service
	 */
	private ISupplierService supplierService;
	/**
	 * 供应商vo
	 */
	private SupplierVo supplierVo  = new SupplierVo();
	

	public SupplierVo getSupplierVo() {
		return supplierVo;
	}

	public void setSupplierVo(SupplierVo supplierVo) {
		this.supplierVo = supplierVo;
	}

	public void setSupplierService(ISupplierService supplierService) {
		this.supplierService = supplierService;
	}

	
	/**
	 * 选择器——供应商选择器的分页查询
	 * @author 232607 
	 * @date 2015-7-28 下午7:47:04
	 * @return
	 * @see
	 */
	@JSON
	public String comboQuerySupplier() {
		try {
			List<SupplierEntity> supplierEntitys = supplierService.comboQuerySupplier(supplierVo.getSupplierEntity(), start, limit);
			supplierVo.setSupplierEntitys(supplierEntitys);
			this.setTotalCount(supplierService.countComboQuerySupplier(supplierVo.getSupplierEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error(e.getMessage());
			return returnError(e.getMessage());
		}
	}
	


}