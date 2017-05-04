package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPtpPscService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpPscEntity;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrPtpPscVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 *
 * 合伙人子公司月报表
 *
 *  * @author foss-youkun-306698
 * @date 2016-3-18 下午3:43:01
 */

public class MvrPartnerComAction extends AbstractAction {

	/** 声明日志对象. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MvrPartnerComAction.class);

	/** 序列号. */
	private static final long serialVersionUID = 2127529594939158830L;
	/**
	 * 合伙人子公司月报表service
	 */
	private IMvrPtpPscService mvrPtpPscService;

	private MvrPtpPscVo mvrPtpPscVo;

	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;

	/**
	 * 查询合伙人子公司月报表的方法
	 * @return
     */
	@JSON
	public String queryMvrParentCom(){
		if(null==mvrPtpPscVo){
			return returnError("参数不能为空！");
		}
		if(null==mvrPtpPscVo.getMvrPtpPscDto()){
			return returnError("参数不能为空！");
		}

		//获取当前登录的用户信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 设置用户数据查询权限
		mvrPtpPscVo.getMvrPtpPscDto().setUserCode(currentInfo.getEmpCode());
		//根据条件查询总记录数
		Long  total = 0L;
		List<MvrPtpPscEntity> mvrPtpPscEntityList = new ArrayList<MvrPtpPscEntity>();
		try{
			total = mvrPtpPscService.queryMvrParentComCount(mvrPtpPscVo.getMvrPtpPscDto());
		}catch(SettlementException e){
			LOGGER.error(e.getMessage());
			return  returnError(e.getMessage());
		}
		//如果总记录数大于0查询集合
		if(total>0){
			try{
				mvrPtpPscEntityList=mvrPtpPscService.queryMvrPtpPscList(mvrPtpPscVo.getMvrPtpPscDto(),this.getStart(),this.getLimit());
			}catch (SettlementException e){
				LOGGER.error(e.getMessage());
				return  returnError(e.getMessage());
			}
		}
		this.setTotalCount(total);
		mvrPtpPscVo.getMvrPtpPscDto().setMvrPtpPscEntityList(mvrPtpPscEntityList);
		return returnSuccess();
	}

