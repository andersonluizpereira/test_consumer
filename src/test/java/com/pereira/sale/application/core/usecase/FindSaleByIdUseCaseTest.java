package com.pereira.sale.application.core.usecase;

import com.pereira.sale.application.core.domain.Sale;
import com.pereira.sale.application.core.domain.enums.SaleStatus;
import com.pereira.sale.application.ports.out.FindSaleByIdOutputPort;
import com.pereira.sale.application.ports.out.SaveSaleOutputPort;
import com.pereira.sale.application.ports.out.SendCreatedSaleOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindSaleByIdUseCaseTest {

    @InjectMocks
    private FindSaleByIdUseCase findSaleByIdUseCase;

    @Mock
    private FindSaleByIdOutputPort findSaleByIdOutputPort;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(findSaleByIdOutputPort);
    }

    @Test
    void givenAValidId_whenCallsGetSale_shouldReturnSale() {
        final var expectedId = 1;
        final var expectedProductId = 1;
        final var expectedUserId = 1;
        final var expectedValue = new BigDecimal("1000.00");
        final var expectedStatus = SaleStatus.toEnum(1);
        final Integer expectedQuantity = 10;

        var expectedSale =
                new Sale(
                expectedId,
                expectedProductId,
                expectedUserId,
                expectedValue,
                expectedStatus,
                expectedQuantity);

        when(findSaleByIdOutputPort.find(eq(expectedId)))
                .thenReturn(Optional.of(expectedSale));

        var actualSale = findSaleByIdUseCase.find(expectedId);

        assertEquals(expectedSale.getId(), actualSale.getId());
        assertEquals(expectedSale.getProductId(), actualSale.getProductId());
        assertEquals(expectedSale.getUserId(), actualSale.getUserId());
        assertEquals(expectedSale.getValue(), actualSale.getValue());
        assertEquals(expectedSale.getStatus(), actualSale.getStatus());
        assertEquals(expectedSale.getQuantity(), actualSale.getQuantity());

    }
}