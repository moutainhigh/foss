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
import com.deppon.foss.module.settlement.pay.api.server.service.INewPDAPayInReportBillCreateService;
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
public class NewPDAPayInReportBillCreateService implements
		INewPDAPayInReportBillCreateService {
	/**
	 * 获取日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(NewPDAPayInReportBillCreateService.class);
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
		// 如果司机编码为空，则抛出异常
		if (StringUtils.isBlank(dto.getDriverCode())) {
			throw new SettlementException("司机编码不能为空！");
		}
		//如果报表类型为空，则抛出异常
		if(StringUtils.isBlank(dto.getRptType())){
			throw new SettlementException("报表类型不能为空");
		}
		//如果查询日期为为空，则抛出异常
		if(dto.getQueryReportDate()==null){
			throw new SettlementException("查询日期不能为空");
		}
		
		//获取查询日期、当前日期、结束日期
		Date queryDate=dto.getQueryReportDate();
		Date nowDate = new Date();
		Date endDate=null;
		//本次报表的缴款时间
		Date currentReportDate=null;
		
		// 获取当前日期前31天的00:00:00
		Date startDate = DateUtils.getStartDatetime(queryDate);
		dto.setReportBeginDate(startDate);
		//获取今天的00:00:00
		Date currentDate = DateUtils.getStartDatetime(nowDate);
		//如果查询当天，则结束时间为当前时间。反之如果查询历史，则结束时间为所查询日期下一天凌晨00:00:00
		if(startDate.equals(currentDate)){
			currentReportDate=nowDate;
			// 获取所选日期下一天的00:00:00
			endDate = nowDate;
			dto.setReportEndDate(endDate);
		}else{
			// 获取所选日期下一天的00:00:00
			endDate = DateUtils.getStartDatetime(queryDate, 1);
			dto.setReportEndDate(endDate);
			currentReportDate=endDate;
		}

		// 设置当前登录部门
		if (StringUtils.isBlank(currentInfo.getCurrentDeptCode())) {
			throw new SettlementException("当前登录部门为空没，请先进行登录！");
		}
		dto.setCreateOrgCode(currentInfo.getCurrentDeptCode());

		// 获取最近一次缴款时间
		Date maxReportDate = driverCollectionRptEntityDao
				.selectMaxReportDateByDate(dto);
		// 如果当期没有缴款，则获取该时间的00:00:00
		if (maxReportDate == null) {
			maxReportDate = startDate;
		}
		
		//声明接货和送货列表
		List<PdaReceiveGoodsDto> receiveList = new ArrayList<PdaReceiveGoodsDto>();
		List<ArrivesheetDeliverDto> deliverList = new ArrayList<ArrivesheetDeliverDto>();
		// 调用接货接口获取接货信息
		if(SettlementDictionaryConstants.DRIVER_COLLECTION_RPT_D__TYPE__PICKUP.equals(dto.getRptType())){
			try {
				// 调用送货接口获取接货信息
				receiveList = waybillRfcService.queryPdaReceiveGoodsDtoByDate(
						maxReportDate,endDate,dto.getDriverCode());
			} catch (BusinessException e) {
				logger.error(e.getMessage(), e);
				throw new SettlementException("调用接货接口报错");
			}
		// 调用送货接口获取送货信息
		}else if(SettlementDictionaryConstants.DRIVER_COLLECTION_RPT_D__TYPE__DELIVERY.equals(dto.getRptType())){
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
		}
		
		// 组装前台要用的DTO
		DriverCollectionRptDto resultDto = convertToDto(receiveList,deliverList, currentInfo);
		resultDto.getEntity().setDriverCode(dto.getDriverCode());// 此处设置司机编码
		resultDto.getEntity().setDriverName(dto.getDriverName());// 此处设置司机名称
		resultDto.getEntity().setVehicleNo(dto.getVehicleNo());// 此处设置车牌号
		resultDto.getEntity().setReportBeginDate(maxReportDate);// 最近一次缴款时间
		resultDto.getEntity().setReportEndDate(currentReportDate);//如果为当天，则为当前时间。如果是别天，则为下一天的00:00:00
		resultDto.getEntity().setRptType(dto.getRptType());//如果为当天，则为当前时间。如果是别天，则为下一天的00:00:00
		// 记录日志
		logger.info("查询service结束");
		// 返回结果集
		return resultDto;
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
		Date startDate = DateUtils.getStartDatetime(dto.getEntity().getReportEndDate());
		// 获取缴款单头
		DriverCollectionRptEntity rptEntity = dto.getEntity();
		
		dto.setReportEndDate(startDate);
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
	private DriverCollectionRptDto convertToDto(
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
		// 声明转化后结算前台要用的接货列表
		List<DriverCollectionRptDEntity> receiveDtoList = new ArrayList<DriverCollectionRptDEntity>();
		// 声明转化后结算前台要用的送货列表
		List<DriverCollectionRptDEntity> deliverDtoList = new ArrayList<DriverCollectionRptDEntity>();
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
