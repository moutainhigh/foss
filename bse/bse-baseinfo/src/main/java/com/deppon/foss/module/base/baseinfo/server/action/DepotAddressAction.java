package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepotAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepotAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.DepotAddressVo;

/**
 * 
 * @ClassName: DepotAddressAction
 * @Description: 进仓地址管理ACtion
 * @author 310854-liuzhenhua
 * @date 2016-9-1 下午2:46:36
 * 
 */
public class DepotAddressAction extends AbstractAction {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 3179011413473049167L;

	private static final Logger LOGGER = LoggerFactory.getLogger(DepotAddressAction.class);
	
	/**
	 * 进仓地址参数和结果对象
	 */
	private DepotAddressVo depotAddressVo;

	private IDepotAddressService depotAddressService;
	
	private AdministrativeRegionsEntity areaEntity;

	private String downloadFileName;

	private InputStream inputStream;
	
	private IAreaAddressService areaAddressService;
	
	private String resultStr;
	
	private String areaName;
	
	public AdministrativeRegionsEntity getAreaEntity() {
		return areaEntity;
	}

	public void setAreaEntity(AdministrativeRegionsEntity areaEntity) {
		this.areaEntity = areaEntity;
	}

	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public DepotAddressVo getDepotAddressVo() {
		return depotAddressVo;
	}

	public void setDepotAddressVo(DepotAddressVo depotAddressVo) {
		this.depotAddressVo = depotAddressVo;
	}

	public void setDepotAddressService(IDepotAddressService depotAddressService) {
		this.depotAddressService = depotAddressService;
	}

	/**
	 * 查询进仓地址
	 * 
	 * @Title: queryDepotAddress
	 * @Description: 查询进仓地址
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @user 310854-liuzhenhua
	 */
	@JSON
	public String queryDepotAddress() {
		try {
			List<DepotAddressEntity> depotAddressEntitys = depotAddressService
					.queryDepotAddress(depotAddressVo.getDepotAddress(), start,
							limit);
			depotAddressVo.setDepotAddressEntitys(depotAddressEntitys);
			long totalCount = depotAddressService
					.getCountByCondition(depotAddressVo.getDepotAddress());
			this.setTotalCount(totalCount);
		} catch (BusinessException e) {
			return returnError("查询进仓地址出错：" + e.getMessage());
		}
		return returnSuccess();
	}

	/**
	 * 新增进仓地址
	 * 
	 * @Title: addDepotAddress
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @user 310854-liuzhenhua
	 */
	@JSON
	public String addDepotAddress() {
		try {
			depotAddressService.addDepotAddress(depotAddressVo
					.getDepotAddress());
		} catch (BusinessException e) {
			return returnError("新增进仓地址出错：" + e.getMessage());
		}
		return returnSuccess();
	}

	/**
	 * 修改进仓地址
	 * 
	 * @Title: updateDepotAddress
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @user 310854-liuzhenhua
	 */
	@JSON
	public String updateDepotAddress() {
		try {
			depotAddressService.updateDepotAddress(depotAddressVo
					.getDepotAddress());
		} catch (BusinessException e) {
			return returnError("修改进仓地址出错：" + e.getMessage());
		}
		return returnSuccess();
	}

	/**
	 * 作废进仓地址
	 * 
	 * @Title: deleteDepotAddress
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @user 310854-liuzhenhua
	 */
	@JSON
	public String deleteDepotAddress() {
		try {
			depotAddressService.deleteDepotAddress(depotAddressVo
					.getDepotAddress());
		} catch (BusinessException e) {
			return returnError("作废进仓地址出错：" + e.getMessage());
		}
		return returnSuccess("作废成功！");
	}

	/**
	 * 导出进仓信息
	* @Title: exportDepotAddresss 
	* @Description: TODO(导出进仓信息) 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	* @user 310854-liuzhenhua
	 */
	public String exportDepotAddresss() {
		try {
			// 导出文件名称
			// downloadFileName =
			// URLEncoder.encode(ColumnConstants.EXPROT_ORIGINATING_LINE_NAME,
			// "UTF-8");
			downloadFileName = new String(
					ColumnConstants.EXPROT_DEPOT_ADDRESS_NAME
							.getBytes("UTF-8"),
					"iso-8859-1");
			// 获取查询参数
		
			// 导出始发线路
			ExportResource exportResource = depotAddressService.exportDepotAddressList();
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting
					.setSheetName(ColumnConstants.EXPROT_DEPOT_ADDRESS_NAME);
			// 设置下载最大条数
			// exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 导出成文件
			inputStream = objExporterExecutor.exportSync(exportResource,
					exportSetting);

			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError("UnsupportedEncodingException", e);
		}
	}
	
	/**
	 * 获取省/市/区县code
	* @Title: queryProvince 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	* @user 310854-liuzhenhua
	 */
	@JSON
	public String queryAreaByName() {
		if(null != areaEntity 
				&& !StringUtil.isEmpty(areaEntity.getName()) 
				&& !StringUtil.isEmpty(areaEntity.getDegree())){
			
			List<AdministrativeRegionsEntity> list = areaAddressService.getAdministrativeRegionsEntityByName(areaEntity,NumberConstants.ZERO,NumberConstants.NUMBER_1);
			if(null != list && 0 < list.size()){
				resultStr = list.get(0).getCode();
			}
		}
		
		return returnSuccess();
	}
}
