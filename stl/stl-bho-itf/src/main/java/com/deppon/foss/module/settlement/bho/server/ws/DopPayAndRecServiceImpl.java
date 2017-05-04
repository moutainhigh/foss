package com.deppon.foss.module.settlement.bho.server.ws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONArray;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.bho.server.inter.DopPayAndRecService;
import com.deppon.foss.module.settlement.common.api.server.service.IDopBillPayableReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.domain.DopBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 家装应收单应付单
 * 
 * @ClassName: DopPayableReceiveServiceImpl
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-12-11 下午6:54:55
 */
public class DopPayAndRecServiceImpl implements DopPayAndRecService {
	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager
			.getLogger(DopPayAndRecServiceImpl.class);

	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;

	/**
	 * 注入Service
	 */
	private IDopBillPayableReceivableService dopBillPayableReceivableService;
	
	/**
	 * 生成家装应收单应付单
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	@Override
	public String buildDopPayAndRec(String payAndRec) {
		logger.info("DOP生成应收应付开始。");
		List<DopBillEntity> dops = new ArrayList<DopBillEntity>();
		DopBillEntity db = new DopBillEntity();
		try {
			// 将JSON字符串转为集合
			JSONArray ja = JSONArray.fromObject(payAndRec);
			Collection<DopBillEntity> coll = JSONArray.toCollection(ja,DopBillEntity.class);
			List<DopBillEntity> list = new ArrayList<DopBillEntity>(coll);
			// 获取运单号
			String waybillNo = list.get(0).getWayBillNo();
			// 客户编码
			String customerCode = list.get(0).getCustomerCode();
			// 设置
			db.setWayBillNo(waybillNo);
			db.setCustomerCode(customerCode);
			int count = dopBillPayableReceivableService.addDopPayAndRec(list);
			if (count == 0) {
				throw new SettlementException("生成应收应付单条数为零。");
			}
			db.setIsSuccess("true");
			db.setErrorMsg("推送成功");
		} catch (SettlementException e) {
			db.setIsSuccess("false");
			db.setErrorMsg(e.getErrorCode());
			logger.info("DOP生成应收应付异常。" + e.getErrorCode());
		} catch (Exception e1) {
			db.setIsSuccess("false");
			db.setErrorMsg(e1.getMessage());
			logger.info("DOP生成应收应付异常。" + e1.getMessage());
		} finally {
			dops.add(db);
			response.addHeader("ESB-ResultCode", "1");
			logger.info("DOP生成应收应付结束");
			return JSONArray.fromObject(dops).toString();
		}
	}
	
	/**
	 * 反审核
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	@Override
	public String cancelDopPayAndRec(String payAndRec) {
		logger.info("DOP反审核应收应付开始。");
		List<DopBillEntity> dops = new ArrayList<DopBillEntity>();
		DopBillEntity db = new DopBillEntity();
		try {
			// 将JSON字符串转为集合
			JSONArray ja = JSONArray.fromObject(payAndRec);
			Collection<DopBillEntity> coll = JSONArray.toCollection(ja,DopBillEntity.class);
			List<DopBillEntity> list = new ArrayList<DopBillEntity>(coll);
			// 获取运单号
			String waybillNo = list.get(0).getWayBillNo();
			// 客户编码
			String customerCode = list.get(0).getCustomerCode();
			// 设置
			db.setWayBillNo(waybillNo);
			db.setCustomerCode(customerCode);
			int count = dopBillPayableReceivableService.cacleDopPayAndRec(list);
			if (count == 0) {
				throw new SettlementException("反审核条数为零。");
			}
			db.setIsSuccess("true");
			db.setErrorMsg("反审核成功！");
		} catch (SettlementException e) {
			db.setIsSuccess("false");
			db.setErrorMsg(e.getErrorCode());
			logger.info("DOP反审核应收单应付单异常。" + e.getErrorCode());
		} catch (Exception e1) {
			db.setIsSuccess("false");
			db.setErrorMsg(e1.getMessage());
			logger.info("DOP反审核应收单应付单异常。" + e1.getMessage());
		} finally {
			dops.add(db);
			response.addHeader("ESB-ResultCode", "1");
			logger.info("DOP反审核应收单应付单结束");
			return JSONArray.fromObject(dops).toString();
		}
	}

	public void setDopBillPayableReceivableService(
			IDopBillPayableReceivableService dopBillPayableReceivableService) {
		this.dopBillPayableReceivableService = dopBillPayableReceivableService;
	}
}
