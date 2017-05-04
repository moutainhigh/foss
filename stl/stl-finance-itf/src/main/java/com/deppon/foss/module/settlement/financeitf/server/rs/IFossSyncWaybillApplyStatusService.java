package com.deppon.foss.module.settlement.financeitf.server.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by 322906 on 2016/6/14.
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IFossSyncWaybillApplyStatusService {

    @POST
    @Path("/addWaybillApplyStatus")
    public String addWaybillApplyStatus(String json);
}
