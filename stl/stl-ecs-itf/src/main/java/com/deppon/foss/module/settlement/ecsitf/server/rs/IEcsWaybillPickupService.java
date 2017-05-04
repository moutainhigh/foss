package com.deppon.foss.module.settlement.ecsitf.server.rs;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.EcsFossWaybillPickupRequest;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 快递新增、更改、作废、校验运单，财务相关接口
 * @author foss-231434-bieyexiong
 * @date 2016-4-19 11:51
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IEcsWaybillPickupService {

	/**
	 * 悟空裹裹订单，补录时，查询裹裹暂存信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-6-25 8:05
	 */
	@POST
	@Path("/queryGreenHandWrap")
	String queryGreenHandWrapByWaybillNo(String jsonReq);
	
	/**
	 * 快递新增运单，生成财务单据接口
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 11:51
	 */
	@POST
	@Path("/addWaybill")
	String addWaybill(String jsonReq);
	
	/**
	 * 快递转寄开单(为了加事务控制，所以重写一个方法)
	 * @author foss-231434-bieyexiong
	 * @date 2016-5-10 10:56
	 */
    //WSCEntity wscEntity
    void toChangeTermsPayment(WSCEntity wscEntity , WaybillPickupInfoDto waybillDto, WaybillPickupInfoDto oldWaybill, CurrentInfo currentInfo, boolean isAddBill);
    void toChangeTermsPayment(EcsFossWaybillPickupRequest req , WaybillPickupInfoDto waybillDto, WaybillPickupInfoDto oldWaybill, CurrentInfo currentInfo, boolean isAddBill);
	void toChangeTermsPayment(WaybillPickupInfoDto waybillDto,WaybillPickupInfoDto oldWaybill,CurrentInfo currentInfo,boolean isAddBill);


    /**
	 * 快递开单(为了加事务控制，所以重写一个方法)
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 21:10
	 */
    void addWaybill(WSCEntity wscEntity, WaybillPickupInfoDto waybillDto, CurrentInfo currentInfo);
	void addWaybill(WaybillPickupInfoDto waybillDto,CurrentInfo currentInfo);
	
	/**
	 * 快递起草更改运单，校验财务是否可以更改接口
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 11:51
	 */
	@POST
	@Path("/canChange")
	String canChange(String jsonReq);
	
	/**
	 * 快递受理更改运单，相关财务单据更改接口
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 11:51
	 */
	@POST
	@Path("/modifiAndCancle")
	String modifiAndCancle(String jsonReq);
	
	/**
	 * 快递更改单(为了加事务控制，所以重写一个方法)
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 21:34
	 */
    String modifiAndCancle(EcsFossWaybillPickupRequest req,String isChange,WaybillPickupInfoDto oldWaybill, WaybillPickupInfoDto newWaybill, CurrentInfo currentInfo);
	String modifiAndCancle(String isChange,WaybillPickupInfoDto oldWaybill,WaybillPickupInfoDto newWaybill,CurrentInfo currentInfo);
}
