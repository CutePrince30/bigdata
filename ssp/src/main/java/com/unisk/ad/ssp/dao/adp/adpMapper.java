package com.unisk.ad.ssp.dao.adp;

import com.unisk.ad.ssp.model.InfoJsParam;

/**
 * Created by CutePrince on 2016-05-31.
 */
public interface AdpMapper {

    InfoJsParam selectOneByAdidFromAd(String adid);

    InfoJsParam selectOneByAdidFromStuff(String adid);

}
