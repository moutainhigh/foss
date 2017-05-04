package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyTonService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IWaitForkAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaitForkAreaDistanceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.scheduling.api.define.PlatformDispatchConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPlatformDistributeDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPlatformDistributeService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformPreAssignEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeQcDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformQcDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformVehicleInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformWaybillDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PlatformDistributeService implements IPlatformDistributeService {

	private static final Logger LOGGER = Logger
			.getLogger(PlatformDistributeService.class);

	private IPlatformDistributeDao platformDistributeDao;

	/**
	 * 综合装卸车效率
	 */
	private ILoadAndUnloadEfficiencyTonService loadAndUnloadEfficiencyTonService;

	/**
	 * 综合线路
	 */
	private ILineService lineService;

	/**
	 * 综合发车标准
	 */
	private IDepartureStandardService departureStandardService;

	/**
	 * 综合车辆
	 */
	private IVehicleService vehicleService;

	/**
	 * 综合月台
	 */
	private IPlatformService platformService;

	/**
	 * 综合外场
	 */
	private IOutfieldService outfieldService;

	/**
	 * 综合货区
	 */
	private IGoodsAreaService goodsAreaService;

	/**
	 * 综合待叉区
	 */
	private IWaitForkAreaService waitForkAreaService;

	/**
	 * 综合组织
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setPlatformDistributeDao(
			IPlatformDistributeDao platformDistributeDao) {
		this.platformDistributeDao = platformDistributeDao;
	}

	public void setLoadAndUnloadEfficiencyTonService(
			ILoadAndUnloadEfficiencyTonService loadAndUnloadEfficiencyTonService) {
		this.loadAndUnloadEfficiencyTonService = loadAndUnloadEfficiencyTonService;
	}

	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setDepartureStandardService(
			IDepartureStandardService departureStandardService) {
		this.departureStandardService = departureStandardService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}

	public void setWaitForkAreaService(IWaitForkAreaService waitForkAreaService) {
		this.waitForkAreaService = waitForkAreaService;
	}

	/**
	 * 查询给定部门code所属外场
	 * 
	 * @param code
	 * @return
	 * @date 2014-3-26
	 * @author Ouyang
	 */
	@Override
	public Map<String, String> queryParentTfrCtrCode(String code) {
		Map<String, String> result = null;

		// 调用综合接口判断当前部门是否外场或外场子部门
		List<String> bizTypesList = new ArrayList<String>();

		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypesList);
		if (orgEntity != null) {
			result = new HashMap<String, String>();
			result.put("code", orgEntity.getCode());
			result.put("name", orgEntity.getName());
		}

		return result;
	}

	/**
	 * 根据外场code查询卸车效率
	 * 
	 * @param orgCode
	 * @return
	 * @date 2014-4-4
	 * @author Ouyang
	 */
	private LoadAndUnloadEfficiencyTonEntity getUnloadEfficiency(String orgCode) {
		return loadAndUnloadEfficiencyTonService
				.queryLoadAndUnloadEfficiencyTonByOrgCode(orgCode);

	}

	/**
	 * 根据出发、到达部门查询线路(只考虑汽运货) 到达当前外场的线路只可能是始发线路或运作到运作； 从当前外场出发的线路只可能是运作到运作或到达线路
	 * 
	 * @param sourceCode
	 * @param targetCode
	 * @return
	 * @date 2014-4-9
	 * @author Ouyang
	 */
	private LineEntity getLine(String sourceCode, String targetCode) {

		if (StringUtils.isEmpty(sourceCode) || StringUtils.isEmpty(targetCode)) {
			return null;
		}

		LineEntity condition = new LineEntity();
		condition.setOrginalOrganizationCode(sourceCode);
		condition.setDestinationOrganizationCode(targetCode);
		condition.setValid(FossConstants.YES);
		condition.setActive(FossConstants.ACTIVE);

		// 根据两点查询线路
		List<LineEntity> lineList = lineService
				.querySimpleLineListByCondition(condition);

		if (CollectionUtils.isEmpty(lineList)) {
			return null;
		}

		// 若只有一条，则返回
		if (lineList.size() == 1) {
			return lineList.get(0);
		}

		// 若有多条，返回汽运的线路
		String lineType = null;
		String transType = null;
		for (LineEntity line : lineList) {
			transType = line.getTransType();
			lineType = line.getLineType();
			if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN
					.equals(transType)
					|| DictionaryValueConstants.BSE_LINE_TYPE_ZHUANXIAN
							.equals(lineType)) {
				return line;
			}
		}

		return null;
	}

	/**
	 * 根据运单号查询走货路径，获取运单的下一部门
	 * 
	 * @param waybill
	 * @return
	 * @date 2014-4-8
	 * @author Ouyang
	 */
	private String getNextDeptCode(PlatformWaybillDto waybill) {

		if (waybill == null) {
			return null;
		}

		return platformDistributeDao.queryNextDeptCode(waybill);
	}

	/**
	 * 获取上一部门到当前外场的时效
	 * 
	 * @param vehicleInfo
	 * @return
	 * @date 2014-4-10
	 * @author Ouyang
	 */
	private Long getDepartLineAging(PlatformVehicleInfoDto vehicleInfo) {

		if (vehicleInfo == null) {
			return null;
		}

		Long aging = 0L;
		String pattern = "HHmm";

		String vehicleOwnerType = vehicleInfo.getVehicleOwnerType();

		String sourceCode = vehicleInfo.getOrigDeptCode();
		String targetCode = vehicleInfo.getDestDeptCode();
		// 获取上一部门到当前外场汽运线路(到外场的只有可能是始发线路或运作到运作)
		LineEntity arrivedLine = getLine(sourceCode, targetCode);
		if (arrivedLine != null) {
			if (DictionaryValueConstants.BSE_LINE_SORT_SOURCE
					.equals(arrivedLine.getLineSort())) {
				// 线路虚拟编码
				String lineVirtualCode = arrivedLine.getVirtualCode();
				if (StringUtils.isNotEmpty(lineVirtualCode)) {
					// 获取对应始发线路的发车标准
					List<DepartureStandardEntity> departStandards = departureStandardService
							.queryDepartureStandardListByLineVirtualCode(lineVirtualCode);
					if (CollectionUtils.isNotEmpty(departStandards)) {
						// 随便取一条始发线路的发车标准，根据到达出发时间，到达时间计算时效(千分之小时)
						DepartureStandardEntity departStandard = departStandards
								.get(0);

						// 出发时间
						Date leaveTime = DateUtils.parseString2Date(
								departStandard.getLeaveTime(), pattern);
						// 到达时间
						Date arriveTime = DateUtils.parseString2Date(
								departStandard.getArriveTime(), pattern);
						// 天数
						Long arriveDay = departStandard.getArriveDay() == null ? 0L
								: departStandard.getArriveDay();
						// 时效(千分之小时)
						aging = (new BigDecimal(arriveTime.getTime()
								- leaveTime.getTime()).divide(new BigDecimal(
										ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60), 0, BigDecimal.ROUND_HALF_UP))
								.longValue()
								+ arriveDay * ConstantsNumberSonar.SONAR_NUMBER_24 * ConstantsNumberSonar.SONAR_NUMBER_1000;
					}
				}
			} else if (DictionaryValueConstants.BSE_LINE_SORT_TRANSFER
					.equals(arrivedLine.getLineSort())) {
				// 运作到运作线路，公司车取卡车时效,外请车取普车时效
				aging = PlatformDispatchConstants.COMPANY
						.equals(vehicleOwnerType) ? arrivedLine.getFastAging()
						: arrivedLine.getCommonAging();
			}
		}

		return aging;
	}

	/**
	 * 获取从当前外场到下一部门的计划出发时间
	 * 
	 * @param destDeptCode
	 *            车从上一环节出发的到达部门，及当前外场
	 * @param nextDeptCode
	 *            车从当前外场出发的下一部门
	 * @return
	 * @date 2014-5-27
	 * @author Ouyang
	 */
	private String getArrivedLineLeaveTimeStr(String destDeptCode,
			String nextDeptCode) {

		String leaveTimeStr = null;

		if (StringUtils.isEmpty(destDeptCode)
				|| StringUtils.isEmpty(nextDeptCode)) {
			return leaveTimeStr;
		}

		// 运单从当前外场到走货路径下一部门的出发线路(外场出发的只有可能是运作到运作或到达线路)
		LineEntity departLine = getLine(destDeptCode, nextDeptCode);
		if (departLine == null
				|| StringUtils.isEmpty(departLine.getVirtualCode())) {
			return leaveTimeStr;
		}
		// 运单从当前部门到走货路径一下部门出发线路对应的发车标准集合
		List<DepartureStandardEntity> departureStandardList = departureStandardService
				.queryDepartureStandardListByLineVirtualCode(departLine
						.getVirtualCode());

		if (CollectionUtils.isEmpty(departureStandardList)) {
			return leaveTimeStr;
		}

		int size = departureStandardList.size();

		if (size == 1) {
			DepartureStandardEntity departStandard = departureStandardList
					.get(0);
			leaveTimeStr = departStandard.getLeaveTime();
		} else {
			// 如果是运作到运作，有时效类型为卡车的发车标准，则取卡车的最后一班车，否则取最后一班车
			// 如果是到达线路，则取最后一班车
			if (DictionaryValueConstants.BSE_LINE_SORT_TRANSFER
					.equals(departLine.getLineSort())) {
				for (int i = size - 1; i >= 0; i--) {
					DepartureStandardEntity departStandard = departureStandardList
							.get(i);
					if (DictionaryValueConstants.PRODUCT_FAST
							.equals(departStandard.getProductType())) {
						leaveTimeStr = departStandard.getLeaveTime();
						break;
					}

					if (i == 0) {
						leaveTimeStr = departureStandardList.get(size - 1)
								.getLeaveTime();
					}
				}
			} else if (DictionaryValueConstants.BSE_LINE_SORT_TARGET
					.equals(departLine.getLineSort())) {
				leaveTimeStr = departureStandardList.get(size - 1)
						.getLeaveTime();
			}
		}

		return leaveTimeStr;
	}

	/**
	 * 将车里的运单按下一部门(从当前外场出发)分组；
	 * 
	 * @param waybillInfos
	 *            车里的运单集合；由于可及时中转票数只考虑卡航和城运，所以这里的运单也只有卡航和城运
	 * @return map的key为下一部门;value为一下部门对应的运单数
	 * @date 2014-5-27
	 * @author Ouyang
	 */
	private Map<String, Integer> groupWaybillsByNextDeptCode(
			List<PlatformWaybillDto> waybillInfos) {
		Map<String, Integer> result = new HashMap<String, Integer>();

		if (CollectionUtils.isEmpty(waybillInfos)) {
			return result;
		}

		List<String> nextDeptCodes = new ArrayList<String>();

		for (PlatformWaybillDto item : waybillInfos) {
			String key = item.getNextDeptCode();

			if (nextDeptCodes.contains(key)) {
				continue;
			}

			nextDeptCodes.add(key);

			int value = 0;

			for (PlatformWaybillDto item2 : waybillInfos) {
				String nextDeptCode = item2.getNextDeptCode();

				if (!StringUtils.equals(key, nextDeptCode)) {
					continue;
				}

				value++;
			}

			if (value == 0) {
				continue;
			}

			result.put(key, value);
		}

		return result;
	}

	/**
	 * 
	 * @param destDeptCode
	 *            车从上一部门出发的到达部门，及当前外场
	 * @param sourceGrouped
	 *            {@link #groupWaybillsByNextDeptCode(List)}方法处理后的结果
	 * @param sourceTime
	 *            对应在途车辆，sourceTime为预计到达时间；对于到达车辆，sourceTime为当前时间
	 * @param unloadTime
	 *            预计卸车用时，单位为分钟
	 * @return
	 * @date 2014-5-28
	 * @author Ouyang
	 */
	private int getTotalVotesTransferInTime(String destDeptCode,
			Map<String, Integer> sourceGrouped, Date sourceTime, int unloadTime) {
		int result = 0;

		if (StringUtils.isEmpty(destDeptCode) || sourceGrouped == null
				|| sourceGrouped.isEmpty()) {
			return result;
		}

		Set<Entry<String, Integer>> entrySet = sourceGrouped.entrySet();
		for (Entry<String, Integer> item : entrySet) {
			// 这一组运单的下一部门
			String nextDeptCode = item.getKey();
			// 通过当前外场，下一部门找运单从当前外场出发的发车标准
			String leaveTimeStr = getArrivedLineLeaveTimeStr(destDeptCode,
					nextDeptCode);

			// nextDeptCode对应的运单数
			int num = item.getValue();

			// 车里某条线路上可及时中转的运单数
			result += getEachGroupVotesTransferInTime(sourceTime, unloadTime,
					leaveTimeStr, num);
		}

		return result;
	}

	/**
	 * 车里某条线路上可及时中转的运单数
	 * 
	 * @param sourceTime
	 *            对应在途车辆，sourceTime为预计到达时间；对于到达车辆，sourceTime为当前时间;
	 *            在此方法体的注释中，都用预计到达时间表示
	 * @param unloadTime
	 *            预计卸车用时，单位为分钟
	 * @param leaveTimeStr
	 *            从当前外场出发到下一部门的发车标准中的发车时间，格式为HHmm
	 * @param num
	 *            将车里的运单按下一部门分组后，某组运单的数量
	 * @return
	 * @date 2014-5-28
	 * @author Ouyang
	 */
	private int getEachGroupVotesTransferInTime(Date sourceTime,
			int unloadTime, String leaveTimeStr, int num) {

		if (sourceTime == null || leaveTimeStr == null) {
			return 0;
		}

		// 预计到达时间(到达当前外场)+12小时；这里的12由业务给定
		Date targetTime = DateUtils.addDate(sourceTime, Calendar.HOUR_OF_DAY,
				ConstantsNumberSonar.SONAR_NUMBER_12);
		// 预计出发时间(从当前外场出发)
		Date estimatedDepartTime = DateUtils.addDate(sourceTime,
				Calendar.MINUTE, unloadTime);

		String pattern = "yyyyMMddHHmm";
		// 将预计到达时间转换成yyyyMMddHHmm格式
		String sourceTimeStr = DateUtils.formatDate2String(sourceTime, pattern);
		// 预计到达时间对应的小时，用于判断预计到达时间+12小时是否跨天；如果201405271705对应的小时为17，加12小时则跨天
		int sourceTimeHour = Integer.valueOf(sourceTimeStr.substring(ConstantsNumberSonar.SONAR_NUMBER_8, ConstantsNumberSonar.SONAR_NUMBER_10))
				.intValue();

		// 先默认不跨天，则车的预计到达时间(到达当前外场)、预计出发时间(从当前外场出发)+12小时是在同一天
		// 那么从当前外场出发的发车标准也取当天；
		String str = sourceTimeStr.substring(0, ConstantsNumberSonar.SONAR_NUMBER_8) + leaveTimeStr;

		// 当跨天时，当发车标准的出发时间<=12时，才取targetTime所在天；
		// 因为targetTime所在时间一定小于12，若发车标准的出发时间>12时，取targetTime所在天,
		// 则发车时间一定晚于targetTime,则可及时中转票数为0，所以此种情况不考虑
		if (sourceTimeHour >= ConstantsNumberSonar.SONAR_NUMBER_12) {
			Integer leaveTimeHour = null;
			try {
				leaveTimeHour = Integer.valueOf(leaveTimeStr.substring(0, 2));
			} catch (NumberFormatException e) {
				return 0;
			}
			if (leaveTimeHour <= ConstantsNumberSonar.SONAR_NUMBER_12) {
				str = DateUtils.formatDate2String(targetTime, pattern)
						.substring(0, ConstantsNumberSonar.SONAR_NUMBER_8) + leaveTimeStr;
			}
		}

		// 从当前外场出发的发车标准(带日期)
		Date leaveTime = DateUtils.parseString2Date(str, pattern);
		if (leaveTime == null) {
			return 0;
		}

		// 当leaveTime>targetTime时，则全不可及时中转
		if (leaveTime.compareTo(targetTime) > 0) {
			return 0;
		}

		// 判断预计出发时间(从当前外场出发)与预计到达时间(到达当前外场)+12小时的先后顺序
		if (targetTime.compareTo(estimatedDepartTime) >= 0) {
			// 当时间先后顺序为 sourceTime -> estimatedDepartTime -> targetTime时：
			// 1、leaveTime>targetTime，则全不可及时中转
			// 2、leaveTime>=estimatedDepartTime && <=targetTime，则此线路货全可及时中转
			// 3、leaveTime<estimatedDepartTime，则按比例计算
			if (leaveTime.compareTo(estimatedDepartTime) >= 0) {
				return num;
			} else {
				long fastTime = leaveTime.getTime() - sourceTime.getTime();

				if (fastTime <= 0) {
					return 0;
				}

				// 有的卸车时间
				BigDecimal haveUnloadTimeMsec = BigDecimal.valueOf(fastTime);

				// 需要的卸车时间
				BigDecimal needUnloadTimeMsec = BigDecimal
						.valueOf(unloadTime * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_1000);

				// 此条线路的可及时中转票数=此条线路的票数 * 有的卸车时间/需要的卸车时间
				return BigDecimal
						.valueOf(num)
						.multiply(haveUnloadTimeMsec)
						.divide(needUnloadTimeMsec, 0, BigDecimal.ROUND_HALF_UP)
						.intValue();
			}

		} else {
			// 当时间先后顺序为 sourceTime -> targetTime -> estimatedDepartTime时：
			// 1、leaveTime>targetTime，则全不可及时中转
			// 2、按比例计算

			long fastTime = leaveTime.getTime() - sourceTime.getTime();

			if (fastTime <= 0) {
				return 0;
			}

			// 有的卸车时间
			BigDecimal haveUnloadTimeMsec = BigDecimal.valueOf(fastTime);

			// 需要的卸车时间
			BigDecimal needUnloadTimeMsec = BigDecimal
					.valueOf(unloadTime * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_1000);

			// 此条线路的可及时中转票数=此条线路的票数 * 有的卸车时间/需要的卸车时间
			return BigDecimal.valueOf(num).multiply(haveUnloadTimeMsec)
					.divide(needUnloadTimeMsec, 0, BigDecimal.ROUND_HALF_UP)
					.intValue();
		}
	}

	/**
	 * 填充未到达公司/外请车其他属性
	 * 
	 * @param vehicleInfo
	 *            车辆信息
	 * @param unloadEfficiency
	 *            外场卸车效率
	 * @param vehicleOwnerType
	 *            车辆所属类型
	 * @param endEstimatedArriveTime
	 *            目标预计到达时间,当根据交接编号/或车牌号查询时为null
	 * @return
	 * @date 2014-4-10
	 * @author Ouyang
	 */
	private PlatformVehicleInfoDto fillOtherPropOnTheWay(
			PlatformVehicleInfoDto vehicleInfo,
			LoadAndUnloadEfficiencyTonEntity unloadEfficiency,
			Date beginEstimatedArriveTime, Date endEstimatedArriveTime) {

		if (vehicleInfo == null) {
			return null;
		}

		// 根据车牌号查询车辆信息
		VehicleAssociationDto vehicleAssociationDto = vehicleService
				.queryVehicleAssociationDtoByVehicleNo(vehicleInfo
						.getVehicleNo());

		if (vehicleAssociationDto != null
				&& StringUtils.isNotEmpty(vehicleAssociationDto
						.getVehicleLengthCode())) {
			// 设置车型
			vehicleInfo.setVehicleLengthCode(vehicleAssociationDto
					.getVehicleLengthCode());
			vehicleInfo.setVehicleLengthName(vehicleAssociationDto
					.getVehicleLengthName());
		}

		// 预计到达时间
		Date estimatedArriveTime = vehicleInfo.getEstimatedArriveTime();
		// 出发时间
		Date departTime = vehicleInfo.getDepartTime();

		// 上一部门到当前外场的时效(千分之小时)
		Long aging = getDepartLineAging(vehicleInfo);

		// 若预计到达时间为空，则根据出发时间+时效重新设置
		if (estimatedArriveTime == null && departTime != null) {
			// 设置预计到达时间
			estimatedArriveTime = DateUtils.addDate(departTime,
					Calendar.SECOND, Long.valueOf(aging * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60 / ConstantsNumberSonar.SONAR_NUMBER_1000)
							.intValue());
			// 重新设置计划到达时间
			vehicleInfo.setEstimatedArriveTime(estimatedArriveTime);

			// 车辆任务明细里面预计到达时间可能为空，这种情况就需要用出发时间+时效来设置该车的预计到达时间，所以需要针对此种情况进行判断
			// 如果查询条件的预计到达时间在该车的实际预计到达时间前，则不满足
			if (beginEstimatedArriveTime != null
					&& endEstimatedArriveTime != null
					&& (beginEstimatedArriveTime.after(estimatedArriveTime) || endEstimatedArriveTime
							.before(estimatedArriveTime))) {
				return null;
			}
		}

		boolean late = false;
		if (estimatedArriveTime == null || departTime == null) {
			late = false;
		} else {
			late = estimatedArriveTime.getTime() - departTime.getTime() > aging * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60;
		}
		// 设置是否晚到
		vehicleInfo.setLate(late ? FossConstants.YES : FossConstants.NO);

		// 根据车辆任务明细id查询此明细的货物信息
		List<PlatformWaybillDto> vehicleWaybills = platformDistributeDao
				.queryWaybillInfosByTaskDetailId(vehicleInfo.getTaskDetailId());

		if (CollectionUtils.isEmpty(vehicleWaybills)) {
			return vehicleInfo;
		}

		// 卡航、城运、空运票数
		int voteFlf = 0;
		int voteFsf = 0;
		int voteAf = 0;

		// 车里货物的总重量、总体积
		BigDecimal sumWeight = BigDecimal.ZERO;
		BigDecimal sumVolue = BigDecimal.ZERO;

		// 用与存放卡航和城运的运单
		List<PlatformWaybillDto> waybillInfos = new ArrayList<PlatformWaybillDto>();

		for (PlatformWaybillDto item : vehicleWaybills) {
			// 设置卡航、城运、空运票数
			String transportTypeCode = item.getTransportTypeCode();
			if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT
					.equals(transportTypeCode)) {
				voteFlf++;
			} else if (ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT
					.equals(transportTypeCode)) {
				voteFsf++;
			} else if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
					.equals(transportTypeCode)) {
				voteAf++;
			}

			BigDecimal weight = item.getHandoverWeight();
			BigDecimal volumn = item.getHandoverVolumn();

			if (weight != null) {
				sumWeight = sumWeight.add(weight);
			}
			if (volumn != null) {
				sumVolue = sumVolue.add(volumn);
			}

			if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT
					.equals(transportTypeCode)
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT
							.equals(transportTypeCode)
					|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG
							.equals(transportTypeCode)
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG
							.equals(transportTypeCode)) {

				// 设置运单的下一部门
				String nextDeptCode = getNextDeptCode(item);
				item.setNextDeptCode(nextDeptCode);

				waybillInfos.add(item);
			}
		}
		// 设置卡航、城运、空运票数
		vehicleInfo.setVoteFlf(voteFlf);
		vehicleInfo.setVoteFsf(voteFsf);
		vehicleInfo.setVoteAf(voteAf);

		// 设置载重体积
		String weightAndVolumn = sumWeight + "千克/" + sumVolue + "立方";
		vehicleInfo.setWeightAndVolumn(weightAndVolumn);

		if (unloadEfficiency == null) {
			return vehicleInfo;
		}

		// 获取卸车重量、体积标准(分钟/吨 、分钟/方)
		BigDecimal weightStd = unloadEfficiency.getUnloadWeightStd();
		BigDecimal volumeStd = unloadEfficiency.getUnloadVolumeStd();

		// 卸车用时(分钟)
		BigDecimal unloadTime = (weightStd.multiply(sumWeight).divide(
				new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_1000)).max(volumeStd.multiply(sumVolue)))
				.setScale(0, BigDecimal.ROUND_HALF_UP);

		// 设置卸车用时
		String time4UnloadTake = unloadTime.longValue() / ConstantsNumberSonar.SONAR_NUMBER_60 + "小时"
				+ unloadTime.longValue() % ConstantsNumberSonar.SONAR_NUMBER_60 + "分钟";
		vehicleInfo.setTime4UnloadTake(time4UnloadTake);

		// 预计出发时间=预计到达时间+卸车用时
		if (estimatedArriveTime != null) {
			Date date = DateUtils.addDate(estimatedArriveTime, Calendar.MINUTE,
					unloadTime.intValue());

			// 设置预计出发时间
			vehicleInfo.setEstimatedDepartTime(date);
		}

		// 将车里的运单按下一部门(从当前外场出发)分组；
		Map<String, Integer> waybillsGrouped = groupWaybillsByNextDeptCode(waybillInfos);

		// 可及时中转票数
		int voteTansferInTime = getTotalVotesTransferInTime(
				vehicleInfo.getDestDeptCode(), waybillsGrouped,
				estimatedArriveTime, unloadTime.intValue());

		// 设置可及时中转票数
		vehicleInfo.setVoteTransferInTime(voteTansferInTime);

		return vehicleInfo;
	}

	/**
	 * 未到达公司车参数处理
	 * 
	 * @param qcDto
	 * @date 2014-4-18
	 * @author Ouyang
	 */
	private void handleParam4CompanyOnTheWay(PlatformDistributeQcDto qcDto) {

		qcDto.setVehicleOwnerType(PlatformDispatchConstants.COMPANY);
		qcDto.setStatus(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);

		if (StringUtils.isEmpty(qcDto.getHandoverNo())) {
			int minEstimatedArrive = qcDto.getMinEstimatedArrive();

			Date date = new Date();

			// 设置预计到达时间
			Date estimatedArriveTime = DateUtils.addDate(date, Calendar.MINUTE,
					minEstimatedArrive);
			qcDto.setBeginEstimatedArriveTime(date);
			qcDto.setEndEstimatedArriveTime(estimatedArriveTime);
		}

		// 当交接编号或车牌不为空时，忽略查询条件的预计到达时间
		if (StringUtils.isNotEmpty(qcDto.getHandoverNo())
				|| StringUtils.isNotEmpty(qcDto.getVehicleNo())) {
			qcDto.setBeginEstimatedArriveTime(null);
			qcDto.setEndEstimatedArriveTime(null);
		}
	}

	/**
	 * 查询未到达公司车
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@Override
	public List<PlatformVehicleInfoDto> queryCompanyOnTheWay(
			PlatformDistributeQcDto qcDto, int start, int limit) {

		List<PlatformVehicleInfoDto> result = new ArrayList<PlatformVehicleInfoDto>();

		// 未到达公司车参数处理
		handleParam4CompanyOnTheWay(qcDto);

		// 查询未到达公司车
		List<PlatformVehicleInfoDto> comanyOnTheWay = platformDistributeDao
				.queryCompanyOnTheWay(qcDto, start, limit);

		// 查询外场卸车效率
		LoadAndUnloadEfficiencyTonEntity unloadEfficiency = getUnloadEfficiency(qcDto
				.getDestDeptCode());

		// 循环填充其他属性(预计到达时间、可及时中转票数、卡航、城运、空运票数、预计到达情况)
		for (PlatformVehicleInfoDto item : comanyOnTheWay) {
			PlatformVehicleInfoDto info = fillOtherPropOnTheWay(item,
					unloadEfficiency, qcDto.getBeginEstimatedArriveTime(),
					qcDto.getEndEstimatedArriveTime());
			if (info != null) {

				result.add(info);
			}

		}

		return result;
	}

	/**
	 * 查询未到达公司车数量
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@Override
	public Long queryCntCompanyOnTheWay(PlatformDistributeQcDto qcDto) {
		// 未到达公司车参数处理
		handleParam4CompanyOnTheWay(qcDto);

		return platformDistributeDao.queryCntCompanyOnTheWay(qcDto);
	}

	/**
	 * 未到达外请车参数处理
	 * 
	 * @param qcDto
	 * @date 2014-4-18
	 * @author Ouyang
	 */
	private void handleParam4LeasedOnTheWay(PlatformDistributeQcDto qcDto) {
		qcDto.setVehicleOwnerType(PlatformDispatchConstants.LEASED);
		qcDto.setStatus(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);

		if (StringUtils.isEmpty(qcDto.getHandoverNo())) {
			int minEstimatedArrive = qcDto.getMinEstimatedArrive();

			Date date = new Date();

			// 设置预计到达时间
			Date estimatedArriveTime = DateUtils.addDate(date, Calendar.MINUTE,
					minEstimatedArrive);

			qcDto.setBeginEstimatedArriveTime(date);
			qcDto.setEndEstimatedArriveTime(estimatedArriveTime);
		}

		// 当交接编号或车牌不为空时，忽略查询条件的预计到达时间
		if (StringUtils.isNotEmpty(qcDto.getHandoverNo())
				|| StringUtils.isNotEmpty(qcDto.getVehicleNo())) {
			qcDto.setBeginEstimatedArriveTime(null);
			qcDto.setEndEstimatedArriveTime(null);
		}
	}

	/**
	 * 查询未到达外请车
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@Override
	public List<PlatformVehicleInfoDto> queryLeasedOnTheWay(
			PlatformDistributeQcDto qcDto, int start, int limit) {

		List<PlatformVehicleInfoDto> result = new ArrayList<PlatformVehicleInfoDto>();

		// 未到达外请车参数处理
		handleParam4LeasedOnTheWay(qcDto);

		// 查询未到达外请车
		List<PlatformVehicleInfoDto> leasedOnTheWay = platformDistributeDao
				.queryLeasedOnTheWay(qcDto, start, limit);
		// 查询外场卸车效率
		LoadAndUnloadEfficiencyTonEntity unloadEfficiency = getUnloadEfficiency(qcDto
				.getDestDeptCode());

		// 循环填充其他属性(预计到达时间、可及时中转票数、卡航、城运、空运票数、预计到达情况)
		for (PlatformVehicleInfoDto item : leasedOnTheWay) {
			PlatformVehicleInfoDto info = fillOtherPropOnTheWay(item,
					unloadEfficiency, qcDto.getBeginEstimatedArriveTime(),
					qcDto.getEndEstimatedArriveTime());
			if (info != null) {

				result.add(info);
			}
		}
		return result;
	}

	/**
	 * 查询未到达外请车数量
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@Override
	public Long queryCntLeasedOnTheWay(PlatformDistributeQcDto qcDto) {
		// 未到达外请车参数处理
		handleParam4LeasedOnTheWay(qcDto);

		return platformDistributeDao.queryCntLeasedOnTheWay(qcDto);
	}

	/**
	 * 查询未到达公司车(带交接编号,交接单号或配载单号)
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-10
	 * @author Ouyang
	 */
	@Override
	public PlatformVehicleInfoDto queryCompanyOnTheWayWithNum(
			PlatformDistributeQcDto qcDto) {
		// 未到达公司车参数处理
		handleParam4CompanyOnTheWay(qcDto);

		// 根据交接编号查询车辆任务明细id
		String taskDetailId = platformDistributeDao
				.queryTaskDetailIdOnTheWayWithNum(qcDto);

		if (StringUtils.isEmpty(taskDetailId)) {
			return null;
		}

		PlatformVehicleInfoDto vehicleInfo = platformDistributeDao
				.queryVehicleInfoByTaskDetailId(taskDetailId);

		// 查询外场卸车效率
		LoadAndUnloadEfficiencyTonEntity unloadEfficiency = getUnloadEfficiency(qcDto
				.getDestDeptCode());

		PlatformVehicleInfoDto result = fillOtherPropOnTheWay(vehicleInfo,
				unloadEfficiency, null, null);

		return result;
	}

	/**
	 * 查询未到达外请车(带交接编号,交接单号或配载单号)
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-10
	 * @author Ouyang
	 */
	@Override
	public PlatformVehicleInfoDto queryLeasedOnTheWayWithNum(
			PlatformDistributeQcDto qcDto) {
		// 未到达外请车参数处理
		handleParam4LeasedOnTheWay(qcDto);

		// 根据交接编号查询车辆任务明细id
		String taskDetailId = platformDistributeDao
				.queryTaskDetailIdOnTheWayWithNum(qcDto);

		if (StringUtils.isEmpty(taskDetailId)) {
			return null;
		}

		PlatformVehicleInfoDto vehicleInfo = platformDistributeDao
				.queryVehicleInfoByTaskDetailId(taskDetailId);

		// 查询外场卸车效率
		LoadAndUnloadEfficiencyTonEntity unloadEfficiency = getUnloadEfficiency(qcDto
				.getDestDeptCode());

		PlatformVehicleInfoDto result = fillOtherPropOnTheWay(vehicleInfo,
				unloadEfficiency, null, null);

		return result;
	}

	/**
	 * 查询到达未分配(带交接编号,交接单号或配载单号)
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-18
	 * @author Ouyang
	 */
	@Override
	public PlatformVehicleInfoDto queryArrivedWithNum(
			PlatformDistributeQcDto qcDto) {
		// 到达未分配参数处理
		handleParam4Arrived(qcDto);

		String taskDetailId = platformDistributeDao
				.queryTaskDetailIdArrivedByNum(qcDto);

		if (StringUtils.isEmpty(taskDetailId)) {
			return null;
		}

		PlatformVehicleInfoDto vehicleInfo = platformDistributeDao
				.queryVehicleInfoByTaskDetailId(taskDetailId);

		// 查询外场卸车效率
		LoadAndUnloadEfficiencyTonEntity unloadEfficiency = getUnloadEfficiency(qcDto
				.getDestDeptCode());

		PlatformVehicleInfoDto result = fillOtherPropArrived(vehicleInfo,
				unloadEfficiency, new Date());

		return result;
	}

	/**
	 * 填充到达未分配其他属性
	 * 
	 * @param vehicleInfo
	 *            车辆信息
	 * @param unloadEfficiency
	 *            卸车效率
	 * @param nowDate
	 *            当前时间
	 * @return
	 * @date 2014-4-11
	 * @author Ouyang
	 */
	private PlatformVehicleInfoDto fillOtherPropArrived(
			PlatformVehicleInfoDto vehicleInfo,
			LoadAndUnloadEfficiencyTonEntity unloadEfficiency, Date nowDate) {

		if (vehicleInfo == null) {
			return null;
		}

		// 根据车牌号查询车辆信息
		VehicleAssociationDto vehicleAssociationDto = vehicleService
				.queryVehicleAssociationDtoByVehicleNo(vehicleInfo
						.getVehicleNo());

		if (vehicleAssociationDto != null
				&& StringUtils.isNotEmpty(vehicleAssociationDto
						.getVehicleLengthCode())) {
			// 设置车型
			vehicleInfo.setVehicleLengthCode(vehicleAssociationDto
					.getVehicleLengthCode());
			vehicleInfo.setVehicleLengthName(vehicleAssociationDto
					.getVehicleLengthName());
		}

		// 设置等待时长(分钟)
		int minWait = (int) ((nowDate.getTime() - vehicleInfo
				.getActualArriveTime().getTime()) / (ConstantsNumberSonar.SONAR_NUMBER_1000 * ConstantsNumberSonar.SONAR_NUMBER_60));
		String timeWait = minWait / ConstantsNumberSonar.SONAR_NUMBER_60 + "小时" + minWait % ConstantsNumberSonar.SONAR_NUMBER_60 + "分钟";

		vehicleInfo.setTimeWait(timeWait);

		// 根据车辆任务明细id查询此明细的货物信息
		List<PlatformWaybillDto> vehicleWaybills = platformDistributeDao
				.queryWaybillInfosByTaskDetailId(vehicleInfo.getTaskDetailId());

		if (CollectionUtils.isEmpty(vehicleWaybills)) {
			return vehicleInfo;
		}

		// 卡航、城运、空运票数
		int voteFlf = 0;
		int voteFsf = 0;
		int voteAf = 0;

		// 车里货物的总重量、总体积
		BigDecimal sumWeight = BigDecimal.ZERO;
		BigDecimal sumVolue = BigDecimal.ZERO;

		// 用与存放卡航和城运的运单
		List<PlatformWaybillDto> waybillInfos = new ArrayList<PlatformWaybillDto>();

		for (PlatformWaybillDto item : vehicleWaybills) {
			// 设置卡航、城运、空运票数
			String transportTypeCode = item.getTransportTypeCode();
			if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT
					.equals(transportTypeCode)) {
				voteFlf++;
			} else if (ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT
					.equals(transportTypeCode)) {
				voteFsf++;
			} else if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
					.equals(transportTypeCode)) {
				voteAf++;
			}

			BigDecimal weight = item.getHandoverWeight();
			BigDecimal volumn = item.getHandoverVolumn();

			if (weight != null) {
				sumWeight = sumWeight.add(weight);
			}
			if (volumn != null) {
				sumVolue = sumVolue.add(volumn);
			}

			if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT
					.equals(transportTypeCode)
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT
							.equals(transportTypeCode)
					|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG
							.equals(transportTypeCode)
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG
							.equals(transportTypeCode)) {

				// 设置运单的下一部门
				String nextDeptCode = getNextDeptCode(item);
				item.setNextDeptCode(nextDeptCode);

				waybillInfos.add(item);
			}
		}
		// 设置卡航、城运、空运票数
		vehicleInfo.setVoteFlf(voteFlf);
		vehicleInfo.setVoteFsf(voteFsf);
		vehicleInfo.setVoteAf(voteAf);

		// 设置载重体积
		String weightAndVolumn = sumWeight + "千克/" + sumVolue + "立方";
		vehicleInfo.setWeightAndVolumn(weightAndVolumn);

		if (unloadEfficiency == null) {
			return vehicleInfo;
		}

		// 获取卸车重量、体积标准(分钟/吨 、分钟/方)
		BigDecimal weightStd = unloadEfficiency.getUnloadWeightStd();
		BigDecimal volumeStd = unloadEfficiency.getUnloadVolumeStd();

		// 卸车用时(分钟)
		BigDecimal unloadTime = (weightStd.multiply(sumWeight).divide(
				new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_1000)).max(volumeStd.multiply(sumVolue)))
				.setScale(0, BigDecimal.ROUND_HALF_UP);

		// 设置卸车用时
		String time4UnloadTake = unloadTime.longValue() / ConstantsNumberSonar.SONAR_NUMBER_60 + "小时"
				+ unloadTime.longValue() % ConstantsNumberSonar.SONAR_NUMBER_60 + "分钟";
		vehicleInfo.setTime4UnloadTake(time4UnloadTake);

		// 预计出发时间=当前时间+卸车用时
		Date date = DateUtils.addDate(nowDate, Calendar.MINUTE,
				unloadTime.intValue());

		// 设置预计出发时间
		vehicleInfo.setEstimatedDepartTime(date);

		// 将车里的运单按下一部门(从当前外场出发)分组；
		Map<String, Integer> waybillsGrouped = groupWaybillsByNextDeptCode(waybillInfos);

		// 可及时中转票数
		int voteTansferInTime = getTotalVotesTransferInTime(
				vehicleInfo.getDestDeptCode(), waybillsGrouped, nowDate,
				unloadTime.intValue());

		// 设置可及时中转票数
		vehicleInfo.setVoteTransferInTime(voteTansferInTime);

		return vehicleInfo;
	}

	/**
	 * 已到达未分配参数处理
	 * 
	 * @param qcDto
	 * @date 2014-4-18
	 * @author Ouyang
	 */
	private void handleParam4Arrived(PlatformDistributeQcDto qcDto) {
		qcDto.setStatus(TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED);

		Date useStartTime = DateUtils.addDate(new Date(), Calendar.DAY_OF_YEAR,
				-1);
		qcDto.setUseStartTime(useStartTime);
	}

	/**
	 * 查询已到达未分配
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@Override
	public List<PlatformVehicleInfoDto> queryArrived(
			PlatformDistributeQcDto qcDto, int start, int limit) {

		List<PlatformVehicleInfoDto> result = new ArrayList<PlatformVehicleInfoDto>();

		// 已到达未分配参数处理
		handleParam4Arrived(qcDto);

		List<PlatformVehicleInfoDto> arrived = platformDistributeDao
				.queryArrived(qcDto, start, limit);

		// 查询外场卸车效率
		LoadAndUnloadEfficiencyTonEntity unloadEfficiency = getUnloadEfficiency(qcDto
				.getDestDeptCode());

		Date nowDate = new Date();

		// 循环填充其他属性(预计到达时间、可及时中转票数、卡航、城运、空运票数、等待时长)
		for (PlatformVehicleInfoDto item : arrived) {
			PlatformVehicleInfoDto info = fillOtherPropArrived(item,
					unloadEfficiency, nowDate);
			if (info != null) {
				result.add(info);
			}
		}

		return result;
	}

	/**
	 * 查询已到达未分配数量
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@Override
	public Long queryCntArrived(PlatformDistributeQcDto qcDto) {
		// 已到达未分配参数处理
		handleParam4Arrived(qcDto);
		return platformDistributeDao.queryCntArrived(qcDto);
	}

	/**
	 * 根据外场、车型、类型查询合适的月台
	 * 
	 * @param tfrCtrCode
	 *            外场
	 * @param vehicleLengthCode
	 *            车型
	 * @param businessType
	 *            长途、短途、接送货
	 * @return
	 * @date 2014-4-12
	 * @author Ouyang
	 */
	private List<PlatformEntity> getSuitablePlatforms(String tfrCtrCode,
			String vehicleLengthCode, String businessType) {

		LOGGER.info("开始根据外场、车型、类型查询合适的月台,tfrCtrCode:" + tfrCtrCode
				+ ",vehicleLengthCode:" + vehicleLengthCode + ",businessType"
				+ businessType);

		String longDistance = TaskTruckConstant.BUSINESS_TYPE_LONG_DISTANCE
				.equals(businessType) ? FossConstants.YES : FossConstants.NO;

		String shortDistance = TaskTruckConstant.BUSINESS_TYPE_SHORT_DISTANCE
				.equals(businessType) ? FossConstants.YES : FossConstants.NO;

		// 调用综合接口，根据外场、车型、类型找到合适的月台
		List<PlatformEntity> result = platformService
				.queryPlatformListByVehicleType2(tfrCtrCode, vehicleLengthCode,
						longDistance, shortDistance, FossConstants.NO);

		LOGGER.info("结束根据外场、车型、类型查询合适的月台,tfrCtrCode:" + tfrCtrCode
				+ ",vehicleLengthCode:" + vehicleLengthCode + ",businessType"
				+ businessType);

		return result;
	}

	/**
	 * 获取月台状态； 若查询条件只显示空月台，则为0或1(预分配也相当与被占用)；若查询条件非只显示空月台，则0、1或2
	 * 
	 * @param suitablePlatform
	 *            外场、车型满足的月台
	 * @param qcDto
	 * @return 0被占用，1空闲，2预分配(预分配只针对非只显示空月台)
	 * @date 2014-4-12
	 * @author Ouyang
	 */
	private String getPlatformStatus(PlatformEntity suitablePlatform,
			PlatformQcDto qcDto) {

		String transferCode = suitablePlatform.getOrganizationCode();
		String virtualCode = suitablePlatform.getVirtualCode();

		LOGGER.info("开始获取月台状态,transferCode:" + transferCode + ",virtualCode:"
				+ virtualCode);

		PlatformDistributeEntity entity = new PlatformDistributeEntity();
		// 外场
		entity.setTransferCenterNo(transferCode);
		// 月台虚拟code
		entity.setPlatformNo(virtualCode);
		// 状态 正在使用
		entity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING);
		// 使用开始时间 业务规则规定 若使用开始时间大于等于当前时间减1天表示 正在使用
		Date useStartTime = DateUtils.addDate(new Date(), Calendar.DAY_OF_YEAR,
				-1);
		entity.setUseStartTime(useStartTime);

		// 先判断月台是否正在使用，使用开始时间大于等于当前时间减一天
		if (platformDistributeDao.queryCntPlatformDistribute(entity) >= 1) {
			LOGGER.info("transferCode:" + transferCode + ",virtualCode:"
					+ virtualCode + ",月台状态为0");
			return "0";
		}

		// 再判断月台是否被计划停靠，且计划停靠的结束时间大于等于在途车的预计到时间(到达车为当前时间)
		entity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_AVAILABLE);
		// 计划使用结束时间
		Date useEndTime = new Date();
		// 若是在途车，则当月台的计划使用结束时间大于等于车辆预计到达时间，则表示月台不可用
		if (TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY.equals(qcDto
				.getStatus()) && qcDto.getEstimatedArriveTime() != null) {
			useEndTime = qcDto.getEstimatedArriveTime();
		}

		entity.setUseEndTime(useEndTime);

		// 月台是否被计划停靠(即预分配)
		boolean isPre = platformDistributeDao
				.queryCntPlatformDistribute(entity) >= 1;

		LOGGER.info("transferCode:" + transferCode + ",virtualCode:"
				+ virtualCode + ",月台状态被预分配:" + isPre);

		LOGGER.info("结束获取月台状态,transferCode:" + transferCode + ",virtualCode:"
				+ virtualCode + "qcDto.getOnlyFree():" + qcDto.getOnlyFree());

		// 再判断是否只显示空月台
		if (FossConstants.YES.equals(qcDto.getOnlyFree())) {
			// 若只显示空月台，则预分配也相当与被占用，月台不在最优月台考虑之列
			return isPre ? "0" : "1";
		} else {
			return isPre ? "2" : "1";
		}
	}

	/**
	 * 剔除掉platformEntities中现在被占用的月台，得到可被分配的月台
	 * 
	 * @param suitablePlatforms
	 *            根据外场和车辆查询出来的合适的月台
	 * @param qcDto
	 * @return
	 * @date 2014-4-12
	 * @author Ouyang
	 */
	private List<PlatformDto> getUseablePlatforms(
			List<PlatformEntity> suitablePlatforms, PlatformQcDto qcDto) {

		LOGGER.info("开始剔除被占用月台，得到可被分配的月台");

		if (CollectionUtils.isEmpty(suitablePlatforms) || qcDto == null) {
			return null;
		}

		List<PlatformDto> useablePlatforms = new ArrayList<PlatformDto>();

		for (PlatformEntity item : suitablePlatforms) {
			// 得到合适月台的当前占用状态
			String platformStatus = getPlatformStatus(item, qcDto);

			if (!"0".equals(platformStatus)) {
				PlatformDto platformDto = new PlatformDto();
				// 外场
				platformDto.setTransferCenterCode(qcDto.getDestDeptCode());
				// 月台虚拟编码
				platformDto.setVirtualCode(item.getVirtualCode());
				// 月台号
				platformDto.setPlatformCode(item.getPlatformCode());
				// 月台类型(长途、短途、接送货)
				platformDto
						.setPlatformType(LoadConstants.LOAD_TASK_TYPE_LONG_DISTANCE
								.equals(qcDto.getBusinessType()) ? "长途" : "短途");
				// 最大可停用车型
				platformDto.setVehicleLengthName(item.getVehicleName());
				// 是否有升降台
				platformDto.setHasLift(item.getHasLift());
				// 状态(计划停靠、正在使用)
				platformDto.setStatus(platformStatus);

				useablePlatforms.add(platformDto);
			}
		}

		LOGGER.info("结束剔除被占用月台，得到可被分配的月台");

		return useablePlatforms;
	}

	/**
	 * 获取车辆任务明细对应的运单从月台到待叉区再到各货区所需的总功
	 * 
	 * @param platform
	 *            月台
	 * @param waybills
	 *            车辆任务明细里的运单
	 * @param manSpeed
	 *            人工速度
	 * @param forkSpeed
	 *            电叉速度
	 * @return
	 * @date 2014-4-14
	 * @author Ouyang
	 */
	private BigDecimal getPlatformForkAreaGoodsAreaWork(PlatformDto platform,
			List<PlatformWaybillDto> waybills, BigDecimal manSpeed,
			BigDecimal forkSpeed) {

		LOGGER.info("getPlatformForkAreaGoodsAreaWork运单从月台到待叉区再到各货区所需的总功,manSpeed:"
				+ manSpeed + ",forkSpeed:" + forkSpeed);

		if (platform == null || CollectionUtils.isEmpty(waybills)
				|| manSpeed == null || forkSpeed == null) {
			return null;
		}

		// 月台编码
		String platformCode = platform.getPlatformCode();
		// 月台所属外场
		String tfrCtrCode = platform.getTransferCenterCode();

		// 调用综合接口，根据月台查询对应的待叉区及月台到待叉区距离
		List<WaitForkAreaDistanceEntity> platformForkAreaDistanceEntity = waitForkAreaService
				.queryDistanceBetweenPlatform(tfrCtrCode, platformCode);

		if (CollectionUtils.isEmpty(platformForkAreaDistanceEntity)) {
			LOGGER.info("waitForkAreaService.queryDistanceBetweenPlatform调用综合接口，根据月台查询对应的待叉区及月台到待叉区距离为空;tfrCtrCode:"
					+ tfrCtrCode + ",platformCode" + platformCode);
			return null;
		}

		// 车辆任务明细对应的运单从月台到各货区所需的总功
		BigDecimal sumWork = null;

		for (PlatformWaybillDto waybill : waybills) {
			// 运单从当前外场出发，下一部门
			String nextDeptCode = getNextDeptCode(waybill);
			// 产品
			String productCode = waybill.getTransportTypeCode();
			// 根据外场、到达部门、产品查询对应货区
			GoodsAreaEntity goodsAreaEntity = goodsAreaService
					.queryGoodsAreaByArriveRegionCode(tfrCtrCode, nextDeptCode,
							productCode);
			if (goodsAreaEntity == null) {
				continue;
			}

			// 货区编码
			String goodsAreaCode = goodsAreaEntity.getGoodsAreaCode();
			if (StringUtils.isEmpty(goodsAreaCode)) {
				continue;
			}

			// 各运单到对应货区的功；取(运单到待叉区A+待叉区A到货区),(运单到待叉区B+待叉区B到货区)的小者
			BigDecimal work = null;

			for (WaitForkAreaDistanceEntity item : platformForkAreaDistanceEntity) {
				// 月台到待叉区距离,及理货员需要走的距离
				BigDecimal manDistance = StringUtils
						.isEmpty(item.getDistance()) ? null : new BigDecimal(
						item.getDistance());

				// 待叉区编码
				String forkAreaCode = item.getWaitForkAreaCode();

				if (manDistance == null || StringUtils.isEmpty(forkAreaCode)) {
					continue;
				}

				// 调用综合接口，查询待叉区到货区的距离
				WaitForkAreaDistanceEntity forkAreaGoodsAreaDistanceEntity = waitForkAreaService
						.queryDistanceBetweenGoodsArea(tfrCtrCode,
								goodsAreaCode, forkAreaCode);

				if (forkAreaGoodsAreaDistanceEntity == null) {
					continue;
				}

				// 待叉区到货区的距离,及叉车需要走的距离
				BigDecimal forkDistance = StringUtils
						.isEmpty(forkAreaGoodsAreaDistanceEntity.getDistance()) ? null
						: new BigDecimal(
								forkAreaGoodsAreaDistanceEntity.getDistance());

				if (forkDistance == null) {
					continue;
				}

				BigDecimal manWork = manDistance.divide(manSpeed, 1,
						BigDecimal.ROUND_HALF_UP);
				BigDecimal forkWork = forkDistance.divide(forkSpeed, 1,
						BigDecimal.ROUND_HALF_UP);

				work = work == null ? manWork.add(forkWork) : work.min(manWork
						.add(forkWork));
			}

			if (work == null) {
				continue;
			}

			// 车辆任务明细里的所有运单从xx月台到各货区所需的总功
			sumWork = sumWork == null ? work : sumWork.add(work);
		}

		LOGGER.info("getPlatformForkAreaGoodsAreaWork运单从月台到待叉区再到各货区所需的总功,sumWork:"
				+ sumWork);

		return sumWork;
	}

	/**
	 * 获取车辆任务明细对应的运单从月台到各货区所需的总功
	 * 
	 * @param platform
	 *            月台
	 * @param waybills
	 *            车辆任务明细里的运单
	 * @return
	 * @date 2014-4-14
	 * @author Ouyang
	 */
	private BigDecimal getPlatformGoodsAreaWork(PlatformDto platform,
			List<PlatformWaybillDto> waybills) {

		if (platform == null || CollectionUtils.isEmpty(waybills)) {
			return null;
		}

		// 月台编码
		String platformCode = platform.getPlatformCode();
		// 月台所属外场
		String tfrCtrCode = platform.getTransferCenterCode();

		LOGGER.info("getPlatformGoodsAreaWork运单从月台到各货区所需的总功,platformCode:"
				+ platformCode + ",tfrCtrCode:" + tfrCtrCode);

		// 车辆任务明细对应的运单从月台到各货区所需的总功
		BigDecimal sumWork = null;

		for (PlatformWaybillDto waybill : waybills) {
			// 运单从当前外场出发，下一部门
			String nextDeptCode = getNextDeptCode(waybill);
			// 产品
			String productCode = waybill.getTransportTypeCode();
			// 根据外场、到达部门、产品查询对应货区
			GoodsAreaEntity goodsAreaEntity = goodsAreaService
					.queryGoodsAreaByArriveRegionCode(tfrCtrCode, nextDeptCode,
							productCode);
			if (goodsAreaEntity == null) {
				LOGGER.info("goodsAreaService.queryGoodsAreaByArriveRegionCode为空;tfrCtrCode:"
						+ tfrCtrCode
						+ ",nextDeptCode:"
						+ nextDeptCode
						+ ",productCode" + productCode);
				continue;
			}

			// 货区编码
			String goodsAreaCode = goodsAreaEntity.getGoodsAreaCode();

			// 查询月台到货区的距离
			List<BigDecimal> distances = platformDistributeDao
					.findPlatformGoodsAreaDistance(platform.getVirtualCode(),
							goodsAreaEntity.getVirtualCode());

			if (CollectionUtils.isEmpty(distances)) {
				continue;
			}

			BigDecimal distance = distances.get(0);

			if (waybill.getHandoverWeight() == null || distance == null) {
				LOGGER.info("platformService.queryDistanceByOrganizationPlatformGoodsarea为空;tfrCtrCode:"
						+ tfrCtrCode
						+ ",goodsAreaCode:"
						+ goodsAreaCode
						+ ",platformCode");
				continue;
			}
			// 运单从xx月台到xx货区所需的功
			BigDecimal work = waybill.getHandoverWeight().multiply(distance);

			// 车辆任务明细里的所有运单从xx月台到各货区所需的总功
			sumWork = sumWork == null ? work : sumWork.add(work);
		}

		LOGGER.info("getPlatformGoodsAreaWork运单从月台到各货区所需的总功,sumWork:" + sumWork);

		return sumWork;
	}

	/**
	 * 从可用月台中选取最优的最多5个月台
	 * 
	 * @param destDeptCode
	 *            到达部门，即当前外场
	 * @param useablePlatforms
	 *            可分配的月台
	 * @param waybills
	 *            车辆明细对应的运单
	 * @return
	 * @date 2014-4-12
	 * @author Ouyang
	 */
	private List<PlatformDto> getOptimalPlatforms(String destDeptCode,
			List<PlatformDto> useablePlatforms,
			List<PlatformWaybillDto> waybills) {

		LOGGER.info("getOptimalPlatforms开始选取最优5月台,destDeptCode:" + destDeptCode);

		if (StringUtils.isEmpty(destDeptCode)
				|| CollectionUtils.isEmpty(useablePlatforms)
				|| CollectionUtils.isEmpty(waybills)) {
			return null;
		}

		// 根据外场code查询外场
		String[] codes = { destDeptCode };
		List<OutfieldEntity> outfields = outfieldService
				.queryOutfieldBatchByCode(codes);
		if (CollectionUtils.isEmpty(outfields)) {
			LOGGER.info("outfieldService.queryOutfieldBatchByCode为空");
			return null;
		}
		OutfieldEntity outfieldEntity = outfields.get(0);
		if (outfieldEntity == null) {
			return null;
		}

		// 外场是否含待叉区属性
		String hasForkArea = outfieldEntity.getIsHaveWaitForkArea();
		LOGGER.info("是否待叉区hasForkArea:" + hasForkArea);

		List<PlatformDto> result = new ArrayList<PlatformDto>();

		// 判断外场是否有待叉区
		if (FossConstants.YES.equals(hasForkArea)) {
			// 取得外场的人工速度、电叉速度
			double manSpeed = outfieldEntity.getManSpeed();
			double forkSpeed = outfieldEntity.getForkSpeed();

			for (PlatformDto platform : useablePlatforms) {
				// 获取车辆任务明细对应的运单从月台到待叉区再到各货区所需的总功
				BigDecimal work = getPlatformForkAreaGoodsAreaWork(platform,
						waybills, new BigDecimal(manSpeed), new BigDecimal(
								forkSpeed));
				if (work != null) {
					platform.setWork(work);
					result.add(platform);
				}
			}
		} else {
			for (PlatformDto platform : useablePlatforms) {
				// 获取车辆任务明细对应的运单从月台到各货区所需的总功
				BigDecimal work = getPlatformGoodsAreaWork(platform, waybills);
				if (work != null) {
					platform.setWork(work);
					result.add(platform);
				}
			}
		}

		if (CollectionUtils.isEmpty(result)) {
			LOGGER.info("getOptimalPlatforms选取最优5月台为空");
			return result;
		}

		// 将月台按车辆任务明细对应的运单从月台到各货区所需的总功排序
		Collections.sort(result, new Comparator<PlatformDto>() {
			@Override
			public int compare(PlatformDto o1, PlatformDto o2) {
				return o1.getWork().compareTo(o2.getWork());
			}
		});

		LOGGER.info("getOptimalPlatforms结束选取最优5月台,destDeptCode:" + destDeptCode);

		// 取功耗最小的5个
		if (result.size() > ConstantsNumberSonar.SONAR_NUMBER_5) {
			return result.subList(0, ConstantsNumberSonar.SONAR_NUMBER_5);
		} else {
			return result;
		}
	}

	/**
	 * 计算最优月台
	 * 
	 * @param taskDetailId
	 *            车辆任务明细id
	 * @param vehicleNo
	 *            车牌
	 * @param destDeptCode
	 *            车辆任务明细的到达部门，即当前外场
	 * @date 2014-4-12
	 * @author Ouyang
	 */
	@Override
	public List<PlatformDto> queryOptimalPlatform(PlatformQcDto qcDto) {

		LOGGER.info("queryOptimalPlatform开始计算最优月台");

		if (qcDto == null) {
			return null;
		}

		// 车辆任务id、车牌、类型(长途、短途)、到达部门(即当前外场)
		String taskDetailId = qcDto.getTaskDetailId();
		String vehicleNo = qcDto.getVehicleNo();
		String destDeptCode = qcDto.getDestDeptCode();
		String businessType = qcDto.getBusinessType();

		LOGGER.info("taskDetailId:" + taskDetailId + ",vehicleNo:" + vehicleNo
				+ ",destDeptCode:" + destDeptCode + ",businessType"
				+ businessType);

		if (StringUtils.isEmpty(taskDetailId)
				|| StringUtils.isEmpty(destDeptCode)
				|| StringUtils.isEmpty(vehicleNo)
				|| StringUtils.isEmpty(businessType)) {
			return null;
		}

		// 车型code
		String vehicleLengthCode = qcDto.getVehicleLengthCode();

		// 根据外场、车型、类型查询合适月台
		List<PlatformEntity> suitablePlatforms = getSuitablePlatforms(
				destDeptCode, vehicleLengthCode, businessType);

		if (CollectionUtils.isEmpty(suitablePlatforms)) {
			LOGGER.info("getSuitablePlatforms合适月台为空");
			return null;
		}

		// 再检查据外场和车型查询出来的月台集合，看哪些是被占用的,剔除后得到可分配月台
		List<PlatformDto> useablePlatforms = getUseablePlatforms(
				suitablePlatforms, qcDto);

		if (CollectionUtils.isEmpty(useablePlatforms)) {
			LOGGER.info("getUseablePlatforms剔除被占用月台，可分配月台为空");
			return null;
		}

		// 根据车辆任务明细id查询此明细的货物信息
		List<PlatformWaybillDto> waybills = platformDistributeDao
				.queryWaybillInfosByTaskDetailId(qcDto.getTaskDetailId());

		// 再从这些可分配的月台中计算最优月台
		List<PlatformDto> optimalPlatforms = getOptimalPlatforms(destDeptCode,
				useablePlatforms, waybills);

		LOGGER.info("queryOptimalPlatform结束计算最优月台");

		return optimalPlatforms;
	}

	/**
	 * 月台预分配
	 * 
	 * @param entity
	 * @date 2014-4-16
	 * @author Ouyang
	 */
	@Deprecated
	@Override
	public void predistribute(PlatformDistributeEntity entity) {

		if (entity == null) {
			throw new TfrBusinessException("请选择要预分配的月台!");
		}

		if (StringUtils.isEmpty(entity.getPlatformNo())) {
			throw new TfrBusinessException("月台虚拟编码为空，请重新选择月台号后再进行预分配!");
		}

		Date date = new Date();

		String tfrCtrCode = FossUserContext.getCurrentDeptCode();
		String tfrCtrName = FossUserContext.getCurrentDeptName();

		Map<String, String> transferCenter = queryParentTfrCtrCode(tfrCtrCode);
		if (transferCenter != null) {
			tfrCtrCode = transferCenter.get("code");
			tfrCtrName = transferCenter.get("name");
		}
		// id
		entity.setId(UUIDUtils.getUUID());
		// 外场编码
		entity.setTransferCenterNo(tfrCtrCode);
		// 外场名称
		entity.setTransferCenterName(tfrCtrName);
		// 装车任务编号
		entity.setLoadTaskNo(null);
		// 状态
		entity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_AVAILABLE);
		// 分配类型 计划
		entity.setType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_PLAN);
		// 计划来源 车辆到达
		entity.setScheduleSource(PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_ARRIVAL);

		// 车型如果为空，沿用老规矩，给个默认值,数据库有非空验证
		if (StringUtils.isBlank(entity.getVehicleModel())) {
			entity.setVehicleModel("CX0001");
		}

		entity.setCreateDate(date);
		platformDistributeDao.predistribute(entity);
	}

	/**
	 * @desc 月台预分配
	 * @param info
	 * @date 2015年4月21日 下午5:59:05
	 * @author Ouyang
	 */
	@Override
	public void preAssign(PlatformPreAssignEntity info) {

		if (info == null) {
			throw new TfrBusinessException("请选择要预分配的月台!");
		}

		if (StringUtils.isEmpty(info.getPlatformVirtualCode())) {
			throw new TfrBusinessException("月台虚拟编码为空，请重新选择月台号后再进行预分配!");
		}

		Date date = new Date();

		UserEntity currentUser = FossUserContext.getCurrentUser();
		String userCode = currentUser.getEmpCode();
		String userName = currentUser.getEmpName();

		info.setId(UUIDUtils.getUUID());
		info.setStatus("PRE");
		info.setCreateUserCode(userCode);
		info.setCreateUserName(userName);
		info.setCreateTime(date);
		info.setModifyUserCode(userCode);
		info.setModifyUserName(userName);
		info.setModifyTime(date);
		int cnt = platformDistributeDao.updatePreAssign(info);
		if (cnt == 0) {
			try {
				platformDistributeDao.preAssign(info);
			} catch (DuplicateKeyException e) {
				platformDistributeDao.updatePreAssign(info);
			}
		}

		PlatformDistributeEntity entity = new PlatformDistributeEntity();
		// 装车任务编号
		entity.setLoadTaskNo(null);
		// 状态
		entity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_AVAILABLE);
		// 分配类型 计划
		entity.setType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_PLAN);
		// 计划来源 车辆到达
		entity.setScheduleSource(PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_ARRIVAL);

		entity.setId(info.getId());
		entity.setTransferCenterNo(info.getTfrCtrCode());
		entity.setTransferCenterName(info.getTfrCtrName());
		entity.setPlatformNo(info.getPlatformVirtualCode());
		entity.setVehicleNo(info.getVehicleNo());
		entity.setVehicleModel(info.getVehicleModel());
		entity.setUseStartTime(info.getUseStartTime());
		entity.setUseEndTime(info.getUseEndTime());

		// 车型如果为空，沿用老规矩，给个默认值,数据库有非空验证
		if (StringUtils.isBlank(entity.getVehicleModel())) {
			entity.setVehicleModel("CX0001");
		}

		entity.setCreateDate(date);
		platformDistributeDao.predistribute(entity);
	}
}
