package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.util.List;

/**   
 * @ClassName FreightEffectEntity  
 * @Description  返回所有快递产品的运费  
 * @author  092038 张贞献  
 * @date 2015-5-6    
 */ 
public class FreightEffectEntity {
	private List<FreightResEntity>  freightResEntitys;
	private String product;
	public List<FreightResEntity> getFreightResEntitys() {
		return freightResEntitys;
	}
	public void setFreightResEntitys(List<FreightResEntity> freightResEntitys) {
		this.freightResEntitys = freightResEntitys;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	
}
