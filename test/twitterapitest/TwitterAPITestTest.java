/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterapitest;

import java.util.Map;
import javax.json.JsonArray;
import javax.json.JsonObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Liu Fan
 */
public class TwitterAPITestTest {
    
    static TwitterAPITest api;
    public TwitterAPITestTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        api = new TwitterAPITest("consumer_key.txt","access_token.txt");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

  

    /**
     * Test of updateStatus method, of class TwitterAPITest.
     */
    @Test
    public void testUpdateTweet() throws Exception {
        api.updateStatus("twitter API is interesting!");
    }
    
    @Test
    public void testGetTweetByID() throws Exception {
        int i = 0;
        for (i = 0; i < 10; i++) {
            Map res = api.getTweetByID("375062248097853441");
            JsonObject in = (JsonObject) res.get("object");
            api.parseJson(in);
        }
    }
    
    @Test
    public void testObtainBearerToekn() throws Exception{
        String token=api.obtainBearerToken();
        System.out.println(token);
    }
    
    @Test
    public void testGetTweetByIDAppOnly() throws Exception {
        
        Map res = api.getTweetByIDAppOnly("377268744034480128");
        int code = (Integer)res.get("code");
    }
    
    @Test
    public void testGetUserByName() throws Exception {
        Map res = api.getUserByScreenNameAppOnly("eastbay");
        int code = ((JsonObject)res.get("object")).getInt("id");
        System.out.println(code);
    }
    
    @Test
    public void testGetUserTimeline() throws Exception {
        String max_id = null;
        String since_id = null;
        Map res;
        String id = null;
        String created_at = null;
        int count = 0;
        int size=0;
        int retwcts = 0;
        do {
            res = api.getTimelineOfUserAppOnly("781270915", max_id, since_id);
            JsonArray arry = (JsonArray) res.get("object");
            size = arry.size();
            for (int i = 0; i < size; i++) {
                id = ((JsonObject) arry.get(i)).getJsonString("id_str").getString();
                //created_at = ((JsonObject) arry.get(i)).getJsonString("created_at").getString();
                boolean retweeted = ((JsonObject) arry.get(i)).containsKey("retweeted_status");
                if(retweeted){
                    retwcts++;
                }
                count++;
                //System.out.println(id+"\t"+created_at);
            }
            long l=Long.parseLong(id);
            l--;           
            max_id = String.valueOf(l);
        } while (size != 0);
        System.out.println(count+"  "+retwcts);
    }

}
