package me.cerrato.w2m.spaceships.domain.converters;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

public abstract class AutoregisteringConverter<F, T> implements Converter<F, T>, InitializingBean {

    @Autowired
    protected ConversionService conversionService;


    @Override
    public void afterPropertiesSet() {
        if (conversionService instanceof GenericConversionService) {
            ((GenericConversionService) conversionService).addConverter(this);
        }
    }
}
