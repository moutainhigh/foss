package com.deppon.foss.module.transfer.unload.server;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressQueryUnloadWaybillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;

/**
* @description 卸车任务测试
* @version 1.0
* @author 328768-foss-gaojianfu
* @update 2016年5月12日 下午6:33:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/unload/server/spring-test.xml" })
//@TransactionConfiguration
//@Transactional
public class UnloadTaskQueryServiceTest {

	
	@Autowired
	private IUnloadTaskQueryService unloadTaskQueryService;
	
	public static final Logger logger = LoggerFactory.getLogger(UnloadTaskQueryServiceTest.class);
	
	/**
	* @description 根据卸车任务编号获取快递卸车明细
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月12日 下午7:17:27
	 */
	@Test
	public void queryUnloadExpressTaskDetailByUnloadTaskNo(){
		
		String unloadTaskNo="8888";
		
		try {
			//List<UnloadWaybillDetailDto>  list=unloadTaskQueryService.queryUnloadExpressTaskDetailByUnloadTaskNo(unloadTaskNo);
			//assertNotNull(list);
			logger.info("根据卸车任务编号获取快递卸车明细成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据卸车任务编号获取快递卸车明细失败！");
		}
		
	}
	
	

	/**
	* @description  根据卸车运单ID获取所有流水号
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月12日 下午7:19:03
	 */
	@Test
	public void queryUnloadSerialNoByExpressUnloadWaybillDetailId(){
		
		ExpressQueryUnloadWaybillDetailDto expressQueryUnloadWaybillDetailDto=new ExpressQueryUnloadWaybillDetailDto();
		expressQueryUnloadWaybillDetailDto.setUnloadTaskNo("8888");
		expressQueryUnloadWaybillDetailDto.setCargoNo("1110");
		expressQueryUnloadWaybillDetailDto.setCargoType("包");
		try {
			//List<UnloadSerialNoDetailEntity>  list=unloadTaskQueryService.queryUnloadSerialNoByExpressUnloadWaybillDetailId(expressQueryUnloadWaybillDetailDto);
			// assertNotNull(list);
			 logger.info(" 根据卸车运单ID获取所有流水号成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" 根据卸车运单ID获取所有流水号失败！");
		}
	}
}
