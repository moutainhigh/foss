package com.deppon.foss.module.settlement.common.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IVtsOutAdjustmentDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.VtsOutAdjustmentEntity;


public class VtsOutAdjustmentDao extends iBatis3DaoImpl implements IVtsOutAdjustmentDao{

	/**
	 * 整车费用调整DaoImpl
	 * @author 340403 foss
	 * @createTime 2016年9月20日 下午3:20:48
	 */
	
		private static final String NAMESPACES = "foss.stl.TVtsOutAdjustmentDao.";// 命名空间路径

		/**
		 * 插入
		 * @author 340403 foss
		 * @createTime 2016年9月20日 下午3:18:41
		 * @param vtsOutAdjustmentEntity
		 * @return
		 */
		public int insert(VtsOutAdjustmentEntity vtsOutAdjustmentEntity) {
			return getSqlSession().insert(NAMESPACES+"insert",vtsOutAdjustmentEntity);
		}
	}


