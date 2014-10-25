package sparql;

import java.util.ArrayList;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import model.Museum;

public class MuseumSparql {

//	public static void main(String[] args) 
//	{
//		new MuseumSparql();
//	}
	
	public static Museum findMuseumByName(String museumName) 
	{
		Museum museum = null;
		
		// now creating query object
		Query query = QueryFactory.create(String.format(Constants.querySearchMuseumInAmsterdam, museumName));
		// initializing queryExecution factory with remote service.
		QueryExecution qexec = QueryExecutionFactory.sparqlService(Constants.SPARQLENDPOINT, query);

		//after it goes standard query execution and result processing which can
		// be found in almost any Jena/SPARQL tutorial.
		try {
		    ResultSet results = qexec.execSelect();
		    
		    System.out.println(results); 
		    results.hasNext();

		    // Result processing is done here.
			QuerySolution querySolution = results.nextSolution();
			
			long id = results.getRowNumber();
			String name = querySolution.getLiteral("name").getString();
			float latitude = querySolution.getLiteral("lat").getFloat();
			float longitude = querySolution.getLiteral("long").getFloat();
			
			museum = new Museum(id, name, latitude, longitude);
		}
		finally {
		   qexec.close();
		}
		return museum;

	}	
	
	public static ArrayList<Museum> findMuseums() 
	{
		ArrayList<Museum> museums = new ArrayList<Museum>();
		
		// now creating query object
		Query query = QueryFactory.create(Constants.queryMuseumAmsterdam);
		// initializing queryExecution factory with remote service.
		// **this actually was the main problem I couldn't figure out.**
		QueryExecution qexec = QueryExecutionFactory.sparqlService(Constants.SPARQLENDPOINT, query);

		//after it goes standard query execution and result processing which can
		// be found in almost any Jena/SPARQL tutorial.
		try {
		    ResultSet results = qexec.execSelect();
		    
		    System.out.println(results); 
		    for (; results.hasNext();) {

		    // Result processing is done here.
			QuerySolution querySolution = results.nextSolution();
			
			long id = results.getRowNumber();
			String name = querySolution.getLiteral("name").getString();
			float latitude = querySolution.getLiteral("lat").getFloat();
			float longitude = querySolution.getLiteral("long").getFloat();
			
			Museum museum = new Museum(id, name, latitude, longitude);
			museums.add(museum);
		    }
		}
		finally {
		   qexec.close();
		}
		return museums;

	}	
}

