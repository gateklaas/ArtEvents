package sparql;

public class Constants {
	
	final static String SPARQLENDPOINT = "http://localhost:3030/art/sparql"; 
	
	final static String PREFIX = "PREFIX base: <http://www.semanticweb.org/Art_Ontology#> "
			+ "PREFIX rdf:	<http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
			+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> "
			+ "PREFIX time: <http://www.w3.org/2006/time#> "
			+ "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> "
			+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
			+ "PREFIX dbo: <http://dbpedia.org/ontology/> "
			+ "PREFIX dbp: <http://dbpedia.org/property/> "
			+ "PREFIX dbr: <http://dbpedia.org/resource/> "
			+ "PREFIX ah: <http://purl.org/artsholland/1.0/> "
			+ "PREFIX dc: <http://purl.org/dc/terms/> "
			+ "PREFIX gr: <http://purl.org/goodrelations/v1#> ";
	

	
	final static String queryMuseumAmsterdam =
			PREFIX +
			"SELECT DISTINCT ?museum ?name ?lat ?long " +
			"WHERE " +
			"{ " +
			"	SERVICE <http://dbpedia.org/sparql> " +
			"	{ " +
			"		?museum rdf:type dbo:Museum; " +
			"		dbo:location dbr:Amsterdam;" +
			"		" +
			"		OPTIONAL { ?museum rdfs:label ?name;" +
			"			geo:lat ?lat;" +
			"			geo:long ?long ." +
			"		}" +
			"		Filter(lang(?name) = 'en') ." +
			"	} " +
			"}";
	
	final static String querySearchMuseumInAmsterdam =
			PREFIX +
			"SELECT DISTINCT ?museum ?name ?lat ?long WHERE " +
			"{ " +
			"	SERVICE <http://dbpedia.org/sparql> " +
			"	{ " +
			"		?museum rdf:type dbo:Museum; " +
			"		dbo:location dbr:Amsterdam;" +
			"		" +
			"		OPTIONAL { ?museum rdfs:label ?name;" +
			"			geo:lat ?lat;" +
			"			geo:long ?long ." +
			"		}" +
			"		Filter(lang(?name) = 'en')." +
			"		Filter(regex(STR(?name), '%s', 'i'))" + //"Filter(STR(?name) = '%s') ." +
			"	} " +
			"}";
	
	final static String queryArtist =
			PREFIX +
			"SELECT DISTINCT ?museum ?name ?lat ?long " +
			"	WHERE " +
			"	{ " +
			"		SERVICE <http://dbpedia.org/sparql> " +
			"		{ " +
			"			?museum rdf:type dbo:Museum; " +
			"			dbo:location dbr:Amsterdam;" +
			"			" +
			"			OPTIONAL { ?museum rdfs:label ?name;" +
			"				geo:lat ?lat;" +
			"				geo:long ?long ." +
			"		}" +
			"		Filter(lang(?name) = 'en') ." +
			"	} " +
			"}";
	
	final static String queryFindEventByMuseumName =
			PREFIX +
			"SELECT DISTINCT ?name ?description ?start_date ?end_date ?price WHERE " +
			"{ " +
			"	BIND (NOW() AS ?now). " +
			"	SERVICE <http://api.artsholland.com/sparql> " +
			"	{ " +
			"		?event ah:venue/dc:title ?museum_name. " +
			"		?event time:hasEnd ?end_date.  " +
			"		OPTIONAL { ?event dc:title ?name. FILTER (langMatches(lang(?name), 'nl')) } " +
			"		OPTIONAL { ?event dc:title ?name. FILTER (langMatches(lang(?name), 'en')) } " +
			"		OPTIONAL { ?event dc:description ?description. FILTER (langMatches(lang(?description), 'nl')) } " +
			"		OPTIONAL { ?event dc:description ?description. FILTER (langMatches(lang(?description), 'en')) } " +
			"		OPTIONAL " +
			"		{ " +
			"			?event ah:production ?production. " +
			"			OPTIONAL { ?production dc:title ?name. FILTER (langMatches(lang(?name), 'nl')) } " +
			"			OPTIONAL { ?production dc:title ?name. FILTER (langMatches(lang(?name), 'en')) } " +
			"			OPTIONAL { ?production dc:description ?description. FILTER (langMatches(lang(?description), 'nl')) } " +
			"			OPTIONAL { ?production dc:description ?description. FILTER (langMatches(lang(?description), 'en')) } " +
			"		} " +
			"		OPTIONAL { ?event gr:offers/gr:hasPriceSpecification/gr:hasMaxCurrencyValue ?price. } " +
			"		OPTIONAL { ?event time:hasBeginning ?start_date. } " +
			"		FILTER ((regex(STR(?museum_name), '%s', 'i')) && (?end_date > ?now)) " +
			"	} " +
			"} " +
			"LIMIT 25";
	
}
