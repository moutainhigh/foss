package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrWoodenEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrWoodenEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrWoodenDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrWoodenVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;

/**
 * 代打木架Action
 * @author 045738
 */
public class MvrWoodenEntityAction extends AbstractAction {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6925266557908193355L;

	/** 日志. */
	private static final Logger LOG = LogManager.getLogger(MvrWoodenEntityAction.class);
	
	/**
	 * 代打木架vo
	 */
	private MvrWoodenVo mvrWoodenVo;
	
	/**
	 * 代打木架service
	 */
	private IMvrWoodenEntityService mvrWoodenEntityService;

	/** excel名称. */
	private String excelName;

	/** excel导出流. */
	private InputStream inputStream;
	
	/**
	 * 功能：查询代打木架报表数据
	 * @author 045738-foss-maojianqiang
	 * @date 2014-4-11
	 * @return
	 */
	@JSON
	public String querymvrWooden(){
		try {
			
			// 查询参数不能为空
			if (mvrWoodenVo == null || mvrWoodenVo.getMvrWoodenDto()==null) {
				return returnError("查询参数为空");
			}
			//声明查询dto
			MvrWoodenDto dto=mvrWoodenVo.getMvrWoodenDto();
			
			// 查询报表期间不能为空
			if (StringUtils.isEmpty(dto.getPeriod())) {
				return returnError("查询代打木架月报表期间为空");
			}
			// 设置用户数据查询权限
			//CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//MvrLddDto.setUserCode(currentInfo.getEmpCode());
			// 查询汇总
			MvrWoodenDto totalDto = mvrWoodenEntityService.queryMvrWoodenTotal(dto);
			if (totalDto.getCount() > 0) {
				// 查询列表
				List<MvrWoodenEntity> queryList = mvrWoodenEntityService.selectByConditions(dto, start, limit);

				// 附加统计信息
				MvrWoodenEntity total = new MvrWoodenEntity();
				BeanUtils.copyProperties(totalDto, total);
				total.setPeriod("合计");
				queryList.add(total);

				// 设置查询结果列表
				dto.setList(queryList);
			}

			// 将查询结果返回给前台
			mvrWoodenVo.setMvrWoodenDto(dto);
			this.setTotalCount((long) totalDto.getCount());
			return returnSuccess();

		} catch (BusinessException e) {
			// 记录日志并返回失败
			LOG.error(e);
			return returnError("查询代打木架月报表异常:" + e.getMessage());

		}

	}

	/**
	 * 功能：导出excel
	 * @author 045738-foss-maojianqiang
	 * @date 2014-4-16
	 * @return
	 */
	public String export() {

		try {
			// 查询参数不能为空
			if (mvrWoodenVo == null || mvrWoodenVo.getMvrWoodenDto()==null) {
				return returnError("查询参数为空");
			}

			//声明查询dto
			MvrWoodenDto dto=mvrWoodenVo.getMvrWoodenDto();
			// 查询报表期间不能为空
			if (StringUtils.isEmpty(dto.getPeriod())) {
				return returnError("查询代打木架月报表期间为空");
			}

			// 设置导出excel名称
			String exportXlsName = "代打木架_" + dto.getPeriod();
			try {
				this.setExcelName(URLEncoder.encode(exportXlsName,
						SettlementConstants.UNICODE_UTF));
			} catch (UnsupportedEncodingException e) {
				LOG.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}

			// 查询导出数据
			List<MvrWoodenEntity> queryList = this.mvrWoodenEntityService.selectByConditions(dto);

			// 导出格式数据
			ExportResource exportResource = getExportResource(queryList);

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName(exportXlsName);
			
			//设置格式
		    Map<Integer, String> map = new HashMap<Integer, String>();
		    for(int i=SettlementReportNumber.EIGHT;i<=SettlementReportNumber.FORTY_EIGHT;i++){
		    	map.put(i, "float");
		    }
		    exportSetting.setDataType(map);

			// 创建导出工具类
			ExporterExecutor executor = new ExporterExecutor();

			// 输出到导出流中
			inputStream = executor.exportSync(exportResource, exportSetting);

		} catch (BusinessException e) {

			// 记录日志并返回失败
			LOG.error(e);
			return returnError("导出快递代理月报表异常:" + e.getMessage());

		}

		return returnSuccess();
	}

	/**
	 * 获取需要导出的excel数据.
	 * 
	 * @param queryList
	 *            the query list
	 * @return the sheet data
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-8 上午10:42:55
	 */
	private ExportResource getExportResource(List<MvrWoodenEntity> queryList) {

		// 导出excel数据
		ExportResource data = new ExportResource();

		// 导出excel表头
		String[] headers = {
			"期间","客户编码","客户名称","起始部门编码","起始部门名称","到达部门编码","到达部门名称","包装入成本","包装其他应收录入","包装其他应付录入","应付冲其他应收","打木架付款申请"
		};

		// 需要导出的excel的列
		String[] columns = {
			"period",
			"customerCode",
			"customerName",
			"origOrgCode",
			"origOrgName",
			"destOrgCode",
			"destOrgName",
			"woodenCost",
			"wrEntry",
			"wpEntry",
			"wothPayWoOthRc",
			"woodenOrigPayTail",
		};

		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(queryList)) {
			// 列数据
			List<String> colList = null;

			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (MvrWoodenEntity entity : queryList) {

				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {
					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					cellValue = (fieldValue == null ? "" : fieldValue
							.toString());
					colList.add(cellValue);

				}

				rowList.add(colList);
			}
		}
		// 设置Header
		data.setHeads(headers);		
		// 设置导出数据
		data.setRowList(rowList);
		return data;
	}
	public MvrWoodenVo getMvrWoodenVo() {
		return mvrWoodenVo;
	}

	public void setMvrWoodenVo(MvrWoodenVo mvrWoodenVo) {
		this.mvrWoodenVo = mvrWoodenVo;
	}

	public void setMvrWoodenEntityService(
			IMvrWoodenEntityService mvrWoodenEntityService) {
		this.mvrWoodenEntityService = mvrWoodenEntityService;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
