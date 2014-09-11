package org.cauli.mock.template;

import java.util.Set;
import java.util.Map;

/**
 * @auther sky
 */
public interface TemplateSourceEngine {

    public Set<String> getReturnStatuses();

    public void createTemplate(String status,String content);

    public void init();

    public boolean hasReturnStatus(String returnStatus);

    public String getTemplate(String status);

    public void updateTemplate(String status,String templateValue);

    public void deleteTemplate(String status);

    public Map<String,String> getAllTemplates();



}
