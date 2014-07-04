
/**
 * 
jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
jjjjjjjjjjjDDDDDDGjjjjjjjjjjjjjjGDDDDDfjjjjjjjjjjj
jjjjjjjjjDDDDDDDDDDfjjjjjjjjjjDDDDDDDDDDfjjjjjjjjj
jjjjjjjjDD;       DDDjjjjjjjjDDG       DDDjjjjjjjj
jjjjjjjD            DDjjjjjjDL           DDjjjjjjj
jjjjjjD              DDjjjjD              fDjjjjjj
jjjjjD                DjjjD                ;jjjjjj
jjjjj           :f     Djj          ff      Djjjjj
jjjji          fff      jD         ff        jjjjj
jjjj           fff   .  D         .ff         jjjj
jjjj           fffj ff            ffff  f     jjjj
jjj            fffffft             ffffff     jjjj
jjj            ffffff              fffff.     jjjj
jjj             ffff                 ff        jjj
jjj                                            jjj
jjj                                            jjj
jjj                                           Ljjj
jGG                                           GGjj
GGGD                                          GGGG
GGGG                    t                     GGGG
GGGG:                   DD                   DGGGG
GGGGD                  DGD                  GDGGGG
GGGGGD                fDGGD                 DGGGGG
GGGGGDD              LDGGGDD               DDGGGGG
GGGGGGDD            DDDGGGGDDi           DDDGGGGGG
GGGGGGGDDD        DDDDGGGGGGDDDt       DDDDGGGGGGG
GGGGGGGGDDDDDDDDDDDDDGGGGGGGGDDDDDDDDDDDDDGGGGGGGG
GGGGGGGGGDDDDDDDDDDDGGGGGGGGGGDDDDDDDDDDDGGGGGGGGG
GGGGGGGGGGGDDDDDDDGGGGGGGGGGGGGGDDDDDDDGGGGGGGGGGG
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
LGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGL
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
GLGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGLG
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
GGGGLGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGLGGGG
GGGGGGLGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGLGGGGGG
 */
package com.dajie.wika.weixin.controllers;

import com.dajie.wika.weixin.constant.Weixin;
import com.dajie.wika.weixin.service.MessageService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by wills on 2/10/14.
 */
@Controller
@RequestMapping("message")
public class MessageController {

    private static final Logger logger= LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping(value="entrence")
    @ResponseBody
    public String entrence(HttpServletRequest request,String signature,String timestamp,String nonce,@RequestParam(required=false)String echostr)throws IOException{
        if(verifyParam(timestamp,nonce,signature)){
            String xmlData = IOUtils.toString(request.getInputStream(), "utf-8");
            if(xmlData==null||xmlData.trim().equals("")){
                logger.debug("this is url verify");
                return echostr;
            }else{
                return messageService.processRequest(xmlData);
            }
        }else{
            logger.debug("verify param fail");
            return null;
        }

    }

    private boolean verifyParam(String timestamp,String nonce,String signature){
        String[] tempStrs={timestamp,nonce, Weixin.Token};
        Arrays.sort(tempStrs);
        StringBuffer sb=new StringBuffer();
        for(String tempStr:tempStrs){
            sb.append(tempStr);
        }
        String realSignature=DigestUtils.shaHex(sb.toString());
        if(signature.equals(realSignature)){
            return true;
        }else{
            return false;
        }
    }
}
