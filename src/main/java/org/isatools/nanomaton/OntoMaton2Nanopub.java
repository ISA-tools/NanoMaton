package org.isatools.nanomaton;

import au.com.bytecode.opencsv.CSVReader;
import org.nanopub.MalformedNanopubException;
import org.nanopub.Nanopub;
import org.nanopub.NanopubImpl;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.URIImpl;
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

   private static final String HTTP = "http://";
   private static final URI SUB_GRAPH_OF = new URIImpl("http://www.w3.org/2004/03/trix/rdfg-1/subGraphOf");

   private URI nanopubURI=null, assertionGraphURI=null, provenanceGraphURI=null, pubInfoGraphURI=null, parentGraphURI=null;
   private Map<String,URI> individualURImap = new HashMap<String, URI>();

    private Collection<Statement> statementCollection = null;
    private Map<String, String> namespaces = null;

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

        statementCollection = new ArrayList<Statement>();
        namespaces = new HashMap<String, String>();
        namespaces.put("np", "http://www.nanopub.org/nschema#");

        parseNanoMatonTemplate(csvFilename, factory);
        Nanopub nanopub = new NanopubImpl(statementCollection, new ArrayList(namespaces.keySet()), namespaces);

        return nanopub;
    }


    /**
     *
     * Reads the OntoMaton template for creating nanopublications.
     *
     */
    private  void parseNanoMatonTemplate(String csvFilename,
                                         ValueFactory factory) throws MalformedNanoMatonTemplateException {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
            Statement stmt;

            String[] nextLine = null;
            while ((nextLine = csvReader.readNext()) != null) {

                stmt = null;

                if (nextLine!=null){

                    if (nextLine[0].startsWith((NanoMatonTemplateSyntax.NANOPUB_URI))){

                        nanopubURI = factory.createURI(nextLine[1]);
                        stmt = factory.createStatement(nanopubURI, RDF.TYPE, Nanopub.NANOPUB_TYPE_URI, nanopubURI);
                        namespaces.put("this", nanopubURI.toString());

                    }else if (nextLine[0].startsWith((NanoMatonTemplateSyntax.ASSERTION_GRAPH_URI))){

                        assertionGraphURI = factory.createURI(nextLine[1]);
                        stmt = factory.createStatement(nanopubURI, Nanopub.HAS_ASSERTION_URI, assertionGraphURI, nanopubURI);
                        namespaces.put("assertion", assertionGraphURI.toString());

                    }else if (nextLine[0].startsWith((NanoMatonTemplateSyntax.PUB_INFO_GRAPH_URI))){

                        pubInfoGraphURI = factory.createURI(nextLine[1]);
                        stmt = factory.createStatement(nanopubURI, Nanopub.HAS_PUBINFO_URI, pubInfoGraphURI, nanopubURI);
                        namespaces.put("pubInfo", pubInfoGraphURI.toString());

                    }else if (nextLine[0].startsWith((NanoMatonTemplateSyntax.PROVENANCE_GRAPH_URI))) {

                        provenanceGraphURI = factory.createURI(nextLine[1]);
                        stmt = factory.createStatement(nanopubURI, Nanopub.HAS_PROVENANCE_URI, provenanceGraphURI, nanopubURI);
                        namespaces.put("provenance", provenanceGraphURI.toString());

                    }else if (nextLine[0].startsWith((NanoMatonTemplateSyntax.PARENT_GRAPH_URI))) {

                        parentGraphURI = factory.createURI(nextLine[1]);
                        stmt = factory.createStatement(nanopubURI,SUB_GRAPH_OF,parentGraphURI, nanopubURI);
                        namespaces.put("rdfg","http://www.w3.org/2004/03/trix/rdfg-1/");


                    }else if (nextLine[0].startsWith(NanoMatonTemplateSyntax.ASSERTION)){

                        stmt = parseStatement(nextLine, factory, assertionGraphURI);

                    } else if (nextLine[0].startsWith(NanoMatonTemplateSyntax.PROVENANCE)){

                        stmt = parseStatement(nextLine, factory, provenanceGraphURI);

                    } else if (nextLine[0].startsWith(NanoMatonTemplateSyntax.PUB_INFO)){

                        stmt = parseStatement(nextLine, factory, pubInfoGraphURI);

                    } else if (nextLine[0].startsWith(NanoMatonTemplateSyntax.PREFIX)){


                        if (!nextLine[1].equals("") && !nextLine[2].equals(""))
                            namespaces.put(nextLine[1], nextLine[2]);

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

    }


    /**
     * Parse a line in the OntoMaton template as an RDF Statement
     *
     * @param line
     * @param factory
     * @param graphURI
     * @return
     */
    private Statement parseStatement(String[] line, ValueFactory factory, URI graphURI){

        URI subject = null, predicate = null, object = null;
        Literal literalObject = null;
        Statement stmt = null;

        if (line[1].startsWith(HTTP))
            subject = factory.createURI(line[1]);
        else {
            subject = factory.createURI(nanopubURI.toString(), line[1]);
            individualURImap.put(line[1], subject);
        }

        if (line[2].startsWith(HTTP))
            predicate = factory.createURI(line[2]);
        else
            predicate = factory.createURI(nanopubURI.toString(),line[2]);

        if (line[3].startsWith(HTTP))
            object = factory.createURI(line[3]);
        else {
            //it is either an object previously defined (see list of subjects) or a literal
            object = individualURImap.get(line[3]);

            if (object==null && line[4]!=null && !line[4].equals("")) {
                literalObject = factory.createLiteral(line[3], line[4]);
            }else {
                object = factory.createURI(nanopubURI.toString(), line[3]);
                individualURImap.put(line[3], object);
            }
        }

        if (subject!=null && predicate !=null && object !=null && graphURI!=null)
            stmt = factory.createStatement(subject, predicate, object, graphURI);

        if (subject!=null && predicate !=null && literalObject !=null && graphURI!=null)
            stmt = factory.createStatement(subject, predicate, literalObject, graphURI);

        return stmt;
    }




}
