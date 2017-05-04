/**   
 * @Title: QuantityStaCommonService.java 
 * @Package com.deppon.foss.module.transfer.platform.server.service.impl 
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年8月26日 下午3:21:44 
 * @version V1.0   
 */
package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.dao.IQuantityStaDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IQuantityStaService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.QuantityStaConstants;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantitySta4ChartDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @ClassName: QuantityStaCommonService
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年8月26日 下午3:21:44
 * 
 */
public class QuantityStaService implements IQuantityStaService {

	/**
	 * 复杂组织
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 组织
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 车队
	 */
	private IMotorcadeService motorcadeService;

	private IConfigurationParamsService configurationParamsService;

	private IQuantityStaDao quantityStaDao;
	
	/**
	 * 总计时的第一列
	 */
	private final static String STA_SUM_STR = "总计";
	
	private final static Logger LOGGER = LoggerFactory.getLogger(QuantityStaService.class); 

	public void setQuantityStaDao(IQuantityStaDao quantityStaDao) {
		this.quantityStaDao = quantityStaDao;
	}

	/**
	 * @param configurationParamsService
	 *            要设置的 configurationParamsService
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param motorcadeService
	 *            要设置的 motorcadeService
	 */
	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 *            要设置的 orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService
	 *            要设置的 orgAdministrativeInfoComplexService
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @Title: queryTransferCenterCode
	 * @Description: 获取转运场code
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年8月29日 上午10:36:26
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public String[] queryTransferCenter() {
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity parentOrg = null;
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		// 查找当前员工的部门实体信息
		OrgAdministrativeInfoEntity orgs = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCodeClean(orgCode);
		// 如果当前员工属于调度组
		if (null != orgs
				&& StringUtils
						.equals(FossConstants.YES, orgs.getDispatchTeam())) {
			// 根据当前员工所在部门CODE查找顶级车队
			parentOrg = orgAdministrativeInfoComplexService
					.getTopFleetByCode(orgs.getCode());
			if (null != parentOrg
					&& StringUtils.isNotEmpty(parentOrg.getCode())) {
				// 根据顶级车队CODE查找所对应的外场
				MotorcadeEntity motorcade = motorcadeService
						.queryMotorcadeByCode(parentOrg.getCode());
				if (null != motorcade
						&& StringUtils
								.isNotEmpty(motorcade.getTransferCenter())) {
					// 返回外场CODE
					OrgAdministrativeInfoEntity tc = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByCodeClean(motorcade
									.getTransferCenter());
					return new String[] { tc.getCode(), tc.getName() };
				}
			}
			return null;
		}
		// 当前员工不属于调度组的的时候
		parentOrg = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypes);
		if (null == parentOrg || StringUtils.isEmpty(parentOrg.getCode())) {
			return null;
		} else {
			return new String[] { parentOrg.getCode(), parentOrg.getName() };
		}
	}

	/**
	 * @Title: queryStaStartAndEndTime
	 * @Description: 获取某外场的货量统计开始时间、结束时间，用于查询时显示
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年8月31日 下午2:36:42
	 * @param @param transferCenterCode
	 * @param @param dataBelongDate
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public String queryStaStartAndEndTime(String transferCenterCode) {
		ConfigurationParamsEntity entityStart = configurationParamsService
				.queryConfigurationParamsByOrgCode(
						DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
						ConfigurationParamsConstants.TFR_PARM__FORECAST_START,
						transferCenterCode);
		// 如果没有货量统计起始时间的配置参数，则抛出异常。
		if (entityStart == null
				|| StringUtils.isBlank(entityStart.getConfValue())) {
			throw new BusinessException("转运场：" + transferCenterCode
					+ "未配置货量统计起始时间");
		}
		String startTimeStr = entityStart.getConfValue();
		return startTimeStr;
	}

	/**
	 * @desc 查询出发货量
	 * @param dto
	 * @return
	 * @date 2014年9月1日下午4:00:18
	 * @author Ouyang
	 */
	@Override
	public List<QuantityStaDto> queryDepart(QuantityStaQcDto dto) {

		if (!dto.isSecondDay()) {
			return quantityStaDao.queryDepart(dto);
		}

		return quantityStaDao.query2ndDayDepart(dto);
	}
	
	@Override
	public QuantityStaDto queryDepartTotal(QuantityStaQcDto dto){
		QuantityStaDto result = null;
		if (!dto.isSecondDay()) {
			result = quantityStaDao.queryDepartTotal(dto);
		}else{
			result = quantityStaDao.queryDepart2ndTotal(dto);
		}
		if(result != null){
			result.setRelevantOrgName(STA_SUM_STR);
		}
		return result;
	}

	@Override
	public List<QuantitySta4ChartDto> queryDepart1Day4Chart(QuantityStaQcDto dto) {
		return quantityStaDao.queryDepart1Day4Chart(dto);
	}

	@Override
	public List<QuantitySta4ChartDto> queryDepartFewDays1Hh4Chart(
			QuantityStaQcDto dto) {
		return quantityStaDao.queryDepartFewDays1Hh4Chart(dto);
	}

	/**
	 * @desc 查询到达货量
	 * @param dto
	 * @return
	 * @date 2014年9月1日下午4:00:18
	 * @author Ouyang
	 */
	@Override
	public List<QuantityStaDto> queryArrive(QuantityStaQcDto dto) {

		if (!dto.isSecondDay()) {
			return quantityStaDao.queryArrive(dto);
		}

		return quantityStaDao.query2ndDayArrive(dto);
	}
	
	@Override
	public QuantityStaDto queryArriveTotal(QuantityStaQcDto dto){
		QuantityStaDto result = null;
		if (!dto.isSecondDay()) {
			result = quantityStaDao.queryArriveTotal(dto);
		}else{
			result = quantityStaDao.queryArrive2ndTotal(dto);
		}
		if(result != null){
			result.setRelevantOrgName(STA_SUM_STR);
		}
		return result;
	}

	@Override
	public List<QuantitySta4ChartDto> queryArrive1Day4Chart(QuantityStaQcDto dto) {
		return quantityStaDao.queryArrive1Day4Chart(dto);
	}

	@Override
	public List<QuantitySta4ChartDto> queryArriveFewDays1Hh4Chart(
			QuantityStaQcDto dto) {
		return quantityStaDao.queryArriveFewDays1Hh4Chart(dto);
	}

	/**
	 * @Title: expertDepart
	 * @Description: 导出货量
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年9月4日 上午11:20:21
	 * @param @param dto
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public ExportResource expertQuantity(QuantityStaQcDto dto) {

		List<QuantityStaDto> results = null;
		String relevantOrgHeader = null;

		if (QuantityStaConstants.STA_TYPE_DEPARTURE.equals(dto.getType())) {
			results = this.queryDepart(dto);
			relevantOrgHeader = "目的地";
		} else {
			results = this.queryArrive(dto);
			relevantOrgHeader = "起始地";
		}

		if (CollectionUtils.isEmpty(results)) {
			// 此处返回N，action中判断，为N时返回查询结果为空，不再导出。。。
			throw new BusinessException(FossConstants.NO);
		}
		
		//查询总计
		QuantityStaDto sumResult = null;
		if (QuantityStaConstants.STA_TYPE_DEPARTURE.equals(dto.getType())) {
			sumResult = this.queryDepartTotal(dto);
		}else{
			sumResult = this.queryArriveTotal(dto);
		}

		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> result = null;
		results.add(sumResult);

		for (QuantityStaDto temp : results) {
			result = new ArrayList<String>();
			// 起始地目的地
			result.add(temp.getRelevantOrgName());
			// 站点组
			result.add(temp.getGroupSiteName());
			// 总票数
			result.add(convertBigDecimal2String(temp.getWaybillQtyTotal()));
			// 总重量
			result.add(convertBigDecimal2String(temp.getWeightTotal()));
			// 总体积
			result.add(convertBigDecimal2String(temp.getVolumeTotal()));
			// 卡货重量
			result.add(convertBigDecimal2String(temp.getFlfWeightTotal()));
			// 卡货体积
			result.add(convertBigDecimal2String(temp.getFlfVolumeTotal()));
			// 卡货票数
			result.add(convertBigDecimal2String(temp.getFlfQtyTotal()));
			// 城货重量
			result.add(convertBigDecimal2String(temp.getFsfWeightTotal()));
			// 城货体积
			result.add(convertBigDecimal2String(temp.getFsfVolumeTotal()));
			// 城货票数
			result.add(convertBigDecimal2String(temp.getExpQtyTotal()));
			// 快递重量
			result.add(convertBigDecimal2String(temp.getExpWeightTotal()));
			// 快递体积
			result.add(convertBigDecimal2String(temp.getExpVolumeTotal()));
			// 快递票数
			result.add(convertBigDecimal2String(temp.getExpQtyTotal()));
			// 开单未交接重量
			result.add(convertBigDecimal2String(temp.getBilledWeightTotal()));
			// 开单未交接体积
			result.add(convertBigDecimal2String(temp.getBilledVolumeTotal()));
			// 开单未交接票数
			result.add(convertBigDecimal2String(temp.getBilledQtyTotal()));
			// 库存重量
			result.add(convertBigDecimal2String(temp.getInstoreWeightTotal()));
			// 库存体积
			result.add(convertBigDecimal2String(temp.getInstoreVolumeTotal()));
			// 库存票数
			result.add(convertBigDecimal2String(temp.getInstoreQtyTotal()));
			// 在途重量
			result.add(convertBigDecimal2String(temp.getIntransitWeightTotal()));
			// 在途体积
			result.add(convertBigDecimal2String(temp.getIntransitVolumeTotal()));
			// 在途票数
			result.add(convertBigDecimal2String(temp.getIntransitQtyTotal()));
			// 预测未开单重量
			result.add(convertBigDecimal2String(temp
					.getForecastUnbilledWeightTotal()));
			// 预测未开单体积
			result.add(convertBigDecimal2String(temp
					.getForecastUnbilledVolumeTotal()));
			// 预测未开单票数
			result.add(convertBigDecimal2String(temp
					.getForecastUnbilledQtyTotal()));
			// 预测总重量
			result.add(convertBigDecimal2String(temp.getForecastWeightTotal()));
			// 预测总体积
			result.add(convertBigDecimal2String(temp.getForecastVolumeTotal()));
			// 预测总票数
			result.add(convertBigDecimal2String(temp.getForecastQtyTotal()));

			rowList.add(result);
		}
		
		HeaderRows[] headerRows = {
				new HeaderRows(0, 2, 0, 0, relevantOrgHeader),
				new HeaderRows(0, 2, 1, 1, "站点组"),
				new HeaderRows(0, 0, 2, PlatformConstants.SONAR_NUMBER_23, "实际货量"),
				new HeaderRows(1, 2, 2, 2, "总票数"),
				new HeaderRows(1, 2, PlatformConstants.SONAR_NUMBER_3, PlatformConstants.SONAR_NUMBER_3, "总重量"),
				new HeaderRows(1, 2, PlatformConstants.SONAR_NUMBER_4, PlatformConstants.SONAR_NUMBER_4, "总体积"),

				new HeaderRows(1, 1, PlatformConstants.SONAR_NUMBER_5, PlatformConstants.SONAR_NUMBER_7, "卡货"),
				new HeaderRows(1, 1, PlatformConstants.SONAR_NUMBER_8, PlatformConstants.SONAR_NUMBER_10, "城货"),
				new HeaderRows(1, 1, PlatformConstants.SONAR_NUMBER_11, PlatformConstants.SONAR_NUMBER_13, "快递"),
				new HeaderRows(1, 1, PlatformConstants.SONAR_NUMBER_14, PlatformConstants.SONAR_NUMBER_16, "开单未交接"),
				new HeaderRows(1, 1, PlatformConstants.SONAR_NUMBER_17, PlatformConstants.SONAR_NUMBER_19, "库存余货"),
				new HeaderRows(1, 1, PlatformConstants.SONAR_NUMBER_20, PlatformConstants.SONAR_NUMBER_22, "在途"),

				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_23, PlatformConstants.SONAR_NUMBER_28, "预测货量"),
				new HeaderRows(1, 1, PlatformConstants.SONAR_NUMBER_23, PlatformConstants.SONAR_NUMBER_25, "预测未开单货量"),
				new HeaderRows(1, 1, PlatformConstants.SONAR_NUMBER_26, PlatformConstants.SONAR_NUMBER_28, "总货量") };

		ExportResource sheet = new ExportResource();
		sheet.setHeaderHeight(PlatformConstants.SONAR_NUMBER_3);

		sheet.setHeaderList(Arrays.asList(headerRows));
		String[] source = { "重量", "体积", "票数" };
		String[] heads = new String[PlatformConstants.SONAR_NUMBER_5];
		for (int i = 0; i <= PlatformConstants.SONAR_NUMBER_7; i++) {
			heads = (String[]) ArrayUtils.addAll(heads, source);
		}
		sheet.setHeads(heads);
		sheet.setRowList(rowList);

		return sheet;
	}

	/**
	 * @Title: convertBigDecimal2String
	 * @Description : 将bigdecimal转化为string
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年9月4日 上午11:30:42
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private String convertBigDecimal2String(BigDecimal num) {
		return num == null ? BigDecimal.ZERO.toString() : num.toString();
	}

	/**
	 * @Title: queryWarnWeightAndVolume
	 * @Description: 获取警戒值(重量、体积)
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年9月7日 下午4:19:02
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public BigDecimal[] queryWarnWeightAndVolume(String transferCenterCode) {
		// 警戒重量
		ConfigurationParamsEntity weightEntity = configurationParamsService
				.queryConfigurationParamsByOrgCode(
						DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
						ConfigurationParamsConstants.TFR_PARM__WARN_WEIGHT,
						transferCenterCode);
		// 警戒体积
		ConfigurationParamsEntity volumeEntity = configurationParamsService
				.queryConfigurationParamsByOrgCode(
						DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
						ConfigurationParamsConstants.TFR_PARM__WARN_VOLUME,
						transferCenterCode);
		// 定义警戒重量、体积
		BigDecimal weightWarn = BigDecimal.ZERO;
		BigDecimal volumeWarn = BigDecimal.ZERO;
		if (weightEntity != null
				&& StringUtils.isNotBlank(weightEntity.getConfValue())) {
			try {
				weightWarn = new BigDecimal(weightEntity.getConfValue());
			} catch (Exception e) {
				LOGGER.info("QuantityStaService.queryWarnWeightAndVolume 报错:" + StringUtils.substring(e.toString(), 0, 100));
			}
		}
		if (volumeEntity != null
				&& StringUtils.isNotBlank(volumeEntity.getConfValue())) {
			try {
				volumeWarn = new BigDecimal(volumeEntity.getConfValue());
			} catch (Exception e) {
				LOGGER.info("QuantityStaService.queryWarnWeightAndVolume 报错:" + StringUtils.substring(e.toString(), 0, 100));
			}
		}

		return new BigDecimal[] { weightWarn, volumeWarn };
	}

	/**
	 * @desc 查询第2天出发派送预测货量，提供给查询派送货量接口
	 * @param transferCenterCode
	 *            外场编码，当此参数为空时，即查询所有外场的数据
	 * @param staDate
	 *            统计日期；如今天是2014-09-16，则接口是查询2014-09-17的出发派送预测货量，
	 *            此参数传入2014-09-16，时分秒毫秒为0
	 * @return 
	 *         外场出发派送预测货量集合；QuantityStaDto实体中有orgCode外场编码，forecastWeightTotal预测重量
	 *         ，forecastVolumeTotal预测体积，forecastQtyTotal预测票数；
	 *         当某外场没有对应数据时，对应的QuantityStaDto实体为空
	 * @date 2014年9月16日 下午3:05:46
	 * @author Ouyang
	 */
	@Override
	public List<QuantityStaDto> query2ndDepartDeliverFcst(
			String transferCenterCode, Date staDate) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("transferCenterCode", transferCenterCode);
		map.put("staDate", PlatformUtils.getFirstMomentOfDay(staDate));
		return quantityStaDao.query2ndDepartDeliverFcst(map);
	}

	/**
	 * @desc 查询某天总预测货量，提供给仓库饱和度接口
	 * @param transferCenterCode
	 *            外场编码(当此参数为空时，即查询所有外场的数据)
	 * @param staDate
	 *            统计日期；staDate只要年月日，时分秒毫秒为0
	 * @return 
	 *         外场出发派送预测货量集合；QuantityStaDto实体中有orgCode外场编码，forecastWeightTotal预测重量
	 *         ，forecastVolumeTotal预测体积，forecastQtyTotal预测票数；
	 *         当某外场没有对应数据时，对应的QuantityStaDto实体为空
	 * @date 2014年9月16日 下午3:43:27
	 * @author Ouyang
	 */
	@Override
	public List<QuantityStaDto> queryTotalFcst(String transferCenterCode,
			Date staDate) {

		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_4);
		map.put("transferCenterCode", transferCenterCode);

		Date date = PlatformUtils.getFirstMomentOfDay(new Date());

		if (staDate.compareTo(date) == 1) {
			map.put("staDate", date);
			return quantityStaDao.query2ndTotalFcst(map);
		}

		map.put("staDate", staDate);

		Integer staHhDepart = quantityStaDao.queryDepartLastFcstStaHh(staDate);
		Integer staHhArrive = quantityStaDao.queryArriveLastFcstStaHh(staDate);
		if (staHhDepart == null && staHhArrive == null) {
			return new ArrayList<QuantityStaDto>();
		}

		map.put("staHhDepart", staHhDepart);
		map.put("staHhArrive", staHhArrive);

		return quantityStaDao.queryTotalFcst(map);
	}

}
