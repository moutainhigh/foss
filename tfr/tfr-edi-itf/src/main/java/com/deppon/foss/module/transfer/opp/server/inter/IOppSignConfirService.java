package com.deppon.foss.module.transfer.opp.server.inter;

import javax.servlet.http.HttpServletResponse;

import com.deppon.foss.esb.edi.server.air.SignInfoSendRequest;
import com.deppon.foss.esb.edi.server.air.SignInfoSendResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 306566 on 2016/05/13.
 */
@Controller
@RequestMapping("/oppSignConfirServer")
public interface IOppSignConfirService {
    /**
     * 根据传入条件查询对账单信息
     * */
    @RequestMapping(value = "/queryIsExsitsWayBillRfc", method = RequestMethod.POST)
    String queryIsExsitsWayBillRfc(String waybillNo,HttpServletResponse resp);


    /**
     * 传入签收信息进行签收信息
     * */

    @RequestMapping(value = "/signWaybillFromOpp", method = RequestMethod.POST)
    SignInfoSendResponse signWaybillFromOpp(SignInfoSendRequest payload,HttpServletResponse resp);


}
