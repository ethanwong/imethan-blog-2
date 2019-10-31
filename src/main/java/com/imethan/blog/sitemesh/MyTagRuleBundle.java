package com.imethan.blog.sitemesh;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * @Name MyTagRuleBundle
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-10-29 13:46
 */
public class MyTagRuleBundle implements TagRuleBundle {
    @Override
    public void install(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        state.addRule("myTag", new ExportTagToContentRule(siteMeshContext,contentProperty.getChild("myTag"),false));
    }

    @Override
    public void cleanUp(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        if (!((ContentProperty)contentProperty.getChild("myTag")).hasValue()) {
            ((ContentProperty)contentProperty.getChild("myTag")).setValue(contentProperty.getValue());
        }
    }
}
