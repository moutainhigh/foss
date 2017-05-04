package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IOuterPriceDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterPriceService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPriceCondtionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.OuterPriceVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

public class OuterPriceService implements IOuterPriceService {
	
	private static final int THREE = 3;
	private static final int FOUR =4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;
	private static final int NINE = 9;
	private static final int TEN =10;
	
	/** 日志 */
	private static final Logger logger = Logger.getLogger(OuterPriceService.class);
    @Inject
	private IOuterPriceDao outerPriceDao;
	private IAreaAddressService areaAddressService;
	private IOutfieldService outfieldService;
	private IVehicleAgencyDeptService vehicleAgencyDeptService;
	
	public void setOuterPriceDao(IOuterPriceDao outerPriceDao) {
		this.outerPriceDao = outerPriceDao;
	}
	public IAreaAddressService getAreaAddressService() {
		return areaAddressService;
	}
	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}
	
	public IOutfieldService getOutfieldService() {
		return outfieldService;
	}
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	
	public IVehicleAgencyDeptService getVehicleAgencyDeptService() {
		return vehicleAgencyDeptService;
	}
	public void setVehicleAgencyDeptService(IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}
	
	
	@Override
	public Long queryOuterPriceVoBatchInfoCount(
			OuterPriceCondtionDto outerPriceCondtionDto) {
		
		return outerPriceDao.queryOuterPriceVoBatchInfoCount(outerPriceCondtionDto);
	}

	@Override
	public List<OuterPricePlanDto> queryOuterPriceVoBatchInfo(
			OuterPriceCondtionDto outerPriceCondtionDto, int start, int limit) {
		//return outerPriceDao.queryOuterPriceVoBatchInfo(outerPriceCondtionDto);
		List<OuterPricePlanDto> oppDtos = outerPriceDao.queryOuterPriceVoBatchInfo(outerPriceCondtionDto, start, limit);
		List<OuterPricePlanDto> lists = new ArrayList<OuterPricePlanDto>();
		for (OuterPricePlanDto dto : oppDtos) {
	    	String temp = areaAddressService.queryRegionByCode(dto.getProvCode()).getName();
	    	dto.setProvName(temp);
	    	temp = areaAddressService.queryRegionByCode(dto.getCityCode()).getName();
	    	dto.setCityName(temp);
	    	temp = areaAddressService.queryRegionByCode(dto.getCountyCode()).getName();
	    	dto.setCountyName(temp);
	    	Date nowDate = new Date();
	    	Date beginTime = dto.getBeginTime();
	    	Date endTime = dto.getEndTime();
	    	if (FossConstants.ACTIVE.equals(dto.getActive()) 
	    			&& nowDate.after(beginTime) && nowDate.before(endTime)) {
	    		dto.setVersion("是");
			} else {
				dto.setVersion("否");
			}
			lists.add(dto);
		}
		return lists;
	}
	
	/**
	 * @Description: 获得当前部门值
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午2:50:57
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private String getCurrentOrgCode() {
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		return currentDept.getCode();
	}
	
	/**
	 * 
	 * @Description: 获得当前人
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午2:50:57
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	private String getCurrentUserCode() {
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		return currentUser.getEmployee().getEmpCode();
	}
	
	/**
	 * @功能 中止/激活偏线价格方案
	 * @author 105888-foss-zhangxingwang
	 */
	@Transactional
	@Override
	public void updateActiveToN(String outerPriceId) {
		if(StringUtils.isEmpty(outerPriceId)){
			throw new BusinessException("传入参数有误，请重试");
		}		
		outerPriceDao.updateActiveToYesOrNo(outerPriceId,FossConstants.NO);
	}
	
	/**
	 * <p>
	 * Description:根据主键Id批量激活偏线价格方案<br />
	 * </p>
	 * @author Rosanu
	 * @version 0.1 2012-10-18
	 * @param id
	 * @return
	 * 
	 */
	@Transactional
	public void updateOuterPriceActiveById(List<OuterPricePlanDto> opps, String yesOrNo) {
		//待激活方案数据
		List<String> ids = new ArrayList<String>();
		String temp = null;
		OuterPriceEntity record = new OuterPriceEntity();
		for (OuterPricePlanDto dto : opps) {
			if (dto == null) {
				continue;
			}
			temp = dto.getOuterPriceId();
			ids.add(temp);
			record.setOuterPriceId(temp);
			//设置已存在相同激活方案的截止时间为新激活方案的生效时间的前一秒
			record.setEndTime(DateUtils.getDateAdd(dto.getBeginTime(), -NumberConstants.NUMBER_1000));
			record.setPartialLineCode(dto.getPartialLineCode());
			record.setOutFieldCode(dto.getOutFieldCode());
			record.setProductCode(dto.getProductCode());
			record.setActive(FossConstants.ACTIVE);
			
			if (outerPriceDao.updateOuterPriceEndTime(record) < 0) {
				throw new BusinessException("修改已激活方案（上个版本）的数据失败，请联系技术支持人员！");
			}
			
			/*OuterPriceEntity ope = outerPriceDao.searchOuterPriceByArgument(
					dto.getPartialLineCode(), dto.getOutFieldCode(), dto.getProductCode(), nowDate);
			if (ope != null) {
				record.setOuterPriceId(ope.getOuterPriceId());
				//设置已存在相同激活方案的截止时间为新激活方案的生效时间的前一秒
				record.setEndTime(DateUtils.getDateAdd(dto.getBeginTime(), -1000));
				
				if (outerPriceDao.updateByPrimaryKeySelective(record) <= 0) {
					throw new BusinessException("修改已激活方案（上个版本）的数据失败，请联系技术支持人员！");
				}
				
			}*/
		}
		
		if (ids != null && ids.size() >= 0) {
			if (ids.size() <= NumberConstants.NUMBER_1000) {
				if (outerPriceDao.updateOuterPriceActiveById(ids, yesOrNo) <= 0) {
					throw new BusinessException("操作失败，请联系技术支持人员！");
				}
			} else {
				List<List<String>> idsLists = CollectionUtils.splitListBySize(ids, FossConstants.ORACLE_MAX_IN_SIZE);
				for (List<String> lists : idsLists) {
					if (outerPriceDao.updateOuterPriceActiveById(lists, yesOrNo) <= 0) {
						throw new BusinessException("操作失败，请联系技术支持人员！");
					}
				}
			}
		} else {
			throw new BusinessException("传入参数有误，请重试");
		}
	}
	
	/**
	 * @功能 中止/激活偏线价格方案
	 * @author 105888-foss-zhangxingwang
	 */
	@Transactional
	@Override
	public void immediatelyActiveOuterPrice(String outerPriceId, String yesOrNo, Date effectiveTime) {
		if(StringUtils.isEmpty(outerPriceId)){
			throw new BusinessException("传入参数有误，ID不能为空！");
		}
		OuterPriceEntity opt = new OuterPriceEntity();
		opt.setActive(yesOrNo);
		if (FossConstants.ACTIVE.equals(yesOrNo)) {
			//激活方案
			OuterPriceEntity ope = outerPriceDao.selectByPrimaryKey(outerPriceId);
			if (ope == null) {
				throw new BusinessException("传入参数有误，ID在系统中不存在！" + outerPriceId);
			}
			ope.setOuterPriceId(outerPriceId);
			//设置已存在相同激活方案的截止时间为新激活方案的生效时间的前一秒
			ope.setEndTime(DateUtils.getDateAdd(effectiveTime, -NumberConstants.NUMBER_1000));
			ope.setActive(FossConstants.ACTIVE);
			
			if (outerPriceDao.updateOuterPriceEndTime(ope) < 0) {
				throw new BusinessException("修改已激活方案（上个版本）的数据失败，请联系技术支持人员！");
			}
			/*
			ope = outerPriceDao.searchOuterPriceByArgument(ope.getPartialLineCode(), 
					ope.getOutFieldCode(), ope.getProductCode(), effectiveTime);

			OuterPriceEntity record = new OuterPriceEntity();
			record.setOuterPriceId(ope.getOuterPriceId());
			//设置已存在相同激活方案的截止时间为新激活方案的生效时间的前一秒
			record.setEndTime(DateUtils.getDateAdd(effectiveTime, -1000));
			
			if (outerPriceDao.updateByPrimaryKeySelective(record) <= 0) {
				throw new BusinessException("修改已激活方案（上个版本）的数据失败，请联系技术支持人员！");
			}
			*/
			opt.setBeginTime(effectiveTime);
		} else {
			//中止方案
			opt.setEndTime(effectiveTime);
		}
		opt.setOuterPriceId(outerPriceId);
		if (outerPriceDao.updateByPrimaryKeySelective(opt) <= 0) {
			throw new BusinessException("操作失败，请联系技术支持人员！");
		}
	}
	
	/**
	 * 立即中止
	 */
	@Override
	@Transactional
	public void immediatelyStopOuterPrice(String outerPriceId, Date effectiveTime){
		if(StringUtil.isBlank(outerPriceId)){
			throw new BusinessException("记录id为空,请联系管理员");
		}
		if(effectiveTime == null){
			throw new BusinessException("中止时间为空,请联系管理员");
		}
		if(effectiveTime.before(new Date())){
			throw new BusinessException("中止时间不能早于当前时间");
		}
		// 根据id查询完整记录
		OuterPriceEntity outerPrice = outerPriceDao.selectByPrimaryKey(outerPriceId);
		if(outerPrice == null){
			logger.error("\nID:" + outerPriceId + "\n查询不到记录");
			throw new BusinessException("查询不到记录，请联系管理员");
		}
		if(!(FossConstants.YES.equals(outerPrice.getActive()))){
			logger.error("\nID:" + outerPriceId + "\nactive:" + outerPrice.getActive() + "\n不能激活未激活记录");
			throw new BusinessException("不能中止未激活记录");
		}
		if(effectiveTime.before(outerPrice.getBeginTime())){
			throw new BusinessException("中止时间不能早于开始时间");
		}
		if(effectiveTime.after(outerPrice.getEndTime())){
			throw new BusinessException("中止时间不能晚于原有中止时间");
		}
		
		OuterPriceEntity opt = new OuterPriceEntity();
		opt.setOuterPriceId(outerPriceId);
		opt.setEndTime(effectiveTime);
		opt.setModifyUserCode(getCurrentUserCode());
		opt.setModifyOrgCode(getCurrentOrgCode());
		Date date = new Date();
		opt.setModifyTime(date);
		opt.setVersionNo(date.getTime());
		if (outerPriceDao.updateByPrimaryKeySelective(opt) <= 0) {
			throw new BusinessException("操作失败，请联系技术支持人员！");
		}
		
	}
	
	@Override
	public OuterPriceVo addOuterPrice(OuterPriceEntity outerPriceEntity) {
		if(outerPriceEntity != null && StringUtil.isNotBlank(outerPriceEntity.getName())) {
			List<OuterPriceEntity> outerPriceEntities = outerPriceDao.queryOuterPriceByName(outerPriceEntity.getName());
			if(CollectionUtils.isNotEmpty(outerPriceEntities)) {
				throw new BusinessException("该方案名称已经存在，不能再次使用！");
			}
			
			//偏线价格方案NAME、有效期、方案描述，来自前端
			String outerPriceUUId = UUIDUtils.getUUID();
			outerPriceEntity.setOuterPriceId(outerPriceUUId);
			
			String temp = getCurrentUserCode();
			outerPriceEntity.setCreateUserCode(temp);
			outerPriceEntity.setModifyUserCode(temp);
			temp = getCurrentOrgCode();
			outerPriceEntity.setCreateOrgCode(temp);
			outerPriceEntity.setModifyOrgCode(temp);
			
			Date date = new Date();
			outerPriceEntity.setCreateTime(date);
			outerPriceEntity.setModifyTime(date);
			outerPriceEntity.setVersionNo(date.getTime());
			date = DateUtils.convert("2999-12-31 23:59:59", DateUtils.DATE_TIME_FORMAT);
			outerPriceEntity.setEndTime(date);
			outerPriceEntity.setActive(FossConstants.INACTIVE);
			
			if (outerPriceDao.insertSelective(outerPriceEntity) <= 0) {
				throw new BusinessException("操作失败，请联系技术支持人员！");
			}
		}
		OuterPriceVo vo = new OuterPriceVo();
		vo.setOuterPriceEntity(outerPriceEntity);
		return vo;
	}
	
	/**
	 * <p>修改偏线价格方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-6 下午3:04:15
	 * 
	 * @param marketingEventEntity
	 * 
	 * @see
	 */
	@Transactional
	public void updateOuterPrice(OuterPriceEntity record) {
		if(record != null && StringUtils.isNotBlank(record.getOuterPriceId())) {
			StringBuffer tempBuffer = new StringBuffer();
			if (StringUtils.isEmpty(record.getPartialLineCode())) {
				tempBuffer.append("目的站 ");
			}
			if (StringUtils.isEmpty(record.getProductCode())) {
				tempBuffer.append(" 运输类型 ");
			}
			if (StringUtils.isEmpty(record.getOutFieldCode())) {
				tempBuffer.append(" 外发外场 ");
			}
			if (tempBuffer != null && StringUtils.isNotEmpty(tempBuffer.toString())) {
				throw new BusinessException(tempBuffer.toString() + " 不能为空！");
			}
			int nameDuplicationNum = outerPriceDao.checkIsExistName(record);
			if(nameDuplicationNum > 1){
				throw new BusinessException("该方案名称已经存在，不能再次使用 ！");
			}
						
			OuterPriceEntity opt = outerPriceDao.selectByPrimaryKey(record.getOuterPriceId()); 
			if (opt != null && opt.getOuterPriceId().equals(record.getOuterPriceId())) {
				opt.setProductCode(record.getProductCode());
				opt.setPartialLineCode(record.getPartialLineCode());
				opt.setOutFieldCode(record.getOutFieldCode());
				opt.setNationCode(record.getNationCode());
				opt.setProvCode(record.getProvCode());
				opt.setCityCode(record.getCityCode());
				opt.setCountyCode(record.getCountyCode());
				opt.setWeightFeeRate(record.getWeightFeeRate());
				opt.setVolumeFeeRate(record.getVolumeFeeRate());
				opt.setMinFee(record.getMinFee());
				opt.setActive(record.getActive());
				opt.setName(record.getName());
				opt.setBeginTime(record.getBeginTime());
				opt.setEndTime(record.getEndTime());
				opt.setRemark(record.getRemark());
				opt.setModifyUserCode(getCurrentUserCode());
				opt.setModifyOrgCode(getCurrentOrgCode());
				Date date = new Date();
				opt.setModifyTime(date);
				opt.setVersionNo(date.getTime());
				
				if (outerPriceDao.updateByPrimaryKeySelective(opt) <= 0) {
					throw new BusinessException("操作失败，请联系技术支持人员！");
				}
			}
			
		}
	}
	

	
	/**
	 * <p>复制偏线价格方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-6 下午3:04:15
	 * 
	 * @param id
	 * 
	 * @see
	 */
	public OuterPriceVo copyOuterPrice(String id, String name){
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("对不起，传入参数为空！");
		}
		OuterPriceEntity record = new OuterPriceEntity();
		record.setOuterPriceId(id);
		id = UUIDUtils.getUUID();
		record.setId(id);
		Date nowDate = new Date();
		record.setVersionNo(nowDate.getTime());
		String temp = DateUtils.convert(nowDate, "yyyyMMddHHmmss");
		if (name.length() > NumberConstants.NUMBER_30) {
			name = name.substring(0, NumberConstants.NUMBER_15) + temp;
		} else {
			name = name + temp;
		}
		record.setName(name);
		
		if (outerPriceDao.copyOuterPrice(record) <= 0) {
			throw new BusinessException("复制失败，请联系技术支持人员！");
		}
		
		OuterPriceVo opv = new OuterPriceVo();
		opv.setOuterPriceId(id);
		opv.setCopyName(name);
		return opv;
	}
	
	/**
	 * <p>删除偏线价格方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-6 下午3:02:48
	 * 
	 * @param marketingEventId
	 * 
	 * @see
	 */
	public void deleteByPrimaryKey(List<String> ids) {
		if (ids != null && ids.size() >= 0) {
			if (ids.size() <= NumberConstants.NUMBER_1000) {
				if (outerPriceDao.deleteByPrimaryKey(ids) <= 0) {
					throw new BusinessException("删除失败，请联系技术支持人员！");
				}
			} else {
				List<List<String>> idsLists = CollectionUtils.splitListBySize(ids, FossConstants.ORACLE_MAX_IN_SIZE);
				for (List<String> lists : idsLists) {
					if (outerPriceDao.deleteByPrimaryKey(lists) <= 0) {
						throw new BusinessException("删除失败，请联系技术支持人员！");
					}
				}
			}
		} else {
			throw new BusinessException("传入参数有误，请重试");
		}
	}
	@Override
	public OuterPriceEntity searchOuterPriceByArgument(String partialLineCode,
			String outFieldCode, String productCode, Date billTime) {
		return outerPriceDao.searchOuterPriceByArgument(partialLineCode, outFieldCode, productCode, billTime);
	}

	/**
	 * <p>
	 * Description:根据主键查询记录<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-18
	 * @param id
	 * @return
	 * OuterPriceVo
	 */
	public OuterPriceVo selectByPrimaryKey(String id) {
		if(StringUtil.isEmpty(id)){
			throw new BusinessException("选择的主方案信息缺失!,请联系运维人员查询原因。");
	    }
		OuterPriceEntity ope = outerPriceDao.selectByPrimaryKey(id);
    	String temp = areaAddressService.queryRegionByCode(ope.getProvCode()).getName();
    	ope.setProvName(temp);
    	temp = areaAddressService.queryRegionByCode(ope.getCityCode()).getName();
    	ope.setCityName(temp);
    	temp = areaAddressService.queryRegionByCode(ope.getCountyCode()).getName();
    	ope.setCountyName(temp);
		OuterPriceVo opv = new OuterPriceVo();
		opv.setOuterPriceEntity(ope);
		return opv;
	}

	/**
	 * <p>
	 * Description:导出偏线价格方案<br />
	 * </p>
	 * @author Rosanu
	 * @version 0.1 2012-10-18
	 * @param id
	 * @return
	 * int
	 */
	public ExportResource exportOuterPrice(OuterPriceCondtionDto dto){
		List<OuterPricePlanDto> oppDtos = outerPriceDao.queryOuterPriceVoBatchInfo(dto, 0, 0);
		if(CollectionUtils.isEmpty(oppDtos)){
		    return null;
		}
		ExportResource exportResource = new ExportResource();
		
		//List<OuterPricePlanDto> pricePlanList = convertToRegionName(oppDtos);
		List<List<String>> rowList = new ArrayList<List<String>>();
		String temp = null;
    	Date nowDate = new Date();
		for (OuterPricePlanDto opp : oppDtos) {
		     rowList.add(exportPlatform(opp, temp, nowDate));
		}
		exportResource.setHeads(PricingColumnConstants.OUTER_PRICE_PLAN_TITLE);	    
		exportResource.setRowList(rowList);
		return exportResource;
	}
	
    /**
     * <p>填充方案 sheet row </p>.
     *
     * @param pricePlanEntity the price plan entity
     * 
     * @return the list
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-27 上午9:59:41
     * 
     * @see
     */
    private List<String> exportPlatform(OuterPricePlanDto dto, String temp, Date nowDate){
		List<String> result = new ArrayList<String>();	

		//{"方案名称","目的站","外发外场","运输类型","省份","城市","区县","重货价格","轻货价格","最低一票","数据状态","生效日期","截止日期","修改时间","修改人","是否当前版本"};
	    result.add(dto.getName());
		result.add(dto.getPartialLineName());
		result.add(dto.getOutFieldName());
		if ("TRANS_VEHICLE".equals(dto.getProductCode())) {
			result.add("汽运");
		} else {
			result.add("空运");
		}
		temp = areaAddressService.queryRegionByCode(dto.getProvCode()).getName();
	    result.add(temp);
    	temp = areaAddressService.queryRegionByCode(dto.getCityCode()).getName();
	    result.add(temp);
    	temp = areaAddressService.queryRegionByCode(dto.getCountyCode()).getName();
	    result.add(temp);
	    result.add(dto.getWeightFeeRate().toString());
	    result.add(dto.getVolumeFeeRate().toString());
	    result.add(dto.getMinFee());
	    
		if(FossConstants.ACTIVE.equals(dto.getActive())){
		    result.add("已激活");
		}else{
		    result.add("未激活");
		}

		//方案开始时间
		result.add(DateUtils.convert(dto.getBeginTime(), DateUtils.DATE_TIME_FORMAT));
		//方案结束时间
		result.add(DateUtils.convert(dto.getEndTime(), DateUtils.DATE_TIME_FORMAT));
		//修改时间
		result.add(DateUtils.convert(dto.getModifyDate(), DateUtils.DATE_TIME_FORMAT));
		result.add(dto.getModifyUser());
		
    	if (FossConstants.ACTIVE.equals(dto.getActive()) 
    			&& nowDate.after(dto.getBeginTime()) && nowDate.before(dto.getEndTime())) {
    		result.add("是");
		} else {
    		result.add("否");
		}

		return result;
	}

	/**
     * <p>
     * Description:根据ID查询记录<br />
     * </p>
     * @author admin
     * @version 0.1 2012-10-18
     * @param id
     * @return
     * OuterPriceEntity
     */
	public OuterPriceVo selectById(String id) {
		if(StringUtil.isEmpty(id)){
			throw new BusinessException("选择的主方案信息缺失!,请联系运维人员查询原因。");
	    }
		OuterPricePlanDto opp = outerPriceDao.selectById(id);
    	String temp = areaAddressService.queryRegionByCode(opp.getProvCode()).getName();
    	opp.setProvName(temp);
    	temp = areaAddressService.queryRegionByCode(opp.getCityCode()).getName();
    	opp.setCityName(temp);
    	temp = areaAddressService.queryRegionByCode(opp.getCountyCode()).getName();
    	opp.setCountyName(temp);
		OuterPriceVo opv = new OuterPriceVo();
		opv.setOuterPricePlanDto(opp);
		return opv;
	}


	/**
	 * 
	 * @Description: 导入时效方案 Company:IBM
	 * 
	 * @author Rosanu
	 * 
	 * @date 2012-12-24 下午2:04:08
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public void importOuterPrice(Workbook book){
		Integer index = 1;
    		// 默认获取获取工作表1
			Sheet sheet = book.getSheetAt(0);// 默认第一个  
            int col = 0;
            try {
            	Iterator rows = sheet.rowIterator();
    			// 加个计数器 count
    			rows.next();// 第一行标题不读
    			if(rows.hasNext()){
    				for (; rows.hasNext();) {
    					Row row = (Row)rows.next();
    					OuterPriceEntity ope = new OuterPriceEntity();
    					String temp = null;
    					col = 0;
    					for (Iterator cells = row.cellIterator(); cells.hasNext();) {
    						Cell cell = (Cell)cells.next();
    						//序号	方案名称	目的站	运输类型	外发外场	省份	城市	区县	重货费率	轻货费率	最低一票	生效日期	截止日期
    						switch (cell.getColumnIndex()) {
    							case 0:
    								//获得填写的序号
    								col ++;
    								break;
    							case 1:
    								//获得填写的方案名称
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								if(StringUtils.isNotBlank(temp)) {
    									List<OuterPriceEntity> outerPriceEntities = outerPriceDao.queryOuterPriceByName(temp.trim());
    									if(CollectionUtils.isNotEmpty(outerPriceEntities)) {
    										throw new BusinessException("该方案名称已经存在，不能再次使用！");
    									}
    								} else {
    									throw new BusinessException("第"+index+"行，第"+col+"列未填写方案名称！");
    								}
    								ope.setName(temp);
    								break;
    							case 2:
    								// 目的站
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								logger.info("目的站>>" + temp);
                                    if(StringUtils.isNotBlank(temp)) {
                                    	OuterBranchEntity entity = new OuterBranchEntity();
                                    	entity.setAgentDeptName(temp.trim());
                                    	List<OuterBranchEntity> obe = vehicleAgencyDeptService.queryVehicleAgencyDepts(entity, TEN, 0);
                                    	if (obe != null && obe.size() > 0 ) {
                                    		ope.setPartialLineCode(obe.get(0).getAgentDeptCode());
                                    		ope.setProvCode(obe.get(0).getProvCode());
                                    		ope.setCityCode(obe.get(0).getCityCode());
                                    		ope.setCountyCode(obe.get(0).getCountyCode());
										} else {
	                                    	throw new BusinessException("第"+index+"行，第"+col+"列的目的站（“" + temp + "”）在系统中不存在！");
										}
                                    } else {
                                    	throw new BusinessException("第"+index+"行，第"+col+"列未填写目的站！");
    								}
    								break;
    							case THREE:
    								// 运输类型
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								logger.info("运输类型>>"+temp);
                                    if(StringUtils.isNotBlank(temp)) {
                                    	//默认为汽运
                                    	if("空运".equals(temp.trim())) {
                                        	ope.setProductCode("TRANS_AIRCRAFT");
                                        } else {
                                        	//汽运
                                        	ope.setProductCode("TRANS_VEHICLE");
        								}
                                    } else {
                                    	//汽运
                                    	ope.setProductCode("TRANS_VEHICLE");
									}
                                    
    								break;
    							case FOUR:
    								// 外发外场
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								logger.info("外发外场>>"+temp);
                                    if(StringUtils.isNotBlank(temp)) {
                                    	OutfieldEntity entity = new OutfieldEntity();
                                    	entity.setName(temp.trim());
                                    	List<OutfieldEntity> oe = outfieldService.queryOutfieldByEntity(entity, 0, TEN);
                                    	if (oe != null && oe.size() > 0) {
											ope.setOutFieldCode(oe.get(0).getOrgCode());
										} else {
	                                    	throw new BusinessException("第"+index+"行，第"+col+"列的外发外场（“" + temp + "”）在系统中不存在！");
										}
                                    } else {
                                    	throw new BusinessException("第"+index+"行，第"+col+"列未填写外发外场！");
									}
    								break;
    							case FIVE:
    								// 重货费率	
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								logger.info("重货费率>>"+temp);
                                    if(StringUtils.isNotBlank(temp)) {
                                    	try {
                                    		ope.setWeightFeeRate(new BigDecimal(temp.trim()));
										} catch (Exception e) {
											throw new BusinessException("第"+index+"行，第"+col+"列填写的重货费率（“" + temp + "”）有误！");
										}
                                    } else {
                                    	throw new BusinessException("第"+index+"行，第"+col+"列未填写重货费率！");
									}
    								break;
    							case SIX:
    								// 轻货费率	
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								logger.info("轻货费率>>"+temp);
                                    if(StringUtils.isNotBlank(temp)) {
                                    	try {
                                    		ope.setVolumeFeeRate(new BigDecimal(temp.trim()));
										} catch (Exception e) {
											throw new BusinessException("第"+index+"行，第"+col+"列填写的轻货费率（“" + temp + "”）有误！");
										}
                                    } else {
                                    	throw new BusinessException("第"+index+"行，第"+col+"列未填写轻货费率！");
									}
    								break;
    							case SEVEN:
    								// 最低一票	
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								logger.info("最低一票>>"+temp);
    								//temp = temp.lastIndexOf(1);
    								
                                    if(StringUtils.isNotBlank(temp)) {
                                    	try {
            								int a = temp.trim().lastIndexOf(".");
            								if (a > 0) {
            									temp = temp.substring(0, a);  
        									}
                                    		ope.setMinFee(Long.parseLong(temp.trim()));
										} catch (Exception e) {
											throw new BusinessException("第"+index+"行，第"+col+"列填写的最低一票（“" + temp + "”）有误！");
										}
                                    } else {
                                    	throw new BusinessException("第"+index+"行，第"+col+"列未填写最低一票！");
									}
    								break;
    							case EIGHT:
    								// 生效日期	
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								logger.info("生效日期>>"+temp);
                                    if(StringUtils.isNotBlank(temp)) {
                                    	try {
                                    		ope.setBeginTime(DateUtils.convert(temp.trim()));
										} catch (Exception e) {
											throw new BusinessException("第"+index+"行，第"+col+"列填写的生效日期（“" + temp + "”）有误！");
										}
                                    } else {
                                    	throw new BusinessException("第"+index+"行，第"+col+"列未填写生效日期！");
									}
    								break;
    							case NINE:
    								// 截止日期	
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								logger.info("截止日期>>"+temp);
                                    if(StringUtils.isNotBlank(temp)) {
                                    	try {
                                    		ope.setEndTime(DateUtils.convert(temp.trim()));
										} catch (Exception e) {
											throw new BusinessException("第"+index+"行，第"+col+"列填写的截止日期（“" + temp + "”）有误！");
										}
                                    } else {
                                		ope.setEndTime(DateUtils.convert("2999-12-31 23:59:59", DateUtils.DATE_TIME_FORMAT));
									}
    								break;
    							default :
    								logger.info("第"+index+"行，第"+col+"列数据超出范围！");
    						}
    					}
    					temp = UUIDUtils.getUUID();
    					ope.setOuterPriceId(temp);
    					
    					temp = getCurrentUserCode();
    					ope.setCreateUserCode(temp);
    					ope.setModifyUserCode(temp);
    					temp = getCurrentOrgCode();
    					ope.setCreateOrgCode(temp);
    					ope.setModifyOrgCode(temp);
    					
    					Date date = new Date();
    					ope.setCreateTime(date);
    					ope.setModifyTime(date);
    					ope.setVersionNo(date.getTime());
    					ope.setActive(FossConstants.INACTIVE);
    					
    					if (outerPriceDao.insertSelective(ope) <= 0) {
    						throw new BusinessException("操作失败，请联系技术支持人员！");
    					}
    					index ++;
    				}
    			}
			} catch (Exception e) {
				throw new BusinessException("第"+index+"行数据出现异常情况。" + e.getMessage());
			}
	}
}
