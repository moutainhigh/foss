package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDADeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAWaybillReturnDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.WaybillReturnEntity;


/**   
 * @ClassName DeliveryWaybillReturnService  
 * @Description 派送装车运单退回功能   
 * @author  092038 张贞献  
 * @date 2015-5-5    
 */ 
public class DeliveryWaybillReturnService implements IBusinessService<Void, WaybillReturnEntity>{
	private IPDADeliverLoadService pdaDeliverLoadService;
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:19:33
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public WaybillReturnEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		WaybillReturnEntity entity = JsonUtil.parseJsonToObject(WaybillReturnEntity.class, asyncMsg.getContent());
		return entity;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:19:38
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, WaybillReturnEntity param)
			throws PdaBusiException {

		//校验数据合法性
		this.validate(param);
		PDAWaybillReturnDto dto = new PDAWaybillReturnDto();
		//任务号
		dto.setLoadTaskNo(param.getTaskCode());
		//运单号
		dto.setWaybillNo(param.getWblCode());   
		//运输性质
		dto.setTransportType(param.getTransType());
		//库存件数
		dto.setStockQty(param.getStockQty());
		//操作件数
		//dto.setOperateQty(param.getOperateQty());
		//退回类型
		dto.setRetreatType(param.getRetreatType());
		//退回内容
		dto.setRetreatReason(param.getRetreatReason());
		try {
			//调用FOSS接口
			long startTime = System.currentTimeMillis();
			//判断退回类型不能为空
			if(null!=param.getRetreatType()){
				pdaDeliverLoadService.waybillReturn(dto);
			}
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]派送运单退回消耗时间:"+(endTime-startTime)+"ms");
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
	}
	

	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:20:12
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate( WaybillReturnEntity entity)
			throws ArgumentInvalidException {
		//检验扫描非空
		Argument.notNull(entity, "WaybillReturnEntity");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "waybillReturnEntity.taskCode");
		//运单号非空
		Argument.hasText(entity.getWblCode(), "waybillReturnEntity.wblCode");
		//退回原因
		Argument.hasText(entity.getRetreatType(), "waybillReturnEntity.retreatType");
	
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_WAYBILLRETURN.VERSION;
	}

	//是否异步
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaDeliverLoadService(
			IPDADeliverLoadService pdaDeliverLoadService) {
		this.pdaDeliverLoadService = pdaDeliverLoadService;
	}
}
