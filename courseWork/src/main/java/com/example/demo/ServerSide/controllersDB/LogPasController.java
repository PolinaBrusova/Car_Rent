package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.EmpLogPas;
import com.example.demo.ServerSide.repositories.EmpLogPasReposiroty;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class LogPasController {
    private final EmpLogPasReposiroty empLogPasReposiroty;

    public LogPasController(EmpLogPasReposiroty empLogPasReposiroty){
        this.empLogPasReposiroty=empLogPasReposiroty;
    }

    @PostMapping("/addLogPas")
    EmpLogPas createLogPas(@RequestParam Long id, @RequestParam String password) {
        EmpLogPas empLogPas = new EmpLogPas(id, password);
        return this.empLogPasReposiroty.save(empLogPas);
    }

    @GetMapping("/getLogPass={id}")
    EmpLogPas getLogPas(@PathVariable Long id) {
        return this.empLogPasReposiroty.findEmpLogPasById(id);
    }

    @GetMapping("/LogPas_Id={id}_password={password}")
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