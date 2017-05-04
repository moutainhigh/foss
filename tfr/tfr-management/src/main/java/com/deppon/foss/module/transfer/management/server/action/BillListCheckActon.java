package com.deppon.foss.module.transfer.management.server.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.management.api.server.service.IBillListCheckService;
import com.deppon.foss.module.transfer.management.api.shared.define.BillListCheckConstants;
import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckStaDto;
import com.deppon.foss.module.transfer.management.api.shared.vo.BillListCheckVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

import org.apache.commons.lang.StringUtils;

public class BillListCheckActon extends AbstractAction {
	private static final Logger logger = LoggerFactory.getLogger(BillListCheckActon.class);
	/**
	 * 序号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 对账单Service类
	 * 
	 * @fields billListCheckService
	 * @author 14022-foss-songjie
	 * @update 2013年11月26日 下午2:06:41
	 * @version V1.0
	 */
	@Resource(name = "billListCheckService")
	private IBillListCheckService billListCheckService;

	/**
	 * 上传的文件
	 * 
	 * @fields uploadFile
	 * @author 14022-foss-songjie
	 * @update 2013年11月29日 上午9:41:30
	 * @version V1.0
	 */
	private File uploadFile;

	/**
	 * 上传的文件名称
	 * 
	 * @fields uploadFileName
	 * @author 14022-foss-songjie
	 * @update 2013年11月29日 上午9:41:40
	 * @version V1.0
	 */
	private String uploadFileFileName;

	/**
	 * 导出Excel 文件流
	 */
	transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;

	/**
	 * 对账单Vo
	 */
	private BillListCheckVo billListCheckVo = new BillListCheckVo();

	/**
	 * 根据查询条件 查询 对账单pojo list （分页显示）
	 * 
	 * @return
	 */
	@JSON
	public String queryBillList() {
		billListCheckService.queryCurrentInfo(FossUserContext.getCurrentDept(),
				billListCheckVo);
		if (BillListCheckConstants.YES.equals(billListCheckVo.getIsTransTeam())) {
			billListCheckVo.setDivisionOrgCode(billListCheckVo.getCurrentCareerCode());
			billListCheckVo.setTransDepartmentCode(billListCheckVo.getCurrentOrgCode());
		}
		
		List<BillListCheckDto> voList = billListCheckService
				.queryBillListCheck(billListCheckVo, start, limit);
		BillListCheckStaDto billListCheckStaDto = billListCheckService
				.queryBillListCheckStatic(billListCheckVo);
		long queryCount = billListCheckService
				.queryBillListCheckCount(billListCheckVo);

		billListCheckVo.setBillListCheckList(voList);
		billListCheckVo.setBillListCheckStaDto(billListCheckStaDto);
		this.setTotalCount(queryCount);

		return returnSuccess();
	}

	/**
	 * 根据对账单id查询 对账单pojo
	 * 
	 * @return
	 */
	@JSON
	public String queryBillListById() {
		return returnSuccess();
	}

	/**
	 * 修改对账单
	 * 
	 * @return
	 */
	public String modBillListById() {
		try {
			billListCheckVo.getBillListCheckDto().setOperatorCode(
					FossUserContext.getCurrentInfo().getEmpCode());
			billListCheckVo.getBillListCheckDto().setOperatorName(
					FossUserContext.getCurrentInfo().getEmpName());
			billListCheckService.updateBillListCheckDto(billListCheckVo
					.getBillListCheckDto());
		} catch (BusinessException be) {
			return returnError(be.getMessage());
		}
		return returnSuccess();
	}

