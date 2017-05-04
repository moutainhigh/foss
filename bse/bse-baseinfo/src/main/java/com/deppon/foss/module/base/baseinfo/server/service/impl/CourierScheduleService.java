/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICourierScheduleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICourierScheduleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleReportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CourierScheduleExcelDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CourierScheduleException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;

/**
 *<p>Title: CourierScheduleService</p>
 * <p>Description: 实现快递员排班信息的service</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-4-17
 */
public class CourierScheduleService implements ICourierScheduleService {
	//操作类型常量 修改，还是新增
	private static final String OP_TYPE_ADD ="ADD"; 
	//private static final String OP_TYPE_UPDATE ="UPDATE";
	/**
	 * 快递员排班dao
	 */
	private ICourierScheduleDao courierScheduleDao;
	/**
	 * 人员接口
	 */
	private IEmployeeService employeeService;
	/**
	 * 组织接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 快递收派小区
	 */
	/*private IExpressDeliverySmallZoneService expressDeliverySmallZoneService;
	
	public void setExpressDeliverySmallZoneService(
			IExpressDeliverySmallZoneService expressDeliverySmallZoneService) {
		this.expressDeliverySmallZoneService = expressDeliverySmallZoneService;
	}*/
	
	
	/**
	 * @param courierScheduleDao the courierScheduleDao to set
	 */
	public void setCourierScheduleDao(ICourierScheduleDao courierScheduleDao) {
		this.courierScheduleDao = courierScheduleDao;
	}
	/**
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 *<p>Title: addCourierSchedule</p>
	 *<p>新增信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午4:18:39
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public CourierScheduleEntity addCourierSchedule(CourierScheduleEntity entity) {
		//非空校验
		if(null ==entity){
			throw new CourierScheduleException("对象为空");
		}
		/*if(StringUtils.isBlank(entity.getCourierCode())&&null ==entity.getSchedulingDate()){
			throw new CourierScheduleException("快递员、或排班日期为空");
		}*/
		//设置用户的快递点部
		EmployeeEntity employeeResult=employeeService.querySimpleEmployeeByEmpCode(entity.getCourierCode());
		//若查询到的为非空,
		if(null !=employeeResult &&StringUtils.isNotBlank(employeeResult.getOrgCode())){
			entity.setExpressPartCode(employeeResult.getOrgCode());
		}else{
			throw new CourierScheduleException("快递员不存在或没有所属部门!");
		}
		//--130134 FOSS20150818版本取消楼层重复校验
		//判断工作类别是否为出车
		/*if(DictionaryValueConstants.PLAN_TYPE_WORK.equals(entity.getPlanType())){
			//楼层非零校验
			if(NumberConstants.ZERO!=entity.getStartFloor()||NumberConstants.ZERO!=entity.getEndFloor()){
				//校验始发楼层<结束楼层
				if(entity.getStartFloor()>entity.getEndFloor()){
					throw new CourierScheduleException("起始楼层不能大于结束楼层");
				}
				//若快递员属性为定区的话，
				if(DictionaryValueConstants.COURIER_NATURE_FIXED.equals(entity.getCourierNature())){
					//校验楼层不能重叠
					boolean flag =isValidFloor(entity,OP_TYPE_ADD);
					if(flag){
						throw new CourierScheduleException("楼层存在重叠,不能做新增操作");
					}
				}
			}
		}*/
		 
		//一个快递员每天只能有一个排班任务进行验证
		/*CourierScheduleEntity courierEntity=new CourierScheduleEntity();
		courierEntity.setStartTime(DateUtils.getStartDatetime(entity.getStartTime()));
		courierEntity.setEndTime(DateUtils.getStartDatetime(entity.getEndTime()));
		courierEntity.setCourierCode(entity.getCourierCode());
		List<CourierScheduleEntity> courierEntitys=
				courierScheduleDao.queryCourierScheduleList(entity, NumberConstants.ZERO, Integer.MAX_VALUE);
		if(CollectionUtils.isEmpty(courierEntitys)){
			throw new CourierScheduleException("当前快递员在该时间段已经存在排班！");
		}*/
		
		//起始和结束时间不能为空
		if(null==entity.getStartTime()||null==entity.getEndTime()){
			throw new CourierScheduleException("排班时间不能为空！");
		}
		
		
		//属性验证
		courierNatureValid(entity);
		//起始时间
		String startTime=DateUtils.convert(entity.getStartTime(), DateUtils.DATE_TIME_FORMAT);

