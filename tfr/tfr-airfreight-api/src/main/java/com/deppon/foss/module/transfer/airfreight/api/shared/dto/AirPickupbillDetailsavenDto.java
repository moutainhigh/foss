package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;

public class AirPickupbillDetailsavenDto {

			//合大票清单
			private AirPickupbillEntity airPickupbillEntity;
			//修改列表,用于调用结算接口
			List<AirPickupbillDetailEntity> stlModifyList;
			//删除列表,用于调用结算接口
			List<String> stlDeleteList;
			//新增列表,用于调用结算接口
			List<AirPickupbillDetailEntity> stlAddList;
			
			
			public List<AirPickupbillDetailEntity> getStlAddList() {
				return stlAddList;
			}
			public void setStlAddList(List<AirPickupbillDetailEntity> stlAddList) {
				this.stlAddList = stlAddList;
			}
			public List<String> getStlDeleteList() {
				return stlDeleteList;
			}
			public void setStlDeleteList(List<String> stlDeleteList) {
				this.stlDeleteList = stlDeleteList;
			}
			public AirPickupbillEntity getAirPickupbillEntity() {
				return airPickupbillEntity;
			}
			public void setAirPickupbillEntity(AirPickupbillEntity airPickupbillEntity) {
				this.airPickupbillEntity = airPickupbillEntity;
			}
			public List<AirPickupbillDetailEntity> getStlModifyList() {
				return stlModifyList;
			}
			public void setStlModifyList(List<AirPickupbillDetailEntity> stlModifyList) {
				this.stlModifyList = stlModifyList;
			}
			
			
			
}
