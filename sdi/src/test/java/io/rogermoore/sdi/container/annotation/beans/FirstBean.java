package io.rogermoore.sdi.container.annotation.beans;

import io.rogermoore.sdi.container.annotation.beans.second.fourth.FourthBean;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class FirstBean {
    private final FourthBean fourthBean;

    @Inject
    public FirstBean(final FourthBean fourthBean) {
        this.fourthBean = fourthBean;
    }
}
