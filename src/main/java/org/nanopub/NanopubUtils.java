package org.nanopub;

import org.openrdf.model.Statement;
import org.openrdf.rio.*;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tobias Kuhn
 */
public class NanopubUtils {

    static {
        RDFWriterRegistry.getInstance().add(new CustomTrigWriterFactory());
    }

    private NanopubUtils() {}  // no instances allowed

    public static List<Statement> getStatements(Nanopub nanopub) {
        List<Statement> s = new ArrayList<>();
        s.addAll(nanopub.getHead());
        s.addAll(nanopub.getAssertion());
        s.addAll(nanopub.getProvenance());
        s.addAll(nanopub.getPubinfo());
        return s;
    }

    public static void writeToStream(Nanopub nanopub, OutputStream out, RDFFormat format)
            throws RDFHandlerException {
        RDFWriter writer = Rio.createWriter(format, out);
        propagateToHandler(nanopub, writer);
    }

    public static void propagateToHandler(Nanopub nanopub, RDFHandler handler)
            throws RDFHandlerException {
        handler.startRDF();
        String s = nanopub.getUri().toString();
        if (nanopub instanceof NanopubImpl && !((NanopubImpl) nanopub).getNsPrefixes().isEmpty()) {
            NanopubImpl np = (NanopubImpl) nanopub;
            for (String p : np.getNsPrefixes()) {
                handler.handleNamespace(p, np.getNamespace(p));
            }
        } else {
            handler.handleNamespace("this", s);
            handler.handleNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
            handler.handleNamespace("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
            handler.handleNamespace("rdfg", "http://www.w3.org/2004/03/trix/rdfg-1/");
            handler.handleNamespace("xsd", "http://www.w3.org/2001/XMLSchema#");
            handler.handleNamespace("owl", "http://www.w3.org/2002/07/owl#");
            handler.handleNamespace("dc", "http://purl.org/dc/terms/");
            handler.handleNamespace("pav", "http://swan.mindinformatics.org/ontologies/1.2/pav/");
            handler.handleNamespace("np", "http://www.nanopub.org/nschema#");
        }
        for (Statement st : getStatements(nanopub)) {
            handler.handleStatement(st);
        }
        handler.endRDF();
    }

}
