package com.deppon.foss.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
* @description 获取封签信息
* @version 1.0
* @author 106162-gis
* @update 2016年4月25日 上午8:07:27
*/
@Produces(MediaType.APPLICATION_JSON)
public interface IITFSealInformationService {

	/**-----------------------------配合快递开发 开始---------------------**/
	/**
	* @description 配合快递传递过来交接单号,获取foss系统的封签号
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#querySealInfo(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	*/
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/sealinformation")
	// http://localhost:8080/foss/services/operatorSealAds/sealinformation
	public Object querySealInfo(String reqMsg);
	
	/**
	* @description 配合(快递)校验快递交接单生成
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	*/
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/checkexpressEir")
	//http://localhost:8080/foss/services/operatorSealAds/checkexpressEir
	public Object checkExpressEIRGenerate(String reqMsg);
	
	
	/**
	* @description 配合(快递)校验校验临时租车标记
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/checkleasecarstate")
	//http://localhost:8080/foss/services/operatorSealAds/checkleasecarstate
	public Object tempLeaseCarState(String reqMsg);
	
	
	/**
	* @description 配合(快递)根据正单号查询运单号
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkWaybillNoByPositiveNum(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/checkwaybillNum")
	//http://localhost:8080/foss/services/operatorSealAds/checkwaybillNum
	public Object checkWaybillNoByPositiveNum(String reqMsg);
	
	/**
	* @description 配合(快递)运单号查询航空正单和航空交接单及其航空交接单轨迹
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#queryEIRSingleFormalByWaybillNo(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/queryEIRAndSingleFormal")
	//http://localhost:8080/foss/services/operatorSealAds/queryEIRAndSingleFormal
	public Object queryEIRAndSFByWaybillNo(String reqMsg);
	
	
	/**
	* @description 配合(快递)运单号查询航空正单和航空正单的制单部门等实体信息
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#queryAirWaybillByWaybillNo(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/queryAirWaybillCreateDept")
	//http://localhost:8080/foss/services/operatorSealAds/queryAirWaybillCreateDept
	public Object queryAirWaybillByWaybillNo(String reqMsg);
	
	
	
	/**
	* @description 配合(快递PDA)根据车牌号校验是否解绑封签
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#isCheckVehicleSealInfo(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年11月23日 上午10:07:04
	* @version V1.0
	*/
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/checkVehicleSealInfo")
	//http://localhost:8080/foss/services/operatorSealAds/checkVehicleSealInfo
	//部署地址：http://192.168.20.21/tfr-wk-itf/services/operatorSealAds/checkVehicleSealInfo
	public Object isCheckVehicleSealInfo(String reqMsg);
	
	
    /**-----------------------------配合快递开发 结束---------------------**/
}
