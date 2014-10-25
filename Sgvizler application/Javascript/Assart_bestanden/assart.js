var assart = {};
var myOnt = "http://www.semanticweb.org/Art_Ontology#";
/**
 * Extends google.visualization.Map in markers dataMode. Draws
 * textboxes with heading, paragraph, link and image. 
 * 
 * Data Format 2--6 columns:
 * 
 *   1. lat
 *   2. long
 *   3. name  (optional)
 *   4. text  (optional)
 *   5. link  (optional)
 *   6. image (optional)
 * 
 * - If < 4 columns, then behaves just as gMap
 * - Only 6 columns will be read, columns > 6 are ignored.
 * 
 * @class assart.Map
 * @extends sgvizler.charts.Chart
 * @constructor
 * @param {Object} container The container element where the
 * chart will be drawn.
 * @since 0.3.0
 **/

/**
 * Same options available as for google.visualization.Map.
 * 
 * @method draw
 * @public
 * @param {google.visualization.DataTable} data
 * @param {Object} [chartOptions]
 * @since 0.3.0
 **/
assart.Map = sgvizler.chartsAdd(
    "assart",
    "Map",
	function (data, chartOpt)
	{
		/*global google */
		var c, noColumns = data.getNumberOfColumns(),
			r, noRows = data.getNumberOfRows(),
			that = this,
			opt = $.extend({
				dataMode: 'markers',
				showTip: true,
				useMapTypeControl: true,
				linkFunction: null,
				linkAlt: null,
			}, chartOpt),
			chart,
			newData,
			newValue;

		// The idea is to put all columns > 2 into the
		// 3. column with html formatting.

		if (noColumns > 2) {
			newData = data.clone();
			// drop columns > 3 from new
			for (c = noColumns - 1; c > 2; c -= 1) {
				newData.removeColumn(c);
			}

			// build new 3. column
			for (r = 0; r < noRows; r += 1) {
				newValue = "<div about='" + myOnt + assart.encodeURI(data.getValue(r, 0)) + "' class='assart-Map'>";
				
				if (noColumns > 2 && data.getValue(r, 2) !== null)
					newValue += "<b property='foaf:name'>" + data.getValue(r, 2) + "</b>";
					
				if (noColumns > 5 && data.getValue(r, 5) !== null)
					newValue += "<div class='img'><img property='dbo:thumbnail' src='" + data.getValue(r, 5) + "'/></div>";
				
				if (noColumns > 3 && data.getValue(r, 3) !== null)
					newValue += "<p class='text' property='dbo:abstract'>" + data.getValue(r, 3) + "</p>";
				
				if (noColumns > 4 && data.getValue(r, 4) !== null) 
					newValue += "<p class='link'><a property='foaf:homepage' href='" + data.getValue(r, 4) + "'/></p>";
				
				if (opt.linkFunction !== undefined)
					newValue += "<p class='link'><a href='#' onclick='" + opt.linkFunction + "(\"" + assart.encodeURI(data.getValue(r, 2)) + "\");return false;'>" + opt.linkText + "</a></p>";
				
				newValue += "</div>";
				newData.setCell(r, 2, newValue);
			}
		} else { // do nothing.
			newData = data;
		}

		chart = new google.visualization.Map(this.container);
		chart.draw(newData, opt);

		google.visualization.events.addListener(
			chart,
			'ready',
			function () { that.fireListener('ready'); }
		);
	},
	{ 'google.visualization.Map': 'map' }
);


/**
 * @class assart.Layout
 * @extends sgvizler.charts.Chart
 * @constructor
 * @param {Object} container The container element where the
 * chart will be drawn.
 * @since 0.3.0
 **/

/** 
 * Available options:
 * 
 * @method draw
 * @public
 * @param {google.visualization.DataTable} data
 * @param {Object} [chartOptions]
 * @since 0.3.0
 **/
assart.Layout = sgvizler.chartsAdd(
    "assart",
    "Layout",
	function (data, chartOpt)
	{
		var pageContent = assart.generateLayout(data, chartOpt, "assart-Layout");

		$(this.container)
			.empty()
			.html(pageContent);

		this.fireListener("ready");
	}
);

