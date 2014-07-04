/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.wika.service;

import java.util.List;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Jan 3, 2014 2:50:50 PM
 */

public interface CorpSearchService {

    /** 查询公司名称 **/
    public List<String> getCorpName(String keyword);
}
