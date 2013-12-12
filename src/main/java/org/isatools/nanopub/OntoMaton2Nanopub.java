package org.isatools.nanopub;

import au.com.bytecode.opencsv.CSVReader;
import org.nanopub.MalformedNanopubException;
import org.nanopub.Nanopub;
import org.nanopub.NanopubImpl;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.RDF;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by the ISATeam.
 * User: agbeltran
 * Date: 02/12/2013
 * Time: 15:58
 *
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public class OntoMaton2Nanopub {

   public OntoMaton2Nanopub(){}

    /**
     *
     * Reads the OntoMaton template for creating nanopublications.
     *
     */
    private  Collection<Statement>  generateStmtsFromOntoMatonTemplate(String csvFilename){
        Collection<Statement> statementCollection = new ArrayList<Statement>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(csvFilename));

            String[] nextLine = null;
            while ((nextLine = csvReader.readNext()) != null) {

                if (nextLine!=null){

                    if (nextLine[0].equals(Nanopub.ASSERTION)){

                        System.out.println("assertion" + nextLine[1]);

                    } else if (nextLine[0].equals(Nanopub.SUPPORTING)){

                    } else if (nextLine[0].equals(Nanopub.PROVENANCE)){

                    }

                }

            }

        }catch(FileNotFoundException fnfex){
            fnfex.printStackTrace();
        }catch(IOException ioex){
            ioex.printStackTrace();
        }

        return statementCollection;
    }




    /**
     *
     *
     * @throws MalformedNanopubException
     */
    public Nanopub generateNanopub(String csvFilename) throws MalformedNanopubException {

        Collection<Statement> statementCollection = generateStmtsFromOntoMatonTemplate(csvFilename);
        Nanopub nanopub = new NanopubImpl(statementCollection);


        ValueFactory factory = ValueFactoryImpl.getInstance();

        URI context = factory.createURI("http://example.org/contexts/graph1");

        URI nanopub1URI = factory.createURI("http://example.org/nanopub1");

        URI assertionGraphURI = factory.createURI("http://example.org/G1");

        URI provenanceGraphURI = factory.createURI("http://example.org/G2");

        URI pubInfoGraphURI = factory.createURI("http://example.org/G4");

        Statement nanopub1Statement1 = factory.createStatement(nanopub1URI, RDF.TYPE ,Nanopub.NANOPUB_TYPE_URI, context);
        Statement nanopub1Statement2 = factory.createStatement(nanopub1URI, Nanopub.HAS_ASSERTION_URI, assertionGraphURI, context);
        Statement nanopub1Statement3 = factory.createStatement(nanopub1URI, Nanopub.HAS_PROVENANCE_URI, provenanceGraphURI, context);
        Statement nanopub1Statement4 = factory.createStatement(nanopub1URI, Nanopub.HAS_PUBINFO_URI, pubInfoGraphURI, context);


        return nanopub;
    }



}
