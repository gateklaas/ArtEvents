package sparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class SparqlEngine {
    
	public static void sparqlQueryResult(String query) 
	{
		// now creating query object
		Query queryString = QueryFactory.create(query);
		// initializing queryExecution factory with remote service.
		// **this actually was the main problem I couldn't figure out.**
		QueryExecution qexec = QueryExecutionFactory.sparqlService(Constants.SPARQLENDPOINT, queryString);

		//after it goes standard query execution and result processing which can
		// be found in almost any Jena/SPARQL tutorial.
		try {
		    ResultSet results = qexec.execSelect();
		    
		    // Result processing is done here.
			QuerySolution querySolution = results.nextSolution();
			System.out.println(querySolution);
		    //return results;
		}
		finally {
		   qexec.close();
		}
	}	
	
}
