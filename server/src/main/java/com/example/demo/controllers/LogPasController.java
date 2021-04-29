package com.example.demo.controllers;

import com.example.demo.models.EmpLogPas;
import com.example.demo.repositories.EmpLogPasReposiroty;
import com.example.demo.utils.MyLogger;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for the database
 */
@RestController
@RequestMapping("api/tests")
public class LogPasController {
    private final EmpLogPasReposiroty empLogPasReposiroty;

    /**
     * Initialises the controller and assigns the private variables
     * @param empLogPasReposiroty EmpLogPasReposiroty - JPA Repository child
     */
    public LogPasController(EmpLogPasReposiroty empLogPasReposiroty){
        this.empLogPasReposiroty=empLogPasReposiroty;
    }

    /**
     * Checks if the login and password do match and exist
     * @param id Long value of the login
     * @param password String value of the password
     * @return true (if the login and password exist and match) or false (if the login and password do not match)
     */
    @GetMapping("/LogPas_Id={id}_password={password}")
    boolean canLogin(@PathVariable Long id, @PathVariable String password) {
        EmpLogPas worker = this.empLogPasReposiroty.findEmpLogPasById(id);
        MyLogger.inform("Обработан ввод пароля по логину "+id);
        if(worker != null){
            return worker.getPassword().equals(String.valueOf(password.hashCode()));
        }else{
            return false;
        }
    }
}
