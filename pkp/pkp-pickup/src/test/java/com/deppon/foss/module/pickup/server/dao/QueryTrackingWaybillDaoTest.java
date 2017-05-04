/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver
 * PACKAGE NAME: com.deppon.foss.module
 * FILE    NAME: QueryGoodsDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.server.dao;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.pickup.api.server.dao.IQueryTrackingWaybillDao;
import com.deppon.foss.module.pickup.pickup.api.shared.define.QueryTrackingWaybillConstants;
import com.deppon.foss.module.pickup.pickup.api.shared.domain.WaybillTrackInfoEntity;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillConditionDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillDto;
import com.deppon.foss.module.pickup.pickup.server.dao.impl.QueryTrackingWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单追踪DAO测试类
 * 
 * @author ibm-wangfei
 * @date Oct 24, 2012 5:06:00 PM
 */
public class QueryTrackingWaybillDaoTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryTrackingWaybillDaoTest.class);
	private IQueryTrackingWaybillDao queryTrackingWaybillDao = null;
	TrackingWaybillConditionDto condition;
	private static ApplicationContext ctx = null;

	String[] xmls = new String[] { "com/deppon/foss/module/pickup/pickup/test/META-INF/spring.xml", 
			"com/deppon/foss/module/pickup/pickup/server/META-INF/spring.xml" };

	@Before
	public void init() throws Exception {
		try {
			if (ctx == null || queryTrackingWaybillDao == null) {
				ctx = new ClassPathXmlApplicationContext(xmls);
				queryTrackingWaybillDao = ctx.getBean(QueryTrackingWaybillDao.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新增运单追踪记录
	 * 
	 * @author ibm-wangfei
	 * @date Jan 6, 2013 11:25:17 AM
	 */
	@Test
	public void addTrackingWaybillEntity() {
		WaybillTrackInfoEntity entity = new WaybillTrackInfoEntity();

		entity.setId(UUIDUtils.getUUID());
		entity.setWaybillNo("472538198");
		entity.setTrackContent("运单运行正常");
		entity.setTrackMan("小明");
		entity.setTrackMethod("电话");
		entity.setTrackStatus(QueryTrackingWaybillConstants.TRACK_NEXT);
		entity.setTrackType("进港追踪");
		entity.setOperator("1");// 操作人 OPERATOR
		entity.setOperatorCode("1"); // 操作人编码 OPERATOR_NO
		entity.setOperateOrgName("1"); // 操作部门 OPERATE_ORG_NAME
		entity.setOperateOrgCode("1"); // 操作部门编码
		entity.setOperateTime(new Date()); // 操作时间 OPERATE_TIME
		int result = queryTrackingWaybillDao.addTrackingWaybillEntity(entity);
		Assert.assertTrue(result > 0);
	}

	/**
	 * 查询运单追踪列表
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 5:32:43 PM
	 */
	@Test
	public void queryByWaybillNo() {
		List<WaybillTrackInfoEntity> list = queryTrackingWaybillDao.queryByWaybillNo("472538198");
		Assert.assertTrue(list.size() > 0);
	}

	/**
	 * 页面查询
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 5:32:43 PM
	 */
	@Test
	public void queryTrackingWaybillCountForBefore() {
		TrackingWaybillConditionDto condition = new TrackingWaybillConditionDto();
		condition.setTrackType(QueryTrackingWaybillConstants.TRACK_NEXT);
		condition.setActive(FossConstants.ACTIVE);
		condition.setReceiveOrgCode("GS00002");
		condition.setReturnBillFlgNum(5);
		condition.setWaybillNo("472538198");
		Long l = queryTrackingWaybillDao.queryTrackingWaybillCountForBefore(condition);
		Assert.assertTrue(l.intValue() == 1);
	}

	/**
	 * 页面查询
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 5:32:43 PM
	 */
	@Test
	public void queryTrackingWaybillDtoListForBefore() {
		TrackingWaybillConditionDto condition = new TrackingWaybillConditionDto();
		condition.setTrackType(QueryTrackingWaybillConstants.TRACK_NEXT);
		condition.setActive(FossConstants.ACTIVE);
		condition.setReceiveOrgCode("GS00002");
		condition.setReturnBillFlgNum(5);
		// condition.setWaybillNo("472538198");
		List<TrackingWaybillDto> list = queryTrackingWaybillDao.queryTrackingWaybillDtoListForBefore(condition, 1, 99999);
		Assert.assertTrue(list.size() > 0);
	}

	/**
	 * 页面查询
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 5:32:43 PM
	 */
	@Test
	public void queryTrackingWaybillCountForNew() {
		TrackingWaybillConditionDto condition = new TrackingWaybillConditionDto();
		condition.setTrackType(QueryTrackingWaybillConstants.TRACK_NEXT);
		condition.setActive(FossConstants.ACTIVE);
		condition.setReceiveOrgCode("GS00002");
		Long l = queryTrackingWaybillDao.queryTrackingWaybillCountForNew(condition);
		Assert.assertTrue(l.intValue() > 0);
	}

	/**
	 * 页面查询
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 5:32:43 PM
	 */
	@Test
	public void queryTrackingWaybillDtoListForNew() {
		TrackingWaybillConditionDto condition = new TrackingWaybillConditionDto();
		condition.setTrackType(QueryTrackingWaybillConstants.TRACK_NEW);
		condition.setActive(FossConstants.ACTIVE);
		condition.setReceiveOrgCode("GS00002");
		// condition.setBillTimeFrom(DateUtils.convert("2012-12-01 01:01:01",
		// DateUtils.DATE_TIME_FORMAT));
		// condition.setBillTimeTo(DateUtils.convert("2012-12-02 01:01:01",
		// DateUtils.DATE_TIME_FORMAT));
		// condition.setWaybillNo("472538198");
		condition.setReturnBillType(WaybillConstants.NOT_RETURN_BILL);
		 condition.setReturnBillFlgNum(0);
		List<TrackingWaybillDto> list = queryTrackingWaybillDao.queryTrackingWaybillDtoListForNew(condition, 1, 99999);
		LOGGER.info(ReflectionToStringBuilder.toString(list));
		Assert.assertTrue(list.size() > 0);
	}
}