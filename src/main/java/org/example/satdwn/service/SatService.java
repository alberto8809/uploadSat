package org.example.satdwn.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.example.satdwn.model.Response;
import org.example.satdwn.model.SatClass;
import org.example.satdwn.model.WSDescargaCFDI;
import org.example.satdwn.repository.SatRepository;
import org.example.satdwn.util.UploadFileToS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@Service
public class SatService {


    @Autowired
    SatRepository repository;
    Logger LOGGER = LogManager.getLogger(SatService.class);

    public Map<String, List<Response>> getFiles(String fileName) {
        return UploadFileToS3.getFilelFromAWS(fileName);
    }


    public boolean requestSat(SatClass satClass) throws IOException {

        if (repository.getUserByToken(satClass.getToken()) != null) {


            //Path destinoCer = Paths.get("/Users/marioalberto/IdeaProjects/upload/" + satClass.getRfc() + ".cer");
            Path destinoCer = Paths.get("/home/ubuntu/satUploadFile/" + satClass.getRfc() + ".cer");


            URL url = new URL(satClass.getCer_path());
            Files.copy(url.openStream(), destinoCer, StandardCopyOption.REPLACE_EXISTING);

            //Path destinoKey = Paths.get("/Users/marioalberto/IdeaProjects/upload/" + satClass.getRfc() + ".key");
            Path destinoKey = Paths.get("/home/ubuntu/satUploadFile/" + satClass.getRfc() + ".key");
            URL url2 = new URL(satClass.getKey_path());
            Files.copy(url2.openStream(), destinoKey, StandardCopyOption.REPLACE_EXISTING);


            InputStream cer = new FileInputStream(String.valueOf(destinoCer));
            InputStream key = new FileInputStream(String.valueOf(destinoKey));


            WSDescargaCFDI solicitud = new WSDescargaCFDI(satClass.getRfc(), cer, key, satClass.getClave());
            solicitud.autenticacion();

            String idSolicitud = solicitud.solicitud(satClass.getInitialDate(), satClass.getFinalDate(), null, satClass.getRfc(),
                    WSDescargaCFDI.TIPO_SOLICITUD_CFDI);


            WSDescargaCFDI.ResultadoVerificaSolicitud resultado = solicitud.verificacion(idSolicitud);

            if (resultado != null) {
                ArrayList<String> idPaquetes = solicitud.obtiener_ids_paquetes(Paths.get(resultado.getXml_resultado()));

                if (idPaquetes != null) {
                    for (String idPaquete : idPaquetes) {
                        String xml = solicitud.descargaPaquete(idPaquete);
                        UploadFileToS3.upload(satClass.getRfc(), xml);
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
            FileUtils.deleteDirectory(new File(String.valueOf(destinoCer)));
            FileUtils.deleteDirectory(new File(String.valueOf(destinoKey)));
        }


        return false;
    }

}
