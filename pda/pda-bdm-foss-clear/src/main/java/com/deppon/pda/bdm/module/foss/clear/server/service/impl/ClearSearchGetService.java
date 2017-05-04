package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.server.service.IFindGoodsAdminService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearSearchGetEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearSearchGetResult;

/**
 * 获取找货货区
 * @author 245955
 *
 */
public class ClearSearchGetService implements IBusinessService<List<ClearSearchGetResult>,ClearSearchGetEntity> {

	private IFindGoodsAdminService findGoodsAdminService;
	/**
	 * 解析包头信息
	 */
	@Override
	public ClearSearchGetEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		ClearSearchGetEntity entity=JsonUtil.parseJsonToObject(ClearSearchGetEntity.class, asyncMsg.getContent());
		return entity;
	}

	/**
	 * 获取清仓货区Service类
	 */
	@Override
	public List<ClearSearchGetResult> service(AsyncMsg asyncMsg,
			ClearSearchGetEntity param) throws PdaBusiException {
		this.validate(asyncMsg,param);
		List<ClearSearchGetResult> tasks = new ArrayList<ClearSearchGetResult>();
		List<GoodsAreaEntity> res = null;
		try {
			//调用FOSS接口
			res=findGoodsAdminService.qureyGoodsAreaByCode(param.getBigDeptCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		if(res != null && !res.isEmpty()){
			for (GoodsAreaEntity entity : res) {
				ClearSearchGetResult model = new ClearSearchGetResult();
				model.setZoneCode(entity.getGoodsAreaCode()); //库区编码 
				model.setZoneName(entity.getGoodsAreaName()); //库区名称
				tasks.add(model);
			}
		}
		return tasks;
	}
   /**
    * 参数验证
    * @param asyncMsg
    * @param entity
    * @throws ArgumentInvalidException
    */
	private void validate(AsyncMsg asyncMsg,ClearSearchGetEntity entity) throws ArgumentInvalidException {
		 // 检验非空
		 Argument.notNull(entity, "entity");
		 // 部门编码非空
	     Argument.notNull(entity.getBigDeptCode(),"entity.bigDeptCode");
	}
	/**
	 * 操作类型
	 */
	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_SEARCH_GET.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setFindGoodsAdminService(
			IFindGoodsAdminService findGoodsAdminService) {
		this.findGoodsAdminService = findGoodsAdminService;
	}
	
}