assart.generateLayout = function (data, opt, divClass)
{
	opt = $.extend({row: {}}, opt);

	generateLayoutRow = function (type, column, value, opt)
	{
		var alt = "", row = "";
		if (value !== undefined && value !== null && value !== "")
		{
			value = assart.decodeURI(value);
			alt = value.split("?")[0];
			
			if (opt.row[column] !== undefined && opt.row[column] !== null)
			{
				if ( opt.row[column]["prefix"] !== undefined)
					value = assart.decodeURI(opt.row[column]["prefix"]) + value;
				else if ( opt.row[column]["alt"] !== undefined)
					alt = assart.decodeURI(opt.row[column]["alt"]);
			}
			
			switch (type.toLowerCase())
			{
				case "title":
				case "name":
					row = "<b property='foaf:name'>" + value + "</b>";
				break;
				
				case "description":
				case "text":
				case "abstract":
					row = "<p property='dbo:abstract' class='text'>" + value + "</p>";
				break;
				
				case "image":
				case "thumbnail":
					row = "<img property='dbo:thumbnail' class='img' src=" + value + " alt=" + alt + " width=192px heigh=192px/>";
				break;
				
				case "homepage":
					row = "<p class='link'><a property='foaf:homepage' href='" + value + "'>" + alt + "</a></p>";
				break;
				
				case "url":
					row = "<p class='link'>" + column + ": <a property='myOnt:" + column + "' href='" + value + "'>" + alt + "</a></p>";
				break;
				
				case "price":
				case "cost":
					row = "<p class='price'>" + column + ": €<span property='dbo:cost'>" + value + "</span></p>";
				break;
				
				default:
					if (value.indexOf("http://") > -1 || value.indexOf(".htm") > -1)
						row = "<p class='link'>" + column + ":&#9;<a property='myOnt:" + column + "' href='" + value + "'>" + alt + "</a></p>";
					else
						row = "<p>" + column + ":&#9;<span property='myOnt:" + column + "'>" + value + "</span></p>";
				break;
			}
		}
		return row;
	};
	var	c, noColumns = data.getNumberOfColumns(),
		r, noRows = data.getNumberOfRows(), div;
	
	if (noRows > 0)
	{
		var value = null, column = null, type, prefix,
		
		div = "<div about='" + myOnt + assart.encodeURI(data.getValue(0, 0)) + "' class='" + divClass + "'>";

		for (r = 0; r < noRows; r += 1)
		{
			if (r > 0)
				div += "</div><div about='" + myOnt + assart.encodeURI(data.getValue(r, 0)) + "' class='" + divClass + "'>";
			for (c = 0; c < noColumns; c += 1)
			{
				column = data.getColumnLabel(c);
				value = data.getValue(r, c);
				div += generateLayoutRow(column, column, value, opt);
			}
		}
		for (var column in opt.row)
		{
			value = opt.row[column]["value"];
				
			if (opt.row[column]["type"] == undefined)
				type = column;
			else
				type = opt.row[column]["type"];
					
			div += generateLayoutRow(type, column, value, opt);
		}
		div += "</div>";
	}
	else
	{
		div = "<div class='" + divClass + "'><p>No data available</p></div>";
	}
	
	return div;
};

assart.getUrlValue = function (VarSearch)
{
	var SearchString = window.location.search.substring(1);
	var VariableArray = SearchString.split('&');
	for (var i = 0; i < VariableArray.length; i++)
	{
		var KeyValuePair = VariableArray[i].split('=');
		if (assart.decodeURI(KeyValuePair[0]) == VarSearch)
			return assart.decodeURI(KeyValuePair[1]);
	}
};

assart.encodeURI = function (unencoded) {
	return encodeURIComponent(unencoded).replace(/'/g, "%27").replace(/"/g, "%22");	
}
assart.decodeURI = function (encoded) {
	return decodeURIComponent(encoded).replace(/\%27/g, "\'").replace(/\%22/g, "\"").replace(/\+/g, " ");
}
