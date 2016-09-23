package CrawlerDemo;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.becks.entity.News;
import com.becks.entity.StockLabel;
import com.becks.entity.Target;
import com.becks.entity.User;
import com.becks.service.NewsService;
import com.becks.service.StockInfoService;
import com.becks.service.StockLabelService;
import com.becks.service.TargetService;
import com.becks.service.UserService;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 创建时间：
 * 
 * @author
 * @version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-hibernate.xml",
		"classpath:spring-redis.xml" })
@Transactional
public class TestService {

	private static final Logger LOGGER = Logger.getLogger(TestService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private NewsService newsService;

	@Autowired
	private TargetService targetService;

	@Autowired
	private StockLabelService stockLabelService;
	
	@Autowired
	private StockInfoService StockInfoService;

	@Resource
	private ShardedJedisPool shardedJedisPool1;

	@Resource
	private ShardedJedisPool shardedJedisPool2;
	
	@Test
	public void save() {
		User user = new User();
		user.setId(6);
		user.setName("beckshwang");
		user.setAge(12);
		Integer id;
		try {
			id = userService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<User> list = userService.findAll();
		for (User user2 : list) {
			System.out.println(user2.getName());
		}

	}

	@Test
	public void saveNews() throws Exception {

		News news = new News();
		news.setTitle("测试网址");
		news.setUrl("www.baidu.com");
		news.setPickTime(new Date());

		newsService.save(news);
		List<News> list = newsService.findAll();
		for (News news2 : list) {
			System.out.println(news2);
		}

	}

	@Test
	public void saveTarget() throws Exception {

		Target t = new Target();
		t.setName("测试网址");
		t.setUrl("www.baidu.com");
		targetService.save(t);
		List<Target> list = targetService.findAll();
		for (Target target2 : list) {
			System.out.println(target2);
		}

	}

	@Test
	public void saveIsExits() {
		// newsService.isExits(targetId, title, url)
		System.out.println(
				newsService.isExits(5l, "惠而浦获得政府补助4000万元", "http://stock.10jqka.com.cn/20160628/c591306555.shtml"));

	}

	@Test
	public void findStockLabelAllTest() throws Exception {

		List<StockLabel> list = stockLabelService.findAll();
		for (StockLabel stockLabel2 : list) {
			System.out.println(stockLabel2);
		}

	}

	@Test
	public void redisTest() throws Exception {

		ShardedJedis jedis1 = shardedJedisPool1.getResource();
		jedis1.sadd("shardedJedisPool1", "shardedJedisPool11");
		shardedJedisPool1.returnResourceObject(jedis1);
		
		ShardedJedis jedis2 = shardedJedisPool2.getResource();
		jedis2.sadd("shardedJedisPool2", "shardedJedisPool22");
		shardedJedisPool2.returnResourceObject(jedis2);

	}
	
	@Test
	public void redisTestSetex() throws Exception {
		ShardedJedis jedis1 = shardedJedisPool1.getResource();
		jedis1.setex("key01", 10, "value01-time10second");
		System.out.println("key01:" + jedis1.get("key01"));
		Thread.sleep(12000);
		System.out.println("after 12s key01:" + jedis1.get("key01"));
		shardedJedisPool1.returnResourceObject(jedis1);
	}
	
}
