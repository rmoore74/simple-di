package io.rogermoore.sdi.container.annotation.beans;

import io.rogermoore.sdi.container.annotation.Bean;
import io.rogermoore.sdi.container.annotation.Inject;
import io.rogermoore.sdi.container.annotation.beans.second.fourth.FourthBean;

@Bean
public class FirstBean {
    private final FourthBean fourthBean;

    @Inject
    public FirstBean(final FourthBean fourthBean) {
        this.fourthBean = fourthBean;
    }
}
