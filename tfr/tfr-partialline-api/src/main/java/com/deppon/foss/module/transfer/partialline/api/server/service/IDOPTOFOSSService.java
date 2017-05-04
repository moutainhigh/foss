package com.deppon.foss.module.transfer.partialline.api.server.service;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.SignInfoAuditRequest;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.SignInfoAuditResponse;


/**
 * 德邦家装-家装送装明细及签收确认
 * DOP推送供应商以及家装签收信息至FOSS系统服务端 Service
 *
 * @author FOSS-LLN-269701
 * @date 2015-12-03 上午9:18:36
 *
 */
@Controller
@RequestMapping("/addDpSignInMsg")
public interface IDOPTOFOSSService {
	/**
	 * 德邦家装
	 * 家装送装明细及签收确认
	 * 
	 * @author foss-lln-269701
	 * @date 2015-12-03 上午11:01:32
	 */

  @RequestMapping(value = "/addDpSignInFromDop", method = RequestMethod.POST)
  public SignInfoAuditResponse addDpjzSignInDetialBillsFromDop(SignInfoAuditRequest requestParam);
  
}
