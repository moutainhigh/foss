/**   
* @Title: ComplementDaoTest.java 
* @Package com.deppon.foss.module.transfer.load.server 
* @Description: 补码dao测试类
* @author shiwei shiwei@outlook.com
* @date 2013-9-3 下午3:20:01 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.load.server;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.load.api.server.dao.IComplementDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.ComplementLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto;
import com.deppon.foss.util.UUIDUtils;

/** 
 * @ClassName: ComplementDaoTest 
 * @Description: 补码dao测试类
 * @author shiwei shiwei@outlook.com
 * @date 2013-9-3 下午3:20:01 
 *  
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class ComplementDaoTest {
	
	@Autowired
	private IComplementDao complementDao;
	
	private static final String str = "test";
	
	private static final Date date = new Date();
	
	private static final String waybillNo = "5648756924";
	
	@BeforeTransaction
	public void beforeTransaction() {

	}
	
	/**
	* @Title: addComplementLog 
	* @Description: 插入一条补码日志
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午3:43:04 
	* @param
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void addComplementLog(){
		ComplementLogEntity log = new ComplementLogEntity();
		log.setId(UUIDUtils.getUUID());
		log.setAfterOrgCode(str);
		log.setAfterOrgName(str);
		log.setBeforeOrgCode(str);
		log.setBeforeOrgName(str);
		log.setCreateDate(date);
		log.setCreateUser(str);
		log.setModifyDate(date);
		log.setModifyUser(str);
		log.setOperationOrgCode(str);
		log.setOperationOrgName(str);
		log.setOperationTime(date);
		log.setOperatorCode(str);
		log.setOperatorName(str);
		log.setWaybillNo(waybillNo);
		complementDao.addComplementLog(log);
		
	}
	
	/**
	* @Title: queryComplementLogListByWaybillNo 
	* @Description: 根据运单号查询补码日志，按时间排序
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午3:44:33 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryComplementLogListByWaybillNo(){
		complementDao.queryComplementLogListByWaybillNo(waybillNo);
		
	}
	
	/**
	* @Title: queryComplementCount 
	* @Description: 查询待补码运单的总数
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午4:16:20 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryComplementCount(){
		ComplementQueryDto queryDto = new ComplementQueryDto();
		queryDto.setWaybillNo(waybillNo);
		complementDao.queryComplementCount(queryDto);
	}
	
	/**
	* @Title: queryComplementList 
	* @Description: 查询待补码运单
	* @author shiwei shiwei@outlook.com
	* @date 2013-9-3 下午4:17:23 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void queryComplementList(){
		ComplementQueryDto queryDto = new ComplementQueryDto();
		queryDto.setWaybillNo(waybillNo);
		complementDao.queryComplementList(queryDto, 0, 1);
	}

}
