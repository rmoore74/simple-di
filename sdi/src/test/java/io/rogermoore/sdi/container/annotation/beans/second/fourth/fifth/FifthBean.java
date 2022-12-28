package io.rogermoore.sdi.container.annotation.beans.second.fourth.fifth;

import io.rogermoore.sdi.container.annotation.beans.second.SecondBean;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class FifthBean {
    private final SecondBean secondBean;
    @Inject
    public FifthBean(@Named("qualifiedPrototypeBean") SecondBean secondBean) {
        this.secondBean = secondBean;
    }
}
