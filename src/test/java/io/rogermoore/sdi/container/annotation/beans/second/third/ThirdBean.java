package io.rogermoore.sdi.container.annotation.beans.second.third;

import io.rogermoore.sdi.container.annotation.Bean;
import io.rogermoore.sdi.container.annotation.Inject;
import io.rogermoore.sdi.container.annotation.beans.FirstBean;

@Bean(qualifier = "thirdBean")
public class ThirdBean {

    private final FirstBean firstBean;

    @Inject
    public ThirdBean(final FirstBean firstBean) {
        this.firstBean = firstBean;
    }

    public FirstBean getFirstBean() {
        return firstBean;
    }
}
