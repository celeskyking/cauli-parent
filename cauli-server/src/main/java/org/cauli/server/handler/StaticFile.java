package org.cauli.server.handler;

public class StaticFile implements TemplateEngine {
    @Override
    public byte[] process(byte[] template, String templatePath, Object templateContext) {
        return template;
    }
}
