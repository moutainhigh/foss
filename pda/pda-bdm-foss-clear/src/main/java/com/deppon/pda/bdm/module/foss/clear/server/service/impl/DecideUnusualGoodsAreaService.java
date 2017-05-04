package com.deppon.pda.bdm.module.foss.clear.server.service.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.UnusualGoodsAreaEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.UnusualGoodsAreaResult;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain 
 * @file AcctFinishEntity.java 
 * @description 判断异常货区是否正常
 * @author 268974 wangzhili
 * @created 2015-11-14  
 * @version 1.0
 */
public class DecideUnusualGoodsAreaService  implements
IBusinessService<UnusualGoodsAreaResult, UnusualGoodsAreaEntity> {
	private static final Log log = LogFactory
			.getLog(CreateClearTaskService.class);
	
	private IPdaStockcheckingService pdaStockcheckingService;

	/**
	 * 
	 * @description 解析json字符串
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public UnusualGoodsAreaEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		UnusualGoodsAreaEntity entity = JsonUtil.parseJsonToObject(
				UnusualGoodsAreaEntity.class, asyncMsg.getContent());
		return entity;
	}

	/**
	 * 
	 * @description (判断异常货区) 
	 * @param asyncMsg
	 * @param 
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public UnusualGoodsAreaResult service(AsyncMsg asyncMsg,
			UnusualGoodsAreaEntity param) throws PdaBusiException {
		//校验参数
		this.validate(asyncMsg, param);
		UnusualGoodsAreaResult result = new UnusualGoodsAreaResult();
		 String isabnormal = null;
		try{
		long startTime = System.currentTimeMillis();
	     isabnormal = pdaStockcheckingService.queryGoodsType(param.getWstrBigDepartmentCodeU(), param.getCrgAreaCode());	
	    long endTime = System.currentTimeMillis();
		log.debug("调用foss接口耗时:" + (endTime - startTime));
		} catch(BusinessException e){
			throw new FossInterfaceException(e.getCause(), e.getMessage());
		} catch (Exception e) {
			throw new FossInterfaceException(null,"调用foss接口出现未知异常");
		}
		result.setIsabnormal(isabnormal);
		return result;
	}

	/**
	 * 
	 * @description 校验数据合法性
	 * @param param
	 * @throws PdaBusiException 
	 * @created 2012-12-28 下午9:08:35
	 */
	private void validate(AsyncMsg asyncMsg,UnusualGoodsAreaEntity param) throws ArgumentInvalidException {
		Argument.notNull(param, "UnusualGoodsAreaEntity");
		//验证货区编码
		Argument.hasText(param.getCrgAreaCode(), "UnusualGoodsAreaEntity.crgAreaCode");
		//验证用户编号
		Argument.hasText(param.getWstrBigDepartmentCodeU(), "UnusualGoodsAreaEntity.wstrBigDepartmentCodeU");
		
	}
	
	/**
	 * @description业务类型
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#getOperType()
	 */
	@Override
	public String getOperType() {	
		return ClearConstant.OPER_TYPE_CLEAR_GOODSAREA_DECIDE.VERSION;
	}

	/**
	 * @description同步
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#isAsync()
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setPdaStockcheckingService(
			IPdaStockcheckingService pdaStockcheckingService) {
		this.pdaStockcheckingService = pdaStockcheckingService;
	}

}
