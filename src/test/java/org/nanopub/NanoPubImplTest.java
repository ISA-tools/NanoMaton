package org.nanopub;

import org.junit.Test;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.RDF;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by the ISATeam.
 * User: agbeltran
 * Date: 04/12/2013
 * Time: 13:23
 *
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public class NanoPubImplTest {


    @Test
    public void nanopubTest() throws MalformedNanopubException {

        /**
         @prefix : <http://www.example.org/mynanopub/>.
         @prefix ex: <http://www.example.org/>.
         @prefix np: <http://www.nanopub.org/nschema#>.
         @prefix dct: <http://purl.org/dc/terms/>.
         @prefix go: <http://purl.obolibrary.org/obo/>.
         @prefix up: <http://purl.uniprot.org/core/> .
         @prefix pav: <http://swan.mindinformatics.org/ontologies/1.2/pav/>
         @prefix xsd: <http://www.w3.org/2001/XMLSchema#>.

         {
         :nanopub1 np:hasAssertion :G1;
         np:hasProvenance :G2;
         np:hasSupporting :G3.
         :G1 a np:Assertion.
         :G2 a np:Provenance.
         :G3 a np:Supporting.
         }

         :G1 {
         <http://purl.uniprot.org/uniprot/O76074>
         up:classifiedWith go:GO_0000287, go:GO_0005737, go:GO_0007165,
         go:GO_0008270, go:GO_0009187, go:GO_0030553.
         }

         :G2 {
         :nanopub1 pav:versionNumber "1.1"
         :nanopub1 pav:previousVersion "1.0".
         :nanopub1 dct:created "2009-09-03"^^xsd:date.
         :nanopub1 dct:creator ex:JohnSmith.
         :nanopub1 dct:rightsHolder ex:SomeOrganization.
         :nanopub1 up:citation <http://bio2rdf.org/medline:99320215>.
         }
         */


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

        URI bob = factory.createURI("http://example.org/bob");
        URI name = factory.createURI("http://example.org/name");
        Literal bobsName = factory.createLiteral("Bob");

        Statement nameStatement = factory.createStatement(bob, name, bobsName, context);


        Collection<Statement> statements = new ArrayList<Statement>();

        statements.add(nanopub1Statement1);
        statements.add(nanopub1Statement2);
        statements.add(nanopub1Statement3);
        //statements.add(nanopub1Statement4);
        statements.add(nameStatement);

        Nanopub nanopub1 = new NanopubImpl(statements);

        System.out.println("Nanopub1= "+nanopub1);

    }



}
