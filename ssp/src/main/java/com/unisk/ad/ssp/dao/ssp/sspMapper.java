package com.unisk.ad.ssp.dao.ssp;

import com.unisk.ad.ssp.model.Ssp2BidderParameter;

public interface SspMapper {

	public Ssp2BidderParameter selectOneBySlotId(String slotId);
	
}
