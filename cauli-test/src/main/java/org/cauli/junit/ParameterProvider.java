package org.cauli.junit;

import freemarker.template.TemplateException;
import org.cauli.exception.FileGeneratorException;
import org.junit.runners.model.FrameworkMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by tianqing.wang on 14-2-28
 */
public interface ParameterProvider {

    public List<FrameworkMethodWithParameters> generator(FrameworkMethodWithParameters method) throws IOException, FileGeneratorException, InstantiationException, IllegalAccessException, TemplateException;
}
