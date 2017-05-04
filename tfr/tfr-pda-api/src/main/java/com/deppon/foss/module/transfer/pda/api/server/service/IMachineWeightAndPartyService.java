package com.deppon.foss.module.transfer.pda.api.server.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deppon.foss.module.transfer.pda.api.shared.domain.RequestMachineScanDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.ResponseParameter;

@Controller
@RequestMapping("/BCMachSortScan")
public interface IMachineWeightAndPartyService {
	@RequestMapping(value = "/scan", method = RequestMethod.POST)
	public ResponseParameter scan(RequestMachineScanDetailEntity requestParam);
}
