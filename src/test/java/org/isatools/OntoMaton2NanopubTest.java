package org.isatools;

import org.isatools.nanopub.OntoMaton2Nanopub;
import org.junit.Test;
import org.nanopub.MalformedNanopubException;
import org.nanopub.Nanopub;
import org.nanopub.NanopubUtils;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;


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
    public void generateNanopubTest() throws MalformedNanopubException, RDFHandlerException {
        //String csv = "/nanopub/ontomaton-nanopub.csv";
        String csv = "/nanopub/soapdenovo2-nanopub1.csv";
        System.out.println("csv="+csv);
        String filepath = getClass().getResource(csv).getFile();
        OntoMaton2Nanopub ontoMaton2Nanopub = new OntoMaton2Nanopub();

        //ValueFactory factory = ValueFactoryImpl.getInstance();
        //URI nanopubURI = factory.createURI("http://example.org/nanopub1");

        Nanopub nanopub = ontoMaton2Nanopub.generateNanopub(filepath);

        System.out.println(nanopub.toString());

        NanopubUtils.writeToStream(nanopub, System.out, RDFFormat.TRIG);
    }
}
