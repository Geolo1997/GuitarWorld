package pers.geolo.guitarworld.http;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-06
 */
public interface HttpHandler {

    void init(HttpConfig httpConfigure);

    void execute(HttpRequest httpRequest);
}
