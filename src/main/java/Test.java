import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {
    HttpServer server;
    @WebRoute(path = "/test2")
    public void init() throws Exception{
        Method method = getClass().getMethod("init");
        WebRoute webRoute = method.getAnnotation(WebRoute.class);

        server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext(webRoute.path(), new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.init();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}