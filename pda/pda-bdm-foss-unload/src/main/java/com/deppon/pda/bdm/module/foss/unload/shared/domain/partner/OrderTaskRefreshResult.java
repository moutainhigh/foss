package com.deppon.pda.bdm.module.foss.unload.shared.domain.partner;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskEntity;
/**
 * 
 * @ClassName: OrderTaskRefreshResult 
 * @Description: TODO(点单任务刷新任务实体) 
 * @author &268974  wangzhili
 * @date 2016-2-22 上午8:57:28 
 *
 */
public class OrderTaskRefreshResult implements Serializable{
	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	private List<PDAOrderTaskEntity> PDAOrderTaskEntitys;

	public List<PDAOrderTaskEntity> getPDAOrderTaskEntitys() {
		return PDAOrderTaskEntitys;
	}

	public void setPDAOrderTaskEntitys(List<PDAOrderTaskEntity> pDAOrderTaskEntitys) {
		PDAOrderTaskEntitys = pDAOrderTaskEntitys;
	}
	

}
