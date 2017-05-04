package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import java.util.List;

import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctPdaDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdBusinessAreasEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdPartSalesDeptEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.kdPartSalesAreaEntitys;
/**
 * @description 快递员当前城市的所有营业区及对应营业部
 * @author 268974
 * @Date:2015-12-17 comment:2016-01-05日常版本需求
 */
public class QueryCourierAllSalesDepartmentService implements IBusinessService<KdBusinessAreasEntity, kdPartSalesAreaEntitys>{

	private IAcctPdaDao acctPdaDao;
    
    public void setAcctPdaDao(IAcctPdaDao acctPdaDao) {
        this.acctPdaDao = acctPdaDao;
    }
	@Override
	public kdPartSalesAreaEntitys parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		kdPartSalesAreaEntitys kdAreaEntity = JsonUtil.parseJsonToObject(kdPartSalesAreaEntitys.class, asyncMsg.getContent());
		return kdAreaEntity;
	}

	
	@Override
	public KdBusinessAreasEntity service(AsyncMsg asyncMsg,
			kdPartSalesAreaEntitys param) throws PdaBusiException {
		KdBusinessAreasEntity entity = new KdBusinessAreasEntity();
		try{
			List<kdPartSalesAreaEntitys> areas = null;
		String areaCode = param.getAreaCode();
		if(areaCode==null||areaCode.length() == 0){
			areas = acctPdaDao.queryCourierAllSalesArea(asyncMsg.getUserCode());
			entity  = acctPdaDao.queryDefaultAreaCode(asyncMsg.getUserCode());
			List<KdPartSalesDeptEntity> list = acctPdaDao.queryAllSalesDept(entity.getDefaultAreaCode());
			entity.setKdPartSalesAreaEntitys(areas);
			entity.setKdPartSalesDeptEntitys(list);
		} else {
			List<KdPartSalesDeptEntity> list = acctPdaDao.queryAllSalesDept(areaCode);
			entity.setKdPartSalesDeptEntitys(list);	
		}
		return entity;
		} catch(Exception e){
			throw new FossInterfaceException(null,"数据库查询出现异常");
		}
	}

	
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_EXP_RCV_QUDE_YINGYEQU_YINGYEBU.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

}
