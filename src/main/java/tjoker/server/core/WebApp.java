package tjoker.server.core;



import tjoker.server.core.WebHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @program: server
 * @description:解析配置文件
 * @author: 十字街头的守候
 * @create: 2020-12-20 13:01
 **/
public class WebApp {
   private static WebContext webContext;
    static {
        try {
            //1、获取解析工厂
            SAXParserFactory factory =SAXParserFactory.newInstance();
            //2、获取解析器
            SAXParser sax =factory.newSAXParser();
            //3、指定xml+处理器
            WebHandler handler = new WebHandler();
            sax.parse(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("web.xml"),handler);
            webContext = new WebContext(handler.getEntityList(),handler.getMappingList());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 通过url获得servlet
     * @param url
     * @return
     */
    public static Servlet getServletFromUrl(String url) throws Exception {
        String clzName = webContext.getClz("/"+url);
        if(clzName!=null){
        Class clz = null;
            clz = Class.forName(clzName);
            Servlet servlet= (Servlet) clz.getConstructor().newInstance();
            return servlet;
        }
        return null;
    }

}

