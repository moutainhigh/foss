package com.deppon.foss.module.transfer.opp.server.ws;

import com.deppon.foss.esb.edi.server.air.SignInfoSendRequest;
import com.deppon.foss.esb.edi.server.air.SignInfoSendResponse;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.transfer.opp.server.inter.IOppSignConfirService;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

/**
 * Created by 302307 on 2016/4/7.
 */
public class OppSignConfirService implements IOppSignConfirService {

    private static final Logger LOGGER = Logger.getLogger(OppSignConfirService.class);


    @Context
    HttpServletResponse resp;
    @Context
    HttpServletRequest req;

    //注入EdiSignInfoService

    private OppToFossSignWaybillService oppToFossSignWaybillService;


   private IWaybillRfcService waybillRfcService;




    @SuppressWarnings("finally")
	@Override
    public @ResponseBody String queryIsExsitsWayBillRfc(@RequestBody String waybillNo,HttpServletResponse resp) {
    	resp.setHeader("ESB-ResultCode", "1");
        LOGGER.info("Opp判断传入的运单号是否存在未处理的更改单开始...");
        Boolean isExsitsWayBillRfc=false;
        String response="";
      /*  if(waybillNo==null || "".equals(waybillNo)){
            LOGGER.info("推送opp到Foss数据异常！");
        }*/
        try {
            //传入的运单号是否存在未处理的更改单
            isExsitsWayBillRfc = waybillRfcService.isExsitsWayBillRfc(waybillNo);
            if(isExsitsWayBillRfc){
                response="Y";
            }else if(!isExsitsWayBillRfc){
                response="N";
            }
           System.out.println("Opp判断传入的运单号是否存在未处理的更改单");
        }catch (Exception e){
        	resp.setHeader("ESB-ResultCode", "0");
        	e.printStackTrace() ;  
            LOGGER.info("推送opp数据异常！");
        }finally {
            //resp.addHeader("OPP-ResultCode", "1");
            return response;
        }
    }


    /**
     *
     * @param SignInfoSendRequest payload
     * @param Holder<ESBHeader> esbHeader
     * @return
     */ 
 
    @SuppressWarnings("finally")
	@Override
    public @ResponseBody SignInfoSendResponse signWaybillFromOpp(@RequestBody SignInfoSendRequest payload,HttpServletResponse resp) {
    	resp.setHeader("ESB-ResultCode", "1");
    	LOGGER.info("Opp传入的运单签收信息签收开始...");
        SignInfoSendResponse signInfoSendResponse = new SignInfoSendResponse();
        try {
            //Opp传入的运单签收信息签收
          signInfoSendResponse=  oppToFossSignWaybillService.sendSignInfo(payload);
        }catch (Exception e){
        	resp.setHeader("ESB-ResultCode", "0");
            LOGGER.info("推送opp数据异常==========！");
            e.printStackTrace();
        }finally {

        	//sonar-352203
            if(signInfoSendResponse == null){
                return null;
            }
            return signInfoSendResponse;
        }
    }
    
    public void setOppToFossSignWaybillService(OppToFossSignWaybillService oppToFossSignWaybillService) {
        this.oppToFossSignWaybillService = oppToFossSignWaybillService;
    }

    public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
        this.waybillRfcService = waybillRfcService;
    }
}
