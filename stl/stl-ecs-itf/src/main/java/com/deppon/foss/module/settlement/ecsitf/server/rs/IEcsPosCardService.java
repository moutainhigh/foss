/**
 * 
 */
package com.deppon.foss.module.settlement.ecsitf.server.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *  悟空pos待机刷卡同步接口
 *  @author  354658-duyijun 
 *	@date 创建时间：2016-10-21 上午9:20:05 
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IEcsPosCardService {
	
	/**
	 * 
	 * @author 354658-duyijun
	 * @date 2016-10-21 上午9:39:09
	 * @param jsonStr
	 * @return
	 */
	@POST
	@Path("/ecsPosCard")
	String ecsPosCard(String jsonStr);

}
