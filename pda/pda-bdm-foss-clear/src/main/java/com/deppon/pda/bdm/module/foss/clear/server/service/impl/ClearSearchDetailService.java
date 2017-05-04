package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.stock.api.server.service.IFindGoodsAdminService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminDetailEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminEntity;
import com.deppon.foss.module.transfer.stock.api.shared.vo.FindGoodsAdminVo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearSearchDetailResult;

/**
 * 获取找货明细
 * @author 245955
 *
 */
public class ClearSearchDetailService implements IBusinessService<List<ClearSearchDetailResult>,FindGoodsAdminEntity>{
	private static final Log LOG = LogFactory.getLog(RefreshClearTaskService.class);
	private IFindGoodsAdminService findGoodsAdminService;
	/**
	 * 解析包头
	 */
	@Override
	public FindGoodsAdminEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
        FindGoodsAdminEntity entity=JsonUtil.parseJsonToObject(FindGoodsAdminEntity.class, asyncMsg.getContent());
		entity.setFindGoodsManCode(asyncMsg.getUserCode());//找货人
		//entity.setOrgCode(asyncMsg.getDeptCode());//部门编码
        return entity;
	}

	/**
	 * 获取找货明细Service类
	 */
	@Override
	public List<ClearSearchDetailResult> service(AsyncMsg asyncMsg,
			FindGoodsAdminEntity findGoodsAdminEntity) throws PdaBusiException {
		validate(asyncMsg,findGoodsAdminEntity);
		LOG.info("start Foods cleartask ...");
		//fOSS返回Vo给PDA
		FindGoodsAdminVo	dtos =new FindGoodsAdminVo();
		try {
			dtos=findGoodsAdminService.createFindGoodsTask(findGoodsAdminEntity);
		} catch (BusinessException e) {
			LOG.error("明细异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		List<ClearSearchDetailResult> clearSearchDetailResult = this.wrapPdaStTaskDtos(dtos);
		LOG.info("Foods cleartask end...");
		return clearSearchDetailResult;
	}

	private List<ClearSearchDetailResult> wrapPdaStTaskDtos(FindGoodsAdminVo  dtos) {
		List<ClearSearchDetailResult> clearSearchDetailResult = new ArrayList<ClearSearchDetailResult>();
		if(dtos!=null){
			//返回到PDA前台
			ClearSearchDetailResult result=null;
			for(FindGoodsAdminDetailEntity entity:dtos.getFindGoodsAdminDetailEntitys()){		
				result = new ClearSearchDetailResult();		
				/**运单号*/
				result.setWaybillNo(entity.getWaybillNo());
				/**丢货件数*/
				result.setPieces(String.valueOf(entity.getLostGoodsQty()));
				/**总件数*/
				result.setTotalQty(String.valueOf(entity.getTotalQty()));
				/**任务号*/
				result.setTaskCode(dtos.getFindGoodsAdminEntity().getTaskNo());
				/**当前货区*/
				result.setGoodsName(dtos.getFindGoodsAdminEntity().getGoodsAreaName());
				/**目的站*/
				result.setZoneName(entity.getDestOrgName());
				/**体积*/
				result.setVolume(entity.getVolume());
				/**重量*/
				result.setWeight(entity.getWeight());
				/**包装*/
				result.setPacking(entity.getPackageType());
				/**流水号*/
				result.setSerialNo(entity.getSerialNo());
				clearSearchDetailResult.add(result);		
			}
		}		
		return clearSearchDetailResult;
	}
	/**
	 * 操作类型
	 */
	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_SEARCH_DETAIL.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
	/**
	 * 非空校验
	 * @param asyncMsg
	 * @param param
	 */
	private void validate(AsyncMsg asyncMsg, FindGoodsAdminEntity param) {
		Argument.notNull(param, "ClearSearchDetailEntity");
		//Argument.hasText(param.getGoodsAreaCode(), "ClearSearchDetailEntity.goodsAreaCode");
		Argument.notNull(param.getTaskCreateDate(), "ClearSearchDetailEntity.taskCreateDate");
		Argument.notNull(param.getTaskEndDate(), "ClearSearchDetailEntity.taskEndDate");
	}

	public void setFindGoodsAdminService(
			IFindGoodsAdminService findGoodsAdminService) {
		this.findGoodsAdminService = findGoodsAdminService;
	}
}
