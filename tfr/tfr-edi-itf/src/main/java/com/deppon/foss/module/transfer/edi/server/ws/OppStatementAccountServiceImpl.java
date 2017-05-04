package com.deppon.foss.module.transfer.edi.server.ws;

import com.deppon.foss.module.settlement.common.api.server.service.IOppStatementService;
import com.deppon.foss.module.settlement.common.api.shared.dto.OppStatementDto;
import com.deppon.foss.module.transfer.edi.server.inter.OppStatementAccountService;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;

/**
 * Created by 302307 on 2016/4/7.
 */
public class OppStatementAccountServiceImpl implements
		OppStatementAccountService {

	private static final Logger LOGGER = Logger
			.getLogger(OppStatementAccountServiceImpl.class);

	// 注入IOppStatementService
	private IOppStatementService oppStatementService;

	@SuppressWarnings("finally")
	@Override
	public @ResponseBody
	String queryStatementOpp(@RequestBody String param, HttpServletResponse resp) {
		LOGGER.info("opp请求对账单数据开始...");
		OppStatementDto result = null;
		try {
			// 将Json字符串转化为实体对象
			JSONObject object = JSONObject.fromObject(param);
			JSONUtils.getMorpherRegistry().registerMorpher(
					new DateMorpher(new String[] { "yyyy-MM-dd",
							"yyyy-MM-dd HH:mm:ss" }));
			OppStatementDto dto = (OppStatementDto) JSONObject.toBean(object,
					OppStatementDto.class);
			result = oppStatementService.queryStatementsByCondition(dto);
			if (null == result) {
				result = new OppStatementDto();
			}
		} catch (Exception e) {
			LOGGER.info("推送opp数据异常！");
		} finally {
			resp.setHeader("ESB-ResultCode", "1");
			String str = JSONObject.fromObject(result).toString();
			try {
				//字符串转码
				str = new String(str.getBytes("UTF-8"), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("转码出错!");
			}
			return str;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public @ResponseBody
	String queryStatementDetailOpp(@RequestBody String param,
			HttpServletResponse resp) {
		LOGGER.info("opp请求对账单明细数据开始...");
		OppStatementDto result = null;
		try {
			// 将Json字符串转化为实体对象
			JSONObject object = JSONObject.fromObject(param);
			OppStatementDto dto = (OppStatementDto) JSONObject.toBean(object,
					OppStatementDto.class);
			result = oppStatementService.queryOppStatementDetailsByNos(dto);
			if (null == result) {
				result = new OppStatementDto();
			}
		} catch (Exception e) {
			LOGGER.info("推送opp数据异常！" + e.getMessage());
		} finally {
			resp.setHeader("ESB-ResultCode", "1");
			String str = JSONObject.fromObject(result).toString();
			try {
				//字符串转码
				str = new String(str.getBytes("UTF-8"), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("转码出错!");
			}
			return str;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public @ResponseBody
	String queryStatementDetailAllOpp(@RequestBody String param,
			HttpServletResponse resp) {
		LOGGER.info("opp请求对导出账单明细数据开始...");
		OppStatementDto result = null;
		try {
			// 将Json字符串转化为实体对象
			JSONObject object = JSONObject.fromObject(param);
			OppStatementDto dto = (OppStatementDto) JSONObject.toBean(object,
					OppStatementDto.class);
			result = oppStatementService.queryOppStatementDetailsAllByNos(dto);
			if (null == result) {
				result = new OppStatementDto();
			}
		} catch (Exception e) {
			LOGGER.info("推送opp数据异常！" + e.getMessage());
		} finally {
			resp.setHeader("ESB-ResultCode", "1");
			String str = JSONObject.fromObject(result).toString();
			try {
				//字符串转码
				str = new String(str.getBytes("UTF-8"), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("转码出错!");
			}
			return str;
		}
	}
	
	/**setter**/
	public void setOppStatementService(IOppStatementService oppStatementService) {
		this.oppStatementService = oppStatementService;
	}

}
