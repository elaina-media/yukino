package net.mikoto.yukino.manager;

import net.mikoto.yukino.model.YukinoModel;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
public class YukinoModelManager extends AbstractManager<YukinoModel> {
    public void register(YukinoModel yukinoModel) {
        super.put(yukinoModel.getModelName(), yukinoModel);
    }
}
