package com.deppon.pda.bdm.module.foss.load.server.service.impl.derytranload;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressSendPieceService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverLoadTaskDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadPdaDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload.CreateDeliveryTranTaskResult;
import com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload.CreateDeliveryTranTaskEntity;

/**
 * 创建派件交接任务
 * @ClassName CreateDeliveryTranTaskService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-16
 */
public class CreateDeliveryTranTaskService implements IBusinessService<CreateDeliveryTranTaskResult, CreateDeliveryTranTaskEntity> {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CreateDeliveryTranTaskEntity.class);
	private ILoadPdaDao loadPdaDao;
	
	private IPDAExpressSendPieceService pdaExpressSendPieceService;

	/**
	 * 解析包体
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-17
	 */
	@Override
	public CreateDeliveryTranTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CreateDeliveryTranTaskEntity model = JsonUtil.parseJsonToObject(CreateDeliveryTranTaskEntity.class,
				asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	/**
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-17
	 */
	@Override
	public CreateDeliveryTranTaskResult service(AsyncMsg asyncMsg, CreateDeliveryTranTaskEntity param) throws PdaBusiException {
		CreateDeliveryTranTaskResult result =null; 
		try {
			//数据合法性校验
			  this.validate(asyncMsg, param);
			  int diffQueryDept;
			  ExpressDeliverLoadTaskDto task = new ExpressDeliverLoadTaskDto();
			  //封装参数
			  task.setDeviceNo(asyncMsg.getPdaCode());//PDA编号
			  task.setOperatorCode(asyncMsg.getUserCode());//操作人
			  task.setCreateOrgCode(asyncMsg.getDeptCode());//创建部门
			  task.setVehicleNo(param.getTruckCode());//车牌号
			  task.setCourier(param.getTallyerCode());//快递员工号
			  result= new CreateDeliveryTranTaskResult();
			  //判断登陆员工与交接员工是否是同一部门
			   String userCode=asyncMsg.getUserCode().toString();	
			   String tallyerCode=param.getTallyerCode().toString();
			   diffQueryDept=loadPdaDao.queryDiffQueryDept(userCode, tallyerCode);
			   if(diffQueryDept<=0){
				  throw new BusinessException("非同部门快递员之间不能交接!");
			   }
			   //返回任务号到前台
			   String taskCode=pdaExpressSendPieceService.createTask(task);
			   result.setTaskCode(taskCode);
		} catch (BusinessException e) {
		  throw new FossInterfaceException(e.getCause(),"非同部门快递员之间不能交接!");
		} 	
	  return result;
	}
	
	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-17
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DELIVERY_TASK_CAEATE.VERSION;
	}
	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-17
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * 参数验证
	 * @description 
	 * @param entity
	 * @author 245955
	 * @date 2015-4-16
	 */
    public void validate(AsyncMsg asyncMsg,CreateDeliveryTranTaskEntity entity)throws ArgumentInvalidException{
    	//检验建立派件交接任务非空
    	Argument.notNull(entity, "entity");
    	//检验员工工号非空
    	Argument.hasText(entity.getUserCode(),"entity.userCode");
    	//检验PDA编号非空
    	Argument.hasText(asyncMsg.getPdaCode(),"asyncMsg.pdaCode");
    	// 检验时间非空
    	//Argument.notNull(entity.getScanTime(),"entity.scanTime");
    }
    public void setLoadPdaDao(ILoadPdaDao loadPdaDao) {
		this.loadPdaDao = loadPdaDao;
	}
	public void setPdaExpressSendPieceService(
			IPDAExpressSendPieceService pdaExpressSendPieceService) {
		this.pdaExpressSendPieceService = pdaExpressSendPieceService;
	}
    
}
