package tjoker.server.core;

import com.sun.security.ntlm.Client;
import tjoker.server.core.util.CloseUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.*;

/**
 * @program: server
 * @description: 封装请求：请求method、url、参数
 * @author: 十字街头的守候
 * @create: 2020-12-19 22:09
 **/
public class Request {
    //请求方式
    private String method;
    //请求资源
    private String url;
    //请求参数
    private Map<String, List<String>> paramMap=new HashMap<>();
//    private ;
    private final String CRLF="\r\n";
    private  String requestInfo;
    private InputStream is;

    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }
    public Request(InputStream is){
        this.is=is;
        //获取请求协议
        byte[] datas=new byte[1024*1024];
        int len= 0;
        try {
            len = is.read(datas);
            requestInfo=new String(datas,0,len);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //分解字符串
        parseRequestInfo();

    }

    /**
     * 分析请求信息
     */
    private void parseRequestInfo() {
        String paramString =""; //接收请求参数
        System.out.println("----分解开始-----");
       if(requestInfo==null||(requestInfo=requestInfo.trim()).equals("")){
           return ;
       }
       //获取请求方式
        String firstLine=requestInfo.substring(0,requestInfo.indexOf(CRLF));
        // /的位置
        int idx=firstLine.indexOf("/");
       method=firstLine.substring(0,idx).trim();
       //获取请求url和参数
        String urlStr=firstLine.substring(idx+1,firstLine.indexOf("HTTP/")).trim();
        //判断方法是get 还是 post
        if(method.equalsIgnoreCase("post")){
            url=urlStr;
            //请求参数
            paramString=requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        }else if(method.equalsIgnoreCase("get")){
            //是否含有参数
            if(urlStr.contains("?")){
                url=urlStr.substring(0,urlStr.indexOf("?"));
                paramString=urlStr.substring(urlStr.indexOf("?")+1).trim();
            }else {
                url=urlStr;
            }
        }
        //不存在请求参数
        if(paramString.equals("")){
            return ;
        }
        //将请求参数封装到Map
        parseParams(paramString);
    }

    /**
     * 将请求参数封装到Map
     * @param paramString
     */
    private void parseParams(String paramString) {
        //分割 将字符产转成数组
        StringTokenizer token = new StringTokenizer(paramString, "&");
        while(token.hasMoreTokens()){
            String keyValue=token.nextToken();
            String[] split = keyValue.split("=");
            split = Arrays.copyOf(split, 2);
            //获取key value
            String key=split[0];
            String value=split[1]==null?null:decode(split[1],"utf-8");
            //存储到map中
            if(!paramMap.containsKey(key)){
                paramMap.put(key,new ArrayList<String>());
            }
            paramMap.get(key).add(value);

        }
    }

    /**
     * 通过name 获取多个值value
     * @param key
     * @return
     */
    public String[] getParameterValues(String key){
        if(!paramMap.containsKey(key)){
            return null;
        }
        List<String> values=paramMap.get(key);
        return  values.toArray(new String[0]);
    }
    private String decode(String value,String code)  {
        try {
          return  java.net.URLDecoder.decode(value,code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过name 获取一个值value
     * @param key
     * @return
     */
    public String getParameterValue(String key){
        String[] values = getParameterValues(key);
        return values==null?null:values[0];
    }

    /**
     * 关闭资源
     */
    public void close(){
        CloseUtil.closeIo(is);
    }
    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, List<String>> getParamMap() {
        return paramMap;
    }
}
