/*
 * Copyright 2017 John Grosh (john.a.grosh@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package database;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public abstract class DataManager {
    
    private final DatabaseConnector connector;
    private final String tableName;
    private SQLColumn[] columns;
    
    protected DataManager(DatabaseConnector connector, String tableName)
    {
        this.connector = connector;
        this.tableName = tableName;
        List<Field> fields = new LinkedList<>();
        for(Field field: this.getClass().getDeclaredFields())
        {
            if(field.getType() == SQLColumn.class)
            {
                fields.add(field);
            }
        }
        columns = new SQLColumn[fields.size()];
        try {
            for(int i=0; i<columns.length; i++)
                columns[i] = (SQLColumn)fields.get(i).get(null);
        } catch(IllegalAccessException ex) {}
    }
    
    protected String primaryKey()
    {
        return null;
    }
    
    protected final void read(String query, ResultsConsumer rc)
    {
        try (Statement statement = getConnection().createStatement();
             ResultSet results = statement.executeQuery(query))
        {
            rc.consume(results);
        } catch( SQLException e) {
            DatabaseConnector.LOG.error("Exception in SQL: "+e);
        }
    }
    
    protected final <T> T read(String query, ResultsFunction<T> rf)
    {
        return read(query, rf, null);
    }
    
    protected final <T> T read(String query, ResultsFunction<T> rf, T err)
    {
        try (Statement statement = getConnection().createStatement();
             ResultSet results = statement.executeQuery(query))
        {
            return rf.apply(results);
        } catch( SQLException e) {
            DatabaseConnector.LOG.error("Exception in SQL: "+e);
            return err;
        }
    }
    
    protected final void readWrite(String query, ResultsConsumer rc)
    {
        try (Statement statement = getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
             ResultSet results = statement.executeQuery(query))
        {
            rc.consume(results);
        } catch( SQLException e) {
            DatabaseConnector.LOG.error("Exception in SQL: "+e);
        }
    }
    
    protected final <T> T readWrite(String query, ResultsFunction<T> rf)
    {
        return readWrite(query, rf, null);
    }
    
    protected final <T> T readWrite(String query, ResultsFunction<T> rf, T err)
    {
        try (Statement statement = getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
             ResultSet results = statement.executeQuery(query))
        {
            return rf.apply(results);
        } catch( SQLException e) {
            DatabaseConnector.LOG.error("Exception in SQL: "+e);
            return err;
        }
    }
    
    public final String getTableName()
    {
        return tableName;
    }
    
    public final SQLColumn[] getColumns()
    {
        return (SQLColumn[])columns.clone();
    }
    
    protected final Connection getConnection()
    {
        return connector.getConnection();
    }
    
    protected final String select(String where, SQLColumn... columns)
    {
        String selection = columns[0].name;
        for(int i=1; i<columns.length; i++)
            selection += ", "+columns[i].name;
        return select(where, selection);
    }
    
    protected final String selectAll(String where)
    {
        return select(where, "*");
    }
    
    protected final String selectAll()
    {
        return select(null, "*");
    }
    
    private String select(String where, String columns)
    {
        return "SELECT "+columns+" FROM "+tableName+(where==null ? "" : " WHERE "+where);
    }
    
    @FunctionalInterface
    public interface ResultsConsumer
    {
        public void consume(ResultSet results) throws SQLException;
    }
    
    @FunctionalInterface
    public interface ResultsFunction<T>
    {
        public T apply(ResultSet results) throws SQLException;
    }
}
