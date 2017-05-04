package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonPackingSupplierVo;
/**
 * 包装供应商公共选择器action
 * @author 130566
 *
 */
public class CommonPackagingSupplierAction extends AbstractAction {

	private static final long serialVersionUID = -7056075818412238993L;
	/**
	 * 交互实体
	 */
	private CommonPackingSupplierVo objectVo =new CommonPackingSupplierVo();
	/**
	 * service
	 */
	private IPackagingSupplierService packagingSupplierService;
	
	public CommonPackingSupplierVo getObjectVo() {
		return objectVo;
	}

	public void setObjectVo(CommonPackingSupplierVo objectVo) {
		this.objectVo = objectVo;
	}

	public void setPackagingSupplierService(
			IPackagingSupplierService packagingSupplierService) {
		this.packagingSupplierService = packagingSupplierService;
	}

	/**
	 * 查询公共选择器
	 * @return
	 */
	public String searchPackagingSupplier(){
		try {
			List<PackagingSupplierEntity> resultList =packagingSupplierService.queryPackagingSupplierUnique(objectVo.getPackagingSupplierEntity(), limit, start);
			objectVo.setPackagingSupplierEntities(resultList);
			this.setTotalCount(packagingSupplierService.queryRecordCount(objectVo.getPackagingSupplierEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
}
