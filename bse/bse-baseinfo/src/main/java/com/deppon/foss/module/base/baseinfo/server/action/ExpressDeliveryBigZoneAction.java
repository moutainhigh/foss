
package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryBigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressDeliverySmallZoneVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 
 * @author 130134
 *
 */
public class ExpressDeliveryBigZoneAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6757517025157239788L;

    /**
     * 
     */
    
    private IExpressDeliveryBigZoneService expressDeliveryBigZoneService;
 
    
    private IExpressDeliverySmallZoneService expressDeliverySmallZoneService;

    /**
     * 导出Excel 文件名.
     */
    private String downloadFileName;

    /**
     * 导出Excel 文件流
     */
    private InputStream inputStream;
    
    private ExpressDeliverySmallZoneVo objectVo = new ExpressDeliverySmallZoneVo();

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressDeliveryBigZoneAction.class);
	
	/**
	 * 
	 * @return
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:21:22
	 */
    @JSON
    public String queryexpressDeliveryBigZoneByEntity() {
	
	ExpressDeliveryBigZoneEntity entityCondition = objectVo.getExpressDeliveryBigZoneEntity();
	entityCondition.setEmpCode(FossUserContext.getCurrentInfo().getEmpCode());
	
	// 返回的结果显示在表格中：
	objectVo.setBigZoneEntityList(expressDeliveryBigZoneService.queryExpressDeliveryBigZones(entityCondition, limit, start));
	totalCount = expressDeliveryBigZoneService.queryRecordCount(entityCondition);
	return returnSuccess();
    }
    
    /**
     * 自动生成大区编码
     * @return
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午1:25:25
     */
    public String autoCreateExpressBigZoneNum() {
    try{
	String regionCode = expressDeliveryBigZoneService.generateCode(objectVo.getExpressDeliveryBigZoneEntity().getManagement());
	objectVo.setCodeStr(regionCode);
    }catch (BusinessException e) {
    	return returnError(e);
	}
	return returnSuccess();
    }
    
    

    /**
     * 根据管理部门查询
     * @return
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午1:25:33
     */
    public String queryExpressDeliveryBigZoneByDeptCode() {

    	ExpressDeliverySmallZoneEntity entityCondition = objectVo.getExpressDeliverySmallZoneEntity();
	// 返回的结果显示在表格中：
	objectVo.setSmallZoneEntityList(expressDeliverySmallZoneService.querySmallZonesByDeptCode(entityCondition.getManagement(),
			entityCondition.getRegionType(),
			entityCondition.getBigzonecode()));
	return returnSuccess();
    }
    
    
    /**
     * 作废大区信息
     * @return
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午1:26:18
     */
    public String deleteExpressDeliveryBigZone() {
	try {
	    objectVo.setReturnInt(expressDeliveryBigZoneService.deleteExpressDeliveryBigZoneByCode(
			    objectVo.getCodeStr(), FossUserContext.getCurrentInfo().getEmpCode()));
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
   /**
    * 新增大区信息
    * @return
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午1:26:36
    */
    public String addExpressDeliveryBigZone() {
	try {
	    objectVo.setReturnInt(expressDeliveryBigZoneService
		    .addExpressDeliveryBigZone(objectVo.getExpressDeliveryBigZoneEntity(),
			    objectVo.getAddSmallZoneList(),
			    objectVo.getDelSmallZoneList()));
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    
    /**
     * 修改大区信息
     * @return
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午1:26:49
     */
    public String updateExpressDeliveryBigZone() {
	try {
	    objectVo.setReturnInt(expressDeliveryBigZoneService
		    .updateExpressDeliveryBigZone(
			    objectVo.getExpressDeliveryBigZoneEntity(),
			    objectVo.getAddSmallZoneList(),
			    objectVo.getDelSmallZoneList()));
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    /**
     * 导出大区信息
     * @return
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午1:26:59
     */
    public String exportExpressDeliveryBigZoneList(){
    	
    	try {
    	    // 导出文件名称
    	    downloadFileName = URLEncoder.encode(ColumnConstants.EXPRESS_BIG_ZONE_NAME, "UTF-8");
    	    // 获取查询参数
    	    ExpressDeliveryBigZoneEntity entity = objectVo.getExpressDeliveryBigZoneEntity();
    	    if (null == entity) {
    		entity = new ExpressDeliveryBigZoneEntity();
    	    }
    	    entity.setEmpCode(FossUserContext.getCurrentInfo().getEmpCode());
    	    // 获取导出数据对象
    	    ExportResource exportResource = expressDeliveryBigZoneService.exportBigZoneList(entity);

    	    ExportSetting exportSetting = new ExportSetting();
    	    // 设置名称
    	    exportSetting.setSheetName(ColumnConstants.EXPRESS_BIG_ZONE_NAME);

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
    
    //---------------------------------------------------------
   /**
    * 判断某个元素在集合中是否存在
    * @param list
    * @param element
    * @return
    * @param @param list
    * @param @param element
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午1:27:17
    */
    /*@SuppressWarnings("unused")
	private boolean exists(List<String> list,String element){
	if(CollectionUtils.isNotEmpty(list) && null != element){
	    for (String elment2 : list) {
                if (StringUtils.equals(elment2,element)) {
                    return true;
                }
            }
	}
	return false;
    }*/
	public ExpressDeliverySmallZoneVo getObjectVo() {
		return objectVo;
	}
	public void setObjectVo(ExpressDeliverySmallZoneVo objectVo) {
		this.objectVo = objectVo;
	}
	public void setExpressDeliveryBigZoneService(
			IExpressDeliveryBigZoneService expressDeliveryBigZoneService) {
		this.expressDeliveryBigZoneService = expressDeliveryBigZoneService;
	}
	public void setExpressDeliverySmallZoneService(
			IExpressDeliverySmallZoneService expressDeliverySmallZoneService) {
		this.expressDeliverySmallZoneService = expressDeliverySmallZoneService;
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
