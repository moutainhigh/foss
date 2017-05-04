package com.deppon.foss.module.settlement.cubcitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 对接cubc打款信息实现
 * 
 * @ClassName: PayInfoManagerService
 * @author &269052 |周禹安
 * @date 2017-1-4 下午10:08:23
 */
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface PayInfoManagerService {
	/**
	 * 裹裹，pda开单，查询数据，根据类型区分
	 * 
	 * @Title: queryPayInfoByParam
	 * @author： 269052 |周禹安
	 * @date 2017-1-4 下午10:19:21
	 */
	@POST
	@Path("/queryPayInfoByParam")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	Response queryPayInfoByParam(String param);

	/**
	 * 作废打款信息
	 * 
	 * @Title: cancelPosCardBySerialNo
	 * @author： 269052 |周禹安
	 * @date 2017-1-9 下午7:15:51
	 */
	@POST
	@Path("/cancelPosCardBySerialNo")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	Response cancelPosCardBySerialNo(String param);
}
