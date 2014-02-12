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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by the ISATeam.
 * User: agbeltran
 * Date: 02/12/2013
 * Time: 15:58
 *
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public class OntoMaton2Nanopub {

   private static String HTTP = "http://";

   private URI nanopubURI=null, nanopubGraphURI=null, assertionGraphURI=null, provenanceGraphURI=null, pubInfoGraphURI=null;
   private Map<String,URI> individualURImap = new HashMap<String, URI>();

   public OntoMaton2Nanopub(){}

    /**
     * Main method to use to generate a NanoPub from an OntoMaton template
     *
     * @param csvFilename
     * @return
     * @throws MalformedNanopubException
     */
    public Nanopub generateNanopub(String csvFilename) throws MalformedNanopubException, MalformedNanoMatonTemplateException {

        ValueFactory factory = ValueFactoryImpl.getInstance();

        //default values for graphs URIs
        //nanopubGraphURI = factory.createURI("http://example.org/contexts/graph1");
        //assertionGraphURI = factory.createURI("http://example.org/G1");
        //provenanceGraphURI = factory.createURI("http://example.org/G2");
        //pubInfoGraphURI = factory.createURI("http://example.org/G4");

        Collection<Statement> statementCollection = generateStmtsFromOntoMatonTemplate(csvFilename, factory);
        Nanopub nanopub = new NanopubImpl(statementCollection);

        return nanopub;
    }


    /**
     *
     * Reads the OntoMaton template for creating nanopublications.
     *
     */
    private  Collection<Statement>  generateStmtsFromOntoMatonTemplate(String csvFilename,
                                                                       ValueFactory factory) throws MalformedNanoMatonTemplateException {
        Collection<Statement> statementCollection = new ArrayList<Statement>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
            Statement stmt;

            String[] nextLine = null;
            while ((nextLine = csvReader.readNext()) != null) {

                stmt = null;

                if (nextLine!=null){

                    if (nextLine[0].startsWith((NanoMatonTemplateSyntax.NANOPUB_GRAPH_URI))){

                        nanopubGraphURI = factory.createURI(nextLine[1]);

                    }else if (nextLine[0].startsWith((NanoMatonTemplateSyntax.NANOPUB_URI))){

                        if (nanopubGraphURI == null)
                            throw new MalformedNanoMatonTemplateException("The nanopubGraphURI must be defined first");

                        nanopubURI = factory.createURI(nextLine[1]);
                        stmt = factory.createStatement(nanopubURI, RDF.TYPE, Nanopub.NANOPUB_TYPE_URI, nanopubGraphURI);

                    }else if (nextLine[0].startsWith((NanoMatonTemplateSyntax.ASSERTION_GRAPH_URI))){

                        if (nanopubGraphURI == null)
                            throw new MalformedNanoMatonTemplateException("The nanopubGraphURI must be defined first");

                        assertionGraphURI = factory.createURI(nextLine[1]);
                        stmt = factory.createStatement(nanopubURI, Nanopub.HAS_ASSERTION_URI, assertionGraphURI, nanopubGraphURI);

                    }else if (nextLine[0].startsWith((NanoMatonTemplateSyntax.PUB_INFO_GRAPH_URI))){

                        if (nanopubGraphURI == null)
                            throw new MalformedNanoMatonTemplateException("The nanopubGraphURI must be defined first");

                        pubInfoGraphURI = factory.createURI(nextLine[1]);
                        stmt = factory.createStatement(nanopubURI, Nanopub.HAS_PUBINFO_URI, pubInfoGraphURI, nanopubGraphURI);

                    }else if (nextLine[0].startsWith((NanoMatonTemplateSyntax.PROVENANCE_GRAPH_URI))) {

                        if (nanopubGraphURI == null)
                            throw new MalformedNanoMatonTemplateException("The nanopubGraphURI must be defined first");

                        provenanceGraphURI = factory.createURI(nextLine[1]);
                        stmt = factory.createStatement(nanopubURI, Nanopub.HAS_PROVENANCE_URI, provenanceGraphURI, nanopubGraphURI);

                    }else if (nextLine[0].startsWith(NanoMatonTemplateSyntax.ASSERTION)){

                        stmt = parseStatement(nextLine, factory, assertionGraphURI);

                    } else if (nextLine[0].startsWith(NanoMatonTemplateSyntax.PROVENANCE)){

                        stmt = parseStatement(nextLine, factory, provenanceGraphURI);

                    } else if (nextLine[0].startsWith(NanoMatonTemplateSyntax.PUB_INFO)){

                        stmt = parseStatement(nextLine, factory, pubInfoGraphURI);

                    }

                    if (stmt!=null)
                        statementCollection.add(stmt);

                }

            } //while

        }catch(FileNotFoundException fnfex){
            fnfex.printStackTrace();
        }catch(IOException ioex){
            ioex.printStackTrace();
        }

        return statementCollection;
    }


    private Statement parseStatement(String[] line, ValueFactory factory, URI graphURI){

        URI subject = null, predicate = null, object = null;
        Statement stmt = null;

        if (line[1].startsWith(HTTP))
            subject = factory.createURI(line[1]);
        else
            subject = factory.createURI(nanopubURI+"/"+line[1]);

        if (line[2].startsWith(HTTP))
            predicate = factory.createURI(line[2]);
        else
            predicate = factory.createURI(nanopubURI+"/"+line[1]);


        if (line[3].startsWith(HTTP))
            object = factory.createURI(line[3]);
        else
            object = factory.createURI(nanopubURI+"/"+line[1]);


        if (subject!=null && predicate !=null && object !=null && graphURI!=null)
            stmt = factory.createStatement(subject, predicate, object, graphURI);

        return stmt;
    }




}
