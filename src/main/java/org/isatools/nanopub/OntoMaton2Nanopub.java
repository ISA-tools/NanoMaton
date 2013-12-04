package org.isatools.nanopub;

import org.nanopub.MalformedNanopubException;
import org.nanopub.Nanopub;
import org.nanopub.NanopubImpl;
import org.openrdf.model.Statement;

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
     *
     */
    private void readOntoMatonTemplate(){

    }

    private Collection<Statement> generateStatements(){
        Collection<Statement> statementCollection = new ArrayList<Statement>();

        return statementCollection;
    }


    /**
     *
     *
     * @throws MalformedNanopubException
     */
    public Nanopub generateNanopub() throws MalformedNanopubException {

        readOntoMatonTemplate();

        Collection<Statement> statementCollection = generateStatements();
        Nanopub nanopub = new NanopubImpl(statementCollection);

        return nanopub;
    }



}
