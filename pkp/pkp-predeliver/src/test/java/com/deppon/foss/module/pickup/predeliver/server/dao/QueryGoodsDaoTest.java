/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver
 * PACKAGE NAME: com.deppon.foss.module
 * FILE    NAME: QueryGoodsDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.predeliver.server.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoDto;
import com.deppon.foss.module.pickup.predeliver.server.dao.impl.QueryGoodsDao;
import com.deppon.foss.module.pickup.predeliver.server.util.SpringTestUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询货量测试类
 * 
 * @author 043258-foss-zhaobin
 * @date 2012-10-23 下午2:33:17
 */
public class QueryGoodsDaoTest {

	private QueryGoodsDao queryGoodsDao = null;

	private static ApplicationContext ctx = null;

	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/predeliver/test/META-INF/spring.xml",
			"com/deppon/foss/module/pickup/predeliver/server/META-INF/spring.xml" };

	@Before
	public void init() throws Exception {
		this.queryGoodsDao = SpringTestUtil.getInstance()
				.getApplicationContext().getBean(QueryGoodsDao.class);
	}

	//@Test
	public void testQueryGoodsDetial() {
		GoodsInfoConditionDto goodsInfoConditionDto = new GoodsInfoConditionDto();
		goodsInfoConditionDto.setWaybillNo("622202802");
		goodsInfoConditionDto.setDepartmentCode("W040002060401");
		goodsInfoConditionDto.setWbrStatus(WaybillRfcConstants.ACCECPT);
		goodsInfoConditionDto.setActive(FossConstants.ACTIVE);
		queryGoodsDao
				.queryGoods(goodsInfoConditionDto, 1, 10);
	}
	
	//@Test
	public void testQueryGoods() {
		GoodsInfoConditionDto goodsInfoConditionDto = new GoodsInfoConditionDto();
		goodsInfoConditionDto.setWaybillNo("622202802");
		List<GoodsInfoDto> dto = queryGoodsDao.queryGoodsInfo(goodsInfoConditionDto);
	}
}