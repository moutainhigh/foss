package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.server.dao.IAgencySystemReportQueryDao;
import com.deppon.foss.module.settlement.agency.api.server.service.IAgencySystemReportQueryService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.AgencySystemReportQueryDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.AgencySystemReportResultDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 偏线全盘报表Service接口实现
 * 
 * @author foss-zhangxiaohui
 * @date Dec 25, 2012 3:41:54 PM
 */
public class AgencySystemReportQueryService implements
		IAgencySystemReportQueryService {

	/**
	 * 日志打印对象声明
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AgencySystemReportQueryService.class);

	/**
	 * 偏线全盘代理报表Dao
	 */
	private IAgencySystemReportQueryDao agencySystemReportQueryDao;
	
	/**
	 * 查询运单入到达部门日期的
	 */
	private IStockService stockService;

	/**
	 * 根据运单单号查询偏线全盘报表
	 * 
	 * @author foss-zhangxiaohui
	 * @param
	 * @date Dec 26, 2012 4:39:43 PM
	 * @return
	 */
	@Override
	public List<AgencySystemReportResultDto> querAgencySystemReportByWayBillNo(
			AgencySystemReportQueryDto dto, CurrentInfo cInfo) {
		// 传入对象非空判断
		if (null == dto) {
			// 如果没有值则抛出异常
			throw new SettlementException("传入参数不能为空");
		}
		// 判断传入的运单号是否为空
		if (StringUtil.isEmpty(dto.getWaybillNo())) {
			// 如果没有值则抛出异常
			throw new SettlementException("传入的运单单号不能为空");
		}

		// Service执行的Log
		LOGGER.debug("entering service");
		
		dto.setEmpCode(cInfo.getEmpCode());
		
		// 设置当前登录部门
		this.setUserOrgCodesParameters(dto, cInfo);
		
		//运单的交接单类型为：偏线
		dto.setProductType(PartiallineConstants.HANDOVER_TYPE_PARTIALLINE_HANDOVER);
		dto.setActive(FossConstants.ACTIVE);

		// 执行查询操作
		List<AgencySystemReportResultDto> list = agencySystemReportQueryDao
				.querAgencySystemReportByWayBillNo(dto);
		
		// Service执行结束打印日志
		LOGGER.debug("successfully exit service");
		
		// 返回查询结果
		return queryResultDtoArriveTime(list);
	}
	
	/**
	 * 根据运单号查询，运单入到达部门的时间
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-22 上午8:37:33
	 * @param list
	 * @return
	 */
	private List<AgencySystemReportResultDto> queryResultDtoArriveTime(List<AgencySystemReportResultDto> list){
		//判断参数是否为空
		if(CollectionUtils.isNotEmpty(list)){
			//声明实例一个list对象
			List<AgencySystemReportResultDto> resultDtos=new ArrayList<AgencySystemReportResultDto>();
			for(AgencySystemReportResultDto dto:list){
				
				//调用中转接口
				List<InOutStockEntity> outStocks=this.stockService.queryInStockInfo(dto.getWaybillNo(), null, 
						dto.getLastLoadOrgCode(),//到达部门编码
						dto.getBillDate());
				if(CollectionUtils.isNotEmpty(outStocks)){
					InOutStockEntity outStockEntity=outStocks.get(outStocks.size()-1);
					dto.setArriveTime(outStockEntity.getInOutStockTime());
				}
				resultDtos.add(dto);
			}
			return resultDtos;
		}
		return list;
	}

	/**
	 * 偏线全盘报表Service接口实现--根据Dto查询应收单
	 * 
	 * @author foss-zhangxiaohui
	 * @param
	 * @date Dec 25, 2012 3:43:02 PM
	 * @return
	 */
	@Override
	public List<AgencySystemReportResultDto> querAgencySystemReportByDto(
			int offset, int start, AgencySystemReportQueryDto dto,
			CurrentInfo cInfo) {
		
		// 验证输入参数
		this.validateInputParameters(dto, cInfo);

		// 设置当前登录部门
		this.setUserOrgCodesParameters(dto, cInfo);

		// Service执行的Log
		LOGGER.debug("entering service");
		
		dto.setEmpCode(cInfo.getEmpCode());
		//运单的交接单类型为：偏线
		dto.setProductType(PartiallineConstants.HANDOVER_TYPE_PARTIALLINE_HANDOVER);
		dto.setActive(FossConstants.ACTIVE);
		
		
	
		// 执行查询操作
		List<AgencySystemReportResultDto> list = agencySystemReportQueryDao
				.querAgencySystemReportByDto(offset, start, dto);
		
		// Service执行结束打印日志
		LOGGER.debug("successfully exit service");
		
		// 返回查询结果
		return queryResultDtoArriveTime(list);
	}
	

	/**
	 * 偏线全盘报表Service接口实现--根据Dto查询总记录条数
	 * 
	 * @author foss-zhangxiaohui
	 * @param
	 * @date Dec 25, 2012 3:43:43 PM
	 * @return
	 */
	@Override
	public AgencySystemReportResultDto queryTotalRecordsInDBByDto(
			AgencySystemReportQueryDto dto, CurrentInfo cInfo) {
		// 验证输入参数
		this.validateInputParameters(dto, cInfo);

		// 设置当前登录部门
		this.setUserOrgCodesParameters(dto, cInfo);
		// Service执行的Log
		LOGGER.debug("entering service");
		
		dto.setEmpCode(cInfo.getEmpCode());
		
		//运单的交接单类型为：偏线
		dto.setProductType(PartiallineConstants.HANDOVER_TYPE_PARTIALLINE_HANDOVER);
		dto.setActive(FossConstants.ACTIVE);
		
		// 执行查询操作
		AgencySystemReportResultDto resultDto = agencySystemReportQueryDao
				.queryTotalRecordsInDBByDto(dto);
		
		// Service执行结束打印日志
		LOGGER.debug("successfully exit service");
		// 返回查询结果
		return resultDto;
	}

	/**
	 * 设置当前登录部门条件
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 26, 2012 4:54:08 PM
	 */
	private void setUserOrgCodesParameters(AgencySystemReportQueryDto dto,
			CurrentInfo cInfo) {

		// 设置登录用户所属部门查询条件部门

		// 获取当前登录用户的部门
//		String userOrgCode = cInfo.getCurrentDeptCode();

		// 设置查询条件
//		dto.setLastLoadOrgCode(userOrgCode);
	}

	/**
	 * 验证查询的条件
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 26, 2012 4:54:08 PM
	 */
	private void validateInputParameters(AgencySystemReportQueryDto dto,
			CurrentInfo cInfo) {
		// 传入对象非空判断
		if (null == dto) {
			// 如果没有值则抛出异常
			throw new SettlementException("传入参数不能为空");
		}
		// 开始日期非空校验
		if (null == dto.getStartBusinessDate()) {
			// 开始日期为空则抛出异常
			throw new SettlementException("开始业务日期不能为空");
		}
		// 结束日期非空校验
		if (null == dto.getEndBusinessDate()) {
			// 结束日期为空则抛出异常
			throw new SettlementException("结束业务日期不能为空");
		}
		// 判断开始日期是否小于结束日期
		if (dto.getStartBusinessDate() != null
				&& dto.getEndBusinessDate() != null) {
			Date startDate = DateUtils.truncate(dto.getStartBusinessDate(),
					Calendar.SECOND);
			Date endDate = DateUtils.truncate(dto.getEndBusinessDate(),
					Calendar.SECOND);
			if (startDate.after(endDate)) {
				throw new SettlementException("开始业务日期大于结束业务日期！");
			}
		}
		// 判断开始日期是否小于结束日期
		if (dto.getHandOverStartTime() != null
				&& dto.getHandOverEndTime() != null) {
			Date startDate = DateUtils.truncate(dto.getHandOverStartTime(),
					Calendar.SECOND);
			Date endDate = DateUtils.truncate(dto.getHandOverEndTime(),
					Calendar.SECOND);
			if (startDate.after(endDate)) {
				throw new SettlementException("开始外发时间大于结束外发时间！");
			}
		}
	}

	/**
	 * @param agencySystemReportQueryDao
	 */
	public void setAgencySystemReportQueryDao(
			IAgencySystemReportQueryDao agencySystemReportQueryDao) {
		this.agencySystemReportQueryDao = agencySystemReportQueryDao;
	}

	
	/**
	 * @param stockService the stockService to set
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

}
