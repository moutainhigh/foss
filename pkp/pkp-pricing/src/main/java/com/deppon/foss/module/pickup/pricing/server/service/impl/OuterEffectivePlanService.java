package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.deppon.foss.module.pickup.pricing.api.server.dao.IOuterEffectivePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterEffectivePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterEffectivePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterEffectivePlanConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterEffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.EffectivePlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.OuterEffectiveVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.WaybillConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 偏线时效方案Service服务类
 * @author Foss-105888-Zhangxingwang
 *
 */
public class OuterEffectivePlanService implements IOuterEffectivePlanService{
	
	private static final int THREE = 3;
	private static final int FOUR =4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;
	private static final int NINE = 9;
	private static final int TEN = 10;
	private static final int ELEVEN  = 11;
	
	/** 日志 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OuterEffectivePlanService.class);

	/**
	 * 偏线时效Dao服务类
	 */
	private IOuterEffectivePlanDao outerEffectivePlanDao;
	/**
	 * 区域服务类
	 */
	private IAreaAddressService areaAddressService;
	/**
	 * 外场服务类
	 */
	private IOutfieldService outfieldService;
	/**
	 * 偏线代理网点服务类
	 */
	private IVehicleAgencyDeptService vehicleAgencyDeptService;	
	
	/**
	 * 根据给定的条件查询后台的条数
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 09:02:50
	 */
	@Override
	public Long queryOuterEffectivePlanVoBatchCount(OuterEffectivePlanConditionDto outerEffectivePlanConditionDto) {
		return outerEffectivePlanDao.queryOuterEffectivePlanVoBatchCount(outerEffectivePlanConditionDto);
	}
	
	/**
	 * 根据给定的条件查询后台的详情数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 09:02:50
	 */
	@Override
	public List<OuterEffectivePlanDto> queryOuterPriceVoBatchInfo(
			OuterEffectivePlanConditionDto outerEffectivePlanConditionDto, int start, int limit) {
		//查询后台数据
		List<OuterEffectivePlanDto> oppDtos = outerEffectivePlanDao.queryOuterPriceVoBatchInfo(outerEffectivePlanConditionDto, start, limit);
		
		//进行相关数据的转换：如到达承诺时点、是否当前版本
		List<OuterEffectivePlanDto> lists = new ArrayList<OuterEffectivePlanDto>();
		for (OuterEffectivePlanDto dto : oppDtos) {
			//判断是否为当前版本
	    	Date nowDate = new Date();
	    	Date beginTime = dto.getBeginTime();
	    	Date endTime = dto.getEndTime();
	    	//判断是否为已经激活的状态且当前时间在当前的有效时间之内
	    	if (FossConstants.ACTIVE.equals(dto.getActive()) && nowDate.after(beginTime) && nowDate.before(endTime)) {
	    		dto.setVersionNo(FossConstants.YES);
	    	//否则都不在本版本里面
			} else {
				dto.setVersionNo(FossConstants.NO);
			}
	    	//修改到达偏线代理网点、派送承诺时点的时间格式
	    	changeDate(dto);
	    	//添加到集合里面
			lists.add(dto);
		}
		return lists;
	}
	
