package akka.http.model.japi.headers;



/**
 *  Model for the `Raw-Request-URI` header.
 *  Custom header we use for transporting the raw request URI either to the application (server-side)
 *  or to the request rendering stage (client-side).
 */
public interface Raw_Request_URI {
    String uri();
}
