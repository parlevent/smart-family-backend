package com.yfzm.flawsweeper.controllers;

import com.yfzm.flawsweeper.form.auth.session.SessionInfo;
import com.yfzm.flawsweeper.form.statistics.item.info.StaItemInfoResponse;
import com.yfzm.flawsweeper.form.statistics.item.num.StaItemNumResponse;
import com.yfzm.flawsweeper.form.statistics.item.tag.StaItemTagResponse;
import com.yfzm.flawsweeper.form.statistics.item.time.StaItemNumTimeResponse;
import com.yfzm.flawsweeper.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.yfzm.flawsweeper.util.Constant.ADMIN_USER;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/item")
    public StaItemInfoResponse getItemInfo(HttpSession httpSession) {
        SessionInfo info = (SessionInfo) httpSession.getAttribute("sessionInfo");
        if (info == null || info.getIsAdmin() != ADMIN_USER) {
            return new StaItemInfoResponse(false);
        }

        return statisticsService.getItemInfo();
    }

    @GetMapping("/item/num")
    public StaItemNumResponse getItemTotalNum(HttpSession httpSession) {
        StaItemNumResponse response = new StaItemNumResponse(false);
        SessionInfo info = (SessionInfo) httpSession.getAttribute("sessionInfo");
        if (info == null || info.getIsAdmin() != ADMIN_USER) {
            return response;
        }
        Long num = statisticsService.getItemTotalNum();
        if (num == null) {
            return response;
        }
        response.setStatus(true);
        response.setNum(num);
        return response;

    }

    @GetMapping("/item/time")
    public StaItemNumTimeResponse getItemTimeNum(HttpSession httpSession) {
        SessionInfo info = (SessionInfo) httpSession.getAttribute("sessionInfo");
        if (info == null || info.getIsAdmin() != ADMIN_USER) {
            return new StaItemNumTimeResponse(false);
        }

        return statisticsService.getItemTimeNum();
    }

    @GetMapping("/item/tag")
    public StaItemTagResponse getItemTagInfo(HttpSession httpSession) {
        SessionInfo info = (SessionInfo) httpSession.getAttribute("sessionInfo");
        if (info == null || info.getIsAdmin() != ADMIN_USER) {
            return new StaItemTagResponse(false);
        }

        return statisticsService.getItemTagInfoNum();
    }
}
