package Storm.RealAnalytics;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

import net.sf.json.JSONObject;

public class CommitBolt extends BaseRichBolt{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OutputCollector collector;

    public void prepare(Map config, TopologyContext context, 
            OutputCollector collector) {
        this.collector = collector;
    }

    public void execute(Tuple tuple) {
    	JSONObject eventObj = (JSONObject) tuple.getValueByField("eventoutput");
    	String session = eventObj.getString("sessionid");
        String eventType = eventObj.getString("event");
        if (eventType.equals("commit"))
       		    this.collector.emit(new Values(session, eventType, "Application Comitted","COMMIT"));
        
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("session","eventType", "desc","action"));
    }
}
