package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBranchSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressBranchSalesDeptVo;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 快递分部与营业部关系
 * @author WeiXing
 *
 */
public interface IExpressBranchSalesDeptService  {
	/**
	 *<P>添加实体<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午1:47:12
	 * @param entity
	 * @return
	 */
	ExpressBranchSalesDeptEntity addExpressBranchSales(ExpressBranchSalesDeptEntity entity);
	/**
	 *<P>根据id 作废<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午1:51:21
	 * @param entity
	 * @return
	 */
	int deleteExpressBranchSalesById(ExpressBranchSalesDeptEntity entity);
	/**
	 *<P>根据快递分部编码 和营业部编码作废<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午2:38:31
	 * @param entity
	 * @return
	 */
	ExpressBranchSalesDeptEntity  deleteExpressBranchByExpressBranchCodeAndSalesCode(ExpressBranchSalesDeptEntity entity);
	/**
	 *<P>根据快递分部编码作废<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:38:31
	 * @param entity
	 * @return
	 */
	ExpressBranchSalesDeptEntity deleteExpressBranchByExpressBranchCode(ExpressBranchSalesDeptEntity entity);
	/**
	 *<P>根据营业部编码作废<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:38:31
	 * @param entity
	 * @return
	 */
	ExpressBranchSalesDeptEntity deleteExpressBranchBySalesCode(ExpressBranchSalesDeptEntity entity);
	/**
	 *<P>根据条件分页查询<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午1:53:32
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<ExpressBranchSalesDeptEntity> queryExpressBranchSalesList(ExpressBranchSalesDeptEntity entity,int start,int limit);
	/**
	 *<P>根据条件查询总数<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午1:57:43
	 * @param entity
	 * @return
	 */
	long queryExpressBranchSalesCount(ExpressBranchSalesDeptEntity entity);
	/**
	 *<P>根据营业部编码查询<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午1:55:42
	 * @param entity
	 * @return
	 */
	List<ExpressBranchSalesDeptEntity> queryExpressBranchSalesBySalesCode(String salesDeptcode);
	/**
	 *<P>根据快递分部编码查询<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午1:56:38
	 * @param entity
	 * @return
	 */
	ExpressBranchSalesDeptEntity queryExpressBranchsalesByExpressBranchCode(String expressBranchCode);
	 
		/**
		 *<P>根据快递分部编码,营业部编码查询<P>
		 * @author :WeiXing
		 * @date : 2014-9-23下午1:56:38
		 * @param expressBranchCode,salesDeptCode
		 * @return
		 */
		ExpressBranchSalesDeptEntity queryExpressBranchsalesByExpressBranchSalesDeptCode(String expressBranchCode,String salesDeptCode);
		 
		/**
		 *<P>根据快递分部编码或营业部编码查询<P>
		 * @author :WeiXing
		 * @date : 2014-9-23下午1:56:38
		 * @param ExpressBranchSalesDeptEntity entity
		 * @return
		 */
		ExpressBranchSalesDeptEntity queryByExpressBranchSalesDeptCode(ExpressBranchSalesDeptEntity entity);
	/**
	 * 
	 *<P>根据id批量作废<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午6:08:31
	 * @param idList
	 * @param modifyUser
	 * @return
	 */
	int deleteExpressBranchSalesByIdList(List<String> idList,
			CurrentInfo modifyUser);
	/**
	 *<P>新增实体列表<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午7:09:26
	 * @param addDeptEntities
	 * @param currentInfo
	 */
	List<ExpressBranchSalesDeptEntity> addExpressBranchSalesList(
			List<ExpressBranchSalesDeptEntity> addDeptEntities,
			String modifyUser);
	/**
	 *<P>修改映射关系<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午2:13:54
	 * @param vo
	 * @param modifyUser
	 * @return
	 */
	List<ExpressBranchSalesDeptEntity> updateExpressBranchSalesList(ExpressBranchSalesDeptVo vo,
			String modifyUser);
}
