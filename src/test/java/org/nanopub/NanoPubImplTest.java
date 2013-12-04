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

        ValueFactory factory = ValueFactoryImpl.getInstance();

        URI context = factory.createURI("http://example.org/contexts/graph1");

        URI nanopub1URI = factory.createURI("http://example.org/nanopub1");

        Statement nanopub1Statement = factory.createStatement(nanopub1URI, RDF.TYPE ,Nanopub.NANOPUB_TYPE_URI, context);

        URI bob = factory.createURI("http://example.org/bob");
        URI name = factory.createURI("http://example.org/name");
        Literal bobsName = factory.createLiteral("Bob");

        Statement nameStatement = factory.createStatement(bob, name, bobsName, context);


        Collection<Statement> statements = new ArrayList<Statement>();

        statements.add(nanopub1Statement);
        statements.add(nameStatement);

        Nanopub nanopub1 = new NanopubImpl(statements);

        System.out.println("Nanopub1= "+nanopub1);

    }



}
