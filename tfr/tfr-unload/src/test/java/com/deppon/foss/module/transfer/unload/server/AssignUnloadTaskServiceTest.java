package com.deppon.foss.module.transfer.unload.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.deppon.foss.module.login.server.service.impl.LoginService;
import com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressCancelAssignUnloadInstructDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.AssignUnloadTaskVo;

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
public class AssignUnloadTaskServiceTest {

	
	@Autowired
	private IAssignUnloadTaskService assignUnloadTaskService;
	
	public static final Logger logger = LoggerFactory.getLogger(AssignUnloadTaskServiceTest.class);
	
	
	@BeforeClass
    public static void beforeClass() {
        
		 try {
	    		LoginService userLogin=new LoginService();
	    		String userName="084544";
	    		String pwd="111111";
	    		//userLogin.userLogin(userName, pwd);
				logger.info("login success！");
			} catch (Exception e) {
				logger.info("login error！");
				//fail("login error！");
				e.printStackTrace();
			}
    }
  
	
	/**
	* @description FOSS同步取消分配卸车任务给悟空
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月12日 下午7:19:03
	 */
	@Test
	public void cancelAssignedUnloadTask(){
		
		ExpressCancelAssignUnloadInstructDto cancelAssignUnloadInstructDto=new ExpressCancelAssignUnloadInstructDto();
		//设置  分配卸车指令编号
		cancelAssignUnloadInstructDto.setCommandNo("09ad2baf-eb24-4626-8731-59943d70ad19");
		//设置  修改时间
		cancelAssignUnloadInstructDto.setUpdateTime(new Date());
		try {
			//assignUnloadTaskService.cancelAssignedUnloadTask(cancelAssignUnloadInstructDto);
			logger.info("FOSS同步取消分配卸车任务给悟空成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("FOSS同步取消分配卸车任务给悟空失败！");
		}
	}
	
	/**
	* @description FOSS同步分配卸车任务给悟空
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月12日 下午7:19:03
	 */
	@Test
	public void assignUnloadTask(){
		
		AssignUnloadTaskVo vo=new AssignUnloadTaskVo();
		 
		AssignUnloadTaskTotalDto assignUnloadTaskTotalDto=new AssignUnloadTaskTotalDto();
		assignUnloadTaskTotalDto.setId("09ad2baf-eb24-4626-8731-59943d70ad19");
		assignUnloadTaskTotalDto.setVehicleNo("苏A888");
		assignUnloadTaskTotalDto.setVehicleType("16.5米");
		assignUnloadTaskTotalDto.setUnloadType("DELIVER");
		assignUnloadTaskTotalDto.setArriveTime("2016-05-09 11:51:17");
		assignUnloadTaskTotalDto.setWeightTotal(8);
		assignUnloadTaskTotalDto.setVolumeTotal(8);
		assignUnloadTaskTotalDto.setWayBillQtyTotal(8);
		assignUnloadTaskTotalDto.setPlatformNo("0123");
		vo.setVehicle(assignUnloadTaskTotalDto);
		
		List<ArriveBillDto> arriveBillList=new  ArrayList<ArriveBillDto>();
		ArriveBillDto arriveBillDto=new ArriveBillDto();
		arriveBillDto.setBillNo("54454");

		LoaderDto loaderDto =new LoaderDto();
		loaderDto.setLoaderCode("4545");
		loaderDto.setLoaderName("黎明");
		vo.setLoader(loaderDto);
		arriveBillDto.setWeightTotal(8);
		arriveBillDto.setVolumeTotal(8);
		arriveBillDto.setGoodsQtyTotal(8);
		arriveBillDto.setOrigOrgCode("54545");
		arriveBillDto.setOrigOrgName("营运部");
		arriveBillDto.setDestOrgCode("8888");
		arriveBillList.add(arriveBillDto);
		vo.setBills(arriveBillList);
		
		try {
			// assignUnloadTaskService.assignUnloadTask(vo);
			logger.info("FOSS同步分配卸车任务给悟空成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("FOSS同步分配卸车任务给悟空失败！");
		}
	}
	
	
}
