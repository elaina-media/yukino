package net.mikoto.yukino.service;

import net.mikoto.yukino.manager.YukinoConfigManager;
import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.mapper.YukinoDataMapper;
import net.mikoto.yukino.model.Config;
import net.mikoto.yukino.model.Field;
import net.mikoto.yukino.model.YukinoModel;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mikoto
 * @date 2022/12/18
 * Create for yukino
 */
public class YukinoDataService {
    private final YukinoConfigManager yukinoConfigManager;
    private final YukinoModelManager yukinoModelManager;
    public YukinoDataService(YukinoConfigManager yukinoConfigManager, YukinoModelManager yukinoModelManager) {
        this.yukinoConfigManager = yukinoConfigManager;
        this.yukinoModelManager = yukinoModelManager;;
    }

    private void doInsert(@NotNull YukinoDataMapper mapper, String tableName, Map<String, Object> data) {
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableName);
        map.put("columnMap", data);
        mapper.insert(map);
    }

    private void doCheckModel(boolean isCheckModel, YukinoModel yukinoModel, Map<String, Object> data) throws NoSuchFieldException {
        if (isCheckModel) {
            for (Field field :
                    yukinoModel.getFields()) {
                Object o = data.get(field.getFieldName());
                if (o == null) {
                    throw new NoSuchFieldException("No such field: " + field.getFieldName() + " in the data.");
                }
            }
        }
    }

    public void insert(Map<String, Object> @NotNull [] data, String modelName, String configName, String tableName) throws NoSuchFieldException {
        Config config = yukinoConfigManager.get(configName);
        YukinoModel yukinoModel = yukinoModelManager.get(modelName);
        SqlSessionFactory sqlSessionFactory = config.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        YukinoDataMapper mapper = sqlSession.getMapper(YukinoDataMapper.class);

        for (Map<String, Object> singleData :
                data) {
            doCheckModel(config.isCheckModel(), yukinoModel, singleData);
            doInsert(mapper, tableName, singleData);
        }

        sqlSession.commit();
        sqlSession.close();
    }

    public void insert(Map<String, Object> data, String modelName, String configName, String tableName) throws NoSuchFieldException {
        Config config = yukinoConfigManager.get(configName);
        YukinoModel yukinoModel = yukinoModelManager.get(modelName);
        SqlSessionFactory sqlSessionFactory = config.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        YukinoDataMapper mapper = sqlSession.getMapper(YukinoDataMapper.class);

        doCheckModel(config.isCheckModel(), yukinoModel, data);
        doInsert(mapper, tableName, data);

        sqlSession.commit();
        sqlSession.close();
    }
}
