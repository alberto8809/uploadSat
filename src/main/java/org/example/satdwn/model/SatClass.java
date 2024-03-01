package org.example.satdwn.model;

import java.io.InputStream;

public class SatClass {

    private String rfc;
    private String cer_path;
    private String key_path;
    private String clave;

    private String initialDate;

    private String finalDate;

    public SatClass() {
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCer_path() {
        return cer_path;
    }

    public void setCer_path(String cer_path) {
        this.cer_path = cer_path;
    }

    public String getKey_path() {
        return key_path;
    }

    public void setKey_path(String key_path) {
        this.key_path = key_path;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    @Override
    public String toString() {
        return "SatClass{" +
                "rfc='" + rfc + '\'' +
                ", cer_path='" + cer_path + '\'' +
                ", key_path='" + key_path + '\'' +
                ", clave='" + clave + '\'' +
                ", initialDate='" + initialDate + '\'' +
                ", finalDate='" + finalDate + '\'' +
                '}';
    }
}
