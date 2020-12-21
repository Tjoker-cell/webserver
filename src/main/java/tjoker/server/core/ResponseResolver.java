package tjoker.server.core;

import java.io.*;

/**
 * @program: server
 * @description: 处理响应结果
 * @author: 十字街头的守候
 * @create: 2020-12-20 15:55
 **/
public class ResponseResolver {
    private StringBuilder sb;
  public ResponseResolver(String fileName) {
      //解析路径
      File file=new File("src/main/java/tjoker/server/servlet/"+fileName);
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(is==null) {
            System.out.println("页面不存在");
            return;
        }
        System.out.println("页面存在");
        byte[] bytes= new byte[1024*1024];
        int lens=0;
         sb=new StringBuilder();
        while(true) {
            try {
                if (!(-1 != (lens = is.read(bytes)))) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            //输出  字节数组转成字符串
            String info = new String(bytes, 0, lens);
            sb.append(info);
        }

    }
    public String getString(){
      return sb.toString();
    }

}
