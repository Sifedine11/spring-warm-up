package ch.etmles.payroll.Department;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {
    private final DepartmentRepository repository;

    DepartmentController(DepartmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/departments")
    List<Department> all() {
        return repository.findAll();
    }

    @PostMapping("/departments")
    Department newDepartment(@RequestBody Department newDepartment) {
        return repository.save(newDepartment);
    }

    @GetMapping("/departments/{id}")
    Department one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
    }

    @PutMapping("/departments/{id}")
    Department replaceDepartment(@RequestBody Department newDepartment, @PathVariable Long id) {
        return repository.findById(id)
                .map(department -> {
                    department.setName(newDepartment.getName());
                    return repository.save(department);
                })
                .orElseGet(() -> {
                    newDepartment.setId(id);
                    return repository.save(newDepartment);
                });
    }

    @DeleteMapping("/departments/{id}")
    void deleteDepartment(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
