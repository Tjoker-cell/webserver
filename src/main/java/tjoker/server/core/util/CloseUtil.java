package tjoker.server.core.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @program: server
 * @description: 关闭资源
 * @author: 十字街头的守候
 * @create: 2020-12-20 17:42
 **/
public class CloseUtil {
    /**
     * 关闭io流
     * @param io
     */
    public static void closeIo(Closeable... io){
        for(Closeable temp:io){
            try {
                if (null != temp) {
                    temp.close();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

}
