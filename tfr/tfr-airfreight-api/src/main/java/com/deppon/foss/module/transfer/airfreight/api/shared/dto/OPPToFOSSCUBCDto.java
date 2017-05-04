package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;

public class OPPToFOSSCUBCDto {
	/**
	 * 合大票清单
	 */
	private AirPickupbillEntity oldAirPickupbillEntity;
	/**
	 * 合大票清单
	 */
	private AirPickupbillEntity newAirPickupbillEntity;
	//修改列表,用于调用结算接口
	List<AirPickupbillDetailEntity> stlModifyList ;
	
	//当前登录人
			private CurrentInfo currentInfo;

			

			public AirPickupbillEntity getOldAirPickupbillEntity() {
				return oldAirPickupbillEntity;
			}

			public void setOldAirPickupbillEntity(AirPickupbillEntity oldAirPickupbillEntity) {
				this.oldAirPickupbillEntity = oldAirPickupbillEntity;
			}

			public AirPickupbillEntity getNewAirPickupbillEntity() {
				return newAirPickupbillEntity;
			}

			public void setNewAirPickupbillEntity(AirPickupbillEntity newAirPickupbillEntity) {
				this.newAirPickupbillEntity = newAirPickupbillEntity;
			}

			public List<AirPickupbillDetailEntity> getStlModifyList() {
				return stlModifyList;
			}

			public void setStlModifyList(List<AirPickupbillDetailEntity> stlModifyList) {
				this.stlModifyList = stlModifyList;
			}

			public CurrentInfo getCurrentInfo() {
				return currentInfo;
			}

			public void setCurrentInfo(CurrentInfo currentInfo) {
				this.currentInfo = currentInfo;
			}
			
}
