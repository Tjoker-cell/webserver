package tjoker.server.servlet;

import tjoker.server.core.Request;
import tjoker.server.core.Response;
import tjoker.server.core.Servlet;

/**
 * @program: server
 * @description:
 * @author: 十字街头的守候
 * @create: 2020-12-20 12:41
 **/
public class LoginServlet extends Servlet {
    @Override
    public void service(Request request, Response response) {
        response.printPath("success.html");
    }
    @Override
    protected void doGet(Request req, Response rep) throws Exception {
    }
    @Override
    protected void doPost(Request req, Response rep) throws Exception {
    }
}
