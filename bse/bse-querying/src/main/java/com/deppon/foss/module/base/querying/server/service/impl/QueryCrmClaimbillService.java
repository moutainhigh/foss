package com.deppon.foss.module.base.querying.server.service.impl;

import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.crm._interface.crmservice.CommException;
import com.deppon.crm._interface.crmservice.FossToCrmService;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.esb.crm.client.QueryClaimbillRequest;
import com.deppon.foss.esb.crm.client.QueryClaimbillResponse;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.querying.server.service.IQueryCrmClaimbillService;
import com.deppon.foss.module.base.querying.shared.define.QueryESBDictionaryConstants;
import com.deppon.foss.module.base.querying.shared.dto.QueryClaimbillResultDto;
import com.deppon.foss.module.base.querying.shared.exception.QueryingBussinessException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 查询CRM理赔信息
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-4-24 下午3:18:53
 */
public class QueryCrmClaimbillService implements IQueryCrmClaimbillService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(QueryCrmClaimbillService.class);

	/**
	 * 调用CRM接口
	 */
	private FossToCrmService fossToCrmService_;

	/**
	 * 根据运单号查询CRM理赔信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-4-24 下午5:08:52
	 */
	@Override
	public QueryClaimbillResultDto queryCrmClaimbillByWaybillNo(String waybillNo) {
		if (StringUtils.isEmpty(waybillNo)) {
			LOGGER.error("运单号不能为空！");
			throw new QueryingBussinessException("运单号不能为空！");
		}
		// 声明ESB表头
		ESBHeader header = new ESBHeader();
		// ESB SERVICE CODE
		header.setEsbServiceCode(QueryESBDictionaryConstants.ESB_FOSS2ESB_QUERY_CLAIMBILL);
		// 与业务相关的字段
		header.setBusinessId(UUIDUtils.getUUID());
		// 请求响应方式
		header.setExchangePattern(QueryESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
		// 版本号
		header.setVersion(QueryESBDictionaryConstants.ESB_HEADER__VERSION);
		// 消息格式
		header.setMessageFormat(QueryESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
		header.setRequestId(UUIDUtils.getUUID());
		// 请求系统
		header.setSourceSystem(QueryESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);

		Holder<ESBHeader> esbHeader = new Holder<ESBHeader>(header);
		// 设置请求参数
		QueryClaimbillRequest request = new QueryClaimbillRequest();
		request.setWaybillNum(waybillNo);

		try {
			// 调用CRM接口，获取查询时结果
			QueryClaimbillResponse response = fossToCrmService_.queryClaimbill(
					request, esbHeader);
			// 声明查询结果集dto
			QueryClaimbillResultDto dto = new QueryClaimbillResultDto();
			BeanUtils.copyProperties(response, dto);
			return dto;
		} catch (CommException e) {
			LOGGER.error("调用CRM查询理赔数据出错" + e.getMessage(), e);
			throw new QueryingBussinessException("调用CRM查询理赔数据出错");
		}
	}

	
	public FossToCrmService getFossToCrmService_() {
		return fossToCrmService_;
	}

	
	public void setFossToCrmService_(FossToCrmService fossToCrmService_) {
		this.fossToCrmService_ = fossToCrmService_;
	}

	 

}
