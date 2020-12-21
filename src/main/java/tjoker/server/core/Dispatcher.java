package tjoker.server.core;

import tjoker.server.core.util.CloseUtil;

import java.io.IOException;
import java.net.Socket;

/**
 * @program: server
 * @description: 高效控制器
 * @author: 十字街头的守候
 * @create: 2020-12-20 13:32
 **/
public class Dispatcher implements Runnable{
    private Socket client;
    private  Request request;
    private Response response;

    Dispatcher(Socket client)  {
        this.client=client;
        //获取请求协议
        try {
            request = new Request(client);
            response=new Response(client);
        } catch (IOException e) {
            CloseUtil.closeIo(client);
        }

    }
    @Override
    public void run() {
        //返回响应信息
        try {
            //首页
            if(request.getUrl().equals("")){
                response.printPath("index.html");
                CloseUtil.closeIo(client);
                return ;
            }
            Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
            if(servlet!=null){
                servlet.service(request,response);
                response.pushToClient(200);
            }else {
                //错误 404 not found
                response.printPath("404.html");
                response.pushToClient(404);
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                //500 error
                response.printPath("500.html");
                response.pushToClient(500);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        CloseUtil.closeIo(client);

    }


}
