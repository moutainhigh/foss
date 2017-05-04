/**  
 * Project Name:tfr-common  
 * File Name:TfrJobTodoServiceTest.java  
 * Package Name:com.deppon.foss.module.transfer.common.test.service  
 * Date:2015年4月15日下午2:43:46  
 *  
 */  
  
package com.deppon.foss.module.transfer.common.test.service;  

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto;
import com.deppon.foss.module.transfer.common.test.BaseTestCase;

/**  
 * ClassName: TfrJobTodoServiceTest <br/>  
 * Function: TfrJobTodoService测试类. <br/>  
 * date: 2015年4月15日 下午2:43:46 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class TfrJobTodoServiceTest extends BaseTestCase{
	
	@Autowired
	private ITfrJobTodoService tfrJobTodoService;
	
	@Test
	public void testAdd(){
		tfrJobTodoService.addJobTodo("test_id", "test_scene", new String[]{"test_goal1","test_goal2"}, new Date(),"045923");
	}
	
	@Test
	public void testUpdate(){
		tfrJobTodoService.updateJobTodoByID("test_id");
	}
	
	@Test
	public void testQuery(){
		TfrJobTodoQueryDto dto = new TfrJobTodoQueryDto();
		dto.setBizEndTime(new Date());
		dto.setBizStartTime(new Date());
		dto.setBusinessGoalList(new String[]{"goal1","goal2"});
		dto.setBusinessSceneList(new String[]{"scene1","scene2"});
		dto.setCount(100);
		dto.setEndTime(new Date());
		dto.setStartTime(new Date());
		tfrJobTodoService.queryJobTodo(dto);
	}
}
  
