package ua.kiev.test.testwebmvc.model;

/**
 * Created by lezha on 10.03.2015.
 */
public class RestErrorInfo {
    private String url;
    private String message;

    public RestErrorInfo(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson() {
        return "{\"url\":\"" + url + "\",\"message\":\"" + message +"\"}";
    }
}
