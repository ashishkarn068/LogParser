package beans;

import java.util.Objects;

public final class Endpoint {

    private final String url;
    private final String method;

    public String getUrl() {
        return url;
    }


    public String getMethod() {
        return method;
    }

    public Endpoint(String url, String method){
        this.url = url;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endpoint)) return false;
        Endpoint endpoint = (Endpoint) o;
        return getUrl().equals(endpoint.getUrl()) &&
                getMethod().equals(endpoint.getMethod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getMethod());
    }
}
