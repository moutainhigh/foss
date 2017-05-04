package com.deppon.foss.module.transfer.opp.server.inter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/oppGetAgentDeptService")
public interface OppGetFossAgentDeptService {
	
    @RequestMapping(value = "/getFossAgentDept", method = RequestMethod.POST)
	String  getFossAgentDept(String param,HttpServletResponse resp) throws Exception;
}
