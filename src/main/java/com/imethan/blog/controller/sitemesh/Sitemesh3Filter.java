package com.imethan.blog.controller.sitemesh;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * @Name Sitemesh3Filter
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-10-29 11:53
 */
public class Sitemesh3Filter extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/*", "/decorator/default")
                .addDecoratorPath("/base64","/decorator/itool")
                .addExcludedPath("/static/**")
                .addExcludedPath("/hal/**")
                .addExcludedPath("/api/**")
                .addTagRuleBundle(new MyTagRuleBundle())


        ;
    }
}
