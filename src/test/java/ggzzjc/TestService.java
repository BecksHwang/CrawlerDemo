package ggzzjc;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.becks.entity.News;
import com.becks.entity.Target;
import com.becks.entity.User;
import com.becks.service.NewsService;
import com.becks.service.TargetService;
import com.becks.service.UserService;

/**
 * 创建时间：
 * 
 * @author
 * @version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-hibernate.xml" })
@Transactional
public class TestService {

	private static final Logger LOGGER = Logger.getLogger(TestService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private NewsService newsService;

	@Autowired
	private TargetService targetService;

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
		//newsService.isExits(targetId, title, url)
		System.out.println(newsService.isExits(5l, "惠而浦获得政府补助4000万元", "http://stock.10jqka.com.cn/20160628/c591306555.shtml"));

	}

}
