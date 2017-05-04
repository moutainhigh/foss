package com.deppon.foss.module.settlement.common.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableDService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableDEntity;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;

/**
 * 应收单明细Service 测试类
 * @author 黄乐为
 * @date 2016-1-11 上午8:55:06
 */
public class BillReceivableDServiceTest extends BaseTestCase{
	@Autowired
	IBillReceivableDService billReceivableDService;
	List<String> list = new ArrayList<String>(); 
	
	private BillReceivableDEntity getBillReceivableDEntity(){
		BillReceivableDEntity entity=new BillReceivableDEntity();
		entity.setAmount(new BigDecimal(200));
		entity.setActive("Y");
		entity.setReceivableNo("11111");
		entity.setSourceBillNo("22222");
		entity.setReceivableType("OR");
		return entity;
		
	}

	/**
	 * 生成应收单明细 测试
	 * @author 黄乐为
	 * @date 2016-1-11 上午8:55:45
	 */
	@Test
	public void testAdd(){
		BillReceivableDEntity entity=this.getBillReceivableDEntity();
		this.billReceivableDService.add(entity);
	}
	
	/**
	 * 生成多条应收单明细 测试
	 * @author 黄乐为
	 * @date 2016-1-12 上午9:57:02
	 */
	@Test
	public void testAddList(){
		List<BillReceivableDEntity> list = new ArrayList<BillReceivableDEntity>();
		list.add(getBillReceivableDEntity());
		this.billReceivableDService.addList(list);
	}
	
	@Test
	public void testqueryByReceivableNOs(){
		list.add("11111");
		this.billReceivableDService.add(getBillReceivableDEntity());
		List<BillReceivableDEntity> entityList = new ArrayList<BillReceivableDEntity>();
		entityList = this.billReceivableDService.queryByReceivableNOs(list, "Y");
		for (BillReceivableDEntity billReceivableDEntity : entityList) {
			System.out.println("ID:"+billReceivableDEntity.getId()
					+"receivableNo"+billReceivableDEntity.getReceivableNo());
		}
	}
	
	@Test
	public void testqueryBySourceBillNOs(){
		list.add("22222");
		this.billReceivableDService.add(getBillReceivableDEntity());
		List<BillReceivableDEntity> entityList = new ArrayList<BillReceivableDEntity>();
		entityList = this.billReceivableDService.queryBySourceBillNOs(list, "Y");
		for (BillReceivableDEntity billReceivableDEntity : entityList) {
			System.out.println("ID:"+billReceivableDEntity.getId()
					+"sourceBillNo"+billReceivableDEntity.getSourceBillNo());
		}
	}
}