	/**
	 * 转换时间格式
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 09:11:13
	 * @param dto
	 */
	private void changeDate(OuterEffectivePlanDto dto) {
		//进行相关格式的转换
		SimpleDateFormat sdf1 = new SimpleDateFormat("HHmm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		//开始进行到达偏线代理网点的时点转换，如果出现异常继续去取数据的原始数据
		Date d;
		//只有时点不为空才能进行转换
		if(StringUtils.isNotEmpty(dto.getArriveOuterBranchTime())){
			String leaveTime;
			try {
				d = sdf1.parse(dto.getArriveOuterBranchTime());
				leaveTime = sdf2.format(d);
			} catch (ParseException e) {
				leaveTime = dto.getDeliveryTime();
			}
			dto.setArriveOuterBranchTime(leaveTime);
		}

		//开始进行派送的时点转换，如果出现异常继续去取数据的原始数据
		//只有数据不为空的情况下才能进行转换
		if(StringUtils.isNotEmpty(dto.getDeliveryTime())){
			String deadTime;
			try {
				d = sdf1.parse(dto.getDeliveryTime());
				deadTime = sdf2.format(d);
			} catch (ParseException e) {
				deadTime = dto.getDeliveryTime();
			}
			dto.setDeliveryTime(deadTime);
		}
	}
	
	/**
	 * @Description: 获得当前部门值
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2012-12-25 下午2:50:57
	 * @return
	 * @version V1.0
	 */
	private String getCurrentOrgCode() {
		//获取当前登陆部门的详细数据
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		//返回当前登陆部门编码
		return currentDept.getCode();
	}
	
	/**
	 * 
	 * @Description: 获得当前人
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2012-12-25 下午2:50:57
	 * @return
	 * @version V1.0
	 */
	private String getCurrentUserCode() {
		//获取当前登陆人
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		//获取当前登陆人编码
		return currentUser.getEmployee().getEmpCode();
	}
	
	/**
	 * @功能 中止/激活偏线价格方案
	 * @author 105888-foss-zhangxingwang
	 */
	@Transactional
	@Override
	public void updateActiveToN(String outerEffectivePlanId) {
		if(StringUtils.isEmpty(outerEffectivePlanId)){
			throw new BusinessException("传入参数有误，请重试");
		}		
		//中止当前记录
		outerEffectivePlanDao.updateActiveToYesOrNo(outerEffectivePlanId,FossConstants.NO);
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
	public void updateOuterEffectivePlanActiveById(List<OuterEffectivePlanDto> outerEffectivePlanDtoList, String yesOrNo) {
		//待激活方案数据
		List<String> ids = new ArrayList<String>();
		String temp = null;
		//在这里进行两步更新，先逐条更新里面的数据，再根据主键全部更新，如果失败，进行事务回滚
		OuterEffectivePlanEntity record = new OuterEffectivePlanEntity();
		
		//一、先逐条修改数据
		for (OuterEffectivePlanDto dto : outerEffectivePlanDtoList) {
			if (dto == null) {
				continue;
			}
			temp = dto.getOuterEffectivePlanId();
			ids.add(temp);
			record.setId(temp);
			//设置已存在相同激活方案的截止时间为新激活方案的生效时间的前一秒
			record.setEndTime(DateUtils.getDateAdd(dto.getBeginTime(), -NumberConstants.NUMBER_1000));
			record.setPartialLineCode(dto.getPartialLineCode());
			record.setOutFieldCode(dto.getOutFieldCode());//getOutFieldCode()
			record.setProductCode(dto.getProductCode());
			record.setActive(FossConstants.ACTIVE);
			
			if (outerEffectivePlanDao.updateOuterEffectivePlanEndTime(record) < 0) {
				throw new BusinessException("修改已激活方案（上个版本）的数据失败，请联系技术支持人员！");
			}
		}
		
		//二、由于Oracle数据库的缺陷，大于1000进行更新会出现报错的情况，在这里需要避免
		if (ids != null && ids.size() >= 0) {
			if (ids.size() <= NumberConstants.NUMBER_1000) {
				if (outerEffectivePlanDao.updateOuterEffectivePlanActiveById(ids, yesOrNo) <= 0) {
					throw new BusinessException("操作失败，请联系技术支持人员！");
				}
			} else {
				List<List<String>> idsLists = CollectionUtils.splitListBySize(ids, FossConstants.ORACLE_MAX_IN_SIZE);
				for (List<String> lists : idsLists) {
					if (outerEffectivePlanDao.updateOuterEffectivePlanActiveById(lists, yesOrNo) <= 0) {
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
	public void immediatelyActiveOuterEffectivePlan(String outerEffectivePlanId,
			String yesOrNo, Date effectiveTime) {
		//判断参数是否有误
		if(StringUtils.isEmpty(outerEffectivePlanId)){
			throw new BusinessException("传入参数有误，ID不能为空！");
		}
		//中止或者激活方案
		OuterEffectivePlanEntity opt = new OuterEffectivePlanEntity();
		opt.setActive(yesOrNo);
		//进行激活方案的操作
		if (FossConstants.ACTIVE.equals(yesOrNo)) {
			//激活方案
			OuterEffectivePlanEntity ope = outerEffectivePlanDao.selectByPrimaryKey(outerEffectivePlanId);
			if (ope == null) {
				throw new BusinessException("传入参数有误，ID在系统中不存在！" + outerEffectivePlanId);
			}
			//设定主键
			ope.setId(outerEffectivePlanId);
			//设置已存在相同激活方案的截止时间为新激活方案的生效时间的前一秒
			ope.setEndTime(DateUtils.getDateAdd(effectiveTime, -NumberConstants.NUMBER_1000));
			ope.setActive(FossConstants.ACTIVE);
			//后台激活方案
			if (outerEffectivePlanDao.updateOuterEffectivePlanEndTime(ope) < 0) {
				throw new BusinessException("修改已激活方案（上个版本）的数据失败，请联系技术支持人员！");
			}
			opt.setBeginTime(effectiveTime);
		//中止当前方案的 操作
		} else {
			//中止方案
			opt.setEndTime(effectiveTime);
		}
		//进行后台数据的更新
		opt.setId(outerEffectivePlanId);
		if (outerEffectivePlanDao.updateByPrimaryKeySelective(opt) <= 0) {
			throw new BusinessException("操作失败，请联系技术支持人员！");
		}
	}
	
	/**
	 * 立即中止
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-26 09:47:34
	 */
	@Override
	@Transactional
	public void immediatelyStopOuterEffectivePlan(String outerEffectivePlanId,
			Date effectiveTime){
		//进行参数的判断
		if(StringUtil.isBlank(outerEffectivePlanId)){
			throw new BusinessException("记录id为空,请联系管理员");
		}
		//进行参数的判断
		if(effectiveTime == null){
			throw new BusinessException("中止时间为空,请联系管理员");
		}
		//进行参数的判断
		if(effectiveTime.before(new Date())){
			throw new BusinessException("中止时间不能早于当前时间");
		}
		// 根据id查询完整记录
		OuterEffectivePlanEntity effectivePlan = outerEffectivePlanDao.selectByPrimaryKey(outerEffectivePlanId);
		if(effectivePlan == null){
			LOGGER.error("\nID:" + outerEffectivePlanId + "\n查询不到记录");
			throw new BusinessException("查询不到记录，请联系管理员");
		}
		// 根据id查询完整记录
		if(!(FossConstants.YES.equals(effectivePlan.getActive()))){
			LOGGER.error("\nID:" + outerEffectivePlanId + "\nactive:" + effectivePlan.getActive() + "\n不能激活未激活记录");
			throw new BusinessException("不能中止未激活记录");
		}
		// 根据id查询完整记录
		if(effectiveTime.before(effectivePlan.getBeginTime())){
			throw new BusinessException("中止时间不能早于开始时间");
		}
		// 根据id查询完整记录
		if(effectiveTime.after(effectivePlan.getEndTime())){
			throw new BusinessException("中止时间不能晚于原有中止时间");
		}
		
		//封装参数
		OuterEffectivePlanEntity opt = new OuterEffectivePlanEntity();
		//ID
		opt.setId(outerEffectivePlanId);
		//结束时间
		opt.setEndTime(effectiveTime);
		//修改人
		opt.setModifyUserCode(getCurrentUserCode());
		//修改人组织
		opt.setModifyOrgCode(getCurrentOrgCode());
		//修改时间
		Date date = new Date();
		opt.setModifyTime(date);
		//修改的版本号
		opt.setVersionNo(date.getTime());
		//进行后台数据库的更新
		if (outerEffectivePlanDao.updateByPrimaryKeySelective(opt) <= 0) {
			throw new BusinessException("操作失败，请联系技术支持人员！");
		}		
	}
	
	/**
	 * 新增数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 09:55:31
	 * 
	 */
	@Override
	public OuterEffectiveVo addOuterEffectivePlan(OuterEffectivePlanEntity outerEffectivePlanEntity) {
		//判断方案名称是否被使用
		if(outerEffectivePlanEntity != null && StringUtil.isNotBlank(outerEffectivePlanEntity.getName())) {
			List<OuterEffectivePlanEntity> effectivePlanEntities = outerEffectivePlanDao.queryOuterEffectivePlanByName(outerEffectivePlanEntity.getName());
			if(CollectionUtils.isNotEmpty(effectivePlanEntities)) {
				throw new BusinessException("该方案名称已经存在，不能再次使用！");
			}
			
			//偏线价格方案NAME、有效期、方案描述，来自前端
			outerEffectivePlanEntity.setId(UUIDUtils.getUUID());
			//当前登陆人的信息
			String temp = getCurrentUserCode();
			outerEffectivePlanEntity.setCreateUserCode(temp);
			outerEffectivePlanEntity.setModifyUserCode(temp);
			temp = getCurrentOrgCode();
			outerEffectivePlanEntity.setCreateOrgCode(temp);
			outerEffectivePlanEntity.setModifyOrgCode(temp);
			Date date = new Date();
			outerEffectivePlanEntity.setCreateTime(date);
			outerEffectivePlanEntity.setModifyTime(date);
			outerEffectivePlanEntity.setVersionNo(date.getTime());
			date = DateUtils.convert("2999-12-31 23:59:59", DateUtils.DATE_TIME_FORMAT);
			outerEffectivePlanEntity.setEndTime(date);
			outerEffectivePlanEntity.setActive(FossConstants.INACTIVE);
			//进行后台数据的新增
			if (outerEffectivePlanDao.insertSelective(outerEffectivePlanEntity) <= 0) {
				throw new BusinessException("操作失败，请联系技术支持人员！");
			}
		}
		OuterEffectiveVo vo = new OuterEffectiveVo();
		vo.setOuterEffectivePlanEntity(outerEffectivePlanEntity);
		return vo;
	}
	
	/**
	 * <p>修改偏线价格方案</p> 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-26 10:28:11
	 * @param marketingEventEntity
	 * @see
	 */
	@Transactional
	public void updateOuterEffectivePlan(OuterEffectivePlanEntity record) {
		//进行前台参数的判断
		if(record != null && StringUtils.isNotBlank(record.getId())) {
			StringBuffer tempBuffer = new StringBuffer();
			//目的站
			if (StringUtils.isEmpty(record.getPartialLineCode())) {
				tempBuffer.append("目的站 ");
			}
			//产品性质
			if (StringUtils.isEmpty(record.getProductCode())) {
				tempBuffer.append(" 运输类型 ");
			}
			//外发外场
			if (StringUtils.isEmpty(record.getOutFieldCode())) {
				tempBuffer.append(" 外发外场 ");
			}
			//抛出异常
			if (tempBuffer != null && StringUtils.isNotEmpty(tempBuffer.toString())) {
				throw new BusinessException(tempBuffer.toString() + " 不能为空！");
			}
			//判断是否名称被使用过
			int nameDuplicationNum = outerEffectivePlanDao.checkIsExistName(record);
			//如果后台数据大于1
			if(nameDuplicationNum > 1){
				throw new BusinessException("该方案名称已经存在，不能再次使用 ！");
			}
			
			//进行后台数据的更新
			//修改人编码
			record.setModifyUserCode(getCurrentUserCode());
			//修改人组织
			record.setModifyOrgCode(getCurrentOrgCode());
			//修改时间
			record.setModifyTime(new Date());
			//版本号
			record.setVersionNo(record.getModifyTime().getTime());
			//后台数据的更新
			if (outerEffectivePlanDao.updateByPrimaryKeySelective(record) <= 0) {
				throw new BusinessException("操作失败，请联系技术支持人员！");
			}
		}
	}
	

	
	/**
	 * <p>复制偏线价格方案</p> 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-26 10:35:29
	 * @param outerEffectivePlanId
	 * @param copyName
	 * @see
	 */
	public OuterEffectiveVo copyOuterEffectivePlan(String outerEffectivePlanId, String copyName){
		//拷贝数据
		if(StringUtils.isEmpty(outerEffectivePlanId)){
			throw new BusinessException("对不起，传入参数为空！");
		}
		//查询需要复制的数据
		OuterEffectivePlanEntity record = new OuterEffectivePlanEntity();
		record = outerEffectivePlanDao.selectByPrimaryKey(outerEffectivePlanId);
		//ID
		record.setId(UUIDUtils.getUUID());
		//设置版本号
		Date nowDate = new Date();
		record.setVersionNo(nowDate.getTime());
		//结束时间
		String temp = DateUtils.convert(nowDate, "yyyyMMddHHmmss");
		//复制的版本名称+时间戳
		if (copyName.length() > NumberConstants.NUMBER_30) {
			copyName = copyName.substring(0, NumberConstants.NUMBER_15) + temp;
		} else {
			copyName = copyName + temp;
		}
		//设置版本名称
		record.setName(copyName);
		//设置是否激活状态
		record.setActive(FossConstants.NO);
		//拷贝后台数据
		if (outerEffectivePlanDao.copyOuterEffectivePlan(record) <= 0) {
			throw new BusinessException("复制失败，请联系技术支持人员！");
		}
		//设置 行的主键与名称		
		OuterEffectiveVo opv = new OuterEffectiveVo();
		//主键
		opv.setOuterEffectivePlanId(record.getId());
		//复制的版本号的名称
		opv.setCopyName(copyName);
		return opv;
	}
	
	/**
	 * <p>删除偏线价格方案</p> 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 10:38:45
	 * @param outerEffectivePlanIds
	 * @see
	 */
	public void deleteByPrimaryKey(List<String> outerEffectivePlanIds) {
		//进行后台数据的删除
		if (outerEffectivePlanIds != null && outerEffectivePlanIds.size() >= 0) {
			if (outerEffectivePlanIds.size() <= NumberConstants.NUMBER_1000) {
				if (outerEffectivePlanDao.deleteByPrimaryKey(outerEffectivePlanIds) <= 0) {
					throw new BusinessException("删除失败，请联系技术支持人员！");
				}
			} else {
				List<List<String>> idsLists = CollectionUtils.splitListBySize(outerEffectivePlanIds, FossConstants.ORACLE_MAX_IN_SIZE);
				for (List<String> lists : idsLists) {
					if (outerEffectivePlanDao.deleteByPrimaryKey(lists) <= 0) {
						throw new BusinessException("删除失败，请联系技术支持人员！");
					}
				}
			}
		} else {
			throw new BusinessException("传入参数有误，请重试");
		}
	}

	/**
	 * <p>
	 * Description:根据主键查询记录<br />
	 * </p>
	 * @author Foss-105888-Zhangxingwang
	 * @version 2014-3-26 10:39:09
	 * @param id
	 * @return
	 * OuterPriceVo
	 */
	public OuterEffectiveVo selectByPrimaryKey(String outerEffectivePlanId) {
		if(StringUtil.isEmpty(outerEffectivePlanId)){
			throw new BusinessException("选择的主方案信息缺失!,请联系运维人员查询原因。");
	    }
		OuterEffectivePlanEntity ope = outerEffectivePlanDao.selectByPrimaryKey(outerEffectivePlanId);
		//省份
    	String temp = areaAddressService.queryRegionByCode(ope.getProvCode()).getName();
    	ope.setProvName(temp);
    	//城市
    	temp = areaAddressService.queryRegionByCode(ope.getCityCode()).getName();
    	ope.setCityName(temp);
    	//区县
    	temp = areaAddressService.queryRegionByCode(ope.getCountyCode()).getName();
    	ope.setCountyName(temp);
    	//返回前台参数
		OuterEffectiveVo opv = new OuterEffectiveVo();
		opv.setOuterEffectivePlanEntity(ope);
		return opv;
	}

	/**
	 * <p>
	 * Description:导出偏线时效方案<br />
	 * </p>
	 * @author Foss-105888-Zhangxingwang
	 * @version 2014-3-26 10:39:09
	 * @param id
	 * @return
	 * int
	 */
	public ExportResource exportOuterEffectivePlan(OuterEffectivePlanConditionDto outerEffectivePlanConditionDto){
		//进行数据的导出
		List<OuterEffectivePlanDto> oppDtos = outerEffectivePlanDao.queryOuterPriceVoBatchInfo(outerEffectivePlanConditionDto, 0, 0);
		if(CollectionUtils.isEmpty(oppDtos)){
		    return null;
		}
		//导出工具
		ExportResource exportResource = new ExportResource();
		
		List<List<String>> rowList = new ArrayList<List<String>>();
		String temp = null;
    	Date nowDate = new Date();
    	//进行后台封装的数据
		for (OuterEffectivePlanDto opp : oppDtos) {
		     rowList.add(exportPlatform(opp, temp, nowDate));
		}
		//设置Excel头名称
		exportResource.setHeads(PricingColumnConstants.OUTER_EFFECTIVE_PLAN_TITLE);
		//设置列所有数据
		exportResource.setRowList(rowList);
		return exportResource;
	}
	

    
    /**
     * <p>填充方案 sheet row </p>.
     * @param pricePlanEntity the price plan entity
     * @return the list
     * @author Foss-105888-Zhangxingwang
     * @date 2014-3-26 10:03:20
     * @see
     */
    private List<String> exportPlatform(OuterEffectivePlanDto dto, String temp, Date nowDate){
		List<String> result = new ArrayList<String>();	

		//{"方案名称","目的站","外发外场","运输类型","数据状态","生效日期","截止日期","是否当前版本","省份","城市","区县","到达偏线承诺时间","承诺派送时间","派送增加天数","修改时间","修改人"}
		//方案名称
		result.add(dto.getName());
		//目的站
		result.add(dto.getPartialLineName());
		//外发外场
		result.add(dto.getOutFieldName());
		//产品类型
		if (WaybillConstants.TRANS_VEHICLE.equals(dto.getProductCode())) {
			result.add("汽运");//运输类型
		} else {
			result.add("空运");
		}
		//是否激活状态
		if(FossConstants.ACTIVE.equals(dto.getActive())){
		    result.add("已激活");//数据状态
		}else{
		    result.add("未激活");
		}

		//方案开始时间
		result.add(DateUtils.convert(dto.getBeginTime(), DateUtils.DATE_TIME_FORMAT));//生效时间
		//方案结束时间
		result.add(DateUtils.convert(dto.getEndTime(), DateUtils.DATE_TIME_FORMAT));//截止时间
		if (FossConstants.ACTIVE.equals(dto.getActive()) 
    			&& nowDate.after(dto.getBeginTime()) && nowDate.before(dto.getEndTime())) {
    		result.add("是");
		} else {
    		result.add("否");
		}
		//省份
		result.add(dto.getProvName());
		//城市
		result.add(dto.getCityName());
		//区县
		result.add(dto.getCountyName());
		if(PricingConstants.TIME_DAY.equals(dto.getMaxTimeUnit())){
			//最大时间
			result.add(String.valueOf(dto.getMaxTime())+"天");
		}else if(PricingConstants.TIME_HOURS.equals(dto.getMaxTimeUnit())){
			//最大时间
			result.add(String.valueOf(dto.getMaxTime())+"时");
		}else{
			//最大时间
			result.add(String.valueOf(dto.getMaxTime())+dto.getMaxTimeUnit());
		}
		if(PricingConstants.TIME_DAY.equals(dto.getMinTimeUnit())){
			//最大时间
			result.add(String.valueOf(dto.getMinTime())+"天");
		}else if(PricingConstants.TIME_HOURS.equals(dto.getMinTimeUnit())){
			//最大时间
			result.add(String.valueOf(dto.getMinTime())+"时");
		}else{
			//最大时间
			result.add(String.valueOf(dto.getMinTime())+dto.getMinTimeUnit());
		}
		//转换参数
		changeDate(dto);
		//到达偏线承诺时间
		result.add(dto.getArriveOuterBranchTime());
		//派送增加天数
		result.add(dto.getAddDay());
		//承诺派送时间
		result.add(dto.getDeliveryTime());
		//修改日期
		result.add(DateUtils.convert(dto.getModifyDate(), DateUtils.DATE_TIME_FORMAT));
		//修改人
		result.add(dto.getModifyUser());
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
	public OuterEffectiveVo selectById(String outerEffectivePlanId) {
		if(StringUtil.isEmpty(outerEffectivePlanId)){
			throw new BusinessException("选择的主方案信息缺失!,请联系运维人员查询原因。");
	    }
		//根据主键查询数据
		OuterEffectivePlanDto opp = outerEffectivePlanDao.selectById(outerEffectivePlanId);
    	OuterEffectiveVo opv = new OuterEffectiveVo();
		opv.setOuterEffectivePlanDto(opp);
		return opv;
	}


	/**
	 * 
	 * @Description: 导入时效方案 Company:IBM
	 * @author Rosanu
	 * @date 2012-12-24 下午2:04:0
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public void importOuterEffectivePlan(Workbook book){
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
					OuterEffectivePlanEntity ope = new OuterEffectivePlanEntity();
					String temp = null;
					col = 0;
					SimpleDateFormat sdf1 = new SimpleDateFormat("HHmm");
					for (Iterator cells = row.cellIterator(); cells.hasNext();) {
						Cell cell = (Cell)cells.next();
						//{序号,方案名称,目的站,外发外场	运输类型,承诺最小天数或时间,承诺最大天数或时间,到达代理网点承诺时点,派送承诺需加天数,派送承诺时点,生效时间,截止时间}
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
									List<OuterEffectivePlanEntity> outerPriceEntities = outerEffectivePlanDao.queryOuterEffectivePlanByName(temp.trim());
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
								LOGGER.info("目的站>>" + temp);
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
								// 外发外场
								col ++;
								temp = ExcelHandleUtil.obtainStringVal(cell);
								LOGGER.info("外发外场>>"+temp);
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
								
							case FOUR:
								// 运输类型
								col ++;
								temp = ExcelHandleUtil.obtainStringVal(cell);
								LOGGER.info("运输类型>>"+temp);
                                if(StringUtils.isNotBlank(temp)) {
                                	//默认为汽运
                                	if("空运".equals(temp.trim())) {
                                    	ope.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002);
                                    } else {
                                    	//汽运
                                    	ope.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
    								}
                                } else {
                                	//汽运
                                	ope.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
								}
                                
								break;
							case FIVE:
								// 承诺最小天数
								col ++;
								String promiseMin = ExcelHandleUtil.obtainStringVal(cell);
								LOGGER.info("承诺最小天数>>"+promiseMin);
                                if(StringUtils.isNotBlank(promiseMin)) {
                                	ope.setMinTime(Double.valueOf(promiseMin).intValue());
                                	ope.setMinTimeUnit(PricingConstants.TIME_DAY);
                                } else {
									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写承诺最小天数", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
                                }
								break;
							case SIX:
								// 承诺最大天数
								col ++;
								String promiseMax = ExcelHandleUtil.obtainStringVal(cell);
								LOGGER.info("承诺最大天数或小时>>"+promiseMax);
                                if(StringUtils.isNotBlank(promiseMax)) {
                                	ope.setMaxTime(Double.valueOf(promiseMax).intValue());
                                	ope.setMaxTimeUnit(PricingConstants.TIME_DAY);
                                } else {
									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写承诺最大天数", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
                                }
								break;
							case SEVEN:
								// 到达营业部承诺时间
								col ++;
								String arrvDeptTime = ExcelHandleUtil.obtainStringVal(cell);
								LOGGER.info("到达偏线承诺时间>>"+arrvDeptTime);
                                if(StringUtils.isNotBlank(arrvDeptTime)) {
                                	if(arrvDeptTime.indexOf(":") > -1) {
                                		String tempStr = arrvDeptTime.replaceAll(":", "");
                                		ope.setArriveOuterBranchTime(tempStr);
                                	} else {
                                		Date d = null;
                                		d = sdf1.parse(arrvDeptTime);
                                		ope.setArriveOuterBranchTime(sdf1.format(d));
                                	}
                                }
								break;
							case EIGHT:
								// 承诺派送需加天数
								col ++;
								String addDay = ExcelHandleUtil.obtainStringVal(cell);
								LOGGER.info("承诺派送需加天数>>"+addDay);
                                if(StringUtils.isNotBlank(addDay)) {
                                	ope.setAddDay(Double.valueOf(addDay).intValue());
                                } else {
									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写派送承诺需加天数", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
                                }
								break;
							case NINE:
								// 派送承诺时间
								col ++;
								String deleveryTime = ExcelHandleUtil.obtainStringVal(cell);
								LOGGER.info("派送承诺时间>>"+deleveryTime);
                                if(StringUtils.isNotBlank(deleveryTime)) {
                                	if(deleveryTime.indexOf(":") > -1) {
                                		String tempStr = deleveryTime.replaceAll(":", "");
                                		ope.setDeliveryTime(tempStr);
                                	} else {
                                    	Date d = null;
                                		d = sdf1.parse(deleveryTime);
                                		ope.setDeliveryTime(sdf1.format(d));
                                	}
                                }
								break;
							case TEN:
								// 生效日期
								col ++;
								temp = ExcelHandleUtil.obtainStringVal(cell);
								LOGGER.info("生效日期>>"+temp);
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
							case ELEVEN:
								// 截止日期
								col ++;
								temp = ExcelHandleUtil.obtainStringVal(cell);
								LOGGER.info("截止日期>>"+temp);
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
								LOGGER.info("第"+index+"行，第"+col+"列数据超出范围！");
						}
					}
					//设定主键
					temp = UUIDUtils.getUUID();
					ope.setId(temp);
					//获取当前登陆I人编码
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
					
					if (outerEffectivePlanDao.insertSelective(ope) <= 0) {
						throw new BusinessException("操作失败，请联系技术支持人员！");
					}
					index ++;
				}
			}
		} catch (Exception e) {
			throw new BusinessException("第"+index+"行数据出现异常情况。" + e.getMessage());
		}
	}
	
	/**
	 * <p>根据外发外场编码、偏线代理网点、时效时间查询偏线时效</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 09:23:34
	 */
	@Override
	public OuterEffectivePlanEntity queryOuterEffectPlanByFieldAndBranch(String outFieldCode, String outerBranchCode, String productCode, Date billDate) {
		return outerEffectivePlanDao.queryOuterEffectPlanByFieldAndBranch(outFieldCode, outerBranchCode, productCode, billDate);
	}
	
	/**
	 * 偏线时效Dao服务类
	 */
	public void setOuterEffectivePlanDao(
			IOuterEffectivePlanDao outerEffectivePlanDao) {
		this.outerEffectivePlanDao = outerEffectivePlanDao;
	}
	/**
	 * 区域服务类
	 */
	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}

	/**
	 * 外场服务类
	 */
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	/**
	 * 偏线代理网点服务类
	 */
	public void setVehicleAgencyDeptService(IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}
}
