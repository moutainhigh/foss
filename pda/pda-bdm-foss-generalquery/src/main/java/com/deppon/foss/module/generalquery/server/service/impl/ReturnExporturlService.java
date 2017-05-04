package com.deppon.foss.module.generalquery.server.service.impl;

import com.deppon.foss.module.generalquery.server.dao.IKnowledgeDao;
import com.deppon.foss.module.generalquery.shared.domain.ChinabdVerEntity;
import com.deppon.foss.module.generalquery.shared.domain.ExDelieryAddExportEntity;
import com.deppon.foss.module.generalquery.shared.domain.ReturnExporturlEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.GeneralQueryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
/**
 * China包自动更新
 * @author 245955
 *
 */
public class ReturnExporturlService implements IBusinessService<ReturnExporturlEntity, ChinabdVerEntity>{
	private IKnowledgeDao knowledgeDao;
	@Override
	public ChinabdVerEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		ChinabdVerEntity chinabdVer=JsonUtil.parseJsonToObject(ChinabdVerEntity.class, asyncMsg.getContent());
		return chinabdVer;
	}

	@Override
	public ReturnExporturlEntity service(AsyncMsg asyncMsg, ChinabdVerEntity param)
			throws PdaBusiException {
		ExDelieryAddExportEntity export=knowledgeDao.returnExportUrl();
		String time=String.valueOf(export.getCreateDate().getTime());
		ReturnExporturlEntity exportUrl=new ReturnExporturlEntity();
		if (param.getChinabdVer().equals(time)) {
			exportUrl.setReqUpgrade("N");
		}else{
			exportUrl.setReqUpgrade("Y");
		}
		exportUrl.setUpdUrl(export.getExportUrl());
		return exportUrl;
	}

	@Override
	public String getOperType() {
		return GeneralQueryConstant.OPER_TYPE_AID_CHINA_EXPORTURL.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setKnowledgeDao(IKnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}

}