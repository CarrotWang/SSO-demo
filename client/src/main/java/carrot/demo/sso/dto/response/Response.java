package carrot.demo.sso.dto.response;

public class Response {
    private int code;

    private String msg;

    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static Response success(String msg){
        Response resp=new Response();
        resp.setCode(0);
        resp.setMsg(msg);
        return  resp;
    }

    public static Response success(String msg, String data){
        Response resp=new Response();
        resp.setCode(0);
        resp.setMsg(msg);
        resp.setData(data);
        return  resp;
    }

    public static Response fail(String msg){
        Response resp=new Response();
        resp.setCode(1);
        resp.setMsg(msg);
        return  resp;
    }


}
