package Skypro.Project_Of_Employee_JavaCore.service;

import Skypro.Project_Of_Employee_JavaCore.exceptions.EmployeeAlreadyAddedException;
import Skypro.Project_Of_Employee_JavaCore.exceptions.EmployeeNotFoundException;
import Skypro.Project_Of_Employee_JavaCore.exceptions.EmployeeStorageIsFullException;
import Skypro.Project_Of_Employee_JavaCore.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * Мапа всех сотрудников в организации
     */
    private Map<String, Employee> employeeMap = new HashMap<>();

    /**
     * Создание ключа и Добавление нового сотрудника в список всех сотрудников организации по ключу
     *
     * @param firstName не может быть {@code null}
     * @param lastName  не может быть {@code null}
     * @return добавленный сотрудник
     */
    public Employee addEmployee(String firstName, String lastName) {
        if (employeeMap.size() > MAX_COUNT_EMPLOYEES) {
            throw new EmployeeStorageIsFullException("Штат сотрудников переполнен.");
        }
        Employee addedEmployee = new Employee(firstName, lastName);
        String key = makeKey(firstName, lastName);
        if (employeeMap.containsKey(key)) {
            throw new EmployeeAlreadyAddedException("Такой сотрудник уже имеется в штате.");
        }
        employeeMap.put(key, addedEmployee);
        return addedEmployee;
    }

    /**
     * Создание ключа и Удаление сотрудника из списка всех сотрудников организации по ключу
     *
     * @param firstName не может быть {@code null}
     * @param lastName  не может быть {@code null}
     * @return удаленный сотрудник
     */
    public Employee deleteEmployee(String firstName, String lastName) {
        Employee removedEmployee = new Employee(firstName, lastName);
        String key = makeKey(firstName, lastName);
        if (!employeeMap.containsKey(key)) {
            throw new EmployeeNotFoundException("Такого сотрудника в списке всех сотрудников организации нет!");
        }
        employeeMap.remove(key, removedEmployee);
        return removedEmployee;
    }

    /**
     * Создание ключа и Поиск сотрудника в списке всех сотрудников организации по ключу
     *
     * @param firstName не может быть {@code null}
     * @param lastName  не может быть {@code null}
     * @return искомый сотрудник
     */
    public Employee findEmployee(String firstName, String lastName) {
        Employee findedEmployee = new Employee(firstName, lastName);
        String key = makeKey(firstName, lastName);
        if (!employeeMap.containsKey(key)) {
            throw new EmployeeNotFoundException("Такого сотрудника в списке всех сотрудников организации нет!");
        } else {
            return employeeMap.get(key);
        }
    }

    /**
     * Получение имеющегося списка всех сотрудников организации, который не модифицирован
     *
     * @return список всех сотрудников организации
     */
    public Collection<Employee> getAll() {
        return Collections.unmodifiableCollection(employeeMap.values());
    }

    /**
     * Метод, выполняющий создание ключа
     *
     * @param firstName не может быть {@code null}
     * @param lastName  не может быть {@code null}
     * @return метод по получению ключу
     */
    public String makeKey(String firstName, String lastName) {
        return firstName + lastName;
    }
}
