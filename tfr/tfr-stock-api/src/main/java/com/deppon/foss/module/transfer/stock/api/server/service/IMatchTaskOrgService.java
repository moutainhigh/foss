package com.deppon.foss.module.transfer.stock.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.OrgDto;


/**
 * 
 * 到达部门为非驻地部门时，匹配任务部门	
 * 运单中的到达部门为非驻地部门时，FOSS匹配任务部门	
 * 1.	当货物未到到达部门，且没有在中转外场库存时，则匹配“收货部门”为任务部门。
 *	2.	当货物在中转外场库存时，则匹配“收货部门”和“当前库存外场”为任务部门。
 *	3.	当货物在到达部门库存或已经签收出库，则匹配“到达部门”为任务部门。
 *	4.	分批配载时，只按照上述前2点规则进行判断，只要分批配载的货物其中一批满足上述前2点中的匹配规则，则匹配出相应任务部门（比如货物分批为AB，既要根据A的库存状态匹配任务部门，也要根据B的库存状态匹配任务部门）。
 *	
 *	到达部门为驻地部门时，匹配任务部门	
 *	运单中的到达部门为驻地部门时，FOSS匹配任务部门	
 *	1.	当货物还未到到达部门所属的驻地外场，且未在中转外场库存，则匹配“收货部门”为任务部门。
 *	2.	当货物在中转外场（此外场不包括到达部门所属的驻地外场）库存时，则匹配“收货部门”和“当前库存外场”为任务部门。
 *	3.	当货物在到达部门库存或已经签收出库，或在到达部门所属驻地外场库存时，则匹配“到达部门”为任务部门。
 *	4.	分批配载时，当货物还未全部到达到达部门所属驻地外场时，只按照上述前2点规则进行判断，只要分批配载的货物其中一批满足上述前2点中的匹配规则，则匹配出相应任务部门（比如货物分批为AB，既要根据A的库存状态匹配任务部门，也要根据B的库存状态匹配任务部门）。
 *	5.	分批配载时，当货物已经全部到达了到达部门所属驻地外场时，则只按照第3点规则进行判断。
 *	
 *	相同任务部门只取其中一个	
 *	FOSS匹配出的任务部门为2个及以上，且这些任务部门中存在相同部门时，则只取这些相同部门中其中一个为任务部门	
 *	FOSS匹配出的任务部门为2个及以上，且这些任务部门中存在相同部门时，则只取这些相同部门中其中任何一个为任务部门，并将该任务部门通过接口传给CRM。
 *	
 *	丢货时，匹配任务部门	存在丢货时，只匹配“收货部门”为任务部门	当货物存在丢货时，则只匹配“收货部门”为任务部门 
 *
 *
 *
 * 匹配任务接口，为CRM提供的接口， 由crm传进来一个运单号，通过库存状态匹配出任务部门！！！！！
 * @author 200978  xiaobingcheng
 * 2014-10-10
 */
public interface IMatchTaskOrgService extends IService {
	
	/**
	 * 由crm传进来一个 运单号、来电人、来电人类型 ，通过库存状态匹配出任务部门
	 * @Author: 200978  xiaobingcheng 
	 * @update:  200968 zwd 20150420
	 * 2014-10-10
	 * @param waybillNo 
	 * @param callMan 
	 * @param callType
	 * @return
	 */
	List<OrgDto> matchTaskOrg(String waybillNo , String callMan ,  String callType);
	
	/**
	 * @author zwd 200968
	 * @param waybillNo 
	 * @param callType
	 * @date 2016-03-02
	 * FOSS可以根据来电人身份、运单的库存状态匹配出相应的部门，并通过接口将部门信息传给CRM。
	 * @return
	 */
	List<OrgDto> matchTaskOrg(String waybillNo , String callType);

}
