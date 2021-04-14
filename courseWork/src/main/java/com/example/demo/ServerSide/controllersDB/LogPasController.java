package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.EmpLogPas;
import com.example.demo.ServerSide.repositories.EmpLogPasReposiroty;
import com.example.demo.utils.MyLogger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class LogPasController {
    private final EmpLogPasReposiroty empLogPasReposiroty;

    public LogPasController(EmpLogPasReposiroty empLogPasReposiroty){
        this.empLogPasReposiroty=empLogPasReposiroty;
    }

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
