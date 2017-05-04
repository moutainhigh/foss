package com.deppon.foss.module.transfer.dubbo.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.dubbo.crm.api.define.Org2CrmEntity;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.transfer.dubbo.api.dao.IMatchTaskOrgDao4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.define.AirWaybillDetailDto;
import com.deppon.foss.module.transfer.dubbo.api.define.AirWaybillEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.ExternalBillDto;
import com.deppon.foss.module.transfer.dubbo.api.define.InOutStockEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.LabeledGoodEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.LdpExternalBillDto;
import com.deppon.foss.module.transfer.dubbo.api.define.PathDetailEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.StockDto;
import com.deppon.foss.module.transfer.dubbo.api.define.StockEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillInfoByWaybillNoReultEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillStockEntity;
import com.deppon.foss.module.transfer.dubbo.api.service.IMatchTaskOrgService4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.service.ITransfer2CrmIntegratedService4dubbo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 到达部门为非驻地部门时，匹配任务部门 运单中的到达部门为非驻地部门时，FOSS匹配任务部门 1.
 * 当货物未到到达部门，且没有在中转外场库存时，则匹配“收货部门”为任务部门。 2. 当货物在中转外场库存时，则匹配“收货部门”和“当前库存外场”为任务部门。
 * new: zwd 200968 20150420 old:3. 当货物在到达部门库存或已经签收出库，则匹配“到达部门”为任务部门。 new:3.
 * 当货物在到达部门库存或已经签收出库，需判断来电客户身份，发货方来电，则匹配“收货部门和到达部门”为任务部门，收货方或第三方来电，则匹配“到达部门”为任务部门。根据来电人字段判断，只根据发货方、收货方、第三方。
 * 4. 分批配载时，只按照上述前2点规则进行判断，只要分批配载的货物其中一批满足上述前2点中的匹配规则，则匹配出相应任务部门
 * （如果分批的货物在多个外场库存时，则取这些库存外场中的第一个外场为任务部门）。
 * 
 * 到达部门为驻地部门时，匹配任务部门 运单中的到达部门为驻地部门时，FOSS匹配任务部门 1.
 * 当货物还未到到达部门所属的驻地外场，且未在中转外场库存，则匹配“收货部门”为任务部门。 2.
 * 当货物在中转外场（此外场不包括到达部门所属的驻地外场）库存时，则匹配“收货部门”和“当前库存外场”为任务部门。 old:3.
 * 当货物在到达部门库存或已经签收出库，或在到达部门所属驻地外场库存时，则匹配“到达部门”为任务部门。 zwd 200968 20150420 new:3.
 * 当货物在到达部门库存或已经签收出库，或在到达部门所属驻地外场库存时，需判断来电客户身份，发货方来电，则匹配“收货部门和到达部门”为任务部门，收货方或第三方来电，则匹配“到达部门”为任务部门。根据来电人字段判断，只根据发货方、收货方、第三方
 * 4.
 * 分批配载时，当货物还未全部到达到达部门所属驻地外场时，只按照上述前2点规则进行判断，只要分批配载的货物其中一批满足上述前2点中的匹配规则，则匹配出相应任务部门
 * （如果分批的货物在多个外场库存时，则取这些库存外场中的第一个外场为任务部门）。 5.
 * 分批配载时，当货物已经全部到达了到达部门所属驻地外场时，则只按照第3点规则进行判断。
 * 
 * new: zwd 200968 20150420 空运、偏线、落地配时，匹配任务部门
 * 货物开单运输性质为精准空运、汽运偏线、标准快递/3.60特惠件（落地配）时，FOSS匹配任务部门 1.
 * 当货物未在中转外场库存且未外发交接给代理，则匹配“收货部门”为任务部门。 2. 当货物在中转外场库存时，则匹配“收货部门”和“当前库存外场”为任务部门。
 * 3. 当货物外发交接给代理，则匹配”外发交接的操作部门”和”收货部门”为任务部门。 4.
 * 分批配载时，当货物还未外发交接给代理，则按照上述前2点规则进行判断，当货物部分外发交接给代理，则按第3点规则进行判断（如果分批的货物在多个外场库存时，则取这些库存外场中的第一个外场为任务部门）
 *
 *
 * 相同任务部门只取其中一个 FOSS匹配出的任务部门为2个及以上，且这些任务部门中存在相同部门时，则只取这些相同部门中其中一个为任务部门
 * FOSS匹配出的任务部门为2个及以上，且这些任务部门中存在相同部门时，则只取这些相同部门中其中任何一个为任务部门，并将该任务部门通过接口传给CRM。
 * 
 * old:丢货时，匹配任务部门 存在丢货时，只匹配“收货部门”为任务部门 当货物存在丢货时，则只匹配“收货部门”为任务部门 new: zwd 200968
 * 20150420 存在丢货时，只匹配“收货部门”和“丢货的上一库存部门”为任务部门 1.
 * 当货物存在丢货时，则只匹配“收货部门”和“丢货的上一库存部门”为任务部门 2.
 * 当货物在多个部门存在丢货时，则匹配“收货部门”和“第一个丢货的上一库存部门”为任务部门
 * 
 * 根据规则，返回给CRM的任务部门最多只有2个
 *
 * 匹配任务接口，为CRM提供的接口， 由crm传进来一个运单号，通过库存状态匹配出任务部门
 * 
 * @author 200978 xiaobingcheng 2014-10-10
 */

public class MatchTaskOrgService4dubbo implements IMatchTaskOrgService4dubbo {

	private static final String LOSEORGCODE = "W01000301050203";
	private static final Logger LOGGER = LogManager.getLogger(MatchTaskOrgService4dubbo.class);

	private ITransfer2CrmIntegratedService4dubbo transfer2CrmIntegratedService4dubbo;
	private ISaleDepartmentService saleDepartmentService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IMatchTaskOrgDao4dubbo matchTaskOrgDao;