		//结束时间
		String endTime=DateUtils.convert(entity.getEndTime(), DateUtils.DATE_TIME_FORMAT);

		
		//起始月份
		String startMonth=startTime.substring(NumberConstants.ZERO,NumberConstants.NUMBER_7);
		//到达月份
		String endMonth=endTime.substring(NumberConstants.ZERO,NumberConstants.NUMBER_7);
		//
		Date compareStartTime=DateUtils.getStartDatetime(entity.getStartTime());
		Date currentEndTime=DateUtils.getStartDatetime(new Date());
		
		if(compareStartTime.compareTo(currentEndTime)<=0){
			throw new CourierScheduleException("起始排班日期必须大于当前时间！");
		}
		//起始排班日期必须大于当前日期
		if(!StringUtils.equals(startTime, endTime)){
			if(!entity.getEndTime().after(entity.getStartTime())){
				throw new CourierScheduleException("排班结束日期必须大于排班起始日期！");
			}
		}
		//起始排班日期与结束排班日期必须为同一月份
		if(!StringUtils.equals(startMonth, endMonth)){
			throw new CourierScheduleException("起始排班日期与结束排班日期必须为同一月份！");
		}
		//当前月
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(new Date());
		int currentMonth = currentCalendar.get(Calendar.MONTH);
		//开始时间月份
		//当前月
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(entity.getStartTime());
		int startMont = startCalendar.get(Calendar.MONTH);
		//排班不能跨三个月，只能对当月以及下月进行排班
		 if(startMont-currentMonth>=2){
			 throw new CourierScheduleException("最多只能对下个月进行排班！"); 
		 }
		 
