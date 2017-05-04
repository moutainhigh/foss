package com.deppon.foss.module.base.baseinfo.test.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.esb.inteface.domain.crm.GeneralTaxpayerInfo;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgGeneralTaxpayerInfoService;
import com.deppon.foss.module.base.baseinfo.test.BaseTestCase;

/**
 * 
 * 同步一般纳税人信息 service测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:308861,date:2016-3-28 下午4:24:30,content:TODO </p>
 * @author 308861 
 * @date 2016-3-28 下午4:24:30
 * @since
 * @version
 */
public class OrgGeneralTaxpayerInfoServiceTest extends BaseTestCase{
	/**
	 * 注入IOrgGeneralTaxpayerInfoDao
	 */
	@Autowired
	private IOrgGeneralTaxpayerInfoService orgGeneralTaxpayerInfoService;

	/**
	 * 
	 * 操作 
	 * @author 308861 
	 * @date 2016-3-28 下午4:28:59
	 * @see
	 */
	@Test
	public void operationTest(){
		try {
			GeneralTaxpayerInfo info=new GeneralTaxpayerInfo();
			info.setCrmId("WcrmId");//crmId唯一标识
			info.setTaxId("HCW111");//税务登记号
			info.setBillTitle("DepponHCW");//发票抬头
			info.setBankName("中国银行");//开户行
			info.setBankNumber("10478523699");//银行账号
			info.setIsTaxpayer("Y");//是否是一般纳税人
			info.setRegTel("12345678941");//注册电话
			info.setRegAddress("上海总部");//注册地址
			info.setCreateDepartment("德邦营业部");//创建部门
			info.setOperation("1");//操作类型  1新增  2修改  3作废
			orgGeneralTaxpayerInfoService.operation(info);
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}

