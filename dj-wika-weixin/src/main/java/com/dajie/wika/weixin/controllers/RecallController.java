package com.dajie.wika.weixin.controllers;

import com.dajie.wika.weixin.service.RecallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wills on 2/17/14.
 */
@Controller
public class RecallController {

    @Autowired
    private RecallService recallService;

    @RequestMapping("recall")
    @ResponseBody
    public String recall(){
        return String.valueOf(recallService.sendRecallServiceMessage());
    }
}