	/**
	 * 导出对账单
	 * 
	 * @return
	 */
	public String exportBillList() {
		try {
			// 文件名
			try {
				fileName = this.encodeFileName("电子对账单");
			} catch (UnsupportedEncodingException e) {
				//sonar-352203
				logger.info("BillListCheckActon.exportBillList 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = billListCheckService
					.exportExcelStream(billListCheckVo);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 电子对账单模板下载
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月10日 下午7:07:37
	*/
	public String downFileModel(){
		try {
			// 文件名
			try {
				fileName = this.encodeFileName("电子对账单");
			} catch (UnsupportedEncodingException e) {
				//sonar-352203
				logger.info("BillListCheckActon.downFileModel 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = billListCheckService.downFileModel();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}

	/**
	 * detail 添加对账单明细
	 * 
	 * @return
	 */
	@JSON
	public String addBillListDetail() {
		billListCheckService.queryCurrentInfo(FossUserContext.getCurrentDept(),
				billListCheckVo);
		if (BillListCheckConstants.YES.equals(billListCheckVo.getIsTransTeam())) {
			try {
				billListCheckVo.getBillListCheckDto().setOperatorCode(
						FossUserContext.getCurrentInfo().getEmpCode());
				billListCheckVo.getBillListCheckDto().setOperatorName(
						FossUserContext.getCurrentInfo().getEmpName());
				billListCheckService.addBillListCheckDto(billListCheckVo
						.getBillListCheckDto());
			} catch (BusinessException be) {
				return returnError("添加失败");
			}
			return returnSuccess();
		} else {
			return returnError("非车队账户不可以导入");
		}
	}

	/**
	 * detail 导入对账单明细
	 * 
	 * @return
	 */
	public String importBillListDetail() {
		billListCheckService.queryCurrentInfo(FossUserContext.getCurrentDept(),
				billListCheckVo);
		if (BillListCheckConstants.YES.equals(billListCheckVo.getIsTransTeam())) {
			BillListCheckDto dto = new BillListCheckDto();
			dto.setOperateDate(new Date());
			dto.setOperatorCode(FossUserContext.getCurrentInfo().getEmpCode());
			dto.setOperatorName(FossUserContext.getCurrentInfo().getEmpName());
			dto.setDivisionOrgCode(billListCheckVo.getCurrentCareerCode());
			dto.setDivisionOrgName(billListCheckVo.getCurrentCareerName());
			dto.setTransDepartmentCode(billListCheckVo.getCurrentOrgCode());
			dto.setTransDepartmentName(billListCheckVo.getCurrentOrgName());
			billListCheckVo.setBillListCheckDto(dto);
			try {
				billListCheckService.importFileComm(uploadFile,
						uploadFileFileName, billListCheckVo);
			} catch (BusinessException e) {
				return returnError(e.getMessage());
			}
		} else {
			return returnError("非车队账户不可以导入");
		}

		return returnSuccess();
	}

	/**
	 * @description 根据当前部门获取相关信息
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月28日 下午2:14:49
	 */

	@JSON
	public String queryCurrentInfo() {
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = FossUserContext
				.getCurrentDept();
		billListCheckService.queryCurrentInfo(orgAdministrativeInfoEntity,
				billListCheckVo);
		return returnSuccess();
	}

	/**
	 * 获取对账单Vo
	 * 
	 * @return
	 */
	public BillListCheckVo getBillListCheckVo() {
		return billListCheckVo;
	}

	/**
	 * 设置对账单Vo
	 * 
	 * @param billListCheckVo
	 */
	public void setBillListCheckVo(BillListCheckVo billListCheckVo) {
		this.billListCheckVo = billListCheckVo;
	}

	@Override
	public String execute() throws Exception {
		return returnSuccess();
	}

	/**
	 * @description 获取上传的文件
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月29日 上午9:42:51
	 */
	public File getUploadFile() {
		return uploadFile;
	}

	/**
	 * @description 设置上传的文件
	 * @param uploadFile
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月29日 上午9:42:55
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * @description 获取上传文件的名称
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月29日 上午10:42:24
	 */
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	/**
	 * @description 设置上传文件的名称
	 * @param uploadFileFileName
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月29日 上午10:42:27
	 */
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	/**
	 * @description 获取Excel文件名
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月29日 下午3:22:59
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @description 设置Excel文件名
	 * @param fileName
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月29日 下午3:23:03
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @description 获取导出工作流
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月29日 下午3:23:37
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * 转换导出文件的文件名
	 * 
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 * @description 转换导出文件的文件名
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年09月29日 上午10:30:50
	 */
	public String encodeFileName(String name)
			throws UnsupportedEncodingException {
		String returnStr;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
		} else {
			returnStr = URLEncoder.encode(name, "UTF-8");
		}
		return returnStr;
	}

	/*
	*//**
	 * @description 注入billListCheckService
	 * @param billListCheckService
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2013年11月28日 下午5:27:38
	 */
	/*
	 * public void setBillListCheckService(IBillListCheckService
	 * billListCheckService) { this.billListCheckService = billListCheckService;
	 * }
	 * 
	 * 
	 * public IBillListCheckService getBillListCheckService() { return
	 * billListCheckService; }
	 */

}
