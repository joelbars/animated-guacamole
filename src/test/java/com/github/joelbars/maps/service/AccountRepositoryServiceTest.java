package com.github.joelbars.maps.service;

import com.github.joelbars.maps.repository.AccountRepositoryService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class AccountRepositoryServiceTest {

    @InjectMock
    AccountRepositoryService personRepository;

    @Test
    public void  shouldCalculateBalance() {

    }

}
