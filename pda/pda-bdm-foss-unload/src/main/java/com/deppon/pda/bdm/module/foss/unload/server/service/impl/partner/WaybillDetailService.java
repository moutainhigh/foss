package com.deppon.pda.bdm.module.foss.unload.server.service.impl.partner;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAOrderTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderSerialNoDetailEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.partner.WaybillDetailEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.partner.WaybillDetailResult;
/**
 * 
 * @ClassName: WaybillDetailService 
 * @Description: TODO(运单明细) 
 * @author &268974  wangzhili
 * @date 2016-1-27 上午11:18:24 
 *
 */
public class WaybillDetailService implements IBusinessService<WaybillDetailResult, WaybillDetailEntity>{
	
	private static final Logger log = Logger.getLogger(WaybillDetailService.class);
	private IPDAOrderTaskService pdaOrderTaskService;
	
	//解析参数
	@Override
	public WaybillDetailEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		WaybillDetailEntity entity = JsonUtil.parseJsonToObject(WaybillDetailEntity.class, asyncMsg.getContent());
		return entity;
	}

	//方法入口
	@Override
	public WaybillDetailResult service(AsyncMsg asyncMsg, WaybillDetailEntity param)
			throws PdaBusiException {
		//校验参数合法性
		this.validate(param);
		log.info("参数值为:"+param);
		List<PDAOrderSerialNoDetailEntity> result = new ArrayList<PDAOrderSerialNoDetailEntity>();
		WaybillDetailResult waybillDetail = new WaybillDetailResult();
		try {
			result = pdaOrderTaskService.queryPDAOrderTaskSerialNoListByBillNo(param.getWaybillDetailID());
			waybillDetail.setPDAOrderSerialNoDetailEntitys(result);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} catch(Exception e){
			throw new FossInterfaceException(null,"调用foss出现未知异常");
		}
		return waybillDetail;
	}

	//操作类型
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_WAY_DETAIL.VERSION;
	}

	//同步
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * 
	 * @Title: validate 
	 * @Description: TODO(校验参数合法性)  
	 * @return void    返回类型 
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @author： 268974  wangzhili
	 */
	private void validate(WaybillDetailEntity entity) throws ArgumentInvalidException{
		//检验运单明细实体非空
		Argument.notNull(entity,"WaybillDetailEntity");
		//检验任务编号非空
		Argument.hasText(entity.getOrderTaskNo(), "entity.orderTaskNo");
		//校验id非空
		Argument.hasText(entity.getWaybillDetailID(), "entity.waybillDetailID");
		//校验运单号
		Argument.hasText(entity.getWaybillNo(), "entity.waybillNo");
	}

	public void setPdaOrderTaskService(IPDAOrderTaskService pdaOrderTaskService) {
		this.pdaOrderTaskService = pdaOrderTaskService;
	}
	

}
