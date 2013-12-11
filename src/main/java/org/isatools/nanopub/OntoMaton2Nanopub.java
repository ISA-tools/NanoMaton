package org.isatools.nanopub;

import au.com.bytecode.opencsv.CSVReader;
import org.nanopub.MalformedNanopubException;
import org.nanopub.Nanopub;
import org.nanopub.NanopubImpl;
import org.openrdf.model.Statement;

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
     *
     */
    private void readOntoMatonTemplate(String csvFilename){
        try {
            CSVReader csvReader = new CSVReader(new FileReader(csvFilename));

            String[] nextLine = null;


            while ((nextLine = csvReader.readNext()) != null) {


            }


        }catch(FileNotFoundException fnfex){
            fnfex.printStackTrace();
        }catch(IOException ioex){
            ioex.printStackTrace();
        }

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
    public Nanopub generateNanopub(String csvFilename) throws MalformedNanopubException {

        readOntoMatonTemplate(csvFilename);

        Collection<Statement> statementCollection = generateStatements();
        Nanopub nanopub = new NanopubImpl(statementCollection);

        return nanopub;
    }



}
