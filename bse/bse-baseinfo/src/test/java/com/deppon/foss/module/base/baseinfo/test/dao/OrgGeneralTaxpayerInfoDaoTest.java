package com.deppon.foss.module.base.baseinfo.test.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgGeneralTaxpayerInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity;
import com.deppon.foss.module.base.baseinfo.test.BaseTestCase;

/**
 * 
 *  同步一般纳税人信息 Dao测试
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:308861,date:2016-3-28 下午4:11:24,content:TODO </p>
 * @author 308861 
 * @date 2016-3-28 下午4:11:24
 * @since
 * @version
 */
public class OrgGeneralTaxpayerInfoDaoTest extends BaseTestCase{
	/**
	 * 注入IOrgGeneralTaxpayerInfoDao
	 */
	@Autowired
	private IOrgGeneralTaxpayerInfoDao orgGeneralTaxpayerInfoDao;
	
	/**
	 * 
	 * 新增
	 * @author 308861 
	 * @date 2016-3-28 下午4:14:56
	 * @see
	 */
	@Test
	public void addGeneralTaxpayerInfoTest(){
		try {
			GeneralTaxpayerInfoEntity entity=new GeneralTaxpayerInfoEntity();
			entity.setCrmId("383721");//crmId唯一标识
			entity.setTaxId("HCW111");//税务登记号
			entity.setBillTitle("DepponHCW");//发票抬头
			entity.setBankName("中国银行");//开户行
			entity.setBankNumber("10478523699");//银行账号
			entity.setIsTaxpayer("Y");//是否是一般纳税人
			entity.setRegTel("12345678941");//注册电话
			entity.setRegAddress("上海总部");//注册地址
			entity.setCreateDepartment("德邦营业部");//创建部门
			GeneralTaxpayerInfoEntity e=orgGeneralTaxpayerInfoDao.
					addGeneralTaxpayerInfo(entity);
			if(e != null){
				Assert.assertNotNull(e);
			}
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	/**
	 * 
	 * 修改 
	 * @author 308861 
	 * @date 2016-3-28 下午4:16:37
	 * @see
	 */
	@Test
	public void updateTaxpayerInfoTest(){
		try {
			GeneralTaxpayerInfoEntity entity=new GeneralTaxpayerInfoEntity();
			entity.setCrmId("383721");
			entity.setBankName("修改银行名称");
			GeneralTaxpayerInfoEntity e=orgGeneralTaxpayerInfoDao.
					updateTaxpayerInfo(entity);
			if(e != null){
				Assert.assertNotNull(e);
			}
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	/**
	 * 
	 * 作废 
	 * @author 308861 
	 * @date 2016-3-28 下午4:21:04
	 * @see
	 */
	@Test
	public void deleteTaxpayerInfoTest(){
		try {
			GeneralTaxpayerInfoEntity entity=new GeneralTaxpayerInfoEntity();
			entity.setCrmId("383721");
			GeneralTaxpayerInfoEntity e=orgGeneralTaxpayerInfoDao.
					deleteTaxpayerInfo(entity);
			if(e != null){
				Assert.assertNotNull(e);
			}
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	/**
	 * 
	 * 根据CRMID查询
	 * @author 308861 
	 * @date 2016-3-28 下午4:23:00
	 * @see
	 */
	@Test
	public void queryTaxpayerInfoByIdTest(){
		try {
			String crmId="383721";
			GeneralTaxpayerInfoEntity e=orgGeneralTaxpayerInfoDao.
					queryTaxpayerInfoById(crmId);
			if(e != null){
				Assert.assertNotNull(e);
			}
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}

