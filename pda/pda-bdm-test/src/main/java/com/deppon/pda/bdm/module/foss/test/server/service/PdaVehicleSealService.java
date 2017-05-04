package com.deppon.pda.bdm.module.foss.test.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.pda.api.server.service.IPdaVehicleSealService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.SealDestDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.SealOrigDetailEntity;

public class PdaVehicleSealService implements IPdaVehicleSealService{

	

	@Override
	public int insertSealDest(String sealState, String vehicleNo,
			String checkOrgCode, List<String> backSealNos,
			List<String> sideSealNos, String checkOrgMemo, String checkerUser,
			String pdaDeviceNo,List<String> arg8) {
		System.out.println("成功");
		return 1;
	}

	@Override
	public int insertSealOrig(String arg0, String arg1, List<String> arg2,
			List<String> arg3, String arg4, String arg5, String arg6,
			String arg7) {
		// TODO Auto-generated method stub
		return 0;
	}

    @Override
    public int insertSealDest(String arg0, String arg1, String arg2, List<SealDestDetailEntity> arg3, String arg4,
            String arg5, String arg6, String arg7) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insertSealOrig(String arg0, String arg1, List<SealOrigDetailEntity> arg2, String arg3, String arg4,
            String arg5, String arg6, String arg7) {
        // TODO Auto-generated method stub
        return 0;
    }

	

	

	
	
	

}
