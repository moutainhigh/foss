package com.deppon.foss.module.pickup.pickup.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pickup.api.server.service.IDriverPickupBillPrintService;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.DriverPickupBillPrintDto;
import com.deppon.foss.module.pickup.pickup.api.shared.exception.DriverPickupBillPrintException;
import com.deppon.foss.module.pickup.pickup.api.shared.vo.DriverPickupBillPrintVo;

public class DriverPickupBillPrintAction extends AbstractAction{
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverPickupBillPrintAction.class);
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 视图对象
	 */
	private DriverPickupBillPrintVo vo = new DriverPickupBillPrintVo();
	
	/**
	 * 司机接货单号service
	 */
	private IDriverPickupBillPrintService driverPickupBillPrintService;
	
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String fileName;
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public IDriverPickupBillPrintService getDriverPickupBillPrintService() {
		return driverPickupBillPrintService;
	}


	/**
	 * 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
	 */
	private InputStream excelStream;
	/**
	 * @author 306486
	 * 查询运单列表
	 * @date 2016年3月1日08:22:47
	 */
	@JSON
	public String queryDriverPickupBill() {
		try{
			//获取当前登入信息部门编码
			String orgCode = FossUserContext.getCurrentUser().getEmployee().getOrgCode();
			DriverPickupBillPrintDto dto = vo.getDriverPickupBillPrintDto();
			//将部门编码封入DTO
			dto.setOrgCode(orgCode);
			//查询运单返回数量
			Long totalCount = driverPickupBillPrintService.queryDriverPickupBillPrintTotal(dto);
			if(totalCount != null && totalCount.intValue() > 0)
			{
				//查询运单
				vo.setRtDriverPickupBillPrintDtoList(driverPickupBillPrintService.queryDriverPickupBillPrint(
						vo.getDriverPickupBillPrintDto(),this.getStart(),
						this.getLimit()));
			}else{
				vo.setRtDriverPickupBillPrintDtoList(null);
			}
			//TOTAL
			this.setTotalCount(totalCount);
		} catch (DriverPickupBillPrintException e) {
			//GO ERROR
			return returnError(e);
		}
		//GO SUCCESS
		return returnSuccess();
	}
	
	/**
	 * 
	 * 导出Excel表格
	 * 
	 * @author 306486-foss-wangshuai
	 * @date 2016年3月1日
	 */
	public String exportDriverPickupBill(){
		try {
			String orgCode = FossUserContext.getCurrentUser().getEmployee().getOrgCode();
			DriverPickupBillPrintDto dto = vo.getDriverPickupBillPrintDto();
			//将部门编码封入DTO
			dto.setOrgCode(orgCode);
			//设置文件名
			fileName = encodeFileName("司机接货单号");
			//返回导出信息
			excelStream = driverPickupBillPrintService.queryDriverPickupBillPrint(dto);
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 
	 * 转换导出文件的文件名
	 * @author 306486
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 * @see
	 */
    public String encodeFileName(String name) throws UnsupportedEncodingException {
    	String returnStr;
    	String agent = (String)ServletActionContext.getRequest().getHeader("USER-AGENT");
    	if(agent != null && agent.indexOf("MSIE") == -1) {
    		returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
    	} else {
    		returnStr = URLEncoder.encode(name, "UTF-8");
    	}
    	return returnStr;
    }

	
	/**
	 * Sets 
	 * 		the 司机接货单号打印service.
	 *
	 * @param driverPickupBillPrintService 
	 * 		the new 司机接货单号打印service
	 */
	public void setDriverPickupBillPrintService(
			IDriverPickupBillPrintService driverPickupBillPrintService) {
		this.driverPickupBillPrintService = driverPickupBillPrintService;
	}
	


	/**
	 * Gets the 视图对象.
	 *
	 * @return the 视图对象
	 */
	public DriverPickupBillPrintVo getVo() {
		return vo;
	}


	/**
	 * Sets the 视图对象.
	 *
	 * @param vo the new 视图对象
	 */
	public void setVo(DriverPickupBillPrintVo vo) {
		this.vo = vo;
	}
	
}
