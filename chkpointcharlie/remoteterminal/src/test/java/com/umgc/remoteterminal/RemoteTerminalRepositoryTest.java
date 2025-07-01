package com.umgc.remoteterminal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
// do not replace the testcontainer data source
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class RemoteTerminalRepositoryTest {

    @Autowired
    private RemoteTerminalRepository remoteTerminalRepository;

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0-debian");
    @Test
    public void testTerminalSaveAndFindById() {
    	
        // Create a new remote terminal
    	RemoteTerminal terminalA = new RemoteTerminal();
    	terminalA.setLocationId(1L);
        
        // save remote terminal
    	remoteTerminalRepository.save(terminalA);

        // find remote terminal
        Optional<RemoteTerminal> result = remoteTerminalRepository.findById(terminalA.getId());
        assertTrue(result.isPresent());

        RemoteTerminal terminalAFromGet = result.get();

        assertEquals(1L, terminalAFromGet.getLocationId());
        
    }

    @Test
    public void testTerminalCRUD() {

    	RemoteTerminal terminalB = new RemoteTerminal();
    	terminalB.setLocationId(1L);
       
        // save remote terminal
    	remoteTerminalRepository.save(terminalB);

        // find remote terminal by location
        // find remote terminal  by id
        Optional<RemoteTerminal> result = remoteTerminalRepository.findById(terminalB.getId());
        assertTrue(result.isPresent());

        RemoteTerminal terminalBFromGet = result.get();

        Long terminalBId = terminalBFromGet.getId();

        assertEquals(1L, terminalBFromGet.getLocationId());
        
        // update remote terminal
        terminalBFromGet.setLocationId(2L);
        remoteTerminalRepository.save(terminalBFromGet);

        // find remote terminal by id
        Optional<RemoteTerminal> result2 = remoteTerminalRepository.findById(terminalBId);
        assertTrue(result2.isPresent());

        RemoteTerminal terminalBFromGet2 = result2.get();

        assertEquals(2L, terminalBFromGet2.getLocationId());
       
        // delete a remote terminal
        remoteTerminalRepository.delete(terminalB);

        // should be empty
        assertTrue(remoteTerminalRepository.findById(terminalBId).isEmpty());

    }


}