		//快递收派小区个数统计--经过讨论无需对小区与快递员规则进行校验
		/*boolean flag=expressDeliverySmallZoneService.countSmallZoneEntityByCode(entity.getExpressSmallZoneCode());		
		
		if(!flag){
			throw new CourierScheduleException("当前收派小区数量不足，请新增收派小区个数！");
		}*/
		//计算总共天数
		Integer startDay=Integer.parseInt(startTime.substring(NumberConstants.NUMBER_8,NumberConstants.NUMBER_10));
		//结束日期
		Integer endDay=Integer.parseInt(endTime.substring(NumberConstants.NUMBER_8,NumberConstants.NUMBER_10));
//		entity.setSchedulingDate(entity.getStartTime());
		int j=0;
		for(int i=endDay-startDay+1;i>0;i--){
			
			entity.setSchedulingDate(DateUtils.addDayToDate(entity.getStartTime(), j));
			//设置日期天数
			Calendar c=Calendar.getInstance();
			c.setTime(entity.getSchedulingDate());
			entity.setDateNum(c.get(Calendar.DAY_OF_MONTH));
			//设置月份
			entity.setYearMonth(startMonth);
		    courierScheduleDao.addCourierSchedule(entity);
		    j++;
		}
		
		
//		//解析日期
//		String schedulingDate=DateUtils.convert(entity.getSchedulingDate(), DateUtils.DATE_TIME_FORMAT);
//		//设置日期天数
//		entity.setDateNum(Integer.parseInt(schedulingDate.substring(NumberConstants.NUMBER_8,NumberConstants.NUMBER_10)));
//		//设置月份
//		entity.setYearMonth(schedulingDate.substring(NumberConstants.ZERO,NumberConstants.NUMBER_7));
		return null;
	}
	
	/**
	 *<p>Title: isValidFloor</p>
	 *<p>定区条件下校验数据库中是否存在重叠的楼层</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-18上午9:44:30
	 * @param entity
	 */
	private boolean isValidFloor(CourierScheduleEntity entity,String operateType) {
		if(null==entity||StringUtils.isBlank(entity.getExpressSmallZoneCode())){
			throw new CourierScheduleException("收派小区为空");
		}
		if(StringUtils.isBlank(operateType)){
			throw new CourierScheduleException("操作类型为空");
		}
		//判断该小区和该排排班、快递员属性 时间下数据库有无数据
		List<CourierScheduleEntity> resultList =courierScheduleDao.queryCourierScheduleListByEntity(entity);
		//是否非空
		if(CollectionUtils.isEmpty(resultList)){
			//为空，则返回false
			return false;
		}
		long resultNum =0;
		//若操作类型为新增时
		if(StringUtils.equals(operateType, OP_TYPE_ADD)){
			//根据小区，排班时间，快递员属性，楼层判断是否存在重叠的小区
			 resultNum =courierScheduleDao.queryCountByIFloor(entity);
		}else{
			//根据小区，排班时间，快递员属性，楼层判断是否存在重叠的小区（并且除了该实体）;
			resultNum =courierScheduleDao.queryCountByFloorNotIncludeEntity(entity);
		}
		//若查询数据>0；说明存在重叠
		if(resultNum > NumberConstants.ZERO){
			return true;
		}
		return false;
	}

	/**
	 *<p>Title: deleteCourierSchedule</p>
	 *<p>作废排班信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午4:18:39
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public int deleteCourierSchedule(
			CourierScheduleEntity entity) {
		// 非空校验
		if (null == entity ||StringUtils.isBlank(entity.getId())) {
			throw new CourierScheduleException("参数为空");
		}
		CourierScheduleEntity result= courierScheduleDao.deleteCourierSchedule(entity);
		return result==null?NumberConstants.ZERO:NumberConstants.NUMBER_1;
	}

	/**
	 *<p>Title: updateCourierSchedule</p>
	 *<p>修改快递员排班信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午4:18:39
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public CourierScheduleEntity updateCourierSchedule(
			CourierScheduleEntity entity) {
		// 非空校验
		if (null == entity ||StringUtils.isBlank(entity.getId())) {
			throw new CourierScheduleException("参数为空");
		}
		if (StringUtils.isBlank(entity.getCourierCode())
				&& null == entity.getSchedulingDate()) {
			throw new CourierScheduleException("快递员、或排班日期为空");
		}
		//查询库中是否存在该数据
		CourierScheduleEntity result =courierScheduleDao.queryCourierScheduleById(entity);
		if(null !=result){
			//若数据库中快递员编码不能与快递员编码，说明快递员做了修改
			if(
					/*!result.getCourierCode().equals(entity.getCourierCode())||*/
					!StringUtils.equals(result.getCourierNature(), entity.getCourierNature())||
					!result.getExpressSmallZoneCode().equals(entity.getExpressSmallZoneCode())){
				
				CourierScheduleEntity entity1=new CourierScheduleEntity();
				entity1.setStartTime(null);
				entity1.setEndTime(null);
//				entity1.setSchedulingDate(entity.getSchedulingDate());
				entity1.setExpressSmallZoneCode(entity.getExpressSmallZoneCode());
				entity1.setSchedulingDate(entity.getSchedulingDate());
//				entity1.setCourierNature(entity.getCourierNature());
				//SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
				//String schedulingTime = df.format(entity.getSchedulingDate());
		//	entity1.setSchedulingTime(entity.getSchedulingDate());
				//属性验证
				courierNatureUpdateValid(entity1,entity.getCourierCode(),entity.getCourierNature(),result.getExpressSmallZoneCode());
				//设置用户的快递点部
				EmployeeEntity employeeResult=employeeService.querySimpleEmployeeByEmpCode(entity.getCourierCode());
				
				if(null !=employeeResult && StringUtils.isNotBlank(employeeResult.getOrgCode())){
					entity.setExpressPartCode(employeeResult.getOrgCode());
				}
			}
		}	
		//--130134 FOSS20150818版本取消楼层重复校验
