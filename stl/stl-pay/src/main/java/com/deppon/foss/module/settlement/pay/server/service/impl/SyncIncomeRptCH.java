/* Copyright ©2014 www.deppon.com. All rights reserved. */
package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.fins.inter.foss.service.FossCashDataInfo;
import com.deppon.fins.inter.foss.service.FossCashDataRequest;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.pay.api.server.dao.ISyncIncomeRptCHDao;
import com.deppon.foss.module.settlement.pay.api.server.service.ISyncIncomeRptCH;
import com.deppon.foss.module.settlement.pay.api.shared.dto.FossCashDataDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;

/**
 * 同步现金缴款报表到财务自助
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-28 上午11:27:16,content: </p>
 * @author 105762
 * @date 2014-7-28 上午11:27:16
 * @since
 * @version
 */
public class SyncIncomeRptCH implements ISyncIncomeRptCH {
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncIncomeRptCH.class);

	/**
	 * dao
	 */
	private ISyncIncomeRptCHDao syncIncomeRptCHDao;

	/**
	 * <p>同步现金缴款报表到财务自助</p> 
	 * @author 105762
	 * @throws ESBException 
	 * @date 2014-7-28 上午11:28:07
	 * @see
	 */
	public void process() throws ESBException {
		LOGGER.info("同步现金缴款报表到财务自助 开始...");
		// 查询昨天的缴款数据
		List<FossCashDataDto> list = syncIncomeRptCHDao.queryIncomeRptCHByDateStr(getYesterdayDateStr());

		if (CollectionUtils.isEmpty(list)) {
			throw new ESBBusinessException("未查询到现金缴款数据");
		}

    // 调用ESB同步现金缴款接口
    int i = 0;
    for (; i < list.size() / SettlementConstants.ESB_FOSS2FOSS_CASH_DATA_BATCH_SIZE; i++) {
        LOGGER.info(String.format("同步现金缴款报表到财务自助 发送第 %d 批数据:%d - %d", 1+i, 1 + i * SettlementConstants.ESB_FOSS2FOSS_CASH_DATA_BATCH_SIZE,
                (i + 1) * SettlementConstants.ESB_FOSS2FOSS_CASH_DATA_BATCH_SIZE));
        sendMessageToEsb(
                list.subList(i * SettlementConstants.ESB_FOSS2FOSS_CASH_DATA_BATCH_SIZE, (i + 1)
                        * SettlementConstants.ESB_FOSS2FOSS_CASH_DATA_BATCH_SIZE), i + 1);
    }
    LOGGER.info(String.format("同步现金缴款报表到财务自助 发送第 %d 批数据:%d - %d", ++i, 1 + list.size() - list.size()
            % SettlementConstants.ESB_FOSS2FOSS_CASH_DATA_BATCH_SIZE, list.size()));
    sendMessageToEsb(list.subList(list.size() - list.size() % SettlementConstants.ESB_FOSS2FOSS_CASH_DATA_BATCH_SIZE, list.size()), i + 1);

    LOGGER.info("同步现金缴款报表到财务自助 结束...");
}

	/**
	 * <p>调用ESB</p> 
	 * @author 105762
	 * @date 2014-8-5 下午4:41:27
	 * @param list
	 * @throws ESBException
	 */
	private static void sendMessageToEsb(List<FossCashDataDto> list, int batchNo) throws ESBException {
		// 临时变量
		FossCashDataRequest request = new FossCashDataRequest();
		List<FossCashDataInfo> infoList = request.getFossCashDataInfo();
		for (FossCashDataDto dto : list) {
			FossCashDataInfo dataInfo = new FossCashDataInfo();
			dataInfo.setCashAmt(dto.getCashAmt());
			dataInfo.setCashDate(dto.getCashDate());
			dataInfo.setDeptNum(dto.getDeptNum());

			infoList.add(dataInfo);
		}

		//调用ESB
		ESBJMSAccessor.asynReqeust(createHeader(batchNo), request);
	}

	/**
	 * <p>获取Header</p> 
	 * @author 105762
	 * @date 2014-8-5 下午3:22:49
	 * @return AccessHeader
	 */
	private static AccessHeader createHeader(int batchNo) {
		AccessHeader header = new AccessHeader();
		header.setBusinessId(getYesterdayDateStr() + "-" + batchNo);
		header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2FOSS_CASH_DATA);
		header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
		return header;
	}

	/**
	 * <p>获取昨日日期字符串</p>
	 * sample: 2014-07-01
	 * @author 105762
	 * @date 2014-7-28 下午4:21:57
	 * @return 昨日日期字符串 如"2014-08-04"
	 */
	private static String getYesterdayDateStr() {
		Date yesterday = DateUtils.addDayToDate(new Date(), -1);
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		return sm.format(yesterday);
	}

	/**
	  * @return  the syncIncomeRptCHDao
	 */
	public ISyncIncomeRptCHDao getSyncIncomeRptCHDao() {
		return syncIncomeRptCHDao;
	}

	/**
	 * @param syncIncomeRptCHDao the syncIncomeRptCHDao to set
	 */
	public void setSyncIncomeRptCHDao(ISyncIncomeRptCHDao syncIncomeRptCHDao) {
		this.syncIncomeRptCHDao = syncIncomeRptCHDao;
	}

}