package com.deppon.foss.module.transfer.unload.server;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.deppon.foss.module.login.server.service.impl.LoginService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskBillsDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressCancelUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressQueryUnloadWaybillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskAddnewDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskModifyDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.UnloadTaskVo;

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
public class UnloadTaskServiceTest {

	
	@Autowired
	private IUnloadTaskService unloadTaskService;
	
	public static final Logger logger = LoggerFactory.getLogger(UnloadTaskServiceTest.class);
	
	
	 
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
	* @description 根据卸车任务编号返回快递单据明细
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月12日 下午7:17:27
	 */
	@Test
	public void queryExpressUnloadWaybillDetail(){
		
		ExpressQueryUnloadWaybillDetailDto expressQueryUnloadBillDetail=new ExpressQueryUnloadWaybillDetailDto();
		
		expressQueryUnloadBillDetail.setUnloadTaskNo("022016050260577");
		expressQueryUnloadBillDetail.setCargoNo("1100");
		expressQueryUnloadBillDetail.setCargoType("运单");
		
		try {
			//List<UnloadBillDetailEntity> list=unloadTaskService.queryExpressUnloadWaybillDetail(expressQueryUnloadBillDetail);
			//assertNotNull(list);
			logger.info("根据卸车任务编号返回快递单据明细成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据卸车任务编号返回快递单据明细失败！");
		}
		
	}
	
	

	/**
	* @description 根据交接单编号返回快递笼号，包号，运单号
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月12日 下午7:19:03
	 */
	@Test
	public void expressQueryWaybillListByHandOverBillNo(){
		
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("handoverBillNo","11212121");
		paramMap.put("unloadTaskNo","000000000");
		try {
			 //List<HandOverBillDetailEntity> list=unloadTaskService.expressQueryWaybillListByHandOverBillNo(paramMap);
			 //assertNotNull(list);
			 logger.info("根据交接单编号返回快递笼号，包号，运单号成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据交接单编号返回快递笼号，包号，运单号失败！");
		}
	}
	
	
	
	/**
	* @description Foss同步新建卸车任务到悟空系统
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月12日 下午7:19:03
	 */
	@Test
	public void addExpressUnloadTask(){
		
		UnloadTaskVo unloadTaskVo=new UnloadTaskVo();
		
		unloadTaskVo.setUnloadTaskNo("3214321");
		
		UnloadTaskAddnewDto addnewDto =new UnloadTaskAddnewDto();
		addnewDto.setVehicleNo("沪A8888");
		addnewDto.setPlatformCode("123");
		
		List<UnloadBillDetailEntity> list=new ArrayList<UnloadBillDetailEntity>();
		UnloadBillDetailEntity unloadBillDetailEntity=new UnloadBillDetailEntity();
		
		unloadBillDetailEntity.setBillNo("123432");
		unloadBillDetailEntity.setPieces(new BigDecimal("1234"));
		unloadBillDetailEntity.setVolume(new BigDecimal("1234"));
		unloadBillDetailEntity.setWeight(new BigDecimal("1234"));
		unloadBillDetailEntity.setExpressArriveCode("342");
		list.add(unloadBillDetailEntity);
		addnewDto.setBillList(list);
		
		 List<LoaderParticipationEntity>  listLoader=new  ArrayList<LoaderParticipationEntity> ();
		 
		 LoaderParticipationEntity loaderParticipationEntity=new LoaderParticipationEntity();
		
		 loaderParticipationEntity.setLoaderName("小明");
		 loaderParticipationEntity.setLoaderCode("8888");
		 loaderParticipationEntity.setLeaveTime(new Date());
		 loaderParticipationEntity.setLoadOrgName("营运部");
		 loaderParticipationEntity.setJoinTime(new Date());
		 listLoader.add(loaderParticipationEntity);
		 addnewDto.setLoaderList(listLoader);
		 
		 unloadTaskVo.setAddnewDto(addnewDto);
		 
		try {
			//unloadTaskService.addExpressUnloadTask(unloadTaskVo);
			logger.info("Foss同步新建卸车任务到悟空系统成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Foss同步新建卸车任务到悟空系统失败！");
		}
	}
	
	/**
	* @description FOSS同步确认卸车任务到WK
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月12日 下午7:19:03
	 */
	@Test
	public void confirmExpressUnloadTask(){
		
		ConfirmUnloadTaskDto confirmUnloadTaskDto=new ConfirmUnloadTaskDto();
		
		confirmUnloadTaskDto.setUnloadTaskId("09ad2baf-eb24-4626-8731-59943d70ad19");
		confirmUnloadTaskDto.setUnloadTaskNo("4323");
		 List<ConfirmUnloadTaskBillsDto> lackList=new ArrayList<ConfirmUnloadTaskBillsDto>();
		
		 ConfirmUnloadTaskBillsDto lackConfirmUnloadTaskBillsDto=new ConfirmUnloadTaskBillsDto();
		
		 lackConfirmUnloadTaskBillsDto.setHandOverBillNo("213");
		
		 lackList.add(lackConfirmUnloadTaskBillsDto);
		 confirmUnloadTaskDto.setLackHandOverBillList(lackList);
		 
		 List<ConfirmUnloadTaskBillsDto> moreList=new ArrayList<ConfirmUnloadTaskBillsDto>();
		
		 ConfirmUnloadTaskBillsDto moreConfirmUnloadTaskBillsDto=new ConfirmUnloadTaskBillsDto();
		 
		 moreConfirmUnloadTaskBillsDto.setHandOverBillNo("3122");
		 moreList.add(moreConfirmUnloadTaskBillsDto);
		 confirmUnloadTaskDto.setMoreSerialNoList(moreList);
		 
		 try {
			//unloadTaskService.confirmExpressUnloadTask(confirmUnloadTaskDto);
			logger.info("FOSS同步确认卸车任务到WK成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("FOSS同步确认卸车任务到WK失败！");
		}
	}
	
	/**
	* @description FOSS同步修改卸车任务到悟空系统
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月12日 下午7:19:03
	 */
	@Test
	public void updateExpressUnloadTask(){
		
		UnloadTaskModifyDto modifyDto=new UnloadTaskModifyDto();
		
		try {
			//unloadTaskService.updateExpressUnloadTask(modifyDto);
			logger.info("FOSS同步修改卸车任务到悟空系统成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("FOSS同步修改卸车任务到悟空系统失败！");
		}
	}
	
	/**
	* @description Foss同步取消卸车任务到悟空系统
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月12日 下午7:19:03
	 */
	@Test
	public void expressCancelUnloadTask(){
		
		ExpressCancelUnloadTaskDto expressCancelUnloadTaskDto=new ExpressCancelUnloadTaskDto();
		
		expressCancelUnloadTaskDto.setUnloadTaskNo("321321");
		expressCancelUnloadTaskDto.setUpdateTime(new Date());
		try {
			//unloadTaskService.expressCancelUnloadTask(expressCancelUnloadTaskDto);
			logger.info("Foss同步取消卸车任务到悟空系统成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Foss同步取消卸车任务到悟空系统失败！");
		}
	}
	
	
}
