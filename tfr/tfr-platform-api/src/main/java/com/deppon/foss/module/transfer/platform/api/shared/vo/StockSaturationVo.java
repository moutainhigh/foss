package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationEntity;


/**
 * 
* @description 仓库饱和度
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月28日 下午3:44:57
*/
public class StockSaturationVo implements java.io.Serializable {

	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:45:07
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	/**
	 * 部门所在外场编码
	* @fields parentTfrCtrCode
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:46:22
	* @version V1.0
	*/
	private String outfieldCode;
	
	
	
	/**
	 * 部门所在外场名称
	* @fields parentTfrCtrName
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:46:19
	* @version V1.0
	*/
	private String outfieldName;

	
	
	/**
	 * 仓库饱和度对象
	* @fields stockSaturationEntity
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:23:19
	* @version V1.0
	*/
	private StockSaturationEntity stockSaturationEntity;
	
	
	/**
	 * 仓库饱和度集合
	* @fields stockSaturationList
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:23:21
	* @version V1.0
	*/
	private List<StockSaturationEntity> stockSaturationList;


	
	/**
	* @description  部门所在外场编码
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:59:01
	*/
	public String getOutfieldCode() {
		return outfieldCode;
	}



	
	/**
	* @description  部门所在外场编码
	* @param outfieldCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:59:04
	*/
	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}



	
	/**
	* @description  部门所在外场名称
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:59:06
	*/
	public String getOutfieldName() {
		return outfieldName;
	}

	
	/**
	* @description 部门所在外场名称
	* @param outfieldName
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:59:09
	*/
	public void setOutfieldName(String outfieldName) {
		this.outfieldName = outfieldName;
	}




	
	/**
	* @description 仓库饱和度对象
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:24:01
	*/
	public StockSaturationEntity getStockSaturationEntity() {
		return stockSaturationEntity;
	}




	
	/**
	* @description 仓库饱和度对象
	* @param stockSaturationEntity
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:24:04
	*/
	public void setStockSaturationEntity(StockSaturationEntity stockSaturationEntity) {
		this.stockSaturationEntity = stockSaturationEntity;
	}




	
	/**
	* @description 仓库饱和度集合
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:24:07
	*/
	public List<StockSaturationEntity> getStockSaturationList() {
		return stockSaturationList;
	}




	
	/**
	* @description 仓库饱和度集合
	* @param stockSaturationList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:24:10
	*/
	public void setStockSaturationList(
			List<StockSaturationEntity> stockSaturationList) {
		this.stockSaturationList = stockSaturationList;
	}

	
	

}
