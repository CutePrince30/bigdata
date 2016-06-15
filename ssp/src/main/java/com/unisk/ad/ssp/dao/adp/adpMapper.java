package com.unisk.ad.ssp.dao.adp;

import com.unisk.ad.ssp.model.InfoJsParameter;

/**
 * Created by CutePrince on 2016-05-31.
 */
public interface AdpMapper {

    InfoJsParameter selectOneByAdidFromAd(String adid);

    InfoJsParameter selectOneByAdidFromStuff(String adid);

}
