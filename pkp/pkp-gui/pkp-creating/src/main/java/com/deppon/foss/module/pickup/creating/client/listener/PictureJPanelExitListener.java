package com.deppon.foss.module.pickup.creating.client.listener;

import java.util.HashMap;

import javax.swing.JViewport;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.util.define.FossConstants;

public class PictureJPanelExitListener implements AncestorListener{

	private PictureWaybillEditUI ui;
	
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	public PictureJPanelExitListener(PictureWaybillEditUI ui){
		this.ui = ui;
	}
	@Override
	public void ancestorAdded(AncestorEvent event) {
		
	}
	@Override
	public void ancestorRemoved(AncestorEvent event) {
		if((event.getAncestor() instanceof PictureWaybillEditUI) && 
				(event.getAncestorParent() instanceof JViewport)){
			HashMap<String, IBinder<WaybillPanelVo>> map = ui.waybillEdit
					.getBindersMap();
			IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
			WaybillPanelVo bean = waybillBinder.getBean();
			
			if(StringUtils.isNotBlank(bean.getPictureWaybillNo())){
				WaybillPictureEntity entity = new WaybillPictureEntity();
				entity.setId(bean.getPictureWaybillNo());
				entity.setWaybillNo(bean.getWaybillNo());
				entity.setActive(FossConstants.ACTIVE);
				WaybillPictureEntity waybillPictureEntity  = waybillService.queryWaybillPictureByEntity(entity);
				if(waybillPictureEntity != null && 
						WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(waybillPictureEntity.getPendgingType())){
					WaybillPictureEntity pictureEntity = new WaybillPictureEntity();
					//如果不是空运不是现金，则还原local为空，belongOrgCode为空
					if((!StringUtil.equals(FossConstants.YES, waybillPictureEntity.getCashPayFlag()))&&(!StringUtil.equals(FossConstants.YES, waybillPictureEntity.getSpecialCustomer()))){
						//默认local为N
						pictureEntity.setLocal("N");
						pictureEntity.setOperator("");
						pictureEntity.setBelongOrgCode("");
					}
					pictureEntity.setId(bean.getPictureWaybillNo());
					pictureEntity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
					waybillService.updatePictureWaybillById(pictureEntity);
				}
			}
		}
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		
	}

}
