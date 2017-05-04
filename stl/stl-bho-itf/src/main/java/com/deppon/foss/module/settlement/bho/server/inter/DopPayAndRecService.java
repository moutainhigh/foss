package com.deppon.foss.module.settlement.bho.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 家装对账单管理
 * 
 * @ClassName: DopStatementService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-12-11 下午6:39:35
 */
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface DopPayAndRecService {
	/**
	 * 生成家装应收单应付单
	 * 
	 * @Title: buildPayAndRec
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/buildDopPayAndRec")
	public String buildDopPayAndRec(String payAndRec);

	/**
	 * 家装应收应付单 反审核
	 * 
	 * @Title: cancelPayAndRec
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/cancelDopPayAndRec")
	public String cancelDopPayAndRec(String payAndRec);
}
