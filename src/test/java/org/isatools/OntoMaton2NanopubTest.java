package org.isatools;

import org.isatools.nanomaton.MalformedNanoMatonTemplateException;
import org.isatools.nanomaton.OntoMaton2Nanopub;
import org.junit.Test;
import org.nanopub.MalformedNanopubException;
import org.nanopub.Nanopub;
import org.nanopub.NanopubUtils;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by the ISATeam.
 * User: agbeltran
 * Date: 09/12/2013
 * Time: 23:11
 *
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public class OntoMaton2NanopubTest {

    @Test
    public void generateNanopubsTest() throws MalformedNanopubException, MalformedNanoMatonTemplateException, RDFHandlerException, FileNotFoundException, IOException {

        for (int i=1; i<9; i++) {
            String csv = "/nanopub/nanopub"+i+".csv";

            System.out.println("csv="+csv);
            String filepath = getClass().getResource(csv).getFile();
            OntoMaton2Nanopub ontoMaton2Nanopub = new OntoMaton2Nanopub();

            Nanopub nanopub = ontoMaton2Nanopub.generateNanopub(filepath);

            System.out.println(nanopub.toString());

            File file = new File("nanopub"+i+".trig");
            if(!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream oFile = new FileOutputStream(file, false);

            NanopubUtils.writeToStream(nanopub, oFile, RDFFormat.TRIG);
        }

    }

    @Test
    public void generateNanopubTest() throws MalformedNanopubException, RDFHandlerException, MalformedNanoMatonTemplateException {
        //String csv = "/nanopub/ontomaton-nanopub.csv";
        //String csv = "/nanopub/soapdenovo2-nanopub1.csv";
        String csv = "/nanopub/nanopub1.csv";
        System.out.println("csv="+csv);
        String filepath = getClass().getResource(csv).getFile();
        OntoMaton2Nanopub ontoMaton2Nanopub = new OntoMaton2Nanopub();

        //ValueFactory factory = ValueFactoryImpl.getInstance();
        //URI nanopubURI = factory.createURI("http://example.org/nanopub1");

        Nanopub nanopub = ontoMaton2Nanopub.generateNanopub(filepath);

        System.out.println(nanopub.toString());

        //NanopubUtils.writeToStream(nanopub, System.out, RDFFormat.TRIG);
        NanopubUtils.writeToStream(nanopub, System.out, RDFFormat.TURTLE);

//        if (nanopub instanceof NanopubImpl && !((NanopubImpl) nanopub).getNsPrefixes().isEmpty()) {
//            NanopubImpl np = (NanopubImpl) nanopub;
//
//
//            for (String p : np.getNsPrefixes()) {
//                handler.handleNamespace(p, np.getNamespace(p));
//            }
//        }


//        NanopubRdfHandler handler = new NanopubRdfHandler();
//
//        handler.startRDF();
//        String s = nanopub.getUri().toString();
//        if (nanopub instanceof NanopubImpl && !((NanopubImpl) nanopub).getNsPrefixes().isEmpty()) {
//            NanopubImpl np = (NanopubImpl) nanopub;
//            for (String p : np.getNsPrefixes()) {
//                handler.handleNamespace(p, np.getNamespace(p));
//            }
//        } else {
//            handler.handleNamespace("this", s);
//            handler.handleNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
//            handler.handleNamespace("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
//            handler.handleNamespace("rdfg", "http://www.w3.org/2004/03/trix/rdfg-1/");
//            handler.handleNamespace("xsd", "http://www.w3.org/2001/XMLSchema#");
//            handler.handleNamespace("owl", "http://www.w3.org/2002/07/owl#");
//            handler.handleNamespace("dc", "http://purl.org/dc/terms/");
//            handler.handleNamespace("pav", "http://swan.mindinformatics.org/ontologies/1.2/pav/");
//            handler.handleNamespace("np", "http://www.nanopub.org/nschema#");
//        }
//        for (Statement st : getStatements(nanopub)) {
//            handler.handleStatement(st);
//        }
//        handler.endRDF();


    }
}
