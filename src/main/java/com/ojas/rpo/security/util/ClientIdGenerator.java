package com.ojas.rpo.security.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ClientIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        String prefix = "cli";
        Connection connection = session.connection();
        long generatedId = 101;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select  count(*) from client");

            if (rs.next()) {
            	System.err.println("Entered First if Condition");
                ResultSet rs1 = statement.executeQuery("select max(id) from client");
                if (rs1.next()) {
                	System.err.println("Entered Nested if Condition");
                	if(rs1.getInt(1)==0) {
                		generatedId =101;
                	}else {
                		generatedId = rs1.getInt(1) + 1;
                	}
                    
                    System.err.println("generatedId="+generatedId);
                }
                // String generatedId = prefix + new Integer(id).toString();
            }
            System.err.println("generatedId="+generatedId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return generatedId;
    }

}
 


