package com.deppon.foss.module.pickup.pricing.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOubrPlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.NumericConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.AgencyPricePlanVo;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.FlightPriceVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递代理运价方案Acion
 * @author 094463-foss-xieyantao
 * @date 2013-7-18 下午5:18:19
 * @since
 * @version
 */
public class AgencyPricePlanAction extends AbstractAction {


    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8818296417935216359L;

    /**
     * 日志处理.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AgencyPricePlanAction.class);

    /**
     * 航空运价 VO 交互对象.
     */
    private FlightPriceVo flightPriceVo = new FlightPriceVo();
    
    /**
     * 快递代理价格方案VO.
     */
    private AgencyPricePlanVo pricePlanVo = new AgencyPricePlanVo();
    /**
     * 最大读取10000列.
     */
    private static final int CELL_COUNT = 10000;
    
    /**
     * 快递代理公司运价方案Service接口.
     */
    private IPartbussPlanService partbussPlanService;
    
    /**
     * 快递代理网点运价方案Service接口.
     */
    private IOutbranchPlanService outbranchPlanService;
    
    /**
     * 快递代理网点运价方案明细Service接口.
     */
    private IOubrPlanDetailService oubrPlanDetailService;
    
    /**
     * 获取 快递代理价格方案VO.
     *
     * @return  the pricePlanVo
     */
    public AgencyPricePlanVo getPricePlanVo() {
        return pricePlanVo;
    }

    
    /**
     * 设置 快递代理价格方案VO.
     *
     * @param pricePlanVo the pricePlanVo to set
     */
    public void setPricePlanVo(AgencyPricePlanVo pricePlanVo) {
        this.pricePlanVo = pricePlanVo;
    }
    
    /**
     * 设置 快递代理网点运价方案Service接口.
     *
     * @param outbranchPlanService the outbranchPlanService to set
     */
    public void setOutbranchPlanService(IOutbranchPlanService outbranchPlanService) {
        this.outbranchPlanService = outbranchPlanService;
    }
    
    /**
     * 设置 快递代理网点运价方案明细Service接口.
     *
     * @param oubrPlanDetailService the oubrPlanDetailService to set
     */
    public void setOubrPlanDetailService(
    	IOubrPlanDetailService oubrPlanDetailService) {
        this.oubrPlanDetailService = oubrPlanDetailService;
    }


    /**
     * 替代空白字符串"".
     */
    private static final String BLANK = "";

    /**
     * <p>
     * 获得航空运价 VO 交互对象
     * </p>.
     *
     * @return the 航空运价 VO 交互对象
     * @author DP-Foss-YueHongJie
     * @date 2013-3-17 上午10:37:49
     * @see
     */
    public FlightPriceVo getFlightPriceVo() {
	return flightPriceVo;
    }

    /**
     * <p>
     * 设置航空运价 VO 交互对象
     * </p>.
     *
     * @param flightPriceVo the new 航空运价 VO 交互对象
     * @author DP-Foss-YueHongJie
     * @date 2013-3-17 上午10:38:07
     * @see
     */
    public void setFlightPriceVo(FlightPriceVo flightPriceVo) {
	this.flightPriceVo = flightPriceVo;
    }

    /**
     * 航空公司.
     */
    IAirlinesService airlinesService;

    /**
     * 设置 航空公司.
     * 
     * @param airlinesService
     *            the new 航空公司
     */
    public void setAirlinesService(IAirlinesService airlinesService) {
	this.airlinesService = airlinesService;
    }

    /**
     * 设置 快递代理公司运价方案Service接口.
     *
     * @param partbussPlanService the partbussPlanService to set
     */
    public void setPartbussPlanService(IPartbussPlanService partbussPlanService) {
        this.partbussPlanService = partbussPlanService;
    }

    /**
     * 导入文件.
     */
    private File uploadFile;

    /**
     * 设置 导入文件.
     *
     * @param uploadFile the new 导入文件
     */
    public void setUploadFile(File uploadFile) {
	this.uploadFile = uploadFile;
    }

