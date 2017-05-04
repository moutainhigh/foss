/**
 * company : com.deppon poroject : foss结算 copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author : panshiqi (309613)
 * @date : 2016年3月2日 下午9:09:30
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAutoPayPtpService;
import com.deppon.foss.module.settlement.pay.api.server.service.IWscWayBillManageService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.WscWayBillManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.WscWayBillManageVo;
import com.deppon.foss.util.DateUtils;

/**
 * 
* @description: 待刷卡运单管理
* @className: WscWayBillManageAction
* 
* @author panshiqi 309613
* @date 2016年3月2日 下午9:10:05 
*
 */
public class TestAction extends AbstractAction {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(TestAction.class);

	/**
	 *注入service 
	 */
	private IBillAutoPayPtpService billAutoPayPtpService;


	@JSON
	public String testMethod() {
		logger.info("请求testMethod开始！");
		try {
			billAutoPayPtpService.autoPaytoPtp();
			// 返回成功
			logger.info("请求testMethod结束！");
			return returnSuccess();
		} catch (BusinessException e) {
			logger.error(e.getErrorCode(), e);
			return returnError(e);
		}
	}


	public IBillAutoPayPtpService getBillAutoPayPtpService() {
		return billAutoPayPtpService;
	}


	public void setBillAutoPayPtpService(
			IBillAutoPayPtpService billAutoPayPtpService) {
		this.billAutoPayPtpService = billAutoPayPtpService;
	}

	
}
