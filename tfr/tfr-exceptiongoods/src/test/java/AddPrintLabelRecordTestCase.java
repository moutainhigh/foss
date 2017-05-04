

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.GoodsLabelPrintDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:com/deppon/foss/module/transfer/exceptiongoods/server/test/spring-test.xml",
		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/spring.xml"
		})
public class AddPrintLabelRecordTestCase {
	
	@Autowired
	private IPrintLabelDao printLabelDao;
	
	@Autowired
	private IPrintLabelService printLabelService;
	
	/**
	 * 测试dao新增方法
	 */
	@Test
	public void testAdd(){
		PrintLabelEntity printLabelEntity = new PrintLabelEntity();
		printLabelEntity.setPrintUserCode("045923");
		printLabelEntity.setPrintUserName("石巍");
		printLabelEntity.setOrgCode("W1234567");
		printLabelEntity.setOrgName("中转开发组");
		printLabelEntity.setPrintTime(new Date());
		printLabelEntity.setWaybillNo("456789123");
		printLabelEntity.setSerialNo("0001");
		
		printLabelDao.addPrintLabel(printLabelEntity);
	}
	
	/**
	 * 测试service新增方法
	 */
	@Test
	public void testAddService(){
		PrintLabelEntity printLabelEntity = new PrintLabelEntity();
		printLabelEntity.setPrintUserCode("045923");
		printLabelEntity.setPrintUserName("石巍");
		printLabelEntity.setPrintTime(new Date());
		printLabelEntity.setWaybillNo("456789123");
		printLabelEntity.setSerialNo("0001");
		
		printLabelService.addPrintLabel(printLabelEntity);
	}
	
	/**
	 * 测试service查询方法
	 */
	@Test
	public void testQuery(){
		List<GoodsLabelPrintDto> list = printLabelService.queryLabelPrintByWaybillNoDetail("456789123");
		System.out.println(1);
	}
	
}
