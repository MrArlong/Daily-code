package com.wx.wx.controller;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
@Controller
@RequestMapping("/weixin")
public class Weixin {
    @Value("${wx.appid}")
    private String appid; // 微信appid
    @Value("${wx.appsecret}")
    private String appsecret;// 微信 appsecret
    @RequestMapping("/frist")
    public void bianma(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
//        获取之后需要跳转的业务接口
        String redirect_uri = URLEncoder.encode("http://xxxxx/weixin/openid", "UTF-8");
        StringBuffer url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" + redirect_uri +
                "&appid="+appid+ "&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        System.out.println("重定向-----------");
        response.sendRedirect(url.toString());
    }

    /**
     * 业务逻辑 用code 换取 openid
     * @param request
     * @param response
     */
    @RequestMapping("/openid")
    public void huidai(HttpServletRequest request, HttpServletResponse response)  {
        response.setContentType("text/html");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");
        String code = request.getParameter("code");
        String url="https://api.weixin.qq.com/sns/oauth2/access_token";
        String param="appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
        String jsons =HttpRequestUti.sendGet(url,param);
        Map map= (Map) JSON.parse(jsons);
        System.out.println(map);


    }


}
