package com.deppon.foss.module.transfer.stock.server.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToOAService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.SalesDeptExpLostConstants;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.ISalesDeptExpLostDao;
import com.deppon.foss.module.transfer.stock.api.server.service.ISalesDeptExpLostService;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.SalesDeptExpLostEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.SalesDeptExpLostOaLogEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.SalesDeptExpLostWaybillSerialNosDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class SalesDeptExpLostService implements ISalesDeptExpLostService {

	private static final Logger LOGGER = Logger
			.getLogger(SalesDeptExpLostService.class);

	private ISalesDeptExpLostDao salesDeptExpLostDao;

	private IStockService stockService;

	private IWaybillManagerService waybillManagerService;

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	@Resource
	private IProductService productService4Dubbo;
	private IProductService productService;

	private ITfrCommonService tfrCommonService;

	private IFOSSToOAService fossToOAService;

	public void setSalesDeptExpLostDao(ISalesDeptExpLostDao salesDeptExpLostDao) {
		this.salesDeptExpLostDao = salesDeptExpLostDao;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setFossToOAService(IFOSSToOAService fossToOAService) {
		this.fossToOAService = fossToOAService;
	}

	/**
	 * @desc 给date加上n天
	 * @param date
	 * @param amount
	 * @return
	 * @date 2014年10月25日 下午4:58:22
	 * @author Ouyang
	 */
	private Date addNDay(Date date, int amount) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, amount);
		return calendar.getTime();
	}

	/**
	 * @desc 获取某时间的0点
	 * @param date
	 * @return
	 * @date 2014年10月31日 下午4:55:15
	 * @author Ouyang
	 */
	private Date getStartTime(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String format = df.format(date);
		try {
			return df.parse(format);
		} catch (ParseException e) {
			//sonar-352203
			LOGGER.info("SalesDeptExpLostService.getStartTime 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
		}
		return new Date();
	}

	/**
	 * @desc 1:先清除历史的0点库存快照 2:保存0点时的库存快照，目前只保留营业部的快递货
	 * @param date统计日期
	 * @date 2014年10月24日 上午10:49:04
	 * @author Ouyang
	 */
	@Override
	public void saveStock0amSnapshot() {
		LOGGER.info("开始清除历史的0点库存快照");
		// 先清除历史的0点库存快照
		salesDeptExpLostDao.truncateStock0amSnapshot();
		LOGGER.info("结束清除历史的0点库存快照");

		LOGGER.info("开始保存0点库存快照的部门编码");
		// 保存0点时的库存快照，目前只保留营业部的快递货
		salesDeptExpLostDao.saveStock0amSnapshot(getStartTime(new Date()));
		LOGGER.info("结束保存0点库存快照的部门编码");
	}

	@Override
	public void generateReport(int threadCount, int threadNo) {
		LOGGER.info("generateReport,threadCount=" + threadCount + "threadNo="
				+ threadNo);

		// 获取当天14点
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, ConstantsNumberSonar.SONAR_NUMBER_14);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date staTime = calendar.getTime();

		LOGGER.info("开始获取0点库存快照的部门编码");
		// 获取0点库存快照的部门编码
		List<String> stockOrgCodes = queryStockOrgCodes(threadCount, threadNo);
		LOGGER.info("结束获取0点库存快照的部门编码");

		LOGGER.info("开始生成快递派送差异报告");
		for (String orgCode : stockOrgCodes) {
			// 生成差异报告
			generateReport(orgCode, staTime);
		}
		LOGGER.info("结束生成快递派送差异报告");

		LOGGER.info("开始快递派送72H差异上报OA丢货");
		for (String orgCode : stockOrgCodes) {
			// 上报OA丢货
			reportOaLost(orgCode);
		}
		LOGGER.info("结束快递派送72H差异上报OA丢货");

		LOGGER.info("开始快递派送72H差异库存少货入库到特殊组织");
		for (String orgCode : stockOrgCodes) {
			// 入库到特殊组织
			migrateStock2SpecialOrg(orgCode);
		}
		LOGGER.info("结束快递派送72H差异库存少货入库到特殊组织");
	}

	/**
	 * @desc 获取0点库存快照的部门编码，按ora_hash(org_code,threadCount) = threadNo分组
	 * @param threadCount
	 *            线程数-1；如启动5个job，则threadCount为4
	 * @param threadNo
	 *            线程号；如启动5个job，则threadNo分别为0、1、2、3、4
	 * @return
	 * @date 2014年10月25日 下午4:41:37
	 * @author Ouyang
	 */
	private List<String> queryStockOrgCodes(int threadCount, int threadNo) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("threadCount", threadCount);
		map.put("threadNo", threadNo);
		return salesDeptExpLostDao.queryStockOrgCodes(map);
	}

	/**
	 * @desc 生成差异报告
	 * @param orgCode部门编码
	 * @param staTime统计时间
	 * @date 2014年10月27日 上午9:28:07
	 * @author Ouyang
	 */
	private void generateReport(String orgCode, Date staTime) {
		// 将快递派送差异报告插入至历史表
		salesDeptExpLostDao.insertSalesDeptExpLost2His(orgCode);
		// 删除快递派送差异报告
		salesDeptExpLostDao.deleteSalesDeptExpLost(orgCode);

		// 获取1，2，3天前的14点
		Date staTime1DayAgo = addNDay(staTime, -1);
		Date staTime2DayAgo = addNDay(staTime, -2);
		Date staTime3DayAgo = addNDay(staTime, -3);

		// 生成24H差异报告
		int count = generateReport(SalesDeptExpLostConstants.status_24H,
				orgCode, staTime, staTime1DayAgo, staTime);

		if (count > 0) {
			// 如果有24H差异生成，再从24H差异的运单和流水号中看是否有满足48H差异的运单，将状态改为48H
			count = generateReport(SalesDeptExpLostConstants.status_48H,
					orgCode, staTime, staTime2DayAgo, staTime1DayAgo);
		}

		if (count > 0) {
			// 如果有48H差异生成，再从48H差异的运单和流水号中看是否有满足72H差异的运单，将状态改为72H
			count = generateReport(SalesDeptExpLostConstants.status_72H,
					orgCode, staTime, staTime3DayAgo, staTime2DayAgo);
		}
	}

	/**
	 * @desc 生成差异报告
	 * @param status差异状态
	 * @param orgCode部门编码
	 * @param staTime统计时间
	 * @param beginTime操作开始时间
	 * @param endTime操作结束时间
	 * @return
	 * @date 2014年10月25日 下午8:43:05
	 * @author Ouyang
	 */
	private int generateReport(String status, String orgCode, Date staTime,
			Date beginTime, Date endTime) {
		// 将参数封装
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("staTime", staTime);
		map.put("orgCode", orgCode);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);

		// 生成24H差异报告
		if (SalesDeptExpLostConstants.status_24H.equals(status)) {
			return salesDeptExpLostDao.insertReport24H(map);
		}

		// 之前的差异状态；48H对应24H，72H对应48H
		String beforeStatus = SalesDeptExpLostConstants.status_24H;
		String nextStatus = SalesDeptExpLostConstants.status_72H;

		if (SalesDeptExpLostConstants.status_72H.equals(status)) {
			beforeStatus = SalesDeptExpLostConstants.status_48H;
		}

		map.put("beforeStatus", beforeStatus);
		map.put("nextStatus", nextStatus);

		// 将24H/48H差异且满足48H/72H存差异的报告信息改为48H/72H差异
		return salesDeptExpLostDao.updateStatus48or72H(map);
	}

	/**
	 * @desc 上报oa丢货
	 * @param orgCode部门编码
	 * @return
	 * @date 2014年10月25日 下午9:11:24
	 * @author Ouyang
	 */
	private void reportOaLost(String orgCode) {
		// 查询72H差异报告
		SalesDeptExpLostEntity params = new SalesDeptExpLostEntity();
		params.setOrgCode(orgCode);
		params.setStatus(SalesDeptExpLostConstants.status_72H);
		List<SalesDeptExpLostEntity> reports72H = queryReportWaybillNo(params);

		for (SalesDeptExpLostEntity report : reports72H) {
			// 运单号
			String waybillNo = report.getWaybillNo();

			// 调用oa上报丢货接口参数封装
			OaReportClearless oaReportClearless = encapsulateOaLostParams(report);
			// 调用oa接口上报丢货
			ResponseDto result = new ResponseDto();

			// 此次是否成功上报标识
			String reported = FossConstants.NO;

			try {
				result = fossToOAService.reportLessGoods(oaReportClearless);
				// false表示oa接口成功返回；包含存在业务异常的情况，此时错误信息不为空
				boolean isException = result.getIsException();
				// oa接口错误信息
				String message = result.getMessage();

				// 如果oa接口成功返回，且错误信息为空则表示成功上报
				if (!isException && StringUtils.isEmpty(message)) {
					SalesDeptExpLostEntity info = new SalesDeptExpLostEntity();
					info.setOrgCode(orgCode);
					info.setWaybillNo(waybillNo);
					info.setStatus(SalesDeptExpLostConstants.status_72H);
					info.setReported(FossConstants.YES);
					// 将上报状态改为Y
					salesDeptExpLostDao.update72hReproted(info);

					reported = FossConstants.YES;
				}
			} catch (Exception e) {
				LOGGER.info("调用oa接口上报丢货失败;waybillNo=" + waybillNo + ",orgCode="
						+ orgCode, e);

				result.setMessage(e.toString());
			}

			// 记录OA上报日志
			reportLog(report, result, reported);
		}
	}

	/**
	 * @desc 将72H状态，且成功上报oa丢货的运单库存少货入库到特殊组织
	 * @param orgCode
	 * @return
	 * @date 2014年10月25日 下午9:11:24
	 * @author Ouyang
	 */
	private void migrateStock2SpecialOrg(String orgCode) {
		// 查询72H差异报告
		SalesDeptExpLostEntity reportParams = new SalesDeptExpLostEntity();
		reportParams.setOrgCode(orgCode);
		reportParams.setStatus(SalesDeptExpLostConstants.status_72H);
		reportParams.setReported(FossConstants.YES);
		List<SalesDeptExpLostEntity> reports72H = salesDeptExpLostDao.queryReport(reportParams);
		//入库时候  推送到ptp的运单信息
		HashMap<String,String> pushMapToPtp = new HashMap<String,String>();
		for (SalesDeptExpLostEntity report : reports72H) {
			// 封装入库参数
			InOutStockEntity stockParams = new InOutStockEntity();
			stockParams.setWaybillNO(report.getWaybillNo());
			stockParams.setSerialNO(report.getSerialNo());
			Date date = new Date();
			stockParams.setInOutStockTime(date);
			stockParams.setScanTime(date);
			stockParams.setOperatorCode("SalesDeptExpLostService");
			stockParams.setOperatorName("快递派送丢货差异报告");
			stockParams.setDeviceType(StockConstants.PC_DEVICE_TYPE);
			stockParams.setOrgCode(orgCode);
			stockParams.setInOutStockType(StockConstants.LOSE_GOODS_IN_STOCK_TYPE);
			//pushMapToPtp为空  或者 pushMapToPtp根据运单号获取的值为空，那么表示运单为推送过，添加到map值为N
			if(StringUtils.isEmpty(pushMapToPtp.get(stockParams.getWaybillNO()))){
				pushMapToPtp.put(stockParams.getWaybillNO(), "N");
				//map中查询到此运单的值 说明推送过状态变为Y
			}else if(StringUtils.equals("N",pushMapToPtp.get(stockParams.getWaybillNO()))){
				pushMapToPtp.put(stockParams.getWaybillNO(), "Y");
			}
			stockParams.setPushMapToPtp(pushMapToPtp);
			// 调用库存接口，少货入库到特殊组织
			int result = 0;
			try {
				result = stockService.inStock(stockParams,
						StockConstants.CONFIRM, StockConstants.CONFIRM, false);

				if (result != 1) {
					LOGGER.info("少货入库到特殊组织失败,参数为" + report);
				}

			} catch (Exception e) {
				LOGGER.info("少货入库到特殊组织失败,参数为" + report, e);
			}
		}
	}

	/**
	 * @desc 记录OA上报日志
	 * @param orgCode
	 * @param report
	 * @param result
	 * @param reported
	 * @date 2014年12月4日 下午2:52:38
	 * @author Ouyang
	 */
	private void reportLog(SalesDeptExpLostEntity report, ResponseDto result,
			String reported) {

		// 记录上报日志
		SalesDeptExpLostOaLogEntity oaLog = new SalesDeptExpLostOaLogEntity();
		oaLog.setId(UUIDUtils.getUUID());
		oaLog.setOrgCode(report.getOrgCode());
		oaLog.setOrgName(report.getOrgName());
		oaLog.setWaybillNo(report.getWaybillNo());
		oaLog.setReported(reported);
		oaLog.setStaTime(report.getStaTime());
		// oa差错编号；当某单已经上报过丢货时，这里返回的是原来的差错编号，
		// 且oa接口错误信息中包含“OA已上报丢货，请在OA上报丢货找到”字样
		oaLog.setOaErrorNo(result.getErrorsNo());
		oaLog.setOperateTime(new Date());
		oaLog.setMessage(StringUtils.left(result.getMessage(), ConstantsNumberSonar.SONAR_NUMBER_300));
		salesDeptExpLostDao.insertReportOaLog(oaLog);
	}

	/**
	 * @desc 封装上报oa丢货参数，完全参照清仓丢货上报方法{@see StReportService#reportOALessGoods}
	 * @param waybillNo
	 * @param orgCode
	 * @return
	 * @date 2014年10月28日 下午4:52:01
	 * @author Ouyang
	 */
	private OaReportClearless encapsulateOaLostParams(
			SalesDeptExpLostEntity report) {

		String waybillNo = report.getWaybillNo();
		String orgCode = report.getOrgCode();

		// 返回结果
		OaReportClearless result = new OaReportClearless();

		// 获取运单信息
		WaybillEntity waybill = waybillManagerService
				.queryWaybillBasicByNo(waybillNo);

		// 运单号
		result.setWayBillId(waybill.getWaybillNo());
		// 上报时间
		result.setReportTime(Calendar.getInstance().getTime());
		// 运输类型
		if (StringUtils.isEmpty(waybill.getTransportType())) {
			// 运输类型为空时，根据运输性质查询对应的一级产品
			ProductEntity entity = productService4Dubbo.getProductLele(
					waybill.getProductCode(), null, 1);
			if (entity != null) {
				result.setTransportType(entity.getName());
			}
		} else {
			result.setTransportType(productService4Dubbo.getProductByCache(
					waybill.getTransportType(), null).getName());
		}
		// 返单类型
		result.setReturnBillType(tfrCommonService.queryDictNameByCode(
				DictionaryConstants.RETURN_BILL_TYPE,
				waybill.getReturnBillType()));
		// 托运人
		result.setShipper(waybill.getDeliveryCustomerContact());
		// 产品类型
		result.setTransportProduct(productService4Dubbo.getProductByCache(
				waybill.getProductCode(), null).getName());
		// 配载类型
		result.setStowageType("");
		// 收获人电话
		if (StringUtils.isNotEmpty(waybill.getReceiveCustomerPhone())) {
			result.setReceiverTel(waybill.getReceiveCustomerPhone());
		} else {
			result.setReceiverTel(waybill.getReceiveCustomerMobilephone());
		}
		// 提货方式
		result.setGroupSendFlag(tfrCommonService.queryDictNameByCode(
				DictionaryConstants.PICKUP_GOODS, waybill.getReceiveMethod()));
		// 储运事项
		result.setRemark(waybill.getTransportationRemark());
		// 重量
		result.setWeight(waybill.getGoodsWeightTotal() != null ? waybill
				.getGoodsWeightTotal().doubleValue() : Double.valueOf(0));
		// 体积
		result.setVloume(waybill.getGoodsVolumeTotal() != null ? waybill
				.getGoodsVolumeTotal().doubleValue() : Double.valueOf(0));
		// 品名
		result.setGoods(waybill.getGoodsName());
		// 发货时间取运单的开单时间
		result.setSendTime(DateUtils.convert(waybill.getCreateTime(),
				DateUtils.DATE_TIME_FORMAT));
		// 目的站
		result.setDestination(waybill.getTargetOrgCode());
		// 收获人
		result.setReceiver(waybill.getReceiveCustomerName());

		// 收货部门
		OrgAdministrativeInfoEntity recieveEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(waybill.getReceiveOrgCode());
		if (recieveEntity != null) {
			result.setReceivingDeptID(recieveEntity.getUnifiedCode());
			result.setReceivingDept(recieveEntity.getName());
		}
		// 付款方式
		result.setPayType(tfrCommonService.queryDictNameByCode(
				DictionaryConstants.SETTLEMENT__PAYMENT_TYPE,
				waybill.getPaidMethod()));
		// 保险金额
		if (waybill.getInsuranceFee() == null) {
			result.setInsuranceMoney(new BigDecimal(0));
		} else {
			result.setInsuranceMoney(waybill.getInsuranceFee());
		}
		// 包装
		result.setGoodsPacking(waybill.getGoodsPackage());
		// 运费总额
		result.setTotal(waybill.getTotalFee());
		// 运单件数
		result.setGoodsCount(waybill.getGoodsQtyTotal());
		// 少货类型，快递派送丢货差异报告需求规定按清仓少货类型上报
		result.setLostGoodsType("清仓少货");
		// 查询部门信息
		OrgAdministrativeInfoEntity currentOrg = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(orgCode);
		// 责任部门名称
		result.setResponsibleDept(currentOrg.getName());
		// 责任部门标杆编码
		result.setResponsibleDeptId(currentOrg.getUnifiedCode());
		// 上报人工号，取营业部负责人
		result.setUserId(currentOrg.getPrincipalNo());

		// 责任事业部
		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_DIVISION);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(orgCode, bizTypesList);
		result.setFinalSysCode(orgAdministrativeInfoEntity != null ? orgAdministrativeInfoEntity
				.getName() : "");

		// 开单部门
		OrgAdministrativeInfoEntity createEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(waybill.getCreateOrgCode());
		if (createEntity != null) {
			result.setSheetDeptId(createEntity.getUnifiedCode());
			result.setSheetDept(createEntity.getName());
		}
		// 收货人
		result.setReceiver(waybill.getReceiveCustomerContact());
		// 交接单，对于丢货上报来说不重要，这里就放运单号
		result.setReplayBill(waybillNo);
		// 发货客户编码
		result.setDeliveryCustomerCode(waybill.getDeliveryCustomerCode());
		// 收货客户编码
		result.setReceiveCustomerCode(waybill.getReceiveCustomerCode());

		// 查询差异流水号集合
		List<SalesDeptExpLostWaybillSerialNosDto> serialNos = queryReportSerialNo(report);

		// 少货件数
		result.setNogoodscount(serialNos.size());

		StringBuilder sb = new StringBuilder();
		for (SalesDeptExpLostWaybillSerialNosDto str : serialNos) {
			sb.append(str.getSerialNo() + ",");
		}

		String serialNoList = "";

		if (sb.length() > 1) {
			serialNoList = sb.substring(0, sb.length() - 1);
		}
		// 差异流水号
		result.setSerialNoList(serialNoList);
		// OA表设计中长度2000，事件经过最多放置650，故此处做处理
		String event = "快递派送丢货差异报告上报,包含件：" + serialNoList;
		if (event.length() > ConstantsNumberSonar.SONAR_NUMBER_650) {
			event = event.substring(0, ConstantsNumberSonar.SONAR_NUMBER_650) + "....";
		}
		result.setEventReport(event);

		return result;
	}

	/**
	 * @desc 查询报告
	 * @param info查询条件
	 * @param start
	 * @param limit
	 * @return
	 * @date 2014年10月27日 上午10:00:07
	 * @author Ouyang
	 */
	@Override
	public List<SalesDeptExpLostEntity> queryReportWaybillNo(
			SalesDeptExpLostEntity info) {
		return salesDeptExpLostDao.queryReportWaybillNo(info);
	}

	/**
	 * @desc 分页查询报告
	 * @param info查询条件
	 * @param start
	 * @param limit
	 * @return
	 * @date 2014年10月27日 上午10:00:07
	 * @author Ouyang
	 */
	@Override
	public List<SalesDeptExpLostEntity> queryReportWaybillNoPaging(
			SalesDeptExpLostEntity info, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return salesDeptExpLostDao.queryReportWaybillNoPaging(info, rowBounds);
	}

	/**
	 * @desc 查询报告数量
	 * @param info
	 * @return
	 * @date 2014年11月19日 上午10:27:27
	 * @author Ouyang
	 */
	@Override
	public Long queryReportWaybillNoCnt(SalesDeptExpLostEntity info) {
		return salesDeptExpLostDao.queryReportWaybillNoCnt(info);
	}

	/**
	 * @desc 查询对应运单的流水号
	 * @param info
	 * @return
	 * @date 2014年10月27日 上午10:31:52
	 * @author Ouyang
	 */
	@Override
	public List<SalesDeptExpLostWaybillSerialNosDto> queryReportSerialNo(
			SalesDeptExpLostEntity info) {
		return salesDeptExpLostDao.queryReportSerialNo(info);
	}

	/**
	 * @desc 导出差异报告
	 * @param info
	 * @return
	 * @date 2014年10月27日 上午10:09:25
	 * @author Ouyang
	 */
	@Override
	public ExportResource exportReport(SalesDeptExpLostEntity info) {
		List<SalesDeptExpLostEntity> reports = salesDeptExpLostDao
				.queryReport(info);

		if (CollectionUtils.isEmpty(reports)) {
			throw new TfrBusinessException("没有符合条件的记录。");
		}

		List<List<String>> resultList = new ArrayList<List<String>>();

		for (SalesDeptExpLostEntity item : reports) {
			List<String> result = new ArrayList<String>();

			// 部门名称
			result.add(item.getOrgName());
			// 运单号
			result.add(item.getWaybillNo());
			// 流水号
			result.add(item.getSerialNo());
			// 差异状态
			result.add(item.getStatus());
			// 是否上报
			result.add(FossConstants.YES.equals(item.getReported()) ? "是" : "否");
			// 日期
			Date staTime = item.getStaTime();
			result.add(String.format("%1$tF %2$tT", staTime, staTime));

			resultList.add(result);
		}

		ExportResource sheet = new ExportResource();
		sheet.setHeads(new String[] { "部门", "运单号", "流水号", "差异状态", "是否已上报丢货",
				"报告生成时间" });
		sheet.setRowList(resultList);
		return sheet;
	}
}