    /**
     * 航班服务.
     */
    private IFlightService flightService;

    /**
     * <p>
     * 注入航班服务
     * </p>.
     *
     * @param flightService the new 航班服务
     * @author DP-Foss-YueHongJie
     * @date 2013-3-17 上午10:39:37
     * @see
     */
    public void setFlightService(IFlightService flightService) {
	this.flightService = flightService;
    }

    /**
     * 行政组织服务.
     */
    private IAdministrativeRegionsService administrativeRegionsService;

    /**
     * <p>
     * 注入行政组织服务
     * </p>.
     *
     * @param administrativeRegionsService the new 行政组织服务
     * @author DP-Foss-YueHongJie
     * @date 2013-3-17 上午10:39:51
     * @see
     */
    public void setAdministrativeRegionsService(
	    IAdministrativeRegionsService administrativeRegionsService) {
	this.administrativeRegionsService = administrativeRegionsService;
    }

    /**
     * <p>分页查询所有快递代理公司运价方案信息</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-26 下午3:06:46
     * @see
     */
    @JSON
    public String queryPartbussPlans() {
	try {
	    //根据传入对象查询符合条件所有快递代理公司运价方案信息
	    List<PartbussPlanEntity> entityList = partbussPlanService.queryInfos(pricePlanVo.getPartbussPlan(), limit, start);
	    pricePlanVo.setPartbussPlanList(entityList);
	    // 查询总记录数
	    Long totalCount = partbussPlanService.queryRecordCount(pricePlanVo.getPartbussPlan());
	    // 设置totalCount
	    this.setTotalCount(totalCount);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }

    /**
     * <p>新增快递代理公司运价方案</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-26 下午3:08:57
     * @see
     */
    @JSON
    public String addPartbussPlan() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    // 获取 快递代理公司运价方案实体类
	    PartbussPlanEntity entity = pricePlanVo.getPartbussPlan();
	    entity.setCreateUser(userCode);
	    entity.setModifyUser(userCode);
	    entity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
	    entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
	    entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	    
	    // 保存成功返回一个对象
	    entity = partbussPlanService.addInfo(entity);
	    pricePlanVo.setPartbussPlan(entity);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>修改快递代理运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午4:18:17
     * @return
     * @see
     */
    @JSON
    public String updatePartbussPlan() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    // 获取 快递代理公司运价方案实体类
	    PartbussPlanEntity entity = pricePlanVo.getPartbussPlan();
	    
	    entity.setModifyUser(userCode);
	    entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
	    
	    // 保存成功返回一个对象
	    entity = partbussPlanService.updateInfo(entity);
	    pricePlanVo.setPartbussPlan(entity);

	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }


    /**
     * <p>删除快递代理公司运价方案</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-26 下午3:26:24
     * @see
     */
    @JSON
    public String deleteFlightPrice() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode

	    partbussPlanService.deleteInfo(pricePlanVo.getIdList(), userCode);

	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }

    /**
     * <p>激活快递代理运价方案</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-26 下午3:59:22
     * @see
     */
    @JSON
    public String activePartbussPlan() {
	try {
	    //根据传入的方案ID 激活快递代理公司运价方案
	    partbussPlanService.activateList(pricePlanVo.getIdList());
	    return returnSuccess(MessageType.ACTIVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }

    /**
     * <p>根据ID查询快递代理公司运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午4:39:29
     * @return
     * @see
     */
    @JSON
    public String queryPartbussPlanById() {
	try {
	    //根据ID查询快递代理公司运价方案
	    PartbussPlanEntity entity = partbussPlanService.queryInfoById(pricePlanVo.getPartbussPlan().getId());
	   
	    pricePlanVo.setPartbussPlan(entity);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }

    /**
     * <p>根据快递代理公司运价方案ＩＤ分页查询所有的快递代理网点运价方案</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午3:48:35
     * @see
     */
    @JSON
    public String queryOutbranchPlans() {
	try {
	    //根据传入对象查询符合条件所有快递代理网点运价方案信息
	    List<OutbranchPlanEntity> outbranchPlanList = outbranchPlanService.queryInfos(pricePlanVo.getOutbranchPlan(), limit, start);
	    pricePlanVo.setOutbranchPlanList(outbranchPlanList);
	    // 查询总记录数
	    Long totalCount = outbranchPlanService.queryRecordCount(pricePlanVo.getOutbranchPlan());
	    // 设置totalCount
	    this.setTotalCount(totalCount);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>根据快递代理网点运价方案ＩＤ分页查询所有的快递代理网点运价方案明细信息</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午3:48:35
     * @see
     */
    @JSON
    public String queryOubrPlanDetails() {
	try {
	    //根据传入对象查询符合条件所有快递代理网点运价方案信息
	    List<OubrPlanDetailEntity> oubrPlanDetailList = oubrPlanDetailService.queryInfos(pricePlanVo.getOubrPlanDetail(), limit, start);
	    pricePlanVo.setOubrPlanDetailList(oubrPlanDetailList);
	    // 查询总记录数
	    Long totalCount = oubrPlanDetailService.queryRecordCount(pricePlanVo.getOubrPlanDetail());
	    // 设置totalCount
	    this.setTotalCount(totalCount);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>根据ID查询快递代理网点运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午4:39:29
     * @return
     * @see
     */
    @JSON
    public String queryOutbranchPlanById() {
	try {
	    //根据ID查询快递代理网点运价方案
	    OutbranchPlanEntity entity = outbranchPlanService.queryInfoById(pricePlanVo.getOutbranchPlan().getId());
	   
	    pricePlanVo.setOutbranchPlan(entity);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>根据ID查询快递代理网点运价方案明细</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午4:39:29
     * @return
     * @see
     */
    @JSON
    public String queryOubrPlanDetailById() {
	try {
	    //根据ID查询快递代理网点运价方案
	    OubrPlanDetailEntity entity = oubrPlanDetailService.queryinfoById(pricePlanVo.getOubrPlanDetail().getId());
	    pricePlanVo.setOubrPlanDetail(entity);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>新增快递代理网点运价方案</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-26 下午3:08:57
     * @see
     */
    @JSON
    public String addOutbranchPlan() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    // 获取 快递代理网点运价方案实体类
	    OutbranchPlanEntity entity = pricePlanVo.getOutbranchPlan();
	    entity.setCreateUser(userCode);
	    entity.setModifyUser(userCode);
	    entity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
	    entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
	    entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	    
	    // 保存成功返回一个对象
	    entity = outbranchPlanService.addInfo(entity);
	    pricePlanVo.setOutbranchPlan(entity);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>修改快递代理网点运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-27 下午4:18:17
     * @return
     * @see
     */
    @JSON
    public String updateOutbranchPlan() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    // 获取 快递代理公司运价方案实体类
	    OutbranchPlanEntity entity = pricePlanVo.getOutbranchPlan();
	    entity.setModifyUser(userCode);
	    entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
	    
	    // 保存成功返回一个对象
	    entity = outbranchPlanService.updateInfo(entity);
	    pricePlanVo.setOutbranchPlan(entity);

	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }


    /**
     * <p>删除快递代理网点运价方案</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-26 下午3:26:24
     * @see
     */
    @JSON
    public String deleteoutbranchPlan() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode

	    outbranchPlanService.deleteInfo(pricePlanVo.getIdList(), userCode);

	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>新增快递代理网点运价方案明细</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-29 上午9:03:40
     * @return
     * @see
     */
    @JSON
    public String addOubrPlanDetail() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    // 获取 快递代理网点运价方案明细实体类.
	    OubrPlanDetailEntity entity = pricePlanVo.getOubrPlanDetail();
	    entity.setCreateUser(userCode);
	    entity.setModifyUser(userCode);
	    entity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
	    entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
	    entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	    
	    // 新增快递代理网点运价方案明细明细
	    oubrPlanDetailService.addInfo(entity);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>修改快递代理网点运价方案明细</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-29 上午9:06:43
     * @return
     * @see
     */
    @JSON
    public String updateOubrPlanDetail() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    // 获取 快递代理网点运价方案明细实体类
	    OubrPlanDetailEntity entity = pricePlanVo.getOubrPlanDetail();
	    entity.setModifyUser(userCode);
	    entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
	    
	    //修改快递代理网点运价方案明细
	    oubrPlanDetailService.updateInfo(entity);

	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }


    /**
     * <p>删除快递代理网点运价方案明细</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-26 下午3:26:24
     * @see
     */
    @JSON
    public String deleteOubrPlanDetail() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode

	    oubrPlanDetailService.deleteInfo(pricePlanVo.getIdList(), userCode);

	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }


    /**
     * <p>升级版本</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-29 下午1:42:10
     * @return
     * @see
     */
    @JSON
    public String copyPartbussPlan() {
	try {
	    //根据ID复制快递代理运价方案
	    PartbussPlanEntity entity = partbussPlanService.copyInfoById(pricePlanVo.getPartbussPlan().getId());
	    pricePlanVo.setPartbussPlan(entity);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 查询航班信息<br/>
     * 
     * 方法名：searchFlightInfo
     * 
     * </p>
     *
     * @return 
     * @author 张斌
     * @时间 2013-1-11
     * @since JDK1.6
     */
    @JSON
    public String searchFlightInfo() {
    	try {
    		// 始发城市
    		String orgCoe = flightPriceVo.getFlightEntity().getOrigCode();
    		AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(orgCoe);
    		flightPriceVo.getFlightEntity().setOrigCode(null);
    		List<FlightEntity> flightEntityList = flightService.queryFlightListBySelective(flightPriceVo.getFlightEntity());
    		List<FlightDto> flightDtoList = new ArrayList<FlightDto>();
    		if (CollectionUtils.isNotEmpty(flightEntityList)) {
    			for (FlightEntity flightEntity : flightEntityList) {
    				FlightDto flightDto = new FlightDto();
    				if (administrativeRegionsEntity != null) {
    					flightDto.setDepartureAirportName(administrativeRegionsEntity.getName());
    				}
    				flightEntity.getOrigCode();
    				flightDto.setFlightNo(flightEntity.getFlightNo());
    				flightDto.setDepartureAirport(flightEntity.getDepartureAirport());
    				flightDto.setDestinationAirport(flightEntity.getDestinationAirport());
    				flightDto.setPlanArriveTime(flightEntity.getPlanArriveTime());
    				flightDto.setPlanLeaveTime(flightEntity.getPlanLeaveTime());
    				flightDtoList.add(flightDto);
    			}
    		}
    		flightPriceVo.setFlightDtoList(flightDtoList);
    		return returnSuccess();
    	} catch (BusinessException e) {
    		LOGGER.error("查询航班信息: " + e.getMessage());
    		return returnError(e);
    	}
    }

    /**
     * <p>立即终止快递代理运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-29 上午10:30:54
     * @return
     * @see
     */
    @JSON
    public String immediatelyStop() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    // 获取 快递代理公司运价方案实体类.
	    PartbussPlanEntity entity = pricePlanVo.getPartbussPlan();
	    entity.setModifyUser(userCode);
	    entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
	    partbussPlanService.immediatelyStop(entity);
	    return returnSuccess(MessageType.STOP_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }

    /**
     * <p>立即激活所有的快递代理运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-29 上午10:32:17
     * @return
     * @see
     */
    @JSON
    public String immediatelyActivate() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    // 获取 快递代理公司运价方案实体类.
	    PartbussPlanEntity entity = pricePlanVo.getPartbussPlan();
	    entity.setModifyUser(userCode);
	    entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
	    partbussPlanService.immediatelyActivate(entity);
	    return returnSuccess(MessageType.ACTIVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error("立即激活航班信息: " + e.getMessage());
	    return returnError(e);
	}
    }

    /**
     * <p>导入快递代理运价信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-9-11 下午2:00:57
     * @return
     * @see
     */
    public String importFlightPrice() {
	// 初始化
	Workbook book = null;
	FileInputStream inputStream = null;
	try {
	    if (uploadFile != null) {
		inputStream = new FileInputStream(uploadFile);
		try {
		    // 如果是2003版本
		    book = new XSSFWorkbook(inputStream);
		} catch (Exception ex) {
		    // 如果是2007版本
		    book = new HSSFWorkbook(inputStream);
		}
	    } else {
		throw new FileException("请选择导入文件", "请选择导入文件");
	    }
	    if (book != null) {
		// 默认获取获取工作表1
		Sheet sheet = book.getSheetAt(0);// 默认第一个

		// 收集excel文件中的将Excel表格每行数据读入列表，开始收集信息，并作必要的检查
		List<PartbussPlanEntity> list = analyticExcells(sheet);
		if(CollectionUtils.isEmpty(list)){
		    throw new FileException("导入数据为空,请检查", "导入数据为空,请检查");
		}
		try {
		    // 批量保存快递代理运价数据
		    partbussPlanService.batchSave(list);
		} catch (BusinessException e) {
		    LOGGER.error(e.getMessage());
		    return returnError(e);
		}
	    }
	    return super.returnSuccess();
	} catch (FileException e) {
	    return super.returnError(e);
	} catch (IOException e) {
	    LOGGER.error(e.getMessage(), e);
	    return returnError("数据文件被破坏，请重新制作导入文件");
	} finally {
	    if (book != null) {
		book = null;
	    }
	    if (inputStream != null) {
		try {
		    inputStream.close();
		} catch (IOException e) {
		    LOGGER.error(e.getMessage(), e);
		    return returnError("文件关闭失败");
		}
	    }
	}
    }

    /**
     * <p>将Excel表格每行数据读入列表</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-9-11 下午2:18:33
     * @param detailMap
     * @param regionMap
     * @param productMap
     * @param sheet
     * @see
     */
    private List<PartbussPlanEntity> analyticExcells(Sheet sheet) {
	StringBuilder builder = new StringBuilder();
	if (sheet != null) {
	    // 获取物理行数
	    int rowCount = sheet.getPhysicalNumberOfRows();
	    // 根据行数循环
	    Row row = null;
	    // EXCEL行记录
	    if (rowCount < NumericConstants.NUM_3) {
		return null;// no data
	    }
	    // 取得第一行数据
	    row = sheet.getRow(0);
	    //获取第一个单元格数据
	    Cell cell1 = row.getCell(0); 
	    // 取单元格值(方案类型名称)
	    String typeName = ExcelHandleUtil.obtainStringVal(cell1);
	    // 取得第二行数据
	    row = sheet.getRow(1);
	    // 看第一行到底有多少列数据，从第5列开始，是目的城市数据
	    Map<String, String> destRegionNameMap = new HashMap<String, String>();
	    
	    if(StringUtils.equals("固定价格", typeName)){
		for (int colNum = 1; colNum < CELL_COUNT; colNum++) {
		    // 方案1从第二行起3列值作为1个对象值
		    Cell cell = row.getCell(colNum * NumericConstants.NUM_3 - 1);
		    if (cell != null) {
			// 取单元格值
			String cellVal = ExcelHandleUtil.obtainStringVal(cell);
			if (StringUtil.isNotBlank(cellVal)) {
			    destRegionNameMap.put(colNum * NumericConstants.NUM_3-1 + BLANK,
				    cellVal);
			} else {
			    break;// 结束，没有数据了
			}
		    }
		}
	    }else {
		for (int colNum = 1; colNum < CELL_COUNT; colNum++) {
		    // 方案2从第二行起4列值作为1个对象值
		    Cell cell = row.getCell(colNum * NumericConstants.NUM_4 - NumericConstants.NUM_2);
		    if (cell != null) {
			// 取单元格值
			String cellVal = ExcelHandleUtil.obtainStringVal(cell);
			if (StringUtil.isNotBlank(cellVal)) {
			    destRegionNameMap.put(colNum * NumericConstants.NUM_4 - NumericConstants.NUM_2 + BLANK,
				    cellVal);
			} else {
			    break;// 结束，没有数据了
			}
		    }
		}
	    }
	    
	    int totalColume = destRegionNameMap.size();
	    //获取当前时间
	    Date date = new Date();
	    List<PartbussPlanEntity> list = new ArrayList<PartbussPlanEntity>();
	    // 根据行数循环(从第4行开始读取数据)
	    for (int rowNum = NumericConstants.NUM_3; rowNum < rowCount; rowNum++) {
		//快递代理公司运价方案实体类 
		PartbussPlanEntity planEntity = new PartbussPlanEntity();
		//设置 生效日期.
		planEntity.setBeginTime(date);
		//设置 结束日期.
		planEntity.setEndTime(new Date(NumberConstants.ENDTIME));
		// 获取每行数据
		// 取得一行的数据
		Row row1 = sheet.getRow(rowNum);
		// 获取快递代理公司名称
		Cell cell = row1.getCell(0);
		// 获取快递代理公司名称
		String expressPartbussName = null;
		if (cell != null) {
		    // 取单元格值
		    expressPartbussName = ExcelHandleUtil.obtainStringVal(cell);
		    if (StringUtil.isBlank(expressPartbussName)) {
			// 结束，没有数据了
			break;
		    }
		} else {
		    // 结束，没有数据了
		    break;
		}
		//设置 快递代理公司名称.
		planEntity.setExpressPartbussName(expressPartbussName);
		
		List<OutbranchPlanEntity> outbrList = new ArrayList<OutbranchPlanEntity>();
		//快递代理网点运价方案实体类 
		OutbranchPlanEntity outbrEntity = new OutbranchPlanEntity();
		// 获取快递代理网点名称
		String outerBranchName = null;
		cell = row1.getCell(1);
		if (cell != null) {
		    // 取单元格值
		    outerBranchName = ExcelHandleUtil.obtainStringVal(cell);
		    if (StringUtil.isBlank(outerBranchName)) {
			builder.append("第" + (rowNum) + "行第2列数据不正确，请检查");			 
		    }
		    //设置 快递代理公司网点名称（扩展）.
		    outbrEntity.setOuterBranchName(outerBranchName);
		} else {
		    builder.append("第" + (rowNum) + "行第2列数据不正确，请检查");
		}
		if(StringUtils.equals("固定价格", typeName)){
		    //设置方案类型
		    outbrEntity.setBillType("FX");
		}else {
		    outbrEntity.setBillType("AW");
		}
		
		List<OubrPlanDetailEntity> detailList = new ArrayList<OubrPlanDetailEntity>();
		//获取快递代理网点明细
		for (int colNum = NumericConstants.NUM_2; colNum < totalColume + NumericConstants.NUM_2; colNum++) {
		    //快递代理网点运价方案明细实体类 
		    OubrPlanDetailEntity entity = new OubrPlanDetailEntity();
		    // 下限
		    Cell cell11 = null;
		    // 上限
		    Cell cell12 = null;
		    // 价格
		    Cell cell13 = null;
		    if(StringUtils.equals("固定价格", typeName)){
			// 下限
			cell11 = row1.getCell((colNum - 1) * NumericConstants.NUM_3 - 1);
			// 上限
			cell12 = row1.getCell((colNum - 1) * NumericConstants.NUM_3);
			// 价格
			cell13 = row1.getCell((colNum - 1) * NumericConstants.NUM_3 + 1);
			// 判断数据格式正确否 首重
			if (cell11 != null
				&& cell11.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			    builder.append("第" + (rowNum) + "行第"
				    + ((colNum - 1) * NumericConstants.NUM_3 - 1)
				    + "列数据不正确，请检查");
			}
			if (cell12 != null
				&& cell11 != null && cell11.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			    builder.append("第" + (rowNum) + "行第"
				    + ((colNum - 1) * NumericConstants.NUM_3) + "列数据不正确，请检查");
			}
			if (cell13 != null
				&& cell11 != null && cell11.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			    builder.append("第" + (rowNum) + "行第"
				    + ((colNum - 1) * NumericConstants.NUM_3 + 1)
				    + "列数据不正确，请检查");
			}
			// 获取重量下限、上限以及价格
			double weightDownline = 0;
			if(cell11 != null){
				weightDownline = cell11.getNumericCellValue();
			}
			double weightOnline = 0;
			if(cell12 != null){
				weightOnline = cell12.getNumericCellValue();
			}
			double firstFee = 0;
			if(cell13 != null){
				firstFee = cell13.getNumericCellValue();
			}
			// 设置重量下限
			entity.setLeftrange(new BigDecimal(weightDownline));
			// 设置重量上限
			entity.setRightrange(new BigDecimal(weightOnline));
			// 设置价格/
			entity.setFee(new BigDecimal(firstFee));
		    }else {
			// 下限
			cell11 = row1.getCell((colNum - 1) * NumericConstants.NUM_4 - NumericConstants.NUM_2);
			// 上限
			cell12 = row1.getCell((colNum - 1) * NumericConstants.NUM_4 -1);
			// 量纲
			cell13 = row1.getCell((colNum - 1) * NumericConstants.NUM_4 );
			// 价格
			Cell cell14 = row1.getCell((colNum - 1) * NumericConstants.NUM_4 +1);
			// 判断数据格式正确否 首重
			if (cell11 != null
				&& cell11.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			    builder.append("第" + (rowNum) + "行第"
				    + ((colNum - 1) * NumericConstants.NUM_4 - NumericConstants.NUM_2)
				    + "列数据不正确，请检查");
			}
			if (cell12 != null
				&& cell11 != null && cell11.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			    builder.append("第" + (rowNum) + "行第"
				    + ((colNum - 1) * NumericConstants.NUM_4 -1) + "列数据不正确，请检查");

			}
			if (cell13 != null
				&& cell11 != null && cell11.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			    builder.append("第" + (rowNum) + "行第"
				    + ((colNum - 1) * NumericConstants.NUM_4)
				    + "列数据不正确，请检查");
			}
			if (cell14 != null
				&& cell11 != null && cell11.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
			    builder.append("第" + (rowNum) + "行第"
				    + ((colNum - 1) * NumericConstants.NUM_4 +1)
				    + "列数据不正确，请检查");
			}
			// 获取重量下限、上限以及价格
			double weightDownline = 0;
			if(cell11 != null){
				weightDownline = cell11.getNumericCellValue();
			}
			double weightOnline = 0;
			if(cell12 != null){
				weightOnline = cell12.getNumericCellValue();
			}
			double dimension = 0;
			if(cell13 != null){
				dimension = cell13.getNumericCellValue();
			}
			double firstFee = 0;
			if(cell14 != null){
				firstFee = cell14.getNumericCellValue();
			}
			// 设置重量下限
			entity.setLeftrange(new BigDecimal(weightDownline));
			// 设置重量上限
			entity.setRightrange(new BigDecimal(weightOnline));
			//设置量纲
			entity.setDimension(new BigDecimal(dimension));
			// 设置价格/
			entity.setFee(new BigDecimal(firstFee));

		    }
		    if (builder.length() > 0) {
			throw new FileException("快递代理运价方案导入时发生错误： ",
				builder.toString());
		    }
		    detailList.add(entity);
		}
		//设置 价格明细.
		outbrEntity.setPriceDetail(detailList);
		outbrList.add(outbrEntity);
		if(CollectionUtils.isNotEmpty(outbrList)){
		    planEntity.setEntityList(outbrList);
		}
		list.add(planEntity);
	    }
	    return list;
	}
	return null;
    }

}