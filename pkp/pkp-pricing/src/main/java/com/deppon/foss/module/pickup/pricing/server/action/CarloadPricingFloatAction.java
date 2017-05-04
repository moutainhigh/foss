package com.deppon.foss.module.pickup.pricing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICarloadPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.CarloadPriceManageMeantVo;
import com.deppon.foss.util.DateUtils;

public class CarloadPricingFloatAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 
     * 日志处理
     * 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PricePlanAction.class);
    
    /**
     * 
     * 导出文件名称
     * 
     */
    private String downloadFileName;
    
    /**
     * 
     * 
     * 导出Excel 文件流
     * 
     */
    private InputStream inputStream;
	
	/**
    *
    * 整车价格方案服务
    *  
    */
   private ICarloadPriceService carloadPriceService;
	
   private CarloadPriceManageMeantVo carloadManageMeantVo=new CarloadPriceManageMeantVo();
	
	public void setCarloadPriceService(ICarloadPriceService carloadPriceService) {
		this.carloadPriceService = carloadPriceService;
	}
	public CarloadPriceManageMeantVo getCarloadManageMeantVo() {
		return carloadManageMeantVo;
	}
	public void setCarloadManageMeantVo(
			CarloadPriceManageMeantVo carloadManageMeantVo) {
		this.carloadManageMeantVo = carloadManageMeantVo;
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

	/**
     * 
     * <p>(查价整车价格参数波动方案信息)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String queryCarloadPricePlanBatchInfo(){
		try {
			String orgcode=	carloadManageMeantVo.getCarloadPricePlanDto().getOrganizationCode();
			carloadManageMeantVo.setCarloadPricePlanDtos(carloadPriceService.queryCarloadPricePlanBatchInfo(carloadManageMeantVo.getCarloadPricePlanDto(),getStart(),getLimit()));
			if(StringUtil.isEmpty(orgcode)){
				this.setTotalCount(carloadPriceService.queryCarloadPricePlanCount(carloadManageMeantVo.getCarloadPricePlanDto()));
			}else{	
			    this.setTotalCount(carloadPriceService.queryCarloadPricePlanBatchInfoCount(carloadManageMeantVo.getCarloadPricePlanDto()));
			}
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询整车价格方案信息出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
	
	/**
     * 
     * <p>(新增整车价格参数方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-9 上午10:04:06
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String addCarloadPricePlan(){
		try {
			CarloadPriceEntity carloadPriceEntity = carloadPriceService.addCarloadPricePlan(carloadManageMeantVo.getCarloadPriceEntity(),carloadManageMeantVo.getCarloadPriceOrgEntityList());
		    carloadManageMeantVo.setCarloadPriceEntity(carloadPriceEntity);
		    return returnSuccess(MessageType.SAVE_PRICE_PLAN_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("新增价格方案信息出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
    
    /**
     * 
     * <p>(新增整车价格参数方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-9 上午10:04:06
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String addCarloadPriceDetail(){
		try {
			List<CarloadPriceDetailEntity> carloadPriceDetailEntitys = carloadPriceService.addCarloadPriceDetail(carloadManageMeantVo.getCarloadPriceDetailEntity());
		    carloadManageMeantVo.setCarloadPriceDetailEntityList(carloadPriceDetailEntitys);
		    return returnSuccess(MessageType.SAVE_PRICE_PLAN_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("新增整车价格方案明细出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
    
    /**
     * <p>(修改整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String updateCarloadPriceDetailPlan(){
		try {
			carloadManageMeantVo.setCarloadPriceDetailEntityList(carloadPriceService.updateCarloadPriceDetailPlan(carloadManageMeantVo.getCarloadPriceDetailEntity()));
		    return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("修改整车价格方案明细信息出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
	
	/**
     * <p>(删除所选择整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String deleteCarloadPricePlanDetail(){
		try {
			carloadManageMeantVo.setCarloadPriceDetailEntityList(carloadPriceService.deleteCarloadPricePlanDetail(carloadManageMeantVo.getCarPlanDetailIds()));
		    return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("删除整车价格方案明细出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
	
	/**
     * <p>(激活整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String activeCarloadPricePlan(){
		try {
			carloadPriceService.activeCarloadPricePlan(carloadManageMeantVo.getCarPlanIds());
		    return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("激活整车价格方案出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
	

	/**
     * <p>(删除整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String deleteCarloadPricePlan(){
		try {
			carloadPriceService.deleteCarloadPricePlan(carloadManageMeantVo.getCarPlanIds());
		    return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("删除整车价格方案出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
	
	/**
     * <p>(立即激活整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String immediatelyActiveCarloadPricePlan(){
		try {
			carloadPriceService.immediatelyActiveCarloadPricePlan(carloadManageMeantVo.getCarloadPriceEntity());
		    return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("立即激活整车价格方案出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
	
	
	/**
     * <p>(立即中止整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String immediatelystopCarloadPricePlan(){
		try {
			carloadPriceService.immediatelystopCarloadPricePlan(carloadManageMeantVo.getCarloadPriceEntity());
		    return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("立即中止整车价格方案出现异常: "+e.getMessage());
		    return returnError(e);
		}
    }
	
	
	
	/**
     * <p>(导出整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
    public String exportCarloadPricePlan(){
    	try {
			String fileName = PricingColumnConstants.EXPORT_CARLOAD_PRICE_PLAN;
			downloadFileName = encodeFileName(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT) + fileName);
			if (null == carloadManageMeantVo.getCarloadPricePlanDto()) {
				carloadManageMeantVo.setCarloadPricePlanDto(new CarloadPricePlanDto());
			}
			// 导出设置
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName(PricingColumnConstants.EXPORT_CARLOAD_PRICE_PLAN);
			exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
			// 存储导出数
			ExportResource exportResource = carloadPriceService.exportCarloadPricePlan(carloadManageMeantVo.getCarloadPricePlanDto());
			// 根据存储的数据调用导出类
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
			return returnSuccess();
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("整车价格参数方案导出，出现异常: " + e.getMessage());
			return returnError("整车价格参数方案导出，出现异常", e.getMessage());
		}
    }
    
    /**
	 * 
	 * 转换导出文件的文件名
	 * @author 076234-foss-panguoyang
	 * @date 2014-5-2 上午9:52:18
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
     * <p>(查询整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String queryCarloadPricePlanDetailInfo(){
		try {
			List<CarloadPriceDetailEntity> carloadPriceDetailEntitys = 
			carloadPriceService.queryCarloadPricePlanDetailInfo(carloadManageMeantVo.getCarloadPricePlanDto(),carloadManageMeantVo.getCarloadPriceDetailEntity());
		    carloadManageMeantVo.setCarloadPriceDetailEntityList(carloadPriceDetailEntitys);
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询整车价格参数波动方案明细: "+e.getMessage());
		    return returnError(e);
		}
    }
	
	 /**
     * <p>(查询整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String queryCarloadPricePlanAndOrgInfo(){
		try {
			carloadManageMeantVo.setCarloadPriceEntity(carloadPriceService.queryCarloadPricePlanAndOrgInfo(carloadManageMeantVo.getCarloadPricePlanDto()));
		    carloadManageMeantVo.setCarloadPricePlanDtos(carloadPriceService.queryCarloadPricePlanDtos(carloadManageMeantVo.getCarloadPricePlanDto()));
		    carloadManageMeantVo.setCarloadPriceDetailEntityList(carloadPriceService.carloadPriceDetailList(carloadManageMeantVo.getCarloadPricePlanDto()));
		    return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("查询整车价格参数波动方案: "+e.getMessage());
		    return returnError(e);
		}
    }
	/**
     * <p>(修改整车价格参数波动方案))</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String updateCarloadPricePlan(){
		try {
			carloadPriceService.updateCarloadPricePlan(carloadManageMeantVo.getCarloadPriceEntity(),carloadManageMeantVo.getCarloadPriceOrgEntityList());

			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("修改整车价格参数波动方案明细: "+e.getMessage());
		    return returnError(e);
		}
	}
	
	/**
     * <p>(复制整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String copyCarloadPricePlan(){
		try {
			String id =carloadPriceService.copyCarloadPricePlan(carloadManageMeantVo.getCarloadPricePlanDto());
			CarloadPricePlanDto dto =carloadManageMeantVo.getCarloadPricePlanDto();
			dto.setId(id);
			carloadManageMeantVo.setCarloadPriceEntity(carloadPriceService.queryCarloadPricePlanAndOrgInfo(dto));
		    carloadManageMeantVo.setCarloadPricePlanDtos(carloadPriceService.queryCarloadPricePlanDtos(dto));
		    carloadManageMeantVo.setCarloadPriceDetailEntityList(carloadPriceService.carloadPriceDetailList(dto));
		    return returnSuccess(MessageType.COPY_PRICE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("复制整车价格参数波动方案: "+e.getMessage());
		    return returnError(e);
		}
    }
	
	
	/**
     * <p>(中止整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@JSON
    public String stopCarloadPricePlan(){
		try {
			carloadPriceService.stopCarloadPricePlan(carloadManageMeantVo.getCarloadPriceEntity());
		    return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("中止整车价格参数波动方案: "+e.getMessage());
		    return returnError(e);
		}
    }
	
	/**
     * <p>(导出整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
    public String exportCarloadPricePlanDetail(){
    	try {
			String fileName = PricingColumnConstants.EXPORT_CARLOAD_PRICE_PLAN_DETAIL;
			downloadFileName = encodeFileName(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT) + fileName);
			if (null == carloadManageMeantVo.getCarloadPricePlanDto()) {
				carloadManageMeantVo.setCarloadPricePlanDto(new CarloadPricePlanDto());
			}
			// 导出设置
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName(PricingColumnConstants.EXPORT_CARLOAD_PRICE_PLAN_DETAIL);
			exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
			// 存储导出数
			ExportResource exportResource = carloadPriceService.exportCarloadPricePlanDetail(carloadManageMeantVo.getCarloadPricePlanDto());
			// 根据存储的数据调用导出类
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			inputStream = objExporterExecutor.exportSync(exportResource, exportSetting);
			return returnSuccess();
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("整车价格参数方案明细导出，出现异常: " + e.getMessage());
			return returnError("整车价格参数方案明细导出，出现异常", e.getMessage());
		}
    }
	
}
