package com.deppon.foss.module.settlement.ptpitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
public interface UpdateWithholdStatusService {

    /**
     * 修改应收单扣款状态
     *
     * @param entity
     *          应收单实体
     * @author 099995-foss-hemingyu
     * @date 2016-01-12 上午18:23:38
     */
	@POST
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Path("/updateWithholdStatus")
    String updateWithholdStatus(String obj);
}
