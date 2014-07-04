package com.dajie.wika.weixin.service;

/**
 * Created by wills on 2/10/14.
 */
public interface MenuService {

    /**
     * 从配置文件获取菜单配置信息,提交到微信
     * 成功返回0
     * @return
     */
    public int createMenu();

    /**
     * 删除菜单
     * @return
     */
    public int deleteMenu();

    /**
     * 获取菜单
     * @return
     */
    public String getMenu();
}
