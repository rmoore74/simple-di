package io.rogermoore.sdi.container.annotation.beans.config;

import io.rogermoore.sdi.container.annotation.beans.FirstBean;
import io.rogermoore.sdi.container.annotation.beans.second.fourth.FourthBean;
import io.rogermoore.sdi.container.annotation.marker.Beans;

import javax.inject.Named;

@Beans
public class MultipleBeans {

    @Named
    public FirstBean firstBean(FourthBean fourthBean) {
        return new FirstBean(fourthBean);
    }

    @Named
    public FourthBean fourthBean() {
        return new FourthBean();
    }

}
