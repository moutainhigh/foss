package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivesheetDeliverDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaReceiveGoodsDto;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDriverCollectionRptDEntityDao;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDriverCollectionRptEntityDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IPDAPayInReportBillCreateService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DriverCollectionRptDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * PDA缴款报表生成服务
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-18 下午6:40:44
 */
public class PDAPayInReportBillCreateService implements
		IPDAPayInReportBillCreateService {
	/**
	 * 获取日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(PDAPayInReportBillCreateService.class);
	/**
	 * 声明起始和结束日期差额最大30天
	 */
	private static final int DATE_LIMIT_DAYS_MONTH = 30;

	/**
	 * 注入PDA缴款报表DAO
	 */
	private IDriverCollectionRptEntityDao driverCollectionRptEntityDao;
	/**
	 * 注入司机缴款报表明细DAO
	 */
	private IDriverCollectionRptDEntityDao driverCollectionRptDEntityDao;
	/**
	 * 注入接送货接货接口
	 */
	private IWaybillRfcService waybillRfcService;
	/**
	 * 注入接送货送货接口
	 */
	private IArriveSheetManngerService arriveSheetManngerService;

	/**
	 * 注入结算生成
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 查询PDA司机接送货信息
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param 查询条件dto
	 *            ,当前登录信息
	 * @date 2012-12-18 下午6:40:44
	 * @return 接送货信息
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPDAPayInReportBillCreateService#queryReceiptSendGoodsInfo()
	 */
	@Override
	public DriverCollectionRptDto queryReceiptSendGoodsInfo(
			DriverCollectionRptDto dto, CurrentInfo currentInfo) {
		logger.info("查询service开始");
		// 判断查询参数dto
		if (dto == null) {
			throw new SettlementException("传入查询参数不能为空！");
		}
		//设置报表结束日期
		dto.setReportEndDate(DateUtils.getStartDatetime(dto.getReportEndDate(), 1));
		// 调用接货接口获取接货信息
		List<PdaReceiveGoodsDto> receiveList = new ArrayList<PdaReceiveGoodsDto>();
		try {
			// 调用送货接口获取接货信息
			receiveList = waybillRfcService.queryPdaExpressReceiveGoodsDtoByDate(
					dto.getReportBeginDate(),dto.getReportEndDate(), dto.getDriverCode());
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			throw new SettlementException("调用接货接口报错");
		}

		// 调用送货接口获取送货信息
		List<ArrivesheetDeliverDto> deliverList = new ArrayList<ArrivesheetDeliverDto>();
		// 声明查询送货接口dto参数
		ArrivesheetDeliverDto deliverDto = new ArrivesheetDeliverDto();
		deliverDto.setDriverCode(dto.getDriverCode());// 声明司机编码
		deliverDto.setOperateBeginTime(dto.getReportBeginDate());// 声明查询起始时间
		deliverDto.setOperateEndTime(dto.getReportEndDate());// 声明查询结束时间
		try {
			// 调用送货接口信息
			deliverList = arriveSheetManngerService
					.queryExpressArriveSheetByDriverCode(deliverDto);
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			throw new SettlementException("调用送货接口报错");
		}
		// 组装前台要用的DTO
		DriverCollectionRptDto resultDto = convertToDto(receiveList,
				deliverList, currentInfo);
		resultDto.getEntity().setDriverCode(dto.getDriverCode());// 此处设置司机编码
		resultDto.getEntity().setDriverName(dto.getDriverName());// 此处设置司机名称
		resultDto.getEntity().setVehicleNo(dto.getVehicleNo());// 此处设置车牌号
		// 记录日志
		logger.info("查询service结束");
		// 返回结果集
		return resultDto;
	}

	/**
	 * njs 接货
	 */
	public DriverCollectionRptDto queryReceiptGoodsInfo(
			DriverCollectionRptDto dto, CurrentInfo currentInfo) {
		logger.info("查询service开始");
		final int numberOfThirtyOne = -31;
		// 获取当前日期
		Date nowDate = new Date();
		// 判断查询参数dto
		if (dto == null) {
			throw new SettlementException("传入查询参数不能为空！");
		}
		// 如果司机编码为空，则抛出异常
		if (StringUtils.isBlank(dto.getDriverCode())) {
			throw new SettlementException("司机编码不能为空！");
		}
		// 获取当前日期前31天的00:00:00
		Date startDate = DateUtils.getStartDatetime(nowDate, numberOfThirtyOne);
		dto.setReportBeginDate(startDate);

		// 获取当前日期
		Date endDate = DateUtils.getStartDatetime(nowDate, 1);
		dto.setReportEndDate(endDate);

		// 设置当前登录部门
		if (StringUtils.isBlank(currentInfo.getCurrentDeptCode())) {
			throw new SettlementException("当前登录部门为空没，请先进行登录！");
		}
		dto.setCreateOrgCode(currentInfo.getCurrentDeptCode());

		// 获取最近一次缴款时间
		Date maxReportDate = driverCollectionRptEntityDao
				.selectMaxReportDate(dto);
		// 如果当期没有缴款，则获取该时间的00:00:00
		if (maxReportDate == null) {
			maxReportDate = startDate;
		}
		// 调用接货接口获取接货信息
		List<PdaReceiveGoodsDto> receiveList = new ArrayList<PdaReceiveGoodsDto>();
		try {
			// 调用送货接口获取接货信息
			receiveList = waybillRfcService.queryPdaReceiveGoodsDto(
					maxReportDate, dto.getDriverCode());
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			throw new SettlementException("调用接货接口报错");
		}

		// 组装前台要用的DTO
		DriverCollectionRptDto resultDto = convertToRecDto(receiveList,
				currentInfo);// 转换接货接口
		resultDto.getEntity().setDriverCode(dto.getDriverCode());// 此处设置司机编码
		resultDto.getEntity().setDriverName(dto.getDriverName());// 此处设置司机名称
		resultDto.getEntity().setVehicleNo(dto.getVehicleNo());// 此处设置车牌号
		resultDto.getEntity().setReportBeginDate(maxReportDate);// 最近一次缴款时间
		resultDto.getEntity().setReportEndDate(nowDate);// 当前时间

		// 记录日志
		logger.info("查询service结束");
		// 返回结果集
		return resultDto;
	}

	/**
	 * njs 接货转换
	 */
	private DriverCollectionRptDto convertToRecDto(
			List<PdaReceiveGoodsDto> receiveList, CurrentInfo currentInfo) {
		// 声明dto
		DriverCollectionRptDto dto = new DriverCollectionRptDto();
		int waybillQtyTotal = 0;// 总票数
		int piecesTotal = 0;// 总件数
		BigDecimal volumeTotal = BigDecimal.ZERO;// 总体积
		BigDecimal weightTotal = BigDecimal.ZERO;// 总重量
		int returnTicketTotal = 0;// 返单总数
		int signwaybillTotal = 0;// 签收单总数
		BigDecimal receiveAmountTotal = BigDecimal.ZERO;// 现金总额
		BigDecimal receivedTotalAmount = BigDecimal.ZERO;// 接货总金额

		// 声明转化后结算前台要用的接货列表
		List<DriverCollectionRptDEntity> receiveDtoList = new ArrayList<DriverCollectionRptDEntity>();

		// 循环接货列表
		for (PdaReceiveGoodsDto receiveDto : receiveList) {
			volumeTotal = volumeTotal.add(receiveDto.getVolume());// 累加总体积
			weightTotal = weightTotal.add(receiveDto.getWeight());// 累加总重量
			receiveAmountTotal = receiveAmountTotal.add(receiveDto
					.getPayAmount());// 累加现金总额
			receivedTotalAmount = receivedTotalAmount.add(receiveDto
					.getPayAmount());// 计算接货总金额
			piecesTotal = piecesTotal + receiveDto.getPieces();// 计算总件数

			// 进行dto转化为结算前台要用的明细实体
			DriverCollectionRptDEntity entity = new DriverCollectionRptDEntity();
			entity.setType(SettlementDictionaryConstants.DRIVER_COLLECTION_RPT_D__TYPE__PICKUP);// 接货
			entity.setWaybillNo(receiveDto.getWaybillNo());// 运单号
			entity.setVehicleNo(receiveDto.getVehicleNo());// 车牌号
			entity.setWeight(receiveDto.getWeight());// 重量
			entity.setVolume(receiveDto.getVolume());// 体积
			entity.setQty(receiveDto.getPieces());// 件数
			entity.setAmount(receiveDto.getPayAmount());// 金额
			// 判断是否签收单
			if (StringUtils.isNotBlank(receiveDto.getIsHaveSignBill())
					&& !WaybillConstants.NOT_RETURN_BILL.equals(receiveDto
							.getIsHaveSignBill())) {
				// 签收单条数+1
				signwaybillTotal = signwaybillTotal + 1;
				entity.setIsSignwaybill(FossConstants.ACTIVE);// 是有签收单
			} else {
				entity.setIsSignwaybill(FossConstants.INACTIVE);// 没有签收单
			}
			// 将转化后的实体放到接货列表中
			receiveDtoList.add(entity);
		}

		// 循环进行数据转化
		DriverCollectionRptEntity rptEntity = new DriverCollectionRptEntity();
		// 总票数
		waybillQtyTotal = receiveList.size();
		rptEntity.setWaybillQtyTotal(waybillQtyTotal);// 设置总票数
		rptEntity.setPiecesTotal(piecesTotal);// 设置总件数
		rptEntity.setVolumeTotal(volumeTotal);// 设置总体积
		rptEntity.setWeightTotal(weightTotal);// 设置总重量
		rptEntity.setReturnTicketTotal(returnTicketTotal);// 设置返单总数
		rptEntity.setSignwaybillTotal(signwaybillTotal);// 设置签收单总数
		rptEntity.setReceiveAmountTotal(receiveAmountTotal);// 现金总额
		rptEntity.setCreateTime(new Date());
		rptEntity.setCreateUserName(currentInfo.getEmpName());// 办理人为当前登录人
		rptEntity.setCreateUserCode(currentInfo.getEmpCode());// 办理人为当前登录人
		dto.setReceivedList(receiveDtoList);// 接货列表
		// 设置接货和送货列表统计明细
		dto.setEntity(rptEntity);
		dto.setReceivedTotalAmount(receivedTotalAmount);// 设置接货总金额
		dto.setReceivedTotalCount((short) receiveList.size());// 设置接货总条数
		dto.setSignwaybillTotal((short) signwaybillTotal);// 设置签收单总数
		dto.setReturnTicketTotal((short) returnTicketTotal);// 设置返单总数
		return dto;
	}

	/**
	 * njs 送货
	 */
	public DriverCollectionRptDto querySendGoodsInfo(
			DriverCollectionRptDto dto, CurrentInfo currentInfo) {
		logger.info("查询service开始");
		final int numberOfThirtyOne = -31;
		// 获取当前日期
		Date nowDate = new Date();
		// 判断查询参数dto
		if (dto == null) {
			throw new SettlementException("传入查询参数不能为空！");
		}
		// 如果司机编码为空，则抛出异常
		if (StringUtils.isBlank(dto.getDriverCode())) {
			throw new SettlementException("司机编码不能为空！");
		}
		// 获取当前日期前31天的00:00:00
		Date startDate = DateUtils.getStartDatetime(nowDate, numberOfThirtyOne);
		dto.setReportBeginDate(startDate);

		// 获取当前日期
		Date endDate = DateUtils.getStartDatetime(nowDate, 1);
		dto.setReportEndDate(endDate);

		// 设置当前登录部门
		if (StringUtils.isBlank(currentInfo.getCurrentDeptCode())) {
			throw new SettlementException("当前登录部门为空没，请先进行登录！");
		}
		dto.setCreateOrgCode(currentInfo.getCurrentDeptCode());

		// 获取最近一次缴款时间
		Date maxReportDate = driverCollectionRptEntityDao
				.selectMaxReportDate(dto);
		// 如果当期没有缴款，则获取该时间的00:00:00
		if (maxReportDate == null) {
			maxReportDate = startDate;
		}

		// 调用送货接口获取送货信息
		List<ArrivesheetDeliverDto> deliverList = new ArrayList<ArrivesheetDeliverDto>();
		// 声明查询送货接口dto参数
		ArrivesheetDeliverDto deliverDto = new ArrivesheetDeliverDto();
		deliverDto.setDriverCode(dto.getDriverCode());// 声明司机编码
		deliverDto.setOperateBeginTime(maxReportDate);// 声明查询起始时间
		deliverDto.setOperateEndTime(endDate);// 声明查询结束时间
		try {
			// 调用送货接口信息
			deliverList = arriveSheetManngerService
					.queryArriveSheetByDriverCode(deliverDto);
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			throw new SettlementException("调用送货接口报错");
		}
		// 组装前台要用的DTO
		DriverCollectionRptDto resultDto = convertToSendDto(deliverList,
				currentInfo);
		resultDto.getEntity().setDriverCode(dto.getDriverCode());// 此处设置司机编码
		resultDto.getEntity().setDriverName(dto.getDriverName());// 此处设置司机名称
		resultDto.getEntity().setVehicleNo(dto.getVehicleNo());// 此处设置车牌号
		resultDto.getEntity().setReportBeginDate(maxReportDate);// 最近一次缴款时间
		resultDto.getEntity().setReportEndDate(nowDate);// 当前时间

		// 记录日志
		logger.info("查询service结束");
		// 返回结果集
		return resultDto;
	}
	
	/**
	 * njs 送货转换
	 */
	private DriverCollectionRptDto convertToSendDto(
			List<ArrivesheetDeliverDto> deliverList, CurrentInfo currentInfo) {

		// 声明dto
		DriverCollectionRptDto dto = new DriverCollectionRptDto();
		int waybillQtyTotal = 0;// 总票数
		int piecesTotal = 0;// 总件数
		BigDecimal volumeTotal = BigDecimal.ZERO;// 总体积
		BigDecimal weightTotal = BigDecimal.ZERO;// 总重量
		int returnTicketTotal = 0;// 返单总数
		int signwaybillTotal = 0;// 签收单总数
		BigDecimal receiveAmountTotal = BigDecimal.ZERO;// 现金总额
		
		BigDecimal deliverTotalAmount = BigDecimal.ZERO;// 送货总金额
		
		// 声明转化后结算前台要用的送货列表
		List<DriverCollectionRptDEntity> deliverDtoList = new ArrayList<DriverCollectionRptDEntity>();
		
		// 循环送货列表
		for (ArrivesheetDeliverDto deliverDto : deliverList) {
			if (deliverDto != null) {
				volumeTotal = volumeTotal.add(deliverDto.getGoodsVolumeTotal());// 累加总体积
				weightTotal = weightTotal.add(deliverDto.getWeight());// 累加总重量
				// 如果为空，则作为0处理
				BigDecimal totalFee = deliverDto.getTotalFee() == null ? BigDecimal.ZERO
						: deliverDto.getTotalFee();
				receiveAmountTotal = receiveAmountTotal.add(totalFee);// 累加现金总额
				deliverTotalAmount = deliverTotalAmount.add(totalFee);// 计算送货总金额
				piecesTotal = piecesTotal + deliverDto.getArriveSheetGoodsQty();// 计算总件数

				// 进行dto转化为结算前台要用的明细实体
				DriverCollectionRptDEntity entity = new DriverCollectionRptDEntity();
				entity.setType(SettlementDictionaryConstants.DRIVER_COLLECTION_RPT_D__TYPE__DELIVERY);// 送货
				entity.setWaybillNo(deliverDto.getWaybillNo());// 运单号
				entity.setVehicleNo(deliverDto.getVehicleNo());
				entity.setWeight(deliverDto.getWeight());// 重量
				entity.setVolume(deliverDto.getGoodsVolumeTotal());// 体积
				entity.setQty(deliverDto.getArriveSheetGoodsQty());// 件数
				entity.setAmount(totalFee);// 金额
				// 判断是否为返单
				if (StringUtils.isNotBlank(deliverDto.getReturnBillType())
						&& !WaybillConstants.NOT_RETURN_BILL.equals(deliverDto
								.getReturnBillType())) {
					// 返单条数+1
					returnTicketTotal = returnTicketTotal + 1;
					entity.setIsReturnTicket(FossConstants.ACTIVE);// 是返单
				} else {
					entity.setIsReturnTicket(FossConstants.INACTIVE);// 不是返单
				}
				// 将转化后的实体放到送货列表中
				deliverDtoList.add(entity);
			}
		}

		// 循环进行数据转化
		DriverCollectionRptEntity rptEntity = new DriverCollectionRptEntity();
		// 总票数
		waybillQtyTotal = deliverList.size();
		rptEntity.setWaybillQtyTotal(waybillQtyTotal);// 设置总票数
		rptEntity.setPiecesTotal(piecesTotal);// 设置总件数
		rptEntity.setVolumeTotal(volumeTotal);// 设置总体积
		rptEntity.setWeightTotal(weightTotal);// 设置总重量
		rptEntity.setReturnTicketTotal(returnTicketTotal);// 设置返单总数
		rptEntity.setSignwaybillTotal(signwaybillTotal);// 设置签收单总数
		rptEntity.setReceiveAmountTotal(receiveAmountTotal);// 现金总额
		rptEntity.setCreateTime(new Date());
		rptEntity.setCreateUserName(currentInfo.getEmpName());// 办理人为当前登录人
		rptEntity.setCreateUserCode(currentInfo.getEmpCode());// 办理人为当前登录人
		dto.setDeliverList(deliverDtoList);// 送货列表
		
		// 设置接货和送货列表统计明细
		dto.setEntity(rptEntity);
		
		dto.setSignwaybillTotal((short) signwaybillTotal);// 设置签收单总数
		dto.setDeliverTotalAmount(deliverTotalAmount);// 设置送货总金额
		dto.setDeliverTotalCount((short) deliverList.size());// 设置送货总条数
		dto.setReturnTicketTotal((short) returnTicketTotal);// 设置返单总数
		return dto;
	}

	/**
	 * 保存司机PDA报表和明细
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param
	 * @date 2012-12-18 下午6:40:44
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPDAPayInReportBillCreateService#addReceiveReportBill()
	 */
	@Override
	@Transactional
	public DriverCollectionRptDto addReceiveReportBill(
			DriverCollectionRptDto dto, CurrentInfo currentInfo) {
		logger.info("保存缴款报表service start");
		// 声明当前日期
		Date nowDate = new Date();
		// 进行格式化到秒 --此处要格式化到秒，去掉毫秒数
		Date formatNowDate = DateUtils.convert(
				DateUtils.convert(nowDate, DateUtils.DATE_TIME_FORMAT),
				DateUtils.DATE_TIME_FORMAT);
		// 获取缴款单头
		DriverCollectionRptEntity rptEntity = dto.getEntity();
		// 获取接货类表
		List<DriverCollectionRptDEntity> receiveList = dto.getReceivedList();
		// 获取送货列表
		List<DriverCollectionRptDEntity> deliverList = dto.getDeliverList();
		// 如果缴款单头为空，则抛出异常
		if (rptEntity == null) {
			throw new SettlementException("传入缴款报表头为空，不能生成！");
		}
		// 如果接货和送货列表都为空，则抛出异常
		if (CollectionUtils.isEmpty(receiveList)
				&& CollectionUtils.isEmpty(deliverList)) {
			throw new SettlementException("接货和送货明细都为空，不能进行保存操作!");
		}
		// 校验保存参数
		validateAddDto(rptEntity);

		// 处理报表头
		rptEntity.setId(UUIDUtils.getUUID());// 获取id
		// 获取报表编号
		String reportNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.SJ);
		rptEntity.setReportNo(reportNo);// 获取报表编号
		rptEntity.setBusinessDate(formatNowDate);// 获取当前日期
		rptEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 币种
		// 给实体设置当前创建部门
		dto.getEntity().setCreateOrgCode(currentInfo.getCurrentDeptCode());
		// 1、好像要查询是否已经生成过
		int flag = driverCollectionRptEntityDao.isExistReport(dto.getEntity());
		if (flag > 0) {
			throw new SettlementException("该报表明细已经生成报表了，请重新查询制作！");
		}
		// 保存缴款单头
		driverCollectionRptEntityDao.addReceiveReportBill(dto.getEntity(),
				currentInfo);
		// 2、保存缴款分录
		// 如果接货明细不为空，则保存接货明细
		if (CollectionUtils.isNotEmpty(receiveList)) {
			// 循环保存
			for (DriverCollectionRptDEntity dEntity : receiveList) {
				dEntity.setCreateTime(formatNowDate);// 设置创建时间与表头业务日期一致，为了便于分区查询
				dEntity.setId(UUIDUtils.getUUID());// id
				dEntity.setReportNo(reportNo);// 现金交接表编号
				dEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 币种
				driverCollectionRptDEntityDao.insert(dEntity);
			}
		}
		// 如果送货明细不为空，则保存送货明细
		if (CollectionUtils.isNotEmpty(deliverList)) {
			// 循环保存
			for (DriverCollectionRptDEntity dEntity : deliverList) {
				dEntity.setCreateTime(formatNowDate);// 设置创建时间与表头业务日期一致，为了便于分区查询
				dEntity.setId(UUIDUtils.getUUID());// id
				dEntity.setReportNo(reportNo);// 现金交接表编号
				dEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 币种
				driverCollectionRptDEntityDao.insert(dEntity);
			}
		}
		// 回传报表编号
		dto.setReportNo(reportNo);
		logger.info("保存缴款报表service end");
		return dto;
	}

	/**
	 * 查询PDA报表
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param 查询条件dto
	 *            ,当前登录信息,分页参数
	 * @date 2012-12-18 下午6:40:44
	 * @return 缴款信息列表
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPDAPayInReportBillCreateService#queryReceiveReportBill()
	 */
	@Override
	public DriverCollectionRptDto queryReceiveReportBill(
			DriverCollectionRptDto dto, CurrentInfo currentInfo, int start,
			int limit) {
		// 设置当前登录部门
		if (StringUtils.isBlank(currentInfo.getCurrentDeptCode())) {
			throw new SettlementException("当前登录部门为空没，请先进行登录！");
		}
		// 如果起始日期或结束日期为空，则弹出提示
		if (dto.getReportBeginDate() == null || dto.getReportEndDate() == null) {
			throw new SettlementException("查询条件开始日期或结束日期不能为空！");
		}
		// 判断起始日期和结束日期是否相差大于30天
		if (DateUtils.getTimeDiff(dto.getReportBeginDate(),
				dto.getReportEndDate()) > DATE_LIMIT_DAYS_MONTH) {
			throw new SettlementException("起始日期和结束日期相差不能超过"
					+ DATE_LIMIT_DAYS_MONTH + "天");
		}
		// 设置登录部门信息
		dto.setCreateOrgCode(currentInfo.getCurrentDeptCode());
		// 根据查询条件查询缴款报表列表
		List<DriverCollectionRptEntity> rptList = driverCollectionRptEntityDao
				.queryReceiveReportBill(dto, start, limit);
		int totalCount = driverCollectionRptEntityDao
				.queryReceiveReportBillCount(dto);
		// 声明查询dto
		DriverCollectionRptDto resultDto = new DriverCollectionRptDto();
		// 设置返回缴款单list
		resultDto.setRptList(rptList);
		// 设置缴款单总条数
		resultDto.setTotalCount(totalCount);
		return resultDto;
	}

	/**
	 * 校验保存参数
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param
	 * @date 2012-12-21 上午10:14:08
	 * @return
	 */
	private void validateAddDto(DriverCollectionRptEntity rptEntity) {
		// 校验报表起始日期和结束日期
		if (rptEntity.getReportBeginDate() == null
				|| rptEntity.getReportEndDate() == null) {
			throw new SettlementException("报表起始日期和结束日期不能为空！");
		}
		// 如果司机编码为空，则提示
		if (StringUtils.isBlank(rptEntity.getDriverCode())) {
			throw new SettlementException("司机编号不能为空");
		}
		// 如果司机编码为空，则提示
		if (StringUtils.isBlank(rptEntity.getDriverName())) {
			throw new SettlementException("司机名称不能为空");
		}
		// 如果总票数为空，则提示
		if (rptEntity.getWaybillQtyTotal() == null) {
			throw new SettlementException("总票数不能为空");
		}
		// 如果应收金额为空，则提示
		if (rptEntity.getReceiveAmountTotal() == null) {
			throw new SettlementException("应收总额不能为空");
		}
		// 如果实收金额为空，则提示
		if (rptEntity.getReceivedAmountTotal() == null) {
			throw new SettlementException("实收总额不能为空");
		}
		// 校验备注信息
		if (rptEntity.getReceiveAmountTotal().compareTo(
				rptEntity.getReceivedAmountTotal()) != 0
				&& StringUtils.isBlank(rptEntity.getNotes())) {
			throw new SettlementException("实收金额与应收金额不相等，备注信息必须填写！");
		}
	}

	

	/**
	 * 将接送货返回的接货和送货信息进行转化
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param 接货列表和送货列表
	 *            、当前登录人
	 * @date 2012-12-18 下午8:35:56
	 * @return 前台要用的dto
	 */
	public DriverCollectionRptDto convertToDto(
			List<PdaReceiveGoodsDto> receiveList,
			List<ArrivesheetDeliverDto> deliverList, CurrentInfo currentInfo) {
		// 声明dto
		DriverCollectionRptDto dto = new DriverCollectionRptDto();
		int waybillQtyTotal = 0;// 总票数
		int piecesTotal = 0;// 总件数
		BigDecimal volumeTotal = BigDecimal.ZERO;// 总体积
		BigDecimal weightTotal = BigDecimal.ZERO;// 总重量
		int returnTicketTotal = 0;// 返单总数
		int signwaybillTotal = 0;// 签收单总数
		BigDecimal receiveAmountTotal = BigDecimal.ZERO;// 现金总额
		BigDecimal receivedTotalAmount = BigDecimal.ZERO;// 接货总金额
		BigDecimal deliverTotalAmount = BigDecimal.ZERO;// 送货总金额
		//接货现金收入和刷卡
		BigDecimal receiveCashIncome = BigDecimal.ZERO;
		BigDecimal receiveCardIncome = BigDecimal.ZERO;
		//送货现金收入和刷卡收入
		BigDecimal deliverCashIncome = BigDecimal.ZERO;
		BigDecimal deliverCardIncome = BigDecimal.ZERO;
		// 声明转化后结算前台要用的接货列表
		List<DriverCollectionRptDEntity> receiveDtoList = new ArrayList<DriverCollectionRptDEntity>();
		// 声明转化后结算前台要用的送货列表
		List<DriverCollectionRptDEntity> deliverDtoList = new ArrayList<DriverCollectionRptDEntity>();
		// 循环接货列表
		for (PdaReceiveGoodsDto receiveDto : receiveList) {
			volumeTotal = volumeTotal.add(receiveDto.getVolume());// 累加总体积
			weightTotal = weightTotal.add(receiveDto.getWeight());// 累加总重量
			//@author 218392 zhangyongxue注释部分移动到下面 if  else 中
//			receiveAmountTotal = receiveAmountTotal.add(receiveDto
//					.getPayAmount());// 累加现金总额
//			receivedTotalAmount = receivedTotalAmount.add(receiveDto
//					.getPayAmount());// 计算接货总金额
			piecesTotal = piecesTotal + receiveDto.getPieces();// 计算总件数
//			receiveCashIncome = receiveCashIncome.add(receiveDto.getCashIncome());//计算PDA现金收入
//			receiveCardIncome = receiveCardIncome.add(receiveDto.getCardIncome());//计算刷卡收入

			// 进行dto转化为结算前台要用的明细实体
			DriverCollectionRptDEntity entity = new DriverCollectionRptDEntity();
			entity.setType(SettlementDictionaryConstants.DRIVER_COLLECTION_RPT_D__TYPE__PICKUP);// 接货
			entity.setWaybillNo(receiveDto.getWaybillNo());// 运单号
			entity.setVehicleNo(receiveDto.getVehicleNo());// 车牌号
			entity.setWeight(receiveDto.getWeight());// 重量
			entity.setVolume(receiveDto.getVolume());// 体积
			entity.setQty(receiveDto.getPieces());// 件数
			entity.setAmount(receiveDto.getPayAmount());// 金额
			
			/**
			*  @218392 zhangyongxue 2015-08-04 19:00:01 解决线上空指针异常的问题：原后台mapper文件中（
			* 1.select g.pre_pay_amount from PKP.T_SRV_WAYBILL_PENDING g该字段，不可能为空，为0或其他数值；但是此需求后，接送货那边后台mapper中现金收入
			* 取值是PKP.T_SRV_ACTUAL_FREIGHT表中的字段pda_pre_pay_amount，而该字段，有可能为null，那么在add方法的时候，就报空指针
			* select t.pda_pre_pay_amount from PKP.T_SRV_ACTUAL_FREIGHT t ;但是该该查询的字段，有可能为空，返回前台就是null）
			* 所以判断 receiveDto.getCashIncome()位null的时候，就将其设置为0
			*/
			if(receiveDto.getCashIncome() == null){
				receiveDto.setCashIncome(BigDecimal.ZERO);
			}
			
			entity.setCardAmount(receiveDto.getCardIncome());
			entity.setCashAmount(receiveDto.getCashIncome());
			/**
			 * @author 218392 zhangyongxue 设置付款方式，存放的是name
			 * @date 2015-07-07 16:56:30
			 */
			entity.setPayType(receiveDto.getPaidMethod());
			/**
			 * @author 218392 zhangyongxue
			 * @date 2015-07-07 17:03:09
			 * 当付款方式为‘月结CT’‘到付FC’‘网上支付OL’的时候，
			 * ‘现付金额’‘刷卡收入’‘PDA现金收入（元）’显示为零
			 */
			if((StringUtils.equals("CT",entity.getPayType())) || (StringUtils.equals("FC",entity.getPayType())) 
					|| (StringUtils.equals("OL",entity.getPayType()))){
				entity.setAmount(BigDecimal.ZERO);//现付金额
				entity.setCardAmount(BigDecimal.ZERO);//刷卡收入
				entity.setCashAmount(BigDecimal.ZERO);//PDA现金收入
				//
				receiveAmountTotal = receiveAmountTotal.add(BigDecimal.ZERO);// 累加现金总额
				receivedTotalAmount = receivedTotalAmount.add(BigDecimal.ZERO);// 计算接货总金额
				receiveCashIncome = receiveCashIncome.add(BigDecimal.ZERO);//计算PDA现金收入
				receiveCardIncome = receiveCardIncome.add(BigDecimal.ZERO);//计算刷卡收入
			}else{
				
				/**
				 * @author 218392 张永雪 2015-11-18 下午 17:18:00
				 * 判断运单类型，传统运单为空，电子运单为EWAYBILL
				 * 如果是电子运单，并且付款方式是现金，那么 现付金额（元）的数值要同步至 pda现金收入（元），
				 * 保证两列数值一致，并且对应累加至合计的费用中
				 */
				if((StringUtils.equals("EWAYBILL", receiveDto.getWaybillType())) &&
						(StringUtils.equals("CH",entity.getPayType()))){
					receiveDto.setCashIncome(receiveDto.getPayAmount());//现付金额，设置到PDA现金收入金额中
					entity.setCashAmount(receiveDto.getCashIncome());//将现付金额，设置到PDA现金收入金额中
				}
				
				receiveAmountTotal = receiveAmountTotal.add(receiveDto
						.getPayAmount());// 累加现金总额
				receivedTotalAmount = receivedTotalAmount.add(receiveDto
						.getPayAmount());// 计算接货总金额
				receiveCashIncome = receiveCashIncome.add(receiveDto.getCashIncome());//计算PDA现金收入
				receiveCardIncome = receiveCardIncome.add(receiveDto.getCardIncome());//计算刷卡收入
			}
			
			
			// 判断是否签收单
			if (StringUtils.isNotBlank(receiveDto.getIsHaveSignBill())
					&& !WaybillConstants.NOT_RETURN_BILL.equals(receiveDto
							.getIsHaveSignBill())) {
				// 签收单条数+1
				signwaybillTotal = signwaybillTotal + 1;
				entity.setIsSignwaybill(FossConstants.ACTIVE);// 是有签收单
			} else {
				entity.setIsSignwaybill(FossConstants.INACTIVE);// 没有签收单
			}
			// 将转化后的实体放到接货列表中
			receiveDtoList.add(entity);

		}
		// 循环送货列表
		for (ArrivesheetDeliverDto deliverDto : deliverList) {
			if (deliverDto != null) {
				volumeTotal = volumeTotal.add(deliverDto.getGoodsVolumeTotal());// 累加总体积
				weightTotal = weightTotal.add(deliverDto.getWeight());// 累加总重量
				// 如果为空，则作为0处理
				BigDecimal totalFee = deliverDto.getTotalFee() == null ? BigDecimal.ZERO
						: deliverDto.getTotalFee();
				receiveAmountTotal = receiveAmountTotal.add(totalFee);// 累加现金总额
				deliverTotalAmount = deliverTotalAmount.add(totalFee);// 计算送货总金额
				piecesTotal = piecesTotal + deliverDto.getArriveSheetGoodsQty();// 计算总件数
				deliverCashIncome = deliverCashIncome.add(deliverDto.getCashIncome());//计算现金收入
				deliverCardIncome = deliverCardIncome.add(deliverDto.getCardIncome());//计算刷卡收入

				// 进行dto转化为结算前台要用的明细实体
				DriverCollectionRptDEntity entity = new DriverCollectionRptDEntity();
				entity.setType(SettlementDictionaryConstants.DRIVER_COLLECTION_RPT_D__TYPE__DELIVERY);// 送货
				entity.setWaybillNo(deliverDto.getWaybillNo());// 运单号
				entity.setVehicleNo(deliverDto.getVehicleNo());
				entity.setWeight(deliverDto.getWeight());// 重量
				entity.setVolume(deliverDto.getGoodsVolumeTotal());// 体积
				entity.setQty(deliverDto.getArriveSheetGoodsQty());// 件数
				entity.setAmount(totalFee);// 金额
				entity.setCardAmount(deliverDto.getCardIncome());
				entity.setCashAmount(deliverDto.getCashIncome());
				// 判断是否为返单
				if (StringUtils.isNotBlank(deliverDto.getReturnBillType())
						&& !WaybillConstants.NOT_RETURN_BILL.equals(deliverDto
								.getReturnBillType())) {
					// 返单条数+1
					returnTicketTotal = returnTicketTotal + 1;
					entity.setIsReturnTicket(FossConstants.ACTIVE);// 是返单
				} else {
					entity.setIsReturnTicket(FossConstants.INACTIVE);// 不是返单
				}
				// 将转化后的实体放到送货列表中
				deliverDtoList.add(entity);
			}
		}

		// 循环进行数据转化
		DriverCollectionRptEntity rptEntity = new DriverCollectionRptEntity();
		// 总票数
		waybillQtyTotal = receiveList.size() + deliverList.size();
		rptEntity.setWaybillQtyTotal(waybillQtyTotal);// 设置总票数
		rptEntity.setPiecesTotal(piecesTotal);// 设置总件数
		rptEntity.setVolumeTotal(volumeTotal);// 设置总体积
		rptEntity.setWeightTotal(weightTotal);// 设置总重量
		rptEntity.setReturnTicketTotal(returnTicketTotal);// 设置返单总数
		rptEntity.setSignwaybillTotal(signwaybillTotal);// 设置签收单总数
		rptEntity.setReceiveAmountTotal(receiveAmountTotal);// 现金总额
		rptEntity.setCreateTime(new Date());
		rptEntity.setCreateUserName(currentInfo.getEmpName());// 办理人为当前登录人
		rptEntity.setCreateUserCode(currentInfo.getEmpCode());// 办理人为当前登录人
		rptEntity.setCashTotalAmount(receiveCashIncome.add(deliverCashIncome)); //总刷卡收入
		rptEntity.setCardTotalAmount(receiveCardIncome.add(deliverCardIncome));//总现金收入
		dto.setDeliverList(deliverDtoList);// 送货列表
		dto.setReceivedList(receiveDtoList);// 接货列表
		// 设置接货和送货列表统计明细
		dto.setEntity(rptEntity);
		dto.setReceivedTotalAmount(receivedTotalAmount);// 设置接货总金额
		dto.setReceivedTotalCount((short) receiveList.size());// 设置接货总条数
		dto.setSignwaybillTotal((short) signwaybillTotal);// 设置签收单总数
		dto.setDeliverTotalAmount(deliverTotalAmount);// 设置送货总金额
		dto.setDeliverTotalCount((short) deliverList.size());// 设置送货总条数
		dto.setReturnTicketTotal((short) returnTicketTotal);// 设置返单总数
		dto.setReceiveCashIncome(receiveCashIncome);// 设置接货现金总收入
		dto.setReceiveCardIncome(receiveCardIncome);// 设置接货刷卡总收入
		dto.setDeliverCashIncome(deliverCashIncome);// 设置送货现金总收入
		dto.setDeliverCardIncome(deliverCardIncome);// 设置送货刷卡总收入
		return dto;
	}

	/**
	 * 根据报表编号查询报表 -- 打印用
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-25 上午8:22:54
	 * @param reportNo
	 * @return 报表头
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPDAPayInReportBillCreateService#queryReceiveReportByReportNo(java.lang.String)
	 */
	@Override
	public DriverCollectionRptEntity queryReceiveReportByReportNo(
			String reportNo) {
		// 如果报表编号为空，则抛出异常
		if (StringUtils.isBlank(reportNo)) {
			throw new SettlementException("报表编号不能为空！");
		}
		DriverCollectionRptEntity entity = driverCollectionRptEntityDao
				.queryReceiveReportByReportNo(reportNo);
		return entity;
	}

	/**
	 * 根据报表编号查询报表明细 -- 打印用
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-25 上午8:22:54
	 * @param reportNo
	 * @return 报表明细
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPDAPayInReportBillCreateService#queryReceiveReportByReportNo(java.lang.String)
	 */
	@Override
	public List<DriverCollectionRptDEntity> queryRptDEntityByReportNo(
			String reportNo, Date createTime) {
		// 如果报表编号为空，则抛出异常
		if (StringUtils.isBlank(reportNo)) {
			throw new SettlementException("报表编号不能为空！");
		}
		// 调用dao接口查询明细
		List<DriverCollectionRptDEntity> rptDList = driverCollectionRptDEntityDao
				.queryByReportNo(reportNo, createTime);
		return rptDList;
	}

	/**
	 * 根据报表编号查询报表明细 --将明细分成接货和送货列表
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-23 下午5:25:18
	 * @return
	 */
	public DriverCollectionRptDto queryReportDetail(String reportNo,
			Date createTime) {
		DriverCollectionRptDto dto = new DriverCollectionRptDto();
		// 调用接口查询报表明细
		List<DriverCollectionRptDEntity> detailList = queryRptDEntityByReportNo(
				reportNo, createTime);
		// 声明接货和送货集合
		List<DriverCollectionRptDEntity> receivedList = new ArrayList<DriverCollectionRptDEntity>();
		List<DriverCollectionRptDEntity> deliverList = new ArrayList<DriverCollectionRptDEntity>();
		BigDecimal receivedTotalAmount = BigDecimal.ZERO;// 接货总金额
		BigDecimal deliverTotalAmount = BigDecimal.ZERO;// 送货总金额
		int returnTicketTotal = 0;// 返单总数
		int signwaybillTotal = 0;// 签收单总数
		// 判断是否存在明细
		if (CollectionUtils.isNotEmpty(detailList)) {
			// 循环区分接货和送货
			for (DriverCollectionRptDEntity d : detailList) {
				// 获取接货列表
				if (SettlementDictionaryConstants.DRIVER_COLLECTION_RPT_D__TYPE__PICKUP
						.equals(d.getType())) {
					receivedList.add(d);
					receivedTotalAmount = receivedTotalAmount
							.add(d.getAmount());
					// 计算签收单数
					if (FossConstants.ACTIVE.equals(d.getIsSignwaybill())) {
						// 签收单条数+1
						signwaybillTotal = signwaybillTotal + 1;
					}
				} else {
					deliverList.add(d);
					deliverTotalAmount = deliverTotalAmount.add(d.getAmount());
					// 计算签收单数
					if (FossConstants.ACTIVE.equals(d.getIsReturnTicket())) {
						// 签收单条数+1
						returnTicketTotal = returnTicketTotal + 1;
					}
				}
			}
		}
		// 封装接货和送货信息
		dto.setReceivedTotalAmount(receivedTotalAmount);// 设置接货总金额
		dto.setReceivedTotalCount((short) receivedList.size());// 设置接货总条数
		dto.setSignwaybillTotal((short) signwaybillTotal);// 设置签收单总数
		dto.setDeliverTotalAmount(deliverTotalAmount);// 设置送货总金额
		dto.setDeliverTotalCount((short) deliverList.size());// 设置送货总条数
		dto.setReturnTicketTotal((short) returnTicketTotal);// 设置返单总数
		dto.setReceivedList(receivedList);
		dto.setDeliverList(deliverList);

		return dto;
	}

	/**
	 * @param driverCollectionRptEntityDao
	 */
	public void setDriverCollectionRptEntityDao(
			IDriverCollectionRptEntityDao driverCollectionRptEntityDao) {
		this.driverCollectionRptEntityDao = driverCollectionRptEntityDao;
	}

	/**
	 * @return waybillRfcService
	 */
	public IWaybillRfcService getWaybillRfcService() {
		return waybillRfcService;
	}

	/**
	 * @param waybillRfcService
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * @param arriveSheetManngerService
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	/**
	 * @param driverCollectionRptDEntityDao
	 */
	public void setDriverCollectionRptDEntityDao(
			IDriverCollectionRptDEntityDao driverCollectionRptDEntityDao) {
		this.driverCollectionRptDEntityDao = driverCollectionRptDEntityDao;
	}

	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

}
