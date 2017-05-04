package com.deppon.foss.module.transfer.platform.server.server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformOpeEffiService;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;

/**
 * 
* @ClassName: PlatformOpeEffiTest 
* @Description: 月台操作效率测试用例
* @author 105944
* @date 2015-3-25 上午10:55:55
 */
public class PlatformOpeEffiTest extends BaseTestCase {
	@Autowired IPlatformOpeEffiService platformOpeEffiService;
	
	@Test
	public void statisticPlatformOpeEffiTest(){
		//统计月台操作效率job
		platformOpeEffiService.statisticPlatformOpeEffi();
	}
}
