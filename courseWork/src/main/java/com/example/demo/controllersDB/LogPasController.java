package com.example.demo.controllersDB;

import com.example.demo.models.EmpLogPas;
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
        return this.empLogPasReposiroty.findEmpLogPasById(id);
    }

    @GetMapping("/emp_log_pas/id={id}/password={password}")
    boolean canLogin(@PathVariable Long id, @PathVariable String password) {
        System.out.println(id);
        EmpLogPas worker = this.empLogPasReposiroty.findEmpLogPasById(id);
        if(worker != null){
            return worker.getPassword().equals(password);
        }else{
            return false;
        }
    }
}
