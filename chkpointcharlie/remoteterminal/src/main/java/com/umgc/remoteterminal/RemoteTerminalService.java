package com.umgc.remoteterminal;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoteTerminalService {

    @Autowired
    private RemoteTerminalRepository remoteTerminalRepository;

    public List<RemoteTerminal> findAll() {
        return remoteTerminalRepository.findAll();
    }

    public Optional<RemoteTerminal> findById(Long id) {
        return remoteTerminalRepository.findById(id);
    }

    public RemoteTerminal save(RemoteTerminal terminal) {
        return remoteTerminalRepository.save(terminal);
    }

    public void deleteById(Long id) {
    	remoteTerminalRepository.deleteById(id);
    }

   
}
