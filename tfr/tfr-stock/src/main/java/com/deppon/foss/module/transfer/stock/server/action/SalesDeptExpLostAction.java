package com.deppon.foss.module.transfer.stock.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressEmployeeService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.stock.api.server.service.ISalesDeptExpLostService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.SalesDeptExpLostEntity;
import com.deppon.foss.module.transfer.stock.api.shared.vo.SalesDeptExpLostVo;

public class SalesDeptExpLostAction extends AbstractAction {

	private static final long serialVersionUID = 2105887286119647613L;

	private ISalesDeptExpLostService salesDeptExpLostService;

	private SalesDeptExpLostVo salesDeptExpLostVo = new SalesDeptExpLostVo();

	private InputStream excelStream;

	private String downloadFileName;

	public void setSalesDeptExpLostService(
			ISalesDeptExpLostService salesDeptExpLostService) {
		this.salesDeptExpLostService = salesDeptExpLostService;
	}

	public SalesDeptExpLostVo getSalesDeptExpLostVo() {
		return salesDeptExpLostVo;
	}

	public void setSalesDeptExpLostVo(SalesDeptExpLostVo salesDeptExpLostVo) {
		this.salesDeptExpLostVo = salesDeptExpLostVo;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	/**
	 * 获取快递员车辆绑定的开单营业部 
	 */
    private IExpressVehiclesService expressVehiclesService;
	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}
	/**
	 * 
	 * 和判断登录的是不是快递员
	 */
	private ICommonExpressEmployeeService commonExpressEmployeeService;
	public void setCommonExpressEmployeeService(
			ICommonExpressEmployeeService commonExpressEmployeeService) {
		this.commonExpressEmployeeService = commonExpressEmployeeService;
	}
	
	
	/**
	 * @desc 查询差异报告
	 * @return
	 * @date 2014年10月27日 上午10:52:51
	 * @author Ouyang
	 */
	public String queryReportWaybillNoPaging() {
		SalesDeptExpLostEntity info = salesDeptExpLostVo.getQcEntity();
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
			String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());//获取快递车辆绑定的营业部
			info.setOrgCode(code);
		}
		salesDeptExpLostVo.setSalesDeptExpLostEntities(salesDeptExpLostService
				.queryReportWaybillNoPaging(info, super.start, super.limit));
		super.setTotalCount(salesDeptExpLostService
				.queryReportWaybillNoCnt(info));
		return SUCCESS;
	}

	/**
	 * @desc 查询流水号
	 * @return
	 * @date 2014年10月27日 上午10:53:10
	 * @author Ouyang
	 */
	public String queryReportSerialNo() {
		SalesDeptExpLostEntity info = salesDeptExpLostVo.getQcEntity();
		salesDeptExpLostVo.setWaybillSerialNos(salesDeptExpLostService
				.queryReportSerialNo(info));
		return SUCCESS;
	}

	/**
	 * @desc 导出excel文件名处理
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 * @date 2014年10月27日 上午11:02:30
	 * @author Ouyang
	 */
	private String encodeFileName(String name)
			throws UnsupportedEncodingException {
		String result = null;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			result = new String(name.getBytes("UTF-8"), "iso-8859-1");
		} else {
			result = URLEncoder.encode(name, "UTF-8");
		}
		return result;
	}

	/**
	 * @desc 导出excel
	 * @return
	 * @date 2014年10月27日 上午10:53:18
	 * @author Ouyang
	 */
	public String exportReport() {
		// 获取查询条件
		SalesDeptExpLostEntity info = salesDeptExpLostVo.getQcEntity();
		// 查询差异报告
		ExportResource exportResource = new ExportResource();
		try {
			exportResource = salesDeptExpLostService.exportReport(info);
		} catch (BusinessException e) {
			return returnError(e);
		}

		// 导出文件名
		String fileName = "快递派送丢货差异报告.xls";
		try {
			downloadFileName = encodeFileName(fileName);
		} catch (UnsupportedEncodingException e) {
			downloadFileName = fileName;
		}

		ExportSetting exportSetting = new ExportSetting();
		// sheet名
		exportSetting.setSheetName("快递派送丢货差异报告");

		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		// 导出文件
		excelStream = objExporterExecutor.exportSync(exportResource,
				exportSetting);

		return SUCCESS;
	}
}
