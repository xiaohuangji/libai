/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.system;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.multipart.MultipartFile;

import com.dajie.mobile.mcp.api.command.AbstractApiCommand;
import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;
import com.dajie.mobile.mcp.api.entity.ApiResultCode;
import com.dajie.mobile.mcp.utils.McpUtils;
import com.dajie.wika.service.ResourceService;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Dec 16, 2013 12:06:19 PM
 */

public class UploadFile extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(UploadFile.class);

    private ResourceService resourceService;
    
    public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
        // TODO Auto-generated method stub
    	Map<String, String> stringParams = context.getStringParams();
        Map<String, MultipartFile> binaryParams = context.getBinaryParams();
        
        String filename=stringParams.get("filename");
        String type=stringParams.get("type");
        
        byte[] is = null;

        try {
            is = binaryParams.get("file").getBytes();
        } catch (Exception e) {
            logger.error("getInputStream failed", e);
            return new ApiResult(ApiResultCode.E_SYS_PARAM);
        }
        
        Object result = null;
        ApiResult apiResult = null;

        // 执行RPC调用       
        try {
            long t = System.currentTimeMillis();
            result =resourceService.uploadFile(is, filename, Integer.valueOf(type));
            
            McpUtils.rpcTimeCost(t, "resource.uploadFile");
        } catch (Exception e) {
            // 异常记录日志， 返回错误信息
            logger.error("RPC error ", e);
            apiResult = new ApiResult(ApiResultCode.E_SYS_RPC_ERROR);
            return apiResult;
        }

        // 正常返回接口数据
        apiResult = new ApiResult(ApiResultCode.SUCCESS, result);
        return apiResult;
    }

        private static  byte[] InputStreamToByte(InputStream is) throws IOException {   
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();   
           int ch;   
           while ((ch = is.read()) != -1) {   
             bytestream.write(ch);   
            }   
           byte imgdata[] = bytestream.toByteArray();   
            bytestream.close();   
           return imgdata;   
           }

}