	/**
	 * 匹配任务接口，为CRM提供的接口， 由crm传进来一个 运单号、来电人 和 来电人类型，通过库存状态匹配出任务部门 update 200968
	 * zwd 20150420
	 */
	@Override
	public List<Org2CrmEntity> matchTaskOrg(String waybillNo, String callMan, String callType) {
		LOGGER.error("中转接口--匹配任务部门开始：" + waybillNo + callMan + callType);
		// 最后的返回LIST
		List<Org2CrmEntity> taskOrgList = new ArrayList<Org2CrmEntity>();
		Set<String> taskOrgSet = new HashSet<String>();// 去除重复的任务部门
		// 错误信息
		StringBuilder message = new StringBuilder("匹配任务部门异常信息：");
		// 获得运单信息
		WaybillInfoByWaybillNoReultEntity waybill = getTransfer2CrmIntegratedService4dubbo()
				.queryWaybillInfoByWaybillNo(waybillNo);
		// 构造收货部门
		Org2CrmEntity receiveTaskOrg = new Org2CrmEntity();
		if (waybill == null) {
			LOGGER.error("运单信息不存在！");
			receiveTaskOrg.setMessage("运单信息不存在！");
			List<Org2CrmEntity> taskOrgListError = new ArrayList<Org2CrmEntity>();
			taskOrgListError.add(receiveTaskOrg);
			return taskOrgListError;
		}
		// 到达部门（提货网点
		String pickupOrgCode = waybill.getCustomerPickupOrgCode();
		// 收货部门(出发部门)
		String receiveOrgCode = waybill.getReceiveOrgCode();
		// 构造收货部门 为任务部门
		// 首先默认 收货部门 为任务部门 zwd 200968
		OrgAdministrativeInfoEntity orgEntity = getOrgAdministrativeInfoService()
				.queryOrgAdministrativeInfoByCode(receiveOrgCode);
		receiveTaskOrg.setIsTransferCenter(orgEntity.getTransferCenter());
		receiveTaskOrg.setOrgUnifiedCode(orgEntity.getUnifiedCode());
		receiveTaskOrg.setOrgCode(orgEntity.getCode());
		receiveTaskOrg.setOrgName(orgEntity.getName());
		List<Org2CrmEntity> receiveTaskOrgList = new ArrayList<Org2CrmEntity>();
		receiveTaskOrgList.add(receiveTaskOrg);// 构造收货部门 为任务部门 end....

		// 根据到达部门查询到达营业部信息
		SaleDepartmentEntity saleDepartment = getSaleDepartmentService().querySimpleSaleDepartmentByCode(pickupOrgCode);
		// 偏线、空运、落地配标示，为true 说明是这三种中的一种情况
		boolean flag = false;
		// 对于偏线、空运和落地配的情况 按 到达部门是非驻地部门计算
		if (saleDepartment == null) {
			flag = true;
			message.append("该运单是空运、偏线或者落地配运单。-");
			saleDepartment = new SaleDepartmentEntity();
			saleDepartment.setStation("N");
			/** 空运、偏线、落地配时，匹配任务部门 根据运单号判断属于那种情况 */
			// public List<AirWaybillEntity>
			// queryAirWayBillListByWayBill(AirWayBillDto airWayBillDto);
			// 查询当前运单的 库存 信息 zwd 200968
			// 根据运单号查询库存
			WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
			waybillStockEntity.setWaybillNO(waybillNo);// 设置运单号
			List<StockEntity> stockList = getTransfer2CrmIntegratedService4dubbo()
					.queryStockByWaybillNo(waybillStockEntity);
			// 通过运单号查询所有流水号 AirWaybillDetailDto
			List<LabeledGoodEntity> serialNoList = getTransfer2CrmIntegratedService4dubbo()
					.queryAllSerialByWaybillNo(waybillNo);
			AirWaybillDetailDto airWaybillDetailDto = new AirWaybillDetailDto();
			airWaybillDetailDto.setWaybillNo(waybillNo);
			// 航空正单
			List<AirWaybillEntity> airWaybillList = getTransfer2CrmIntegratedService4dubbo()
					.queryAirWayBillListByWayBill(airWaybillDetailDto);
			// 3.当货物外发交接给代理，则匹配”外发交接的操作部门”和”收货部门”为任务部门。
			if (airWaybillList.size() > 0) {
				taskOrgSet.add(airWaybillList.get(0).getCreateOrgCode());
				taskOrgSet.add(receiveOrgCode);
			}
			// 2.当货物在中转外场库存时，则匹配“收货部门”和“当前库存外场”为任务部门。
			if (stockList.size() > 0 && serialNoList.size() == stockList.size()) {
				taskOrgSet.add(receiveOrgCode);
				// 查询参数，所有的库存部门，收货部门不用考虑
				Set<String> stockSet2 = new HashSet<String>();
				for (StockEntity stock : stockList) {
					stockSet2.add(stock.getOrgCode()); // 取得当前部门
				}
				// 根据库存部门，查询走货路径中第一个库存部门
				String taskOrg = this.queryFirstOrgByStockOrgSet(stockSet2, waybillNo);
				StringBuilder sb = new StringBuilder();
				for (String string : stockSet2) {
					sb.append(string + "、");
				}
				if (StringUtil.isEmpty(taskOrg)) {
					message.append("匹配任务部门失败,当前库存部门在走货路径中不存在。运单号：" + waybillNo + ".部门编码：" + sb.toString());
					LOGGER.error("匹配任务部门失败，走货路径存在异常，当前库存部门在走货路径中不存在。运单号：" + waybillNo);
				}
				taskOrgSet.add(taskOrg);
			}
			// 1.当货物未在中转外场库存且未外发交接给代理，则匹配“收货部门”为任务部门。
			if (airWaybillList.size() <= 0 && stockList.size() <= 0) {
				taskOrgSet.add(receiveOrgCode);
			}
			// 4.分批配载时，当货物还未外发交接给代理，则按照上述前2点规则进行判断，当货物部分外发交接给代理，则按第3点规则进行判断（如果分批的货物在多个外场库存时，则取这些库存外场中的第一个外场为任务部门）
			if (stockList.size() > 0 && serialNoList.size() != stockList.size()) {
				// 查询参数，所有的库存部门，收货部门不用考虑
				Set<String> stockSet2 = new HashSet<String>();
				for (StockEntity stock : stockList) {
					stockSet2.add(stock.getOrgCode()); // 取得当前部门
				}
				// 根据库存部门，查询走货路径中第一个库存部门
				String taskOrg = this.queryFirstOrgByStockOrgSet(stockSet2, waybillNo);
				StringBuilder sb = new StringBuilder();
				for (String string : stockSet2) {
					sb.append(string + "、");
				}
				if (StringUtil.isEmpty(taskOrg)) {
					message.append("匹配任务部门失败,当前库存部门在走货路径中不存在。运单号：" + waybillNo + ".部门编码：" + sb.toString());
					LOGGER.error("匹配任务部门失败，走货路径存在异常，当前库存部门在走货路径中不存在。运单号：" + waybillNo);
				}
				taskOrgSet.add(taskOrg);
			}

			// 偏线
			// List<ExternalBillDto> selectByParams(ExternalBillDto dto, int
			// limit, int start, CurrentInfo currentInfo);
			ExternalBillDto dto = new ExternalBillDto();
			dto.setWaybillNo(waybillNo);
			List<ExternalBillDto> externalBillList = getTransfer2CrmIntegratedService4dubbo()
					.selectExternalByWayBillNo(dto);
			// 1.当货物未在中转外场库存且未外发交接给代理，则匹配“收货部门”为任务部门。
			if (externalBillList.size() <= 0 && stockList.size() <= 0) {
				taskOrgSet.add(receiveOrgCode);
			}
			// 3.当货物外发交接给代理，则匹配”外发交接的操作部门”和”收货部门”为任务部门。
			if (externalBillList.size() > 0) {
				taskOrgSet.add(externalBillList.get(0).getExternalOrgCode());
				taskOrgSet.add(receiveOrgCode);
			}

			// 落地配
			List<LdpExternalBillDto> ldpExternalBillList = getTransfer2CrmIntegratedService4dubbo()
					.queryExternalBillListByWaybillNo(waybillNo);
			// 1.当货物未在中转外场库存且未外发交接给代理，则匹配“收货部门”为任务部门。
			if (ldpExternalBillList.size() <= 0 && stockList.size() <= 0) {
				taskOrgSet.add(receiveOrgCode);
			}
			// 3.当货物外发交接给代理，则匹配”外发交接的操作部门”和”收货部门”为任务部门。
			if (ldpExternalBillList.size() > 0) {
				taskOrgSet.add(ldpExternalBillList.get(0).getExternalOrgCode());
				taskOrgSet.add(receiveOrgCode);
			}

		}
		// 是否为驻地营业部
		String isStation = saleDepartment.getStation();
		LOGGER.info("matchTaskOrg 是否为驻地营业部 = " + isStation);
		// 根据运单号查询库存
		WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
		waybillStockEntity.setWaybillNO(waybillNo);// 设置运单号
		// 查询当前运单的 库存 信息 zwd 200968
		List<StockEntity> stockList = getTransfer2CrmIntegratedService4dubbo()
				.queryStockByWaybillNo(waybillStockEntity);
		// 如果货物有库存
		if (CollectionUtils.isNotEmpty(stockList)) {
			LOGGER.info("matchTaskOrg 货物有库存");
			// 通过运单号查询所有流水号
			List<LabeledGoodEntity> serialNoList = getTransfer2CrmIntegratedService4dubbo()
					.queryAllSerialByWaybillNo(waybillNo);
			// 库存部门set 去掉重复的当前库存部门编码 zwd 200968
			Set<String> orgCodeSet = new HashSet<String>();
			for (StockEntity stockEntity : stockList) {
				orgCodeSet.add(stockEntity.getOrgCode());
			}
			// 如果库存只有一个库存部门，说明不是分批走货，或者已经到达到达部门,或者部门货物还在途中
			if (orgCodeSet.size() == 1) {
				LOGGER.info("库存只有一个库存部门，说明不是分批走货，或者已经到达到达部门,或者部门货物还在途中");
				// 如果开单件数 = 库存件数 说明不是分批走的，或者全部到达到达部门
				// 先判断签收件数是+加上库存件数是否等于开单件数，如果是...
				StockDto dto = new StockDto();
				dto.setWaybillNo(waybillNo);
				dto.setDestroyed("N");
				dto.setStatus("SIGN");
				dto.setActive("Y");
				List<String> signList = getTransfer2CrmIntegratedService4dubbo().querySerialNoByWaybillNo(dto);
				// 如果签收件数加库存件数等于开单件数 此时匹配到达部门
				if ((signList.size() + stockList.size() == serialNoList.size())) {
					LOGGER.info("签收件数加库存件数等于开单件数 此时匹配到达部门");
					// 拿其中一件库存货物
					StockEntity stock = stockList.get(0);
					// 如果是驻地营业部
					if (StringUtils.equals(isStation, FossConstants.YES)) {
						// 库存部门编号OrgCode&驻地营业部所属外场 TransferCenter
						if (StringUtils.equals(stock.getOrgCode(), saleDepartment.getTransferCenter())) {
							// 运单中的到达部门为驻地部门时
							// 当货物在到达部门库存或已经签收出库，或在到达部门所属驻地外场库存时，需判断来电客户身份
							// 发货方来电，则匹配“收货部门和到达部门”为任务部门，收货方或第三方来电，则匹配“到达部门”为任务部门。
							// 根据来电人字段判断，只根据发货方、收货方、第三方。
							// 来电人类型 -
							// 发货方=SHIPMAN、收货方=RECEIVEMAN、第三方=THIRDPARTY、内部同事=INNERCOLLE
							if (callType.equals("SHIPMAN")) {
								// 到达部门是任务部门
								taskOrgSet.add(pickupOrgCode);
								taskOrgSet.add(receiveOrgCode);
							}
							if (callType.equals("RECEIVEMAN") || callType.equals("THIRDPARTY")) {
								taskOrgSet.clear();
								// 到达部门是任务部门
								taskOrgSet.add(pickupOrgCode);
							}
						} else if (StringUtils.equals(stock.getOrgCode(), MatchTaskOrgService4dubbo.LOSEORGCODE)) {
							// 1.当货物存在丢货时，则只匹配“收货部门”和“丢货的上一库存部门”为任务部门和“丢货的上一库存部门”为任务部门
							// 2.当货物在多个部门存在丢货时，则匹配“收货部门”和“第一个丢货的上一库存部门”为任务部门
							// List<PathDetailEntity>
							// queryPathDetailList(PathDetailEntity
							// pathDetailEntity);
							/**
							 * 库存部门（实际走货路由）+运单号 ---》查询 入库件数小于开单件数 不一致 存在丢货 --1、
							 * 如果部门数大于1 取第一个丢货的上一库存部门 --2、 如果等于1 丢货的上一库存部门
							 */
							PathDetailEntity pathDetailEntity = new PathDetailEntity();
							pathDetailEntity.setWaybillNo(waybillNo);
							List<PathDetailEntity> pathDetailEntityList = getMatchTaskOrgDao()
									.queryPathDetailList(pathDetailEntity);

							String departOrgCode = null;
							for (PathDetailEntity path : pathDetailEntityList) {
								List<InOutStockEntity> inOutStockList = getTransfer2CrmIntegratedService4dubbo()
										.queryInStockInfoSmall(waybillNo, null, path.getOrigOrgCode(), new Date());

								if (inOutStockList.size() < serialNoList.size()) {
									if (!StringUtils.equals(path.getOrigOrgCode(), receiveOrgCode)) {
										OrgAdministrativeInfoEntity orgEntity2 = getOrgAdministrativeInfoService()
												.queryOrgAdministrativeInfoByCode(departOrgCode);
										if (!StringUtils.equals(departOrgCode, receiveOrgCode)) {
											Org2CrmEntity receiveTaskOrg5 = new Org2CrmEntity();
											if (orgEntity2 != null) {
												receiveTaskOrg5.setIsTransferCenter(orgEntity2.getTransferCenter());
												receiveTaskOrg5.setOrgUnifiedCode(orgEntity2.getUnifiedCode());
												receiveTaskOrg5.setOrgCode(orgEntity2.getCode());
												receiveTaskOrg5.setOrgName(orgEntity2.getName());
												receiveTaskOrgList.add(receiveTaskOrg5);
											}
										}
										return receiveTaskOrgList;
									} else {
										return receiveTaskOrgList;
									}
								}
								departOrgCode = path.getOrigOrgCode();
							}
							// 如果不是始发营业部
						} else {// 不管库存在收货部门还是中转外场，都将该部门和收货部门添加到任务部门
							taskOrgSet.add(receiveOrgCode);
							taskOrgSet.add(stock.getOrgCode());
						}
						// 如果不是驻地营业部
					} else {
						// 如果库存部门是到达部门
						if (StringUtils.equals(stock.getOrgCode(), pickupOrgCode)) {
							// 当货物在到达部门库存或已经签收出库，需判断来电客户身份，发货方来电，则匹配“收货部门和到达部门”为任务部门，
							// 收货方或第三方来电，则匹配“到达部门”为任务部门。
							// 根据来电人字段判断，只根据发货方、收货方、第三方。
							// 来电人类型 -
							// 发货方=SHIPMAN、收货方=RECEIVEMAN、第三方=THIRDPARTY、内部同事=INNERCOLLE
							if (callType.equals("SHIPMAN")) {
								// 到达部门是任务部门
								taskOrgSet.add(pickupOrgCode);
								taskOrgSet.add(receiveOrgCode);
							}
							if (callType.equals("RECEIVEMAN") || callType.equals("THIRDPARTY")) {
								taskOrgSet.clear();
								// 到达部门是任务部门
								taskOrgSet.add(pickupOrgCode);
							}
						} else if (StringUtils.equals(stock.getOrgCode(), MatchTaskOrgService4dubbo.LOSEORGCODE)) {
							// 1.当货物存在丢货时，则只匹配“收货部门”和“丢货的上一库存部门”为任务部门和“丢货的上一库存部门”为任务部门
							// 2.当货物在多个部门存在丢货时，则匹配“收货部门”和“第一个丢货的上一库存部门”为任务部门
							// List<PathDetailEntity>
							// queryPathDetailList(PathDetailEntity
							// pathDetailEntity);
							/**
							 * 库存部门（实际走货路由）+运单号 ---》查询 入库件数小于开单件数 不一致 存在丢货 --1、
							 * 如果部门数大于1 取第一个丢货的上一库存部门 --2、 如果等于1 丢货的上一库存部门
							 */
							PathDetailEntity pathDetailEntity = new PathDetailEntity();
							pathDetailEntity.setWaybillNo(waybillNo);
							List<PathDetailEntity> pathDetailEntityList = getMatchTaskOrgDao()
									.queryPathDetailList(pathDetailEntity);

							String departOrgCode = null;
							for (PathDetailEntity path : pathDetailEntityList) {

								List<InOutStockEntity> inOutStockList = getTransfer2CrmIntegratedService4dubbo()
										.queryInStockInfoSmall(waybillNo, null, path.getOrigOrgCode(), new Date());

								if (inOutStockList.size() < serialNoList.size()) {

									if (!StringUtils.equals(path.getOrigOrgCode(), receiveOrgCode)) {
										OrgAdministrativeInfoEntity orgEntity2 = getOrgAdministrativeInfoService()
												.queryOrgAdministrativeInfoByCode(departOrgCode);
										if (!StringUtils.equals(departOrgCode, receiveOrgCode)) {
											Org2CrmEntity receiveTaskOrg2 = new Org2CrmEntity();
											if (orgEntity2 != null) {
												receiveTaskOrg2.setIsTransferCenter(orgEntity2.getTransferCenter());
												receiveTaskOrg2.setOrgUnifiedCode(orgEntity2.getUnifiedCode());
												receiveTaskOrg2.setOrgCode(orgEntity2.getCode());
												receiveTaskOrg2.setOrgName(orgEntity2.getName());
												receiveTaskOrgList.add(receiveTaskOrg2);
											}
										}

										return receiveTaskOrgList;
									} else {
										return receiveTaskOrgList;
									}
								}

								departOrgCode = path.getOrigOrgCode();

							}
							// 如果不是始发营业部
						} else {// 不管库存在收货部门还是中转外场，都将该部门和收货部门添加到任务部门
							taskOrgSet.add(receiveOrgCode);
							taskOrgSet.add(stock.getOrgCode());
						}
					}
				} else {// 部分货物在途,分批走货
						// 查询参数，所有的库存部门，收货部门不用考虑
					Set<String> stockSet = new HashSet<String>();
					// 循环库存list
					for (StockEntity stockEntity : stockList) {
						// 如果是驻地营业部
						if (StringUtils.equals(isStation, FossConstants.YES)) {
							// 如果有一件货在丢货部门，则返回收货部门
							if (StringUtils.equals(stockEntity.getOrgCode(), MatchTaskOrgService4dubbo.LOSEORGCODE)) {
								// 1.当货物存在丢货时，则只匹配“收货部门”和“丢货的上一库存部门”为任务部门和“丢货的上一库存部门”为任务部门
								// 2.当货物在多个部门存在丢货时，则匹配“收货部门”和“第一个丢货的上一库存部门”为任务部门
								// List<PathDetailEntity>
								// queryPathDetailList(PathDetailEntity
								// pathDetailEntity);
								/**
								 * 库存部门（实际走货路由）+运单号 ---》查询 入库件数小于开单件数 不一致 存在丢货
								 * --1、 如果部门数大于1 取第一个丢货的上一库存部门 --2、 如果等于1
								 * 丢货的上一库存部门
								 */
								PathDetailEntity pathDetailEntity = new PathDetailEntity();
								pathDetailEntity.setWaybillNo(waybillNo);
								List<PathDetailEntity> pathDetailEntityList = getMatchTaskOrgDao()
										.queryPathDetailList(pathDetailEntity);

								String departOrgCode = null;
								for (PathDetailEntity path : pathDetailEntityList) {

									List<InOutStockEntity> inOutStockList = getTransfer2CrmIntegratedService4dubbo()
											.queryInStockInfoSmall(waybillNo, null, path.getOrigOrgCode(), new Date());

									if (inOutStockList.size() < serialNoList.size()) {

										if (!StringUtils.equals(path.getOrigOrgCode(), receiveOrgCode)) {
											OrgAdministrativeInfoEntity orgEntity2 = getOrgAdministrativeInfoService()
													.queryOrgAdministrativeInfoByCode(departOrgCode);
											if (!StringUtils.equals(departOrgCode, receiveOrgCode)) {
												Org2CrmEntity receiveTaskOrg6 = new Org2CrmEntity();
												if (orgEntity2 != null) {
													receiveTaskOrg6.setIsTransferCenter(orgEntity2.getTransferCenter());
													receiveTaskOrg6.setOrgUnifiedCode(orgEntity2.getUnifiedCode());
													receiveTaskOrg6.setOrgCode(orgEntity2.getCode());
													receiveTaskOrg6.setOrgName(orgEntity2.getName());
													receiveTaskOrgList.add(receiveTaskOrg6);
												}
											}

											return receiveTaskOrgList;
										} else {
											return receiveTaskOrgList;
										}
									}

									departOrgCode = path.getOrigOrgCode();

								}
								// 如果不是始发营业部
							} else if (!StringUtils.equals(stockEntity.getOrgCode(), receiveOrgCode)) {
								// 库存是否是驻地外场
								if (StringUtils.equals(stockEntity.getOrgCode(), saleDepartment.getTransferCenter())) {
									// 先判断签收件数是+加上库存件数是否等于开单件数，如果是...
									StockDto dto1 = new StockDto();
									dto1.setWaybillNo(waybillNo);
									dto1.setDestroyed("N");
									dto1.setStatus("SIGN");
									dto1.setActive("Y");
									List<String> signList1 = getTransfer2CrmIntegratedService4dubbo()
											.querySerialNoByWaybillNo(dto);
									// 如果签收件数加库存件数等于开单件数 此时匹配到达部门
									if ((signList1.size() + stockList.size() == serialNoList.size()) && !flag) {
										// 需判断来电客户身份，发货方来电，则匹配“收货部门和到达部门”为任务部门，
										// 收货方或第三方来电，则匹配“到达部门”为任务部门。根据来电人字段判断，只根据发货方、收货方、第三方。
										// 当货物在到达部门库存或已经签收出库，需判断来电客户身份，发货方来电，则匹配“收货部门和到达部门”为任务部门，
										// 收货方或第三方来电，则匹配“到达部门”为任务部门。
										// 根据来电人字段判断，只根据发货方、收货方、第三方。
										// 来电人类型 -
										// 发货方=SHIPMAN、收货方=RECEIVEMAN、第三方=THIRDPARTY、内部同事=INNERCOLLE

										if (callType.equals("SHIPMAN")) {
											// 到达部门是任务部门
											taskOrgSet.add(pickupOrgCode);
											taskOrgSet.add(receiveOrgCode);
										}
										if (callType.equals("RECEIVEMAN") || callType.equals("THIRDPARTY")) {
											taskOrgSet.clear();
											// 到达部门是任务部门
											taskOrgSet.add(pickupOrgCode);
										}

									} else {
										// 匹配收货部门
										taskOrgSet.add(receiveOrgCode);
									}
									// 不是始发营业部，不是驻地营业部外场，则匹配该外场
								} else {
									taskOrgSet.add(receiveOrgCode);
									stockSet.add(stockEntity.getOrgCode());// 取得当前部门
								}
							} else {// 是收货部门
								taskOrgSet.add(receiveOrgCode);
							}
							// 如果不是驻地营业部
						} else {
							// 如果有一件货在丢货部门，则返回收货部门
							if (StringUtils.equals(stockEntity.getOrgCode(), MatchTaskOrgService4dubbo.LOSEORGCODE)) {
								// 1.当货物存在丢货时，则只匹配“收货部门”和“丢货的上一库存部门”为任务部门和“丢货的上一库存部门”为任务部门
								// 2.当货物在多个部门存在丢货时，则匹配“收货部门”和“第一个丢货的上一库存部门”为任务部门
								// List<PathDetailEntity>
								// queryPathDetailList(PathDetailEntity
								// pathDetailEntity);
								/**
								 * 库存部门（实际走货路由）+运单号 ---》查询 入库件数小于开单件数 不一致 存在丢货
								 * --1、 如果部门数大于1 取第一个丢货的上一库存部门 --2、 如果等于1
								 * 丢货的上一库存部门
								 */
								PathDetailEntity pathDetailEntity = new PathDetailEntity();
								pathDetailEntity.setWaybillNo(waybillNo);
								List<PathDetailEntity> pathDetailEntityList = getMatchTaskOrgDao()
										.queryPathDetailList(pathDetailEntity);

								String departOrgCode = null;
								for (PathDetailEntity path : pathDetailEntityList) {

									List<InOutStockEntity> inOutStockList = getTransfer2CrmIntegratedService4dubbo()
											.queryInStockInfoSmall(waybillNo, null, path.getOrigOrgCode(), new Date());

									if (inOutStockList.size() < serialNoList.size()) {

										if (!StringUtils.equals(path.getOrigOrgCode(), receiveOrgCode)) {
											OrgAdministrativeInfoEntity orgEntity2 = getOrgAdministrativeInfoService()
													.queryOrgAdministrativeInfoByCode(departOrgCode);
											if (!StringUtils.equals(departOrgCode, receiveOrgCode)) {
												Org2CrmEntity receiveTaskOrg3 = new Org2CrmEntity();
												if (orgEntity2 != null) {
													receiveTaskOrg3.setIsTransferCenter(orgEntity2.getTransferCenter());
													receiveTaskOrg3.setOrgUnifiedCode(orgEntity2.getUnifiedCode());
													receiveTaskOrg3.setOrgCode(orgEntity2.getCode());
													receiveTaskOrg3.setOrgName(orgEntity2.getName());
													receiveTaskOrgList.add(receiveTaskOrg3);
												}
											}

											return receiveTaskOrgList;
										} else {
											return receiveTaskOrgList;
										}
									}

									departOrgCode = path.getOrigOrgCode();

								}
								// 如果不是始发营业部
							} else if (!StringUtils.equals(stockEntity.getOrgCode(), receiveOrgCode)) {
								// 库存是否是到达部门
								if (StringUtils.equals(stockEntity.getOrgCode(), pickupOrgCode)) {
									// 先判断签收件数是+加上库存件数是否等于开单件数，如果是，则匹配到达部门
									StockDto dto2 = new StockDto();
									dto2.setWaybillNo(waybillNo);
									dto2.setDestroyed("N");
									dto2.setStatus("SIGN");
									dto2.setActive("Y");
									List<String> signList2 = getTransfer2CrmIntegratedService4dubbo()
											.querySerialNoByWaybillNo(dto);
									// 如果签收件数加库存件数等于开单件数 此时匹配到达部门
									if ((signList2.size() + stockList.size() == serialNoList.size()) && !flag) {
										// 直接匹配到达部门
										taskOrgSet.add(pickupOrgCode);
									} else {
										// 匹配收货部门
										taskOrgSet.add(receiveOrgCode);
									}
									// 不是始发营业部，不是到达部门，则匹配该外场
								} else {
									taskOrgSet.add(receiveOrgCode);
									stockSet.add(stockEntity.getOrgCode());// 取得当前部门
								}
							} else {// 是收货部门
								taskOrgSet.add(receiveOrgCode);
							}
						}
					} // for end
					if (com.deppon.foss.util.CollectionUtils.isNotEmpty(stockSet)) {
						// 根据库存部门，查询走货路径中第一个库存部门
						String taskOrg = this.queryFirstOrgByStockOrgSet(stockSet, waybillNo);
						StringBuilder sb = new StringBuilder();
						for (String string : stockSet) {
							sb.append(string + "、");
						}
						if (StringUtil.isEmpty(taskOrg)) {
							message.append("匹配任务部门失败,当前库存部门在走货路径中不存在。运单号：" + waybillNo + ".部门编码：" + sb.toString());
							LOGGER.error("匹配任务部门失败，走货路径存在异常，当前库存部门在走货路径中不存在。运单号：" + waybillNo);
						}
						taskOrgSet.add(taskOrg);
					}
				}
			} else {// 存在多个库存部门，分批走货
					// 查询参数，所有的库存部门，收货部门不用考虑
				Set<String> stockSet = new HashSet<String>();
				// 循环库存list
				for (StockEntity stockEntity : stockList) {
					// 如果是驻地营业部
					if (StringUtils.equals(isStation, FossConstants.YES)) {
						// 如果有一件货在丢货部门，则返回收货部门
						if (StringUtils.equals(stockEntity.getOrgCode(), MatchTaskOrgService4dubbo.LOSEORGCODE)) {
							// 1.当货物存在丢货时，则只匹配“收货部门”和“丢货的上一库存部门”为任务部门和“丢货的上一库存部门”为任务部门
							// 2.当货物在多个部门存在丢货时，则匹配“收货部门”和“第一个丢货的上一库存部门”为任务部门
							// List<PathDetailEntity>
							// queryPathDetailList(PathDetailEntity
							// pathDetailEntity);
							/**
							 * 库存部门（实际走货路由）+运单号 ---》查询 入库件数小于开单件数 不一致 存在丢货 --1、
							 * 如果部门数大于1 取第一个丢货的上一库存部门 --2、 如果等于1 丢货的上一库存部门
							 */
							PathDetailEntity pathDetailEntity = new PathDetailEntity();
							pathDetailEntity.setWaybillNo(waybillNo);
							List<PathDetailEntity> pathDetailEntityList = getMatchTaskOrgDao()
									.queryPathDetailList(pathDetailEntity);
							String departOrgCode = null;
							for (PathDetailEntity path : pathDetailEntityList) {

								List<InOutStockEntity> inOutStockList = getTransfer2CrmIntegratedService4dubbo()
										.queryInStockInfoSmall(waybillNo, null, path.getOrigOrgCode(), new Date());

								if (inOutStockList.size() < serialNoList.size()) {

									if (!StringUtils.equals(path.getOrigOrgCode(), receiveOrgCode)) {
										OrgAdministrativeInfoEntity orgEntity2 = getOrgAdministrativeInfoService()
												.queryOrgAdministrativeInfoByCode(departOrgCode);
										if (!StringUtils.equals(departOrgCode, receiveOrgCode)) {
											Org2CrmEntity receiveTaskOrg7 = new Org2CrmEntity();
											if (orgEntity2 != null) {
												receiveTaskOrg7.setIsTransferCenter(orgEntity2.getTransferCenter());
												receiveTaskOrg7.setOrgUnifiedCode(orgEntity2.getUnifiedCode());
												receiveTaskOrg7.setOrgCode(orgEntity2.getCode());
												receiveTaskOrg7.setOrgName(orgEntity2.getName());
												receiveTaskOrgList.add(receiveTaskOrg7);
											}
										}

										return receiveTaskOrgList;
									} else {
										return receiveTaskOrgList;
									}
								}

								departOrgCode = path.getOrigOrgCode();

							}
							// 如果不是始发营业部
						} else if (!StringUtils.equals(stockEntity.getOrgCode(), receiveOrgCode)) {
							// 库存是否是驻地外场
							if (StringUtils.equals(stockEntity.getOrgCode(), saleDepartment.getTransferCenter())) {
								// 匹配收货部门
								taskOrgSet.add(receiveOrgCode);
								// 不是始发营业部，不是驻地营业部外场，则匹配该外场
							} else {
								taskOrgSet.add(receiveOrgCode);
								stockSet.add(stockEntity.getOrgCode());// 取得当前部门
							}
						} else {// 是收货部门
							taskOrgSet.add(receiveOrgCode);
						}
						// 如果不是驻地营业部
					} else {
						// 如果有一件货在丢货部门，则返回收货部门
						if (StringUtils.equals(stockEntity.getOrgCode(), MatchTaskOrgService4dubbo.LOSEORGCODE)) {
							// 1.当货物存在丢货时，则只匹配“收货部门”和“丢货的上一库存部门”为任务部门和“丢货的上一库存部门”为任务部门
							// 2.当货物在多个部门存在丢货时，则匹配“收货部门”和“第一个丢货的上一库存部门”为任务部门
							// List<PathDetailEntity>
							// queryPathDetailList(PathDetailEntity
							// pathDetailEntity);
							/**
							 * 库存部门（实际走货路由）+运单号 ---》查询 入库件数小于开单件数 不一致 存在丢货 --1、
							 * 如果部门数大于1 取第一个丢货的上一库存部门 --2、 如果等于1 丢货的上一库存部门
							 */
							PathDetailEntity pathDetailEntity = new PathDetailEntity();
							pathDetailEntity.setWaybillNo(waybillNo);
							List<PathDetailEntity> pathDetailEntityList = getMatchTaskOrgDao()
									.queryPathDetailList(pathDetailEntity);

							String departOrgCode = null;
							for (PathDetailEntity path : pathDetailEntityList) {

								List<InOutStockEntity> inOutStockList = getTransfer2CrmIntegratedService4dubbo()
										.queryInStockInfoSmall(waybillNo, null, path.getOrigOrgCode(), new Date());

								if (inOutStockList.size() < serialNoList.size()) {

									if (!StringUtils.equals(path.getOrigOrgCode(), receiveOrgCode)) {
										OrgAdministrativeInfoEntity orgEntity2 = getOrgAdministrativeInfoService()
												.queryOrgAdministrativeInfoByCode(departOrgCode);
										if (!StringUtils.equals(departOrgCode, receiveOrgCode)) {
											Org2CrmEntity receiveTaskOrg4 = new Org2CrmEntity();
											if (orgEntity2 != null) {
												receiveTaskOrg4.setIsTransferCenter(orgEntity2.getTransferCenter());
												receiveTaskOrg4.setOrgUnifiedCode(orgEntity2.getUnifiedCode());
												receiveTaskOrg4.setOrgCode(orgEntity2.getCode());
												receiveTaskOrg4.setOrgName(orgEntity2.getName());
												receiveTaskOrgList.add(receiveTaskOrg4);
											}
										}

										return receiveTaskOrgList;
									} else {
										return receiveTaskOrgList;
									}
								}

								departOrgCode = path.getOrigOrgCode();

							}
							// 如果不是始发营业部
						} else if (!StringUtils.equals(stockEntity.getOrgCode(), receiveOrgCode)) {
							// 库存是否是到达部门
							if (StringUtils.equals(stockEntity.getOrgCode(), pickupOrgCode)) {
								// 匹配收货部门
								taskOrgSet.add(receiveOrgCode);
								// 不是始发营业部，不是到达部门，则匹配该外场
							} else {
								taskOrgSet.add(receiveOrgCode);
								stockSet.add(stockEntity.getOrgCode());// 取得当前部门
							}
						} else {// 是收货部门
							taskOrgSet.add(receiveOrgCode);
						}
					}
				} // for end
				if (com.deppon.foss.util.CollectionUtils.isNotEmpty(stockSet)) {
					// 根据库存部门，查询走货路径中第一个库存部门
					String taskOrg = this.queryFirstOrgByStockOrgSet(stockSet, waybillNo);
					StringBuilder sb = new StringBuilder();
					for (String string : stockSet) {
						sb.append(string + "、");
					}
					if (StringUtil.isEmpty(taskOrg)) {
						message.append("匹配任务部门失败,当前库存部门在走货路径中不存在。运单号：" + waybillNo + ".部门编码：" + sb.toString());
						LOGGER.error("匹配任务部门失败，走货路径存在异常，当前库存部门在走货路径中不存在。运单号：" + waybillNo);
					}
					taskOrgSet.add(taskOrg);
				}
			}
		} else {// 如果货物没有库存，则判断是否已签收，没有就只匹配收货部门,否则匹配到达部门
			LOGGER.info("matchTaskOrg 货物没有库存，则判断是否已签收，没有就只匹配收货部门,否则匹配到达部门");
			boolean isSigned = false;
			// 根据运单号查询运单是否 全部签收
			isSigned = this.checkWaybillIsSigned(waybillNo);
			// 根据到达部门查询到达营业部信息
			SaleDepartmentEntity saleDepartment2 = getSaleDepartmentService()
					.querySimpleSaleDepartmentByCode(pickupOrgCode);

			// 如果全部签收 并且不是空运。偏线或者落地配
			if (isSigned && saleDepartment2 != null) {
				// 当货物在到达部门库存或已经签收出库,需判断来电客户身份，发货方来电
				// 则匹配“收货部门和到达部门”为任务部门，收货方或第三方来电，则匹配“到达部门”为任务部门。
				// 根据来电人字段判断，只根据发货方、收货方、第三方。
				if (callType.equals("SHIPMAN")) {
					// 到达部门是任务部门
					taskOrgSet.add(pickupOrgCode);
					taskOrgSet.add(receiveOrgCode);
				}
				if (callType.equals("RECEIVEMAN") || callType.equals("THIRDPARTY")) {
					taskOrgSet.clear();
					// 到达部门是任务部门
					taskOrgSet.add(pickupOrgCode);
				}
			} else {// 否则匹配收货部门
				taskOrgSet.add(receiveOrgCode);
			}
		}

		// 如果匹配的任务部门不为空
		if (CollectionUtils.isNotEmpty(taskOrgSet)) {
			for (String orgCode : taskOrgSet) {
				LOGGER.error("中转接口--匹配任务部门编码为:" + orgCode);
				OrgAdministrativeInfoEntity org = getOrgAdministrativeInfoService()
						.queryOrgAdministrativeInfoByCode(orgCode);
				Org2CrmEntity taskOrg = new Org2CrmEntity();
				taskOrg.setMessage(message.toString());
				if (org != null) {
					taskOrg.setOrgCode(org.getCode());
					taskOrg.setOrgUnifiedCode(org.getUnifiedCode());
					taskOrg.setOrgName(org.getName());
					taskOrg.setIsTransferCenter(org.getTransferCenter());
					taskOrgList.add(taskOrg);
				}
			}
		}
		LOGGER.error("中转接口--匹配任务部门结束");
		return taskOrgList;
	}

