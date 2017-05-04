package com.deppon.foss.module.transfer.platform.server.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.platform.api.server.dao.ICarEfficiencyManageDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.CarEfficiencyEntity;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;

public class CarEfficiencyManageDaoTest extends BaseTestCase {
	@Autowired ICarEfficiencyManageDao carEfficiencyManageDao;
	
	//@Test
	public void testQueryCarEfficiencyWayLong() {
		CarEfficiencyEntity carEfficiency = new CarEfficiencyEntity();
		carEfficiency.setActualDepartTime("2013-11-02");
		carEfficiency.setTypeLoad("0");
		carEfficiency.setOrgCode("W3100020616");
		List<CarEfficiencyEntity> xx = carEfficiencyManageDao.queryCarEfficiencyWayLong(carEfficiency,0,0);
		if(xx!=null && xx.size()>0){
			int i=1;
			for (CarEfficiencyEntity carEfficiencyEntity : xx) {
				System.out.println("第"+i+"行:"+carEfficiencyEntity.getOrgName()+":"+carEfficiencyEntity.getBillNo()+" type:"+carEfficiencyEntity.getTypeLoad());
				i++;
			}
		}else{
			fail("空记录");
		}
	}

	//@Test
	public void testQueryCarEfficiencyWayLongCount() {
		CarEfficiencyEntity carEfficiency = new CarEfficiencyEntity();
		carEfficiency.setActualDepartTime("2013-11-02");
		carEfficiency.setTypeLoad("2");
		carEfficiency.setOrgCode("W3100020616");
		int xx= carEfficiencyManageDao.queryCarEfficiencyWayLongCount(carEfficiency);
		System.out.println("xxx:"+xx);
	}

	//@Test
	public void testQueryCarEfficiencyWayShort() {
		CarEfficiencyEntity carEfficiency = new CarEfficiencyEntity();
		carEfficiency.setActualDepartTime("2013-11-02");
		carEfficiency.setTypeLoad("2");
		carEfficiency.setOrgCode("W3100020616");
		List<CarEfficiencyEntity> xx = carEfficiencyManageDao.queryCarEfficiencyWayShort(carEfficiency,0,0);
		if(xx!=null && xx.size()>0){
			int i=1;
			for (CarEfficiencyEntity carEfficiencyEntity : xx) {
				System.out.println("第"+i+"行:"+carEfficiencyEntity.getOrgName()+":"+carEfficiencyEntity.getVehicleNo()+" type:"+carEfficiencyEntity.getTypeLoad());
				i++;
			}
		}else{
			fail("空记录");
		}
	}

	//@Test
	public void testQueryCarEfficiencyWayShortCount() {
		CarEfficiencyEntity carEfficiency = new CarEfficiencyEntity();
		carEfficiency.setActualDepartTime("2013-11-02");
		carEfficiency.setTypeLoad("0");
		carEfficiency.setOrgCode("W3100020616");
		int xx= carEfficiencyManageDao.queryCarEfficiencyWayShortCount(carEfficiency);
		System.out.println("xxx:"+xx);
	}

	@Test
	public void testQueryLoaderListByTaskId() {
		List<String> xxx = new ArrayList<String>();
//		xxx.add("c4255a91-7b94-4b3b-a065-c8ab891a06b9");
//		xxx.add("5a35b68c-436d-47af-b799-801729f3f4a8");
//		xxx.add("bf9eae63-2e8c-4750-a5f2-5407f3d0bd0f");
//		xxx.add("bdd8c92c-c334-47f8-a02d-3019f6b50e57");
		String taskIds ="c4255a91-7b94-4b3b-a065-c8ab891a06b9,5a35b68c-436d-47af-b799-801729f3f4a8,bf9eae63-2e8c-4750-a5f2-5407f3d0bd0f";
		
		if(StringUtils.isNotBlank(taskIds)){
			String[] taskIdArray = taskIds.split(",");
			if(taskIdArray!=null&&taskIdArray.length>0){
				xxx = Arrays.asList(taskIdArray);
			}else{
			}
		}else{
		}
		
		List<CarEfficiencyEntity> xx = carEfficiencyManageDao.queryLoaderListByTaskId(xxx);
		
		if(xx!=null && xx.size()>0){
			int i=1;
			for (CarEfficiencyEntity carEfficiencyEntity : xx) {
				System.out.println("第"+i+"行:"+carEfficiencyEntity.getLoaderCode()+":"+carEfficiencyEntity.getLoaderName());
				i++;
			}
		}else{
			fail("空记录");
		}
	}

}
