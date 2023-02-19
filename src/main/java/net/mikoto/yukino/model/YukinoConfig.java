package net.mikoto.yukino.model;

import lombok.Data;
import net.mikoto.yukino.parser.ParserHandle;

/**
 * @author mikoto
 * &#064;date 2022/12/11
 * Create for yukino
 */
@Data
public class YukinoConfig {
    private ParserHandle<?, ?>[] parserHandles;
    private String modelsPath = "/models";
}