/*		// 判断工作类别是否为出车
		if (DictionaryValueConstants.PLAN_TYPE_WORK
				.equals(entity.getPlanType())) {
			// 楼层非零校验
			if (NumberConstants.ZERO == entity.getStartFloor()
					|| NumberConstants.ZERO == entity.getEndFloor()) {
				throw new CourierScheduleException("起始楼层、或结束楼层不能为零");
			}
			// 校验始发楼层<结束楼层
			if (entity.getStartFloor() > entity.getEndFloor()) {
				throw new CourierScheduleException("起始楼层不能大于结束楼层");
			}
			// 若快递员属性为定区的话，
			if (DictionaryValueConstants.COURIER_NATURE_FIXED.equals(entity
					.getCourierNature())) {
				// 校验楼层不能重叠
				boolean flag = isValidFloor(entity,OP_TYPE_UPDATE);
				if (flag) {
					throw new CourierScheduleException("楼层存在重叠,不能做修改操作");
				}
			}
		}*/
		
		return courierScheduleDao.updateCourierSchedule(entity);
	}
	/**
	 *<p>Title: addCourierScheduleMore</p>
	 *<p>批量插入</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25下午4:46:52
	 * @param excelDtos
	 * @param empCode
	*/
	@Override
	public int addCourierScheduleMore(List<CourierScheduleExcelDto> excelDtos,
			String empCode) {
		if(CollectionUtils.isEmpty(excelDtos)||StringUtils.isBlank(empCode)){
			throw new  CourierScheduleException("楼层存在重叠,不能做修改操作");
		}
		int count =0;
		//循环
		for (CourierScheduleExcelDto courierScheduleExcelDto : excelDtos) {
			int rowNum =courierScheduleExcelDto.getRowNum();
			CourierScheduleEntity entity =courierScheduleExcelDto.getScheduleEntity();
			//添加创建人
			entity.setCreateUser(empCode);
			//执行新增
			count+=addCourierSchedule(entity, rowNum);
		}
		return count == excelDtos.size()?count:NumberConstants.ZERO;
	}
	/**
	 *<p>Title: addCourierSchedule</p>
	 *<p>插入方法（用于批量导入排班使用）</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25下午5:01:25
	 * @param entity
	 * @param rowNum
	 * @return
	 */
	@Transactional
	@Override
	public int addCourierSchedule(CourierScheduleEntity entity,int rowNum){
		//非空校验
		if(null ==entity||rowNum<1){
			throw new CourierScheduleException("对象为空");
		}
		if(StringUtils.isBlank(entity.getCourierCode())&&null ==entity.getSchedulingDate()){
			throw new CourierScheduleException("快递员、或排班日期为空");
		}
		//判断工作类别是否为出车
		if(DictionaryValueConstants.PLAN_TYPE_WORK.equals(entity.getPlanType())){
			//楼层非零校验
			if(NumberConstants.ZERO==entity.getStartFloor()||NumberConstants.ZERO==entity.getEndFloor()){
				throw new CourierScheduleException("起始楼层、或结束楼层不能为零");
			}
			//校验始发楼层<结束楼层
			if(entity.getStartFloor()>=entity.getEndFloor()){
				throw new CourierScheduleException("起始楼层不能大于结束楼层");
			}
			//若快递员属性为定区的话，
			if(DictionaryValueConstants.COURIER_NATURE_FIXED.equals(entity.getCourierNature())){
				//校验楼层不能重叠
				boolean flag =isValidFloor(entity,OP_TYPE_ADD);
				if(flag){
					throw new CourierScheduleException("第"+rowNum+"行记录,楼层存在重叠,无法做新增操作！" +
							"并且已经插入前"+(rowNum-1)+"行记录");
				}
			}
		}
		//解析日期
		String schedulingDate=DateUtils.convert(entity.getSchedulingDate(), DateUtils.DATE_TIME_FORMAT);
		//设置日期天数
		entity.setDateNum(Integer.parseInt(schedulingDate.substring(NumberConstants.NUMBER_8,NumberConstants.NUMBER_10)));
		//设置月份
		entity.setYearMonth(schedulingDate.substring(NumberConstants.ZERO,NumberConstants.NUMBER_7));	
		CourierScheduleEntity result =courierScheduleDao.addCourierSchedule(entity);
		return result == null?NumberConstants.ZERO:NumberConstants.NUMBER_1;
	}
	
	/**
	 *<p>Title: queryCourierScheduleList</p>
	 *<p>分页查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午4:18:39
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<CourierScheduleEntity> queryCourierScheduleList(
			CourierScheduleEntity entity, int start, int limit) {
		if(null==entity){
			entity =new CourierScheduleEntity();
		}
		List<CourierScheduleEntity> resultList =courierScheduleDao.queryCourierScheduleList(entity, start, limit);
		return attachName(resultList);
	}
	/**
	 *<p>Title: deleteCourierScheduleMore</p>
	 *<p>批量作废</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-28上午8:41:59
	 * @param ids
	 * @param modifyUser
	 * @return
	*/
	@Override
	public int deleteCourierScheduleMore(List<String> ids, String modifyUser) {
		//非空校验
		if(CollectionUtils.isEmpty(ids)||StringUtils.isBlank(modifyUser)){
			throw new CourierScheduleException("参数为空");
		}
		int count =0;
		for (String id : ids) {
			CourierScheduleEntity entity =new CourierScheduleEntity();
			entity.setId(id);
			entity.setModifyUser(modifyUser);
			//执行作废
			count +=this.deleteCourierSchedule(entity);
		}
		return count==ids.size()?NumberConstants.NUMBER_1:NumberConstants.ZERO;
	}
	/**
	 *<p>Title: queryCount</p>
	 *<p>分页查询总数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午4:18:39
	 * @param entity
	 * @return
	 */
	@Override
	public long queryCount(CourierScheduleEntity entity) {
		if(null==entity){
			entity =new CourierScheduleEntity();
		}
		return courierScheduleDao.queryCount(entity);
	}
	/**
	 *<p>Title: queryCourierScheduleListByOther</p>
	 *<p>根据条件查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-24下午6:43:33
	 * @param entity
	 * @return
	*/
	@Override
	public List<CourierScheduleEntity> queryCourierScheduleListByOther(
			CourierScheduleEntity entity) {
		if(null==entity){
			entity =new CourierScheduleEntity();
		}
		List<CourierScheduleEntity> resultList =courierScheduleDao.queryCourierScheduleListByOther(entity);
		return attachName(resultList);
	}
	
	/**
	 * ******以下为工具方法************
	 */
	/**
	 *<p>Title: attachName</p>
	 *<p>工具方法：给集合添加上快递员，部门名字</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25上午9:35:07
	 * @param resultList
	 * @return
	 */
	private List<CourierScheduleEntity> attachName(
			List<CourierScheduleEntity> resultList) {
		//若集合为空
		if(CollectionUtils.isEmpty(resultList)){
			return null;
		}
		for (CourierScheduleEntity courierScheduleEntity : resultList) {
			attachName(courierScheduleEntity);
		}
		return resultList;
	}
	/**
	 *<p>Title: attachName</p>
	 *<p>工具方法：给排班实体添加上快递员，部门名字</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25上午9:39:20
	 * @param courierScheduleEntity
	 */
	private CourierScheduleEntity attachName(CourierScheduleEntity courierScheduleEntity) {
		if(null==courierScheduleEntity){
			return null;
		}
		//设置快递员名字
		if(StringUtils.isNotBlank(courierScheduleEntity.getCourierCode())){
			String name =employeeService.queryEmpNameByEmpCode(courierScheduleEntity.getCourierCode());
			courierScheduleEntity.setCourierName(name);
		}
		//设置快递点部名称
		if(StringUtils.isNotBlank(courierScheduleEntity.getExpressPartCode())){
			String name =orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(courierScheduleEntity.getExpressPartCode());
			courierScheduleEntity.setExpressPartName(name);
		}
		return courierScheduleEntity;
	}
	/**
	 *<p>Title: queryCourierScheduleReportList</p>
	 *<p>根据报表式查询排班信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-28下午7:11:06
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	*/
	@Override
	public List<CourierScheduleReportEntity> queryCourierScheduleReportList(
			CourierScheduleEntity entity, int start, int limit) {
		if(null==entity){
			entity =new CourierScheduleEntity();
		}
		return courierScheduleDao.queryCourierScheduleReportList(entity,start,limit);
	}
	/**
	 *<p>Title: queryReportListCount</p>
	 *<p></p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-29下午1:33:26
	 * @param entity
	 * @return
	*/
	@Override
	public long queryReportListCount(CourierScheduleEntity entity) {
		if(null==entity){
			entity =new CourierScheduleEntity();
		}
		return courierScheduleDao.queryReportListCount(entity);
	}
	/**
	 * 
	 *<p>Title: queryCourierScheduleByCondition</p>
	 *<p>根据收派小区名称和楼层查询排班集合</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午6:36:09
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICourierScheduleService#queryCourierScheduleByCondition(java.lang.String, java.lang.Integer)
	 * @param expressSmallZoneCode
	 * @param floor
	 * @return
	 */
	@Override
	public List<CourierScheduleEntity> queryCourierScheduleByCondition(
			String expressSmallZoneName, Integer floor) {
		if(StringUtils.isBlank(expressSmallZoneName)){
			throw new CourierScheduleException("收派小区名称为空");
		}
		int intFloor=0;
		if(null !=floor){
			intFloor=floor.intValue();
		}
		CourierScheduleEntity courierSchedule =new CourierScheduleEntity();
		//去除两端空格
		courierSchedule.setExpressSmallZoneName(expressSmallZoneName.trim());
		return courierScheduleDao.queryCourierScheduleByCondition(courierSchedule,intFloor);
	}
	/**
	 * <p>根据小区编码,小区名称作废相关的小区编码的排班信息</p>
	 * @param expressSmallZoneCode
	 * @return
	 */
	@Transactional
	@Override
	public int deleteCourierScheduleByExpressSmallZoneCode(
			String[] expressSmallZoneCodes) {
		if (expressSmallZoneCodes.length ==0) {
			throw new CourierScheduleException("参数为空");
		}
		return courierScheduleDao.deleteCourierScheduleByExpressSmallZoneCode(expressSmallZoneCodes);
	}
	/**
	 * <p>根据小区编码,小区名称修改相关的小区编码，小区名称排班信息</p>
	 * @param newRegionsCode
	 * @param newRegionsName
	 * @param oldRegionsCode
	 * @param oldRegionsName
	 * @return
	 */
	@Transactional
	@Override
	public int updateCourierScheduleByCondition(String newRegionsCode,
			String newRegionsName, String oldRegionsCode, String oldRegionsName) {
		if(StringUtils.isBlank(oldRegionsName)||StringUtils.isBlank(oldRegionsCode)){
			throw new CourierScheduleException("查询参数不能为空");
		}
		if(StringUtils.isBlank(newRegionsName)||StringUtils.isBlank(newRegionsCode)){
			throw new CourierScheduleException("修改参数不能为空");
		}
		CourierScheduleEntity newCourierSchedule =new CourierScheduleEntity();
		newCourierSchedule.setExpressSmallZoneCode(newRegionsCode);
		newCourierSchedule.setExpressSmallZoneName(newRegionsName);
		newCourierSchedule.setVersionNo(new Date().getTime());
		newCourierSchedule.setModifyDate(new Date());
		newCourierSchedule.setModifyUser("system");
		return courierScheduleDao.updateCourierScheduleByCondition(newCourierSchedule,oldRegionsCode,oldRegionsName);
	}
	
	/**
	 * 对同一时间段一个小区最多只能有一个主责和一个替班进行验证
	 * @param entity
	 */
	private void courierNatureValid(CourierScheduleEntity entity){
		
		CourierScheduleEntity entity1=new CourierScheduleEntity();
		entity1.setStartTime(DateUtils.getStartDatetime(entity.getStartTime()));
		entity1.setEndTime(DateUtils.getStartDatetime(entity.getEndTime()));
		entity1.setExpressSmallZoneCode(entity.getExpressSmallZoneCode());
		entity1.setCourierNature(entity.getCourierNature());
		//判断排班时间段人员不能重复排班
		List<CourierScheduleEntity> courierScheduleEntitys=
				courierScheduleDao.queryCourierScheduleList(entity1, NumberConstants.ZERO, Integer.MAX_VALUE);
		
		//TODO
		if(!CollectionUtils.isEmpty(courierScheduleEntitys)){
			String exceptionMsg=null;
			if(StringUtils.equals(courierScheduleEntitys.get(0).getCourierNature(), "FIXED")){
				exceptionMsg="该时间段已存在主责属性的排班！";
			}else if(StringUtils.equals(courierScheduleEntitys.get(0).getCourierNature(), "MOTORIZED")){
				exceptionMsg="该时间段已存在替班属性的排班！";
			} else{
				exceptionMsg="排班重复！";
			}
			throw new CourierScheduleException(exceptionMsg);
		}
	}
	
	/**
	 * 
	 * @param entity
	 * @param courierCode
	 */
	private void courierNatureUpdateValid(CourierScheduleEntity entity,String courierCode,String courierNature,
			String oldSmallZoneCode){
		
		List<CourierScheduleEntity> courierScheduleEntitys=
				courierScheduleDao.queryCourierScheduleList(entity, NumberConstants.ZERO, Integer.MAX_VALUE);
		
		if(!CollectionUtils.isEmpty(courierScheduleEntitys)){
			if(courierScheduleEntitys.size()>1){
				throw new CourierScheduleException("每天每个小区只能安排一个主责和一个替班！");	
			}
			
			if(StringUtils.equals(courierScheduleEntitys.get(0).getCourierNature(), courierNature)){
				
				throw new CourierScheduleException("每天每个小区只能安排一个主责和一个替班！");
			}
			/*if(StringUtils.equals(courierScheduleEntitys.get(0).getCourierCode(), courierCode)){
				
				throw new CourierScheduleException("一个小区一个快递员只能有一种快递员属性！");
			}*/
			
		}
	}
	
}
