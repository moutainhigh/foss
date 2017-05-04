package com.deppon.foss.module.base.baseinfo.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressSaleDepartmentResultDto;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.ExpressPartSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class ExpressPartSalesDeptDaoTest {

	    private JdbcTemplate jdbc;
	    
	    private IExpressPartSalesDeptDao expressPartSalesDeptDao;
	    private ExpressPartSalesDeptDao entity = new ExpressPartSalesDeptDao();
	   
	    @Before
	    public void setUp() throws Exception {
		jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
		expressPartSalesDeptDao = SpringTestHelper.get().getBeanByInterface(ExpressPartSalesDeptDao.class);
	    }

	   @After
	    public void tearDown() throws Exception {
		   
	    }
	   
	   /**
	    * 查询
	    * @author foss-qiaolifeng
	    * @date 2013-9-6 下午4:18:52
	    */
	   @Ignore
	   @Test
	   public void testQueryExpressPartSalesDeptByCondition(){
		    ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto = new ExpressPartSalesDeptQueryDto();
		    expressPartSalesDeptQueryDto.setActive(FossConstants.ACTIVE);
		    expressPartSalesDeptQueryDto.setExpressPartCode("W01130500102");
		    expressPartSalesDeptQueryDto.setSalesDeptCode("W011305080605");
			
			long count = expressPartSalesDeptDao.queryTotalByCondition(expressPartSalesDeptQueryDto);
			if(count>0){
				List<ExpressPartSalesDeptResultDto> rtnList = expressPartSalesDeptDao.queryExpressPartSalesDeptByCondition(expressPartSalesDeptQueryDto, 0, 1);
				Assert.assertNull(rtnList);
			}
		   
	   }
	   
	   /**
		 * 根据营业部编码和创建时间查询快递点部信息
		 * @author foss-qiaolifeng
		 * @date 2013-9-6 下午2:47:05
		 */
		@Ignore
		@Test
		public void testQueryExpressPartSalesDeptBySalesCodeAndTime(){
			
			String salesDeptCode = "W011305080605";
			Date createTime = new Date();
			
			List<ExpressPartSalesDeptResultDto> rtnList = expressPartSalesDeptDao.queryExpressPartSalesDeptBySalesCodeAndTime(salesDeptCode, createTime);
			Assert.assertNull(rtnList);
		}
	   
		 /**
		 * 根据快递点部编码查询快递点部营业部映射关系
		 * @author foss-qiaolifeng
		 * @date 2013-9-6 下午2:47:05
		 */
		@Ignore
		@Test
		public void testQueryExpressPartSalesDeptByExpressPartCode(){
			
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto = new ExpressPartSalesDeptQueryDto();
		    expressPartSalesDeptQueryDto.setActive(FossConstants.ACTIVE);
		    expressPartSalesDeptQueryDto.setExpressPartCode("W01130500102");
			
			List<ExpressPartSalesDeptResultDto> rtnList = expressPartSalesDeptDao.queryExpressPartSalesDeptByExpressPartCode(expressPartSalesDeptQueryDto);
			Assert.assertNull(rtnList);
		}
		
		 /**
		 * 根据营业部编码列表查询营业部扩展dto
		 * @author foss-qiaolifeng
		 * @date 2013-9-6 下午2:47:05
		 */
		@Ignore
		@Test
		public void testQuerySaleDepartmentResultDtoBySalesDeptCodeList(){
			
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto = new ExpressPartSalesDeptQueryDto();
		    expressPartSalesDeptQueryDto.setActive(FossConstants.ACTIVE);
		    List<String> selectedCodeList = new ArrayList<String>();
		    selectedCodeList.add("W011305080605");
		    expressPartSalesDeptQueryDto.setSelectedCodeList(selectedCodeList);
			
		    List<ExpressSaleDepartmentResultDto> rtnList = expressPartSalesDeptDao.querySaleDepartmentResultDtoBySalesDeptCodeList(expressPartSalesDeptQueryDto);
		    Assert.assertNull(rtnList);
		}
		
		 /**
		 *  根据营业部条件查部营业部信息,多条件模糊查询
		 * @author foss-qiaolifeng
		 * @date 2013-9-6 下午2:47:05
		 */
		@Ignore
		@Test
		public void testQuerySaleDepartmentResultDtoByCondition(){
			
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto = new ExpressPartSalesDeptQueryDto();
			expressPartSalesDeptQueryDto.setSalesDeptName("百子湾营业部");
			
		    List<ExpressSaleDepartmentResultDto> rtnList = expressPartSalesDeptDao.querySaleDepartmentResultDtoByCondition(expressPartSalesDeptQueryDto);
		    Assert.assertNull(rtnList);
		}
		
		/**
		 *  根据快递点部编码、营业部编码查询有效的映射信息
		 * @author foss-qiaolifeng
		 * @date 2013-9-6 下午2:47:05
		 */
		@Ignore
		@Test
		public void testQueryResultDtoByExpressPartCodeAndSalesDeptCode(){
			
			String expressPartCode = "W01130500102"; 
			String salesDeptCode = "W011305080605"; 
			String active = FossConstants.ACTIVE;
			
			List<ExpressPartSalesDeptResultDto> rtnList = expressPartSalesDeptDao.
					queryResultDtoByExpressPartCodeAndSalesDeptCode(expressPartCode, salesDeptCode, active);
		    Assert.assertNull(rtnList);
		}
		
		/**
		 *  根据id查询快递点部营业部映射信息
		 * @author foss-qiaolifeng
		 * @date 2013-9-6 下午2:47:05
		 */
		@Ignore
		@Test
		public void testQueryInfosByIds(){
			
			List<String> ids = new ArrayList<String>();
			ids.add("c910dd4f-e0b6-4447-baa7-6b8f7a4d912c");
		
			List<ExpressPartSalesDeptEntity> rtnList = expressPartSalesDeptDao.queryInfosByIds(ids);
		    Assert.assertNull(rtnList);
		}
		
		/**
		 *  根据营业部编码\激活状态查询营业部快递点部映射关系
		 * @author foss-qiaolifeng
		 * @date 2013-9-6 下午2:47:05
		 */
		@Ignore
		@Test
		public void testQueryInfosBySalesCode(){
			
			String salesDeptCode = "W011305080605"; 
			String active = FossConstants.ACTIVE;
		
			List<ExpressPartSalesDeptEntity> rtnList = expressPartSalesDeptDao.queryInfosBySalesCode(salesDeptCode, active);
		    Assert.assertNull(rtnList);
		}
		
		
		/**
		 *  新增快递点部营业部映射信息
		 * @author foss-qiaolifeng
		 * @date 2013-9-6 下午2:47:05
		 */
		@Ignore
		@Test
		public void testAddExpressPartSalesDept(){
			ExpressPartSalesDeptEntity entity = new ExpressPartSalesDeptEntity();
			entity.setId(UUIDUtils.getUUID());// ID
			entity.setPartCode("W01130500102");// 快递点部编码
			entity.setSalesDeptCode("W011305080605");// 营业部编码
			entity.setCreateUserCode("092444");// 创建人
			entity.setModifyUserCode("092444");// 修改人
			entity.setCreateTime(new Date());// 创建时间
			entity.setModifyTime(new Date());// 修改时间
			entity.setBeginTime(new Date());//开始时间
			entity.setEndTime(new Date(
					NumberConstants.ENDTIME));//结束时间
			entity.setActive(FossConstants.ACTIVE);// 有效
			entity.setVersionNo(new Date().getTime());// 版本号
			
			int i = expressPartSalesDeptDao.addExpressPartSalesDept(entity);
			
			Assert.assertEquals(i, 1);
		}
		
		/**
		 *  修改快递点部营业部映射信息
		 * @author foss-qiaolifeng
		 * @date 2013-9-6 下午2:47:05
		 */
		@Ignore
		@Test
		public void testUpdateExpressPartSalesDeptByPartCode(){
			
			//新增
			ExpressPartSalesDeptEntity entity = new ExpressPartSalesDeptEntity();
			entity.setId(UUIDUtils.getUUID());// ID
			entity.setPartCode("W01130500102");// 快递点部编码
			entity.setSalesDeptCode("W011305080605");// 营业部编码
			entity.setCreateUserCode("092444");// 创建人
			entity.setModifyUserCode("092444");// 修改人
			entity.setCreateTime(new Date());// 创建时间
			entity.setModifyTime(new Date());// 修改时间
			entity.setBeginTime(new Date());//开始时间
			entity.setEndTime(new Date(
					NumberConstants.ENDTIME));//结束时间
			entity.setActive(FossConstants.ACTIVE);// 有效
			entity.setVersionNo(new Date().getTime());// 版本号
			expressPartSalesDeptDao.addExpressPartSalesDept(entity);
			
			//修改
			entity.setModifyTime(new Date());
			entity.setModifyUserCode("042366");
			entity.setPartCode("W011302020304");
			entity.setVersionNo(new Date().getTime());
			 
			int i = expressPartSalesDeptDao.updateExpressPartSalesDeptByPartCode(entity);
			Assert.assertEquals(i, 1);
		}
		
		/**
		 *  根据id删除快递点部营业部映射信息
		 * @author foss-qiaolifeng
		 * @date 2013-9-6 下午2:47:05
		 */
		@Ignore
		@Test
		public void testDeleteInfosByIds(){
			
			List<String> ids = new ArrayList<String>();
			ids.add("c910dd4f-e0b6-4447-baa7-6b8f7a4d912c");
		
			int i = expressPartSalesDeptDao.deleteInfosByIds(ids);
		    Assert.assertNull(i);
		}
}
