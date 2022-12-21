package io.rogermoore.sdi.container.annotation.beans.second.fourth.fifth;

import io.rogermoore.sdi.container.annotation.Bean;
import io.rogermoore.sdi.container.annotation.Inject;
import io.rogermoore.sdi.container.annotation.beans.second.SecondBean;

@Bean
public class FifthBean {
    @Inject
    public FifthBean(SecondBean secondBean) {}
}
