package com.dajie.wika.weixin.controllers;

import com.dajie.wika.weixin.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wills on 2/10/14.
 */
@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("create")
    @ResponseBody
    public int createMenu(){
        return menuService.createMenu();
    }

    @RequestMapping("delete")
    @ResponseBody
    public int deleteMenu(){
        return menuService.deleteMenu();
    }

    @RequestMapping("get")
    @ResponseBody
    public String getMenu(){
        return menuService.getMenu();
    }
}
