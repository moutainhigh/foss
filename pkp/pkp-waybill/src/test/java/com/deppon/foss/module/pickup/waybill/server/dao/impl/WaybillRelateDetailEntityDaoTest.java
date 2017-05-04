package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRelateDetailEntityDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;

/**
 * WaybillRelateDetailEntityDao 测试类
 * @author 272311-sangwenhao
 *
 */
public class WaybillRelateDetailEntityDaoTest {

	private IWaybillRelateDetailEntityDao waybillRelateDetailEntityDao ;
	private ApplicationContext cx = null;
	@Before
	public void setUp() throws Exception {
		if (cx == null || waybillRelateDetailEntityDao == null) {
			cx = new ClassPathXmlApplicationContext(new String[]{"com/deppon/foss/module/pickup/waybill/server/META-INF/springTest.xml"});
			waybillRelateDetailEntityDao = (IWaybillRelateDetailEntityDao) cx.getBean(WaybillRelateDetailEntityDao.class);
		}
	}

	@After
	public void tearDown() throws Exception {
		waybillRelateDetailEntityDao = null ;
	}
 
	@Test
	public void testQueryWaybillRelateDetailByWaybillNos() {
		List<String> waybillNoList = new ArrayList<String>();
		waybillNoList.add("5820151119"); 
		waybillNoList.add("422324789");
		
		List<WaybillRelateDetailEntity> waybillRelateDetailEntities = waybillRelateDetailEntityDao.queryWaybillRelateDetailByWaybillNos(waybillNoList, 0, 0, false);
		
		if(CollectionUtils.isNotEmpty(waybillRelateDetailEntities)){
			System.out.println("子母件运单信息 返回的记录总数："+waybillRelateDetailEntities.size());
		}else{
			System.out.println("子母件运单信息 返回的记录为空");
		}
		for(WaybillRelateDetailEntity entity : waybillRelateDetailEntities){
			System.out.println("子母件运单信息 母件运单号："+entity.getParentWaybillNo()+"; 子运单号："+entity.getWaybillNo()+";"+entity.getIsPicPackage());
		}
		
	}

	@Test
	public void testQueryWaybillRelateDetailsByWaybillNos() {
		List<String> waybillNoList = new ArrayList<String>();
		waybillNoList.add("5820151119");
		waybillNoList.add("422324789");
		
		List<WaybillRelateDetailEntity> waybillRelateDetailEntities = waybillRelateDetailEntityDao.queryWaybillRelateDetailsByWaybillNos(waybillNoList, 0, 0, false);
		
		if(CollectionUtils.isNotEmpty(waybillRelateDetailEntities)){
			System.out.println("关联子母件运单信息 返回的记录总数："+waybillRelateDetailEntities.size());
		}else{
			System.out.println("关联子母件运单信息 返回的记录为空");
		}
		for(WaybillRelateDetailEntity entity : waybillRelateDetailEntities){
			System.out.println("关联子母件运单信息 母件运单号："+entity.getParentWaybillNo()+"; 子运单号："+entity.getWaybillNo()+";"+entity.getIsPicPackage());
		}
		
	}

}
