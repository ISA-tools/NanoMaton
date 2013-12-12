package org.nanopub;

import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

import java.util.Calendar;
import java.util.Set;

/**
 *
 * Nanopub interface
 *
 * Extended Tobias's interface to include Supporting statements.
 *
 * @author Tobias Kuhn
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public interface Nanopub {

    //elements from the schema
    public static final String ASSERTION = "Assertion";
    public static final String SUPPORTING = "Supporting";
    public static final String PROVENANCE = "Provenance";

    public static final URI NANOPUB_TYPE_URI = new URIImpl("http://www.nanopub.org/nschema#Nanopublication");
    public static final URI HAS_ASSERTION_URI = new URIImpl("http://www.nanopub.org/nschema#hasAssertion");
    public static final URI HAS_PROVENANCE_URI = new URIImpl("http://www.nanopub.org/nschema#hasProvenance");
    public static final URI HAS_PUBINFO_URI = new URIImpl("http://www.nanopub.org/nschema#hasPublicationInfo");

    public URI getUri();

    public URI getHeadUri();

    public Set<Statement> getHead();

    public URI getAssertionUri();

    public Set<Statement> getAssertion();

    public URI getProvenanceUri();

    public Set<Statement> getProvenance();

    public URI getPubinfoUri();

    public Set<Statement> getPubinfo();

    public Set<URI> getGraphUris();

    public Calendar getCreationTime();

    public Set<URI> getAuthors();

    public Set<URI> getCreators();

    public Set<URI> getSupporting();

}
