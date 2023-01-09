package net.mikoto.yukino.model;

import lombok.Data;
import net.mikoto.yukino.parser.ParserHandler;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
@Data
public class Config {
    private ParserHandler<?, ?>[] parserHandlers;
    private String modelsPath = "/models";
    private boolean isCheckModel = false;
    private SqlSessionFactory sqlSessionFactory;
}
