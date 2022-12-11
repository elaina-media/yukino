package net.mikoto.yukino.model;

import lombok.Data;
import net.mikoto.yukino.parser.ParserHandler;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
@Data
public class Config {
    ParserHandler<?, ?>[] parserHandlers;
    String modelsPath = "/models";
}
