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
package database.columns;

import database.SQLColumn;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class IntegerColumn extends SQLColumn<Integer>
{
    
    public IntegerColumn(String name, boolean nullable, int defaultValue)
    {
        super(name, nullable, defaultValue);
    }
    
    @Override
    public String getDataDescription()
    {
        return "INTEGER" + (defaultValue==null ? "" : " DEFAULT "+defaultValue) + nullable();
    }
    
    @Override
    public Integer getValue(ResultSet results) throws SQLException
    {
        return results.getInt(name);
    }

    @Override
    public void updateValue(ResultSet results, Integer newValue) throws SQLException
    {
        results.updateInt(name, newValue);
    }
}
