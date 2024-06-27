package Skypro.Project_Of_Employee_JavaCore.service;

import Skypro.Project_Of_Employee_JavaCore.exceptions.EmployeeAlreadyAddedException;
import Skypro.Project_Of_Employee_JavaCore.exceptions.EmployeeNotFoundException;
import Skypro.Project_Of_Employee_JavaCore.exceptions.EmployeeStorageIsFullException;
import Skypro.Project_Of_Employee_JavaCore.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
/**
 * программа, которая занимается учетом сотрудников и помогает кадрам и бухгалтерии автоматизировать процессы
 */
public class EmployeeService {

    /**
     * Максимальное количество сотрудников в организации
     */
    private final static int MAX_COUNT_EMPLOYEES = 3;
    /**
     * Список всех сотрудников в организации
     */
    private List<Employee> employeeList = new ArrayList<>();

    /**
     * Добавление нового сотрудника в список всех сотрудников организации
     * @param firstName не может быть {@code null}
     * @param lastName не может быть {@code null}
     * @return добавленный сотрудник
     */
    public Employee addEmployee(String firstName, String lastName) {
        if(employeeList.size()>MAX_COUNT_EMPLOYEES) {
            throw new EmployeeStorageIsFullException("Штат сотрудников переполнен.");
        }
        Employee addedEmployee = new Employee(firstName, lastName);
        if(employeeList.contains(addedEmployee)) {
            throw new EmployeeAlreadyAddedException("Такой сотрудник уже имеется в штате.");
        }
        employeeList.add(addedEmployee);
        return addedEmployee;
    }

    /**
     * Удаление сотрудника из списка всех сотрудников организации
     * @param firstName не может быть {@code null}
     * @param lastName не может быть {@code null}
     * @return удаленный сотрудник
     */
    public Employee deleteEmployee(String firstName, String lastName) {
        Employee removedEmployee = new Employee(firstName, lastName);
        if(!employeeList.contains(removedEmployee)) {
            throw new EmployeeNotFoundException("Такого сотрудника в списке всех сотрудников организации нет!");
        }
        employeeList.remove(removedEmployee);
        return removedEmployee;
    }

    /**
     * Поиск сотрудника в списке всех сотрудников организации
     * @param firstName не может быть {@code null}
     * @param lastName не может быть {@code null}
     * @return искомый сотрудник
     */
    public Employee findEmployee(String firstName, String lastName) {
        Employee findedEmployee = new Employee(firstName,lastName);
        if(!employeeList.contains(findedEmployee)) {
            throw new EmployeeNotFoundException("Такого сотрудника в списке всех сотрудников организации нет!");
        }
        Employee result = null;
        for (Employee employees: employeeList) {
            if(employees.equals(findedEmployee)) {
                result = findedEmployee;
            }
        }
        return result;
    }

    /**
     * Получение имеющегося списка всех сотрудников организации, который не модифицирован
     * @return список всех сотрудников организации
     */
    public List<Employee> getAll() {
        return Collections.unmodifiableList(employeeList);
    }
}
