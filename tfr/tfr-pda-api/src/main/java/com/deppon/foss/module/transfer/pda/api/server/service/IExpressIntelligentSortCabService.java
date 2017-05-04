package com.deppon.foss.module.transfer.pda.api.server.service;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deppon.foss.module.transfer.pda.api.shared.domain.RequestIntelligentSortParam;
import com.deppon.foss.module.transfer.pda.api.shared.domain.RequestPacakgeNoParam;
import com.deppon.foss.module.transfer.pda.api.shared.domain.RequestPathDetailParam;
import com.deppon.foss.module.transfer.pda.api.shared.domain.ResponseParameter;

@Controller
@RequestMapping("/IntelligentSort")
public interface IExpressIntelligentSortCabService {
  //生成包号
  @RequestMapping(value = "/makePacakgeNo", method = RequestMethod.POST)
  public ResponseParameter makePacakgeNo(RequestPacakgeNoParam requestParam);
  //建包
  @RequestMapping(value = "/createPacakge", method = RequestMethod.POST)
  public ResponseParameter createPacakge(RequestIntelligentSortParam entity);
  //获取走货路由
  @RequestMapping(value = "/queryWaybillPath", method = RequestMethod.POST)
  public ResponseParameter queryWaybillPath(RequestPathDetailParam requestParam);
  
}
