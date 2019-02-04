import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;



public class MyHandler implements HttpHandler {
    Request request = new Request();
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();

    }

    @Override
    public void handle(HttpExchange exchange) {
        Class<Request> requestClass = Request.class;
        for(Method method : requestClass.getDeclaredMethods()){
            if(method.isAnnotationPresent(Webroute.class)){
                Annotation annotation = method.getAnnotation(Webroute.class);
                Webroute route =(Webroute) annotation;
                if(route.path().equals(exchange.getRequestURI().getPath())){
                   String response = null;
                   try {
                       response = (String) method.invoke(request);
                   }catch (InvocationTargetException e){
                       e.printStackTrace();
                   }catch (IllegalAccessException e){
                       e.printStackTrace();
                   }

                   try {
                       exchange.sendResponseHeaders(200, response.length());
                       OutputStream os = exchange.getResponseBody();
                       os.write(response.getBytes());
                       os.close();
                   }catch (IOException e){
                       e.printStackTrace();
                   }
                }
            }
        }

    }



    }

