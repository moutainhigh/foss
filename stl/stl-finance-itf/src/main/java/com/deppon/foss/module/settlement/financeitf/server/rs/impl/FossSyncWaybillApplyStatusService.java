package com.deppon.foss.module.settlement.financeitf.server.rs.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillApplyStatusService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillApplyStatusEntity;
import com.deppon.foss.module.settlement.financeitf.server.rs.IFossSyncWaybillApplyStatusService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 322906 on 2016/6/14.
 */
public class FossSyncWaybillApplyStatusService implements IFossSyncWaybillApplyStatusService {
    private final Logger logger = LogManager.getLogger(FossSyncWaybillApplyStatusService.class);

    //成功返回1
    private static final String SUCCESS = "1";

    //失败返回0
    private static final String FAILURE = "0";

    private IWaybillApplyStatusService waybillApplyStatusService;

    @Context
    HttpServletResponse res;

    @Override
    public @ResponseBody String addWaybillApplyStatus(@RequestBody String json) {
        res.setHeader("ESB-ResultCode" , "1");

        if(StringUtil.isBlank(json)){
            logger.error("获取发票申请状态信息失败：参数为空");
            return this.getResponse(FAILURE, "获取发票申请状态信息失败：参数为空");
        }
        try{
            Map<String, List<WaybillApplyStatusEntity>> params= (Map<String, List<WaybillApplyStatusEntity>>) JSONArray.parse(json);
            List<WaybillApplyStatusEntity> list = JSONArray.parseArray(JSONArray.toJSONString(params.get("waybillApplyStatus")), WaybillApplyStatusEntity.class);
            if(list==null){
                return this.getResponse(FAILURE, "FIMS没有将发票申请状态信息发送到FOSS");
            }
            for(WaybillApplyStatusEntity entity:list){
                WaybillApplyStatusEntity existEntity = waybillApplyStatusService.queryByBillNo(entity.getBillNo());
                if(existEntity==null ){
                    waybillApplyStatusService.add(entity);
                }else{
                    waybillApplyStatusService.updateBillStatus(entity);
                }
            }

            return this.getResponse(SUCCESS, "同步发票申请状态信息成功");
        }catch (ClassCastException e){
            logger.error("json信息转换异常");
            return this.getResponse(FAILURE, "json信息转换异常"+e.getMessage());
        }catch (Exception e){
            logger.error("同步发票申请状态信息失败"+e.getMessage());
            return this.getResponse(FAILURE, "同步发票申请状态信息失败"+e.getMessage());
        }

    }
    private String getResponse(String result,String message){
        Map<String,Object> map = new HashMap<String,Object>();

        map.put("result", result);
        map.put("message", message);
        String response = JSONObject.toJSONString(map);
        return response;
    }

    public void setWaybillApplyStatusService(IWaybillApplyStatusService waybillApplyStatusService) {
        this.waybillApplyStatusService = waybillApplyStatusService;
    }
}
