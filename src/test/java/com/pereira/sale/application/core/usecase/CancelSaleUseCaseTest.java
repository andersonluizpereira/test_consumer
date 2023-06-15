package com.pereira.sale.application.core.usecase;

import com.pereira.sale.application.core.domain.Sale;
import com.pereira.sale.application.core.domain.enums.SaleStatus;
import com.pereira.sale.application.ports.in.FindSaleByIdInputPort;
import com.pereira.sale.application.ports.out.SaveSaleOutputPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelSaleUseCaseTest {

    @InjectMocks
    private CancelSaleUseCase cancelSaleUseCase;

    @Mock
    private FindSaleByIdInputPort findSaleByIdInputPort;

    @Mock
    private SaveSaleOutputPort saveSaleOutputPort;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(findSaleByIdInputPort, saveSaleOutputPort);
    }

    @Test
    void givenAValid_whenCallsCancelSale_shouldSale() {
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

        when(findSaleByIdInputPort.find(eq(expectedId)))
                .thenReturn((expectedSale));

        assertTrue(expectedSale.getStatus().equals(SaleStatus.PENDING));

        cancelSaleUseCase.cancel(expectedSale);

        verify(saveSaleOutputPort, times(1)).save(any(Sale.class));
        var sale = findSaleByIdInputPort.find(expectedId);
        verify(findSaleByIdInputPort, times(2)).find(eq(expectedId));
        verifyNoMoreInteractions(findSaleByIdInputPort);
        verifyNoMoreInteractions(saveSaleOutputPort);
        assertTrue(sale.getStatus().equals(SaleStatus.CANCELED));
    }

    @Test
    void givenAValid_whenCallsCancelSale_shouldSaleError() {
        final var expectedId = 1;
        final var expectedProductId = 1;
        final var expectedUserId = 1;
        final var expectedValue = new BigDecimal("1000.00");
        final var expectedStatus = SaleStatus.toEnum(1);
        final Integer expectedQuantity = 10;

        var sale =
                new Sale(
                        expectedId,
                        expectedProductId,
                        expectedUserId,
                        expectedValue,
                        expectedStatus,
                        expectedQuantity);

        when(findSaleByIdInputPort.find(eq(expectedId)))
                .thenReturn((sale));

        assertTrue(sale.getStatus().equals(SaleStatus.PENDING));

        doThrow(new IllegalStateException("Gateway error"))
                .when(saveSaleOutputPort).save(eq(sale));

        Assertions.assertThrows(IllegalStateException.class, () -> cancelSaleUseCase.cancel(sale));
        Mockito.verify(saveSaleOutputPort, times(1)).save(eq(sale));

    }
}