package com.deppon.foss.module.settlement.closing.server.action;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPtpRpsService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpRpsEntity;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrPtpRpsVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 *
 * 合伙奖罚特殊月报表
 *
 *  * @author foss-youkun-306698
 * @date 2016-3-18 下午3:43:01
 */

public class MvrPartnerRewAction extends AbstractAction {

	/** 声明日志对象. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MvrPartnerRewAction.class);

	/** 序列号. */
	private static final long serialVersionUID = 2127529594939158830L;

	private IMvrPtpRpsService mvrPtpRpsService;

	private MvrPtpRpsVo mvrPtpRpsVo;

	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;

	/**
	 * 查询合伙人人奖罚特殊月报表
	 * @return
     */
	@JSON
	public String queryMvrPtpRps(){
		if(null==mvrPtpRpsVo){
			return returnError("参数不能空！");
		}
		if(null==mvrPtpRpsVo.getMvrPtpRpsDto()){
			return returnError("参数不能空！");
		}
		//获取当前登录的用户信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 设置用户数据查询权限
		mvrPtpRpsVo.getMvrPtpRpsDto().setUserCode(currentInfo.getEmpCode());
		Long  count = 0L;
		List<MvrPtpRpsEntity> mvrPtpRpsEntityList = new ArrayList<MvrPtpRpsEntity>();
		try{
			count = this.mvrPtpRpsService.queryMvrPtpRpsCount(mvrPtpRpsVo.getMvrPtpRpsDto());
		}catch (SettlementException e){
			LOGGER.error(e.getMessage());
			return  returnError(e.getMessage());
		}
		//如果总记录数大于零
		if(count>0){
			try{
				mvrPtpRpsEntityList= this.mvrPtpRpsService.queryMvrPtpRpsEntityList(mvrPtpRpsVo.getMvrPtpRpsDto(),this.getStart(),this.getLimit());
			}catch (SettlementException e){
				LOGGER.error(e.getMessage());
				returnError(e.getMessage());
			}
		}
		this.setTotalCount(count);
		this.mvrPtpRpsVo.getMvrPtpRpsDto().setMvrPtpRpsEntityList(mvrPtpRpsEntityList);
		return returnSuccess();
	}

