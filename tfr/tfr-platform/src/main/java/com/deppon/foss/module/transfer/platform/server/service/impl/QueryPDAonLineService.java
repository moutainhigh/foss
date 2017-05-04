package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.IQueryPDAonLineDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.server.service.IQueryPDAonLineService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PDAOnlineUsingEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.PDAOnlineUsingDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TransferCenterDto;
import com.deppon.foss.util.CollectionUtils;

/**
 * 统计PDA在线情况
 * 
 * @author foss中转-105795-wqh
 * @date 2015-01-26 上午8:16:40
 */
public class QueryPDAonLineService implements IQueryPDAonLineService {

	/**
	 * logger
	 */
	private final Logger logger = LoggerFactory
			.getLogger(QueryPDAonLineService.class);

	/**
	 * 查找上级组织时，最大向上找6级
	 */
	public static final int ORG_QUERY_RECURRENCE_NUM = 7;

	// 注入dao
	private IQueryPDAonLineDao queryPDAonLineDao;

	// 引用GoodsAreaDensityService
	private GoodsAreaDensityService goodsAreaDensityService;

	//注入查询经营本部service
	private IPlatformCommonService platformCommonService;
	
	/**
	 * com.deppon.foss.module.transfer.platform.api.server.service
	 * 
	 * @desc 调存储过程，生成PDA在线量日统计与月统计
	 * @param date
	 * @return int
	 * @author 105795
	 * @date 2015年1月26日下午3:36:54
	 */
	public int calculatePDAonline() {
		Date currentiTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentiTime);
		//获取前一天的数据
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		// 将时间转为
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 将时间格式化后获取日期
		currentiTime=calendar.getTime();
		String strDate = sdf.format(currentiTime).substring(0, PlatformConstants.SONAR_NUMBER_10);
		// 在将日期字符转为日期格式
		try {
			currentiTime = sdf.parse(strDate);
		} catch (ParseException e) {
			logger.error("转换日期格式异常！");
		}

		logger.info("计算PDA在线情况开始： 时间为：" + currentiTime);
		// 统计-pda某天的登入退出情况于 及 查询（理货员、电叉司机、电叉司机组长）所列岗位所属部门、工号、姓名、岗位
		queryPDAonLineDao.countPDAEmpToPro(currentiTime);

		String hqCode = "";
		String hqName = "";
		// 找经营本部

		// 找出全国所有外场，剔除掉分部
		List<TransferCenterDto> transferCenterList = queryPDAonLineDao
				.queryAllTransferCenter();
		// 循环调存储过程
		for (TransferCenterDto dto : transferCenterList) {
		  Map<String,String> hqEntity=	platformCommonService.findSupHq(dto.getTransferCenterCode());
			/*TransferCenterDto hqEntity = queryOrgByOrgCode(dto
					.getTransferCenterCode());*/
			if (hqEntity != null) {
				// 经营本部
				hqCode = hqEntity.get("HQ_CODE");
				hqName = hqEntity.get("HQ_NAME");
			}
			// 找外场
			String outfiledCode = dto.getTransferCenterCode();
			String outfiledName = dto.getTransferCenterName();
			// 找外场
			// 调计算pda在线使用情况存储过程
			queryPDAonLineDao.calculatePDAOnline(hqCode, hqName, outfiledCode,
					outfiledName, currentiTime);

		}

