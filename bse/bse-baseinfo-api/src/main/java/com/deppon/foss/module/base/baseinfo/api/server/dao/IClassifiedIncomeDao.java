package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ClassifiedIncomeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ClassifiedIncomeQueryDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
public interface IClassifiedIncomeDao {
	/**
	 * 新增重分类收入基础信息service接口
	 * @author 307196
	 *
	 */
	ClassifiedIncomeEntity addClassifiedIncome(ClassifiedIncomeEntity entity,CurrentInfo currentInfo,Date createTime,String flag);
	
    /**
     * 修改重分类收入基础信息service接口
     * @author 307196
     *
     */
	ClassifiedIncomeEntity updateClassifiedIncome(ClassifiedIncomeEntity entity,CurrentInfo currentInfo);
	

    
    /**
     * 总条数重分类收入基础信息service接口
     * @author 307196
     *
     */
    long queryTotalByCondition(ClassifiedIncomeQueryDto entity);
	
    /**
     * 分页查询重分类收入基础信息service接口
     * @author 307196
     *
     */
    List<ClassifiedIncomeEntity> queryClassifiedIncomeByCondition(ClassifiedIncomeQueryDto entity,int start, int limit);
    /**
	 * 根据产品类型和所属子公司查询
	 */
    public List<ClassifiedIncomeEntity> queryClassifiedIncomeBySubAndType(String productTypeCode,String owendSubsidiaryCode);
    /**
     * 根据ids进行查询
     * @param ids
     * @return
     */
    public List<ClassifiedIncomeEntity> queryInfosByIds(List<String> ids);
   
}