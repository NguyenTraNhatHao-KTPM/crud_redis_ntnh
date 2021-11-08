package com.crud_redis_ntnh.repository;

import com.crud_redis_ntnh.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class EmployeeRepository {

    private String HASH_KEY = "EMPLOYEE";
    private String LIST_KEY = "EMPLOYEE-LIST";
    private String SET_KEY = "EMPLOYEE-SET";

    private HashOperations hashOperations;//crud hash
    private ListOperations listOperations;//crud list
    private SetOperations setOperations;//crud set

    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {

        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.setOperations = redisTemplate.opsForSet();
        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee) {
//        crud hash
//        hashOperations.put(HASH_KEY, employee.getId(), employee);

//        crud list
//        listOperations.leftPush(LIST_KEY, employee);

//        crud set
        setOperations.add(SET_KEY, employee);
    }

    public Set<Employee> findAll() {
//        crud hash
//        return hashOperations.values(HASH_KEY);

//        crud list
//        Long lastIndex = listOperations.size(LIST_KEY) - 1;
//        return listOperations.range(LIST_KEY, 0, lastIndex);


//        crud set
        return setOperations.members(SET_KEY);
    }

    public Employee findById(Integer id) {
//        crud hash
//        return (Employee) hashOperations.get(HASH_KEY, id);

//        crud list
//        List<Employee> employees = findAll();

//        crud set
        Set<Employee> employees = findAll();
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
//        listOperations.rightPopAndLeftPush(LIST_KEY, id);

//        crud set
        Employee employee = findById(id);
        setOperations.remove(SET_KEY, employee);
    }
}
