package com.deppon.foss.module.settlement.vtsitf.server.ws;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsSyncWaybillAndActualService;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillStatusSyncToFossEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.vtsitf.server.inter.IWaybillModifyService;
import com.deppon.foss.module.transfer.common.api.shared.domain.ResponseParameterEntity;

/**
 * @ClassName: UpdatePkpStateAfterCheck 
 * @author &339073 |guojing027@deppon.com.com
 * @date 2016-6-15 下午5:05:51
 */
public class WaybillModifyServiceImpl implements IWaybillModifyService {
	/**
	 * 日志记录
	 */
	private static final Logger logger = LogManager
			.getLogger(WaybillModifyServiceImpl.class);

	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;
	
	/**
	 * 注入接口
	 */
	private IVtsSyncWaybillAndActualService vtsSyncWaybillAndActualService;

	/**
	 * @Title: updatePkpStateAfterCheck
	 * @author： guojing027@deppon.com.com
	 * @date
	 */
	@SuppressWarnings("finally")
	@Override
	@Transactional
	public String updateWaybillStatus(String param) {
		logger.info("修改运单状态开始");
		/**
		 * 返回数据
		 */
		ResponseParameterEntity entity = new ResponseParameterEntity();
		WaybillStatusSyncToFossEntity retEntity = new WaybillStatusSyncToFossEntity();
		try {
			if (StringUtils.isEmpty(param)) {
				throw new SettlementException("数据错误:参数为空!");
			}
			RequestParameterEntity jb = JSONObject.parseObject(param,
					RequestParameterEntity.class);
			
			retEntity = JSONObject.parseObject(
					jb.getRequestEntity().toString(),
					WaybillStatusSyncToFossEntity.class);

			// 业务处理
			int result = vtsSyncWaybillAndActualService
					.syncWaybillUpdateState(retEntity);
			
			if (result != 2) {
				throw new SettlementException("更新条数应为两条...");
			}
			entity.setResultFlag(true);
			entity.setFailureReason("");
		} catch (SettlementException e) {
			entity.setResultFlag(false);
			entity.setFailureReason(e.getErrorCode());
		} catch (BusinessException e1) {
			entity.setResultFlag(false);
			entity.setFailureReason(e1.getMessage());
		} catch (Exception e2) {
			entity.setResultFlag(false);
			entity.setFailureReason(e2.getMessage());
		} finally {
			response.addHeader("ESB-ResultCode", "1");
			return JSONObject.toJSONString(entity);
		}
	}

	/* setter */
	public void setVtsSyncWaybillAndActualService(
			IVtsSyncWaybillAndActualService vtsSyncWaybillAndActualService) {
		this.vtsSyncWaybillAndActualService = vtsSyncWaybillAndActualService;
	}
}
