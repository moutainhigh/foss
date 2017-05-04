package com.deppon.pda.bdm.module.foss.test.server.service.acct;

import java.math.BigDecimal;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponReverseResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmActiveInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodPDADto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PDAGoodsAttrDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryPictureWaybillResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressPdaDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WoodenRequirePdaDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;

public class PdaWaybillService implements IPdaWaybillService {

	@Override
	public CouponReverseResultDto reverseCouponState(String arg0) {
		CouponReverseResultDto dto = new CouponReverseResultDto();
		dto.setMessage("测试");
		dto.setSuccess(true);
		return dto;
	}

	@Override
	public ResultDto submitGoodsAttr(PDAGoodsAttrDto arg0) {
		ResultDto dto = new ResultDto();
		dto.setCode("110");
		dto.setMsg("success");
		return dto;
	}

	@Override
	public ResultDto submitWaybillByPDA(WaybillPdaDto arg0) {
		// TODO Auto-generated method stub
		ResultDto dto = new ResultDto();
		dto.setCode("110");
		dto.setMsg("success");
		return dto;
	}

	@Override
	public ResultDto uploadLabeledGood(LabeledGoodPDADto arg0) {
		ResultDto dto = new ResultDto();
		dto.setCode("110");
		dto.setMsg("success");
		return dto;
	}

	@Override
	public CouponInfoResultDto validateCoupon(CouponInfoDto arg0) {
		CouponInfoResultDto dto = new CouponInfoResultDto();
		dto.setCanNotUseReason("no reason");
		dto.setCanUse(true);
		dto.setCouponAmount(new BigDecimal(100));
		return dto;
	}

	@Override
	public ResultDto submitWaybillByPDAForAirAndVehicle(WaybillPdaDto arg0) {
		ResultDto dto = new ResultDto();
		dto.setCode("110");
		dto.setMsg("success");
		return dto;
	}

	@Override
	public void addForTransfer(WaybillPdaDto arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitWaybill(WaybillPdaDto arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	public void submitWaybillExpress(WaybillExpressPdaDto arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addForTransferExpress(WaybillExpressPdaDto arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void submitPdaWaybill(WaybillExpressPdaDto arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ResultDto updateWaybillByPDA(WaybillPdaDto waybillPdaDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateWaybill(WaybillPdaDto waybillPdaDto, String billOrgCode) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateForTransfer(WaybillPdaDto waybillPdaDto, String billOrgCode, WaybillPendingEntity pending) {
        // TODO Auto-generated method stub
        
    }
/*
    @Override
    public CrmActiveInfoDto getActiveInfoList(CrmActiveParamVo arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CrmActiveInfoDto getActiveInfoListExpress(CrmActiveParamVo arg0) {
        // TODO Auto-generated method stub
        return null;
    }*/

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService#getActiveInfoList(com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo)
	 */
	@Override
	public CrmActiveInfoDto getActiveInfoList(CrmActiveParamVo pdaParamDto) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService#getActiveInfoListExpress(com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo)
	 */
	@Override
	public CrmActiveInfoDto getActiveInfoListExpress(
			CrmActiveParamVo pdaParamDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultDto batchGenerateActiveEWaybillByPda(
			EWaybillConditionDto eWaybillConditionDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultDto activeEWaybillByPda(
			WaybillExpressPdaDto waybillExpressPdaDto, String billOrgCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultDto returnEWaybillByPda(
			EWaybillConditionDto eWaybillConditionDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultDto cancelWaybillPictureByPDA(String waybill, String driverCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultDto autoSubmitWaybillByPDA(
			WoodenRequirePdaDto woodenRequirePdaDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int pushMessageStatus(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public QueryPictureWaybillResultDto queryWaybillPictureByPDA(
			WaybillPicturePdaDto waybillPicturePdaDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryWaybillPictureByWaybillNo(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultDto submitWaybillPictureByPDA(
			WaybillPicturePdaDto waybillPicturePdaDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void submitWaybill(WaybillDto arg0) {
		// TODO Auto-generated method stub
		
	}





}
