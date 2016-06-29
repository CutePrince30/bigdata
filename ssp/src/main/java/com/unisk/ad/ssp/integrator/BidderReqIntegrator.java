package com.unisk.ad.ssp.integrator;

import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.dao.ssp.SspMapper;
import com.unisk.ad.ssp.model.Ssp2BidderPullParam;
import com.unisk.ad.ssp.util.BeanUtils;
import com.unisk.ad.ssp.util.RenderUtils;
import com.unisk.ad.ssp.util.TemplateUtils;
import com.unisk.ad.ssp.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@Component
public class BidderReqIntegrator {

    private static Logger log = LoggerFactory.getLogger("ssp");

    @Autowired
    protected SspMapper sspMapper;

    public String generateBidderPullReq(MediaType mt, String appid, String siteid, String zoneid, String device) {
        // 推广位信息
        Ssp2BidderPullParam zoneInfo = sspMapper.selectOneByZoneId(zoneid);
        // 渠道信息
        Ssp2BidderPullParam mediaInfo;

        String mediaid = mt.equals(MediaType.WEB) ? siteid : appid;

        mediaInfo = mt.equals(MediaType.WEB) ? sspMapper.selectSiteInfoByMediaId(mediaid) :
                sspMapper.selectAppInfoByMediaId(mediaid);

        Ssp2BidderPullParam ssp2BidderParameter = new Ssp2BidderPullParam();

        if (zoneInfo == null) {
            ssp2BidderParameter.setSlotid(zoneid);
            log.warn("无广告位信息, zoneid: " + zoneid);
        }
        else {
            BeanUtils.merge(zoneInfo, ssp2BidderParameter);
        }

        if (mediaInfo == null) {
            if (mt.equals(MediaType.WEB)) {
                ssp2BidderParameter.setSiteid(mediaid);
            }
            else {
                ssp2BidderParameter.setAppid(mediaid);
            }
            log.warn("无渠道信息, mediaid: " + mediaid);
        }
        else {
            BeanUtils.merge(mediaInfo, ssp2BidderParameter);
        }

        ssp2BidderParameter.setMediaType(mt.getFlag());
        ssp2BidderParameter.setReqid(UUIDGenerator.generate());
        ssp2BidderParameter.setDevice(device);

        Template ssp2BidderTemplate = TemplateUtils.getTemplate("/template/ssp2bidder_pull_template.beetl");
        ssp2BidderTemplate.binding("obj", ssp2BidderParameter);

        return ssp2BidderTemplate.render();
    }

}
