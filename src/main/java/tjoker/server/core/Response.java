package tjoker.server.core;

import tjoker.server.core.util.CloseUtil;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * @program: server
 * @description:封装响应信息
 * @author: 十字街头的守候
 * @create: 2020-12-19 19:55
 **/
public class Response {
    //常量
    private final String CRLF="\r\n";
    private final String BLANK=" ";
    //响应头
    private StringBuilder headInfo;
    //响应内容
    private StringBuilder context;
    //流
   private BufferedWriter bw;
    //长度
    int len;
    private Response(){
        context=new StringBuilder();
        headInfo=new StringBuilder();
        len=0;
    }
    public Response(Socket client){
        this();
        try {
            bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            headInfo=null;
        }
    }
    public Response(OutputStream os){
      this();
      bw=new BufferedWriter(new OutputStreamWriter(os));
    }

    /**
     * 响应正文,传递文件名
     * @param fileName
     * @return
     */
    public Response printPath(String fileName)  {
        //通过response处理响应信息
        ResponseResolver responseHandler = new ResponseResolver(fileName);
        String s = responseHandler.getString();
            context.append(s).append(CRLF);
            len+=(s+CRLF).getBytes().length;
            return this;
    }
    /**
     * 响应正文+换行
     * @param info
     * @return
     */
    public Response println(String info){
        context.append(info).append(CRLF);
        len+=(info+CRLF).getBytes().length;
        return this;
    }

    /**
     * 创建头部信息
     * @param code
     */
    private void createHeader(int code){
        //1)  HTTP协议版本、状态代码、描述
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch(code){
            case 200:
                headInfo.append("OK");
                break;
            case 404:
                headInfo.append("NOT FOUND");
                break;
            case 500:
                headInfo.append("SEVER ERROR");
                break;
        }
        headInfo.append(CRLF);
        //2)  响应头(Response Head)
        headInfo.append("Server:tjoker Server/0.0.1").append(CRLF);
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Content-type:text/html;charset=utf-8").append(CRLF);
        //正文长度 ：字节长度
        headInfo.append("Content-Length:").append(len).append(CRLF);
        headInfo.append(CRLF); //分隔符
    }

    /**
     * 推送到客户端
     */
    void pushToClient(int code) throws IOException {
        if(headInfo==null){
            code=500;
        }
        createHeader(code);
        //头信息+分隔符
        bw.append(headInfo.toString());
        //正文信息
        bw.append(context.toString());
        bw.flush();

    }
    /**
     * 关闭资源
     */
    public void close(){
        CloseUtil.closeIo(bw);
    }
}
