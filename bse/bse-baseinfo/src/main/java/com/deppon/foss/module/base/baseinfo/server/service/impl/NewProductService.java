package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;

import com.deppon.esb.inteface.domain.crm.XcpSyncRequest;
import com.deppon.esb.inteface.domain.crm.XcpSyncResponse;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INewProductDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.INewProductService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NewProductEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 新产品客户协议Service
 * @author 198771
 *
 */

public class NewProductService implements INewProductService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID =46546494497946134L;
	
	private INewProductDao newProductDao;
	

	public void setNewProductDao(INewProductDao newProductDao) {
		this.newProductDao = newProductDao;
	}

	@Override
	public XcpSyncResponse addNewProductEntity(XcpSyncRequest request) {
		//根据客户编码查询
		NewProductEntity product = this.queryNewProductByConstomerCode(request.getCustomerCode());
		NewProductEntity entity = this.convertEntity(request);
		XcpSyncResponse response = new XcpSyncResponse();
		if(product==null){
			newProductDao.addNewProductEntity(entity);
		}else{
			//不为空设置修改人及修改时间
			entity.setModifyDate(new Date());
			entity.setModifyUser("CRM");
			newProductDao.updateNewProductByConstomerCode(entity);
		}
		response.setIfSuccess("1");
		return response;
	}



	@Override
	public NewProductEntity queryNewProductByConstomerCode(String customerCode) {
		return newProductDao.queryNewProductByConstomerCode(customerCode);
	}

	//转换
	private NewProductEntity convertEntity(XcpSyncRequest request){
		NewProductEntity entity = new NewProductEntity();
		if(request!=null){
			entity.setId(UUIDUtils.getUUID());
			entity.setCreateDate(new Date());
			entity.setCreateUser("CRM");
			entity.setCustomerCode(request.getCustomerCode());
			entity.setStartTime(request.getStartTime());
			entity.setEndTime(request.getEndTime());
			entity.setIsNewProCus(request.getIsNewProCus());
		}
		return entity;
	}
}
