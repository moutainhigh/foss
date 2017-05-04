package com.deppon.foss.module.transfer.edi.server.inter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 302307 on 2016/4/7.
 */
@Controller
@RequestMapping("/oppStatementServer")
public interface OppStatementAccountService {


    /**
     * 根据传入条件查询对账单信息
     * */
    @RequestMapping(value = "/queryStatementOpp", method = RequestMethod.POST)
    String queryStatementOpp(String param,HttpServletResponse resp);


    /**
     * 根据传入条件查询对账单信息
     * */
    @RequestMapping(value = "/queryStatementDetailOpp", method = RequestMethod.POST)
    String queryStatementDetailOpp(String param,HttpServletResponse resp);


    /**
     * 根据传入条件查询对账单信息所有的信息
     * */
    @RequestMapping(value = "/queryStatementDetailAllOpp", method = RequestMethod.POST)
    String queryStatementDetailAllOpp(String param,HttpServletResponse resp);
}
