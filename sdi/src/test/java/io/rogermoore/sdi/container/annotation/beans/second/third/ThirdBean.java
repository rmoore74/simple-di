package io.rogermoore.sdi.container.annotation.beans.second.third;

import io.rogermoore.sdi.container.annotation.beans.FirstBean;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named("thirdBean")
@Singleton
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
