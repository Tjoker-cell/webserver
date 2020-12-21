package tjoker.server.core;

import tjoker.server.core.util.CloseUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: server
 * @description: 使用serverScoket建立与浏览器的连接
 * @author: 十字街头的守候
 * @create: 2020-12-19 16:33
 **/
public class Server {
    private boolean isRunning=true;
    ServerSocket serverSocket=null;
    public static void main(String[] args)  {
        Server server = new Server();
        server.start();

    }
    //启动服务
    public void start() {
        //创建连接
        try {
            serverSocket = new ServerSocket(8888);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败...");
        }
    }
    //接收连接处理
   public void receive(){
        while(isRunning){
       try {
           Socket client = serverSocket.accept();
           System.out.println("一个客户端建立了连接");
          new Thread(new Dispatcher(client)).start();
       } catch (IOException e) {
           e.printStackTrace();
           System.out.println("客户端连接失败...");
       }
   }
    }
    //停止服务
    public void stop(){
        isRunning=false;
        CloseUtil.closeIo(serverSocket);
    }

}
