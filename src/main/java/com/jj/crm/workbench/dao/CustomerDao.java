package com.jj.crm.workbench.dao;

import com.jj.crm.workbench.entity.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(@Param("company") String company);

    int saveCustomer(Customer customer);

    List<String> getCustomerName(String name);
}
