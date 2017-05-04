package com.deppon.pda.bdm.module.foss.unload.shared.domain.partner;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderSerialNoDetailEntity;
/**
 * 
 * @ClassName: WaybillDetailResult 
 * @Description: TODO(运单明细返回结果实体) 
 * @author &268974  wangzhili
 * @date 2016-2-22 上午8:55:40 
 *
 */
public class WaybillDetailResult implements Serializable{
	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	
	private List<PDAOrderSerialNoDetailEntity> PDAOrderSerialNoDetailEntitys;

	public List<PDAOrderSerialNoDetailEntity> getPDAOrderSerialNoDetailEntitys() {
		return PDAOrderSerialNoDetailEntitys;
	}

	public void setPDAOrderSerialNoDetailEntitys(
			List<PDAOrderSerialNoDetailEntity> pDAOrderSerialNoDetailEntitys) {
		PDAOrderSerialNoDetailEntitys = pDAOrderSerialNoDetailEntitys;
	}
	

}
