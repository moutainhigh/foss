package com.deppon.foss.module.transfer.platform.api.shared.utils;

import java.util.ArrayList;

import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDto;

/**
 * 辅助类
 * @author 042770
 * @desc 营业部未到、集中接货未到和上一外场短途未到的数据都是由几部分数据组成，
 *       每一部分可能会存在同一出发部门的数据，比如说营业部未到=a营业部库存+b营业部出发,
 *       a和b的记录中都存在上海xx营业部的数据，需要将两部分上海xx营业部的数据合并
 */
public class CargoArrivedList extends ArrayList<CargoArrivedDto> {

	private static final long serialVersionUID = 7568031399094952384L;

	@Override
	public boolean add(CargoArrivedDto e) {
		for (CargoArrivedDto cargo : this) {
			if (cargo.equals(e)) {
				// 总重量、体积、票数
				cargo.setWeightTotal(cargo.getWeightTotal().add(
						e.getWeightTotal()));
				cargo.setVolumeTotal(cargo.getVolumeTotal().add(
						e.getVolumeTotal()));
				cargo.setVoteTotal(cargo.getVoteTotal() + e.getVoteTotal());
				
				// 卡航重量、体积、票数
				cargo.setWeightFlf(cargo.getWeightFlf().add(e.getWeightFlf()));
				cargo.setVolumeFlf(cargo.getVolumeFlf().add(e.getVolumeFlf()));
				cargo.setVoteFlf(cargo.getVoteFlf() + e.getVoteFlf());
				
				// 城运重量、体积、票数
				cargo.setWeightFsf(cargo.getWeightFsf().add(e.getWeightFsf()));
				cargo.setVolumeFsf(cargo.getVolumeFsf().add(e.getVolumeFsf()));
				cargo.setVoteFsf(cargo.getVoteFsf() + e.getVoteFsf());
				
				// 快递重量、体积、票数
				cargo.setWeightExp(cargo.getWeightExp().add(e.getWeightExp()));
				cargo.setVolumeExp(cargo.getVolumeExp().add(e.getVolumeExp()));
				cargo.setVoteExp(cargo.getVoteExp() + e.getVoteExp());

				return true;
			}
		}
		return super.add(e);
	}

}
