
package com.deppon.pda.bdm.module.foss.load.server.service.impl.electronicbill;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.CreateLoadTask;


/**
 * @ClassName CreateLoadTaskEWaybillService.java
 * @Description 电子运单 创建装车任务
 * @author 201638
 * @date 2015-1-29
 */
public class CreateLoadTaskEWaybillService implements IBusinessService<String, CreateLoadTask> {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CreateLoadTaskEWaybillService.class);


	private IPDAExpressPickService pdaExpressPickService;

	@Override
	public CreateLoadTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CreateLoadTask model = JsonUtil.parseJsonToObject(CreateLoadTask.class, asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}

	/**
	 * <p>
	 * TODO(建立装车任务)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:13:13
	 * @param asyncMsg
	 *            请求消息
	 * @param param
	 *            建立装车任务信息
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg,
	 *      java.lang.Object)
	 */
	@Transactional
	@Override
	public String service(AsyncMsg asyncMsg, CreateLoadTask param) throws PdaBusiException {
		// 数据合法性校验
		this.validate(param);
		// 封装参数
		LoadTaskDto model = new LoadTaskDto();
		//工号
		model.setOperatorCode(asyncMsg.getUserCode());
		//创建人部门
		model.setCreateOrgCode(asyncMsg.getDeptCode());
		//车牌号
		model.setVehicleNo(param.getTruckCode());
		//货物类型 快递货只有A类型
		model.setGoodsType("A");
		//目的站编码
		List<String> destCodes = new ArrayList<String>();
		destCodes.add(param.getDestCode());
		model.setDestOrgCodes(destCodes);
		//设备号
		model.setDeviceNo(asyncMsg.getPdaCode());
		//返回值  任务号
		String taskCode = "";
		// 调用FOSS接口
		try {
			taskCode = pdaExpressPickService.createTask(model);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		return taskCode;
	}

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:14:21
	 * @param createLoadTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(CreateLoadTask createLoadTask) throws ArgumentInvalidException {
		// 检验建立装车任务非空
		Argument.notNull(createLoadTask, "createLoadTask");
		// 检验车牌号非空
		Argument.hasText(createLoadTask.getTruckCode(), "createLoadTask.truckCode");
		//检验目的站
		Argument.hasText(createLoadTask.getDestCode(), "createLoadTask.getDestCode");
	}

	/**
	 * @description 电子运单二期 接货装车 创建装车任务
	 * @return ACCT_41
	 * @author 201638
	 * @date 2015-1-29
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_EXP_RCV_CREATE_LOAD_TASK.VERSION;
	}

	/**
	 * @description 同步接口
	 * @return false
	 * @author 201638
	 * @date 2015-1-29
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaExpressPickService(IPDAExpressPickService pdaExpressPickService) {
		this.pdaExpressPickService = pdaExpressPickService;
	}



}
