package net.mikoto.yukino.service;

import lombok.extern.log4j.Log4j2;
import net.mikoto.yukino.manager.YukinoConfigManager;
import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.mapper.YukinoDataMapper;
import net.mikoto.yukino.strategy.PrimaryKeyGenerateStrategy;
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
 * &#064;date 2022/12/18
 * Create for yukino
 */
@Log4j2
public class YukinoDaoService {
    private final YukinoConfigManager yukinoConfigManager;
    private final YukinoModelManager yukinoModelManager;

    /**
     * The constructor of YukinoDaoService
     *
     * @param yukinoConfigManager net.mikoto.yukino.manager.YukinoConfigManager
     * @param yukinoModelManager net.mikoto.yukino.manager.YukinoModelManager
     */
    public YukinoDaoService(YukinoConfigManager yukinoConfigManager, YukinoModelManager yukinoModelManager) {
        this.yukinoConfigManager = yukinoConfigManager;
        this.yukinoModelManager = yukinoModelManager;
    }

    /**
     * Insert data into database.
     *
     * @param yukinoModel The yukino model (Mainly use the strategy in it)
     * @param mapper The instance mapper of YukinoDataMapper
     * @param data The real data
     * @return The lines it affect.
     */
    private int doInsert(@NotNull YukinoModel yukinoModel, @NotNull YukinoDataMapper mapper, Map<String, Object> data) {
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", yukinoModel.getTableNameStrategy().run(data));
        map.put("columnMap", data);
        return mapper.insert(map);
    }

    /**
     * Check the data is compare to the model
     * Do something important to the data (e.g. Insert primary key)
     *
     * @param isCheckModel If it is need to check every colum of the model.
     * @param yukinoModel The yukino model
     * @param data The real data
     */
    private void doCheckModel(boolean isCheckModel, @NotNull YukinoModel yukinoModel, @NotNull Map<String, Object> data) {
        // Insert primary key
        Field idField = yukinoModel.getFields()[yukinoModel.getIdFieldIndex()];
        if (!data.containsKey(idField.getFieldName())) {
            PrimaryKeyGenerateStrategy<?> pkGenerateStrategy = yukinoModel.getPkGenerateStrategy();
            if (pkGenerateStrategy != null) {
                data.put(idField.getFieldName(), yukinoModel.getPkGenerateStrategy().run());
            }
        }

        if (isCheckModel) {
            // Check data length
            if (data.size() != yukinoModel.getFields().length) {
                throw new RuntimeException("Wrong data size.");
            }

            // Check fields
            for (Field field :
                    yukinoModel.getFields()) {
                if (!data.containsKey(field.getFieldName())) {
                    throw new RuntimeException("Wrong field.");
                }
            }
        }
    }

    public void insert(Map<String, Object> @NotNull [] data, String modelName, String configName) {
        Config config = yukinoConfigManager.get(configName);
        YukinoModel yukinoModel = yukinoModelManager.get(modelName);
        SqlSessionFactory sqlSessionFactory = config.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        YukinoDataMapper mapper = sqlSession.getMapper(YukinoDataMapper.class);

        int lines = 0;
        for (Map<String, Object> singleData : data) {
            doCheckModel(config.isCheckModel(), yukinoModel, singleData);
            lines += doInsert(yukinoModel, mapper, singleData);
        }

        log.info("Do insert to " + lines + " lines");

        sqlSession.commit();
        sqlSession.close();
    }

    public void insert(Map<String, Object> data, String modelName, String configName) {
        Config config = yukinoConfigManager.get(configName);
        YukinoModel yukinoModel = yukinoModelManager.get(modelName);
        SqlSessionFactory sqlSessionFactory = config.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        YukinoDataMapper mapper = sqlSession.getMapper(YukinoDataMapper.class);

        doCheckModel(config.isCheckModel(), yukinoModel, data);
        int lines = doInsert(yukinoModel, mapper, data);

        log.info("Do insert to " + lines + " lines");

        sqlSession.commit();
        sqlSession.close();
    }
}
