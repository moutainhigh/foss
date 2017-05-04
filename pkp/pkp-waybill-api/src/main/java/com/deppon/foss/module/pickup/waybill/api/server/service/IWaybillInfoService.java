package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by 343451 on 2017/3/1.
 * CUBC-TRADE 根据运单号查询运单信息
 */
@Produces("application/json")
@Consumes("application/json")
public interface IWaybillInfoService {

    @POST
    @Path("/queryWaybillInfoByNo")
    String queryWaybillInfoByNo(String request);
}
