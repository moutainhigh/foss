package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LeasedVehicleVo;
/**
 * 外请车基础数据的导出的action
 * @author Foss-130566 zengJunFan
 *
 */

public class ExportLeasedDriverExcelAction extends AbstractAction implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 6717943718642702462L;


	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportLeasedDriverExcelAction.class);
	
	/**
	 * 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
	 */
	private InputStream excelStream;
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String fileName;
	/**
	 * vo
	 */
	private LeasedVehicleVo leasedVehicleVo;
	/**
	 * 外请车的service
	 */
	private ILeasedVehicleService leasedVehicleService;
	/**
	 * 
	 *<p>导出外请车（外墙挂车，厢式车，拖头）</p>
	 *@author 130566-zengJunfan
	 *@date   2013-8-2上午9:33:34
	 * @return
	 */
	public String exportLeasedVehicle(){
		try {
			LeasedTruckEntity leasedTruck = leasedVehicleVo.getLeasedVehicle();
			//外请车不为空，并且外请车类型不为空
			if(leasedTruck !=null && StringUtils.isNotBlank(leasedTruck.getVehicleType())){
				//若类型是外请挂车 ，设置其表文件名
				String excelName="";
				//若类型是外请挂车 ，设置其表文件名
				if(leasedTruck.getVehicleType().equals("vehicletype_trailer")){ 
					excelName ="外请挂车信息";
				}else if(leasedTruck.getVehicleType().equals("vehicletype_tractors")){ //若类型是外请拖头
					excelName ="外请拖头信息";
				}else{ //否则是外请厢式车
					excelName ="外请厢式车信息";
				}
				fileName =new String(excelName.getBytes("UTF-8"), "iso-8859-1");
			}
			//导出表格
			excelStream =leasedVehicleService.exportLeaseVehicle(leasedTruck);
		}catch (UnsupportedEncodingException e) {
			LOGGER.debug(e.getMessage());
			return returnError("UnsupportedEncodingException",e);
		}
		return returnSuccess();
	}
	
	
	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
	}
	

	public LeasedVehicleVo getLeasedVehicleVo() {
		return leasedVehicleVo;
	}

	public void setLeasedVehicleVo(LeasedVehicleVo leasedVehicleVo) {
		this.leasedVehicleVo = leasedVehicleVo;
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
	
}
