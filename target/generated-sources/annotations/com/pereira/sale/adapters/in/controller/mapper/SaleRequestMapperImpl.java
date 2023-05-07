package com.pereira.sale.adapters.in.controller.mapper;

import com.pereira.sale.adapters.in.controller.request.SaleRequest;
import com.pereira.sale.application.core.domain.Sale;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-22T09:38:48-0300",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.6 (Eclipse Adoptium)"
)
@Component
public class SaleRequestMapperImpl implements SaleRequestMapper {

    @Override
    public Sale toSale(SaleRequest saleRequest) {
        if ( saleRequest == null ) {
            return null;
        }

        Sale sale = new Sale();

        sale.setProductId( saleRequest.getProductId() );
        sale.setUserId( saleRequest.getUserId() );
        sale.setValue( saleRequest.getValue() );
        sale.setQuantity( saleRequest.getQuantity() );

        return sale;
    }
}
