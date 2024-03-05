package org.example.satdwn.model;

import org.bouncycastle.jcajce.provider.symmetric.DES;

public class Response {

    private String url;

    private Descripcion descripcion;


    public Response() {
    }

    public Response(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Descripcion getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Descripcion descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Response{" +
                "url='" + url + '\'' +
                ", descripcion=" + descripcion +
                '}';
    }
}
