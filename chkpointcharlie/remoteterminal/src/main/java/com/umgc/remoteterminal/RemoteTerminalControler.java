package com.umgc.remoteterminal;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/RemoteTerminal")
public class RemoteTerminalControler {

    @Autowired
    private RemoteTerminalService remoteTerminalService;

    @GetMapping
    public List<RemoteTerminal> findAll() {
        return remoteTerminalService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<RemoteTerminal> findById(@PathVariable Long id) {
        return remoteTerminalService.findById(id);
    }

    // create a remote terminal
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public RemoteTerminal create(@RequestBody RemoteTerminal terminal) {
        return remoteTerminalService.save(terminal);
    }

    // update a remote terminal
    @PutMapping
    public RemoteTerminal update(@RequestBody RemoteTerminal terminal) {
        return remoteTerminalService.save(terminal);
    }

    // delete a remote terminal
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
    	remoteTerminalService.deleteById(id);
    }


}

