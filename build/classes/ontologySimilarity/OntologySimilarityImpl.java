
package ontologySimilarity;

import ontologyManager.OntologyManager;
import static java.lang.System.out;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * @author Alok Dhamanaskar (alokd@uga.edu)
 * @see LICENSE (MIT style license file). 
 * 
 * The class that implements all the methods for OntologySimnilarity interface.
 */
public class OntologySimilarityImpl implements OntologySimilarity {

    private final static Logger log = Logger.getLogger(OntologySimilarityImpl.class.getName());
        
    /**
     * Method that, given 2 OWL classes from the same Ontology computes a similarity score
     * @param class1 OWLClass1
     * @param class2 OWLClass1
     * @param owlURI URI for the Ontology File, Can be a local file / URL
     * @return A similarity Score between O and 1
     */
    @Override
    public double getConceptSimScore(OWLClass class1, OWLClass class2, String owlURI) 
    {
        if(class1 == null || class2 == null)
        {
            log.log(Level.WARNING, "Class1 or Class2 cannot be null; Concept Similarity score returned 0");
            return 0.0;
        }//if
        else
        {
            return ConceptSimilarity.getConceptSimScore(class1, class2, owlURI);
        }//else
    
    }//getConceptSimScore
    

    /**
     * Method that, given IRI for 2 OWL classes from the same Ontology computes a similarity score
     * @param class1 IRI for OWLClass1
     * @param class2 IRI for OWLClass2
     * @param owlURI URI for the Ontology File, Can be a local file / URL
     * @return A similarity Score between O and 1    
     */        
    @Override
    public double getConceptSimScore(String class1, String class2, String owlURI)
    {
        if( owlURI == null || owlURI.trim().equals(""))
        {
            log.log(Level.WARNING, "Location of ontology file is null/empty Concept Similarity score returned 0");
            return 0.0;
        }//if
        else if(class1 == null || class2 == null  || class1.trim().equals("") || class2.trim().equals(""))
        {
            log.log(Level.WARNING, "URI for Class1/Class2 is null/empty, Concept Similarity score returned 0");
            return 0.0;        
        }//else if
        else
        {
            OntologyManager parser = OntologyManager.getInstance(owlURI);

            OWLClass OWLclass1 = parser.getConceptClass(class1.trim());
            OWLClass OWLclass2 = parser.getConceptClass(class2.trim());

            if(OWLclass1 == null)
            {
                log.log(Level.WARNING, "No class found for IRI ("+class1+") in Ontology "+owlURI+" \nConcept Similarity score returned 0");
                return 0.0;
            }//if
            else if(OWLclass2 == null)
            {
                log.log(Level.WARNING, "No class found for IRI ("+class2+"); in Ontology "+owlURI+" \nConcept Similarity score returned 0");
                return 0.0;            
            }//else if
            else
            {
                return this.getConceptSimScore(OWLclass1, OWLclass2, owlURI);        
            }//else

        }//else

    }//getConceptSimScore
    
    public static void main(String[] args)
    {
        //Test code
        
        OntologySimilarity OntSim= new OntologySimilarityImpl();
        String class1 = "http://purl.obolibrary.org/obo/OBIws_0000100";
        String class2 = "http://purl.obolibrary.org/obo/OBIws_0000034 ";
        String owlURI = "http://obi-webservice.googlecode.com/svn/trunk/ontology/view/webService.owl";
        
        double score = OntSim.getConceptSimScore(class2, class1, owlURI);
        out.println("Score = "+ score);

        OntologyManager parser = OntologyManager.getInstance(owlURI);
        
        OWLClass OWLclass1 = parser.getConceptClass(class1);
        //score = OntSim.getConceptSimScore(OWLclass1, null, owlURI);
        //out.println("Score = "+ score);    
    
    }//main


}//OntologySimilarity
