package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;

public class AirChangeInventoryCUBCDto {
	//合大票清单
	//删除实体
			private AirPickupbillEntity oldMaster;
			//删除实体
			private AirPickupbillEntity newMaster;
			//给CUBC的数据实体
			List<AirPickupbillDetailEntity> addedDetails;
			//当前登录人
			List<AirPickupbillDetailEntity> modifiedDetails;
			
			List<String> removedDetails;
		
			
			
			//当前登录人
			private CurrentInfo currentInfo;

			public AirPickupbillEntity getOldMaster() {
				return oldMaster;
			}

			public void setOldMaster(AirPickupbillEntity oldMaster) {
				this.oldMaster = oldMaster;
			}

			public AirPickupbillEntity getNewMaster() {
				return newMaster;
			}

			public void setNewMaster(AirPickupbillEntity newMaster) {
				this.newMaster = newMaster;
			}

			public List<AirPickupbillDetailEntity> getAddedDetails() {
				return addedDetails;
			}

			public void setAddedDetails(List<AirPickupbillDetailEntity> addedDetails) {
				this.addedDetails = addedDetails;
			}

			public List<AirPickupbillDetailEntity> getModifiedDetails() {
				return modifiedDetails;
			}

			public void setModifiedDetails(List<AirPickupbillDetailEntity> modifiedDetails) {
				this.modifiedDetails = modifiedDetails;
			}

			public List<String> getRemovedDetails() {
				return removedDetails;
			}

			public void setRemovedDetails(List<String> removedDetails) {
				this.removedDetails = removedDetails;
			}

			public CurrentInfo getCurrentInfo() {
				return currentInfo;
			}

			public void setCurrentInfo(CurrentInfo currentInfo) {
				this.currentInfo = currentInfo;
			}
			
			
			
}
