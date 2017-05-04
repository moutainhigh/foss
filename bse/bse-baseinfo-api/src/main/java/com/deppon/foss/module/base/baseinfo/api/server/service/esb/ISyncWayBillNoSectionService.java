package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.WayBillNoSectionDto;
/**
 * 同步FOSS分配的运单号段给CRM系统
 * @author 262036 HuangWei
 *
 * @date 2015-6-25 下午3:14:04
 */
public interface ISyncWayBillNoSectionService {

	/**
	 * @return WayBillNoSectionDto
	 * @description 同步FOSS分配的运单号段给CRM系统
	 * @author 262036 HuangWei
	 * @date 2015-6-25下午3:14:50
	 */
	@POST
	@Path(value="/addWayBillNoSection")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	WayBillNoSectionDto addWayBillNoSection(String rertunString);
	//http://localhost:8080/bse-baseinfo-web/services/wayBillNoSection/addWayBillNoSection
}
