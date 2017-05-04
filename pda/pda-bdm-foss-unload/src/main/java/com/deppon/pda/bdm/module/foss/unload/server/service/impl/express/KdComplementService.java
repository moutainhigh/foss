package com.deppon.pda.bdm.module.foss.unload.server.service.impl.express;

//package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAComplementService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAComplementDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.kuaidi.QryComplementReqModel;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.kuaidi.QryComplementResModel;

/**
 * 查询补码结果接口
 * 
 * @author wenwuneng
 * @version 1.0
 * @created 2013年7月23日14:51:12
 */
public class KdComplementService implements IBusinessService<List<QryComplementResModel>, QryComplementReqModel> {
	private static final Log LOG = LogFactory.getLog(KdComplementService.class);
	private IPDAComplementService pdaComplementService;

	@Override
	public QryComplementReqModel parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		QryComplementReqModel qryComplementReqModel = JsonUtil.parseJsonToObject(QryComplementReqModel.class, asyncMsg.getContent());
		return qryComplementReqModel;
	}

	@Transactional
	@Override
	public List<QryComplementResModel> service(AsyncMsg asyncMsg, QryComplementReqModel param) throws PdaBusiException {
		LOG.debug("=============查询补码结果 start====================");
		// this.validateBusinessData(param);
		List<QryComplementResModel> resList = null;
		try {
			Date date = param.getQueryStartTime();
			boolean paramBoolean = param.isQueryType();
			String wblCode = param.getWblCode();
			if (wblCode != null && !wblCode.equals("")) {// 运单号不等于空
				date = null;
			} else {
				if (date == null) {
					System.out.println(date);
					Calendar canlendar = Calendar.getInstance(); // java.util包
					canlendar.add(Calendar.DATE, -3); // 日期减 如果不够减会将月变动
					date = canlendar.getTime();
				}
			}
			// 调用FOSS接口
			List<PDAComplementDto> pdaComplementDtos = pdaComplementService.queryComplement(date, wblCode, paramBoolean, asyncMsg.getDeptCode());
			// 封装返回参数
			resList = this.getResult(pdaComplementDtos);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		LOG.debug("=============查询补码结果 end====================");
		return resList;
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_COMP_QUERY.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * 参数有效性校验
	 */
	private void validateBusinessData(QryComplementReqModel param) {
		Argument.notNull(param, "QryComplementReqModel");
		Argument.hasText(param.getWblCode(), "QryComplementReqModel.wblCode");
		Argument.isTrue(param.isQueryType(), "QryComplementReqModel.paramBoolean");
	}

	/**
	 * 封装返回实体
	 */

	private List<QryComplementResModel> getResult(List<PDAComplementDto> pdaComplementDtos) {
		List<QryComplementResModel> resList = new ArrayList<QryComplementResModel>();
		if (pdaComplementDtos == null || pdaComplementDtos.isEmpty()) {
			return resList;
		}
		for (PDAComplementDto d : pdaComplementDtos) {
			QryComplementResModel complementRes = new QryComplementResModel();
			complementRes.setWblCode(d.getWayBillNo());// 运单号
			complementRes.setTargetOrgCode(d.getTargetOrgCode());// 目的部门编码
			complementRes.setDeryCrgAddress(d.getReceiveCustomerAddress());// 收货地址
			complementRes.setComplementDate(d.getComplementTime());// 补码时间
			complementRes.setReachOrgCode(d.getReachOrgCode());// 提货网点编码
			complementRes.setReachOrgName(d.getReachOrgName());// 提货网点名称
			complementRes.setIsBeAddCode(d.getBeAddCode());// 是否补码
			complementRes.setFinalOutDept(d.getFinalOutDept());// 最终到达外场
			//author:245960 Date 2015-06-09
			complementRes.setBeEWaybill(d.getBeEWaybill());//是否电子面单
			complementRes.setCityShort(d.getSimpleOrgName());//城市简称
			resList.add(complementRes);
		}
		return resList;
	}

	public void setPdaComplementService(IPDAComplementService pdaComplementService) {
		this.pdaComplementService = pdaComplementService;
	}
}
