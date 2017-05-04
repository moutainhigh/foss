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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IToAddPartnerProgramDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IToAddPartnerProgramService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ToAddPartnerProgramEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ToAddPartnerProgramCondtionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.ToAddPartnerProgramVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

public class ToAddPartnerProgramService implements IToAddPartnerProgramService {
	
	private static final int THREE = 3;
	private static final int FOUR =4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;
	/** 日志 */
	private static final Logger logger = Logger.getLogger(ToAddPartnerProgramService.class);
    @Inject
	private IToAddPartnerProgramDao toAddPartnerProgramDao;
	private IAreaAddressService areaAddressService;
	private IOutfieldService outfieldService;
	private IVehicleAgencyDeptService vehicleAgencyDeptService;
	
	
	
	public void setToAddPartnerProgramDao(IToAddPartnerProgramDao toAddPartnerProgramDao) {
		this.toAddPartnerProgramDao = toAddPartnerProgramDao;
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
	 * @功能 根据条件查询合伙人到达加收方案总数
	 * @author 265475
	 * @date 2016-9-30 9:37:54
	 * @return
	 */
	@Override
	public Long queryToAddPartnerProgramVoBatchInfoCount(
			ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto) {
		return toAddPartnerProgramDao.queryToAddPartnerProgramVoBatchInfoCount(toAddPartnerProgramCondtionDto);
	}
	
	/**
	 * .
	 * <p>
	 * 新增合伙人到达加收方案<br/>
	 * 方法名：addToAddPartnerProgram
	 * </p>
	 * 
	 * @author 265475
	 * 
	 * @时间 2016-09-05
	 * 
	 * @since JDK1.6
	 */
	@Override
	public ToAddPartnerProgramVo toAddPartnerProgramPrice(ToAddPartnerProgramEntity toAddPartnerProgramEntity) {
				if(toAddPartnerProgramEntity != null && StringUtil.isNotBlank(toAddPartnerProgramEntity.getPlanName())) {
					//合伙人到达加收方NAME、有效期、方案描述，来自前端
					String toAddPartnerProgramUUId = UUIDUtils.getUUID();
					toAddPartnerProgramEntity.setToAddPartnerProgramid(toAddPartnerProgramUUId);
					
					String temp = getCurrentUserCode();
					toAddPartnerProgramEntity.setCreateUserCode(temp);
					toAddPartnerProgramEntity.setModifyUserCode(temp);
					temp = getCurrentOrgCode();
					toAddPartnerProgramEntity.setCreateOrgCode(temp);
					toAddPartnerProgramEntity.setModifyOrgCode(temp);
					
					Date date = new Date();
					toAddPartnerProgramEntity.setCreateTime(date);
					toAddPartnerProgramEntity.setModifyTime(date);
					toAddPartnerProgramEntity.setVersionNo(date.getTime());
					date = DateUtils.convert("2999-12-31 23:59:59", DateUtils.DATE_TIME_FORMAT);
					toAddPartnerProgramEntity.setEndTime(date);
					toAddPartnerProgramEntity.setActive(FossConstants.INACTIVE);
					
					if (toAddPartnerProgramDao.insertSelective(toAddPartnerProgramEntity) <= 0) {
						throw new BusinessException("操作失败，请联系技术支持人员！");
					}
				}
				ToAddPartnerProgramVo vo = new ToAddPartnerProgramVo();
				vo.setToAddPartnerProgramEntity(toAddPartnerProgramEntity);
				return vo;
	}
	
	/**
	 * @功能 根据条件查询合伙人到达加收方案
	 * @author 265475
	 * @date 2016-9-30 9:37:54
	 * @return
	 */
	@Override
	public List<ToAddPartnerProgramEntity> querytoAddPartnerProgramVoBatchInfo(
			ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto, int start, int limit) {
		List<ToAddPartnerProgramEntity> oppDtos = toAddPartnerProgramDao.querytoAddPartnerProgramVoBatchInfo(toAddPartnerProgramCondtionDto, start, limit);
		List<ToAddPartnerProgramEntity> lists = new ArrayList<ToAddPartnerProgramEntity>();
				for (ToAddPartnerProgramEntity dto : oppDtos) {
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
     * 
     * <p>(
     * 根据方案ID查询合伙到达加收方案明细信息
     * )<br/>
     * </p> 
     * @author 265475
	 * @date 2016-09-05 下午2:04:08
	 * @return
	 * @version V1.0
     * @return
     * @see
     */
	public ToAddPartnerProgramVo selectById(String id) {
		if(StringUtil.isEmpty(id)){
			throw new BusinessException("选择的主方案信息缺失!,请联系运维人员查询原因。");
	    }
		ToAddPartnerProgramEntity opp = toAddPartnerProgramDao.selectById(id);
		ToAddPartnerProgramVo opv = new ToAddPartnerProgramVo();
		opv.setToAddPartnerProgramEntity(opp);
		return opv;
	}
	
	
	/**
	 * .
	 * <p>
	 * 修改合伙人到达加收方案<br/>
	 * 方法名：updateToAddPartnerProgram
	 * </p>
	 * 
	 * @author zoushengli
	 * 
	 * @时间 2016-09-05
	 * 
	 * @since JDK1.6
	 */
	@Override
	public void updateToAddPartnerProgram(ToAddPartnerProgramEntity record) {
		if(record != null && StringUtils.isNotBlank(record.getToAddPartnerProgramid())) {
			StringBuffer tempBuffer = new StringBuffer();
			if (StringUtils.isEmpty(record.getOrgCode())) {
				tempBuffer.append("目的站 ");
			}
			if (StringUtils.isEmpty(record.getTransportFlag())) {
				tempBuffer.append(" 运输类型 ");
			}
			if (tempBuffer != null && StringUtils.isNotEmpty(tempBuffer.toString())) {
				throw new BusinessException(tempBuffer.toString() + " 不能为空！");
			}
			record.setModifyUserCode(getCurrentUserCode());
			record.setModifyOrgCode(getCurrentOrgCode());
				Date date = new Date();
			record.setModifyTime(date);
			record.setVersionNo(date.getTime());
				
			try {
				toAddPartnerProgramDao.updateByPrimaryKeySelective(record);
			} catch (Exception e) {
				throw new BusinessException(tempBuffer.toString() + "更新合伙人加收方案失败");
			}
		}
		
	}
	
	/**
	 * .
	 * <p>
	 * 删除合伙人到达加收方案<br/>
	 * 方法名：deletePricingDiscount
	 * </p>
	 * @author 265475
	 * @date 2016-09-05 下午2:04:08
	 * @return
	 * @version V1.0
	 */
	@Override
	public void deleteByPrimaryKey(List<String> ids) {
		if (ids != null && ids.size() >= 0) {
			if (ids.size() <= NumberConstants.NUMBER_1000) {
				if (toAddPartnerProgramDao.deleteByPrimaryKey(ids) <= 0) {
					throw new BusinessException("删除失败，请联系技术支持人员！");
				}
			} else {
				List<List<String>> idsLists = CollectionUtils.splitListBySize(ids, FossConstants.ORACLE_MAX_IN_SIZE);
				for (List<String> lists : idsLists) {
					if (toAddPartnerProgramDao.deleteByPrimaryKey(lists) <= 0) {
						throw new BusinessException("删除失败，请联系技术支持人员！");
					}
				}
			}
		} else {
			throw new BusinessException("传入参数有误，请重试");
		}
	}
	
	/**
	 * 激活合伙人到达加收方案
	 * @author 265475
	 * @date 2016-09-05 下午2:04:08
	 * @return
	 * @version V1.0
	 */
	@Override
	public void immediatelyActiveToAddPartnerProgram(String toAddPartnerProgramid, String yesOrNo, Date effectiveTime) {
		if(StringUtils.isEmpty(toAddPartnerProgramid)){
			throw new BusinessException("传入参数有误，ID不能为空！");
		}
		ToAddPartnerProgramEntity opt = new ToAddPartnerProgramEntity();
		opt.setActive(yesOrNo);
		if (FossConstants.ACTIVE.equals(yesOrNo)) {
			//激活方案
			ToAddPartnerProgramEntity ope = toAddPartnerProgramDao.selectByPrimaryKey(toAddPartnerProgramid);
			if (ope == null) {
				throw new BusinessException("传入参数有误，ID在系统中不存在！" + toAddPartnerProgramid);
			}
			ope.setToAddPartnerProgramid(toAddPartnerProgramid);
			//设置已存在相同激活方案的截止时间为新激活方案的生效时间的前一秒
			ope.setEndTime(DateUtils.getDateAdd(effectiveTime, -NumberConstants.NUMBER_1000));
			ope.setActive(FossConstants.ACTIVE);
			
			if (toAddPartnerProgramDao.updateToAddPartnerProgramEndTime(ope) < 0) {
				throw new BusinessException("修改已激活方案（上个版本）的数据失败，请联系技术支持人员！");
			}
		
			
			opt.setBeginTime(effectiveTime);
		} else {
			//中止方案
			opt.setEndTime(effectiveTime);
		}
		opt.setToAddPartnerProgramid(toAddPartnerProgramid);
		if (toAddPartnerProgramDao.updateByPrimaryKeySelective(opt) <= 0) {
			throw new BusinessException("操作失败，请联系技术支持人员！");
		}
	}
	
	/**
	 * <p>
	 * Description:根据主键Id批量激活合伙人到达加收方案<br />
	 * </p>
	 * @author zoushengli
	 * @version 0.1 2016-09-05
	 * @param id
	 * @return
	 * int
	 */
	@Transactional
	public void updateToAddPartnerProgramActiveById(List<ToAddPartnerProgramEntity> opps, String yesOrNo) {
		//待激活方案数据
		List<String> ids = new ArrayList<String>();
		List<String> orgCodes = new ArrayList<String>();

		String temp = null;
		ToAddPartnerProgramEntity record = new ToAddPartnerProgramEntity();
		for (ToAddPartnerProgramEntity dto : opps) {
			if (dto == null) {
				continue;
			}
			temp = dto.getToAddPartnerProgramid();
			ids.add(temp);
			record.setToAddPartnerProgramid(temp);
			//设置已存在相同激活方案的截止时间为新激活方案的生效时间的前一秒
			record.setEndTime(DateUtils.getDateAdd(dto.getBeginTime(), -NumberConstants.NUMBER_1000));
			record.setOrgCode(dto.getOrgCode());
			record.setTransportFlag(dto.getTransportFlag());
			record.setActive(FossConstants.ACTIVE);
			
			for(int j = 0;j<orgCodes.size();j++){
				if(!StringUtils.isBlank(orgCodes.get(j))){
				if(orgCodes.get(j).equals(dto.getOrgCode())){
					throw new BusinessException("批量激活时,同一目的站不能选着多条");
				}}
			}
			
			orgCodes.add(dto.getOrgCode());
			
			if (toAddPartnerProgramDao.updateToAddPartnerProgramEndTime(record) < 0) {
				throw new BusinessException("修改已激活方案（上个版本）的数据失败，请联系技术支持人员！");
			}
			
		}
		
		if (ids != null && ids.size() >= 0) {
			if (ids.size() <= NumberConstants.NUMBER_1000) {
				if (toAddPartnerProgramDao.updateToAddPartnerProgramActiveById(ids, yesOrNo) <= 0) {
					throw new BusinessException("操作失败，请联系技术支持人员！");
				}
			} else {
				List<List<String>> idsLists = CollectionUtils.splitListBySize(ids, FossConstants.ORACLE_MAX_IN_SIZE);
				for (List<String> lists : idsLists) {
					if (toAddPartnerProgramDao.updateToAddPartnerProgramActiveById(lists, yesOrNo) <= 0) {
						throw new BusinessException("操作失败，请联系技术支持人员！");
					}
				}
			}
		} else {
			throw new BusinessException("传入参数有误，请重试");
		}
	}
	
	/**
	 * 中止合伙人到达加收方案
	 * @author 265475
	 * @date 2016-09-05 下午2:04:08
	 * @return
	 * @version V1.0
	 */
	@Override
	public void immediatelyStopToAddPartnerProgram(String toAddPartnerProgramid, Date effectiveTime) {
		if(StringUtil.isBlank(toAddPartnerProgramid)){
			throw new BusinessException("记录id为空,请联系管理员");
		}
		if(effectiveTime == null){
			throw new BusinessException("中止时间为空,请联系管理员");
		}
		if(effectiveTime.before(new Date())){
			throw new BusinessException("中止时间不能早于当前时间");
		}
		// 根据id查询完整记录
		ToAddPartnerProgramEntity outerPrice = toAddPartnerProgramDao.selectByPrimaryKey(toAddPartnerProgramid);
		if(outerPrice == null){
			logger.error("\nID:" + toAddPartnerProgramid + "\n查询不到记录");
			throw new BusinessException("查询不到记录，请联系管理员");
		}
		if(!(FossConstants.YES.equals(outerPrice.getActive()))){
			logger.error("\nID:" + toAddPartnerProgramid + "\nactive:" + outerPrice.getActive() + "\n不能激活未激活记录");
			throw new BusinessException("不能中止未激活记录");
		}
		if(effectiveTime.before(outerPrice.getBeginTime())){
			throw new BusinessException("中止时间不能早于开始时间");
		}
		if(effectiveTime.after(outerPrice.getEndTime())){
			throw new BusinessException("中止时间不能晚于原有中止时间");
		}
		
		ToAddPartnerProgramEntity opt = new ToAddPartnerProgramEntity();
		opt.setToAddPartnerProgramid(toAddPartnerProgramid);
		opt.setEndTime(effectiveTime);
		opt.setModifyUserCode(getCurrentUserCode());
		opt.setModifyOrgCode(getCurrentOrgCode());
		Date date = new Date();
		opt.setModifyTime(date);
		opt.setVersionNo(date.getTime());
		if (toAddPartnerProgramDao.updateByPrimaryKeySelective(opt) <= 0) {
			throw new BusinessException("操作失败，请联系技术支持人员！");
		}
		
	}
	
	/**
	 * .
	 * <p>
	 * 导出合伙人到达加收方案<br/>
	 * 方法名：exportToAddPartnerProgram
	 * </p>
	 * 
	 * @author zoushengli
	 * 
	 * @时间 2016-09-05
	 * 
	 * @since JDK1.6
	 */
	public ExportResource exportToAddPartnerProgram(ToAddPartnerProgramCondtionDto dto){
		List<ToAddPartnerProgramEntity> oppDtos = toAddPartnerProgramDao.querytoAddPartnerProgramVoBatchInfo(dto, 0, 0);
		if(CollectionUtils.isEmpty(oppDtos)){
		    return null;
		}
		ExportResource exportResource = new ExportResource();
		//List<ToAddPartnerProgramEntity> pricePlanList = convertToRegionName(oppDtos);
		List<List<String>> rowList = new ArrayList<List<String>>();
     	Date nowDate = new Date();
		for (ToAddPartnerProgramEntity opp : oppDtos) {
		     rowList.add(exportPlatform(opp, nowDate));
		}
		exportResource.setHeads(PricingColumnConstants.TOADDPARTNERPROGRAM_PLAN_TITLE);	    
		exportResource.setRowList(rowList);
		return exportResource;
	}
	
	/**
	 * 
	 * @Description: 导入合伙人到达加收方案 
	 * 
	 * @author 265475
	 * 
	 * @date 2016-09-05 下午2:04:08
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void importToAddPartnerProgram(Workbook book) {
		Integer index = 1;
    	//多个Excel表格
    	//for (int i = 0; i < sheetNum; i++) {
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
    					ToAddPartnerProgramEntity ope = new ToAddPartnerProgramEntity();
    					String temp = null;
    					col = 0;
    					for (Iterator cells = row.cellIterator(); cells.hasNext();) {
    						Cell cell = (Cell)cells.next();
    						//序号	方案名称	目的站	运输类型	重货费率	轻货费率	最低一票	生效日期	截止日期
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
    									ope.setPlanName(temp);
    								} else {
    									throw new BusinessException("第"+index+"行，第"+col+"列未填写方案名称！");
    								}
    								
    								break;
    							case 2:
    								// 目的站
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								logger.info("目的站>>" + temp);
                                    if(StringUtils.isNotBlank(temp)) {
                                    	SaleDepartmentEntity entity = new SaleDepartmentEntity();
                                    	entity.setName(temp.trim());
                                    	List<SaleDepartmentEntity> obe = toAddPartnerProgramDao.queryTwolevelAgencyDeptsByCondition(entity, NumberConstants.NUMBER_10, 0);
                                    	if (obe != null && obe.size() > 0 ) {
                                    		ope.setOrgCode(obe.get(0).getCode());
                                    		ope.setNetworkModel(obe.get(0).getNetworkModel());
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
                                    	if("汽运".equals(temp.trim())) {
                                        	ope.setTransportFlag("0");
                                        } else {
                                        	//throw new BusinessException("第"+index+"行，第"+col+"列的目的站（“" + temp + "”）在系统中不存在！");
                                        	throw new BusinessException("第"+index+"行，第"+col+"运输类型（“" + temp + "”）无法添加！");
    										
        								}
                                    } 
                                    
    								break;
    							case FOUR:
    								// 重货费率	
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								logger.info("重货费率>>"+temp);
                                    if(StringUtils.isNotBlank(temp)) {
                                    	try {
                                    		ope.setHeavyPrice(new BigDecimal(temp.trim()));
										} catch (Exception e) {
											throw new BusinessException("第"+index+"行，第"+col+"列填写的重货费率（“" + temp + "”）有误！");
										}
                                    } else {
                                    	throw new BusinessException("第"+index+"行，第"+col+"列未填写重货费率！");
									}
    								break;
    							case FIVE:
    								// 轻货费率	
    								col ++;
    								temp = ExcelHandleUtil.obtainStringVal(cell);
    								logger.info("轻货费率>>"+temp);
                                    if(StringUtils.isNotBlank(temp)) {
                                    	try {
                                    		ope.setLightPrice(new BigDecimal(temp.trim()));
										} catch (Exception e) {
											throw new BusinessException("第"+index+"行，第"+col+"列填写的轻货费率（“" + temp + "”）有误！");
										}
                                    } else {
                                    	throw new BusinessException("第"+index+"行，第"+col+"列未填写轻货费率！");
									}
    								break;
    							case SIX:
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
                                    		ope.setLowestPrice(new BigDecimal(temp.trim()));
										} catch (Exception e) {
											throw new BusinessException("第"+index+"行，第"+col+"列填写的最低一票（“" + temp + "”）有误！");
										}
                                    } else {
                                    	throw new BusinessException("第"+index+"行，第"+col+"列未填写最低一票！");
									}
    								break;
    							case SEVEN:
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
    							case EIGHT:
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
    					ope.setToAddPartnerProgramid(temp);
    					
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
    					
    					if (toAddPartnerProgramDao.insertSelective(ope) <= 0) {
    						throw new BusinessException("操作失败，请联系技术支持人员！");
    					}
    					index ++;
    				}
    			}
			} catch (Exception e) {
				throw new BusinessException("第"+index+"行数据出现异常情况。" + e.getMessage());
			}
		//}
	}
	
	
	
	 /**
     * <p>填充方案 sheet row </p>.
     *
      * @author 265475
	 * @date 2016-09-05 下午2:04:08
	 * @return
	 * @version V1.0
     * 
     * @see
     */
    private List<String> exportPlatform(ToAddPartnerProgramEntity dto,  Date nowDate){
		List<String> result = new ArrayList<String>();	

		//{"方案名称","营业部","外发外场","运输类型","重货价格","轻货价格","最低一票","数据状态","生效日期","截止日期","修改时间","修改人","是否当前版本"};
	    result.add(dto.getPlanName());
		result.add(dto.getName());
		if ("0".equals(dto.getTransportFlag())) {
			result.add("汽运");
		} else {
			result.add("空运");
		}
	    result.add(dto.getHeavyPrice().toString());
	    result.add(dto.getLightPrice().toString());
	    result.add(dto.getLowestPrice().toString());
	    
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
		result.add(DateUtils.convert(dto.getModifyTime(), DateUtils.DATE_TIME_FORMAT));
		result.add(dto.getModifyUserCode());
		
    	if (FossConstants.ACTIVE.equals(dto.getActive()) 
    			&& nowDate.after(dto.getBeginTime()) && nowDate.before(dto.getEndTime())) {
    		result.add("是");
		} else {
    		result.add("否");
		}

		return result;
	}

	/**
	 * 合伙人到达加收方案：目的站网点名称 
	 * @author 352676 
	 * @date 2016年9月1日 下午4:10:38
	 * @param saleDepartmentEntity
	 * @param limit
	 * @param start
	 * @return 
	 */
	@Override
	public List<SaleDepartmentEntity> queryTwolevelAgencyDeptsByCondition(
			SaleDepartmentEntity saleDepartmentEntity, int limit, int start) {
		if (null == saleDepartmentEntity) {
			saleDepartmentEntity = new SaleDepartmentEntity();
		}
		List<SaleDepartmentEntity> saleDepartmentEntitys = toAddPartnerProgramDao.queryTwolevelAgencyDeptsByCondition(saleDepartmentEntity, limit,
				start);
		return saleDepartmentEntitys;
	}
	
	/**
	 * 合伙人到达加收方案：目的站网点名称  总数量
	 * @author 352676 
	 * @date 2016年9月1日 下午4:13:02
	 * @param saleDepartmentEntity
	 * @return 
	 */
	@Override
	public Long queryTwolevelAgencyDeptsCount(SaleDepartmentEntity saleDepartmentEntity) {
		if (null == saleDepartmentEntity) {
			saleDepartmentEntity = new SaleDepartmentEntity();
		}
		return toAddPartnerProgramDao.queryTwolevelAgencyDeptsCount(saleDepartmentEntity);
	}

	/**
	 * 根据查询条件查询加收方案信息
	 * @author Foss-351326-xingjun 
	 * @date 2016-8-31 下午7:16:33
	 * @param toAddPartnerProgramCondtionDto
	 * @return 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IToAddPartnerProgramDao#addedFeePlanCaculateQuery(com.deppon.foss.module.pickup.pricing.api.shared.dto.ToAddPartnerProgramCondtionDto)
	 */
	@Override
	public ToAddPartnerProgramEntity addedFeePlanCaculateQuery(
			ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto) {
		return toAddPartnerProgramDao.addedFeePlanCaculateQuery(toAddPartnerProgramCondtionDto);
	}

	

}
