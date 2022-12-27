package net.mikoto.yukino.test;

import com.alibaba.fastjson2.JSONObject;
import net.mikoto.yukino.YukinoApplication;
import net.mikoto.yukino.manager.YukinoConfigManager;
import net.mikoto.yukino.mapper.YukinoDataMapper;
import net.mikoto.yukino.model.Config;
import net.mikoto.yukino.parser.ParserHandler;
import net.mikoto.yukino.parser.impl.ModelFileParser;
import net.mikoto.yukino.parser.impl.JsonFileToObjectParser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
public class YukinoTestApplication {
    @Test
    public void applicationTest() throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        System.out.println(applicationContext);
        YukinoApplication yukinoApplication = (YukinoApplication) applicationContext.getBean("yukinoApplication");
        YukinoConfigManager yukinoConfigManager = yukinoApplication.getYukinoConfigManager();
        Config config = new Config();
        config.setParserHandlers(
                new ParserHandler[]{
                        new JsonFileToObjectParser(yukinoApplication.getYukinoJsonManager()),
                        new ModelFileParser(yukinoApplication.getYukinoModelManager())
                });
        yukinoConfigManager.put("default", config);
        yukinoApplication.doScan("default");
        System.out.println();

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        YukinoDataMapper mapper = sqlSession.getMapper(YukinoDataMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", "pixiv.artwork");
//        List<Map<String, Object>> list = mapper.select(map);
//        for (Map<String, Object> o :
//                list) {
//            JSONObject jsonObject = new JSONObject(o);
//            System.out.println(jsonObject.toJSONString());
//        }
        String jsonString = "{\"author_name\":\"SINABI\",\"has_series\":false,\"illust_url_mini\":\"/c/48x48/img-master/img/2022/09/23/00/29/58/101414659_p0_square1200.jpg\",\"like_count\":222,\"pk_artwork_id\":101414659,\"grading\":false,\"create_time\":\"2022-09-22 23:29:58\",\"description\":\"\",\"bookmark_count\":16,\"patch_time\":\"2022-09-25 13:28:08\",\"artwork_title\":\"20220922線画\",\"tags\":\"練習\",\"series_id\":0,\"illust_url_thumb\":\"/c/250x250_80_a2/img-master/img/2022/09/23/00/29/58/101414659_p0_square1200.jpg\",\"illust_url_regular\":\"/img-master/img/2022/09/23/00/29/58/101414659_p0_master1200.jpg\",\"update_time\":\"2022-09-22 23:29:58\",\"series_order\":0,\"previous_artwork_id\":0,\"author_id\":1044676,\"next_artwork_id\":0,\"illust_url_small\":\"/c/540x540_70/img-master/img/2022/09/23/00/29/58/101414659_p0_master1200.jpg\",\"page_count\":1,\"view_count\":0,\"illust_url_original\":\"/img-original/img/2022/09/23/00/29/58/101414659_p0.jpg\"}";
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        map.put("columnMap", jsonObject);
        System.out.println(mapper.insert(map));
        sqlSession.commit();
        sqlSession.close();
    }
}
