package com.deppon.foss.module.transfer.partialline.server.action;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.transfer.partialline.api.server.service.ISalesdeptDeliveryprocService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.SalesdeptDeliveryEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.SalesdeptDeliveryprocException;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.SalesDeptDeliveryProcVo;

public class SalesdeptDeliveryprocAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private ISalesdeptDeliveryprocService salesDeptDeliveryProcService;

	/**
	 * 
	 * @param salesdeptDeliveryprocService
	 */
	public void setSalesDeptDeliveryProcService(
			ISalesdeptDeliveryprocService salesDeptDeliveryProcService) {
		this.salesDeptDeliveryProcService = salesDeptDeliveryProcService;
	}

	private SalesDeptDeliveryProcVo objectVo = new SalesDeptDeliveryProcVo();

	private String downloadFileName;
	
	 /**
     * 导出Excel 文件流
     */
    private InputStream inputStream;
	/**
	 * 
	 * @return
	 */
	public String salesDeptDeliveryQuery() {
		try {
			List<SalesdeptDeliveryEntity> salesdeptDeliveryProcs = salesDeptDeliveryProcService
					.salesDeptDeliveryQuery(objectVo.getWaynoStr(),objectVo.getStatus());
			if (!CollectionUtils.isEmpty(salesdeptDeliveryProcs)) {
				objectVo.setSalesdeptDeliveryEntitys(salesdeptDeliveryProcs);
				this.setTotalCount((long) salesdeptDeliveryProcs.size());
			}
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}

	/**
	 * 运单货物交接
	 * 
	 * @return
	 */
	public String goodsHandOver() {
		try {
			List<SalesdeptDeliveryEntity> salesdeptDeliveryProcs = objectVo
					.getSalesdeptDeliveryEntitys();
			salesDeptDeliveryProcService.addSalesDeptDeliveryList(salesdeptDeliveryProcs);
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}

	/**
	 * 导出明细(只能导出已确认运单信息)
	 * @return
	 */
	public String exportWayNoDetail(){
		try {
			
			// 导出文件名称
		    downloadFileName = URLEncoder.encode(ColumnConstants.EXPORT_WAYBILLINFO_NAME, "UTF-8");
//		    // 获取查询参数
//		    String wayNoStr=objectVo.getWaynoStr();
//		    String wayNoStatus=objectVo.getStatus();
		    
		    SalesdeptDeliveryEntity entity=objectVo.getSalesdeptDeliveryEntity();
		    if(null==entity){
		    	throw new SalesdeptDeliveryprocException("请选择你要导出的运单信息！","最请选择你要导出的运单信息！");
		    }
		    // 获取导出数据对象
		    ExportResource exportResource = salesDeptDeliveryProcService.exprotSalesDeptDeliveryQuery(entity);
//
		    ExportSetting exportSetting = new ExportSetting();
		    // 设置名称
		    exportSetting.setSheetName(ColumnConstants.EXPORT_WAYBILLINFO_NAME);

		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
		    // 导出成文件
		    inputStream = objExporterExecutor.exportSync(exportResource,
			    exportSetting);	
		    return returnSuccess();
		} catch (Exception e) {
			return returnError(e.getMessage());
//			 return returnSuccess();
		}
		
	}
	
	/**
	 * 取消交货确认
	 * @return
	 */
	public String canlGoodsHandOver(){
		
		try {
			List<SalesdeptDeliveryEntity> salesdeptDeliveryProcs = objectVo
					.getSalesdeptDeliveryEntitys();
			salesDeptDeliveryProcService.updateSalesDeptDeliveryList(salesdeptDeliveryProcs);
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	
	public SalesDeptDeliveryProcVo getObjectVo() {
		return objectVo;
	}

	public void setObjectVo(SalesDeptDeliveryProcVo objectVo) {
		this.objectVo = objectVo;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
