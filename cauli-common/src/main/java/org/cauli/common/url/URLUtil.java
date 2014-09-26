package org.cauli.common.url;

import org.springframework.web.util.HtmlUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by tianqing.wang on 2014/9/26
 */
public class URLUtil {

    public String decode(String value){
        try {
            return URLDecoder.decode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public String decode(String value,String encoding){
        try {
            return URLDecoder.decode(value,encoding);
        } catch (UnsupportedEncodingException e) {
            return decode(value);
        }
    }

    public String encode(String value,String encoding){
        try {
            return URLEncoder.encode(value,encoding);
        } catch (UnsupportedEncodingException e) {
            return encode(value);
        }
    }

    public String encode(String value){
        try {
            return URLEncoder.encode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }


    public String htmlEscape(String url){
        return HtmlUtils.htmlEscape(url);
    }

    public String htmlUnEscape(String url){
        return HtmlUtils.htmlUnescape(url);
    }







}
