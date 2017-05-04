package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressDeliverySmallZoneVo;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class ExpressDeliverySmallZoneAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8217086612960164348L;

	 /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressDeliverySmallZoneAction.class);
	

	private ExpressDeliverySmallZoneVo objectVo = new ExpressDeliverySmallZoneVo();

	private IExpressDeliverySmallZoneService expressDeliverySmallZoneService;

	/**
     * 业务锁
     */
 	private IBusinessLockService businessLockService;
	 
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setExpressDeliverySmallZoneService(
			IExpressDeliverySmallZoneService expressDeliverySmallZoneService) {
		this.expressDeliverySmallZoneService = expressDeliverySmallZoneService;
	}
	/**
     * 导出Excel 文件名.
     */
    private String downloadFileName;

    /**
     * 导出Excel 文件流
     */
    private InputStream inputStream;

	
	
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

	 

	public ExpressDeliverySmallZoneVo getObjectVo() {
		return objectVo;
	}

	public void setObjectVo(ExpressDeliverySmallZoneVo objectVo) {
		this.objectVo = objectVo;
	}

	public String queryExpressDeliverySmallZoneByEntity() {
		
		ExpressDeliverySmallZoneEntity entityCondition = objectVo.getExpressDeliverySmallZoneEntity();
		entityCondition.setEmpCode(FossUserContext.getCurrentInfo().getEmpCode());
		/*List<String> deptCodes = FossUserContext.getCurrentUserManagerDeptCodes();
		ExpressDeliverySmallZoneEntity entityCondition=null;
		//获取页面传输的值
		String empCode=FossUserContext.getCurrentInfo().getEmpCode();

		if(!CollectionUtils.isEmpty(deptCodes)){
			if(null==entityCondition||StringUtils.isEmpty(entityCondition.getManagement())){
				entityCondition=new ExpressDeliverySmallZoneEntity();
				List<String> orgCodes=orgAdministrativeInfoComplexService.queryExpressExpressPartByDeptCode(empCode);
				if(CollectionUtils.isEmpty(orgCodes)){
					return returnError("您对该点部没有查询权限！");
				}
				entityCondition.setManagementCodeList(orgCodes);
			}else{
				 if(!exists(deptCodes,entityCondition.getManagement())){
					 return returnError("您对该点部没有查询权限！"); 
				 }
			}
		}else{
			return returnError("您对该点部没有查询权限！");
		}*/
		// 返回的结果显示在表格中：
		objectVo.setSmallZoneEntityList(expressDeliverySmallZoneService.queryExpressDeliverySmallZones(entityCondition, limit, start));
		totalCount = expressDeliverySmallZoneService.queryRecordCount(entityCondition);
		return returnSuccess();
	}

	/**
	 * 判断某个元素在集合中是否存在
	 * @param list
	 * @param element
	 * @return
	 * @param @param list
	 * @param @param element
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午3:12:22
	 */
	/*@SuppressWarnings("unused")
	private boolean exists(List<String> list, String element) {
		if (CollectionUtils.isNotEmpty(list) && null != element) {
			for (String elment2 : list) {
				if (StringUtils.equals(elment2, element)) {
					return true;
				}
			}
		}
		return false;
	}*/
	
	/**
	 * 新增小区信息
	 * @return
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午3:12:31
	 */
	public String addExpressDeliverySmallZone() {

		try {
			
			ExpressDeliverySmallZoneEntity entity =objectVo.getExpressDeliverySmallZoneEntity();
			//新增时 去空格
			entity.setRegionName(entity.getRegionName().trim());
			//优化加锁-187862-dujunhui
			MutexElement mutex = new MutexElement(String.valueOf(objectVo.
					getExpressDeliverySmallZoneEntity().getRegionCode()), 
					"EXPRESS_DELIVERY_SMALLZONE_REGIONCODE",MutexElementType.
					EXPRESS_DELIVERY_SMALLZONE_REGIONCODE);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
				int l = expressDeliverySmallZoneService
						.addExpressDeliverySmallZone(entity);
				objectVo.setReturnInt(l);
				
				LOGGER.info("开始解锁：" + mutex.getBusinessNo());
				// 解业务锁
				businessLockService.unlock(mutex);
				LOGGER.info("完成解锁：" + mutex.getBusinessNo());
			} else {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
    /**
     * 更新小区信息
     * @return
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午3:12:48
     */
    public String updateExpressDeliverySmallZone() {
	try {

		ExpressDeliverySmallZoneEntity entity =objectVo.getExpressDeliverySmallZoneEntity();
		//去空格
		entity.setRegionName(entity.getRegionName().trim());
	    objectVo.setReturnInt(expressDeliverySmallZoneService.updateExpressDeliverySmallZone(objectVo
			    .getExpressDeliverySmallZoneEntity()));
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 作废小区信息
     * @return
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午3:13:04
     */
    public String deleteExpressDeliverySmallZone() {
	try {
	    objectVo.setReturnInt(expressDeliverySmallZoneService.deleteExpressDeliverySmallZoneByCode( objectVo.getCodeStr(), "1"));
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 导出接送货小区EXCEl
     * @return
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午3:13:20
     */
    public String exportExpressDeliverySmallZoneList() {
    	try {
    	
    	/*Long l=expressDeliverySmallZoneService.queryDataPermissions(FossUserContext.getCurrentInfo().getEmpCode(), objectVo.getExpressDeliverySmallZoneEntity().getManagement());
    	if(l<=0){
    		return returnError("你没有权限,请配置权限之后在查询！");
    	}*/
	    // 导出文件名称
	    downloadFileName = URLEncoder.encode(ColumnConstants.SMALL_ZONE_NAME, "UTF-8");
	    // 获取查询参数
	    ExpressDeliverySmallZoneEntity entity = objectVo.getExpressDeliverySmallZoneEntity();
	    if (null == entity) {
		    entity = new ExpressDeliverySmallZoneEntity();
	    }
	    entity.setEmpCode(FossUserContext.getCurrentInfo().getEmpCode());
	    // 获取导出数据对象
	    ExportResource exportResource = expressDeliverySmallZoneService.exportSmallZoneList(entity);

	    ExportSetting exportSetting = new ExportSetting();
	    // 设置名称
	    exportSetting.setSheetName(ColumnConstants.SMALL_ZONE_NAME);

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
     * 根据管理部门编码查询区域编码
     * @return
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午3:13:26
     */
    public String queryRegionCodeByManagement(){
    	
    	
    	ExpressDeliverySmallZoneEntity entity =objectVo.getExpressDeliverySmallZoneEntity();
    	String regionCode=expressDeliverySmallZoneService.queryRegionCodeByManagement(entity);
    	objectVo.setRegionCode(regionCode);
        return returnSuccess();
    }
    
    public String queryDataPermissions(){
    	String deptCode=objectVo.getExpressDeliverySmallZoneEntity().getManagement();
    	Long l=expressDeliverySmallZoneService.queryDataPermissions(FossUserContext.getCurrentInfo().getEmpCode(), deptCode);
    	if(l<=0){
    		return returnError("你没有权限,请配置权限之后在查询！");
    	}
    	return returnSuccess();
    }
    /**
     * 更具小区名称校验名称是否重复
     * @return
     */
    @JSON
    public String querySmallZoneByName(){
        try {
        	ExpressDeliverySmallZoneEntity entity =
        			expressDeliverySmallZoneService.querySmallZoneByName(objectVo.getExpressDeliverySmallZoneEntity().getRegionName());
        	if(null!=entity){
        		return returnError("该小区名称已存在！");
        	}
        	objectVo.setExpressDeliverySmallZoneEntity(entity);
        	return returnSuccess();
		} catch (BusinessException e) {
			 return returnError(e.getMessage()); 
		}
    }
    
    
    
    @JSON
   public String querySmallCourierByCode(){
    	ExpressDeliverySmallZoneEntity entity =
    			expressDeliverySmallZoneService.querySmallZoneByGisId(objectVo.getExpressDeliverySmallZoneEntity());
    	objectVo.setExpressDeliverySmallZoneEntity(entity);
	   return returnSuccess();
   }
    
    
}
