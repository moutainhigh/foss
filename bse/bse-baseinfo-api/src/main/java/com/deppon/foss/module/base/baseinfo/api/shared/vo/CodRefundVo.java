package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity;

/**
 * (CodRefund的VO类)
 * @author 187862-dujunhui
 * @date 2014-7-16 上午8:18:19
 * @since
 * @version v1.0
 */
public class CodRefundVo implements Serializable {

	/**
	 * （序列化）
	 */
	private static final long serialVersionUID = -5242412564776134693L;
	
	//代收货款打包退款基础资料实体
	private CodRefundEntity codRefundEntity;
	
	//代收货款打包退款基础资料实体链表
	private List<CodRefundEntity> codRefundEntityList;
	
	//信息批量删除的codeList
	private String[] codeList;

	/**
	 * @return  the codRefundEntity
	 */
	public CodRefundEntity getCodRefundEntity() {
		return codRefundEntity;
	}

	/**
	 * @param codRefundEntity the codRefundEntity to set
	 */
	public void setCodRefundEntity(CodRefundEntity codRefundEntity) {
		this.codRefundEntity = codRefundEntity;
	}

	/**
	 * @return  the codRefundEntityList
	 */
	public List<CodRefundEntity> getCodRefundEntityList() {
		return codRefundEntityList;
	}

	/**
	 * @param codRefundEntityList the codRefundEntityList to set
	 */
	public void setCodRefundEntityList(List<CodRefundEntity> codRefundEntityList) {
		this.codRefundEntityList = codRefundEntityList;
	}

	/**
	 * @return  the codeList
	 */
	public String[] getCodeList() {
		return codeList;
	}

	/**
	 * @param codeList the codeList to set
	 */
	public void setCodeList(String[] codeList) {
		this.codeList = codeList;
	}
}