	/**
	 * 导出合伙人奖罚特殊月报表
	 * @return
     */
	public String exportMvrPtpRps(){
		if(null==mvrPtpRpsVo){
			return returnError("参数不能空！");
		}
		if(null==mvrPtpRpsVo.getMvrPtpRpsDto()){
			return returnError("参数不能空！");
		}
		// 导出始发应收月报表期间不能为空
		if (StringUtils.isEmpty(mvrPtpRpsVo.getMvrPtpRpsDto().getPeriod())) {
			// 提示导出始发应收月报表期间不能为空
			throw new SettlementException("导出始发应收月报表期间不能为空");
		}
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 设置用户数据查询权限
		mvrPtpRpsVo.getMvrPtpRpsDto().setUserCode(currentInfo.getEmpCode());

		// 导出文件名称：
		// 始发应收月报表_+账期构成
		try {
			String exportExcelName = "合伙人奖罚特殊月报表" + mvrPtpRpsVo.getMvrPtpRpsDto().getPeriod() + ".xls";
			// 转化编码
			this.setExeclName(new String((exportExcelName)
					.getBytes(SettlementConstants.UNICODE_GBK),
					SettlementConstants.UNICODE_ISO));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("导出Excel失败");
		}

		//查询出需要导出的数据
		List<MvrPtpRpsEntity> mvrPtpPscEntityList =null;
		try{
			mvrPtpPscEntityList = mvrPtpRpsService.exportMvrPtpPsc(mvrPtpRpsVo.getMvrPtpRpsDto());
		}catch (SettlementException e){
			LOGGER.error(e.getMessage());
			return returnError("导出失败！");
		}
		// 查询结果不能为空
		if (CollectionUtils.isEmpty(mvrPtpPscEntityList)) {
			// 提示导出月报表期间不能为空
			throw new SettlementException("导出月报表查询数据为空");
		}

		// 生成导出数据源类
		ExportResource sheet = new ExportResource();
		// 设置表单表头
		sheet.setHeads(this.getExcelHeads());

		Object[] dataAndType = this.exportDataAndType(mvrPtpPscEntityList);
		// 处理返回的结果集
		List<List<String>> dataList = (List<List<String>>) dataAndType[0];
		Map<Integer, String> type = (Map<Integer, String>) dataAndType[1];
		// 设置表单数据
		sheet.setRowList(dataList);

		sheet.setHeaderHeight(SettlementReportNumber.THREE);
		HeaderRows[] headerCfg = {
				new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ZERO, SettlementReportNumber.SIX, "数据统计维度"),// 7
				new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.SEVEN, SettlementReportNumber.NINE, "合伙人预存款【H】")// 3
				 };
		sheet.setHeaderList(Arrays.asList(headerCfg));

		// 创建导出表头对象
		ExportSetting exportSetting = new ExportSetting();

		// 设置sheet名称
		String exprotSheetName = mvrPtpRpsVo.getMvrPtpRpsDto()
				.getPeriod().toString();
		exportSetting.setSheetName(exprotSheetName);
		exportSetting.setDataType(type);

		// 创建导出工具类
		ExporterExecutor objExporterExecutor = new ExporterExecutor();

		// 导出成文件
		inputStream = objExporterExecutor.exportSync(sheet, exportSetting);

		// 正常返回
		return returnSuccess();
	}



	/**
	 * 合伙人奖罚特殊月报表表头
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-3-18 下午6:06:58
	 * @return
	 */
	private String[] getExcelHeads() {

		// 始发应收报表导出数据的表头
		String[] header = {
				"业务类型",
				"客户名称",
				"客户编码",
				"始发部门名称",
				"始发部门编码",
				"到达部门名称",
				"到达部门编码",
				"理赔冲成本",
				"理赔入收入",
				"代打木架应收"};
		// 返回excel表头
		return header;
	}

	/**
	 * 生成导出合伙人表数据
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-3-18 下午4:09:28
	 * @param queryList
	 * @return
	 */
	private Object[] exportDataAndType(List<MvrPtpRpsEntity> queryList) {

		String[] properties = new String[] {
							"productCode",
							"customerCode",
							"customerName",
							"origOrgCode",
							"origOrgName",
							"destOrgCode",
							"destOrgName",
							"ptpErrSspc",
							"ptpErrSsei",
							"ptpErrShwfr"};

		// 生成返回数据集合
		List<List<String>> resultList = new ArrayList<List<String>>();

		List<String> strProperties = Arrays.asList(new String[] {
				"productCode",
				"customerCode",
				"customerName",
				"origOrgCode",
				"origOrgName",
				"destOrgCode",
				"destOrgName"});

		List<String> data = null;
		Object fieldValue = null;
		String cellValue = null;

		// 循环处理
		for (MvrPtpRpsEntity entity : queryList) {

			data = new ArrayList<String>();
			for (String property : properties) {
				// 获取对象的值，如果为空，则设置其为空字符串
				fieldValue = ReflectionUtils.getFieldValue(entity, property);
				cellValue = (fieldValue == null ? "" : fieldValue.toString());
				data.add(cellValue);
			}

			resultList.add(data);
		}

		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		for (int i = 0; i < properties.length; i++) {
			String type = strProperties.contains(properties[i]) ? "string": "float";
			typeMap.put(i, type);
		}

		// 返回
		return new Object[] { resultList,typeMap};
	}


	public MvrPtpRpsVo getMvrPtpRpsVo() {
		return mvrPtpRpsVo;
	}

	public void setMvrPtpRpsVo(MvrPtpRpsVo mvrPtpRpsVo) {
		this.mvrPtpRpsVo = mvrPtpRpsVo;
	}

	public void setMvrPtpRpsService(IMvrPtpRpsService mvrPtpRpsService) {
		this.mvrPtpRpsService = mvrPtpRpsService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getExeclName() {
		return execlName;
	}

	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}
}
