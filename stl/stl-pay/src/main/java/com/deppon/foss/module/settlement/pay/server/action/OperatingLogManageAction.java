package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IOperatingLogManageService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.OperatingLogVo;
import com.deppon.foss.util.DateUtils;

/**
 * 操作日志action
 * 
 * @author foss-qiaolifeng
 * @date 2012-12-10 下午4:56:50
 */
public class OperatingLogManageAction extends AbstractAction {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(OperatingLogManageAction.class);

	/**
	 * 操作日志action序列号
	 */
	private static final long serialVersionUID = 6736370370460761808L;

	/**
	 * 操作日志vo
	 */
	private OperatingLogVo operatingLogVo;

	/**
	 * 操作日志service
	 */
	private IOperatingLogManageService operatingLogManageService;

	/**
	 * 导出输出流
	 */
	private ByteArrayInputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;

	/**
	 * 查询操作日志
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午5:13:39
	 */
	@JSON
	public String queryOperatingLogList() {

		try {

			// 处理时间查询条件
			setQueryConditionForDate();

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 查询操作日志总条数
			Long totalRows = operatingLogManageService.queryTotalRowsbyDate(
					operatingLogVo.getOperatingLogQueryDto(), cInfo);

			if (totalRows > 0) {

				// 查询操作日志
				List<OperatingLogResultDto> list = operatingLogManageService
						.queryOperatingLogByDateAndPage(
								operatingLogVo.getOperatingLogQueryDto(),
								this.getStart(), this.getLimit(), cInfo);

				// 设置返回值
				operatingLogVo.setOperatingLogResultDtoList(list);
				this.setTotalCount(totalRows);
			}
		} catch (BusinessException be) {
			logger.error(be.getMessage(), be);
			return returnError(be);
		}
		return returnSuccess();
	}

	/**
	 * 导出日志报表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午8:36:03
	 */
	public String exportOperatingLog() {

		try {
			logger.debug("导出日志开始...");

			// 设置时间查询条件
			setQueryConditionForDate();

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 查询审核日志
			List<OperatingLogResultDto> dtoList = operatingLogManageService
					.queryOperatingLogByDate(operatingLogVo
							.getOperatingLogQueryDto(),cInfo);

			// 生成Excel代码
			try {

				// 设置Excel名称
				//this.setExeclName(URLEncoder.encode("审核日志报表", "UTF-8"));
				this.setExeclName(new String("审核日志报表".getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				logger.error(e1.getMessage());
			}

			// 查询到的结果集不为空时，执行导出操作
			if (CollectionUtils.isNotEmpty(dtoList)) {

				if (dtoList.size() > SettlementConstants.EXPORT_MAX_COUNT) {
					throw new SettlementException("每次最多只能导出"
							+ SettlementConstants.EXPORT_MAX_COUNT
							+ "条数据,请重新查询并导出");
				}

				ExcelExport export = new ExcelExport();
				// 设置每次最多导出6万条数据
				HSSFWorkbook work = export.exportExcel(
						fillSheetDataByOperatingLog(dtoList), "sheet",
						SettlementConstants.EXPORT_MAX_COUNT);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					work.write(baos);
					inputStream = new ByteArrayInputStream(baos.toByteArray());
				} catch (IOException e) {
					logger.error(e.getMessage());
				} finally {
					if (baos != null) {
						try {
							baos.close();
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					}
				}
			} else {
				return returnError("导出数据为空！");
			}

			return returnSuccess();
		} catch (BusinessException be) {
			logger.error(be.getMessage(), be);
			return returnError(be);
		}
	}

	/**
	 * 生成日志的excel
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-6 上午11:13:38
	 */
	private SheetData fillSheetDataByOperatingLog(
			List<OperatingLogResultDto> dtoList) {
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(new String[] { "日志编号", "业务编号", "业务单据类型","业务名称",
				"操作人所属事业部", "操作人所属大区", "操作人名称", "操作人所属小区", "部门", "操作时间",
				"操作人IP", "备注" });

		//因原来在循环里面调用速度比较慢，一次性把所有的缓存获取到
		//生成待获取转换编码
		List<String> types=new ArrayList<String>();
		types.add(DictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE);// 日志单据类型
		types.add(DictionaryConstants.OPERATING_LOG__OPERATE_TYPE);// 日志操作类型
		//获取全部待转换缓存
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(types);
		
		if (CollectionUtils.isNotEmpty(dtoList)) {
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (OperatingLogResultDto entity : dtoList) {
				List<String> cellList = new ArrayList<String>();
				cellList.add(entity.getId());// 日志编号
				cellList.add(entity.getOperateBillNo());// 业务编号
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map, 
						DictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE,entity.getOperateBillType()));// 业务单据类型
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map, 
						DictionaryConstants.OPERATING_LOG__OPERATE_TYPE,entity.getOperateType()));// 业务名称
				cellList.add(entity.getDivisionName());// 操作人所属事业部
				cellList.add(entity.getBusinessBigRegionName());// 操作人所属大区
				cellList.add(entity.getOperatorName());// 操作人名称
				cellList.add(entity.getBusinessSmallRegionName());// 操作人所属小区
				cellList.add(entity.getOperateOrgName());// 部门
				cellList.add(DateUtils.convert(entity.getOperateTime(),
						DateUtils.DATE_TIME_FORMAT));// 操作时间
				cellList.add(entity.getOperatorIp());// 操作人IP
				cellList.add(entity.getNotes());// 备注

				rowList.add(cellList);
			}
			sheetData.setRowList(rowList);
		}
		return sheetData;
	}

	/**
	 * 设置时间查询条件
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午5:09:21
	 */
	private void setQueryConditionForDate() {

		// 设置执行时使用小于结束日期+1天
		if (operatingLogVo.getOperatingLogQueryDto().getStartOperateTime() != null
				&& operatingLogVo.getOperatingLogQueryDto().getEndOperateTime() != null) {

			// 生成加一天的结束时间
			String dateEndTemp = DateUtils.addDay(operatingLogVo
					.getOperatingLogQueryDto().getEndOperateTime(), 1);
			Date dateEnd = DateUtils.convert(dateEndTemp);

			// 将结束时间加一天数据设置到查询条件上
			operatingLogVo.getOperatingLogQueryDto().setEndOperateTime(dateEnd);
		}
	}

	public void setOperatingLogManageService(
			IOperatingLogManageService operatingLogManageService) {
		this.operatingLogManageService = operatingLogManageService;
	}

	public void setOperatingLogVo(OperatingLogVo operatingLogVo) {
		this.operatingLogVo = operatingLogVo;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getExeclName() {
		return execlName;
	}

	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}

	public OperatingLogVo getOperatingLogVo() {
		return operatingLogVo;
	}

}
