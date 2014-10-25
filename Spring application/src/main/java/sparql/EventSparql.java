package sparql;

import model.Event;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class EventSparql {

	public static Object findEventByMuseumName(String museumName) {
		Event event = null;

		// now creating query object
		Query query = QueryFactory.create(String.format(
				Constants.queryFindEventByMuseumName, museumName));
		// initializing queryExecution factory with remote service.
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				Constants.SPARQLENDPOINT, query);

		// after it goes standard query execution and result processing which
		// can
		// be found in almost any Jena/SPARQL tutorial.
		try {
			System.out.println(qexec);
			ResultSet results = qexec.execSelect();

			System.out.println(results);
			results.hasNext();

			// Result processing is done here.
			QuerySolution querySolution = results.nextSolution();

			long id = results.getRowNumber();
			String name = querySolution.getLiteral("name").getString();
			String beginning = querySolution.getLiteral("beginning")
					.getString();
			String end = querySolution.getLiteral("end").getString();
			float cost = querySolution.getLiteral("cost").getFloat();
			float latitude = querySolution.getLiteral("lat").getFloat();
			float longitude = querySolution.getLiteral("long").getFloat();

			event = new Event(id, name, beginning, end, cost, latitude,
					longitude);
		} finally {
			qexec.close();
		}
		return event;

	}
}
