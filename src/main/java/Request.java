public class Request {

    @Webroute(path = "/test1")
    public  String test1(){
        return "responding to /test1";
    }

    @Webroute(path = "/test2")
    public  String test2(){
        return "responding to /test2";
    }

    @Webroute(path = "/test3")
    public  String test3(){
        return "responding to /test3";
    }

}
