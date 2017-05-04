package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBranchSalesDeptEntity;

/**
 * 快递分部营业部映射关系Dao
 * 
 * @author foss-WeiXing
 * @date 2014-9-23 上午10:13:11
 */
public interface IExpressBranchSalesDeptDao {
	/**
	 *<P>添加单挑快递分部营业部关系<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:28:02
	 * @param entity
	 * @return
	 */
	ExpressBranchSalesDeptEntity addExpressBranchSales(ExpressBranchSalesDeptEntity entity);
	/**
	 *<P>根据ID作废关系信息<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:33:35
	 * @param entity
	 * @return
	 */
	int deleteExpressBranchSalesById(ExpressBranchSalesDeptEntity entity);
	
	/**
	 *<P>根据条件分页查询实体<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:40:30
	 * @param expressBranchCode
	 * @param expressBranchcode
	 * @return
	 */
	List<ExpressBranchSalesDeptEntity> queryExpressBranchSalesList(ExpressBranchSalesDeptEntity entity, int start, int limit);
	/**
	 *<P>查询总数<P>
	 * @author :WeiXing
	 * @date : 2014-3-26上午11:05:17
	 * @param entity
	 * @return
	 */
	long queryExpressBranchSalesCount(ExpressBranchSalesDeptEntity entity);
	/**
	 *<P>根据营业部编码查询实体列表<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:43:13
	 * @param expressBranchCode
	 * @return
	 */
	List<ExpressBranchSalesDeptEntity> queryExpressBranchSalesListbySalesCode(String expressBranchCode);
	/**
	 *<P>根据快递分部编码查询实体<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:45:42
	 * @param expressBranchCode
	 * @return
	 */
	ExpressBranchSalesDeptEntity queryExpressBranchSalesbyExpressBranchCode(String expressBranchCode);
	
	/**
	 *<P>根据快递分部编码,营业部编码查询实体<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:45:42
	 * @param expressBranchCode,salesDeptCode
	 * @return
	 */
	ExpressBranchSalesDeptEntity queryExpressBranchSalesbyExpressBranchSalesDeptCode(String expressBranchCode,String salesDeptCode);
	 
		/**
		 *<P>根据快递分部编码或营业部编码查询实体<P>
		 * @author :WeiXing
		 * @date : 2014-9-23下午3:45:42
		 * @param ExpressBranchSalesDeptEntity entity
		 * @return
		 */
		ExpressBranchSalesDeptEntity queryByExpressBranchSalesDeptCode(ExpressBranchSalesDeptEntity entity );
		 
	/**
	 * 
	 *<P>根据快递分部编码或、营业部编码动态作废实体<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午2:23:41
	 * @param deleteEntity
	 */
	ExpressBranchSalesDeptEntity deleteExpressBranchSales(ExpressBranchSalesDeptEntity deleteEntity);
}
