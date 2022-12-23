package io.rogermoore.sdi.container.annotation.beans.config;

import io.rogermoore.sdi.container.annotation.beans.FirstBean;
import io.rogermoore.sdi.container.annotation.beans.second.fourth.FourthBean;
import io.rogermoore.sdi.container.annotation.marker.Beans;

import javax.inject.Named;
import javax.inject.Singleton;

@Beans
public class MultipleBeans {

    @Named
    @Singleton
    public FirstBean firstBean(FourthBean fourthBean) {
        return new FirstBean(fourthBean);
    }

    @Named
    @Singleton
    public FourthBean fourthBean() {
        return new FourthBean();
    }

}
