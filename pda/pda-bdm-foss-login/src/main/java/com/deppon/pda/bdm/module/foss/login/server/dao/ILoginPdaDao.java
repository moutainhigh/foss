package com.deppon.pda.bdm.module.foss.login.server.dao;

import com.deppon.pda.bdm.module.foss.login.shared.domain.PdaLoginDeviceEntity;

public interface ILoginPdaDao {
	public boolean checkPdaLoginInfoData(PdaLoginDeviceEntity entity);
	public int updPdaLoginInfoData(PdaLoginDeviceEntity entity);
	public int savePdaLoginInfoData(PdaLoginDeviceEntity entity);
	public boolean chekLastLoginOutDate(PdaLoginDeviceEntity entity);
	public void updPdaLoginOutDate(PdaLoginDeviceEntity outDateEntity);
}
