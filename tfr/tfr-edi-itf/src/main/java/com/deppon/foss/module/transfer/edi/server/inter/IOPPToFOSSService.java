package com.deppon.foss.module.transfer.edi.server.inter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deppon.foss.module.transfer.edi.server.domain.OPPLocusRequest;
import com.deppon.foss.module.transfer.edi.server.domain.OPPLocusResponse;
import com.deppon.foss.module.transfer.edi.server.domain.OppWaybillNoRequest;
import com.deppon.foss.module.transfer.edi.server.domain.RfcPushDataToFossRequst;
import com.deppon.foss.module.transfer.edi.server.domain.SerialNoByOppWaybillNoResponse;

/**
 * 
* @description OPP推送数据至FOSS
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月12日 上午11:35:52
 */
@Controller
@RequestMapping("/dataFromOpp")
public interface IOPPToFOSSService {

	/**
	 * 
	* @description OPP根据运单号查询 该运单所有的流水号
	* @param requstStr
	* @return SerialNoByOppWaybillNoResponse
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月12日 上午11:40:56
	 */
	@RequestMapping(value = "/getSerialNoByOppWaybillNo", method = RequestMethod.POST)
	public SerialNoByOppWaybillNoResponse getSerialNoByOppWaybillNo(OppWaybillNoRequest requestStr,HttpServletResponse resp);
	
	/**
	 * 
	* @description OPP推送出发到达以及返货轨迹给FOSS
	* @param requstStr
	* @return SerialNoByOppWaybillNoResponse
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月12日 上午11:40:56
	 */
	@RequestMapping(value = "/getOppLocusForBse", method = RequestMethod.POST)
	public OPPLocusResponse getOppLocusForBse(OPPLocusRequest requstStr,HttpServletResponse resp);
	
	/**
	 * 
	* @description opp推送 更改单数据至foss 更新foss合大票相关表
	* @param requstStr
	* @return
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月12日 下午5:47:44
	 */
	@RequestMapping(value = "/updateAirPickUpInFromOPP", method = RequestMethod.POST)
	OPPLocusResponse updateAirPickUpInFromOPP(@RequestBody RfcPushDataToFossRequst requstStr,HttpServletResponse resp);

}
