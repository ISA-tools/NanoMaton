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
     * Main method to use to generate a NanoPub from an OntoMaton template
     *
     * @param csvFilename
     * @param nanopubURI
     * @return
     * @throws MalformedNanopubException
     */
    public Nanopub generateNanopub(String csvFilename, URI nanopubURI) throws MalformedNanopubException {


        ValueFactory factory = ValueFactoryImpl.getInstance();

        URI nanopubGraphURI = factory.createURI("http://example.org/contexts/graph1");

        URI assertionGraphURI = factory.createURI("http://example.org/G1");

        URI provenanceGraphURI = factory.createURI("http://example.org/G2");

        URI pubInfoGraphURI = factory.createURI("http://example.org/G4");


        Collection<Statement> statementCollection = generateStmtsFromOntoMatonTemplate(csvFilename, factory , nanopubURI, assertionGraphURI, provenanceGraphURI, pubInfoGraphURI);

        Statement nanopub1Statement1 = factory.createStatement(nanopubURI, RDF.TYPE, Nanopub.NANOPUB_TYPE_URI, nanopubGraphURI);
        Statement nanopub1Statement2 = factory.createStatement(nanopubURI, Nanopub.HAS_ASSERTION_URI, assertionGraphURI, nanopubGraphURI);
        Statement nanopub1Statement3 = factory.createStatement(nanopubURI, Nanopub.HAS_PROVENANCE_URI, provenanceGraphURI, nanopubGraphURI);
        Statement nanopub1Statement4 = factory.createStatement(nanopubURI, Nanopub.HAS_PUBINFO_URI, pubInfoGraphURI, nanopubGraphURI);

        statementCollection.add(nanopub1Statement1);
        statementCollection.add(nanopub1Statement2);
        statementCollection.add(nanopub1Statement3);
        statementCollection.add(nanopub1Statement4);

        Nanopub nanopub = new NanopubImpl(statementCollection);

        return nanopub;
    }


    /**
     *
     * Reads the OntoMaton template for creating nanopublications.
     *
     */
    private  Collection<Statement>  generateStmtsFromOntoMatonTemplate(String csvFilename, ValueFactory factory, URI nanopubURI, URI assertionGraphURI, URI provenanceGraphURI, URI pubInfoGraphURI){
        Collection<Statement> statementCollection = new ArrayList<Statement>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
            Statement stmt;

            String[] nextLine = null;
            while ((nextLine = csvReader.readNext()) != null) {

                stmt = null;

                if (nextLine!=null){


                    if (nextLine[0].startsWith(Nanopub.ASSERTION)){


                        stmt = factory.createStatement(factory.createURI(nextLine[1]), factory.createURI(nextLine[2]), factory.createURI(nextLine[3]), assertionGraphURI);


                    } else if (nextLine[0].startsWith(Nanopub.SUPPORTING)){

                        stmt = factory.createStatement(factory.createURI(nextLine[1]), factory.createURI(nextLine[2]), factory.createURI(nextLine[3]), provenanceGraphURI);

                    } else if (nextLine[0].startsWith(Nanopub.PROVENANCE)){

                        stmt =  factory.createStatement(factory.createURI(nextLine[1]), factory.createURI(nextLine[2]), factory.createURI(nextLine[3]), pubInfoGraphURI);

                    }

                    if (stmt!=null)
                        statementCollection.add(stmt);

                }

            }

        }catch(FileNotFoundException fnfex){
            fnfex.printStackTrace();
        }catch(IOException ioex){
            ioex.printStackTrace();
        }

        return statementCollection;
    }


    private Statement parseStatement(String[] line, ValueFactory factory, URI graphURI){

        Statement stmt =  factory.createStatement(factory.createURI(line[1]), factory.createURI(line[2]), factory.createURI(line[3]), graphURI);

    }




}
