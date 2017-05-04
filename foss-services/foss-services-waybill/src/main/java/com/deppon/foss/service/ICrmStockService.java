package com.deppon.foss.service;

import java.util.List;

import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;


/**
  * FOSS-TFR提供给CRM的接口
  * @author 273247
  * @date 2016-02-21 上午12:45:05
   
         移动CRM需要向FOSS系统传递未签收的运单号码作为参数查询，FOSS需返回该运单的库存位置和库存时间信息。
*/
@Produces(MediaType.APPLICATION_JSON)
public interface ICrmStockService{

	/**
	 * @desc:返回库存信息
	 * @param waybillNo
	 * @return
	 * @author:zzq
	 * @date:2016年02月21日上午12:50:26
	 */
	
    @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value="/backStockInfo")
	public List<StockEntity> backStockInfo(String waybillNo);

	//自测：http://localhost:8080/bse-baseinfo-web/services/restfulService/receiveSupplierInfo
	//开发环境：http://10.224.68.36:8080/bse-baseinfo-web/services/restfulService/receiveSupplierInfo
	//日常2：http://10.230.13.69/bse-baseinfo-web/services/restfulService/receiveSupplierInfo
}
