package com.deppon.foss.module.transfer.load.server;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deppon.foss.module.transfer.common.api.shared.dto.WKLoadTempDto;
import com.deppon.foss.module.transfer.load.api.server.service.IWKLoadTempService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
public class WKLoadTempServiceTest {
	
	@Autowired
	private IWKLoadTempService wkLoadTempService;

	
	/**
	* @description 测试向T_OPT_WK_LOAD_TEMP表中添加数据
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:25:42
	*/
	public void testInsert() {
		
		
		WKLoadTempDto dto = new WKLoadTempDto();
//		for (int i = 0; i < 20; i++) {
//			
//			dto.setJsonData("fasfasdfas" + i+i);
//			dto.setTaskNo("ssss" + i);
//			dto.setTaskType(2);
////			wkLoadTempService.inertData(dto );
//		}
	}
	
	
	/**
	* @description 测试从T_OPT_WK_LOAD_TEMP表中删除数据
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:26:27
	*/
	public void testDelete() {
//		WKLoadTempDto dto = new WKLoadTempDto();
//		String taskNo = "ssss0";
//		dto.setTaskNo(taskNo);
//		dto.setTaskType(2);
//		int result = wkLoadTempService.deleteData(dto );
//		System.out.println(result);
	}
	
	
	/**
	* @description 获取表T_OPT_WK_LOAD_TEMP的总条数
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:26:52
	*/
	public void testGetCount() {
//		int taskType = 1;
//		int result = wkLoadTempService.getCount(taskType);
//		System.out.println(result);
	}
	
	
	/**
	* @description 测试分页查询数据
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:27:29
	*/
	public void testGetList() {
//		int taskType = 0;
//		int startNum = 0;
//		int endNum = 10;
//		List<WKLoadTempDto> list = wkLoadTempService.getDatasByPage(taskType, startNum, endNum);
//		
//		for (WKLoadTempDto wkLoadTempDto : list) {
//			System.out.println(wkLoadTempDto.getTaskNo());
//		}
	}
	
	@Test
	public void mainTest() {
//		testDelete();
//		testGetCount();
//		testGetList();
//		testInsert();
	}
	
}
