package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaStTaskDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.CreateClearTask;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.CreateClearTaskResult;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.StockPostionEntity;

/**
 * 
 * @ClassName CreateClearTaskService
 * @Description 创建清仓任务服务类
 * @author xujun dpxj@deppon.com
 * @date 2013-1-10 下午7:02:49
 */
public class CreateClearTaskService implements
		IBusinessService<CreateClearTaskResult, CreateClearTask> {
	private static final Log LOG = LogFactory
			.getLog(CreateClearTaskService.class);
	
	private IPdaStockcheckingService pdaStockcheckingService;

	@Override
	public CreateClearTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CreateClearTask clearTask = JsonUtil.parseJsonToObject(
				CreateClearTask.class, asyncMsg.getContent());
		return clearTask;
	}

	@Transactional
	@Override
	public CreateClearTaskResult service(AsyncMsg asyncMsg,
			CreateClearTask param) {
		LOG.info("start create cleartask ...");
		// 参数验证
		this.validate(asyncMsg, param);
		CreateClearTaskResult res = new CreateClearTaskResult();
		PdaStTaskDto dtos = null;
		// 是否是快递货
		String isExpressGoods = null;
		//是否是驻地营业部
		String isBusiDept = null;
		if ("Express".equals(param.getIsExpressInfo())||"COURIER".equals(asyncMsg.getUserType())) {
			isExpressGoods = "Y";
		} else if ("NonExpress".equals(param.getIsExpressInfo())) {
			isExpressGoods = "N";
		} else {
			isExpressGoods = null;
		}
		if("COURIER".equals(asyncMsg.getUserType())){
			try {
				// 判断营业部是不是驻地营业部
				isBusiDept = pdaStockcheckingService.queryDeptInfo(asyncMsg.getDeptCode());
				if("Y".equals(isBusiDept)||isBusiDept == null){
					throw new FossInterfaceException(null,"驻地营业部快递员不能创建清仓任务");
				}
			} catch (BusinessException e) {
				LOG.error("查询快递员所在的营业部是不是驻地营业部：" + e.getMessage());
				throw new FossInterfaceException(e.getCause(), e.getErrorCode());
			} 
		}
		try {
			dtos = pdaStockcheckingService.createPdaStTask(
					asyncMsg.getDeptCode(), 
					param.getCrgAreaCode(), 
					asyncMsg.getUserCode(), 
					asyncMsg.getPdaCode(), 
					param.getTaskNo(), 
					param.getIsDeryCleanStock(),// 是否派送清仓 （Y
					param.getStartPieces(), // 起始件数 2013-08-14
					param.getEndPieces(), // 结束件数 2013-08-14
					param.getTakeType(),//提货方式
					param.getAdministrativeRegion(),//行政区域 
					isExpressGoods//是否快递货
					);
			LOG.debug("调用foss接口耗结束");
		} catch (BusinessException e) {
			LOG.error("创建任务异常：" + e);
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		} catch (Exception e){
			throw new FossInterfaceException(null,"调用foss接口出现未知异常");
		}	
		res.setCreateUser(dtos.getCreatorCode());
		res.setTaskCode(dtos.getStTaskNo());
		res.setGoodsAreaCode(dtos.getGoodsAreaCode());
		res.setGoodsAreaName(dtos.getGoodsAreaName());

		if ("Y".equals(param.getIsDeryCleanStock())) {
			List<BaseDataDictDto> baseDataDictDtos = dtos.getPositionNoList();
			List<StockPostionEntity> entities = new ArrayList<StockPostionEntity>();
			if(baseDataDictDtos != null){
				addPostions(entities, baseDataDictDtos);
				res.setPositionNoList(entities);
			}else{
				LOG.info("库位信息为空");
			}
		}
		LOG.info("create cleartask end...");
		return res;
	}

	private void addPostions(List<StockPostionEntity> postionVos,
			List<BaseDataDictDto> dtos) {
		StockPostionEntity inOutStockPostionEntity = null;
		for (BaseDataDictDto dto : dtos) {
			inOutStockPostionEntity = new StockPostionEntity();
			inOutStockPostionEntity.setValueCode(dto.getValueCode());
			inOutStockPostionEntity.setValueName(dto.getValueName());
			postionVos.add(inOutStockPostionEntity);
		}
	}

	private void validate(AsyncMsg asyncMsg, CreateClearTask param) throws ArgumentInvalidException{
		Argument.notNull(param, "CreateClearTask");
		//Argument.hasText(param.getIsExpressInfo(), "CreateClearTask.isExpressInfo");
		//Argument.hasText(param.getTaskNo(), "CreateClearTask.TaskNo");
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.DeptCode");
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_TASK_CREATE.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaStockcheckingService(
			IPdaStockcheckingService pdaStockcheckingService) {
		this.pdaStockcheckingService = pdaStockcheckingService;
	}
}
