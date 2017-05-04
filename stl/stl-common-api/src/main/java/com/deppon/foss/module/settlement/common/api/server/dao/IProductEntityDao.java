package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.ProductEntity;


/**
 * 产品类型公共DAO接口类
 * @author 272005-foss-huanglewei
 * @date 2015-08-24 下午3:56:23
 */
public interface IProductEntityDao {

    /**
     * 查询快递产品类型
	 * @author 272005-foss-huanglewei
	 * @date 2015-08-24 下午3:58:29
     * @param entity 产品类型
     * @return
     */
	public List<ProductEntity> queryPackageProduct();
    
    /**
     * 查询快递产品代码
	 * @author 272005-foss-huanglewei
	 * @date 2015-09-07 下午4:41:14
     * @param entity 产品类型
     * @return
     */
	public List<String> queryPackageProductCode();
    
    /**
     * 查询快递产品名称
	 * @author 272005-foss-huanglewei
	 * @date 2015-09-07 下午4:41:14
     * @param entity 产品类型
     * @return
     */
	public List<String> queryPackageProductName();
    
    /**
	 * 查询所有产品类型
	 * @author foss-huanglewei
	 * @date 22015-08-24 下午4:41:14
	 * @return
	 * @see
	 */
	public List<ProductEntity> queryAllProduct();

    /**
	 * 查询所有产品代码
	 * @author foss-huanglewei
	 * @date 22015-09-07 下午4:41:14
	 * @return
	 * @see
	 */
	public List<String> queryAllProductCode();
	
    /**
	 * 查询所有产品名称
	 * @author foss-huanglewei
	 * @date 22015-09-07 下午4:41:14
	 * @return
	 * @see
	 */
	public List<String> queryAllProductName();
	
	/**
	 * 查询所有产品名称
	 * @author foss-huanglewei
	 * @date 22015-09-07 下午4:41:14
	 * @return
	 * @see
	 */
	public String queryProductNameByCode(String productCode);
}
