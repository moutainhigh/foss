package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;


import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.CustomerEntity;

/** 
  * @ClassName KdCustomerQueryService 
  * @Description 快递客户信息查询
  * @author zhangzhenxian 
  * @date 2013-7-24 下午7:21:26 
*/ 
public class KdCustomerQueryService implements IBusinessService<CustomerEntity, CustomerEntity> {

	private Logger log = Logger.getLogger(getClass());

	
	/**
	 * 
	 * @description 解析包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public CustomerEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
	    CustomerEntity customer = JsonUtil.parseJsonToObject(CustomerEntity.class, asyncMsg.getContent());
		return customer;
	}

	/** 
	* @Description: 校验数据合法性 描述
	* @param asyncMsg
	* @param customer
	* @throws PdaBusiException 
	* @return void
	* @author zhangzhenxian
	* @date 2013-7-24 下午7:21:41
	*/ 
	private void validate(AsyncMsg asyncMsg, CustomerEntity customer) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		
		// 判断开单实体
		Argument.notNull(customer, "CustomerEntity");

	}

	/**
	 * 
	 * @description 服务方法
	 * @param asyncMsg
	 * @param customer
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	public CustomerEntity service(AsyncMsg asyncMsg, CustomerEntity customer) throws PdaBusiException {
		this.validate(asyncMsg, customer);
		CustomerEntity reCustomer=null;
		
		
		log.debug("---调用FOSS查询客户信息接口开始---");
		
		try {
			long startTime = System.currentTimeMillis();
			
			reCustomer = customer; 
	        reCustomer.setCustomerId("092038");
	        reCustomer.setCustomerName("张贞献");
			
			//reCustomer= pdaWaybillService.submitWaybillByPDA(waybillPdaDto);
			log.debug("---调用FOSS查询客户信息接口返回结果："+reCustomer.getCustomerId()+"---");
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]FOSS查询客户信息接口接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS查询客户信息接口结束---");
		return reCustomer;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_QUERY_CUSINFO.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
}
