package com.pereira.sale.application.core.usecase;

import com.pereira.sale.application.core.domain.Sale;
import com.pereira.sale.application.core.domain.enums.SaleStatus;
import com.pereira.sale.application.ports.out.SaveSaleOutputPort;
import com.pereira.sale.application.ports.out.SendCreatedSaleOutputPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSaleUseCaseTest {

    @InjectMocks
    private CreateSaleUseCase createSaleUseCase;

    @Mock
    private SaveSaleOutputPort saveSaleOutputPort;
    @Mock
    private SendCreatedSaleOutputPort sendCreatedSaleOutputPort;

//    @BeforeEach
//    void cleanUp() {
//        Mockito.reset(saveSaleOutputPort);
//    }

    @Test
    void givenAValidCommand_whenCallsCreateSale_shouldReturnSale() {

        final var aId = 1;
        final var aProductId = 1;
        final var aUserId = 1;
        final var aValue = new BigDecimal("1000.00");
        final var aStatus = SaleStatus.toEnum(1);
        final Integer aQuantity = 10;

        var sale = new Sale(aId, aProductId, aUserId, aValue, aStatus, aQuantity);

        when(saveSaleOutputPort.save(any()))
                .thenAnswer(returnsFirstArg());

        doNothing()
                .when(sendCreatedSaleOutputPort).send(any(), any());

        Assertions.assertDoesNotThrow(() -> createSaleUseCase.create(sale));

        Mockito.verify(saveSaleOutputPort, times(1)).save(eq(sale));

    }
}