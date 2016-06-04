package com.unisk.ad.ssp.integrator;

import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.config.Operate;
import com.unisk.ad.ssp.dao.ssp.SspMapper;
import com.unisk.ad.ssp.model.Ssp2BidderParameter;
import com.unisk.ad.ssp.util.TemplateUtils;
import com.unisk.ad.ssp.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@Component
public class BidderReqIntegrator {

    @Autowired
    protected SspMapper sspMapper;

    public String generateBidderReq(MediaType mt, String appid, String siteid, String slotid, String device) {
        // 必须要有广告位id
        Ssp2BidderParameter ssp2BidderParameter = sspMapper.selectOneBySlotId(slotid);
        if (ssp2BidderParameter == null) {
            return "failed: 无广告位信息";
        }

        // TODO: 通过appid查找对应的数据
        if (StringUtils.isNotEmpty(appid)) {
            ssp2BidderParameter.setAppid(appid);
        }

        // TODO: 通过siteid查找对应数据
        if (StringUtils.isNotEmpty(siteid)) {
            ssp2BidderParameter.setSiteid(siteid);
            //ssp2BidderParameter.setSiteXXX();
        }

        ssp2BidderParameter.setMediaType(mt.getFlag());

        // TODO: 怎么取instl
        ssp2BidderParameter.setInstl("0");
        ssp2BidderParameter.setReqid(UUIDGenerator.generate());

        ssp2BidderParameter.setDevice(device);

        Template ssp2BidderTemplate = TemplateUtils.getTemplate("/template/ssp2bidder_template.beetl");
        ssp2BidderTemplate.binding("obj", ssp2BidderParameter);

        return ssp2BidderTemplate.render();
    }

}
