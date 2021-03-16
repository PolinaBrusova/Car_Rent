package com.example.demo.controllersDB;

import com.example.demo.models.Department;
import com.example.demo.models.EmpLogPas;
import com.example.demo.models.Employee;
import com.example.demo.models.Position;
import com.example.demo.repositories.EmpLogPasReposiroty;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class LogPasController {
    private final EmpLogPasReposiroty empLogPasReposiroty;

    public LogPasController(EmpLogPasReposiroty empLogPasReposiroty){
        this.empLogPasReposiroty=empLogPasReposiroty;
    }

    @PostMapping("/emp_log_pas")
    EmpLogPas createLogPas(@RequestParam Long id, @RequestParam String password) {
//        curl -X POST http://127.0.0.1:8080/api/theater/ticket?price=777
        EmpLogPas empLogPas = new EmpLogPas(id, password);
        return this.empLogPasReposiroty.save(empLogPas);
    }

    @GetMapping("/emp_log_pas/{id}")
    EmpLogPas getLogPas(@PathVariable Long id) {
//        curl -X GET http://127.0.0.1:8080/api/theater/ticket?price=777
        return this.empLogPasReposiroty.findEmpLogPasById(id);
    }
}