		logger.info("计算PDA在线情况结束： 时间为：" + new Date());
		logger.info("计算PDA在线情况工用去："
				+ (new Date().getTime() - currentiTime.getTime()));
		return 1;

	}

	/**
	 * com.deppon.foss.module.transfer.platform.api.server.dao
	 * 
	 * @desc 查询全国所有外场剔除掉分部
	 * @param
	 * @return List<TransferCenterDto>
	 * @author 105795
	 * @date 2015年1月28日下午5:06:39
	 */
	public List<TransferCenterDto> queryAllTransferCenter() {
		List<TransferCenterDto> transferCenterList = queryPDAonLineDao
				.queryAllTransferCenter();
		return transferCenterList;

	}

	/**
	 * com.deppon.foss.module.transfer.platform.api.server.service
	 * 
	 * @desc 查当前登录员工对应的外场或经营本部
	 * @param
	 * @return List<TransferCenterDto>
	 * @author 105795
	 * @date 2015年1月30日上午9:03:47
	 */
	public PDAOnlineUsingEntity queryHqAndTransferCenter() {

		PDAOnlineUsingEntity result = new PDAOnlineUsingEntity();
		// 获取当前员工信息
		String currentDeptCode = FossUserContext.getCurrentInfo()
				.getCurrentDeptCode();
		// 找外场
		Map<String, String> tfrCtrCodeMap = goodsAreaDensityService
				.queryParentTfrCtrCode(currentDeptCode);

		if (tfrCtrCodeMap != null && tfrCtrCodeMap.size() > 0) {
			// 说明找到外场信息
			result.setTransferCenterCode(tfrCtrCodeMap.get("code"));
			result.setTransferCenterName(tfrCtrCodeMap.get("name"));

		}
		// 找经营本部
		Map<String,String> hqEntity=platformCommonService.findSupHq(currentDeptCode);
		if (hqEntity != null) {
			
			result.setHqCode(hqEntity.get("HQ_CODE"));
			result.setHqName( hqEntity.get("HQ_NAME"));
		}
		

		return result;
	}

	/**
	 * com.deppon.foss.module.transfer.platform.api.server.dao
	 * 
	 * @desc 根据转入条件查询pda的使用情况
	 * @param pdaOnlineUsingEntity
	 *            其中统计日期不能为空
	 * @return List<PDAOnlineUsingEntity>
	 * @author 105795
	 * @date 2015年1月31日下午4:14:03
	 */
	public List<PDAOnlineUsingEntity> queryPDAOnlineUsing(
			PDAOnlineUsingEntity pdaOnlineUsingEntity) {
		Date nowDate = new Date();
		logger.info("计算PDA在线情况开始： 时间为：" + nowDate);
		List<PDAOnlineUsingEntity> pdaOnlineUsingEntityList = null;
		if (pdaOnlineUsingEntity == null) {
			logger.error("查询参数不能为空");
			throw new TfrBusinessException("查询参数不能为空");
		}
		if (pdaOnlineUsingEntity.getStaDate() == null
				&& pdaOnlineUsingEntity.getStaMonth() == 0) {
			logger.error("统计日期或统计月份不能同时为空");
			throw new TfrBusinessException("统计日期或统计月份不能同时为空");
		}
		try {
			pdaOnlineUsingEntityList = queryPDAonLineDao
					.queryPDAOnlineUsing(pdaOnlineUsingEntity);

		} catch (TfrBusinessException e) {
			throw new TfrBusinessException(e.getErrorCode());
		}

		if (pdaOnlineUsingEntityList == null
				|| pdaOnlineUsingEntityList.size() == 0) {
			throw new TfrBusinessException("没有查到满足条件的数据");
		}

		logger.info("计算PDA在线情况结束： 时间为：" + new Date());
		logger.info("计算PDA在线情况结束： 一共用去："
				+ ((new Date()).getTime() - nowDate.getTime()));
		return pdaOnlineUsingEntityList;

	}

	/**
	 * com.deppon.foss.module.transfer.platform.api.server.dao
	 * 
	 * @desc 查询理货员 叉车司机所有人员使用PDA的情况
	 * @param pdaOnlineUsingEntity
	 * @return List<PDAOnlineUsingDto>
	 * @author 105795
	 * @date 2015年2月2日下午2:40:54
	 */
	public List<PDAOnlineUsingDto> queryAllCategoryPDAUsing(
			PDAOnlineUsingEntity pdaOnlineUsingEntity) {
		Date nowTime = new Date();
		List<PDAOnlineUsingDto> pdaOnlineUsingList = null;
		logger.info("查询理货员 叉车司机所有人员使用PDA的情况  开始时间：" + nowTime.getTime());
		// 检查转入参数合法性
		checkedParms(pdaOnlineUsingEntity);
		pdaOnlineUsingList = queryPDAonLineDao
				.queryAllCategoryPDAUsing(pdaOnlineUsingEntity);
		if (CollectionUtils.isEmpty(pdaOnlineUsingList)
				&& pdaOnlineUsingList.size() == 0) {
			throw new TfrBusinessException("查询结果为空");
		}
		// 处理显示格式
		// handler(pdaOnlineUsingList);
		logger.info("查询理货员 叉车司机所有人员使用PDA的情况  结束时间：" + new Date());
		logger.info("查询理货员 叉车司机所有人员使用PDA的情况  一共用去："
				+ (new Date().getTime() - nowTime.getTime()));
		return pdaOnlineUsingList;

	}

	/**
	 * com.deppon.foss.module.transfer.platform.api.server.dao
	 * 
	 * @desc 查询理货员 叉车司机所有人员使用PDA的情况 分页
	 * @param pdaOnlineUsingEntity
	 * @return List<PDAOnlineUsingDto>
	 * @author 105795
	 * @date 2015年2月2日下午2:40:54
	 */
	public List<PDAOnlineUsingDto> queryAllCategoryPDAUsing(
			PDAOnlineUsingEntity pdaOnlineUsingEntity, int start, int limit) {
		Date nowTime = new Date();
		List<PDAOnlineUsingDto> pdaOnlineUsingList = null;
		logger.info("查询理货员 叉车司机所有人员使用PDA的情况  开始时间：" + nowTime.getTime());
		// 检查转入参数合法性
		checkedParms(pdaOnlineUsingEntity);
		pdaOnlineUsingList = queryPDAonLineDao.queryAllCategoryPDAUsing(
				pdaOnlineUsingEntity, start, limit);
		if (CollectionUtils.isEmpty(pdaOnlineUsingList)
				&& pdaOnlineUsingList.size() == 0) {
			throw new TfrBusinessException("查询结果为空");
		}
		// 处理显示格式
		handler(pdaOnlineUsingList);
		logger.info("查询理货员 叉车司机所有人员使用PDA的情况  结束时间：" + new Date());
		logger.info("查询理货员 叉车司机所有人员使用PDA的情况  一共用去："
				+ (new Date().getTime() - nowTime.getTime()));
		return pdaOnlineUsingList;

	}

	// 检查转入参数
	private void checkedParms(PDAOnlineUsingEntity pdaOnlineUsingEntity) {

		if (pdaOnlineUsingEntity == null) {
			throw new TfrBusinessException("参数不能为空！");

		}
		if (pdaOnlineUsingEntity.getStaDate() == null
				&& pdaOnlineUsingEntity.getStaMonth() == 0) {
			throw new TfrBusinessException("统计日期和统计月份不能同时为空！");
		}

	}

	/**
	 * com.deppon.foss.module.transfer.platform.api.server.dao
	 * 
	 * @desc 查询某一个外场下面的从月初到查询日期的所有pda使用情况
	 * @param transferCenterCode
	 *            外场编码
	 * @params startStaDate 月初统计日期，endStaDate查询日期 当start和limit为0时表示不分页
	 * @return List<PDAOnlineUsingDto>
	 * @author 105795
	 * @date 2015年2月3日上午9:51:01
	 */
	public List<PDAOnlineUsingDto> queryAllCategoryPDAUsingDetail(
			String transferCenterCode, Date startStaDate, Date endStaDate,
			int start, int limit) {
		Date nowTime = new Date();
		List<PDAOnlineUsingDto> pdaOnlineUsingList = null;
		logger.info("查询理货员 叉车司机所有人员使用PDA的情况  开始时间：" + nowTime.getTime());
		// 检查转入参数合法性
		if (StringUtil.isEmpty(transferCenterCode)) {
			throw new TfrBusinessException("外场不能为空");
		}

		if (startStaDate == null || endStaDate == null) {
			throw new TfrBusinessException("查询日期不能为空");
		}

		pdaOnlineUsingList = queryPDAonLineDao.queryAllCategoryPDAUsingDetail(
				transferCenterCode, startStaDate, endStaDate, start, limit);
		if (CollectionUtils.isEmpty(pdaOnlineUsingList)
				&& pdaOnlineUsingList.size() == 0) {
			throw new TfrBusinessException("查询结果为空");
		}
		// 处理现实格式
		handler(pdaOnlineUsingList);

		logger.info("查询理货员 叉车司机所有人员使用PDA的情况  结束时间：" + new Date());
		logger.info("查询理货员 叉车司机所有人员使用PDA的情况  一共用去："
				+ (new Date().getTime() - nowTime.getTime()));
		return pdaOnlineUsingList;

	}

	/**
	 * com.deppon.foss.module.transfer.platform.api.server.dao
	 * 
	 * @desc 根据部门code查询经营部门
	 * @param code
	 * @return TransferCenterDto
	 * @author 105795
	 * @date 2015年2月4日上午11:18:33
	 */
	public TransferCenterDto queryOrgByOrgCode(String code) {
		String hqType = "经营本部";
		TransferCenterDto org = queryPDAonLineDao.queryOrgByOrgCode(code);
		if (org == null) {
			logger.debug("如果部门不存在 ");
			throw new TfrBusinessException("如果部门不存在");
		}
		// 最多找7次
		for (int i = 0; i < ORG_QUERY_RECURRENCE_NUM; i++) {
			// 如果当前部门为经营本部直接返回
			if (org.getOrgName().contains(hqType)) {
				return org;
			}
			org = queryPDAonLineDao.queryOrgByOrgCode(org.getParentCode());
			if (org == null) {
				throw new TfrBusinessException("没有找到部门信息");
			}
		}
		return null;

	}

	/**
	 * 处理时间显示格式
	 * */

	private void handler(List<PDAOnlineUsingDto> sourceList) {
		for (PDAOnlineUsingDto dto : sourceList) {
			// 日  将天中的时刻 如果为1 转换为 01显示
			// 叉车
			if (dto.getForkUesdPDAOlineTimeDay() != null
					&& dto.getForkUesdPDAOlineTimeDay().length() == 1) {
				dto.setForkUesdPDAOlineTimeDay("0"
						+ dto.getForkUesdPDAOlineTimeDay() + ":00");

			} else {

				dto.setForkUesdPDAOlineTimeDay(dto.getForkUesdPDAOlineTimeDay()
						+ ":00");
			}
			// 理货员
			if (dto.getClerkUesdPDAOlineTimeDay() != null
					&& dto.getClerkUesdPDAOlineTimeDay().length() == 1) {
				dto.setClerkUesdPDAOlineTimeDay("0"
						+ dto.getClerkUesdPDAOlineTimeDay() + ":00");

			} else {
				dto.setClerkUesdPDAOlineTimeDay(dto
						.getClerkUesdPDAOlineTimeDay() + ":00");
			}
			// 所有人
			if (dto.getAllUesdPDAOlineTimeDay() != null
					&& dto.getAllUesdPDAOlineTimeDay().length() == 1) {
				dto.setAllUesdPDAOlineTimeDay("0"
						+ dto.getAllUesdPDAOlineTimeDay() + ":00");

			} else {
				dto.setAllUesdPDAOlineTimeDay(dto.getAllUesdPDAOlineTimeDay()
						+ ":00");
			}

		}

	}

	/**
	 * com.deppon.foss.module.transfer.platform.api.server.service
	 * 
	 * @desc 导出pda使用明细
	 * @param transferCenterCode
	 *            startStaDate
	 * @return ExportResource
	 * @author 105795
	 * @date 2015年2月6日上午9:40:18
	 */
	public ExportResource exportPDAUsingDetail(String transferCenterCode,
			Date startStaDate, Date endStaDate) {

		List<PDAOnlineUsingDto> pdaOnlineUsingList = queryPDAonLineDao
				.queryAllCategoryPDAUsingDetail(transferCenterCode,
						startStaDate, endStaDate, 0, 0);
		// 时间格式处理
		handler(pdaOnlineUsingList);
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> result = null;

		for (PDAOnlineUsingDto dto : pdaOnlineUsingList) {
			result = new ArrayList<String>();

			// 转运场
			result.add(dto.getTransferCenterName() != null ? dto
					.getTransferCenterName() : null);
			// 日期
			result.add(dto.getStaDate() != null ? dto.getStaDate() : null);
			// 当日最高峰电叉司机使用pda台数
			result.add(dto.getForkUesdPDACountDay() + "");
			// 当日最高峰电叉司机使用pda时间
			result.add(dto.getForkUesdPDAOlineTimeDay() != null ? dto
					.getForkUesdPDAOlineTimeDay() : null);
			// 当日最高峰理货员使用pda台数
			result.add(dto.getClerkUesdPDACountDay() + "");
			// 当日最高峰理货员使用pda时间
			result.add(dto.getClerkUesdPDAOlineTimeDay() != null ? dto
					.getClerkUesdPDAOlineTimeDay() : null);
			// 当日最高峰所有人员使用pda台数
			result.add(dto.getAllUesdPDACountDay() + "");
			// 当日最高峰所有人员使用pda时间
			result.add(dto.getAllUesdPDAOlineTimeDay() != null ? dto
					.getAllUesdPDAOlineTimeDay() : null);

			rowList.add(result);
		}
		ExportResource sheet = new ExportResource();
		sheet.setHeads(PlatformConstants.TFRCTRPDAUSINGINFO_EXCEL_TITLE);
		sheet.setRowList(rowList);
		return sheet;

	}

	/**
	 * @param queryPDAonLineDao
	 *            the queryPDAonLineDao to set
	 */
	public void setQueryPDAonLineDao(IQueryPDAonLineDao queryPDAonLineDao) {
		this.queryPDAonLineDao = queryPDAonLineDao;
	}

	/**
	 * @param goodsAreaDensityService
	 *            the goodsAreaDensityService to set
	 */
	public void setGoodsAreaDensityService(
			GoodsAreaDensityService goodsAreaDensityService) {
		this.goodsAreaDensityService = goodsAreaDensityService;
	}

	/**
	 * @param platformCommonService the platformCommonService to set
	 */
	public void setPlatformCommonService(
			IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}

	
}
