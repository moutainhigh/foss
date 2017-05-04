package com.deppon.foss.module.transfer.load.server;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageScanDetailDto;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class PDAExpressPackageServiceTest {
	@Autowired
	IPDAExpressPackageService pdaExpressPackageService;
	@Test
	public void queryUnFinishedPackage(){
		pdaExpressPackageService.queryUnFinishedPackage("3434", new Date(), new Date());
	}
	@Test
	public void createPackage(){
		pdaExpressPackageService.createPackage("sdfds","sfdds","sdfds","sdfdsf","sdfdsf",new Date(),"");
	}
	@Test
	public void refrushPackageDetail(){
		pdaExpressPackageService.refrushPackageDetail("3434");
	}
	@Test
	public void moreGoodsVerify(){
		pdaExpressPackageService.moreGoodsVerify("3434", "123213", "12321");
	}
	@Test
	public void scan(){
		ExpressPackageScanDetailDto scanDto = new ExpressPackageScanDetailDto();
		pdaExpressPackageService.scan(scanDto);
	}
	@Test
	public void cancelPackage(){
		pdaExpressPackageService.cancelPackage("3434", "123213", "12321", new Date());
	}
	@Test
	public void submitPackage(){
		pdaExpressPackageService.submitPackage("3434", "123213", "12321", new Date());
	}
}
