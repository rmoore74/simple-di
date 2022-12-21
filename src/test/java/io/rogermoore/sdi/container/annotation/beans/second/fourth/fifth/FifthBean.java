package io.rogermoore.sdi.container.annotation.beans.second.fourth.fifth;

import io.rogermoore.sdi.container.annotation.beans.second.SecondBean;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class FifthBean {
    @Inject
    public FifthBean(SecondBean secondBean) {}
}
