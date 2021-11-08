package com.crud_redis_ntnh.repository;

import com.crud_redis_ntnh.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private String HASH_KEY = "EMPLOYEE";
    private String LIST_KEY = "EMPLOYEE-LIST";

    private HashOperations hashOperations;//crud hash
    private ListOperations listOperations;//crud list

    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {

        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee) {
//        crud hash
//        hashOperations.put(HASH_KEY, employee.getId(), employee);

//        crud list
        listOperations.leftPush(LIST_KEY, employee);
    }

    public List<Employee> findAll() {
//        crud hash
//        return hashOperations.values(HASH_KEY);

//        crud list
        Long lastIndex = listOperations.size(LIST_KEY) - 1;
        return listOperations.range(LIST_KEY, 0, lastIndex);
    }

    public Employee findById(Integer id) {
//        crud hash
//        return (Employee) hashOperations.get(HASH_KEY, id);

//        crud list
        List<Employee> employees = findAll();
        for (Employee employee : employees) {
            if (employee.getId() == id)
                return employee;
        }

        return new Employee();
    }

    public void update(Employee employee) {
        saveEmployee(employee);
    }

    public void delete(Integer id) {
//        crud hash
//        hashOperations.delete(HASH_KEY, id);

//        crud list
        listOperations.rightPopAndLeftPush(LIST_KEY, id);
    }
}
