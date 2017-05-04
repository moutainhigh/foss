package com.deppon.foss.module.base.baseinfo.api.server.dao;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesExpenseMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SalesExpenseMappingQueryDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
/**
 * 营业部与外请车费用承担部门映射信息Dao接口
 * @author 307196
 *
 */
public interface ISalesExpenseMappingDao {
	/**
	 * 新增营业部与外请车费用承担部门映射信息Dao接口
	 * @author 307196
	 *
	 */
	int addSalesExpenseMapping(SalesExpenseMappingEntity entity,CurrentInfo currentInfo);
	
    /**
     * 修改营业部与外请车费用承担部门映射信息Dao接口
     * @author 307196
     *
     */
	int updateSalesExpenseMapping(SalesExpenseMappingEntity entity,CurrentInfo currentInfo);
    /**
     * 总条数营业部与外请车费用承担部门映射信息Dao接口
     * @author 307196
     *
     */
    long queryTotalByCondition(SalesExpenseMappingQueryDto entity);
	
    /**
     * 分页查询营业部与外请车费用承担部门映射信息Dao接口
     * @author 307196
     *
     */
    List<SalesExpenseMappingEntity> querySalesExpenseMappingByCondition(SalesExpenseMappingQueryDto entity,int start, int limit);
    /**
	 * 根据营业部编码和预算承担部门编码查询
	 */
    public List<SalesExpenseMappingEntity> querySalesExpenseMappingBySubAndType(String businessDepartmentCode,String budgetDepartmentCode);
    /**
     * 根据ids进行查询
     * @param ids
     * @return
     */
    public List<SalesExpenseMappingEntity> queryInfosByIds(List<String> ids);
    /**
  	 * 根据营业部编码
  	 */
    public List<SalesExpenseMappingEntity> queryByBusinessDepart(String businessDepartmentCode);
   
}