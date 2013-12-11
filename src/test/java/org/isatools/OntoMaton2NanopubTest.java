package org.isatools;

import org.isatools.nanopub.OntoMaton2Nanopub;
import org.junit.Test;
import org.nanopub.MalformedNanopubException;

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
    public void generateNanopubTest() throws MalformedNanopubException {
        String csv = "/nanopub/ontomaton-nanopub.csv";
        System.out.println("csv="+csv);
        String filepath = getClass().getResource(csv).getFile();
        OntoMaton2Nanopub ontoMaton2Nanopub = new OntoMaton2Nanopub();
        ontoMaton2Nanopub.generateNanopub(filepath);
    }
}
