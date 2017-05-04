package com.deppon.foss.module.transfer.platform.server.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.platform.api.server.dao.IUnloadGoodsDetailNoDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.NoUnloadGoodsDetail;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;

public class UnloadGoodsDetailNoDaoImplTest extends BaseTestCase {
	@Autowired IUnloadGoodsDetailNoDao unloadGoodsDetailNoDao;
	
	@Test
	public void testQueryNoUnloadGoodsDetailListSum(){
		@SuppressWarnings("unchecked")
		Map<String,String> map = new HashedMap();
		map.put("taskId", "0dc4ce2a-e22c-4bcb-a2f7-542b2fd64ce3");
		map.put("productCode", "FLF");
		
		NoUnloadGoodsDetail xx = unloadGoodsDetailNoDao.queryNoUnloadGoodsDetailListSum(map);
		if(xx!=null){
			System.out.println("Volume:"+xx.getGoodsVolumeTotal()+"   Weight:"+xx.getGoodsWeightTotal());
		}else{
			fail("空记录");
		}
	}
	
	//@Test
	public void testQueryNoUnloadGoodsDetailList() {
		@SuppressWarnings("unchecked")
		Map<String,String> map = new HashedMap();
		map.put("taskId", "07ea626c-9f01-401e-8c8d-db03e4725fe0");
		map.put("productCode", "FLF");
		List<NoUnloadGoodsDetail> xx = unloadGoodsDetailNoDao.queryNoUnloadGoodsDetailList(map,-1,-1);
		if(xx!=null && xx.size()>0){
			int i=1;
			for (NoUnloadGoodsDetail noUnloadGoodsDetail : xx) {
				System.out.println("第"+i+"行："+noUnloadGoodsDetail.getWaybillNo()+":"+noUnloadGoodsDetail.getGoodsVolumeTotal());
				i++;
			}
		}else{
			fail("空记录");
		}
	}

	//@Test
	public void testQueryNextNameByOrgCode() {
		String unloadOrgCode = "W3100020616";
		List<String> waybillNo = new ArrayList<String>();
		waybillNo.add("400730502");
		List<NoUnloadGoodsDetail> xx = unloadGoodsDetailNoDao.queryNextNameByOrgCode(unloadOrgCode, waybillNo);
		if(xx!=null && xx.size()>0){
			int i=1;
			for (NoUnloadGoodsDetail noUnloadGoodsDetail : xx) {
				System.out.println("第"+i+"行："+noUnloadGoodsDetail.getNextOrgname());
				i++;
			}
		}else{
			fail("空记录");
		}
		
	}

}
