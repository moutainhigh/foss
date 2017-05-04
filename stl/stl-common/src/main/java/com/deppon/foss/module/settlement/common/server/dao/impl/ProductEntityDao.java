package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IProductEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.ProductEntity;

/**
 * 产品类型Dao
 * 
 * @author 272005-foss-huanglewei
 * @date 2015-08-25 上午8:42:31
 * @since
 * @version
 */
public class ProductEntityDao extends iBatis3DaoImpl implements IProductEntityDao{

	private static final String NAMESPACE = "foss.stl.ProductEntityDao.";// 命名空间路径
	
	/**
	 * 查询快递产品
	 * 
	 * @author 272005-foss-huanglewei
	 * @date 2015-08-25 上午8:42:31
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductEntity> queryPackageProduct() {
		return  (List<ProductEntity>) this.getSqlSession().selectList(NAMESPACE + "queryPackageProduct");
	}

	/**
	 * 查询快递所有产品
	 * 
	 * @author 272005-foss-huanglewei
	 * @date 2015-08-25 上午8:52:14
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductEntity> queryAllProduct() {
		return  (List<ProductEntity>) this.getSqlSession().selectList(NAMESPACE + "queryAllProduct");
	}

	/**
	 * 查询快递产品代码
	 * 
	 * @author 272005-foss-huanglewei
	 * @date 2015-09-07 下午4:42:31
	 * @return
	 */
	@Override
	public List<String> queryPackageProductCode() {
        List<ProductEntity> listP = queryPackageProduct();
        List<String> listS = new ArrayList<String>();
		for (ProductEntity productEntity : listP) {
			listS.add(productEntity.getProductCode());
		}
		return listS;
	}

	/**
	 * 查询快递产品名称
	 * 
	 * @author 272005-foss-huanglewei
	 * @date 2015-09-07 下午4:42:31
	 * @return
	 */
	@Override
	public List<String> queryPackageProductName() {
        List<ProductEntity> listP = queryPackageProduct();
        List<String> listS = new ArrayList<String>();
		for (ProductEntity productEntity : listP) {
			listS.add(productEntity.getProductName());
		}
		return listS;
	}

	/**
	 * 查询所有产品代码
	 * 
	 * @author 272005-foss-huanglewei
	 * @date 2015-09-07 下午4:42:31
	 * @return
	 */
	@Override
	public List<String> queryAllProductCode() {
        List<ProductEntity> listP = queryAllProduct();
    	List<String> listS = new ArrayList<String>();
		for (ProductEntity productEntity : listP) {
			listS.add(productEntity.getProductCode());
		}
		return listS;
	}

	/**
	 * 查询所有产品名称
	 * 
	 * @author 272005-foss-huanglewei
	 * @date 2015-09-07 下午4:42:31
	 * @return
	 */
	@Override
	public List<String> queryAllProductName() {
        List<ProductEntity> listP = queryAllProduct();
    	List<String> listS = new ArrayList<String>();
		for (ProductEntity productEntity : listP) {
			listS.add(productEntity.getProductName());
		}
		return listS;
	}
	
	/**
	 * 查询产品名称通过产品代码
	 * 
	 * @author 272005-foss-huanglewei
	 * @date 2015-09-07 下午4:42:31
	 * @return
	 */
	@Override
	public String queryProductNameByCode(String productCode) {
		return  (String) this.getSqlSession().selectOne(NAMESPACE + "queryProductName", productCode);
	}

}