	/**
	 * 根据库存部门，查询走货路径中第一个库存部门
	 * 
	 * @Author: 200978 xiaobingcheng 2014-10-20
	 * @param stockSet
	 * @return
	 */
	private String queryFirstOrgByStockOrgSet(Set<String> stockSet, String waybillNo) {
		return this.getMatchTaskOrgDao().queryFirstOrgByStockOrgSet(stockSet, waybillNo);
	}

	/**
	 * 根据运单号，判断该运单是否已经全部签收
	 * 
	 * @Author: 200978 xiaobingcheng 2014-10-22
	 * @param waybill
	 * @return
	 */
	private boolean checkWaybillIsSigned(String waybillNo) {
		return this.getMatchTaskOrgDao().checkWaybillIsSigned(waybillNo);
	}

	/**
	 * @author zwd 200968
	 * @param waybillNo
	 * @param callType
	 * @date 2016-03-02 FOSS可以根据来电人身份、运单的库存状态匹配出相应的部门，并通过接口将部门信息传给CRM。
	 * @return
	 */
	@Override
	public List<Org2CrmEntity> matchTaskOrg(String waybillNo, String callType) {
		/**
		 * 发货方=SHIPMAN、收货方=RECEIVEMAN 1、来电人为发货人时，传收货部门信息给CRM。
		 * 2、来电人为收货人，运输性质是“空运”、“偏线”、“落地配”时，传收货部门信息给CRM。
		 * 3、来电人为收货人，到达部门为非驻地部门时，根据货物库存状态，传相应部门信息给CRM。 1.货未全部到到达部门，传收货部门信息给CRM。
		 * 2.货物全部到到达部门/已经签收出库，传到达部门信息给CRM； //3.单号信息在FOSS无法带出部门信息的，传空值给CRM。 4、
		 * 来电人为收货人，到达部门为驻地部门时，根据货物库存状态，传相应部门信息给CRM。
		 * 1.货还未全部到到达部门所属的驻地外场，传收货部门信息给CRM。其他情况
		 * （如部分货物到到达部门所属的驻地外场，部分已到达到达部门及之后状态），传到达部门信息给CRM；
		 * //2.单号信息在FOSS无法带出部门信息的，传空值给CRM。
		 */
		LOGGER.error("中转接口--匹配工单短信部门开始");
		// 最后的返回LIST
		List<Org2CrmEntity> taskOrgList = new ArrayList<Org2CrmEntity>();
		Set<String> taskOrgSet = new HashSet<String>(); // 去除重复的任务部门
		// 错误信息
		StringBuilder message = new StringBuilder("匹配工单短信部门异常信息：");
		// 获得运单信息
		WaybillInfoByWaybillNoReultEntity waybill = getTransfer2CrmIntegratedService4dubbo()
				.queryWaybillInfoByWaybillNo(waybillNo);
		// 构造收货部门
		Org2CrmEntity receiveTaskOrg = new Org2CrmEntity();
		if (waybill == null) {
			LOGGER.error("运单信息不存在！");
			receiveTaskOrg.setMessage("运单信息不存在！");
			List<Org2CrmEntity> taskOrgListError = new ArrayList<Org2CrmEntity>();
			taskOrgListError.add(receiveTaskOrg);
			return taskOrgListError;
		}

		// 取得产品类型
		String productCode = waybill.getProductCode();
		// 判断是否是经济快递
		if (directDetermineIsExpressByProductCode(productCode)) {

			LOGGER.error("运单运输性质为快递！");
			receiveTaskOrg.setMessage("运单运输性质为快递！");
			List<Org2CrmEntity> taskOrgListError = new ArrayList<Org2CrmEntity>();
			taskOrgListError.add(receiveTaskOrg);
			return taskOrgListError;
		}

		// 到达部门（提货网点）
		String pickupOrgCode = waybill.getCustomerPickupOrgCode();
		// 收货部门(出发部门)
		String receiveOrgCode = waybill.getReceiveOrgCode();
		// 根据到达部门查询到达营业部信息
		SaleDepartmentEntity saleDepartment = getSaleDepartmentService().querySimpleSaleDepartmentByCode(pickupOrgCode);

		// 根据运单号查询库存
		WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
		waybillStockEntity.setWaybillNO(waybillNo);// 设置运单号
		// 查询当前运单的 库存 信息
		List<StockEntity> stockList = getTransfer2CrmIntegratedService4dubbo()
				.queryStockByWaybillNoInStockTime(waybillStockEntity);
		boolean isSigned = false;
		// 根据运单号查询运单是否 全部签收
		isSigned = this.checkWaybillIsSigned(waybillNo);

		// 发货方=SHIPMAN、收货方=RECEIVEMAN
		if (callType.equals("SHIPMAN")) {
			// 来电人为发货人时，传收货部门信息给CRM
			taskOrgSet.add(receiveOrgCode);
		} else if (callType.equals("RECEIVEMAN")) {

			// 来电人为收货人，运输性质是“空运”、“偏线”、“落地配”时，传收货部门信息给CRM。
			if (saleDepartment == null) {
				taskOrgSet.add(receiveOrgCode);
			} else {
				// 是否为驻地营业部
				String isStation = saleDepartment.getStation();
				// 如果货物有库存
				if (CollectionUtils.isNotEmpty(stockList)) {
					// 通过运单号查询所有流水号
					List<LabeledGoodEntity> serialNoList = getTransfer2CrmIntegratedService4dubbo()
							.queryAllSerialByWaybillNo(waybillNo);

					// 如果开单件数 = 库存件数 说明不是分批走的，或者全部到达到达部门
					// 先判断签收件数是+加上库存件数是否等于开单件数，如果是...
					StockDto dto = new StockDto();
					dto.setWaybillNo(waybillNo);
					dto.setDestroyed("N");
					dto.setStatus("SIGN");
					dto.setActive("Y");
					List<String> signList = getTransfer2CrmIntegratedService4dubbo().querySerialNoByWaybillNo(dto);
					// 如果签收件数加库存件数等于开单件数 此时匹配到达部门
					if ((signList.size() + stockList.size() == serialNoList.size())) {
						// 拿其中一件库存货物
						StockEntity stock = stockList.get(0);
						// 如果是驻地营业部
						if (StringUtils.equals(isStation, FossConstants.YES)) {
							// 库存部门编号OrgCode&驻地营业部所属外场 TransferCenter
							if (StringUtils.equals(stock.getOrgCode(), saleDepartment.getTransferCenter())) {
								taskOrgSet.add(pickupOrgCode);
							} else {
								taskOrgSet.add(receiveOrgCode);
							}
						} else {
							// 非驻地营业部
							if (StringUtils.equals(stock.getOrgCode(), pickupOrgCode)) {
								taskOrgSet.add(pickupOrgCode);
							} else {
								taskOrgSet.add(receiveOrgCode);
							}
						}
					}
				} else {
					if (isSigned == true) {
						// 全部签收
						taskOrgSet.add(pickupOrgCode);
					} else {
						taskOrgSet.add(receiveOrgCode);
					}
				}
			}
		}

		// 如果匹配的任务部门不为空
		if (com.deppon.foss.util.CollectionUtils.isNotEmpty(taskOrgSet)) {
			for (String orgCode : taskOrgSet) {
				LOGGER.error("中转接口--匹配工单短信部门编码为:" + orgCode);
				OrgAdministrativeInfoEntity org = getOrgAdministrativeInfoService()
						.queryOrgAdministrativeInfoByCode(orgCode);
				Org2CrmEntity taskOrg = new Org2CrmEntity();
				taskOrg.setMessage(message.toString());
				if (org != null) {
					taskOrg.setOrgCode(org.getCode());
					taskOrg.setOrgUnifiedCode(org.getUnifiedCode());
					taskOrg.setOrgName(org.getName());
					taskOrg.setIsTransferCenter(org.getTransferCenter());
					taskOrgList.add(taskOrg);
				}
			}
		}
		return taskOrgList;
	}

	private static boolean directDetermineIsExpressByProductCode(String productCode) {
		// 判定数据是否为空
		if (productCode == null || "".equals(productCode)) {
			return false;
		}
		// 在人为判定是否相等
		if ("PACKAGE".equals(productCode) || "RCP".equals(productCode) || "EPEP".equals(productCode)
				|| "DEAP".equals(productCode)) {
			return true;
		}
		return false;
	}

	public ITransfer2CrmIntegratedService4dubbo getTransfer2CrmIntegratedService4dubbo() {
		return transfer2CrmIntegratedService4dubbo;
	}

	@Autowired
	public void setTransfer2CrmIntegratedService4dubbo(
			ITransfer2CrmIntegratedService4dubbo transfer2CrmIntegratedService4dubbo) {
		this.transfer2CrmIntegratedService4dubbo = transfer2CrmIntegratedService4dubbo;
	}

	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	@Autowired
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	@Autowired
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IMatchTaskOrgDao4dubbo getMatchTaskOrgDao() {
		return matchTaskOrgDao;
	}

	@Autowired
	public void setMatchTaskOrgDao(IMatchTaskOrgDao4dubbo matchTaskOrgDao) {
		this.matchTaskOrgDao = matchTaskOrgDao;
	}

}