	/**
	 * 导出合伙人子公司报表
	 * @return
     */
	public String exportMvrParentCom(){
		if(null==mvrPtpPscVo){
			return returnError("参数不能为空！");
		}
		if(null==mvrPtpPscVo.getMvrPtpPscDto()){
			return returnError("参数不能为空！");
		}
		// 导出始发应收月报表期间不能为空
		if (StringUtils.isEmpty(mvrPtpPscVo.getMvrPtpPscDto().getPeriod())) {
			// 提示导出始发应收月报表期间不能为空
			throw new SettlementException("导出始发应收月报表期间不能为空");
		}
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 设置用户数据查询权限
		mvrPtpPscVo.getMvrPtpPscDto().setUserCode(currentInfo.getEmpCode());

		// 导出文件名称：
		// 始发应收月报表_+账期构成
		try {
			String exportExcelName = "合伙人子公司月报表" + mvrPtpPscVo.getMvrPtpPscDto().getPeriod() + ".xls";
			// 转化编码
			this.setExeclName(new String((exportExcelName)
					.getBytes(SettlementConstants.UNICODE_GBK),
					SettlementConstants.UNICODE_ISO));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("导出Excel失败");
		}

		//查询出需要导出的数据
		List<MvrPtpPscEntity> mvrPtpPscEntityList =null;
		try{
			mvrPtpPscEntityList = mvrPtpPscService.exportMvrParentCom(mvrPtpPscVo.getMvrPtpPscDto());
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
				new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ZERO, SettlementReportNumber.TWELVE, "数据统计维度"),// 14
				new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.THIRTEEN, SettlementReportNumber.TWENTY, "合伙人预存款【H】"),// 8
				new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_ONE, SettlementReportNumber.TWENTY_ONE, "还款运单总运费（到付）【H】"),// 1
				new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_TWO, SettlementReportNumber.TWENTY_THREE, "运单月结还款金额【H】"),// 2
				new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_FOUR, SettlementReportNumber.TWENTY_SEVEN, "运单成本【H】"),// 4
				new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_EIGHT, SettlementReportNumber.TWENTY_NINE, "运单委托应付款【H】"),//2
				new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY, SettlementReportNumber.THIRTY_TWO, "发起付款申请【H】"),// 3
				new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.THIRTY_THREE, SettlementReportNumber.SEVENTY_NINE, "签收运单【H】"),// 47
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY_THREE, SettlementReportNumber.FORTY, "签收后非月结部分始发提成应收已核销金额"),//8
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FORTY_ONE, SettlementReportNumber.FORTY_EIGHT, "签收后非月结部分到达应收已核销金额"), //8
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FORTY_NINE, SettlementReportNumber.FIFTY_THREE, "签收后非月结部分委托派费应收已核销金额"), //5
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY_FOUR, SettlementReportNumber.SIXTY_ONE, "签收时月结部分始发提成应收已核销金额"),//8
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY_TWO, SettlementReportNumber.SIXTY_SIX, "签收时月结部分委托派费应收已核销金额"), //5
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY_SEVEN, SettlementReportNumber.SEVENTY_FOUR, "签收时月结部分始发提成应收未核销金额"),//8
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SEVENTY_FIVE, SettlementReportNumber.SEVENTY_NINE, "签收时月结部分委托派费应收未核销金额"),//5
				new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.EIGHTY, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_NINE, "反签收运单【H】" ),   // 50
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY, SettlementReportNumber.EIGHTY_SEVEN, "反签收后非月结部分始发提成应收已核销金额" ),//8
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY_EIGHT, SettlementReportNumber.NINETY_FIVE, "反签收后非月结部分到达应收已核销金额" ),//8
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.NINETY_SIX, SettlementReportNumber.ONE_HUNDRED, "反签收后非月结部分委托派费应收已核销金额" ),//5
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_ONE, SettlementReportNumber.ONE_HUNDRED_AND_EIGHT, "反签收时月结部分始发提成应收已核销金额" ), //8
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_NINE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTEEN, "反签收时月结部分委托派费应收已核销金额" ), //5
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_FOURTEEN, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_ONE, "反签收时月结部分始发提成应收未核销金额" ), //8
				new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_TWO, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_SIX, "反签收时月结部分委托派费应收未核销金额" ), //5

		};
		sheet.setHeaderList(Arrays.asList(headerCfg));

		// 创建导出表头对象
		ExportSetting exportSetting = new ExportSetting();

		// 设置sheet名称
		String exprotSheetName = mvrPtpPscVo.getMvrPtpPscDto()
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
	 * 合伙人子公司月报表表头
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-3-18 下午6:06:58
	 * @return
	 */
	private String[] getExcelHeads() {

		// 始发应收报表导出数据的表头
		String[] header = {
				"业务类型(运输性质)",
				"客户名称",
				"客户编码",
				"始发部门名称",
				"始发部门编码",
				"到达部门名称",
				"到达部门编码",
				"预收部门名称",
				"预收部门编码",
				"应付部门名称",
				"应付部门编码",
				"应收部门名称",
				"应收部门编码",
				"预收加盟商款项",
				"预收加盟冲应收始发提成已签收",
				"预收加盟冲应收委托派费已签收",
				"预收加盟冲应收到付运费已签收(D)",
				"预收加盟冲应收到付运费已签收（H）",
				"预收加盟冲应收代收货款已签收",
				"应收罚款已核销",
				"退预收付款申请",
				"还款到付运费",
				"还款电汇未签收",
				"还款电汇已签收",
				"零担到达提成入成本",
				"快递到达提成入成本",
				"零担到达提成反确认",
				"快递到达提成反确认",
				"委托派费代付已签收",
				"委托派费代付反签收",
				"到达提成付款申请",
				"委托派费代付申请",
				"到付运费代付申请",
				"公布价运费",
				"开单操作费",
				"包装费",
				"保价费",
				"代收货款手续费",
				"送货费（不含上楼）",
				"基础送货费",
				"其他费用",
				"公布价运费",
				"开单操作费",
				"包装费",
				"保价费",
				"代收货款手续费",
				"送货费（不含上楼）",
				"基础送货费",
				"其他费用",
				"送货上楼费",
				"送货进仓费",
				"大件上楼费",
				"超远派送费",
				"签收单返回",
				"公布价运费",
				"开单操作费",
				"包装费",
				"保价费",
				"代收货款手续费",
				"送货费（不含上楼）",
				"基础送货费",
				"其他费用",
				"送货上楼费",
				"送货进仓费",
				"大件上楼费",
				"超远派送费",
				"签收单返回",
				"公布价运费",
				"开单操作费",
				"包装费",
				"保价费",
				"代收货款手续费",
				"送货费（不含上楼）",
				"基础送货费",
				"其他费用",
				"送货上楼费",
				"送货进仓费",
				"大件上楼费",
				"超远派送费",
				"签收单返回",
				"公布价运费",
				"开单操作费",
				"包装费",
				"保价费",
				"代收货款手续费",
				"送货费（不含上楼）",
				"基础送货费",
				"其他费用",
				"公布价运费",
				"开单操作费",
				"包装费",
				"保价费",
				"代收货款手续费",
				"送货费（不含上楼）",
				"基础送货费",
				"其他费用",
				"送货上楼费",
				"送货进仓费",
				"大件上楼费",
				"超远派送费",
				"签收单返回",
				"公布价运费",
				"开单操作费",
				"包装费",
				"保价费",
				"代收货款手续费",
				"送货费（不含上楼）",
				"基础送货费",
				"其他费用",
				"送货上楼费",
				"送货进仓费",
				"大件上楼费",
				"超远派送费",
				"签收单返回",
				"公布价运费",
				"开单操作费",
				"包装费",
				"保价费",
				"代收货款手续费",
				"送货费（不含上楼）",
				"基础送货费",
				"其他费用",
				"送货上楼费",
				"送货进仓费",
				"大件上楼费",
				"超远派送费",
				"签收单返回"};

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
	private Object[] exportDataAndType(List<MvrPtpPscEntity> queryList) {

		String[] properties = new String[] {
				"productCode",
				"customerName",
				"customerCode",
				"origOrgName",
				"origOrgCode",
				"destOrgName",
				"destOrgCode",
				"generatingOrgName",
				"generatingOrgCode",
				"payableOrgName",
				"payableOrgCode",
				"receivableOrgCode",
				"receivableOrgName",
				"ptpPdeApf",
				"ptpPdeAra",
				"ptpPdeApc",
				"ptpPdeCsrd",
				"ptpPdeCsrh",
				"ptpPdeRrpc",
				"ptpPdeFhbw",
				"ptpPdeBapa",
				"ptpRtfRtp",
				"ptpWfrRtr",
				"ptpWfrRtpr",
				"ptpSctLcrc",
				"ptpSctEac",
				"ptpSctZld",
				"ptpSctUeac",
				"ptpCphFpc",
				"ptpCphPrc",
				"ptpIprAcpa",
				"ptpIprPafc",
				"ptpIprPfpa",
				"ptpQfyjOcrfyPpf",
				"ptpQfyjOcrfyBof",
				"ptpQfyjOcrfyPc",
				"ptpQfyjOcrfyTif",
				"ptpQfyjOcrfyCf",
				"ptpQfyjOcrfyDf",
				"ptpQfyjOcrfyBdc",
				"ptpQfyjOcrfyOe",
				"ptpQfyjArbfyPpf",
				"ptpQfyjArbfyBof",
				"ptpQfyjArbfyPc",
				"ptpQfyjArbfyTif",
				"ptpQfyjArbfyCf",
				"ptpQfyjArbfyDf",
				"ptpQfyjArbfyBdc",
				"ptpQfyjArbfyOe",
				"ptpQfyjWtpfyDf",
				"ptpQfyjWtpfyDc",
				"ptpQfyjWtpfyLuf",
				"ptpQfyjWtpfyUld",
				"ptpQfyjWtpfySr",
				"ptpQyjSftcyPpf",
				"ptpQyjSftcyBof",
				"ptpQyjSftcyPc",
				"ptpQyjSftcyTif",
				"ptpQyjSftcyCf",
				"ptpQyjSftcyDf",
				"ptpQyjSftcyBdc",
				"ptpQyjSftcyOe",
				"ptpQyjWtpfyDf",
				"ptpQyjWtpfyDc",
				"ptpQyjWtpfyLuf",
				"ptpQyjWtpfyUld",
				"ptpQyjWtpfySr",
				"ptpQyjSftcwPpf",
				"ptpQyjSftcwBof",
				"ptpQyjSftcwPc",
				"ptpQyjSftcwTif",
				"ptpQyjSftcwCf",
				"ptpQyjSftcwDf",
				"ptpQyjSftcwBdc",
				"ptpQyjSftcwOe",
				"ptpQyjWtpfwDf",
				"ptpQyjWtpfwDc",
				"ptpQyjWtpfwLuf",
				"ptpQyjWtpfwUld",
				"ptpQyjWtpfwSr",
				"ptpFqfyjSftcyPpf",
				"ptpFqfyjSftcyBof",
				"ptpFqfyjSftcyPc",
				"ptpFqfyjSftcyTif",
				"ptpFqfyjSftcyCf",
				"ptpFqfyjSftcyDf",
				"ptpFqfyjSftcyBdc",
				"ptpFqfyjSftcyOe",
				"ptpFqfyjDdysyPpf",
				"ptpFqfyjDdysyBof",
				"ptpFqfyjDdysyPc",
				"ptpFqfyjDdysyTif",
				"ptpFqfyjDdysyCf",
				"ptpFqfyjDdysyDf",
				"ptpFqfyjDdysyBdc",
				"ptpFqfyjDdysyOe",
				"ptpFqfyjWtpfyDf",
				"ptpFqfyjWtpfyDc",
				"ptpFqfyjWtpfyLuf",
				"ptpFqfyjWtpfyUld",
				"ptpFqfyjWtpfySr",
				"ptpFqyjSftcyPpf",
				"ptpFqyjSftcyBof",
				"ptpFqyjSftcyPc",
				"ptpFqyjSftcyTif",
				"ptpFqyjSftcyCf",
				"ptpFqyjSftcyDf",
				"ptpFqyjSftcyBdc",
				"ptpFqyjSftcyOe",
				"ptpFqyjWtpfyDf",
				"ptpFqyjWtpfyDc",
				"ptpFqyjWtpfyLuf",
				"ptpFqyjWtpfyUld",
				"ptpFqyjWtpfySr",
				"ptpFqyjSftcwPpf",
				"ptpFqyjSftcwBof",
				"ptpFqyjSftcwPc",
				"ptpFqyjSftcwTif",
				"ptpFqyjSftcwCf",
				"ptpFqyjSftcwDf",
				"ptpFqyjSftcwBdc",
				"ptpFqyjSftcwOe",
				"ptpFqyjWtpfwDf",
				"ptpFqyjWtpfwDc",
				"ptpFqyjWtpfwLuf",
				"ptpFqyjWtpfwUld",
				"ptpFqyjWtpfwSr"};

		// 生成返回数据集合
		List<List<String>> resultList = new ArrayList<List<String>>();

		List<String> strProperties = Arrays.asList(new String[] {"period",
				"productCode",
				"customerName",
				"customerCode",
				"origOrgName",
				"origOrgCode",
				"destOrgName",
				"destOrgCode",
				"generatingOrgName",
				"generatingOrgCode",
				"payableOrgName",
				"payableOrgCode",
				"receivableOrgCode",
				"receivableOrgName" });

		List<String> data = null;
		Object fieldValue = null;
		String cellValue = null;

		// 循环处理
		for (MvrPtpPscEntity entity : queryList) {

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



	public void setMvrPtpPscService(IMvrPtpPscService mvrPtpPscService) {
		this.mvrPtpPscService = mvrPtpPscService;
	}

	public MvrPtpPscVo getMvrPtpPscVo() {
		return mvrPtpPscVo;
	}

	public void setMvrPtpPscVo(MvrPtpPscVo mvrPtpPscVo) {
		this.mvrPtpPscVo = mvrPtpPscVo;
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
