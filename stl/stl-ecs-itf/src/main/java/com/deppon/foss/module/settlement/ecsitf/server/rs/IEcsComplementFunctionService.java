package com.deppon.foss.module.settlement.ecsitf.server.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.esb.inteface.domain.ecs.EcsFossComplementRequest;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementComplementBillDto;

/**
 * 快递补码接口
 * @author foss-231434-bieyexiong
 * @date 2016-4-21 20:09
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8") 
public interface IEcsComplementFunctionService {

	/**
	 * 补码功能财务接口调用：如果运单存在应收虚拟网点的到付运费或者代收货款费用，且最终网点信息为自有网点，则需要调用结算接口，
	 * 红冲虚拟网点的应收到付运费或代收货款费用，生成应收最新提货网点的到付运费或代收货款费用。.
	 *
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-21 20:12
	 */
	@POST
	@Path("/complement")
	String complementFunctionWriteBackAndCreateReceivable(String jsonReq);
	
	/**
	 * 补码功能财务接口调用：如果运单存在应收虚拟网点的到付运费或者代收货款费用，且最终网点信息为自有网点，则需要调用结算接口，
	 * 红冲虚拟网点的应收到付运费或代收货款费用，生成应收最新提货网点的到付运费或代收货款费用。.
	 * (为了加事务控制，所以重写一个方法)
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-21 20:12
	 */
	void complementFunctionWriteBackAndCreateReceivable(SettlementComplementBillDto dto,CurrentInfo currentInfo,EcsFossComplementRequest req);
	
	/**
	 * 校验能否补码
	 * @author foss-231434-bieyexiong
	 * @date 2016-6-01 14:05
	 */
	@POST
	@Path("/checkComplement")
	String checkComplementFunction(String jsonReq);
}
