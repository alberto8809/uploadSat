package org.example.satdwn.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.satdwn.model.Response;
import org.example.satdwn.model.SatClass;
import org.example.satdwn.model.User;
import org.example.satdwn.model.WSDescargaCFDI;
import org.example.satdwn.repository.SatRepository;
import org.example.satdwn.util.EncryptionUtil;
import org.example.satdwn.util.UploadFileToS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class SatService {


    @Autowired
    SatRepository repository;
    Logger LOGGER = LogManager.getLogger(SatService.class);

    public Map<String, List<Response>> getFiles(String fileName) {
        return UploadFileToS3.getFilelFromAWS(fileName);
    }


    public SatClass requestSat(SatClass satClass) throws IOException {
        LOGGER.info("User values { " + satClass + "}");

        if (repository.getUserByMail(satClass.getToken()) != null) {

            // genearte for CER
            URL url = new URL(satClass.getCer_path());
            URLConnection connection = url.openConnection();
            connection.connect();
            connection.getContent();

            // genearte for KEY
            URL url2 = new URL(satClass.getKey_path());
            URLConnection connection2 = url2.openConnection();
            connection2.connect();
            connection2.getContent();


            WSDescargaCFDI solicitud = new WSDescargaCFDI(satClass.getRfc(), connection.getInputStream(), connection2.getInputStream(), satClass.getClave());

            solicitud.autenticacion();
            String idSolicitud = solicitud.solicitud(satClass.getInitialDate(), satClass.getFinalDate(), null, satClass.getRfc(), WSDescargaCFDI.TIPO_SOLICITUD_CFDI);
            WSDescargaCFDI.ResultadoVerificaSolicitud resultado = solicitud.verificacion(idSolicitud);
            if (resultado != null) {
                ArrayList<String> idPaquetes = solicitud.obtiener_ids_paquetes(Paths.get(resultado.getXml_resultado()));
                if (idPaquetes != null) {
                    for (String idPaquete : idPaquetes) {
                        String xml = solicitud.descargaPaquete(idPaquete);
                        String zip = solicitud.extraer_zip_de_xml(Paths.get(xml));
                        //UploadFileToS3.upload(zip);
                        //System.out.println(zip);
                    }
                }
            }

        }
        return satClass;
    }

}
