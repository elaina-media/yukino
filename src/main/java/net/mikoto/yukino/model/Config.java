package net.mikoto.yukino.model;

import lombok.Data;
import net.mikoto.yukino.parser.ParserHandle;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author mikoto
 * &#064;date 2022/12/11
 * Create for yukino
 */
@Data
public class Config {
    private ParserHandle<?, ?>[] parserHandles;
    private String modelsPath = "/models";
    private boolean isCheckModel = false;
    private SqlSessionFactory sqlSessionFactory;
}
