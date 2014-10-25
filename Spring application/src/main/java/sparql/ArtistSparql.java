package sparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class ArtistSparql {

	// public static void main(String[] args)
	// {
	// new ArtistSparql();
	// }

	public ArtistSparql() {
		// now creating query object
		Query query = QueryFactory.create(Constants.queryMuseumAmsterdam);
		// initializing queryExecution factory with remote service.
		// **this actually was the main problem I couldn't figure out.**
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				Constants.SPARQLENDPOINT, query);

		// after it goes standard query execution and result processing which
		// can
		// be found in almost any Jena/SPARQL tutorial.
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {

				// Result processing is done here.
				QuerySolution querySolution = results.nextSolution();
				System.out.println(querySolution.get("museum") + "  "
						+ querySolution.get("name") + "  "
						+ querySolution.get("lat") + "  "
						+ querySolution.get("long"));
			}
		} finally {
			qexec.close();
		}

	}
}
