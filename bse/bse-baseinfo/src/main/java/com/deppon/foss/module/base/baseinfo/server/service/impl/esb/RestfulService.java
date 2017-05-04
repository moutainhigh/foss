package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISupplierService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.IRestfulService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierRequest;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierResponse;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;



/**
 *  Restful服务端接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-12-21 上午9:39:57,content:TODO </p>
 * @author 232607 
 * @date 2015-12-21 上午9:39:57
 * @since
 * @version
 */
public class RestfulService  implements IRestfulService{
	
	private static final long serialVersionUID = 1L;
	//日志记录
	private static final Logger LOGGER = LoggerFactory.getLogger(RestfulService.class);
	@Context
	HttpServletRequest HttpRequest;
	@Context
	HttpServletResponse HttpResponse;
	/**
	 * 供应商service
	 */
	private ISupplierService supplierService;
	/**
     * 业务锁
     */
    private IBusinessLockService businessLockService;
    
    
	public void setSupplierService(ISupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/** 
	 * <p>接收供应商信息，DOP推送供应商信息到FOSS</p> 
	 * @author 232607
	 * @date 2015-9-10 下午2:01:17
	 * @param json
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IQueryOrgAttitudeService#queryOrgAttribute(java.lang.String)
	 */
	@Override
	public String receiveSupplierInfo(String json) {
		LOGGER.info("------------------------接收供应商信息接口begin----------------------------");
		HttpResponse.setCharacterEncoding("utf-8");
		HttpResponse.setHeader("ESB-ResultCode", "1");
		SupplierResponse response = new SupplierResponse();
		try {
			//将发送过来的json转成请求java类
			JSONObject jsonObj = JSONObject.fromObject(json);
			SupplierRequest request = (SupplierRequest) JSONObject.toBean(jsonObj,SupplierRequest.class);
			//请求为空则直接返回
			if(request==null){
				response.setSuccess(false);
				response.setMessage("请求信息为空");
				String res=JSON.toJSONString(response);
				return res;
			}
			//请求编码为空则直接返回
			if(StringUtils.isEmpty(request.getSupplierCode())){
				response.setSuccess(false);
				response.setMessage("供应商编码为空");
				String res=JSON.toJSONString(response);
				return res;
			}
			//加锁，若已锁则等1秒
			MutexElement mutex = new MutexElement(
					request.getSupplierCode(),
					"SUPPLIER_CODE",
					MutexElementType.SUPPLIER_CODE);
			boolean result = businessLockService.lock(mutex,1);
			//加锁失败则直接返回
			if(!result){
				response.setSuccess(false);
				response.setMessage("加锁失败");
				String res=JSON.toJSONString(response);
				return res;
			}
			SupplierEntity entity=supplierConvertRequestToEntity(request);
			//通过编码查后台有没有这个供应商，无则新增，有则作废再新增
			SupplierEntity oldEntity=supplierService.querySupplierByCode(request.getSupplierCode());
			if(oldEntity==null){
				supplierService.addSupplier(entity);
			}else{
				supplierService.deleteSupplierByCode(request.getSupplierCode());
				supplierService.addSupplier(entity);
			}
			response.setSuccess(true);
			response.setMessage("保存成功");
			String res=JSON.toJSONString(response);
			LOGGER.info("------------------------接收供应商信息接口end----------------------------");
			return res;
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage("未知异常");
			String res=JSON.toJSONString(response);
			return res;
		}
	}
	//供应商信息接口——将DOP发送的请求转换成咱们用来存储的实体
	public SupplierEntity supplierConvertRequestToEntity(SupplierRequest request) {
		SupplierEntity entity=new SupplierEntity();
		entity.setCode(request.getSupplierCode());
		entity.setName(request.getSupplierFullName());
		entity.setSimpleName(request.getSupplierSimpleName());
		entity.setContactPerson(request.getContactPerson());
		entity.setContactPhone(request.getContactPhone());
		entity.setContactAddress(request.getContactAddress());
		entity.setFurnitureFlag(("1").equals(request.getFurnitureFlag())?"Y":"N");
		entity.setHouseholdFlag(("1").equals(request.getFurnitureFlag())?"Y":"N");
		entity.setConstructionFlag(("1").equals(request.getConstructionFlag())?"Y":"N");
		entity.setRemark(request.getRemark());
		entity.setActiveTime(request.getCancelTime());
		entity.setIsUsing(("1").equals(request.getActiveFlag())?"Y":"N");
		return entity;
	}
	
}
