package com.deppon.foss.module.transfer.departure.server.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.departure.api.server.service.IQueryDriverByVehicleNoService;

/**
 * 
 * 通知客户DAO测试类
 * 
 * @author ibm-wangfei
 * @date Oct 24, 2012 5:06:00 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath*:com/deppon/foss/module/departrue/test/META-INF/spring.xml" })
@TransactionConfiguration
@Transactional
public class QueryDriverByVehicleNoServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests
{

	@Autowired
	private IQueryDriverByVehicleNoService queryDriverByVehicleNoService = null;

	@BeforeTransaction
	public void beforeTransaction()
	{
		queryDriverByVehicleNoService.queryDriverInfoByVehicleNo("沪C00001"); 
	}

	/**
	 *  
	 * 测试新增运单客户通知记录
	 * 
	 * @author ibm-liubinbin
	 * @date Oct 24, 2012 5:29:55 PM
	 */
	@Test
	@Transactional
	public void testWriteDepartData()
	{
		// sharedService.queryDriverInfoByVehicleNo("123");
	}

//	public static void main(String args[]) throws UnsupportedEncodingException
//	{
//		isCompletedByDisplace();
//	}
//	
//	/**
//	 * 
//	 * 通过移位的方式判断该操作是否已经完成
//	 * @author foss-liubinbin(for IBM)
//	 * @throws UnsupportedEncodingException 
//	 * @date 2013-1-24 下午2:35:01
//	 */
//	private static String isCompletedByDisplace() throws UnsupportedEncodingException
//	{
//		String str1 = "0101";
//		String str2 = "0111";
//		char[] stra1  = str1.toCharArray();
//		char[] stra2  = str2.toCharArray();
//		char[] temp = new char[stra1.length];
//		for(int i=0;i<stra1.length;i++)
//		{
//			temp[i] = (char) ((stra1[i])&(stra2[i]));
//			System.out.println(temp[i]);
//		}
//		System.out.println(String.valueOf(temp));
//		return temp.toString();
//	}

}
