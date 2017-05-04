/*
 * PROJECT NAME: tfr-scheduling-web
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.dao
 * FILE    NAME: TruckDepartPlanTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.dao;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.scheduling.api.define.TruckDepartPlanConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanUpdateService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckDepartPlanDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckDepartPlanUpdateEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 计划测试
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-22 上午9:11:46
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/scheduling/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class TruckDepartPlanTest extends AbstractTransactionalJUnit4SpringContextTests {

	private static final Log logger = LogFactory.getLog(DateUtils.class);

	@Autowired
	ITruckDepartPlanService truckDepartPlanService;
	@Autowired
	ITruckDepartPlanDetailService truckDepartPlanDetailService;
	@Autowired
	ITruckDepartPlanUpdateService truckDepartPlanUpdateService;

	@BeforeTransaction
	public void beforeTransaction() {
		// testAddTruckDepartPlan();
		// testAddTruckDepartPlanDetail();
		this.testAddTruckDepartPlanUpdate();

	}

	@AfterTransaction
	public void afterTransaction() {

	}

	@Test
	public void test() {

	}

	/**
	 * 
	 * 新增发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-22 上午10:12:42
	 */
	// @Test
	public void testAddTruckDepartPlan() {
		// 计划实体
		TruckDepartPlanDto truckDepartPlanDto = new TruckDepartPlanDto();
		// UUID
		truckDepartPlanDto.setId(UUIDUtils.getUUID());
		// 出发部门
		truckDepartPlanDto.setOrigOrgCode("OrigOrgCode");
		// 到达部门
		truckDepartPlanDto.setDestOrgCode("DestOrgCode");
		// 计划日期
		truckDepartPlanDto.setPlanDate(new Date());
		// 计划类型
		truckDepartPlanDto.setPlanType(TruckDepartPlanConstants.PLAN_TYPE_SHORT);
		// 是否异常
		truckDepartPlanDto.setIsIssue(TruckDepartPlanConstants.IS_ISSUE_NO);
		// 备注
		truckDepartPlanDto.setNotes(TruckDepartPlanConstants.COLUMN_NULL_REPLACER);
		// 创建时间
		truckDepartPlanDto.setCreateTime(new Date());
		// 创建人员CODE
		truckDepartPlanDto.setCreateUserCode("096591");
		// 创建人员姓名
		truckDepartPlanDto.setCreateUserName("CreateUserName");
		// 创建人员组织CODE
		truckDepartPlanDto.setCreateOrgCode("CreateOrgCode");
		// 更新时间
		truckDepartPlanDto.setUpdateTime(new Date());
		// 更新人员CODE
		truckDepartPlanDto.setUpdateUserCode(TruckDepartPlanConstants.COLUMN_NULL_REPLACER);
		// 更新人员姓名
		truckDepartPlanDto.setUpdateUserName(TruckDepartPlanConstants.COLUMN_NULL_REPLACER);
		// 更新人员组织code
		truckDepartPlanDto.setUpdateOrgCode(TruckDepartPlanConstants.COLUMN_NULL_REPLACER);
		// truckDepartPlanDto.setDetailList("DetailList");
		// 状态
		truckDepartPlanDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		truckDepartPlanService.addTruckDepartPlan(truckDepartPlanDto, null);
	}

	// @Test
	public void testAddTruckDepartPlanDetail() {
		TruckDepartPlanDetailDto truckDepartPlanDetailDto = new TruckDepartPlanDetailDto();
		truckDepartPlanDetailDto.setId(UUIDUtils.getUUID());
		// 出发部门
		truckDepartPlanDetailDto.setOrigOrgCode("OrigOrgCode");
		// 到达部门
		truckDepartPlanDetailDto.setDestOrgCode("DestOrgCode");
		// 计划类型
		truckDepartPlanDetailDto.setPlanType(TruckDepartPlanConstants.PLAN_TYPE_SHORT);
		// 创建信息
		truckDepartPlanDetailDto.setCreateTime(new Date());
		truckDepartPlanDetailDto.setCreateUserCode("CreateUserCode");
		truckDepartPlanDetailDto.setCreateUserName("CreateUserName");
		truckDepartPlanDetailDto.setCreateOrgCode("CreateOrgCode");
		// 更新信息
		truckDepartPlanDetailDto.setUpdateTime(new Date());
		truckDepartPlanDetailDto.setUpdateUserCode(TruckDepartPlanConstants.COLUMN_NULL_REPLACER);
		truckDepartPlanDetailDto.setUpdateUserName(TruckDepartPlanConstants.COLUMN_NULL_REPLACER);
		truckDepartPlanDetailDto.setUpdateOrgCode(TruckDepartPlanConstants.COLUMN_NULL_REPLACER);
		// 计划ID
		truckDepartPlanDetailDto.setTruckDepartPlanId("15c8b60f-8f73-4be4-a424-93d4854e4aba");
		// 车牌号
		truckDepartPlanDetailDto.setVehicleNo("12345");
		// 线路虚拟code
		truckDepartPlanDetailDto.setLineNo("LineNo");
		// 发车日期
		truckDepartPlanDetailDto.setDepartDate(new Date());
		// 计划发车时间
		truckDepartPlanDetailDto.setPlanDepartTime(new Date());
		// 班次
		truckDepartPlanDetailDto.setFrequencyNo("1");
		// 月台分配ID
		truckDepartPlanDetailDto.setPlatformDistributeId("1");
		// 是否正班车
		truckDepartPlanDetailDto.setIsOnScheduling(TruckDepartPlanConstants.IS_ON_SCHEDULING_YES);
		// 班次类型
		truckDepartPlanDetailDto.setFrequencyType(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
		// 车型
		truckDepartPlanDetailDto.setTruckModel("17.6");
		// 车辆归属类型
		truckDepartPlanDetailDto.setTruckType("TruckType");
		// 车辆报道时间
		truckDepartPlanDetailDto.setTruckArriveTime(new Date());
		// 货柜号
		truckDepartPlanDetailDto.setContainerNo("ContainerNo");
		// 最大载重
		truckDepartPlanDetailDto.setMaxLoadWeight(BigDecimal.valueOf(100));
		// 实际最大载重
		truckDepartPlanDetailDto.setActualMaxLoadWeight(BigDecimal.valueOf(100));
		// 车容积
		truckDepartPlanDetailDto.setTruckVolume(BigDecimal.valueOf(50));
		// 预计装载重量
		truckDepartPlanDetailDto.setPlanLoadWeight(BigDecimal.valueOf(50));
		// 预计装载体积
		truckDepartPlanDetailDto.setPlanLoadVolume(BigDecimal.valueOf(50));
		// 车辆信息备注
		truckDepartPlanDetailDto.setTruckInfoNotes("TruckInfoNotes");
		// 司机编号1
		truckDepartPlanDetailDto.setDriverCode1("12345");
		// 司机姓名1
		truckDepartPlanDetailDto.setDriverName1("和田");
		// 联系方式1
		truckDepartPlanDetailDto.setDriverPhone1("18918186765");
		// 司机编号2
		truckDepartPlanDetailDto.setDriverCode2("DriverCode2");
		// 司机姓名2
		truckDepartPlanDetailDto.setDriverName2("DriverName2");
		// 联系方式2
		truckDepartPlanDetailDto.setDriverPhone2("DriverPhone2");
		// 线路名称
		truckDepartPlanDetailDto.setLineName("LineName");
		// 计划到达时间
		truckDepartPlanDetailDto.setPlanArriveTime(new Date());
		// 状态
		truckDepartPlanDetailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		truckDepartPlanDetailService.addTruckDepartPlanDetail(truckDepartPlanDetailDto);
	}

	/**
	 * 查询发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-22 下午2:38:09
	 */
	@Test
	public void queryTruckDepartPlan() {
		TruckDepartPlanDto truckDepartPlanDto = new TruckDepartPlanDto();
		truckDepartPlanDto.setPlanType(TruckDepartPlanConstants.PLAN_TYPE_SHORT);
		truckDepartPlanDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		List<TruckDepartPlanDto> list = truckDepartPlanService.queryTruckDepartPlan(truckDepartPlanDto, 15, 0);

		if (CollectionUtils.isNotEmpty(list)) {

			if (CollectionUtils.isNotEmpty(list)) {
				if (logger.isWarnEnabled()) {
					logger.warn("list.size():" + list.size());
				}
			}
		}
	}

	public void testAddTruckDepartPlanUpdate() {
		TruckDepartPlanUpdateEntity logEntity = new TruckDepartPlanUpdateEntity();
		logEntity.setId(UUIDUtils.getUUID());
		logEntity.setCreateTime(new Date());
		logEntity.setCreateUserCode("CreateUserCode");
		logEntity.setCreateUserName("CreateUserName");
		logEntity.setCreateOrgCode("CreateOrgCode");
		logEntity.setTruckDepartPlanDetailId("0ee29d7e-ab72-4380-8798-91b01cdc4da8");
		logEntity.setModifyContent("ModifyContent");
		logEntity.setCreateUser("CreateUser");
		logEntity.setModifyUser("ModifyUser");
		logEntity.setCreateDate(new Date());
		logEntity.setModifyDate(new Date());
		truckDepartPlanUpdateService.addTruckDepartPlanUpdate(logEntity);
	}

	@Test
	public void testQueryTruckDepartPlanUpdates() {
		// 参数
		List<TruckDepartPlanDetailEntity> detailList = new ArrayList<TruckDepartPlanDetailEntity>();
		TruckDepartPlanDetailEntity detail = null;
		detail = new TruckDepartPlanDetailEntity();
		detail.setId("0ee29d7e-ab72-4380-8798-91b01cdc4da8");
		detailList.add(detail);
		detail = new TruckDepartPlanDetailEntity();
		detail.setId("f13c45a9-359c-4833-a062-0bf6c055186d");
		detailList.add(detail);

	}

	@Test
	public void testQueryTotalCount() {
		List<TruckDepartPlanDetailEntity> detailList = new ArrayList<TruckDepartPlanDetailEntity>();
		TruckDepartPlanDetailEntity detail = null;
		detail = new TruckDepartPlanDetailEntity();
		detail.setId("0ee29d7e-ab72-4380-8798-91b01cdc4da8");
		detailList.add(detail);
		detail = new TruckDepartPlanDetailEntity();
		detail.setId("f13c45a9-359c-4833-a062-0bf6c055186d");
		detailList.add(detail);

	}

	public static void main(String a[]) {
		// printMethod("truckDepartPlanDto", TruckDepartPlanDto.class);
		// printMethod("truckDepartPlanDetailDto",
		// TruckDepartPlanDetailDto.class);
		printMethod("forecast", ForecastQuantityEntity.class);
		// printMethod("truckDepartPlanDetailDto",
		// TruckDepartPlanDetailDto.class);
	}

	/**
	 * 快速生成set方法
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-22 上午10:13:05
	 */
	@SuppressWarnings("rawtypes")
	static void printMethod(String beanName, Class clazz) {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (StringUtils.isNotBlank(method.getName()) && method.getName().indexOf("set") > -1) {
				Class<?>[] types = method.getParameterTypes();
				if (types[0].getSimpleName().equals("String")) {

				} else if (types[0].getSimpleName().equals("Date")) {

				} else {

				}

			}
		}

	}
}
