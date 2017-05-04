package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.service.ITruckEfficiencyService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TruckEfficiencyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.TruckEfficiencyVo;

/**
 * 装卸车效率管理 action
 * 
 * @author 200978 xiaobingcheng 2015-1-23
 */
public class TruckEfficiencyAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5008962892283099028L;

	private static final Logger LOG = LoggerFactory
			.getLogger(TruckEfficiencyAction.class);

	private ITruckEfficiencyService truckEfficiencyService;

	private TruckEfficiencyVo truckEfficiencyVo = new TruckEfficiencyVo();
	
	/**
	 * 导出Excel 文件流
	 */
	private transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	

	public TruckEfficiencyVo getTruckEfficiencyVo() {
		return truckEfficiencyVo;
	}
	public void setTruckEfficiencyVo(TruckEfficiencyVo truckEfficiencyVo) {
		this.truckEfficiencyVo = truckEfficiencyVo;
	}
	public void setTruckEfficiencyService(
			ITruckEfficiencyService truckEfficiencyService) {
		this.truckEfficiencyService = truckEfficiencyService;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 装卸车效率管理跳转首页
	 * 
	 * @Author: 200978 xiaobingcheng 2015-1-23
	 * @return
	 */
	public String truckEfficiencyIndex() {
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		try {
			Map<String, String> transferCenter = truckEfficiencyService
					.queryParentTfrCtrCode(currentDeptCode);
			if (transferCenter != null) {
				truckEfficiencyVo.setTransferCenterCode(transferCenter
						.get("code"));
				truckEfficiencyVo.setTransferCenterName(transferCenter
						.get("name"));
				truckEfficiencyVo.setOperationDeptCode(null);
				truckEfficiencyVo.setOperationDeptName(null);
			} else {
				Map<String, String> operationDept = this.truckEfficiencyService
						.queryOperationDeptCode(currentDeptCode);
				if (operationDept != null) {
					truckEfficiencyVo.setTransferCenterCode(null);
					truckEfficiencyVo.setTransferCenterName(null);
					truckEfficiencyVo.setOperationDeptCode(operationDept
							.get("code"));
					truckEfficiencyVo.setOperationDeptName(operationDept
							.get("name"));
				} else {

					truckEfficiencyVo.setTransferCenterCode(null);
					truckEfficiencyVo.setTransferCenterName(null);
					truckEfficiencyVo.setOperationDeptCode(null);
					truckEfficiencyVo.setOperationDeptName(null);
				}
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOG.error(e.getStackTrace().toString());
			return returnError(e);
		}
	}

	/**
	 * 按日期查询装卸车效率
	 * 
	 * @Author: 200978 xiaobingcheng 2015-1-23
	 * @return
	 */
	public String queryLoadUnloadEfficiencyByDay() {
		TruckEfficiencyEntity truckEfficiencyEntity = new TruckEfficiencyEntity();
		truckEfficiencyEntity.setStatisticDate(truckEfficiencyVo.getStatisticDate());
		truckEfficiencyEntity.setTransferCenterCode(truckEfficiencyVo.getTransferCenterCode());
		truckEfficiencyEntity.setOperationDeptCode(truckEfficiencyVo.getOperationDeptCode());
		List<TruckEfficiencyEntity> list = new ArrayList<TruckEfficiencyEntity>();
		Long count;
		try {
			//如果转运场不为空，则记录显示查询时间当月1号到所选日期的所有记录;其他情况 一个转运场只显示一条
			if(StringUtil.isNotEmpty(truckEfficiencyVo.getTransferCenterCode())){
				list = this.truckEfficiencyService.queryTruckEfficiencyByDayOfTransfer(truckEfficiencyEntity,this.getStart(),this.getLimit());
				count = this.truckEfficiencyService.queryTruckEfficiencyByDayOfTransferCount(truckEfficiencyEntity);
			}else{
				list = this.truckEfficiencyService.queryTruckEfficiencyByDay(truckEfficiencyEntity,this.getStart(),this.getLimit());
				count = this.truckEfficiencyService.queryTruckEfficiencyByDayCount(truckEfficiencyEntity);
			}
			truckEfficiencyVo.setTruckEfficiencyList(list);
			super.setTotalCount(count);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 导出按日期查询的装卸车效率数据
	 * 
	 * @Author: 200978 xiaobingcheng 2015-1-23
	 * @return
	 */
	public String exportLoadUnloadEfficiencyByDay() {
		try {
			/**
			 * 设置文件名
			 */
			fileName = this.truckEfficiencyService.encodeFileName(PlatformConstants.TRUCK_EFFICIENCY_DAY_SHEET_NAME);
			
			TruckEfficiencyEntity truckEfficiencyEntity = new TruckEfficiencyEntity();
			truckEfficiencyEntity.setStatisticDate(truckEfficiencyVo.getStatisticDate());
			truckEfficiencyEntity.setTransferCenterCode(truckEfficiencyVo.getTransferCenterCode());
			truckEfficiencyEntity.setOperationDeptCode(truckEfficiencyVo.getOperationDeptCode());
			/**
			 * 获取文件内容流
			 */
			excelStream = truckEfficiencyService.queryTruckEfficiencyByDayExcelStream(truckEfficiencyEntity);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 按月份查询装卸车效率
	 * 
	 * @Author: 200978 xiaobingcheng 2015-1-23
	 * @return
	 */
	public String queryLoadUnloadEfficiencyByMonth() {
		TruckEfficiencyEntity truckEfficiencyEntity = new TruckEfficiencyEntity();
		truckEfficiencyEntity.setStatisticDate(truckEfficiencyVo.getStatisticDate());
		truckEfficiencyEntity.setTransferCenterCode(truckEfficiencyVo.getTransferCenterCode());
		truckEfficiencyEntity.setOperationDeptCode(truckEfficiencyVo.getOperationDeptCode());
		try {
			List<TruckEfficiencyEntity> list = this.truckEfficiencyService.queryTruckEfficiencyByMonth(truckEfficiencyEntity,this.getStart(),this.getLimit());
			Long count = this.truckEfficiencyService.queryTruckEfficiencyByMonthCount(truckEfficiencyEntity);
			truckEfficiencyVo.setTruckEfficiencyList(list);
			super.setTotalCount(count);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 导出按月份查询的装卸车效率数据
	 * 
	 * @Author: 200978 xiaobingcheng 2015-1-23
	 * @return
	 */
	public String exportLoadUnloadEfficiencyByMonth() {
		try {
			/**
			 * 设置文件名
			 */
			fileName = this.truckEfficiencyService.encodeFileName(PlatformConstants.TRUCK_EFFICIENCY_MONTH_SHEET_NAME);
			
			TruckEfficiencyEntity truckEfficiencyEntity = new TruckEfficiencyEntity();
			truckEfficiencyEntity.setStatisticDate(truckEfficiencyVo.getStatisticDate());
			truckEfficiencyEntity.setTransferCenterCode(truckEfficiencyVo.getTransferCenterCode());
			truckEfficiencyEntity.setOperationDeptCode(truckEfficiencyVo.getOperationDeptCode());
			/**
			 * 获取文件内容流
			 */
			excelStream = truckEfficiencyService.queryTruckEfficiencyByMonthExcelStream(truckEfficiencyEntity);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

}
