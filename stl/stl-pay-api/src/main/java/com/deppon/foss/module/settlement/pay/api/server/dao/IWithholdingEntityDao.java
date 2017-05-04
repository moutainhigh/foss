package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.WithholdingDto;

/**
 * @author 045738
 * @描述：预提记录Dao
 */
public interface IWithholdingEntityDao {
	/**
	 * 功能：根据传入的单号，类型，和预提状态来查询。
	 * 		单号、类型为必填项，预提状态为非必填
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return
	 */
	public List<WithholdingEntity> selectByNosAndType(List<String> billNos,String billType,List<String> status,String active);
	
	/**
	 * 功能：新增预提记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-23
	 * @return
	 */
	public int insert(WithholdingEntity entity);
	
	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 105762
	 * @date 2014-7-10 下午5:43:24
	 * @param dto
	 * @return
	 * @see
	 */
	int wipePayableWorkflowNo(WithholdingDto dto);

	/**
	 * <p>预提成功</p> 
	 * @author 105762
	 * @date 2014-7-11 下午5:37:13
	 * @param en
	 * @return
	 * @see
	 */
	int updateWithholdingSuccessByWorkflowNo(WithholdingEntity en);

	/**
	 * <p>预提失败</p> 
	 * @author 105762
	 * @date 2014-7-11 下午5:37:18
	 * @param en
	 * @return
	 * @see
	 */
	int updateWithholdingFailedByWorkflowNo(WithholdingEntity en);
}
