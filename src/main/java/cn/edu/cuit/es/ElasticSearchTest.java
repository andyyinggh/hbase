package cn.edu.cuit.es;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class ElasticSearchTest {
	 
	
	public static void main(String[] args) throws IOException {
		
		String clustername = "es-yp";
		String hostname = "172.21.0.71";
		int port = 9300;
		TransportClient client = null;
		Settings settings = Settings.settingsBuilder().put("cluster.name",clustername).build();
		client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(hostname, port)));
		
		XContentBuilder builder = XContentFactory.jsonBuilder()
				.startObject()
				.field("user","admin")
				.field("password","123456")
				.endObject();
		
//		IndexResponse response = client.prepareIndex("index_test", "type_test", "id_test")
//				.setSource(builder).get();
//		String index = response.getIndex();
//		System.out.println(index);
		
		
		QueryBuilder qb = QueryBuilders.termQuery("user","admin");
		SearchResponse response = client.prepareSearch("index_test")
				.setTypes("type_test")
				.setQuery(qb)
				.setSearchType(SearchType.QUERY_AND_FETCH)
				.execute().actionGet();
		System.out.println(response.toString());
		
	}
}

	
