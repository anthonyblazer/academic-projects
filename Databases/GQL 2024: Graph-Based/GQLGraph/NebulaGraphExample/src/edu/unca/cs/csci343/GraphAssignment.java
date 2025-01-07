package edu.unca.cs.csci343;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.json.JSONObject;

import com.vesoft.nebula.driver.graph.data.Node;
import com.vesoft.nebula.driver.graph.data.ResultSet;
import com.vesoft.nebula.driver.graph.data.ValueWrapper;
import com.vesoft.nebula.driver.graph.exception.AuthFailedException;
import com.vesoft.nebula.driver.graph.exception.IOErrorException;
import com.vesoft.nebula.driver.graph.net.NebulaClient;

public class GraphAssignment {
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("ConnectionExample");
    private static String cursor_node_id;
    
    public static void main(String[] args) throws AuthFailedException, IOErrorException, UnknownHostException {
    	String username = "ablazer1";
    	NebulaClient client = NebulaClient.builder("localhost:9669", username, "Nebula1234").build();

    	String schema_select = "SESSION SET SCHEMA `/" + username + "`";
    	client.execute(schema_select);


    	// ############ GQL Statements ##################################
    	String gql_string = "CREATE GRAPH TYPE IF NOT EXISTS UniversityType AS {\n"
    			+ "NODE Student ( {stuId STRING PRIMARY KEY, firstName String, lastName STRING,\n"
    			+ "credits INT64} ),\n"
    			+ "NODE Department ( {deptName STRING PRIMARY KEY, office STRING, deptCode\n"
    			+ "STRING} ),\n"
    			+ "NODE Faculty ( {facId STRING PRIMARY KEY, firstName STRING, lastName STRING,\n"
    			+ "rank STRING} ),\n"
    			+ "NODE Class ( {deptCode STRING, courseNumber INT64, section INT64, room STRING,\n"
    			+ "schedule STRING, PRIMARY KEY(deptCode, courseNumber, section)} ),\n"
    			+ "NODE Textbook ( {isbn STRING PRIMARY KEY, title STRING, publisher STRING, author\n"
    			+ "LIST<STRING>, publicationYear INT64} ),\n"
    			+ "NODE Evaluation ( {‘date‘ DATE, rater STRING PRIMARY KEY, rating FLOAT64} ),\n"
    			+ "EDGE Chairs (Faculty)-[:Chairs]->(Department),\n"
    			+ "EDGE Employs (Department)-[:Employs]->(Faculty),\n"
    			+ "EDGE Offers (Department)-[:Offers]->(Class),\n"
    			+ "EDGE HasMajor (Student)-[:HasMajor]->(Department),\n"
    			+ "EDGE EnrollsIn (Student)-[:EnrollsIn {grade INT64}]->(Class),\n"
    			+ "EDGE Uses (Class)-[:Uses]->(Textbook),\n"
    			+ "EDGE Teaches (Faculty)-[:Teaches]->(Class),\n"
    			+ "EDGE Rates (Evaluation)-[:Rates]->(Faculty)\n"
    			+ "}\n"; // put your query here
    	
    	client.execute(gql_string);

    	gql_string = "CREATE GRAPH IF NOT EXISTS University :: UniversityType"; // put your query here
    	client.execute(gql_string);

    	gql_string = "SESSION SET GRAPH University\n";
    	client.execute(gql_string);

    	//########################

    	//Create graph
    	
    	gql_string = "INSERT OR REPLACE\n"
    			+ "(s1@Student {stuId: \"54aghbt\", firstName: \"Charlie\", lastName: \"DramaMaker\", credits: 37}),\n"
    			+ "(s2@Student {stuId: \"46zfsdf\", firstName: \"June\", lastName: \"Programmer\", credits: 73}),\n"
    			+ "(s3@Student {stuId: \"lmoi3nas\", firstName: \"El\", lastName: \"Capitan\", credits: 12}),\n"
    			+ "(s4@Student {stuId: \"mabksdl39\", firstName: \"Cristoph\", lastName: \"McMovie\", credits: 100})\n"
    			+ ",\n"
    			+ "(s5@Student {stuId: \"90m3img\", firstName: \"Gandalf\", lastName: \"Potter\", credits: 92}),\n"
    			+ "(s6@Student {stuId: \"uasd9823t\", firstName: \"Luke\", lastName: \"Picard\", credits: 28}),\n"
    			+ "(d1@Department {deptName: \"Computer Science\", office: \"RRO\", deptCode: \"CSCI\"}),\n"
    			+ "(d2@Department {deptName: \"Drama\", office: \"WHI\", deptCode: \"DRAM\"}),\n"
    			+ "(d3@Department {deptName: \"New Media\", office: \"OWE\", deptCode: \"NM\"}),\n"
    			+ "(f1@Faculty {facId: \"Awesome\", firstName: \"Dr.\", lastName: \"Bogert\", rank: \"Professor\"}),\n"
    			+ "(f2@Faculty {facId: \"TheNewGuy\", firstName: \"Dr. Carlson\", lastName: \"Steel\", rank: \"\n"
    			+ "Assistant Professor\"}),\n"
    			+ "(f3@Faculty {facId: \"TheOldStandby\", firstName: \"Dr. Bob\", lastName: \"Smith\", rank: \"\n"
    			+ "Associate Professor\"}),\n"
    			+ "(f4@Faculty {facId: \"SeniorLect\", firstName: \"Chris\", lastName: \"LaBute\", rank: \"Senior\n"
    			+ "Lecturer\"}),\n"
    			+ "(c1@Class {deptCode: \"CSCI\", courseNumber: 201, section: 1, room: \"RRO 217\", schedule: \"M\n"
    			+ "6:00-8:30\"}),\n"
    			+ "(c2@Class {deptCode: \"CSCI\", courseNumber: 202, section: 1, room: \"WHI 008\", schedule: \"\n"
    			+ "MWF 8:00-9:15\"}),\n"
    			+ "(c3@Class {deptCode: \"CSCI\", courseNumber: 343, section: 1, room: \"RRO 217\", schedule: \"\n"
    			+ "MWF 2:00-3:15\"}),\n"
    			+ "(c4@Class {deptCode: \"DRAM\", courseNumber: 111, section: 2, room: \"WHI 103\", schedule: \"\n"
    			+ "TR 1:15-2:45\"}),\n"
    			+ "(c5@Class {deptCode: \"NM\", courseNumber: 101, section: 1, room: \"OWE 301\", schedule: \"F\n"
    			+ "11:00-1:45\"}),\n"
    			+ "(c6@Class {deptCode: \"NM\", courseNumber: 231, section: 1, room: \"OWE 305\", schedule: \"\n"
    			+ "MW 12:30-2:10\"}),\n"
    			+ "(t1@Textbook {isbn: \"978-0135205976\", title: \"Java Foundations\", publisher: \"Pearson\",\n"
    			+ "author: [\"John Lewis\", \"Peter DePasquale\", \"Joe Chase\"], publicationYear: 2019}),\n"
    			+ "(t2@Textbook {isbn: \"978-1284231588\", title: \"Databases Illuminated\", publisher: \"Jones &\n"
    			+ "Bartlett Learning\", author: [\"Catherine Ricardo\", \"Susan Urban\", \"Karen Davis\"],\n"
    			+ "publicationYear: 2022}),\n"
    			+ "(t3@Textbook {isbn: \"978-1718501065\", title: \"Practical SQL: A Beginner’s Guide to\n"
    			+ "Storytelling with Data\", publisher: \"No Starch Press\", author: [\"Anthony DeBarros\"],\n"
    			+ "publicationYear: 2022}),\n"
    			+ "(t4@Textbook {isbn: \"978-1119913573\", title: \"Respect for Acting\", publisher: \"Jossey-Bass\",\n"
    			+ "author: [\"Uta Hagen\", \"Haskel Frankel\"], publicationYear: 2023}),\n"
    			+ "(t5@Textbook {isbn: \"978-1118008188\", title: \"HTML and CSS: Design and Build Websites\",\n"
    			+ "publisher: \"John Wiley & Sons\", author: [\" Jon Duckett\"], publicationYear: 2011}),\n"
    			+ "(e1@Evaluation {‘date‘: date(\"2024-09-25\", \"%Y-%m-%d\"), rater: \"McFlourish\", rating:\n"
    			+ "8}),\n"
    			+ "(e2@Evaluation {‘date‘: date(\"2024-09-26\", \"%Y-%m-%d\"), rater: \"Wind\", rating: 7}),\n"
    			+ "(e3@Evaluation {‘date‘: date(\"2024-09-27\", \"%Y-%m-%d\"), rater: \"Flood\", rating: 1}),\n"
    			+ "(e4@Evaluation {‘date‘: date(\"2024-10-28\", \"%Y-%m-%d\"), rater: \"Braveheart\", rating:\n"
    			+ "10}),\n"
    			+ "(d1)-[@Employs]->(f1), (d1)-[@Employs]->(f3), (d2)-[@Employs]->(f3), (d3)-[@Employs\n"
    			+ "]->(f2),\n"
    			+ "(f1)-[@Chairs]->(d1), (f2)-[@Chairs]->(d3),\n"
    			+ "(d1)-[@Offers]->(c1), (d1)-[@Offers]->(c2), (d1)-[@Offers]->(c3), (d2)-[@Offers]->(c4),\n"
    			+ "(d3)-[@Offers]->(c5), (d3)-[@Offers]->(c6),\n"
    			+ "(c1)-[@Uses]->(t1), (c2)-[@Uses]->(t1), (c3)-[@Uses]->(t2), (c3)-[@Uses]->(t3), (c4)-[\n"
    			+ "@Uses]->(t4), (c6)-[@Uses]->(t5),\n"
    			+ "(f1)-[@Teaches]->(c1), (f1)-[@Teaches]->(c3), (f3)-[@Teaches]->(c2), (f3)-[@Teaches]->(\n"
    			+ "c4), (f2)-[@Teaches]->(c5), (f2)-[@Teaches]->(c6),\n"
    			+ "(e1)-[@Rates]->(f1), (e2)-[@Rates]->(f1), (e3)-[@Rates]->(f1), (e4)-[@Rates]->(f2),\n"
    			+ "(s1)-[@HasMajor]->(d2), (s1)-[@EnrollsIn {grade: 80}]->(c4),\n"
    			+ "(s2)-[@HasMajor]->(d1), (s2)-[@EnrollsIn {grade: 70}]->(c2), (s2)-[@EnrollsIn {grade:\n"
    			+ "100}]->(c3),\n"
    			+ "(s3)-[@HasMajor]->(d1), (s3)-[@EnrollsIn {grade: 90}]->(c1), (s3)-[@EnrollsIn {grade:\n"
    			+ "65}]->(c5),\n"
    			+ "(s4)-[@HasMajor]->(d3), (s4)-[@EnrollsIn {grade: 100}]->(c6), (s4)-[@EnrollsIn {grade:\n"
    			+ "95}]->(c1),\n"
    			+ "(s5)-[@HasMajor]->(d3), (s5)-[@EnrollsIn {grade: 100}]->(c5), (s5)-[@EnrollsIn {grade:\n"
    			+ "95}]->(c6),\n"
    			+ "(s6)-[@HasMajor]->(d2), (s6)-[@EnrollsIn {grade: 100}]->(c4), (s6)-[@EnrollsIn {grade:\n"
    			+ "95}]->(c1)";
    	
    	client.execute(gql_string); 	
    	
    	
    	//Homework Assignment #5
		// Question: Write a GQL query to find the non-majors enrolled in each class.

    	//(c@Class)<-[:EnrollsIn]-(s@Student) : Finds the students in a class bu sets the class to a var
    	//(s@Student)-[:HasMajor]->(d@Department) : Finds each students major and sets the dept to a var
    	//WHERE NOT c.deptCode = d.deptCode : Takes the class deptId and compares it to the dept's deptId
    	gql_string = "MATCH (s@Student)-[:EnrollsIn]->(c@Class)\n"
    			+ "MATCH (s)-[:HasMajor]->(d@Department)\n"
    			+ "WHERE NOT c.deptCode = d.deptCode\n"
    			+ "return c,s,d";//returns the class, the student and their major (not associated w/ the class)
    	client.execute(gql_string);
    	
    	
    	client.close();
    }

}
        
	


